package com.example.diplomaaaaaaaaa;

import java.util.ArrayList;

public class Schedule {
    private ArrayList<Route> routes;
    public Schedule(ArrayList<Route> routes) {
        this.routes = routes;
    }
    public void addRoute(Route route) {
        this.routes.add(route);
    }
    public ArrayList<Route> getRoutes() {
        return routes;
    }
    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }
//    public String getInfo() {
//        String res = "";
//        for (int i = 0; i < this.routes.size(); i++) {
//            res += (i+1) + " " + this.routes.get(i).getInfo() + "\n";
//        }
//        return res;
//    }
}

