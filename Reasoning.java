//���������
import java.util.*;

/*����ʵ�����������*/
public class Reasoning {
	//�û�������������ݷ���һ�����鵱�У���������������������ַ���ƥ��
	private ArrayList<String> userDemandList = new ArrayList<String>();

	private ArrayList<ArrayList> infCause;

	private ArrayList<String> infResult;

	private Rule rule;

	// �û�ϣ���õ����,���������е�ĳ������
	String[] str = new String[] { "��������³��", "��������槼�", "����������", "�������ǲ��ļ�",
			"�����������", "�������ǰ�����Լ", "�������ǳ�ҧ��","��������������" ,"�������ǰ�����","��������������","����������˽�","��������̹��2"};

	List<String> strList;
	
//���캯��
	public Reasoning(ArrayList<String> userDemandList) {
		this.userDemandList = userDemandList;
		strList = Arrays.asList(str);

		infCause = new ArrayList<ArrayList>();

		infResult = new ArrayList<String>();
	}
/*�÷���ͨ���Ƚ��û�����͹����б�����������,�ж��û������Ƿ������ĳ���������������,
 * ������,�ͱȽ��������������,��ͣ��ѭ��,ֱ�����һ������.
 * ����,�ж���������Ľ����Ƿ�Ϊ���ս���,����,�򷵻ؽ���!����,�жϸ�������Ľ����Ƿ�Ӧ�������û������б���.
 * */
	public String findCause(ArrayList<Rule> rulesList) {
		/*�ж��û������Ƿ�Ϊ��,�Լ��Ƿ�ì��*/
		if (userDemandList != null && isContradict(userDemandList)) {
			for (int i = 0; i < rulesList.size(); i++) {

				List<String> causeList = new ArrayList<String>(
						rulesList.get(i).cause.size());

				for (String tempString : rulesList.get(i).cause) {
					causeList.add(tempString.trim());
				}
			/*	trim() �����Ƴ��ַ�������Ŀհ��ַ�������Ԥ�����ַ�.
			  �ù��ܳ�ȥ�ַ�����ͷ��ĩβ�Ŀո�������ַ���
			  ����ִ�гɹ�ʱ����ɾ����string�ַ����ײ���β���ո���ַ�����
			  ����׼ȷ�õ���������
			 */
				String result = new String(rulesList.get(i).result.trim());
				if (userDemandList.containsAll(causeList))

					if (strList.contains(result)) {

						return result;
					}

					else {
						if (!userDemandList.contains(result)) {
							userDemandList.add(result);
							if (!isContradict(userDemandList))
								return null;
							i = 0;
						} else
							continue;

					}

			}
		}
		return null;

	}

	//�����ж��û������Ƿ����ì�ܵ�����
	public boolean isContradict(ArrayList<String> userDemandList) {
		if (userDemandList.contains("������ȥ��·")
				&& userDemandList.contains("������ȥ��·"))
			return false;
		if (userDemandList.contains("�����︨������") && userDemandList.contains("�����������"))
			return false;
		if (userDemandList.contains("�������Ǹ���")
				&& userDemandList.contains("������ȥ��·"))
			return false;

		return true;
	}

	//�÷�����findcaseһ��
	public String find(ArrayList<Rule> rulesList) {
		ArrayList tempList = new ArrayList();

		while(this.userDemandList != null) {
			//iterator�������������ӿ�ʵ�ֱ�������
			Iterator var3 = this.userDemandList.iterator();

			while(var3.hasNext()) {
				String str = (String)var3.next();
				tempList.add(str);
			}
			this.findcause(rulesList);
			if (!this.findcause(rulesList)) {
				if (this.userDemandList.size() == 1) {
					return (String)this.userDemandList.get(0);
				}
				break;
			}
		}

		return null;
	}

	/*ʵ�����ַ�����ƥ���ɾ�������������������м�����ɾ����*/
	private boolean findcause(ArrayList<Rule> rulesList) {
		boolean odd = false;
		for (int i = 0; i < rulesList.size(); i++) {

			ArrayList<String> causeList = new ArrayList<String>(rulesList
					.get(i).cause.size());

			for (String tempString : rulesList.get(i).cause) {
				causeList.add(tempString.trim());
			}

			String result = new String(rulesList.get(i).result.trim());

			if (userDemandList.containsAll(causeList)) {

				infCause.add(causeList);

				infResult.add(result);

				userDemandList.removeAll(causeList);
				odd = true;
				if (!userDemandList.contains(result)) {
					userDemandList.add(result);
				}
			}
		}
		return odd;
	}

	/*��������������ʵ�ֻ�õ�������*/
	public List<ArrayList> getInfCause() {
		return infCause;

	}

	public List<String> getInfResult() {
		return infResult;

	}

}
