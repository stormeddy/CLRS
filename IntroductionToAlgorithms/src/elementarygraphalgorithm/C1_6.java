package elementarygraphalgorithm;

/**
 * �˴��ṩ���ַ�����һ�������ϵķ�����һ�����Լ���ķ�����
 * �������ԣ����ͬʱִ��100000000�Σ������ϵķ����ٶ���3.6�룬�ҵķ����ٶ���3��
 * @author xiazdong
 *
 */
public class C1_6 {
	//�ж�ͼ�Ƿ����ͨ�û��
	private static int sink_index = -1;
	public static void main(String[] args) throws Exception {
		Adjacent_Matrix adj_matrix = GraphFactory.getAdjacentMatrixInstance("graph\\22.1_6.txt");
		boolean flag = has_universal_sink2(adj_matrix);
		System.out.println(flag);
		if(flag)System.out.println(adj_matrix.getVertexValue(sink_index));
	}
	/**
	 * �Լ��ķ���
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
		//���arr[i][*]==0 arr[*(except i)][i]==1
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
	 * ���ϵķ���
	 * @return
	 */
	public static boolean has_universal_sink2(Adjacent_Matrix g){
		//CLRS����
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
		//���arr[i][*]==0 arr[*(except i)][i]==1
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
