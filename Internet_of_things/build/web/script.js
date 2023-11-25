setInterval(function () {
    fetch('http://localhost:9999/Internet_of_things/Sensor_Data?action=schedule')
        .then(function (resolve) {
          return resolve.json();
        })
        .then(function (data) {
          schedule(data);

    })
    function schedule(data) {
//        console.log(data);
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            var targetDateTime = new Date(item.time);
            // Lấy thời gian hiện tại
            const currentDateTime = new Date();
            // So sánh thời gian hiện tại với thời gian mục tiêu
            const isMatch = (
                targetDateTime.getFullYear() === currentDateTime.getFullYear() &&
                targetDateTime.getMonth() === currentDateTime.getMonth() &&
                targetDateTime.getDate() === currentDateTime.getDate() &&
                targetDateTime.getHours() === currentDateTime.getHours() &&
                targetDateTime.getMinutes() === currentDateTime.getMinutes()
            );
            if (isMatch && item.state === 0) {
              // Thực hiện hành động khi đạt đến thời gian mục tiêu
//                alert("Đã đạt đến thời gian mục tiêu:");
                if(item.device === 'fan'){
                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=on1`, {
                        method: 'Post'
                    });
                }
                else if(item.device === 'pump'){
                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=on2`, {
                        method: 'Post'
                    });
                }
                else{
                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=on3`, {
                        method: 'Post'
                    });
                }
                fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=updateState&id=${item.id}&dulieu=1`);
            } 
            var tmp = new Date(item.time_off);
            if(currentDateTime >= tmp && item.state === 1){
                if(item.device === 'fan'){
                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=off1`, {
                        method: 'Post'
                    })
                }
                else if(item.device === 'pump'){
                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=off2`, {
                        method: 'Post'
                    })
                }
                else{
                    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?data=off3`, {
                        method: 'Post'
                    })
                }
                fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=updateState&id=${item.id}&dulieu=2`);
            }
        }
    }
}, 0);