package com.example.diplomaaaaaaaaa;

import javafx.beans.property.*;

import java.util.Calendar;

public class Route {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleIntegerProperty trainID = new SimpleIntegerProperty();
    private SimpleStringProperty departure = new SimpleStringProperty("");
    private SimpleStringProperty arrive = new SimpleStringProperty("");
    private SimpleStringProperty stations = new SimpleStringProperty("");
    private SimpleIntegerProperty ticketCost = new SimpleIntegerProperty();
    private SimpleStringProperty status = new SimpleStringProperty("");                  // активний/скасований/затриманий
    private SimpleStringProperty reason = new SimpleStringProperty("");
//    private SimpleIntegerProperty returnedTickets = new SimpleIntegerProperty();
    private SimpleStringProperty category = new SimpleStringProperty("");                    // туристичний/внутрішній/міжнародний/спеціальний
    private SimpleIntegerProperty seatsNumber = new SimpleIntegerProperty();

    public Route(Integer id, Integer train, String departure, String arrive, String stations, Integer ticketCost, String status, String reason, String category, Integer seatsNumber) {
        this.id.set(id);
        this.trainID.set(train);
        this.departure.set(departure);
        this.arrive.set(arrive);
        this.stations.set(stations);
        this.ticketCost.set(ticketCost);
        this.status.set(status);
        this.reason.set(reason);
        this.category.set(category);
        this.seatsNumber.set(seatsNumber);
    }

    public void setTrainID(int trainID) {
        this.trainID.set(trainID);
    }

    public int getTrainID() {
        return trainID.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
//    public void cancelRoute() {
//        this.status = "скасований";
//    }
//
//    public void delayRoute(String reason, Integer returnedTickets) {
//        this.status = "затриманий";
//        this.reason = reason;
//        this.returnedTickets = returnedTickets;
//    }

    public String getReason() {
        return reason.get();
    }

    public Integer getTrain() {
        return this.trainID.get();
    }

    public void setTrain(Integer train) {
        this.trainID.set(train);
    }

    public String getDeparture() {
        return departure.get();
    }

    public void setDeparture(String departure) {
        this.departure.set(departure);
    }

    public String getArrive() {
        return arrive.get();
    }

    public void setArrive(String arrive) {
        this.arrive.set(arrive);
    }

    public String getStations() {
        return stations.get();
    }

    public void setStations(String stations) {
        this.stations.set(stations);
    }

    public Integer getTicketCost() {
        return ticketCost.get();
    }

    public void setTicketCost(Integer ticketCost) {
        this.ticketCost.set(ticketCost);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public Integer getSeatsNumber() {
        return seatsNumber.get();
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber.set(seatsNumber);
    }

//    public String getInfo() {
//        String res = "Тип поїзда: " + this.train.getType() + " Відправлення: " + this.departure.get(Calendar.DATE) + "." +
//                this.departure.get(Calendar.MONTH) + "." + this.departure.get(Calendar.YEAR) + " " +
//                this.departure.get(Calendar.HOUR) + ":" + this.departure.get(Calendar.MINUTE) +
//                " Прибуття: " + this.arrive.get(Calendar.DATE) + "." + this.arrive.get(Calendar.MONTH) + "." + this.arrive.get(Calendar.YEAR) + " " +
//                this.arrive.get(Calendar.HOUR) + ":" + this.arrive.get(Calendar.MINUTE) + " Початкова станція: " + this.stations.get(0) +
//                " Кінцева станція: " + this.stations.get(stations.size()-1) + " Ціна квитка: " + this.ticketCost +
//                " Статус: " + this.status + " Категрія: " + this.category + " Кількість місць: " + this.seatsNumber;
//        if (this.status.equals("затриманий")) {
//            res += " Причина: " + this.reason + " Кількість зданих квитків: " + this.returnedTickets;
//        }
//
//        return res;
//    }
}
