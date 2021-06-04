
package qlcf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class qlnvFr extends javax.swing.JFrame {
    private PreparedStatement pst = null;  
    private Connection conn=null;
    private ResultSet rs=null;
    
    private String sql="SELECT * FROM QLNV ORDER BY maNV";
    
    private boolean add=false, change=false;
    
    private Detail detail;
    

     private void connection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=QuanCaPhe;"
                    + "username=sa;password=123456");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public qlnvFr(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(this);      
                    
         connection();
        Disabled();
        loadData(sql);
        detail=new Detail(d);
        check_User();
    }
    
    private void loadData(String sql){
        try{
            String[] arry={"Mã Nhân Viên","Họ Tên","Giới Tính","Ngày Sinh","SĐT","Địa Chỉ"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("maNV").trim());
                vector.add(rs.getString("tenNV").trim());
                vector.add(rs.getString("gioiTinh").trim());
                vector.add(rs.getString("ngaySinh").trim());
                vector.add(rs.getString("sdt").trim());
                vector.add(rs.getString("diaChi").trim());
                model.addRow(vector);
            }
            tableNV.setModel(model);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
     
    private void Enabled(){
        tfMaNV.setEnabled(true);
        tfHoten.setEnabled(true);
        tfNgaysinh.setEnabled(true);
        tfSDT.setEnabled(true);
        tfTaikhoan.setEnabled(true);
        pass.setEnabled(true);
        passConfirm.setEnabled(true);
        tfDiachi.setEnabled(true);
        rbNu.setEnabled(true);
        rbNam.setEnabled(true);
    }
    
    private void Disabled(){
        tfMaNV.setEnabled(false);
        tfHoten.setEnabled(false);
        tfNgaysinh.setEnabled(false);
        tfSDT.setEnabled(false);
        tfTaikhoan.setEnabled(false);
        pass.setEnabled(false);
        passConfirm.setEnabled(false);
        tfDiachi.setEnabled(false);
        rbNu.setEnabled(false);
        rbNam.setEnabled(false);
    }
    
    private void reset(){
        add=false;
        change=false;
        tfMaNV.setText("");
        tfHoten.setText("");
        ((JTextField)tfNgaysinh.getDateEditor().getUiComponent()).setText("");
        tfSDT.setText("");
        tfTaikhoan.setText("");
        pass.setText("");
        passConfirm.setText("");
        tfDiachi.setText("");
        lbTrangthai.setText("Trạng Thái");
        rbNam.setSelected(false);
        btnEdit.setEnabled(false);
        btnDel.setEnabled(false);
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
        btnAdd.setEnabled(true);
    }

    private void checkGT(String GT){
        if(GT.equals("Nam"))
            rbNam.setSelected(true);
        else
            rbNu.setSelected(true);
    }
    
    private boolean checkNull(){
        if(tfMaNV.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập mã nhân viên!");
            return false;
        }
        else
        if(tfHoten.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập họ tên nhân viên!");
            return false;
        }
        else
        if(rbNam.isSelected()==false && rbNu.isSelected()==false){
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn giới tính!");
            return false;
        }
        else
        if(((JTextField)tfNgaysinh.getDateEditor().getUiComponent()).getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập ngày sinh!");
            return false;
        }
        else   
        if(tfSDT.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập số điện thoại!");
            return false;
        }
        else   
        if(tfDiachi.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập địa chỉ!");
            return false;
        }
        else
        if(tfTaikhoan.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập tài khoản!");
            lbTrangthai.setText("Bạn chưa nhập tài khoản!");
            return false;
        }
        else
        if(pass.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập mật khẩu!");
            return false;
        }
        else
        if(passConfirm.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập lại mật khẩu!");
            return false;
        }
        else
        if(String.valueOf(pass.getPassword()).equals(String.valueOf(passConfirm.getPassword()))){
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Nhập lại mật khẩu không đúng!");
            return false;
        }
    }
    
    private String gioiTinh(){
        if(rbNam.isSelected())
            return rbNam.getText();
        else
            return rbNu.getText();
    }
    
    private void addNV(){
        if(checkNull()){
            String sqlInsert="INSERT INTO  QLNV (maNV,tenNV,gioiTinh,ngaySinh,sdt,diaChi,taiKhoan,matKhau) VALUES(?,?,?,?,?,?,?,?)";
            try{
                pst=conn.prepareStatement(sqlInsert);
                pst.setString(1, this.tfMaNV.getText());
                pst.setString(2, this.tfHoten.getText());
                pst.setString(3, gioiTinh());
                pst.setString(4, ((JTextField)tfNgaysinh.getDateEditor().getUiComponent()).getText());
                pst.setString(5,tfSDT.getText());
                pst.setString(6, tfDiachi.getText());
                pst.setString(7, tfTaikhoan.getText());
                pst.setString(8, pass.getText());
                
                pst.executeUpdate();
                reset();
                loadData(sql);
                Disabled();
                lbTrangthai.setText("Thêm nhân viên thành công!");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }  
    private void changeNV(){
       int click=tableNV.getSelectedRow();
            TableModel model=tableNV.getModel();
        
        if(checkNull()){
            String sqlChange="UPDATE QLNV SET maNV=?,tenNV=?,gioiTinh=?,ngaysinh=?,sdt=?,diachi=?,taiKhoan=?,matKhau=? WHERE maNV='"+model.getValueAt(click,0)+"'";
            try{
                pst=conn.prepareStatement(sqlChange);
                pst.setString(1, tfMaNV.getText());
                pst.setString(2, tfHoten.getText());
                pst.setString(3, gioiTinh());
                pst.setString(4, ((JTextField)tfNgaysinh.getDateEditor().getUiComponent()).getText());
                pst.setString(5,tfSDT.getText());
                pst.setString(6, tfDiachi.getText());
                pst.setString(7, tfTaikhoan.getText());
                pst.setString(8, pass.getText());
                
                
                pst.executeUpdate();
                reset();
                loadData(sql);
                Disabled();
                JOptionPane.showMessageDialog(null, "Sửa thông tin nhân viên thành công!");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    private boolean check(){
        try {
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("maNV").toString().trim().equals(tfMaNV.getText())){
                     JOptionPane.showMessageDialog(null,"Mã nhân viên bạn nhập đã tồn tại!");
                    return false;
                }
                else
                if(rs.getString("taiKhoan").toString().trim().equals(tfTaikhoan.getText())){
                     JOptionPane.showMessageDialog(null,"Tài khoản bạn nhập đã tồn tại!");
                    return false;
                }
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    private void loadAccount(String s){
        try{
            String sql="SELECT * FROM QLNV WHERE maNV='"+s+"'";
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                tfTaikhoan.setText(rs.getString("taiKhoan").trim());
                pass.setText(rs.getString("matKhau").trim());
                passConfirm.setText(rs.getString("matKhau").trim());
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void check_User(){
        if(String.valueOf(detail.getUser().substring(0, 4)).equals("User")){
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(false);
            btnDel.setEnabled(false);
            btnSave.setEnabled(false);
            btnCancel.setEnabled(false);
        }
    }
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lbQLNV = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNV = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lbMaNV = new javax.swing.JLabel();
        lbHoten = new javax.swing.JLabel();
        lbGioitinh = new javax.swing.JLabel();
        lbNgaysinh = new javax.swing.JLabel();
        lbSDT = new javax.swing.JLabel();
        lbDiachi = new javax.swing.JLabel();
        tfMaNV = new javax.swing.JTextField();
        tfHoten = new javax.swing.JTextField();
        tfSDT = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfTaikhoan = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        tfDiachi = new javax.swing.JTextField();
        rbNam = new javax.swing.JRadioButton();
        rbNu = new javax.swing.JRadioButton();
        passConfirm = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        tfNgaysinh = new com.toedter.calendar.JDateChooser();
        tfFind = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lbTrangthai = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý nhân viên");

        lbQLNV.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        lbQLNV.setText("Quản lý nhân viên");

        tableNV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableNV);

        lbMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbMaNV.setText("Mã Nhân viên:");

        lbHoten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbHoten.setText("Họ và Tên:");

        lbGioitinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbGioitinh.setText("Giới tính:");

        lbNgaysinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbNgaysinh.setText("Ngày sinh:");

        lbSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbSDT.setText("Số điện thoại:");

        lbDiachi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbDiachi.setText("Địa chỉ:");

        tfMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfHoten.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tfSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSDTKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Tài khoản:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mật khẩu:");

        tfTaikhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfDiachi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        buttonGroup1.add(rbNam);
        rbNam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbNam.setText("Nam");

        buttonGroup1.add(rbNu);
        rbNu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbNu.setText("Nữ");

        passConfirm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Xác nhận lại mật khẩu:");

        tfNgaysinh.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMaNV)
                    .addComponent(lbHoten)
                    .addComponent(lbGioitinh)
                    .addComponent(lbNgaysinh)
                    .addComponent(lbDiachi)
                    .addComponent(lbSDT))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfSDT)
                        .addComponent(tfHoten)
                        .addComponent(tfDiachi)
                        .addComponent(tfNgaysinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(rbNu, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                            .addComponent(rbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tfMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTaikhoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMaNV)
                    .addComponent(jLabel1)
                    .addComponent(tfTaikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbHoten)
                            .addComponent(tfHoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbGioitinh)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbNu)
                            .addComponent(rbNam)
                            .addComponent(jLabel4)
                            .addComponent(passConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNgaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNgaysinh))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDiachi)
                    .addComponent(tfDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tfFind.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfFindKeyReleased(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(51, 51, 255));
        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("THÊM");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(51, 51, 255));
        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("SỬA");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(51, 51, 255));
        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("LƯU");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 51, 102));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("HỦY");
        btnCancel.setEnabled(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnDel.setBackground(new java.awt.Color(51, 51, 255));
        btnDel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDel.setForeground(new java.awt.Color(255, 255, 255));
        btnDel.setText("XÓA");
        btnDel.setEnabled(false);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd)
                        .addComponent(btnSave)
                        .addComponent(btnEdit)
                        .addComponent(btnDel))))
        );

        btnBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/trangchu.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lbTrangthai.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lbTrangthai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangthai.setText("Trạng Thái");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Thông tin nhân viên");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnBack)
                            .addGap(303, 303, 303)
                            .addComponent(lbQLNV)
                            .addGap(0, 167, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 949, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(247, 247, 247))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbQLNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTrangthai)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        setBounds(0, 0, 1026, 678);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        reset();
        add=true;
        Enabled();
        btnAdd.setEnabled(false);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
    }//GEN-LAST:event_btnAddActionPerformed
    
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        add=false;
        change=true;
        Enabled();
        btnAdd.setEnabled(false);
        btnDel.setEnabled(false);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa nhân viên này hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION){
            
            String sqlDelete="DELETE FROM QLNV WHERE maNV=?";
            
            try{
                pst=conn.prepareStatement(sqlDelete);
                pst.setString(1, String.valueOf(this.tfMaNV.getText()));
                pst.executeUpdate();
                reset();
                loadData(sql);
                Disabled();
                 JOptionPane.showMessageDialog(null,"Xóa thông tin nhân viên thành công!");
            
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            
          
        }
        else reset();
    }//GEN-LAST:event_btnDelActionPerformed
    
    private void tableNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNVMouseClicked
        int click=tableNV.getSelectedRow();
        TableModel model=tableNV.getModel();
        
        tfMaNV.setText(model.getValueAt(click, 0).toString());
        tfHoten.setText(model.getValueAt(click, 1).toString());
        ((JTextField)tfNgaysinh.getDateEditor().getUiComponent()).setText(model.getValueAt(click, 3).toString());
        tfSDT.setText(model.getValueAt(click, 4).toString());
        tfDiachi.setText(model.getValueAt(click, 5).toString());
        
        checkGT(model.getValueAt(click, 2).toString());
        loadAccount(model.getValueAt(click, 0).toString());
        
        btnEdit.setEnabled(true);
        btnDel.setEnabled(true);
        check_User();
    }//GEN-LAST:event_tableNVMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        //SQL.closeConnection(conn);
        MainFr home = new MainFr(detail);
        this.setVisible(false);
        home.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(add==true){
            if(check()){
                addNV();
            }
        }
        else{
            if(change==true)
                changeNV();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        reset();
        Disabled();
        loadData(sql);
        check_User();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tfSDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSDTKeyReleased
        tfSDT.setText(cutChar(tfSDT.getText()));
        
        if(tfSDT.getText().length()==11 || tfSDT.getText().length()==10 ){
            
            btnSave.setEnabled(true);
            lbTrangthai.setText("Số điện thoại đã hợp lệ!!");
        }
        else
        if(tfSDT.getText().length()>11 || tfSDT.getText().length()<10){
            btnSave.setEnabled(false);
            lbTrangthai.setText("Số điện thoại không được nhỏ hơn 10 số hoặc vượt quá 11 số!!");
        }
    }//GEN-LAST:event_tfSDTKeyReleased

    private void tfFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFindKeyReleased
        String sql = "SELECT * FROM QLNV where maNV like N'%"+tfFind.getText()+"%' or tenNV like N'%"+tfFind.getText()+"%' or gioiTinh like N'%"+tfFind.getText()+"%' or ngaySinh like N'%"+tfFind.getText()+"%' or sdt like N'%"+tfFind.getText()+"%' or diaChi like N'%"+tfFind.getText()+"%' or taiKhoan like N'%"+tfFind.getText()+"%' or matKhau like N'%"+tfFind.getText()+"'";
        //Disabled();
        loadData(sql);
        //tfFind.setText("");
        reset();
        check_User();
        btnCancel.setEnabled(true);
    }//GEN-LAST:event_tfFindKeyReleased

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
            java.util.logging.Logger.getLogger(qlnvFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(qlnvFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(qlnvFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(qlnvFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail= new Detail();
                new qlnvFr(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDiachi;
    private javax.swing.JLabel lbGioitinh;
    private javax.swing.JLabel lbHoten;
    private javax.swing.JLabel lbMaNV;
    private javax.swing.JLabel lbNgaysinh;
    private javax.swing.JLabel lbQLNV;
    private javax.swing.JLabel lbSDT;
    private javax.swing.JLabel lbTrangthai;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPasswordField passConfirm;
    private javax.swing.JRadioButton rbNam;
    private javax.swing.JRadioButton rbNu;
    private javax.swing.JTable tableNV;
    private javax.swing.JTextField tfDiachi;
    private javax.swing.JTextField tfFind;
    private javax.swing.JTextField tfHoten;
    private javax.swing.JTextField tfMaNV;
    private com.toedter.calendar.JDateChooser tfNgaysinh;
    private javax.swing.JTextField tfSDT;
    private javax.swing.JTextField tfTaikhoan;
    // End of variables declaration//GEN-END:variables
}
