package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.List;

public class StrongConnectedComponents_Algorithm {
	private Adjacent_List G;
	private List<String> flist;	//�Ӵ�С��fֵ������,����ֵ������������õ�
	private Adjacent_List GT;//G��ת��ͼ
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int size;
	private int weight[];	//�洢ǿ��ͨ��֧�ĸ����ڵ��ֵ��ͬһ��ǿ��ͨ��֧�Ľڵ��ֵ��ͬ������ֵ�ķ�Χ[1,ǿ��ͨ��֧����]
	private int cc;			//ǿ��ͨ��֧�ĸ���
	public StrongConnectedComponents_Algorithm(Adjacent_List G){
		this.G = G;
		this.size = G.getSize();
		color = new int[size];
		weight = new int[size];
		for(int i=0;i<size;i++){
			weight[i] = 0;
			color[i] = WHITE;
		}
		cc = 0;
	}
	/**
	 * ���ǿ��ͨ��֧����
	 * @return
	 */
	public int getComponentSize(){
		return cc;
	}
	/**
	 * �������ǿ��ͨ��֧��ͬһ��ǿ��ͨ��֧�Ľڵ�һ�����
	 */
	public void printAllComponents(){
		List<List<String>> scclist = new ArrayList<List<String>>();
		for(int i=0;i<cc;i++){
			scclist.add(new ArrayList<String>());
		}
		for(int i=0;i<size;i++){
//			int index = GT.getVertexIndex(flist.get(i));
			scclist.get(weight[i]-1).add(GT.getVertexValue(i));
		}
		for(List<String> list:scclist){
			for(String str:list){
				System.out.print(str+" ");
			}
			System.out.println();
		}
	}
	
	public Adjacent_List getG(){
		return G;
	}
	
	public Adjacent_List getGT(){
		return GT;
	}
	
	/**
	 * ִ��ǿ��ͨ��֧�㷨
	 * @throws Exception
	 */
	public void SCC() throws Exception{
		TopologicalSort_Algorithm topo_alg = new TopologicalSort_Algorithm(G);
		topo_alg.TopologicalSort();	//����������fֵ�Ӵ�С����
		flist = topo_alg.getResultList();
//		topo_alg.print();
		Transposition_AdjacentList_Algorithm trans_alg = new Transposition_AdjacentList_Algorithm(G);
		GT = trans_alg.transposeG();	//ת��ͼ
//		GT.printAllEdges();
		DFS(GT);	//��ת��ͼ����DFS��������Ȩֵ
	}
	
	private void DFS(Adjacent_List g){
		for(int i=0;i<flist.size();i++){
			//GT.getVertexValue(i)������G.getVertexValue(i)���Եڶ���DFSΪ��׼Ѱ�ҽڵ�������
			int index = GT.getVertexIndex(flist.get(i));
			if(color[index]==WHITE){
				cc++;
				DFS_VISIT(g,index,cc);
			}
		}
	}
	private void DFS_VISIT(Adjacent_List g,int i,int cc) {
		color[i] = GRAY;
		weight[i] = cc;
		List<String> temp=g.getListByVertexIndex(i);
		for(int j=0;j<temp.size();j++){
			String value = temp.get(j);
			int index = g.getVertexIndex(value);
			if(color[index]==WHITE){
				DFS_VISIT(g,index,cc);
			}
		}
		color[i] = BLACK;
	}
	/**
	 * ���ÿ���ڵ��Ȩֵ,��ͬȨֵ�ĵ�Ϊͬһ��ǿ��ͨ��֧
	 * @return
	 */
	public int[] getWeight(){
		return weight;
	}
	public void printVertexAndWeight(){
		for(int i=0;i<weight.length;i++){
			//GT.getVertexValue(i)������G.getVertexValue(i)���Եڶ���DFSΪ��׼Ѱ�ҽڵ�������
			System.out.println(GT.getVertexValue(i)+":"+weight[i]);
		}
	}
	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list = GraphFactory.getAdjacentListInstance("graph\\scc_input.txt");
		
		StrongConnectedComponents_Algorithm alg = new StrongConnectedComponents_Algorithm(adjacent_list);
		alg.SCC();
		System.out.println("ǿ��ͨ��֧������"+alg.getComponentSize());
		alg.printAllComponents();
//		int[] weight = alg.getWeight();
		alg.printVertexAndWeight();
//		for(int i=0;i<weight.length;i++){
//			System.out.println(adjacent_list.getVertexValue(i)+":"+weight[i]);
//		}
	}
}
