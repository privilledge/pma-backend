package com.privilledge.pma.model;

import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String status;
    private  String description;
    private String summary;
    @Temporal(TemporalType.DATE)
    private Date addedDate;
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    public String getSummary() {
        return summary;
    }

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    private String notes;
    private String progress;
    public Long getId() {
        return id;
    }

    public String getProgress() {
        return progress;
    }

    public Project() {
    }

    public Project(Long id, String projectName, String status, String description, String summary, Date addedDate, Date dueDate, String notes, String progress) {
        this.id = id;
        this.projectName = projectName;
        this.status = status;
        this.description = description;
        this.summary = summary;
        this.addedDate = addedDate;
        this.dueDate = dueDate;
        this.notes = notes;
        this.progress = progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
