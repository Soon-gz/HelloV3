package com.hellobaby.library.data.model;

import java.io.Serializable;

public class EventItem implements Serializable {
    String eventName;
    String eventlocal;
    String eventCost;
    String peopleCount;
    String eventTime;
    String deadLine;
    String contentPerson;
    String eventDetail;
    String isgq;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventlocal() {
        return eventlocal;
    }

    public void setEventlocal(String eventlocal) {
        this.eventlocal = eventlocal;
    }

    public String getEventCost() {
        return eventCost;
    }

    public void setEventCost(String eventCost) {
        this.eventCost = eventCost;
    }

    public String getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(String peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getContentPerson() {
        return contentPerson;
    }

    public void setContentPerson(String contentPerson) {
        this.contentPerson = contentPerson;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public String getIsgq() {
        return isgq;
    }

    public void setIsgq(String isgq) {
        this.isgq = isgq;
    }

    public EventItem(String eventName, String eventlocal, String eventCost, String peopleCount, String eventTime, String deadLine, String contentPerson, String eventDetail, String isgq) {
        this.eventName = eventName;
        this.eventlocal = eventlocal;
        this.eventCost = eventCost;
        this.peopleCount = peopleCount;
        this.eventTime = eventTime;
        this.deadLine = deadLine;
        this.contentPerson = contentPerson;
        this.eventDetail = eventDetail;
        this.isgq=isgq;
    }
}
