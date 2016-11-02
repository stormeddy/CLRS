package k_merge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * k·�ϲ�,��k��������������������
 * ʹ����С��,ÿȡ��һ����Сֵ,ԭ��Сֵ�����б����һ��ֵ�ͼӽ���С��
 * @author xiazdong
 */
public class K_Merge {
	private static int MAX = Integer.MAX_VALUE;
	private Pair pair[];
	private int k;		//��������ʼ��Ϊ�б�ĸ���
	private int[]point;	//point[i]Ϊ��i��list���ڵ�ָ��ָ����
	private List<int[]>list;
	private int heapsize;	//�������ѵ���Ŀ���������һ�������Ѿ�����ʱ���ѵĴ�С�����
	public K_Merge(List<int[]> list){
		this.k = list.size();
		pair = new Pair[k+1];
		point = new int[k];
		this.list = list;
		for(int i=0;i<k;i++){
			point[i] = 0;
		}
		for(int i=0;i<k;i++){
			int[] arr = list.get(i);
			Pair p = new Pair(i,arr[point[i]]);
			pair[i+1] = p;
		}
		heapsize = k;
		BUILD_MIN_HEAP();
	}
	
	private void BUILD_MIN_HEAP() {
		for(int i=heapsize/2;i>=1;i--){
			MIN_HEAPIFY(i);
		}
	}
	private void MIN_HEAPIFY(int x){
		int smallest = x;
		if(2*x<=heapsize&&pair[2*x].value<pair[smallest].value){
			smallest = 2*x;
		}
		if((2*x+1)<=heapsize&&pair[2*x+1].value<pair[smallest].value){
			smallest = 2*x+1;
		}
		if(smallest!=x){
			swap(smallest,x);
			MIN_HEAPIFY(smallest);
		}
	}
	private void swap(int largest, int x) {
		Pair temp = pair[largest];
		pair[largest] = pair[x];
		pair[x] = temp;
	}
	
	private void INSERT(Pair p) {
		pair[heapsize] = new Pair(p.index,MAX);
		DECREASE_KEY(heapsize,p.value);
	}
	private void DECREASE_KEY(int x, int k) {
		
		if(pair[x].value<k){
			System.err.println("����ҪDECREASE");
			return;
		}
		pair[x].value = k;
		while(x>1&&pair[x/2].value>pair[x].value){
				swap(x/2,x);
				x = x/2;
		}
		
	}
	private Pair EXTRACT_MIN() {
		Pair min = pair[1];
		pair[1] = pair[k];
		MIN_HEAPIFY(1);
		return min;
	}
	public List<Integer> k_mergesort(){
		List<Integer> l = new LinkedList<Integer>();
		while(heapsize>0){
			Pair min = EXTRACT_MIN();
			l.add(min.value);
			if(point[min.index]<list.get(min.index).length-1){
				point[min.index]++;
				Pair p = new Pair(min.index,list.get(min.index)[point[min.index]]);
				INSERT(p);
			}
			else{
				heapsize--;
			}
		}
		return l;
	}
	public static void main(String[] args) {
		int[] a1 = {1,3,5,7,9};
		int[] a2 = {2,4,6,8,10};
		List<int[]>list = new ArrayList<int[]>();
		list.add(a1);
		list.add(a2);
		K_Merge merge = new K_Merge(list);
		List<Integer> l = merge.k_mergesort();
		for(Integer e:l) System.out.println(e);
	}
}

class Pair{
	int index;//indexΪλ�ڵڼ���list�е�Ԫ��
	int value;
	public Pair(int index, int value) {
		super();
		this.index = index;
		this.value = value;
	}
	public Pair() {
		super();
	}
	
}