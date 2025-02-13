import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AlertaComponent } from './alerta/alerta.component';

@Injectable({
  providedIn: 'root'
})
export class AlertaService {
  constructor(private dialog: MatDialog) {}

  exibirAlerta(titulo: string, mensagem: string): void {
    this.dialog.open(AlertaComponent, {
      data: { titulo, mensagem },
      width: '350px'
    });
  }
}
