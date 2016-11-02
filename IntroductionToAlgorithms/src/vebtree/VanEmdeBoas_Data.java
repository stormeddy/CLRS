package vebtree;

import vebtree.ProtoVEBTree_N_Data.Data;

public class VanEmdeBoas_Data {
	//支持带卫星关键字的VEBTree
	int u;
	Integer min;
	Integer max;
	Data min_data;
	Data max_data;
	int up_sqrt;
	int down_sqrt;
	VanEmdeBoas_Data summary;
	VanEmdeBoas_Data[] cluster;
	
	public VanEmdeBoas_Data(int u){
		this.u=u;	
		up_sqrt=u>>bits(u)/2;
		down_sqrt=u/up_sqrt;
		min=null;
		max=null;
		min_data=null;
		max_data=null;
		if (this.u!=2) {
			summary=new VanEmdeBoas_Data(up_sqrt);
			cluster=new VanEmdeBoas_Data[up_sqrt];
			for (int i = 0; i < cluster.length; i++) {
				cluster[i]=new VanEmdeBoas_Data(down_sqrt);
			}
		}
	}
	
	//根据关键字获取相应的卫星数据
	public Data get_data(VanEmdeBoas_Data V,Integer x){
		if(x==V.min)
			return V.min_data;
		if(x==V.max)
			return V.max_data;
		if(V.u==2)
			return null;
		return get_data(V.cluster[high(V, x)], low(V, x));
	}
	
	public int bits(int u){
		int k=-1;
		while (u!=0) {
			k++;
			u>>=1;
		}
		return k;
	}
	
	public Integer minimum(VanEmdeBoas_Data V){
		return V.min;
	}
	
	public Integer maximum(VanEmdeBoas_Data V){
		return V.max;
	}
	
	public int high(VanEmdeBoas_Data V,int x){
		return x/V.down_sqrt;
	}
	
	public int low(VanEmdeBoas_Data V,int x){
		return x%V.down_sqrt;
	}
	
	public int index(VanEmdeBoas_Data V,int x,int y){
		return x*V.down_sqrt+y;
	}
	
	public boolean member(VanEmdeBoas_Data V,Integer x){
		if (x==V.min|| x==V.max) {
			return true;
		}else if (V.u==2) {
			return false;
		}else {
			return member(V.cluster[high(V, x)], low(V, x));
		}
	}
	
	public Integer successor(VanEmdeBoas_Data V,int x){
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
	
	public Integer predecessor(VanEmdeBoas_Data V,int x){
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
	
	public void empty_insert(VanEmdeBoas_Data V,int x,Data data){
		V.min=x;
		V.max=x;
		V.min_data=V.max_data=data;
	}
	
	public void insert(VanEmdeBoas_Data V,int x,Data data){
		if (V.min==null){ 
			empty_insert(V, x, data);
		}else {
			if (x<V.min) {
				//交换x与V.min，data与V.min_data
				int t=x;
				x=V.min;
				V.min=t;
				Data t_data=data;
				data=V.min_data;
				V.min_data=t_data;
				
			}
			if (V.u>2) {
				if (minimum(V.cluster[high(V, x)])==null) {
					insert(V.summary, high(V, x),data);
					empty_insert(V.cluster[high(V, x)], low(V, x),data);
				}else {
					insert(V.cluster[high(V, x)], low(V, x),data);
				}
			}
			if (x>V.max) {
				V.max=x;
				V.max_data=data;
			}
		}		
	}
	
	public void delete(VanEmdeBoas_Data V,int x){
		if (V.min==V.max) {
			V.min=null;
			V.max=null;
			V.min_data=null;
			V.max_data=null;
		}else if (V.u==2) {
			if (x==0) {
				V.min=1;
				V.min_data=V.max_data;
			}else {
				V.min=0;
				V.max_data=V.min_data;
			}
			V.max=V.min;
			V.max_data=V.min_data;
		}else {
			if (x==V.min) {
				Integer first_cluster=minimum(V.summary);
				x=index(V, first_cluster, minimum(V.cluster[first_cluster]));
				V.min=x;
				V.min_data=V.cluster[first_cluster].min_data;
			}
			delete(V.cluster[high(V, x)],low(V, x));
			if (minimum(V.cluster[high(V, x)])==null) {
				delete(V.summary, high(V, x));
				if (x==V.max) {
					Integer summary_max=maximum(V.summary);
					if (summary_max==null) {
						V.max=V.min;
						V.max_data=V.min_data;
					}else {
						V.max=index(V, summary_max, maximum(V.cluster[summary_max]));
						V.max_data=V.cluster[summary_max].max_data;
					}
				}
			}else {
				if (x==V.max) {
					V.max=index(V, high(V, x), maximum(V.cluster[high(V, x)]));
					V.max_data=V.cluster[high(V, x)].max_data;
				}
			}
		}
	}
	
	public void display(VanEmdeBoas_Data V,int sum){
		if (V.min!=null) {
			//如果该VEBTree不为空
			if (V.u==2) {				
				if (V.min==V.max) {
					System.out.print(sum+V.min+" "+V.min_data.c+" ");
				}else {
					System.out.print(sum+V.min+" "+V.min_data.c+" ");
					System.out.print(sum+V.max+" "+V.max_data.c+" ");
				}							
			}else {
				System.out.print(V.min+sum+" "+V.min_data.c+" ");
				for (int i = 0; i < V.cluster.length; i++) {
					display(V.cluster[i], sum+V.down_sqrt*i);
				}
			}
		}		
	}
	
	
	public static void main(String[] args) {
		VanEmdeBoas_Data V=new VanEmdeBoas_Data(16);
		int[] a={5,7,14,15,2,3,4};
		for (int i = 0; i < a.length; i++) {
			Data data=V.new Data((char) ('A'+a[i]));
			V.insert(V, a[i],data);
			V.display(V, 0);
			System.out.println();
		}
		for (int i = 0; i < a.length; i++) {
			V.delete(V, a[i]);
			V.display(V, 0);
			System.out.println();
		}
		System.out.println("end");
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
//		V.delete(V, 2);
//		System.out.println(V.member(V, 14));
//		pre=V.predecessor(V, 15);
//		System.out.println(pre);
//		V.display(V, 0);
	}
	
	
	class Data{
		char c;
		public Data(char c){
			this.c=c;
		}
	}
}
