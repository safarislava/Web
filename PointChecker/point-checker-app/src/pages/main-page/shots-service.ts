import {BehaviorSubject, map, Observable, tap} from 'rxjs';
import {inject, Injectable} from '@angular/core';
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
export class ShotsService {
  private shotsSubject = new BehaviorSubject<Shot[]>([]);
  public shots$: Observable<Shot[]> = this.shotsSubject.asObservable().pipe(
    map(shots => this.sortShots(shots))
  );

  private http = inject(HttpClient);

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
    return this.http.get<Shot[]>(`${urlApi}/shots`, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }
    }).pipe(
      tap(response => {
        this.updateShots(response);
      })
    );
  }

  public addShot(x: string, y: string, r: string, weapon: string): Observable<any> {
    const payload = { x, y, r, weapon };

    return this.http.post(`${urlApi}/shots`, payload, {
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }
    }).pipe(
      tap(() => {
        this.loadShots().subscribe();
      })
    );
  }

  public clearShots(): Observable<any> {
    return this.http.delete(`${urlApi}/shots`).pipe(
      tap(() => {
        this.loadShots().subscribe();
      })
    );
  }
}
