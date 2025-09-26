function draw_darth_vader() {
    let r = 150;
    document.getElementById("graph-path")
        .setAttribute("d", `M${r} ${0}V${-r/4}L${3*r/4} ${-2*r/3}L${r/2} ${-3*r/4}`+
            `L${3*r/4} ${-r/6}L${r/3} ${-r/4}A${2*r/5} ${2*r/5} 0 0 0 ${-r/3} ${-r/4}L${-3*r/4} ${-r/6}` +
            `L${-r/2} ${-3*r/4}L${-3*r/4} ${-2*r/3}L${-r} ${-r/4}V${r/4}L${-3*r/4} ${2*r/3}` +
            `L${-r/2} ${3*r/4}L${-3*r/4} ${r/6}L${-r/3} ${r/4} A${2*r/5} ${2*r/5} 0 0 0 ${r/3} ${r/4}` +
            `L${3*r/4} ${r/6}L${r/2} ${3*r/4}L${3*r/4} ${2*r/3}L${r} ${r/4}Z`);

    document.getElementById("graph-x").setAttribute("x1", `${-1.1*r}`);
    document.getElementById("graph-x").setAttribute("x2", `${1.1*r}`);
    document.getElementById("graph-x").setAttribute("y1", `${0}`);
    document.getElementById("graph-x").setAttribute("y2", `${0}`);

    document.getElementById("graph-y").setAttribute("x1", `${0}`);
    document.getElementById("graph-y").setAttribute("x2", `${0}`);
    document.getElementById("graph-y").setAttribute("y1", `${-1.1*r}`);
    document.getElementById("graph-y").setAttribute("y2", `${1.1*r}`);

    document.getElementById("graph-x-label").setAttribute("x", `${1.1*r+20}`);
    document.getElementById("graph-x-label").setAttribute("y", `${0}`);

    document.getElementById("graph-y-label").setAttribute("x", `${0}`);
    document.getElementById("graph-y-label").setAttribute("y", `${-1.1*r-20}`);

    document.getElementById("graph-ry-label").setAttribute("x", `${10}`);
    document.getElementById("graph-ry-label").setAttribute("y", `${-r}`);

    document.getElementById("graph-yr-label").setAttribute("x", `${10}`);
    document.getElementById("graph-yr-label").setAttribute("y", `${r}`);

    document.getElementById("graph-rx-label").setAttribute("x", `${r}`);
    document.getElementById("graph-rx-label").setAttribute("y", `${20}`);

    document.getElementById("graph-xr-label").setAttribute("x", `${-r}`);
    document.getElementById("graph-xr-label").setAttribute("y", `${20}`);

    document.getElementById("graph-x-arrow").setAttribute("points",
        `${r*1.1+10},0 ${r*1.1},-5 ${r*1.1},5`);

    document.getElementById("graph-y-arrow").setAttribute("points",
        `0,${-r*1.1-10} -5,${-r*1.1} 5,${-r*1.1}`);
}

window.onload = draw_darth_vader;

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

    const response = await fetch(document.URL + "controller-servlet" +
        `?x=${xValues.join(",")}&y=${y}&r=${rValues.join(",")}`);

    const result = await response.text();
    process(result);
}

function process(data) {
    document.documentElement.innerHTML = data;
    draw_darth_vader();
}

function printError(error) {
    document.getElementById("problem-label").innerText = error;
}

function clearError() {
    document.getElementById("problem-label").innerText = "";
}