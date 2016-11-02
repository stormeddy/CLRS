package elementarygraphalgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class GraphFactory {
	
	/**
	 * �涨���ߵ���ĿҪ���ڵ��ڵ����Ŀ
	 * @return
	 * @throws Exception
	 */
	public static Adjacent_List getRandomGraph() throws Exception{
		int vertex_number = 0;
		int edge_number = 0;
		while(true){
			Scanner scanner = new Scanner(System.in);
			System.out.print("�����Ŀ��");
			vertex_number = scanner.nextInt();
			System.out.println("\n�ߵ���Ŀ��");
			edge_number = scanner.nextInt();
			if(vertex_number>edge_number){
				System.out.println("***************************\n�ߵ���ĿҪ���ڵ��ڵ����Ŀ\n***************************");
				continue;
			}
			else break;
		}
		PrintWriter writer = new PrintWriter(new FileOutputStream("graph\\random.txt"), true);
		writer.println(vertex_number+" "+edge_number);
		//�ܹ��������edge_number����
		for(int i=1;i<=vertex_number;i++){
			int end = new Random().nextInt(vertex_number)+1;
			writer.println(i+" "+end);
		}
		int other = edge_number - vertex_number;
		for(int i=0;other>0&&i<other;i++){
			int begin = new Random().nextInt(vertex_number)+1;
			int end = new Random().nextInt(vertex_number)+1;
			writer.println(begin+" "+end);
		}
		return getAdjacentListInstance("graph\\random.txt");
	}
	
	/**
	 * ����һ���ڽӱ�
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Adjacent_List getAdjacentListInstance(String file) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = reader.readLine();
		String[]str = line.split(" ");
		int vertex_size = Integer.parseInt(str[0]);
		int edge_size = Integer.parseInt(str[1]);
		Adjacent_List adjList = new Adjacent_List(vertex_size,edge_size);
		for(int i=0;i<edge_size;i++){
			line = reader.readLine();
			str = line.split(" ");
			adjList.addEdge(str[0],str[1]);
		}
		return adjList; 
	}
	/**
	 * ����һ���ڽӾ���
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Adjacent_Matrix getAdjacentMatrixInstance(String file) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = reader.readLine();
		String[]str = line.split(" ");
		int vertex_size = Integer.parseInt(str[0]);
		int edge_size = Integer.parseInt(str[1]);
		Adjacent_Matrix matrix = new Adjacent_Matrix(vertex_size);
		for(int i=0;i<edge_size;i++){
			line = reader.readLine();
			str = line.split(" ");
			matrix.addEdge(str[0],str[1]);
		}
		return matrix; 
	}
	
	/**
	 * ����һ����Ȩ�ڽ�����
	 * @param file
	 * @return
	 * @throws Exception
	 */
    public static Weighted_Adjacent_List getWeightedAdjacentListInstance(String file)throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = reader.readLine();
		String[]str = line.split(" ");
		int vertex_size = Integer.parseInt(str[0]);
		int edge_size = Integer.parseInt(str[1]);
		Weighted_Adjacent_List adjacent_list = new Weighted_Adjacent_List(vertex_size);
		for(int i=0;i<edge_size;i++){
			line = reader.readLine();
			str = line.split(" ");
			//����������ΪȨ��
			adjacent_list.addEdge(str[0],str[1],Integer.parseInt(str[2]));
		}
		return adjacent_list;
	}
    
    /**
	 * ����һ����Ȩ�ڽӾ���
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Weighted_Adjacent_Matrix getWeightedAdjacentMatrixInstance(String file) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = reader.readLine();
		String[]str = line.split(" ");
		int vertex_size = Integer.parseInt(str[0]);
		int edge_size = Integer.parseInt(str[1]);
		Weighted_Adjacent_Matrix matrix = new Weighted_Adjacent_Matrix(vertex_size);
		for(int i=0;i<edge_size;i++){
			line = reader.readLine();
			str = line.split(" ");
			matrix.addEdge(str[0],str[1],Integer.parseInt(str[2]));
		}
		return matrix; 
	}
    
    public static Weighted_Adjacent_List getWeightedAdjacentListInstance_DifferenceConstraints(String file)throws Exception{
//    	��ȡ���Լ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = reader.readLine();
		String[]str = line.split(" ");
		int vertex_size = Integer.parseInt(str[0]);
		int edge_size = Integer.parseInt(str[1]);
		HashSet<String> vertex_set=new HashSet<String>();
		Weighted_Adjacent_List adjacent_list = new Weighted_Adjacent_List(vertex_size+1);
		for(int i=0;i<edge_size;i++){
			line = reader.readLine();
			str = line.split(" ");
			//����������ΪȨ��
			adjacent_list.addEdge(str[1],str[0],Integer.parseInt(str[2]));
			vertex_set.add(str[0]);
			vertex_set.add(str[1]);
		}
		String source="x0";
//		Iterator<String> iter=vertex_set.iterator();
		for (String string : vertex_set) {
			adjacent_list.addEdge(source, string, 0);
		}
		return adjacent_list;
    }
    
	public static void main(String[] args) throws Exception {
//		Adjacent_List adjList = GraphFactory.getAdjacentListInstance("input"+File.separator+"dfs_input.txt");
		Adjacent_List adjList = GraphFactory.getRandomGraph();
		adjList.printAllEdges();
	}
}
