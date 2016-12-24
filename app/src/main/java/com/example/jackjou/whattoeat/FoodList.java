package com.example.jackjou.whattoeat;

/**
 * Created by JackJou on 2016/12/1.
 */

public class FoodList {

    /*名字*/
    private String name;
    /*備註*/
    private String remark;

    public FoodList(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String sex){
        this.remark = remark;
    }

}
