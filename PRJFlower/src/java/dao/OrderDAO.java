/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.SQLServerConnection;
import entity.Account;
import entity.Cart;
import entity.Order;
import entity.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class OrderDAO {

    public List<Order> getAllByOrderStatusIdForUser(int accountId, int orderStatusId) {
        String sql = "select * from [order] o join account a ON o.accountId = a.accountId where a.accountId = ? And o.orderStatusId = ?";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, accountId);
            ps.setObject(2, orderStatusId);
            ResultSet rs = ps.executeQuery();
            List<Order> list = new ArrayList<>();//
            while (rs.next()) {
                Order obj = Order.builder()
                        .orderId(rs.getInt("orderId"))
                        .account(Account.builder()
                                .accountId(rs.getInt("accountId"))
                                .accountEmail(rs.getString("accountEmail"))
                                .accountPassword(rs.getString("accountPassword"))
                                .accountDeleted(rs.getBoolean("accountDeleted"))
                                .build())
                        .orderCreateDate(rs.getDate("orderCreateDate"))
                        .orderExpectedDate(rs.getDate("orderExpectedDate"))
                        .orderDoneDate(rs.getDate("orderDoneDate"))
                        .orderNameContact(rs.getString("orderNameContact"))
                        .orderPhoneContact(rs.getString("orderPhoneContact"))
                        .orderAddressContact(rs.getString("orderAddressContact"))
                        .orderStatusId(rs.getInt("orderStatusId"))
                        .build();
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Order> getAllByOrderStatusIdForAdmin(int pageCur, int numberOrderPerPage, int orderStatusId) {
        String sql = "select * from [order] o join account a ON o.accountId = a.accountId WHERE o.orderStatusId = ?\n"
                + "ORDER BY o.orderId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY; ";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, orderStatusId);
            ps.setObject(2, (pageCur - 1) * numberOrderPerPage);
            ps.setObject(3, numberOrderPerPage);
            ResultSet rs = ps.executeQuery();
            List<Order> list = new ArrayList<>();//
            while (rs.next()) {
                Order obj = Order.builder()
                        .orderId(rs.getInt("orderId"))
                        .account(Account.builder()
                                .accountId(rs.getInt("accountId"))
                                .accountEmail(rs.getString("accountEmail"))
                                .accountPassword(rs.getString("accountPassword"))
                                .accountDeleted(rs.getBoolean("accountDeleted"))
                                .build())
                        .orderCreateDate(rs.getDate("orderCreateDate"))
                        .orderExpectedDate(rs.getDate("orderExpectedDate"))
                        .orderDoneDate(rs.getDate("orderDoneDate"))
                        .orderNameContact(rs.getString("orderNameContact"))
                        .orderPhoneContact(rs.getString("orderPhoneContact"))
                        .orderAddressContact(rs.getString("orderAddressContact"))
                        .orderStatusId(rs.getInt("orderStatusId"))
                        .build();
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public int getSizeByOrderStatusIdForAdmin(int orderStatusId) {

        String sql = ""
                + "Select count (orderId) as total FROM \n"
                + "(\n"
                + "	select orderId from [order] o join account a ON o.accountId = a.accountId WHERE o.orderStatusId = ?\n"
                + ") as a;";
        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, orderStatusId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    public int add(Order obj) {
        int check = 0;
        String sql = "INSERT INTO [Order](accountId, orderCreateDate, orderExpectedDate, orderNameContact, orderPhoneContact, orderAddressContact, orderStatusId)"
                + " VALUES(?, GETDATE(), GETDATE() + 5, ?, ?, ?, 1)";
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = (con != null) ? con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : null;) {
            ps.setObject(1, obj.getAccount().getAccountId());
            ps.setObject(2, obj.getOrderNameContact());
            ps.setObject(3, obj.getOrderPhoneContact());
            ps.setObject(4, obj.getOrderAddressContact());

            check = ps.executeUpdate();
            if (check > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    public boolean update(int orderId, int orderStatusId) {
        int check = 0;
        String sql = orderStatusId == 3
                ? "UPDATE [Order] SET orderDoneDate = GETDATE(), orderStatusId = ? WHERE orderId = ?"
                : "UPDATE [Order] SET orderStatusId = ? WHERE orderId = ?";

        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setObject(1, orderStatusId);
            ps.setObject(2, orderId);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(int id) {
        int check = 0;
        String sql = "DELETE FROM [Order] Where OrderId = ?";

        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public String richestUser() {
        String sql = "SELECT TOP 1 SUM(orderDetailPriceProduct * OrderDetailQuantity) as totalBill, acc.accountDetailName\n"
                + "FROM [Order] o \n"
                + "JOIN [OrderDetail] od\n"
                + "on o.orderId=od.orderId \n"
                + "JOIN [AccountDetail] acc ON o.accountId = acc.accountId\n"
                + "WHERE o.orderStatusId=3\n"
                + "GROUP BY o.accountId,acc.accountDetailName\n"
                + "ORDER BY totalBill DESC";
        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("totalBill") + "|" + rs.getString("accountDetailName");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
      public int totalYear(int tyYear) {
        
        String sql = "SELECT SUM(od.orderDetailPriceProduct * od.orderDetailQuantity) AS totalAmount\n"
                + "FROM OrderDetail od \n"
                + "JOIN [Order] o ON od.orderId = o.orderId\n"
                + "WHERE o.orderStatusId = 3 AND YEAR(o.orderDoneDate) = ? \n"
                + "GROUP BY YEAR(o.orderDoneDate);";
        
        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, tyYear);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }
    
    public int totalMonth(int tmMonth, int tmYear) {
        
        String sql = "SELECT SUM(od.orderDetailPriceProduct * od.orderDetailQuantity) AS totalAmount\n"
                + "FROM OrderDetail od \n"
                + "JOIN [Order] o ON od.orderId = o.orderId\n"
                + "WHERE o.orderStatusId = 3 AND MONTH(o.orderDoneDate) = ? AND YEAR(o.orderDoneDate) = ?\n"
                + "GROUP BY MONTH(o.orderDoneDate);";
        
        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, tmMonth);
            ps.setObject(2, tmYear);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(new OrderDAO().getSizeByOrderStatusIdForAdmin(1));
        System.out.println(new OrderDAO().richestUser());
    }
}
