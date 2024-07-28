package dio.project.Design.Patterns.implement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dio.project.Design.Patterns.model.Client;
import dio.project.Design.Patterns.model.ClientRepository;
import dio.project.Design.Patterns.model.Endereco;
import dio.project.Design.Patterns.model.EnderecoRepository;
import dio.project.Design.Patterns.services.ClienteService;
import dio.project.Design.Patterns.services.ViaCepService;


@Service

public class ClienteServiceImplement implements ClienteService {
	
	

		
		@Autowired
		private ClientRepository clienteRepository;
		@Autowired
		private EnderecoRepository enderecoRepository;
		@Autowired
		private ViaCepService viaCepService;
		

		@Override
		public Iterable<Client> buscarTodos() {			
			return clienteRepository.findAll();
		}

		@Override
		public Client buscarPorId(Long id) {
			Optional<Client> cliente = clienteRepository.findById(id);
			return cliente.get();
		}

		@Override
		public void inserir(Client cliente) {
			salvarClienteComCep(cliente);
		}

		@Override
		public void atualizar(Long id, Client cliente) {
			Optional<Client> clienteBd = clienteRepository.findById(id);
			if (clienteBd.isPresent()) {
				salvarClienteComCep(cliente);
			}
		}

		@Override
		public void deletar(Long id) {
			clienteRepository.deleteById(id);
		}

		private void salvarClienteComCep(Client cliente) {
			String cep = cliente.getEndereco().getCep();
			Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
				Endereco novoEndereco = viaCepService.consultarCep(cep);
				enderecoRepository.save(novoEndereco);
				return novoEndereco;
			});
			cliente.setEndereco(endereco);
			clienteRepository.save(cliente);
		}

}
