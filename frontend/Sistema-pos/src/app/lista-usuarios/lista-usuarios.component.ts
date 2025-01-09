import { Component, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../usuario.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-usuarios.component.html',
  styleUrls: ['./lista-usuarios.component.css']
})
export class ListaUsuariosComponent implements OnInit {

    user: Usuario[];

    constructor(private userService: UsuarioService, private router:Router){

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

    actualizarUsuario(id: number) {
      this.router.navigate(['actualizar-usuario', id]);
    }

    eliminarUsuario(id: number) {
      Swal.fire({
        title: '¿Estás seguro?',
        text: "Confirma si deseas eliminar al empleado",
        icon: 'warning', // Cambiado 'type' a 'icon'
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, elimínalo',
        cancelButtonText: 'No, cancelar',
        buttonsStyling: true
      }).then((result) => {
        if (result.isConfirmed) {
          this.userService.eliminarUsuario(id).subscribe(dato => {
            console.log(dato);
            this.obtenerUsuarios();
            Swal.fire(
              'Empleado eliminado',
              'El empleado ha sido eliminado con exito',
              'success'
            )
          })
        }
      });
  
    } 

}
