package com.example.medcare;

public class Disease {
    int ID;
    String diseaseName, diseaseInfo;

    public Disease(int ID,String diseaseName, String diseaseInfo) {
        this.ID=ID;
        this.diseaseName = diseaseName;
        this.diseaseInfo = diseaseInfo;
    }

    public Disease() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseInfo() {
        return diseaseInfo;
    }

    public void setDiseaseInfo(String diseaseInfo) {
        this.diseaseInfo = diseaseInfo;
    }
}
