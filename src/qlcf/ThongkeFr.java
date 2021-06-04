
package qlcf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class ThongkeFr extends javax.swing.JFrame {

    private Detail detail;

    private PreparedStatement pst = null;  
    private Connection conn=null;
    private ResultSet rs=null;
    
    private String sql="SELECT * FROM ThongKe";
    
    private boolean add=false, change=false;
    private String date="1/1/2021";
    
    public ThongkeFr(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(this);
        detail=new Detail(d);
        connection();    
        load(sql);
        loadDate();
        setrad();
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
     private void loadDate(){
        try {
            jDateChooser1.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        jDateChooser2.setDate(new java.util.Date());
    }
    private void load(String sql){
        int count=0;
        long tongTien=0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try{
            String[] arry={"Mã hóa đơn","Tổng tiền","Tên nhân viên","Ngày Bán"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("id_Hoadon") );                
                vector.add(rs.getString("tongTien").trim());               
                vector.add(rs.getString("tenNV").trim());
                vector.add(rs.getString("ngay").trim());               
                model.addRow(vector);
                String []s=rs.getString("tongTien").trim().split("\\s");
                tongTien=convertedToNumbers(s[0])+tongTien;
                count++;
            }
            tableThongke.setModel(model);
            lblSoHoaDon.setText(String.valueOf(count));
            lblTongDoanhThu.setText(formatter.format(tongTien)+" "+"VND");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private long convertedToNumbers(String s){
        String number="";
        String []array=s.replace(","," ").split("\\s");
        for(String i:array){
            number=number.concat(i);
        }
        return Long.parseLong(number);
    }
    private void setrad(){
    if(tableThongke1.getRowCount() == 0){
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(false);
    }
    else{jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(true);}
    }
    private void load2(String sql){
        
        int count=0;
        long tongTien=0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try{
           String[] arr={"Mã hóa đơn","Bàn","Tổng tiền","Tiền nhận của khách","Tiền thừa","Tên nhân viên","Ngày Bán","Thời gian"};
            DefaultTableModel model=new DefaultTableModel(arr,0);
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                    vector.add(rs.getString("Id_Hoadon"));
                    vector.add(rs.getInt("ban"));
                    vector.add(rs.getString("tongTien").trim());
                    vector.add(rs.getString("tienKH").trim());
                    vector.add(rs.getString("tienThua").trim());
                    vector.add(rs.getString("tenNV").trim());
                    vector.add(rs.getString("ngay").trim());
                    vector.add(rs.getString("thoiGian").trim());
                    model.addRow(vector);
                    String []s=rs.getString("tongTien").trim().split("\\s");
                    tongTien=convertedToNumbers(s[0])+tongTien;
                    count++;
                
            }
            tableThongke1.setModel(model);
            lblSoHoaDon.setText(String.valueOf(count));
            lblTongDoanhThu.setText(formatter.format(tongTien)+" "+"VND");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
    
   
   
    
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        btnHome = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblSoHoaDon = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableThongke = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableThongke1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        lbTrangthai = new javax.swing.JLabel();
        btnRevenue = new javax.swing.JButton();
        btnfind3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thống kê");

        btnHome.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/trangchu.png"))); // NOI18N
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Tổng Số Hóa Đơn Bán Ra:");

        lblSoHoaDon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSoHoaDon.setText("0");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Tổng Tiền Thu Về:");

        lblTongDoanhThu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTongDoanhThu.setText("0 VND");

        tableThongke.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableThongke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableThongke.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableThongkeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableThongke);

        tableThongke1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableThongke1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tableThongke1);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Tất cả hóa đơn");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Thông tin chi tiết");

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setText("Giảm dần");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setText("Tăng dần");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Sắp xếp theo doanh thu:");

        lbTrangthai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTrangthai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangthai.setText("Trạng thái");

        btnRevenue.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnRevenue.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\Image\\thongke.png")); // NOI18N
        btnRevenue.setText("Thống Kê");
        btnRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevenueActionPerformed(evt);
            }
        });

        btnfind3.setText("Reset");
        btnfind3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfind3ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Thống Kê Từ Ngày:");

        jDateChooser1.setDateFormatString("dd/MM/yyyy");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Đến Ngày:");

        jDateChooser2.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(204, 204, 204)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 53, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnfind3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addComponent(lbTrangthai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRevenue)
                        .addGap(193, 193, 193))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(22, 22, 22)
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnfind3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lbTrangthai)
                        .addGap(18, 18, 18)
                        .addComponent(btnRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jLabel7)
                    .addComponent(lblTongDoanhThu)
                    .addComponent(jLabel4)
                    .addComponent(lblSoHoaDon)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        MainFr home = new MainFr(detail);
        this.setVisible(false);
        home.setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        try{                                              
            String sql = " SELECT * FROM Thongke   ORDER BY tien desc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            int count=0;
            long tongTien=0;
            try{
                String[] arry={"Mã hóa đơn","Bàn","Tổng tiền","Tiền nhận của khách","Tiền thừa","Tên nhân viên","Ngày Bán","Thời gian"};
                DefaultTableModel model=new DefaultTableModel(arry,0);
                
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                
                while(rs.next()){
                    Vector vector=new Vector();
                    vector.add(rs.getString("Id_Hoadon"));
                    vector.add(rs.getInt("ban"));
                    vector.add(rs.getString("tongTien").trim());
                    vector.add(rs.getString("tienKH").trim());
                    vector.add(rs.getString("tienThua").trim());
                    vector.add(rs.getString("tenNV").trim());
                    vector.add(rs.getString("ngay").trim());
                    vector.add(rs.getString("thoiGian").trim());
                    model.addRow(vector);
                    String []s=rs.getString("tongTien").trim().split("\\s");
                    tongTien=convertedToNumbers(s[0])+tongTien;
                    count++;
                }
                tableThongke1.setModel(model);
                
                lblSoHoaDon.setText(String.valueOf(count));
                lblTongDoanhThu.setText(formatter.format(tongTien)+" "+"VND");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            
        }
        catch(SQLException ex){
            Logger.getLogger(ThongkeFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        try{                                              
            String sql = " SELECT * FROM Thongke   ORDER BY tien asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            int count=0;
            long tongTien=0;
            try{
                String[] arry={"Mã hóa đơn","Bàn","Tổng tiền","Tiền nhận của khách","Tiền thừa","Tên nhân viên","Ngày Bán","Thời gian"};
                DefaultTableModel model=new DefaultTableModel(arry,0);
                
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                
                while(rs.next()){
                    Vector vector=new Vector();
                    vector.add(rs.getString("Id_Hoadon"));
                    vector.add(rs.getInt("ban"));
                    vector.add(rs.getString("tongTien").trim());
                    vector.add(rs.getString("tienKH").trim());
                    vector.add(rs.getString("tienThua").trim());
                    vector.add(rs.getString("tenNV").trim());
                    vector.add(rs.getString("ngay").trim());
                    vector.add(rs.getString("thoiGian").trim());
                    model.addRow(vector);
                    String []s=rs.getString("tongTien").trim().split("\\s");
                    tongTien=convertedToNumbers(s[0])+tongTien;
                    count++;
                }
                tableThongke1.setModel(model);
                lblSoHoaDon.setText(String.valueOf(count));
                lblTongDoanhThu.setText(formatter.format(tongTien)+" "+"VND");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ThongkeFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void tableThongkeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableThongkeMouseClicked
        try{
             int Click=tableThongke.getSelectedRow();
              TableModel model=tableThongke.getModel();
            String sql = " SELECT * FROM Thongke WHERE Id_Hoadon =N'"+model.getValueAt(Click, 0).toString()+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();

            try{
                String[] arry={"Mã hóa đơn","Bàn","Tổng tiền","Tiền nhận của khách","Tiền thừa","Tên nhân viên","Ngày Bán","Thời gian"};
                DefaultTableModel model1 =new DefaultTableModel(arry,0);
                
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                
                while(rs.next()){
                    Vector vector=new Vector();
                    vector.add(rs.getString("Id_Hoadon"));
                    vector.add(rs.getInt("ban"));
                    vector.add(rs.getString("tongTien").trim());
                    vector.add(rs.getString("tienKH").trim());
                    vector.add(rs.getString("tienThua").trim());
                    vector.add(rs.getString("tenNV").trim());
                    vector.add(rs.getString("ngay").trim());
                    vector.add(rs.getString("thoiGian").trim());
                    model1.addRow(vector);
                    String []s=rs.getString("tongTien").trim().split("\\s");
                }
                tableThongke1.setModel(model1);
             
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        catch(SQLException ex){
            Logger.getLogger(ThongkeFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tableThongkeMouseClicked

    private void btnfind3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfind3ActionPerformed
         ThongkeFr thongke = new ThongkeFr(detail);
        this.setVisible(false);
        thongke.setVisible(true);
    }//GEN-LAST:event_btnfind3ActionPerformed

    private void btnRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevenueActionPerformed
        String sqlRevenue="SELECT * FROM Thongke WHERE ngay BETWEEN '"+new java.sql.Date(jDateChooser1.getDate().getTime())+"' AND '"+new java.sql.Date(jDateChooser2.getDate().getTime())+"'";
        load2(sqlRevenue);
        setrad();
    }//GEN-LAST:event_btnRevenueActionPerformed


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
            java.util.logging.Logger.getLogger(ThongkeFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongkeFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongkeFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongkeFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail= new Detail();
                new ThongkeFr(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnRevenue;
    private javax.swing.JButton btnfind3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTrangthai;
    private javax.swing.JLabel lblSoHoaDon;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JTable tableThongke;
    private javax.swing.JTable tableThongke1;
    // End of variables declaration//GEN-END:variables
}
