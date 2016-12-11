package com.example.jackjou.whattoeat;

/**
 * Created by JackJou on 2016/12/1.
 */

public class FoodList {

    /*名字*/
    private String name;
    /*性别*/
    private String sex;

    public FoodList(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSex(){
        return sex;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

}
