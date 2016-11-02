package elementarygraphalgorithm;

public class C2_8 {
	//树的直径
	static int begin;
	static int end;
	static Adjacent_List g;
	public static void main(String[] args) throws Exception {
		
		Adjacent_List g = GraphFactory.getAdjacentListInstance("graph\\22.2_8.txt");
		C2_8.g = g;
		System.out.println("diameter: "+getDiameter());
		System.out.println("From "+ g.getVertexValue(begin)+" to "+ g.getVertexValue(end));
		
	}
	public static int getDiameter(){
		//初始源节点任取
		int i = 7;
//		int i = 0;
		BFS_Algorithm bfs_alg = new BFS_Algorithm(g);
		bfs_alg.BFS(i);	//O(v+e)
		int [] d = bfs_alg.getDistances();
		begin = 0;
		for(int a=0;a<d.length;a++){	//O(v)
			if(d[begin]<d[a])
				begin = a;
		}
		
		bfs_alg.BFS(begin);	//O(v+e)
		end = 0;
		for(int a=0;a<d.length;a++){		//O(v)
			if(d[end]<d[a])
				end = a;
		}
		return d[end];
	}
}
