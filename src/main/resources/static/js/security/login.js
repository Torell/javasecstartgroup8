document.addEventListener("DOMContentLoaded", function() {
    const errorMessageDiv = document.getElementById("errorMessage");

    if (errorMessageDiv) {
        setTimeout(function() {
            errorMessageDiv.style.display = 'none';
        }, 5000);
    }
});