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
    name = "escapedfires",
    indexes = {@Index(columnList = "year", name = "escapeYearIndex")})
public class EscapedFire {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private int id;

  @Column(name = "year")
  private int year;

  @Column(name = "month")
  private Integer month;

  @Column(name = "day")
  private Integer day;

  @Column(name = "name")
  private String name;

  @Column(name = "acres")
  private double acres;

  @Column(name = "latitude")
  private double latitude;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "treatmentType")
  private String treatmentType;

  @Column(name = "countyUnitID")
  private String countyUnitID;

  @Column(name = "counties")
  private String counties;

  @Column(name = "source")
  private String source;

  @Column(name = "owner")
  private String owner;

  public EscapedFire(
      int year,
      Integer month,
      Integer day,
      String name,
      double acres,
      double latitude,
      double longitude,
      String treatmentType,
      String countyUnitID,
      String counties,
      String owner,
      String source) {
    this.year = year;
    this.month = month;
    this.day = day;
    this.name = name;
    this.acres = acres;
    this.latitude = latitude;
    this.longitude = longitude;
    this.treatmentType = treatmentType;
    this.countyUnitID = countyUnitID;
    this.counties = counties;
    this.owner = owner;
    this.source = source;
  }
}
