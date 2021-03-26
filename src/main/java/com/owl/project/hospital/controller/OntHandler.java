/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.project.hospital.controller;

import com.owl.project.hospital.model.VisitInfo;
import com.owl.project.hospital.model.HospitalInfo;
import com.owl.project.hospital.model.StateInfo;
import com.owl.project.hospital.util.UtilityClass;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

/**
 *
 * @author Upasana
 */
public class OntHandler {

    OntModel ontModel;
    static Map<String, HospitalInfo> hospitalMap = new HashMap<String, HospitalInfo>();
    static Map<String, StateInfo> stateMap = new HashMap<String, StateInfo>();

    // to store all (URI, individuals) to reduce searching time for an individual through model.
    public Map<String, Individual> cacheMemory = new HashMap<String, Individual>();

    // constructor to get the initialized ontModel from OntModelHandler class to make further modifications
    public OntHandler() {
        ontModel = new OntModelHandler().getInitialModel();
    }

    // method to write the owl model to a specific path
    private void writeOWLModelToFile() throws IOException {
        System.out.println("[WebSemantics] OntHandler class - inside method writeOWLModelToFile - inTime --- " + new Date().getTime());
        BufferedWriter out = null;
        try {
            Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            Path filePath = root.resolve("MedicareMetaInfo.owl");
            out = new BufferedWriter(new FileWriter(filePath.toString()));
            ontModel.write(out);

            System.out.println("[WebSemantics] OntHandler class - inside method writeOWLModelToFile - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    /* method to check hospital instance in hospitalMap on basis of facilityId and return class object
    Input :- facilityId [String]
    Output :- Object [Hospital]
     */
    private HospitalInfo getHospitalInstance(String facilityId) {
        try {
            return hospitalMap.containsKey(facilityId) ? hospitalMap.get(facilityId) : new HospitalInfo(facilityId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //@author Gayathri
    /* method to check State instance in stateMap on basis of stateCode and return class object
    Input :- stateCode [String]
    Output :- Object [State]
     */
    private StateInfo getStateInstance(String stateCode) {
        try {
            return stateMap.containsKey(stateCode) ? stateMap.get(stateCode) : new StateInfo(stateCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /* method to read data from general information file of hospitals
        set data in the hospitalMap declared global - all the required from the general information file
     */
    private void readGeneralInfo() {
        System.out.println("[WebSemantics] OntHandler class - inside method readGeneralInfo - inTime --- " + new Date().getTime());
        try {
            List<Object> dataList = new UtilityClass().readDataFromCSV("Hospital_General_Info.csv");

            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = (Map<String, Object>) dataList.get(i);

                HospitalInfo hospital = getHospitalInstance(dataMap.get("Facility ID").toString())
                        .name(dataMap.get("Facility Name").toString())
                        .address(dataMap.get("Address").toString())
                        .city(dataMap.get("City").toString())
                        .state(dataMap.get("State").toString())
                        .zipCode(dataMap.get("ZIP Code").toString())
                        .country("USA")
                        .phoneNumber(dataMap.get("Phone Number").toString())
                        .type(dataMap.get("Hospital Type").toString())
                        .ownership(dataMap.get("Hospital Ownership").toString())
                        .hasEmergency(dataMap.get("Emergency Services").toString())
                        .rating(dataMap.get("Hospital overall rating").toString());

                hospitalMap.put(dataMap.get("Facility ID").toString(), hospital);

                StateInfo stateInst = getStateInstance(dataMap.get("State").toString());
                stateMap.put(stateInst.getStateCode(), stateInst);
            }
            System.out.println("[WebSemantics] OntHandler class - inside method readGeneralInfo - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to read data from unplanned visit file of hospitals
        set data in the hospitalMap declared global using Measure ID, Measure Name, Denominator, Number of Patients, Number of Patients Returned
     */
    // @Author Aditi
    private void readUnplannedVisitInfo() {
        System.out.println("[WebSemantics] OntHandler class - inside method readUnplannedVisitInfo - inTime --- " + new Date().getTime());
        try {
            List<Object> dataList = new UtilityClass().readDataFromCSV("Unplanned_Hospital_Visit.csv");

            List<VisitInfo> visitList = new ArrayList<VisitInfo>();

            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataVisitMap = (Map<String, Object>) dataList.get(i);
                String den = dataVisitMap.get("Denominator").toString().trim();
                String patientNum = dataVisitMap.get("Number of Patients").toString().trim();
                String patientNumReturn = dataVisitMap.get("Number of Patients Returned").toString().trim();
                if (!"Not Applicable".equals(patientNum) && !"Not Applicable".equals(patientNumReturn) && !"Not Available".equals(den)) {
                    VisitInfo visitObj = new VisitInfo();
                    visitObj.setDenominator(den);
                    visitObj.setVisitMeasureId(dataVisitMap.get("Measure ID").toString());
                    visitObj.setVisitMeasureName(dataVisitMap.get("Measure Name").toString());

                    String facilityId = dataVisitMap.get("Facility ID").toString();

                    HospitalInfo hospital = getHospitalInstance(facilityId);
                    visitList = hospital.getVisitList();
                    if (visitList == null) {
                        visitList = new ArrayList<VisitInfo>();
                    }
                    visitList.add(visitObj);
                    hospital.setVisitList(visitList);
                    hospitalMap.put(facilityId, hospital);

                    StateInfo stateInst = getStateInstance(dataVisitMap.get("State").toString());
                    stateInst.setPatientNum(patientNum);
                    stateInst.setPatientReturnNum(patientNumReturn);
                    stateMap.put(stateInst.getStateCode(), stateInst);
                }
            }
            System.out.println("[WebSemantics] OntHandler class - inside method readUnplannedVisitInfo - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to read data from beneficiary file of hospitals
        set data in the hospitalMap declared global using Score, Measure ID, Measure Name
     */
    //@author Gayathri
    private void readBeneficiaryInfo() {
        System.out.println("[WebSemantics] OntHandler class - inside method readBeneficiaryInfo - inTime --- " + new Date().getTime());
        try {
            List<Object> dataList = new UtilityClass().readDataFromCSV("Medicare_Spending_Beneficiary.csv");
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> bendataMap = (Map<String, Object>) dataList.get(i);
                String facilityId = bendataMap.get("Facility ID").toString();
                String score = bendataMap.get("Score").toString();
                if (!"Not Available".equals(score)) {
                    HospitalInfo hospital = getHospitalInstance(facilityId);
                    hospital.setScore(score);
                    hospital.setBenMeasureId(bendataMap.get("Measure ID").toString());
                    hospital.setBenMeasureName(bendataMap.get("Measure Name").toString());
                    hospitalMap.put(facilityId, hospital);
                }
            }
            System.out.println("[WebSemantics] OntHandler class - inside method readBeneficiaryInfo - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to add GeneralInfo to model using Individual class for future use in case of queries
    Input:- hospitalList [List<HospitalInfo>] - final list of hospital data to be added in the model
     */
    //@author Upasana
    private void addGeneralInfoToModel(List<HospitalInfo> hospitalList) {
        System.out.println("[WebSemantics] OntHandler class - inside method addGeneralInfoToModel - inTime --- " + new Date().getTime());
        try {
            OntClass hospitalInfo = ontModel.createClass(OntModelHandler.nameSpace + OntModelHandler.Entities.Hospital);
            OntClass stateClass = ontModel.createClass(OntModelHandler.nameSpace + OntModelHandler.Entities.State);
            OntClass countryClass = ontModel.createClass(OntModelHandler.nameSpace + OntModelHandler.Entities.Country);
            OntClass type = ontModel.getOntClass(OntModelHandler.nameSpace + OntModelHandler.Entities.Type);
            OntClass ownership = ontModel.getOntClass(OntModelHandler.nameSpace + OntModelHandler.Entities.Ownership);
            OntClass visit = ontModel.getOntClass(OntModelHandler.nameSpace + OntModelHandler.EntitiesVisits.VisitStats);
            OntClass beneficiary = ontModel.getOntClass(OntModelHandler.nameSpace + OntModelHandler.BeneficiaryEntities.BenStats);

            int dataLen = hospitalList.size();

            for (HospitalInfo h : hospitalList) {
                Individual hospitalInst = hospitalInfo.createIndividual(OntModelHandler.nameSpace + h.getHospitalId());
                Individual stateInst = getIndividualRecord(stateClass, OntModelHandler.nameSpace + h.getState());
                Individual countryInst = countryClass.createIndividual(OntModelHandler.nameSpace + "USA");
                Individual typeInst = type.createIndividual(OntModelHandler.nameSpace + h.getType());
                Individual ownershipInst = ownership.createIndividual(OntModelHandler.nameSpace + h.getOwnershipName());
                Individual benInst = beneficiary.createIndividual(OntModelHandler.nameSpace + h.getHospitalId() + "2018");

                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasFacilityName), h.getHospitalName());
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasFacilityId), h.getHospitalId());
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasEmergencyService), String.valueOf(h.getHasEmergency()));
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasPhoneNumber), h.getPhoneNumber());
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasAddress), h.getAddress());
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasCountry), countryInst);
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasCity), h.getCity());
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasState), stateInst);
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasZipcode), h.getZipcode());
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasOwnership), ownershipInst);
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasType), typeInst);
                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasProperties.hasRating), h.getRating());

                //@author Gayathri
                ontModel.add(benInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.BenificiaryhasProperties.hasBenScore), h.getScore());
                ontModel.add(benInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.BenificiaryhasProperties.hasBenMeasureId), h.getBenMeasureId());
                ontModel.add(benInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.BenificiaryhasProperties.hasBenMeasureName), h.getBenMeasureName());
                ontModel.add(benInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.BenificiaryhasProperties.hasBenYear), "2018");

                //@author Aditi
                List<VisitInfo> vList = h.getVisitList();
                if (vList != null) {
                    int index = 1;
                    for (VisitInfo vInfo : vList) {
                        Individual visitInst = getIndividualRecord(visit, OntModelHandler.nameSpace + h.getHospitalId() + "2016_" + index);
                        ontModel.add(visitInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasVisitProperties.hasVisitYear), "2016");
                        ontModel.add(visitInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasVisitProperties.hasVisitMeasureId), vInfo.getVisitMeasureId());
                        ontModel.add(visitInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasVisitProperties.hasVisitMeasureName), vInfo.getVisitMeasureName());
                        ontModel.add(visitInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasVisitProperties.hasDenominator), vInfo.getDenominator());
                        ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasVisitProperties.hasVisitStats), visitInst);
                        index++;
                    }
                }

                ontModel.add(hospitalInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.BenificiaryhasProperties.hasBenStats), benInst);
                System.out.println("Hospital Info processing -- Remained: " + (--dataLen) + " - Processing facility Id : " + h.getHospitalId());
            }
            System.out.println("[WebSemantics] OntHandler class - inside method addGeneralInfoToModel - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to map total number of patients and number of patients returned to our model according to the state from visit file
           Input:- states [List<StateInfo>]
     */
    //@author Gayathri
    public void addPatientNumber(List<StateInfo> states) {
        OntClass state = ontModel.getOntClass(OntModelHandler.nameSpace + OntModelHandler.Entities.State);
        for (StateInfo st : states) {
            Individual stateInst = ontModel.getIndividual(OntModelHandler.nameSpace + st.getStateCode());
            if (stateInst == null) {
                stateInst = state.createIndividual(OntModelHandler.nameSpace + st.getStateCode());
                stateInst.addComment(new UtilityClass().getStateName(st.getStateCode()), "EN");
            }

            if (st.getPatientNum() != null) {
                ontModel.add(stateInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasVisitProperties.hasNumOfPatients), st.getPatientNum());
            }
            if (st.getPatientReturnNum() != null) {
                ontModel.add(stateInst, ontModel.getProperty(OntModelHandler.nameSpace + OntModelHandler.hasVisitProperties.hasPatientsReturned), st.getPatientReturnNum());
            }
        }
    }

    /* if already created then get instance using Individual 
    Input:- instanceClass [OntClass]
            URI [String]
     */
    private Individual getIndividualRecord(OntClass instanceClass, String URI) {
        try {
            if (!cacheMemory.containsKey(URI)) {
                cacheMemory.put(URI, instanceClass.createIndividual(URI));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cacheMemory.get(URI);
    }

    public static void main(String[] args) {
        OntHandler modelInstance = new OntHandler();
        try {
            // reading general data
            modelInstance.readGeneralInfo();
            //@author Aditi
            // reading unplanned visit data
            modelInstance.readUnplannedVisitInfo();
            //reading beneficiary data
            //@author Gayathri
            modelInstance.readBeneficiaryInfo();

            // @author Upasana
            //adding general info to the data
            modelInstance.addGeneralInfoToModel(new ArrayList<>(modelInstance.hospitalMap.values()));
            //adding patient numbers to the state
            modelInstance.addPatientNumber(new ArrayList<>(modelInstance.stateMap.values()));
            //write the model to the file
            modelInstance.writeOWLModelToFile();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
