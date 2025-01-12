import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { Venta } from './'; // Asegúrate de tener una clase 'Venta' en tu proyecto.

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private baseURL = "http://localhost:8080/api/ventas"; // Asegúrate que esta es la URL correcta de tu API.

  constructor(private httpClient: HttpClient) { }

  // Obtener lista de todas las ventas
  obtenerListaVentas(): Observable<any[]> {
    return this.httpClient.get<any[]>(`${this.baseURL}`);
  }

  // Obtener una venta por su ID
  obtenerVentaPorId(id: number): Observable<any> {
    return this.httpClient.get<any>(`${this.baseURL}/${id}`);
  }

  // Registrar una nueva venta
  registrarVenta(venta: any): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, venta, { responseType: 'text' , withCredentials: true});
  }

  // Actualizar una venta existente
  actualizarVenta(id: number, venta: any): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, venta, { responseType: 'text' });
  }

  // Eliminar una venta
  eliminarVenta(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`, { responseType: 'text' });
  }
}
