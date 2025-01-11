import { Routes } from '@angular/router';
import { ActualizarUsuarioComponent } from './componentes/usuarios/actualizar-usuario/actualizar-usuario.component';
import { ListaUsuariosComponent } from './componentes/usuarios/lista-usuarios/lista-usuarios.component';
import { RegistrarUsuarioComponent } from './componentes/usuarios/registrar-usuario/registrar-usuario.component';
import { ProductosComponent } from './componentes/productos/productos.component';
import { ActualizarProductoComponent } from './componentes/productos/actualizar-producto/actualizar-producto.component';
import { AgregarProductoComponent } from './componentes/productos/agregar-producto/agregar-producto.component';
import { LoginComponent } from './componentes/autenticacion/login/login.component';
import { RegistroComponent } from './componentes/autenticacion/registro/registro.component';
import { VentaComponent } from './componentes/venta/venta.component';
export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'usuarios', component: ListaUsuariosComponent },
  { path: 'actualizar-usuario/:id', component: ActualizarUsuarioComponent},
  { path: 'usuarios', component: ListaUsuariosComponent },
  { path: 'productos',component : ProductosComponent},
  { path: 'actualizar-producto/:id', component: ActualizarProductoComponent},
  { path: 'agregar-producto', component: AgregarProductoComponent},
  { path: 'ventas', component:VentaComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registro', component: RegistroComponent},
];
