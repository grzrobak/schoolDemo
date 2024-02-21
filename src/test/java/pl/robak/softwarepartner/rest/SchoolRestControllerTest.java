package pl.robak.softwarepartner.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.robak.softwarepartner.TestUtils.readFromFile;
import static pl.robak.softwarepartner.TestUtils.toJsonTree;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getSchoolSummary() throws Exception {
        String url = "http://localhost:" + port + "/school/1/2024/1";
        String response = restTemplate.getForObject(url, String.class);

        String expectedResponse = readFromFile("/rest/schoolSummaryExpectedResponse.json");

        assertThat(toJsonTree(response))
                .isEqualTo(toJsonTree(expectedResponse));

    }

}