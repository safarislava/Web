import { Component, HostListener, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-start-page',
  templateUrl: './start-page.component.html',
  styleUrl: './start-page.component.scss',
})
export class StartPageComponent implements OnDestroy {
  isCompliant = false;
  isKeyDown = false;
  progressOffset = 160;

  private progressInterval: any;
  private progress = 0;
  private readonly progressTime = 1000;
  private readonly updateInterval = 20;

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (!this.isKeyDown && !this.isCompliant) this.startProgress();
  }

  @HostListener('document:keyup', ['$event'])
  handleKeyUp(event: KeyboardEvent): void {
    if (!this.isCompliant) this.stopProgress();
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
    this.openForm();
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

  public openForm(): void {
    this.isCompliant = true;
  }

  public closeForm(): void {
    this.isCompliant = false;
  }

  ngOnDestroy(): void {
    this.stopProgress();
  }
}
