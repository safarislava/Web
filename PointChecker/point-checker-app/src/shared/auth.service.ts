import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { urlApi } from './api-config'; // Убедитесь, что путь корректен

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);

  refreshToken(): Observable<any> {
    return this.http.get(`${urlApi}/auth-sessions`, {
      withCredentials: true,
    });
  }

  logout(): Observable<any> {
    return this.http
      .delete(`${urlApi}/auth-sessions`, {
        withCredentials: true,
      })
      .pipe(
        tap(() => {
          // Логика очистки состояния на клиенте, если необходимо
          console.log('User logged out');
        })
      );
  }
}
