package com.danielklodka;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.reflect.annotation.ExceptionProxy;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;


/**
 * Created by klodka on 2016-12-13.
 */
public class Sejm {

    public static String idsOfAllDeputiesInTenure(int tenure) throws Exception {
        JSONObject deputies = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]="+Integer.toString(tenure));
        JSONArray depArr = deputies.getJSONArray("Dataobject");
        String IDs = "";
        for (int i = 0; i < depArr.length(); i++){
            IDs += Integer.toString(depArr.getJSONObject(i).getInt("id"))+"-"+depArr.getJSONObject(i).getJSONObject("data").getString("poslowie.nazwa")+",";
        }
        if (!IDs.isEmpty()) return IDs.substring(0,IDs.length()-1);
        return IDs;
    }

    public static int deputyIDInTenure(String name,int tenure) throws Exception{
        JSONObject deputies = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]="+Integer.toString(tenure));
        JSONArray depArr = deputies.getJSONArray("Dataobject");
        for (int i = 0; i < depArr.length(); i++){
            if (depArr.getJSONObject(i).getJSONObject("data").getString("poslowie.nazwa").equals(name)) return depArr.getJSONObject(i).getInt("id");
        }
        return -1;
    }

    private static String outgoingsOfDeputyInTenure(int ID,int tenure) throws Exception{
        JSONObject outgoings = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie/"+Integer.toString(ID)+".json?layers[]=wyjazdy");
        if (outgoings.getJSONObject("data").getInt("poslowie.liczba_wyjazdow") == 0) return "";
        JSONArray outArr = outgoings.getJSONObject("layers").getJSONArray("wyjazdy");
        String outStr ="";
        for (int i = 0; i < outArr.length(); i++){
            outStr += outArr.getJSONObject(i).getString("kraj")+",";
        }
        if (!outStr.isEmpty()) return outStr.substring(0,outStr.length()-1);
        return outStr;
    }

    public static String outgoingsOfDeputyInTenure(String name,int tenure) throws Exception{
        int deputyID = deputyIDInTenure(name,tenure);
        if (deputyID==-1) return "Deputy does not exist";
        return outgoingsOfDeputyInTenure(deputyID,tenure);
    }

    public static String visitorsOfCountryInTenure(String country,int tenure) throws Exception{
        String IDs = idsOfAllDeputiesInTenure(tenure);
        String names = "";
        for (String id : IDs.split(",")){
            String wyjazdy = outgoingsOfDeputyInTenure(Integer.parseInt(id.split("-")[0]),tenure);
            for (String wyjazd: wyjazdy.split(",")) {
                if (wyjazd.equals(country)) names += id.split("-")[1]+",";
            }
        }

        if (!names.isEmpty()) return names.substring(0,names.length()-1);
        return names;
    }

    public static String biggestTravellerInTenure(int tenure) throws Exception{
        String IDs = idsOfAllDeputiesInTenure(tenure);
        String name="";
        int numberOfTrips = -1;
        for (String id : IDs.split(",")) {
            JSONObject outgoings = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie/" + id.split("-")[0] + ".json?layers[]=wyjazdy");
            if (numberOfTrips < outgoings.getJSONObject("data").getInt("poslowie.liczba_wyjazdow")){
                numberOfTrips = outgoings.getJSONObject("data").getInt("poslowie.liczba_wyjazdow");
                name = id.split("-")[1];
            }
        }
        return name;
    }

    public static String theMostExpensiveTripInTenure(int tenure) throws Exception{
        String IDs = idsOfAllDeputiesInTenure(tenure);
        String name="";
        double cost = -1;
        for (String id : IDs.split(",")) {
            JSONObject outgoings = JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie/" + id.split("-")[0] + ".json?layers[]=wyjazdy");
            if (outgoings.getJSONObject("data").getInt("poslowie.liczba_wyjazdow") == 0) continue;
            JSONArray outArr = outgoings.getJSONObject("layers").getJSONArray("wyjazdy");
            for (int i = 0 ; i < outArr.length() ; i++){
                if(cost< outArr.getJSONObject(i).getDouble("koszt_suma")){
                    cost = outArr.getJSONObject(i).getDouble("koszt_suma");
                    name = id.split("-")[1];
                }
            }
        }
        return name;
    }
}
