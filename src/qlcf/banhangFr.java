
package qlcf;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;


public class banhangFr extends javax.swing.JFrame implements Runnable,ActionListener{

    private Detail detail;
    private Thread thread;
    

    private PreparedStatement pst = null;  
    private Connection conn=null, connCheck=null;
    private ResultSet rs=null, rsCheck=null;
    public  String soban;
    private boolean Pay=false;
    
    JButton []ban=new JButton[5*5];
    
    public banhangFr(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(this);
        setData();
        taoBan();
        veBan();
        detail=new Detail(d);

        lbNhanVien.setText(d.getName());
        connection();
        lblTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
        lblDate.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
        
        Disabled();
        checkBill();
        loadLoaiNuoc();
        checkTinhTrangban();
        Start();
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
    
    
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }
    
    private void Disabled(){
       //cbxNuoc.setEnabled(false);
        tfSoLuong.setEnabled(false);      
        btnAdd.setEnabled(false);
        txbtenNuoc.setEnabled(false);
    }
    
    private void taoBan (){
        int count=1;
        JButton oldButton=new JButton();
        oldButton.setBounds(0,0,0,0);
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                
                JButton button = new JButton(""+count);
                button.setHorizontalTextPosition(JButton.CENTER);
                button.setVerticalTextPosition(JButton.BOTTOM);
                button.setBackground(Color.green);
                button.setBounds(oldButton.getX()+oldButton.getWidth(), oldButton.getY(), 70, 70);
                button.addActionListener(this);
                
                oldButton.setBounds(button.getX(),button.getY() , button.getWidth()+5, button.getHeight()+5);
                
                ban[count-1]=button;
                count++;
            }
            oldButton.setBounds(0, oldButton.getY()+oldButton.getHeight(), 0, 0);
        }
    }
    
    private void veBan(){
        for (JButton jButton : ban) {
            PanelBan.add(jButton);
        }
    }
    
    private void checkBill(){
        if(tableHoaDon.getRowCount()==0){
            tfPay.setEnabled(false);
            tfTienNhanCuaKach.setEnabled(false);
        }
        else {
            tfPay.setEnabled(true);
            tfPay.setBackground(Color.red);
            tfTienNhanCuaKach.setEnabled(true);
        }
    }
    

     private void loadLoaiNuoc(){
       cbLoaiNuoc.removeAllItems();
        String sql = "SELECT * FROM Classify";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                this.cbLoaiNuoc.addItem(rs.getString("Classify").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    } 
    private void Start(){
        if(thread==null){
            thread= new Thread(this);
            thread.start();
        }
    }
    
    
    
    private void loadData(String sql){
        try{
            String[] arry={"Tên thức uống","Số lượng","Thành tiền"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("tenNuoc").trim());
                vector.add(rs.getInt("soLuong"));
                vector.add(rs.getString("thanhTien").trim());
                model.addRow(vector);
            }
            tableHoaDon.setModel(model);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void loadNuoc(String sql){
        try{
            String[] arry={"Tên thức uống","Giá Bán","Số Lượng Còn"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("tenNuoc").trim());
                vector.add(rs.getString("giaBan").trim());
                vector.add(rs.getInt("SoLuong"));
                model.addRow(vector);
            }
            tblNuoc.setModel(model);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void deleteThongTinHoaDon(){
        try {
            String sqlDelete="DELETE FROM ThongTinHoaDon";
            pst=conn.prepareStatement(sqlDelete);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deleteHoaDon(){
        try {
            String sqlDelete="DELETE FROM HoaDon";
            pst=conn.prepareStatement(sqlDelete);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void checkTinhTrangban(){
        try {
            for (JButton jButton : ban) {
                String sql="SELECT * FROM BanHang WHERE ban="+jButton.getText();
                 pst=conn.prepareStatement(sql);
                 rs=pst.executeQuery();
                if(rs.next()){
                  
                    jButton.setBackground(Color.red);
                }
                else   { 
                          jButton.setBackground(Color.green);
                }
            }
        }
        catch (SQLException ex) {
           ex.printStackTrace();
        }
    }

    private void checkSoLuongHang(){
        int Click=tblNuoc.getSelectedRow();
        TableModel model=tblNuoc.getModel();
        String sqlCheck="SELECT * FROM QLNuoc WHERE tenNuoc=N'"+model.getValueAt(Click, 0).toString()+"'";
        try{
            
            pst=conn.prepareStatement(sqlCheck);
            rs=pst.executeQuery();
            while(rs.next()){
                if(rs.getInt("soLuong")==0){             
                    JOptionPane.showMessageDialog(null,rs.getString("tenNuoc")+" hết hàng!!" );
                    btnAdd.setEnabled(false);
                    tfSoLuong.setEnabled(false);  
                }
                else{  
                    tfSoLuong.setEnabled(true);                   
                    
                    
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private double convertedToNumbers(String s){
        String number="";
        String []array=s.replace(","," ").split("\\s");
        for(String i:array){
            number=number.concat(i);
        }
        return Double.parseDouble(number);
    }
    
    
    
    private void tinhTongTien(){
        lbTongTien.setText("0 VNĐ");
        
        String sqlPay="SELECT * FROM BanHang WHERE ban="+lbBan.getText();
        try{
            pst=conn.prepareStatement(sqlPay);
            rs=pst.executeQuery();
            while(rs.next()){
                String []s1=rs.getString("thanhTien").toString().trim().split("\\s");
                String []s2=lbTongTien.getText().split("\\s");
        
                double totalMoney=convertedToNumbers(s1[0])+ convertedToNumbers(s2[0]);
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                
                lbTongTien.setText(formatter.format(totalMoney)+" "+s1[1]);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private int dem(){
    String sql_count = "select *from ThongKe";
    int count = 0;
     try{
             pst=conn.prepareStatement(sql_count);
            rs=pst.executeQuery();
            while(rs.next()){
                count +=1;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
     return count;
    }
    private void luuThongKe() throws ParseException{
        try {
            String []s=lbTongTien.getText().split("\\s");
            String sqlThongKe="INSERT INTO ThongKe (ban,tongTien,tienKH,tienThua,tenNV,ngay,thoiGian,Id_Hoadon,tien) VALUES("+lbBan.getText()+",N'"+lbTongTien.getText()+"',N'"+(tfTienNhanCuaKach.getText()+" "+ s[1])+"',N'"+lbTienthua.getText()+"',N'"+lbNhanVien.getText()+"','"+new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(lblDate.getText()).getTime())+"','"+lblTime.getText()+"','"+"HD"+dem()+"','"+s[0]+"')";
            
            pst=conn.prepareStatement(sqlThongKe);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
     
    private void Update_SL(){
        try{
            String sqlTemp="SELECT * FROM QLNuoc WHERE tenNuoc =N'"+txbtenNuoc.getText()+"'";
                    
            PreparedStatement pstTemp=conn.prepareStatement(sqlTemp);
             ResultSet rsTemp=pstTemp.executeQuery();
                    
            if(rsTemp.next()){
                        
                String sqlUpdate="UPDATE QLNuoc SET soLuong="+(rsTemp.getInt("soLuong")-Integer.parseInt(tfSoLuong.getText()))+" WHERE tenNuoc=N'"+txbtenNuoc.getText()+"'";
                        
                 pst=conn.prepareStatement(sqlUpdate);
                 pst.executeUpdate();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
   
    private void Delete(){
        try {
            String sqlDelete="DELETE FROM BanHang WHERE ban="+lbBan.getText();
            pst=conn.prepareStatement(sqlDelete);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addThucUong() {
        try {
            String sql="SELECT * FROM BanHang WHERE ban="+lbBan.getText();
            String sqlInsert="INSERT INTO BanHang (ban,tenNuoc,soLuong,thanhTien) VALUES("+lbBan.getText()+",N'"+txbtenNuoc.getText()+"',"+tfSoLuong.getText()+",N'"+lbThanhTien.getText()+"')";
            
            pst=conn.prepareStatement(sqlInsert);
            pst.executeUpdate();
           // lblStatus.setText("Thêm sản phẩm thành công!");
            JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công");
            Disabled();
            loadData(sql);
            checkBill();
        } catch (SQLException ex) {
            Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addHoaDon() {
        String sql="SELECT * FROM BanHang WHERE ban="+lbBan.getText();
        try {
             pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String sqlInsert="INSERT INTO HoaDon (tenNuoc,soLuong,thanhTien) VALUES(N'"+rs.getString("tenNuoc")+"',"+rs.getInt("soLuong")+",N'"+rs.getString("thanhTien")+"')";
                pst=conn.prepareStatement(sqlInsert);
                pst.executeUpdate();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void Refresh(){
        Pay=false;
        txbtenNuoc.setText("");
        lbBan.setText("0");
        tfSoLuong.setText("");
        lbThanhTien.setText("0 VNĐ");
        lbTongTien.setText("0 VNĐ");
        tfTienNhanCuaKach.setText("");
        lbTienthua.setText("0 VNĐ");
        tfPay.setEnabled(false);
        tblNuoc.clearSelection();
        Disabled();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        PanelBan = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cbLoaiNuoc = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbThanhTien = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfSoLuong = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNuoc = new javax.swing.JTable();
        txbtenNuoc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        img = new javax.swing.JLabel();
        btnchuyen = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        tfTienNhanCuaKach = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTienthua = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        tfPay = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbNhanVien = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbBan = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý bán hàng");
        setBackground(new java.awt.Color(153, 255, 255));

        jLabel1.setFont(new java.awt.Font("Microsoft Himalaya", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bán hàng");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout PanelBanLayout = new javax.swing.GroupLayout(PanelBan);
        PanelBan.setLayout(PanelBanLayout);
        PanelBanLayout.setHorizontalGroup(
            PanelBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );
        PanelBanLayout.setVerticalGroup(
            PanelBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("THÔNG TIN BÀN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(PanelBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbLoaiNuoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbLoaiNuoc.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbLoaiNuocPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbLoaiNuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiNuocActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/plus.png"))); // NOI18N
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Số lượng");

        lbThanhTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbThanhTien.setForeground(new java.awt.Color(51, 51, 51));
        lbThanhTien.setText("0 VNĐ");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Thành tiền:");

        tfSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSoLuongKeyReleased(evt);
            }
        });

        tblNuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblNuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNuocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNuoc);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tên Nước");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Loại Nước");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("MENU");

        img.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        btnchuyen.setBackground(new java.awt.Color(255, 51, 51));
        btnchuyen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnchuyen.setForeground(new java.awt.Color(0, 51, 51));
        btnchuyen.setText("Chuyển bàn");
        btnchuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchuyenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(lbThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnchuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(192, 192, 192)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txbtenNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(161, 161, 161)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txbtenNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbThanhTien)
                                    .addComponent(jLabel12)))
                            .addComponent(btnAdd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnchuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addComponent(img, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnHome.setIcon(new javax.swing.ImageIcon("D:\\NetBeansProjects\\QLCF\\src\\Photos\\trangchu.png")); // NOI18N
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setForeground(new java.awt.Color(51, 51, 51));

        tfTienNhanCuaKach.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tfTienNhanCuaKach.setForeground(new java.awt.Color(51, 51, 51));
        tfTienNhanCuaKach.setText("0");
        tfTienNhanCuaKach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfTienNhanCuaKachKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Tổng tiền:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Tiền nhận:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Tiền thừa:");

        lbTienthua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTienthua.setForeground(new java.awt.Color(51, 51, 51));
        lbTienthua.setText("0 VNĐ");

        lbTongTien.setBackground(new java.awt.Color(204, 204, 255));
        lbTongTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTongTien.setForeground(new java.awt.Color(51, 51, 51));
        lbTongTien.setText("0 VNĐ");

        tfPay.setBackground(new java.awt.Color(255, 51, 51));
        tfPay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tfPay.setForeground(new java.awt.Color(0, 51, 51));
        tfPay.setText("Thanh toán");
        tfPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfTienNhanCuaKach, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTienthua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfPay, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTongTien)
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfTienNhanCuaKach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPay, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbTienthua)))
        );

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableHoaDon);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel8.setText("Họ tên nhân viên:");

        lbNhanVien.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lbNhanVien.setText("name");

        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel9.setText("Thời gian:");

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblTime.setText("time");

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        jLabel6.setText("Ngày:");

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblDate.setText("day");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Bàn số:");

        lbBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbBan.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(42, 42, 42)
                        .addComponent(lbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(101, 101, 101)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbBan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbBan)))
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("DANH SÁCH ORDERS SẢN PHẨM");

        jPanel5.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(0, 204, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Bàn có khách");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Bàn trống");

        jPanel7.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Đang chọn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(388, 388, 388)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("Quản lí bàn");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        
        MainFr home = new MainFr(detail);
        this.setVisible(false);
        home.setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
       String sql = "SELECT * FROM QLNuoc WHERE loaiNuoc=N'"+cbLoaiNuoc.getSelectedItem().toString()+"'";
        addThucUong();
       Update_SL();
        tinhTongTien();
        loadNuoc(sql);
    }//GEN-LAST:event_btnAddActionPerformed
    private void loadimg(){
    String sql = "SELECT  *  FROM QLNuoc where tenNuoc = ? ";
        try { 
            pst=conn.prepareStatement(sql);
            pst.setString(1, this.txbtenNuoc.getText().toString());
            
            rs=pst.executeQuery();
            while(rs.next()){
                ImageIcon icon = new ImageIcon(rs.getString("hinhanh").toString());
                img.setIcon(icon);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cbLoaiNuocPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbLoaiNuocPopupMenuWillBecomeInvisible
       //cbxNuoc.removeAllItems();
        String sql = "SELECT * FROM QLNuoc WHERE loaiNuoc=N'"+cbLoaiNuoc.getSelectedItem().toString()+"'";
        try {
            
             pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            loadNuoc(sql);
            while(rs.next()){
                //this.cbxNuoc.addItem(rs.getString("tenNuoc").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
        if(tblNuoc.getRowCount()==0){
            //cbxNuoc.setEnabled(false);
            tfSoLuong.setEnabled(false);
            tfSoLuong.setText("");
            lbThanhTien.setText("0 VNĐ");
        }
        else {
            //cbxNuoc.setEnabled(true);           
            tfSoLuong.setText("");
            lbThanhTien.setText("0 VNĐ");
        }
    }//GEN-LAST:event_cbLoaiNuocPopupMenuWillBecomeInvisible
    
    private void tfSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSoLuongKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
 
        tfSoLuong.setText(cutChar(tfSoLuong.getText()));
          int Click=tblNuoc.getSelectedRow();
          TableModel model=tblNuoc.getModel();
          
        if(tfSoLuong.getText().equals("")){
            String []s=model.getValueAt(Click, 1).toString().split("\\s");
            lbThanhTien.setText("0"+" "+s[1]);
            btnAdd.setEnabled(false);
           
        }
        else{
            tfSoLuong.setText(cutChar(tfSoLuong.getText()));
          
            String sqlCheck="SELECT * FROM QLNuoc WHERE tenNuoc=N'"+model.getValueAt(Click, 0).toString()+"'";
            try{
            pst=conn.prepareStatement(sqlCheck);
            rs=pst.executeQuery();
                while(rs.next()){
                    if((rs.getInt("soLuong")-Integer.parseInt(tfSoLuong.getText()))<0){
                        String []s=model.getValueAt(Click, 1).toString().split("\\s");
                        lbThanhTien.setText("0"+" "+s[1]);
                        btnAdd.setEnabled(false);
                        //lblStatus.setText("Số lượng sản phẩm bán không được vượt quá số lượng hàng trong kho!!");  
                        //JOptionPane.showMessageDialog(null,"Số lượng sản phẩm bán không được vượt quá số lượng hàng trong kho!!" );
                    }
                    else{
                        int soluong=Integer.parseInt(tfSoLuong.getText().toString());
                        String []s=model.getValueAt(Click, 1).toString().split("\\s");
                        lbThanhTien.setText(formatter.format(convertedToNumbers(s[0])*soluong)+" "+s[1]);
                        
                        //lblStatus.setText("Số lượng sản phẩm bán hợp lệ!!");
                        //JOptionPane.showMessageDialog(null,"Số lượng sản phẩm bán hợp lệ!!" );
                        btnAdd.setEnabled(true);  
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_tfSoLuongKeyReleased

    private void tfTienNhanCuaKachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTienNhanCuaKachKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        
        tfTienNhanCuaKach.setText(cutChar(tfTienNhanCuaKach.getText()));
        
        if(tfTienNhanCuaKach.getText().equals("")){
            String []s=lbTongTien.getText().split("\\s");
            lbTienthua.setText("0"+" "+s[1]);
        }
        else{
            tfTienNhanCuaKach.setText(formatter.format(convertedToNumbers(tfTienNhanCuaKach.getText())));
            
            String s1=tfTienNhanCuaKach.getText();
            String[] s2=lbTongTien.getText().split("\\s");
            
            if((convertedToNumbers(s1)-convertedToNumbers(s2[0]))>=0){
                lbTienthua.setText(formatter.format((convertedToNumbers(s1)-convertedToNumbers(s2[0])))+" "+s2[1]);              
                Pay=true;
            }
            else {
                
                lbTienthua.setText(formatter.format((convertedToNumbers(s1)-convertedToNumbers(s2[0])))+" "+s2[1]);                
                Pay=false;
            }
        }
    }//GEN-LAST:event_tfTienNhanCuaKachKeyReleased
    private void Update(){
        lblTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
        if(lbBan.getText().equals("0"))
            cbLoaiNuoc.setEnabled(false);
        else cbLoaiNuoc.setEnabled(true);
        
    }
      private void setData(){
        Disabled();
        lblDate.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
        lblTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
    }
    private void tfPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPayActionPerformed
        deleteHoaDon();
        deleteThongTinHoaDon();
        if(Pay==true){
            try {
                String sql="SELECT * FROM BanHang WHERE ban="+lbBan.getText();
                Disabled();
                String []s=lbTongTien.getText().split("\\s");
                String sqlHoaDon="INSERT INTO ThongTinHoaDon (ban,tongTien,tienKH,tienThua,tenNV,ngay,thoiGian) VALUES(?,?,?,?,?,?,?)";
       
                pst=conn.prepareStatement(sqlHoaDon);
                pst.setString(1, lbBan.getText());
                pst.setString(2, lbTongTien.getText());
                pst.setString(3, tfTienNhanCuaKach.getText());
                pst.setString(4, lbTienthua.getText());
                pst.setString(5, lbNhanVien.getText());
                try {
                    pst.setDate(6, new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(lblDate.getText()).getTime()));
                } catch (ParseException ex) {
                    Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
                }
                pst.setString(7, lblTime.getText());
                pst.executeUpdate();
                addHoaDon();
                luuThongKe();
                Delete();
                loadData(sql);
                JOptionPane.showMessageDialog(null,"Thực hiện thanh toán bàn "+lbBan.getText()+" thành công!" );
                Refresh();        
                btnAdd.setEnabled(false);
                tfPay.setEnabled(false);
                tfTienNhanCuaKach.setEnabled(false);
            } catch (SQLException ex) {
                Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(banhangFr.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(Pay==false){
            JOptionPane.showMessageDialog(null, "Số tiền không hợp lệ !");
        }
    }//GEN-LAST:event_tfPayActionPerformed

    private void cbLoaiNuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiNuocActionPerformed
        
    }//GEN-LAST:event_cbLoaiNuocActionPerformed

    private void tblNuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNuocMouseClicked
        int Click=tblNuoc.getSelectedRow();
        TableModel model=tblNuoc.getModel();
        txbtenNuoc.setText(model.getValueAt(Click,0).toString());
        tfSoLuong.setEnabled(true);
        checkSoLuongHang();
        tfSoLuong.setText("");
        loadimg();
    }//GEN-LAST:event_tblNuocMouseClicked

    private void btnchuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchuyenActionPerformed
       chuyen_ghep chuyen = new chuyen_ghep();
       chuyen.setVisible(true);
    }//GEN-LAST:event_btnchuyenActionPerformed
       

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
            java.util.logging.Logger.getLogger(banhangFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(banhangFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(banhangFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(banhangFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail= new Detail();
                new banhangFr(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBan;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnchuyen;
    private javax.swing.JComboBox<String> cbLoaiNuoc;
    private javax.swing.JLabel img;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbBan;
    private javax.swing.JLabel lbNhanVien;
    private javax.swing.JLabel lbThanhTien;
    private javax.swing.JLabel lbTienthua;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tblNuoc;
    private javax.swing.JButton tfPay;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tfTienNhanCuaKach;
    private javax.swing.JTextField txbtenNuoc;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while(true){
        Update();  
            try{
                Thread.sleep(1);  
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Refresh();
       // lblStatus.setText("Trạng Thái");
        String sql="SELECT * FROM BanHang WHERE ban="+((JButton)e.getSource()).getText()+"";
        loadData(sql);
        checkTinhTrangban();
        //((JButton)e.getSource()).setIcon(icon2);
        ((JButton)e.getSource()).setBackground(Color.yellow);
        soban=((JButton)e.getSource()).getText();
        lbBan.setText(((JButton)e.getSource()).getText());
        
        img.setIcon(null);
        tinhTongTien();
        checkBill();
    }
}
