package elementarygraphalgorithm;

import java.util.HashMap;
import java.util.Map;

public class Adjacent_Matrix {
	private int vertex_size;		/*�����Ŀ*/
	private boolean isdirected;		/*�Ƿ�������ͼ*/
	private int[][] matrix; 
	private Map<String,Integer>valueToIndexMap;	/*ֵ-������,����ֵ���ܹ��ҳ��˵���adj�е�����*/
	private Map<Integer,String>IndexToValueMap;	
	private int current_vertex_size;
	public Adjacent_Matrix(int size){
		this.vertex_size = size;
		matrix = new int[size][size];
		isdirected = true;
		current_vertex_size = 0;
		valueToIndexMap = new HashMap<String,Integer>();
		IndexToValueMap = new HashMap<Integer,String>();
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				matrix[i][j] = 0;
			}
		}
	}
	public static Adjacent_Matrix constructGraphFromMatrix(int[][]matrix){
		Adjacent_Matrix adj_matrix = new Adjacent_Matrix(matrix.length);
		adj_matrix.matrix = matrix;
		adj_matrix.vertex_size = matrix.length;
		return adj_matrix;
	}
	public int[][] getMatrix(){
		return matrix;
	}
	public int getSize(){
		return vertex_size;
	}
	public boolean getIsdirected() {
		return isdirected;
	}
	public void addEdge(String begin,String end){
		Integer a = valueToIndexMap.get(begin);
		Integer b = valueToIndexMap.get(end);
		if(a==null){
			a = addVertex(begin);
		}
		if(b==null) b = addVertex(end);
		matrix[a][b]++;
	}
	public void setEdge(String a,String b,int value){
		//����һ���߻��߱�Ȩ�ؼ�1��value�˴�����Ȩ�ص����ã�ֻ��һ����־����־���ޱ�
		if(value!=0) addEdge(a,b);
	}
	public int addVertex(String value){
		if(valueToIndexMap.get(value)!=null)
			try {
				throw new Exception("�Ѿ��ӹ��Ľڵ�");
			} catch (Exception e) {
				e.printStackTrace();
			}
		valueToIndexMap.put(value, current_vertex_size);
		IndexToValueMap.put(current_vertex_size, value);
		current_vertex_size++;
		return current_vertex_size - 1;
	}
	public int getElement(int a,int b){
		return matrix[a][b];
	}
	public int getElement(String a,String b){
		int c = getVertexIndex(a);
		int d = getVertexIndex(b);
		return getElement(c, d);
		
	}
	public void printAllEdges(){
		for(int i=0;i<vertex_size;i++){
			for(int j=0;j<vertex_size;j++){
				if(matrix[i][j]!=0){
					System.out.println(getVertexValue(i)+"-->"+getVertexValue(j));
				}
			}
		}
			
	}
	/**
	 * ���ýڵ�ֵ��ýڵ������,��������"A",����1
	 * @param value
	 * @return
	 */
	public int getVertexIndex(String value){
		return valueToIndexMap.get(value);
	}
	/**
	 * ���ýڵ�������ýڵ�ֵ,��������1,����"A"
	 * @param index
	 * @return
	 */
	public String getVertexValue(int index){
		return IndexToValueMap.get(index);
	}
}
