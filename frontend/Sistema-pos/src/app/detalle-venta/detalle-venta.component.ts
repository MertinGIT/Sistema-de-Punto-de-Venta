import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VentaService } from '../servicios/ventas/venta.service';
import { Producto } from '../entidades/producto';
import { Usuario } from '../entidades/usuario';
import { ProductosService } from '../servicios/productos/productos.service';

@Component({
  selector: 'app-detalle-venta',
  templateUrl: './detalle-venta.component.html',
  styleUrls: ['./detalle-venta.component.css']
})
export class DetalleVentaComponent implements OnInit {
  ventaId: number;
  ventaDetalles: any;
  usuario: Usuario;
  producto: Producto;
  iva: number = 0;

  constructor(
    private route: ActivatedRoute,
    private ventaService: VentaService,
    private productosService: ProductosService
  ) {}
  productos: any[] = [];

  ngOnInit(): void {
    // Obtener el ID de la venta desde la URL
    this.ventaId = Number(this.route.snapshot.paramMap.get('ventaId'));
    this.obtenerDetallesVenta();
  }
  obtenerProductos(): void {
    this.productosService.obtenerListaProductos().subscribe((data) => {
      console.log("Los datos recibidos son: ",data)
      this.productos = data;
    });
  }
  obtenerDetallesVenta(): void {
    // Primero, obtener los detalles de la venta
    
    this.ventaService.obtenerDetalleVenta(this.ventaId).subscribe((data) => {
      this.ventaDetalles = data;
      
      // Luego, obtener el producto relacionado con esta venta
      this.ventaService.obtenerProductoPorId(data.producto_id).subscribe((productoData) => {
        this.producto = productoData;
        this.calcularIVA(this.producto.precio * data.cantidad);
      });

      // Obtener el usuario que hizo la venta
      this.ventaService.obtenerUsuarioPorId(data.usuario_id).subscribe((usuarioData) => {
        this.usuario = usuarioData;
      });
    });
  }

  calcularIVA(subtotal: number): void {
    this.iva = subtotal * 0.1; // 10% de IVA
  }
}
