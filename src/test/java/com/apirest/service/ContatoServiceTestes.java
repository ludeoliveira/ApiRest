package com.apirest.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.apirest.entidades.Contato;
import com.apirest.repository.ContatoRepository;
import com.apirest.services.ContatoDTO;
import com.apirest.services.ContatoService;

// anotacao teste unitario(não carrega o contexto da aplicação)
@ExtendWith(SpringExtension.class)
public class ContatoServiceTestes {
	
	private Long idExistente;
	private Long idNaoExistente;
	private Contato contatoValido;
	private Contato contatoInvalido;
	
	@BeforeEach
	void setup() {
		idExistente = 1l;
		idNaoExistente = 1000l;
		contatoValido = new Contato("Maria", "maria@gmail.com","(47)99999-9999");
		contatoInvalido = new Contato("Maria", "mariagmail.com","(47)999999-99999");
		
//		programar o comportamento simulado do repositório:
	
		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(contatoInvalido);
		Mockito.when(repository.save(contatoValido)).thenReturn(contatoValido);
		
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(idNaoExistente);
		
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(new Contato()));
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(idNaoExistente);
	}
	
	@InjectMocks
	ContatoService service;
	
//	vai simular as funcionalidades do repositorio:
	@Mock
	ContatoRepository repository;
	
	@Test
	public void retornaExcecaoQuandoSalvarSemSucesso() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvar(contatoInvalido));
		Mockito.verify(repository).save(contatoInvalido);
	}
	
	@Test
	public void retornaContatoDtoQuandoSalvarComSucesso() {
		ContatoDTO contatoDTO = service.salvar(contatoValido);
		Assertions.assertNotNull(contatoDTO);
		Mockito.verify(repository).save(contatoValido);
	}
	
	@Test
	public void retornaNadaAoExcluirComIdExistente() {
		Assertions.assertDoesNotThrow(() -> {
		service.excluirContato(idExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
	}
	
	@Test
	public void lancaEntidadeNaoEncontradaAoExcluirIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, ()-> {
			service.excluirContato(idNaoExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(idNaoExistente);
	}
	
	@Test
	public void retornaContatoQuandopesquisaPorIdExistente() {
		Contato ct = service.consultarContato(idExistente);
		Assertions.assertNotNull(ct);
		Mockito.verify(repository).findById(idExistente);
	}
	
	@Test
	public void lancaEntidadeNaoEncontradaAoConsultarIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, ()-> {
			service.consultarContatoPorId(idNaoExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).findById(idNaoExistente);
	}

}
