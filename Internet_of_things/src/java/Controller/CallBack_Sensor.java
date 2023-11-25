// Source code is decompiled from a .class file using FernFlower decompiler.
package Controller;

import Dao.DeviceDAO;
import Dao.SensorDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
//import 
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class CallBack_Sensor implements MqttCallback{

    private final Sensor_Data test;
    
    CallBack_Sensor(Sensor_Data var1) {
      this.test = var1;
    }

    
    
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("K\u1ebft n\u1ed1i MQTT \u0111\u00e3 m\u1ea5t");
//        try {
//            test.init();
//        } catch (ServletException ex) {
//            Logger.getLogger(CallBack_Sensor.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    try {
        System.out.println("Nhận được dữ liệu MQTT từ chủ đề: " + topic);
        System.out.println("Nội dung: " + new String(message.getPayload()));

        String result = new String(message.getPayload());
        System.out.println(result);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
        // Gía tri nhiet do, do am, am dat
        if(topic.equals("topic")){
            if (jsonObject.has("temp") && jsonObject.has("humi") && jsonObject.has("soil")) {
                float temp = jsonObject.get("temp").getAsFloat();
                float humi = jsonObject.get("humi").getAsFloat();
                float soil = jsonObject.get("soil").getAsFloat();
                String time = test.getDateTime();
                if(Float.isNaN(temp) && Float.isNaN(humi)){
                    
                }
                else{
                    try {
                        new SensorDAO().insertData(temp, humi, soil, time);
                    } finally {
                        // Đóng tài nguyên nếu cần thiết
                    }
                }
            } else {
                System.out.println("Dữ liệu JSON không hợp lệ");
            }
        }
        // Trang thai thiet bi
        else if (topic.equals("stateDevice")) {
            if (jsonObject.has("led") && jsonObject.has("pump") && jsonObject.has("fan")) {
                String led = jsonObject.get("led").getAsString();
                String pump = jsonObject.get("pump").getAsString();
                String fan = jsonObject.get("fan").getAsString();

                try {
                    new DeviceDAO().insertDevice(led, pump, fan);
                } finally {
                    // Đóng tài nguyên nếu cần thiết
                }
            
            } else {
                System.out.println("Dữ liệu JSON không hợp lệ");
            }
        }
        } catch (JsonSyntaxException ex) {
            System.err.println("Lỗi xử lý JSON: " + ex.getMessage());
        }
        catch (Throwable ex) {
            Logger.getLogger(CallBack_Sensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
   public void deliveryComplete(IMqttDeliveryToken token) {
   }
}
