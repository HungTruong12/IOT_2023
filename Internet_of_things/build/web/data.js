var nhietdo = document.getElementById('nhietdo');
var doam = document.getElementById('doam');
var doamdat = document.getElementById('doamdat');

var quat = document.getElementById('quat');
var bom = document.getElementById('bom');
var den = document.getElementById('led');
// nút Auto
//var autoButton = document.getElementById('auto');
//var displayStyle = window.getComputedStyle(autoButton).display;
// Nut dieu khien tbi
var quat2 = document.getElementById('quat2');
var bom2 = document.getElementById('bom2');
var den2 = document.getElementById('led2');
var on_all = document.getElementById('on_all');
var off_all = document.getElementById('off_all');

var subData = document.getElementById('subData');

setInterval(function () {
  fetch('http://localhost:9999/Internet_of_things/Sensor_Data?action=getData')
    .then(function (resolve) {
      return resolve.json();
    })
    .then(function (data) {
      updateCanvas(data);

    })
  function updateCanvas(data) {
    let temp = data.temp;
    let humi = data.humi;
    let soil = data.soil;
    nhietdo.textContent = temp + "°C";
    doam.textContent = humi + "%";
    doamdat.textContent = soil + "%";
  }
}, 3000);

//setInterval(function () {
//    fetch('http://localhost:9999/Internet_of_things/Sensor_Data?action=schedule')
//        .then(function (resolve) {
//          return resolve.json();
//        })
//        .then(function (data) {
//          schedule(data);
//
//    })
//    function schedule(data) {
////        console.log(data);
//        for (var i = 0; i < data.length; i++) {
//            var item = data[i];
//            var targetDateTime = new Date(item.time);
//            // Lấy thời gian hiện tại
//            const currentDateTime = new Date();
//            // So sánh thời gian hiện tại với thời gian mục tiêu
//            const isMatch = (
//                targetDateTime.getFullYear() === currentDateTime.getFullYear() &&
//                targetDateTime.getMonth() === currentDateTime.getMonth() &&
//                targetDateTime.getDate() === currentDateTime.getDate() &&
//                targetDateTime.getHours() === currentDateTime.getHours() &&
//                targetDateTime.getMinutes() === currentDateTime.getMinutes()
//            );
//            if (isMatch && item.state === 0) {
//              // Thực hiện hành động khi đạt đến thời gian mục tiêu
////                alert("Đã đạt đến thời gian mục tiêu:");
//                if(item.device === 'fan'){
//                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=on1`, {
//                        method: 'Post'
//                    });
//                }
//                else if(item.device === 'pump'){
//                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=on2`, {
//                        method: 'Post'
//                    });
//                }
//                else{
//                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=on3`, {
//                        method: 'Post'
//                    });
//                }
//                fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=updateState&id=${item.id}&dulieu=1`);
//            } 
//            var tmp = new Date(item.time_off);
//            if(currentDateTime >= tmp && item.state === 1){
//                if(item.device === 'fan'){
//                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=off1`, {
//                        method: 'Post'
//                    })
//                }
//                else if(item.device === 'pump'){
//                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=off2`, {
//                        method: 'Post'
//                    })
//                }
//                else{
//                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=off3`, {
//                        method: 'Post'
//                    })
//                }
//                fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=updateState&id=${item.id}&dulieu=2`);
//            }
//        }
//    }
//}, 0);

setInterval(function () {
  fetch('http://localhost:9999/Internet_of_things/Sensor_Data?action=getDataDevice')
    .then(function (resolve) {
      return resolve.json();
    })
    .then(function (data) {
      update(data);
    })
  function update(data) {
    let led = data.led;
    let pump = data.pump;
    let fan = data.fan;
    var autoButton = document.getElementById('auto');
    var displayStyle = window.getComputedStyle(autoButton).display;
    if(fan === 'ON'){
        quat.textContent = fan;
        quat.style.background = 'green';
        if(displayStyle === 'none'){
            quat2.style.background = 'red';
            quat2.textContent = 'OFF';
        }
    }
    else{
        quat.textContent = fan;
        quat.style.background = 'red';
        if(displayStyle === 'none'){
            quat2.style.background = 'green';
            quat2.textContent = 'ON';
        }
    }
    
    if(pump === 'ON'){
        bom.textContent = pump;
        bom.style.background = 'green';
        if(displayStyle === 'none'){
            bom2.style.background = 'red';
            bom2.textContent = 'OFF';
        }
    }
    else{
        bom.textContent = pump;
        bom.style.background = 'red';
        if(displayStyle === 'none'){
            bom2.style.background = 'green';
            bom2.textContent = 'ON';
        }
    }
    
    if(led === 'ON'){
        den.textContent = led;
        den.style.background = 'green';
        if(displayStyle === 'none'){
            den2.style.background = 'red';
            den2.textContent = 'OFF';
        }
    }
    else{
        den.textContent = led;
        den.style.background = 'red';
        if(displayStyle === 'none'){
            den2.style.background = 'green';
            den2.textContent = 'ON';
        }
    }
  }
}, 3000);

function clickManual() {
    document.getElementById('auto').style.display = 'block';
    document.getElementById('manual').style.display = 'none';
    document.getElementById('form').style.display = 'block';
  
    den2.disabled = 'true';
    quat2.disabled = 'true';
    bom2.disabled = 'true';
    on_all.disabled = 'true';
    off_all.disabled = 'true';
    
    den2.style.background = 'blue';
    den2.textContent = 'Auto';
    quat2.style.background = 'blue';
    quat2.textContent = 'Auto';
    bom2.style.background = 'blue';
    bom2.textContent = 'Auto';
    on_all.style.background = 'blue';
    on_all.textContent = 'Auto';
    off_all.style.background = 'blue';
    off_all.textContent = 'Auto';
    
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=state&data=auto`);
}

function clickAuto() {
    document.getElementById('auto').style.display = 'none';
    document.getElementById('manual').style.display = 'block';
    document.getElementById('form').style.display = 'none';
    
    on_all.style.background = 'green';
    on_all.textContent = 'ON';
    off_all.style.background = 'red';
    off_all.textContent = 'OFF';
    den2.disabled = false;
    quat2.disabled = false;
    bom2.disabled = false;
    on_all.disabled = false;
    off_all.disabled = false;
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=state&data=manual`);
}


quat2.addEventListener('click', function (){
    var message = "";
    if(quat2.textContent === 'ON'){
        message = 'on1';
        quat2.textContent = 'OFF';
        quat2.style.background = 'red';
    }
    else{
        message = 'off1';
        quat2.textContent = 'ON';
        quat2.style.background = 'green';
    }
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=${message}`, {
        method: 'Post'
    });
})

bom2.addEventListener('click', function (){
    var message = "";
    if(bom2.textContent === 'ON'){
        message = 'on2';
        bom2.textContent = 'OFF';
        bom2.style.background = 'red';
    }
    else{
        message = 'off2';
        bom2.textContent = 'ON';
        bom2.style.background = 'green';
    }
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=${message}`, {
        method: 'Post'
    })
})

den2.addEventListener('click', function (){
    var message = "";
    if(den2.textContent === 'ON'){
        message = 'on3';
        den2.textContent = 'OFF';
        den2.style.background = 'red';
    }
    else{
        message = 'off3';
        den2.textContent = 'ON';
        den2.style.background = 'green';
    }
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=${message}`, {
        method: 'Post'
    })
})

on_all.addEventListener('click', function (){
    fetch('http://localhost:9999/Internet_of_things/Sensor_Data?data=on4', {
        method: 'Post'
    })
})

off_all.addEventListener('click', function (){
    fetch('http://localhost:9999/Internet_of_things/Sensor_Data?data=off4', {
        method: 'Post'
    })
})

subData.addEventListener('click', function (){
    var t = document.getElementById("nhietdo2").value;
    var h = document.getElementById("doamkk2").value;
    var bomtuoi = document.getElementById("doambom2").value;
    var tatbom = document.getElementById("doamtat2").value;
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=postData&nhietdo=${t}&doamkk=${h}&doambom=${bomtuoi}&doamtat=${tatbom}`);
    alert("Success");
})
