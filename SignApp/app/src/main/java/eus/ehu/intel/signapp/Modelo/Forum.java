package eus.ehu.intel.signapp.Modelo;

import java.io.Serializable;

/**
 * Created by iubuntu on 31/12/17.
 */

public class Forum implements Serializable{

    private int id;
    private String nick;
    private String password;
    private String resp;
    private String question;
    private int date;

    public Forum() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResp() {
        return this.resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }


}
