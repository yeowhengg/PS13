package com.myapplicationdev.android.id20042741.ps13;

public class AreasForVaccine {

    private String location;
    private String address;
    private String vaccine_type;

    public AreasForVaccine (String location, String address, String vaccine_type){
        this.location = location;
        this.address = address;
        this.vaccine_type = vaccine_type;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getVaccine_type() {
        return vaccine_type;
    }

    public String toString(){
        return String.format("Location: %s\n Address: %s\n Vaccine Type: %s\n", location, address, vaccine_type);
    }
}
