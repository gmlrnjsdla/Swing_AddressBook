import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WinDeleteMember extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfName;
	private JTextField tfJumin1;
	private JPasswordField tfJumin2;
	private JTextField tfMobile;
	private JTextField tfAddress;
	private JTextField tfCompany;
	private JComboBox cbGYear;
	private String picPath; // 그림 경로
	private JLabel lblPic;
	
	

	/**
	 * Create the dialog.
	 * @param jumin 
	 */
	public WinDeleteMember(String jumin) {

		setTitle("동문 주소록");
		setBounds(100, 100, 513, 321);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(12, 37, 57, 15);
		contentPanel.add(lblName);
		
		tfName = new JTextField();
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					tfJumin1.requestFocus();
				}
			}
		});
		tfName.setBounds(81, 34, 116, 21);
		contentPanel.add(tfName);
		tfName.setColumns(10);
		
		tfJumin1 = new JTextField();
		tfJumin1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(tfJumin1.getText().length()==5)
					tfJumin2.requestFocus();				
			}
		});
		tfJumin1.setColumns(10);
		tfJumin1.setBounds(81, 73, 79, 21);
		contentPanel.add(tfJumin1);
		
		JLabel lblJumin = new JLabel("주민번호:");
		lblJumin.setBounds(12, 76, 57, 15);
		contentPanel.add(lblJumin);
		
		tfJumin2 = new JPasswordField();
		tfJumin2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(String.valueOf(tfJumin2.getPassword()).length()==6)
					tfMobile.requestFocus();
			}
		});
		tfJumin2.setBounds(185, 73, 101, 21);
		contentPanel.add(tfJumin2);
		
		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.setBounds(167, 76, 6, 15);
		contentPanel.add(lblNewLabel);
		
		JLabel lblMobile = new JLabel("전화번호:");
		lblMobile.setBounds(12, 112, 57, 15);
		contentPanel.add(lblMobile);
		
		tfMobile = new JTextField();
		tfMobile.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					String mobileNumber = tfMobile.getText();				
					if(mobileNumber.length() == 8) {
						tfMobile.setText("010" + mobileNumber);
						tfCompany.requestFocus();					
					}else if(mobileNumber.substring(0,3).equals("010") &&
							mobileNumber.length() == 11) {						
						tfCompany.requestFocus();					
					}
					else {
						tfMobile.requestFocus();
						tfMobile.setSelectionStart(0);
						tfMobile.setSelectionEnd(mobileNumber.length());
					}
				}
			}
		});
		tfMobile.setColumns(10);
		tfMobile.setBounds(81, 109, 205, 21);
		contentPanel.add(tfMobile);
		
		JLabel lblAddress = new JLabel("주소:");
		lblAddress.setBounds(12, 195, 57, 15);
		contentPanel.add(lblAddress);
		
		tfAddress = new JTextField();
		tfAddress.setColumns(10);
		tfAddress.setBounds(81, 192, 404, 21);
		contentPanel.add(tfAddress);
		
		JButton btnDoro = new JButton("도로명 검색...");
		btnDoro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinDoroSearch winDoroSearch = new WinDoroSearch();
				winDoroSearch.setModal(true);
				winDoroSearch.setVisible(true);
				String sAddr = winDoroSearch.getAddress();
				tfAddress.setText(sAddr);
				tfAddress.requestFocus();
			}
		});
		btnDoro.setBounds(377, 216, 108, 23);
		contentPanel.add(btnDoro);
		
		lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = 
							new FileNameExtensionFilter("그림파일","jpg","gif","png");
					chooser.setFileFilter(filter);
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int ret = chooser.showOpenDialog(null);
					if(ret==JFileChooser.APPROVE_OPTION) {
						picPath = chooser.getSelectedFile().getPath();
						ImageIcon image  = new ImageIcon(picPath);
						Image picImage = image.getImage();
						picImage = picImage.getScaledInstance(100, 120, Image.SCALE_SMOOTH);
						ImageIcon image2 = new ImageIcon(picImage);
						lblPic.setIcon(image2);
						System.out.println(picPath);
					}
				}
			}
		});
		
		lblPic.setToolTipText("더블클릭하여 사진 선택");
		lblPic.setBackground(new Color(255, 255, 0));
		lblPic.setOpaque(true);
		lblPic.setBounds(310, 10, 100, 120);
		contentPanel.add(lblPic);
		
		JLabel lblCompany = new JLabel("직장:");
		lblCompany.setBounds(12, 152, 57, 15);
		contentPanel.add(lblCompany);
		
		tfCompany = new JTextField();
		tfCompany.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					cbGYear.requestFocus();
				}
			}
		});
		tfCompany.setColumns(10);
		tfCompany.setBounds(81, 149, 79, 21);
		contentPanel.add(tfCompany);
		
		JLabel lblGYear = new JLabel("졸업년도:");
		lblGYear.setBounds(185, 152, 57, 15);
		contentPanel.add(lblGYear);
		
		cbGYear = new JComboBox();
		cbGYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfAddress.requestFocus();
			}
		});
		cbGYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					tfAddress.requestFocus();
				}
			}
		});
		cbGYear.setBounds(267, 148, 85, 23);
		contentPanel.add(cbGYear);
		
		Calendar calendar = Calendar.getInstance();
		int curYear = calendar.get(Calendar.YEAR);
		for(int year = curYear-100; year<=curYear; year++)
			cbGYear.addItem(year);
		cbGYear.setSelectedItem(curYear);
		
		JButton btnInsertMember = new JButton("회원삭제");
		btnInsertMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteMember(jumin);
			}
		});
		btnInsertMember.setBounds(81, 249, 97, 23);
		contentPanel.add(btnInsertMember);
		
		JButton btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(202, 249, 97, 23);
		contentPanel.add(btnCancel);
		
		getMinfo(jumin);
	}

	protected void DeleteMember(String jumin) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"1234");
			Statement stmt = con.createStatement();			
			String sql = "DELETE FROM addresstbl WHERE jumin='"+jumin+"'";
			int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?");
			if(result == JOptionPane.YES_OPTION) {
				stmt.executeUpdate(sql);
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				setVisible(false);
				WinDeleteSearch windel = new WinDeleteSearch();
				windel.setModal(true);
				windel.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(null, "취소되었습니다.");
				setVisible(false);
				WinDeleteSearch windel = new WinDeleteSearch();
				windel.setModal(true);
				windel.setVisible(true);
			}
			
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}			
	}
	
	protected void getMinfo(String jumin) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"1234");
			Statement stmt = con.createStatement();		
			
			String sql = "SELECT * FROM addresstbl WHERE jumin='"+jumin+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				String name = rs.getString("name");
				String mobile = rs.getString("mobile");
				String address = rs.getString("address");
				String company = rs.getString("company");
				String gyear = rs.getString("gyear");
				
				tfName.setText(name);
				tfJumin1.setText(jumin.substring(0,6));
				tfJumin2.setText(jumin.substring(6,13));
				tfMobile.setText(mobile);
				tfAddress.setText(address);
				tfCompany.setText(company);
				cbGYear.setSelectedItem(gyear);
				
				picPath = rs.getString("pic");
				ImageIcon image  = new ImageIcon(picPath);
				Image picImage = image.getImage();
				picImage = picImage.getScaledInstance(100, 120, Image.SCALE_SMOOTH);
				ImageIcon image2 = new ImageIcon(picImage);
				lblPic.setIcon(image2);
				
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}			
	}
	
}
