import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  HostListener,
  inject,
  OnDestroy,
  PLATFORM_ID,
  ViewChild
} from '@angular/core';
import {FormComponent} from './form.component/form.component';
import {CommonModule, isPlatformBrowser, NgOptimizedImage} from '@angular/common';
import {timeout} from 'rxjs';

@Component({
  selector: 'app-start-page',
  standalone: true,
  templateUrl: './start-page.component.html',
  styleUrl: './start-page.component.scss',
  imports: [CommonModule, FormComponent, NgOptimizedImage]
})
export class StartPageComponent implements OnDestroy, AfterViewInit {
  @ViewChild(FormComponent) formComponent!: FormComponent;
  public progressOffset = 160;
  public isVideoLoaded = false;

  private platformId = inject(PLATFORM_ID);
  private isKeyDown = false;
  private progressInterval: any;
  private progress = 0;
  private readonly progressTime = 1000;
  private readonly updateInterval = 20;

  @HostListener('touchstart', ['$event'])
  handleTouchDownEvent(event: TouchEvent): void {
    if (!this.isKeyDown && !this.formComponent.isFormOpen()) this.startProgress();
  }

  @HostListener('document:mousedown', ['$event'])
  handleMouseDownEvent(event: MouseEvent): void {
    if (!this.isKeyDown && !this.formComponent.isFormOpen()) this.startProgress();
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyDownEvent(event: KeyboardEvent): void {
    if (!this.isKeyDown && !this.formComponent.isFormOpen()) this.startProgress();
  }

  @HostListener('touchend', ['$event'])
  onTouchEnd(event: TouchEvent) {
    if (!this.formComponent.isFormOpen()) this.stopProgress();
  }

  @HostListener('document:mouseup', ['$event'])
  handleMouseUpEvent(event: MouseEvent): void {
    if (!this.formComponent.isFormOpen()) this.stopProgress();
  }

  @HostListener('document:keyup', ['$event'])
  handleKeyUpEvent(event: KeyboardEvent): void {
    if (!this.formComponent.isFormOpen()) this.stopProgress();
  }

  constructor(private cdRef: ChangeDetectorRef) {}

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.loadVideo();
    }
  }

  private loadVideo() {
    const video = document.getElementById('background-video') as HTMLVideoElement;
    const preview = document.getElementById('video-preview') as HTMLImageElement;

    if (video && preview) {
      video.load();

      video.addEventListener('loadeddata', () => {
        this.isVideoLoaded = true;
        this.cdRef.detectChanges();
        video.play();
      });
    }
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
        this.cdRef.markForCheck();
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
    this.cdRef.detectChanges();
  }

  ngOnDestroy(): void {
    this.stopProgress();
  }
}
