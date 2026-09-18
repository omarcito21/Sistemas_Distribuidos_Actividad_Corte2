package org.example.views;

import org.example.application.ClienteSalario;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JTextField txtIp;
    private JTextField txtPuerto;

    private JTextField txtHorasNormales;
    private JTextField txtHorasExtras;
    private JTextField txtValorHora;
    private JTextField txtRecargo;

    private JLabel lblEstado;
    private JLabel lblPagoNormal;
    private JLabel lblPagoExtras;
    private JLabel lblSalarioTotal;
    private JLabel lblMensaje;

    private JButton btnConectar;
    private JButton btnDesconectar;
    private JButton btnCalcular;

    private ClienteSalario cliente;

    public VentanaPrincipal() {

        setTitle("CLIENTE UDP - SALARIO");

        setSize(650, 520);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        construirInterfaz();
    }

    private void construirInterfaz() {

        JTabbedPane pestañas =
                new JTabbedPane();

        pestañas.addTab(
                "CONEXIÓN UDP",
                crearPanelConexion()
        );

        pestañas.addTab(
                "CALCULAR SALARIO",
                crearPanelSalario()
        );

        add(pestañas);
    }

    private JPanel crearPanelConexion() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );

        panel.add(
                new JLabel("IP DEL SERVIDOR:")
        );

        txtIp =
                new JTextField("localhost");

        panel.add(txtIp);

        panel.add(
                new JLabel("PUERTO UDP:")
        );

        txtPuerto =
                new JTextField("9007");

        panel.add(txtPuerto);

        panel.add(
                new JLabel("ESTADO:")
        );

        lblEstado =
                new JLabel("Desconectado");

        lblEstado.setForeground(
                Color.RED
        );

        panel.add(lblEstado);

        btnConectar =
                new JButton("CONECTAR");

        panel.add(btnConectar);

        btnDesconectar =
                new JButton("DESCONECTAR");

        btnDesconectar.setEnabled(false);

        panel.add(btnDesconectar);

        btnConectar.addActionListener(
                e -> conectar()
        );

        btnDesconectar.addActionListener(
                e -> desconectar()
        );

        return panel;
    }

    private JPanel crearPanelSalario() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                9,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        panel.add(
                new JLabel(
                        "Horas normales trabajadas:"
                )
        );

        txtHorasNormales =
                new JTextField();

        panel.add(txtHorasNormales);

        panel.add(
                new JLabel(
                        "Horas extras:"
                )
        );

        txtHorasExtras =
                new JTextField();

        panel.add(txtHorasExtras);

        panel.add(
                new JLabel(
                        "Valor de la hora normal:"
                )
        );

        txtValorHora =
                new JTextField();

        panel.add(txtValorHora);

        panel.add(
                new JLabel(
                        "Porcentaje de recargo:"
                )
        );

        txtRecargo =
                new JTextField();

        panel.add(txtRecargo);

        btnCalcular =
                new JButton(
                        "CALCULAR SALARIO"
                );

        btnCalcular.setEnabled(false);

        panel.add(btnCalcular);

        panel.add(new JLabel());

        panel.add(
                new JLabel("Pago normal:")
        );

        lblPagoNormal =
                new JLabel("$0.00");

        panel.add(lblPagoNormal);

        panel.add(
                new JLabel(
                        "Pago horas extras:"
                )
        );

        lblPagoExtras =
                new JLabel("$0.00");

        panel.add(lblPagoExtras);

        panel.add(
                new JLabel("Salario total:")
        );

        lblSalarioTotal =
                new JLabel("$0.00");

        lblSalarioTotal.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        panel.add(lblSalarioTotal);

        panel.add(
                new JLabel("Mensaje:")
        );

        lblMensaje =
                new JLabel("");

        panel.add(lblMensaje);

        btnCalcular.addActionListener(
                e -> calcularSalario()
        );

        return panel;
    }

    private void conectar() {

        try {

            String ip =
                    txtIp.getText().trim();

            int puerto =
                    Integer.parseInt(
                            txtPuerto
                                    .getText()
                                    .trim()
                    );

            if (puerto < 1 ||
                    puerto > 65535) {

                JOptionPane.showMessageDialog(
                        this,
                        "Puerto inválido"
                );

                return;
            }

            cliente =
                    new ClienteSalario();

            String respuesta =
                    cliente.conectar(
                            ip,
                            puerto
                    );

            String[] datos =
                    respuesta.split(
                            "\\|",
                            2
                    );

            if (datos[0]
                    .equalsIgnoreCase(
                            "CONECTADO"
                    )) {

                lblEstado.setText(
                        "CONECTADO"
                );

                lblEstado.setForeground(
                        Color.GREEN
                );

                btnConectar.setEnabled(
                        false
                );

                btnDesconectar.setEnabled(
                        true
                );

                btnCalcular.setEnabled(
                        true
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Conectado al servidor UDP."
                );

            } else {

                cliente.desconectar();

                JOptionPane.showMessageDialog(
                        this,
                        "El servidor no aceptó la conexión."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El puerto debe ser numérico."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar al servidor UDP.\n"
                            + "Verifique que el servidor esté iniciado."
            );
        }
    }

    private void calcularSalario() {

        try {

            float horasNormales =
                    Float.parseFloat(
                            txtHorasNormales
                                    .getText()
                                    .trim()
                    );

            float horasExtras =
                    Float.parseFloat(
                            txtHorasExtras
                                    .getText()
                                    .trim()
                    );

            float valorHora =
                    Float.parseFloat(
                            txtValorHora
                                    .getText()
                                    .trim()
                    );

            float recargo =
                    Float.parseFloat(
                            txtRecargo
                                    .getText()
                                    .trim()
                    );

            if (horasNormales < 0 ||
                    horasExtras < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Las horas no pueden ser negativas."
                );

                return;
            }

            if (valorHora <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El valor de la hora debe ser mayor que 0."
                );

                return;
            }

            if (recargo < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El recargo no puede ser negativo."
                );

                return;
            }

            Thread hilo =
                    new Thread(() -> {

                        try {

                            String respuesta =
                                    cliente.calcularSalario(
                                            horasNormales,
                                            horasExtras,
                                            valorHora,
                                            recargo
                                    );

                            procesarRespuesta(
                                    respuesta
                            );

                        } catch (Exception e) {

                            SwingUtilities.invokeLater(
                                    () -> JOptionPane.showMessageDialog(
                                            this,
                                            "Error de comunicación UDP."
                                    )
                            );
                        }
                    });

            hilo.start();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Todos los campos deben contener números válidos."
            );
        }
    }

    private void procesarRespuesta(
            String respuesta) {

        String[] datos =
                respuesta.split(
                        "\\|",
                        5
                );

        SwingUtilities.invokeLater(() -> {

            if (datos[0]
                    .equalsIgnoreCase("OK")) {

                float pagoNormal =
                        Float.parseFloat(
                                datos[1]
                        );

                float pagoExtras =
                        Float.parseFloat(
                                datos[2]
                        );

                float salarioTotal =
                        Float.parseFloat(
                                datos[3]
                        );

                lblPagoNormal.setText(
                        String.format(
                                "$%,.2f",
                                pagoNormal
                        )
                );

                lblPagoExtras.setText(
                        String.format(
                                "$%,.2f",
                                pagoExtras
                        )
                );

                lblSalarioTotal.setText(
                        String.format(
                                "$%,.2f",
                                salarioTotal
                        )
                );

                lblMensaje.setText(
                        datos.length > 4
                                ? datos[4]
                                : ""
                );

            } else {

                lblMensaje.setText(
                        datos.length > 1
                                ? datos[1]
                                : "Error"
                );
            }
        });
    }

    private void desconectar() {

        if (cliente != null) {

            cliente.desconectar();
        }

        lblEstado.setText(
                "Desconectado"
        );

        lblEstado.setForeground(
                Color.RED
        );

        btnConectar.setEnabled(
                true
        );

        btnDesconectar.setEnabled(
                false
        );

        btnCalcular.setEnabled(
                false
        );
    }
}