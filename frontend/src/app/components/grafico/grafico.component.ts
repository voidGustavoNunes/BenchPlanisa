import { HttpClient } from '@angular/common/http';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ID } from '@datorama/akita';
import { ChartConfiguration } from 'chart.js';
import { Comparacao } from 'src/app/modules/Comparacao';
import { ComparacaoService } from 'src/app/service/ComparacaoService';

@Component({
  selector: 'app-grafico',
  templateUrl: './grafico.component.html',
  styleUrls: ['./grafico.component.scss']
})
export class GraficoComponent implements OnChanges {
  constructor(private http: HttpClient, private comparacaoService: ComparacaoService) {}

  @Input() benchmarkId: ID | null = null;

  metricaSelecionada: string = 'confirmados';
  filtroDataInicial: string | null = null;
  filtroDataFinal: string | null = null;

  dadosCompletos: Comparacao[] = [];

  chartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: []
  };

  chartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    plugins: {
      legend: { display: true },
      tooltip: { enabled: true }
    },
    scales: {
      y: {
        beginAtZero: true,
        title: { display: true, text: 'Número de Casos' }
      },
      x: {
        title: { display: true, text: 'Data' },
        ticks: {
          autoSkip: true,
          maxTicksLimit: 10,
          maxRotation: 45,
          minRotation: 45
        }
      }
    }
  };

  ngOnChanges(changes: SimpleChanges) {
    if (changes['benchmarkId'] && this.benchmarkId) {
      this.carregarDados();
    }
  }

  private carregarDados() {
    if (!this.benchmarkId) return;

    this.comparacaoService.carregarDadosComparacao(this.benchmarkId).subscribe({
      next: (dados) => {
        this.dadosCompletos = dados;
        this.aplicarFiltros();
      },
      error: (erro) => console.error('Erro ao carregar dados:', erro)
    });
  }

  aplicarFiltros() {
    if (!this.dadosCompletos.length) return;

    let dadosFiltrados = [...this.dadosCompletos];


    if (this.filtroDataInicial) {
      const dataInicial = new Date(this.filtroDataInicial);
      dadosFiltrados = dadosFiltrados.filter(d => new Date(d.data) >= dataInicial);
    }


    if (this.filtroDataFinal) {
      const dataFinal = new Date(this.filtroDataFinal);
      dataFinal.setHours(23, 59, 59); 
      dadosFiltrados = dadosFiltrados.filter(d => new Date(d.data) <= dataFinal);
    }

    this.atualizarGrafico(dadosFiltrados);
  }

  private atualizarGrafico(dados: Comparacao[]) {
    if (!dados.length) return;

    const labels = dados.map(d => new Date(d.data).toLocaleDateString('pt-BR'));

    let valor1: number[] = [];
    let valor2: number[] = [];
    let labelMetrica = '';


    switch (this.metricaSelecionada) {
      case 'confirmados':
        valor1 = dados.map(d => d.confirmados1);
        valor2 = dados.map(d => d.confirmados2);
        labelMetrica = 'Casos Confirmados';
        this.atualizarEscalaY('Número de Casos');
        break;
      case 'mortes':
        valor1 = dados.map(d => d.mortes1);
        valor2 = dados.map(d => d.mortes2);
        labelMetrica = 'Óbitos';
        this.atualizarEscalaY('Número de Óbitos');
        break;
      case 'taxaLetalidade':
        valor1 = dados.map(d => d.taxaLetalidade1);
        valor2 = dados.map(d => d.taxaLetalidade2);
        labelMetrica = 'Taxa de Letalidade (%)';
        this.atualizarEscalaY('Taxa (%)');
        break;
      case 'casosPor100kHab':
        valor1 = dados.map(d => d.casosPor100kHab1);
        valor2 = dados.map(d => d.casosPor100kHab2);
        labelMetrica = 'Casos por 100 mil hab.';
        this.atualizarEscalaY('Casos por 100 mil hab.');
        break;
    }

    this.chartData = {
      labels,
      datasets: [
        {
          data: valor1,
          label: `${dados[0].identificadorLocalidade1} - ${labelMetrica}`,
          borderColor: 'rgb(59, 130, 246)',
          tension: 0.1
        },
        {
          data: valor2,
          label: `${dados[0].identificadorLocalidade2} - ${labelMetrica}`,
          borderColor: 'rgb(239, 68, 68)',
          tension: 0.1
        }
      ]
    };
  }

  private atualizarEscalaY(texto: string) {
    if (this.chartOptions?.scales?.['y']) {
      this.chartOptions.scales['y'].title = {
        display: true,
        text: texto
      };
    }
  }
}
