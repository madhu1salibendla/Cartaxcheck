package org.cartaxcheck.model;

public class VehicleDetails implements Comparable< VehicleDetails > {
    private String registrationNumber;
    private String make;
    private String model;
    private String color;
    private String year;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public VehicleDetails(String registrationNumber, String make, String model, String color, String year) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    @Override
    public int compareTo(VehicleDetails o) {
       if (this.getRegistrationNumber() != null && o.getRegistrationNumber() != null) {
           return this.getRegistrationNumber().compareTo(o.getRegistrationNumber());
       }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleDetails that = (VehicleDetails) o;

        return registrationNumber != null ? registrationNumber.equals(that.registrationNumber) : that.registrationNumber == null;
    }

    @Override
    public int hashCode() {
        return registrationNumber != null ? registrationNumber.hashCode() : 0;
    }
}
