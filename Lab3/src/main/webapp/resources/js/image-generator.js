const canvas = document.createElement('canvas');
canvas.width = 400;
canvas.height = 400;
canvas.style.border = '1px solid #ccc';

const ctx = canvas.getContext('2d');
ctx.translate(200, 200);
ctx.scale(1, -1);

function drawGraph() {
    ctx.clearRect(-200, -200, 400, 400);

    drawMainFigure();
    drawCoordinateAxes();
    drawArrows();
    drawPoints();
    drawLabels();
}

function updateGraphImage() {
    drawGraph();

    const imgElement = document.querySelector('[id$="graph-image"]');
    if (imgElement == null) return;

    imgElement.src = canvas.toDataURL('image/png');
    imgElement.style.display = 'block';
    imgElement.alt = '';
}

function drawMainFigure(R = 150) {
    ctx.save();
    ctx.strokeStyle = '#144b6c';
    ctx.fillStyle = 'rgba(52, 152, 219, 0.5)';
    ctx.lineWidth = 2;

    ctx.beginPath();

    ctx.moveTo(R, 0);
    ctx.lineTo(R, -R/4);
    ctx.lineTo(3*R/4, -2*R/3);
    ctx.lineTo(R/2, -3*R/4);
    ctx.lineTo(3*R/4, -R/6);
    ctx.lineTo(R/3, -R/4);

    const startAngle = Math.atan2(-R/4, R/3);
    const endAngle = Math.atan2(-R/4, -R/3);
    ctx.arc(0, 0, 2*R/5, startAngle, endAngle, false);

    ctx.lineTo(-3*R/4, -R/6);
    ctx.lineTo(-R/2, -3*R/4);
    ctx.lineTo(-3*R/4, -2*R/3);
    ctx.lineTo(-R, -R/4);
    ctx.lineTo(-R, R/4);
    ctx.lineTo(-3*R/4, 2*R/3);
    ctx.lineTo(-R/2, 3*R/4);
    ctx.lineTo(-3*R/4, R/6);
    ctx.lineTo(-R/3, R/4);

    const startAngle2 = Math.atan2(R/4, -R/3);
    const endAngle2 = Math.atan2(R/4, R/3);
    ctx.arc(0, 0, 2*R/5, startAngle2, endAngle2, false);

    ctx.lineTo(3*R/4, R/6);
    ctx.lineTo(R/2, 3*R/4);
    ctx.lineTo(3*R/4, 2*R/3);
    ctx.lineTo(R, R/4);

    ctx.closePath();
    ctx.fill();
    ctx.stroke();
    ctx.restore();
}

function drawPoints() {
    let rScale = document.querySelector('[id$="hidden-points:form-r-image"]').value;
    if (rScale == null || rScale.trim() === "") return;

    let pointsJson = document.querySelector('[id$="hidden-points:points"]').value;
    let points = JSON.parse(pointsJson);

    for (let point of points) {
        switch (point.shape) {
            case 'circle':
                addPoint(point.x, point.y, point.r, point.isPointInArea, rScale);
                break;
            case 'square':
                addSquarePoint(point.x, point.y, point.r, point.isPointInArea, rScale);
                break;
            case 'triangle':
                addTrianglePoint(point.x, point.y, point.r, point.isPointInArea, rScale);
                break;
        }
    }
}

function drawCoordinateAxes() {
    ctx.save();
    ctx.strokeStyle = 'white';
    ctx.lineWidth = 1.5;

    ctx.beginPath();
    ctx.moveTo(-165, 0);
    ctx.lineTo(165, 0);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(0, -165);
    ctx.lineTo(0, 165);
    ctx.stroke();

    ctx.restore();
}

function drawArrows() {
    ctx.save();
    ctx.fillStyle = 'white';

    ctx.beginPath();
    ctx.moveTo(175, 0);
    ctx.lineTo(165, -5);
    ctx.lineTo(165, 5);
    ctx.closePath();
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(0, 175);
    ctx.lineTo(-5, 165);
    ctx.lineTo(5, 165);
    ctx.closePath();
    ctx.fill();

    ctx.restore();
}

function drawLabels() {
    ctx.save();
    ctx.scale(1, -1);
    ctx.fillStyle = 'white';
    ctx.font = '12px Arial';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';

    ctx.fillText('X', 185, 0);
    ctx.fillText('Y', 0, -185);

    ctx.fillText('-R', -150, -20);
    ctx.fillText('R', 150, -20);
    ctx.fillText('-R', 20, 150);
    ctx.fillText('R', 20, -150);

    ctx.restore();
}

function addPoint(x, y, r, hit, rScale) {
    ctx.save();

    if (r === rScale) {
        let scaledX = (x / r) * 150;
        let scaledY = (y / r) * 150;

        ctx.fillStyle = hit ? 'green' : 'red';
        ctx.beginPath();
        ctx.arc(scaledX, scaledY, 3, 0, Math.PI * 2);
        ctx.fill();

        ctx.restore();
    }
}

function addSquarePoint(x, y, r, hit, rScale) {
    ctx.save();

    if (r === rScale) {
        let scaledX = (x / r) * 150;
        let scaledY = (y / r) * 150;

        ctx.fillStyle = hit ? 'green' : 'red';
        ctx.beginPath();
        ctx.rect(scaledX-3, scaledY-3, 6, 6);
        ctx.fill();

        ctx.restore();
    }
}

function addTrianglePoint(x, y, r, hit, rScale) {
    ctx.save();

    if (r === rScale) {
        let scaledX = (x / r) * 150;
        let scaledY = (y / r) * 150;

        ctx.fillStyle = hit ? 'green' : 'red';
        ctx.beginPath();
        ctx.moveTo(scaledX, scaledY + 4);
        ctx.lineTo(scaledX + 4, scaledY - 2);
        ctx.lineTo(scaledX - 4, scaledY - 2);
        ctx.fill();

        ctx.restore();
    }
}

updateGraphImage();