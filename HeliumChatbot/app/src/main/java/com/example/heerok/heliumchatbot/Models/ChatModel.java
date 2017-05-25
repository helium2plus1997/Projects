package com.example.heerok.heliumchatbot.Models;

/**
 * Created by heerok on 25/5/17.
 */

public class ChatModel {

    public String message;
    public boolean is_send;

    public ChatModel(String message,boolean is_send){
        this.message=message;
        this.is_send=is_send;
    }
    public ChatModel(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return is_send;
    }

    public void setSend(boolean send) {
        is_send = send;
    }
}
