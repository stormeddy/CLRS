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
		//�������·��Ȩ�ؾ���
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
		//ͬʱ�������·��Ȩ�ؾ����ǰ������
		int n=L.length;
		int[][] Lp=new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Lp[i][j]=L[i][j];//��������󣬴�ʱֻ�Ա仯�˵�Lp���¶�Ӧ��parent
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
		//���L֮������parent�����ܻ���bug������Ǽ���L��ͬʱ���parent
		int n=L.length;
		int[][] parent=new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {				
				for (int k = 0; k < n; k++) {
//					if ( L[i][j]==L[i][k]+w[k][j] && ( (w[i][j]!=Integer.MAX_VALUE && j!=k) || (i!=k && j!=k) ) ) {
				    if ( L[i][j]==L[i][k]+w[k][j] && j!=k ) {
						//1.i��j֮�����i->j�ıߣ���ʱҪ��j��ǰ������Ϊj��	2.i��j֮�䲻����i->j�ıߣ���ʱҪ��j��ǰ����Ϊj��i(��ʱǰ��Ҳ������Ϊ)
						//�����������д��j!=k
						parent[i][j]=k;
					}				
				}
			}
		}
		
		for (int i = 0; i < n; i++) {
			//ͬһ���ڵ�֮��û��ǰ���ڵ�
			parent[i][i]=-1;
		}
		return parent;
	}
	
	public void print_all_pairs_shortest_path(Weighted_Adjacent_Matrix W){
		//������нڵ�Ե����·��
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
		//������нڵ�Ե����·��,ʹ��phi
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
				System.out.print(W.getVertexValue(j)+" ");//i==j������¿��Բ��������һ��
				System.out.println();
			}
			System.out.println();
		}
	}
	
	
	public void print_shortest_path_using_phi(int[][] phi,int i,int j,Weighted_Adjacent_Matrix W){
		//phi[i][j]Ϊi->j���м�ڵ�
		//���ﲻͬ��print_shortest_path,i�����ܵ���j
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
		//ʹ�÷���ƽ����
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
		
		//�ж��Ƿ����Ȩ��Ϊ��ֵ�Ļ�·
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
		
//		int len=0;//��̳��ȵ�Ȩ��Ϊ��ֵ�Ļ�·�ĳ���
//		int[][] preL=new int[n][n];
		
		for (int m = 2; m <= n-1; m++) {
			
			//������̳��ȵ�Ȩ��Ϊ��ֵ�Ļ�·�ĳ���
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
		//�˴������ϲ�ͬ����Ϊ��extend_shortest_paths_with_parent()��
		//Lp[i][j]=L[i][j](���������ϵ������)
		//��ˣ���ʼparent��i->j�ı�ʹ��parent[i][j]=i
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
			//ͬһ���ڵ�֮��û��ǰ���ڵ�
			parent[i][i]=-1;
		}
		return L;
	}
	
	
	public int[][] floyd_warshall(Weighted_Adjacent_Matrix W){
		return floyd_warshall(W.getMatrix());
	}
	
	public int[][] floyd_warshall(int[][] w){
		//floyd_warshall�㷨
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
		
		//���D[0]~D[n]
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
						//����D[k-1][i][j]==temp�����������������Ҫ��������д���
						//1.D[k-1][i][j]Ϊ�����  
						//2.i,j,(k-1)������ͬ�ڵ�(i->j�б�,��ʱk-1==j;i��j��ͬһ�ڵ�;���ÿ���k-1==i�����,��������Է�����һ�����д���)
						
					//����ֱ��д��if (D[k-1][i][j]<=temp){
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
		
		//���D[0]~D[n],parent[0]~parent[n]
//		for (int i = 0; i < D.length; i++) {
//			print_matrix(D[i]);
//			System.out.println();
//			print_matrix(parent[i]);
//			System.out.println();
//		}
		
		//parent[n]Ϊ���յ�ǰ������
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
		//��������ͼ�Ĵ��ݱհ�
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
		//���D[0]~D[n]
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
		
		//���D[0]~D[n],parent[0]~parent[n]
//		for (int i = 0; i < D.length; i++) {
//			print_matrix(D[i]);
//			System.out.println();
//			print_matrix(parent[i]);
//			System.out.println();
//		}
		
		//parent[n]Ϊ���յ�ǰ������
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
//		//��ͼ����Ȩ�غ�Ϊ��ֵ�Ļ�·ʱ������õ���parent��cal_parent��һ��
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
