package disjointset;

public class DS_List {
	//不相交集合链表
	DSet[] s;
	Node[] n;
	
	public DS_List(int u){
		//u为节点个数，节点编号为1~u
		s= new DSet[u+1];
		n= new Node[u+1];
	}
	
	class DSet{
		int head;
		int tail;
		int size;
	}
	
	class Node{
		int rep;
		int next;
//		int val;		
//		public Node(int val){
//			this.val=val;
//		}		
	}
	
	public void make_set(int x){
		if (s[x]==null) {
			s[x]=new DSet();
		}
		s[x].head=x;
		s[x].tail=x;
		s[x].size=1;
		if (n[x]==null) {
			n[x]=new Node();
		}
		n[x].rep=x;
		n[x].next=-1;//-1表示不存在
	}
	
	public int find_set(int x){
		return n[x].rep;
	}
	
	public void union(int x,int y){
		x=n[x].rep;
		y=n[y].rep;
		if (s[x].size>=s[y].size) {
			//若集合s[x]与s[y]一样大，将s[y]接到s[x]之后
			int t=x;
			x=y;
			y=t;
		}
		n[s[y].tail].next=s[x].head;
		s[y].size+=s[x].size;
		for (int i = s[x].head; i != -1; i=n[i].next) {
			n[i].rep=y;
		}
		s[x].head=-1;
	}
	
	public void connect_components(int[] v,int[] e){
		for (int i = 0; i < v.length; i++) {
			make_set(v[i]);
		}
		for (int i = 0; i < e.length; i=i+2) {
			if (find_set(e[i])!=find_set(e[i+1])) {
				union(e[i], e[i+1]);
			}
		}
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
		String vertex="abcdefghij";
		String edge="bdegachiabefbc";
		char[] vc=vertex.toCharArray();
		char[] ec=edge.toCharArray();
		int[] v=new int[vc.length];
		for (int i = 0; i < v.length; i++) {
			v[i]=vc[i]-'a'+1;
		}
		int[] e=new int[ec.length];
		for (int i = 0; i < e.length; i++) {
			e[i]=ec[i]-'a'+1;
		}		
		
		DS_List ds_List=new DS_List(v.length);
//		for (int i = 0; i < v.length; i++) {
//			ds_List.make_set(v[i]);
//		}
//		for (int i = 0; i < e.length; i=i+2) {
//			if (ds_List.find_set(e[i])!=ds_List.find_set(e[i+1])) {
//				ds_List.union(e[i], e[i+1]);
//			}
//		}
		ds_List.connect_components(v, e);
		for (int i = 0; i < v.length; i++) {
			System.out.print(ds_List.find_set(v[i])+" ");
		}
		System.out.println();
		System.out.println(ds_List.same_component(v[1], v[2]));
	}

}
