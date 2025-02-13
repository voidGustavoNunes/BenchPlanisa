import { Component} from '@angular/core';
import { BenchMarkService } from '../../service/BenchMarkService';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-bench-mark',
  templateUrl: './bench-mark.component.html',
  styleUrls: ['./bench-mark.component.scss']
})
export class BenchMarkComponent {

  benchmarkForm: FormGroup;
  comparisonData: any[] = [];

  constructor(
    private fb: FormBuilder,
    private benchmarkService: BenchMarkService,
    private snackBar: MatSnackBar
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
}
