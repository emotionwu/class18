
import javax.swing.*;
import java.text.*;
import java.util.Hashtable;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
//�ӿڱ�ʾƥ������Ľ����������ɨ����
import java.util.regex.MatchResult;
//ͼ�λ�������������ð�
import java.awt.font.*;

/*������,���ɲ˵����͹�����,�����û������벢��ʾ���*/
public class MainFrame extends JFrame {

	private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int ICONWIDTH=30;
	
	private boolean modeOdd;

    AbstractAction saveAction;
    AbstractAction createKBAction;
    HelpAction helpAction;

	//private JFileChooser chooser;
	private JLabel displayName, displayIcon, displayLabel;
    private JTextArea input;

    //���
    private JTextArea depict;//���
    private JButton certain;
    private JPanel panel;
    private JRadioButtonMenuItem brief,detailedly;
    

	public MainFrame() {
		modeOdd=true;

		createKBAction=new CreateKBAction();
		helpAction = new HelpAction();

		createMenu();
		//Jframe����ֱ������������Ҫ����װ��
		Container cp = getContentPane();
		displayName = new JLabel("����������:");
		displayName.setBounds(50, 0, 200, 150);

		input = new JTextArea();
		input.setBounds(50, 120, 200, 250);
		input.setBackground(new Color(120, 246, 250));
		input.setLineWrap(true);

		certain = new JButton("ȷ��");
		certain.setBounds(100, 420, 80, 40);
		//��Ӧͼ�ν����û������ť
		certain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> userDemandList = new ArrayList<String>();
				String str = input.getText();
				Scanner s = new Scanner(str);
				while (s.hasNext()) {
					userDemandList.add(s.next());
				}
				SearchPeople searchPeople = new SearchPeople(userDemandList);
				String animalName = searchPeople.search(modeOdd);
				if (animalName != null) {
					depict.setText(animalName + "\n");
					depict.append("�����������:"+"\n");

					List<ArrayList> infCause = searchPeople.getInfCause();
					List<String> infResult = searchPeople.getInfResult();
                    int i=0;
					for (ArrayList<String> causeList:infCause) {
						//���ú�����������
						for (String cause : causeList)
							depict.append(cause + "\n");
						depict.append("�Ƴ�:");
						depict.append(infResult.get(i) + "\n");
						i++;
					}

					setIcon(animalName);

				} else {
					JOptionPane.showMessageDialog(MainFrame.this, "�����������Ч��Ϣ",
							"��Ϣ��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}

			}

		});

		displayLabel = new JLabel("������ʾ");
		displayLabel.setBounds(300, 0, 150, 150);

		// ������ҳ�,����ʾ�����ͼƬ
		displayIcon = new JLabel();
		displayIcon.setBounds(300, 120, 200, 150);
		displayIcon.setIcon(new ImageIcon(".\\image\\³��.gif"));

		depict = new JTextArea();
		depict.setEditable(false);
		depict.setLineWrap(true);
		depict.setBackground(new Color(139, 255, 118));

		JScrollPane pane = new JScrollPane(depict);
		pane.setBounds(300, 270, 200, 200);

		panel = new JPanel();
		panel.setLayout(null);
		panel.add(displayName);
		panel.add(input);
		panel.add(certain);
		panel.add(displayLabel);
		panel.add(displayIcon);
		panel.add(pane);
		cp.add(panel, BorderLayout.CENTER);
		cp.add(createToolBar(), BorderLayout.NORTH);

	}

	public void createMenu() {
		JMenu fileMenu = new JMenu("�˳�ģʽ");
		fileMenu.setMnemonic('F');
		JMenuItem newItem = new JMenuItem("New", 'N');
		newItem.setEnabled(false);
		fileMenu.add(newItem);

		fileMenu.addSeparator();

		JMenuItem save = new JMenuItem(saveAction);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		fileMenu.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		fileMenu.add(exit);
		
		JMenu editMenu=new JMenu("�༭");
		//������㱻�����ڴ˰�ť���ȴ����е�ĳ���ط�������˰�ť
		//��������ڰ�ť����ʱ������
		editMenu.setMnemonic('E');
		JMenuItem createKBItem=new JMenuItem(createKBAction);
		//��ݼ�
		createKBItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_MASK));
		editMenu.add(createKBItem);
		
		
		JMenu modeMenu=new JMenu("ģʽ");
		modeMenu.setMnemonic('M');
		JMenu modeStyle=new JMenu("ѡ��ģʽ");
		modeStyle.setMnemonic('S');
		brief=new JRadioButtonMenuItem(new ModeAction("��"));
		detailedly=new JRadioButtonMenuItem(new ModeAction("��ϸ"));
		detailedly.setSelected(true);
		ButtonGroup group=new ButtonGroup();
		group.add(brief);
		group.add(detailedly);
		modeStyle.add(brief);
		modeStyle.add(detailedly);
		modeMenu.add(modeStyle);
		
		JMenu helpMenu = new JMenu("����");
		helpMenu.setMnemonic('H');
		JMenuItem help = new JMenuItem(helpAction);
		help.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpMenu.add(help);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(modeMenu);
		menuBar.add(helpMenu);

	}

	//��ť��Ӧ
	public JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
	//	toolBar.add(openAction);
		toolBar.add(saveAction);
		toolBar.add(createKBAction);
		return toolBar;
	}
//����֪ʶ�ⰴť��Ӧ
	private class CreateKBAction extends AbstractAction{
		Icon icon;
		public CreateKBAction(){
			icon=getIcon(".\\image\\����֪ʶ��.gif");
			putValue(Action.NAME,"����֪ʶ��");
			putValue(Action.SMALL_ICON,icon);
			putValue(Action.SHORT_DESCRIPTION,"����֪ʶ��");
		}
		public void actionPerformed(ActionEvent e){
			new CreateKB(MainFrame.this);
		}
	}
	
	private class ModeAction extends AbstractAction{
		public ModeAction(String name){
			putValue(Action.NAME,name);
			
		}
		public void actionPerformed(ActionEvent e){
			if(e.getSource() instanceof JRadioButtonMenuItem){
				JRadioButtonMenuItem radioButton=(JRadioButtonMenuItem)e.getSource();
				if(radioButton==brief)
					modeOdd=false;
				else
					modeOdd=true;
			}
				
			
		}
	}

	//����һ�����֣���ʾ�����ʽ
	private class HelpAction extends AbstractAction {
		final Hashtable<TextAttribute, Object> map;

		AttributedString str;

		LineBreakMeasurer lineMeasurer;

		int paragraphStart, paragraphEnd;

		public HelpAction() {
			putValue(Action.NAME, "help");
			putValue(Action.SHORT_DESCRIPTION, "����");

			map = new Hashtable<TextAttribute, Object>();

			map.put(TextAttribute.FAMILY, "Serief");
			map.put(TextAttribute.SIZE, new Float(18.0));
			map.put(TextAttribute.FOREGROUND, Color.YELLOW);

			str = new AttributedString("�������ж�����ʱ,����ѭһ������:"+"\n"
					+ "�����������Ӧ����Ӣ����ĸ,����:IF,AND " 
					+ "������һ��������,Ҫ�س�ת������������һ���� "+"\n"
					+ "���������Ҫ��Rule.txt�ļ���������ͬ " +"\n"
					+ "�����ָ�ʽ:" + "������ȥ��· "+"\n"
					+ "�ȶ� " +"\n"
					+ "��Ŵ���"+"\n"
					+ " ���ܹ��ó���������³����Ƴ�����Ϸ����Ĳ���", map);

		}

		public void actionPerformed(ActionEvent e) {
			JFrame frame = new JFrame("help");

			frame.add(new JPanel() {
				public void paintComponent(Graphics g) {
					super.paintComponent(g);

					Graphics2D g2 = (Graphics2D) g;
					g2.setPaint(new GradientPaint(0, 0, new Color(150, 22, 0, 255),
							getWidth(), getHeight(), new Color(100, 100, 255)));
					
					g2.fillRect(0, 0, getWidth(), getHeight());
					if (lineMeasurer == null) {
						AttributedCharacterIterator paragraph = str
								.getIterator();
						
						paragraphStart = paragraph.getBeginIndex();
						paragraphEnd = paragraph.getEndIndex();
						FontRenderContext frc = g2.getFontRenderContext();
						lineMeasurer = new LineBreakMeasurer(paragraph, frc);
					}
					float breakWidth = (float) getWidth();
					float drawPosY = 50;
					lineMeasurer.setPosition(paragraphStart);
					while (lineMeasurer.getPosition() < paragraphEnd) {
						TextLayout layout = lineMeasurer.nextLayout(breakWidth);
						float drawPosX = layout.isLeftToRight() ? 0
								: breakWidth - layout.getAdvance();
						
						drawPosY += layout.getAscent();
						layout.draw(g2, drawPosX, drawPosY);
						drawPosY += layout.getDescent() + layout.getLeading();
					}

					g2.setColor(Color.WHITE);

				}

			});
			
			frame.setSize(MainFrame.this.getWidth(), MainFrame.this
							.getHeight());
			
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {

				}
			});
			
			frame.setVisible(true);
		}

	}
	
	private Icon getIcon(String address){
		ImageIcon icon=new ImageIcon(address);
		if(icon.getIconWidth()>ICONWIDTH){
			icon=new ImageIcon(icon.getImage().getScaledInstance(ICONWIDTH, -1, Image.SCALE_DEFAULT));
		}
		return icon;
	}

	private void setIcon(String string) {
		String patString = "(.+)��(.+)";
		List<String> list = parseLine(string, patString);
		String animalName = list.get(1).trim();
		Map<String, Icon> iconMap = loadIcon();
		Icon showIcon = iconMap.get(animalName);
		displayIcon.setIcon(showIcon);

	}

	private Map loadIcon() {
		Map<String, Icon> iconMap = new HashMap<String, Icon>();

		ImageIcon DajiIcon = new ImageIcon(".\\image\\槼�.gif");
		ImageIcon SunceIcon = new ImageIcon(".\\image\\���.gif");
		ImageIcon YaoIcon = new ImageIcon(".\\image\\��.gif");
		ImageIcon CaiwenjiIcon = new ImageIcon(".\\image\\���ļ�.jpg");
		ImageIcon BailiIcon = new ImageIcon(".\\image\\������Լ.gif");
		ImageIcon ChengyaojinIcon = new ImageIcon(".\\image\\��ҧ��.gif");
		ImageIcon MilaidiIcon = new ImageIcon(".\\image\\������.gif");
		ImageIcon AnqilaIcon = new ImageIcon(".\\image\\������.gif");
		ImageIcon LanlinwangIcon = new ImageIcon(".\\image\\������.gif");
		ImageIcon ZhubajieIcon = new ImageIcon(".\\image\\��˽�.gif");
		ImageIcon LubanIcon = new ImageIcon(".\\image\\³��.gif");


		iconMap.put(new String("槼�"), DajiIcon);
		iconMap.put(new String("���"), SunceIcon);
		iconMap.put(new String("��"), YaoIcon);
		iconMap.put(new String("���ļ�"), CaiwenjiIcon);
		iconMap.put(new String("������Լ"), BailiIcon);
		iconMap.put(new String("��ҧ��"), ChengyaojinIcon);
		iconMap.put(new String("������"), MilaidiIcon);
		iconMap.put(new String("������"), AnqilaIcon);
		iconMap.put(new String("������"), LanlinwangIcon);
		iconMap.put(new String("��˽�"), ZhubajieIcon);
		iconMap.put(new String("³��"), LubanIcon);



		return iconMap;
	}
//�ַ���������ƥ��
	private List parseLine(String str, String patString) {
		List<String> stringList = new ArrayList<String>();
		Scanner scanner = new Scanner(str);
		scanner.findInLine(patString);
		MatchResult result = scanner.match();
		for (int i = 1; i <= result.groupCount(); i++) {
			stringList.add(result.group(i));

		}
		return stringList;

	}

	public static void main(String[] args) {
		// TODO �Զ����ɷ������
		JFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);

	}

}
