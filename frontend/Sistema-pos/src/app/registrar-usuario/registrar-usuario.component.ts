import { Component } from '@angular/core';
import { Usuario } from '../usuario';
import { UsuarioService } from '../usuario.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registrar-usuario',
  standalone: true,
  imports: [FormsModule, CommonModule ],
  templateUrl: './registrar-usuario.component.html',
  styleUrl: './registrar-usuario.component.css'
})
export class RegistrarUsuarioComponent {
  usuario: Usuario = new Usuario();
  constructor(private usuarioService:UsuarioService, private router:Router){}

  ngOnInit(): void{

  }

  guardarUsuario(){

    this.usuarioService.registrarUsuario(this.usuario).subscribe(dato => {
      console.log(dato)
      this.irListaUsuario();
    });
  }

  irListaUsuario(){
    this.router.navigate(['/']);
  }

  onSubmit(){
    this.guardarUsuario();
  }
}
