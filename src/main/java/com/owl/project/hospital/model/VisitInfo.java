/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.project.hospital.model;

/**
 *
 * @author Gayathri
 */

// model class for unplanned visit info - getter and setter methods
public class VisitInfo {

    public String denominator = "",
            visitMeasureId = "",
            visitMeasureName = "";

    public String getDenominator() {
        return denominator;
    }

    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }

    public String getVisitMeasureId() {
        return visitMeasureId;
    }

    public void setVisitMeasureId(String visitMeasureId) {
        this.visitMeasureId = visitMeasureId;
    }

    public String getVisitMeasureName() {
        return visitMeasureName;
    }

    public void setVisitMeasureName(String visitMeasureName) {
        this.visitMeasureName = visitMeasureName;
    }

    @Override
    public String toString() {
        return "BenInfo{" + "denominator=" + denominator + ", visitMeasureId=" + visitMeasureId + ", visitMeasureName=" + visitMeasureName + '}';
    }

}
