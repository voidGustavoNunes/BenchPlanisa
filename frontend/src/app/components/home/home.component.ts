import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ID } from '@datorama/akita';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{
  benchmarkSelecionado: ID | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['benchmarkId']) {
        this.benchmarkSelecionado = params['benchmarkId'];
      }
    });
  }

  chartData: any[] = [];

  onSearch(searchParams: any) {
    this.benchmarkSelecionado = searchParams.benchmarkId;
  }

  onBenchmarkCreated(id: ID) {
    this.benchmarkSelecionado = id;
  }

  onBenchmarkUpdated(id: ID) {
    this.benchmarkSelecionado = id;
  }
}
