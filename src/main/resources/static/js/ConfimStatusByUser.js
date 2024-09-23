$(document).ready(function() {

    $('.confirm-btn').click(function(e) {
        const orderNum = $(this).data('order-num');
        console.log(orderNum);
        $('#confirmStatusForm').data('order-num', orderNum);
        $('#confirmStatusForm').modal('show');
    })

    $('#confirmStatusForm .confirm-btn').click(function (e) {
        e.preventDefault();
        const orderNum = $('#confirmStatusForm').data('order-num');
        $.ajax({
            url: '/user/confirmStatus?&orderNum=' + orderNum,
            method: 'POST',
            contentType: 'application/json',
            success: function (response) {
                $('#confirmStatusForm').modal('hide');
                toastr.success(response);
                setTimeout(function() {
                    window.location.reload();
                }, 500);
            },
            error: function (xhr, status, error) {
                toastr.error(xhr.responseText);
            }
        });
    });

    $('.cancel-btn').click(function() {
        $('#confirmStatusForm').modal('hide');
    })
})