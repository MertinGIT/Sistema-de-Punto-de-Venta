export class DetalleVenta {
  id: number;          // ID del detalle de la venta
  cantidad: number;    // Cantidad del producto vendido
  subtotal: number;    // Subtotal (cantidad * precio del producto)
  producto_id: number; // ID del producto relacionado
  venta_id: number;    // ID de la venta a la que pertenece el detalle

  constructor(id: number, cantidad: number, subtotal: number, producto_id: number, venta_id: number) {
    this.id = id;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
    this.producto_id = producto_id;
    this.venta_id = venta_id;
  }
}