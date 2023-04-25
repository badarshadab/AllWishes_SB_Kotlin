package com.examp.allwishes.ui.model;

public class FavoriteData {

    private String fPath;
    private String fQuote;
    private int fColor = 0;
    private boolean isSelected = false;

    public FavoriteData(String fPath) {
        this.fPath = fPath;
    }

    public String getfPath() {
        return fPath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getfQuote() {
        String quotes = fPath;
        int index = quotes.indexOf("#");
        int colorValue = 0;
        if(index != -1){
            fQuote = quotes.substring(0,index);
        }
        System.out.println("getfQuote " + fQuote);
        return fQuote;
    }

    public int getfColor() {
        String quotes = fPath;
        int index = quotes.indexOf("#");
        if(index != -1){
            fColor = Integer.parseInt(quotes.substring(index+1,quotes.length()));
        }
        return fColor;
    }
}
