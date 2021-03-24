/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.project.hospital.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Upasana
 */
public class UtilityClass {

    public static Map<String, String> stateNameMap = new LinkedHashMap<String, String>();

    // initialize stateMap with the state code and their full name
    static {
        stateNameMap.put("AL", "Alabama");
        stateNameMap.put("AK", "Alaska");
        stateNameMap.put("AB", "Alberta");
        stateNameMap.put("AS", "American Samoa");
        stateNameMap.put("AZ", "Arizona");
        stateNameMap.put("AR", "Arkansas");
        stateNameMap.put("BC", "British Columbia");
        stateNameMap.put("CA", "California");
        stateNameMap.put("CO", "Colorado");
        stateNameMap.put("CT", "Connecticut");
        stateNameMap.put("DE", "Delaware");
        stateNameMap.put("DC", "District Of Columbia");
        stateNameMap.put("FL", "Florida");
        stateNameMap.put("GA", "Georgia");
        stateNameMap.put("GU", "Guam");
        stateNameMap.put("HI", "Hawaii");
        stateNameMap.put("ID", "Idaho");
        stateNameMap.put("IL", "Illinois");
        stateNameMap.put("IN", "Indiana");
        stateNameMap.put("IA", "Iowa");
        stateNameMap.put("KS", "Kansas");
        stateNameMap.put("KY", "Kentucky");
        stateNameMap.put("LA", "Louisiana");
        stateNameMap.put("ME", "Maine");
        stateNameMap.put("MB", "Manitoba");
        stateNameMap.put("MD", "Maryland");
        stateNameMap.put("MA", "Massachusetts");
        stateNameMap.put("MI", "Michigan");
        stateNameMap.put("MN", "Minnesota");
        stateNameMap.put("MP", "Northern Mariana Islands");
        stateNameMap.put("MS", "Mississippi");
        stateNameMap.put("MO", "Missouri");
        stateNameMap.put("MT", "Montana");
        stateNameMap.put("NE", "Nebraska");
        stateNameMap.put("NV", "Nevada");
        stateNameMap.put("NB", "New Brunswick");
        stateNameMap.put("NH", "New Hampshire");
        stateNameMap.put("NJ", "New Jersey");
        stateNameMap.put("NM", "New Mexico");
        stateNameMap.put("NY", "New York");
        stateNameMap.put("NF", "Newfoundland");
        stateNameMap.put("NC", "North Carolina");
        stateNameMap.put("ND", "North Dakota");
        stateNameMap.put("NT", "Northwest Territories");
        stateNameMap.put("NS", "Nova Scotia");
        stateNameMap.put("NU", "Nunavut");
        stateNameMap.put("OH", "Ohio");
        stateNameMap.put("OK", "Oklahoma");
        stateNameMap.put("ON", "Ontario");
        stateNameMap.put("OR", "Oregon");
        stateNameMap.put("PA", "Pennsylvania");
        stateNameMap.put("PE", "Prince Edward Island");
        stateNameMap.put("PR", "Puerto Rico");
        stateNameMap.put("QC", "Quebec");
        stateNameMap.put("RI", "Rhode Island");
        stateNameMap.put("SK", "Saskatchewan");
        stateNameMap.put("SC", "South Carolina");
        stateNameMap.put("SD", "South Dakota");
        stateNameMap.put("TN", "Tennessee");
        stateNameMap.put("TX", "Texas");
        stateNameMap.put("UT", "Utah");
        stateNameMap.put("VT", "Vermont");
        stateNameMap.put("VI", "Virgin Islands");
        stateNameMap.put("VA", "Virginia");
        stateNameMap.put("WA", "Washington");
        stateNameMap.put("WV", "West Virginia");
        stateNameMap.put("WI", "Wisconsin");
        stateNameMap.put("WY", "Wyoming");
        stateNameMap.put("YT", "Yukon Territory");
    }


    /* method to read csv file return List of data consist values in key-value pair
    Input :- fileName [String]
    Output :- List<Object> == List<Map<String,Object>>
     */
    public List<Object> readDataFromCSV(String fileName) {
        System.out.println("[WebSemantics] UtilityClass class - inside method readDataFromCSV - inTime --- " + new Date().getTime());
        List<Object> csvDataList = new ArrayList<Object>();
        try {
            CSVReader openCsvReader = new CSVReaderBuilder(
                    new InputStreamReader(Objects.requireNonNull(getClass()
                            .getClassLoader().getResourceAsStream(fileName)))).build();
            String[] csvData;
            String[] cols = openCsvReader.readNext();
            while ((csvData = openCsvReader.readNext()) != null) {
                Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
                for (int i = 0; i < csvData.length; i++) {
                    dataMap.put(cols[i].trim(), csvData[i].trim());
                }
                csvDataList.add(dataMap);
            }
            System.out.println("[WebSemantics] UtilityClass class - inside method readDataFromCSV - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return csvDataList;
    }

    //method to get state name from the given state code from csv
    public String getStateName(String stateCode) {
        return stateNameMap.get(stateCode);
    }
}
