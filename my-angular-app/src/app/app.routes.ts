import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'servicos',
    loadComponent: () => import('./pages/servicos/servicos.component').then(m => m.ServicosComponent)
    // If the file does not exist, create 'src/app/pages/servicos/servicos.component.ts' and export ServicosComponent from it.
  },
  {
    path: '**',
    redirectTo: ''
  }
];