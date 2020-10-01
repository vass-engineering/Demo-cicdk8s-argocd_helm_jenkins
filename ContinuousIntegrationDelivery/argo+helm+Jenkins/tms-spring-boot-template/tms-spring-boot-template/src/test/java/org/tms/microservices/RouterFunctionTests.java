package org.tms.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterFunctionTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void given_routerFunction_when_call_then_Ok() throws Exception {

	    //Given
        var address = "http://localhost:" + port + "/";
	    var result = this.restTemplate.getForObject(address, String.class);

	    //Then
	    then(result).isEqualTo("Hello World");
	}
}
