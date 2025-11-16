import { Routes } from '@angular/router';
import {StartPageComponent} from '../pages/start-page/start-page.component';
import {MainPageComponent} from '../pages/main-page/main-page.component';

export const routes: Routes = [
  {
    path: '',
    component: StartPageComponent
  },
  {
    path: 'main',
    component: MainPageComponent
  }
];
