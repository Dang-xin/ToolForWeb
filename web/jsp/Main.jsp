<%--
  Created by IntelliJ IDEA.
  User: dang.xin
  Date: 2024/7/6
  Time: 1:04
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
    <link rel="stylesheet" href="../bootstrap-switch/bootstrap-switch.min.css">
    <script src="../js/jquery-3.7.1.min.js"></script>
    <script src="../bootstrap/js/bootstrap.bundle.js"></script>
    <script src="../bootstrap-switch/bootstrap-switch.js"></script>
    <script src="../bootstrap/js/bootstrap.min.js"></script>
    <script src="../js/common.js"></script>
</head>
<body>
<div class="row no-gutters">
    <div class="col-12" style="background-color: #333; color: #fff; height: 40px">
    </div>
</div>
<div class="float-right">
    <label for="DBConnectionInfo-left-switch"></label>
    <input id="DBConnectionInfo-left-switch" type="checkbox"  checked>
</div>

<div class="row no-gutters h-100" style="margin-top: 40px">
    <div class="col-2" id="tabs">
        <div class="nav flex-column nav-pills" role="tablist" aria-orientation="vertical">
            <button id="DBConnectionInfo-tab" class="nav-link active" data-toggle="pill" data-target="#DBConnectionInfo" type="button" role="tab" aria-controls="DBConnectionInfo" aria-selected="true">数据库连接信息</button>
            <button id="TranslateSQL-tab" class="nav-link" data-toggle="pill" data-target="#TranslateSQL" type="button" role="tab" aria-controls="TranslateSQL" aria-selected="true">翻译SQL</button>
            <button id="Test-tab" class="nav-link" data-toggle="pill" data-target="#Test" type="button" role="tab" aria-controls="Test" aria-selected="true">测试页面</button>
        </div>
    </div>

    <div id="Tool-Area" class="col-7">
        <div class="tab-content offset-1 col-6" style="margin: 10% 20% 100% 20%">
            <div class="tab-pane fade show active" id="DBConnectionInfo" role="tabpanel" aria-labelledby="DBConnectionInfo-tab">
                <div class="embed-responsive embed-responsive-1by1">
                    <iframe class="embed-responsive-item" src="DBConnectionInfo.jsp"></iframe>
                </div>
            </div>
            <div class="tab-pane fade" id="TranslateSQL" role="tabpanel" aria-labelledby="TranslateSQL-tab">
                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item" src="TranslateSQL.jsp"></iframe>
                </div>
            </div>
            <div class="tab-pane fade" id="Test" role="tabpanel" aria-labelledby="Test-tab">
                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item" src="Test.jsp"></iframe>
                </div>
            </div>
        </div>
    </div>

    <div class="col-3">
        <div id="DBConnectionInfo-left" class="border border-success float-right" style="width: 90%">
            <div class="form-group w-50 float-left" style="margin: 5% 0 0 5%">
                <label for="DBConnectionSelect">数据库连接名称</label>
                <select class="form-control float-right" id="DBConnectionSelect">
                    <option>--请选择要连接的数据库名称--</option>
                </select>
            </div>
            <div class="row float-left" style="width: 85%; margin: 5% 0 5% 5%">
                <label for="Server-Main">服务器地址</label>
                <input id="Server-Main" type="text" class="form-control" disabled>

                <label for="Port-Main">端口号</label>
                <input id="Port-Main" type="text" class="form-control"  disabled>

                <label for="DataBaseName-Main">数据库名称</label>
                <input id="DataBaseName-Main" type="text" class="form-control" disabled>

                <label for="UserID-Main">用户名</label>
                <input id="UserID-Main" type="text" class="form-control" disabled>

                <label for="Password-Main">密码</label>
                <input id="Password-Main" type="password" class="form-control" disabled>
            </div>
        </div>
    </div>
</div>

<script>
    /*
    * 根据左侧菜单栏选择显示不同的画面
    * */
    $('#tabs button').on('click', function (event) {
        event.preventDefault()
        $(this).tab('show')
    });

    /*
    * switch开关按钮的设定及绑定对应的方法
    * */
    $("[id='DBConnectionInfo-left-switch']").bootstrapSwitch({
        onColor: "success",
        offColor: "warning",
        onSwitchChange : function(event, state) {
            if (state === true) {
                $("#DBConnectionInfo-left").show()
                $("#Tool-Area").removeClass("col-10").addClass("col-7")

            } else {
                $("#DBConnectionInfo-left").hide()
                $("#Tool-Area").removeClass("col-7").addClass("col-10")
            }
        }
    });
</script>
</body>
</html>
