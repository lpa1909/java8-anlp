$(document).ready(function() {
    let currentPage = 0;
    const pageSize = 3;
    let currentProductCode = null;

    $('.view-comment-button').click(function() {
        currentPage = 0;
        currentProductCode = $(this).data('product-code');
        loadComments(currentPage, currentProductCode);
    })

    function loadComments(page, productCode) {
        $.ajax({
            url: '/loadCommentOfProduct?productCode=' + productCode + '&page=' + page + '&size=' + pageSize,
            type: 'GET',
            contentType: 'application/json',
            success: function(response) {
                console.log(response)
                $('#viewCommentDetail').empty();

                $.each(response.content, function (index, commentInfo) {
                    const commentElement = $('<div class="comment" id="viewCommentDetail">')
                    commentElement.html(`
                        <div class="user-info">
                            <img src="data:image/png;base64,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" alt="Ảnh đại diện" class="avatar">
                            <div class="user-details">
                                <div class="rating">
                                    <span class="star filled">&#9733;</span>
                                    <span class="star filled">&#9733;</span>
                                    <span class="star filled">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                </div>
                                <h4>${commentInfo.userName}</h4>
                                <p class="comment-date">${formatDate(commentInfo.createdAt)}</p>
                            </div>
                        </div>
                    <p class="comment-text">${commentInfo.details}</p>
                    `);
                    $('#viewCommentDetail').append(commentElement);
                })

                // Cập nhật phân trang
                updatePagination(response);

                $('#viewAccountDetail').modal('show')
                toastr.info('Load data successfully', response);
            },
            error: function (jqXHR, status, error) {
                toastr.error('Load data failed.');
            }
        });
    }

    function updatePagination(response) {
        const totalPages = response.totalPages;
        console.log(totalPages)
        // Tạo hoặc cập nhật phần tử phân trang
        let pagination = $('#pagination');
        if (pagination.length === 0) {
            pagination = $('<div id="pagination"></div>');
            $('#viewAccountDetail').append(pagination);
        } else {
            pagination.empty();
        }

        // Tạo các nút phân trang
        for (let i = 0; i < totalPages; i++) {
            const pageButton = $('<button class="page-button btn btn-ouline-danger">' + (i + 1) + '</button>');
            if (i === currentPage) {
                pageButton.addClass('active');
            }
            pageButton.click(function() {
                currentPage = i;
                loadComments(currentPage, currentProductCode); // Truyền currentProductCode
            });
            pagination.append(pageButton);
        }
    }

    $('.close-button').click(function(){
        $('#viewAccountDetail').modal('hide');
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
})