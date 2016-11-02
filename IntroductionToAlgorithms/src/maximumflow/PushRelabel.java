package maximumflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Weighted_Adjacent_Matrix;

public class PushRelabel {
	//ǰ��������ǩ�㷨
	
	private int[] h;//�߶�
	private int[] e;//������
	private int[] current;//u.N�����е�ǰ�ڵ�
	
	private void convert_to_capacity(Weighted_Adjacent_Matrix G){
		//Ȩ�ؾ���ͼ��Ȩ�ش�������,ת��Ϊ��������ͼ
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
	
	private void initialize_preflow(Weighted_Adjacent_Matrix G,int[][] f, String s){
		int size=G.getSize();
		h=new int[size];
		e=new int[size];
		for (int i = 0; i < size; i++) {
			h[i]=0;
			e[i]=0;
		}
		
		
		int s_index=G.getVertexIndex(s);
		h[s_index]=size;
		
		int[][] c=G.getMatrix();
		for (int i = 0; i < size; i++) {
			f[s_index][i]=c[s_index][i];
			e[i]=c[s_index][i];
			e[s_index]=e[s_index]-c[s_index][i];
		}
		
	}
	
	private int cf(int[][] c, int[][] f, int u, int v){
		//����д�����cf(u,v)
		if (c[u][v]!=0) {
			return  c[u][v]-f[u][v];
		}else if (c[v][u]!=0) {
			return f[v][u];
		}else {
			return 0;
		}
	}
	
	private void push(int[][] c, int[][] f, int u,int v){
		//���Ͳ���
		int delta=Integer.min(e[u], cf(c, f, u, v));
		if (c[u][v]>0) {
			f[u][v]+=delta;
		}else {
			f[v][u]-=delta;
		}
		e[u]-=delta;
		e[v]+=delta;
	}
	
	private void relabel(int[][] c, int[][] f,int u){
		int min=Integer.MAX_VALUE;
		for (int i = 0; i < e.length; i++) {
			if (cf(c, f, u, i)>0) {
				min=Integer.min(min, h[i]);
			}
		}
		h[u]=1+min;
		//26.5-3 ֻ��u.h��1Ҳ����
		//h[u]=h[u]+1;
	}
	
	private void discharge(Weighted_Adjacent_Matrix G, int[][] c, int[][] f,String ustr, ArrayList<LinkedList<Integer>> neighbour){
		int u=G.getVertexIndex(ustr);
		int ind=0;
		while (e[u]>0) {
			int v=current[u];
			LinkedList<Integer> nei=neighbour.get(u);
//			int len=nei.size();			
			if (v==-1) {
				relabel(c, f, u);
				ind=0;
				current[u]=nei.get(ind);
			}else if (cf(c, f, u, v)>0 && h[u]==h[v]+1) {
				push(c, f, u, v);
			}else {
				++ind;
				current[u]=nei.get(ind);
			}
		}
	}
	
	private ArrayList<LinkedList<Integer>> construct_neighbour(Weighted_Adjacent_Matrix G, String s, String t){
		int[][] c=G.getMatrix();
		int size=G.getSize();
		int s_ind=G.getVertexIndex(s);
		int t_ind=G.getVertexIndex(t);
		ArrayList<LinkedList<Integer>> neighbour=new ArrayList<LinkedList<Integer>>(size);
		for (int i = 0; i < size; i++) {
			LinkedList<Integer> list=new LinkedList<Integer>();
			neighbour.add(list);
		}
		for (int u = 0; u < size; u++) {
			for (int v = 0; v < size; v++) {
				if (c[u][v] > 0) {
					neighbour.get(u).add(v);
					neighbour.get(v).add(u);
				}
			}			
		}
		for (int i = 0; i < size; i++) {
			//-1��ʾNIL
			neighbour.get(i).add(-1);
		}
		neighbour.get(s_ind).clear();
		neighbour.get(t_ind).clear();
		return neighbour;
	}
	
	public int[][] relabel_to_front(Weighted_Adjacent_Matrix G, String s, String t){
		int size=G.getSize();
		int[][] f=new int[size][size];//������
		int[][] c=G.getMatrix();
		convert_to_capacity(G);
		initialize_preflow(G, f, s);
		
		int s_ind=G.getVertexIndex(s);
		int t_ind=G.getVertexIndex(t);
		LinkedList<String> list=new LinkedList<>();
		for (int i = 0; i < size; i++) {
			if (i!=s_ind&&i!=t_ind) {
				list.add(G.getVertexValue(i));
			}			
		}
		
		current=new int[size];
		ArrayList<LinkedList<Integer>> neighbour=construct_neighbour(G, s, t);
		for (int u = 0; u < size; u++) {
			if (u!=s_ind && u!=t_ind) {
				current[u]=neighbour.get(u).get(0);
			}			
		}
		
		int u=0;
		while (u<size-2) {
			String ustr=list.get(u);
			int old_h=h[G.getVertexIndex(ustr)];
			discharge(G, c, f, ustr, neighbour);
			if (h[G.getVertexIndex(ustr)]>old_h) {
				list.remove(u);
				list.addFirst(ustr);
				u=0;
			}
			++u;
		}
		return f;
	}
	
	private void discharge_queue(Weighted_Adjacent_Matrix G, int[][] c, int[][] f,int u, ArrayList<LinkedList<Integer>> neighbour, Queue<Integer> q, int t_ind){
		
		int ind=0;
		while (e[u]>0) {
			int v=current[u];
			LinkedList<Integer> nei=neighbour.get(u);
//			int len=nei.size();			
			if (v==-1) {
				relabel(c, f, u);
				ind=0;
				current[u]=nei.get(ind);
			}else if (cf(c, f, u, v)>0 && h[u]==h[v]+1) {
				push(c, f, u, v);
				if (e[v]>0&&v!=t_ind) {
					//�������֮��v���,��Ҫ��v����q��,���ǻ�ڵ����
					q.add(v);
				}
			}else {
				++ind;
				current[u]=nei.get(ind);
			}
		}
	}
	
	public int[][] push_relabel_queue(Weighted_Adjacent_Matrix G, String s, String t){
		//26.5-2
		int size=G.getSize();
		int[][] f=new int[size][size];//������
		int[][] c=G.getMatrix();
		convert_to_capacity(G);
		initialize_preflow(G, f, s);
		
		int s_ind=G.getVertexIndex(s);
		int t_ind=G.getVertexIndex(t);
		
		Queue<Integer> q=new LinkedList<Integer>();
		for (int i = 0; i < size; i++) {
			if (c[s_ind][i]>0) {
				q.add(i);
			}
		}
		
		ArrayList<LinkedList<Integer>> neighbour=construct_neighbour(G, s, t);
		for (int u = 0; u < size; u++) {
			if (u!=s_ind && u!=t_ind) {
				current[u]=neighbour.get(u).get(0);
			}			
		}
		
		while (!q.isEmpty()) {
			discharge_queue(G, c, f, q.peek(), neighbour, q, t_ind);
			q.poll();
		}
		
		return f;
	}
	
	public void print_matrix(int[][] w){
		for (int i = 0; i < w.length; i++) {
			System.out.println(Arrays.toString(w[i]));
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_Matrix G=GraphFactory.getWeightedAdjacentMatrixInstance("graph\\relabel_input.txt");
		PushRelabel pr=new PushRelabel();
		String s="s";
		String t="t";
		int[][] f=pr.relabel_to_front(G, s, t);
		System.out.println("flow matrix:");
		pr.print_matrix(f);
		
		System.out.println("flow matrix:");
		int[][] f_q=pr.push_relabel_queue(G, s, t);
		pr.print_matrix(f_q);
	}

}
