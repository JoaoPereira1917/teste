package p.Domain.entities;

import p.Domain.tipos.NumeroConta;

public class Poupanca extends Conta {
    public Poupanca(Cliente cliente, NumeroConta numero, float saldo) {
        super(cliente, numero, saldo);
    }

    public Poupanca(Cliente cliente, NumeroConta numero) {
        super(cliente, numero);
    }
}
