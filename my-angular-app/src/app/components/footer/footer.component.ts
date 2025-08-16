import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  currentYear: number = new Date().getFullYear();
  
  socialLinks = [
    { name: 'Instagram', url: 'https://instagram.com/pedrohkeventos', icon: 'fa-instagram' },
    { name: 'Facebook', url: 'https://facebook.com/pedrohkeventos', icon: 'fa-facebook' },
    { name: 'LinkedIn', url: 'https://linkedin.com/company/pedrohkeventos', icon: 'fa-linkedin' }
  ];

  contactInfo = {
    phone: '(11) 9999-9999',
    email: 'contato@pedrohkeventos.com.br',
    address: 'São Paulo - SP'
  };

  quickLinks = [
    { text: 'Home', path: '/' },
    { text: 'Serviços', path: '/servicos' },
    { text: 'Sobre', path: '/sobre' },
    { text: 'Contato', path: '/contato' }
  ];
}