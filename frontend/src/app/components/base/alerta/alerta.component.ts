import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export type TipoAlerta = 'sucesso' | 'erro' | 'aviso' | 'info';

@Component({
  selector: 'app-alerta',
  templateUrl: './alerta.component.html',
  styleUrls: ['./alerta.component.scss']
})
export class AlertaComponent {

  constructor(
    public dialogRef: MatDialogRef<AlertaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { titulo: string, mensagem: string, tipo: TipoAlerta }
  ) {}

  fechar(): void {
    this.dialogRef.close();
  }

  get classeAlerta(): string {
    return `alerta-${this.data.tipo}`;
  }
}
