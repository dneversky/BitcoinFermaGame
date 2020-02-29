package com.bitcoin.domain;

import com.bitcoin.data.database.Crud;
import com.bitcoin.data.entities.Users;

import java.util.List;

public class Authorize {
    private String email;
    private String password;
    private List<Users> list = Crud.getUsers();

    public Authorize(String email, String password){
        this.email = email;
        this.password = password;
    }

    public boolean auth(){
        return findAccount() && identify();
    }

    public boolean registry(){
        if(findAccount()) {
            Crud.addUser(email, password);
            return true;
        } else {
            return false;
        }
    }

    private boolean findAccount(){
        if(list != null && !list.isEmpty()) {
            for (Users pair : list) {
                if (pair.getEmail().equals(email)) {
                    return false;
                }
            }
        }

        return true;
    }

    public String removeAccount(){
        long id = 0;
        for(Users pair : list){
            if(pair.getEmail().equals(email)){
                if(identify()) {
                    id = pair.getId();
                } else {
                    return "Неверный пароль";
                }
            }
        }
        if(id == 0) return "Аккаунт не найден!";
        return Crud.removeUser(id);
    }

    private boolean identify(){
        List<Users> initList = Crud.getUser(email);
        for(Users pair : initList){
            return pair.getPassword().equals(password);
        }
        return false;
    }
}
