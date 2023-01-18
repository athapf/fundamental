package de.thaso.demo.payara.example.person.data;

import javax.json.bind.annotation.JsonbProperty;

public class Person {

    private String sureName;
    private String foreName;
    private Address address;

    @JsonbProperty("_note")
    private Note note;

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public String getForeName() {
        return foreName;
    }

    public void setForeName(String foreName) {
        this.foreName = foreName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
