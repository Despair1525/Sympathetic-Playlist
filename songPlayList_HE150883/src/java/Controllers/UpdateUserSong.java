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
import jakarta.servlet.http.HttpSession;
import model.song_HE150883;

/**
 *
 * @author smileymask
 */
public class UpdateUserSong extends HttpServlet {

    DAO dao;
//    String status = "";
//
//    public void init() {
//        dao = new DAO();
//    }

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
            out.println("<title>Servlet UpdateUserSong</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateUserSong at " + request.getContextPath() + "</h1>");
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
        HttpSession ses = request.getSession();

        if (request.getAttribute("DAO") != null) {
            String type = request.getParameter("type");
            String id = request.getParameter("id");
            dao = (DAO) request.getAttribute("DAO");
            boolean checkAuthetication = false;
            request.setAttribute("DAO", dao);

            if (type.equals("4") || type.equals("5")) {
                String playlistid = request.getParameter("playlistId");
                if (type.equals("4")) {
                    System.out.println(playlistid);
                    boolean Public = (!dao.getPlaylist(playlistid).isPlaylistPublic());
                    dao.updatePlaylist(playlistid, Public);
                    ses.setAttribute("reload", true);
                    System.out.println(dao.status);
                    response.sendRedirect("LoadUserPage?playlistId=" + playlistid);

                } else {
                    dao.deletePlaylist(playlistid);
                    ses.setAttribute("reload", true);
                    System.out.println(dao.status);
                    response.sendRedirect("loadPlaylist");
                }

            } else {
                if (id != null && type != null) {
                    song_HE150883 updateSong = dao.findSong(id, dao.songList);
                    if (updateSong != null) {
                        String userSong = updateSong.getUserId();
                        String currentUser = dao.getUserId(request);

                        if (type.equals("0") && userSong.equals(currentUser)) {

                            request.setAttribute("updateSong", updateSong);
                            request.getRequestDispatcher("view/User/uploadSong.jsp").forward(request, response);
                        }
                        if (type.equals("1") && userSong.equals(currentUser)) {
                            dao.deleteSong(id);
                            ses.setAttribute("reload", true);
                            System.out.println(dao.status);
                            response.sendRedirect(request.getContextPath() + "/LoadUserPage");
                        }

                        if (type.equals("2")) {
                            System.out.println("Send to PlaylistPages");
                            dao.loadplayListUser(dao.getUserId(request));
                            request.setAttribute("songId", id);
                            request.getRequestDispatcher("view/User/playList.jsp").forward(request, response);
                        }

                        if (type.equals("3")) {
                            String playlist = request.getParameter("playlistId");
                            System.out.println("Remove from Playlist " + playlist);
                            dao.deleteContains(id, playlist);
                            ses.setAttribute("reload", true);
                            System.out.println(dao.status);
                            response.sendRedirect("LoadUserPage?playlistId=" + playlist);
                        }

                    }

                } else {

                    response.sendRedirect(request.getContextPath() + "/LoadUserPage");

                }
            }

        } else {

            request.setAttribute("UpdateUserSong", "UpdateUserSong");
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
        processRequest(request, response);
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
