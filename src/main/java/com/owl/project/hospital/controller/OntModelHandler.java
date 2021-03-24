/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.project.hospital.controller;

import java.util.Date;
import org.apache.jena.ontology.DataRange;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.XSD;

/**
 *
 * @author Upasana
 */
public class OntModelHandler {

    private String dataURI = "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#";
    public static String nameSpace = "https://data.medicare.gov/d/nrth-mfg3#";

    /*----------------------------------------- ORDER SHOULD BE MAINTAINED IN ALL THE LIST -----------------------------------------     */
    public static enum Entities {
        MedicareMD, Hospital, FacilityId, FacilityName, PhoneNumber, Rating, State, City, Address, Zipcode, Country, EmergencyService, Type, Ownership
    }

    public static enum hasProperties {
        hasFacilityId, hasFacilityName, hasPhoneNumber, hasRating, hasState, hasCity, hasAddress, hasZipcode, hasCountry, hasEmergencyService, hasType, hasOwnership
    }

    public static enum isOfProperties {
        isFacilityIdOf, isFacilityNameOf, isPhoneNumberOf, isRatingOf, isStateOf, isCityOf, isAddressOf, isZipcodeOf, isCountryOf, isEmergencyServiceOf, isTypeOf, isOwnershipOf
    }

    /*----------------------------------------- ORDER SHOULD BE MAINTAINED IN ALL THE LIST ENDS HERE -----------------------------------------     */
    //@author Aditi

    /*----------------------------------------- For VISITS ORDER SHOULD BE MAINTAINED IN ALL THE LIST -----------------------------------------     */
    public static enum EntitiesVisits {
        VisitStats, VisitMeasureId, VisitMeasureName, Denominator, NumOfPatients, PatientsReturned, VisitYear
    }

    public static enum hasVisitProperties {
        hasVisitStats, hasVisitMeasureId, hasVisitMeasureName, hasDenominator, hasNumOfPatients, hasPatientsReturned, hasVisitYear
    }

    public static enum isOfVisitProperties {
        isVisitStatsOf, isVisitMeasureIdOf, isVisitMeasureNameOf, isDenominatorOf, isNumOfPatientsOf, isPatientsReturnedOf, isVisitYearOf
    }

    /*----------------------------------------- FOR VISITS ORDER SHOULD BE MAINTAINED IN ALL THE LIST ENDS HERE -----------------------------------------     */
    ////**** Aditi *****
    //author@ Gayathri
    public static enum BeneficiaryEntities {
        BenStats, BenMeasureId, BenMeasureName, Score, BenYear
    }

    public static enum BenificiaryhasProperties {
        hasBenStats, hasBenMeasureId, hasBenMeasureName, hasBenScore, hasBenYear
    }

    public static enum BeneficiaryisOfProperties {
        isBenStatsOf, isBenMeasureIdOf, isBenMeasureNameOf, isBenScoreOf, isBenYearOf
    }

    private OntModel ontModel;

    // method to setup initial ontology model and return "ontModel" object of OntModel class
    public OntModel getInitialModel() {
        System.out.println("[WebSemantics] OntModelHandler class - inside method getInitialModel - inTime --- " + new Date().getTime());
        try {
            //rule-based reasoner with OWL rules
            ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
            ontModel.setNsPrefix("ds", nameSpace);
            ontModel.setNsPrefix("du", dataURI);
            setUpInitialModel();
            System.out.println("[WebSemantics] OntModelHandler class - inside method getInitialModel - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ontModel;
    }

    //method to set up all the initial classes required to be there in ontology model
    public void setUpInitialModel() {
        try {
            System.out.println("[WebSemantics] OntModelHandler class - inside method setUpInitialModel - inTime --- " + new Date().getTime());

            //medicareMD class will act as canvas to add more elements
            OntClass medicareMD = ontModel.createClass(nameSpace + Entities.MedicareMD);

            OntClass hospitalInfo = ontModel.createClass(nameSpace + Entities.Hospital);
            hospitalInfo.addComment("Hospital class represent the general information including some beneficiary and unplanned visits statistics.", "EN");

            OntClass stateClass = ontModel.createClass(nameSpace + Entities.State);
            stateClass.addComment("Represents the states in the US.", "EN");

            OntClass countryClass = ontModel.createClass(nameSpace + Entities.Country);
            countryClass.addComment("Considering the fedral data of USA for wide variety of inhabitats.", "EN");

            OntClass benStClass = ontModel.createClass(nameSpace + BeneficiaryEntities.BenStats);
            benStClass.addComment("This represents the Beneficiary Year Stats data", "EN");

            OntClass benYearClass = ontModel.createClass(nameSpace + BeneficiaryEntities.BenYear);
            benYearClass.addComment("This represents the Beneficiary Year", "EN");

            //Visit class will act as canvas to add more elements
            //@author Aditi
            OntClass visitClass = ontModel.createClass(nameSpace + EntitiesVisits.VisitStats);
            visitClass.addComment("This represents the unplanned visits of patients ", "EN");

            OntClass visitYearClass = ontModel.createClass(nameSpace + EntitiesVisits.VisitYear);
            visitYearClass.addComment("This represents the unplanned visits of patients ", "EN");

            //add first general info class to the canvas and the one which is gonna common in all
            medicareMD.addSubClass(hospitalInfo);
            medicareMD.addSubClass(stateClass);
            medicareMD.addSubClass(countryClass);

            //intersect classes mapping with the hospital info class
            hospitalInfo.convertToIntersectionClass(ontModel.createList(ontModel.createClass(nameSpace + Entities.FacilityId), ontModel.createClass(nameSpace + Entities.FacilityName),
                    ontModel.createClass(nameSpace + Entities.PhoneNumber), ontModel.createClass(nameSpace + Entities.Ownership), ontModel.createClass(nameSpace + Entities.EmergencyService), ontModel.createClass(nameSpace + Entities.Type)));

            visitYearClass.convertToIntersectionClass(ontModel.createList(ontModel.createClass(nameSpace + hasVisitProperties.hasNumOfPatients), ontModel.createClass(nameSpace + hasVisitProperties.hasPatientsReturned),
                    ontModel.createClass(nameSpace + hasVisitProperties.hasVisitYear), ontModel.createClass(nameSpace + hasVisitProperties.hasDenominator), ontModel.createClass(nameSpace + hasVisitProperties.hasVisitMeasureId),
                    ontModel.createClass(nameSpace + hasVisitProperties.hasVisitMeasureName)));

            benYearClass.convertToIntersectionClass(ontModel.createList(ontModel.createClass(nameSpace + BenificiaryhasProperties.hasBenScore), ontModel.createClass(nameSpace + BenificiaryhasProperties.hasBenMeasureId), ontModel.createClass(nameSpace + BenificiaryhasProperties.hasBenMeasureName)));

            int index = 0;
            for (hasProperties prop : hasProperties.values()) {
                //method call to set up the hasProperty in the model
                ObjectProperty objProp = ontModel.createObjectProperty(nameSpace + prop);
                setHasProperties(ontModel, objProp, prop.name(), hospitalInfo, "HospitalInfo", index);
                index++;
            }

            index = 0;
            for (hasVisitProperties visitProp : hasVisitProperties.values()) {
                //method call to set up the hasVisitProperty in the model
                ObjectProperty objProp = ontModel.createObjectProperty(nameSpace + visitProp);
                String comText = visitProp.name();
                if ("hasVisitStats".equals(comText)) {
                    objProp.addComment("has MedicareMetadata's unplanned visit's", "EN");
                    objProp.addRange(visitClass);
                    objProp.addDomain(medicareMD);
                    ObjectProperty isInverVisit = ontModel.createObjectProperty(nameSpace + isOfVisitProperties.isVisitStatsOf);
                    isInverVisit.addComment(" is MedicareMetadata's unplanned visit", "EN");
                    isInverVisit.addInverseOf(objProp);
                } else {
                    setHasProperties(ontModel, objProp, comText, visitClass, "VisitInfo", index);
                }
                index++;
            }

            index = 0;
            for (BenificiaryhasProperties benprop : BenificiaryhasProperties.values()) {
                //method call to set up the hasBeneficiaryProperty in the model
                ObjectProperty objProp = ontModel.createObjectProperty(nameSpace + benprop);
                String comText = benprop.name();
                if ("hasBenStats".equals(comText)) {
                    objProp.addComment(" has MedicareMetadata's beneficiary data ", "EN");
                    objProp.addRange(benStClass);
                    objProp.addDomain(medicareMD);
                    ObjectProperty isInverBen = ontModel.createObjectProperty(nameSpace + BeneficiaryisOfProperties.isBenStatsOf);
                    isInverBen.addComment(" is MedicareMetadata's beneficiary data ", "EN");
                    isInverBen.addInverseOf(objProp);
                } else {
                    setHasProperties(ontModel, objProp, benprop.name(), benStClass, "beneficiary", index);
                }
                index++;
            }

            System.out.println("[WebSemantics] OntModelHandler class - inside method setUpInitialModel - outTime --- " + new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to setHasProperties in the selected classes, modifies the model object accepts several inputs
    Input :- model [OntModel]
            prop [hasProperties] {enum class object}
            comText [String] - comment text
            hospitalInfo [OntClass] - to use as domain while setting properties
            propOf [String] - mainClass Name in which properties are added
            index [int] - index to use in the loop
     */
    public void setHasProperties(OntModel model, ObjectProperty objProp, String comText, OntClass ontClassObj, String propOf, int index) {
        try {

            objProp.addComment(propOf + " has property " + comText, "EN");
            objProp.addDomain(ontClassObj);
            if ("hasFacilityName".equals(comText) || "hasPhoneNumber".equals(comText) || "hasBenMeasureId".equals(comText) || "hasBenMeasureName".equals(comText) || "hasVisitMeasureId".equals(comText) || "hasVisitMeasureName".equals(comText)) {
                objProp.addRange(XSD.xstring);
                objProp.setRDFType(OWL.FunctionalProperty);
            } else if ("hasFacilityId".equals(comText) || "hasNumOfPatients".equals(comText) || "hasPatientsReturned".equals(comText) || "hasDenominator".equals(comText)) {
                objProp.addRange(XSD.positiveInteger);
                objProp.setRDFType(OWL.FunctionalProperty);
            } else if ("hasEmergencyService".equals(comText)) {
                objProp.addRange(XSD.xboolean);
                objProp.setRDFType(OWL.FunctionalProperty);
            } else if ("hasAddress".equals(comText) || "hasZipcode".equals(comText) || "hasCity".equals(comText) || "hasCountry".equals(comText) || "hasState".equals(comText) || "hasType".equals(comText) || "hasOwnership".equals(comText)) {
                objProp.addRange(ontModel.createClass(nameSpace + Entities.values()[index + 2]));
            } else if ("hasVisitYear".equals(comText)) {
                objProp.addRange(ontModel.createClass(nameSpace + EntitiesVisits.values()[index]));
            } else if ("hasBenYear".equals(comText)) {
                objProp.addRange(ontModel.createClass(nameSpace + BeneficiaryEntities.values()[index]));
            } else if ("hasRating".equals(comText)) {
                objProp.addComment("has ratings from 1-5 for hospitals", "EN");
                Literal lowRating = ontModel.createTypedLiteral(1);
                Literal highRating = ontModel.createTypedLiteral(5);
                DataRange ratingRange = ontModel.createDataRange(ontModel.createList(lowRating, highRating));
                objProp.addRange(ratingRange);
            } else if ("hasBenScore".equals(comText)) {
                objProp.addComment(" has score from 0.45-1.55 for hospitals", "EN");
                Literal lowScore = ontModel.createTypedLiteral(0.45);
                Literal highScore = ontModel.createTypedLiteral(1.55);
                DataRange scoreRange = ontModel.createDataRange(ontModel.createList(lowScore, highScore));
                objProp.addRange(scoreRange);
                objProp.setRDFType(OWL.FunctionalProperty);
            }
            //method call to set up the isOfProperty in the model
            setIsOfProperties(model, objProp, propOf, index, comText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to setIsOfProperties in the selected classes, modifies the model object accepts several inputs
    Input :- model [OntModel]
            hasProp [hasProperties] {enum class object corresponding to isOFProperty}
            propOf [String] - mainClass Name in which properties are added
            index [int] - index to use to get exact object from isOfProperties enum with respect to the hasProperties enum
     */
    public void setIsOfProperties(OntModel model, ObjectProperty objProp, String propOf, int index, String comText) {
        try {
            ObjectProperty objOfProp = null;
            if ("HospitalInfo".equals(propOf)) {
                objOfProp = model.createObjectProperty(nameSpace + isOfProperties.values()[index]);
                objOfProp.addComment(isOfProperties.values()[index].name() + " " + propOf, "EN");

                if ("hasFacilityId".equals(comText) || "hasFacilityName".equals(comText) || "hasPhoneNumber".equals(comText)) {
                    objOfProp.setRDFType(OWL.InverseFunctionalProperty);
                }
            } //@author Aditi
            else if ("VisitInfo".equals(propOf)) {
                objOfProp = model.createObjectProperty(nameSpace + isOfVisitProperties.values()[index]);
                objOfProp.addComment(isOfVisitProperties.values()[index].name() + " " + propOf, "EN");
            } else if ("beneficiary".equals(propOf)) {
                objOfProp = model.createObjectProperty(nameSpace + BeneficiaryisOfProperties.values()[index]);
                objOfProp.addComment(BeneficiaryisOfProperties.values()[index].name() + " " + propOf, "EN");
            }

            objOfProp.addInverseOf(objProp);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
