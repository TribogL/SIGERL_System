package FrontEnd;

import java.awt.Toolkit;
import java.awt.Image;

import BackEnd.ClsMetEquipment;
import BackEnd.ClsMetRequests;

import java.awt.Color;

import java.sql.Date;
import java.time.LocalDate;
import com.toedter.calendar.JDateChooser;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FRMReservations extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMReservations.class.getName());
    private ClsMetEquipment metEquipment;
    private ClsMetRequests metRequests;
    private int currentUserId; // Segun lo escrito en login
    private boolean isAdmin; // Segun lo escrito en login

    public FRMReservations() {
        this(1, true); // Default para testear (comentar antes de presentacion)
    }

    // Metodo constructor
    public FRMReservations(int userId, boolean isAdmin) {
        this.currentUserId = userId;
        this.isAdmin = isAdmin;
        
        initComponents();
        setLocationRelativeTo(this);
        
        metEquipment = new ClsMetEquipment();
        metRequests = new ClsMetRequests();
        
        applyPanelStyling();
        loadEquipmentComboBox();
        loadReservationStats();
        loadCurrentReservations();
        setupDateChoosers();
    }

    private void applyPanelStyling() {
        jPanel2.setBackground(new java.awt.Color(102, 204, 255, 190));
        jPanel2.setOpaque(true);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));

        pnlNavigation.setBackground(new java.awt.Color(51, 255, 255, 170));
        pnlNavigation.setOpaque(true);
        pnlNavigation.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));
        pnlNavigation.setVisible(false);

        pnlTotalItems1.setBackground(new java.awt.Color(102, 204, 255, 190));
        pnlTotalItems1.setOpaque(true);
        pnlTotalItems1.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));

        pnlStock.setBackground(new java.awt.Color(102, 204, 255, 190));
        pnlStock.setOpaque(true);
        pnlStock.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));
        
        pnlStatus.setBackground(new java.awt.Color(102, 204, 255, 190));
        pnlStatus.setOpaque(true);
        pnlStatus.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));

        pnlCritical.setBackground(new java.awt.Color(102, 204, 255, 190));
        pnlCritical.setOpaque(true);
        pnlCritical.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));

        pnlCategories.setBackground(new java.awt.Color(102, 204, 255, 190));
        pnlCategories.setOpaque(true);
        pnlCategories.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));

        pnlReserve.setBackground(new java.awt.Color(102, 204, 255, 190));
        pnlReserve.setOpaque(true);
        pnlReserve.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));

        pnlReservations.setBackground(new java.awt.Color(102, 204, 255, 190));
        pnlReservations.setOpaque(true);
        pnlReservations.setBorder(javax.swing.BorderFactory.createLineBorder(
            new java.awt.Color(127, 222, 255, 51), 1));
    }

     // Setup de date choosers con validacion
    private void setupDateChoosers() {
        // Initialize JDateChoosers (add these to your form in Design view)
        jDateChooserStart = new JDateChooser();
        jDateChooserEnd = new JDateChooser();
        
        // Limitacion de fecha de inicio a hoy
        jDateChooserStart.setMinSelectableDate(java.util.Date.from(
            LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
        jDateChooserEnd.setMinSelectableDate(java.util.Date.from(
            LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
        
        // Add to panel (adjust coordinates as needed)
        pnlReserve.add(jDateChooserStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 180, 25));
        pnlReserve.add(jDateChooserEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 350, 25));
        
        // Chequeo de rango de fecha inmediato (apenas es seleccionada)
        jDateChooserEnd.addPropertyChangeListener("date", evt -> validateDateRange());
    }
    
    // Validacion si fecha final sigue a la fecha inicial
    private void validateDateRange() {
        if (jDateChooserStart.getDate() != null && jDateChooserEnd.getDate() != null) {
            if (jDateChooserEnd.getDate().before(jDateChooserStart.getDate())) {
                JOptionPane.showMessageDialog(this,
                    "End date must be after start date",
                    "Invalid Date Range",
                    JOptionPane.WARNING_MESSAGE);
                jDateChooserEnd.setDate(null);
            }
        }
    }
    
    // Carga de equipos al combobox con indicarod de dispinibilidad
    private void loadEquipmentComboBox() {
        DefaultTableModel equipmentModel = metEquipment.ListEquipment();
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
        comboModel.addElement("Select equipment");
        
        for (int i = 0; i < equipmentModel.getRowCount(); i++) {
            int id = (int) equipmentModel.getValueAt(i, 0);
            String name = (String) equipmentModel.getValueAt(i, 1);
            int quantity = (int) equipmentModel.getValueAt(i, 6);
            boolean available = (boolean) equipmentModel.getValueAt(i, 8);
            
            // Formato: "ID - Name (Cantidad disponible)"
            String item = String.format("%d - %s (%d available)", id, name, quantity);
            
            // Guardar ID
            comboModel.addElement(item);
        }
        
        filterResEq.setModel(comboModel);
        
        // Metodo para mostrar equipos no disponibles en gris
        filterResEq.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value != null && !value.toString().equals("Select equipment")) {
                    String item = value.toString();
                    // Validacion de si hay stock disponible
                    if (item.contains("(0 available)")) {
                        setForeground(Color.GRAY);
                        setEnabled(false);
                    } else if (!isSelected) {
                        setForeground(Color.BLACK);
                    }
                }
                
                return this;
            }
        });
    }

    // Trae el ID del equipo seleccionado en el ComboBox
    private int getSelectedEquipmentId() {
        String selected = (String) filterResEq.getSelectedItem();
        if (selected == null || selected.equals("Select equipment")) {
            return -1;
        }
        
        // Extract ID from "ID - Name (Quantity available)" format
        try {
            return Integer.parseInt(selected.split(" - ")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    // Carga de estadiscticas de reserva
    private void loadReservationStats() {
        int totalEquip = metEquipment.getTotalEquipmentCount();
        int available = metEquipment.getAvailableEquipmentCount();
        int inUse = metEquipment.getActiveEquipmentToday();
        int todaysBookings = metRequests.getActiveReservationsToday();

        txtTotalEquipment.setText(String.valueOf(totalEquip));
        txtAvailable.setText(String.valueOf(available));
        txtUsed.setText(String.valueOf(inUse));
        txtReservations.setText(String.valueOf(todaysBookings));
    }

    // Llenado de tabla de reservaciones
    private void loadCurrentReservations() {
        DefaultTableModel model = metRequests.listAllReservations();
        jTable1.setModel(model);
    }

    // Para hacer nueva reservacion
    private void btnNewResActionPerformed(java.awt.event.ActionEvent evt) {
        // Validacion
        int equipmentId = getSelectedEquipmentId();
        if (equipmentId == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select equipment",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (jDateChooserStart.getDate() == null || jDateChooserEnd.getDate() == null) {
            JOptionPane.showMessageDialog(this,
                "Please select start and end dates",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String purpose = txtPurpose.getText().trim();
        if (purpose.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter purpose of reservation",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Conversion de fechas
        Date startDate = new Date(jDateChooserStart.getDate().getTime());
        Date endDate = new Date(jDateChooserEnd.getDate().getTime());

        // Chequeo de disponibilidad en rango de fechas
        boolean available = metRequests.isEquipmentAvailable(equipmentId, startDate, endDate);
        
        if (!available) {
            int choice = JOptionPane.showConfirmDialog(this,
                "Equipment is already reserved for some or all of the selected dates.\n" +
                "Do you want to check availability for different dates?",
                "Equipment Unavailable",
                JOptionPane.YES_NO_OPTION, // Tipo de panel con SI o NO
                JOptionPane.WARNING_MESSAGE);
            
            if (choice == JOptionPane.YES_OPTION) {
                // Borrado de fechas automatico (para elegir nuevas)
                jDateChooserStart.setDate(null);
                jDateChooserEnd.setDate(null);
            }
            return;
        }

        // Creacion de la reservacion una vez validada
        boolean success = metRequests.createRequest(
            currentUserId, equipmentId, startDate, endDate, purpose, isAdmin);

        // Si es Admin, se acepta automaticamente
        if (success) {
            String message = isAdmin ? 
                "Reservation created and approved successfully!" :
                "Reservation request submitted for approval!";
            
            JOptionPane.showMessageDialog(this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

            // Limpieza para nueva reserva
            clearReservationForm();
            
            // Refresh
            loadReservationStats();
            loadCurrentReservations();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error creating reservation. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpieza para nueva reserva
    private void clearReservationForm() {
        filterResEq.setSelectedIndex(0);
        jDateChooserStart.setDate(null);
        jDateChooserEnd.setDate(null);
        txtPurpose.setText("");
    }

    // Filtrado de reservaciones por estado
    private void filterCurrentResActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedFilter = (String) filterCurrentRes.getSelectedItem();
        
        // Espacio para agregar logica de filtrado luego (modificar ClsMetRequests para que acepte filtro de status)
        loadCurrentReservations();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane2 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        pnlNavigation = new javax.swing.JPanel();
        btnNav4 = new javax.swing.JButton();
        btnNavDash = new javax.swing.JButton();
        btnNavInventory = new javax.swing.JButton();
        btnLogout = new javax.swing.JToggleButton();
        btnCerrar = new javax.swing.JButton();
        btnNavReservations = new javax.swing.JButton();
        tabQuickReserve = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        pnlReserve = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblResDate = new javax.swing.JLabel();
        filterResEq = new javax.swing.JComboBox<>();
        lblResPurpose = new javax.swing.JLabel();
        lblResEq = new javax.swing.JLabel();
        lblResTime = new javax.swing.JLabel();
        txtPurpose = new javax.swing.JTextField();
        btnNewRes = new javax.swing.JButton();
        jDateChooserStart = new com.toedter.calendar.JDateChooser();
        jDateChooserEnd = new com.toedter.calendar.JDateChooser();
        txtDateEnd = new javax.swing.JLabel();
        txtDateStart = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pnlReservations = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        filterCurrentRes = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnNav = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnNewReservation = new javax.swing.JButton();
        pnlStock = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtAvailable = new java.awt.TextField();
        pnlCritical = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtUsed = new java.awt.TextField();
        pnlTotalItems1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTotalEquipment = new java.awt.TextField();
        pnlCategories = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtReservations = new java.awt.TextField();
        pnlStatus = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSearchStatus = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 540));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane2.setPreferredSize(new java.awt.Dimension(1000, 700));
        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel1.setMinimumSize(new java.awt.Dimension(780, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlNavigation.setBackground(new java.awt.Color(51, 255, 255));
        pnlNavigation.setPreferredSize(new java.awt.Dimension(250, 620));
        pnlNavigation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNav4.setText("4");
        btnNav4.setToolTipText("");
        pnlNavigation.add(btnNav4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 210, 30));

        btnNavDash.setText("Dashboard");
        btnNavDash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavDashActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnNavDash, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 210, 30));

        btnNavInventory.setText("Inventory");
        btnNavInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavInventoryActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnNavInventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 210, 30));

        btnLogout.setText("Log out");
        pnlNavigation.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 100, 40));

        btnCerrar.setBackground(new java.awt.Color(255, 51, 51));
        btnCerrar.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setText("X");
        btnCerrar.setMaximumSize(new java.awt.Dimension(30, 30));
        btnCerrar.setMinimumSize(new java.awt.Dimension(30, 30));
        btnCerrar.setPreferredSize(new java.awt.Dimension(50, 50));
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 560, 40, 40));

        btnNavReservations.setText("Reservations");
        btnNavReservations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavReservationsActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnNavReservations, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 210, 30));

        jPanel1.add(pnlNavigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, 620));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlReserve.setMaximumSize(new java.awt.Dimension(470, 205));
        pnlReserve.setPreferredSize(new java.awt.Dimension(470, 200));
        pnlReserve.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Quick reserve");
        pnlReserve.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        lblResDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResDate.setText("Date");
        pnlReserve.add(lblResDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, -1, -1));

        filterResEq.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select equipment", "Item 2", "Item 3", "Item 4" }));
        pnlReserve.add(filterResEq, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        lblResPurpose.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResPurpose.setText("Purpose");
        pnlReserve.add(lblResPurpose, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, -1, -1));

        lblResEq.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResEq.setText("Equipment");
        pnlReserve.add(lblResEq, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        lblResTime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResTime.setText("Time slot");
        pnlReserve.add(lblResTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));
        pnlReserve.add(txtPurpose, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 180, 50));

        btnNewRes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewRes.setText("Reserve equipment");
        pnlReserve.add(btnNewRes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 590, -1));
        pnlReserve.add(jDateChooserStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, -1, -1));
        pnlReserve.add(jDateChooserEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, -1, -1));

        txtDateEnd.setText("End:");
        pnlReserve.add(txtDateEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, -1, -1));

        txtDateStart.setText("Start:");
        pnlReserve.add(txtDateStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, -1, -1));

        jPanel3.add(pnlReserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 465));

        tabQuickReserve.addTab("Quick reserve", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlReservations.setMaximumSize(new java.awt.Dimension(470, 205));
        pnlReservations.setPreferredSize(new java.awt.Dimension(470, 200));
        pnlReservations.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Current reservations");
        pnlReservations.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        pnlReservations.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 650, 410));

        filterCurrentRes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All status", "Active", "Confirmed", "Pending" }));
        pnlReservations.add(filterCurrentRes, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 100, -1));

        jPanel4.add(pnlReservations, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 445));

        tabQuickReserve.addTab("Current reservations", jPanel4);

        jPanel1.add(tabQuickReserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 670, 500));

        jPanel2.setBackground(new java.awt.Color(51, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 50));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNav.setText("nav");
        btnNav.setPreferredSize(new java.awt.Dimension(25, 25));
        btnNav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavActionPerformed(evt);
            }
        });
        jPanel2.add(btnNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Equipment Reservations ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Schedule and manage equipment usage  ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        btnNewReservation.setBackground(new java.awt.Color(51, 255, 153));
        btnNewReservation.setForeground(new java.awt.Color(255, 255, 255));
        btnNewReservation.setText("+ New Reservation");
        btnNewReservation.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel2.add(btnNewReservation, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 140, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 50));

        pnlStock.setBackground(new java.awt.Color(51, 255, 255));
        pnlStock.setForeground(new java.awt.Color(255, 255, 255));
        pnlStock.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlStock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Available now");
        pnlStock.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtAvailable.setBackground(new java.awt.Color(60, 63, 65));
        txtAvailable.setEditable(false);
        pnlStock.add(txtAvailable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 180, -1));

        pnlCritical.setBackground(new java.awt.Color(51, 255, 255));
        pnlCritical.setForeground(new java.awt.Color(255, 255, 255));
        pnlCritical.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlCritical.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("In use");
        pnlCritical.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtUsed.setBackground(new java.awt.Color(60, 63, 65));
        txtUsed.setEditable(false);
        pnlCritical.add(txtUsed, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlCritical, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 180, -1));

        pnlTotalItems1.setBackground(new java.awt.Color(51, 255, 255));
        pnlTotalItems1.setForeground(new java.awt.Color(255, 255, 255));
        pnlTotalItems1.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlTotalItems1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Total equipment");
        pnlTotalItems1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtTotalEquipment.setBackground(new java.awt.Color(60, 63, 65));
        txtTotalEquipment.setEditable(false);
        pnlTotalItems1.add(txtTotalEquipment, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlTotalItems1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, -1));

        pnlCategories.setBackground(new java.awt.Color(102, 204, 255));
        pnlCategories.setForeground(new java.awt.Color(255, 255, 255));
        pnlCategories.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlCategories.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Today's bookings");
        pnlCategories.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtReservations.setBackground(new java.awt.Color(60, 63, 65));
        txtReservations.setEditable(false);
        pnlCategories.add(txtReservations, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 60, 180, -1));

        pnlStatus.setForeground(new java.awt.Color(255, 255, 255));
        pnlStatus.setPreferredSize(new java.awt.Dimension(270, 520));
        pnlStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Equipment status");
        pnlStatus.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        txtSearchStatus.setBackground(new java.awt.Color(255, 255, 255));
        txtSearchStatus.setForeground(new java.awt.Color(153, 153, 153));
        txtSearchStatus.setText("Search equipment...");
        pnlStatus.add(txtSearchStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, -1));

        jPanel1.add(pnlStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background2.jpg"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 660));

        jLayeredPane2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jLayeredPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavActionPerformed
        pnlNavigation.setVisible(!pnlNavigation.isVisible());
    }//GEN-LAST:event_btnNavActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnNavDashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavDashActionPerformed
        FRMAdminDashboard Dashboard = new FRMAdminDashboard();
        Dashboard.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNavDashActionPerformed

    private void btnNavInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavInventoryActionPerformed
        FRMInventory Inventory = new FRMInventory();
        Inventory.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNavInventoryActionPerformed

    private void btnNavReservationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavReservationsActionPerformed
        FRMReservations Reservations = new FRMReservations();
        Reservations.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNavReservationsActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FRMReservations().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JToggleButton btnLogout;
    private javax.swing.JButton btnNav;
    private javax.swing.JButton btnNav4;
    private javax.swing.JButton btnNavDash;
    private javax.swing.JButton btnNavInventory;
    private javax.swing.JButton btnNavReservations;
    private javax.swing.JButton btnNewRes;
    private javax.swing.JButton btnNewReservation;
    private javax.swing.JComboBox<String> filterCurrentRes;
    private javax.swing.JComboBox<String> filterResEq;
    private com.toedter.calendar.JDateChooser jDateChooserEnd;
    private com.toedter.calendar.JDateChooser jDateChooserStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblResDate;
    private javax.swing.JLabel lblResEq;
    private javax.swing.JLabel lblResPurpose;
    private javax.swing.JLabel lblResTime;
    private javax.swing.JPanel pnlCategories;
    private javax.swing.JPanel pnlCritical;
    private javax.swing.JPanel pnlNavigation;
    private javax.swing.JPanel pnlReservations;
    private javax.swing.JPanel pnlReserve;
    private javax.swing.JPanel pnlStatus;
    private javax.swing.JPanel pnlStock;
    private javax.swing.JPanel pnlTotalItems1;
    private javax.swing.JTabbedPane tabQuickReserve;
    private java.awt.TextField txtAvailable;
    private javax.swing.JLabel txtDateEnd;
    private javax.swing.JLabel txtDateStart;
    private javax.swing.JTextField txtPurpose;
    private java.awt.TextField txtReservations;
    private javax.swing.JTextField txtSearchStatus;
    private java.awt.TextField txtTotalEquipment;
    private java.awt.TextField txtUsed;
    // End of variables declaration//GEN-END:variables
}
