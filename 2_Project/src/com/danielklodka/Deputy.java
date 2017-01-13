package com.danielklodka;

import java.util.Vector;

/**
 * Created by daniel on 29.12.2016.
 */
public class Deputy {

    private final int id;
    private final String name;
    private int daysAbroad=-1;
    private Vector<String> cities = new Vector<String>();
    private double trifleSpendings;
    private double spendings;
    private double expensiveTrip;


    public Deputy(int i,String nam){
        id = i;
        name = nam;
    }

    public void updateOutgoings(Vector<String> cits, int num, double most){
        if (cits == null) cities = new Vector<String>();
        else cities = new Vector<String>(cits);
        daysAbroad = num;
        expensiveTrip = most;
    }
    public void updateSpendings(double all,double trifle){
        trifleSpendings = trifle;
        spendings = all;
    }

    public String name(){
        return name;
    }
    public int id(){
        return id;
    }

    public boolean hasBeenInCountry(String city){
        if (cities.isEmpty()) return false;
        return cities.contains(city);
    }

    public int numberOfTrips(){
        if (cities.isEmpty()) return 0;
        return cities.size();
    }

    public int daysAbroad(){
        return daysAbroad;
    }
    public double expensiveTrip(){
        return expensiveTrip;
    }
    public double spendings(){
        return spendings;
    }
    public double trifleSpendings(){
        return trifleSpendings;
    }

}
