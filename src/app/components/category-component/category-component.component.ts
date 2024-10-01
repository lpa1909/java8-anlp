import { Component } from '@angular/core';

@Component({
  selector: 'app-category-component',
  templateUrl: './category-component.component.html',
  styleUrl: './category-component.component.css'
})
export class CategoryComponentComponent {
  isCollapsed = false;

  toggleCollapsed(): void {
    this.isCollapsed = !this.isCollapsed;
  }
}
