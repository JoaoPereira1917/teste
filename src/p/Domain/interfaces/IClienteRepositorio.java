package p.Domain.interfaces;

import p.Domain.entities.Cliente;
import p.Domain.entities.Conta;
import p.Domain.tipos.CPF;

import java.util.List;
import java.util.Optional;

public interface IClienteRepositorio {
    public List<Cliente> ListarClientes();
    public Optional<Cliente> BuscarClientes(CPF cpf);
    public void Cadastrar(Cliente c);
    public boolean ExisteCliente(CPF cpf);
}
