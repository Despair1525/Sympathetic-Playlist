/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import java.util.*;

/**
 *
 * @author smileymask
 */
public class mainPageFilter extends HttpServlet {

    DAO dao;

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
            out.println("<title>Servlet mainPageFilter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet mainPageFilter at " + request.getContextPath() + "</h1>");
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

        if (request.getAttribute("DAO") != null) {
            ArrayList<String> genreFilter = new ArrayList<>();
            String searchName = request.getParameter("search_box");
            dao = (DAO) request.getAttribute("DAO");
            for (String genreId : dao.genreHm.keySet()) {
                if (request.getParameter(genreId) != null) {
//                    System.out.println(genreId + " " + dao.genreHm.get(genreId).getGenreName());
                    genreFilter.add(dao.genreHm.get(genreId).getGenreName());
                };
            };
            
            ArrayList<song_HE150883> listFilter = new ArrayList<>();
            if (!searchName.equals("")) {
                listFilter = dao.filterSong(searchName);
                if (!genreFilter.isEmpty()) {
                    listFilter = dao.filterGenres(listFilter, genreFilter);
                };
            };

            if ( searchName.equals("") && !genreFilter.isEmpty()) {
                listFilter = dao.filterGenres(dao.songList, genreFilter);
            };

            request.setAttribute("listFilter", listFilter);
            request.getRequestDispatcher("view/searchPage.jsp").forward(request, response);
        } else {
            request.setAttribute("mainPageFilter", "mainPageFilter");
            request.getRequestDispatcher("LoadDB").forward(request, response);

        };
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
