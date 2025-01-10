import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Producto } from '../../entidades/producto';
import { ProductosService } from '../../servicios/productos/productos.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AppComponent } from '../../app.component';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit{

product: Producto[];

    constructor(private productoService: ProductosService, private router:Router){

    }

    ngOnInit(): void{
      this.obtenerProductos();
    }

    private obtenerProductos(){
      this.productoService.obtenerListaProductos().subscribe(dato =>{
        console.log('Datos recibidos del backend:', dato);
        this.product = dato;
      })
    }

    actualizarProducto(id: number) {
      this.router.navigate(['actualizar-producto', id]);
    }

    agregarProducto() {
      this.router.navigate(['agregar-producto']);
    }

    eliminarProducto(id: number) {
      Swal.fire({
        title: '¿Estás seguro?',
        text: "Confirma si deseas eliminar el producto",
        icon: 'warning', // Cambiado 'type' a 'icon'
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, elimínalo',
        cancelButtonText: 'No, cancelar',
        buttonsStyling: true
      }).then((result) => {
        if (result.isConfirmed) {
          this.productoService.eliminarProducto(id).subscribe(dato => {
            console.log(dato);
            this.obtenerProductos();
            Swal.fire(
              'Producto eliminado',
              'El producto ha sido eliminado con exito',
              'success'
            )
          })
        }
      });
  
    } 

}
