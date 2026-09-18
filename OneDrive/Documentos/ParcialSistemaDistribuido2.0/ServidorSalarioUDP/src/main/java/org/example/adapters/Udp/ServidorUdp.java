package org.example.adapters.Udp;

import org.example.application.ServicioSalario;
import org.example.domain.CalculoSalario;
import org.example.ports.SalarioInputPort;
import org.example.ports.SalarioOutputPort;
import org.example.views.VentanaPrincipal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorUdp extends Thread
        implements SalarioInputPort, SalarioOutputPort {

    private final int puerto;
    private final VentanaPrincipal ventana;

    private DatagramSocket socket;
    private boolean estado = false;

    private final ServicioSalario servicioSalario;

    public ServidorUdp(
            int puerto,
            VentanaPrincipal ventana) {

        this.puerto = puerto;
        this.ventana = ventana;

        this.servicioSalario =
                new ServicioSalario();
    }

    @Override
    public void run() {

        iniciarServidor();
    }

    public void iniciarServidor() {

        try {

            socket = new DatagramSocket(puerto);

            estado = true;

            ventana.getBtnIniciar()
                    .setText("DETENER");

            ventana.getTxtEstado()
                    .setText("ONLINE");

            ventana.getTxtEstado()
                    .setForeground(
                            java.awt.Color.GREEN
                    );

            escribirLog(
                    "Servidor UDP iniciado en el puerto "
                            + puerto
            );

            while (estado) {

                byte[] buffer = new byte[1024];

                DatagramPacket paquete =
                        new DatagramPacket(
                                buffer,
                                buffer.length
                        );

                socket.receive(paquete);

                String mensaje =
                        new String(
                                paquete.getData(),
                                0,
                                paquete.getLength(),
                                StandardCharsets.UTF_8
                        );

                InetAddress direccion =
                        paquete.getAddress();

                int puertoCliente =
                        paquete.getPort();

                String ip =
                        direccion.getHostAddress();

                escribirLog(
                        "Mensaje recibido de "
                                + ip
                                + ":"
                                + puertoCliente
                                + " -> "
                                + mensaje
                );

                procesarMensaje(
                        mensaje,
                        direccion,
                        puertoCliente
                );
            }

        } catch (IOException e) {

            if (estado) {

                escribirLog(
                        "Error en el servidor UDP: "
                                + e.getMessage()
                );
            }

        } finally {

            cerrarServidor();
        }
    }

    private void procesarMensaje(
            String mensaje,
            InetAddress direccion,
            int puertoCliente) {

        try {

            if (mensaje.equalsIgnoreCase("CONECTAR")) {

                enviarRespuesta(
                        "CONECTADO|Servidor UDP listo",
                        direccion.getHostAddress(),
                        puertoCliente
                );

                escribirLog(
                        "Cliente conectado: "
                                + direccion.getHostAddress()
                );

                return;
            }

            if (mensaje.startsWith("SALARIO|")) {

                String[] datos =
                        mensaje.split("\\|");

                if (datos.length != 5) {

                    enviarRespuesta(
                            "ERROR|Cantidad de datos incorrecta",
                            direccion.getHostAddress(),
                            puertoCliente
                    );

                    return;
                }

                float horasNormales =
                        Float.parseFloat(datos[1]);

                float horasExtras =
                        Float.parseFloat(datos[2]);

                float valorHora =
                        Float.parseFloat(datos[3]);

                float recargo =
                        Float.parseFloat(datos[4]);

                CalculoSalario.Salario salario =
                        calcular(
                                horasNormales,
                                horasExtras,
                                valorHora,
                                recargo
                        );

                String respuesta =
                        "OK|"
                                + salario.pagoNormal
                                + "|"
                                + salario.pagoHorasExtras
                                + "|"
                                + salario.salarioTotal
                                + "|"
                                + salario.mensaje;

                enviarRespuesta(
                        respuesta,
                        direccion.getHostAddress(),
                        puertoCliente
                );

                escribirLog(
                        "Salario calculado y enviado"
                );
            }

        } catch (NumberFormatException e) {

            enviarRespuesta(
                    "ERROR|Los datos enviados no son válidos",
                    direccion.getHostAddress(),
                    puertoCliente
            );

        }
    }

    @Override
    public CalculoSalario.Salario calcular(
            float horasNormales,
            float horasExtras,
            float valorHoraNormal,
            float porcentajeRecargo) {

        return servicioSalario.calcularSalario(
                horasNormales,
                horasExtras,
                valorHoraNormal,
                porcentajeRecargo
        );
    }

    @Override
    public void enviarRespuesta(
            String mensaje,
            String ip,
            int puertoCliente) {

        try {

            byte[] datos =
                    mensaje.getBytes(
                            StandardCharsets.UTF_8
                    );

            InetAddress direccion =
                    InetAddress.getByName(ip);

            DatagramPacket paquete =
                    new DatagramPacket(
                            datos,
                            datos.length,
                            direccion,
                            puertoCliente
                    );

            socket.send(paquete);

            escribirLog(
                    "Respuesta enviada a "
                            + ip
                            + ":"
                            + puertoCliente
                            + " -> "
                            + mensaje
            );

        } catch (IOException e) {

            escribirLog(
                    "Error enviando respuesta: "
                            + e.getMessage()
            );
        }
    }

    public void detenerServidor() {

        estado = false;

        if (socket != null &&
                !socket.isClosed()) {

            socket.close();
        }

        ventana.getBtnIniciar()
                .setText("INICIAR");

        ventana.getTxtEstado()
                .setText("OFF LINE");

        ventana.getTxtEstado()
                .setForeground(
                        java.awt.Color.RED
                );

        escribirLog(
                "Servidor UDP detenido"
        );
    }

    private void cerrarServidor() {

        if (socket != null &&
                !socket.isClosed()) {

            socket.close();
        }

        estado = false;
    }

    private void escribirLog(String mensaje) {

        String fecha =
                new SimpleDateFormat(
                        "dd-MM-yyyy HH:mm:ss"
                ).format(new Date());

        String texto =
                fecha + " - " + mensaje;

        System.out.println(texto);

        javax.swing.SwingUtilities.invokeLater(
                () -> ventana.getCajaLog()
                        .append(texto + "\n")
        );
    }
}