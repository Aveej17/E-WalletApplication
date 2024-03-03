package com.Jeeva.UserService;

import com.Jeeva.UserService.model.User;
import com.Jeeva.UserService.model.UserType;
import com.Jeeva.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(com.Jeeva.UserService.UserServiceApplication.class, args);
	}
}
//@SpringBootApplication
//public class UserServiceApplication implements CommandLineRunner {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	public static void main(String[] args) {
//		SpringApplication.run(UserServiceApplication.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		User txnService = User.builder()
//				.phoneNo("txn-service")
//				.password(passwordEncoder.encode("txn-service"))
//				.authority("SERVICE")
//				.userType(UserType.SERVICE)
//				.build();
//
//		userRepository.save(txnService);
//
//
//	}
//}
