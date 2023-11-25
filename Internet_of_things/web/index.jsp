
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        .header{
            display: flex;
            width: 100%;
            background-color: #333333;
            justify-content: space-between;
            color: white;
            padding: 0px;
            margin: 0px;
        }
        .header h2:first-child {
            padding-left: 30px; 
        }

        .header h2:last-child {
            padding-right: 30px;
        }
        .sidebar{
            margin-top: 5px;
            width: 20%;
            color: white;
            height: 70%;
            background-color: #333333;
            
        }
        .sidebar h2{
            margin-left: 20px;
        }
        .val{
            color: white;
            margin-left: 30px;
            margin-top: 20px;
        }
        .control{
            background-color: #666666;
            padding: 5px;
        }
        .title{
            display: flex;
            text-align: center;
            background-color: #666666;
        }
        .measure{
            background-color: #666666;
            padding-left: 30px;
        }
        .measure a{
            color: white;
        }
        .sidebar p {
            padding: 0;
            margin: 0;
            margin-left: 20px
        }
        .container{
            display: flex;
        }
        .container-value{
            display: flex;
            margin-top: 20px;
            margin-bottom: 20px;
        }
        .temp{
            display: flex;
            text-align: center;
            border: 1px solid black;
            margin-left: 110px;
        }
        .temp-container {
            flex-direction: column;
            padding-left: 20px;
            padding-right: 20px;
        }
        .content{
            padding-left: 50px;
        }
        .button-container {
            position: relative;
        }

        #auto {
            position: absolute;
            top: 0;
            left: 0;
            background-color: greenyellow;
        }
        
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }

        table tr{
            height: 50px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h2>Trang quan ly thông tin</h2>
        <h2>Admin</h2>
    </div>
    
    <div class="container">
        <div class="sidebar">
            <div class="title">
                <img src="ptit.png" alt="alt" style="width: 50px; height: 50px; margin-top: 7px; padding-left: 15px; border-radius: 50%" />
                <h2>Smart Garden</h2>
            </div>
            <p>Control</p>
            <div class="control">
                <a href="" class="val">Device control</a>
            </div>

            <p style="margin-left: 20px">Environmental value</p>
            <div class="measure">
                <a href="">Temperature</a><br><br>
                <a href="">Humidity</a><br><br>
                <a href="">Soil moisture</a><br><br>
            </div>
        </div>
        
        <div class="content">
            <h1 style="padding-left: 100px;">Device Control</h1>
            <div style="border: 1px solid black; width: 105%; margin-left: 50px"></div>
            <div class="container-value">
                <div class="temp">
                    <img src="nhiet-do.jfif" alt="alt" style="width: 100px; height: 100px"/>
                    <div class="temp-container">
                        <p>Temperature</p>
                        <h2>${nhietdo}°C</h2>
                    </div>
                </div>
                <div class="temp">
                    <img src="do-am.jpg" alt="alt" style="width: 100px; height: 100px"/>
                    <div class="temp-container">
                        <p>Humidity</p>
                        <h2>${khongkhi}%</h2>
                    </div>
                </div>
                
                <div class="temp">
                    <img src="do-am-dat.jfif" alt="alt" style="width: 100px; height: 100px"/>
                    <div class="temp-container">
                        <p>Soil moisture</p>
                        <h2>${amdat}%</h2>
                    </div>
                </div>
            </div>
            <div style="border: 1px solid black; width: 105%; margin-left: 50px"></div>
            <div style="margin-left: 50px; margin-top: 20px;">
                <div class="button-container">
                    <button type="button" id="manual" onclick="clickManual()" style="background-color: greenyellow">Manual</button> <br>
                    <button type="button" id="auto" onclick="clickAuto()" style="background-color: greenyellow; display: none">Auto</button><br>
                    <form action="" method="post" style="display: none" id="form" onsubmit="disableButtons()" >
                        Temperature<input type="text" name="nhietdo">
                        Humidity<input type="text" name="doamkk">
                        Soil moisture<input type="text" name="doamdat">
                        <input type="submit" value="Save">
                    </form>
                </div>
            </div>
            <table>
                <tr>
                    <th>STT</th>
                    <th>Chuc nang</th>
                    <th>Trang Thai</th>
                    <th>ON/OFF</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>Dieu khien quat</td>
                    <td>${TTQuat}</td>
                    <td><button type="button" id="quat" onclick="solve1()" style="background-color: greenyellow">${TTQuat}ON</button></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Dieu khien phun suong</td>
                    <td>${TTPhunSuong}</td>
                    <td><button type="button" id="phunsuong" onclick="solve2()" style="background-color: greenyellow">${TTPhunSuong}</button></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Dieu khien bom</td>
                    <td>${TTBom}</td>
                    <td><button type="button" id="bom" onclick="solve3()" style="background-color: greenyellow">${TTBom}</button></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>Dieu khien Den</td>
                    <td>${TTDen}</td>
                    <td><button type="button" id="led" onclick="solve4()" style="background-color: greenyellow">${TTDen}</button></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>Bat Tat Ca</td>
                    <td>${TTAll}</td>
                    <td><button type="button" id="all" onclick="solve5()" style="background-color: greenyellow">${TTAll}</button></td>
                </tr>
            </table>
        </div>
    </div>
    <script>
        function clickManual() {
            document.getElementById("manual").style.display = "none";
            document.getElementById("auto").style.display = "block";
            document.getElementById("form").style.display = "block";
        }

        function clickAuto() {
            document.getElementById("auto").style.display = "none";
            document.getElementById("form").style.display = "none";
            document.getElementById("manual").style.display = "block";
        }
        
        function solve1(){
            var a = document.getElementById("quat");
            var buttonText = a.textContent;
            if (buttonText === "ON") {
                a.textContent = "OFF";
                a.style.backgroundColor = "red";
            } else {
                a.textContent = "ON";
                a.style.backgroundColor = "greenyellow";
            }
        }
        
        function solve2(){
            var a = document.getElementById("phunsuong");
            var buttonText = a.textContent;
            if (buttonText === "ON") {
                a.textContent = "OFF";
                a.style.backgroundColor = "red";
            } else {
                a.textContent = "ON";
                a.style.backgroundColor = "greenyellow";
            }
        }
        
        function solve3(){
            var a = document.getElementById("bom");
            var buttonText = a.textContent;
            if (buttonText === "ON") {
                a.textContent = "OFF";
                a.style.backgroundColor = "red";
            } else {
                a.textContent = "ON";
                a.style.backgroundColor = "greenyellow";
            }
        }
        
        function solve4(){
            var a = document.getElementById("led");
            var buttonText = a.textContent;
            if (buttonText === "ON") {
                a.textContent = "OFF";
                a.style.backgroundColor = "red";
            } else {
                a.textContent = "ON";
                a.style.backgroundColor = "greenyellow";
            }
        }
        
        function solve5(){
            var a = document.getElementById("all");
            var buttonText = a.textContent;
            if (buttonText === "ON") {
                a.textContent = "OFF";
                a.style.backgroundColor = "red";
            } else {
                a.textContent = "ON";
                a.style.backgroundColor = "greenyellow";
            }
        }
        
        function disableButtons() {
            document.getElementById("quat").disabled = true;
            document.getElementById("phunsuong").disabled = true;
            document.getElementById("bom").disabled = true;
            document.getElementById("led").disabled = true;
            document.getElementById("all").disabled = true;
        }

        solve1();
        solve2();
        solve3();
        solve4();
        solve5();
        function fetchDataFromServlet() {
            $.ajax({
                url: 'MqttSubscriberServlet',
                method: 'POST',
                dataType: 'json',
                success: function(response) {
                    if (response.success) {
                        // Cập nhật dữ liệu trên trang web
                        $('#nhietdo').text(response.nhietdo);
                        $('#amdat').text(response.amdat);
                        $('#khongkhi').text(response.khongkhi);
                        alert('Nhiệt độ: ' + response.nhietdo);
                        // Tiếp tục lặp lại việc gọi fetchDataFromServlet()
                        setTimeout(fetchDataFromServlet, 1000); // Gọi lại sau 1 giây
                    } else {
                        console.log('Lỗi khi gửi yêu cầu đến servlet');
                    }
                },
                error: function(xhr, status, error) {
                    console.log('Lỗi AJAX: ' + error);
                }
            });
        }
        fetchDataFromServlet();
    </script>
</body>
</html>

