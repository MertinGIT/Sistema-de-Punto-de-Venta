import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Usuario } from '../../../entidades/usuario';
import { UsuarioService } from '../../../servicios/usuarios/usuario.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {
  usuario: Usuario = new Usuario();
  constructor(private usuarioService: UsuarioService, private router: Router) { }

  ngOnInit(): void {

  }

  registrarUsuario() {
    this.usuarioService.registrarUsuario(this.usuario).subscribe(dato => {
      console.log(dato)
      this.ingresar();
    });
  }

  ingresar() {
    this.router.navigate(['/login']);
  }

  onSubmit() {
    this.registrarUsuario();
  }
}
