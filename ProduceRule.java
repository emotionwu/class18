

import java.util.*;
import java.io.*;

//�ַ���ƥ���
//Ĭ��������ʽƥ��
//����pattern matcher������ pattern��������ʽ�������ı���ģʽ�� matcher������pattern������ƥ��ģʽ���ַ�������ƥ��
import java.util.regex.*;

//����ʵ���˴�Rule.txt�ļ��ж�ȡ����
public class ProduceRule {
	/*List��һ���ӿڣ���ArrayList��List�ӿڵ�һ��ʵ���ࡣ
	ArrayList��̳в�ʵ����List�ӿڡ�
	*list����ʹ�ø�Ϊ�㷺���ں���ʹ��������ʱ���Ϊ����
	 */
	private ArrayList<String> resultList;

	private ArrayList<ArrayList> conditList;

	private List<ArrayList> sumList;  //��ͬ��������ͽ��

	Scanner s;

	public ProduceRule() {
		//���ֻ��һ�� ���������ַ����洢
		resultList = new ArrayList<String>();                 //���б����ڷ��ù���Ľ��۲���
		conditList = new ArrayList<ArrayList>();              //���б����ڷ��ù������������
		sumList = new ArrayList<ArrayList>();                 //���б���������resultList��conditList
	}

	//ͨ��Scanner��ʵ���˶�ȡ�ض���ʽ���ļ�
	public List readFile() {
		String fileName = "Rule.txt";
		File file = new File(fileName);
		try {
			s = new Scanner(new FileInputStream(file));
			String wholeExp = "^IF\\s*(.+)\\s*THEN\\s*(.+)";
			String partExp = "\\s*AND\\s*";

			//'^'��'$'�ֱ�ƥ��һ�еĿ�ʼ�ͽ�������ȡ�������ͽ���
			Pattern wholePat = Pattern.compile(wholeExp, Pattern.MULTILINE);
			Pattern partPat = Pattern.compile(partExp, Pattern.MULTILINE);

			//����һ��һ�е�ɨ��
			//�����ɨ�����������л�����һ�У���java.util.Scanner���hasNextLine()����������true��
			while (s.hasNextLine()) {
				s.nextLine();
				String line = s.findInLine(wholePat);
				if (line != null) {
					MatchResult result = s.match();
					parseLine(result.group(1), partPat);
					resultList.add(result.group(2));
				} else {
					System.out.println("errror format");
				}

			}
			sumList.add(conditList);
			sumList.add(resultList);
			return sumList;

			//��׽û�о���δ֪���ļ�
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}

		return null;

	}

	//ParseLine ������֪ͨ Document ������Ϊ�������ݸ������ַ������з�����
	private void parseLine(String str, Pattern pat) {
		Scanner scanner = new Scanner(str).useDelimiter(pat);
		ArrayList<String> cells = new ArrayList<String>();
		while (scanner.hasNext()) {
			cells.add(scanner.next());
		}
		conditList.add(cells);
	}

}
