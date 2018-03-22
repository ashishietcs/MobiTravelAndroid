package org.smartcity;

/**
 * Created by ASAHU3 on 3/9/2018.
 */

public class Ticket {
    String userid;
    String from;
    String to;

    String no_persons;
    String status, id, created;
    String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    String resourceUrl;
    public String getUserid() {
        return userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNo_persons() {
        return no_persons;
    }

    public void setNo_persons(String no_persons) {
        this.no_persons = no_persons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }



}
