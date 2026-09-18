package org.example.domain;

public class SolicitudSalario {

    private float horasNormales;
    private float horasExtras;
    private float valorHora;
    private float recargo;

    public SolicitudSalario(
            float horasNormales,
            float horasExtras,
            float valorHora,
            float recargo) {

        this.horasNormales = horasNormales;
        this.horasExtras = horasExtras;
        this.valorHora = valorHora;
        this.recargo = recargo;
    }

    public String convertirMensaje() {

        return "SALARIO|"
                + horasNormales
                + "|"
                + horasExtras
                + "|"
                + valorHora
                + "|"
                + recargo;
    }
}