package org.example.application;

import org.example.domain.CalculoSalario;

public interface SalarioUseCase {

    CalculoSalario.Salario calcularSalario(
            float horasNormales,
            float horasExtras,
            float valorHoraNormal,
            float porcentajeRecargo
    );
}