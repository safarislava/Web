import {BehaviorSubject, catchError, map, Observable, of, tap} from 'rxjs';
import {inject, Injectable, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser} from '@angular/common';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {urlApi} from '../../shared/api-config';

class Bullet {
  public id!: number;
  public x!: number;
  public y!: number;
  public isPointInArea!: boolean;
}

class ShotDetails {
  public type!: string;
  public bullet?: Bullet;
  public bullets?: Bullet[];
}

export class Shot {
  public id!: number;
  public x!: string;
  public y!: string;
  public r!: string;
  public accuracy!: number;
  public deltaTime!: number;
  public time!: string;
  public details!: ShotDetails;
}

@Injectable({
  providedIn: 'root'
})
export class ShotsService {
  private platformId = inject(PLATFORM_ID);
  private shotsSubject = new BehaviorSubject<Shot[]>([]);
  public shots$: Observable<Shot[]> = this.shotsSubject.asObservable().pipe(
    map(shots => this.sortShots(shots))
  );

  constructor(private router: Router, private http: HttpClient) {}

  get currentShots(): Shot[] {
    return this.shotsSubject.value;
  }

  private updateShots(shots: Shot[]): void {
    this.shotsSubject.next(shots);
  }

  public sortShots(shots: Shot[]): Shot[] {
    return shots.sort((a, b) => a.id - b.id);
  }

  public loadShots(): Observable<Shot[]> {
    if (!isPlatformBrowser(this.platformId)) {
      return of([]);
    }

    return this.http.get<Shot[]>(urlApi + "/shots", {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .pipe(
        tap(response => {
          this.updateShots(response);
        }),
        catchError(error => {
          if (error.status === 401) {
            this.router.navigate(['/']);
          }
          return of([]);
        })
      );
  }

  public addShot(x: string, y: string, r: string, weapon: string): void {
    const payload = {
      x: x,
      y: y,
      r: r,
      weapon: weapon
    };

    this.http.post(urlApi + "/shots", payload, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }})
      .subscribe({
        next: (response) => {
          this.loadShots().subscribe();
        },
        error: (error) => {
          if (error.status === 401) {
            this.router.navigate(['/']);
          }
        }
      });
  }

  public clearShots(): void {
    this.http.delete(urlApi + "/shots", {
      withCredentials: true
    })
      .subscribe({
        next: (response) => {
          this.loadShots().subscribe();
        },
        error: (error) => {
          if (error.status === 401) {
            this.router.navigate(['/']);
          }
        }
      });
  }
}
