package com.example.diplomaaaaaaaaa;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

public class Ticket {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty routeID = new SimpleIntegerProperty();
    private SimpleIntegerProperty price = new SimpleIntegerProperty();
    private SimpleStringProperty isStudent = new SimpleStringProperty("");
    private SimpleStringProperty baggagePlaceUse = new SimpleStringProperty("");    // Чи здані речі в багажне відділення


    public Ticket(Integer id, Integer routeID, Integer price, String isStudent, String baggagePlaceUse) {
        this.id.set(id);
        this.routeID.set(routeID);
        this.price.set(price);
        this.isStudent.set(isStudent);
        this.baggagePlaceUse.set(baggagePlaceUse);
    }

    public String getBaggagePlaceUse() {
        return baggagePlaceUse.get();
    }

    public void setBaggagePlaceUse(String baggagePlaceUse) {
        this.baggagePlaceUse.set(baggagePlaceUse);
    }

    public String getIsStudent() {
        return isStudent.get();
    }

    public void setIsStudent(String isStudent) {
        this.isStudent.set(isStudent);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getRouteID() {
        return routeID.get();
    }

    public void setRouteID(int routeID) {
        this.routeID.set(routeID);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    //    public String getInfo() {
//        return "Дата продажу: " + this.date.get(Calendar.DATE) + "." + this.date.get(Calendar.MONTH) + "." + this.date.get(Calendar.YEAR) +
//                " Ціна: " + this.route.getTicketCost() + " Початкова точка маршруту: " + this.route.getStations().get(0) +
//                " Кінцева точка маршруту: " + this.route.getStations().get(this.route.getStations().size()-1) +
//                " Дата відправлення: " + this.route.getDeparture().get(Calendar.DATE) + "." + this.route.getDeparture().get(Calendar.MONTH) + "."
//                + this.route.getDeparture().get(Calendar.YEAR) + " Ім'я пасажира: " + this.passenger.getName();
//    }
}

