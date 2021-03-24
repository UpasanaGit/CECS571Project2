package com.owl.project.hospital.model;

import java.util.List;

/**
 *
 * @author Gayathri
 */
// model class for hospital info - getter and setter methods
public class HospitalInfo {

    public String hospitalId,
            hospitalName = "",
            ownershipName = "",
            type = "",
            zipcode = "",
            address = "",
            state = "",
            country = "USA",
            city = "",
            phoneNumber = "",
            hasEmergency = "",
            rating = "",
            score = "",
            avgPatients = "",
            benMeasureId = "",
            benMeasureName = "";

    public List<VisitInfo> visitList;

    public List<VisitInfo> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<VisitInfo> visitList) {
        this.visitList = visitList;
    }

    public HospitalInfo(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public static HospitalInfo create(String hospitalId) {
        return new HospitalInfo(hospitalId);
    }

    public HospitalInfo name(String n) {
        this.hospitalName = n;
        return this;
    }

    public HospitalInfo hasEmergency(String b) {
        this.hasEmergency = b;
        return this;
    }

    public HospitalInfo zipCode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public HospitalInfo address(String address) {
        this.address = address;
        return this;
    }

    public HospitalInfo state(String state) {
        this.state = state;
        return this;
    }

    public HospitalInfo country(String country) {
        this.country = country;
        return this;
    }

    public HospitalInfo city(String city) {
        this.city = city;
        return this;
    }

    public HospitalInfo avgPatients(String avgPatients) {
        this.avgPatients = avgPatients;
        return this;
    }

    public HospitalInfo score(String score) {
        this.score = score;
        return this;
    }

    public HospitalInfo rating(String rating) {
        this.rating = rating;
        return this;
    }

    public HospitalInfo type(String type) {
        this.type = type;
        return this;
    }

    public HospitalInfo ownership(String ownershipName) {
        this.ownershipName = ownershipName;
        return this;
    }

    public HospitalInfo phoneNumber(String number) {
        this.phoneNumber = number;
        return this;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBenMeasureId() {
        return benMeasureId;
    }

    public void setBenMeasureId(String benMeasureId) {
        this.benMeasureId = benMeasureId;
    }

    public String getBenMeasureName() {
        return benMeasureName;
    }

    public void setBenMeasureName(String benMeasureName) {
        this.benMeasureName = benMeasureName;
    }

    public String getOwnershipName() {
        return ownershipName;
    }

    public void setOwnershipName(String ownershipName) {
        this.ownershipName = ownershipName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvgPatients() {
        return avgPatients;
    }

    public void setAvgPatients(String avgPatients) {
        this.avgPatients = avgPatients;
    }

    public String getHasEmergency() {
        return hasEmergency;
    }

    public void setHasEmergency(String hasEmergency) {
        this.hasEmergency = hasEmergency;
    }

    @Override
    public String toString() {
        return "Hospital{"
                + "hospitalId='" + hospitalId + '\''
                + ", name='" + hospitalName + '\''
                + ", score='" + score + '\''
                + ", rating='" + rating + '\''
                + ", ownershipName='" + ownershipName + '\''
                + ", type='" + type + '\''
                + ", zipcode='" + zipcode + '\''
                + ", address='" + address + '\''
                + ", state='" + state + '\''
                + ", country='" + country + '\''
                + ", city='" + city + '\''
                + ", avgPatients='" + avgPatients + '\''
                + ", benMeasureId='" + benMeasureId + '\''
                + ", benMeasureName='" + benMeasureName + '\''
                + ", hasEmergency=" + hasEmergency
                + '}';
    }

}
