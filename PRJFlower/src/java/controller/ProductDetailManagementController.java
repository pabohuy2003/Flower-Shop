/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ProductColorDAO;
import dao.ProductDAO;
import dao.ProductImgDetailDAO;
import entity.Category;
import entity.Product;
import entity.ProductColor;
import entity.ProductImgDetail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ProductDetailManagementController", urlPatterns = {"/product-detail-management"})
public class ProductDetailManagementController extends HttpServlet {

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
            out.println("<title>Servlet ProductDetailManagementController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailManagementController at " + request.getContextPath() + "</h1>");
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
        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        ProductImgDetailDAO productImgDetailDAO = new ProductImgDetailDAO();
        ProductColorDAO productColorDAO = new ProductColorDAO();
        
        List<Category> lstCategory = categoryDAO.getAll();
        int productId = Integer.parseInt(request.getParameter("productId"));
        List<ProductImgDetail> lstProductImgDetail = productImgDetailDAO.getAll(productId);
        Product product = productDAO.getOne(productId);
        List<ProductColor> lstProductColor = productColorDAO.getAll(productId, false);
        
        request.setAttribute("lstProductColor", lstProductColor);
        request.setAttribute("product", product);
        request.setAttribute("lstCategory", lstCategory);
        request.setAttribute("lstProductImgDetail", lstProductImgDetail);
        request.getRequestDispatcher("product-detail-management.jsp").forward(request, response);
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
        
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int productPrice = Integer.parseInt(request.getParameter("productPrice"));
        boolean productIsFeatured = request.getParameter("productIsFeatured") != null;
        boolean productIsRecent = request.getParameter("productIsRecent") != null;
        String[] productColorIds = request.getParameterValues("productColorId");
        String productDescription = request.getParameter("productDescription");
        
        Product product = productDAO.getOne(productId);
        product.setProductName(productName);
        product.setCategoryId(categoryId);
        product.setProductPrice(productPrice);
        product.setProductIsFeatured(productIsFeatured);
        product.setProductIsRecent(productIsRecent);
        product.setProductDescription(productDescription);
        productDAO.update(product, productId);
        
        productColorDAO.updateActive(productColorIds, productId);
        
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
