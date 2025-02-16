import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ID } from '@datorama/akita';
import { BenchMark } from 'src/app/modules/BenchMark';
import { ComparacaoDTO } from 'src/app/modules/dto/ComparacaoDTO';
import { BenchMarkService } from 'src/app/service/BenchMarkService';
import { ComparacaoService } from 'src/app/service/ComparacaoService';

@Component({
  selector: 'app-tabela',
  templateUrl: './tabela.component.html',
  styleUrls: ['./tabela.component.scss']
})
export class TabelaComponent implements OnInit {

  comparacoes: ComparacaoDTO[] = [];
  benchMarks: BenchMark[] = [];
  loading = false;


  constructor(private benchMarkService: BenchMarkService, private router: Router, private comparacaoService: ComparacaoService) {}

  ngOnInit(): void {
    this.carregarBenchmarks();
  }

  carregarBenchmarks(): void {
    this.loading = true;
    this.benchMarkService.getList().subscribe({
      next: (data) => {
        this.benchMarks = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar benchmarks', error);
        this.loading = false;
        alert('Erro ao carregar os dados. Por favor, tente novamente.');
      }
    });
  }

  atualizarBenchmark(benchmark: BenchMark): void {
    this.router.navigate(['/home'], { state: { benchmark } });
  }

  visualizarComparacao(id: ID): void {
    this.router.navigate(['/home'], {
      queryParams: { benchmarkId: id },
      state: { fromComparacao: true }
    });
  }


  excluirBenchmark(id: ID): void {
    if (confirm('Tem certeza que deseja excluir este benchmark?')) {
      this.loading = true;
      this.benchMarkService.delete(id).subscribe({
        next: () => {
          this.benchMarks = this.benchMarks.filter(b => b.id !== id);
          this.loading = false;
          alert('Benchmark excluído com sucesso!');
        },
        error: (error) => {
          console.error('Erro ao excluir benchmark', error);
          this.loading = false;
          alert('Erro ao excluir o benchmark. Por favor, tente novamente.');
        }
      });
    }
  }


}
