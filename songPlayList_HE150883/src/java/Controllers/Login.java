/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.*;
import model.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author smileymask
 */
public class Login extends HttpServlet {

    DAO dao;
    DAOUser daoUser;
    String status = "";

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
            out.println("<title>Servlet Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);

        if (request.getAttribute("DaoUser") != null) {

            daoUser = (DAOUser) request.getAttribute("DaoUser");
            request.getRequestDispatcher("view/Login.jsp").forward(request, response);
        }
         else {
        request.setAttribute("Login", "Login");
        request.getRequestDispatcher("LoadDB").forward(request, response);
    
    };
        

    }

    ;


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
//        processRequest(request, response);
        
        if(daoUser == null) {
            doGet(request, response);
        }
        else{
        if (request.getParameter("Login") != null) {
            String userId = request.getParameter("user");
            String userPass = request.getParameter("pass");
            System.out.println("Login In Web");
            System.out.println("User: " + userId);
            System.out.println("Pass: " + userPass);

            User_HE150883 user = checkUser(userId, userPass);
            HttpSession ses = request.getSession();
            if (user == null) {
                request.setAttribute("LoginMess", "ID or Password not found in Database");
                request.getRequestDispatcher("view/Login.jsp").forward(request, response);
            } else {

                ses.setAttribute("user", user);
                Cookie c = new Cookie("user", user.getUserType() + user.getUserId());
                int maxAge = Integer.parseInt(getServletContext().getInitParameter("maxAge"));
                c.setMaxAge( maxAge );
                response.addCookie(c);
                response.sendRedirect("LoadDB");
            }
        }
    }
    }

    public User_HE150883 checkUser(String user, String pass) {
       
            pass = MD5.getMd5(pass);
        
        for (User_HE150883 obj : daoUser.userList) {
            if (obj.getUserId().equals(user) && obj.getUserPassword().equals(pass)) {
                return obj;
            };

        };

        return null;
    }

    ;

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
