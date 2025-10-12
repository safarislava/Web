function setHiddenValues(event) {
    let img = event.currentTarget;
    let rect = img.getBoundingClientRect();

    let r = document.querySelector('[id$="hidden-r"]').value;
    if (r == null) {
        printError("R не выбран");
        return;
    }
    printError("");

    let x = ((event.clientX - rect.left) - 200) * r / 150;
    let y = (200 - (event.clientY - rect.top)) * r / 150;

    document.querySelector('[id$="hidden-x"]').value = x;
    document.querySelector('[id$="hidden-y"]').value = y;
}

function printError(error) {
    document.getElementById("problem-label").innerText = error;
}

function updateGraphImage() {}

function handleGraphAjaxEvent(data) {
    if (data.status === "begin") {
        let r = document.querySelector('[id$="hidden-r"]').value;
        console.log(r);

        if (r == null || r.trim() === "") {
            printError('R не введён');
            return;
        }
        else {
            printError("")
        }
    }
}

function handleMainAjaxEvent(data) {
    if (data.status === "begin") {
        let x = document.querySelector('[id$="hiddenMainForm:form-x"]').value;
        if (x == null || x.trim() === "") {
            printError('X невалиден');
            return;
        }

        let y = document.querySelector('[id$="hiddenMainForm:form-y"]').value;
        if (y == null || y.trim() === "") {
            printError('Y невалиден');
            return;
        }

        let r = document.querySelector('[id$="hiddenMainForm:form-r"]').value;
        if (r == null || r.trim() === "") {
            printError('R невалиден');
            return;
        }

        printError("")
    }
}
