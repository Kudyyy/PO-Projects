package com.danielklodka;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.reflect.annotation.ExceptionProxy;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by klodka on 2016-12-13.
 */
public class Sejm {

    private Vector<Deputy> deputies = new Vector<Deputy>();

    public Sejm(int tenure) throws Exception{
        setDeputiesForTenure(tenure);
    }

    public void setDeputiesForTenure(int tenure) throws Exception{
        JSONArray deputiesArr = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]="+Integer.toString(tenure)).getJSONArray("Dataobject");
        for (int i = 0; i < deputiesArr.length(); i++){
            deputies.add(new Deputy(deputiesArr.getJSONObject(i).getInt("id"),deputiesArr.getJSONObject(i).getJSONObject("data").getString("poslowie.nazwa")));
        }
    }


    public Deputy getDeputy(String name){
        for (Deputy deputy: deputies) {
            if (deputy.name().equals(name)) return deputy;
        }
        System.out.println("Did not find deputy");
        System.exit(1);
        return null;
    }

    public Void updateOutgoingsOfDeputy(Deputy dep) throws Exception{
        JSONObject outgoings = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie/"+dep.id()+".json?layers[]=wyjazdy");
        if (outgoings.getJSONObject("data").getInt("poslowie.liczba_wyjazdow") == 0){
            dep.updateOutgoings(null, 0,0);
            return null;
        }
        JSONArray outArr = outgoings.getJSONObject("layers").getJSONArray("wyjazdy");
        Vector<String> cities = new Vector<String>();
        int days=0;
        double expen = -1;
        for (int i = 0; i < outArr.length(); i++){
            cities.addElement(outArr.getJSONObject(i).getString("kraj"));
            days += outArr.getJSONObject(i).getInt("liczba_dni");
            if(expen< outArr.getJSONObject(i).getDouble("koszt_suma")) expen = outArr.getJSONObject(i).getDouble("koszt_suma");

        }
        dep.updateOutgoings(cities,days,expen);
        return null;
    }

    public void updateOutgoingsOfDeputies() throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(6500);
        List<Callable<Void>> callables = new ArrayList<>(Arrays.asList());
        for(Deputy deputy : deputies){
            callables.add(()->updateOutgoingsOfDeputy(deputy));
        }
        executor.invokeAll(callables);
        executor.shutdown();
    }

    public String visitorsOfCountry(String country) throws Exception{
        updateOutgoingsOfDeputies();
        String names="";
        for (Deputy deputy : deputies){
            if (deputy.hasBeenInCountry(country)) names+= deputy.name()+",";
        }
        if (!names.isEmpty()) return names.substring(0,names.length()-1);
        return names;
    }

    public String biggestTraveller() throws Exception{
        updateOutgoingsOfDeputies();
        String name= "nobody travelled anywhere";
        int numberOfTrips = -1;
        for (Deputy deputy : deputies) {
            if (deputy.numberOfTrips()>numberOfTrips){
                numberOfTrips = deputy.numberOfTrips();
                name = deputy.name();
            }
        }
        return name;
    }

    public String theMostExpensiveTrip() throws Exception{
        updateOutgoingsOfDeputies();
        String name= "nobody travelled anywhere";
        double cost = -1;
        for (Deputy deputy : deputies) {
            if (deputy.expensiveTrip() > cost){
                cost = deputy.expensiveTrip();
                name = deputy.name();
            }
        }
        return name;
    }

    public Void updateSpendingsOfDeputy(Deputy deputy) throws Exception{
        JSONObject spendings = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie/" + deputy.id() + ".json?layers[]=wydatki");
        JSONArray spendArr = spendings.getJSONObject("layers").getJSONObject("wydatki").getJSONArray("roczniki");
        double sum = 0;
        double trifle = 0;
        int years = spendings.getJSONObject("layers").getJSONObject("wydatki").getInt("liczba_rocznikow");
        if (years == 0){
            deputy.updateSpendings(0,0);
            return null;
        }
        for (int i = 0; i<spendArr.length(); i++){
            for (int j = 0; j < spendArr.getJSONObject(i).getJSONArray("pola").length(); j++){
                sum += spendArr.getJSONObject(i).getJSONArray("pola").getDouble(j);
            }
            trifle += spendArr.getJSONObject(i).getJSONArray("pola").getDouble(12);
        }
        deputy.updateSpendings(sum,trifle);
        return null;
    }

    public void updateSpendingsOfDeputies() throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(6500);
        List<Callable<Void>> callables = new ArrayList<>();
        for (Deputy deputy : deputies){
            callables.add(()->updateSpendingsOfDeputy(deputy));
        }
        executor.invokeAll(callables);
        executor.shutdown();
    }

    public double avarageSpendingsOfDeputies() throws Exception{
        double sum=0;
        updateSpendingsOfDeputies();
        for (Deputy deputy : deputies){
            sum += deputy.spendings();
        }
        if (sum == 0) return 0;
        return sum/(deputies.size());
    }

    public double spendingsOnTrifleRedacorationsOfDeputy(String name) throws Exception{
        Deputy deputy = getDeputy(name);
        updateSpendingsOfDeputy(deputy);
        return deputy.trifleSpendings();

    }

    public double spendingsOfDeputy(String name) throws Exception{
        Deputy deputy = getDeputy(name);
        updateSpendingsOfDeputy(deputy);
        return deputy.spendings();

    }

    public String longestStayAbroad() throws Exception{
        updateOutgoingsOfDeputies();
        String resultName = "Nobody travelled anywhere";
        int days = -1;
        for (Deputy deputy : deputies){
            if (deputy.daysAbroad() > days){
                days = deputy.daysAbroad();
                resultName = deputy.name();
            }
        }
        return resultName;

    }

}
