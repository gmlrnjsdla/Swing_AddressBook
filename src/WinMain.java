import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinMain extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WinMain dialog = new WinMain();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinMain() {
		setTitle("ICI ��âȸ");
		setBounds(100, 100, 272, 322);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 5, 5));
		{
			JButton btnInsertMember = new JButton("");
			btnInsertMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinInsertMember winInsertMember = 
							new WinInsertMember();
					winInsertMember.setModal(true);
					winInsertMember.setVisible(true);
				}
			});
			btnInsertMember.setToolTipText("ȸ�� ����");
			btnInsertMember.setIcon(new ImageIcon("images/addUser.png"));
			contentPanel.add(btnInsertMember);
		}
		{
			JButton btnUpdateMember = new JButton("");
			btnUpdateMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinSearch winsearch = new WinSearch();
					setVisible(false);
					winsearch.setModal(true);
					winsearch.setVisible(true);
				}
			});
			btnUpdateMember.setToolTipText("ȸ������ ����");
			btnUpdateMember.setIcon(new ImageIcon("images/update.png"));
			contentPanel.add(btnUpdateMember);
		}
		{
			JButton btnDeleteMember = new JButton("");
			btnDeleteMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinDeleteSearch windel = new WinDeleteSearch();
					setVisible(false);
					windel.setModal(true);
					windel.setVisible(true);
				}
			});
			btnDeleteMember.setToolTipText("ȸ�� Ż��");
			btnDeleteMember.setIcon(new ImageIcon("images/delete.png"));
			contentPanel.add(btnDeleteMember);
		}
		{
			JButton btnSearchMember = new JButton("");
			btnSearchMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinSearchAll winall = new WinSearchAll();
					setVisible(false);
					winall.setModal(true);
					winall.setVisible(true);
				}
			});
			btnSearchMember.setToolTipText("ȸ�� �˻�");
			btnSearchMember.setIcon(new ImageIcon("images/search.png"));
			contentPanel.add(btnSearchMember);
		}
		{
			JButton btnmoney = new JButton("");
			btnmoney.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinCollectMoney winmon = new WinCollectMoney();
					setVisible(false);
					winmon.setModal(true);
					winmon.setVisible(true);
				}
			});
			btnmoney.setIcon(new ImageIcon("images/money.png"));
			contentPanel.add(btnmoney);
		}
		{
			JButton btnallmoney = new JButton("");
			btnallmoney.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinMoneyList winlist = new WinMoneyList();
					setVisible(false);
					winlist.setModal(true);
					winlist.setVisible(true);
				}
			});
			btnallmoney.setIcon(new ImageIcon("images/allmoney.png"));
			contentPanel.add(btnallmoney);
		}
	}

}
