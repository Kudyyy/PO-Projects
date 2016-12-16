package com.danielklodka;
import org.json.JSONObject;

import java.net.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        JSONObject cos =  JSONObjectCreator.getJSONOBjectForURL("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
        //System.out.println(cos.toString());
        //System.out.println(cos.getJSONArray("Dataobject").length());
        System.out.println(Sejm.theMostExpensiveTripInTenure(7));
    }

}

