package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import minimumspanningtree.Edge;

/**
 * �ڽӱ�����߲��ִ����ֵ���ұ�������Ҳ��ֵ,Ҫ���������Ҫͨ��map
 * @author xiazdong
 *
 */
public class Weighted_Adjacent_List{
	//ÿ���ڵ㶼��һ��ֵ
	private int vertex_size;		/*�������Ŀ*/
	private ArrayList<Vertex>adj; 	/*�ڽӱ�*/
	private HashMap<String,Integer> map;	/*ֵ-������,����ֵ���ܹ��ҳ��˵���adj�е�����*/
	private int current_vertex_size;	//��ǰ�Ѿ��ж��ٽڵ�
	private int edge_size;	/*�ߵ�����Ŀ*/
	
	public Weighted_Adjacent_List clone_adjacent_list(){
		Weighted_Adjacent_List ret=new Weighted_Adjacent_List(vertex_size);
		for(int i=0;i<vertex_size;i++){
			List<Pair> list = getListByVertexIndex(i);
			for(Pair p:list){
				ret.addEdge(getVertexValue(i), p.end, p.weight);				
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
	public Weighted_Adjacent_List(int size,int edgesize){
		this.vertex_size = size;
		this.edge_size = edgesize;
		adj = new ArrayList<Vertex>();
		map = new HashMap<String,Integer>();
		current_vertex_size = 0;
	}
	public Weighted_Adjacent_List(int size){
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
	public void addEdge(String begin,String end,int weight){
		Integer beginIndex = map.get(begin);
		Integer endIndex = map.get(end);
		if(beginIndex==null){
			beginIndex = addVertex(begin);
		}
		if(endIndex==null){
			endIndex = addVertex(end);
		}
		adj.get(beginIndex).insert(end,weight);
	}
	
	/**
	 * ���(begin,end)��,������orig��Ϣ
	 * @param begin
	 * @param end
	 */
	public void addEdge(String begin,String end,int weight,Edge edge){
		Integer beginIndex = map.get(begin);
		Integer endIndex = map.get(end);
		if(beginIndex==null){
			beginIndex = addVertex(begin);
		}
		if(endIndex==null){
			endIndex = addVertex(end);
		}
		adj.get(beginIndex).insert(end,weight,edge);
	}
	/**
	 * ��һ���ڵ�����Ӷ�����
	 * @param begin
	 * @param list
	 */
	public void addEdges(String begin ,List<Pair> list){
		Integer beginIndex = map.get(begin);
		if(beginIndex==null){
			beginIndex = addVertex(begin);
		}
		for(Pair a:list){
			Integer endIndex = map.get(a.end);
			if(endIndex==null){
				endIndex = addVertex(a.end);
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
			map.put(value,current_vertex_size++);
			return current_vertex_size - 1;	
		}
	}
	/**
	 * ���ݽڵ��ֵ���ؽڵ������
	 * @param value �ڵ��ֵ
	 * @return ����
	 */
	public List<Pair> getListByVertexValue(String value){
		if (map.get(value)!=null) {
			return adj.get(map.get(value)).getLists();
		}
		return null;
	}
	/**
	 * ���ݽڵ���������ؽڵ������
	 * @param index
	 * @return ���û������������򷵻ؿյ�����
	 */
	public List<Pair> getListByVertexIndex(int index){
		if(adj.size()==0) return new LinkedList<Pair>();
		return adj.get(index).getLists()==null?new LinkedList<Pair>():adj.get(index).getLists();
	}
	/**
	 * ���ýڵ�ֵ��ýڵ������,��������"A",����1
	 * @param value
	 * @return
	 */
	public Integer getVertexIndex(String value){
		return map.get(value);
	}
	/**
	 * ���ýڵ�������ýڵ�ֵ,��������2,����1
	 * ����Ҳ������򷵻�null
	 * @param index
	 * @return
	 */
	public String getVertexValue(int index){
		if(adj.size()==0) return null;
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
	public void addAllVertexFromOtherGraph(Weighted_Adjacent_List g) {
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
		private List<Pair> lists;//��Ӧ������
		
		public List<Pair> getLists() {
			return lists;
		}
		public Vertex(String value) {
			this.setValue(value);
			lists = new ArrayList<Pair>();
		}
		public void insert(String end,int weight) {
			lists.add(new Pair(end,weight));
		}
		public void insert(String end,int weight,Edge edge) {
			lists.add(new Pair(end,weight,edge));
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
		Weighted_Adjacent_List adj_list = GraphFactory.getWeightedAdjacentListInstance("graph\\22_1_w.txt");
		adj_list.printAllEdges();
	}

}
