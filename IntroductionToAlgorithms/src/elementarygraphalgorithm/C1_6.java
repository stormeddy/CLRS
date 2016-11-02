package elementarygraphalgorithm;

/**
 * 此处提供两种方法，一种是网上的方法，一种是自己想的方法，
 * 经过测试，如果同时执行100000000次，则网上的方法速度是3.6秒，我的方法速度是3秒
 * @author xiazdong
 *
 */
public class C1_6 {
	//判断图是否存在通用汇点
	private static int sink_index = -1;
	public static void main(String[] args) throws Exception {
		Adjacent_Matrix adj_matrix = GraphFactory.getAdjacentMatrixInstance("graph\\22.1_6.txt");
		boolean flag = has_universal_sink2(adj_matrix);
		System.out.println(flag);
		if(flag)System.out.println(adj_matrix.getVertexValue(sink_index));
	}
	/**
	 * 自己的方法
	 * @return
	 */
	public static boolean has_universal_sink(Adjacent_Matrix g){
		int i=0,j=0;
		boolean flag = true;
		while(j<=g.getSize()-1){
			if(g.getElement(i, j)==1){
				i = j;
			}
			j++;
		}
		//检查arr[i][*]==0 arr[*(except i)][i]==1
		for(int a=0;a<g.getSize();a++){
//			if(g.getElement(i, a)==1&&i!=a){
			if(g.getElement(i, a)==1){
				flag = false;
			}
			if(g.getElement(a, i)==0&&i!=a){
				flag = false;
			}
		}
		if(flag) sink_index = i;
		return flag;
	}
	/**
	 * 网上的方法
	 * @return
	 */
	public static boolean has_universal_sink2(Adjacent_Matrix g){
		//CLRS方法
		int i=0,j=0;
		boolean flag = true;
		while(j<=g.getSize()-1){
			if(g.getElement(i, j)==1){
				i++;
			}
			else
				j++;
		}
		
		if (i>=g.getSize()) {
			flag=false;
			return flag;
		}
		//检查arr[i][*]==0 arr[*(except i)][i]==1
		for(int a=0;a<g.getSize();a++){
//			if(g.getElement(i, a)==1&&i!=a){
			if(g.getElement(i, a)==1){
				flag = false;
			}
			if(g.getElement(a, i)==0&&i!=a){
				flag = false;
			}
		}
		if(flag) sink_index = i;
		return flag;
	}
}
