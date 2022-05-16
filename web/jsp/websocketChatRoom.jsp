<%-- Created by IntelliJ IDEA. User: shun_ Date: 2022/5/15 Time: 14:33 To change this template use File | Settings |
    File Templates. --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html>

        <head>
            <title>聊天窗口【${sessionScope.loginName}】</title>
            <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
            <script type="text/javascript">
                var ws;
                //这里修改外网ip或服务器地址 bg：tyza66 如果在本地测试请将地址改为localhost
                //var ws_url = "ws://localhost:8080/WebSocketChatRoom_war_exploded/chatroom";
                var ws_url = "ws://10.45.179.10:8080/WebSocketChatRoom_war_exploded/chatroom";
                var picp = "";
                $(function () {
                    ws_connect();
                    $("#send").click(function () {
                        ws_sendMsg();
                    });
                    $("#uploadImg").click(function () {
                        ws_sendImg();
                    });
                });

                function ws_connect() {
                    var loginUser = "${sessionScope.loginName}";
                    if ('WebSocket' in window) {
                        ws = new WebSocket(ws_url + "?loginName=" + loginUser);
                    } else if ('MozWebSocket' in window) {
                        ws = new MozWebSocket(ws_url + "?loginName=" + loginUser);
                    } else {
                        console.log('Error: WebSocket is not supported by this browser.');
                        return;
                    }

                    ws.onopen = function () {
                        console.log('Info: WebSocket connection opened.');
                    };

                    ws.onclose = function () {
                        console.log('Info: WebSocket closed.');
                    };

                    ws.onmessage = function (message) {
                        if ("string" == typeof (message.data)) {
                            var receiveMsg = message.data;
                            var obj = JSON.parse(receiveMsg);
                            if (obj.type == "s") {
                                $("#record").append("<div style=\"color:pink;\">" + obj.msgSender + ":" + obj.msgInfo + "</div>");
                                var userHtml = "";
                                var userList = obj.userList;
                                for (var i = 0; i < userList.length; i++) {
                                    userHtml = userHtml + userList[i] + "<br/>"
                                }
                                $("#userList").html(userHtml);
                            } else if (obj.type == "p") {
                                $("#record").append("<div style=\"color:green;\">" + obj.msgSender + "&nbsp;" + obj.msgDateStr + "</div><div>" + obj.msgInfo + "</div>");
                            } else if (obj.type == "img") {
                                picp = ("<div style=\"color:green;\">" + obj.msgSender + "&nbsp;" + obj.msgDateStr + "</div>");
                            } else {

                            }
                        } else {
                            var reader = new FileReader();
                            reader.readAsDataURL(message.data);
                            reader.onload = function (evt) {
                                if (evt.target.readyState == FileReader.DONE) {
                                    var url = evt.target.result;
                                    console.log(picp);
                                    $("#record").append(picp + "<div><img src='" + url + "' style='max-height:150px;max-width:150px;vertical-align:middle;align:middle;' /></div>");
                                }
                            }
                        }
                        console.log(message.data);
                    };
                }

                function ws_sendMsg() {
                    var msg = $("#msg").val();
                    ws.send(msg);
                    $("#msg").val("");
                }

                function ws_sendImg() {
                    //图片上传
                    var fObj = $("#img")[0].files[0];
                    if (fObj) {
                        var reader = new FileReader();
                        reader.readAsArrayBuffer(fObj);
                        reader.onload = function (evt) {
                            ws.send(evt.target.result);
                        }
                        $("#img").val("");
                    } else {
                        $("#img").val("");
                    }
                };
            </script>
        </head>

        <body>
            ********************【${sessionScope.loginName}】的聊天窗口******************************
            <table style="border: 1px solid #00F;">
                <tbody>
                    <tr>
                        <td colspan="2" align="center">
                            <h3>欢迎来到聊天页面</h3>
                        </td>
                    </tr>
                    <tr>
                        <td width="500px" height="300px" style="border: 1px solid #00F; vertical-align: top;"
                            id="content" name="content">
                            <div style="background-color:white;">
                                <table id="tbRecord">
                                    <tbody id="record"
                                        style="display:block;height:300px; width:500px; overflow:auto;" />
                                </table>
                            </div>
                        </td>
                        <td width="100px" style="border:1px solid #00F; vertical-align:top;">
                            <div style="overflow:auto;">
                                <table id="tbuserList">
                                    <tbody id="userList" style="display:block; height:300px;overflow:auto;" />
                                </table>
                            </div>
                        </td>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2" align="center">
                            <input id="msg" name="msg" style="width:100%;" placeholder="信息输入" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <button style="margin:0 30px 0 30px" id="send" name="send">发送</button>
                            <input type="file" id="img" style="width:200px; height:30px" />
                            <button id="uploadImg" name="uploadImg">上传图片</button>
                            <button style="margin:0 30px 0 30px" id="disconnect" name="disconnect">断开连接</button>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </body>

        </html>