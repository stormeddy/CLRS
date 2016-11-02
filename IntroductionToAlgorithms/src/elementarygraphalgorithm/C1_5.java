package elementarygraphalgorithm;

import java.util.Iterator;
import java.util.List;

public class C1_5 {
	//«Û∆Ω∑ΩÕº
	public static void main(String[] args) throws Exception {
		Adjacent_Matrix adj_matrix = GraphFactory.getAdjacentMatrixInstance("graph\\22.1_5.txt");
		print(adj_matrix.getMatrix());
		int[][]result = getSquareGraph(adj_matrix);
		print(result);
		
		Adjacent_List adj_list=GraphFactory.getAdjacentListInstance("graph\\22.1_5.txt");
		adj_list.printAllEdges();
		System.out.println();
		Adjacent_List ret=getSquareGraph_List(adj_list);
		ret.printAllEdges();
	}
	public static int[][] getSquareGraph(Adjacent_Matrix g){
		int[][] matrix = g.getMatrix();
		int result[][] = new int[matrix.length][matrix.length];
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix.length;j++){
				for(int k=0;k<matrix.length;k++){
					result[i][j] += matrix[i][k]*matrix[k][j];
				}
			}
		}
		return result;
	}
	
	public static Adjacent_List getSquareGraph_List(Adjacent_List g) throws Exception{
		int vertex_size=g.getSize();
		Adjacent_List ret=new Adjacent_List(vertex_size);
		for (int i = 0; i < vertex_size; i++) {
			List<String> u_adj_list=g.getListByVertexIndex(i);
			Iterator<String> iter=u_adj_list.iterator();
			while (iter.hasNext()) {
				String vtr=iter.next();
//				ret.addEdge(g.getVertexValue(i), vtr);
				List<String> v_adj_list=g.getListByVertexValue(vtr);
				ret.addEdges(g.getVertexValue(i), v_adj_list);			
			}
			
		}
//		ret=C1_4.remove_multiedge(ret);
		return ret;
		
	}
	
	public static void print(int[][] arr){
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr.length;j++)
				System.out.print(arr[i][j]+" ");
			System.out.println();
		}
		System.out.println();
	}
}
