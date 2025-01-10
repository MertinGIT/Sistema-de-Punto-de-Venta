import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Producto } from '../../../entidades/producto';
import { ProductosService } from '../../../servicios/productos/productos.service';
import { Router, RouterLink } from '@angular/router';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'app-agregar-producto',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './agregar-producto.component.html',
  styleUrl: './agregar-producto.component.css'
})
export class AgregarProductoComponent {
  producto: Producto = new Producto();
  constructor(private productoService: ProductosService, private router: Router) { }

  ngOnInit(): void {

  }

  guardarProducto() {

    this.productoService.agregarProducto(this.producto).subscribe(dato => {
      console.log(dato)
      this.irListaProducto();
    });
  }

  irListaProducto() {
    this.router.navigate(['/productos']);
  }

  onSubmit() {
    this.guardarProducto();
  }
}
