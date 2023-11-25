/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Schedule {
    private int id;
    private String device, time, time_off;
    private int state;

    public Schedule(int id, String device, String time, String time_off, int state) {
        this.id = id;
        this.device = device;
        this.time = time;
        this.time_off = time_off;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    public String getDevice() {
        return device;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_off() {
        return time_off;
    }

    public void setTime_off(String time_off) {
        this.time_off = time_off;
    }
    
}
