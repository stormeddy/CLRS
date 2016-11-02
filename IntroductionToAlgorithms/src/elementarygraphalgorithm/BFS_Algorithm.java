package elementarygraphalgorithm;

import java.util.LinkedList;
import java.util.List;

public class BFS_Algorithm {
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int size;
	private int d[];
	private int parent[];
	private LinkedList<Integer> q;
	private Adjacent_List G;
	
	public BFS_Algorithm (Adjacent_List G){
		this.G = G;
		this.size = G.getSize();
		d = new int[size];
		color = new int[size];
		parent = new int[size];
		q = new LinkedList<Integer>();
		for(int i=0;i<size;i++){
			d[i] = Integer.MAX_VALUE;
			color[i] = WHITE;
			parent[i] = -1;
		}
	}
	
	public BFS_Algorithm (Weighted_Adjacent_Matrix G){
//		this.G = G;
		this.size = G.getSize();
		d = new int[size];
		color = new int[size];
		parent = new int[size];
		q = new LinkedList<Integer>();
		for(int i=0;i<size;i++){
			d[i] = Integer.MAX_VALUE;
			color[i] = WHITE;
			parent[i] = -1;
		}
	}
	
	public void BFS(Adjacent_List G,int i){
		BFS(G,G.getVertexValue(i));
	}
	
	public void BFS(int i){
		BFS(G,G.getVertexValue(i));
	}
	
	public void BFS(Adjacent_List G,String s){
		for(int i=0;i<size;i++){
			d[i] = Integer.MAX_VALUE;
			color[i] = WHITE;
			parent[i] = -1;//前驱节点为nil
		}
		q = new LinkedList<Integer>();
		int begin = G.getVertexIndex(s);
		color[begin] = GRAY;
		d[begin] = 0;
//		q.add(begin);
		q.addLast(begin);
		while(!q.isEmpty()){
			int u = q.pop();
			List<String> list = G.getListByVertexIndex(u);
			for(String str:list){
				int v = G.getVertexIndex(str);
				if(color[v]==WHITE){
					color[v] = GRAY;
					d[v] = d[u]+1;
					parent[v] = u;
					q.addLast(v);	//加入队列
				}
			}
			color[u] = BLACK;
		}
	}
	
	
	public void BFS(Weighted_Adjacent_Matrix G,int i){
		BFS(G,G.getVertexValue(i));
	}
	public void BFS(Weighted_Adjacent_Matrix G,String s){
		for(int i=0;i<size;i++){
			d[i] = Integer.MAX_VALUE;
			color[i] = WHITE;
			parent[i] = -1;//前驱节点为nil
		}
		q = new LinkedList<Integer>();
		int begin = G.getVertexIndex(s);
		color[begin] = GRAY;
		d[begin] = 0;
//		q.add(begin);
		q.addLast(begin);
		
		int[][] w=G.getMatrix();
		while(!q.isEmpty()){
			int u = q.pop();
			
			for(int v=0; v<size ; v++){
				if(w[u][v]!=0 && color[v]==WHITE){
					color[v] = GRAY;
					d[v] = d[u]+1;
					parent[v] = u;
					q.addLast(v);	//加入队列
				}
			}
			color[u] = BLACK;
		}
	}	
	
	
	/**
	 * 给定起点、终点，输出路径
	 * @param s
	 * @param t
	 */
	public void print_path(Adjacent_List G,String s,String t){
		int s_index = G.getVertexIndex(s);
		int t_index = G.getVertexIndex(t);
		if(s_index == t_index) System.out.print(s+" ");
		else if(parent[t_index]==-1) System.out.println("no path from "+s + " to "+ t + " exists");
		else {
			print_path(G,s, G.getVertexValue(parent[t_index]));
			System.out.print(t+" ");
		}
	}
	/**
	 * 输出从源结点到全部的节点对应的路径距离
	 */
	public void print_AllDistances(Adjacent_List G){
		for(int i=0;i<d.length;i++){
			System.out.println(G.getVertexValue(i)+":"+d[i]);
		}
	}
	
	/**
	 * 输出全部的节点对应的前驱结点
	 */
	public void print_AllParents(Adjacent_List G){
		for(int i=0;i<parent.length;i++){
			System.out.println(G.getVertexValue(i)+":"+G.getVertexValue(parent[i]));
		}
	}
	/**
	 * 获得全部的路径
	 * @return
	 */
	public int[] getDistances(){
		return d;
	}
	/**
	 * 给定起点、终点，返回两点之间的路径
	 * @param s
	 * @param t
	 * @return
	 */
	public int getDistance(Adjacent_List G,String t){
		return d[G.getVertexIndex(t)];
	}
	public int getDistance(int t){
		return d[t];
	}
	
	/**
	 * 获得全部的前驱节点
	 * @return
	 */
	public int[] getParents(){
		return parent;
	}
	
	public static void main(String[] args) throws Exception {
		Adjacent_List G = GraphFactory.getAdjacentListInstance("graph\\bfs_input.txt");
		BFS_Algorithm bfs_alg = new BFS_Algorithm(G);
		bfs_alg.BFS(G,"u");
		bfs_alg.print_AllDistances(G);
		System.out.println();
		bfs_alg.print_AllParents(G);
		bfs_alg.print_path(G,"s", "y");
////		System.out.println(bfs_alg.getDistance( "y"));
		
//		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22.2_1.txt");
//		BFS_Algorithm bfs_alg = new BFS_Algorithm(adjlist);
//		bfs_alg.BFS("3");
//		bfs_alg.print_AllDistances();
//		System.out.println();
//		bfs_alg.print_AllParents();

//		System.out.println(bfs_alg.getDistance( "y"));
	}
}