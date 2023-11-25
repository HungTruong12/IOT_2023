var itemsPerPage = 10; // Số mục hiển thị trên mỗi trang
var currentPage = 1; // Trang hiện tại
var submit = document.getElementById('submit')
var start = document.getElementById('start').value
var end = document.getElementById('end').value
var select_search=document.getElementById("select-option-search").value
var search = document.getElementById('search-input')
var select = document.getElementById('select-option').value
var sort_type_list = document.getElementsByName('sort')
var sort_type = "";

for (var i = 0; i < sort_type_list.length; i++) {
  if (sort_type_list[i].checked) {
    sort_type = sort_type_list[i].value;
    break;
  }
}
submit.addEventListener('click', function () {
    console.log(end)
    console.log(search.value)
    currentPage=1
    GetDataBySearch(10)
    updatePagination()
})
function displayData(page) {
  var startIndex = (page - 1) * itemsPerPage;
  var endIndex = startIndex + itemsPerPage;
  var tableBody = document.getElementById("tableBody");
  tableBody.innerHTML = "";

  for (var i = 0; i < data2.length; i++) {
    var row = document.createElement("tr");
    var cell1 = document.createElement("td");
    var cell2 = document.createElement("td");
    var cell3 = document.createElement("td");
    var cell4 = document.createElement("td");
    var cell5 = document.createElement("td");
    var cell6 = document.createElement("td");
    var cell7 = document.createElement("td");
    cell1.textContent = data2[i].id;
    cell2.textContent = data2[i].sensor_name;
    cell3.textContent = data2[i].temp;
    cell4.textContent = data2[i].humi;
    cell5.textContent = data2[i].light;
    cell6.textContent = data2[i].bui;
    cell7.textContent=data2[i].time;
    row.appendChild(cell1);
    row.appendChild(cell2);
    row.appendChild(cell3);
    row.appendChild(cell4);
    row.appendChild(cell5);
    row.appendChild(cell6);
    row.appendChild(cell7);
    tableBody.appendChild(row);
  }
}

function GetDataBySearch(index) {
    var pagram = index-10
    var start = document.getElementById('start').value
    var end = document.getElementById('end').value
    var select_search=document.getElementById("select-option-search").value
    var search = document.getElementById('search-input').value
    var select = document.getElementById('select-option').value

    var sort_type_list = document.getElementsByName('sort')
    var sort_type = "";

    for (var i = 0; i < sort_type_list.length; i++) {
      if (sort_type_list[i].checked) {
        sort_type = sort_type_list[i].value;
        break;
      }
    }
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=search&search_type=${select_search}&keyword=${search}&index=${pagram}&start=${start}&end=${end}&colume=${select}&sort_type=${sort_type}`)
        .then((response) => response.json())
        .then(function (response) {
            data2 = response
            displayData(currentPage+1);
            setupPagination();

        })
}

function GetALLData(index) {
    var pagram = index-10;
    var start = document.getElementById('start').value
    var end = document.getElementById('end').value
    var search = document.getElementById('search-input').value
    var select = document.getElementById('select-option').value

    var sort_type_list = document.getElementsByName('sort')
    var sort_type = "";
    for (var i = 0; i < sort_type_list.length; i++) {
        if (sort_type_list[i].checked) {
          sort_type = sort_type_list[i].value;
          break;
        }
    }
    fetch(`http://localhost:9999/Internet_of_things/Sensor_Data?action=getAllDataSensor`)
      .then((response) => response.json())
      .then(function (response) {
        data2 = response
        displayData(currentPage+1);
        setupPagination();

      })
}