import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { ListaUsuariosComponent } from './componentes/usuarios/lista-usuarios/lista-usuarios.component';
import { RegistrarUsuarioComponent } from './componentes/usuarios/registrar-usuario/registrar-usuario.component';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ProductManager';
}

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient() // Asegúrate de que esto esté presente
  ]
}).catch(err => console.error(err));
