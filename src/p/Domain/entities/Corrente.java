package p.Domain.entities;

import p.Domain.tipos.NumeroConta;

public class Corrente extends Conta {
    public Corrente(Cliente cliente, NumeroConta numero) {
        super(cliente, numero);
    }

    public Corrente(Cliente cliente, NumeroConta numero, float saldo) {
        super(cliente, numero, saldo);
    }
}
