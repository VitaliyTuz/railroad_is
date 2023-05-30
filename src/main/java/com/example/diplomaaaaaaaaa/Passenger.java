package com.example.diplomaaaaaaaaa;

import java.util.Calendar;

public class Passenger {            // НЕ ПОТРІБНИЙ
    private boolean baggagePlaceUse;
    private String name;
    private String gender;
    private Integer age;
    private Calendar borderCrossingDate;

    public Passenger(boolean baggagePlaceUse, String name, String gender, Integer age) {
        this.baggagePlaceUse = baggagePlaceUse;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Passenger(boolean baggagePlaceUse, String name, String gender, Integer age, Calendar borderCrossingDate) {
        this.baggagePlaceUse = baggagePlaceUse;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.borderCrossingDate = borderCrossingDate;
    }

    public boolean isBaggagePlaceUse() {
        return baggagePlaceUse;
    }

    public void setBaggagePlaceUse(boolean baggagePlaceUse) {
        this.baggagePlaceUse = baggagePlaceUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Calendar getBorderCrossingDate() {
        return borderCrossingDate;
    }

    public void setBorderCrossingDate(Calendar borderCrossingDate) {
        this.borderCrossingDate = borderCrossingDate;
    }

    public String getInfo() {
        String res = "Ім'я: " + this.name + " Стать: " + this.gender + " Вік: " + this.age + " Чи здані речі в багажне відділення: ";

        if (baggagePlaceUse) {
            res += "так";
        } else {
            res += "ні";
        }

        if (borderCrossingDate != null) {
            res += " Дата перетину кордону: " + this.borderCrossingDate.get(Calendar.DATE) + "." +
                    this.borderCrossingDate.get(Calendar.MONTH) + "." + this.borderCrossingDate.get(Calendar.YEAR);
        }

        return res;
    }
}

