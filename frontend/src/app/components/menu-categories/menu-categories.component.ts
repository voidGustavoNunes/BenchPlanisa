import { Component } from '@angular/core';

@Component({
  selector: 'app-menu-categories',
  templateUrl: './menu-categories.component.html',
  styleUrls: ['./menu-categories.component.scss']
})
export class MenuCategoriesComponent {
  categories = [
    'Página Inicial',
    'Tabela de BenchMark',
    'Sobre'
  ];

  selectCategory(category: string) {
    console.log('Selected category:', category);
  }
}
