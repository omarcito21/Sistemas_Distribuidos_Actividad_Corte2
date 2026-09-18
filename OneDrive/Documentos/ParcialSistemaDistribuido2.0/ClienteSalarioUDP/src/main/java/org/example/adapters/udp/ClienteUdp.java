package org.example.adapters.udp;

import org.example.ports.SalarioClientPort;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ClienteUdp implements SalarioClientPort {

    private DatagramSocket socket;

    private InetAddress servidor;

    private int puerto;

    public ClienteUdp(
            String ip,
            int puerto) throws Exception {

        this.servidor =
                InetAddress.getByName(ip);

        this.puerto = puerto;

        socket =
                new DatagramSocket();

        socket.setSoTimeout(5000);
    }

    @Override
    public String conectar() throws Exception {

        enviar("CONECTAR");

        return recibir();
    }

    @Override
    public String enviarSalario(
            String mensaje) throws Exception {

        enviar(mensaje);

        return recibir();
    }

    private void enviar(
            String mensaje) throws Exception {

        byte[] datos =
                mensaje.getBytes(
                        StandardCharsets.UTF_8
                );

        DatagramPacket paquete =
                new DatagramPacket(
                        datos,
                        datos.length,
                        servidor,
                        puerto
                );

        socket.send(paquete);
    }

    private String recibir() throws Exception {

        byte[] buffer =
                new byte[2048];

        DatagramPacket paquete =
                new DatagramPacket(
                        buffer,
                        buffer.length
                );

        socket.receive(paquete);

        return new String(
                paquete.getData(),
                0,
                paquete.getLength(),
                StandardCharsets.UTF_8
        );
    }

    @Override
    public void desconectar() {

        if (socket != null &&
                !socket.isClosed()) {

            socket.close();
        }
    }

    public boolean estaActivo() {

        return socket != null &&
                !socket.isClosed();
    }
}