package allpairsshortestpaths;

import java.util.Arrays;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Weighted_Adjacent_Matrix;

public class All_Pairs_Shortest_Paths {

	private int[][] extend_shortest_paths(int[][] L,Weighted_Adjacent_Matrix W){
		int[][] w=W.getMatrix();
		return extend_shortest_paths(L, w);
	}
	
	private int[][] extend_shortest_paths(int[][] L,int[][] w){
		//计算最短路径权重矩阵
		int n=L.length;
		int[][] Lp=new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Lp[i][j]=Integer.MAX_VALUE;
				for (int k = 0; k < n; k++) {
					int temp;
					if (L[i][k]==Integer.MAX_VALUE || w[k][j]==Integer.MAX_VALUE) {
						temp=Integer.MAX_VALUE;
					}else {
						temp=L[i][k]+w[k][j];
					}
					Lp[i][j]=Integer.min(Lp[i][j], temp);					
				}
			}
		}
		return Lp;
	}
	
	private int[][] extend_shortest_paths_with_parent(int[][] L,int[][] w,int[][] parent){
		//同时计算最短路径权重矩阵和前驱矩阵
		int n=L.length;
		int[][] Lp=new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Lp[i][j]=L[i][j];//不是无穷大，此时只对变化了的Lp更新对应的parent
				for (int k = 0; k < n; k++) {
					int temp;
					if (L[i][k]==Integer.MAX_VALUE || w[k][j]==Integer.MAX_VALUE) {
						temp=Integer.MAX_VALUE;
					}else {
						temp=L[i][k]+w[k][j];
					}
//					Lp[i][j]=Integer.min(Lp[i][j], temp);
					if (temp<Lp[i][j]) {
						Lp[i][j]=temp;
//						if ( (w[i][j]!=Integer.MAX_VALUE && j!=k) || (i!=k && j!=k) )
//						if(j!=k)
							parent[i][j]=k;
					}
				}
			}
		}		
		
		return Lp;
	}
	
	public int[][] cal_parent(int[][] L,int[][] w){
		//求得L之后再求parent，可能会有bug，最好是计算L的同时求得parent
		int n=L.length;
		int[][] parent=new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {				
				for (int k = 0; k < n; k++) {
//					if ( L[i][j]==L[i][k]+w[k][j] && ( (w[i][j]!=Integer.MAX_VALUE && j!=k) || (i!=k && j!=k) ) ) {
				    if ( L[i][j]==L[i][k]+w[k][j] && j!=k ) {
						//1.i与j之间存在i->j的边，此时要求j的前驱不能为j；	2.i与j之间不存在i->j的边，此时要求j的前驱不为j和i(此时前驱也不可能为)
						//所以总体可以写成j!=k
						parent[i][j]=k;
					}				
				}
			}
		}
		
		for (int i = 0; i < n; i++) {
			//同一个节点之间没有前驱节点
			parent[i][i]=-1;
		}
		return parent;
	}
	
	public void print_all_pairs_shortest_path(Weighted_Adjacent_Matrix W){
		//输出所有节点对的最短路径
		int n=W.getSize();
		int[][] w=W.getMatrix();
		int[][] L=faster_all_pairs_shortest_paths(W);		
		int[][] parent=cal_parent(L, w);
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(W.getVertexValue(i)+"->"+W.getVertexValue(j)+":");
				print_shortest_path(parent, i, j, W);
				System.out.println();
			}
			System.out.println();
		}
	}
	
	
	public void print_shortest_path(int[][] parent,int i,int j,Weighted_Adjacent_Matrix W){
		if (i==j) {
			System.out.print(W.getVertexValue(i)+" ");
		}else if (parent[i][j]==-1) {
			System.out.print("no path from "+W.getVertexValue(i)+" to "+W.getVertexValue(j)+" exists.");
		}else {
			print_shortest_path(parent, i, parent[i][j], W);
			System.out.print(W.getVertexValue(j)+" ");
		}
	}
	
	
	public void print_all_pairs_shortest_path_using_phi(Weighted_Adjacent_Matrix W){
		//输出所有节点对的最短路径,使用phi
		//25.2-7
		int n=W.getSize();
		int[][] w=W.getMatrix();

		int[][] phi=new int[n][n];
		floyd_warshall_with_phi(w, phi);
		print_matrix(phi);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(W.getVertexValue(i)+"->"+W.getVertexValue(j)+":");
				System.out.print(W.getVertexValue(i)+" ");
				print_shortest_path_using_phi(phi, i, j, W);
				System.out.print(W.getVertexValue(j)+" ");//i==j的情况下可以不用输出这一行
				System.out.println();
			}
			System.out.println();
		}
	}
	
	
	public void print_shortest_path_using_phi(int[][] phi,int i,int j,Weighted_Adjacent_Matrix W){
		//phi[i][j]为i->j的中间节点
		//这里不同于print_shortest_path,i不可能等于j
		int[][] w=W.getMatrix();
		if (phi[i][j]==-1) {
			if (w[i][j]==Integer.MAX_VALUE) {
				System.out.print("no path from "+W.getVertexValue(i)+" to "+W.getVertexValue(j)+" exists.");
			}
		}else {
			print_shortest_path_using_phi(phi, i, phi[i][j], W);
			System.out.print(W.getVertexValue(phi[i][j])+" ");
			print_shortest_path_using_phi(phi, phi[i][j], j, W);
		}
	}
		
	
	public int[][] faster_all_pairs_shortest_paths(Weighted_Adjacent_Matrix W){
		//使用反复平方法
		int n=W.getSize();
		int[][] L=W.getMatrix();
//		print_matrix(L);
//		System.out.println();
		int m=1;
		while (m<n-1) {
			L=extend_shortest_paths(L, L);
//			print_matrix(L);
//			System.out.println();
			m<<=1;
		}
		
		//判断是否包含权重为负值的环路
		boolean neg_cirlce=false;
		for (int i = 0; i < n; i++) {
			if (L[i][i]<0) {
				neg_cirlce=true;
				break;
			}			
		}
		if (neg_cirlce) {
			System.out.println("negative circle");
		}
		return L;
	}
	
	
	
	public int[][] slow_all_pairs_shortest_paths(Weighted_Adjacent_Matrix W){
		int n=W.getSize();
		int[][] L=W.getMatrix();
//		print_matrix(L);
//		System.out.println();
		
//		int len=0;//最短长度的权重为负值的环路的长度
//		int[][] preL=new int[n][n];
		
		for (int m = 2; m <= n-1; m++) {
			
			//计算最短长度的权重为负值的环路的长度
			/*for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					preL[i][j]=L[i][j];
				}
			}
			L=extend_shortest_paths(preL, W);	
			
			for (int i = 0; i < n; i++) {
				if (L[i][i]<0 && preL[i][i]==0) {
					len=m;
				}
			}*/
			
			L=extend_shortest_paths(L, W);	
			
			
//			print_matrix(L);
//			System.out.println();			
		}
//		System.out.println(len);
		return L;
	}
	
	public int[][] slow_all_pairs_shortest_paths_with_parent(Weighted_Adjacent_Matrix W,int[][] parent){
		int n=W.getSize();
		int[][] L=W.getMatrix();
//		print_matrix(L);
//		System.out.println();
		//此处和书上不同，因为在extend_shortest_paths_with_parent()中
		//Lp[i][j]=L[i][j](而不是书上的无穷大)
		//因此，初始parent中i->j的边使得parent[i][j]=i
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (L[i][j]!=Integer.MAX_VALUE) {
					parent[i][j]=i;
				}
			}
		}
		for (int m = 2; m <= n-1; m++) {
			L=extend_shortest_paths_with_parent(L, W.getMatrix(), parent);		
//			print_matrix(L);
//			System.out.println();			
		}
		
		for (int i = 0; i < n; i++) {
			//同一个节点之间没有前驱节点
			parent[i][i]=-1;
		}
		return L;
	}
	
	
	public int[][] floyd_warshall(Weighted_Adjacent_Matrix W){
		return floyd_warshall(W.getMatrix());
	}
	
	public int[][] floyd_warshall(int[][] w){
		//floyd_warshall算法
		int n=w.length;
		int[][][] D=new int[n+1][n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				D[0][i][j]=w[i][j];
			}
		}
		
		for (int k = 1; k <= n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int temp;
					if (D[k-1][i][k-1]!=Integer.MAX_VALUE && D[k-1][k-1][j]!=Integer.MAX_VALUE) {
						temp=D[k-1][i][k-1]+D[k-1][k-1][j];
					}else {
						temp=Integer.MAX_VALUE;
					}
					D[k][i][j]=Integer.min(D[k-1][i][j], temp);					
				}
			}
		}
		
		//输出D[0]~D[n]
//		for (int i = 0; i < D.length; i++) {
//			print_matrix(D[i]);
//			System.out.println();
//		}
		
		return D[n];		
	}
	
	
	public int[][] floyd_warshall_with_parent(int[][] w,int[][] p){
		int n=w.length;
		int[][][] D=new int[n+1][n][n];
		int[][][] parent=new int[n+1][n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				D[0][i][j]=w[i][j];
				if (i==j || w[i][j]==Integer.MAX_VALUE) {
					parent[0][i][j]=-1;
				}else {
					parent[0][i][j]=i;
				}
			}
		}
		
		for (int k = 1; k <= n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int temp;
					if (D[k-1][i][k-1]!=Integer.MAX_VALUE && D[k-1][k-1][j]!=Integer.MAX_VALUE) {
						temp=D[k-1][i][k-1]+D[k-1][k-1][j];
					}else {
						temp=Integer.MAX_VALUE;
					}
					
//					D[k][i][j]=Integer.min(D[k-1][i][j], temp);	
					
//					if (D[k-1][i][j]<temp || (D[k-1][i][j]==temp && temp==Integer.MAX_VALUE) || (D[k-1][i][j]==temp && (k-1==j||i==j))) {
						//对于D[k-1][i][j]==temp的情况，有两种情形要在这个块中处理：
						//1.D[k-1][i][j]为无穷大  
						//2.i,j,(k-1)中有相同节点(i->j有边,此时k-1==j;i和j是同一节点;不用考虑k-1==i的情况,该情况可以放在下一个块中处理)
						
					//或者直接写成if (D[k-1][i][j]<=temp){
					if (D[k-1][i][j]<=temp){
						D[k][i][j]=D[k-1][i][j];
						parent[k][i][j]=parent[k-1][i][j];
					}else {						
						D[k][i][j]=temp;
						parent[k][i][j]=parent[k-1][k-1][j];
						
					}
				
				}
			}
		}
		
		//输出D[0]~D[n],parent[0]~parent[n]
//		for (int i = 0; i < D.length; i++) {
//			print_matrix(D[i]);
//			System.out.println();
//			print_matrix(parent[i]);
//			System.out.println();
//		}
		
		//parent[n]为最终的前驱矩阵
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				p[i][j]=parent[n][i][j];
			}
		}
		
		return D[n];		
	}
	
	
	public boolean[][] transitive_closure(Weighted_Adjacent_Matrix W){
		return transitive_closure(W.getMatrix());
	}
	
	public boolean[][] transitive_closure(int[][] w){
		//计算有向图的传递闭包
		int n=w.length;
		boolean[][][] D=new boolean[n+1][n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i!=j && w[i][j]==Integer.MAX_VALUE) {
					D[0][i][j]=false;
				}else {
					D[0][i][j]=true;
				}
			}
		}
		
		for (int k = 1; k <= n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {					
					D[k][i][j]=D[k-1][i][j] || (D[k-1][i][k-1] && D[k-1][k-1][j]);					
				}
			}
		}		
		//输出D[0]~D[n]
		for (int i = 0; i < D.length; i++) {
			print_matrix(D[i]);
			System.out.println();
		}
		
		return D[n];		
	}
	
	
	public int[][] floyd_warshall_with_phi(int[][] w,int[][] p){
		int n=w.length;
		int[][][] D=new int[n+1][n][n];
		int[][][] phi=new int[n+1][n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				D[0][i][j]=w[i][j];
				
					phi[0][i][j]=-1;
				
			}
		}
		
		for (int k = 1; k <= n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int temp;
					if (D[k-1][i][k-1]!=Integer.MAX_VALUE && D[k-1][k-1][j]!=Integer.MAX_VALUE) {
						temp=D[k-1][i][k-1]+D[k-1][k-1][j];
					}else {
						temp=Integer.MAX_VALUE;
					}
					
					if (D[k-1][i][j]<=temp){
						D[k][i][j]=D[k-1][i][j];
						phi[k][i][j]=phi[k-1][i][j];
					}else {						
						D[k][i][j]=temp;
						phi[k][i][j]=k-1;
						
					}
				
				}
			}
		}
		
		//输出D[0]~D[n],parent[0]~parent[n]
//		for (int i = 0; i < D.length; i++) {
//			print_matrix(D[i]);
//			System.out.println();
//			print_matrix(parent[i]);
//			System.out.println();
//		}
		
		//parent[n]为最终的前驱矩阵
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				p[i][j]=phi[n][i][j];
			}
		}
		
		return D[n];		
	}
	
	
	
	public void print_matrix(int[][] w){
		for (int i = 0; i < w.length; i++) {
			System.out.println(Arrays.toString(w[i]));
		}
	}
	
	public void print_matrix(boolean[][] w){
		for (int i = 0; i < w.length; i++) {
			System.out.println(Arrays.toString(w[i]));
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		All_Pairs_Shortest_Paths apsp=new All_Pairs_Shortest_Paths();
		Weighted_Adjacent_Matrix W=GraphFactory.getWeightedAdjacentMatrixInstance("graph\\25-1.txt");
		int[][] w=W.getMatrix();
		int n=W.getSize();
//		apsp.print_matrix(w);
//		int[][] L=apsp.slow_all_pairs_shortest_paths(W);
////		int[][] L=apsp.faster_all_pairs_shortest_paths(W);
//		apsp.print_matrix(L);
//		
//		
//		int[][] parent=apsp.cal_parent(L, W.getMatrix());
//		apsp.print_matrix(parent);
//		
//		
		apsp.print_all_pairs_shortest_path(W);
		
//		
//		
//		int[][] p=new int[n][n];
////		for (int i = 0; i < p.length; i++) {
////			for (int j = 0; j < p[0].length; j++) {
////				p[i][j]=-2;
////			}
////		}
//		
//		//当图存在权重和为负值的环路时，计算得到的parent和cal_parent不一样
//		int[][] L_p=apsp.slow_all_pairs_shortest_paths_with_parent(W, p);
//		apsp.print_matrix(L_p);
//		System.out.println();
//		apsp.print_matrix(p);
		
		
//		apsp.floyd_warshall(w);
		
		
//		int[][] fw_p=new int[n][n];
//		int[][] D=apsp.floyd_warshall_with_parent(w, fw_p);
//		apsp.print_matrix(D);
//		apsp.print_matrix(fw_p);
//		
//		apsp.transitive_closure(w);
		
		
		apsp.print_all_pairs_shortest_path_using_phi(W);
	}

}
