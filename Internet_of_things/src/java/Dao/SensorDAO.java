
package Dao;

import Dao.DBContext;
import static Dao.DBContext.con;
import Model.Schedule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import Model.Sensor;

public class SensorDAO extends DBContext{
    public SensorDAO() {
    }
    
    public void insertSchedule(String device, String time, String time_off){
        String sql = "INSERT INTO schedule (device, time, time_off, state) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, device);
            ps.setString(2, time);
            ps.setString(3, time_off);
            ps.setInt(4, 0);
            ps.execute();
        } catch (Exception e) {
        }
    }
    
    public void updateState(int id, int state){
        String sql = "UPDATE schedule set state = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, id);
            ps.execute();
        } catch (Exception e) {
        }
    }
    
    public ArrayList<Schedule> getAllSchedule(){
        ArrayList<Schedule> a = new ArrayList<>();
        String sql = "SELECT * FROM schedule where state = 1 OR STATE = 0 ORDER BY time ASC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                a.add(new Schedule(rs.getInt("id"), rs.getString("device"), rs.getString("time"), rs.getString("time_off"), rs.getInt("state")));
            return a;
        } catch (Exception e) {
        }
        return a;
    }
    
    public void deleteSchedule(int id){
        String sql = "DELETE FROM schedule WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
        }
    }
    
    public boolean checkLogin(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void insertData(float temp, float humi, float soil, String time) throws SQLException, Throwable {
        String sql = "INSERT INTO Sensor (temp, humi, soil, time) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setFloat(1, temp);
            ps.setFloat(2, humi);
            ps.setFloat(3, soil);
            ps.setString(4, time);
            ps.execute();
        } catch (Exception e) {
        }
    }
    
    public List<Sensor> searchData(int index, String start, String end, String column, String sort_type, String keyword, String search_type) throws SQLException {
        List<Sensor> list = new ArrayList();
        String sql = "SELECT * FROM Sensor WHERE " + search_type + " LIKE ? AND time BETWEEN ? AND ? ORDER BY " + column + " " + sort_type + " OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY;";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, start);
            ps.setString(3, end);
            ps.setInt(4, index);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                Float temp = resultSet.getFloat("temp");
                Float humi = resultSet.getFloat("humi");
                Float soil = resultSet.getFloat("soil");
                Timestamp time = resultSet.getTimestamp("time");
                String time_string = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(time);
                list.add(new Sensor(id, temp, humi, soil, time_string));
            }
        } catch (Exception e) {
        }
      return list;
   }
    public List<Sensor> getAllData(int index, String start, String end, String column, String sort_type) throws SQLException {
        List<Sensor> list = new ArrayList();
        String sql = "SELECT * FROM sensor WHERE time BETWEEN ? AND ?\n ORDER BY " + column + " " + sort_type + " OFFSET " + index + " ROWS FETCH NEXT 10 ROWS ONLY";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, start);
        preparedStatement.setString(2, end);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
           int id = resultSet.getInt("id");
           Float temp = resultSet.getFloat("temp");
           Float humi = resultSet.getFloat("humi");
           Float soil = resultSet.getFloat("soil");
           Timestamp time = resultSet.getTimestamp("time");
           String time_string = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(time);
           list.add(new Sensor(id, temp, humi, soil, time_string));
        }
        return list;
    }

    public ArrayList<Sensor> getAll(){
        ArrayList<Sensor> a = new ArrayList<>();
        String sql = "SELECT * FROM Sensor";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                a.add(new Sensor(rs.getInt("id"), rs.getFloat("temp"), rs.getFloat("humi"), rs.getFloat("soil"), rs.getString("time")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
    
    public ArrayList<Sensor> getAll(int page, int limit) {
        ArrayList<Sensor> result = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY id) AS RowNum, * FROM Sensor) AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        int offset = (page - 1) * limit;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, offset + 1);
            ps.setInt(2, offset + limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new Sensor(rs.getInt("id"), rs.getFloat("temp"), rs.getFloat("humi"), rs.getFloat("soil"), rs.getString("time")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public Sensor getADataSensor(){
        String sql = "SELECT TOP 1 * FROM Sensor ORDER BY id DESC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Sensor(rs.getInt("id"), rs.getFloat("temp"), rs.getFloat("humi"), rs.getFloat("soil"), rs.getString("time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    public static void main(String[] args) throws Throwable {
//        for(Sensor x : new SensorDAO().searchData(1, "2023-11-16", "2023-11-23", "id", "DESC" , "2", "temp"))
//            System.out.println(x.getId());
//    }
}
