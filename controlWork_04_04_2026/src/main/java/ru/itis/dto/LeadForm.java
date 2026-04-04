package ru.itis.dto;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class LeadForm {

    @NotBlank
    @Pattern(regexp = "^[+0-9()\\-\\s]{7,20}$", message = "Введите корректный номер")
    private String phoneNumber;

    private String pageSource;

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPageSource() {
        return pageSource;
    }
    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }
}
