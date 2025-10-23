import {Component, HostListener, OnDestroy, ViewChild} from '@angular/core';
import {FormComponent} from './form.component/form.component';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-start-page',
  standalone: true,
  templateUrl: './start-page.component.html',
  styleUrl: './start-page.component.scss',
  imports: [CommonModule, FormComponent]
})
export class StartPageComponent implements OnDestroy {
  isKeyDown = false;
  progressOffset = 160;

  @ViewChild(FormComponent) formComponent!: FormComponent;

  private progressInterval: any;
  private progress = 0;
  private readonly progressTime = 1000;
  private readonly updateInterval = 20;

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (!this.isKeyDown && !this.formComponent.isFormOpen()) this.startProgress();
  }

  @HostListener('document:keyup', ['$event'])
  handleKeyUp(event: KeyboardEvent): void {
    if (!this.formComponent.isFormOpen()) this.stopProgress();
  }

  private startProgress(): void {
    this.progress = 0;
    this.isKeyDown = true;
    this.updateProgress();

    this.progressInterval = setInterval(() => {
      if (this.progress >= this.progressTime) {
        this.completeProgress();
      }
      else {
        this.progress += this.updateInterval;
        this.updateProgress();
      }
    }, this.updateInterval);
  }

  private updateProgress(): void {
    this.progressOffset = 160 * (1 - this.progress / this.progressTime);
  }

  private completeProgress(): void {
    this.stopProgress();
    this.formComponent.openForm();
  }

  private stopProgress(): void {
    this.isKeyDown = false;
    if (this.progressInterval) {
      clearInterval(this.progressInterval);
      this.progressInterval = null;
    }
    this.progressOffset = 160;
    this.progress = 0;
  }

  ngOnDestroy(): void {
    this.stopProgress();
  }
}
