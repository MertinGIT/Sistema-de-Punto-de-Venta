import { Routes } from '@angular/router';
import { ActualizarUsuarioComponent } from './componentes/usuarios/actualizar-usuario/actualizar-usuario.component';
import { ListaUsuariosComponent } from './componentes/usuarios/lista-usuarios/lista-usuarios.component';
import { RegistrarUsuarioComponent } from './componentes/usuarios/registrar-usuario/registrar-usuario.component';
import { ProductosComponent } from './componentes/productos/productos.component';
import { VentasComponent } from './componentes/ventas/ventas.component';
export const routes: Routes = [
  { path: 'usuarios', component: ListaUsuariosComponent }, 
  { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
  { path: 'productos',component : ProductosComponent},
  { path: 'ventas',component : VentasComponent}
];
