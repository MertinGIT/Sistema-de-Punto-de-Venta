import { Component, OnInit } from '@angular/core';
import { VentaService } from '../../servicios/ventas/venta.service';
import { ProductosService } from '../../servicios/productos/productos.service'; // Importar el servicio de productos
import Swal from 'sweetalert2';
import { catchError, of, tap } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { ActualizarVentaComponent } from '../../actualizar-venta/actualizar-venta.component'; // Importar el componente

import { ActivatedRoute, Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { UsuarioService } from '../../servicios/usuarios/usuario.service';

@Component({
  selector: 'app-venta',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe, ActualizarVentaComponent,NavbarComponent],
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.css'],
})
export class VentaComponent implements OnInit {
  ventas: any[] = [];
  productos: any[] = [];  // Almacenar productos
  ventaEnEdicion: any = null; // Venta seleccionada para edición
  venta: any = {
    fecha: '',
    metodoPago: '',
    montoTotal: 0.0,
    cantidad: null,
    subtotal: null,
    producto_id: null,
    usuarioId: null,
  };
  mostrarFormularioVenta = false;

  constructor(
    private ventaService: VentaService,
    private productosService: ProductosService, // Inyectar el servicio de productos
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.obtenerVentas();
    this.obtenerProductos();  // Obtener la lista de productos
  }

  obtenerVentas(): void {
    this.ventaService.obtenerListaVentas().subscribe((data) => {
      this.ventas = data;
    });
  }

  obtenerProductos(): void {
    this.productosService.obtenerListaProductos().subscribe((data) => {
      console.log('Productos obtenidos desde la API:', data);
      this.productos = data;
    });
  }

  onSubmit(): void {
    // Obtención del producto seleccionado y cálculo de subtotal y monto total
    const producto = this.productos.find((p) => p.id === this.venta.producto_id);
    console.log(producto)
    if (producto) {
      this.venta.subtotal = producto.precio * this.venta.cantidad;
      this.venta.montoTotal = this.venta.subtotal + this.calcularIVA(this.venta.subtotal);
    }
    this.venta.usuarioId = this.obtenerUsuarioId();
    console.log("** Venta a enviar: ",this.venta);

    // Enviar la venta al servidor
    this.ventaService.registrarVenta(this.venta).subscribe(() => {
      this.obtenerVentas();
      this.mostrarFormularioVenta = false;
      this.venta = { fecha: '', metodoPago: '', montoTotal: 0.0, cantidad: null, producto_id: null };
    });
  }

  calcularIVA(subtotal: number): number {
    return subtotal * 0.10; // 10% IVA
  }

  calcularMontoTotal(): void {
    console.log('Productos obtenidos desde la API:', this.productos);
    console.log('Producto ID seleccionado:', this.venta.producto_id);
  
    // Asegúrate de convertir ambos valores al mismo tipo
    const producto = this.productos.find((p) => p.id === Number(this.venta.producto_id));
  
    if (producto) {
      console.log('Producto encontrado:', producto);
      const cantidad = this.venta.cantidad || 0;
      this.venta.subtotal = producto.precio * cantidad;
      this.venta.montoTotal = this.venta.subtotal + this.calcularIVA(this.venta.subtotal);
    } else {
      console.error('Producto no encontrado.');
      this.venta.subtotal = 0;
      this.venta.montoTotal = 0;
    }
  }
  
  

  obtenerUsuarioId(): number {
    // Obtiene el idUsuario desde el servicio
    const idS: string | null = this.usuarioService.getIdUsuario(); // El valor podría ser null
  
    if (!idS) {
      throw new Error('Usuario no logueado o idUsuario no encontrado');
    }
  
    const id: number = Number(idS);
  
    if (isNaN(id)) {
      throw new Error('El idUsuario no es un número válido');
    }
  
    return id;
  }
  

  eliminarVenta(id: number): void {
    this.ventaService.eliminarVenta(id).subscribe(() => this.obtenerVentas());
  }

  verDetallesVenta(id: number): void {
    this.ventaService.obtenerVentaPorId(id).subscribe((data) => {
      console.log(data); // Mostrar detalles en consola
    });
  }


  editarVenta(venta: any): void {
    this.ventaEnEdicion = { ...venta }; // Clona la venta seleccionada para editar
  }
  
  onVentaActualizada(): void {
    this.obtenerVentas(); // Refresca la lista de ventas
    this.ventaEnEdicion = null; // Cierra el modal
  }
  
  onCancelarEdicion(): void {
    this.ventaEnEdicion = null; // Cierra el modal sin guardar
  }
  
}
