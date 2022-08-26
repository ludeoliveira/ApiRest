package com.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.entidades.Pessoa;
import com.apirest.services.PessoaService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class PessoaController {
	
	@Autowired
	PessoaService service;
	
	@GetMapping("/pessoa/{idpessoa}")
	public ResponseEntity<Pessoa> consultar(@PathVariable("idpessoa") Long idpessoa) {
		return ResponseEntity.status(HttpStatus.OK).body(service.consultar(idpessoa));
	}
	
	@GetMapping("/pessoa")
	public ResponseEntity<List<Pessoa>> consultarTodos() {
		return ResponseEntity.status(HttpStatus.OK).body(service.consultarTodos());
	}
	
	@PostMapping("/pessoa")
	public ResponseEntity<Pessoa> salvar(@RequestBody Pessoa pessoa) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(pessoa));
	}
	
	@PutMapping("/pessoa/{idpessoa}")
	public ResponseEntity<Pessoa> alterar(@PathVariable("idpessoa") long idpessoa, @RequestBody Pessoa pessoa) {
		return ResponseEntity.status(HttpStatus.OK).body(service.alterar(idpessoa, pessoa));
	}
}
