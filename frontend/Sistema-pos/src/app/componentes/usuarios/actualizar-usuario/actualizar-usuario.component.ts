import { Component } from '@angular/core';
import { Usuario } from '../../../entidades/usuario';
import { UsuarioService } from '../../../servicios/usuarios/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { catchError, of, tap } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'app-actualizar-usuario',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './actualizar-usuario.component.html',
  styleUrl: './actualizar-usuario.component.css'
})
export class ActualizarUsuarioComponent {

  id: number;
  usuario: Usuario = new Usuario();
  constructor(private usuarioService: UsuarioService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.usuarioService.obtenerUsuarioPorId(this.id).pipe(
      tap(dato => { //realiza algun efecto secundario
        this.usuario = dato;
      }),
      catchError(error => {
        console.error(error);
        return of(null); // Retorna un observable vacío en caso de error
      })
    ).subscribe();
  }

  irAlaListaDeUsuarios() {
    this.router.navigate(['/usuarios']);
    Swal.fire('Usuario actualizado', `El usuario ${this.usuario.nombre} ha sido actualizado con exito`, `success`);
  }

  onSubmit(): void {
    if (this.usuario) {
      this.usuarioService.actualizarUsuario(this.id, this.usuario).pipe(
        tap(dato => {
          this.irAlaListaDeUsuarios(); // Redirige en caso de éxito
        }),
        catchError(error => {
          console.error('Error al actualizar el usuario:', error);
          return of(null); // Retorna un observable vacío en caso de error
        })
      ).subscribe(); // Realiza la suscripción
    }
  }

}