/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ColorDAO;
import dao.ProductColorDAO;
import dao.ProductDAO;
import entity.Category;
import entity.Color;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import utils.Helper;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ProductManagementController", urlPatterns = {"/product-management"})
public class ProductManagementController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductManagementController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductManagementController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Properties properties = Helper.getPropertiesByFileName("const/const.properties");
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> lstCategory = categoryDAO.getAll();
        request.setAttribute("lstCategory", lstCategory);
        ProductDAO productDAO = new ProductDAO();
        
        int pageCur = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        int numberProductManagementPerPage = Integer.parseInt(properties.getProperty("numberProductManagementPerPage"));
        List<Integer> lstPage = new ArrayList<>();
        int size = productDAO.getSize(null, null, null, false);
        int totalPage = size % numberProductManagementPerPage == 0
                ? size / numberProductManagementPerPage
                : size / numberProductManagementPerPage + 1;
        for (int i = 1; i <= totalPage; i++) {
            lstPage.add(i);
        }
        List<Product> lstProduct = productDAO.getAllPerPage(pageCur, numberProductManagementPerPage, null, null, null, false);
        System.out.println(lstProduct);
        
        request.setAttribute("pageCur", pageCur);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("lstPage", lstPage);
        request.setAttribute("lstProduct", lstProduct);
        request.getRequestDispatcher("product-management.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        ProductColorDAO productColorDAO = new ProductColorDAO();
        String productName = request.getParameter("productName");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int productPrice = Integer.parseInt(request.getParameter("productPrice"));
        String productDescription = request.getParameter("productDescription");
        Product product = Product.builder()
                .productName(productName)
                .productImg("assets/img/system/product-default.jpg")
                .productPrice(productPrice)
                .productDescription(productDescription)
                .categoryId(categoryId)
                .productIsFeatured(false)
                .productIsRecent(false)
                .productDeleted(false)
                .build();
        
        int productId = productDAO.add(product);
        
        for (Color c : new ColorDAO().getAll()) {
            productColorDAO.add(productId, c.getColorId());
        }
        
        response.sendRedirect("product-detail-management?productId=" + productId);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
