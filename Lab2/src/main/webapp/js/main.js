var controllerServletUrl = "http://localhost:8080/Lab2_war_exploded/controller-servlet";

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

async function sendData() {
    let xValues = getXValues().map(x => Number(x));
    let y = document.getElementById("y-data").value;
    let rValues = getRValues().map(x => Number(x));

    let error = validate(xValues, y, rValues)
    if (error) {
        printError(error);
        return;
    }

    const response = await fetch(controllerServletUrl +
        `?x=${xValues.join(",")}&y=${y}&r=${rValues.join(",")}`);

    const result = await response.text();
    process(result);
}

function process(data) {
    document.documentElement.innerHTML = data;
}

function printError(error) {
    document.getElementById("problem-label").innerText = error;
}

function clearError() {
    document.getElementById("problem-label").innerText = "";
}