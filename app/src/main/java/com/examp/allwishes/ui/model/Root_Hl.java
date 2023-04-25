package com.examp.allwishes.ui.model;



import java.util.ArrayList;

public class Root_Hl {
    public ArrayList<Event> events;
    public ArrayList<DailyWishe> dailyWishes;

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }


    public ArrayList<DailyWishe> getDailyWishes() {
        return dailyWishes;
    }

    public void setDailyWishes(ArrayList<DailyWishe> dailyWishes) {
        this.dailyWishes = dailyWishes;
    }
}
