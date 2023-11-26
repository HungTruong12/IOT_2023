#define BLYNK_TEMPLATE_ID "TMPL6Pu1KWRaH"
#define BLYNK_TEMPLATE_NAME "IOT"
#define BLYNK_AUTH_TOKEN "TAQB7qJdGjVvZJffhMBcPFF7i5W8KZfC"

#include <DHT.h>
#include <Adafruit_Sensor.h>
#include <DHT_U.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>
#include <BlynkSimpleEsp8266.h>

// Khai báo thông số của cảm biến DHT11
#define DHTPIN 4   // Chân dữ liệu của DHT11 được kết nối với chân D5 của ESP8266
#define DHTTYPE DHT11    // Loại cảm biến (DHT11 hoặc DHT22)
DHT_Unified dht(DHTPIN, DHTTYPE);

int relayBom = D7;    // relay Bơm
int relayQuat = 5;    // relay quạt
int den = D5;   // Chân led
int nutnhan = 12;  // Nút bật đèn
int nutbatquat = D3;
int nutbatbom = D4;
int state = 0;  // trạng thái thủ công hoặc tự động
int doam_bomtuoi = 20;
int doam_tatbomtuoi = 40;

int nhietdo_batquat = 23;
// Khai báo chân kết nối cảm biến quang trở (ví dụ: chân A0)
int cb = A0;      //Chân cảm biến ở chân Analog: A0
int doc_cb, TBcb;

// Thiết lập WiFi
const char* ssid = "P302";
const char* password = "234567890";
char auth[] = BLYNK_AUTH_TOKEN;

// Thiết lập thông tin máy chủ MQTT
const char* mqtt_server = "192.168.32.103";  // Địa chỉ IP hoặc tên miền của máy chủ MQTT
const int mqtt_port = 1883;              // Cổng MQTT

WiFiClient espClient;
PubSubClient client(espClient);


void callback(char* topic, byte* payload, unsigned int length) {
  String message = "";
  String topicStr = String(topic);
  Serial.println(topic);
  if(strcmp(topic, "postData") == 0){
    DynamicJsonDocument doc(256);
    deserializeJson(doc, payload, length);
    String jsonString;
    serializeJson(doc, jsonString);

    // In ra chuỗi JSON
    Serial.println(jsonString);

    nhietdo_batquat = doc["nhietdo"];
    doam_bomtuoi = doc["doambom"];
    doam_tatbomtuoi = doc["doamtat"];
    // state = 1;
  }
  else if(strcmp(topic, "control") == 0){
    for (int i = 0; i < length; i++) {
      message += (char)payload[i];
    }
    Serial.println(message);
    // điều khiển quạt
    if (message == "on1") {
      digitalWrite(relayQuat, LOW);
    } else if (message == "off1") {
      digitalWrite(relayQuat, HIGH); 
    }
    // Điều khiển bơm
    if (message == "on2") {
      digitalWrite(relayBom, LOW); // Bật đèn
    } else if (message == "off2") {
      digitalWrite(relayBom, HIGH); // Tắt đèn
    }
    // điều khiển đèn
    if (message == "on3") {
      digitalWrite(den, HIGH); // Bật đèn
    } else if (message == "off3") {
      digitalWrite(den, LOW); // Tắt đèn
    }
    
    // bật tất cả
    if (message == "on4") {
      digitalWrite(den, HIGH); // Bật đèn
      digitalWrite(relayQuat, LOW);
      digitalWrite(relayBom, LOW);
    } 
    // Tắt tất cả
    if (message == "off4") {
      digitalWrite(den, LOW); // Tắt đèn
      digitalWrite(relayQuat, HIGH);
      digitalWrite(relayBom, HIGH);
    }
    
  }
  else if(strcmp(topic, "state") == 0){
    for (int i = 0; i < length; i++) {
      message += (char)payload[i];
    }
    Serial.print(message);
    if(message == "auto")
      state = 1;
    if(message == "manual")
      state = 0;
  }                           
}

void setup() {
  Serial.begin(115200);
  delay(10);

  // Kết nối với mạng WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Kết nối đến mạng WiFi...");
  }
  Serial.println("Đã kết nối đến mạng WiFi");

  // Kết nối đến máy chủ MQTT
  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);  // Đặt hàm callback để xử lý tin nhắn từ máy chủ MQTT

  // Kết nối cảm biến DHT11
  pinMode(relayBom, OUTPUT);    //Tín hiệu xuất ra từ relay
  pinMode(relayQuat, OUTPUT);
  pinMode(den, OUTPUT);

  digitalWrite(relayBom, HIGH);
  digitalWrite(relayQuat,HIGH);
  digitalWrite(den, LOW);
  // mới thêm vào
  dht.begin();
  Blynk.begin(auth, ssid, password);
  //
  reconnect();
}

unsigned long DHTdelay = 3000;
unsigned long timer = 0;
void loop() {
  client.loop();
  // Đọc nhiệt độ và độ ẩm từ cảm biến DHT11 và độ ẩm đất
  unsigned long currentTime = millis();
  if (currentTime - timer >= DHTdelay) {
    timer = currentTime;

    // Đọc ánh sáng từ cảm biến quang trở
    // for(int i=0;i<=9;i++)   /*Chúng ta sẽ tạo một hàm for để đọc 10 lần giá trị cảm biến, 
    //                         sau đó lấy giá trị trung bình để được giá trị chính xác nhất.*/
    //     {
    //       doc_cb += analogRead(cb);     
    //     }
    // TBcb=doc_cb/10;     //Tính giá trị trung bình
    TBcb = analogRead(cb);  
    sensors_event_t event;
    dht.temperature().getEvent(&event);
    float temperature = event.temperature;
    dht.humidity().getEvent(&event);
    float humidity = event.relative_humidity;

    float phantramao = map(TBcb, 0, 1023, 0, 100);    //Chuyển giá trị Analog thành giá trị %
    float phantramthuc = 100 - phantramao; 

    
    if (isnan(temperature) || isnan(humidity)) {
        Serial.println("Failed to read from DHT sensor!");
        // return;
    }
    // Làm tròn các độ ẩm
      float nhietdo = round(temperature * 10) / 10.0; // Làm tròn temperature với 1 chữ số thập phân
      float doam = round(humidity * 10) / 10.0; // Làm tròn humidity với 1 chữ số thập phân
      float doamdat = round(phantramthuc * 10) / 10.0; // Làm tròn soil với 1 chữ số thập phân
      delay (1000);

      // đọc lên blynk
      Blynk.virtualWrite(V5, nhietdo);
      Blynk.virtualWrite(V6, doam);
      Blynk.virtualWrite(V7, doamdat);


      Serial.print("Nhiệt độ: ");
      Serial.println(temperature);
      Serial.print("Độ ẩm: ");
      Serial.println(humidity);
      Serial.print("Độ ẩm đất: ");
      Serial.println(phantramthuc);
      Serial.println("\n");

      // Trạng thái thủ công
      if(state == 0){
      }
      else{
        // Bật quạt tự động
        if(nhietdo > nhietdo_batquat) digitalWrite(relayQuat, LOW);
        else digitalWrite(relayQuat, HIGH);
        // Bật nước tự động
        if(doamdat < doam_bomtuoi){
          digitalWrite(relayBom, LOW);
          digitalWrite(den, HIGH);
          Serial.println("Bật bơm tưới!");
        }
        else{
          if(doamdat > doam_tatbomtuoi){
            digitalWrite(relayBom, HIGH);
            digitalWrite(den, LOW);
            Serial.println("Tắt bơm tưới!");
          }
        }
      }


      // Kiểm tra kết nối với máy chủ MQTT
      if (!client.connected()) {
        reconnect();
      }
      StaticJsonDocument<200> jsonDoc;
      jsonDoc["temp"] = nhietdo;
      jsonDoc["humi"] = doam;
      jsonDoc["soil"] = doamdat;

      char jsonBuffer[200];
      serializeJson(jsonDoc, jsonBuffer);

      // Gửi chuỗi JSON lên MQTT topic
      client.publish("topic", jsonBuffer);

      if (!client.connected()) {
        reconnect();
      }
      StaticJsonDocument<200> json;
      if(digitalRead(den) == HIGH) json["led"] = "ON";
      else json["led"] = "OFF";
      if(digitalRead(relayBom) == LOW) json["pump"] = "ON";
      else json["pump"] = "OFF";
      if(digitalRead(relayQuat) == LOW) json["fan"] = "ON";
      else json["fan"] = "OFF";

      char jsonBuffer2[200];
      serializeJson(json, jsonBuffer2);
      client.publish("stateDevice", jsonBuffer2);
    // }
    
    doc_cb=0;
    // delay(5000);
  }
}

void reconnect() {
  while (!client.connected()) {
    Serial.println("Kết nối đến máy chủ MQTT...");
    if (client.connect("ESP8266Client")) {
      Serial.println("Đã kết nối đến máy chủ MQTT");
      client.subscribe("control");
      client.subscribe("postData");
      client.subscribe("state");
    } else {
      Serial.println("Kết nối lại sau 5 giây...");
      delay(5000);
    }
  }

}
