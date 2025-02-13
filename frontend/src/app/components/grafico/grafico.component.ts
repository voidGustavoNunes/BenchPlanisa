import { Component, Input, OnChanges } from '@angular/core';
import { ChartConfiguration } from 'chart.js';

@Component({
  selector: 'app-grafico',
  templateUrl: './grafico.component.html',
  styleUrls: ['./grafico.component.scss']
})
export class GraficoComponent implements OnChanges {
  @Input() data: any[] = [];

  chartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Casos - Localidade 1',
        borderColor: 'rgb(59, 130, 246)',
        tension: 0.1
      },
      {
        data: [],
        label: 'Casos - Localidade 2',
        borderColor: 'rgb(239, 68, 68)',
        tension: 0.1
      }
    ]
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
        title: {
          display: true,
          text: 'Número de Casos'
        }
      },
      x: {
        title: {
          display: true,
          text: 'Data'
        }
      }
    }
  };

  ngOnChanges() {
    if (this.data.length) {
      this.updateChartData();
    }
  }

  private updateChartData() {
    // Agrupa os dados por localidade
    const location1Data = this.data.filter(d => d.localidade === this.data[0].localidade);
    const location2Data = this.data.filter(d => d.localidade === this.data[this.data.length - 1].localidade);

    // Atualiza as labels (datas)
    this.chartData.labels = location1Data.map(d =>
      new Date(d.data).toLocaleDateString('pt-BR')
    );

    // Atualiza os dados de casos para cada localidade
    this.chartData.datasets[0].data = location1Data.map(d => d.casos);
    this.chartData.datasets[0].label = `Casos - ${location1Data[0].localidade}`;

    this.chartData.datasets[1].data = location2Data.map(d => d.casos);
    this.chartData.datasets[1].label = `Casos - ${location2Data[0].localidade}`;

    // Força a atualização do gráfico
    this.chartData = { ...this.chartData };
  }

}
