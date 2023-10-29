package api;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Register {
    private String email;
    private String password;

    public Register() {
        // Пустой конструктор
    }

    public Register(String email, String password) {
        this.email = email;
        this.password = password;
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
}
