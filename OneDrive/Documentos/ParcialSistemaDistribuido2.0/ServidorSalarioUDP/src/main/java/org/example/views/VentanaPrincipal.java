package org.example.views;

import org.example.adapters.Udp.ServidorUdp;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class VentanaPrincipal extends JFrame {

    private JButton btnIniciar;
    private JButton btnLimpiar;

    private JTextArea cajaLog;

    private JTextField campoIP;
    private JTextField campoPuerto;

    private JLabel txtEstado;

    private ServidorUdp servidor;

    public VentanaPrincipal() {

        setTitle("SERVIDOR UDP - SALARIO");

        setSize(600, 450);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {

        JPanel principal =
                new JPanel();

        principal.setLayout(
                new BoxLayout(
                        principal,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo =
                new JLabel(
                        "SERVIDOR UDP - SALARIO"
                );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        principal.add(titulo);

        principal.add(
                Box.createVerticalStrut(20)
        );

        JPanel conexion =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10
                        )
                );

        conexion.setBorder(
                BorderFactory.createTitledBorder(
                        "CONFIGURACIÓN UDP"
                )
        );

        conexion.add(
                new JLabel("DIRECCIÓN IP:")
        );

        campoIP =
                new JTextField();

        campoIP.setEditable(false);

        try {

            campoIP.setText(
                    InetAddress
                            .getLocalHost()
                            .getHostAddress()
            );

        } catch (Exception e) {

            campoIP.setText(
                    "No disponible"
            );
        }

        conexion.add(campoIP);

        conexion.add(
                new JLabel("PUERTO UDP:")
        );

        campoPuerto =
                new JTextField("9007");

        conexion.add(campoPuerto);

        conexion.add(
                new JLabel("ESTADO:")
        );

        txtEstado =
                new JLabel("OFF LINE");

        txtEstado.setForeground(
                Color.RED
        );

        conexion.add(txtEstado);

        btnIniciar =
                new JButton("INICIAR");

        btnIniciar.setForeground(
                Color.GREEN
        );

        btnIniciar.addActionListener(
                e -> iniciarDetener()
        );

        conexion.add(btnIniciar);

        principal.add(conexion);

        principal.add(
                Box.createVerticalStrut(20)
        );

        JPanel panelLog =
                new JPanel(
                        new BorderLayout()
                );

        panelLog.setBorder(
                BorderFactory.createTitledBorder(
                        "LOG DEL SERVIDOR UDP"
                )
        );

        cajaLog =
                new JTextArea(
                        12,
                        45
                );

        cajaLog.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(cajaLog);

        panelLog.add(
                scroll,
                BorderLayout.CENTER
        );

        btnLimpiar =
                new JButton("LIMPIAR");

        btnLimpiar.addActionListener(
                e -> cajaLog.setText("")
        );

        panelLog.add(
                btnLimpiar,
                BorderLayout.SOUTH
        );

        principal.add(panelLog);

        add(principal);
    }

    private void iniciarDetener() {

        if (btnIniciar
                .getText()
                .equalsIgnoreCase("INICIAR")) {

            try {

                int puerto =
                        Integer.parseInt(
                                campoPuerto
                                        .getText()
                                        .trim()
                        );

                if (puerto < 1 ||
                        puerto > 65535) {

                    JOptionPane.showMessageDialog(
                            this,
                            "El puerto debe estar entre 1 y 65535"
                    );

                    return;
                }

                servidor =
                        new ServidorUdp(
                                puerto,
                                this
                        );

                servidor.start();

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "El puerto debe ser numérico"
                );
            }

        } else {

            if (servidor != null) {

                servidor.detenerServidor();
            }
        }
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public JLabel getTxtEstado() {
        return txtEstado;
    }

    public JTextArea getCajaLog() {
        return cajaLog;
    }
}