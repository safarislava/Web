import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandlerFn, HttpInterceptorFn,
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

  return next(req).pipe(
    catchError((error) => {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        if (isPlatformBrowser(platformId)) {
          if (req.url.includes('/api/users/login')) {
            return throwError(() => error);
          }
          return handle401Error(req, next, authService, router);
        }
        else {
          return EMPTY;
        }
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
      switchMap(() => {
        isRefreshing = false;
        refreshTokenSubject.next(true);
        return next(request);
      }),
      catchError((err) => {
        isRefreshing = false;
        router.navigate(['/']);
        return throwError(() => err);
      })
    );
  }

  return refreshTokenSubject.pipe(
    filter((result) => result !== null),
    take(1),
    switchMap(() => next(request))
  );
};
