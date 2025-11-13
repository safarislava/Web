import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandlerFn,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import {
  BehaviorSubject,
  catchError,
  EMPTY,
  filter,
  Observable,
  switchMap,
  take,
  throwError,
} from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

let isRefreshing = false;
const refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);

  if (!isPlatformBrowser(platformId) && req.url.includes('/api/')) {
    return EMPTY;
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (!isPlatformBrowser(platformId)) {
        return throwError(() => error);
      }

      if (error.status === 401) {
        if (req.url.includes('/users/login') || req.url.includes('/users/register')) {
          return throwError(() => error);
        }
        return handle401Error(req, next, authService, router);
      }
      return throwError(() => error);
    })
  );
};

const handle401Error = (
  request: HttpRequest<any>,
  next: HttpHandlerFn,
  authService: AuthService,
  router: Router
): Observable<HttpEvent<any>> => {
  if (!isRefreshing) {
    isRefreshing = true;
    refreshTokenSubject.next(null);

    return authService.refreshToken().pipe(
      switchMap((token: any) => {
        isRefreshing = false;
        refreshTokenSubject.next(token);
        return next(request);
      }),
      catchError((error) => {
        isRefreshing = false;
        refreshTokenSubject.next(null);

        router.navigate(['/']);
        return throwError(() => error);
      })
    );
  }

  return refreshTokenSubject.pipe(
    filter((token) => token !== null),
    take(1),
    switchMap(() => next(request)),
    catchError((error) => {
      router.navigate(['/']);
      return throwError(() => error);
    })
  );
};
