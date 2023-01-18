package de.thaso.demo.payara.example.greeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PersonIT {

    private URL url;
    private HttpURLConnection connection;

    @BeforeEach
    public void setUp() throws Exception {
        url = new URL("http://localhost:8081/person-demo/personen");
        connection = (HttpURLConnection) url.openConnection();
    }

    @Test
    @Disabled
    public void callGreetingCheckState() throws Exception {
        // given
        connection.setRequestMethod("GET");
        // when
        final int result = connection.getResponseCode();
        // then
        assertThat(result, is(200));
    }

    @Test
    public void callPerson() throws Exception {
        // given
        url = new URL("http://localhost:8081/person-demo/personen/123");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // when
        final int result = connection.getResponseCode();
        InputStreamReader in = new InputStreamReader((InputStream) connection.getContent());
        BufferedReader buff = new BufferedReader(in);
        String line;
        StringBuilder builder = new StringBuilder();
        do {
            line = buff.readLine();
            builder.append(line).append("\n");
        } while (line != null);
        buff.close();
        System.out.println("\n==>\n" + builder.toString() +"\n<==");
        // then
        assertThat(result, is(200));
    }
}
