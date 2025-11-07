package p.Domain.entities;

import p.Domain.tipos.CPF;

public class Cliente {
    String Nome;
    public CPF Cpf;
    public Cliente(String nome, CPF cpf) {
        Nome = nome;
        Cpf = cpf;
    }
}
