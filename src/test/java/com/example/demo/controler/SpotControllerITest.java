package com.example.demo.controler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.repository.SpotActionRepository;
import com.example.demo.repository.SpotBookRepository;
import com.example.demo.repository.SpotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpotControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    SpotBookRepository spotBookRepository;

    @Autowired
    SpotActionRepository spotActionRepository;

    String addSpotUrl;

    @AfterEach
    void deleteAll() {
        spotRepository.deleteAll();
        spotBookRepository.deleteAll();
        spotActionRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
       addSpotUrl = "http://localhost:" + port + "/spot/add";
    }

    @Test
    void getAllSpotsTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < 10; i++) {
            HttpEntity<String> request = new HttpEntity<>("{\"description\":\"spot for %s\"}".formatted(i), headers);
            this.restTemplate.postForObject(addSpotUrl,
                                            request,
                                            String.class);
        }

        var result = this.restTemplate.getForObject("http://localhost:" + port + "/spot/", Object[].class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.length);
    }

    @Test
    void bookSpotIfAvailableTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("{\"description\":\"spot for %s\"}".formatted("book"), headers);
        this.restTemplate.postForObject(addSpotUrl,
                                        request,
                                        String.class);

        var now = LocalDateTime.now();
        HttpEntity<String> bookRequest =
            new HttpEntity<>(("""
                {
                    "dateFrom": "%s",
                    "intervalHour": 1,
                    "fromHour": %s,
                    "name": "vahan"
                }
                """).formatted(now.format(DateTimeFormatter.ISO_DATE), now.getHour()), headers);
        var result = this.restTemplate.postForObject("http://localhost:" + port + "/spot/book", bookRequest, Object.class);

        Assertions.assertNotNull(result);
        Assertions.assertInstanceOf(Map.class, result);
        Assertions.assertTrue(((Map) result).containsKey("bookId"));
    }

    @Test
    void cancelBookTest() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < 10; i++) {
            HttpEntity<String> request =
                new HttpEntity<>("{\"description\":\"spot for %s\"}".formatted("book"), headers);
            this.restTemplate.postForObject(addSpotUrl,
                                            request,
                                            String.class);
        }

        var now = LocalDateTime.now();
        List<String> bookidList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HttpEntity<String> bookRequest =
                new HttpEntity<>(("""
                    {
                        "dateFrom": "%s",
                        "intervalHour": 1,
                        "fromHour": %s,
                        "name": "vahan"
                    }
                    """).formatted(now.format(DateTimeFormatter.ISO_DATE), now.getHour()), headers);
            var result =
                this.restTemplate.postForObject("http://localhost:" + port + "/spot/book", bookRequest, Object.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Map.class, result);
            Assertions.assertTrue(((Map) result).containsKey("bookId"));
            bookidList.add(((Map) result).get("bookId").toString());
        }
        var cancelResult =
            this.restTemplate.exchange(
                "http://localhost:" + port + "/spot/book/"+bookidList.getFirst(),
                HttpMethod.DELETE,
                null,
                Object.class).getBody();

        Assertions.assertNotNull(cancelResult);
        Assertions.assertInstanceOf(Map.class, cancelResult);
        Assertions.assertTrue(((Map) cancelResult).containsKey("bookId"));
        Assertions.assertEquals(bookidList.getFirst(), ((Map) cancelResult).get("bookId"));
    }

    @Test
    void parkSpotTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < 10; i++) {
            HttpEntity<String> request =
                new HttpEntity<>("{\"description\":\"spot for %s\"}".formatted("book"), headers);
            this.restTemplate.postForObject(addSpotUrl,
                                            request,
                                            String.class);
        }

        var now = LocalDateTime.now();
        List<String> bookidList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HttpEntity<String> bookRequest =
                new HttpEntity<>(("""
                    {
                        "dateFrom": "%s",
                        "intervalHour": 1,
                        "fromHour": %s,
                        "name": "vahan"
                    }
                    """).formatted(now.format(DateTimeFormatter.ISO_DATE), now.getHour()), headers);
            var result =
                this.restTemplate.postForObject("http://localhost:" + port + "/spot/book", bookRequest, Object.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Map.class, result);
            Assertions.assertTrue(((Map) result).containsKey("bookId"));
            bookidList.add(((Map) result).get("bookId").toString());
        }

        var parkResult =
            this.restTemplate.postForObject("http://localhost:" + port + "/spot/park/"+bookidList.getFirst(), null, Object.class);

        Assertions.assertNotNull(parkResult);
        Assertions.assertInstanceOf(Map.class, parkResult);
        Assertions.assertTrue(((Map) parkResult).containsKey("spotId"));
        Assertions.assertTrue(((Map) parkResult).containsKey("state"));
        Assertions.assertEquals("PARKED", ((Map) parkResult).get("state"));
    }

    @Test
    void releaseSpotTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < 10; i++) {
            HttpEntity<String> request =
                new HttpEntity<>("{\"description\":\"spot for %s\"}".formatted("book"), headers);
            this.restTemplate.postForObject(addSpotUrl,
                                            request,
                                            String.class);
        }

        var now = LocalDateTime.now();
        List<String> bookidList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HttpEntity<String> bookRequest =
                new HttpEntity<>(("""
                    {
                        "dateFrom": "%s",
                        "intervalHour": 1,
                        "fromHour": %s,
                        "name": "vahan"
                    }
                    """).formatted(now.format(DateTimeFormatter.ISO_DATE), now.getHour()), headers);
            var result =
                this.restTemplate.postForObject("http://localhost:" + port + "/spot/book", bookRequest, Object.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Map.class, result);
            Assertions.assertTrue(((Map) result).containsKey("bookId"));
            bookidList.add(((Map) result).get("bookId").toString());
        }

        var parkResult =
            this.restTemplate.postForObject("http://localhost:" + port + "/spot/park/"+bookidList.getFirst(), null, Object.class);

        Assertions.assertNotNull(parkResult);
        Assertions.assertInstanceOf(Map.class, parkResult);
        Assertions.assertTrue(((Map) parkResult).containsKey("spotId"));
        Assertions.assertTrue(((Map) parkResult).containsKey("state"));
        Assertions.assertEquals("PARKED", ((Map) parkResult).get("state"));

        var spotId = ((Map) parkResult).get("spotId");

        var releaseResult =
            this.restTemplate.postForObject("http://localhost:" + port + "/spot/release/"+spotId, null, Object.class);

        Assertions.assertNotNull(releaseResult);
        Assertions.assertInstanceOf(Map.class, releaseResult);
        Assertions.assertTrue(((Map) releaseResult).containsKey("spotId"));
        Assertions.assertTrue(((Map) releaseResult).containsKey("state"));
        Assertions.assertEquals("FREE", ((Map) releaseResult).get("state"));
    }
}