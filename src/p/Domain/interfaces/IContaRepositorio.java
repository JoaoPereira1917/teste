package p.Domain.interfaces;
import java.util.Optional;

import p.Domain.entities.Conta;
import p.Domain.tipos.NumeroConta;

import java.util.ArrayList;
import java.util.List;

public interface IContaRepositorio {



    public List<Conta> ListarContas();
    public Optional<Conta> BuscarConta(NumeroConta numero);
    public void SalvarConta(Conta conta);
    public boolean existeNumero(NumeroConta numero);

}
