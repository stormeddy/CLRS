package vebtree;

public class ProtoVEBTree_N_Duplicate {
	//支持重复关键字的ProtoVEBTree
	int n;
	int u;
	int sqrt;
	int a[];
	ProtoVEBTree_N_Duplicate summary;
	ProtoVEBTree_N_Duplicate[] cluster;
	
	public ProtoVEBTree_N_Duplicate(int u){
		n=0;
		this.u=u;
		sqrt=(int)Math.sqrt(u);
		if (this.u==2) {
			a=new int[]{0,0};
		}
		else {
			summary=new ProtoVEBTree_N_Duplicate(sqrt);
			cluster=new ProtoVEBTree_N_Duplicate[sqrt];
			for (int i = 0; i < cluster.length; i++) {
				cluster[i]=new ProtoVEBTree_N_Duplicate(sqrt);
			}
		}
	}
	
	protected int high(ProtoVEBTree_N_Duplicate V,int x){
		return x/V.sqrt;
	}
	
	protected int low(ProtoVEBTree_N_Duplicate V,int x){
		return x%V.sqrt;
	}
	
	private int index(ProtoVEBTree_N_Duplicate V,int x,int y) {
		return x*V.sqrt+y;
	}
	
	/*private boolean isLeaf(ProtoVEBTree_N_Duplicate V){
		return V.u==2;
	}*/
	
	public int member(ProtoVEBTree_N_Duplicate V,int x) {
		if (V.u==2) 
			return V.a[x];
		else 
			return member(V.cluster[high(V, x)], low(V, x));
		
	}
	
	public Integer minimum(ProtoVEBTree_N_Duplicate V){
		if (V.u==2) {
			if (V.a[0]>0) 
				return 0;
			else if(V.a[1]>0)
				return 1;
			else
				return null;
		}
		else {
			Integer min_cluster=minimum(V.summary);
			if(min_cluster==null){
				return null;
			}else{
				int offset=minimum(V.cluster[min_cluster]);
				return index(V, min_cluster, offset);
			}
		}
	}
	
	public Integer maximum(ProtoVEBTree_N_Duplicate V){
		if (V.u==2) {
			if (V.a[1]>0) 
				return 1;
			else if(V.a[0]>0)
				return 0;
			else 
				return null;
		}else {
			Integer max_cluster=maximum(V.summary);
			if (max_cluster==null) {
				return null;
			}else {
				int offset=maximum(V.cluster[max_cluster]);
				return index(V, max_cluster, offset);
			}
		}
	}
	
	public Integer successor(ProtoVEBTree_N_Duplicate V,int x){
		if (V.u==2) {
			if(x==0 && V.a[1]>0)
				return 1;
			else
				return null;
		}else {
			Integer offset=successor(V.cluster[high(V, x)],low(V, x));
			if (offset!=null) {
				return index(V, high(V, x), offset);
			}else {
				Integer succ_cluster=successor(V.summary, high(V, x));
				if (succ_cluster==null) {
					return null;
				}else {
					int succ_offset=minimum(V.cluster[succ_cluster]);
					return index(V, succ_cluster, succ_offset);
				}
			}
		}
	}
	
	public Integer predecessor(ProtoVEBTree_N_Duplicate V,int x){
		if (V.u==2) {
			if (x==1 && V.a[0]>0) 
				return 0;
			else
				return null;
		}else {
			Integer offset=predecessor(V.cluster[high(V, x)],low(V, x));
			if (offset!=null) {
				return index(V, high(V, x), offset);
			}else {
				Integer pre_cluster=predecessor(V.summary,high(V, x));
				if (pre_cluster==null) {
					return null;
				}else {
					int pre_offset=maximum(V.cluster[pre_cluster]);
					return index(V, pre_cluster, pre_offset);
				}
			}
		}
	}
	
	public void insert(ProtoVEBTree_N_Duplicate V,int x){
		V.n++;
		if (V.u==2) {
			V.a[x]++;
		}else {
			insert(V.cluster[high(V, x)], low(V, x));
			insert(V.summary, high(V, x));
		}
	}
	
	public boolean delete(ProtoVEBTree_N_Duplicate V,int x){
		if (V.u==2) {
			if (V.a[x]>0) {
				V.a[x]--;
				V.n--;
				return true;
			}
			//x原先不存在
			return false;
		}
		boolean delete_element=delete(V.cluster[high(V, x)], low(V, x));
		if (delete_element) {
			V.n--;
			//该簇的n也要减1
			delete(V.summary, high(V, x));
		}
		return delete_element;
	}
	
	//遍历ProtoVEBTree
	public void display(ProtoVEBTree_N_Duplicate V,int sum){
		if (V.u==2) {
			if (V.a[0]>0) {
				System.out.print(sum+" ");
			}
			if (V.a[1]>0) {
				System.out.print(sum+1+" ");
			}
		}else {
			for (int i = 0; i < V.cluster.length; i++) {
				display(V.cluster[i], sum+V.sqrt*i);
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={5,7,14,15,2,3,4,2,3,4};
		ProtoVEBTree_N_Duplicate V=new ProtoVEBTree_N_Duplicate(16);
		for (int i = 0; i < a.length; i++) {
			V.insert(V, a[i]);
			V.display(V, 0);
			System.out.println();
			System.out.println(V.n);
		}		
		for (int i = 0; i < a.length; i++) {
			V.delete(V, a[i]);
			V.display(V, 0);
			System.out.println();
			System.out.println(V.n);
		}
	}

}
