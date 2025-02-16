import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ID } from '@datorama/akita';
import { TipoLocalidade } from 'src/app/modules/enum/TipoLocalidade';
import { BenchMarkService } from 'src/app/service/BenchMarkService';

@Component({
  selector: 'app-bench-mark',
  templateUrl: './bench-mark.component.html',
  styleUrls: ['./bench-mark.component.scss'],
})
export class BenchMarkComponent {
  benchmarkForm: FormGroup;
  tipoLocalidade = TipoLocalidade;
  benchmarkSelecionado: ID | null = null;

  @Output() benchmarkCreated = new EventEmitter<ID>();
  @Output() benchmarkUpdated = new EventEmitter<ID>();
  @Output() searchEvent = new EventEmitter<{ benchmarkId: ID }>();

  constructor(
    private fb: FormBuilder,
    private benchmarkService: BenchMarkService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.benchmarkForm = this.fb.group({
      nome: ['', Validators.required],
      tipoLocalidade: ['', Validators.required],
      estadoLocalidade1: ['', [Validators.required, Validators.maxLength(2), Validators.minLength(2)]],
      localidade1: ['', Validators.required],
      estadoLocalidade2: ['', [Validators.required, Validators.maxLength(2), Validators.minLength(2)]],
      localidade2: ['', Validators.required],
      dataInicial: ['', Validators.required],
      dataFinal: ['', Validators.required],
    });

    this.setupFormListeners();

    const nav = this.router.getCurrentNavigation();
    if (nav?.extras.state && nav.extras.state['benchmark']) {
      this.preencherFormulario(nav.extras.state['benchmark']);
    }
  }

  preencherFormulario(benchmark: any): void {
    this.benchmarkForm.patchValue({
      nome: benchmark.nome,
      tipoLocalidade: benchmark.tipoLocalidade,
      estadoLocalidade1: benchmark.estadoLocalidade1 || '',
      localidade1: benchmark.localidade1 || '',
      estadoLocalidade2: benchmark.estadoLocalidade2 || '',
      localidade2: benchmark.localidade2 || '',
      dataInicial: benchmark.dataInicial,
      dataFinal: benchmark.dataFinal,
    });
    this.benchmarkSelecionado = benchmark.id;
  }

  private setupFormListeners() {
    this.benchmarkForm.get('estadoLocalidade1')?.valueChanges.subscribe((value) => {
      if (value) {
        this.benchmarkForm.patchValue({ estadoLocalidade1: value.toUpperCase() }, { emitEvent: false });
      }
    });

    this.benchmarkForm.get('estadoLocalidade2')?.valueChanges.subscribe((value) => {
      if (value) {
        this.benchmarkForm.patchValue({ estadoLocalidade2: value.toUpperCase() }, { emitEvent: false });
      }
    });
  }

  onSubmit() {
    if (this.benchmarkForm.valid) {
      const formValue = this.benchmarkForm.value;

      console.log('Formulário', formValue);

      if (this.benchmarkSelecionado) {
        this.benchmarkService.update(this.benchmarkSelecionado, formValue).subscribe({
          next: () => {
            this.snackBar.open('Benchmark atualizado com sucesso!', 'Fechar', { duration: 3000 });
            if (this.benchmarkSelecionado !== null) {
              this.benchmarkUpdated.emit(this.benchmarkSelecionado);
            }
          },
          error: (error) => {
            if (error.error?.code === 'LOCALIDADE_NAO_ENCONTRADA') {
              this.snackBar.open(error.error.message, 'Fechar', { duration: 5000 });
            } else {
              this.snackBar.open('Erro ao atualizar benchmark', 'Fechar', { duration: 3000 });
            }
          },
        });
      } else {
        this.benchmarkService.create(formValue).subscribe({
          next: (response: any) => {
            this.snackBar.open('Benchmark criado com sucesso!', 'Fechar', { duration: 3000 });
            this.benchmarkSelecionado = response.id;
            this.benchmarkCreated.emit(response.id);
          },
          error: (error) => {
            if (error.error?.code === 'LOCALIDADE_NAO_ENCONTRADA') {
              this.snackBar.open(error.error.message, 'Fechar', { duration: 5000 });
            } else {
              console.error('Erro ao enviar:', error);
              this.snackBar.open('Erro ao criar benchmark', 'Fechar', { duration: 3000 });
            }
          },
        });
      }
    } else {
      console.warn('Formulário inválido:', this.benchmarkForm.value);
      this.snackBar.open('Formulário inválido. Verifique os campos.', 'Fechar', { duration: 3000 });
    }
  }

  getTipoLocalidadeValues(): string[] {
    return Object.values(this.tipoLocalidade);
  }
}
