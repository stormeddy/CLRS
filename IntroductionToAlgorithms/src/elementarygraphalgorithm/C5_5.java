package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.List;

public class C5_5 {
	//计算分量图
	private Adjacent_List G;
	private List<String> flist;	//从大到小的f值的序列,他的值从拓扑排序里得到
	private Adjacent_List GT;//G的转置图
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int size;
	private int weight[];	//存储强连通分支的各个节点的值，同一个强连通分支的节点的值相同，并且值的范围[1,强连通分支个数]
	private int cc;			//强连通分支的值
	private List<String> component_vertex;
	private boolean constructed[][];
	//构造分支图时使用下面变量
//	private boolean visited[];		
	//private Adjacent_List adjlist;
	public C5_5(Adjacent_List G){
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
	 * 获得强连通分支各个顶点
	 * @return
	 */
	public List<String> getComponenentVertex(){
		return component_vertex;
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
			scclist.get(weight[i]-1).add(GT.getVertexValue(i));
		}
		for(List<String> list:scclist){
			for(String str:list){
				System.out.print(str+" ");
			}
			System.out.println();
		}
	}
	
	public List<List<Integer>> getComponentListInInteger(){
		//获取强连通分量分支链表
		List<List<Integer>> scclist = new ArrayList<List<Integer>>();
		for(int i=0;i<cc;i++){
			scclist.add(new ArrayList<Integer>());
		}
		for(int i=0;i<size;i++){
			scclist.get(weight[i]-1).add(G.getVertexIndex(GT.getVertexValue(i)));
		}
		return scclist;
/*		for(List<String> list:scclist){
			for(String str:list){
				System.out.print(str+" ");
			}
			System.out.println();
		}*/
	}
	
	public List<List<String>> getComponentList(){
		//获取强连通分量分支链表
		List<List<String>> scclist = new ArrayList<List<String>>();
		for(int i=0;i<cc;i++){
			scclist.add(new ArrayList<String>());
		}
		for(int i=0;i<size;i++){
			scclist.get(weight[i]-1).add(GT.getVertexValue(i));
		}
		return scclist;
/*		for(List<String> list:scclist){
			for(String str:list){
				System.out.print(str+" ");
			}
			System.out.println();
		}*/
	}
	private Adjacent_List getG(){
		return G;
	}
	
	public Adjacent_List getGT(){
		return GT;
	}
	/**
	 * 执行强连通分支算法
	 * @throws Exception
	 */
	private void SCC() throws Exception{
		TopologicalSort_Algorithm topo_alg = new TopologicalSort_Algorithm(G);
		topo_alg.TopologicalSort();	//拓扑排序获得f值从大到小序列
		flist = topo_alg.getResultList();
		Transposition_AdjacentList_Algorithm trans_alg = new Transposition_AdjacentList_Algorithm(G);
		GT = trans_alg.transposeG();	//转置图
		DFS(GT);	//对转置图进行DFS，并赋予权值
	}
	private void DFS(Adjacent_List g){
		for(int i=0;i<flist.size();i++){
			int index = g.getVertexIndex(flist.get(i));
			if(color[index]==WHITE){
				cc++;
				DFS_VISIT(g,index,cc);
			}
		}
	}
	private void DFS_VISIT(Adjacent_List g,int i,int cc) {
		color[i] = GRAY;
		weight[i] = cc;
		for(int j=0;j<g.getListByVertexIndex(i).size();j++){
			String value = g.getListByVertexIndex(i).get(j);
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
			System.out.println(GT.getVertexValue(i)+":"+weight[i]);
		}
	}
	
	private void constructComponentVertex(){
		//将原图顶点聚合成新的分量图顶点
		component_vertex = new ArrayList<>();
		for(int i=0;i<cc;i++){
			component_vertex.add("");
		}
		for(int i=0;i<size;i++){
			component_vertex.set(weight[i]-1, component_vertex.get(weight[i]-1)+(GT.getVertexValue(i)));
		}
//		for(String str:component_vertex){			
//			System.out.println(str);
//		}
	}
	public Adjacent_List constructComponentGraph() throws Exception{
		//1.通过强连通分支为每个节点赋予一个权重。
//		C5_5 scc_alg = new C5_5(G);
//		scc_alg.SCC();
		SCC();
//		printAllComponents();
		constructComponentVertex();
		int size = getComponentSize();	//size为强连通分支个数
		constructed=new boolean[size][size];
//		visited = new boolean[size];
//		for(int i=0;i<visited.length;i++){
//			visited[i] = false;
//		} 
		Adjacent_List adjlist = new Adjacent_List(size);
		for(int i=0;i<size;i++){
			adjlist.addVertex(component_vertex.get(i));
		}
		weight = getWeight();
		
		for(int i=0;i<GT.getSize();i++){
			List<String> list = GT.getListByVertexIndex(i);
			for(String str:list){
				//weight[i]的中的i都是对应于GT中的顶点索引
				int v = GT.getVertexIndex(str);
				int v_comp=weight[v];
				int i_comp=weight[i];
				if(i_comp!=v_comp&&constructed[i_comp-1][v_comp-1]==false){
					//防止添加重边
					//通过weight[i]获得分量索引，进而得到聚合后的顶点					
//					System.out.println(component_vertex.get(i_comp-1)+"-->"+component_vertex.get(v_comp-1));
					adjlist.addEdge(component_vertex.get(v_comp-1), component_vertex.get(i_comp-1));//注意v，i的顺序，GT为原图的转置
//					int sccIndex1 = adjlist.getVertexIndex(component_vertex.get(i_comp-1));
//					adjlist.getListByVertexIndex(sccIndex1).add(component_vertex.get(v_comp-1));
					constructed[i_comp-1][v_comp-1]=true;
				}
			}
		}
		//2.构造分支图
//		Traverse(adjlist);	//大致构造分支图
//		
//		//删除重边
//		for(int i=0;i<size;i++){
//			visited[i] = true;
//			Iterator<String> iter = adjlist.getListByVertexIndex(i).iterator();
//			while(iter.hasNext()){
//				String vstr = iter.next();
//				int v = adjlist.getVertexIndex(vstr);
//				if(!visited[v]){
//					visited[v] = true;
//				}
//				else{
//					iter.remove();
//				}
//			}
//			iter = adjlist.getListByVertexIndex(i).iterator();
//			while(iter.hasNext()){
//				String vstr = iter.next();
//				int v = adjlist.getVertexIndex(vstr);
//				visited[v] = false;
//			}
////			visited[i] = false;
//		}
//		
		return adjlist;
	}
//	private void Traverse(Adjacent_List adjlist) {
//		for(int i=0;i<G.getSize();i++){
//			List<String> list = G.getListByVertexIndex(i);
//			for(String str:list){
//				int v = G.getVertexIndex(str);
//				if(weight[i]!=weight[v]){
//					int sccIndex1 = adjlist.getVertexIndex(weight[i]+"");
//					adjlist.getListByVertexIndex(sccIndex1).add(weight[v]+"");
//				}
//			}
//		}
//	}
	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list = GraphFactory.getAdjacentListInstance("graph\\scc_input.txt");
		C5_5 alg = new C5_5(adjacent_list);
		Adjacent_List list = alg.constructComponentGraph();
		list.printAllEdges();
//		System.out.println(list.getEdgeSize());
		
	}
}