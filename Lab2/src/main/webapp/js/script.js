document.getElementById('graph').addEventListener('mousedown', async (event) => {
    let rValues = getRValues()
    if (!rValues) {
        printError("R не указан");
        return;
    }
    if (rValues.length > 1) {
        printError("Указано много R");
        return;
    }

    let r =  Number(rValues[0]);
    let x = (event.offsetX - 200) / R * r;
    let y = (200 - event.offsetY) / R * r;
    if (-5 > y || y > 3) {
        printError("Y неправильный");
        return;
    }
    sendData([x], [y], [r]);
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

    sendData(xValues, [y], rValues);
}

function printError(error) {
    document.getElementById("problem-label").innerText = error;
}

function clearError() {
    document.getElementById("problem-label").innerText = "";
}

async function sendData(xValues, yValues, rValues) {
    let token = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    if (token == null) return;

    const query = `?x=${xValues.join(",")}&y=${yValues.join(",")}&r=${rValues.join(",")}`;
    const response = await fetch(document.URL + "/controller-servlet" + query, {
        redirect: 'follow',
        headers: {"x-csrf-token": token},
    }).then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    });
}