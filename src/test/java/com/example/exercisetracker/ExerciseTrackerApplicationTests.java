package com.example.exercisetracker;

import org.apache.coyote.Response;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ServletResponseMethodArgumentResolver;

import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExerciseTrackerApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	// TEST GET REQUEST USERS - SHOULD RETURN 200 OK
	@Test
	public void getUsers() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/users").toString(), String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// TEST GET REQUEST USER THAT DOESN'T EXIST - SHOULD RETURN 404 NOT FOUND
	@Test
	public void getUserNotFound() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/users/99").toString(), String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	// TEST POST REQUEST ADD USER - SHOULD RETURN 201 CREATED
	@Test
	public void addUser() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = new URL("http://localhost:" + port + "/users").toString();
		User user = new User("Alex", 72, 190);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");

		HttpEntity<User> request = new HttpEntity<>(user, headers);

		ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, request, String.class);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
	}

	// TEST POST REQUEST ADD EXERCISE - SHOULD RETURN 201 CREATED
	@Test
	public void addExercise() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = new URL("http://localhost:" + port + "/exercises").toString();
		Exercise exercise = new Exercise("Deadlift", Status.IN_PROGRESS, 8, 200, 2);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");

		HttpEntity<Exercise> request = new HttpEntity<>(exercise, headers);

		ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, request, String.class);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
	}

	// Test for completing an in progress exercise - Should return a 200 OK
	@Test
	public void completeExercise() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = new URL("http://localhost:" + port + "/exercises/4/complete").toString();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");

		HttpEntity<User> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// TODO: figure out how to check if METHOD_NOT_ALLOWED response is returned
	// Test for completing an in progress exercise - Should return method not allowed
	@Test
	public void completeCompletedExercise() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = new URL("http://localhost:" + port + "/exercises/3/complete").toString();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");

		HttpEntity<User> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);
		assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
	}

}
