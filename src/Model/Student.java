package Model;

import java.io.Serializable;

public class Student implements Serializable{
    private String id;
    private int campusCode;
    private String name;
    private String phone;
    private String email;
    private String mountainCode;
    private double tuitionFee;

    public Student() {
    }

    
    public Student(String id, int campusCode, String name, String phone, String email, String mountainCode, double tuitionFee) {
        this.id = id;
        this.campusCode = campusCode;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.mountainCode = mountainCode;
        this.tuitionFee = calcDiscountFee(phone);
    }
    public double calcDiscountFee(String phone){
        if(phone.matches("^(03[2-9]|08[1-5]|08[8]|09[1-8]|096|097|098)\\d{7}$"))
            return 6000000 * 0.65; 
        else
            return 6000000;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(int campusCode) {
        this.campusCode = campusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.tuitionFee = calcDiscountFee(phone);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMountainCode() {
        return mountainCode;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = mountainCode;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tutionFee) {
        this.tuitionFee = tutionFee;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %.0f", id, name, phone, mountainCode, tuitionFee);
    }
    
    
}
