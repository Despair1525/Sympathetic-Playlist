/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.DAO;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.tagext.PageData;
import model.ControlPaging;
import model.playList_HE150883;
import model.song_HE150883;

/**
 *
 * @author smileymask
 */
public class LoadUserPage extends HttpServlet {

    DAO dao;
//    DAO dao;
//    String status = "";
//
//    public void init() {
//        dao = new DAO();
//    }

    public String getUserId(HttpServletRequest request) {
        String userId = "";
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cooky : cookies) {
                if (cooky.getName().equals("user")) {
                    String s = cooky.getValue();
                    userId = s.substring(1);
                }
            }
        }

        return userId;
    }

    ;
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
            out.println("<title>Servlet LoadUserPage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadUserPage at " + request.getContextPath() + "</h1>");
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

            dao = (DAO) request.getAttribute("DAO");
            if (request.getParameter("playlistId") != null ) {
                boolean Public = true;
                String playlistId = request.getParameter("playlistId");
                playList_HE150883 playlistChoose = dao.getPlaylist(playlistId);
                String UserPlayList = playlistChoose.getUserId();
                String currentUser = dao.getUserId(request);
                 request.setAttribute("public", Public);             
                if(!currentUser.equals(UserPlayList) && !playlistChoose.isPlaylistPublic()){
                      playlistChoose= null;        
                }
               
               
                if (playlistChoose != null) {
                     System.out.println("Number Of Song: "+ playlistChoose.getSongs().size());
                    request.setAttribute("playlistPlay", playlistChoose);
                    request.getRequestDispatcher("view/User/playlistPlay.jsp").forward(request, response);
                }
                else {
                response.sendRedirect("LoadDB");
                };
            } 
            
            else if (request.getParameter("addPlaylist") != null) {
            String songId = request.getParameter("songId");
            for (playList_HE150883 playlist : dao.playlistList) {
                String playListId = request.getParameter(playlist.getPlaylistId());
                if (playListId != null) {
                    dao.insertContains(playListId, songId);
                    HttpSession ses = request.getSession(); 
                    ses.setAttribute("reload", true);
                    System.out.println(dao.status);

                };

            };
            response.sendRedirect("view/User/playList.jsp");

        }
            
            else {
                
                dao.loadSongUser(getUserId(request));
                int size = dao.songListUser.size();
                int nrpp = 7;
                int cp = 0;
                ControlPaging ControlPage = new ControlPaging(nrpp, cp, size);
                ControlPage.calc();
                request.setAttribute("CP", ControlPage);
                request.setAttribute("currentPage", cp);
                request.getRequestDispatcher("view/User/index.jsp").forward(request, response);
            };

        } else {
            request.setAttribute("LoadUserPage", "LoadUserPage");
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
        
         request.setCharacterEncoding("UTF-8");
         if(dao == null) {
             doGet(request, response);
         }
         else{
        request.setAttribute("DAO", dao);
        if (request.getParameter("cp") != null) {
            int size = Integer.parseInt(request.getParameter("size"));
            int nrpp = 7;
            int cp = Integer.parseInt(request.getParameter("cp"));
            int np = Integer.parseInt(request.getParameter("np"));

            if (request.getParameter("home") != null) {
                cp = 0;
            }
            if (request.getParameter("pre3") != null) {
                cp = cp - 3;
            }
            if (request.getParameter("pre") != null) {
                cp = cp - 1;
            }
            if (request.getParameter("next") != null) {
                cp = cp + 1;
            }
            if (request.getParameter("next3") != null) {
                cp = cp + 3;
            }
            if (request.getParameter("end") != null) {
                cp = np - 1;
            }

            for (int i = 0; i < np; i++) {
                if (request.getParameter("btn" + i) != null) {
                    cp = i;
                }
            }
            ControlPaging page = new ControlPaging(nrpp, cp, size);
            page.calc();
            request.setAttribute("CP", page);
            request.getRequestDispatcher("view/User/index.jsp").forward(request, response);

        } else if (request.getParameter("addPlaylist") != null) {

            String songId = request.getParameter("songId");

            for (playList_HE150883 playlist : dao.playlistList) {
                String playListId = request.getParameter(playlist.getPlaylistId());
                if (playListId != null) {
                    dao.insertContains(playListId, songId);
                    HttpSession ses = request.getSession(); 
                    ses.setAttribute("reload", true);
                    System.out.println(dao.status);

                };

            };
            response.sendRedirect("view/User/playList.jsp");

        };
         }
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
