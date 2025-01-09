import { Component, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../usuario.service';
@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-usuarios.component.html',
  styleUrls: ['./lista-usuarios.component.css']
})
export class ListaUsuariosComponent implements OnInit {

    user: Usuario[] = [];

    constructor(private userService: UsuarioService){

    }

    ngOnInit(): void{
      this.obtenerUsuarios();
    }

    private obtenerUsuarios(){
      this.userService.obtenerListaUsuarios().subscribe(dato =>{
        console.log('Datos recibidos del backend:', dato);
        this.user = dato; // Asigna los datos a la propiedad "user"
      })
    }
}
