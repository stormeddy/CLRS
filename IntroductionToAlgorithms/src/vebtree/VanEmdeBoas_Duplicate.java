package vebtree;

public class VanEmdeBoas_Duplicate {
	//支持重复关键字的VEBTree
	int u;
	Integer min;
	Integer max;
	int min_cnt;
	int max_cnt;
	int up_sqrt;
	int down_sqrt;
	VanEmdeBoas_Duplicate summary;
	VanEmdeBoas_Duplicate[] cluster;
	
	public VanEmdeBoas_Duplicate(int u){
		this.u=u;	
		up_sqrt=u>>bits(u)/2;
		down_sqrt=u/up_sqrt;
		min=null;
		max=null;
		min_cnt=0;
		max_cnt=0;
		if (this.u!=2) {
			summary=new VanEmdeBoas_Duplicate(up_sqrt);
			cluster=new VanEmdeBoas_Duplicate[up_sqrt];
			for (int i = 0; i < cluster.length; i++) {
				cluster[i]=new VanEmdeBoas_Duplicate(down_sqrt);
			}
		}
	}
	
	public int bits(int u){
		int k=-1;
		while (u!=0) {
			k++;
			u>>=1;
		}
		return k;
	}
	
	public Integer minimum(VanEmdeBoas_Duplicate V){
		return V.min;
	}
	
	public Integer maximum(VanEmdeBoas_Duplicate V){
		return V.max;
	}
	
	public int high(VanEmdeBoas_Duplicate V,int x){
		return x/V.down_sqrt;
	}
	
	public int low(VanEmdeBoas_Duplicate V,int x){
		return x%V.down_sqrt;
	}
	
	public int index(VanEmdeBoas_Duplicate V,int x,int y){
		return x*V.down_sqrt+y;
	}
	
	public boolean member(VanEmdeBoas_Duplicate V,Integer x){
		if (x==V.min|| x==V.max) {
			return true;
		}else if (V.u==2) {
			return false;
		}else {
			return member(V.cluster[high(V, x)], low(V, x));
		}
	}
	
	public Integer successor(VanEmdeBoas_Duplicate V,int x){
		if (V.u==2) {
			if (x==0&&V.max==1) 
				return 1;			
			else 
				return null;			
		}else if(V.min!=null&&x<V.min){
			return V.min;
		}else {
			Integer max_low=maximum(V.cluster[high(V, x)]);
			if (max_low!=null&&low(V, x)<max_low) {
				Integer offset=successor(V.cluster[high(V, x)],low(V, x));
				return index(V, high(V, x), offset);
			}else {
				Integer succ_cluster=successor(V.summary, high(V, x));
				if (succ_cluster==null) {
					return null;
				}else {
					Integer offset=minimum(V.cluster[succ_cluster]);
					return index(V, succ_cluster, offset);
				}
			}
		}
	}
	
	public Integer predecessor(VanEmdeBoas_Duplicate V,int x){
		if (V.u==2) {
			if (x==1&&V.min==0)
				return 0;
			else
				return null;
		}else if (V.max!=null&&x>V.max) {
			return V.max;
		}else {
			Integer min_low=minimum(V.cluster[high(V, x)]);
			if (min_low!=null&&low(V, x)>min_low) {
				Integer offset=predecessor(V.cluster[high(V, x)], low(V, x));
				return index(V, high(V, x), offset);
			}else {
				Integer pre_cluster=predecessor(V.summary, high(V, x));
				if (pre_cluster==null) {
					//如果前驱是V.min，而V.min不在任何簇中
					if (V.min!=null&&x>V.min)
						return V.min;
					else 
						return null;					
				}else {
					Integer offset=maximum(V.cluster[pre_cluster]);
					return index(V, pre_cluster, offset);
				}
			}
		}
	}
	
	public void empty_insert(VanEmdeBoas_Duplicate V,int x){
		V.min=x;
		V.max=x;
		V.min_cnt=1;
		V.max_cnt=1;
	}
	
	public void insert(VanEmdeBoas_Duplicate V,int x){
		if (V.min==null){ 
			empty_insert(V, x);
		}else {
			if (x==V.min) {
				V.min_cnt++;
				return;
			}
			if (x==V.max) {
				V.max_cnt++;
				return;
			}
			
			if (x<V.min) {
				int t=x;
				x=V.min;
				V.min=t;
				V.min_cnt=1;
			}
			if (V.u>2) {
				if (minimum(V.cluster[high(V, x)])==null) {
					insert(V.summary, high(V, x));
					empty_insert(V.cluster[high(V, x)], low(V, x));
				}else {
					insert(V.cluster[high(V, x)], low(V, x));
				}
			}
			if (x>V.max) {
				V.max=x;
				V.max_cnt=1;
			}
		}		
	}
	
	public void delete(VanEmdeBoas_Duplicate V,int x){
		if (V.min==V.max) {
			if (V.min_cnt>1) {
				V.min_cnt--;
			}else {
				V.min=null;
				V.max=null;
				V.min_cnt=0;
			}
			V.max_cnt=V.min_cnt;
			return;			
		}else if (V.u==2) {
			if (x==0) {
				V.min_cnt--;
				//原先的V.min被删除，V.min要被修改
				if (V.min_cnt==0) {
					V.min=1;
					V.min_cnt=V.max_cnt;
				}				
			}else {
				V.max_cnt--;
				//原先的V.max被删除，V.max要被修改
				if (V.max_cnt==0) {
					V.max=0;
					V.max_cnt=V.min_cnt;
				}				
			}
			return;
//			V.max=V.min;
		}else {
			if (x==V.min) {
				if (V.min_cnt>1) {
					V.min_cnt--;
					return;
				}
				//V.min发生改变
				Integer first_cluster=minimum(V.summary);
				x=index(V, first_cluster, minimum(V.cluster[first_cluster]));
				V.min=x;
				V.min_cnt=V.cluster[first_cluster].min_cnt;
			}
			delete(V.cluster[high(V, x)],low(V, x));
			if (minimum(V.cluster[high(V, x)])==null) {
				delete(V.summary, high(V, x));
				if (x==V.max) {
//					if (V.max==V.min) {
//						V.max_cnt=V.min_cnt;
//						return;
//					}
					V.max_cnt--;
					if (V.max_cnt==0) {
						//V.max发生改变
						Integer summary_max=maximum(V.summary);
						if (summary_max==null) {
							V.max=V.min;
							V.max_cnt=V.min_cnt;
						}else {
							V.max=index(V, summary_max, maximum(V.cluster[summary_max]));
							V.max_cnt=V.cluster[summary_max].max_cnt;
						}
					}
					
				}
			}else {
				if (x==V.max) {
//					if (V.max==V.min) {
//						V.max_cnt=V.min_cnt;
//						return;
//					}
					V.max_cnt--;
					if (V.max_cnt==0) {
						V.max=index(V, high(V, x), maximum(V.cluster[high(V, x)]));
						V.max_cnt=V.cluster[high(V, x)].max_cnt;
					}					
				}
			}
		}
	}
	
	public void display(VanEmdeBoas_Duplicate V,int sum){
		if (V.min!=null) {
			//如果该VEBTree不为空
			if (V.u==2) {				
				if (V.min==V.max) {
					int i=V.min_cnt;
					while(i>0){
						System.out.print(sum+V.min+" ");
						i--;
					}
				}else {
					int i=V.min_cnt;
					while(i>0){
						System.out.print(sum+V.min+" ");
						i--;
					}
					int j=V.max_cnt;
					while(j>0){
						System.out.print(sum+V.max+" ");
						j--;
					}
				}							
			}else {
				int j=V.min_cnt;
				while(j>0){
					System.out.print(V.min+sum+" ");
					j--;
				}
				for (int i = 0; i < V.cluster.length; i++) {
					display(V.cluster[i], sum+V.down_sqrt*i);
				}
			}
		}		
	}
	
	
	public static void main(String[] args) {
		VanEmdeBoas_Duplicate V=new VanEmdeBoas_Duplicate(16);
		int[] a={5,7,14,15,2,3,4,2,3,4};
		for (int i = 0; i < a.length; i++) {
			V.insert(V, a[i]);
			V.display(V, 0);
			System.out.println();
		}
		
		for (int i = 0; i < a.length; i++) {
			V.delete(V, a[i]);
			V.display(V, 0);
			System.out.println();
		}
//		V.display(V, 0);
//		System.out.println();
//		
//		Integer pre=V.predecessor(V, 7);
//		System.out.println(pre);
//		
//		Integer succ=V.successor(V, 7);
//		System.out.println(succ);
//		
//		System.out.println(V.member(V, 8));
//		System.out.println(V.member(V, 14));
//		
//		System.out.println(V.minimum(V));
//		System.out.println(V.maximum(V));
//		
//		V.delete(V, 14);
//		System.out.println(V.member(V, 14));
//		pre=V.predecessor(V, 15);
//		System.out.println(pre);
//		V.display(V, 0);
	}

}
