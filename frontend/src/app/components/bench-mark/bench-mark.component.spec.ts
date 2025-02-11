import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BenchMarkComponent } from './bench-mark.component';

describe('BenchMarkComponent', () => {
  let component: BenchMarkComponent;
  let fixture: ComponentFixture<BenchMarkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BenchMarkComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BenchMarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
