package com.altimetrik.springsecuritywithjwt.model;

public class TokenExpirationDetail {

   private String msg;
   private String detail;

   public TokenExpirationDetail(String msg, String detail) {
      this.msg = msg;
      this.detail = detail;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public String getDetail() {
      return detail;
   }

   public void setDetail(String detail) {
      this.detail = detail;
   }
}
