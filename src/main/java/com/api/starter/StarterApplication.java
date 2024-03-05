package com.api.starter;

import com.mylib.photo.EnablePhotoApiClients;
import com.mylib.photo.client.PhotoApiClient;
import com.mylib.photo.client.dto.PhotoResponse;
import com.mylib.post.EnablePostApiClients;
import com.mylib.post.client.dto.PostResponse;
import com.mylib.post.service.PostApiService;
import com.mylib.user.EnableUserApiClients;
import com.mylib.user.client.dto.UserResponse;
import com.mylib.user.service.UserApiService;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnablePhotoApiClients(decoder = "photoDecoder")
@EnableUserApiClients(errorDecoder = "userErrorDecoder", decoder = "userDecoder")
@EnablePostApiClients
public class StarterApplication {

	@Autowired
	UserApiService userApiService;

	@Autowired
	PostApiService postApiService;

	@Autowired
	PhotoApiClient photoApiService;

	@PostConstruct
	public void init () throws NoSuchAlgorithmException {
		user();
		post();
		photo();
	}

	private void user() throws NoSuchAlgorithmException {
		System.out.println("==========user==========");
		System.out.println("service = " + userApiService);
		List<UserResponse> users = userApiService.getUsers();
		for (UserResponse user : users) {
			System.out.println("user = " + user);
		}

		System.out.println("====================");
		int randomUserId = SecureRandom.getInstanceStrong().nextInt(1, 10);
		UserResponse user = userApiService.getUser(String.valueOf(randomUserId));
		System.out.println("user = " + user);

		try {
			userApiService.getUser("999");
		} catch (FeignException.FeignClientException e) {
			System.out.println("e = " + e);
		} catch (FeignException.FeignServerException e1) {
			System.out.println("e1 = " + e1);
		} catch (FeignException e2) {
			System.out.println("e2 = " + e2);
		}
	}

	private void post() throws NoSuchAlgorithmException {
		System.out.println("==========post==========");
		System.out.println("postApiService = " + postApiService);
		List<PostResponse> posts = postApiService.getPosts();
		for (PostResponse post : posts) {
			System.out.println("post = " + post);
		}

		System.out.println("====================");
		int randomUserId = SecureRandom.getInstanceStrong().nextInt(1, 10);
		PostResponse post = postApiService.getPost(String.valueOf(randomUserId));
		System.out.println("post = " + post);

		try {
			postApiService.getPost("999");
		} catch (FeignException.FeignClientException e) {
			System.out.println("e = " + e);
		} catch (FeignException.FeignServerException e1) {
			System.out.println("e1 = " + e1);
		} catch (FeignException e2) {
			System.out.println("e2 = " + e2);
		}

	}

	private void photo() throws NoSuchAlgorithmException {
		System.out.println("==========photo==========");
		System.out.println("photoApiService = " + photoApiService);
		List<PhotoResponse> photos = photoApiService.getPhotos();
		for (PhotoResponse photo : photos) {
			System.out.println("photo = " + photo);
		}

		System.out.println("====================");
		int randomUserId = SecureRandom.getInstanceStrong().nextInt(1, 10);
		PhotoResponse photo = photoApiService.getPhoto(String.valueOf(randomUserId));
		System.out.println("photo = " + photo);

		try {
			photoApiService.getPhoto("99999");
		} catch (FeignException.FeignClientException e) {
			System.out.println("e = " + e);
		} catch (FeignException.FeignServerException e1) {
			System.out.println("e1 = " + e1);
		} catch (FeignException e2) {
			System.out.println("e2 = " + e2);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

}
