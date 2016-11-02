package singlesourceshortestpaths;

import java.util.List;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class BellmanFord {

	private int[] d;
	private int[] newd;
	private int[] parent;
	private boolean change;
	boolean solved;
	
	public int[] get_d(){
		return d;
	}
	
	public boolean bellman_ford(Weighted_Adjacent_List G,String s){
		init_single_source(G, s);
		int size=G.getSize();
		for (int i = 1; i <= size-1; i++) {
			for (int u = 0; u < size; u++) {
				List<Pair> list=G.getListByVertexIndex(u);
				for (int j = 0; j < list.size(); j++) {
					Pair pair=list.get(j);
					int v=G.getVertexIndex(pair.end);
					relax(u, v, pair.weight);					
				}
			}
		}
		for (int u = 0; u < size; u++) {
			List<Pair> list=G.getListByVertexIndex(u);
			for (int j = 0; j < list.size(); j++) {
				Pair pair=list.get(j);
				int v=G.getVertexIndex(pair.end);
				
				int temp;
				if (d[u]==Integer.MAX_VALUE) {
					temp=Integer.MAX_VALUE;
				}else {
					temp=d[u]+pair.weight;
				}
				if (d[v]>temp) {
					newd[v]=Integer.MIN_VALUE;//对于无解的情况,赋予一个负无穷大的值
					solved=false;//此处可直接返回
				}
			}
		}
		//24.1-6
		if (!solved) {
			int start=-1;
			for (int i = 0; i < d.length; i++) {
				if (newd[i]==Integer.MIN_VALUE) {
					start=i;
					break;
				}
			}
			search_negative_circle(G, start);
		}
		
		return solved;
	}
	
	public void search_negative_circle(Weighted_Adjacent_List G,int start){
		int i=start;
		while (parent[i]!=start) {
			System.out.print(G.getVertexValue(i)+"<--");
			i=parent[i];
		}
		System.out.print(G.getVertexValue(i));
		System.out.println();		
	}
	
	private void init_single_source(Weighted_Adjacent_List G,String s){
		solved=true;
		int size=G.getSize();
		d=new int[size];
		newd=new int[size];
		parent=new int[size];
		for (int i = 0; i < size; i++) {
			d[i]=Integer.MAX_VALUE;
			parent[i]=-1;
		}
		int s_index=G.getVertexIndex(s);
		d[s_index]=0;
	}
	
	private void relax(int u,int v,int weight){
		//松弛操作
		int temp;
		if (d[u]==Integer.MAX_VALUE) {
			temp=Integer.MAX_VALUE;
		}else {
			temp=d[u]+weight;
		}
		if (d[v]>temp) {
			d[v]=temp;
			parent[v]=u;
		}
	}
	
	public void bellman_ford_change(Weighted_Adjacent_List G,String s){
		//24.1-3,若存在从源结点可达的权值和为负的环路,则会无限循环下去.
		init_single_source(G, s);
		change=true;
		int size=G.getSize();
		while (change) {
			change=false;
			for (int u = 0; u < size; u++) {
				List<Pair> list=G.getListByVertexIndex(u);
				for (int j = 0; j < list.size(); j++) {
					Pair pair=list.get(j);
					int v=G.getVertexIndex(pair.end);
					relax_change(u, v, pair.weight);					
				}
			}
		}
		//不用返回是否存在解决方案
/*		for (int u = 0; u < size; u++) {
			List<Pair> list=G.getListByVertexIndex(u);
			for (int j = 0; j < list.size(); j++) {
				Pair pair=list.get(j);
				int v=G.getVertexIndex(pair.end);
				
				int temp;
				if (d[u]==Integer.MAX_VALUE) {
					temp=Integer.MAX_VALUE;
				}else {
					temp=d[u]+pair.weight;
				}
				if (d[v]>temp) {
					return false;
				}
			}
		}
		return true;*/
	}
	
	private void relax_change(int u,int v,int weight){
		int temp;
		if (d[u]==Integer.MAX_VALUE) {
			temp=Integer.MAX_VALUE;
		}else {
			temp=d[u]+weight;
		}
		if (d[v]>temp) {
			d[v]=temp;
			parent[v]=u;
			change=true;
		}
	}
	
	public boolean bellman_ford_modify(Weighted_Adjacent_List G,String s){
		//24.1-5
		init_single_source_modify(G, s);
		int size=G.getSize();
		for (int i = 1; i <= size-1; i++) {
			for (int u = 0; u < size; u++) {
				List<Pair> list=G.getListByVertexIndex(u);
				for (int j = 0; j < list.size(); j++) {
					Pair pair=list.get(j);
					int v=G.getVertexIndex(pair.end);
					relax_modify(u, v, pair.weight);					
				}
			}
		}
		//下面是一种类似于24.1-4的修正,针对无解的情况
		for (int u = 0; u < size; u++) {
			List<Pair> list=G.getListByVertexIndex(u);
			for (int j = 0; j < list.size(); j++) {
				Pair pair=list.get(j);
				int v=G.getVertexIndex(pair.end);
				
				int temp;
				if (d[u]==Integer.MAX_VALUE) {
					temp=Integer.MAX_VALUE;
				}else {
					temp=d[u]+pair.weight;
				}
				if (d[v]>temp) {
					d[v]=Integer.MIN_VALUE;//对于无解的情况,赋予一个负无穷大的值
					solved=false;//此处可直接返回
				}
			}
		}
		return solved;
	}
	
	private void init_single_source_modify(Weighted_Adjacent_List G,String s){
		solved=true;
		int size=G.getSize();
		d=new int[size];
		parent=new int[size];
		for (int i = 0; i < size; i++) {
			d[i]=Integer.MAX_VALUE;
			parent[i]=-1;
		}
//		int s_index=G.getVertexIndex(s);
//		d[s_index]=0;
	}
	
	private void relax_modify(int u,int v,int weight){
		//更新v.d时考虑 u为源点或者,u为其他最短路径上某结点这两种情况.
		int temp=weight+((d[u]<0)?d[u]:0);
		if (d[v]>temp) {
			d[v]=temp;
//			parent[v]=u;
		}
	}
	
	/**
	 * 给定起点、终点，输出路径
	 * @param s
	 * @param t
	 */
	public void print_path(Weighted_Adjacent_List G,String s,String t){
		if (!solved) {
			return;
		}
		int s_index = G.getVertexIndex(s);
		int t_index = G.getVertexIndex(t);
		if(s_index == t_index) System.out.print(s+" ");
		else if(parent[t_index]==-1) System.out.println("no path from "+s + " to "+ t + " exists");
		else {
			print_path(G,s, G.getVertexValue(parent[t_index]));
			System.out.print(t+" ");
		}
	}
	
	public void print_all_paths(Weighted_Adjacent_List G,String source){
		for (int i = 0; i < G.getSize(); i++) {
			String dest=G.getVertexValue(i);
			System.out.print(source+"-->"+dest+":");
			print_path(G, source, dest);
			System.out.println();
		}
	}
	
	/**
	 * 输出从源结点到全部的节点对应的路径距离
	 */
	public void print_AllDistances(Weighted_Adjacent_List G){
		for(int i=0;i<d.length;i++){
			System.out.println(G.getVertexValue(i)+":"+d[i]+" "+newd[i]);
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List G = GraphFactory.getWeightedAdjacentListInstance("graph\\bellman_ford.txt");
		BellmanFord bf=new BellmanFord();
		String source="s";
		boolean solved=bf.bellman_ford(G, source);
		System.out.println(solved);
		
		bf.print_AllDistances(G);
		
		for (int i = 0; i < G.getSize(); i++) {
			String dest=G.getVertexValue(i);
			System.out.print(source+"-->"+dest+":");
			bf.print_path(G, source, dest);
			System.out.println();
		}
		
//		bf.bellman_ford_change(G, source);
//		bf.print_AllDistances(G);
//		
//		for (int i = 0; i < G.getSize(); i++) {
//			String dest=G.getVertexValue(i);
//			System.out.print(source+"-->"+dest+":");
//			bf.print_path(G, source, dest);
//			System.out.println();
//		}
		
//		System.out.println(bf.bellman_ford_modify(G, source));
//		bf.print_AllDistances(G);
	}

}
