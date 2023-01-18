package de.thaso.demo.payara.example.greeting.control;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingControl {

    public String helloWorld() {
        return "Hello, World!";
    }
}
