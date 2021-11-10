package edu.uci.banerjee.burnserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(
    name = "fires",
    indexes = {
      @Index(columnList = "source", name = "sourceIndex"),
      @Index(columnList = "county", name = "countyIndex"),
      @Index(columnList = "year", name = "yearIndex")
    })
public class Fire {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private int id;

  @Column(name = "acres")
  private double acres;

  @Column(name = "burnType")
  private String burnType;

  @Column(name = "county")
  private String county;

  @Column(name = "latitude")
  private double latitude;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "name")
  private String name;

  @Column(name = "source")
  private String source;

  @Column(name = "year")
  private int year;

  @Column(name = "month")
  private Integer month;

  @Column(name = "day")
  private Integer day;

  @Column(name = "owner")
  private String owner;

  @Column(name = "severity")
  private Double severity;

  public Fire(
      double acres,
      String burnType,
      String county,
      double latitude,
      double longitude,
      String name,
      String source,
      int year,
      Integer month,
      Integer day,
      String owner,
      Double severity) {
    this.acres = acres;
    this.burnType = burnType;
    this.county = county;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
    this.source = source;
    this.year = year;
    this.month = month;
    this.day = day;
    this.owner = owner;
    this.severity = severity;
  }
}
