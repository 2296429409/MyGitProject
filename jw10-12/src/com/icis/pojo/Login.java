package com.icis.pojo;


public class Login {

  private String user;
  private String password;
  private Integer lId;

  public Login() {
  }

  public Login(String user, String password, Integer lId) {
    this.user = user;
    this.password = password;
    this.lId = lId;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Integer getLId() {
    return lId;
  }

  public void setLId(Integer lId) {
    this.lId = lId;
  }

}
