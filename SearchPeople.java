/**
 * 
 */

import java.util.*;

//�����ǿ�����,�������������������������
public class SearchPeople {
	private Rule rule;

	private ProduceRule produceRule;

	private Reasoning reasoning;

	private ArrayList<Rule> rulesList;

	private List<ArrayList> sumList;

	private ArrayList<ArrayList<String>> conditList;

	private ArrayList<String> resultList;

	public SearchPeople(ArrayList<String> userDemandList) {
		rulesList = new ArrayList<Rule>();

		reasoning = new Reasoning(userDemandList);

		produceRule = new ProduceRule();

		List readFile = produceRule.readFile();

		sumList = readFile;

		if (sumList.size() == 2) {
			conditList = sumList.get(0);

			resultList = sumList.get(1);
			
			/*���ɹ���,��������rulesList�б���*/
			for (int i = 0; i < conditList.size(); i++) {
				ArrayList<String> tempList = new ArrayList<String>();

				for (String temp : conditList.get(i)) {
					tempList.add(temp);

				}
				String tempString = new String(resultList.get(i));

				rulesList.add(new Rule(tempList, tempString));
			}

		}

	}

	/*�����û�ѡ���ģʽ:�򵥻���ϸģʽ,���ò�ͬ��������,
	 * Ĭ��������(����Ϊ��),�ǵ�����ϸģʽ*/
	public String search(boolean odd) {
		if(odd){
		return reasoning.find(rulesList);             //��ϸ������
		}
		 return reasoning.findCause(rulesList);      //��������
		
	}

	/*��������������ʵ�ֻ�õ�������*/
	public List<ArrayList> getInfCause() {
		return reasoning.getInfCause();

	}

	public List<String> getInfResult() {
		return reasoning.getInfResult();

	}

}
