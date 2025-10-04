let urlFastCGI = "http://localhost:24570/api"

function getXValues() {
    let xBoxes = document.getElementsByName("x").values();
    let xValues = [];
    for (let box of xBoxes) {
        if (box.checked) {
            xValues.push(box.value);
        }
    }
    if (xValues.length === 0) return null;
    return xValues;
}

function getRValues() {
    let rBoxes = document.getElementsByName("r").values();
    let rValues = [];
    for (let box of rBoxes) {
        if (box.checked) {
            rValues.push(box.value);
        }
    }
    if (rValues.length === 0) return null;
    return rValues;
}

function validate(xValues, y, rValues) {
    if (!xValues) return "X не указан";
    if (!y) return "Y не указан";
    if (!rValues) return "R не указан"

    for (let x of xValues) {
        if (![-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2].includes(Number(x))) return "X принимает недопустимые значения";
    }

    for (let r of rValues) {
        if (![1, 1.5, 2, 2.5, 3].includes(Number(r))) return "R принимает недопустимые значения";
    }

    if (y < -5 || y > 3) {
        return "Y принимает недопустимые значения";
    }

    return null;
}

function sendData() {
    let xValues = getXValues();
    let y = document.getElementById("y-data").value;
    let rValues = getRValues();

    let error = validate(xValues, y, rValues)
    if (error) {
        printError(error);
        return;
    }

    for (let x of xValues) {
        for (let r of rValues) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", urlFastCGI, true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.onload = function() {
                console.log(xhr.responseText);
                process(JSON.parse(xhr.responseText));
            };
            xhr.send(JSON.stringify({"x": x, "y" : y, "r" : r}));
        }
    }
}

function process(data) {
    switch (data.title){
        case "Point response":
            addPoints(data);
            clearError();
            break;
        case "Wrong request":
            printError(data.detail);
            break;
    }
}

function addPoints(data) {
    let index = document.getElementById("results-tbody").children.length;
    document.getElementById("results-tbody").innerHTML +=
        `<tr>\n` +
        `    <td>${index}</td>\n` +
        `    <td>${data.x}</td>\n` +
        `    <td>${data.y}</td>\n` +
        `    <td>${data.r}</td>\n` +
        `    <td>${data.isPointInArea}</td>\n` +
        `    <td>${data.deltaTime}</td>\n` +
        `    <td>${data.time}</td>\n` +
        `</tr>`;


    let cx = 150 + data.x / data.r * 100;
    let cy = 150 - data.y / data.r * 100;
    document.getElementById("graph").innerHTML +=
        `<circle cx="${cx}" cy="${cy}" r="3" fill="black" />\n`;
}

function printError(error) {
    document.getElementById("problem-label").innerText = error;
}

function clearError() {
    document.getElementById("problem-label").innerText = "";
}

function sendWrongJson() {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", urlFastCGI, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function() {
        process(JSON.parse(xhr.responseText));
    };
    xhr.send('{"x":"0","y":"6","r":"3"}');
}