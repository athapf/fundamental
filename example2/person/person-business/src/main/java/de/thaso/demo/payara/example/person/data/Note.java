package de.thaso.demo.payara.example.person.data;

import de.thaso.demo.payara.example.person.utils.LocalDateXmlAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement
public class Note {

    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate created;
    private StateEnum state;
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private List<LocalDate> tickets;
    private String text;

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<LocalDate> getTickets() {
        return tickets;
    }

    public void setTickets(List<LocalDate> tickets) {
        this.tickets = tickets;
    }
}
