import { Component, OnInit } from '@angular/core';
import { LocalidadeService } from '../../service/LocalidadeService';
import { AlertaService } from '../base/alertaService';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private localidadeService: LocalidadeService, private alertaService: AlertaService) {}
  chartData: any[] = [];

  onSearch(searchParams: any) {
    console.log('Search params:', searchParams);
  }

  ngOnInit(): void {
    this.verificarEPopularDados();
  }

  private verificarEPopularDados(): void {
    this.localidadeService.getEstados().subscribe({
      next: (estados) => {
        if (estados.length === 0) {
          this.localidadeService.popularEstadosEMunicipios().subscribe({
            next: () => this.alertaService.exibirAlerta('Sucesso', 'Estados e municípios cadastrados com sucesso!'),
            error: (err) => this.alertaService.exibirAlerta('Erro', 'Erro ao popular banco: ' + err.message)
          });
        } else {
          this.alertaService.exibirAlerta('Informação', 'Os estados já estão cadastrados. Nenhuma ação necessária.');
        }
      },
      error: (err) => this.alertaService.exibirAlerta('Erro', 'Erro ao verificar estados: ' + err.message)
    });
  }
}
