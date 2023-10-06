/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

/**
 *
 * @author USUARIO
 */
public class vista_administrador extends javax.swing.JFrame {

    /**
     * Creates new form vista_llamada
     */
    public vista_administrador() {
        initComponents();
        this.setLocation(1100, 50);
        //CSS
        this.setTitle("Centro de llamadas COVID-19");
        this.setResizable(false);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        botonNumero = new javax.swing.JButton();
        botonSimbolo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        anuncio1 = new javax.swing.JLabel();
        botonDominio = new javax.swing.JButton();
        botonLlamar = new javax.swing.JButton();
        botonEnfermedad = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));

        jPanel1.setBackground(new java.awt.Color(3, 111, 158));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 190));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 190));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Menú para administrador");
        jLabel1.setFocusable(false);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 10, 400, -1));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Este menú es de uso exclusivo para los administradores");
        jLabel2.setFocusable(false);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 400, -1));

        botonNumero.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        botonNumero.setForeground(new java.awt.Color(3, 111, 158));
        botonNumero.setText("NUMEROS");
        botonNumero.setAlignmentX(1.0F);
        botonNumero.setAlignmentY(1.0F);
        botonNumero.setBorder(null);
        botonNumero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonNumeroMouseClicked(evt);
            }
        });
        botonNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNumeroActionPerformed(evt);
            }
        });
        jPanel1.add(botonNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 80, 30));
        botonNumero.getAccessibleContext().setAccessibleName("");

        botonSimbolo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        botonSimbolo.setForeground(new java.awt.Color(3, 111, 158));
        botonSimbolo.setText("SIMBOLOS");
        botonSimbolo.setAlignmentX(1.0F);
        botonSimbolo.setAlignmentY(1.0F);
        botonSimbolo.setBorder(null);
        botonSimbolo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonSimboloMouseClicked(evt);
            }
        });
        botonSimbolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSimboloActionPerformed(evt);
            }
        });
        jPanel1.add(botonSimbolo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 80, 30));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 400, 10));

        anuncio1.setFont(new java.awt.Font("Serif", 2, 14)); // NOI18N
        anuncio1.setForeground(new java.awt.Color(255, 255, 255));
        anuncio1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        anuncio1.setFocusable(false);
        jPanel1.add(anuncio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 171, 15));

        botonDominio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        botonDominio.setForeground(new java.awt.Color(3, 111, 158));
        botonDominio.setText("DOMINIOS");
        botonDominio.setAlignmentX(1.0F);
        botonDominio.setAlignmentY(1.0F);
        botonDominio.setBorder(null);
        botonDominio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonDominioMouseClicked(evt);
            }
        });
        botonDominio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDominioActionPerformed(evt);
            }
        });
        jPanel1.add(botonDominio, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 80, 30));

        botonLlamar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        botonLlamar.setForeground(new java.awt.Color(3, 111, 158));
        botonLlamar.setText("INFORMES");
        botonLlamar.setAlignmentX(1.0F);
        botonLlamar.setAlignmentY(1.0F);
        botonLlamar.setBorder(null);
        botonLlamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonLlamarMouseClicked(evt);
            }
        });
        botonLlamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLlamarActionPerformed(evt);
            }
        });
        jPanel1.add(botonLlamar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 80, 30));

        botonEnfermedad.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        botonEnfermedad.setForeground(new java.awt.Color(3, 111, 158));
        botonEnfermedad.setText("DIAGNOSTICO");
        botonEnfermedad.setAlignmentX(1.0F);
        botonEnfermedad.setAlignmentY(1.0F);
        botonEnfermedad.setBorder(null);
        botonEnfermedad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonEnfermedadMouseClicked(evt);
            }
        });
        botonEnfermedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEnfermedadActionPerformed(evt);
            }
        });
        jPanel1.add(botonEnfermedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 80, 30));

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
    }// </editor-fold>//GEN-END:initComponents

    private void botonNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNumeroActionPerformed

    }//GEN-LAST:event_botonNumeroActionPerformed

    private void botonNumeroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonNumeroMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonNumeroMouseClicked

    private void botonSimboloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSimboloMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonSimboloMouseClicked

    private void botonSimboloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSimboloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonSimboloActionPerformed

    private void botonDominioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonDominioMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonDominioMouseClicked

    private void botonDominioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDominioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonDominioActionPerformed

    private void botonLlamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonLlamarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonLlamarMouseClicked

    private void botonLlamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLlamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonLlamarActionPerformed

    private void botonEnfermedadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonEnfermedadMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonEnfermedadMouseClicked

    private void botonEnfermedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEnfermedadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonEnfermedadActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vista_administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vista_administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vista_administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vista_administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vista_administrador().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel anuncio1;
    public static javax.swing.JButton botonDominio;
    public static javax.swing.JButton botonEnfermedad;
    public static javax.swing.JButton botonLlamar;
    public static javax.swing.JButton botonNumero;
    public static javax.swing.JButton botonSimbolo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

}
