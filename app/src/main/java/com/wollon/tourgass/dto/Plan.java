package com.wollon.tourgass.dto;

import java.util.Date;
import java.util.UUID;

/**
 * Created by 漫聆默 on 2017/11/23 0023.
 */

public class Plan {
    private UUID id;
    private String title;
    private Date time;
    private String details;
    private boolean solved;

    public Plan() {
        id=UUID.randomUUID();
        time=new Date();
        details ="";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
