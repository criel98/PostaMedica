/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Package_Formulario;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Package_Clases.DatosPacienteCSV;
import Package_Clases.Paciente;
import Package_Clases.GestorColas;
import javax.print.attribute.standard.PrinterMessageFromOperator;

public class Admisiones extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admisiones.class.getName());
    DatosPacienteCSV gestor = new Package_Clases.DatosPacienteCSV();

    private DefaultTableModel modeloTabla;

    private void disableBox() {
        lblTipoDocumento.setVisible(false);
        cboTipoDocumento.setVisible(false);
        lblNumeroDocumento.setVisible(false);
        txtNumeroDocumento.setVisible(false);
        lblTelefono.setVisible(false);
        txtTelefono.setVisible(false);
        lblApellidoMaterno.setVisible(false);
        txtApellidoMaterno.setVisible(false);
        btnLimpiar.setEnabled(false);
        btnEditar.setEnabled(false);
    }

    private void enableBox() {
        lblTipoDocumento.setVisible(true);
        cboTipoDocumento.setVisible(true);
        lblNumeroDocumento.setVisible(true);
        txtNumeroDocumento.setVisible(true);
        lblTelefono.setVisible(true);
        txtTelefono.setVisible(true);
        lblApellidoMaterno.setVisible(true);
        txtApellidoMaterno.setVisible(true);
    }

    // REFACTORIZADO: Este método ahora lee la memoria limpia del gestor y pinta la JTable
    private void cargarTablaDesdeGestor() {
        modeloTabla.setRowCount(0); // Limpiamos tabla
        java.util.ArrayList<Paciente> lista = gestor.listarPacientes();

        // Recorremos la lista desde el último índice hasta el cero
        // lista.size() - 1 es el último elemento registrado
        for (int i = lista.size() - 1; i >= 0; i--) {
            Paciente p = lista.get(i);

            modeloTabla.addRow(new Object[]{
                p.getIdPaciente(),
                p.getTipoDocumento(),
                p.getNumeroDocumento(),
                p.getNombre() + " " + p.getApellidoPaterno() + " " + p.getApellidoMaterno(),
                p.getFechaNacimiento(),
                p.getGenero(),
                p.getNumeroTelefono(),
                p.getTipoPaciente(),});
        }
    }

    private void cargar_datos_formulario() {
        modeloTabla = (DefaultTableModel) tblPacientes.getModel();
        // Cargar combo documento
        this.cboTipoDocumento.removeAllItems();
        this.cboTipoDocumento.addItem("Seleccione");
        this.cboTipoDocumento.addItem("DNI");
        this.cboTipoDocumento.addItem("Carnet Extranjeria");

        // Cargar combo genero
        this.cboGenero.removeAllItems();
        this.cboGenero.addItem("Seleccione");
        this.cboGenero.addItem("Masculino");
        this.cboGenero.addItem("Femenino");

        //Cargar Combo Emergencia
        this.cboEmergencia.addItem("Seleccione");
        this.cboEmergencia.addItem("Emergencia");
        this.cboEmergencia.addItem("Nuevo");
    }

    // funcion para limpiar campos del formulario
    private void limpiarCampos() {
        cboTipoDocumento.setSelectedIndex(0);
        cboGenero.setSelectedIndex(0);
        cboEmergencia.setSelectedIndex(0);
        txtNumeroDocumento.setText("");
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtTelefono.setText("");
        txtEdad.setText("");
        txtFechaNacim.setText("");
        btnRegistrar.setEnabled(true);
        cboEmergencia.requestFocus(); // Devuelve el foco al inicio
    }

    // funcion para validar que todos los campós esten llenos
    private boolean validar_campos_formulario() {
        String tipoEmergencia = cboEmergencia.getSelectedItem().toString();

        if (tipoEmergencia.equals("Seleccione")) {
            JOptionPane.showMessageDialog(this, "Debe indicar si es una Emergencia o atención Regular.", "Validación", JOptionPane.WARNING_MESSAGE);
            cboEmergencia.requestFocus();
            return false;
        }

        if (cboGenero.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Género.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtNombre.getText().trim().isEmpty() || txtApellidoPaterno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El Nombre y Apellido Paterno son obligatorios siempre.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 2. Validaciones EXCLUSIVAS para atención Regular (Ignorar si es emergencia)
        if (tipoEmergencia.equals("Nuevo")) {
            // 1. Validaciones OBLIGATORIAS para ambos casos (Emergencia y Regular)
            if (cboTipoDocumento.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un Tipo de Documento", "Validación", JOptionPane.WARNING_MESSAGE);
                cboTipoDocumento.requestFocus();
                return false;
            }

            if (txtApellidoMaterno.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El Apellido Materno es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            String fechaNac = txtFechaNacim.getText().trim();
            if (fechaNac.equals("//") || fechaNac.length() < 10) {
                JOptionPane.showMessageDialog(this, "Complete la Fecha de Nacimiento correctamente (Día/Mes/Año).", "Validación", JOptionPane.WARNING_MESSAGE);
                txtFechaNacim.requestFocus();
                return false;
            }

            if (cboGenero.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un Género", "Validación", JOptionPane.WARNING_MESSAGE);
                cboGenero.requestFocus();
                return false;
            }

            if (txtTelefono.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe rellenar el número de contacto", "Validación", JOptionPane.WARNING_MESSAGE);
                txtTelefono.requestFocus();
                return false;
            }
        }

        return true;
    }

    public Admisiones() {

        initComponents();
        disableBox();
        cargar_datos_formulario();

        // Diseño bordes redondeados para el formulario
        txtNombre.putClientProperty("JComponent.roundRect", true);
        txtApellidoPaterno.putClientProperty("JComponent.roundRect", true);
        txtApellidoMaterno.putClientProperty("JComponent.roundRect", true);
        txtNumeroDocumento.putClientProperty("JComponent.roundRect", true);
        txtTelefono.putClientProperty("JComponent.roundRect", true);

        cargarTablaDesdeGestor();

        cboEmergencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String tipo = cboEmergencia.getSelectedItem().toString();
                if (tipo.equals("Emergencia")) {
                    disableBox();
                    lblApellidoMaterno.setVisible(true);
                    txtApellidoMaterno.setVisible(true);
                    lblTelefono.setVisible(true);
                    txtTelefono.setVisible(true);
                    btnLimpiar.setEnabled(true);
                    btnRegistrar.setText("Registrar Emergencia");

                } else {
                    enableBox();
                    btnLimpiar.setEnabled(true);
                    btnRegistrar.setText("Registrar Paciente");
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTipoDocumento = new javax.swing.JLabel();
        lblNumeroDocumento = new javax.swing.JLabel();
        txtNumeroDocumento = new javax.swing.JTextField();
        cboTipoDocumento = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        txtApellidoPaterno = new javax.swing.JTextField();
        txtApellidoMaterno = new javax.swing.JTextField();
        lblApellidoMaterno = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        cboGenero = new javax.swing.JComboBox<>();
        lblGenero = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtFechaNacim = new javax.swing.JFormattedTextField();
        txtEdad = new javax.swing.JTextField();
        lblEdad = new javax.swing.JLabel();
        lblTipoPaciente = new javax.swing.JLabel();
        cboEmergencia = new javax.swing.JComboBox<>();
        btnEditar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Clientes - Posta Médica");
        setMinimumSize(new java.awt.Dimension(852, 866));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnRegistrar.setBackground(new java.awt.Color(0, 102, 204));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar Paciente");
        btnRegistrar.setPreferredSize(new java.awt.Dimension(180, 40));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        tblPacientes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Tipo Doc.", "N° Documento", "Paciente", "F. Nacimiento", "Género", "Teléfono", "Atención"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPacientes.setToolTipText("holza");
        tblPacientes.setRowHeight(30);
        tblPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPacientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPacientes);
        if (tblPacientes.getColumnModel().getColumnCount() > 0) {
            tblPacientes.getColumnModel().getColumn(0).setResizable(false);
            tblPacientes.getColumnModel().getColumn(1).setResizable(false);
            tblPacientes.getColumnModel().getColumn(1).setPreferredWidth(29);
            tblPacientes.getColumnModel().getColumn(2).setResizable(false);
            tblPacientes.getColumnModel().getColumn(4).setResizable(false);
            tblPacientes.getColumnModel().getColumn(4).setPreferredWidth(55);
            tblPacientes.getColumnModel().getColumn(5).setResizable(false);
            tblPacientes.getColumnModel().getColumn(5).setPreferredWidth(45);
            tblPacientes.getColumnModel().getColumn(6).setResizable(false);
            tblPacientes.getColumnModel().getColumn(6).setPreferredWidth(45);
            tblPacientes.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 204));
        jLabel7.setText("REGISTRO DE PACIENTES");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Complete la informacion para registrar un nuevo paciente.");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 204));
        jLabel9.setText("LISTA DE PACIENTES REGISTRADOS");

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(51, 51, 51));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setPreferredSize(new java.awt.Dimension(180, 40));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(51, 51, 51));
        btnCerrar.setText("Cerrar");
        btnCerrar.setPreferredSize(new java.awt.Dimension(180, 40));
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 224, 230)));

        lblTipoDocumento.setForeground(new java.awt.Color(22, 30, 64));
        lblTipoDocumento.setText("Tipo Documento :");

        lblNumeroDocumento.setForeground(new java.awt.Color(22, 30, 64));
        lblNumeroDocumento.setText("Numero de Documento :");

        txtNumeroDocumento.setMinimumSize(null);
        txtNumeroDocumento.setPreferredSize(new java.awt.Dimension(64, 30));

        cboTipoDocumento.setMinimumSize(null);
        cboTipoDocumento.setPreferredSize(new java.awt.Dimension(64, 30));

        txtNombre.setMinimumSize(new java.awt.Dimension(64, 30));
        txtNombre.setPreferredSize(new java.awt.Dimension(64, 30));

        txtApellidoPaterno.setMinimumSize(new java.awt.Dimension(64, 30));
        txtApellidoPaterno.setPreferredSize(new java.awt.Dimension(64, 30));
        txtApellidoPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoPaternoActionPerformed(evt);
            }
        });

        txtApellidoMaterno.setMinimumSize(new java.awt.Dimension(64, 30));
        txtApellidoMaterno.setPreferredSize(new java.awt.Dimension(64, 30));

        lblApellidoMaterno.setForeground(new java.awt.Color(22, 30, 64));
        lblApellidoMaterno.setText("Apellido Materno :");

        lblApellidoPaterno.setForeground(new java.awt.Color(22, 30, 64));
        lblApellidoPaterno.setText("Apellido Paterno :");

        lblNombre.setForeground(new java.awt.Color(22, 30, 64));
        lblNombre.setText("Nombre :");

        lblFechaNacimiento.setForeground(new java.awt.Color(22, 30, 64));
        lblFechaNacimiento.setText("Fecha de Nacimiento:");

        cboGenero.setMinimumSize(new java.awt.Dimension(64, 30));
        cboGenero.setPreferredSize(new java.awt.Dimension(64, 30));

        lblGenero.setForeground(new java.awt.Color(22, 30, 64));
        lblGenero.setText("Genero :");

        lblTelefono.setForeground(new java.awt.Color(22, 30, 64));
        lblTelefono.setText("Telefono:");

        txtTelefono.setMinimumSize(new java.awt.Dimension(64, 30));
        txtTelefono.setPreferredSize(new java.awt.Dimension(64, 30));

        try {
            txtFechaNacim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaNacim.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaNacim.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtFechaNacim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFechaNacim.setMinimumSize(new java.awt.Dimension(64, 30));
        txtFechaNacim.setPreferredSize(new java.awt.Dimension(64, 30));
        txtFechaNacim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaNacimActionPerformed(evt);
            }
        });
        txtFechaNacim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaNacimKeyReleased(evt);
            }
        });

        txtEdad.setEditable(false);
        txtEdad.setBackground(new java.awt.Color(255, 255, 255));
        txtEdad.setPreferredSize(new java.awt.Dimension(71, 30));

        lblEdad.setText("Edad:");

        lblTipoPaciente.setForeground(new java.awt.Color(22, 30, 64));
        lblTipoPaciente.setText("TIpo de Paciente :");

        cboEmergencia.setMinimumSize(new java.awt.Dimension(64, 30));
        cboEmergencia.setPreferredSize(new java.awt.Dimension(64, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFechaNacim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEdad)
                            .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGenero)
                            .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTelefono)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(15, 15, 15))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(lblTipoPaciente)
                            .addComponent(cboEmergencia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cboTipoDocumento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblApellidoPaterno, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                            .addComponent(lblTipoDocumento))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblApellidoMaterno)
                                    .addComponent(lblNumeroDocumento))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTipoPaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboEmergencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipoDocumento)
                            .addComponent(lblNumeroDocumento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(lblNombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(lblApellidoPaterno)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblApellidoMaterno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblFechaNacimiento)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtFechaNacim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblTelefono)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblGenero)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtEdad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblEdad)
                        .addGap(36, 36, 36)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(51, 51, 51));
        btnEditar.setText("Editar");
        btnEditar.setPreferredSize(new java.awt.Dimension(180, 40));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(51, 51, 51));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel14.setText("Digite su Documento :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar))
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(btnBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtApellidoPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoPaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoPaternoActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose(); // Cierra de forma correcta esta ventana actual
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
       // 1. Validación de campos
        if (!validar_campos_formulario()) {
            return;
        }

        // 2. Extracción de datos
        String numDoc = txtNumeroDocumento.getText().trim();
        String nombre = txtNombre.getText().toUpperCase().trim();
        String apPaterno = txtApellidoPaterno.getText().toUpperCase().trim();
        String apMaterno = txtApellidoMaterno.getText().toUpperCase().trim();
        String fechaNac = txtFechaNacim.getText().trim();
        String genero = (cboGenero.getSelectedIndex() == 0) ? "No especifica" : cboGenero.getSelectedItem().toString();
        String tipoDoc = (cboTipoDocumento.getSelectedIndex() == 0) ? "" : cboTipoDocumento.getSelectedItem().toString();
        
        // Manejo seguro del número de teléfono (por si el usuario deja el campo vacío)
        int numero = 0;
        try {
            numero = Integer.parseInt(txtTelefono.getText().trim());
        } catch (NumberFormatException e) {
            numero = 0; 
        }
        
        String tipoAtencion = cboEmergencia.getSelectedItem().toString();
        
        // 3. Generación de ID
        int idGenerado = (int) (System.currentTimeMillis() % 100000);

        // 4. Creación del objeto
        Package_Clases.Paciente nuevoPaciente = new Package_Clases.Paciente(
                idGenerado, nombre, apPaterno, apMaterno, fechaNac, genero, tipoDoc, numDoc, numero, tipoAtencion
        );
        
        // 5. Persistencia (Guardado en CSV)
        gestor.Guardar(nuevoPaciente);
        
        // 6. ENRUTAMIENTO: Derivar a la cola correspondiente
        if (tipoAtencion.equalsIgnoreCase("Emergencia")) {
            GestorColas.getInstancia().encolarConsultorio(nuevoPaciente);
            JOptionPane.showMessageDialog(this, "Paciente registrado. ¡DERIVADO A EMERGENCIA!");
        } else {
            GestorColas.getInstancia().encolarTriaje(nuevoPaciente);
            JOptionPane.showMessageDialog(this, "Paciente registrado y enviado a Triaje.");
        }
        limpiarCampos();
        cargarTablaDesdeGestor();

    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void txtFechaNacimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaNacimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaNacimActionPerformed

    private void txtFechaNacimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaNacimKeyReleased
        String fechaTexto = txtFechaNacim.getText().trim();

        // Verificamos formato completo (evitamos el "/" del inicio)
        if (fechaTexto.length() == 10 && !fechaTexto.contains(" ")) {

            // CORRECCIÓN: Usamos la clase Persona correctamente (Mayúscula)
            Package_Clases.Persona temporal = new Package_Clases.Persona();

            // Le inyectamos únicamente la fecha
            temporal.setFechaNacimiento(fechaTexto);

            // Calculamos la edad usando el método que ya diseñaste
            int edadCalculada = temporal.calcularEdad();

            if (edadCalculada >= 0 && edadCalculada < 130) {
                txtEdad.setText(String.valueOf(edadCalculada));
            } else {
                txtEdad.setText("");
            }
        } else {
            txtEdad.setText(""); // Limpia si la fecha está incompleta
        }
    }//GEN-LAST:event_txtFechaNacimKeyReleased

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        cargarTablaDesdeGestor();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (!validar_campos_formulario()) {
            return;
        }

        int filaSeleccionada = tblPacientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente de la tabla para editar.");
            return;
        }

        // 1. OBTENEMOS EL CÓDIGO ÚNICO (Columna 0)
        String codigoSeleccionado = tblPacientes.getValueAt(filaSeleccionada, 0).toString();
        String numDocNuevo = txtNumeroDocumento.getText().trim();

        // 2. VALIDACIÓN DE DUPLICADOS: Solo si hay un DNI ingresado
        if (!numDocNuevo.isEmpty()) {

        }

        // 3. RECOLECTAMOS DATOS
        String tipoDoc = (cboTipoDocumento.getSelectedIndex() == 0) ? "" : cboTipoDocumento.getSelectedItem().toString();
        String nombre = txtNombre.getText().toUpperCase().trim();
        String apPaterno = txtApellidoPaterno.getText().toUpperCase().trim();
        String apMaterno = txtApellidoMaterno.getText().toUpperCase().trim();
        String fechaNac = txtFechaNacim.getText().trim();
        String genero = (cboGenero.getSelectedIndex() == 0) ? "No especifica" : cboGenero.getSelectedItem().toString();
        String telefono = txtTelefono.getText().trim();
        String tipoAtencion = cboEmergencia.getSelectedItem().toString();

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String dniBusqueda = txtBuscar.getText().trim();
        if (dniBusqueda.isEmpty()) {
            return;
        }
        Paciente pacienteEncontrado = gestor.Buscar(dniBusqueda);
        if (pacienteEncontrado != null) {
            // Rellenamos el formulario automáticamente
            cboEmergencia.setSelectedItem(pacienteEncontrado.getTipoPaciente());
            cboTipoDocumento.setSelectedItem(pacienteEncontrado.getTipoDocumento());
            txtNombre.setText(pacienteEncontrado.getNombre());
            txtApellidoPaterno.setText(pacienteEncontrado.getApellidoPaterno());
            txtApellidoMaterno.setText(pacienteEncontrado.getApellidoMaterno());
            txtNumeroDocumento.setText(pacienteEncontrado.getNumeroDocumento());
            txtFechaNacim.setText(pacienteEncontrado.getFechaNacimiento());
            cboGenero.setSelectedItem(pacienteEncontrado.getGenero());
            txtTelefono.setText(Integer.toString(pacienteEncontrado.getNumeroTelefono()));
            txtBuscar.setText("");
            JOptionPane.showMessageDialog(this, "Paciente con encontrado: "+ pacienteEncontrado.getNombre()+ " (ID: " + pacienteEncontrado.getIdPaciente() + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Paciente no registrado. Puede proceder a registrarlo como nuevo.");
        }


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacientesMouseClicked
        // Verificamos si el usuario hizo DOBLE CLIC (2)
        if (evt.getClickCount() == 2) {
            int filaSeleccionada = tblPacientes.getSelectedRow();

            if (filaSeleccionada != -1) {
                // Obtenemos el N° de Documento de la fila seleccionada (que está en la columna 2)
                String numDocSeleccionado = tblPacientes.getValueAt(filaSeleccionada, 2).toString();
                Paciente p = gestor.Buscar(numDocSeleccionado);

                if (p != null) {
                    cboEmergencia.setSelectedItem(String.valueOf(p.getTipoPaciente()));
                    cboTipoDocumento.setSelectedItem(p.getTipoDocumento());
                    txtNumeroDocumento.setText(p.getNumeroDocumento());
                    txtNombre.setText(p.getNombre());
                    txtApellidoPaterno.setText(p.getApellidoPaterno());
                    txtApellidoMaterno.setText(p.getApellidoMaterno());
                    txtFechaNacim.setText(p.getFechaNacimiento());
                    cboGenero.setSelectedItem(p.getGenero());
                    txtEdad.setText(String.valueOf(p.calcularEdad()));
                    txtTelefono.setText(String.valueOf(p.getNumeroTelefono()));
                }
            }
        }
    }//GEN-LAST:event_tblPacientesMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Admisiones().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cboEmergencia;
    private javax.swing.JComboBox<String> cboGenero;
    private javax.swing.JComboBox<String> cboTipoDocumento;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumeroDocumento;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipoDocumento;
    private javax.swing.JLabel lblTipoPaciente;
    private javax.swing.JTable tblPacientes;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JFormattedTextField txtFechaNacim;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
