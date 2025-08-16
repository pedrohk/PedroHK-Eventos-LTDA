import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  services = [
    { title: 'Casamentos', description: 'Realize o casamento dos seus sonhos' },
    { title: 'Corporativo', description: 'Eventos empresariais de alto padrão' },
    { title: 'Festas', description: 'Comemorações inesquecíveis' }
  ];

  scrollToServices() {
    document.getElementById('services')?.scrollIntoView({ behavior: 'smooth' });
  }
}