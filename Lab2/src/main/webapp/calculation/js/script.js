async function sendData() {
    // TODO validation of document.location.search
    // TODO home button

    let params = new URLSearchParams(document.location.search);

    let xValues = params.get('x');
    let yValues = params.get('y');
    let rValues = params.get('r');

    const response = await fetch("http://localhost:8080/Lab2_war_exploded/controller-servlet" +
        document.location.search);
    const result = await response.text();
    process(result);
}

document.addEventListener("DOMContentLoaded", sendData);

function process(data) {
    document.documentElement.innerHTML = data;
}