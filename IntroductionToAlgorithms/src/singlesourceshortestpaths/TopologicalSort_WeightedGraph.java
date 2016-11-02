package singlesourceshortestpaths;

import java.util.ArrayList;
import java.util.List;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Weighted_Adjacent_List;

/**
 * �˴�������������ͨ��DFS��f[]�������С�
 * ��һ��ʵ�ַ����ǲ����������Ϊ0�ĵ�
 * @author xiazdong
 *
 */
public class TopologicalSort_WeightedGraph {
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int size;
	private int d[];
	private int f[];
	private int time;	
	private Weighted_Adjacent_List G;			//�ڽӱ�
	private List<String> resultList;	//�洢���������ֵ������
	public TopologicalSort_WeightedGraph(Weighted_Adjacent_List G){
		this.G = G;
		size = G.getSize();
		color = new int[size];
		d = new int[size];
		f = new int[size];
		time = 0;
		resultList = new ArrayList<String>();
		for(int i=0;i<color.length;i++)
			color[i] = WHITE;
	}
	
	public List<String> getResultList() {
		return resultList;
	}

	public String[] TopologicalSort(){
		DFS();
		return resultList.toArray(new String[0]);
	}
	public void DFS(){
		for(int i=0;i<size;i++){
			if(color[i]==WHITE){
				DFS_VISIT(i);
			}
		}
	}
	private void DFS_VISIT(int i) {
		color[i] = GRAY;
		time++;
		d[i]=time;
		for(int j=0;j<G.getListByVertexIndex(i).size();j++){
			String value = G.getListByVertexIndex(i).get(j).end;
			int index = G.getVertexIndex(value);
			if(color[index]==WHITE){
				DFS_VISIT(index);
			}
		}
		time++;
		f[i] = time;
		resultList.add(0, G.getVertexValue(i));	//��f[i]ֵ���뵽���е�ͷ��
		color[i] = BLACK;
	}
	
	public void print(){
		for(String e:resultList){
			int i=G.getVertexIndex(e);
			System.out.println(e+"("+d[i]+"/"+f[i]+")");
		}
	}
	
	public static void main(String[] args) throws Exception {
		//��Ȩͼ����������ͬ�Ǵ�Ȩͼһ��
		Weighted_Adjacent_List adjacent_list  = GraphFactory.getWeightedAdjacentListInstance("graph\\dag_shortest.txt");
	
		TopologicalSort_WeightedGraph alg = new TopologicalSort_WeightedGraph(adjacent_list);
		alg.TopologicalSort();
//		for(String e:result) System.out.print(e+" ");
		alg.print();
	}
}
