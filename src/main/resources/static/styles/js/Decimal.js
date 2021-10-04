$(document).ready(function () {
    $(".decimal").change(function() {
        $(this).val(parseFloat($(this).val()).toFixed(2));
    });
});