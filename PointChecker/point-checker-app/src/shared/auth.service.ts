import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { urlApi } from './api-config';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);

  refreshToken(): Observable<any> {
    return this.http.get(`${urlApi}/users/refresh`, {
      withCredentials: true,
    });
  }

  logout(): Observable<any> {
    return this.http
      .delete(`${urlApi}/users/logout`, {
        withCredentials: true,
      })
      .pipe(
        tap(() => {
          console.log('User logged out');
        })
      );
  }
}
