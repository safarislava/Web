const R = 150;

function init_darth_vader() {
    document.getElementById("graph-path")
        .setAttribute("d", `M${R} ${0}V${-R/4}L${3*R/4} ${-2*R/3}L${R/2} ${-3*R/4}`+
            `L${3*R/4} ${-R/6}L${R/3} ${-R/4}A${2*R/5} ${2*R/5} 0 0 0 ${-R/3} ${-R/4}L${-3*R/4} ${-R/6}` +
            `L${-R/2} ${-3*R/4}L${-3*R/4} ${-2*R/3}L${-R} ${-R/4}V${R/4}L${-3*R/4} ${2*R/3}` +
            `L${-R/2} ${3*R/4}L${-3*R/4} ${R/6}L${-R/3} ${R/4} A${2*R/5} ${2*R/5} 0 0 0 ${R/3} ${R/4}` +
            `L${3*R/4} ${R/6}L${R/2} ${3*R/4}L${3*R/4} ${2*R/3}L${R} ${R/4}Z`);

    document.getElementById("graph-x").setAttribute("x1", `${-1.1*R}`);
    document.getElementById("graph-x").setAttribute("x2", `${1.1*R}`);
    document.getElementById("graph-x").setAttribute("y1", `${0}`);
    document.getElementById("graph-x").setAttribute("y2", `${0}`);

    document.getElementById("graph-y").setAttribute("x1", `${0}`);
    document.getElementById("graph-y").setAttribute("x2", `${0}`);
    document.getElementById("graph-y").setAttribute("y1", `${-1.1*R}`);
    document.getElementById("graph-y").setAttribute("y2", `${1.1*R}`);

    document.getElementById("graph-x-label").setAttribute("x", `${1.1*R+20}`);
    document.getElementById("graph-x-label").setAttribute("y", `${0}`);

    document.getElementById("graph-y-label").setAttribute("x", `${0}`);
    document.getElementById("graph-y-label").setAttribute("y", `${-1.1*R-20}`);

    document.getElementById("graph-ry-label").setAttribute("x", `${10}`);
    document.getElementById("graph-ry-label").setAttribute("y", `${-R}`);

    document.getElementById("graph-yr-label").setAttribute("x", `${10}`);
    document.getElementById("graph-yr-label").setAttribute("y", `${R}`);

    document.getElementById("graph-rx-label").setAttribute("x", `${R}`);
    document.getElementById("graph-rx-label").setAttribute("y", `${20}`);

    document.getElementById("graph-xr-label").setAttribute("x", `${-R}`);
    document.getElementById("graph-xr-label").setAttribute("y", `${20}`);

    document.getElementById("graph-x-arrow").setAttribute("points",
        `${R*1.1+10},0 ${R*1.1},-5 ${R*1.1},5`);

    document.getElementById("graph-y-arrow").setAttribute("points",
        `0,${-R*1.1-10} -5,${-R*1.1} 5,${-R*1.1}`);
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
