import { Component, OnInit } from '@angular/core';
import { LocalidadeService } from './service/LocalidadeService';
import { AlertaService } from './components/base/alertaService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent{
  title = 'benchPlanisa';

  constructor(
  ) {}


}
