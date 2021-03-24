/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.project.hospital.model;

/**
 *
 * @author Aditi
 */
// model class for State info - getter and setter methods
public class StateInfo {

    String stateCode;
    String patientNum;
    String patientReturnNum;

    public StateInfo(String stateCd) {
        this.stateCode = stateCd;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPatientNum() {
        return patientNum;
    }

    public void setPatientNum(String patientNum) {
        this.patientNum = patientNum;
    }

    public String getPatientReturnNum() {
        return patientReturnNum;
    }

    public void setPatientReturnNum(String patientReturnNum) {
        this.patientReturnNum = patientReturnNum;
    }

    public static StateInfo createObj(String abbr) {
        return new StateInfo(abbr);
    }

    public void setPatientsNum(String patients) {
        this.patientNum = patients;
    }

    public void setPatientsReturnNum(String patientReturn) {
        this.patientReturnNum = patientReturn;
    }
}
