let index = {
    init: function () {
        $("#btn-join").on("click", () => {
            this.join();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
    },

    join: function () {
        let data = {
            username: $("#username").val(),
            nickname: $("#nickname").val(),
            introduction: $("#introduction").val(),
            userRole: $("#userRole").val()
        }

        var username = $("#username").val();
        var nickname = $("#nickname").val();
        var introduction = $("#introduction").val();
        var userRole = $("#userRole").val();

        if (username.length === 0) {
            alert("아이디를 입력해 주세요");
            $("#username").focus();
            return false;
        }

        if (username.length < 4 || username.length > 16) {
            alert("아이디는 4~12자 사이의 영어만 사용해 주세요");
            $("#username").focus();
            return false;
        }

        if (nickname.length === 0) {
            alert("닉네임을 입력해주세요.")
            $("#nickname").focus();
            return false;
        }

        if (nickname.length < 2 || nickname.length > 12) {
            alert("닉네임은 2~12자 사이만 가능합니다.");
            $("#nickname").focus();
            return false;
        }

        if (introduction.length === 0) {
            alert("자기 소개를 적어주세요!")
            $("#introduction").focus();
            return false;
        }

        if (introduction.length < 10) {
            alert("자기 소개는 10글자 이상 적어주세요!")
            $("#introduction").focus();
            return false;
        }

        $.ajax({
            type: "POST",
            url: "/api/v1/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            if (res.status === 500) {
                alert("유저 등록에 실패하였습니다!");
            } else {
                alert("유저 등록 완료!🎉");
                location.href = "/";
            }
        }).fail(function (error) {
            alert("양식에 맞게 정보를 기입해 주세요!!");
        });
    },

    update:
        function () {
            let data = {
                username: $("#username").val(),
                nickname: $("#nickname").val(),
                introduction: $("#introduction").val(),
                userRole: $("#userRole").val()
            }

            var username = $("#username").val();
            var nickname = $("#nickname").val();
            var introduction = $("#introduction").val();
            var userRole = $("#userRole").val();

            if (username.length === 0) {
                alert("아이디를 입력해 주세요");
                $("#username").focus();
                return false;
            }

            if (nickname.length === 0) {
                alert("변경할 닉네임을 입력해주세요.")
                $("#nickname").focus();
                return false;
            }

            if (nickname.length < 2 || nickname.length > 12) {
                alert("닉네임은 2~12자 사이만 가능합니다.");
                $("#nickname").focus();
                return false;
            }

            if (!/^[가-힣a-zA-Z0-9]*$/.test(nickname)) {
                alert("닉네임은 특수문자를 제외한 문자로 지정해주세요.");
                $("#nickname").focus();
                return false;
            }

            if (introduction.length === 0) {
                alert("자기 소개를 적어주세요!")
                $("#introduction").focus();
                return false;
            }

            if (introduction.length < 10) {
                alert("자기 소개는 10글자 이상 적어주세요!")
                $("#introduction").focus();
                return false;
            }
            $.ajax({
                type: "PATCH",
                url: "/api/v1/user/update",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function (res) {
                if (res.status === 500) {
                    alert("유저 수정에 실패하였습니다!");
                } else {
                    alert("유저 수정 완료!🎉");
                    location.href = "/";
                }
            }).fail(function (error) {
                alert("해당 ID를 찾을 수 없습니다. 올바르게 입력해 주세요.");
            });
        },
}

index.init();