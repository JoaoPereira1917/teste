package p.Domain.interfaces;

import p.Domain.entities.Conta;
import p.Domain.tipos.NumeroConta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContaRepImpl implements IContaRepositorio {
    private final List<Conta> contas = new ArrayList<>();

    @Override
    public void SalvarConta(Conta conta) {
        contas.add(conta);
    }

    @Override
    public Optional<Conta> BuscarConta(NumeroConta numero) {
        return contas.stream()
                .filter(c -> c.getNumero().equals(numero))
                .findFirst();
    }

    @Override
    public List<Conta> ListarContas() {
        return new ArrayList<>(contas);
    }

    @Override
    public boolean existeNumero(NumeroConta numero) {
        return contas.stream().anyMatch(c -> c.getNumero().equals(numero));
    }
}
