import {AfterViewInit, Component, HostListener, inject, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser} from '@angular/common';
import {ShotsService} from '../ShotsService';

@Component({
  selector: 'app-points-area',
  imports: [],
  templateUrl: './points-area.component.html',
  styleUrl: './points-area.component.scss',
  standalone: true
})
export class PointsAreaComponent implements AfterViewInit {
  private canvas!: HTMLCanvasElement;
  private ctx!: CanvasRenderingContext2D;
  private platformId = inject(PLATFORM_ID);

  private isImageLoaded: boolean = false;
  private image: HTMLImageElement | undefined;
  private scale: number = 50;

  private shotsService = inject(ShotsService);

  private x: number | undefined;
  private y: number | undefined;
  private r: number | undefined;
  private weapon: string = "REVOLVER";

  set X(x: number) { this.x = x; }
  set Y(y: number) { this.y = y; }
  set R(r: number) { this.r = r; }
  set Weapon(weapon: string) { this.weapon = weapon; }

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.initCanvas();
      this.image = new Image();
      this.image.src = "/assets/images/target.png";
      this.image.onload = () => {
        this.isImageLoaded = true;
        this.updateGraphImage();
      }
      this.shotsService.shots$.subscribe(shots => {
        this.updateGraphImage();
      });
    }
  }

  @HostListener('mousemove', ['$event'])
  handleMouseOver(event: MouseEvent): void {
    if (this.r == undefined) return;
    this.x = ((event.offsetX - this.canvas.width / 2)/ this.scale);
    this.y = ((this.canvas.height / 2 - event.offsetY) / this.scale);
    this.updateGraphImage();
  }

  @HostListener('mousedown', ['$event'])
  handleMouseDown(event: MouseEvent): void {
    if (this.x == undefined || this.y == undefined || this.r == undefined || this.weapon == undefined) return;
    this.shotsService.addShot(this.x, this.y, this.r, this.weapon);
  }

  private initCanvas() : void {
    this.canvas = document.createElement('canvas');
    this.canvas = document.createElement('canvas');
    this.canvas.width = 400;
    this.canvas.height = 800;
    this.canvas.style.border = '1px solid #ccc';

    this.ctx = this.canvas.getContext('2d')!;
    this.ctx.translate(this.canvas.width/2, this.canvas.height/2);
    this.ctx.scale(1, -1);
  }

  private drawGraph(): void {
    this.ctx.clearRect(-this.canvas.width/2, -this.canvas.height/2, this.canvas.width, this.canvas.height);

    this.drawMainFigure();
    this.drawShots();
    this.drawScope();
  }

  public updateGraphImage() {
    this.drawGraph();

    let imgElement = document.querySelector('[id$="graph-image"]');
    if (imgElement == null) return;

    imgElement.setAttribute("src", this.canvas.toDataURL('image/png'));
  }

  private drawMainFigure() {
    if (this.isImageLoaded) {
      const scale = this.canvas.height / this.image!.height * 0.9;
      const width = this.image!.width * scale;
      const height = this.image!.height * scale;

      this.ctx.save();
      this.ctx.scale(1, -1);
      this.ctx.drawImage(this.image!, -width/2, -height/2, width, height);
      this.ctx.restore();
    }
  }

  private drawShots() {
    for (let shot of this.shotsService.currentShots) {
      const details = JSON.parse(shot.details);
      switch (details.type){
        case "Revolver":
          this.drawBullet(details.bullet.x, details.bullet.y, details.bullet.isPointInArea);
          break;
        case "Shotgun":
          for (let bullet of details.bullets) {
            this.drawBullet(bullet.x, bullet.y, bullet.isPointInArea);
          }
          break;
      }
    }
  }

  private drawScope() {
    this.drawScopePart("black", 9);
    this.drawScopePart("#e60003", 3);
  }

  private drawScopePart(color: string, width: number) {
    if (this.x == undefined || this.y == undefined || this.r == undefined || this.weapon == undefined) return;

    this.ctx.save();

    this.ctx.strokeStyle = color;
    this.ctx.lineWidth = width;

    switch (this.weapon) {
      case "REVOLVER": {
        this.ctx.beginPath();
        this.ctx.moveTo(this.x * this.scale - this.r * this.scale, this.y * this.scale);
        this.ctx.lineTo(this.x * this.scale + this.r * this.scale, this.y * this.scale);
        this.ctx.stroke();

        this.ctx.beginPath();
        this.ctx.moveTo(this.x * this.scale, this.y * this.scale - this.r * this.scale);
        this.ctx.lineTo(this.x * this.scale, this.y * this.scale + this.r * this.scale);
        this.ctx.stroke();
        break;
      }
      case "SHOTGUN": {
        this.ctx.beginPath();
        this.ctx.arc(this.x * this.scale, this.y * this.scale, this.r * this.scale, 0, 2 * Math.PI);
        this.ctx.stroke();
        break;
      }
    }

    this.ctx.restore();
  }

  private drawBullet(x: number, y: number, hit: boolean) {
    this.ctx.save();

    this.ctx.fillStyle = hit ? 'green' : 'red';
    this.ctx.beginPath();
    this.ctx.arc(x * this.scale, y * this.scale, 5, 0, Math.PI * 2);
    this.ctx.fill();

    this.ctx.restore();
  }
}
