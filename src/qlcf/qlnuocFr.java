
package qlcf;


import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class qlnuocFr extends javax.swing.JFrame {
    private PreparedStatement pst = null;  
    private Detail detail;
    
    private String sql="SELECT * FROM QLNuoc ORDER BY maNuoc";
    String duongdan = null;
    private Connection conn=null;
    private ResultSet rs=null;
   
    
    private boolean add=false, change=false;
    
    public qlnuocFr(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(this);
        detail=new Detail(d);      
        connection();        
        Disabled();
        loadData(sql);
        loadLoaiNuoc();
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
    private void checkKyTu(String arry){
        char[] character=arry.toCharArray();
        for(int i = 0; i<character.length;i++){
            if(String.valueOf(character[i]).matches("\\D+")){
                btnSave.setEnabled(false);
                break;
            }
            else btnSave.setEnabled(true);
        }
    }
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }  
    
    private void loadLoaiNuoc(){
        cbLoaiNuoc.removeAllItems();
        cbLoaiNuoc.addItem("Cafe");
        cbLoaiNuoc.addItem("Sinh Tố");
        cbLoaiNuoc.addItem("Nước Giải Khát");
        cbLoaiNuoc.addItem("Trà Sữa");
    }
    
    private void loadData(String sql){
        try{
            String[] arry={"Mã Thức Uống","Loại Nước","Tên Nước","Đơn Vị","Số Lượng","Giá Bán","hinhanh"};
            DefaultTableModel model=new DefaultTableModel(arry,0);
      
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("maNuoc").trim());
                vector.add(rs.getString("loaiNuoc").trim());
                vector.add(rs.getString("tenNuoc").trim());
                vector.add(rs.getString("donVi").trim());
                vector.add(rs.getInt("soLuong"));
                vector.add(rs.getString("giaBan").trim());
                vector.add(rs.getString("hinhanh").trim());
                
                model.addRow(vector);
            }
            tableDrink.setModel(model);
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
    
    private void Enabled(){
        tfMatu.setEnabled(true);
        cbLoaiNuoc.setEnabled(true);
        tfTen.setEnabled(true);
        tfDonVi.setEnabled(true);
        tfSoLuong.setEnabled(true);
        tfGia.setEnabled(true);
        jButton1.setEnabled(true);
    }
    
    private void Disabled(){
        tfMatu.setEnabled(false);
        cbLoaiNuoc.setEnabled(false);
        tfTen.setEnabled(false);
        tfDonVi.setEnabled(false);
        tfSoLuong.setEnabled(false);
        tfGia.setEnabled(false);
        jButton1.setEnabled(false);
        
    }
    
    private void reset(){
        add=false;
        change=false;
        loadLoaiNuoc();
        tfMatu.setText("");
        cbLoaiNuoc.setSelectedIndex(0);
        tfTen.setText("");
        tfDonVi.setText("");
        tfSoLuong.setText("");
        tfGia.setText("");
        btnAdd.setEnabled(true);
        btnSave.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDel.setEnabled(false);
        btnCancel.setEnabled(false);
        lblanh.setText("");
        
    }

    private boolean checkNull(){
        if(tfMatu.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Bạn chưa nhập mã thức uống!");
            return false;
        }
        else
        if(cbLoaiNuoc.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null,"Bạn chưa chọn loại thức uống!");
            return false;
        }
        else
        if(tfTen.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Bạn chưa nhập tên thức uống");
            return false;
        }
        else   
        if(tfDonVi.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Bạn chưa nhập đơn vị tính!");
            return false;
        }
        else   
        if(tfSoLuong.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Bạn chưa nhập số lượng nước!");
            return false;
        }
        else   
        if(tfGia.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Bạn chưa nhập giá!");
            return false;
        }
        if(this.lblanh.getText().length() == 0){
            JOptionPane.showMessageDialog(null,"Bạn chưa chọn ảnh! ");
            return false;
        }
        return true;
    }
    private void addDrink(){
        if(checkNull()){
            String sqlInsert="INSERT INTO QLNuoc (maNuoc,loaiNuoc,tenNuoc,giaBan,donVi,soLuong,hinhanh) VALUES(?,?,?,?,?,?,?)";
            try{
                pst=conn.prepareStatement(sqlInsert);
                pst.setString(1, tfMatu.getText());
                pst.setString(2, cbLoaiNuoc.getSelectedItem().toString());
                pst.setString(3, tfTen.getText());
                pst.setString(4,tfGia.getText()+" "+"VNĐ");
                pst.setString(5, tfDonVi.getText());
                pst.setString(6,tfSoLuong.getText());
                pst.setString(7, duongdan.toString().trim());             
                pst.executeUpdate();
                reset();
                loadData(sql);
                Disabled();
                 JOptionPane.showMessageDialog(null,"Thêm thức uống thành công!");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }  
     private void editDrink(){
      int click=tableDrink.getSelectedRow();
            TableModel model=tableDrink.getModel();
        
        if(checkNull()){
            String sqlChange="UPDATE QLNuoc SET maNuoc=?,loaiNuoc=?, tenNuoc=?, giaBan=?, donVi=?,soLuong=?,hinhanh=? WHERE maNuoc=N'"+model.getValueAt(click, 0)+"'";
            try{
                pst=conn.prepareStatement(sqlChange);
                pst.setString(1, tfMatu.getText());
                pst.setString(2, cbLoaiNuoc.getSelectedItem().toString());
                pst.setString(3, tfTen.getText());
                pst.setString(4, tfGia.getText()+" VNĐ");
                pst.setString(5,tfDonVi.getText());
                pst.setString(6, tfSoLuong.getText());
                pst.setString(7, duongdan.toString().trim());
                
                
                pst.executeUpdate();
                reset();
                loadData(sql);
                Disabled();
                 JOptionPane.showMessageDialog(null,"Thay đổi thông tin thành công!");
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
                if(rs.getString("maNuoc").toString().trim().equals(tfMatu.getText())){
                     JOptionPane.showMessageDialog(null,"Mã nước bạn nhập đã tồn tại");
                    return false;
                }
                else
                if(rs.getString("tenNuoc").toString().trim().equals(tfTen.getText())){
                     JOptionPane.showMessageDialog(null,"Thức uống bạn nhập đa tồn tại!");
                    return false;
                }
            }
            //SQL.closeResultSet( rs);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbQLTU = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbMatu = new javax.swing.JLabel();
        lbLoai = new javax.swing.JLabel();
        lbTen = new javax.swing.JLabel();
        lbGia = new javax.swing.JLabel();
        tfMatu = new javax.swing.JTextField();
        tfTen = new javax.swing.JTextField();
        tfGia = new javax.swing.JTextField();
        cbLoaiNuoc = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfDonVi = new javax.swing.JTextField();
        tfSoLuong = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDrink = new javax.swing.JTable();
        tfFind = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lbThongtin = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblanh = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý thức uống");
        setResizable(false);

        lbQLTU.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lbQLTU.setText("Quản lý thức uống");

        lbMatu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbMatu.setText("Mã thức uống:");

        lbLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbLoai.setText("Loại thức uống:");

        lbTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTen.setText("Tên thức uống:");

        lbGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbGia.setText("Giá tiền:");

        tfMatu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfGiaKeyReleased(evt);
            }
        });

        cbLoaiNuoc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Đơn vị tính:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Số lượng:");

        tfDonVi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tfSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSoLuongActionPerformed(evt);
            }
        });
        tfSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSoLuongKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMatu)
                    .addComponent(lbTen)
                    .addComponent(jLabel3))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfMatu, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTen, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lbLoai)
                    .addComponent(lbGia))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfGia, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfMatu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMatu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTen))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbLoai)
                            .addComponent(cbLoaiNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbGia))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableDrink.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableDrink.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã thức uống", "Loại thức uống", "Tên thức uống", "Giá bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableDrink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDrinkMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableDrink);
        if (tableDrink.getColumnModel().getColumnCount() > 0) {
            tableDrink.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableDrink.getColumnModel().getColumn(1).setPreferredWidth(25);
            tableDrink.getColumnModel().getColumn(3).setPreferredWidth(20);
        }

        tfFind.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFindActionPerformed(evt);
            }
        });
        tfFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfFindKeyReleased(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/trangchu.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
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
        btnEdit.setText("EDIT");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbThongtin.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbThongtin.setText("Thông tin Thức uống");

        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setText("Chọn ảnh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblanh.setText(".");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(261, 261, 261)
                        .addComponent(lbQLTU))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblanh)
                        .addGap(207, 207, 207)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbThongtin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(310, 917, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack)
                    .addComponent(lbQLTU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbThongtin)
                    .addComponent(tfFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblanh))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(498, 498, 498))
        );

        setBounds(0, 0, 1067, 646);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        reset();
        add=true;
        Enabled();
        btnAdd.setEnabled(false);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
    }//GEN-LAST:event_btnAddActionPerformed
    
    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        int click=JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa thức uống này hay không?", "Thông báo", 2);
        if(click==JOptionPane.YES_OPTION){
            
            String sqlDelete="DELETE FROM QLNuoc WHERE maNuoc=?";
            
           try{
                pst=conn.prepareStatement(sqlDelete);
                pst.setString(1, String.valueOf(this.tfMatu.getText()));
                pst.executeUpdate();
                 reset();
            loadData(sql);
            Disabled();
            JOptionPane.showMessageDialog(null,"Đã xóa!");
            
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        
        }
        else reset();
    }//GEN-LAST:event_btnDelActionPerformed

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

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        Disabled();
        reset();
        loadData(sql);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        MainFr home = new MainFr(detail);
        this.setVisible(false);
        home.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void tableDrinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDrinkMouseClicked
        cbLoaiNuoc.removeAllItems();
        int click=tableDrink.getSelectedRow();
        TableModel model=tableDrink.getModel();
        
        tfMatu.setText(model.getValueAt(click, 0).toString());
        cbLoaiNuoc.addItem(model.getValueAt(click, 1).toString());
        tfTen.setText(model.getValueAt(click, 2).toString());
        tfDonVi.setText(model.getValueAt(click, 3).toString());
        tfSoLuong.setText(model.getValueAt(click, 4).toString());
        
        String []s1=model.getValueAt(click,5).toString().split("\\s");
        tfGia.setText(s1[0]);
        ImageIcon icon = new ImageIcon(model.getValueAt(click,6).toString());
        jLabel1.setIcon(icon);
        duongdan = model.getValueAt(click,6).toString();
        lblanh.setText(".");

        
        this.btnEdit.setEnabled(true);
        this.btnDel.setEnabled(true);
    }//GEN-LAST:event_tableDrinkMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(add==true){
            if(check()){
                addDrink();
            }
        }
        else{
            if(change==true)
                editDrink();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tfGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfGiaKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        
        if(tfGia.getText().equals("")){
            return;
        }
        else
        if(tfGia.getText().matches("\\D+")){
            tfGia.setText(cutChar(tfGia.getText()));
        }
        else{
            tfGia.setText(formatter.format(convertedToNumbers(cutChar(tfGia.getText()))));
        }
    }//GEN-LAST:event_tfGiaKeyReleased

    private void tfSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSoLuongKeyReleased
        checkKyTu(this.tfSoLuong.getText());
        tfSoLuong.setText(cutChar(tfSoLuong.getText()));
    }//GEN-LAST:event_tfSoLuongKeyReleased

    private void tfSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSoLuongActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser filechose = new JFileChooser();
        filechose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int  returnvalue  = filechose.showOpenDialog(this);
        if(returnvalue == JFileChooser.APPROVE_OPTION);
        {
            File file = filechose.getSelectedFile();
            if(file == null) {
                JOptionPane.showMessageDialog(null,"Bạn phải chọn đường dẫn");
            }
            else duongdan = file.getAbsolutePath();
            BufferedImage b;
            try{
                b= ImageIO.read(file);
                jLabel1.setIcon(new ImageIcon(b));
            }catch(Exception e){}
            lblanh.setText(".");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tfFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFindActionPerformed

    private void tfFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFindKeyReleased
         String sql = "SELECT * FROM QLNuoc where maNuoc like N'%"+tfFind.getText()+"%' or tenNuoc like N'%"+tfFind.getText()+"%' or loaiNuoc like N'%"+tfFind.getText()+"%'";
        loadData(sql);
        //tfFind.setText("");
        //Disabled();
        //reset();
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
            java.util.logging.Logger.getLogger(qlnuocFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(qlnuocFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(qlnuocFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(qlnuocFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail= new Detail();
                new qlnuocFr(detail).setVisible(true);
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
    private javax.swing.JComboBox<String> cbLoaiNuoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbGia;
    private javax.swing.JLabel lbLoai;
    private javax.swing.JLabel lbMatu;
    private javax.swing.JLabel lbQLTU;
    private javax.swing.JLabel lbTen;
    private javax.swing.JLabel lbThongtin;
    private javax.swing.JLabel lblanh;
    private javax.swing.JTable tableDrink;
    private javax.swing.JTextField tfDonVi;
    private javax.swing.JTextField tfFind;
    private javax.swing.JTextField tfGia;
    private javax.swing.JTextField tfMatu;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tfTen;
    // End of variables declaration//GEN-END:variables
}
