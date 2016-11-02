package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class C22_2 {
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private int color[];
	private int size;
	private int d[];
	private int f[];
	private int time;
	private int parent[];
	private Adjacent_List G;
	private List<String> cutVertexList;
	private List<Edge> cutEdgeList;
//	private List<Edge> bccList;
	private Adjacent_List connected_graphs[];
	private int weight[];
	private int cc;
	private int final_count;
	private int bcc[][];//用矩阵表示的边
	public C22_2(Adjacent_List g){
		this.G = g;
		size = g.getSize();
		parent = new int[size];
		color = new int[size];
		d = new int[size];
		f = new int[size];
		final_count = 0;
		bcc = new int[size][size];
		for(int i=0;i<bcc.length;i++){
			for(int j=0;j<bcc[i].length;j++){
				bcc[i][j] = 0;
			}
		}
		cutEdgeList = new ArrayList<Edge>();
		cutVertexList = new ArrayList<String>();
//		bccList = new ArrayList<Edge>();
		weight = new int[size];
		for(int i=0;i<size;i++){
			d[i] = 0;
			f[i] = 0;
			color[i] = WHITE;
			parent[i] = -1;
			weight[i] = 0;
		}
	}
	
	
	
	//注意！！！！ 以下函数需要分开执行，不能一起执行
	public static void main(String[] args) throws Exception {
		Adjacent_List g = GraphFactory.getAdjacentListInstance("graph\\22-2.txt");
		C22_2 alg = new C22_2(g);
		
		//************c)****
//		int[] ret=alg.calculate_low(g);
//		for (int i = 0; i < ret.length; i++) {
//			System.out.println(g.getVertexValue(i)+" "+ret[i]);
//		}
		
		
		
		//************d)******
//		List<String> cutlist = alg.getCutVertex(g);
//		System.out.println("割点：");
//		for(String str:cutlist){
//			System.out.print(str+" ");
//		}
		
		
		
		//************f)*********
//		List<Edge> cutEdges = alg.getCutEdge();
//		System.out.println("\n====================");
//		System.out.println("\n割边：");
//		for(Edge e:cutEdges)
//			System.out.println(e);
		
		//************h)*********
		System.out.println("====================");
		alg.calculate_bcc(g);
		
		
	}
	//求出low数组
	public int[] calculate_low(Adjacent_List g){
		int[] low = new int[g.getSize()];
		for(int i=0;i<low.length;i++){
			low[i] = 0;
			color[i]=WHITE;
		}
		DFS_VISIT(g,0,low);
		return low;
		
	}
	private void DFS_VISIT(Adjacent_List g,int i,int[]low) {
		color[i] = GRAY;
		time++;
		d[i] = time;
		low[i] = d[i];
		for(int j=0;j<g.getListByVertexIndex(i).size();j++){
			String value = g.getListByVertexIndex(i).get(j);
			int index = g.getVertexIndex(value);
			if(color[index]==WHITE){
				parent[index] = i;
				DFS_VISIT(g,index,low);
				low[i] = Math.min(low[i], low[index]);
			}
			else if(color[index]==GRAY&&parent[i]!=index){
				//对于边(i,index),先搜索i,而不是先搜索index
				low[i] = Math.min(low[i], d[index]);
			}
			
		}
		time++;
		f[i]=time;
		color[i] = BLACK;
	}
	public void deleteCutEdge(int[] low){
		color = new int[G.getSize()];
		for(int i=0;i<G.getSize();i++){
			color[i] = WHITE;
		}
		DFS_VISIT4(0,low);
	}
	public List<Edge> getCutEdge(){
		int[] low = calculate_low(G);
//		for(int i=0;i<G.getSize();i++){
//			color[i] = WHITE;
//		}
//		//这种方法也可以,判断条件为low[index]>d[i],此时可以不用在DFS_VISIT4中删除割边
//		DFS_VISIT4(0,low);
		for (int i = 0; i < low.length; i++) {
			if (i!=0 && d[i]==low[i]) {
				//根节点不考虑
				//d[i]==low[i]时边(parent[i],i)为割边
				cutEdgeList.add(new Edge(G.getVertexValue(parent[i]),G.getVertexValue(i)));
			}
		}
		return cutEdgeList;
	}
	private void DFS_VISIT4( int i,int[]low) {
		color[i] = GRAY;
		for(int j=0;j<G.getListByVertexIndex(i).size();j++){
			String value = G.getListByVertexIndex(i).get(j);
			int index = G.getVertexIndex(value);
			if(color[index]==WHITE){
				DFS_VISIT4(index,low);
				//******************判断割边*********************
				if(low[index]>d[i]){
					cutEdgeList.add(new Edge(G.getVertexValue(i),G.getVertexValue(index)));
					G.getListByVertexIndex(i).remove(j);	//删除两条边，因为是无向图
					G.getListByVertexIndex(index).remove(G.getVertexValue(i));
				}
				
			}
			
		}
		time++;
		color[i] = BLACK;
	}
	/**
	 * 获得g的割点
	 * @param g
	 * @return
	 */
	public List<String> getCutVertex(Adjacent_List g){
		cutVertexList = new ArrayList<String>();
		color = new int[g.getSize()];	
		int[] low = calculate_low(g);			
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
		}
		DFS_VISIT2(g,0,low);
//		for (int i = 0; i < d.length; i++) {
//			if (parent[i]>0 && low[i]>=d[parent[i]]) {
//				//判断非根节点是否为割点
//				//i不是根节点且parent[i]也不是根节点,此时 parent[i] > 0
//				if(!cutVertexList.contains(g.getVertexValue(parent[i])))
//					cutVertexList.add(g.getVertexValue(parent[i]));
//			}
//		}
		//******************判断根节点是否为割点********************
		int count = 0;
		for(int i=0;i<parent.length;i++){
			if(parent[i]==0) count++;
		} 
		if(count>=2) cutVertexList.add(g.getVertexValue(0));
		return cutVertexList;
	}
	/**
	 * 此函数用于添加割点到cutVertexList中
	 * @param g
	 * @param i
	 * @param low
	 */
	private void DFS_VISIT2(Adjacent_List g, int i,int[] low) {
		color[i] = GRAY;
		for(int j=0;j<g.getListByVertexIndex(i).size();j++){
			String value = g.getListByVertexIndex(i).get(j);
			int index = g.getVertexIndex(value);
			if(color[index]==WHITE){
				DFS_VISIT2(g,index,low);
				//******************判断非根节点是否为割点********************
				if(low[index]>=d[i]&&parent[i]!=-1){
					if(!cutVertexList.contains(g.getVertexValue(i)))
						cutVertexList.add(g.getVertexValue(i));
				}
			}
		}
		color[i] = BLACK;
	}
	
	private void calculate_bcc(Adjacent_List g1){
		//1.去桥边
		int[] low = calculate_low(g1);
		deleteCutEdge(low);
		System.out.println("===第一步：去除桥边======");
		System.out.println("图中边如下:");
		g1.printAllEdges();
		//现已成功去除桥边
		//2.构建连通子图数组
		DFS1();	//为连通的点标识一样的weight
		List<Integer> weightlist = new ArrayList<Integer>();
		for(int i=0;i<cc;i++){
			weightlist.add(0);
		}
		//计算每个连通子图的节点个数
		for(int i=0;i<weight.length;i++){
			int a = weightlist.get(weight[i]-1);
			weightlist.remove(weight[i]-1);
			weightlist.add(weight[i]-1, ++a);
		}
		//cc为去桥边后连通子图个数
		connected_graphs = new Adjacent_List[cc];
		
		for(int i=0;i<connected_graphs.length;i++){
			connected_graphs[i] = new Adjacent_List(weightlist.get(i));
		}
		
		//3.遍历每条边，并填到新图中
		for(int i=0;i<size;i++){
			connected_graphs[weight[i]-1].addVertex(G.getVertexValue(i));
			Iterator<String> iter = G.getListByVertexIndex(i).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				int v = G.getVertexIndex(vstr);
				connected_graphs[weight[i]-1].addEdge(G.getVertexValue(i), G.getVertexValue(v));
			}
		}
		System.out.println("第三步：构建新的连通子图");
		for(int i=0;i<connected_graphs.length;i++){
			System.out.println("****************");
			connected_graphs[i].printAllEdges();
			System.out.println("****************");
		}
		
		//4.对于每个连通图，如果有割点，就去除割点
		
		for(int i=0;i<connected_graphs.length;i++){
			Adjacent_List g = connected_graphs[i];
			List<String> list = getCutVertex(g);
			g.printAllEdges();
			if(list.size()==0){
				final_count++;
				for(int a=0;a<g.getSize();a++){
					Iterator<String> iter = g.getListByVertexIndex(a).iterator();
					while(iter.hasNext()){
						String vstr = iter.next();
						bcc[G.getVertexIndex(g.getVertexValue(a))][G.getVertexIndex(vstr)] = final_count;
					}
				}
			}
			else{	//有割点
				//去除割点
				Adjacent_List gc=g.clone_adjacent_list();//复制图g
				for(int a=0;a<gc.getSize();a++){
					if(!list.contains(gc.getVertexValue(a))){
						Iterator<String> iter = gc.getListByVertexIndex(a).iterator();
						while(iter.hasNext()){
							String vstr = iter.next();
							if(list.contains(vstr)){
								iter.remove();
							}
						}
					}else {
						//去除割点所在的边
						gc.getListByVertexIndex(a).clear();
					}
				}
				//DFS求连通分支，一边求连通分支一遍填bcc二维数组
//				System.out.println();
//				gc.printAllEdges();
				DFS3(gc,list);
				
				//对割点连接的每条边，判断bcc值
				for(int a=0;a<list.size();a++){
					List<String> l = g.getListByVertexValue(list.get(a));
					for(int b = 0;b<l.size();b++){
						//找到节点所在的边(G.getVertexIndex(l.get(b)),index)(以索引表示的边)对应的bcc不为0的值
						List<String> temp=g.getListByVertexIndex(g.getVertexIndex(l.get(b)));
						int index=0;
						for(String e:temp){
							index=G.getVertexIndex(e);
							if (bcc[G.getVertexIndex(l.get(b))][index]!=0) {
								break;
							}
						}
//						int index = G.getVertexIndex(g.getListByVertexIndex(g.getVertexIndex(l.get(b))).get(0));
						bcc[G.getVertexIndex(l.get(b))][G.getVertexIndex(g.getVertexValue(a))] = bcc[G.getVertexIndex(l.get(b))][index];
						bcc[G.getVertexIndex(g.getVertexValue(a))][G.getVertexIndex(l.get(b))] = bcc[G.getVertexIndex(l.get(b))][index];
					}
				}
			}
		}
		G.printVertexMap();
		//输出bcc[]
		System.out.println("bcc数组的值：");
		for(int i=0;i<bcc.length;i++){
			for(int j=0;j<bcc[i].length;j++){
				System.out.print(bcc[i][j]+" ");
			}
			System.out.println();
		}
	}
	private void DFS3(Adjacent_List g,List<String>list) {
		color = new int[g.getSize()];
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
		}
		for(int i=0;i<g.getSize();i++){
			//遍历非割点,给非割点构成的边对应的bcc赋值
			if(color[i]==WHITE && !list.contains(g.getVertexValue(i)) ){
				final_count ++ ;
				DFS_VISIT3(g,i);
			}
		}
	}
	private void DFS_VISIT3(Adjacent_List g, int i) {
		color[i] = GRAY;
		for(int j=0;j<g.getListByVertexIndex(i).size();j++){
			String value = g.getListByVertexIndex(i).get(j);
			int index = g.getVertexIndex(value);
			if(color[index]==WHITE){
				bcc[G.getVertexIndex(g.getVertexValue(i))][G.getVertexIndex(value)] = final_count;
				bcc[G.getVertexIndex(value)][G.getVertexIndex(g.getVertexValue(i))] = final_count;
				DFS_VISIT3(g,index);
			}
		}
		color[i] = BLACK;
	}
	//h)
	public void DFS1(){
		for(int i=0;i<size;i++){
			color[i] = WHITE;
		}
		cc = 0;
		for(int i=0;i<G.getSize();i++){			
			if(color[i]==WHITE){
				cc++;
				DFS_VISIT1(i,cc);
			}
		}
	}
	private void DFS_VISIT1(int i, int cc) {
		color[i] = GRAY;
		weight[i] = cc;
		for(int j=0;j<G.getListByVertexIndex(i).size();j++){
			String value = G.getListByVertexIndex(i).get(j);
			int index = G.getVertexIndex(value);
			if(color[index]==WHITE){
				DFS_VISIT1(index,cc);
			}
		}
		color[i] = BLACK;
	}
	public void printLow(int[]low){
		System.out.println("low数组的值:");
		for(int i=0;i<low.length;i++){
			System.out.println(G.getVertexValue(i)+"="+low[i]+" ");
		}
	}
	
	class Edge{
		String begin;
		String end;
		int cc;
		public Edge(String begin, String end) {
			this.begin = begin;
			this.end = end;
		}
		public Edge(String begin, String end,int cc) {
			this.begin = begin;
			this.end = end;
			this.cc = cc;
		}
		@Override
		public String toString() {
			if(cc==0)
				return "Edge [begin=" + begin + ", end=" + end + "]";
			else
				return "Edge [begin=" + begin + ", end=" + end +", bcc="+cc+"]";
		}
	}
}
