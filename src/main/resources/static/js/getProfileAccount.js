$(document).ready(function() {
    $('.edit-button').click(function(e) {
        var accountId = $(this).data('accountId');
        console.log(accountId);
        $.ajax({
            url: '/admin/loadData?accountId=' + accountId,
            method: 'GET',
            contentType: 'application/json',
            success: function(response){
                console.log(response)
                $('#changePasswordForm input[name="fullName"]').val(response.fullName);
                $('#changePasswordForm input[name="gmail"]').val(response.gmail);
                $('#changePasswordForm input[name="phoneNumber"]').val(response.phoneNumber);
                $('#changePasswordForm input[name="userName"]').val(response.userName);
                $('#changePasswordForm input[name="userRole"] option').val(response.userRole);
                $('#changePasswordForm').modal('show');
                toastr.success('Load data successfully', response);
            },
            error: function(jqXHR, status, error){
                toastr.error('Load data failed.');
            }
        });

    });

    $('.closeButton').click(function(){
        $('#changePasswordForm').modal('hide');
    })

    $('.view-btn').click(function(){
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
                    '<td>' + response.createdAt + '</td>' +
                    '<td>' + response.updatedAt + '</td>' +
                    '<td>' + response.deletedAt + '</td>' +
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

    $('.closeButton2').click(function(){
        $('#viewAccountDetail').modal('hide');
    })
});

