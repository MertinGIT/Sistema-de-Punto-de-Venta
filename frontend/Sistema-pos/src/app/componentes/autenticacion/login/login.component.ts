import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Usuario } from '../../../entidades/usuario';
import { UsuarioService } from '../../../servicios/usuarios/usuario.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink,FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
   usuario: Usuario = new Usuario();
    constructor(private usuarioService: UsuarioService, private router: Router) { }
  
    ngOnInit(): void {
  
    }
  
    verificarUsuario() {
      this.usuarioService.loginUsuario(this.usuario).subscribe({
        next: (response: { token: string; idUsuario: number }) => {
          // Guardar los datos en el servicio de autenticación
          this.usuarioService.saveUserData(response.token, response.idUsuario);
    
          // Confirmar en consola
          console.log('Usuario ID:', response.idUsuario);
    
          // Llamar a la función ingresar o redirigir al usuario
          this.ingresar();
        },
        error: (err) => {
          console.error('Error al verificar el usuario:', err);
        },
      });
    }
    
  
    ingresar() {
      this.router.navigate(['/usuarios']);
    }
  
    onSubmit() {
      this.verificarUsuario();
    }
}
