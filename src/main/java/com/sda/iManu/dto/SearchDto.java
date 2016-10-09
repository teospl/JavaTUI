package com.sda.iManu.dto;


import com.sda.iManu.domain.Access;
import com.sda.iManu.domain.Country;
import com.sda.iManu.domain.Travel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by teos on 2016-09-28.
 */
@Getter
@Setter
@ToString
public class SearchDto {

    private List<Country> countries = new LinkedList<>();

    private String selectedCountry;

    private List<Access> accesses = new LinkedList<>();

    private String selectedAccess;

    private String dateFrom;

    private String dateTo;

    private String expression;

//    public List<Country> getCountries();
//
//    public void setCountries(List<Country> countries);
//
////    public String getSelectedCountry() {
////        return selectedCountry;
////    }
//
//    public void setSelectedCountry(String selectedCountry) {
//        this.selectedCountry = selectedCountry;
//    }
//
//    public List<Travel> getTravelByCountry(String country){
//        return null;
//    }

}
