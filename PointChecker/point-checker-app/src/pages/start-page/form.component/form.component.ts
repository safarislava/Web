import { Component } from '@angular/core';
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

  private isEnterOrRegister: boolean = true;
  private urlApi = "http://localhost:8080/PointChecker-1.0/api";

  constructor(private router: Router, private http: HttpClient, private formBuilder: FormBuilder) {
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
  }

  public closeForm(): void {
    this.isCompliant = false;
  }

  public onSubmit(): void {
    const payload = {
      username: this.username?.value,
      password: this.password?.value,
    };

    if (this.isEnterOrRegister) {
      this.http.post(this.urlApi + "/user/login", payload, {
        withCredentials: true,
      })
        .subscribe({
          next: (result) => {
            this.router.navigate(['/main']);
          },
          error: (error) => {
            console.log('Login failed:', error.status);
          }
        });
    }
    else {
      this.http.post(this.urlApi + "/user/register", payload, {
        withCredentials: true,
      })
        .subscribe({
          next: (result) => {
            this.router.navigate(['/main']);
          },
          error: (error) => {
            console.log('Register failed:', error.status);
          }
        });
    }
  }

  public onRegistrationClick(): void {
    this.isEnterOrRegister = false;
  }

  public onEnterClick(): void {
    this.isEnterOrRegister = true;
  }

  get username(): AbstractControl | null { return this.loginForm.get('username'); }
  get password(): AbstractControl | null { return this.loginForm.get('password'); }
}
