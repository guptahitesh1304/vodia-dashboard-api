package com.vodia.api.dashboard.Test;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

  private String id;
  private String quote;

  public Value() {
  }

  public String getId() {
    return this.id;
  }

  public String getQuote() {
    return this.quote;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setQuote(String quote) {
    this.quote = quote;
  }

  @Override
  public String toString() {
    return "Value{" +
        "id=" + id +
        ", quote='" + quote + '\'' +
        '}';
  }
}