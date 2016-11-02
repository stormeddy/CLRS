package elementarygraphalgorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class DFS_Algorithm{
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int cell[];//连通分量编号
	private int size;
	private int d[];
	private int f[];
	private int time;
	private int rcell;
	private int parent[];
	private Adjacent_List G;
	private HashSet<Edge> T_Set;//树边集
	private HashSet<Edge> B_Set;//后向边集
	private HashSet<Edge> F_Set;//前向边集
	private HashSet<Edge> C_Set;//横向边集
	
	private class Edge{
		private int u;
		private int v;
		public Edge(int u,int v){
			this.u=u;
			this.v=v;
		}
	}
	public DFS_Algorithm(Adjacent_List G){
		this.G = G;
		size = G.getSize();
		color = new int[size];
		d = new int[size];
		f = new int[size];
		cell=new int[size];
		parent = new int[size];
		time = 0;
		rcell=0;
		for(int i=0;i<size;i++){
			color[i] = WHITE;
			parent[i] = -1;
		}
		T_Set=new HashSet<Edge>();
		B_Set=new HashSet<Edge>();
		F_Set=new HashSet<Edge>();
		C_Set=new HashSet<Edge>();
	}
	
	public int[] getD() {
		return d;
	}

	public int[] getF() {
		return f;
	}
	//主函数
	public void DFS(){
		for(int i=0;i<G.getSize();i++){
			if(color[i]==WHITE){
				rcell++;
				DFS_VISIT(i,rcell);
//				dfs_visit(i);
			}
		}
	}
	public void DFS_VISIT(int i,int rcell) {
		color[i] = GRAY;
		time++;
		d[i] = time;
		for(int j=0;j<G.getListByVertexIndex(i).size();j++){
			String value = G.getListByVertexIndex(i).get(j);
			int index = G.getVertexIndex(value);
			//根据index的颜色确定边(i,index)为哪种类型的边
			if(color[index]==WHITE){
				parent[index] = i;
				T_Set.add(new Edge(i, index));
				DFS_VISIT(index,rcell);
			}else if (color[index]==GRAY) {
				B_Set.add(new Edge(i, index));
			}else {
				//对于无向图这一块可删除
				if (d[i]<d[index]) {
					F_Set.add(new Edge(i, index));
				}else {
					C_Set.add(new Edge(i, index));
				}
			}
		}
		time++;
		f[i] = time;
		color[i] = BLACK;
		cell[i]=rcell;
	}
	
	private boolean judge(HashSet<Edge> s,Edge e){
		//判断该边集合是否包含该边
		Iterator<Edge> iter=s.iterator();
		while(iter.hasNext()){
			Edge t=iter.next();
			if (t.u==e.u&&t.v==e.v) {
				return true;
			}
		}
		return false;
	}
	
	public void dfs_visit(int v){
		//非递归版本
		if (color[v]==BLACK) {
			return;
		}
		Stack<Integer> s=new Stack<Integer>();
		s.push(v);
//		if (color[v]==WHITE) {
//			//该节点未被探索过，是新的源节点
//			color[v]=GRAY;
////			d[v]=++time;
//		}else {
//			return;
//		}		
		while (!s.isEmpty()) {
			int u=s.peek();
			if (color[u]==BLACK) {
				s.pop();
				continue;
			}
			if (color[u]==WHITE) {
				d[u]=++time;
				color[u]=GRAY;
				if(parent[u]!=-1)//如果不是源结点
					T_Set.add(new Edge(parent[u], u));
				List<String> list=G.getListByVertexIndex(u);
				for (int i = 0; i < list.size(); i++) {
					String value=list.get(i);
					int index=G.getVertexIndex(value);
					if (color[index]==WHITE) {
//						T_Set.add(new Edge(u, index));
						parent[index]=u;
						s.push(index);
					}else if (color[index]==GRAY) {
						B_Set.add(new Edge(u, index));
					} else {
						if (d[u] < d[index]) {
							//此处对于书上的例子可删除
							F_Set.add(new Edge(u, index));
						} else {
							C_Set.add(new Edge(u, index));
						}
					}				
				}
			}else {
				//此时color[u]==GRAY
				List<String> list=G.getListByVertexIndex(u);
				for (int i = 0; i < list.size(); i++) {
					String value=list.get(i);
					int index=G.getVertexIndex(value);
					//该边若不属于树边，则必然属于前向边
					if(!judge(T_Set, new Edge(u, index))&&d[u] < d[index]){
						F_Set.add(new Edge(u, index));
					}
				}//对于无向图else到此处可删除				
				s.pop();
				f[u]=++time;
				color[u]=BLACK;
			}
			
//			if (color[u]==GRAY) {
//				boolean all_gray=true;
//				List<String> list=G.getListByVertexIndex(u);
//				for (int i = 0; i < list.size(); i++) {
//					String value=list.get(i);
//					int index=G.getVertexIndex(value);
//					if (color[index]==WHITE) {
//						all_gray=false;
//						parent[index]=u;
//						color[index]=GRAY;
//						s.push(index);
//					}							
//				}
//				if (all_gray) {
//					color[u]=BLACK;
//					f[u]=++time;
//					s.pop();
//					continue;
//				}				
//			}
//			if (color[u]==WHITE) {
//				color[u]=GRAY;
//				d[u]=++time;
//				List<String> list=G.getListByVertexIndex(u);
//				for (int i = 0; i < list.size(); i++) {
//					String value=list.get(i);
//					int index=G.getVertexIndex(value);
//					if (color[index]==WHITE) {
//						d[index]=++time;
//						parent[index]=u;
//						color[index]=GRAY;
//						s.push(index);
//					}							
//				}
//			}
		}
	}
	/**
	 * 输出DFS的每个节点的d值和f值
	 */
	public void print_d_f(){
		for(int i=0;i<size;i++){
			System.out.println(G.getVertexValue(i)+"("+d[i]+"/"+f[i]+")");
		}
	}
	
	/**
	 * 输出DFS的每个节点的连通分量编号
	 */
	public void print_cell(){
		for(int i=0;i<size;i++){
			System.out.println(G.getVertexValue(i)+":"+cell[i]+" ");
		}
	}
	
	public void print_edges(HashSet<Edge> s){
		Iterator<Edge> iterator=s.iterator();
		while (iterator.hasNext()) {
			Edge e=iterator.next();
			System.out.println(G.getVertexValue(e.u)+" --> "+G.getVertexValue(e.v));
		}
	}
	
	public void print_all_edges(){
		System.out.println("T_Set:");
		print_edges(T_Set);
		System.out.println("B_Set:");
		print_edges(B_Set);
		System.out.println("F_Set:");
		print_edges(F_Set);
		System.out.println("C_Set:");
		print_edges(C_Set);
	}
	
	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list = GraphFactory.getAdjacentListInstance("graph\\dfs_input.txt");
		DFS_Algorithm dfs_alg = new DFS_Algorithm(adjacent_list);
		dfs_alg.DFS();
		dfs_alg.print_all_edges();
		dfs_alg.print_d_f();	//输出d和f数组
		dfs_alg.print_cell();
	}
}
