package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.List;

public class C5_5 {
	//�������ͼ
	private Adjacent_List G;
	private List<String> flist;	//�Ӵ�С��fֵ������,����ֵ������������õ�
	private Adjacent_List GT;//G��ת��ͼ
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int size;
	private int weight[];	//�洢ǿ��ͨ��֧�ĸ����ڵ��ֵ��ͬһ��ǿ��ͨ��֧�Ľڵ��ֵ��ͬ������ֵ�ķ�Χ[1,ǿ��ͨ��֧����]
	private int cc;			//ǿ��ͨ��֧��ֵ
	private List<String> component_vertex;
	private boolean constructed[][];
	//�����֧ͼʱʹ���������
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
	 * ���ǿ��ͨ��֧����
	 * @return
	 */
	public int getComponentSize(){
		return cc;
	}
	/**
	 * ���ǿ��ͨ��֧��������
	 * @return
	 */
	public List<String> getComponenentVertex(){
		return component_vertex;
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
		//��ȡǿ��ͨ������֧����
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
		//��ȡǿ��ͨ������֧����
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
	 * ִ��ǿ��ͨ��֧�㷨
	 * @throws Exception
	 */
	private void SCC() throws Exception{
		TopologicalSort_Algorithm topo_alg = new TopologicalSort_Algorithm(G);
		topo_alg.TopologicalSort();	//����������fֵ�Ӵ�С����
		flist = topo_alg.getResultList();
		Transposition_AdjacentList_Algorithm trans_alg = new Transposition_AdjacentList_Algorithm(G);
		GT = trans_alg.transposeG();	//ת��ͼ
		DFS(GT);	//��ת��ͼ����DFS��������Ȩֵ
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
	 * ���ÿ���ڵ��Ȩֵ,��ͬȨֵ�ĵ�Ϊͬһ��ǿ��ͨ��֧
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
		//��ԭͼ����ۺϳ��µķ���ͼ����
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
		//1.ͨ��ǿ��ͨ��֧Ϊÿ���ڵ㸳��һ��Ȩ�ء�
//		C5_5 scc_alg = new C5_5(G);
//		scc_alg.SCC();
		SCC();
//		printAllComponents();
		constructComponentVertex();
		int size = getComponentSize();	//sizeΪǿ��ͨ��֧����
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
				//weight[i]���е�i���Ƕ�Ӧ��GT�еĶ�������
				int v = GT.getVertexIndex(str);
				int v_comp=weight[v];
				int i_comp=weight[i];
				if(i_comp!=v_comp&&constructed[i_comp-1][v_comp-1]==false){
					//��ֹ����ر�
					//ͨ��weight[i]��÷��������������õ��ۺϺ�Ķ���					
//					System.out.println(component_vertex.get(i_comp-1)+"-->"+component_vertex.get(v_comp-1));
					adjlist.addEdge(component_vertex.get(v_comp-1), component_vertex.get(i_comp-1));//ע��v��i��˳��GTΪԭͼ��ת��
//					int sccIndex1 = adjlist.getVertexIndex(component_vertex.get(i_comp-1));
//					adjlist.getListByVertexIndex(sccIndex1).add(component_vertex.get(v_comp-1));
					constructed[i_comp-1][v_comp-1]=true;
				}
			}
		}
		//2.�����֧ͼ
//		Traverse(adjlist);	//���¹����֧ͼ
//		
//		//ɾ���ر�
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