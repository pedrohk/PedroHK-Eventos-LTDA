import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-servicos',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section class="servicos-container">
      <h1>Serviços</h1>
      <p>Veja nossos serviços de eventos!</p>
    </section>
  `,
  styles: [`
    .servicos-container {
      padding: 2rem;
      max-width: 900px;
      margin: 0 auto;
    }
  `]
})
export class ServicosComponent {}