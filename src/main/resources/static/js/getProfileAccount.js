$(document).ready(function () {
    toastr.options = {
        'closeButton': true,
        'debug': false,
        'newestOnTop': false,
        'progressBar': false,
        'positionClass': 'toast-top-right',
        'preventDuplicates': false,
        'showDuration': '1000',
        'hideDuration': '1000',
        'timeOut': '5000',
        'extendedTimeOut': '1000',
        'showEasing': 'swing',
        'hideEasing': 'linear',
        'showMethod': 'fadeIn',
        'hideMethod': 'fadeOut',
    }
    $('.edit-button').click(function (e) {
        var accountId = $(this).data('accountId');
        console.log(accountId);
        $.ajax({
            url: '/admin/loadData?accountId=' + accountId,
            method: 'GET',
            contentType: 'application/json',
            success: function (response) {
                console.log(response)
                $('#changePasswordForm input[name="fullName"]').val(response.fullName);
                $('#changePasswordForm input[name="gmail"]').val(response.gmail);
                $('#changePasswordForm input[name="phoneNumber"]').val(response.phoneNumber);
                $('#changePasswordForm input[name="userName"]').val(response.userName);
                $('#changePasswordForm input[name="userRole"] option').val(response.userRole);
                $('#changePasswordForm').modal('show');
                toastr.success('Load data successfully', response);
            },
            error: function (jqXHR, status, error) {
                toastr.error('Load data failed.');
            }
        });

    });

    $('.closeButton').click(function () {
        $('#changePasswordForm').modal('hide');
    })

    $('.view-btn').click(function () {
        var accountId = $(this).data('accountId');
        console.log(accountId);
        $.ajax({
            url: '/admin/loadData?accountId=' + accountId,
            method: 'GET',
            contentType: 'application/json',
            success: function(response){
                console.log(response)
                $('#viewAccountDetail table tr:gt(0)').remove();
                var newRow = '<tr>' +
                    '<td>' + response.fullName + '</td>' +
                    '<td>' + response.gmail + '</td>' +
                    '<td>' + response.phoneNumber + '</td>' +
                    '<td>' + response.userName + '</td>' +
                    '<td>' + response.userRole + '</td>' +
                    '<td>' + (response.active ? '<i class="fa fa-check-circle" aria-hidden="true" style="color: green;"></i>' : '<i class="fa fa-times-circle" aria-hidden="true" style="color: red;"></i>') + '</td>' +
                    '<td>' + formatDate(response.createdAt) + '</td>' +
                    '<td>' + formatDate(response.updatedAt) + '</td>' +
                    '<td>' + formatDate(response.deletedAt) + '</td>' +
                    '<td>' + (response.deleted ? '<i class="fa fa-check-circle" aria-hidden="true" style="color: green;"></i>' : '<i class="fa fa-times-circle" aria-hidden="true" style="color: red;"></i>') + '</td>' +
                    '</tr>';

                // Thêm hàng mới vào bảng
                $('#viewAccountDetail table').append(newRow);
                $('#viewAccountDetail').modal('show');
                toastr.success('Load data successfully', response);
            },
            error: function(jqXHR, status, error){
                toastr.error('Load data failed.');
            }
        });
    });

    $('.closeButton2').click(function () {
        $('#viewAccountDetail').modal('hide');
    })

    $('.add-btn').click(function () {
        $('#addAccountForm').modal('show');
    })

    $('.closeButtonAdd').click(function () {
        $('#addAccountForm').modal('hide');
    })


    function formatDate(dateString) {
        if (!dateString) return ''; // Xử lý trường hợp dateString là null hoặc undefined
        const date = new Date(dateString);
        const formatter = new Intl.DateTimeFormat('vi-VN', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
        return formatter.format(date);
    }

});



