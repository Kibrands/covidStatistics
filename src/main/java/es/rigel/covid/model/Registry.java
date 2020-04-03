package es.rigel.covid.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Registry {
    private Date date;
    private String countryCode;
    private String countryName;
    private Integer newCases;
    private Integer newDeaths;
    private Integer newRecovered;
}
