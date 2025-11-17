import {BehaviorSubject, map, Observable, Subscription, tap} from 'rxjs';
import {inject, Injectable, OnDestroy} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {urlApi, urlWs} from '../../shared/api-config';
import {WebSocketSubject} from 'rxjs/internal/observable/dom/WebSocketSubject';
import {webSocket} from 'rxjs/webSocket';

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
  private socket$!: WebSocketSubject<any>;
  private wsSubscription!: Subscription;

  constructor() {
    this.connect();
    this.loadShots().subscribe();
  }

  ngOnDestroy(): void {
    if (this.wsSubscription) {
      this.wsSubscription.unsubscribe();
    }
    if (this.socket$) {
      this.socket$.complete();
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
    console.log(payload);

    return this.http.post(`${urlApi}/shots`, payload, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  public clearShots(): Observable<any> {
    return this.http.delete(`${urlApi}/shots`, {
      withCredentials: true
    }).pipe(
      tap(response => {
        this.loadShots().subscribe();
      })
    );
  }

  private connect(): void {
    this.socket$ = webSocket(urlWs);

    this.wsSubscription = this.socket$.subscribe({
      next: (shots: Shot[]) => {
        const currentShots = this.shotsSubject.value;
        this.updateShots([...currentShots, ...shots])
      },
      error: (err) => console.error('WebSocket error:', err),
      complete: () => console.log('WebSocket connection closed')
    });
  }

  private sortShots(shots: Shot[]): Shot[] {
    return [...shots].sort((a, b) => a.id - b.id);
  }
}
