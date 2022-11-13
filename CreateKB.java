
//����滭��
import java.awt.*;
import java.awt.event.*;

//ͼ�ο��ӻ���
import javax.swing.*;

//�ܵõ�������
import java.io.*;
//��ϵͳ�ļ�
import java.util.Scanner;

/*����ʵ�����û�����ӿڲ���ʾ�û�����,���ҽ������ض���ʽ���û��������Rule.txt�ļ���*/
//���֪ʶ�⵽ָ���ļ���
public class CreateKB {
	private static final int WIDTH = 500;

	private static final int HEIGHT = 600;

	private JDialog dialog;

	//��������࣬��������ؼ�
	private JPanel inPanel, showPanel, buttonPanel, panel;

	//�ı���ؼ����������
	private JTextField resultField;

	//�������������������
	private JTextArea causeArea, showArea;

	//��ť
	private JButton certain, exit;

	public CreateKB(JFrame owner) {
		dialog = new JDialog(owner, "����֪ʶ��");
		//���ֹ�����
		dialog.setLayout(null);

		//���ý��۵ı߽�ʹ�С
		resultField = new JTextField();
		resultField.setPreferredSize(new Dimension(210, 60));
		resultField.setBounds(0, 420, 210, 60);

		//��ʾ��ǩ
		JLabel fieldLabel = new JLabel("���������");
		fieldLabel.setLabelFor(resultField);
		fieldLabel.setBounds(0, 360, 210, 60);

		//����������
		causeArea = new JTextArea();
		causeArea.setPreferredSize(new Dimension(210, 300));
		//�ɻ���
		causeArea.setLineWrap(true);
		causeArea.setBounds(0, 60, 210, 300);

		//������ʾ��ǩ
		JLabel areaLabel = new JLabel("����������");
		areaLabel.setLabelFor(causeArea);
		areaLabel.setBounds(0, 0, 210, 60);

		showArea = new JTextArea();
		//�ؼ����ɱ��༭
		showArea.setEditable(false);
		showArea.setLineWrap(true);
		showArea.setPreferredSize(new Dimension(210, 420));
		showArea.setBounds(290, 60, 210, 420);

		JLabel showAreaLabel = new JLabel("�����ʾ");
		showAreaLabel.setLabelFor(showArea);
		showAreaLabel.setBounds(290, 0, 210, 60);

		certain = new JButton(new InputAction());
		certain.setBounds(210, 210, 80, 60);
		exit = new JButton(new ExitAction());
		exit.setBounds(210, 510, 80, 60);

		dialog.add(resultField);
		dialog.add(fieldLabel);
		dialog.add(causeArea);
		dialog.add(areaLabel);
		dialog.add(showArea);
		dialog.add(showAreaLabel);
		dialog.add(certain);
		dialog.add(exit);
		dialog.setSize(WIDTH, HEIGHT);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	}

	//�滭��swing��io��ĸ���
	private class InputAction extends AbstractAction {
		ImageIcon icon;

		public InputAction() {
			icon = new ImageIcon(".\\image\\input.gif");
			if (icon.getIconWidth() < 80)
				//ͼƬ����
				icon = new ImageIcon(icon.getImage().getScaledInstance(20, -1,
						Image.SCALE_DEFAULT));
			putValue(Action.SMALL_ICON, icon);

		}

		public void actionPerformed(ActionEvent e) {

			//���������ͽ����
			String str = causeArea.getText();
			String s = resultField.getText();
			if (s.length()==0||str.length()==0) {
				JOptionPane.showConfirmDialog(null, "���������������������", "��Ϣ��ʾ",
						JOptionPane.INFORMATION_MESSAGE);

			}
			else{
			showArea.setText("" + "IF" + " " + str + "\n" + "THEN" + " " + s);
			Scanner scanner=new Scanner(str);
			String tempString=new String();
			while(scanner.hasNextLine()){
				tempString +=scanner.nextLine();
				if(scanner.hasNextLine())
					tempString +=" "+"AND"+" ";
			}
			String string=new String();
			string="IF" + " " + tempString +" "+ "THEN" + " " + s;
			File file=new File(".\\Rule.txt");
			 try{
			 	    //����
			    	 BufferedWriter out=new BufferedWriter(new FileWriter(file,true));
				     out.write(string);
				     out.newLine();
				     out.close();

					 //��׽���������쳣
			 }catch(IOException ioe){
				ioe.printStackTrace();
			}
			
			}

		}
		
	}

	private class ExitAction extends AbstractAction {
		//�˳�
		public ExitAction() {
			putValue(Action.NAME, "EXIT");
		}

		public void actionPerformed(ActionEvent e) {
			if (dialog != null)
				dialog.dispose();
		}

	}

}
