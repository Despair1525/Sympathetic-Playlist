/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.*;
import model.*;
import java.util.*;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class DAO { //Data access object

    private Connection con;
    public ArrayList<song_HE150883> songList;
    public ArrayList<song_HE150883> songListUser;
    public HashMap<String, song_HE150883> songHm;
    public ArrayList<playList_HE150883> playlistList;
    public ArrayList<playList_HE150883> playListListUser;
    public HashMap<String, genre_HE150883> genreHm; //search with O(1)
    public ArrayList<Menu> MenuList;
    public ArrayList<rate_He150883> rateList;
   
    public HashMap<String, Integer> songScore;
    public HashMap<String, ArrayList<String>> HMSongGenre;
    public HashMap<String, ArrayList<song_HE150883>> HMcontainSong;
    public String status;

    public DAO() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }
    }
  
    public ArrayList<song_HE150883> getSongList() {
        return songList;
    }

    public void setSongList(ArrayList<song_HE150883> songList) {
        this.songList = songList;
    }

    public HashMap<String, genre_HE150883> getGenreHm() {
        return genreHm;
    }

    public void setGenreHm(HashMap<String, genre_HE150883> genreHm) {
        this.genreHm = genreHm;
    }

    public HashMap<String, ArrayList<String>> getHMSongGenre() {
        return HMSongGenre;
    }

    public void setHMSongGenre(HashMap<String, ArrayList<String>> HMSongGenre) {
        this.HMSongGenre = HMSongGenre;
    }

    public HashMap<String, song_HE150883> getSongHm() {
        return songHm;
    }

    public void setSongHm(HashMap<String, song_HE150883> songHm) {
        this.songHm = songHm;
    }

    public ArrayList<playList_HE150883> getPlaylistList() {
        return playlistList;
    }

    public void setPlaylistList(ArrayList<playList_HE150883> playlistList) {
        this.playlistList = playlistList;
    }

    public ArrayList<rate_He150883> getRateList() {
        return rateList;
    }

    public void setRateList(ArrayList<rate_He150883> rateList) {
        this.rateList = rateList;
    }

    public HashMap<String, Integer> getSongScore() {
        return songScore;
    }

    public void setSongScore(HashMap<String, Integer> songScore) {
        this.songScore = songScore;
    }

    public HashMap<String, ArrayList<song_HE150883>> getHMcontainSong() {
        return HMcontainSong;
    }

    public void setHMcontainSong(HashMap<String, ArrayList<song_HE150883>> HMcontainSong) {
        this.HMcontainSong = HMcontainSong;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<song_HE150883> getSongListUser() {
        return songListUser;
    }

    public void setSongListUser(ArrayList<song_HE150883> songListUser) {
        this.songListUser = songListUser;
    }

    public ArrayList<playList_HE150883> getPlayListListUser() {
        return playListListUser;
    }

    public void setPlayListListUser(ArrayList<playList_HE150883> playListListUser) {
        this.playListListUser = playListListUser;
    }

/////////////////////// LOAD DATABASE///////////////////////////////////////////////////////////////
    public void loadSong() {
        songList = new ArrayList<>();
        songHm = new HashMap<>();
        loadRate();
        loadGenreSong();
        String sql = "select * from Song_HE150883 ORDER BY LinkSong DESC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int Score = 0;
            ArrayList<String> genres = new ArrayList<>();
            while (rs.next()) {
                Score = 0;
                String id = rs.getString("songID"); // get by column name or index of column
                String name = rs.getString("songName");
                String artist = rs.getString("artist");
                int duration = rs.getInt("duration");
                String linkSong = rs.getString("LinkSong");
                String linkImage = rs.getString("LinkImage");
                String userId = rs.getString("userId");
                Timestamp songDate = rs.getTimestamp("songDate");
                if (songScore.containsKey(id)) {
                    Score = songScore.get(id);
                }
                if (HMSongGenre.containsKey(id)) {
                    genres = HMSongGenre.get(id);
                };
                songList.add(new song_HE150883(id, name, artist, duration, linkSong, linkImage, userId, Score, genres, songDate));
                songHm.put(id, new song_HE150883(id, name, artist, duration, linkSong, linkImage, userId, Score, genres));
                genres = new ArrayList<>();
            }
        } catch (Exception e) {
            status = "Error Load Song List " + e.getMessage();
        }
    }

    public void loadSongUser(String UserID) {
        if (songList == null) {
            loadSong();
        }
        songListUser = new ArrayList<>();
        for (song_HE150883 song : songList) {
            if (song.getUserId().equals(UserID)) {
                songListUser.add(song);
            }
        }
    }

    public void loadplayListUser(String UserID) {
        if (playlistList == null) {
            loadPlaylist();
        }
        playListListUser = new ArrayList<>();
        for (playList_HE150883 playlist : playlistList) {
            if (playlist.getUserId().equals(UserID)) {
                playListListUser.add(playlist);
            }
        }
    }

    public void loadRate() {
        rateList = new ArrayList<>();
        songScore = new HashMap<>();
       
        String sql = "select a.userId,a.Id,b.Score,a.LastTime from\n"
                + "(SELECT DISTINCT  userId, Id ,MAX(TimeRate)\n"
                + "OVER (PARTITION BY userId, Id) as 'LastTime'\n"
                + "FROM Rate_HE150883 ) a \n"
                + "join Rate_HE150883 b\n"
                + "on a.userId = b.userId and a.Id = b.Id and a.LastTime = b.TimeRate;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userId = rs.getString(1);
                String Id = rs.getString(2);
                int Score = rs.getInt(3);

               
                if (!songScore.containsKey(Id)) {
                    songScore.put(Id, Score);
                } else {
                    songScore.replace(Id, Score + songScore.get(Id));
                };

                rateList.add(new rate_He150883(userId, Id, Score));
            }

            
        } catch (Exception e) {
            status = "Error Load Genre " + e.getMessage();
        }
    }

    public void loadGenre() {
        genreHm = new HashMap<>();
        String sql = "select * from genre_HE150883";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String genreId = rs.getString(1);
                String genreName = rs.getString(2);
                genreHm.put(genreId, new genre_HE150883(genreId, genreName));
            }
        } catch (Exception e) {
            status = "Error Load Genre " + e.getMessage();
        }
    }

    public void loadGenreSong() {
        loadGenre();
        HMSongGenre = new HashMap<>();
        String sql = "select * from Song_Genre_HE150883";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String songId = rs.getString(1);
                String genreId = rs.getString(2);
                if (HMSongGenre.containsKey(songId)) {
                    HMSongGenre.get(songId).add(genreHm.get(genreId).getGenreName());
                } else {
                    ArrayList<String> newgenre = new ArrayList<>();
                    newgenre.add(genreHm.get(genreId).getGenreName());
                    HMSongGenre.put(songId, newgenre);
                };
            }
        } catch (Exception e) {
            status = "Error Load GenreSong " + e.getMessage();
        }

    }

    public void loadContains() {
        HMcontainSong = new HashMap<>();
        String sql = "select DISTINCT * from Contains_HE150883";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String songId = rs.getString(1);
                String playlistId = rs.getString(2);
                if (HMcontainSong.containsKey(playlistId)) {
                    HMcontainSong.get(playlistId).add(songHm.get(songId));
                } else {
                    ArrayList<song_HE150883> newSong = new ArrayList<>();
                    newSong.add(songHm.get(songId));
                    HMcontainSong.put(playlistId, newSong);
                };
            }
        } catch (Exception e) {
            status = "Error Load GenreSong " + e.getMessage();
        }
    }

    ;
    public void loadPlaylist() {
        loadContains();
        playlistList = new ArrayList<>();
        String sql = "select * from Playlist_HE150883;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int Score = 0;
            ArrayList<song_HE150883> listSong = new ArrayList<>();
            while (rs.next()) {
                Score = 0;
                String id = rs.getString(1); // get by column name or index of column
                String name = rs.getString(2);
                boolean Public = rs.getBoolean(4);
                String userId = rs.getString("userId");

                if (HMcontainSong.containsKey(id)) {
                    listSong = HMcontainSong.get(id);
                };
                playlistList.add(new playList_HE150883(id, name, Public, listSong, userId));
                status = "Load Playlist succsess";
//        System.out.println("Test list: "+playlistList.get(0).getPlaylistName());
            }
        } catch (Exception e) {
            status = "Error Load Song List " + e.getMessage();
        }

    }

    ;
 ////////////////////////////////////////// INSERT DATABASE /////////////////////////////////////////////////
    
    public void insertSong(String songName, String artist, String linkSong, String linkImage, String userId, ArrayList<String> genres) {
        String sql = "insert into Song_HE150883(songId,songName,artist,linkSong,linkImage,userId)\n"
                + "values\n"
                + "(?,?,?,?,?,?)";
        String songId = generateId(userId);

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, songId);
            ps.setString(2, songName);
            ps.setString(3, artist);
            ps.setString(4, linkSong);
            ps.setString(5, linkImage);
            ps.setString(6, userId);
            ps.execute();
            status = "Insert Song Succsess";

            insertGenreSong(songId, genres);

        } catch (Exception e) {
            status = ("Error " + e.getMessage());
        }

    }

    public void insertGenreSong(String songId, ArrayList<String> genres) {
        String sql = "INSERT INTO Song_Genre_HE150883(songId,genreId) values";
        if (genres.isEmpty()) {
            return;
        }
        try {
            for (int i = 0; i < genres.size(); i++) {
                if (i == genres.size() - 1) {
                    sql += " (?,?);";
                } else {
                    sql += " (?,?),";
                };
            };
            PreparedStatement ps = con.prepareStatement(sql);
            int count = 1;
            for (String id : genres) {
                ps.setString(count, songId);
                ps.setString(count + 1, id);
                count += 2;
            };
            ps.execute();
            status = "Insert Song Genre Succsess";
        } catch (Exception e) {
            status = ("Error in insert genres: " + e.getMessage());
        }

    }

    public void insertRate(String userId, String Id, int Score) {
        String sql = "Insert into Rate_HE150883(userId,Id,Score) VALUES (?,?,?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, Id);
            ps.setInt(3, Score);

            ps.execute();
            status = "Insert Rate Succsess";
        } catch (Exception e) {
            status = ("Error " + e.getMessage());
        }
    }

    public void insertPlaylist(String name, boolean Public, String user, String songId) {

        String id = generateId(user, 3);
        String sql = "";
        sql += "INSERT INTO Playlist_HE150883(playlistId,playlistName,playListPublic,userId) VALUES (?,?,?,?);";
        if (songHm.containsKey(songId)) {

            sql += "INSERT INTO Contains_HE150883(songId,playlistId) VALUES (?,?)";

        };
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setBoolean(3, Public);
            ps.setString(4, user);
            if (songHm.containsKey(songId)) {
                ps.setString(5, songId);
                ps.setString(6, id);
            }
            ps.execute();

            status = "Insert Playlist Succsess";
        } catch (Exception e) {
            status = ("Error " + e.getMessage());
        }

    }

    public void insertContains(String playListId, String songId) {
        String sql = "INSERT INTO Contains_HE150883(songId,playlistId) VALUES (?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, songId);
            ps.setString(2, playListId);
            ps.execute();
            status = "Add " + songId + " song into playlist:" + playListId + "succsess";
        } catch (Exception e) {
            status = "Error in update " + e.getMessage();
        };

    }

    ;
///////////////////////////////////////////////UPDATE/DELETE///////////////////////////////////////////////////////////
    
    public void updateSong(String id, String songName, String artist, String linkImage, ArrayList<String> listGenre) {
        String sql = "UPDATE Song_HE150883 SET songName = ? , artist = ? , LinkImage = ?\n"
                + "WHERE songID = ?; DELETE Song_Genre_HE150883 WHERE songId = ?;";    
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, songName);
            ps.setString(2, artist);
            
            ps.setString(3, linkImage);
            ps.setString(4, id);
            ps.setString(5, id);
            
            
            ps.execute();
            status = "UpdateSong Succsess";
            insertGenreSong(id, listGenre);
        } catch (Exception e) {
            status = "Error in update " + e.getMessage();
        }

    }
    
    public void updatePlaylist(String playlistId , boolean Public) {
    
    String sql ="UPDATE Playlist_HE150883 SET playListPublic =? WHERE playlistId = ? ";
      try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, Public);
            ps.setString(2, playlistId);
            ps.execute();
            status = "Update playlist Success !";
        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }
    };
    
    public void deletePlaylist(String playlistId ) {
    
    String sql ="DELETE Contains_HE150883 WHERE playlistId = ?;"
            + "DELETE Playlist_HE150883 WHERE playlistId = ?;"
            ;
      try {
            PreparedStatement ps = con.prepareStatement(sql);
           ps.setString(1, playlistId);
            ps.setString(2, playlistId);
            ps.execute();
            status = "Delete playlist Success !";
        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }
    };

    public void deleteSong(String songID) {
        String sql = 
                  "DELETE Song_Genre_HE150883 WHERE songId = ? ;"
                + "DELETE Contains_HE150883 WHERE songId = ? ;"
                + "DELETE Rate_HE150883 WHERE Id = ? ;"
                + "DELETE Song_HE150883 WHERE songID = ? ;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, songID);
            ps.setString(2, songID);
            ps.setString(3, songID);
             ps.setString(4, songID);
            ps.execute();
            status = "DeleteSong Success !";
        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }

    }
    public void deleteContains(String songId , String playlistId) {
    
    String sql ="DELETE Contains_HE150883 WHERE songId = ? and playlistId = ?";
      try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, songId);
            ps.setString(2, playlistId);
            ps.execute();
            status = "DeleteSong From playlist Success !";
        } catch (Exception e) {
            status = "Error " + e.getMessage();
        }
    };
    
    

    ///////////////////////////////////////////// Other Function//////////////////////////////////// ///   
    public String genCaptcha() {

        // Khai bao 1 mang gom tat ca cac ki tu: chu thuong, chu hoa va so
        char data[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String captcha = "";
        Random random = new Random();
        while (captcha.length() < 4) {
            int number = random.nextInt(data.length);
            String character = "" + data[number];
            // Neu captcha khong chua ki tu "character" roi thi captcha + them "character"
            if (!captcha.contains(character)) {
                captcha += character;
            }
            // Neu khong co thi tiep tuc quay lai chay vong while
        }

        return captcha;
    }

    public String genCaptcha(int num) {

        // Khai bao 1 mang gom tat ca cac ki tu: chu thuong, chu hoa va so
        char data[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String captcha = "";
        Random random = new Random();
        while (captcha.length() < num) {
            int number = random.nextInt(data.length);
            String character = "" + data[number];
            // Neu captcha khong chua ki tu "character" roi thi captcha + them "character"
            if (!captcha.contains(character)) {
                captcha += character;
            }
            // Neu khong co thi tiep tuc quay lai chay vong while
        }

        return captcha;
    }

    public String generateId(String userId) {
        Date d = new Date();
        String TimeCypher = d.getSeconds() + "" + d.getMonth() + "" + d.getDate();
        String id = (userId + "-" + TimeCypher + "-" + genCaptcha());
        return id;
    }

    public String generateId(String userId, int num) {
        Date d = new Date();
        String TimeCypher = d.getSeconds() + "" + d.getMonth() + "" + d.getDate();
        String id = (userId + "-" + TimeCypher + "-" + genCaptcha(num));
        return id;
    }

    public song_HE150883 findSong(String id, ArrayList<song_HE150883> listSong) {
        for (song_HE150883 song : listSong) {
            if (song.getSongId().equals(id)) {
                return song;
            };
        };
        return null;
    }

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

    public boolean listContain(String key, ArrayList<String> lst) {
        System.out.println(key);
        for (String i : lst) {
            System.out.println(lst);
            if (i.equals(key)) {
                return true;
            }
        };
        return false;
    }

    public boolean checkName(String name1, String name2) {
        name1 = name1.toLowerCase().trim();
        name2 = name2.toLowerCase().trim();
        ArrayList<String> list1 = new ArrayList<>();
        Collections.addAll(list1, name1.split(" "));

        ArrayList<String> list2 = new ArrayList<>();
        Collections.addAll(list2, name2.split(" "));
        return list1.stream().anyMatch(element -> list2.contains(element));
    }

    ;
    
    public boolean checkList(ArrayList list1, ArrayList list2) {
        return list1.stream().anyMatch(element -> list2.contains(element));
    }

    ;
    
    public ArrayList<song_HE150883> filterSong(String name) {
        ArrayList<song_HE150883> filterList = new ArrayList<>();

        for (song_HE150883 song : songList) {
            if (checkName(song.getSongName(), name)) {
                filterList.add(song);
            };

        };
        return filterList;

    }
public ArrayList<song_HE150883> filterSongLink() {
        ArrayList<song_HE150883> filterList = new ArrayList<>();

        for (song_HE150883 song : songList) {
            if ( song.getLinkSong() != null ) {
                filterList.add(song);
            };

        };
        return filterList;

    }
    
    
    public ArrayList<song_HE150883> filterGenres(ArrayList<song_HE150883> songList, ArrayList<String> genresList) {
        ArrayList<song_HE150883> newList = new ArrayList<>();
        for (song_HE150883 song : songList) {
            if (checkList(song.getGenre(), genresList)) {
                System.out.println(song.getSongName() + ": " + song.getGenre().toString());
                newList.add(song);
            }
        };

        return newList;
    }

    public playList_HE150883 getPlaylist(String id) {
        for (playList_HE150883 play : playlistList) {
            if (play.getPlaylistId().equals(id)) {
                return play;
            }
        }
        return null;
    }

    public int findRate(String user, String song) {

        for (rate_He150883 rate : rateList) {

            if (rate.getUserId().equals(user) && rate.getId().equals(song)) {
                return rate.getScore();
            };

        }
        return 0;
    }

    public static String getMenu(ArrayList<Menu> menu) {
        return getSubMenu(menu, 0);
    }

    ;
    
    public static String getSubMenu(ArrayList<Menu> menu, int pid) {
        // Load toàn bộ main item mà có main ID
        boolean check = true;
        for (Menu m : menu) {
            if (m.getPid() == pid) {
                System.out.println(m.getName());
                check = false;
                break;
            };
        };
        if (check) {
            return "";
        }

        String s = "<ul>\n";
        for (Menu m : menu) {
            if (m.getPid() == pid) {
                s += "<li><a href ='" + m.getLink() + "'title='" + m.getTitle() + "'>" + m.getName() + "</a>";
            }
            s += getSubMenu(menu, m.getId());
            s += "</li>\n";
        };
        s += "</ul>\n";
        return s;

    }
;
//    public static void main(String[] args) {
//       DAO dao = new DAO();
//       dao.loadMenu();
//       String menu = getMenu(dao.MenuList);
//        System.out.println(menu);
//      
//    }

}
