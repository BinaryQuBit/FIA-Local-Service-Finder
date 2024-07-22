package com.FIA.backend;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "postservice")
public class PostService {

    // Default constructor
    public PostService() {
    }

    // Parameterized constructor
    public PostService(String typeService, String description) {
        this.typeService = typeService;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "typeService")
    private String typeService;

    @Column(name = "description")
    private String description;

    @Column(name = "emailService")
    private String emailService;

    @Column(name = "phoneService")
    private String phoneService;

    @Column(name = "cityService")
    private String cityService;

    @Column(name = "provinceService")
    private String provinceService;

    @Column(name = "countryService")
    private String countryService;

    @Column(name = "postedBy")
    private String postedBy;

    @Column(name = "status")
    private String status = "Active";

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
        if (status == null) {
            status = "Active";
        }
    }
}
