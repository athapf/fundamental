package de.thaso.demo.payara.example.person.control;

import de.thaso.demo.payara.example.person.data.Note;
import de.thaso.demo.payara.example.person.data.Person;
import de.thaso.demo.payara.example.person.data.StateEnum;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PersonControl {

    public Person readPerson(final long id) {
        Person result = new Person();
        result.setForeName("Horst");
        Note note = new Note();
        note.setState(StateEnum.IMPORTANT);
        note.setCreated(LocalDate.now());
        note.setText("Bitte beachten!!");
        final List<LocalDate> ticketList = new ArrayList<>();
        ticketList.add(LocalDate.MIN);
        ticketList.add(LocalDate.now());
        note.setTickets(ticketList);
        result.setNote(note);
        return result;
    }
}
