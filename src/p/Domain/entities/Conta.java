package p.Domain.entities;

import p.Domain.tipos.NumeroConta;

public abstract class Conta  {
    protected float Saldo;
    private final NumeroConta Numero;
    public Cliente ClienteAss;//cliente associado
    public Conta(Cliente cliente, NumeroConta numero) {
        ClienteAss = cliente;
        Numero = numero;
    }
    // vamo perguntar se ao criar conta, fazer um deposito, se sim usa esse construtor se não valor padrão de saldo = 0 ->>>
    public Conta(Cliente cliente, NumeroConta numero, float saldo) {
        ClienteAss = cliente;
        Numero = numero;
        Saldo = saldo;
    }
    public void Depositar(float valor) {
        if(valor > 0){Saldo += valor; System.out.println("Deposito de R$"+valor +" realizado com sucesso");}
        else throw new IllegalArgumentException("Você precisa depositar algum valor");
    }
    public void Saque(float valor) {
        if(valor <= Saldo || valor > 0){Saldo -= valor;}
    }
    @Override
    public String toString() {
        return (ClienteAss+" - "+ Numero + " - Saldo: R$"+ Saldo);
    }
    public float getSaldo() {
        return Saldo;
    }
    public NumeroConta getNumero() {
        return Numero;
    }





}
