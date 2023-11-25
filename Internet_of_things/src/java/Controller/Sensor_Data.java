package Controller;

import Dao.DeviceDAO;
import Dao.SensorDAO;
import Model.Schedule;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Model.Sensor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

@WebServlet(
   urlPatterns = {"/SenSor_Data"},
   loadOnStartup = 1
)

public class Sensor_Data extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MqttClient mqttClient;
    
    public String getDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

    public String DateConversionExample(String date) throws ParseException {
        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = inputFormat.parse(date);
        String outputDate = outputFormat.format(date2);
        System.out.println("Ng\u00e0y sau khi chuy\u1ec3n \u0111\u1ed5i: " + outputDate);
        return outputDate;
    }
    public void init() throws ServletException {
        super.init();
        System.out.print("Hello");
        String brokerUrl = "tcp://localhost:1883"; 
        String clientId = MqttClient.generateClientId();
        try {
           this.mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
           MqttConnectOptions connOpts = new MqttConnectOptions();
           this.mqttClient.connect(connOpts);
           
           this.mqttClient.setCallback(new CallBack_Sensor(this));
           this.mqttClient.subscribe("topic");
           this.mqttClient.subscribe("stateDevice");
//           this.mqttClient.connect(connOpts);
        } catch (MqttException e) {
           e.printStackTrace();
        }
    }
//    public void init() throws ServletException {
//        super.init();
//        String brokerUrl = "tcp://localhost:1883";
//        String clientId = "MyMqttSubscriber";
//
//        try {
//            this.mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
//            MqttConnectOptions connOpts = new MqttConnectOptions();
//            this.mqttClient.setCallback(new CallBack_Sensor(this));
//            this.mqttClient.connect(connOpts);
//
//            if (this.mqttClient.isConnected()) {
//                System.out.println("Kết nối MQTT thành công");
//
//                // Đăng ký callback và đăng ký chủ đề sau khi kết nối thành công
//                this.mqttClient.setCallback(new CallBack_Sensor(this));
//                this.mqttClient.subscribe("topic");
//                this.mqttClient.subscribe("stateDevice");
////                this.mqttClient.connect(connOpts);
//            } else {
//                System.out.println("Kết nối MQTT thất bại");
//                this.mqttClient.connect(connOpts);
//            }
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        String action = request.getParameter("action");
        if(action.equals("getData")){
            Gson gson = new Gson();
            String a = gson.toJson(new SensorDAO().getADataSensor());
            response.getWriter().write(a);
        }
        if(action.equals("getDataDevice")){
            Gson gson = new Gson();
            String a = gson.toJson(new DeviceDAO().getDevice());
            response.getWriter().write(a);
        }
        if(action.equals("postData")){
            float nhietdo = Float.parseFloat(request.getParameter("nhietdo"));
            float doam = Float.parseFloat(request.getParameter("doamkk"));
            float doambom = Float.parseFloat(request.getParameter("doambom"));
            float doamtat = Float.parseFloat(request.getParameter("doamtat"));
            
            try {
                String brokerUrl = "tcp://localhost:1883";
                String clientId = "MyMqttSubscriber";
                MqttClient mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
                mqttClient.connect();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("nhietdo", nhietdo);
                jsonObject.addProperty("doam", doam);
                jsonObject.addProperty("doambom", doambom);
                jsonObject.addProperty("doamtat", doamtat);

                String jsonString = jsonObject.toString();
                try {
                    MqttMessage message = new MqttMessage(jsonString.getBytes());
                    mqttClient.publish("postData", message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                
            } catch (MqttException var7) {
                var7.printStackTrace();
            }
            System.out.println(nhietdo);
        }
        if(action.equals("state")){
            String data = request.getParameter("data");
            try {
                String brokerUrl = "tcp://localhost:1883";
                String clientId = "MyMqttSubscriber";
                MqttClient mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
                mqttClient.connect();
                try {
                    String topic = "state";
                    MqttMessage message = new MqttMessage(data.getBytes());
                    message.setQos(0);
                    mqttClient.publish(topic, message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        if(action.equals("getAllDataSensor")){
            String page = request.getParameter("page");
            Gson gson = new Gson();
            String a = gson.toJson(new SensorDAO().getAll(Integer.parseInt(page), 10));
            response.getWriter().write(a);
        }
        if(action.equals("search")){
            String start = request.getParameter("start");
            String end = request.getParameter("end");
            String colume = request.getParameter("column");
            String sort_type = request.getParameter("sort_type");
            int currentIndex = Integer.parseInt(request.getParameter("index"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            String search_type = request.getParameter("search_type");
            String key = request.getParameter("keyword");
            System.out.print("abc");
            List<Sensor> list = new ArrayList();

            try {
                list = new SensorDAO().searchData(currentIndex, start, end, colume, sort_type, key, search_type);
            } catch (SQLException ex) {
                Logger.getLogger(Sensor_Data.class.getName()).log(Level.SEVERE, null, ex);
            }

            Gson gson = new Gson();
            String a = gson.toJson(list);
            response.getWriter().write(a);
        }
        if(action.equals("schedule")){
            List<Schedule> list = new ArrayList<>();
            list = new SensorDAO().getAllSchedule();
            Gson gson = new Gson();
            String a = gson.toJson(list);
            response.getWriter().write(a);
        }
        if(action.equals("updateState")){
            int state = Integer.parseInt(request.getParameter("dulieu"));
            int idSchedule = Integer.parseInt(request.getParameter("id"));
            new SensorDAO().updateState(idSchedule, state);
        }
        // 
        if(action.equals("addSchedule")){
            String device = request.getParameter("device");
            String time_on = this.convertDateTimeFormat(request.getParameter("time"));
            String time_off = this.convertDateTimeFormat(request.getParameter("time_off"));
            new SensorDAO().insertSchedule(device, time_on, time_off);
        }
        //
        if(action.equals("deleteSchedule")){
            int id = Integer.parseInt(request.getParameter("id"));
            new SensorDAO().deleteSchedule(id);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//       this.doGet(request, response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        String control_device = request.getParameter("data");
        try {
                String brokerUrl = "tcp://localhost:1883";
                String clientId = "MyMqttSubscriber";
                MqttClient mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
                mqttClient.connect();
                try {
                    String topic = "control";
                    MqttMessage message = new MqttMessage(control_device.getBytes());
                    message.setQos(0);
                    mqttClient.publish(topic, message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//       new SensorDAO().insertSchedule("led", new Sensor_Data().getDateTime());
//    }
    public String convertDateTimeFormat(String datetimeString) {
        LocalDateTime datetime = LocalDateTime.parse(datetimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return datetime.format(formatter);
    }

//    public static void main(String[] args) {
//    }
}
