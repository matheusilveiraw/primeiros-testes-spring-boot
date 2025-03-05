package com.minhaprimeiraapijava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //define o ponto de entrada da aplicação
public class MinhaApiApplication {

	public static void main(String[] args) { //é o ponto de entrada de um programa Java
		SpringApplication.run(MinhaApiApplication.class, args); //inicia o spring boot
	}
}