package org.example.ports;

public interface SalarioClientPort {

    String conectar() throws Exception;

    String enviarSalario(String mensaje)
            throws Exception;

    void desconectar();
}