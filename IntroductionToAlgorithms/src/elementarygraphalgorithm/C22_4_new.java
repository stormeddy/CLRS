package elementarygraphalgorithm;

import java.util.List;

public class C22_4_new {
	//����ÿ���ڵ�ɴ�ڵ��б����С�Ľڵ�
	//Ҳ��Ԥ�Ƚ�ͼSCC������ÿ��SCC���Ϊ���нڵ����С��ţ�Ȼ���ô˴��ķ���
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private static int color[];
	private static int L[];
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		C22_4_new c=new C22_4_new();		
		Adjacent_List G = GraphFactory.getAdjacentListInstance("graph\\dfs_input.txt");		
		c.calculate_reachability(G);
		for (int i = 0; i < G.getSize(); i++) {
			if (L[i]==Integer.MAX_VALUE) {
				System.out.println(G.getVertexValue(i)+"("+i+")"+" "+"null"+"("+L[i]+")");
			}else {
				System.out.println(G.getVertexValue(i)+"("+i+")"+" "+G.getVertexValue(L[i])+"("+L[i]+")");
			}
		}
	}

	public void calculate_reachability(Adjacent_List G){
		L = new int[G.getSize()];
		for (int i = 0; i < L.length; i++) {
			L[i]=Integer.MAX_VALUE;
		}
		color=new int[G.getSize()];
		for (int i = 0; i < color.length; i++) {
			color[i]=WHITE;
		}
		Transposition_AdjacentList_Algorithm tr=new Transposition_AdjacentList_Algorithm(G);
		Adjacent_List GT=tr.transposeG();
		DFS(G, GT);		
	}
	
	public void DFS(Adjacent_List G,Adjacent_List GT){
		for(int i=0;i<G.getSize();i++){
			int t=GT.getVertexIndex(G.getVertexValue(i));
			if(color[t]==WHITE){				
				DFS_VISIT(G,GT,i,t);
			}
		}
	}
	
	public void DFS_VISIT(Adjacent_List G,Adjacent_List GT, int root,int i) {
		color[i] = GRAY;		
		List<String> list=GT.getListByVertexIndex(i);
		if (G.getVertexValue(root).equals(GT.getVertexValue(i))&&list.size()!=0) {
			//������������������ĸ���ֱ����ͼG�п�����ɴﵽ��С��Žڵ�
			int g_index=G.getVertexIndex(GT.getVertexValue(i));
			List<String> g_list=G.getListByVertexIndex(root);
			for(int j=0;j<g_list.size();j++){
				String value = g_list.get(j);
				int cur=G.getVertexIndex(value);
				if (cur<L[g_index]) {
					L[g_index]=cur;
				}
			}
		}
		for(int j=0;j<list.size();j++){
			String value = list.get(j);
			int index = GT.getVertexIndex(value);	
			
			if(color[index]==WHITE){		
				L[G.getVertexIndex(value)]=root;
				DFS_VISIT(G,GT,root,index);
			}
		}
		if(GT.getListByVertexIndex(i).size()==0&&L[G.getVertexIndex(GT.getVertexValue(i))]==Integer.MAX_VALUE){
			//�õ����Ϊ0���������������Ϊ�����ýڵ�,��Ϊ�ýڵ㻹δ��������
			color[i]=WHITE;
		}
		else {
			color[i] = BLACK;
		}
	}
	
}
