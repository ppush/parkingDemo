package com.example.demo.controler;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.repository.SpotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdminControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    SpotRepository spotRepository;


    @AfterEach
    void deleteAll() {
        spotRepository.deleteAll();
    }

    @Test
    void addOneSpotTest() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("{\"description\":\"spot for test\"}", headers);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/spot/add",
                                                   request,
                                                  String.class)).contains("spot for test");
    }

    @Test
    void addTenSpotTest() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var url = "http://localhost:" + port + "/spot/add";

        for (int i = 0; i < 9; i++) {
            HttpEntity<String> request = new HttpEntity<>("{\"description\":\"spot for %s\"}".formatted(i), headers);
            this.restTemplate.postForObject(url,
                                            request,
                                            String.class);
        }
        HttpEntity<String> request = new HttpEntity<>("{\"description\":\"spot for %s\"}".formatted(10), headers);
        assertThat(this.restTemplate.postForObject(url,
                                                   request,
                                                   String.class)).contains("spot for 10");
    }
}