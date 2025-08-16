import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'eventos',
    loadComponent: () => import('./pages/eventos/eventos.component').then(m => m.EventosComponent)
  },
  {
    path: '**',
    redirectTo: ''
  }
];