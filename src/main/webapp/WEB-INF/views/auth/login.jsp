<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>관리자 로그인</title>
    <script type="text/javascript">
        function doLogin() {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "login.json", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            var userId = document.getElementById("loginId").value;
            var password = document.getElementById("password").value;
            var params = "userId=" + encodeURIComponent(userId)
                       + "&password=" + encodeURIComponent(password);

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        try {
                            var res = JSON.parse(xhr.responseText);
                            alert(res.message);
                            if (res.success && res.redirectUrl) {
                                location.href = res.redirectUrl;
                            }
                        } catch (e) {
                            alert("로그인 처리 중 오류가 발생했습니다.");
                        }
                    } else {
                        alert("서버 오류가 발생했습니다.");
                    }
                }
            };
            xhr.send(params);
        }
    </script>
</head>
<body>
<h2>관리자 로그인</h2>

<div>
    <label for="loginId">아이디</label>
    <input type="text" id="loginId" name="loginId"/>
</div>
<div>
    <label for="password">비밀번호</label>
    <input type="password" id="password" name="password"/>
</div>
<div>
    <button type="button" onclick="doLogin()">로그인</button>
</div>

</body>
</html>
