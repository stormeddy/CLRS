package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 邻接表中左边部分存的是值，右边链表存的也是值,要获得索引需要通过map
 * @author xiazdong
 *
 */
public class Adjacent_List {
	//每个节点都有一个值
	private int vertex_size;		/*点的总数目*/
	public ArrayList<Vertex> adj; 	/*邻接表*/
	private HashMap<String,Integer> map;	/*值-索引对,给定值，能够找出此点在adj中的索引*/
	private int current_vertex_size;	//当前已经有多少节点
	private int edge_size;	/*边的总数目*/
	
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
	 * 返回节点数目
	 * @return
	 */
	public int getSize(){
		return vertex_size;
	}
	/**
	 * 返回边的个数
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
	 * 添加(begin,end)边
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
	 * 往一个节点内添加多条边
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
	 * 添加点,节点的索引其实就是根据当前节点个数生成的
	 * @param value 节点的值
	 * @return 节点的索引
	 */
	public int addVertex(String value){
		if(map.get(value)!=null)
		{
			//System.out.println(value+"已添加");
			return map.get(value);
		}
		else{
			adj.add(new Vertex(value));
			map.put(value,current_vertex_size++);//第一个顶点的索引为0
//			vertex_size++;
			return current_vertex_size - 1;	
		}
	}
	/**
	 * 根据节点的值返回节点的链表
	 * @param value 节点的值
	 * @return 链表
	 */
	public List<String> getListByVertexValue(String value){
		return adj.get(map.get(value)).getLists();
	}
	/**
	 * 根据节点的索引返回节点的链表
	 * @param index
	 * @return 如果没有这个索引，则返回空的链表
	 */
	public List<String> getListByVertexIndex(int index){
		if(adj.size()==0) return new LinkedList<String>();
		return adj.get(index).getLists()==null?new LinkedList<String>():adj.get(index).getLists();
	}
	/**
	 * 利用节点值获得节点的索引,比如输入"A",返回1
	 * @param value
	 * @return
	 */
	public int getVertexIndex(String value){
		return map.get(value);
	}
	/**
	 * 利用节点索引获得节点值,比如输入2,返回1
	 * 如果找不到，则返回null
	 * @param index
	 * @return
	 */
	public String getVertexValue(int index){
		//邻接链表不存在或者索引下标为-1时返回null
		if(adj.size()==0||index==-1) return null;
		return adj.get(index).getValue();
	}
	/**
	 * 输出点的索引-值映射
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
	 * 打印所有的边
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
	 * 将其他的邻接链表的点添加进当前图
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
	//邻接表左边的数组+右边的链表
	private class Vertex{
		private String value;	//值
		private List<String> lists;//对应的链表
		
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
	 * 测试函数
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Adjacent_List adj_list = GraphFactory.getAdjacentListInstance("graph\\22_2.txt");
		adj_list.printAllEdges();
		
	}
}
