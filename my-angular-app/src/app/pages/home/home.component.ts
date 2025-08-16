import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  services = [
    { title: 'Casamentos', description: 'Realize o casamento dos seus sonhos' },
    { title: 'Corporativo', description: 'Eventos empresariais de alto padrão' },
    { title: 'Festas', description: 'Comemorações inesquecíveis' }
  ];
}