document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    const errorMessage = document.getElementById("errorMessage");

    form.addEventListener("submit", function(event) {
        const passwordInput = document.getElementById("password");
        const passwordConfirmationInput = document.getElementById("passwordConfirmation");

        if (passwordInput.value !== passwordConfirmationInput.value) {
            event.preventDefault();
            errorMessage.style.display = "block";
            passwordInput.value = "";
            passwordConfirmationInput.value = "";
            setTimeout(function() {
                errorMessage.style.display = "none";
            }, 5000);
        }
    });
});