import { Routes } from '@angular/router';
import { ListaUsuariosComponent } from './lista-usuarios/lista-usuarios.component';
import { RegistrarUsuarioComponent } from './registrar-usuario/registrar-usuario.component';

export const routes: Routes = [
  { path: 'usuarios', component: ListaUsuariosComponent }, 
  { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
  { path: 'registrar-usuario',component : RegistrarUsuarioComponent}
];
