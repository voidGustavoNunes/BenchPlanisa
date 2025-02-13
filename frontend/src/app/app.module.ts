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
import { MatCardModule } from '@angular/material/card';
import { ResultadoComponent } from './components/resultado/resultado.component';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SobreComponent } from './components/sobre/sobre.component';

@NgModule({
  declarations: [
    AppComponent,
    BenchMarkComponent,
    GraficoComponent,
    MenuCategoriesComponent,
    TabelaComponent,
    HomeComponent,
    ResultadoComponent,
    SobreComponent
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
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
