package p.Domain.interfaces;

import p.Domain.entities.Cliente;
import p.Domain.entities.Conta;
import p.Domain.tipos.CPF;

import java.util.List;
import java.util.Optional;

public interface IClienteRepositorio {
     List<Cliente> ListarClientes();
     Optional<Cliente> BuscarCliente(CPF cpf);
     void Cadastrar(Cliente c);
     boolean ExisteCliente(CPF cpf);
}
