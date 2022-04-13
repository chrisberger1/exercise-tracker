package com.example.exercisetracker;

import com.example.exercisetracker.enums.Status;
import com.example.exercisetracker.models.Exercise;
import com.example.exercisetracker.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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

	// Test updating user weight with PUT request - should return 201 created
	@Test
	public void changeUserWeight() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = new URL("http://localhost:" + port + "/users/1").toString();
		User user = new User("Chris", 73, 185);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");

		HttpEntity<User> request = new HttpEntity<>(user, headers);

		ResponseEntity<String> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
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

	// Test for deleting a user - should return 204 no content
	@Test
	public void deleteUser() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = new URL("http://localhost:" + port + "/users/1").toString();

		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null ,String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
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
