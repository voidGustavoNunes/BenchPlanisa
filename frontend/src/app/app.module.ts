import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BenchMarkComponent } from './components/bench-mark/bench-mark.component';
import { GraficoComponent } from './components/grafico/grafico.component';
import { MenuCategoriesComponent } from './components/menu-categories/menu-categories.component';
import { TabelaComponent } from './components/tabela/tabela.component';

@NgModule({
  declarations: [
    AppComponent,
    BenchMarkComponent,
    GraficoComponent,
    MenuCategoriesComponent,
    TabelaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
