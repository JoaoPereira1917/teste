package p.Domain.servicos;

import p.Domain.entities.Conta;
import p.Domain.interfaces.ContaRepImpl;
import p.Domain.interfaces.IContaRepositorio;
import p.Domain.tipos.CPF;
import p.Domain.tipos.NumeroConta;

import java.lang.annotation.Repeatable;
import java.util.List;

public class ContaService  {
    private final IContaRepositorio Repositorio;
    public ContaService(IContaRepositorio Repositorio) {
        this.Repositorio = Repositorio;
    }


    public void Listar(){
        List<Conta> contas = Repositorio.ListarContas();
        contas.forEach(System.out::println);
    }
    public void Depositar(NumeroConta nConta, float valor){
        var conta = Repositorio.BuscarConta(nConta).orElseThrow(() -> new IllegalArgumentException("este número não é vinulado a nenhuma conta cadastrada"));
    }
    public void CriarConta(CPF cpf, String Nome, TipoConta tipo, NumeroConta numero, float saldo ){

    }



}
