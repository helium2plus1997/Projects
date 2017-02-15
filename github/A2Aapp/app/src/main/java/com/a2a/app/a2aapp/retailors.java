package com.a2a.app.a2aapp;

/**
 * Created by Heerok Banerjee on 5/19/2016.
 */
public class retailors {

    private String retailor_name;
    private String retailor_addr;
    private String retailor_username;
    private String shop_name;
    private Boolean ischecked=Boolean.FALSE;

    public String getname(){
        return retailor_name;
    }
    public String getShopname(){return shop_name;}
    public String getusername(){
        return retailor_username;
    }
    public String getaddr(){
        return retailor_addr;
    }
    public Boolean getIschecked(){
        return ischecked;
    }


    public void setShopname(String shopname){
        shop_name=shopname;
    }
    public void setname(String name){
        retailor_name=name;
    }
    public void setusername(String username){
        retailor_username=username;
    }
    public void setaddr(String addr){
        retailor_addr=addr;
    }

    public void setchecked(Boolean check){
        ischecked=check;

    }

    }
