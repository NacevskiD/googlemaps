package com.david;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    static Scanner stringScanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception{

        String key = null;
        try(BufferedReader reader = new BufferedReader(new FileReader("key.txt"))){
            key = reader.readLine();
            System.out.println(key);
        }catch (Exception ioe){
            System.out.println("No key found.");
            System.exit(-1);
        }
        System.out.println("Enter the name of a place");
        String place = stringScanner.nextLine();
        GeoApiContext context = new GeoApiContext().setApiKey(key);

        GeocodingResult[] results = GeocodingApi.geocode(context,place).await();
        LatLng mctcLatLng = new LatLng(results[0].geometry.location.lat,results[0].geometry.location.lng);
        ElevationResult[] elevationResults = ElevationApi.getByPoints(context,mctcLatLng).await();


        if (results.length >=1){
            ElevationResult mctcElevation = elevationResults[0];
            System.out.println(String.format("The elevation of " + results[0].formattedAddress + " is %.2f meters above sea level.",mctcElevation.elevation));
        }




	// write your code here
        stringScanner.close();
    }
}
