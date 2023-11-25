/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.Device;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ADMIN
 */
public class DeviceDAO extends DBContext{
    public Device getDevice(){
        try {
            String sql = "SELECT TOP 1 * FROM Device ORDER BY id DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return new Device(rs.getInt("id"), rs.getString("led"), rs.getString("pump"), rs.getString("fan"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertDevice(String led, String pump, String fan){
        try {
            String sql = "INSERT INTO Device (led, pump, fan) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, led);
            ps.setString(2, pump);
            ps.setString(3, fan);
            ps.execute();
        } catch (Exception e) {
        }
    }
    public static void main(String[] args) {
        System.out.println(new DeviceDAO().getDevice().getFan());
    }
}
