$(document).ready(function() {

    $('.change-button').click(function(e) {
        const orderId = $(this).data('order-id');
        console.log(orderId)
        $('#addAccountForm').data('order-id', orderId);
        $('#addAccountForm').modal('show');
    })

    $('#addAccountForm .dropdown-item[data-status]').click(function (e) {
        e.preventDefault();

        const orderId = $('#addAccountForm').data('order-id');
        const selectedStatus = $(this).data('status');

        $.ajax({
            url: '/admin/changeStatus?status=' + selectedStatus + '&orderNum=' + orderId,
            method: 'POST',
            contentType: 'application/json',
            success: function (response) {
                $('#addAccountForm').modal('hide');
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

    $('.close-btn').click(function() {
        $('#addAccountForm').modal('hide');
    })
})