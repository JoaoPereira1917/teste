package p.Domain.servicos;


import p.Domain.entities.Cliente;
import p.Domain.interfaces.IClienteRepositorio;
import p.Domain.tipos.CPF;

import java.util.List;

public class ClienteService {
    private final IClienteRepositorio Repo;
    public ClienteService(IClienteRepositorio repo) {
        Repo = repo;
    }
    public void CadastrarCliente(String nome, CPF cpf)
    {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (cpf == null) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }
        if (Repo.ExisteCliente(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        Repo.Cadastrar(new Cliente(nome, cpf));

    }
    public List<Cliente> ListarClientes()
    {
        return Repo.ListarClientes();
    }
}
