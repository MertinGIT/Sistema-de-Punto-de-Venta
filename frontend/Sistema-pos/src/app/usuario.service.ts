import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from './usuario';
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

  registrarUsuario(usuario:Usuario) : Observable<Object>{

    return this.httpClient.post(`${this.baseURL}register`, usuario, { responseType: 'text' });
  }
}
