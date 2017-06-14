package hpe.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class HystrixServiceMain {

	public static void main(String[] args) {
		SpringApplication.run(HystrixServiceMain.class, args);
	}

}

@RestController
class RibbonController {

	@GetMapping("/find-user/{id}")
	public User findUserById(@PathVariable Long id) {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("/find-user/" + id);
		return new User(id, "HPE");
	}
}

class User {
	public User() {
	}

	public User(Long id, String name) {
		this.id = id;
		this.name = name + " " + id;
	}

	public Long id;
	public String name;
}
