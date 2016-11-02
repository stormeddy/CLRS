package disjointset;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Offline_Minimum {
	//21-1
	public int[] offline_minimum(String s,int n){
		//Ԥ����������㼯�Ϻͱ߼���
		String[] arr=s.split("e");
		int m=arr.length-1;
		int k=0;
		int v_num=n;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals("")) {
				v_num++;
			}
		}
		int[] v=new int[v_num];//���㼯��,����ee֮���ʹ��-1��Ϊ���붥��
		List<Integer> e=new ArrayList<Integer>();//���ֱ߼���		
		for (int i = 0; i < arr.length; i++) {
			String[] small=arr[i].split("");
			String first=small[0];
			if (first.equals("")) {
				v[k]=-1;
				k++;
			}else{
				int first_num=Integer.valueOf(first);
				for (int j = 0; j < small.length; j++) {
					if (!small[j].equals("")) {
						v[k]=Integer.valueOf(small[j]);	
						e.add(first_num);
						e.add(v[k]);										
						k++;
					}
				}
			}			
		}
		
//		System.out.println(Arrays.toString(v));
		int[] edge=new int[e.size()];//������ʽ�Ĳ��ֱ߼���
		for (int i = 0; i < edge.length; i++) {
			edge[i]=e.get(i);
		}
//		System.out.println(Arrays.toString(edge));
		
		
		int[] extracted=new int[m+1];//extract[0]��ʹ��
		DS_Forest_Null f=new DS_Forest_Null(n);//ʵ�ʵĶ�����Ŀ��ȻΪn,��1��n
		f.connect_components(v, edge);
		for (int i = 0; i < v.length; i++) {
			System.out.print(f.find_set(v[i])+" ");
		}
		System.out.println();
		for (int i = 1; i <= n; i++) {
			int j=f.forest.indexOf(f.find_set(i));//���ҵ�i���ڵļ��ϴ������ҵ���Ӧ�ļ��ϱ��
			if (j!=-1&&j!=m+1) {
				extracted[j]=i;
				int l=j+1;
				while (l<f.forest.size()&&f.forest.get(l)==null) {
					l++;
				}
				if(l!=f.forest.size()){
					if (f.forest.get(l)==-1) {
					f.forest.set(l, f.forest.get(j));
					f.forest.set(j, null);
					}else {
					f.union_x_y(f.forest.get(j), f.forest.get(l));
					f.forest.set(j, null);					
					}
				}				
			}
			for (int i1 = 0; i1 < v.length; i1++) {
				System.out.print(f.find_set(v[i1])+" ");
			}
			System.out.println();
		}
		System.out.println(m);
		System.out.println(f.forest.toString());
		return extracted;
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="48e3e926eee17e5";
		Offline_Minimum o=new Offline_Minimum();
		System.out.println(Arrays.toString(o.offline_minimum(s, 9)));
	}

}
