package org.example.ports;

public interface SalarioOutputPort {

    void enviarRespuesta(
            String mensaje,
            String ip,
            int puerto
    );
}