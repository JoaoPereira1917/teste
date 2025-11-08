package p.Domain.servicos;

import p.Domain.Enum.TipoConta;
import p.Domain.dto.RelatorioBancoDTO;
import p.Domain.dto.RelatorioBancoDTO.TotaisPorTipo;
import p.Domain.entities.Cliente;
import p.Domain.entities.Conta;
import p.Domain.entities.Corrente;
import p.Domain.entities.Poupanca;
import p.Domain.interfaces.IClienteRepositorio;
import p.Domain.interfaces.IContaRepositorio;
import p.Domain.tipos.CPF;
import p.Domain.tipos.NumeroConta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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

        var cliente = ClienteRepo.BuscarCliente(cpf).orElseThrow(() -> new IllegalArgumentException("Cliente não cadastrado."));

        if (Repo.existeNumero(numero)) {
            throw new IllegalArgumentException("Esse número de conta já foi cadastrado.");
        }

        var conta = criarContaTipo(tipoConta, numero, cliente, saldoInicial);
        Repo.SalvarConta(conta);
    }
    public void SacarConta(NumeroConta numero, float valor) {
        var conta = Repo.BuscarConta(numero).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
        if(valor <= 0f) throw new IllegalArgumentException("Valor para saque deve ser positivo.");
        if(valor > conta.getSaldo()) throw new IllegalArgumentException("Saldo insuficiente.");
        conta.Saque(valor);

    }
    public void Transferir(NumeroConta Origem, NumeroConta Destino, float Valor) {
        if (Origem == null || Destino == null) {
            throw new IllegalArgumentException("O número da conta é obrigatório.");
        }
        if (Valor <= 0f) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo.");
        }
        if (Origem.equals(Destino)) {
            throw new IllegalArgumentException("Contas de origem e destino devem ser diferentes.");
        }

        var ContaOrigem = Repo.BuscarConta(Origem)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada."));
        var ContaDestino = Repo.BuscarConta(Destino)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));

        ContaOrigem.Saque(Valor);
        ContaDestino.Depositar(Valor);


    }
    public float ConsultarSaldo(NumeroConta numero) {
        var conta = Repo.BuscarConta(numero).orElseThrow(() -> new IllegalArgumentException("Este número não é vinulado a nenhuma conta cadastrada"));
        return conta.getSaldo();


    }
    public void AplicarRendimento(float Percentual) {
        if (Percentual <= 0f) { // opcional: ver explicação abaixo
            throw new IllegalArgumentException("Percentual deve ser positivo.");
        }
        for (Conta c : Repo.ListarContas()) {
            if (c instanceof Poupanca p) {
                p.AplicarRendimento(Percentual);
            }
        }
    }
    public List<Conta> ListarContasOrdenadasSaldo(){
        List<Conta> lista = new ArrayList<>(Repo.ListarContas()); // cópia para não mexer no repositório
        lista.sort((a, b) -> Float.compare(b.getSaldo(), a.getSaldo())); // desc
        return lista;
    }
    public RelatorioBancoDTO GerarRelatorioConsolidado() {
        int qtdCorrente = 0;
        int qtdPoupanca = 0;
        float somaCorrente = 0f;
        float somaPoupanca = 0f;

        List<Conta> contas = Repo.ListarContas();

        for (Conta c : contas) {
            if (c instanceof Corrente) {
                qtdCorrente++;
                somaCorrente += c.getSaldo();
            } else if (c instanceof Poupanca) {
                qtdPoupanca++;
                somaPoupanca += c.getSaldo();
            }
        }

        int totalContas = qtdCorrente + qtdPoupanca;
        float saldoTotalBanco = somaCorrente + somaPoupanca;

        List<TotaisPorTipo> porTipo = new ArrayList<>();
        porTipo.add(new TotaisPorTipo("CORRENTE", qtdCorrente, somaCorrente));
        porTipo.add(new TotaisPorTipo("POUPANCA", qtdPoupanca, somaPoupanca));

        return new RelatorioBancoDTO(porTipo, totalContas, saldoTotalBanco);
    }

    private Conta criarContaTipo(TipoConta tipo, NumeroConta numero, Cliente cliente, float saldoInicial){
        switch (tipo){
            case CORRENTE: return new Corrente(cliente,numero,  saldoInicial);
            case POUPANCA: return new Poupanca(cliente,numero,  saldoInicial);
            default: throw new IllegalArgumentException("Tipo de conta inválido.");
        }
    }



}
