# CECS571Project2

This file contains instructions on how to set up Web Semantics Project 2 on IDE

How to download and open the project in IDE:

Using Github Link:
a. Go to Github link "https://github.com/UpasanaGit/CECS571Project2" 
b. Download the ZIP file 
c. Unzip the file 
d. Make sure that you have already installed IDE(IntelliJ", "NetBeans", "Eclipse) on your system
d. Browse the downloaded unzip in any of the IDE "IntelliJ", "NetBeans", "Eclipse"
e. Build the project once all the file uploaded under the project folder
f. Run the project.


*******************************************************************************

Project Description:
We have worked on three datasets related to Healthcare domain namely "Hospital General Information", "Unplanned_Hospital_Visits_-_Hospital" and "Medicare_Spending_Per_Beneficiary___Hospital" in CSV format and converting information into OWL format.

We are using following columns from given respective datasets:


***********Hospital General Information****************
Facility ID, Facility Name, City, State, ZIP Code, Hospital Type , Hospital Ownership, Emergency Services , Hospital overall rating, Phone Number

***********Unplanned_Hospital_Visits_-_Hospital****************
Measure ID, Measure Name, Denominator, Score, Number of Patients, Number of Patients Returned,  Start Date 


***********Medicare_Spending_Per_Beneficiary_Hospital****************
Measure ID, Measure Name, Score , Start date, End Date


Pom.xml

This XML file describes to the IDE platform what your application can do.
It is a required file and it consist of project build tools related to the project like project dependencies and properties etc.

*******************************************************************************

src/*
Under this directory is the Java source for for your application.

Java source directory - 
*********************************************

Controller 

/src/main/java/com/owl/project/hospital/controller/ 


This directory consist of two main java classes and one java class(PrepareExcel.java) to filter out not required data from three of the datasets, one (OntHandler) which is responsible to control reading and writing data from datasets into model and other(OntModelHandler) which is responsible to declare all the Entities and properties and methods to setup initial ontology model and return "ontModel" object of OntModel class.


/src/main/java/com/owl/project/hospital/controller/OntHandler.java
/src/main/java/com/owl/project/hospital/controller/OntModelHandler.java
/src/main/java/com/owl/project/hospital/controller/PrepareExcel.java

*********************************************

model
This directory consist of all the object classes.


CECS571Project2/src/main/java/com/owl/project/hospital/model/HospitalInfo.java


CECS571Project2/src/main/java/com/owl/project/hospital/model/StateInfo.java


CECS571Project2/src/main/java/com/owl/project/hospital/model/VisitInfo.java


*********************************************

Utility directory

CECS571Project2/src/main/java/com/owl/project/hospital/util/UtilityClass.java

This is the utility class for describing all the states for the respective datasets and reading csv file which will return list of data.This file also contains method to get state name from the given state code from csv.Also we are creating collections in this Utility class and using them further in controller java classes.


*******************************************************************************

res/*

Under this directory are the resources for your application.



CECS571Project2/src/main/resources/Hospital_General_Info.csv

CECS571Project2/src/main/resources/Medicare_Spending_Beneficiary.csv


CECS571Project2/src/main/resources/Unplanned_Hospital_Visit.csv	


CECS571Project2/src/main/resources/log4j.properties


These are the datasets which we are using to generating our Ontology

*******************************************************************************


*******************************************************************************

UpasanaGit/CECS571Project2

This is the path for .owl file where it is going to generate after running the project.

Example - UpasanaGit/CECS571Project2


CECS571Project2/MedicareMetaInfo.owl

*******************************************************************************
