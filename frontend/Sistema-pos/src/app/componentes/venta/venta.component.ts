import { Component, OnInit } from '@angular/core';
import { VentaService } from '../../servicios/ventas/venta.service';
import { ProductosService } from '../../servicios/productos/productos.service';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { ActualizarVentaComponent } from './../../actualizar-venta/actualizar-venta.component';
import { Venta, DetalleVenta } from './../../entidades/venta';

@Component({
  selector: 'app-venta',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe, ActualizarVentaComponent],
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.css'],
})
export class VentaComponent implements OnInit {
  ventas: any[] = [];
  productos: any[] = [];
  ventaEnEdicion: any = null;
  venta: any = {
    fecha: '',
    metodoPago: '',
    montoTotal: 0.0,
    cantidad: null,
    subtotal: null,
    producto_id: null,
    usuario_id: null,
    detalles: []
  };
  mostrarFormularioVenta = false;

  constructor(
    private ventaService: VentaService,
    private productosService: ProductosService
  ) {}

  ngOnInit(): void {
    this.obtenerVentas();
    this.obtenerProductos();
  }

  obtenerVentas(): void {
    this.ventaService.obtenerListaVentas().subscribe((data) => {
      this.ventas = data;
    });
  }

  obtenerProductos(): void {
    this.productosService.obtenerListaProductos().subscribe((data) => {
      this.productos = data;
    });
  }

  onSubmit(): void {
    const producto = this.productos.find((p) => p.id === Number(this.venta.producto_id));
  
    console.log("ID del producto:", this.venta.producto_id);
    console.log("El producto encontrado es: ", producto);
  
    if (producto) {
      const detalleVenta = {
        producto_id: this.venta.producto_id,
        cantidad: this.venta.cantidad,
        subtotal: producto.precio * this.venta.cantidad,
      };
  
      // Agrega el detalle a la lista de detalles
      this.venta.detalles.push(detalleVenta);
      console.log("Detalles de la venta:", this.venta.detalles);
      // Calcula el monto total
      this.calcularMontoTotal();
    } else {
      console.error("Producto no encontrado o producto_id inválido:", this.venta.producto_id);
      // Aquí podrías mostrar un mensaje de error
    }
  
    // Asigna el usuario ID
    this.venta.usuario_id = this.obtenerUsuarioId();
  
    // Llama al servicio para registrar la venta
    this.ventaService.registrarVenta(this.venta).subscribe(() => {
      this.obtenerVentas();
      this.mostrarFormularioVenta = false;
      this.resetVenta();
    });
  }
  

  calcularIVA(subtotal: number): number {
    return subtotal * 0.1;
  }

  calcularMontoTotal(): void {
    const producto = this.productos.find((p) => Number(p.id) === Number(this.venta.producto_id));

    if (producto) {
      this.venta.subtotal = producto.precio * (this.venta.cantidad || 0);
      this.venta.montoTotal = this.venta.subtotal + this.calcularIVA(this.venta.subtotal);
    } else {
      this.venta.subtotal = 0;
      this.venta.montoTotal = 0;
    }
  }

  obtenerUsuarioId(): number {
    return 7;
  }

  eliminarVenta(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.ventaService.eliminarVenta(id).subscribe(() => this.obtenerVentas());
      }
    });
  }

  verDetallesVenta(id: number): void {
    console.log('ID recibido:', id); // Verifica el ID enviado al método
  
    this.ventaService.obtenerDetalleVenta(id).subscribe((data) => {
      console.log('Respuesta del backend:', data); // Muestra la respuesta completa para depuración
      
      if (data) {
        // Define explícitamente el tipo de detalle
        interface DetalleVenta {
          cantidad: number;
          subtotal: number;
          producto_id: number;
        }
    
        // Extraemos los detalles de la venta
        const detallesVenta: DetalleVenta[] = data.detalles.map((detalle: any) => ({
          cantidad: detalle.cantidad,
          subtotal: detalle.subtotal,
          producto_id: detalle.producto_id,
        }));
    
        // Extraemos la información general de la venta
        const ventaInfo = {
          usuario: data.usuario || 'N/A',
          metodoPago: data.metodoPago || 'N/A',
          montoTotal: data.montoTotal || 0,
          fecha: data.fecha || 'N/A',
          detalles: detallesVenta,
        };
    
        // Mostrar la información en el pop-up
        Swal.fire({
          title: 'Detalles de la Venta',
          html: `
            <strong>Usuario:</strong> ${ventaInfo.usuario || 'Desconocido'}<br>
            <strong>Método de Pago:</strong> ${ventaInfo.metodoPago || 'Desconocido'}<br>
            <strong>Fecha:</strong> ${new Date(ventaInfo.fecha).toLocaleDateString()}<br>
            <strong>Monto Total:</strong> $${ventaInfo.montoTotal || 0}<br>
            <strong>Detalles:</strong><br>
            <ul>
              ${ventaInfo.detalles
                .map((d: DetalleVenta) =>
                  `<li>Producto ID: ${d.producto_id}, Cantidad: ${d.cantidad}, Subtotal: ${d.subtotal}</li>`
                )
                .join('')}
            </ul>
          `,
          icon: 'info',
        });
      } else {
        Swal.fire('Error', 'No se encontraron detalles para esta venta.', 'error');
      }
    });
    
  }
  

  editarVenta(venta: any): void {
    this.ventaEnEdicion = { ...venta };
    this.mostrarFormularioVenta = true; 
  }

  onVentaActualizada(): void {
    this.obtenerVentas();
    this.ventaEnEdicion = null;
  }

  onCancelarEdicion(): void {
    this.ventaEnEdicion = null;
  }

  resetVenta(): void {
    this.venta = {
      fecha: '',
      metodoPago: '',
      montoTotal: 0.0,
      detalles: [], 
      usuario_id: null,
    };
  }

  detalleSeleccionado = {
    producto_id: null,
    cantidad: 1,
    subtotal: 0,
  };
  
  agregarDetalle(): void {
    const producto = this.productos.find((p) => p.id === this.detalleSeleccionado.producto_id);
  
    if (!producto) {
      console.error('Producto no encontrado');
      return;
    }
  
    if (producto.stock < this.detalleSeleccionado.cantidad) {
      console.error('Stock insuficiente para el producto:', producto.nombre);
      return;
    }
  
    const detalleVenta: DetalleVenta = {
      producto_id: producto.id,
      cantidad: this.detalleSeleccionado.cantidad,
      subtotal: this.detalleSeleccionado.subtotal,
    };
  
    this.venta.detalles.push(detalleVenta);
    this.calcularMontoTotal();
    this.resetDetalleSeleccionado();
  }

  resetDetalleSeleccionado(): void {
    this.detalleSeleccionado = {
      producto_id: null,
      cantidad: 1,
      subtotal: 0,
    };
  }

  calcularSubtotal(detalle: any): void {
    const producto = this.productos.find((p) => p.id === detalle.productoId);
    if (producto) {
      detalle.subtotal = producto.precio * detalle.cantidad;
    }
  }
  
}
