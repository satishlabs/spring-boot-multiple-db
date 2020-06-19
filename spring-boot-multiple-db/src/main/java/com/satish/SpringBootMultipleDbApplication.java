package com.satish;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satish.model.Book;
import com.satish.model.User;
import com.satish.repository.BookRepository;
import com.satish.repository.UserReposiotry;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.satish.*"})
public class SpringBootMultipleDbApplication {
	
	@Autowired
	private UserReposiotry userReposiotry;
	
	@Autowired
	private BookRepository bookRepository;
		
	@PostConstruct
	public void addData2DB() {
		userReposiotry.saveAll(
				Stream.of(new User(744,"Satish"),new User(455,"MsDhoni")).collect(Collectors.toList()));
		bookRepository.saveAll(
				Stream.of(new Book(111,"Core Java"),new Book(222,"Spring Boot")).collect(Collectors.toList()));
	}
	
	@GetMapping("/getUsers")
	public List<User> getUsers(){
		return userReposiotry.findAll();
	}
	
	@GetMapping("/getBooks")
	public List<Book> getBooks(){
		return bookRepository.findAll();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultipleDbApplication.class, args);
	}

}
