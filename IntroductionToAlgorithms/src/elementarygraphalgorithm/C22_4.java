package elementarygraphalgorithm;

import java.util.List;

/*
 * 1.��ͼתΪDAG
 * 2.������������˳����м���
 */
public class C22_4 {
	//�����⣡
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private static int color[];
	private static int L[];
	private static Adjacent_List G;
	private static Adjacent_List component_graph;
	private static int minu[];	//ÿ��ǿ��ͨ��֧��min
	private static int min[];	//���ս��
	public static void main(String[] args) throws Exception {
		
		Adjacent_List G = GraphFactory.getAdjacentListInstance("graph\\dfs_input.txt");
		C22_4.G = G;
		L = new int[G.getSize()];
		for(int i=0;i<G.getSize();i++){
			L[i] = i+1;
		}
		int result[] = getMinU();
		for(int i=0;i<result.length;i++)
			System.out.println(G.getVertexValue(i)+"="+result[i]+" "+G.getVertexValue(result[i]-1));
	}
	public static int[] getMinU() throws Exception{
		C5_5 scc_alg = new C5_5(G);
		component_graph = scc_alg.constructComponentGraph();
		int[] weight = scc_alg.getWeight();
		List<List<Integer>> list = scc_alg.getComponentListInInteger();
		int mincompL[] = new int[component_graph.getSize()];	//ÿ��ǿ��ͨ��֧����С��L
		minu = new int[component_graph.getSize()];
		min = new int[G.getSize()];
		color = new int[component_graph.getSize()];
		for(int i=0;i<color.length;i++){
			color[i] = WHITE;
			minu[i] = Integer.MAX_VALUE;
		}
		for(int i=0;i<min.length;i++)
			min[i] = Integer.MAX_VALUE;
		for(int i=0;i<mincompL.length;i++)
			mincompL[i] = Integer.MAX_VALUE;
		
		for(int i=0;i<list.size();i++){	//i=weight-1
			List<Integer> everyList = list.get(i);
			for(int j=0;j<everyList.size();j++){
				if(mincompL[i]>L[everyList.get(j)]){
					mincompL[i] = L[everyList.get(j)];  //mincompL[i] Ϊweight=i+1�ķ�֧��minL
				}
			}
		}
		calculate_minu(mincompL);
		for(int i=0;i<min.length;i++){
			min[i] = minu[weight[i]-1];
		}
		return min;
	}
	private static void calculate_minu(int[] mincompL) {
		for(int i=0;i<minu.length;i++){	//minu����ΪmincompL
			minu[i] = mincompL[i];
		}
		TopologicalSort_Algorithm topo_alg = new TopologicalSort_Algorithm(component_graph);
		String[] topo = topo_alg.TopologicalSort();
		for(int i=0;i<topo.length;i++){
			int u = component_graph.getVertexIndex(topo[i]);
			if(color[u]==WHITE)
				DFS_visit(u);
		}
	}
	private static void DFS_visit(int i) {
		color[i] = GRAY;
		for(int j=0;j<component_graph.getListByVertexIndex(i).size();j++){
			String value =component_graph.getListByVertexIndex(i).get(j);
			int index = component_graph.getVertexIndex(value);
			if(color[index]==WHITE){
				DFS_visit(index);
				minu[i] = Math.min(minu[i],minu[index]);
			}
		}
		color[i] = BLACK;
		
	}
	
	
}
