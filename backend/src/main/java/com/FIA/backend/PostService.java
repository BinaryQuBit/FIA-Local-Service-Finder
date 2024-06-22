package com.FIA.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "postservice")
public class PostService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "typeservice")
    private String typeService;

    @Column(name = "description")
    private String description;

    @Column(name = "emailservice")
    private String emailService;

    @Column(name = "phoneservice")
    private String phoneService;

    @Column(name = "cityservice")
    private String cityService;

    @Column(name = "provinceservice")
    private String provinceService;

    @Column(name = "countryservice")
    private String countryService;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailService() {
        return emailService;
    }

    public void setEmailService(String emailService) {
        this.emailService = emailService;
    }

    public String getPhoneService() {
        return phoneService;
    }

    public void setPhoneService(String phoneService) {
        this.phoneService = phoneService;
    }

    public String getCityService() {
        return cityService;
    }

    public void setCityService(String cityService) {
        this.cityService = cityService;
    }

    public String getProvinceService() {
        return provinceService;
    }

    public void setProvinceService(String provinceService) {
        this.provinceService = provinceService;
    }

    public String getCountryService() {
        return countryService;
    }

    public void setCountryService(String countryService) {
        this.countryService = countryService;
    }
}
