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

public class WinMoneyList extends JDialog {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMoneyList dialog = new WinMoneyList();
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
	public WinMoneyList() {
		setTitle("\uD68C\uBE44\uC785\uAE08");
		setBounds(100, 100, 469, 366);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 425, 296);
		getContentPane().add(scrollPane);
		
		String columnNames[] = {"번호","이름","금액","입금날짜","비고"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		getList();
	}
	
	public void getList() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"1234");
			Statement stmt = con.createStatement();			
			String sql = "SELECT a.name,m.* FROM moneyTBL m LEFT JOIN addresstbl a ON m.jumin = a.jumin"; 
			ResultSet rs = stmt.executeQuery(sql);
			int cnt=0;
			String record[] = new String[5];
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			dtm.setRowCount(0);
			while(rs.next()) {
				
				record[0] = Integer.toString(++cnt);
				record[1] = rs.getString("name");
				record[3] = rs.getString("mdate");
				record[2] = rs.getString("money");
				record[4] = rs.getString("etc");
				dtm.addRow(record);
				
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
}
