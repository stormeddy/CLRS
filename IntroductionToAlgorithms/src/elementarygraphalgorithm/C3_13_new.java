package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 网上是对每个节点DFS，寻找BLACK的点
 * 我的方法对网上进行了改进，就是不是对每个节点都DFS，而是比如第一次对1进行DFS，经过2 3 4，则第二次不需要对2 3 4进行DFS了，而是DFS其他点，当DFS其他点之前需要清空color
 * O(V(V+E))
 * @author xiazdong
 *
 */

public class C3_13_new {
	private static int color[];
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private static Adjacent_List g;
	private static Adjacent_List undirected_g;
	private static int [] cc ;//弱连通分量编号
	private static int weight = -1;	//记录有多少个弱连通图
	private static boolean flag = true;	//最终判断是否为单连通
	private static int parent[];
	public static void main(String[] args) throws Exception {
		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22.3_13_new.txt");
		C3_13_new.g = adjlist;
		//System.out.println(singly_connected());
		System.out.println(single_connected_2());
	}
	public static boolean single_connected_2() throws Exception{
		//1.初始化各个变量
		cc = new int[g.getSize()];
		for(int i=0;i<cc.length;i++){
			cc[i] = 0;
		}
		color = new int[g.getSize()];
		parent = new int[g.getSize()];
		for(int a = 0;a<color.length;a++)  color[a] = WHITE;
		for(int a = 0;a<parent.length;a++) parent[a] = -1;
		
		//2.构造无向图
		System.out.println("=============\n第一步：构造无向图：\n============");
		undirected_g = new Adjacent_List(g.getSize());	//undirected_g为原图g的无向图，用来求弱连通子图
		constructUndirectedGraph(g);	//构造无向图
		undirected_g.printAllEdges();
		System.out.println("=============\n第二步：求无向图的连通分支\n========");
		//2.对无向图求连通分支
		calculateConnectedComponents(undirected_g);
		List<List<String>> weak_connected_lists = new ArrayList<List<String>>();
		for(int i=0;i<=weight;i++){
			weak_connected_lists.add(i, new ArrayList<String>());
		}
		for(int i=0;i<cc.length;i++){
			if(weak_connected_lists.get(cc[i]).add(g.getVertexValue(i)));
		}
		System.out.println("弱连通分支：");
		for(int i=0;i<weak_connected_lists.size();i++){
			System.out.println(weak_connected_lists.get(i).toString());
		}
		
		//3.将原图分成多个有向连通图
		System.out.println("=============\n第三步：构造多个连通有向图\n========");
		Adjacent_List weak_connected_graph[] = new Adjacent_List[weight+1];//weak_connected_graph为多个有向连通图，这些图拼起来就是图G
		for(int i=0;i<weight+1;i++){
			weak_connected_graph[i] = new Adjacent_List(weak_connected_lists.get(i).size());
		}
		for(int u=0;u<g.getSize();u++){
			Iterator<String> iter = g.getListByVertexIndex(u).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				weak_connected_graph[cc[u]].addEdge(g.getVertexValue(u), vstr);
			}
		}
		for(int i=0;i<weak_connected_graph.length;i++){
			weak_connected_graph[i].printAllEdges();
			System.out.println("====");
		}
		//4.对每个连通有向图进行SCC，并判断SCC和分支图是否为单连通。
		System.out.println("============\n第四步：对每个连通有向图构造分支图，并对每个强连通分支计算是否存在单连通\n===========");
		Adjacent_List component_graph[] = new Adjacent_List[weight+1];
		for(int i=0;i<weight+1;i++){	//对每个连通图进行SCC
			C5_5 alg = new C5_5(weak_connected_graph[i]);
			//弱连通图中的分支图
			component_graph[i] = alg.constructComponentGraph();
			int component_size = alg.getComponentSize();	
			List<List<String>>  componentList =  alg.getComponentList();
			int strong_weight[] = alg.getWeight();
			//每个元素是一个弱连通图中的一个强连通分量图
			Adjacent_List scc_graph[] = new Adjacent_List[component_size];
			for(int j=0;j<scc_graph.length;j++){
				scc_graph[j] = new Adjacent_List(componentList.get(j).size());
			}
			for(int u=0;u<weak_connected_graph[i].getSize();u++){
				Iterator<String> iter = weak_connected_graph[i].getListByVertexIndex(u).iterator();
				while(iter.hasNext()){
					String vstr = iter.next();
					if(strong_weight[u]==strong_weight[weak_connected_graph[i].getVertexIndex(vstr)]){
						scc_graph[strong_weight[u]-1].addEdge(weak_connected_graph[i].getVertexValue(u), vstr);	//将边加入到相应的强连通分支中
					}
				}
			}
			System.out.println("**************");
			for(int z=0;z<scc_graph.length;z++){
				scc_graph[z].printAllEdges();
				System.out.println("***********");
			}
			
			//对强连通分支判断
			for(int y=0;y<scc_graph.length;y++){
				judgeForwardEdge(scc_graph[y]);
				if(!flag) return false;
			}
			//对分支图判断
			Adjacent_List com_g = component_graph[i];
			com_g.printAllEdges();
			int[] indegree = new int[com_g.getSize()];
			for(int j=0;j<indegree.length;j++){
				indegree[j] = 0;
			}
			for(int k=0;k<com_g.getSize();k++){
				Iterator<String> iter = com_g.getListByVertexIndex(k).iterator();
				while(iter.hasNext()){
					String vstr = iter.next();
					int v = com_g.getVertexIndex(vstr);
					indegree[v]++;
				}
			}
			//2.找出入度为0的点.
			for(int c=0;c<indegree.length;c++){
				if(indegree[c]==0){
					judgeForwardEdge(com_g);
					if(!flag) return false;
				}
			}
			if(!flag) return false;
		}
		return flag;
		
	}

	private static void judgeForwardEdge(Adjacent_List g) {
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
		}
		
		for(int i=0;i<g.getSize();i++){
			if(color[i]==WHITE){
				DFS_VISIT3(g,i);
			}
		}
	}
	private static void DFS_VISIT3(Adjacent_List g, int u) {
		color[u] = GRAY;
		List<String> list = g.getListByVertexIndex(u);
		for(int i=0;i<list.size();i++){
			int v = g.getVertexIndex(list.get(i));
			if(color[v]==WHITE) {
				DFS_VISIT3(g,v);
			}
			else if(color[v]==BLACK){
				//存在前向边或者横向边，不是单连通图
				flag = false;
			}
		}
		color[u] = BLACK;
	}
	/**
	 * 输入参数为无向图，求无向图的连通分支
	 * 连通分支范围为0~weight,假设有n个连通分支，则weight=n-1
	 */
	private static void calculateConnectedComponents(Adjacent_List g) {
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
			parent[i] = -1;
		}
		weight = -1;
		for(int i=0;i<g.getSize();i++){
			if(color[i]==WHITE){
				weight++;
				DFS_VISIT2(g,i,weight);
			}
		}
	}
	private static void DFS_VISIT2(Adjacent_List g,int u,int weight) {
		color[u] = GRAY;
		cc[u] = weight;
		List<String> list = g.getListByVertexIndex(u);
		for(int i=0;i<list.size();i++){
			int v = g.getVertexIndex(list.get(i));
			if(color[v]==WHITE) {
				DFS_VISIT2(g,v,weight);
			}
		}
		color[u] = BLACK;
	}
	/**
	 * 有向图变无向图
	 * @param g
	 */
	private static void constructUndirectedGraph(Adjacent_List g) {
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
		}
		for(int i=0;i<g.getSize();i++){
			if(color[i]==WHITE)
				DFS_VISIT1(i);
		}
	}
	private static void DFS_VISIT1(int u) {
		color[u] = GRAY;
		List<String> list = g.getListByVertexIndex(u);
		for(int i=0;i<list.size();i++){
			int v = g.getVertexIndex(list.get(i));
			//边(u,v)没有被访问过
			if(parent[u]!=v){
				undirected_g.addEdge(g.getVertexValue(u), list.get(i));
				undirected_g.addEdge(list.get(i), g.getVertexValue(u));
			}
			if(color[v]==WHITE) {
				parent[v] = u;
				DFS_VISIT1(v);
			}
		}
		color[u] = BLACK;
	}
	
}
