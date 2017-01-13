package com.danielklodka;
import org.json.JSONObject;

import java.net.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        for (String arg:args) System.out.println(arg);
        if (args.length < 2) System.exit(2);
        if(args[0].matches("[0-9]") && args[1].matches("[0-9]")) {
            Sejm sejm = new Sejm(Integer.parseInt(args[1]));
            switch (Integer.parseInt(args[0])){
                case 1:
                    if (args.length != 3) System.exit(2);
                    System.out.println(sejm.spendingsOfDeputy(args[2]));
                    break;
                case 2:
                    if (args.length != 3) System.exit(2);
                    System.out.println(sejm.spendingsOnTrifleRedacorationsOfDeputy(args[2]));
                    break;
                case 3:
                    System.out.println(sejm.avarageSpendingsOfDeputies());
                    break;
                case 4:
                    System.out.println(sejm.biggestTraveller());
                    break;
                case 5:
                    System.out.println(sejm.longestStayAbroad());
                    break;
                case 6:
                    System.out.println(sejm.theMostExpensiveTrip());
                    break;
                case 7:
                    System.out.println(sejm.visitorsOfCountry("Włochy"));
                    break;

            }

        }
    }

}

