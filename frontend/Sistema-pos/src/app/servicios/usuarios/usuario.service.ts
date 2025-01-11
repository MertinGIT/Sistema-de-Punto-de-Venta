import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../../entidades/usuario';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  //URL del get de todos los usuarios.
  private baseURL = "http://localhost:8080/auth/";
  constructor(private httpClient: HttpClient) { }

  obtenerListaUsuarios(): Observable<Usuario[]> {
    // Aquí puedes realizar la solicitud HTTP GET y devolver un Observable.
    return this.httpClient.get<Usuario[]>(`${this.baseURL}listar`);
  }

  registrarUsuario(usuario: Usuario): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}register`, usuario, { responseType: 'text' });
  }

  loginUsuario(usuario: Usuario): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}login`, usuario, { responseType: 'text' });
  }

  //este metodo sirve para actualizar el usuario
  actualizarUsuario(id: number, usuario: Usuario): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}${id}`, usuario, { responseType: 'text' });
  }

  //este metodo sirve para obtener o buscar un Usuario
  obtenerUsuarioPorId(id: number): Observable<Usuario> {
    return this.httpClient.get<Usuario>(`${this.baseURL}${id}`);
  }

  eliminarUsuario(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}${id}`, { responseType: 'text' });
  }
}
