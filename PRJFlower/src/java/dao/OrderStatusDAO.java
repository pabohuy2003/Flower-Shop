/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.SQLServerConnection;
import entity.OrderStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class OrderStatusDAO {
        public List<OrderStatus> getAll() {

        String sql = "Select * from OrderStatus";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            List<OrderStatus> list = new ArrayList<>();
            while (rs.next()) {
                OrderStatus s = OrderStatus.builder()
                        .orderStatusId(rs.getInt("orderStatusId"))
                        .orderStatusName(rs.getString("orderStatusName"))
                        .build();
                list.add(s);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
