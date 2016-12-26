package com.example.jackjou.whattoeat;

/**
 * Created by JackJou on 2016/12/1.
 */

public class FoodList {

    private int id;
    /*名字*/
    private String name;
    /*備註*/
    private String note;

    public FoodList(int id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNote(){
        return note;
    }

    public void setRemark(String note){
        this.note = note;
    }

}
