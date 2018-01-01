package eus.ehu.intel.signapp.Modelo;

/**
 * Created by iubuntu on 31/12/17.
 */


public class User {

    private String nick;
    private String password;


    public User() {
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
}
