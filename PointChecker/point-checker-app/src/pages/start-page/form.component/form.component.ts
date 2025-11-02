import {ChangeDetectorRef, Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-form',
  standalone: true,
  templateUrl: './form.component.html',
  styleUrl: './form.component.scss',
  imports: [CommonModule, ReactiveFormsModule]
})
export class FormComponent {
  public isCompliant = false;
  public loginForm!: FormGroup;
  public errorMessage: boolean = false;

  private isEnterOrRegister: boolean = true;
  private urlApi = "http://localhost:8080/PointChecker-1.0/api/";

  constructor(private router: Router, private http: HttpClient, private formBuilder: FormBuilder, private cdr: ChangeDetectorRef) {
    this.loginForm = this.createForm();
  }

  private createForm(): FormGroup {
    return this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern(/[a-zA-Z0-9]+/)]],
      password: ['', [Validators.required, Validators.pattern(/[a-zA-Z0-9!@#$%&]+/)]],
    });
  }

  public isFormOpen(): boolean {
    return this.isCompliant;
  }

  public openForm(): void {
    this.isCompliant = true;
    this.errorMessage = false;
  }

  public closeForm(): void {
    this.isCompliant = false;
    this.errorMessage = false;
  }

  public onSubmit(): void {
    this.loginForm.markAllAsTouched();

    if (this.loginForm.invalid) {
      return;
    }

    const payload = {
      username: this.username?.value,
      password: this.password?.value,
    };

    if (this.isEnterOrRegister) {
      this.http.post(this.urlApi + "auth-sessions", payload, {
        withCredentials: true,
      })
        .subscribe({
          next: (result) => {
            this.errorMessage = false;
            this.router.navigate(['/main']);
          },
          error: (error) => {
            this.errorMessage = true;
            this.cdr.detectChanges();
          }
        });
    }
    else {
      this.http.post(this.urlApi + "users", payload, {
        withCredentials: true,
      })
        .subscribe({
          next: (result) => {
            this.errorMessage = false;
            this.router.navigate(['/main']);
          },
          error: (error) => {
            this.errorMessage = true;
            this.cdr.detectChanges();
          }
        });
    }
  }

  public onRegistrationClick(): void {
    this.isEnterOrRegister = false;
    this.errorMessage = false;
    this.onSubmit();
  }

  public onEnterClick(): void {
    this.isEnterOrRegister = true;
    this.errorMessage = false;
    this.onSubmit();
  }

  get username(): AbstractControl | null { return this.loginForm.get('username'); }
  get password(): AbstractControl | null { return this.loginForm.get('password'); }
}
