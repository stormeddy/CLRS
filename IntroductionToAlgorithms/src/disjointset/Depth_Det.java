package disjointset;

public class Depth_Det {
	int u;
	int[] p;
	int[] rank;
	int[] depth;
//	ArrayList<Integer> forest;//最后划分出的各个集合
	
	public Depth_Det(int u){
		this.u=u;
		p=new int[u+1];
		rank=new int[u+1];
		depth=new int[u+1];
	}
	
	public void init(){
		for (int i = 1; i <= u; i++) {
			make_set(i);
		}
	}
	
	public void make_set(int x){
		p[x]=x;
		rank[x]=0;
		depth[x]=0;
	}
	
	public int find_root(int v){
		if (p[v]!=p[p[v]]) {
			int y=p[v];
			p[v]=find_root(y);
			depth[v]+=depth[y];
		}
		return p[v];
	}
	
	public int find_depth(int v){
		find_root(v);
		//路径压缩之后，v为根节点或为根节点的孩子节点
		if (v==p[v]) {
			return depth[v];
		}else {
			return depth[v]+depth[p[v]];
		}
	}
	
	public int find_set(int x){
		//循环形式的find_root,同时更新了depth[]
		int temp=x,sum=0,ans;
		while (temp!=p[temp]) {
			sum+=depth[temp];
			temp=p[temp];
		}
		ans=temp;
		while (x!=ans) {
			sum-=depth[x];
			depth[x]+=sum;
			temp=p[x];
			p[x]=ans;
			x=temp;
		}
		return ans;
	}
	
//	public int find_set_nonrecursive(int x){
//		int ret=x,t;
//		while(ret!=p[ret]){
//			ret=p[ret];
//		}
//		while(x!=ret){
//			t=p[x];
//			p[x]=ret;
//			x=t;
//		}
//		return ret;
//	}
	
	public void union(int x,int y){
		if (x==y) {
			return;
		}
		link(x, y);
	}
	
	public void link(int x,int y){
		//将x接到y上面
		p[x]=y;
		depth[x]=1;
		rank[y]++;
	}
	
//	public void graft(int r,int v){
//		int x=find_set(r);
//		int y=find_set(v);
//		if (x<y) {
//			union(x, y);
//		}else {
//			union(y, x);
//		}
//	}
		
	public void graft(int r,int v){
		int r_root=find_root(r);
		int v_root=find_root(v);
		int z=find_depth(v);
		if (rank[r_root]>rank[v_root]) {
			p[v_root]=r_root;
			depth[r_root]=depth[r_root]+z+1;
			depth[v_root]=depth[v_root]-depth[r_root];
		}else {
			p[r_root]=v_root;
			depth[r_root]=depth[r_root]+z+1-depth[v_root];
			if (rank[r_root]==rank[v_root]) {
				rank[v_root]++;
			}
		}
	}
	
		//若集合s[x]与s[y]一样大，将s[x]接到s[y]上面
//		if (rank[x]>rank[y]) {
//			p[y]=x;
//		}else {
//			p[x]=y;
//			if (rank[x]==rank[y]) {
//				rank[y]++;
//			}
//		}
	
	

	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String vertex="abcdefghij";
//		String edge="bdegachiabefbc";
//		char[] vc=vertex.toCharArray();
//		char[] ec=edge.toCharArray();
//		int[] v=new int[vc.length];
//		for (int i = 0; i < v.length; i++) {
//			v[i]=vc[i]-'a'+1;
//		}
//		int[] e=new int[ec.length];
//		for (int i = 0; i < e.length; i++) {
//			e[i]=ec[i]-'a'+1;
//		}		
//		
//		Depth_Det Depth_Det=new Depth_Det(v.length);
//
//		Depth_Det.connect_components(v, e);
//		for (int i = 0; i < v.length; i++) {
//			System.out.print(Depth_Det.find_set(v[i])+" ");
//		}
//		System.out.println();
//		System.out.println(Depth_Det.same_component(v[1], v[2]));
//		
//		for (int i = 0; i < v.length; i++) {
//			Depth_Det.print_set(v[i]);
//			System.out.println();
//		}


	}

}
