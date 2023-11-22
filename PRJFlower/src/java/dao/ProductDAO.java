/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.SQLServerConnection;
import entity.Product;
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
public class ProductDAO {

    public int getSize(String[] colorIds, String priceFrom, String priceTo, boolean needActive) {

        String sql = ""
                + "Select Count (productId) as total FROM (\n"
                + "	Select Distinct\n"
                + "		p.productId,\n"
                + "		p.productName,\n"
                + "		p.productImg,\n"
                + "		p.productPrice,\n"
                + "		p.productDescription,\n"
                + "		p.categoryId,\n"
                + "		p.productIsFeatured,\n"
                + "		p.productIsRecent,\n"
                + "		p.productDeleted \n"
                + "	from Product p \n"
                + "		Join ProductColor ps ON p.productId = ps.productId\n";
        if (needActive) {
            sql += "Where ps.active = 1\n";
        }
        if (priceFrom != null && priceTo != null) {
            sql += " And p.productPrice between " + priceFrom + " and " + priceTo;
        }
        if (colorIds != null) {
            sql += " And (";
            for (int i = 0; i < colorIds.length - 1; i++) {
                sql += " ps.colorId = " + colorIds[i] + " OR ";
            }
            sql += " ps.colorId = " + colorIds[colorIds.length - 1] + ") ";
        }
        sql += ") as a";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    public List<Product> getAllPerPage(int pageCur, int numberProductPerPage, String[] colorIds, String priceFrom, String priceTo, boolean needActive) {

        String sql = "Select Distinct\n"
                + "	p.productId,\n"
                + "	p.productName,\n"
                + "	p.productImg,\n"
                + "	p.productPrice,\n"
                + "	p.productDescription,\n"
                + "	p.categoryId,\n"
                + "	p.productIsFeatured,\n"
                + "	p.productIsRecent,\n"
                + "	p.productDeleted \n"
                + "from Product p \n"
                + "		Join ProductColor ps ON p.productId = ps.productId\n";
        if (needActive) {
            sql += "Where ps.active = 1\n";
        }
        if (priceFrom != null && priceTo != null) {
            sql += " And p.productPrice between " + priceFrom + " and " + priceTo;
        }
        if (colorIds != null) {
            sql += " And (";
            for (int i = 0; i < colorIds.length - 1; i++) {
                sql += " ps.colorId = " + colorIds[i] + " OR ";
            }
            sql += " ps.colorId = " + colorIds[colorIds.length - 1] + ") ";
        }
        sql += " ORDER BY p.productId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY; ";//
        System.out.println(sql);
        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, (pageCur - 1) * numberProductPerPage);
            ps.setObject(2, numberProductPerPage);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();

            List<Product> list = new ArrayList<>();//
            while (rs.next()) {
                Product p = Product.builder()
                        .productId(rs.getInt("productId"))
                        .productName(rs.getString("productName"))
                        .productImg(rs.getString("productImg"))
                        .productPrice(rs.getInt("productPrice"))
                        .productDescription(rs.getString("productDescription"))
                        .categoryId(rs.getInt("categoryId"))
                        .productIsFeatured(rs.getBoolean("productIsFeatured"))
                        .productIsRecent(rs.getBoolean("productIsRecent"))
                        .productDeleted(rs.getBoolean("productDeleted"))
                        .build();
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public int getSizeByCategoryId(int categoryId) {

        String sql = "SELECT COUNT(productId) FROM Product WHERE categoryId = ? ";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    public List<Product> getAllPerPageByCategoryId(int pageCur, int numberProductPerPage, int categoryId) {

        String sql = "SELECT * FROM Product WHERE categoryId = ? ORDER BY productId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY; ";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, categoryId);
            ps.setObject(2, (pageCur - 1) * numberProductPerPage);
            ps.setObject(3, numberProductPerPage);
            ResultSet rs = ps.executeQuery();

            List<Product> list = new ArrayList<>();//
            while (rs.next()) {
                Product p = Product.builder()
                        .productId(rs.getInt("productId"))
                        .productName(rs.getString("productName"))
                        .productImg(rs.getString("productImg"))
                        .productPrice(rs.getInt("productPrice"))
                        .productDescription(rs.getString("productDescription"))
                        .categoryId(rs.getInt("categoryId"))
                        .productIsFeatured(rs.getBoolean("productIsFeatured"))
                        .productIsRecent(rs.getBoolean("productIsRecent"))
                        .productDeleted(rs.getBoolean("productDeleted"))
                        .build();
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public int getSizeBySearchValue(String searchValue) {

        String sql = "SELECT COUNT(p.productId) from Product p JOIN Category c ON p.categoryId = c.categoryId WHERE p.productName like ? OR c.categoryName Like ?";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, "%" + searchValue + "%");
            ps.setObject(2, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    public List<Product> getAllPerPageBySearchValue(int pageCur, int numberProductPerPage, String searchValue) {

        String sql = "SELECT * from Product p JOIN Category c ON p.categoryId = c.categoryId WHERE p.productName like ? OR c.categoryName Like ? ORDER BY p.productId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY; ";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, "%" + searchValue + "%");
            ps.setObject(2, "%" + searchValue + "%");
            ps.setObject(3, (pageCur - 1) * numberProductPerPage);
            ps.setObject(4, numberProductPerPage);
            ResultSet rs = ps.executeQuery();

            List<Product> list = new ArrayList<>();//
            while (rs.next()) {
                Product p = Product.builder()
                        .productId(rs.getInt("productId"))
                        .productName(rs.getString("productName"))
                        .productImg(rs.getString("productImg"))
                        .productPrice(rs.getInt("productPrice"))
                        .productDescription(rs.getString("productDescription"))
                        .categoryId(rs.getInt("categoryId"))
                        .productIsFeatured(rs.getBoolean("productIsFeatured"))
                        .productIsRecent(rs.getBoolean("productIsRecent"))
                        .productDeleted(rs.getBoolean("productDeleted"))
                        .build();
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Product> getAllByRecent() {

        String sql = "SELECT * FROM Product WHERE productIsRecent = 1";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            List<Product> list = new ArrayList<>();//
            while (rs.next()) {
                Product p = Product.builder()
                        .productId(rs.getInt("productId"))
                        .productName(rs.getString("productName"))
                        .productImg(rs.getString("productImg"))
                        .productPrice(rs.getInt("productPrice"))
                        .productDescription(rs.getString("productDescription"))
                        .categoryId(rs.getInt("categoryId"))
                        .productIsFeatured(rs.getBoolean("productIsFeatured"))
                        .productIsRecent(rs.getBoolean("productIsRecent"))
                        .productDeleted(rs.getBoolean("productDeleted"))
                        .build();
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Product> getAllByFeatured() {

        String sql = "SELECT * FROM Product WHERE productIsFeatured = 1";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            List<Product> list = new ArrayList<>();//
            while (rs.next()) {
                Product p = Product.builder()
                        .productId(rs.getInt("productId"))
                        .productName(rs.getString("productName"))
                        .productImg(rs.getString("productImg"))
                        .productPrice(rs.getInt("productPrice"))
                        .productDescription(rs.getString("productDescription"))
                        .categoryId(rs.getInt("categoryId"))
                        .productIsFeatured(rs.getBoolean("productIsFeatured"))
                        .productIsRecent(rs.getBoolean("productIsRecent"))
                        .productDeleted(rs.getBoolean("productDeleted"))
                        .build();
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public Product getOne(int productId) {

        String sql = "Select * From Product Where productId = ?";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product s = Product.builder()
                        .productId(rs.getInt("productId"))
                        .productName(rs.getString("productName"))
                        .productImg(rs.getString("productImg"))
                        .productPrice(rs.getInt("productPrice"))
                        .productDescription(rs.getString("productDescription"))
                        .categoryId(rs.getInt("categoryId"))
                        .productIsFeatured(rs.getBoolean("productIsFeatured"))
                        .productIsRecent(rs.getBoolean("productIsRecent"))
                        .productDeleted(rs.getBoolean("productDeleted"))
                        .build();
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<Product> getRandByCategoryId(int numberProduct, int categoryId, int productId) {

        String sql = "SELECT TOP " + numberProduct + " * FROM product where categoryId = ? AND productId != ? ORDER BY NEWID()";//

        try ( Connection connection = SQLServerConnection.getConnection();  PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, categoryId);
            ps.setObject(2, productId);
            ResultSet rs = ps.executeQuery();

            List<Product> list = new ArrayList<>();//
            while (rs.next()) {
                Product p = Product.builder()
                        .productId(rs.getInt("productId"))
                        .productName(rs.getString("productName"))
                        .productImg(rs.getString("productImg"))
                        .productPrice(rs.getInt("productPrice"))
                        .productDescription(rs.getString("productDescription"))
                        .categoryId(rs.getInt("categoryId"))
                        .productIsFeatured(rs.getBoolean("productIsFeatured"))
                        .productIsRecent(rs.getBoolean("productIsRecent"))
                        .productDeleted(rs.getBoolean("productDeleted"))
                        .build();
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public boolean update(Product obj, int productId) {
        int check = 0;
        String sql = "UPDATE Product SET productName = ?, categoryId = ?, productPrice = ?, productIsFeatured = ?, productIsRecent = ?, productDescription = ? WHERE productId = ?";

        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setObject(1, obj.getProductName());
            ps.setObject(2, obj.getCategoryId());
            ps.setObject(3, obj.getProductPrice());
            ps.setObject(4, obj.isProductIsFeatured());
            ps.setObject(5, obj.isProductIsRecent());
            ps.setObject(6, obj.getProductDescription());
            ps.setObject(7, productId);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public int add(Product obj) {
        int check = 0;
        String sql = "INSERT [dbo].[Product] ([productName], [productImg], [productPrice], [productDescription], [categoryId], [productIsFeatured], [productIsRecent], [productDeleted])"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try ( Connection con = SQLServerConnection.getConnection();  PreparedStatement ps = (con != null) ? con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : null;) {
            ps.setObject(1, obj.getProductName());
            ps.setObject(2, obj.getProductImg());
            ps.setObject(3, obj.getProductPrice());
            ps.setObject(4, obj.getProductDescription());
            ps.setObject(5, obj.getCategoryId());
            ps.setObject(6, obj.isProductIsFeatured());
            ps.setObject(7, obj.isProductIsRecent());
            ps.setObject(8, obj.isProductDeleted());

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

    public static void main(String[] args) {
    }
}
