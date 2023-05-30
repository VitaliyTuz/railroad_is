package com.example.diplomaaaaaaaaa;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Locomotive {
        private SimpleIntegerProperty id = new SimpleIntegerProperty();
        private SimpleStringProperty name = new SimpleStringProperty("");
        private SimpleStringProperty brigade = new SimpleStringProperty("");
        //    private SimpleStringProperty homeStation = new SimpleStringProperty("");
        private SimpleStringProperty currentStation = new SimpleStringProperty("");
        private SimpleStringProperty arriveTime = new SimpleStringProperty("");
        private SimpleIntegerProperty routeCount = new SimpleIntegerProperty();
        //    private ArrayList<Calendar> technicalInspectionDates;
//    private ArrayList<Calendar> repairDates;
        private SimpleIntegerProperty repairCount = new SimpleIntegerProperty();
        private ObjectProperty<Date> releaseDate = new SimpleObjectProperty<>();       // дата випуску локомотива
        private SimpleIntegerProperty routeCountBeforeRepair = new SimpleIntegerProperty();
        private SimpleStringProperty type = new SimpleStringProperty("");


    Locomotive(Integer id, String name, String brig, String currentStation,
               Integer repairCount, Date releaseDate, String type) {
        this.id.set(id);
        this.name.set(name);
        this.brigade.set(brig);
//        this.homeStation.set(homeStation);
        this.currentStation.set(currentStation);
//        this.arriveTime.set(arriveTime);
//        this.routeCount.set(routeCount);
        this.repairCount.set(repairCount);
        this.releaseDate.set(releaseDate);
//        this.routeCountBeforeRepair.set(routeCountBeforeRepair);
        this.type.set(type);
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public Integer getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBrigade() {
        return brigade.get();
    }

    public void setBrigade(String brigade) {
        this.brigade.set(brigade);
    }

//    public String getHomeStation() {
//        return homeStation.get();
//    }

//    public void setHomeStation(String homeStation) {
//        this.homeStation.set(homeStation);
//    }

    public String getCurrentStation() {
        return currentStation.get();
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation.set(currentStation);
    }

    public String getArriveTime() {
        return arriveTime.get();
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime.set(arriveTime);
    }

    public Integer getRouteCount() {
        return routeCount.get();
    }

    public void setRouteCount(Integer routeCount) {
        this.routeCount.set(routeCount);
    }

//    public ArrayList<Calendar> getTechnicalInspectionDates() {
//        return technicalInspectionDates;
//    }

//    public void setTechnicalInspectionDates(ArrayList<Calendar> technicalInspectionDates) {
//        this.technicalInspectionDates = technicalInspectionDates;
//    }

//    public ArrayList<Calendar> getRepairDates() {
//        return repairDates;
//    }
//
//    public void setRepairDates(ArrayList<Calendar> repairDates) {
//        this.repairDates = repairDates;
//    }

    public Integer getRepairCount() {
        return repairCount.get();
    }

    public void setRepairCount(Integer repairCount) {
        this.repairCount.set(repairCount);
    }

    public Date getReleaseDate() {
        return releaseDate.get();
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public Integer getRouteCountBeforeRepair() {
        return routeCountBeforeRepair.get();
    }

    public void setRouteCountBeforeRepair(Integer routeCountBeforeRepair) {
        this.routeCountBeforeRepair.set(routeCountBeforeRepair);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

//    public String getInfo() {
//        String InspectionDates = "";
//        for (int i = 0; i < technicalInspectionDates.size(); i++) {
//            InspectionDates += technicalInspectionDates.get(i).get(Calendar.DATE) + "." + technicalInspectionDates.get(i).get(Calendar.MONTH) +
//                    "." + technicalInspectionDates.get(i).get(Calendar.YEAR) + " ";
//        }
//        String repDates = "";
//        for (int i = 0; i < repairDates.size(); i++) {
//            repDates += repairDates.get(i).get(Calendar.DATE) + "." + repairDates.get(i).get(Calendar.MONTH) +
//                    "." + repairDates.get(i).get(Calendar.YEAR) + " ";
//        }
//        Calendar calendar = Calendar.getInstance();
//        Integer thisYear = calendar.get(Calendar.YEAR);
//
//        return "Ім'я: " + this.name + " Бригада, що обслуговує: " + this.brigade +
//                " Домашня станція: " + this.homeStation + " Станція, на якій локомотив зараз: " + this.currentStation +
//                " Час прибуття на станцію: " + this.arriveTime + " Кількість скоєних маршрутів: " + this.routeCount +
//                " Дати технічного огляду: " + InspectionDates + " Дати ремонту: " + repDates + " Кількість ремонтів: " + this.repairCount +
//                " Вік: " + (thisYear - this.releaseDate.get(Calendar.YEAR)) + " Кількість маршрутів до ремонту: " + this.routeCountBeforeRepair +
//                " Тип: " + this.type;
//    }
}

