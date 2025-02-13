import { Component, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Resultado } from 'src/app/modules/Resultado';
import { BenchMark } from 'src/app/modules/BenchMark';
import { BenchMarkService } from '../../service/BenchMarkService';
import { ID } from '@datorama/akita';

@Component({
  selector: 'app-resultado',
  templateUrl: './resultado.component.html',
  styleUrls: ['./resultado.component.scss']
})
export class ResultadoComponent {
  @Input() benchmarkId: ID | undefined;
  benchmark: BenchMark | undefined;

  displayedColumns = ['date', 'location', 'confirmedCases', 'deaths', 'deathRate'];
  dataSource: MatTableDataSource<Resultado> | undefined;

  chartData: any[] | undefined;
  chartLabels: string[] | undefined;
  chartOptions = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };

  constructor(private benchmarkService: BenchMarkService) {}

  ngOnInit() {
    this.loadBenchmark();
  }

  private loadBenchmark() {
    if (this.benchmarkId) {
      this.benchmarkService.getEntity(this.benchmarkId)
        .subscribe(benchmark => {
          this.benchmark = benchmark;
          this.prepareChartData();
          this.dataSource = new MatTableDataSource(benchmark.resultados);
        });
    }
  }

  private prepareChartData() {
    // Preparar dados para o gráfico
  }
}
