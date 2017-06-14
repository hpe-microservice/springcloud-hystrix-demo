package hpe.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
public class HystrixClientMain {

	public static void main(String[] args) {
		SpringApplication.run(HystrixClientMain.class, args);
	}

}

@EnableCircuitBreaker
@RestController
class HystrixClientController {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/ribbon/{id}")
	@HystrixCommand(fallbackMethod = "fallback")
	public User ribbon(@PathVariable Long id) {
		return this.restTemplate.getForObject("http://hystrix-finduser-service/" + "find-user/" + id, User.class);
	}

	public User fallback(Long id) {
		System.out.println("enter fallback()..");
		return new User(-1L, "error");
	}
}

class User {
	public User() {
	}

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long id;
	public String name;
}
