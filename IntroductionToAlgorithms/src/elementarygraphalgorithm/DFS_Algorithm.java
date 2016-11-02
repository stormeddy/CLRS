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
	private int cell[];//��ͨ�������
	private int size;
	private int d[];
	private int f[];
	private int time;
	private int rcell;
	private int parent[];
	private Adjacent_List G;
	private HashSet<Edge> T_Set;//���߼�
	private HashSet<Edge> B_Set;//����߼�
	private HashSet<Edge> F_Set;//ǰ��߼�
	private HashSet<Edge> C_Set;//����߼�
	
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
	//������
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
			//����index����ɫȷ����(i,index)Ϊ�������͵ı�
			if(color[index]==WHITE){
				parent[index] = i;
				T_Set.add(new Edge(i, index));
				DFS_VISIT(index,rcell);
			}else if (color[index]==GRAY) {
				B_Set.add(new Edge(i, index));
			}else {
				//��������ͼ��һ���ɾ��
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
		//�жϸñ߼����Ƿ�����ñ�
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
		//�ǵݹ�汾
		if (color[v]==BLACK) {
			return;
		}
		Stack<Integer> s=new Stack<Integer>();
		s.push(v);
//		if (color[v]==WHITE) {
//			//�ýڵ�δ��̽���������µ�Դ�ڵ�
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
				if(parent[u]!=-1)//�������Դ���
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
							//�˴��������ϵ����ӿ�ɾ��
							F_Set.add(new Edge(u, index));
						} else {
							C_Set.add(new Edge(u, index));
						}
					}				
				}
			}else {
				//��ʱcolor[u]==GRAY
				List<String> list=G.getListByVertexIndex(u);
				for (int i = 0; i < list.size(); i++) {
					String value=list.get(i);
					int index=G.getVertexIndex(value);
					//�ñ������������ߣ����Ȼ����ǰ���
					if(!judge(T_Set, new Edge(u, index))&&d[u] < d[index]){
						F_Set.add(new Edge(u, index));
					}
				}//��������ͼelse���˴���ɾ��				
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
	 * ���DFS��ÿ���ڵ��dֵ��fֵ
	 */
	public void print_d_f(){
		for(int i=0;i<size;i++){
			System.out.println(G.getVertexValue(i)+"("+d[i]+"/"+f[i]+")");
		}
	}
	
	/**
	 * ���DFS��ÿ���ڵ����ͨ�������
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
		dfs_alg.print_d_f();	//���d��f����
		dfs_alg.print_cell();
	}
}
