import {AfterViewInit, Component, HostListener, Inject, PLATFORM_ID} from '@angular/core';
import {isPlatformBrowser} from '@angular/common';
import {AbstractControl} from '@angular/forms';

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
  private readonly isBrowser: boolean;

  private isImageLoaded: boolean = false;
  private image: HTMLImageElement | undefined;

  private x: number | undefined;
  private y: number | undefined;
  private r: number | undefined;
  private scale: number = 50;

  set X(x: number) { this.x = x; }
  set Y(y: number) { this.y = y; }
  set R(r: number) { this.r = r; }

  constructor(@Inject(PLATFORM_ID) platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  ngAfterViewInit(): void {
    if (this.isBrowser) {
      this.initCanvas();
      this.image = new Image();
      this.image.src = "/assets/images/target.png";
      this.image.onload = () => {
        this.isImageLoaded = true;
        this.updateGraphImage();
      }
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
    if (this.x == undefined || this.y == undefined || this.r == undefined) return;

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
    this.drawPoints();
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

  private drawPoints() {
    // let rScale = document.querySelector('[id$="hidden-points:form-r-image"]').value;
    // if (rScale == null || rScale.trim() === "") return;
    //
    // let pointsJson = document.querySelector('[id$="hidden-points:points"]').value;
    // let points = JSON.parse(pointsJson);
    //
    // for (let point of points) {
    //   switch (point.shape) {
    //     case 'circle':
    //       addPoint(point.x, point.y, point.r, point.isPointInArea, rScale);
    //       break;
    //     case 'square':
    //       addSquarePoint(point.x, point.y, point.r, point.isPointInArea, rScale);
    //       break;
    //     case 'triangle':
    //       addTrianglePoint(point.x, point.y, point.r, point.isPointInArea, rScale);
    //       break;
    //   }
    // }
  }

  private drawScope() {
    this.drawScopePart("black", 9);
    this.drawScopePart("#e60003", 3);
  }

  private drawScopePart(color: string, width: number) {
    if (this.x == undefined || this.y == undefined || this.r == undefined) return;

    this.ctx.save();

    this.ctx.strokeStyle = color;
    this.ctx.lineWidth = width;

    this.ctx.beginPath();
    this.ctx.arc(this.x * this.scale, this.y * this.scale, this.r * this.scale, 0, 2 * Math.PI);
    this.ctx.stroke();

    this.ctx.beginPath();
    this.ctx.moveTo(this.x * this.scale - this.r * this.scale, this.y * this.scale);
    this.ctx.lineTo(this.x * this.scale + this.r * this.scale, this.y * this.scale);
    this.ctx.stroke();

    this.ctx.beginPath();
    this.ctx.moveTo(this.x * this.scale, this.y * this.scale - this.r * this.scale);
    this.ctx.lineTo(this.x * this.scale, this.y * this.scale + this.r * this.scale);
    this.ctx.stroke();

    this.ctx.restore();
  }

  private drawPoint(x: number, y: number, r: number, hit: boolean, rScale: number) {
    this.ctx.save();

    this.ctx.fillStyle = hit ? 'green' : 'red';
    this.ctx.beginPath();
    this.ctx.arc(x * this.scale, y * this.scale, 3, 0, Math.PI * 2);
    this.ctx.fill();

    this.ctx.restore();
  }
}
