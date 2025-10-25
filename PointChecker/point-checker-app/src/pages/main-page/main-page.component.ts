import {Component, OnInit, ViewChild} from '@angular/core';
import {PointsAreaComponent} from './points-area.component/points-area.component';
import {AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    PointsAreaComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent implements OnInit {
  @ViewChild(PointsAreaComponent) pointsAreaComponent!: PointsAreaComponent;
  pointForm!: FormGroup;
  private urlApi = "http://localhost:8080/PointChecker-1.0/api";

  constructor(private router: Router, private http: HttpClient, private formBuilder: FormBuilder) {
    this.pointForm = this.createForm();
  }

  ngOnInit(): void {
    this.x?.valueChanges.subscribe(x => {
      this.onXChange(x);
    });

    this.y?.valueChanges.subscribe(y => {
      this.onYChange(y);
    });

    this.r?.valueChanges.subscribe(r => {
      this.onRChange(r);
    });

    this.http.get(this.urlApi + "/point-area/get-points", {
      withCredentials: true,
    })
      .subscribe({
        next: (response)=> {
          console.log(response);
        },
        error: (error) => {
          this.router.navigate(['/']);
        }
      });
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

  private createForm(): FormGroup {
    return this.formBuilder.group({
      x: ['', [Validators.required, Validators.pattern(/.*/)]],
      y: ['', [Validators.required, Validators.pattern(/.*/)]],
      r: ['', [Validators.required, Validators.pattern(/.*/)]],
    });
  }

  get x(): AbstractControl | null { return this.pointForm.get('x'); }
  get y(): AbstractControl | null { return this.pointForm.get('y'); }
  get r(): AbstractControl | null { return this.pointForm.get('r'); }

  public shootAction(): void {

  }
}

