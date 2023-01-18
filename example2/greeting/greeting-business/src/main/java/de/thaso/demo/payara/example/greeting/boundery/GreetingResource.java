package de.thaso.demo.payara.example.greeting.boundery;

import de.thaso.demo.payara.example.greeting.control.GreetingControl;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    private GreetingControl greetingControl;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return greetingControl.helloWorld();
    }
}
