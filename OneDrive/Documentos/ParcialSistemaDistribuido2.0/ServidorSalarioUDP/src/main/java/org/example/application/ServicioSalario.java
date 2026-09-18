package org.example.application;

import org.example.domain.CalculoSalario;

public class ServicioSalario implements SalarioUseCase {

    @Override
    public CalculoSalario.Salario calcularSalario(
            float horasNormales,
            float horasExtras,
            float valorHoraNormal,
            float porcentajeRecargo) {

        CalculoSalario calculo =
                new CalculoSalario(
                        horasNormales,
                        horasExtras,
                        valorHoraNormal,
                        porcentajeRecargo
                );

        return calculo.calcular();
    }
}