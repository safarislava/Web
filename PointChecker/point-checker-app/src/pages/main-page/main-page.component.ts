import {AfterViewInit, Component, ElementRef, inject, OnInit, PLATFORM_ID, ViewChild} from '@angular/core';
import {PointsAreaComponent} from './points-area.component/points-area.component';
import {AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AsyncPipe, isPlatformBrowser, NgOptimizedImage} from '@angular/common';
import {ShotsService} from './ShotsService';
import VanillaTilt from 'vanilla-tilt';
import {positiveNumberValidator} from '../../shared/positive-validator';


@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    PointsAreaComponent,
    ReactiveFormsModule,
    AsyncPipe,
    NgOptimizedImage,
  ],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent implements OnInit, AfterViewInit {
  @ViewChild(PointsAreaComponent) pointsAreaComponent!: PointsAreaComponent;
  private platformId = inject(PLATFORM_ID);
  public pointForm!: FormGroup;
  public shotsService = inject(ShotsService);

  private weapon: string = "REVOLVER";

  constructor(private formBuilder: FormBuilder, private elementRef: ElementRef) {
    this.pointForm = this.createForm();
  }

  public shootAction(): void {
    if (!isPlatformBrowser(this.platformId)) return;
    if (this.X?.invalid || this.Y?.invalid || this.R?.invalid || this.Weapon == null) return;
    this.shotsService.addShot(this.X?.value, this.Y?.value, this.R?.value, this.Weapon);
  }

  public clearShots(): void {
    this.shotsService.clearShots();
  }

  ngOnInit(): void {
    this.X?.valueChanges.subscribe(x => {
      this.onXChange(x);
    });

    this.Y?.valueChanges.subscribe(y => {
      this.onYChange(y);
    });

    this.R?.valueChanges.subscribe(r => {
      this.onRChange(r);
    });
    this.shotsService.loadShots().subscribe();
  }

  ngAfterViewInit(): void {
    if (!isPlatformBrowser(this.platformId)) return;
    const tiltElement = this.elementRef.nativeElement.querySelector('.background');
    if (tiltElement) {
      VanillaTilt.init(tiltElement, {
        max: 1,
        speed: 300,
        glare: true,
        'max-glare': 0.1,
        'full-page-listening': true,
      });
    }
  }

  private onXChange(x: number): void {
    this.pointsAreaComponent.X = x;
    this.pointsAreaComponent.updateGraphImage();
  }

  private onYChange(y: number): void {
    this.pointsAreaComponent.Y = y;
    this.pointsAreaComponent.updateGraphImage();
  }

  private onRChange(r: number): void {
    this.pointsAreaComponent.R = r;
    this.pointsAreaComponent.updateGraphImage();
  }

  public setRevolver(): void {
    this.weapon = "REVOLVER";
    this.pointsAreaComponent.Weapon = "REVOLVER";
    this.pointsAreaComponent.updateGraphImage();
  }

  public setShotgun(): void {
    this.weapon = "SHOTGUN"
    this.pointsAreaComponent.Weapon = "SHOTGUN";
    this.pointsAreaComponent.updateGraphImage();
  }

  private createForm(): FormGroup {
    return this.formBuilder.group({
      x: ['', [Validators.required, Validators.pattern(/^[+-]?([0-9]*[.])?[0-9]+$/)]],
      y: ['', [Validators.required, Validators.pattern(/^[+-]?([0-9]*[.])?[0-9]+$/)]],
      r: ['', [Validators.required, Validators.pattern(/^[+-]?([0-9]*[.])?[0-9]+$/), positiveNumberValidator()]],
    });
  }

  get X(): AbstractControl | null { return this.pointForm.get('x'); }
  get Y(): AbstractControl | null { return this.pointForm.get('y'); }
  get R(): AbstractControl | null { return this.pointForm.get('r'); }
  get Weapon(): string { return this.weapon; }
}
