<%--
  Created by IntelliJ IDEA.
  User: huangfan
  Date: 2018/12/22
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("ctx",request.getContextPath());
%>
<html>
<head>
    <title>login success</title>
    <script src="/resources/js/common/jquery-1.8.2.min.js"></script>
</head>
<script type="text/javascript">
    var ctx="${ctx}";
    $(document).ready(function () {
//        getCurrentUser();
        $("#redirect").click(function () {
            $.ajax({
                type:"post",
                url:ctx+"/loginController/auth",
                contentType:"application/json; charset=utf-8",
                dataType : "text",
                success:function(data){
                    console.log(data);
                    $("#msg").text(" <h2>request succesfully,we will open a page according to the response result...</h2>");
                    if(data=="success"){
                        window.open("http://localhost:8081/getSession");
                        $("#msg").append("<br> <h2>now,pay attention to the address in the browser!</h2>")
                    }else{
//                        window.location.href="http://localhost:8763/crossDomainLogin/getSession";
                    }
                }
            });
        });
    })
 /*   function getCurrentUser () {
        $.ajax({
            type:"post",
            url:ctx+"/loginController/getSession",
            contentType:"application/json; charset=utf-8",
            dataType : "text",
            success:function(data){
                console.log(data);
                $("#user").text(data);
            }
        });
    }*/

</script>
<body>
<h2>login success ! Current user:</h2>
<div id="user">${username}</div>

<h2>And now we are going to redirect...</h2>

    <input type="button" value="redirect" id="redirect">
    <div id="msg"></div>

</body>
</html>
