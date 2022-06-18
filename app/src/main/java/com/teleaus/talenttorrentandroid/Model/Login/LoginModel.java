package com.teleaus.talenttorrentandroid.Model.Login;

import java.io.Serializable;

public class LoginModel implements Serializable {
   private boolean success;
   private LoginData data;
   private String access_token;

   public LoginModel() {
   }

   public LoginModel(boolean success, LoginData data, String access_token) {
      this.success = success;
      this.data = data;
      this.access_token = access_token;
   }

   public boolean isSuccess() {
      return success;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }

   public LoginData getData() {
      return data;
   }

   public void setData(LoginData data) {
      this.data = data;
   }

   public String getAccess_token() {
      return access_token;
   }

   public void setAccess_token(String access_token) {
      this.access_token = access_token;
   }
}
