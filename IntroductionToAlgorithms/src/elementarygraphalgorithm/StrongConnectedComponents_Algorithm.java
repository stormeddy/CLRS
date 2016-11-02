package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.List;

public class StrongConnectedComponents_Algorithm {
	private Adjacent_List G;
	private List<String> flist;	//从大到小的f值的序列,他的值从拓扑排序里得到
	private Adjacent_List GT;//G的转置图
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int size;
	private int weight[];	//存储强连通分支的各个节点的值，同一个强连通分支的节点的值相同，并且值的范围[1,强连通分支个数]
	private int cc;			//强连通分支的个数
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
	 * 获得强连通分支个数
	 * @return
	 */
	public int getComponentSize(){
		return cc;
	}
	/**
	 * 归类输出强连通分支，同一个强连通分支的节点一起输出
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
	 * 执行强连通分支算法
	 * @throws Exception
	 */
	public void SCC() throws Exception{
		TopologicalSort_Algorithm topo_alg = new TopologicalSort_Algorithm(G);
		topo_alg.TopologicalSort();	//拓扑排序获得f值从大到小序列
		flist = topo_alg.getResultList();
//		topo_alg.print();
		Transposition_AdjacentList_Algorithm trans_alg = new Transposition_AdjacentList_Algorithm(G);
		GT = trans_alg.transposeG();	//转置图
//		GT.printAllEdges();
		DFS(GT);	//对转置图进行DFS，并赋予权值
	}
	
	private void DFS(Adjacent_List g){
		for(int i=0;i<flist.size();i++){
			//GT.getVertexValue(i)而不是G.getVertexValue(i)，以第二次DFS为基准寻找节点索引对
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
	 * 获得每个节点的权值,相同权值的点为同一个强连通分支
	 * @return
	 */
	public int[] getWeight(){
		return weight;
	}
	public void printVertexAndWeight(){
		for(int i=0;i<weight.length;i++){
			//GT.getVertexValue(i)而不是G.getVertexValue(i)，以第二次DFS为基准寻找节点索引对
			System.out.println(GT.getVertexValue(i)+":"+weight[i]);
		}
	}
	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list = GraphFactory.getAdjacentListInstance("graph\\scc_input.txt");
		
		StrongConnectedComponents_Algorithm alg = new StrongConnectedComponents_Algorithm(adjacent_list);
		alg.SCC();
		System.out.println("强连通分支个数："+alg.getComponentSize());
		alg.printAllComponents();
//		int[] weight = alg.getWeight();
		alg.printVertexAndWeight();
//		for(int i=0;i<weight.length;i++){
//			System.out.println(adjacent_list.getVertexValue(i)+":"+weight[i]);
//		}
	}
}
