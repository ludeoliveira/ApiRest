package com.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.entidades.Compromisso;
import com.apirest.repository.CompromissoRepository;

@RestController
@RequestMapping("/")
public class CompromissoController {
	
	@Autowired
	CompromissoRepository repo;
	
	@GetMapping("/compromisso")
	public ResponseEntity<List<Compromisso>> consultaCompromisso() {
		return ResponseEntity.ok(repo.findAll());
	}
	
	@PostMapping("/compromisso")
	public ResponseEntity<Compromisso> salvarCompromisso(@RequestBody Compromisso compromisso) {
		return ResponseEntity.ok(repo.save(compromisso));
	}
	
	@GetMapping("/compromisso/contatos/{nome}")
	public ResponseEntity<List<Compromisso>> consultaCompromissosPorNomeContato(@PathVariable("nome") String nome) {
		return ResponseEntity.ok(repo.consultaCompromissosPorNomeContato(nome));
	}
}
