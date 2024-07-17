<%--
  Created by IntelliJ IDEA.
  User: dang.xin
  Date: 2024/7/7
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <!-- 必须的 meta 标签 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Title</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
    <script src="../js/jquery-3.7.1.min.js"></script>
    <script src="../js/popper.min.js"></script>
    <script src="../bootstrap/js/bootstrap.min.js"></script>
    <script src="../js/common.js"></script>
</head>
<body>
<form id="NewDBConnectionInfo">
    <div class="form-group">
        <div class="input-group" style="margin-bottom: 20px">
            <div>
                <label for="DBConnectionName">端口号</label>
                <input id="DBConnectionName" name="DBConnectionName" type="text" class="form-control" autocomplete="off" placeholder="请输入数据库连接名">
            </div>
            <div class="btn-group" style="height: 38px">
                <button class="btn btn-light dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
                </button>
                <ul id="DBConnectionName-ul" class="dropdown-menu">
                    <li>首页1</li>
                    <li>首页2</li>
                    <li>首页3</li>
                    <li>首页4</li>
                </ul>
            </div>
        </div>

        <div id="NoDBConnectionName-alert" class="alert alert-danger" role="alert" style="padding: 5px; display: none">
            <span style="font-size: 15px;">请输入数据库连接名</span>
        </div>

        <label for="Server">服务器地址</label>
        <input id="Server" name="Server" type="text" class="form-control" autocomplete="off">

        <label for="Port">端口号</label>
        <input id="Port" name="Port" type="text" class="form-control" autocomplete="off">

        <label for="DataBaseName">数据库名称</label>
        <input id="DataBaseName" name="DataBaseName" type="text" class="form-control" autocomplete="off">

        <label for="UserID">用户名</label>
        <input id="UserID" name="UserID" type="text" class="form-control" autocomplete="off">

        <label for="Password">密码</label>
        <input id="Password" name="Password" type="password" class="form-control" autocomplete="off">
    </div>

    <div class="btn-group float-right" role="group">
        <button id="Save" type="button" class="btn btn-primary">保存</button>
        <button id="Connect" type="button" class="btn btn-info">连接</button>
        <button id="Delete" type="button" class="btn btn-danger">删除</button>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function(){
        $("#Save").click(function () {
            let DBConnectionName = $("#DBConnectionName").val();
            if (DBConnectionName === "") {
                $("#NoDBConnectionName-alert").show();
                return false;
            }
            let DBConnectionInfoJson = $.GetDBConnectionInfoFormToJson();
            let DBConnectionNameInStorge = localStorage.getItem('DBConnectionName');
            DBConnectionNameInStorge[DBConnectionName] = DBConnectionInfoJson;
            // 存储到localStorage
            localStorage.setItem("DBConnectionName", DBConnectionInfoJson);
            console.log("localStorage=" + localStorage.getItem("DBConnectionName"));
            console.log("sessionStorage" + sessionStorage.getItem("DBConnectionName"));
        });

        $("#Delete").click(function () {
            let DBConnectionName = $("#DBConnectionName").val();
            if (DBConnectionName === "") {
                $("#NoDBConnectionName-alert").show();
                return false;
            }
            // 从localStorage中删除
            localStorage.removeItem(DBConnectionName);
        });

        $("#DBConnectionName").focus(function () {
            $("#NoDBConnectionName-alert").hide();
        });

        $.GetDBConnectionInfoFormToJson = function() {
            // 获取表单数据
            let formData = $("#NewDBConnectionInfo").serializeArray();
            let DBConnectionInfoJson = {};
            $.each(formData, function(index, field){
                DBConnectionInfoJson[field.name] = field.value;
            });
            // 将表单数据转换为Json格式
            return JSON.stringify(DBConnectionInfoJson);
        }

        $.DBConnectionNameUlReload = function () {
            let liStr = "";
            for (let key in sessionStorage) {
                liStr += "<li>" + key + "</li>";
            }
            $("#DBConnectionName-ul").empty().append(liStr);
        }

        $(function(){
            OnloadDBConnectionInfo(function () {
                $.DBConnectionNameUlReload();
            });
        });
    });
</script>
</body>
</html>
