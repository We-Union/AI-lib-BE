package com.main.model;

import java.util.Objects;

public class User {
    private long id;
    private String username;
    private String nickname;
    private String password;

    public long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Boolean checkPassword(String password)
    {
        return Objects.equals(Encrypt(password), this.password);
    }

    public void setPassword(String password)
    {
        this.password = Encrypt(password);
    }

    private String Encrypt(String password)
    {
        return password;
    }

}