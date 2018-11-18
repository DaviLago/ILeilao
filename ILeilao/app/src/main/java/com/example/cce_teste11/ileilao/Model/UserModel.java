package com.example.cce_teste11.ileilao.Model;

public class UserModel {

    public static final String PARTICIPANTE = "participante";
    public static final String LEILOEIRO = "leiloeiro";

    private String name;
    private String email;
    private String password;
    private String type;

    private static UserModel logged_user;

    public UserModel(String name, String email, String password, String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public UserModel(String email, String password, String type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public UserModel(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static UserModel getLogged_user() {
        return logged_user;
    }

    public static void setLogged_user(UserModel logged_user) {
        UserModel.logged_user = logged_user;
    }
}
