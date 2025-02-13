import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  chartData: any[] = [];

  onSearch(searchParams: any) {
    console.log('Search params:', searchParams);
  }
}
