import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Producto } from '../../../entidades/producto';
import { ProductosService } from '../../../servicios/productos/productos.service';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { tap, catchError, of } from 'rxjs';
import Swal from 'sweetalert2';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'app-actualizar-producto',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './actualizar-producto.component.html',
  styleUrl: './actualizar-producto.component.css'
})
export class ActualizarProductoComponent {
  id:number;
  producto: Producto = new Producto();
  constructor(private productoService: ProductosService, private router: Router, private route: ActivatedRoute) { }
  
    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
  
      this.productoService.obtenerProductoPorId(this.id).pipe(
        tap(dato => { //realiza algun efecto secundario
          this.producto = dato;
        }),
        catchError(error => {
          console.error(error);
          return of(null); // Retorna un observable vacío en caso de error
        })
      ).subscribe();
    }
  
    irAlaListaDeProductos() {
      this.router.navigate(['/productos']);
      Swal.fire('Producto actualizado', `El producto ${this.producto.nombre} ha sido actualizado con exito`, `success`);
    }
  
    onSubmit(): void {
      if (this.producto) {
        this.productoService.actualizarProducto(this.id, this.producto).pipe(
          tap(dato => {
            this.irAlaListaDeProductos(); // Redirige en caso de éxito
          }),
          catchError(error => {
            console.error('Error al actualizar el usuario:', error);
            return of(null); // Retorna un observable vacío en caso de error
          })
        ).subscribe(); // Realiza la suscripción
      }
    }
  
}
