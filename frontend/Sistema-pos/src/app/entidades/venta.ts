export interface DetalleVenta {
    producto_id: number;
    cantidad: number;
    subtotal: number;
  }


export interface Venta {
    fecha: string;
    metodoPago: string;
    montoTotal: number;
    detalles: DetalleVenta[];
    usuario_id: number | null;
  }