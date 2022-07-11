/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.DAO;
import DAL.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.*;
import model.*;
import java.io.File;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoadDB", urlPatterns = {"/LoadDB"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 100, // 100 MB  The file size in bytes after which the file will be temporarily stored on disk. The default size is 0 bytes.
        maxFileSize = 1024 * 1024 * 100, // 100 MB  The maximum size allowed for uploaded files,
        maxRequestSize = 1024 * 1024 * 100 // 100 MB The maximum size allowed for a multipart/form-data
)
public class LoadDB extends HttpServlet {

    private String musicPath = "D:\\FPT\\SEMESTER\\SUM22\\PRJ301\\ProjectPRJ_Sympathetic\\songPlayList_HE150883\\web\\musics\\";
    private String imagePath = "D:\\FPT\\SEMESTER\\SUM22\\PRJ301\\ProjectPRJ_Sympathetic\\songPlayList_HE150883\\web\\images\\";

    DAO dao;
    String status = "";
    DAOUser daoUser;
    recomendSystem rs;

    public void init() {
        dao = new DAO();
        daoUser = new DAOUser();
        rs = new recomendSystem();
    }

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

        }
    }

    ;


    
    
   

    ;
    

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

        if (request.getParameter("songId-playList") != null) {
            String songId = request.getParameter("songId-playList");
            request.setAttribute("songId", songId);
            request.getRequestDispatcher("view/User/makePlaylist.jsp").forward(request, response);
        } else {
            HttpSession ses = request.getSession();
            boolean reload = true;
            boolean reloadUser = true;
            if (ses.getAttribute("reload") != null) {
                reload = (boolean) (ses.getAttribute("reload")); //return object
            }
            if (ses.getAttribute("reloadUser") != null) {
                reloadUser = (boolean) (ses.getAttribute("reloadUser"));
            };

            if (reloadUser) {
                daoUser.loadUser();
                ses.setAttribute("reloadUser", false);
                System.out.println("Load Database User Succsess!");
            };

            if (reload) {
                dao.loadSong();
                dao.loadPlaylist();
                ses.setAttribute("reload", false);
                System.out.println("Load Database Succsess!");

            };
            request.setAttribute("DAO", dao);
            request.setAttribute("DaoUser", daoUser);

            if (request.getAttribute("LoadUserPage") != null) {
                request.getRequestDispatcher("LoadUserPage").forward(request, response);
            } else if (request.getAttribute("LoadPlaylist") != null) {
                request.getRequestDispatcher("loadPlaylist").forward(request, response);
            } else if (request.getAttribute("UpdateUserSong") != null) {
                request.getRequestDispatcher("UpdateUserSong").forward(request, response);
            } else if (request.getAttribute("mainPageFilter") != null) {
                request.getRequestDispatcher("mainPageFilter").forward(request, response);
            } else if (request.getAttribute("profileUser") != null) {
                request.getRequestDispatcher("profileUser").forward(request, response);
            } else if (request.getAttribute("Login") != null) {
                request.getRequestDispatcher("Login").forward(request, response);
            } else if (request.getAttribute("Register") != null) {
                request.getRequestDispatcher("Register").forward(request, response);
            } else if (request.getAttribute("resetPass") != null) {
                request.getRequestDispatcher("resetPassword").forward(request, response);
            } else if (request.getAttribute("LoadAdminPage") != null) {
                request.getRequestDispatcher("LoadAdminPage").forward(request, response);
            } else {
                ArrayList<song_HE150883> newSong = (ArrayList<song_HE150883>) dao.songList.clone();
                ///////// Song have Highese Score
                Collections.sort(dao.songList, (o1, o2) -> o2.getScore() - o1.getScore());
                //////////////////////////////// Song just public 
                Collections.sort(newSong, (o1, o2) -> o2.getSongDate().compareTo(o1.getSongDate()));
                ////////////////////////////////// Recomend Song ////////////////////////

                String currentUser = dao.getUserId(request);
                
                if (!currentUser.equals("") && dao.songList.size() > 10) {
                    ArrayList<song_HE150883> listSong;
                    listSong = dao.filterSongLink();
                    rs.slopeOne(listSong, daoUser.userList, dao.rateList);
                    HashMap<song_HE150883, Double> listRecomend = rs.getOutputData().get(daoUser.getUser(currentUser));
                    ArrayList<song_HE150883> listReco = new ArrayList<>();
                    listReco.addAll(listRecomend.keySet());
                    Collections.sort(listReco, (o1, o2) -> (listRecomend.get(o1) - listRecomend.get(o2)) > 0 ? 1 : -1);
                    request.setAttribute("recomendSong", listReco);
                }

                request.setAttribute("newSong", newSong);
                if(currentUser.equals("")) request.setAttribute("recomendSong", dao.songList);
                request.getRequestDispatcher("view/mainPage.jsp").forward(request, response);
            }
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
    public String writeFile(String File, String path, HttpServletRequest request) throws ServletException {
        try {
            Part filePart = request.getPart(File);
            String fileName = filePart.getSubmittedFileName();
            filePart.write(path + fileName);
            return fileName;
        } catch (IOException e) {
            return null;
        }
    }

    ;
    
   
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession ses = request.getSession();
        if (dao == null){
            doGet(request, response);
        }else{
        boolean reload = true;
        if (ses.getAttribute("reload") != null) {
            reload = (boolean) (ses.getAttribute("reload")); //return object
        }

        if (reload) {
            dao.loadSong();
            dao.loadPlaylist();
            ses.setAttribute("reload", false);
            System.out.println("Load Database Succsess!");

        };
        request.setAttribute("DAO", dao);

        if (request.getParameter("submitSong") != null || request.getParameter("UpdateSong") != null) {
            if (request.getParameter("submitSong") != null) {
                String user = request.getParameter("user");
                String songName = request.getParameter("songName");
                String artist = request.getParameter("Artist");
                ArrayList<String> genreList = new ArrayList<>();
                for (String key : dao.genreHm.keySet()) {
                    if (request.getParameter(key) != null) {
                        genreList.add(request.getParameter(key));
                        System.out.println(request.getParameter(key));
                    };
                };
                System.out.println(request.getParameter("songFile"));

                String songLink = "musics/" + writeFile("songFile", musicPath, request);

                System.out.println("Write Musics Success!");
                // Move Image Song into Folder 
                String imageLink = "images/" + writeFile("fileImage", imagePath, request);
                /// Insert into database 
                dao.insertSong(songName, artist, songLink, imageLink, user, genreList);
                ses.setAttribute("reload", true);
                System.out.println(dao.status);
            }

            if (request.getParameter("UpdateSong") != null) {
                String songName = request.getParameter("songName");
                String artist = request.getParameter("Artist");
                ArrayList<String> genreList = new ArrayList<>();

                for (String key : dao.genreHm.keySet()) {
                    if (request.getParameter(key) != null) {
                        genreList.add(request.getParameter(key));
                        System.out.println(request.getParameter(key));
                    };
                };
                String fileName = writeFile("fileImage", imagePath, request);
                String imageLink = "images/" + fileName;
                String songId = request.getParameter("songId");
                if (fileName == null) {
                    imageLink = dao.songHm.get(songId).getLinkImage();
                }
                
                dao.updateSong(songId, songName, artist, imageLink, genreList);
                ses.setAttribute("reload", true);
                System.out.println(dao.status);

            };
            response.sendRedirect("LoadUserPage");
        };

        if (request.getParameter("songId-playlist") != null) {
            System.out.println(request.getParameter("songId-playlist"));
            if (request.getParameter("NoPlay") != null) {
                response.sendRedirect("LoadUserPage");
            } else {

                String playName = request.getParameter("namePlaylist");
                boolean pulbicPlay = request.getParameter("playlistPublic").equals("1");
                String idSong = request.getParameter("songId-playlist");
                String user = request.getParameter("user");
                dao.insertPlaylist(playName, pulbicPlay, user, idSong);
                ses.setAttribute("reload", true);
                System.out.println(dao.status);
                System.out.println(dao.status);
                response.sendRedirect("view/User/playList.jsp");
            };
        };
        }
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
