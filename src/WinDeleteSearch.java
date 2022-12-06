import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuItem;

public class WinDeleteSearch extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField tfName;
	private JTextField tfMobile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinDeleteSearch dialog = new WinDeleteSearch();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinDeleteSearch() {
		setBounds(100, 100, 505, 416);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				JLabel lblNewLabel = new JLabel("\uC774\uB984 : ");
				panel.add(lblNewLabel);
			}
			{
				tfName = new JTextField();
				panel.add(tfName);
				tfName.setColumns(10);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("\uC804\uD654\uBC88\uD638 : ");
				panel.add(lblNewLabel_1);
			}
			{
				tfMobile = new JTextField();
				panel.add(tfMobile);
				tfMobile.setColumns(10);
			}
			{
				JButton btnNewButton = new JButton("\uD0D0\uC0C9");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						showResult();
					}
				});
				panel.add(btnNewButton);
			}
			{
				JButton btnNewButton = new JButton("\uCDE8\uC18C");
				panel.add(btnNewButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				String columnNames[]= {"번호","주민번호","이름","전화번호","주소","졸업년도","회사명"};
				DefaultTableModel dtm = new DefaultTableModel(columnNames,0);
				
				table = new JTable(dtm);
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int row = table.getSelectedRow();
						String jumin = (String)table.getValueAt(row, 1);
						
						WinDeleteMember winup = new WinDeleteMember(jumin);
						setVisible(false);
						winup.setModal(true);
						winup.setVisible(true);
					}

					
				});
				scrollPane.setViewportView(table);
			}
		}
		
		
		
	}
	
	protected void showResult() { // 검색 결과를 테이블에 출력		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/sqlDB",
							"root",
							"1234");
			Statement stmt = con.createStatement();			
			String sql = "select * from addressTBL where name like '%"+tfName.getText()+"%'"
					+ "AND mobile like '%"+tfMobile.getText()+"%'"; 
			ResultSet rs = stmt.executeQuery(sql);
			int cnt=0;
			String record[] = new String[7];
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			dtm.setRowCount(0);
			while(rs.next()) {
				record[0] = Integer.toString(++cnt);
				for(int i=1; i < record.length; i++)
					record[i] = rs.getString(i);
				dtm.addRow(record);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}	
	}
	
	

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
