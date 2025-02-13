import { Component, OnInit} from '@angular/core';
import { BenchMarkService } from '../../service/BenchMarkService';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TipoLocalidade } from 'src/app/modules/enum/TipoLocalidade';
import { map, Observable, startWith, switchMap } from 'rxjs';
import { Estado } from 'src/app/modules/Estado';
import { Municipio } from 'src/app/modules/Municipio';
import { LocalidadeService } from 'src/app/service/LocalidadeService';

@Component({
  selector: 'app-bench-mark',
  templateUrl: './bench-mark.component.html',
  styleUrls: ['./bench-mark.component.scss']
})
export class BenchMarkComponent implements OnInit {

  benchmarkForm: FormGroup;
  comparisonData: any[] = [];
  tipoLocalidade = TipoLocalidade;
  filteredOptions$: Observable<(Estado | Municipio)[]> = new Observable();

  constructor(
    private fb: FormBuilder,
    private benchmarkService: BenchMarkService,
    private snackBar: MatSnackBar,
    private localidadeService: LocalidadeService
  ) {
    this.benchmarkForm = this.fb.group({
      name: ['', Validators.required],
      locationType: ['', Validators.required],
      location1: ['', Validators.required],
      location2: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }
  
  ngOnInit(): void {
    this.filteredOptions$ = this.benchmarkForm.get('location1')!.valueChanges.pipe(
      startWith(''),
      switchMap(value => this.buscarLocalidades(value))
    );
  }

  onSubmit() {
    if (this.benchmarkForm.valid) {
      this.benchmarkService.create(this.benchmarkForm.value)
        .subscribe({
          next: (response: any) => {
            this.snackBar.open('Benchmark criado com sucesso!', 'Fechar', {
              duration: 3000
            });

            this.loadComparisonData();
          },
          error: (error: any) => {
            this.snackBar.open('Erro ao criar benchmark', 'Fechar', {
              duration: 3000
            });
          }
        });
    }
  }

  getTipoLocalidadeValues(): string[] {
    return Object.values(this.tipoLocalidade);
  }

  private loadComparisonData() {
    const formValue = this.benchmarkForm.value;

    this.benchmarkService.compararLocalidades(
      formValue.location1,
      formValue.location2,
      formValue.locationType,
      formValue.startDate,
      formValue.endDate
    ).subscribe({
      next: (data) => {
        this.comparisonData = data;
      },
      error: (error) => {
        this.snackBar.open('Erro ao carregar dados de comparação', 'Fechar', {
          duration: 3000
        });
      }
    });
  }

  buscarLocalidades(query: string): Observable<(Estado | Municipio)[]> {
    if (!query || query.length < 2) return new Observable(obs => obs.next([]));

    return this.benchmarkForm.get('locationType')!.value === 'Estado'
      ? this.localidadeService.getEstados().pipe(
          map(estados => estados.filter(e => e.sigla.toLowerCase().includes(query.toLowerCase())))
        )
      : this.localidadeService.getMunicipios().pipe(
          map(municipios => municipios.filter(m => m.nome.toLowerCase().includes(query.toLowerCase())))
        );
  }

  exibirNome(option: Estado | Municipio): string {
    return 'sigla' in option ? option.sigla : option.nome;
  }


}
