import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-form',
  standalone: true,
  templateUrl: './form.component.html',
  styleUrl: './form.component.scss',
  imports: [CommonModule]
})
export class FormComponent {
  isCompliant = false;

  public isFormOpen(): boolean {
    return this.isCompliant;
  }

  public openForm(): void {
    this.isCompliant = true;
  }

  public closeForm(): void {
    this.isCompliant = false;
  }
}
