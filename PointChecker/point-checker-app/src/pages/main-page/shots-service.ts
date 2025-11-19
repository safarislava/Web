import {BehaviorSubject, map, Observable, Subscription, take, tap} from 'rxjs';
import {inject, Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {urlApi} from '../../shared/api-config';

class Bullet {
  public id!: number;
  public x!: number;
  public y!: number;
  public hit!: boolean;
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
export class ShotsService implements OnDestroy {
  private shotsSubject = new BehaviorSubject<Shot[]>([]);
  public shots$: Observable<Shot[]> = this.shotsSubject.asObservable().pipe(
    map(shots => this.sortShots(shots))
  );

  private http = inject(HttpClient);
  private pollSubscription!: Subscription;

  constructor() {
    this.loadShots();
    this.startPolling();
  }

  ngOnDestroy(): void {
    if (this.pollSubscription) {
      this.pollSubscription.unsubscribe();
    }
  }

  get currentShots(): Shot[] {
    return this.shotsSubject.value;
  }

  private updateShots(shots: Shot[]): void {
    this.shotsSubject.next(shots);
  }

  public loadShots() {
    return this.http.get<Shot[]>(`${urlApi}/shots`, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }}).pipe(
        tap(response => {
          this.updateShots(response);
        })
    );
  }

  public addShot(x: string, y: string, r: string, weapon: string): Observable<any> {
    const payload = { x, y, r, weapon };

    return this.http.post<Shot>(`${urlApi}/shots`, payload, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }
    }).pipe(
      tap(response => {
        this.updateShots([...this.shotsSubject.value, response]);
      })
    );
  }

  public clearShots(): Observable<any> {
    return this.http.delete(`${urlApi}/shots`, {
      withCredentials: true
    }).pipe(
      tap(() => {
        this.loadShots().subscribe();
      })
    );
  }

  private startPolling(): void {
    if (this.pollSubscription) {
      this.pollSubscription.unsubscribe();
    }

    this.pollSubscription = this.http.get<Shot[]>(`${urlApi}/shots/poll`, { withCredentials: true })
      .pipe(take(1))
      .subscribe({
        next: (updatedShots) => {
          if (updatedShots) {
            this.updateShots(updatedShots);
          }
          this.startPolling();
        },
        error: (err) => {
          console.error('Long polling request failed:', err);
          setTimeout(() => this.startPolling(), 5000);
        }
      });
  }

  private sortShots(shots: Shot[]): Shot[] {
    return [...shots].sort((a, b) => a.id - b.id);
  }
}
