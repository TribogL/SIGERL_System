package FrontEnd;



import BackEnd.ClsMetEquipment;
import Connection.ClsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.awt.Toolkit;
import java.awt.Image;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;

public class FRMInventory extends javax.swing.JFrame {



    ClsConnection CN;
    PreparedStatement PS;
    ResultSet RS;

    // Glass pane to block interaction when navigation is open
    private javax.swing.JPanel glassPane;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMInventory.class.getName());

    public FRMInventory() {
        initComponents();
        setLocationRelativeTo(this);

        // Window size fix
        setSize(1000, 666);
        setPreferredSize(new java.awt.Dimension(1000, 666));

        CN = new ClsConnection();
        FillTable();

        pnlNavigation.setVisible(false);

        fixZIndexOrder();      // Z-Index to avoid panels overlapping
        applyPanelStyling();   // Transparent / styled panels
        loadInventoryStats();  // Load inventory stats at start
    }

    // -----------------------------
    // Z-INDEX & GLASS PANE SETUP
    // -----------------------------
    private void fixZIndexOrder() {
        // Navigation panel fully opaque when visible
        pnlNavigation.setOpaque(true);

        // Z-order: 0 = front
        jPanel1.setComponentZOrder(pnlNavigation, 0);
        jPanel1.setComponentZOrder(jPanel2, 1);
        jPanel1.setComponentZOrder(pnlSearch, 2);
        jPanel1.setComponentZOrder(jScrollPane1, 3);

        jPanel1.setComponentZOrder(pnlTotalItems1, 2);
        jPanel1.setComponentZOrder(pnlStock, 2);
        jPanel1.setComponentZOrder(pnlCritical, 2);
        jPanel1.setComponentZOrder(pnlCategories, 2);

        // Navigation panel mouse listener (avoid passthrough)
        pnlNavigation.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Just consume, do nothing
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Just consume, do nothing
            }
        });

        // Setup glass pane to dim and block behind
        setupNavigationGlassPane();
    }

    private void setupNavigationGlassPane() {
        glassPane = new javax.swing.JPanel();
        glassPane.setOpaque(false);
        glassPane.setBackground(new java.awt.Color(0, 0, 0, 100));
        glassPane.setVisible(false);

        glassPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Clicking on glassPane closes nav
                btnNavActionPerformed(null);
            }
        });

        glassPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                e.consume();
            }
        });

        // Add glassPane to the main panel, taking right side of the screen
        jPanel1.add(glassPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 750, 666));
        jPanel1.setComponentZOrder(glassPane, 1);
    }

    // -----------------------------
    // STYLING
    // -----------------------------
    private void applyPanelStyling() {
        // Navigation panel almost opaque
        pnlNavigation.setBackground(new java.awt.Color(51, 255, 255, 240));
        pnlNavigation.setOpaque(true);
        pnlNavigation.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(0, 0, 0, 255), 3
                )
        );

        // Stats panels styling
        java.awt.Color panelBg = new java.awt.Color(127, 222, 255, 180);
        javax.swing.border.Border panelBorder = javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(255, 255, 255, 100), 1
        );

        pnlTotalItems1.setBackground(panelBg);
        pnlTotalItems1.setOpaque(true);
        pnlTotalItems1.setBorder(panelBorder);

        pnlStock.setBackground(panelBg);
        pnlStock.setOpaque(true);
        pnlStock.setBorder(panelBorder);

        pnlCritical.setBackground(panelBg);
        pnlCritical.setOpaque(true);
        pnlCritical.setBorder(panelBorder);

        pnlCategories.setBackground(panelBg);
        pnlCategories.setOpaque(true);
        pnlCategories.setBorder(panelBorder);
    }

    // -----------------------------
    // AUTO-GENERATED INIT
    // -----------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnNav = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        pnlNavigation = new javax.swing.JPanel();
        btnNavDash = new javax.swing.JButton();
        btnNavInventory = new javax.swing.JButton();
        btnNavReservations = new javax.swing.JButton();
        btnNav4 = new javax.swing.JButton();
        btnLogout = new javax.swing.JToggleButton();
        btnCerrar = new javax.swing.JButton();
        pnlStock = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtLowItems = new java.awt.TextField();
        pnlTotalItems1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTotalItems1 = new java.awt.TextField();
        pnlCritical = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCritical = new java.awt.TextField();
        pnlCategories = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCategories = new java.awt.TextField();
        pnlSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        filterCategories = new javax.swing.JComboBox<>();
        filterStatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 900));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 666));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 666));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jLabel1.setText("Inventory management");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Track and manage laboratory supplies  ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        btnAdd.setText("Add item");
        btnAdd.setPreferredSize(new java.awt.Dimension(100, 25));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 60));

        pnlNavigation.setBackground(new java.awt.Color(51, 255, 255));
        pnlNavigation.setPreferredSize(new java.awt.Dimension(250, 666));
        pnlNavigation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNavDash.setText("Dashboard");
        btnNavDash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavDashActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnNavDash, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 210, 30));

        btnNavInventory.setText("Inventory");
        btnNavInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavInventoryActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnNavInventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 210, 30));

        btnNavReservations.setText("Reservations");
        btnNavReservations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavReservationsActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnNavReservations, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 210, 30));

        btnNav4.setText("4");
        btnNav4.setToolTipText("");
        pnlNavigation.add(btnNav4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 210, 30));

        btnLogout.setText("Log out");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 543, 100, 40));

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
        pnlNavigation.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 40, 40));

        jPanel1.add(pnlNavigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 56, -1, 610));

        pnlStock.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlStock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("In low stock");
        pnlStock.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtLowItems.setBackground(new java.awt.Color(60, 63, 65));
        txtLowItems.setEditable(false);
        txtLowItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLowItemsActionPerformed(evt);
            }
        });
        pnlStock.add(txtLowItems, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 180, -1));

        pnlTotalItems1.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlTotalItems1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Total items");
        pnlTotalItems1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtTotalItems1.setBackground(new java.awt.Color(60, 63, 65));
        txtTotalItems1.setEditable(false);
        txtTotalItems1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalItems1ActionPerformed(evt);
            }
        });
        pnlTotalItems1.add(txtTotalItems1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlTotalItems1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 180, -1));

        pnlCritical.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlCritical.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Critical stock");
        pnlCritical.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtCritical.setBackground(new java.awt.Color(60, 63, 65));
        txtCritical.setEditable(false);
        txtCritical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriticalActionPerformed(evt);
            }
        });
        pnlCritical.add(txtCritical, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlCritical, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 180, -1));

        pnlCategories.setPreferredSize(new java.awt.Dimension(185, 75));
        pnlCategories.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Categories");
        pnlCategories.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtCategories.setBackground(new java.awt.Color(60, 63, 65));
        txtCategories.setEditable(false);
        txtCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriesActionPerformed(evt);
            }
        });
        pnlCategories.add(txtCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, -1));

        jPanel1.add(pnlCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, 180, -1));

        pnlSearch.setPreferredSize(new java.awt.Dimension(750, 50));
        pnlSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearch.setBackground(new java.awt.Color(255, 255, 255));
        txtSearch.setForeground(new java.awt.Color(153, 153, 153));
        txtSearch.setText("Search by name , ID or supplier");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        pnlSearch.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 14, 480, -1));

        filterCategories.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"All categories", "Item 2", "Item 3", "Item 4"}));
        pnlSearch.add(filterCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, 30));

        filterStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"All status", "In stock", "Low stock", "Critical"}));
        filterStatus.setPreferredSize(new java.awt.Dimension(105, 22));
        pnlSearch.add(filterStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, 30));

        jPanel1.add(pnlSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 750, -1));

        tblItems.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(tblItems);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 980, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    // -----------------------------
    // NAV BUTTON / GLASSPANE TOGGLE
    // -----------------------------
    private void btnNavActionPerformed(java.awt.event.ActionEvent evt) {
        boolean isVisible = !pnlNavigation.isVisible();
        pnlNavigation.setVisible(isVisible);
        glassPane.setVisible(isVisible);

        if (isVisible) {
            jPanel1.setComponentZOrder(pnlNavigation, 0);
            jPanel1.setComponentZOrder(glassPane, 1);

            glassPane.setOpaque(true);
            glassPane.setBackground(new java.awt.Color(0, 0, 0, 80));

            pnlNavigation.repaint();
            glassPane.repaint();
            jPanel1.revalidate();
        } else {
            glassPane.setOpaque(false);
            jPanel1.repaint();
        }
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        FRMAddEquipment frm = new FRMAddEquipment(this);
        frm.setLocationRelativeTo(this);
        frm.setVisible(true);
    }

    private void btnNavDashActionPerformed(java.awt.event.ActionEvent evt) {
        FRMAdminDashboard frm = new FRMAdminDashboard();
        frm.setLocationRelativeTo(this);
        frm.setVisible(true);
        this.dispose();
    }

    private void btnNavInventoryActionPerformed(java.awt.event.ActionEvent evt) {
        // Already here
    }

    private void btnNavReservationsActionPerformed(java.awt.event.ActionEvent evt) {
        FRMReservations frm = new FRMReservations();
        frm.setLocationRelativeTo(this);
        frm.setVisible(true);
        this.dispose();
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        FRMLogin frm = new FRMLogin();
        frm.setLocationRelativeTo(this);
        frm.setVisible(true);
        this.dispose();
    }

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void txtLowItemsActionPerformed(java.awt.event.ActionEvent evt) {
        // No action needed
    }

    private void txtTotalItems1ActionPerformed(java.awt.event.ActionEvent evt) {
        // No action needed
    }

    private void txtCriticalActionPerformed(java.awt.event.ActionEvent evt) {
        // No action needed
    }

    private void txtCategoriesActionPerformed(java.awt.event.ActionEvent evt) {
        // No action needed
    }

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchText = txtSearch.getText().trim();

        if (searchText.isEmpty() || searchText.equals("Search by name , ID or supplier")) {
            FillTable();
            return;
        }

        filterTable(searchText);
    }

    // -----------------------------
    // INVENTORY STATS
    // -----------------------------
    private void loadInventoryStats() {
        ClsMetEquipment metEquip = new ClsMetEquipment();

        int total = metEquip.getTotalEquipmentCount();
        txtTotalItems1.setText(String.valueOf(total));

        int lowStock = metEquip.getLowStockCount();
        txtLowItems.setText(String.valueOf(lowStock));

        int critical = metEquip.getCriticalStockCount();
        txtCritical.setText(String.valueOf(critical));

        int categories = metEquip.getCategoriesCount();
        txtCategories.setText(String.valueOf(categories));

        if (lowStock > 0) {
            txtLowItems.setForeground(new java.awt.Color(255, 153, 0)); // Orange
        }

        if (critical > 0) {
            txtCritical.setForeground(new java.awt.Color(255, 51, 51)); // Red
        }
    }

    public void FillTable() {
        ClsMetEquipment Equipment = new ClsMetEquipment();
        tblItems.setModel(Equipment.ListEquipment());
        loadInventoryStats();
    }

    public void refreshInventory() {
        FillTable();
        loadInventoryStats();
    }

    // -----------------------------
    // FILTER TABLE
    // -----------------------------
    private void filterTable(String searchText) {
        ClsMetEquipment metEquip = new ClsMetEquipment();
        DefaultTableModel fullModel = metEquip.ListEquipment();
        DefaultTableModel filteredModel = new DefaultTableModel();

        // Copy column names
        for (int i = 0; i < fullModel.getColumnCount(); i++) {
            filteredModel.addColumn(fullModel.getColumnName(i));
        }

        String lowerSearch = searchText.toLowerCase();
        for (int i = 0; i < fullModel.getRowCount(); i++) {
            String id = fullModel.getValueAt(i, 0).toString().toLowerCase();
            String name = fullModel.getValueAt(i, 1).toString().toLowerCase();
            String supplier = fullModel.getValueAt(i, 3).toString().toLowerCase();

            if (id.contains(lowerSearch) || name.contains(lowerSearch) || supplier.contains(lowerSearch)) {
                Object[] row = new Object[fullModel.getColumnCount()];
                for (int j = 0; j < fullModel.getColumnCount(); j++) {
                    row[j] = fullModel.getValueAt(i, j);
                }
                filteredModel.addRow(row);
            }
        }

        tblItems.setModel(filteredModel);
    }

    // -----------------------------
    // MAIN
    // -----------------------------
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FRMInventory().setVisible(true));
    }


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JToggleButton btnLogout;
    private javax.swing.JButton btnNav;
    private javax.swing.JButton btnNav4;
    private javax.swing.JButton btnNavDash;
    private javax.swing.JButton btnNavInventory;
    private javax.swing.JButton btnNavReservations;
    private javax.swing.JComboBox<String> filterCategories;
    private javax.swing.JComboBox<String> filterStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlCategories;
    private javax.swing.JPanel pnlCritical;
    private javax.swing.JPanel pnlNavigation;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlStock;
    private javax.swing.JPanel pnlTotalItems1;
    private javax.swing.JTable tblItems;
    private java.awt.TextField txtCategories;
    private java.awt.TextField txtCritical;
    private java.awt.TextField txtLowItems;
    private javax.swing.JTextField txtSearch;
    private java.awt.TextField txtTotalItems1;
    // End of variables declaration                   
}
