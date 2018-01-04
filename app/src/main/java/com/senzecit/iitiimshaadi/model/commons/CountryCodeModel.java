package com.senzecit.iitiimshaadi.model.commons;

/**
 * Created by senzec on 1/1/18.
 */

public class CountryCodeModel {

    public CountryCodeModel(String name, String dial_code, String code) {
        this.name = name;
        this.dial_code = dial_code;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getDial_code() {
        return dial_code;
    }

    public String getCode() {
        return code;
    }

    String name ;
    String dial_code ;
    String code ;


}
