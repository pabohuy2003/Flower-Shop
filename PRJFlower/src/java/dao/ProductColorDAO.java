/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.SQLServerConnection;
import entity.Color;
import entity.ProductColor;
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
public class ProductColorDAO {

    public List<ProductColor> getAll(int productId, boolean needActive) {

        String sql = ""
                + "select \n"
                + "	ps.productColorId,\n"
                + "	ps.productId,\n"
                + "	ps.active,\n"
                + "	s.colorId,\n"
                + "	s.colorValue \n"
                + "from ProductColor ps \n"
                + "	join Color s On ps.colorId = s.colorId where ps.productId = ? ";//
        sql += needActive ? " And ps.active = 1" : "";
        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, productId);
            ResultSet rs = ps.executeQuery();

            List<ProductColor> list = new ArrayList<>();//
            while (rs.next()) {
                ProductColor s = ProductColor.builder()
                        .productColorId(rs.getInt("productColorId"))
                        .productId(rs.getInt("productId"))
                        .color(Color.builder()
                                .colorId(rs.getInt("colorId"))
                                .colorValue(rs.getString("colorValue"))
                                .build())
                        .active(rs.getBoolean("active"))
                        .build();
                list.add(s);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public boolean updateActive(String[] productColorIds, int productId) {
        int check = 0;
        String sql = "UPDATE ProductColor SET active = 0 WHERE active = 1 AND productId = " + productId;
        if (productColorIds != null) {
            for (String productColorId : productColorIds) {
                sql += "\nUPDATE ProductColor SET active = 1 WHERE productColorId = " + productColorId;
            }
        }

        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);) {
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public static void main(String[] args) {
        System.out.println(new ProductColorDAO().getAll(114, true));
    }

    public boolean add(int productId, int colorId) {
        int check = 0;
        String sql = "INSERT INTO ProductColor(productId, colorId, active)"
                + " VALUES(?, ?, ?)";
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setObject(1, productId);
            ps.setObject(2, colorId);
            ps.setObject(3, 0);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
}
