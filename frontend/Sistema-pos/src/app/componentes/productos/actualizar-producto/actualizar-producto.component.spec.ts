import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarProductoComponent } from './actualizar-producto.component';

describe('ActualizarProductoComponent', () => {
  let component: ActualizarProductoComponent;
  let fixture: ComponentFixture<ActualizarProductoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualizarProductoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualizarProductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
