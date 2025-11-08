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

        if (percentual <= 0f) throw new IllegalArgumentException("Percentual deve ser positivo.");
        float fator = 1f + (percentual / 100f); // CERTO
        Saldo *= fator;


    }
}
