import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinCollectMoney extends JDialog {
	private JTextField tfMoney;
	private JTextField tfEtc;
	private JTextField tfJumin;
	private JTable table;
	private JLabel lbltotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinCollectMoney dialog = new WinCollectMoney();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinCollectMoney() {
		setTitle("\uD68C\uBE44\uC785\uAE08");
		setBounds(100, 100, 469, 519);
		getContentPane().setLayout(null);
		
		JLabel lbMoney = new JLabel("\uAE08\uC561 : ");
		lbMoney.setBounds(38, 39, 57, 15);
		getContentPane().add(lbMoney);
		
		tfMoney = new JTextField();
		tfMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tfMoney.setSelectionStart(0);
				tfMoney.setSelectionEnd(tfMoney.getText().length());
			}
		});
		tfMoney.setText("0");
		tfMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMoney.setBounds(87, 36, 87, 21);
		getContentPane().add(tfMoney);
		tfMoney.setColumns(10);
		
		JLabel lbMoney_1 = new JLabel("\uBE44\uACE0 : ");
		lbMoney_1.setBounds(38, 71, 57, 15);
		getContentPane().add(lbMoney_1);
		
		tfEtc = new JTextField();
		tfEtc.setHorizontalAlignment(SwingConstants.LEFT);
		tfEtc.setColumns(10);
		tfEtc.setBounds(87, 68, 220, 21);
		getContentPane().add(tfEtc);
		
		JLabel lbMoney_2 = new JLabel("\uC8FC\uBBFC\uBC88\uD638 : ");
		lbMoney_2.setBounds(12, 99, 83, 15);
		getContentPane().add(lbMoney_2);
		
		tfJumin = new JTextField();
		tfJumin.setHorizontalAlignment(SwingConstants.RIGHT);
		tfJumin.setColumns(10);
		tfJumin.setBounds(87, 96, 153, 21);
		getContentPane().add(tfJumin);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 164, 425, 306);
		getContentPane().add(scrollPane);
		
		String columnNames[] = {"번호","입금날짜","금액","비고"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		JButton btnInput = new JButton("\uC785\uAE08");
		btnInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = 
							DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/sqlDB",
									"root",
									"1234");
					Statement stmt = con.createStatement();			
					String sql = "INSERT INTO moneytbl(jumin,money,etc) "
							+ "VALUES('"+tfJumin.getText()+"','"+tfMoney.getText()+"','"+tfEtc.getText()+"')"; 
					stmt.executeUpdate(sql);
					
					
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnInput.setBounds(218, 127, 74, 22);
		getContentPane().add(btnInput);
		
		JButton btnSearch = new JButton("\uC870\uD68C");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String jumin = tfJumin.getText();
					int total = 0;
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = 
							DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/sqlDB",
									"root",
									"1234");
					Statement stmt = con.createStatement();			
					String sql = "select * from moneyTBL WHERE jumin='"+jumin+"'"; 
					ResultSet rs = stmt.executeQuery(sql);
					int cnt=0;
					String record[] = new String[4];
					DefaultTableModel dtm = (DefaultTableModel)table.getModel();
					dtm.setRowCount(0);
					while(rs.next()) {
						record[0] = Integer.toString(++cnt);
						record[1] = rs.getString("mdate");
						record[2] = rs.getString("money");
						record[3] = rs.getString("etc");
						dtm.addRow(record);
						total = total + rs.getInt("money");
					}
					lbltotal.setText("회비 : "+total);
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(132, 127, 74, 22);
		getContentPane().add(btnSearch);
		
		lbltotal = new JLabel("\uCD1D \uD68C\uBE44 : ");
		lbltotal.setHorizontalAlignment(SwingConstants.LEFT);
		lbltotal.setFont(new Font("굴림", Font.BOLD, 17));
		lbltotal.setBounds(234, 25, 203, 29);
		getContentPane().add(lbltotal);

	}
}
