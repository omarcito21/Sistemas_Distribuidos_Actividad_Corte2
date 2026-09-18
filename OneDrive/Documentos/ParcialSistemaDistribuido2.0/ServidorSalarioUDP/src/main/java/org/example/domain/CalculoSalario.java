package org.example.domain;

public class CalculoSalario {

    private float horasNormales;
    private float horasExtras;
    private float valorHoraNormal;
    private float porcentajeRecargo;

    public CalculoSalario(
            float horasNormales,
            float horasExtras,
            float valorHoraNormal,
            float porcentajeRecargo) {

        this.horasNormales = horasNormales;
        this.horasExtras = horasExtras;
        this.valorHoraNormal = valorHoraNormal;
        this.porcentajeRecargo = porcentajeRecargo;
    }

    public Salario calcular() {

        Salario salario = new Salario();

        if (horasNormales < 0) {
            salario.mensaje =
                    "Las horas normales no pueden ser negativas";
            return salario;
        }

        if (horasExtras < 0) {
            salario.mensaje =
                    "Las horas extras no pueden ser negativas";
            return salario;
        }

        if (valorHoraNormal <= 0) {
            salario.mensaje =
                    "El valor de la hora debe ser mayor a 0";
            return salario;
        }

        if (porcentajeRecargo < 0) {
            salario.mensaje =
                    "El recargo no puede ser negativo";
            return salario;
        }

        salario.pagoNormal =
                horasNormales * valorHoraNormal;

        float valorHoraExtra =
                valorHoraNormal +
                        (valorHoraNormal * porcentajeRecargo / 100);

        salario.pagoHorasExtras =
                horasExtras * valorHoraExtra;

        salario.salarioTotal =
                salario.pagoNormal +
                        salario.pagoHorasExtras;

        salario.mensaje =
                "Salario calculado correctamente";

        return salario;
    }

    public static class Salario {

        public float pagoNormal;
        public float pagoHorasExtras;
        public float salarioTotal;
        public String mensaje;
    }
}