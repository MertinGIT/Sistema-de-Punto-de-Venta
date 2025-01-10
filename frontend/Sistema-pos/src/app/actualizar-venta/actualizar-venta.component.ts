import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { VentaService } from '../venta.service';
import { ProductosService } from '../servicios/productos/productos.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actualizar-venta',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './actualizar-venta.component.html',
  styleUrls: ['./actualizar-venta.component.css']
})
export class ActualizarVentaComponent implements OnInit {
  @Input() venta: any = {}; // Recibe la venta a actualizar desde el componente padre
  @Output() actualizarVentaEvent = new EventEmitter<void>(); // Notifica al padre tras la actualización
  @Output() cancelarEvent = new EventEmitter<void>(); // Notifica para cerrar el modal sin guardar

  productos: any[] = [];

  constructor(
    private ventaService: VentaService,
    private productosService: ProductosService
  ) {}

  ngOnInit(): void {
    this.obtenerProductos();
  }

  obtenerProductos(): void {
    this.productosService.obtenerListaProductos().subscribe((data) => {
      this.productos = data;
    });
  }

  calcularSubtotal(): void {
    const producto = this.productos.find((p) => p.id === Number(this.venta.producto_id));
    if (producto) {
      this.venta.subtotal = producto.precio * this.venta.cantidad;
      this.venta.montoTotal = this.venta.subtotal + this.calcularIVA(this.venta.subtotal);
    }
  }

  calcularIVA(subtotal: number): number {
    return subtotal * 0.1; // 10% IVA
  }

  onActualizarVenta(): void {
    this.ventaService.actualizarVenta(this.venta.id, this.venta).subscribe(() => {
      this.actualizarVentaEvent.emit(); // Notifica que la venta fue actualizada
    });
  }

  onCancelar(): void {
    this.cancelarEvent.emit(); // Notifica que se cancela la operación
  }
}
