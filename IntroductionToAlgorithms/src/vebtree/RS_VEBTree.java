package vebtree;

public class RS_VEBTree {
	int u;
	Integer min;
	Integer max;
	int up_sqrt;
	int down_sqrt;
	RS_VEBTree summary;
	RS_VEBTree[] cluster;
	
	public RS_VEBTree(int u){
		this.u=u;	
		up_sqrt=u>>bits(u)/2;
		down_sqrt=u/up_sqrt;
		min=null;
		max=null;
		summary=null;
		cluster=new RS_VEBTree[up_sqrt];
		//在之后动态地初始化summary和cluster
//		if (this.u!=2) {
//			summary=new RS_VEBTree(up_sqrt);
//			cluster=new RS_VEBTree[up_sqrt];
//			for (int i = 0; i < cluster.length; i++) {
//				cluster[i]=new RS_VEBTree(down_sqrt);
//			}
//		}
	}
	
	public RS_VEBTree get_cluster(RS_VEBTree V,int x){
		if (V.cluster[x]==null) {
			V.cluster[x]=new RS_VEBTree(V.down_sqrt);
		}
		return V.cluster[x];
	}
	
	public RS_VEBTree get_summary(RS_VEBTree V){
		if (V.summary==null) {
			V.summary=new RS_VEBTree(V.up_sqrt);
		}
		return V.summary;
	}
	
	public int bits(int u){
		int k=-1;
		while (u!=0) {
			k++;
			u>>=1;
		}
		return k;
	}
	
	public Integer minimum(RS_VEBTree V){
		return V.min;
	}
	
	public Integer maximum(RS_VEBTree V){
		return V.max;
	}
	
	public int high(RS_VEBTree V,int x){
		return x/V.down_sqrt;
	}
	
	public int low(RS_VEBTree V,int x){
		return x%V.down_sqrt;
	}
	
	public int index(RS_VEBTree V,int x,int y){
		return x*V.down_sqrt+y;
	}
	
	public boolean member(RS_VEBTree V,Integer x){
		if (x==V.min|| x==V.max) {
			return true;
		}else if (V.u==2) {
			return false;
		}else {
			return member(V.get_cluster(V, high(V, x)), low(V, x));
		}
	}
	
	public Integer successor(RS_VEBTree V,int x){
		if (V.u==2) {
			if (x==0&&V.max==1) 
				return 1;			
			else 
				return null;			
		}else if(V.min!=null&&x<V.min){
			return V.min;
		}else {
			Integer max_low=maximum(V.get_cluster(V,high(V, x)));
			if (max_low!=null&&low(V, x)<max_low) {
				Integer offset=successor(V.get_cluster(V,high(V, x)),low(V, x));
				return index(V, high(V, x), offset);
			}else {
				Integer succ_cluster=successor(V.get_summary(V), high(V, x));
				if (succ_cluster==null) {
					return null;
				}else {
					//此处V.cluster[succ_cluster]必然不为null
					Integer offset=minimum(V.cluster[succ_cluster]);
					return index(V, succ_cluster, offset);
				}
			}
		}
	}
	
	public Integer predecessor(RS_VEBTree V,int x){
		if (V.u==2) {
			if (x==1&&V.min==0)
				return 0;
			else
				return null;
		}else if (V.max!=null&&x>V.max) {
			return V.max;
		}else {
			Integer min_low=minimum(V.get_cluster(V,high(V, x)));
			if (min_low!=null&&low(V, x)>min_low) {
				Integer offset=predecessor(V.get_cluster(V,high(V, x)), low(V, x));
				return index(V, high(V, x), offset);
			}else {
				Integer pre_cluster=predecessor(V.get_summary(V), high(V, x));
				if (pre_cluster==null) {
					//如果前驱是V.min，而V.min不在任何簇中
					if (V.min!=null&&x>V.min)
						return V.min;
					else 
						return null;					
				}else {
					//此处V.cluster[pre_cluster]必然不为null
					Integer offset=maximum(V.cluster[pre_cluster]);
					return index(V, pre_cluster, offset);
				}
			}
		}
	}
	
	public void empty_insert(RS_VEBTree V,int x){
		V.min=x;
		V.max=x;
	}
	
	public void insert(RS_VEBTree V,int x){
		if (V.min==null){ 
			empty_insert(V, x);
		}else {
			if (x<V.min) {
				int t=x;
				x=V.min;
				V.min=t;
			}
			if (V.u>2) {
				if (minimum(V.get_cluster(V,high(V, x)))==null) {
					insert(V.get_summary(V), high(V, x));
					empty_insert(V.cluster[high(V, x)], low(V, x));
				}else {
					insert(V.get_cluster(V, high(V, x)), low(V, x));
				}
			}
			if (x>V.max) {
				V.max=x;
			}
		}		
	}
	
	public void delete(RS_VEBTree V,int x){
		if (V.min==V.max) {
			V.min=null;
			V.max=null;
		}else if (V.u==2) {
			if (x==0) {
				V.min=1;
			}else {
				V.min=0;
			}
			V.max=V.min;
		}else {
			if (x==V.min) {
				Integer first_cluster=minimum(V.get_summary(V));
				x=index(V, first_cluster, minimum(V.get_cluster(V, first_cluster)));
				V.min=x;
			}
			delete(V.get_cluster(V, high(V, x)),low(V, x));
			if (minimum(V.get_cluster(V, high(V, x)))==null) {
				delete(V.get_summary(V), high(V, x));
				if (x==V.max) {
					Integer summary_max=maximum(V.get_summary(V));
					if (summary_max==null) {
						V.max=V.min;
					}else {
						V.max=index(V, summary_max, maximum(V.cluster[summary_max]));
					}
				}
			}else {
				if (x==V.max) {
					V.max=index(V, high(V, x), maximum(V.get_cluster(V, high(V, x))));
				}
			}
		}
	}
	
	public void display(RS_VEBTree V,int sum){
		if (V.min!=null) {
			//如果该VEBTree不为空
			if (V.u==2) {				
				if (V.min==V.max) {
					System.out.print(sum+V.min+" ");
				}else {
					System.out.print(sum+V.min+" ");
					System.out.print(sum+V.max+" ");
				}							
			}else {
				System.out.print(V.min+sum+" ");
				for (int i = 0; i < V.up_sqrt; i++) {
					if (V.cluster[i]!=null) {
						display(V.cluster[i], sum+V.down_sqrt*i);
					}
					
				}
			}
		}		
	}
	
	
	public static void main(String[] args) {
		RS_VEBTree V=new RS_VEBTree(16);
		int[] a={5,7,14,15,2,3,4};
		for (int i = 0; i < a.length; i++) {
			V.insert(V, a[i]);
			V.display(V, 0);
			System.out.println();
		}
//		V.display(V, 0);
		System.out.println();
		
		Integer pre=V.predecessor(V, 7);
		System.out.println(pre);
		
		Integer succ=V.successor(V, 7);
		System.out.println(succ);
		
		System.out.println(V.member(V, 8));
		System.out.println(V.member(V, 14));
		
		System.out.println(V.minimum(V));
		System.out.println(V.maximum(V));
		V.insert(V, 3);
		V.delete(V, 2);
		V.delete(V, 6);//此处会删除7,练习20.3-4
		System.out.println(V.member(V, 14));
		pre=V.predecessor(V, 15);
		System.out.println(pre);
		V.display(V, 0);
	}
}
