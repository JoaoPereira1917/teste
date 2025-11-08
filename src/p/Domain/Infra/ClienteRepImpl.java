package p.Domain.Infra;

import p.Domain.entities.Cliente;
import p.Domain.entities.Conta;
import p.Domain.interfaces.IClienteRepositorio;
import p.Domain.tipos.CPF;
import p.Domain.tipos.NumeroConta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClienteRepImpl implements IClienteRepositorio
{
    private final List<Cliente> clientes = new ArrayList<>();
    @Override
    public void Cadastrar(Cliente cliente)
    {
        if(ExisteCliente(cliente.getCpf()))
        {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        clientes.add(cliente);

    }

    @Override
    public Optional<Cliente> BuscarClientes(CPF cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst();
    }

    @Override
    public List<Cliente> ListarClientes() {
        //return new ArrayList<>(clientes);
        return Collections.unmodifiableList(clientes);
    }

    @Override
    public boolean ExisteCliente(CPF cpf) {
        return clientes.stream().anyMatch(c -> c.getCpf().equals(cpf));
    }

}
