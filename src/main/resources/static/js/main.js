$(document).ready(function () {
    $('.sub-btn').click(function () {
        $(this).next('.submenu').slideToggle(500);
    })
})

setTimeout(function () {
    $(".alert-message").addClass("show");
}, 100);

$(".alert-message").on("click", function () {
    $(".alert-message").fadeOut();
});

setTimeout(function () {
    $(".alert-message").removeClass("show");
}, 6000);