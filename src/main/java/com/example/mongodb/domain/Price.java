package com.example.mongodb.domain;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hcj on 18-8-3
 */
@Setter
@Getter
@ToString
public class Price {
  private Double retail;
  // 廉价出售
  private Double sale;
  private Instant start  = Instant.now();
  private Instant end  = Instant.now();
  public Price(){System.out.println("无参");}
  public Price(Double retail, Double sale) {
    this.retail = retail;
    this.sale = sale;
  }

  public Price(Double retail, Double sale, Instant start, Instant end) {
    this.retail = retail;
    this.sale = sale;
    this.start = start;
    this.end = end;
  }
    public   static  void main(String[] args){
      Price price = new Price(1d, 2d);
      System.out.println(price);

    }
}
