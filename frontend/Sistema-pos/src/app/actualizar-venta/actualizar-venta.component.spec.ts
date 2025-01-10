import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarVentaComponent } from './actualizar-venta.component';

describe('ActualizarVentaComponent', () => {
  let component: ActualizarVentaComponent;
  let fixture: ComponentFixture<ActualizarVentaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualizarVentaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualizarVentaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
