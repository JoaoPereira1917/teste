package p.Domain.servicos;

import p.Domain.Enum.TipoConta;
import p.Domain.entities.Cliente;
import p.Domain.entities.Conta;
import p.Domain.entities.Corrente;
import p.Domain.entities.Poupanca;
import p.Domain.interfaces.IClienteRepositorio;
import p.Domain.interfaces.IContaRepositorio;
import p.Domain.tipos.CPF;
import p.Domain.tipos.NumeroConta;

import java.math.BigDecimal;
import java.util.List;

public class ContaService  {
    private final IContaRepositorio Repo;
    private final IClienteRepositorio ClienteRepo;
    public ContaService(IContaRepositorio Repo, IClienteRepositorio clienteRepo) {
        this.Repo = Repo;
        ClienteRepo = clienteRepo;
    }

    public List<Conta> Listar(){
        return Repo.ListarContas();
    }
    public void Depositar(NumeroConta nConta, float valor){
        if(nConta == null){ throw new IllegalArgumentException("Número é obrigatorio");}
        if (valor <= 0 ) throw new IllegalArgumentException("Valor do depósito deve ser positivo.");
        var conta = Repo.BuscarConta(nConta).orElseThrow(() -> new IllegalArgumentException("Este número não é vinulado a nenhuma conta cadastrada"));
        conta.Depositar(valor);
    }
    public void CriarConta(CPF cpf, String tipo, NumeroConta numero, float saldoInicial) {
        if (cpf == null) throw new IllegalArgumentException("CPF obrigatório.");
        if (tipo == null || tipo.isBlank()) throw new IllegalArgumentException("Tipo de conta obrigatório.");
        if (numero == null) throw new IllegalArgumentException("Número da conta obrigatório.");
        if (saldoInicial < 0f) throw new IllegalArgumentException("Saldo incial não pode ser negativo.");
        final TipoConta tipoConta;
        try {
            tipoConta = TipoConta.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de conta inválido, use CORRENTE ou POUPANCA.");
        }

        var cliente = ClienteRepo.BuscarClientes(cpf).orElseThrow(() -> new IllegalArgumentException("Cliente não cadastrado."));

        if (Repo.existeNumero(numero)) {
            throw new IllegalArgumentException("Esse número de conta já foi cadastrado.");
        }

        var conta = criarContaTipo(tipoConta, numero, cliente, saldoInicial);
        Repo.SalvarConta(conta);
    }
    private Conta criarContaTipo(TipoConta tipo, NumeroConta numero, Cliente cliente, float saldoInicial){
        switch (tipo){
            case CORRENTE: return new Corrente(cliente,numero,  saldoInicial);
            case POUPANCA: return new Poupanca(cliente,numero,  saldoInicial);
            default: throw new IllegalArgumentException("Tipo de conta inválido.");
        }
    }



}
