import {Component, OnInit, ViewChild} from '@angular/core';
import {PointsAreaComponent} from './points-area.component/points-area.component';
import {AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

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

  constructor(private http: HttpClient, private formBuilder: FormBuilder) {
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
    class PointResponse {
      constructor(public x: number, public y: number, public r: number) {
      }
    }
    this.postData(new PointResponse(this.x!.value, this.y!.value, this.r!.value));
  }

  postData(payload: any): Observable<any> {
    return this.http.post(`/api`, payload);
  }
}

