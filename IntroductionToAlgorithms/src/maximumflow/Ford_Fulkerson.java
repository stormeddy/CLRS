package maximumflow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import elementarygraphalgorithm.BFS_Algorithm;
import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Weighted_Adjacent_Matrix;

public class Ford_Fulkerson {
	
	private void convert_to_capacity(Weighted_Adjacent_Matrix G){
		//权重矩阵图中权重代表容量,转化为容量矩阵图
		int size=G.getSize();
		int[][] c=G.getMatrix();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (c[i][j]==Integer.MAX_VALUE) {
					c[i][j]=0;
				}				
			}
		}
	}
	
	public int[][] ford_fulkerson(Weighted_Adjacent_Matrix G, String src, String dst){
		int size=G.getSize();
		//额外用数组表示流的值
		convert_to_capacity(G);
		int[][] c=G.getMatrix();
		//f为流图矩阵
		int[][] f=new int[size][size];
		//初始Gf与G相同
		Weighted_Adjacent_Matrix Gf=G.constructGraphFromMatrix(c);
		//cf为残存图矩阵
		int[][] cf=Gf.getMatrix();
		
		List<Integer> path=null;
		int cf_p=Integer.MAX_VALUE;
		while ((path=exist_path(Gf, src, dst)).size()>2) {
			for (int i = 0; i < path.size()-1; i++) {
				int u=path.get(i);
				int v=path.get(i+1);
				cf_p=Integer.min(cf_p, cf(c, f, u, v));
			}
			for (int i = 0; i < path.size()-1; i++) {
				int u=path.get(i);
				int v=path.get(i+1);
				if (c[u][v]!=0) {
					f[u][v]+=cf_p;					
				}else {
					f[v][u]-=cf_p;
				}
				cf[u][v]=c[u][v]-f[u][v];
				cf[v][u]=f[u][v];
			}
//			construct_residual_matrix(cf, c, f);
//			System.out.println("c:");
//			print_matrix(c);
//			System.out.println();
//			System.out.println("f:");
//			print_matrix(f);
//			System.out.println();
//			System.out.println("cf:");
//			print_matrix(cf);
//			System.out.println();
			
		}
		return f;
	}
	
//	private void construct_residual_matrix(int[][] cf,int[][] c, int[][] f){
//		int size=c.length;
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				if (c[i][j]!=0) {
//					cf[i][j]=c[i][j]-f[i][j];
//				}else if (c[j][i]!=0) {
//					cf[i][j]=f[j][i];
//				}
//			}
//		}
//	}	
	
	private int cf(int[][] c, int[][] f, int u, int v){
		//计算残存容量cf(u,v)
		if (c[u][v]!=0) {
			return  c[u][v]-f[u][v];
		}else if (c[v][u]!=0) {
			return f[v][u];
		}else {
			return 0;
		}
	}
	
	public int cal_maximum_flow(Weighted_Adjacent_Matrix G, String src, String dst){
		int[][] f=ford_fulkerson(G, src, dst);
		return flow_out(G, f, src)-flow_in(G, f, src);
	}
	
	private int flow_in(Weighted_Adjacent_Matrix G, int[][] f, String src){
		int sum=0;
		int s=G.getVertexIndex(src);
		for (int i = 0; i < f.length; i++) {
			sum+=f[i][s];
		}
		return sum;
	}
	
	private int flow_out(Weighted_Adjacent_Matrix G, int[][] f, String src){
		int sum=0;
		int s=G.getVertexIndex(src);
		for (int i = 0; i < f[0].length; i++) {
			sum+=f[s][i];
		}
		return sum;
	}
	private List<Integer> exist_path(Weighted_Adjacent_Matrix Gf,String src, String dst){
		//寻找图Gf中src->dst的简单路径
		//使用Edmonds-Karp算法
		LinkedList<Integer> path=new LinkedList<>();
		BFS_Algorithm bfs=new BFS_Algorithm(Gf);
		bfs.BFS(Gf, src);
		
		int[] parent=bfs.getParents();
		int index=Gf.getVertexIndex(dst);
		
		while (parent[index]!=-1) {
			path.addFirst(index);
			index=parent[index];
		}
		path.addFirst(index);
		return path;
	}
	
	public void print_matrix(int[][] w){
		for (int i = 0; i < w.length; i++) {
			System.out.println(Arrays.toString(w[i]));
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_Matrix G=GraphFactory.getWeightedAdjacentMatrixInstance("graph\\mf_input.txt");
		Ford_Fulkerson ff=new Ford_Fulkerson();
		String src="s";
		String dst="t";
		int[][] flow=ff.ford_fulkerson(G, src, dst);
		System.out.println("capacity matrix:");
		ff.print_matrix(G.getMatrix());
		
		System.out.println("flow matrix:");
		ff.print_matrix(flow);
		
		int mf=ff.cal_maximum_flow(G, src, dst);
		System.out.println("maximum flow:");
		System.out.println(mf);
	}

}
