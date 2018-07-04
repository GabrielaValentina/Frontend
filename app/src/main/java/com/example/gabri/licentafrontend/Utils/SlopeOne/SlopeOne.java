package com.example.gabri.licentafrontend.Utils.SlopeOne;

import android.util.Log;

import com.example.gabri.licentafrontend.Domain.Landmark;
import com.example.gabri.licentafrontend.Domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gabri on 5/20/2018.
 */

public class SlopeOne {

    /*
    //Long -> id_user; Map<Long, String> -> <id_landmark, appreciation>
    private Map<Long, Map<Long, Double>> data;
    //Differences matrix
    private Map<Long, Map<Long, Double>> differencesMatrix;
    //Frequencies matrix
    private Map<Long, Map<Long, Integer>> frequenciesMatrix;
    List<Landmark> landmarkList;

    public SlopeOne(){
        data = new HashMap<>();
        differencesMatrix = new HashMap<>();
        frequenciesMatrix = new HashMap<>();
        //get all data from server

        //get all hardcode data
        populate_list();


    }

    private void build_matrices() {
        //iterate through users
        for(Map<Long, Double> user : data.values()){
            //iterate throught user data
            for(Map.Entry<Long, Double> entry : user.entrySet()){
                Long landmark1 = entry.getKey();
                double rating1 = entry.getValue();

                //verify if it not exist in differences matrix
                if(!differencesMatrix.containsKey(landmark1)){
                    //create new entry
                    differencesMatrix.put(landmark1, new HashMap<Long, Double>());
                    frequenciesMatrix.put(landmark1, new HashMap<Long, Integer>());
                }

                //compare the ratings of all landmarks
                for(Map.Entry<Long, Double> entry2 : user.entrySet()){
                    Long landmark2 = entry2.getKey();
                    double rating2 = entry2.getValue();

                    int count = 0;
                    if(frequenciesMatrix.get(landmark1).containsKey(landmark2))
                        count = frequenciesMatrix.get(landmark1).get(landmark2);

                    double old_difference = 0.0;
                    if(differencesMatrix.get(landmark1).containsKey(landmark2))
                        old_difference = differencesMatrix.get(landmark1).get(landmark2);

                    double new_difference = rating1 - rating2;

                    //update matrics

                    differencesMatrix.get(landmark1).put(landmark2, old_difference + new_difference);
                   Log.d("diff -> ",differencesMatrix.get(landmark1).get(landmark2) + "");
                    frequenciesMatrix.get(landmark1).put(landmark2, count + 1); //+1 in case that one user rated
                     Log.d("freq -> ",  frequenciesMatrix.get(landmark1).get(landmark2)+"");
                    //the item before.
                }
            }
        }

        //calculating the similarity scores inside the matrices
        for(Long user : differencesMatrix.keySet()){
            for(Long landmark : differencesMatrix.get(user).keySet()){
                double value = differencesMatrix.get(user).get(landmark);
                int count = frequenciesMatrix.get(user).get(landmark).intValue();
                //dividing landmark rating's difference by the number of its occurrences.
                differencesMatrix.get(user).put(landmark, value / count);
            }
        }
        print_data();
    }

    public void populate_list(){
        //landmarks list
       landmarkList = new ArrayList<>();
        long id = 1;
        Landmark landmark1 = new Landmark(id,"castel",46.1667, 24.35, "Medias",1);
        id = 2;
        Landmark landmark2 = new Landmark(id,"camp",46.1667, 25.35, "Copsa-Mica",1);
        id = 3;
        Landmark landmark3 = new Landmark(id,"padure",43.1667, 24.35, "Sibiu",1);
        id = 4;
        Landmark landmark4 = new Landmark(id,"castel",44.1667, 24.35, "Mures",1);
        id = 5;
        Landmark landmark5 = new Landmark(id,"lac",46.1667, 24.35, "Medias",1);
        landmarkList.add(landmark1); landmarkList.add(landmark2); landmarkList.add(landmark3);
        landmarkList.add(landmark4); landmarkList.add(landmark5);

        //users list
        List<User> userList = new ArrayList<>();
        id = 1;
        User user = new User(id, "Ana", "Maria", "ana@yahoo.com", "ana", "0743107867");
        userList.add(user);
        id = 2;
        user = new User(id, "Alex", "Ion", "ana@yahoo.com", "ana", "0743107867");
        userList.add(user);
        id = 3;
        user = new User(id, "Maria", "Pop", "ana@yahoo.com", "ana", "0743107867");
        userList.add(user);
        id = 4;
        user = new User(id, "Gabriela", "Valentina", "ana@yahoo.com", "ana", "0743107867");
        userList.add(user);

        //populate map
        //user 1
        Map<Long, Double> landmark_appreciation_map = new HashMap<>();
        landmark_appreciation_map.put(landmarkList.get(0).getId_landmark(), 10.00);
        landmark_appreciation_map.put(landmarkList.get(1).getId_landmark(), 0.00);
        landmark_appreciation_map.put(landmarkList.get(2).getId_landmark(), 0.00);
        landmark_appreciation_map.put(landmarkList.get(3).getId_landmark(), 10.00);
        data.put(userList.get(0).getId(), landmark_appreciation_map);

        //user 2
         landmark_appreciation_map = new HashMap<>();
        landmark_appreciation_map.put(landmarkList.get(0).getId_landmark(), 10.00);
        landmark_appreciation_map.put(landmarkList.get(1).getId_landmark(), 10.00);
        landmark_appreciation_map.put(landmarkList.get(2).getId_landmark(), 10.00);
        landmark_appreciation_map.put(landmarkList.get(3).getId_landmark(), 10.00);
        landmark_appreciation_map.put(landmarkList.get(4).getId_landmark(), 0.00);
        data.put(userList.get(1).getId(), landmark_appreciation_map);

        //user 3
        landmark_appreciation_map = new HashMap<>();
        landmark_appreciation_map.put(landmarkList.get(2).getId_landmark(), 10.00);
        landmark_appreciation_map.put(landmarkList.get(4).getId_landmark(), 10.00);
        data.put(userList.get(2).getId(), landmark_appreciation_map);

        //user 4
        landmark_appreciation_map = new HashMap<>();
        landmark_appreciation_map.put(landmarkList.get(3).getId_landmark(), 0.00);
        data.put(userList.get(3).getId(), landmark_appreciation_map);

        build_matrices();
    }

    public void print_data(){
        for(Long user : data.keySet()){
            Log.d("user - > " , user + "");
            for(Long landmark : data.get(user).keySet())
                Log.d("landmark - > ", landmark + " " + data.get(user).get(landmark).floatValue());
        }
        for(int i=0; i< landmarkList.size(); i++){
            Log.d("landmark -> " , landmarkList.get(i).getLocation()   + "size -> " + landmarkList.size());
            Log.d("differencesMatrix -> ", differencesMatrix.size() +"");
            Log.d("differencesMatrix -> ", differencesMatrix.values().contains(landmarkList.get(i))+"");
          //  printMatrixes(differencesMatrix.get(landmarkList.get(i)), frequenciesMatrix.get(landmarkList.get(i)));
        }
    }

    private void printMatrixes(Map<Long, Double> ratings, Map<Long, Integer> frequencies) {
        Log.d("pMat ->" , landmarkList.size()+"");
     //   Log.d(" rat ->" , ratings.size() + "  ");
       // Log.d("freq", ""+ frequencies.size());
        if(!ratings.isEmpty() && !frequencies.isEmpty()) {
            for (int i = 0; i < landmarkList.size(); i++) {
                Log.d("ratings : ", ratings.get(landmarkList.get(i)) + "");
                Log.d("frequencies : ", frequencies.get(landmarkList.get(i)) + "");
            }
        }
    }

    */
}
