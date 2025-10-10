const logo = document.getElementById('yoda-dvd');

let x = 200;
let y = 0;
let dx = 10;
let dy = 10;


function animate() {
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;
    const logoRect = logo.getBoundingClientRect();
    const logoWidth = logoRect.width;
    const logoHeight = logoRect.height;

    x += dx;
    y += dy;

    if (x + logoWidth >= screenWidth || x <= 0) dx = -dx;
    if (y + logoHeight >= screenHeight || y <= 0) dy = -dy;

    logo.style.left = x + 'px';
    logo.style.top = y + 'px';

    requestAnimationFrame(animate);
}

animate();

window.addEventListener('resize', function() {
    x = Math.min(x, window.innerWidth - logo.offsetWidth);
    y = Math.min(y, window.innerHeight - logo.offsetHeight);
});