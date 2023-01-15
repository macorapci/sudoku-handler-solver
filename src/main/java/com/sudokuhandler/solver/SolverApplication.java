package com.sudokuhandler.solver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = @Server(url = "${solver-app.url}"))
@SpringBootApplication
public class SolverApplication {
	public static void main(String[] args) {
		SpringApplication.run(SolverApplication.class, args);
	}
}
