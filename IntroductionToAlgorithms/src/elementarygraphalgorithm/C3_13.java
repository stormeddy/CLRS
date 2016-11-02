package elementarygraphalgorithm;

import java.util.List;

public class C3_13 {
//	思路：对每个节点DFS，每次DFS前清空color，DFS时寻找正向边或交叉边（color[v]==BLACK）
//	复杂度：O(V(V+E))
	private static int color[];
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private static Adjacent_List g;
	public static void main(String[] args) throws Exception {
		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22.3_13_bad.txt");
		C3_13.g = adjlist;
		System.out.println(singly_connected());
	}
	public static boolean singly_connected(){
		int size = g.getSize();
		color = new int[size];
		for(int a = 0;a<color.length;a++)  color[a] = WHITE;
		for(int i=0;i<size;i++){
			if(color[i]==WHITE){
				for(int a = 0;a<color.length;a++) color[a] = WHITE;
				boolean flag = DFS(i);
				if(!flag) return false;
			}
		}
		return true;
	}
	public static boolean DFS(int u){
		color[u] = GRAY;
		List<String> list = g.getListByVertexIndex(u);
		for(int i=0;i<list.size();i++){
			int v = g.getVertexIndex(list.get(i));
			if(color[v]==WHITE) {
				boolean flag = DFS(v);
				if(!flag) return false;
			}
			//前向边或横向边
			else if(color[v]==BLACK) return false;
		}
		color[u] = BLACK;
		return true;
	}
}