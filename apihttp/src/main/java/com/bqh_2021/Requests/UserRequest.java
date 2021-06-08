package com.bqh_2021.Requests;

public class UserRequest {
    
    protected String email, nickname, password;
    protected boolean isAdult;

    public UserRequest(String email, String nickname, String password, boolean isAdult){
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.isAdult = isAdult;
    }

    public boolean getIsAdult(){
        return isAdult;
    }

    public void setIsAdult(boolean isAdult){
        this.isAdult = isAdult;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
