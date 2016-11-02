package elementarygraphalgorithm;

import java.util.Iterator;

public class C1_3{
	//求转置图
	public static Adjacent_List getTransposeGraph(Adjacent_List g){
		Adjacent_List Gt = new Adjacent_List(g.getSize());
		for(int u=0;u<g.getSize();u++){
			Iterator<String> iter = g.getListByVertexIndex(u).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				Gt.addEdge(vstr , g.getVertexValue(u));	//添加转置边
			}
		}
		return Gt;
	}
	
	public static Adjacent_Matrix getTransposeMatrix(Adjacent_Matrix g){
		Adjacent_Matrix Gt = new Adjacent_Matrix(g.getSize());
		for(int i=0;i<g.getSize();i++){
			for(int j=0;j<g.getSize();j++){
				Gt.setEdge(g.getVertexValue(j), g.getVertexValue(i), g.getElement(i, j));
			}
		}
		return Gt;
	}
	
	public static void main(String[] args) throws Exception {
		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22_2.txt");
		System.out.println("====原图===");
		adjlist.printAllEdges();
		Adjacent_List transposeGraph = getTransposeGraph(adjlist);
		System.out.println("=====转置图=====");
		transposeGraph.printAllEdges();
		System.out.println();
		
		Adjacent_Matrix adj_matrix = GraphFactory.getAdjacentMatrixInstance("graph\\22_2.txt");
		System.out.println("====原图===");
		adj_matrix.printAllEdges();
		System.out.println("=====转置图=====");
		Adjacent_Matrix Gt = getTransposeMatrix(adj_matrix);
		Gt.printAllEdges();
	}
}
