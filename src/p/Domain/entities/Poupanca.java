package p.Domain.entities;

import p.Domain.tipos.NumeroConta;

public class Poupanca extends Conta {
    public Poupanca(Cliente cliente, NumeroConta numero, float saldo) {
        super(cliente, numero, saldo);
    }

    public Poupanca(Cliente cliente, NumeroConta numero) {
        super(cliente, numero);
    }
    public void AplicarRendimento(float percentual) {

        if(percentual > 0) {
            float fator = (1+ percentual)/100;
            Saldo = Saldo * fator;

        }
        else{throw new RuntimeException("O percentual de rendimendo deve ser maior que zero");}

    }
}
