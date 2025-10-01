document.getElementById('graph').addEventListener('mousedown', async (event) => {
    let x = (event.offsetX - 200) / R;
    let y = (200 - event.offsetY) / R;
    let rValues = getRValues().map(x => Number(x));

    if (!rValues) {
        printError("R не указан");
        return;
    }

    for (let r of rValues) {
        if (-5 > y * r || y * r > 3) {
            printError("Y неправильный");
            return;
        }
    }

    let xValues = [], yValues = [];
    for (let r of rValues) {
        xValues.push(x * r);
        yValues.push(y * r);
    }

    window.location.replace(
        `http://localhost:8080/Lab2_war_exploded/calculation/` +
        `?x=${xValues.join(",")}&y=${yValues.join(",")}&r=${rValues.join(",")}`);

});

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

async function redirectCalculationPage() {
    let xValues = getXValues().map(x => Number(x));
    let y = document.getElementById("y-data").value;
    let rValues = getRValues().map(x => Number(x));

    let error = validate(xValues, y, rValues)
    if (error) {
        printError(error);
        return;
    }

    window.location.replace(
        `http://localhost:8080/Lab2_war_exploded/calculation/?x=${xValues.join(",")}&y=${y}&r=${rValues.join(",")}`);
}

function printError(error) {
    document.getElementById("problem-label").innerText = error;
}

function clearError() {
    document.getElementById("problem-label").innerText = "";
}