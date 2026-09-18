package org.example.application;

import org.example.adapters.udp.ClienteUdp;
import org.example.domain.SolicitudSalario;

public class ClienteSalario {

    private ClienteUdp clienteUdp;

    public String conectar(
            String ip,
            int puerto) throws Exception {

        clienteUdp =
                new ClienteUdp(
                        ip,
                        puerto
                );

        return clienteUdp.conectar();
    }

    public String calcularSalario(
            float horasNormales,
            float horasExtras,
            float valorHora,
            float recargo) throws Exception {

        SolicitudSalario solicitud =
                new SolicitudSalario(
                        horasNormales,
                        horasExtras,
                        valorHora,
                        recargo
                );

        String mensaje =
                solicitud.convertirMensaje();

        return clienteUdp.enviarSalario(
                mensaje
        );
    }

    public void desconectar() {

        if (clienteUdp != null) {

            clienteUdp.desconectar();
        }
    }
}