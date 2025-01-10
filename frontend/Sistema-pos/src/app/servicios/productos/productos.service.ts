import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Producto } from '../../entidades/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {
  //URL del get de todos los usuarios.
  private baseURL = "http://localhost:8080/api/productos";
  constructor(private httpClient: HttpClient) { }

  obtenerListaProductos(): Observable<Producto[]> {
    // Aquí puedes realizar la solicitud HTTP GET y devolver un Observable.
    return this.httpClient.get<Producto[]>(`${this.baseURL}`);
  }

  agregarProducto(producto:Producto) : Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`, producto, { responseType: 'text' });
  }

    //este metodo sirve para actualizar el usuario
    actualizarProducto(id: number, producto: Producto): Observable<Object> {
      return this.httpClient.put(`${this.baseURL}/${id}`, producto, { responseType: 'text' });
    }
  
    //este metodo sirve para obtener o buscar un Usuario
    obtenerProductoPorId(id: number): Observable<Producto> {
      return this.httpClient.get<Producto>(`${this.baseURL}/${id}`);
    }
  
    eliminarProducto(id: number): Observable<Object> {
      return this.httpClient.delete(`${this.baseURL}/${id}`, { responseType: 'text' });
    }
}
