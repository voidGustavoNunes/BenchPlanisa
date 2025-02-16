import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu-categories',
  templateUrl: './menu-categories.component.html',
  styleUrls: ['./menu-categories.component.scss']
})
export class MenuCategoriesComponent {

  constructor(private router: Router) { }

  menuItems = [
    { name: 'Página Inicial', route: '/home' },
    { name: 'BenchMarks Cadastradas', route: '/tabela' },
    { name: 'Sobre o projeto', route: '/sobre' }
  ];

  selectCategory(category: string) {
    this.router.navigate([category]);
  }
}
