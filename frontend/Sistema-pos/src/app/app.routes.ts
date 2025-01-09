import { Routes } from '@angular/router';
import { ActualizarUsuarioComponent } from './componentes/usuarios/actualizar-usuario/actualizar-usuario.component';
import { ListaUsuariosComponent } from './componentes/usuarios/lista-usuarios/lista-usuarios.component';
import { RegistrarUsuarioComponent } from './componentes/usuarios/registrar-usuario/registrar-usuario.component';
export const routes: Routes = [
  { path: 'usuarios', component: ListaUsuariosComponent }, 
  { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
  { path: 'registrar-usuario',component : RegistrarUsuarioComponent},
  { path: 'actualizar-usuario/:id',component : ActualizarUsuarioComponent}
];
