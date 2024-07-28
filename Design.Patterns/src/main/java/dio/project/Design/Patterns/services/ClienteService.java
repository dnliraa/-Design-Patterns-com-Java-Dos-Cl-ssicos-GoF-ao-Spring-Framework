package dio.project.Design.Patterns.services;


import dio.project.Design.Patterns.model.Client;


public interface ClienteService {

	Iterable<Client> buscarTodos();

	Client buscarPorId(Long id);

	void inserir(Client cliente);

	void atualizar(Long id, Client cliente);

	void deletar(Long id);

}