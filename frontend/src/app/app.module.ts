import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { NgChartsModule } from 'ng2-charts';
import { AppComponent } from './app.component';
import { BenchMarkComponent } from './components/bench-mark/bench-mark.component';
import { GraficoComponent } from './components/grafico/grafico.component';
import { MenuCategoriesComponent } from './components/menu-categories/menu-categories.component';
import { TabelaComponent } from './components/tabela/tabela.component';
import { HomeComponent } from './components/home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SobreComponent } from './components/sobre/sobre.component';
import { AlertaComponent } from './components/base/alerta/alerta.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RodapeComponent } from './components/rodape/rodape.component';
import { BenchMarkService } from './service/BenchMarkService';
import { ComparacaoService } from './service/ComparacaoService';
import { RouterModule } from '@angular/router';
import { MatNativeDateModule } from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent,
    BenchMarkComponent,
    GraficoComponent,
    MenuCategoriesComponent,
    TabelaComponent,
    HomeComponent,
    SobreComponent,
    AlertaComponent,
    RodapeComponent,
  ],
  imports: [
    BrowserModule,
    NgChartsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    CommonModule,
    MatCardModule,
    MatButtonModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatPaginatorModule,
    MatSnackBarModule,
    RouterModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatSelectModule,
  ],
  providers: [
    BenchMarkService,
    ComparacaoService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
