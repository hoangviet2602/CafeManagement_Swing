/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlcf;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ASUS-PRO
 */
public class DanhMucSP extends javax.swing.JFrame {
    private Connection conn = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Detail detail;
    private boolean Add=false,Change=false;
    
  
    String sql = "SELECT * FROM Classify";
    
    public DanhMucSP(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail=new Detail(d);
        //lblStatus.setForeground(Color.red);
        connection();
      
        load_DMSP(sql);
     
        DisabledClassify();
      
    }
     private void connection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=QuanCaPhe;"
                    + "username=sa;password=123456");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
    private void load_DMSP(String sql){
        tableDMSP.removeAll();
        try{
            String [] arr={"Mã Loai Nước","Tên loại Nước"};
            DefaultTableModel modle=new DefaultTableModel(arr,0);
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("ID").trim());
                vector.add(rs.getString("Classify").trim());
                modle.addRow(vector);
            }
            tableDMSP.setModel(modle);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void backHome(){
        MainFr home=new MainFr(detail);
        this.setVisible(false);
        home.setVisible(true);
    }
    
 
    
    private void EnabledClassify(){
        txtIdDMSP.setEnabled(true);
        txtTenDM.setEnabled(true);
        //lblStatus.setText("Trạng Thái!");
    }
    
    
    
    private void DisabledClassify(){
        txtIdDMSP.setEnabled(false);
        txtTenDM.setEnabled(false);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private void Refresh(){
        Change=false;
        Add=false;

        txtIdDMSP.setText("");
        txtTenDM.setText("");
      
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSave.setEnabled(false);
        DisabledClassify();
    
    }
    
   
    
   
    
    private boolean CheckClassify(){
        boolean kq=true;
        String sqlCheck="SELECT * FROM Classify";
        try{
           PreparedStatement pstCheck=conn.prepareStatement(sqlCheck);
            ResultSet rs=pstCheck.executeQuery();
            while(rs.next()){
                if(this.txtIdDMSP.getText().equals(rs.getString("ID").toString().trim())){
                      JOptionPane.showMessageDialog(null,"Mã Loại nước đã tồn tại" );
                    return false;
                    
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    
   
    
   
    
    private boolean checkNullClassify(){
        boolean kq=true;
        if(String.valueOf(this.txtIdDMSP.getText()).length()==0){
            //lblStatus.setText("Bạn chưa nhập ID cho loại linh kiện!");
            return false;
        }
        if(String.valueOf(this.txtTenDM.getText()).length()==0){
            //lblStatus.setText("Bạn chưa nhập tên linh kiện!");
            return false;
        }   
        return kq;
    }
  
    
    
    
    
    private void addClassify(){
        if(checkNullClassify()){
            String sqlInsert="INSERT INTO Classify (ID,Classify) VALUES(?,?)";
            try{
                pst=conn.prepareStatement(sqlInsert);
                pst.setString(1, txtIdDMSP.getText());
                pst.setString(2, txtTenDM.getText());
                pst.executeUpdate();
                //lblStatus.setText("Thêm loại linh kiện thành công!");
                DisabledClassify();
                Refresh();
                load_DMSP(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
   
    
    private void changedClassify(){
        int Click=tableDMSP.getSelectedRow();
        TableModel model=tableDMSP.getModel();
        if(checkNullClassify()){
            String sqlChange="UPDATE Classify SET ID=?, Classify=? WHERE ID='"+model.getValueAt(Click,0).toString().trim()+"'";
            try{
                pst=conn.prepareStatement(sqlChange);
                pst.setString(1, this.txtIdDMSP.getText());
                pst.setString(2,this.txtTenDM.getText() );
                pst.executeUpdate();
                //lblStatus.setText("Lưu thay đổi thành công!");
                DisabledClassify();
                Refresh();
                load_DMSP(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
                                        

                                                                                                           

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableDMSP = new javax.swing.JTable();
        txtIdDMSP = new javax.swing.JTextField();
        txtTenDM = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbQLTU = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tableDMSP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableDMSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Danh mục", "Tên Danh mục"
            }
        ));
        tableDMSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDMSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableDMSP);

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdd.setText("ADD");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Mã Danh Mục");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Tên Danh Mục");

        lbQLTU.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbQLTU.setText("Quản Lí Danh Mục Nước");

        btnHome.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\QLCF\\src\\Photos\\trangchu.png")); // NOI18N
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnHuy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHuy.setText("CANCEL");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdDMSP, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel2)
                                .addGap(28, 28, 28)
                                .addComponent(txtTenDM))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(btnHuy))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHome)
                        .addGap(108, 108, 108)
                        .addComponent(lbQLTU, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome)
                    .addComponent(lbQLTU, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIdDMSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(txtTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnHuy))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableDMSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDMSPMouseClicked
        txtIdDMSP.setText("");
        txtTenDM.setText("");
        
        int Click=tableDMSP.getSelectedRow();
        TableModel model=tableDMSP.getModel();
        txtIdDMSP.setText(model.getValueAt(Click, 0).toString());
        txtTenDM.setText(model.getValueAt(Click, 1).toString());
        
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
       
    }//GEN-LAST:event_tableDMSPMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
         Refresh();
        Add=true;
        btnAdd.setEnabled(false);
        btnSave.setEnabled(true);
        load_DMSP(sql);
        EnabledClassify();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Add=false;
        Change=true;
        EnabledClassify();
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
        //btnCancel.setEnabled(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa danh mục hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM Classify WHERE ID=? AND Classify=? ";//
            try{
                pst=conn.prepareStatement(sqlDelete);
                pst.setString(1, txtIdDMSP.getText());
                pst.setString(2, txtTenDM.getText());
               
                pst.executeUpdate();
                
                DisabledClassify();
                Refresh();
                //lblStatus.setText("Xóa nhân viên thành công!");
                load_DMSP(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
         if(Add==true){
            if(CheckClassify()){
                addClassify();
            }
            else ;//lblStatus.setText("Không thể thêm sản phẩm vì mã sản phẩm bạn nhập đã tồn tại");
        }else if(Change==true){
            changedClassify();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        backHome();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                                         
        int lick=JOptionPane.showConfirmDialog(null,"Bạn Có Muốn Thoát Khỏi Chương Trình Hay Không?","Thông Báo",2);
        if(lick==JOptionPane.OK_OPTION){
            System.exit(0);
        }
        else{
            if(lick==JOptionPane.CANCEL_OPTION){    
                this.setVisible(true);
            }
        
    }         
    }//GEN-LAST:event_formWindowClosing

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        Refresh();
        load_DMSP(sql);
        
    }//GEN-LAST:event_btnHuyActionPerformed

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
            java.util.logging.Logger.getLogger(DanhMucSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanhMucSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanhMucSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanhMucSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail= new Detail();
                new DanhMucSP(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbQLTU;
    private javax.swing.JTable tableDMSP;
    private javax.swing.JTextField txtIdDMSP;
    private javax.swing.JTextField txtTenDM;
    // End of variables declaration//GEN-END:variables
}
