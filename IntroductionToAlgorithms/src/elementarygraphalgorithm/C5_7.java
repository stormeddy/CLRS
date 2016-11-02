package elementarygraphalgorithm;

import java.util.Arrays;
import java.util.List;
public class C5_7 {
	private static Adjacent_List g;
//	private static Adjacent_List adjlist;
//	private static int[]weight;
//	private static boolean isExist[][];
	
	public static void main(String[] args) throws Exception {
		//相比于scc_input.txt，22.5_7.txt去除了边(c,g),就不再是半连通的了
		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22.5_7.txt");
		C5_7.g = adjlist;
		System.out.println(isSemiconnected());
	}
	public static boolean isSemiconnected() throws Exception{
		C5_5 scc_alg = new C5_5(g);
		Adjacent_List scclist = scc_alg.constructComponentGraph();
		scclist.printAllEdges();
		TopologicalSort_Algorithm topo_alg = new TopologicalSort_Algorithm(scclist);
		String[] vertexs = topo_alg.TopologicalSort();
		System.out.println(Arrays.toString(vertexs));
		boolean flag ;
		//检查拓扑排序后是否存在线性链
		for(int i=0;i<vertexs.length-1;i++){
			flag = false;
			String s1 = vertexs[i];
//			System.out.println(s1);
			String s2 = vertexs[i+1];
			List<String> scclinklist = scclist.getListByVertexValue(s1);
			
			for(String elem:scclinklist){
				if(elem.equals(s2)){
					flag = true;
					break;
				}
			}
			if(!flag) return false;
		}
		return true;
	}
	/*public static Adjacent_List getBranchGraph() throws Exception{
		//此处计算分支图，与C5_5一样
		StrongConnectedComponents_Algorithm scc_alg = new StrongConnectedComponents_Algorithm(g);
		scc_alg.SCC();
		int size = scc_alg.getComponentSize();
		adjlist = new Adjacent_List(size);
		isExist = new boolean[size][size];
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				isExist[i][j] = false;
			}
		}
		for(int i=1;i<=size;i++){
			adjlist.addVertex(i+"");
		}
		weight = scc_alg.getWeight();
		Traverse();
		return adjlist;
	}
	private static void Traverse() {
		for(int i=0;i<g.getSize();i++){
			List<String> list = g.getListByVertexIndex(i);
			for(String str:list){
				//i begin ; str end
				int v = g.getVertexIndex(str);
				if(weight[i]!=weight[v]){
					int sccIndex1 = adjlist.getVertexIndex(weight[i]+"");
					int sccIndex2 = adjlist.getVertexIndex(weight[v]+"");
					if(!isExist[sccIndex1][sccIndex2]){
						adjlist.getListByVertexIndex(sccIndex1).add(weight[v]+"");
						isExist[sccIndex1][sccIndex2] = true;
					}
				}
			}
		}
	}*/
}






