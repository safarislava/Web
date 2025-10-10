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
    if (data.status === "error") {

        const requiredFields = document.querySelectorAll('#graphForm [required]');
        let hasEmptyFields = false;

        requiredFields.forEach(field => {
            if (!field.value || field.value.trim() === '') hasEmptyFields = true;
        });

        if (hasEmptyFields) {
            printError('R не введён');
        }
        else {
            printError("")
        }
    }
}

function handleMainAjaxEvent(data) {
    if (data.status === "error") {
        const requiredFields = document.querySelectorAll('#mainForm [required]');
        let hasEmptyFields = false;

        requiredFields.forEach(field => {
            if (!field.value || field.value.trim() === '') hasEmptyFields = true;
        });

        if (hasEmptyFields) {
            printError('Данные невалидны');
        }
        else {
            printError("")
        }
    }
}
