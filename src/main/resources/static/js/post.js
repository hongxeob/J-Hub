let index = {
    init: function () {
        $("#btn-write").on("click", () => {
            this.save();
        });

        $("#btn-delete").on("click", () => {
            this.delete();
        });

        $("#btn-update").on("click", () => {
            this.update();
        });
        $("#btn-comment-save").on("click", () => {
            this.saveComment();
        })

        $("btn-delete -comment").on("click", () => {
            this.deleteComment();
        })

    },

    save: function () {
        let data = {
            username: $("#username").val(),
            title: $("#title").val(),
            content: $("#content").val(),
            category: $("#category").val(),
        }
        var username = $("#username").val();
        var title = $("#title").val();
        var content = $("#content").val();

        if (username.length == 0) {
            alert("게시물 작성 확인에 필요한 유저ID를 입력해주세요.")
            $("#username").focus();
            return false;
        }

        if (title.length == 0) {
            alert("제목은 필수입니다.");
            $("#title").focus();
            return false;
        }

        if (title.length < 4) {
            alert("제목은 4글자 이상 적어주세요.");
            $("#title").focus();
            return false;
        }

        if (content.length == 0) {
            alert("내용은 필수입니다.");
            $("#content").focus();
            return false;
        }

        if (content.length < 0) {
            alert("내용은 4자 이상 작성해 주세요.");
            $("#content").focus();
            return false;
        }

        $.ajax({
            type: "POST",
            url: "/api/v1/posts",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("글이 작성되었습니다!!🎉")
            location.href = "/posts";
        }).fail(function (error) {
            alert("잘못된 회원 ID입니다.");
        });
    },

    update: function () {
        let id = $("#id").val();
        let data = {
            username: $("#username").val(),
            title: $("#title").val(),
            content: $("#content").val(),
            category: $("#category").val(),
        }

        $.ajax({
            type: "PATCH",
            url: "/api/v1/posts/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("글이 수정 되었습니다!!🎉")
            location.href = "/posts/" + id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete: function () {
        if (confirm("정말로 삭제하시겠습니까?")) {
            let id = $("#id").text();
            let username = prompt("게시물 작성자의 ID를 입력하세요 : ")

            if (!username) {
                alert("아이디를 입력해주세요.");
                return;
            }

            let data = {
                username: username
            }

            $.ajax({
                type: "DELETE",
                url: "/api/v1/posts/" + id,
                data: {username: username},
                dataType: "json"
            }).done(function (res) {
                alert("글이 삭제되었습니다!!🎉")
                location.href = "/posts";
            }).fail(function (error) {
                alert("작성자ID와 일치하지 않습니다.");
            });
        }
    },

    saveComment: function () {
        let data = {
            content: $("#comment-content").val(),
            username: $("#username").val(),
        };

        let postId = $("#postId").val();

        $.ajax({
            type: "POST",
            url: `/api/v1/posts/${postId}/comments`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("댓글이 작성되었습니다!!🎉")
            location.href = `/posts/${postId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

$(document).on('click', '.delete-comment-btn', function () {
    var postId = $(this).data('post-id');
    var commentId = $(this).data('comment-id');

    var username = prompt("작성자 이름을 입력하세요:");

    deleteComment(postId, commentId, username);
});

function deleteComment(postId, commentId, username) {
    $.ajax({
        type: "DELETE",
        url: `/api/v1/posts/${postId}/comment/${commentId}`,
        data: {username: username}  // 'username' 값을 요청 파라미터로 전달
    }).done(function (res) {
        alert("댓글이 삭제되었습니다!!🎉");
        location.href = "/posts/" + postId;
    }).fail(function (error) {
        alert("작성자 ID와 일치하지 않습니다.");
    });
}

index.init();
