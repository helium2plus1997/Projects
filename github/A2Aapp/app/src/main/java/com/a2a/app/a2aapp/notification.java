package com.a2a.app.a2aapp;

/**
 * Created by Heerok Banerjee on 6/7/2016.
 */
public class notification {

    String notification_id;
    int order_id;
    String ret_id;
    String product_name;
    String contact;
    String address;
    int product_id;
    int price;
    int quantity;
    long total;
    String del_type;

    public void setPrice(int priceq) {
        this.price = priceq;
    }
    public void setOrder_id(int orderid){this.order_id=orderid;}
    public void setContactnum(String cnt){this.contact=cnt;}
    public void setAddress(String addr){this.address=addr;}
    public void setProduct_id(int id){this.product_id=id;}
    public void setDel_type(String s){this.del_type=s;}
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setnoteId(String id) {
        this.notification_id = id;
    }
    public void setretid(String retid){this.ret_id=retid;}
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public void setTotal(long total){this.total=total;}



    public String getnoteId() {
        return notification_id;
    }
    public String getContactnum(){return contact;}
    public String getAddress(){return address;}
    public int getOrder_id(){return order_id;}
    public String getretid(){return ret_id;}
    public String getProduct_name() {
        return product_name;
    }
    public int getProduct_id(){return product_id;}
    public int getPrice(){return price;}
    public long getTotal(){ return total;}
    public String getDel_type(){return del_type;}
    public int getQuantity() {return quantity;}


}
