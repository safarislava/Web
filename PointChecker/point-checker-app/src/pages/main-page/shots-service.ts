import {BehaviorSubject, map, Observable, tap} from 'rxjs';
import {inject, Injectable, OnDestroy, OnInit, PLATFORM_ID} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {urlApi, urlWs} from '../../shared/api-config';
import {Client, IMessage} from '@stomp/stompjs';
import {isPlatformBrowser} from '@angular/common';

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
  private platformId = inject(PLATFORM_ID);
  private stompClient!: Client;

  constructor() {
    if (isPlatformBrowser(this.platformId)) {
      this.connectWebSocket();
    }
  }

  ngOnDestroy(): void {
    this.disconnectWebSocket();
  }

  get currentShots(): Shot[] {
    return this.shotsSubject.value;
  }

  private updateShots(shots: Shot[]): void {
    this.shotsSubject.next(shots);
  }

  public loadInitialShots() {
    this.http.get<Shot[]>(`${urlApi}/shots`, {
      withCredentials: true,
      headers: { 'Content-Type': 'application/json' }
    }).subscribe(response => {
      this.updateShots(response);
    });
  }

  public addShot(x: string, y: string, r: string, weapon: string): Observable<any> {
    const payload = { x, y, r, weapon };

    return this.http.post<Shot>(`${urlApi}/shots`, payload, {
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
      tap(() => {
        this.updateShots([]);
      })
    );
  }

  private sortShots(shots: Shot[]): Shot[] {
    return [...shots].sort((a, b) => a.id - b.id);
  }

  private connectWebSocket(): void {
    this.stompClient = new Client({
      brokerURL: urlWs,
      connectHeaders: {},
      debug: (str) => {
        // console.log(new Date(), str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.stompClient.onConnect = (frame) => {
      this.stompClient.subscribe('/user/queue/shots', (message: IMessage) => {
        const newShots: Shot[] = JSON.parse(message.body);
        const currentShots = this.currentShots;

        const shotMap = new Map<number, Shot>();
        currentShots.forEach(shot => shotMap.set(shot.id, shot));
        newShots.forEach(shot => shotMap.set(shot.id, shot));

        this.updateShots(Array.from(shotMap.values()));
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    this.stompClient.activate();
  }

  private disconnectWebSocket(): void {
    if (this.stompClient?.active) {
      this.stompClient.deactivate();
    }
  }
}
