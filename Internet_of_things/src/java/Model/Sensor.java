/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Sensor {
    private int id;
    private float temp;
    private float humi;
    private float soil;
    private String time;

    public Sensor(int id, float temp, float humi, float soil, String time) {
        this.id = id;
        this.temp = temp;
        this.humi = humi;
        this.soil = soil;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setHumi(float humi) {
        this.humi = humi;
    }

    public void setSoil(float soil) {
        this.soil = soil;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public float getTemp() {
        return temp;
    }

    public float getHumi() {
        return humi;
    }

    public float getSoil() {
        return soil;
    }

    public String getTime() {
        return time;
    }
    
    
}
