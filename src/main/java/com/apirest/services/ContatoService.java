package com.apirest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.entidades.Contato;
import com.apirest.repository.ContatoRepository;
import com.apirest.services.exception.ValidacaoException;

@Service
public class ContatoService {
	
	@Autowired
	ContatoRepository repo;

	public ContatoDTO salvar(Contato contato) {
//		if (contato.getFone().length() != 14) {
//			throw new ValidacaoException("Telefone inválido");
//		}
//		if (!contato.getEmail().contains("@")) {
//			throw new ValidacaoException("E-mail inválido");
//		}
		
//		ct recebe o contato que foi salvo no banco:
		Contato ct = repo.save(contato);
//		pegamos o ct e passamos no contatoDTO pra receber os dados do construtor dto:
		ContatoDTO contatoDTO = new ContatoDTO(ct);
		return contatoDTO;
	}

	public List<ContatoDTO> consultarContatos() {
//		preenchemos todos os contatos aqui:
		List<Contato> contatos = repo.findAll();
//		criamos uma arraylist pq o contatodto nao vai pegar referencia, so mostrar
		List<ContatoDTO> contatosDTO = new ArrayList();
		for(Contato ct : contatos) {
			contatosDTO.add(new ContatoDTO(ct));
		}
		return contatosDTO;
	}

	public ContatoDTO consultarContatoPorId(Long idcontato) {
		Optional<Contato> opcontato = repo.findById(idcontato);
		Contato ct = opcontato.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
//		Se o optional tiver conteudo, o conteudo vai pra dentro de ct. caso nao tenha, orElseThrow lança excecao e manda msg
		return new ContatoDTO(ct);
	}
	
//	este metodo abaixo so é usado dentro do service(private) para ser utilizado dentro do metodo excluir consultar o contato antes de excluir
	public Contato consultarContato(Long idcontato) {
		Optional<Contato> opcontato = repo.findById(idcontato);
		Contato ct = opcontato.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
		return ct;
	} 

	public ContatoDTO alterarContato(Long idcontato, Contato contato) {
		Contato ct = consultarContato(idcontato);
		ct.setEmail(contato.getEmail()); 
		ct.setNome(contato.getNome());
		ct.setFone(contato.getFone());
		return new ContatoDTO(repo.save(ct));
	}
	 
	
	public void excluirContato(Long idcontato) {
		Contato ct = consultarContato(idcontato);
//		Optional<Contato> opcontato = repo.findById(idcontato);
//		Contato ct = opcontato.orElseThrow(() -> new OperacaoNaoPermitidaException("O contato que deseja excluir não existe"));
		repo.deleteById(idcontato);
		
	}
	
	public List<ContatoDTO> consultarContatoPorEmail(String email) {
//		findByEmail esta no repository(metodo customizado)
		return ContatoDTO.converteParaDTO(repo.findByEmail(email));
	}

}
