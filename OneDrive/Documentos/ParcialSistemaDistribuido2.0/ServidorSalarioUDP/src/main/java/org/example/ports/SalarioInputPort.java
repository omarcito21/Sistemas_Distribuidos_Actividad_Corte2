package org.example.ports;

import org.example.domain.CalculoSalario;

public interface SalarioInputPort {

    CalculoSalario.Salario calcular(
            float horasNormales,
            float horasExtras,
            float valorHoraNormal,
            float porcentajeRecargo
    );
}