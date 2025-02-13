import { Component } from '@angular/core';

@Component({
  selector: 'app-menu-categories',
  templateUrl: './menu-categories.component.html',
  styleUrls: ['./menu-categories.component.scss']
})
export class MenuCategoriesComponent {
  menuItems = [
    { name: 'Página Inicial', route: '/home' },
    { name: 'Tabela de BenchMark', route: '/tabela' },
    { name: 'Sobre', route: '/sobre' }
  ];

  selectCategory(category: string) {
    console.log('Selected category:', category);
  }
}
