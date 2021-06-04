package qlcf;

import javax.swing.JOptionPane;

public class MainFr extends javax.swing.JFrame {

    private Detail detail;
    
    
    public MainFr(Detail d ) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(this);
        detail=new Detail(d);
    }

  
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnData = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnQLNV = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnBanhang = new javax.swing.JButton();
        btnQLNuoc = new javax.swing.JButton();
        btnThongke = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnData1 = new javax.swing.JButton();

        btnData.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnData.setForeground(new java.awt.Color(0, 0, 255));
        btnData.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\danhmuc.jpg")); // NOI18N
        btnData.setText("Danh Mục Sản Phẩm");
        btnData.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnData.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnQLNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLNV.setForeground(new java.awt.Color(0, 0, 204));
        btnQLNV.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\CUSTOMER.png")); // NOI18N
        btnQLNV.setText("Quản Lí Nhân Viên");
        btnQLNV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQLNV.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnQLNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnQLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNVActionPerformed(evt);
            }
        });

        btnLogout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(0, 0, 204));
        btnLogout.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\LogOut.png")); // NOI18N
        btnLogout.setText("Đăng Xuất");
        btnLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogout.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnLogout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(0, 0, 204));
        btnExit.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\Exit.png")); // NOI18N
        btnExit.setText("Thoát");
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnBanhang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBanhang.setForeground(new java.awt.Color(0, 0, 204));
        btnBanhang.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\banhang.png")); // NOI18N
        btnBanhang.setText("Bán Hàng");
        btnBanhang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBanhang.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnBanhang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBanhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanhangActionPerformed(evt);
            }
        });

        btnQLNuoc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLNuoc.setForeground(new java.awt.Color(0, 0, 204));
        btnQLNuoc.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\QLCF\\src\\Photos\\menu.png")); // NOI18N
        btnQLNuoc.setText("Quản Lí Thức Uống");
        btnQLNuoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQLNuoc.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnQLNuoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnQLNuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNuocActionPerformed(evt);
            }
        });

        btnThongke.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThongke.setForeground(new java.awt.Color(0, 0, 204));
        btnThongke.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\thongke1.png")); // NOI18N
        btnThongke.setText("Thống Kê");
        btnThongke.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThongke.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnThongke.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThongke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongkeActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(245, 244, 244));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Home");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnData1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnData1.setForeground(new java.awt.Color(0, 0, 255));
        btnData1.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\danhmuc.jpg")); // NOI18N
        btnData1.setText("Danh Mục Sản Phẩm");
        btnData1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnData1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnData1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnData1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnData1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addComponent(btnData1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBanhang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQLNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQLNuoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongke)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThongke, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnQLNuoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnQLNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBanhang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnData1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBanhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanhangActionPerformed
        banhangFr banhang = new banhangFr(detail);
        this.setVisible(false);
        banhang.setVisible(true);
    }//GEN-LAST:event_btnBanhangActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất khỏi tài khoản hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            LoginFr dangnhap = new LoginFr();
            this.setVisible(false);
            dangnhap.setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnThongkeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongkeActionPerformed
        ThongkeFr thongke = new ThongkeFr(detail);
        this.setVisible(false);
        thongke.setVisible(true);
    }//GEN-LAST:event_btnThongkeActionPerformed

    private void btnQLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNVActionPerformed
        qlnvFr qlnv = new qlnvFr(detail);
        this.setVisible(false);
        qlnv.setVisible(true);
    }//GEN-LAST:event_btnQLNVActionPerformed

    private void btnQLNuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNuocActionPerformed
        qlnuocFr qln = new qlnuocFr(detail);
        this.setVisible(false);
        qln.setVisible(true);
    }//GEN-LAST:event_btnQLNuocActionPerformed

    private void btnData1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnData1ActionPerformed
       DanhMucSP data = new DanhMucSP(detail);
        this.setVisible(false);
        data.setVisible(true);
    }//GEN-LAST:event_btnData1ActionPerformed

    public static void main(String args[]) {
      /*  try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail= new Detail();
                new MainFr(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBanhang;
    private javax.swing.JButton btnData;
    private javax.swing.JButton btnData1;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnQLNV;
    private javax.swing.JButton btnQLNuoc;
    private javax.swing.JButton btnThongke;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
