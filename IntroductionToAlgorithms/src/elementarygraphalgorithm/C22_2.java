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
	private int bcc[][];//�þ����ʾ�ı�
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
	
	
	
	//ע�⣡������ ���º�����Ҫ�ֿ�ִ�У�����һ��ִ��
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
//		System.out.println("��㣺");
//		for(String str:cutlist){
//			System.out.print(str+" ");
//		}
		
		
		
		//************f)*********
//		List<Edge> cutEdges = alg.getCutEdge();
//		System.out.println("\n====================");
//		System.out.println("\n��ߣ�");
//		for(Edge e:cutEdges)
//			System.out.println(e);
		
		//************h)*********
		System.out.println("====================");
		alg.calculate_bcc(g);
		
		
	}
	//���low����
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
				//���ڱ�(i,index),������i,������������index
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
//		//���ַ���Ҳ����,�ж�����Ϊlow[index]>d[i],��ʱ���Բ�����DFS_VISIT4��ɾ�����
//		DFS_VISIT4(0,low);
		for (int i = 0; i < low.length; i++) {
			if (i!=0 && d[i]==low[i]) {
				//���ڵ㲻����
				//d[i]==low[i]ʱ��(parent[i],i)Ϊ���
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
				//******************�жϸ��*********************
				if(low[index]>d[i]){
					cutEdgeList.add(new Edge(G.getVertexValue(i),G.getVertexValue(index)));
					G.getListByVertexIndex(i).remove(j);	//ɾ�������ߣ���Ϊ������ͼ
					G.getListByVertexIndex(index).remove(G.getVertexValue(i));
				}
				
			}
			
		}
		time++;
		color[i] = BLACK;
	}
	/**
	 * ���g�ĸ��
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
//				//�жϷǸ��ڵ��Ƿ�Ϊ���
//				//i���Ǹ��ڵ���parent[i]Ҳ���Ǹ��ڵ�,��ʱ parent[i] > 0
//				if(!cutVertexList.contains(g.getVertexValue(parent[i])))
//					cutVertexList.add(g.getVertexValue(parent[i]));
//			}
//		}
		//******************�жϸ��ڵ��Ƿ�Ϊ���********************
		int count = 0;
		for(int i=0;i<parent.length;i++){
			if(parent[i]==0) count++;
		} 
		if(count>=2) cutVertexList.add(g.getVertexValue(0));
		return cutVertexList;
	}
	/**
	 * �˺���������Ӹ�㵽cutVertexList��
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
				//******************�жϷǸ��ڵ��Ƿ�Ϊ���********************
				if(low[index]>=d[i]&&parent[i]!=-1){
					if(!cutVertexList.contains(g.getVertexValue(i)))
						cutVertexList.add(g.getVertexValue(i));
				}
			}
		}
		color[i] = BLACK;
	}
	
	private void calculate_bcc(Adjacent_List g1){
		//1.ȥ�ű�
		int[] low = calculate_low(g1);
		deleteCutEdge(low);
		System.out.println("===��һ����ȥ���ű�======");
		System.out.println("ͼ�б�����:");
		g1.printAllEdges();
		//���ѳɹ�ȥ���ű�
		//2.������ͨ��ͼ����
		DFS1();	//Ϊ��ͨ�ĵ��ʶһ����weight
		List<Integer> weightlist = new ArrayList<Integer>();
		for(int i=0;i<cc;i++){
			weightlist.add(0);
		}
		//����ÿ����ͨ��ͼ�Ľڵ����
		for(int i=0;i<weight.length;i++){
			int a = weightlist.get(weight[i]-1);
			weightlist.remove(weight[i]-1);
			weightlist.add(weight[i]-1, ++a);
		}
		//ccΪȥ�űߺ���ͨ��ͼ����
		connected_graphs = new Adjacent_List[cc];
		
		for(int i=0;i<connected_graphs.length;i++){
			connected_graphs[i] = new Adjacent_List(weightlist.get(i));
		}
		
		//3.����ÿ���ߣ������ͼ��
		for(int i=0;i<size;i++){
			connected_graphs[weight[i]-1].addVertex(G.getVertexValue(i));
			Iterator<String> iter = G.getListByVertexIndex(i).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				int v = G.getVertexIndex(vstr);
				connected_graphs[weight[i]-1].addEdge(G.getVertexValue(i), G.getVertexValue(v));
			}
		}
		System.out.println("�������������µ���ͨ��ͼ");
		for(int i=0;i<connected_graphs.length;i++){
			System.out.println("****************");
			connected_graphs[i].printAllEdges();
			System.out.println("****************");
		}
		
		//4.����ÿ����ͨͼ������и�㣬��ȥ�����
		
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
			else{	//�и��
				//ȥ�����
				Adjacent_List gc=g.clone_adjacent_list();//����ͼg
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
						//ȥ��������ڵı�
						gc.getListByVertexIndex(a).clear();
					}
				}
				//DFS����ͨ��֧��һ������ͨ��֧һ����bcc��ά����
//				System.out.println();
//				gc.printAllEdges();
				DFS3(gc,list);
				
				//�Ը�����ӵ�ÿ���ߣ��ж�bccֵ
				for(int a=0;a<list.size();a++){
					List<String> l = g.getListByVertexValue(list.get(a));
					for(int b = 0;b<l.size();b++){
						//�ҵ��ڵ����ڵı�(G.getVertexIndex(l.get(b)),index)(��������ʾ�ı�)��Ӧ��bcc��Ϊ0��ֵ
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
		//���bcc[]
		System.out.println("bcc�����ֵ��");
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
			//�����Ǹ��,���Ǹ�㹹�ɵı߶�Ӧ��bcc��ֵ
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
		System.out.println("low�����ֵ:");
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
