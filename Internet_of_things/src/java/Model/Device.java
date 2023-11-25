/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Device {
    private int id;
    private String led, pump, fan;

    public Device(int id, String led, String pump, String fan) {
        this.id = id;
        this.led = led;
        this.pump = pump;
        this.fan = fan;
    }

    public Device() {
    }
    
    public int getId() {
        return id;
    }

    public String getLed() {
        return led;
    }

    public String getPump() {
        return pump;
    }

    public String getFan() {
        return fan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLed(String led) {
        this.led = led;
    }

    public void setPump(String pump) {
        this.pump = pump;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }
    
    
}
