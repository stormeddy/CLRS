package disjointset;

import java.util.ArrayList;

public class DS_Forest_Null {

	//不相交集合森林
		int[] p;
		int[] rank;
		int[] next;
		ArrayList<Integer> forest;//最后划分出的各个集合
		
		public DS_Forest_Null(int u){
			p=new int[u+1];
			rank=new int[u+1];
			next=new int[u+1];
		}
		
		public void make_set(int x){
			p[x]=x;
			rank[x]=0;
			next[x]=x;
		}
		
		public int find_set(int x){
			if (x!=-1) {
				if (x!=p[x]) {
					p[x]=find_set(p[x]);			
				}
				return p[x];
			}else {
				return -1;
			}
			
		}
		
		public int find_set_nonrecursive(int x){
			int ret=x,t;
			while(ret!=p[ret]){
				ret=p[ret];
			}
			while(x!=ret){
				t=p[x];
				p[x]=ret;
				x=t;
			}
			return ret;
		}
		
		public void union_x_y(int x,int y){
			//将x并入y
			link_x_y(find_set(x), find_set(y));
		}
		
		public void link_x_y(int x,int y){
			//将x并入y,不是按秩合并
			p[x]=y;
			if (rank[x]==rank[y]) {
				rank[y]++;
			}
			int t=next[x];
			next[x]=next[y];
			next[y]=t;
		}
		
		public void union(int x,int y){
			link(find_set(x), find_set(y));
		}
		
		public void link(int x,int y){
			//若集合s[x]与s[y]一样大，将s[y]接到s[x]上面
			if (rank[x]<rank[y]) {
				p[x]=y;
			}else {
				p[y]=x;
				if (rank[x]==rank[y]) {
					rank[x]++;
				}
			}
			int t=next[x];
			next[x]=next[y];
			next[y]=t;
			
			//若集合s[x]与s[y]一样大，将s[x]接到s[y]上面
//			if (rank[x]>rank[y]) {
//				p[y]=x;
//			}else {
//				p[x]=y;
//				if (rank[x]==rank[y]) {
//					rank[y]++;
//				}
//			}
		}
		
		public void print_set(int x){
			//打印出x所在集合的所有成员
			int t=x;
			while(next[x]!=t){
				System.out.print(x+" ");
				x=next[x];
			}
			System.out.print(x);
		}
		
		public void connect_components(int[] v,int[] e){
			for (int i = 0; i < v.length; i++) {
				if (v[i]!=-1) {
					make_set(v[i]);
				}				
			}
			for (int i = 0; i < e.length; i=i+2) {
				if (find_set(e[i])!=find_set(e[i+1])) {
					union(e[i], e[i+1]);
				}
			}
			forest=new ArrayList<Integer>();
			forest.add(-1);//forest(0)为-1,不使用
			for (int i = 0; i < v.length; i++) {
				
				int k=find_set(v[i]);
				if (k==-1) {
					forest.add(-1);
				}else {
					if (!forest.contains(k)) {
						forest.add(k);
					}
				}
				
				
			}
//			for (int i = 0; i < forest.size(); i++) {
//				System.out.print(forest.get(i)+" ");
//			}
//			System.out.println();
		}
		
		public boolean same_component(int v1,int v2){
			if (find_set(v1)==find_set(v2)) {
				return true;
			}else {
				return false;
			}
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
