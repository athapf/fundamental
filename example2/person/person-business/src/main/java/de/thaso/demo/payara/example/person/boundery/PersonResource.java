package de.thaso.demo.payara.example.person.boundery;

import de.thaso.demo.payara.example.person.control.PersonControl;
import de.thaso.demo.payara.example.person.data.Person;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/personen")
public class PersonResource {

    @Inject
    private PersonControl personControl;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person helloWorld(@PathParam("id") @NotNull Integer id) {
        return personControl.readPerson(id);
    }
}
