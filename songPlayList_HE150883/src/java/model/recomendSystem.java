/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.*;
import DAL.*;
import java.util.Map.Entry;
import model.*;

/**
 *
 * @author smileymask
 */
public class recomendSystem {

    private  HashMap<song_HE150883, Map<song_HE150883, Double>> diff = new HashMap<>();
    private  HashMap<song_HE150883, Map<song_HE150883, Integer>> freq = new HashMap<>();
    private  HashMap<User_HE150883, HashMap<song_HE150883, Double>> inputData;
    private  HashMap<User_HE150883, HashMap<song_HE150883, Double>> outputData = new HashMap<>();
    
    private ArrayList<song_HE150883> songList;
    private ArrayList<User_HE150883> userList;
    private ArrayList<rate_He150883> rateList;

    public HashMap<User_HE150883, HashMap<song_HE150883, Double>> getOutputData() {
        return outputData;
    }

    public void setOutputData(HashMap<User_HE150883, HashMap<song_HE150883, Double>> outputData) {
        this.outputData = outputData;
    }
    
    public void slopeOne(ArrayList<song_HE150883> songList ,ArrayList<User_HE150883> userList ,ArrayList<rate_He150883> rateList ) {
        
        this.songList = songList;
        this.userList = userList;
        this.rateList = rateList;
        
        inputData = initializeData();
        buildDifferencesMatrix(inputData);
        predict(inputData);
    }
    
    
    
     public HashMap<User_HE150883, HashMap<song_HE150883, Double>> initializeData() {
         
        HashMap<User_HE150883, HashMap<song_HE150883, Double>> data = new HashMap<>();
        HashMap<song_HE150883, Double> newUser;
        Set<song_HE150883> newRecommendationSet;
        
        for (User_HE150883 user : userList) {
            
            newUser = new HashMap<>();
            newRecommendationSet = new HashSet<>();
            
            while(!(newRecommendationSet.size() == 10)) {
                Random random = new Random();
                int number = random.nextInt(songList.size());
                newRecommendationSet.add(songList.get(number));
            }

//            newRecommendationSet.addAll(songList) ;
        
            for (song_HE150883 song : newRecommendationSet) {
                newUser.put(song, checkUserRate(user, song));
            }
            data.put(user, newUser);
        }
        return data;
    }
     public double checkUserRate(User_HE150883 user , song_HE150883 song){
     
     for(rate_He150883 rate: rateList) {
         if(rate.getUserId().equals(user.getUserId()) && rate.getId().equals(song.getSongId())) {
         return rate.getScore();
         };
     
     }
     return Math.random();
     };
             
      private  void buildDifferencesMatrix(HashMap<User_HE150883, HashMap<song_HE150883, Double>> data) {
          
        for (HashMap<song_HE150883, Double> user : data.values()) {
            
            for (Entry<song_HE150883, Double> e : user.entrySet()) { //e List song User Rate
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<song_HE150883, Double>());
                    freq.put(e.getKey(), new HashMap<song_HE150883, Integer>());
                }
                for (Entry<song_HE150883, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey());
                    }
                    
                    double oldDiff = 0.0;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey());
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (song_HE150883 j : diff.keySet()) {
            for (song_HE150883 i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i);
                int count = freq.get(j).get(i);
                diff.get(j).put(i, oldValue/count);
            }
        }
//        printData(data);
    }
      
        private  void predict(HashMap<User_HE150883, HashMap<song_HE150883, Double>> data) {
        HashMap<song_HE150883, Double> uPred = new HashMap<>();
        HashMap<song_HE150883, Integer> uFreq = new HashMap<>();
        for (song_HE150883 j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<User_HE150883, HashMap<song_HE150883, Double>> e : data.entrySet()) {
            for (song_HE150883 j : e.getValue().keySet()) {
                for (song_HE150883 k : diff.keySet()) {
                    try {
                        double predictedValue = diff.get(k).get(j) + e.getValue().get(j);
                        double finalValue = predictedValue * freq.get(k).get(j);
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j));
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<song_HE150883, Double> clean = new HashMap<song_HE150883, Double>();
            for (song_HE150883 j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j) / uFreq.get(j));
                }
            }
            for (song_HE150883 j : songList) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else if (!clean.containsKey(j)) {
                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
//        printData(outputData);
    }
     
     private  void printData(HashMap<User_HE150883, HashMap<song_HE150883, Double>> data) {
        for (User_HE150883 user : data.keySet()) {
            System.out.println(user.getUserId() + ":");
            print(data.get(user));
        }
    }
     
       private  void print(HashMap<song_HE150883, Double> hashMap) {
        for (song_HE150883 j : hashMap.keySet()) {
            System.out.println(" " + j.getSongName() + " --> " + hashMap.get(j));
        }
    }
    public static void main(String[] args) {
        
        recomendSystem rs = new recomendSystem();
        DAO dao = new DAO();
        DAOUser daoUser = new DAOUser();
        dao.loadSong();
        daoUser.loadUser();
        
       

        ArrayList<song_HE150883> listSong;
        listSong = dao.filterSongLink();
        System.out.println(listSong.size());
        
       
      
        
        rs.slopeOne( listSong, daoUser.userList,dao.rateList);

        

    }

}
