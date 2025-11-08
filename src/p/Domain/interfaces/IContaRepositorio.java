package p.Domain.interfaces;
import java.util.Optional;

import p.Domain.entities.Conta;
import p.Domain.tipos.NumeroConta;

import java.util.List;

public interface IContaRepositorio {



     List<Conta> ListarContas();
     Optional<Conta> BuscarConta(NumeroConta numero);
     void SalvarConta(Conta conta);
     boolean existeNumero(NumeroConta numero);

}
