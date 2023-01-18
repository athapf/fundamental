package de.thaso.demo.payara.example.greeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GreetingIT {

    private URL url;
    private HttpURLConnection connection;

    @BeforeEach
    public void setUp() throws Exception {
        url = new URL("http://localhost:8081/greeting-demo/hello");
        connection = (HttpURLConnection) url.openConnection();
    }

    @Test
    public void callGreetingCheckState() throws Exception {
        // given
        connection.setRequestMethod("GET");
        // when
        final int result = connection.getResponseCode();
        // then
        assertThat(result, is(200));
    }

    @Test
    public void callGreetingCheckResult() throws Exception {
        // given
        connection.setRequestMethod("GET");
        // when
        final String result = retriveStringFromStream((InputStream) connection.getContent());
        // then
        assertThat(result, equalTo("Hello, World!"));
    }

    private String retriveStringFromStream(InputStream content) {
        return new BufferedReader(
                new InputStreamReader(content, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
