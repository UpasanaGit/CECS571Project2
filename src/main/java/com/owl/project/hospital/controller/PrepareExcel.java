/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.project.hospital.controller;

import com.owl.project.hospital.util.UtilityClass;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Upasana
 */
public class PrepareExcel {

    public static void main(String[] args) {
        try {
            List<Object> dataList = new UtilityClass().readDataFromCSV("Hospital_General_Information.csv");
            List<Object> visitList = new UtilityClass().readDataFromCSV("Unplanned_Hospital_Visits_Hospital.csv");
            List<Object> benList = new UtilityClass().readDataFromCSV("Medicare_Spending_Per_Beneficiary_Hospital.csv");

            List<String> hList = new ArrayList<>();
            List<String> vList = new ArrayList<>();
            List<String> bList = new ArrayList<>();

            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataVisitMap = (Map<String, Object>) dataList.get(i);
                if (!hList.contains(dataVisitMap.get("Facility ID").toString())) {
                    if (!"Not Available".equals(dataVisitMap.get("Rating"))) {
                        hList.add(dataVisitMap.get("Facility ID").toString());
                    }
                }
            }

            for (int i = 0; i < visitList.size(); i++) {
                Map<String, Object> vMap = (Map<String, Object>) visitList.get(i);
                if (!vList.contains(vMap.get("Facility ID").toString())) {
                    vList.add(vMap.get("Facility ID").toString());
                }
            }

            for (int i = 0; i < benList.size(); i++) {
                Map<String, Object> bMap = (Map<String, Object>) benList.get(i);
                if (!bList.contains(bMap.get("Facility ID").toString())) {
                    bList.add(bMap.get("Facility ID").toString());
                }
            }

            System.out.println(hList.size());
            System.out.println(vList.size());
            System.out.println(bList.size());

            List<Object> finalList = new ArrayList<Object>();

            for (int i = 0; i < hList.size(); i++) {
                boolean vFlag = false;
                boolean bFlag = false;
                System.out.println("Facility Id : " + hList.get(i));
                if (bList.contains(hList.get(i))) {
                    System.out.println("Ben consist this facility id");
                    bFlag = true;
                }
                if (vList.contains(hList.get(i))) {
                    System.out.println("Visit consist this facility id");
                    vFlag = true;
                }
                System.out.println();
                if (vFlag && bFlag) {
                    finalList.add(hList.get(i));
                }
            }

            System.out.println(finalList.size());

            String row = "";
            int count = 0;
            List<String> fileList = new ArrayList<String>();

            BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\Upasana\\Documents\\NetBeansProjects\\WebSemantics\\src\\main\\resources\\Hospital_General_Information.csv"));
            FileWriter csvWriter = new FileWriter("Hospital_General_Info.csv");

            csvWriter.append(csvReader.readLine());
            csvWriter.append("\n");
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                if (finalList.contains(data[0])) {
                    csvWriter.append(row);
                    csvWriter.append("\n");
                    fileList.add(data[0]);
                    count++;
                }
                System.out.println(count);
                if (count == 1000) {
                    break;
                }
            }
            csvReader.close();
            csvWriter.flush();
            csvWriter.close();

            csvReader = new BufferedReader(new FileReader("C:\\Users\\Upasana\\Documents\\NetBeansProjects\\WebSemantics\\src\\main\\resources\\Unplanned_Hospital_Visits_Hospital.csv"));
            csvWriter = new FileWriter("Unplanned_Hospital_Visit.csv");

            csvWriter.append(csvReader.readLine());
            csvWriter.append("\n");
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                if (fileList.contains(data[0])) {
                    csvWriter.append(row);
                    csvWriter.append("\n");
                }
            }
            csvReader.close();
            csvWriter.flush();
            csvWriter.close();

            csvReader = new BufferedReader(new FileReader("C:\\Users\\Upasana\\Documents\\NetBeansProjects\\WebSemantics\\src\\main\\resources\\Medicare_Spending_Per_Beneficiary_Hospital.csv"));
            csvWriter = new FileWriter("Medicare_Spending_Beneficiary.csv");

            csvWriter.append(csvReader.readLine());
            csvWriter.append("\n");
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                if (fileList.contains(data[0])) {
                    csvWriter.append(row);
                    csvWriter.append("\n");
                }
            }
            csvReader.close();
            csvWriter.flush();
            csvWriter.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
