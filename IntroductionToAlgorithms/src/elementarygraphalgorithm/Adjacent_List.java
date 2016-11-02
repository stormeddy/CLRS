package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * �ڽӱ�����߲��ִ����ֵ���ұ�������Ҳ��ֵ,Ҫ���������Ҫͨ��map
 * @author xiazdong
 *
 */
public class Adjacent_List {
	//ÿ���ڵ㶼��һ��ֵ
	private int vertex_size;		/*�������Ŀ*/
	public ArrayList<Vertex> adj; 	/*�ڽӱ�*/
	private HashMap<String,Integer> map;	/*ֵ-������,����ֵ���ܹ��ҳ��˵���adj�е�����*/
	private int current_vertex_size;	//��ǰ�Ѿ��ж��ٽڵ�
	private int edge_size;	/*�ߵ�����Ŀ*/
	
	public Adjacent_List clone_adjacent_list(){
		Adjacent_List ret=new Adjacent_List(vertex_size,edge_size);
		for(int i=0;i<vertex_size;i++){
			List<String> list = getListByVertexIndex(i);
			for(String str:list){
				ret.addEdge(getVertexValue(i), str);
				
			}
		}
		return ret;
	}
	/**
	 * ���ؽڵ���Ŀ
	 * @return
	 */
	public int getSize(){
		return vertex_size;
	}
	/**
	 * ���رߵĸ���
	 * @return
	 */
	public int getEdgeSize(){
		return edge_size;
	}
	public Adjacent_List(int size,int edgesize){
		this.vertex_size = size;
		this.edge_size = edgesize;
		adj = new ArrayList<Vertex>();
		map = new HashMap<String,Integer>();
		current_vertex_size = 0;
	}
	public Adjacent_List(int size){
		this.vertex_size = size;
		adj = new ArrayList<Vertex>();
		map = new HashMap<String,Integer>();
		current_vertex_size = 0;
	}
	/**
	 * ���(begin,end)��
	 * @param begin
	 * @param end
	 */
	public void addEdge(String begin,String end){
		Integer beginIndex = map.get(begin);
		Integer endIndex = map.get(end);
		if(beginIndex==null){
			beginIndex = addVertex(begin);
		}
		if(endIndex==null){
			endIndex = addVertex(end);
		}
		adj.get(beginIndex).insert(end);
		edge_size++;
	}
	/**
	 * ��һ���ڵ�����Ӷ�����
	 * @param begin
	 * @param list
	 */
	public void addEdges(String begin ,List<String> list){
		Integer beginIndex = map.get(begin);
		if(beginIndex==null){
			beginIndex = addVertex(begin);
		}
		for(String a:list){
			Integer endIndex = map.get(a);
			if(endIndex==null){
				endIndex = addVertex(a);
			}
		}
		adj.get(beginIndex).getLists().addAll(list);
	}
	/**
	 * ��ӵ�,�ڵ��������ʵ���Ǹ��ݵ�ǰ�ڵ�������ɵ�
	 * @param value �ڵ��ֵ
	 * @return �ڵ������
	 */
	public int addVertex(String value){
		if(map.get(value)!=null)
		{
			//System.out.println(value+"�����");
			return map.get(value);
		}
		else{
			adj.add(new Vertex(value));
			map.put(value,current_vertex_size++);//��һ�����������Ϊ0
//			vertex_size++;
			return current_vertex_size - 1;	
		}
	}
	/**
	 * ���ݽڵ��ֵ���ؽڵ������
	 * @param value �ڵ��ֵ
	 * @return ����
	 */
	public List<String> getListByVertexValue(String value){
		return adj.get(map.get(value)).getLists();
	}
	/**
	 * ���ݽڵ���������ؽڵ������
	 * @param index
	 * @return ���û������������򷵻ؿյ�����
	 */
	public List<String> getListByVertexIndex(int index){
		if(adj.size()==0) return new LinkedList<String>();
		return adj.get(index).getLists()==null?new LinkedList<String>():adj.get(index).getLists();
	}
	/**
	 * ���ýڵ�ֵ��ýڵ������,��������"A",����1
	 * @param value
	 * @return
	 */
	public int getVertexIndex(String value){
		return map.get(value);
	}
	/**
	 * ���ýڵ�������ýڵ�ֵ,��������2,����1
	 * ����Ҳ������򷵻�null
	 * @param index
	 * @return
	 */
	public String getVertexValue(int index){
		//�ڽ��������ڻ��������±�Ϊ-1ʱ����null
		if(adj.size()==0||index==-1) return null;
		return adj.get(index).getValue();
	}
	/**
	 * ����������-ֵӳ��
	 */
	public void printVertexMap(){
		System.out.println("=====================");
		Set<Entry<String, Integer>> entry = map.entrySet();
		for(Entry<String, Integer> e:entry){
			System.out.println(e.getKey()+":"+e.getValue());
		}
		System.out.println("=====================");
	}
	/**
	 * ��ӡ���еı�
	 */
	public void printAllEdges(){
		for(int i=0;i<vertex_size;i++){
			if(adj.size()==0) continue;
			else{
				Vertex adjelement = adj.get(i);
				for(int j=0;j<adjelement.getLists().size();j++){
					System.out.println(adjelement.getValue()+" --> "+ adjelement.getLists().get(j));
				}
			}
		}
	}
	/**
	 * ���������ڽ�����ĵ���ӽ���ǰͼ
	 * @param g 
	 */
	public void addAllVertexFromOtherGraph(Adjacent_List g) {
		this.vertex_size = g.getSize();
		map = new HashMap<String, Integer>();
		this.edge_size = g.getEdgeSize();
		for(int i=0;i<g.getSize();i++){
			addVertex(g.getVertexValue(i));
		}
	}
	//�ڽӱ���ߵ�����+�ұߵ�����
	private class Vertex{
		private String value;	//ֵ
		private List<String> lists;//��Ӧ������
		
		public List<String> getLists() {
			return lists;
		}
		public Vertex(String value) {
			this.setValue(value);
			lists = new ArrayList<String>();
		}
		public void insert(String end) {
			lists.add(end);
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	/**
	 * ���Ժ���
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Adjacent_List adj_list = GraphFactory.getAdjacentListInstance("graph\\22_2.txt");
		adj_list.printAllEdges();
		
	}
}
