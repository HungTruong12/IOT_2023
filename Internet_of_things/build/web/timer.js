setInterval(function () {
    fetch('http://localhost:9999/Internet_of_things/Sensor_Data?action=schedule')
        .then(function (resolve) {
          return resolve.json();
        })
        .then(function (data) {
          getSchedule(data);

        })
    function getSchedule(data) {
        const tableBody = document.getElementById("table-body");
        tableBody.innerHTML = "";
        
        for (var i = 0; i < data.length; i++) {
            var item = data[i];

            // Tạo một hàng mới
            var row = document.createElement("tr");

            // Tạo các ô dữ liệu
            var sttCell = document.createElement("td");
            sttCell.textContent = i + 1;
            row.appendChild(sttCell);

            var deviceCell = document.createElement("td");
            deviceCell.textContent = item.device;
            row.appendChild(deviceCell);

            var timeOnCell = document.createElement("td");
            timeOnCell.textContent = item.time;
            row.appendChild(timeOnCell);

            var timeOffCell = document.createElement("td");
            timeOffCell.textContent = item.time_off;
            row.appendChild(timeOffCell);
            
            var deleteCell = document.createElement("td");
            var deleteButton = document.createElement("button");
            deleteButton.textContent = "Xóa";
            deleteButton.addEventListener("click", function() {
                fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=deleteSchedule&id=${item.id}`);
            });
            deleteCell.appendChild(deleteButton);
            row.appendChild(deleteCell);

            // Thêm hàng vào bảng
            tableBody.appendChild(row);
        }
    }
}, 1000);