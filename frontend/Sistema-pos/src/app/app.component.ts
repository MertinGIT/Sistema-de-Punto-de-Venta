import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ListaUsuariosComponent } from './lista-usuarios/lista-usuarios.component';
import { provideHttpClient } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { RegistrarUsuarioComponent } from './registrar-usuario/registrar-usuario.component';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    ListaUsuariosComponent,
    RegistrarUsuarioComponent
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
