package randomizeinplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Test {
	//生成  均匀随机排列
	public static void main(String[] args) {
		randomize_in_place(new int[]{1,2,3,4,5,6,7});
	}

	public static void randomize_in_place(int[] arr) {        //核心函数 O(n)
		int length = arr.length;
		for(int i=0;i<length;i++){
			IntegerWeightRandom rand = new IntegerWeightRandom();
			rand.addWeightNumRange(1, i, length-1);
			int idx = rand.getNextInt();
			swap(arr,idx,i);
		}
		for(int i=0;i<arr.length;i++)
			System.out.print(arr[i]+"  ");
		
	}

	private static void swap(int[] arr, int idx, int i) {
		int tmp = arr[idx];
		arr[idx] = arr[i];
		arr[i] = tmp;
	}
}
class IntegerWeightRandom {  
    private List<Integer> weights = new ArrayList<Integer>();  
    private List<List<Integer>> numbers = new ArrayList<List<Integer>>();  
    private Integer totalWeight;  
    private Random random = new Random();  
    public IntegerWeightRandom() {  
          
    }  
      
    private Integer getTotalWeight(){  
        if(totalWeight == null){  
            totalWeight = 0;  
            for(Integer weight:weights){  
                totalWeight += weight;  
            }  
        }  
        return totalWeight;  
    }  
    /* 
     * 为某个number赋予一个weight，可能一个weight对应多个numbers，因此List<Integer>weight中一个元素对应List<List<Integer>>numbers中的一个List<Integer> 
     * */  
    //此函数表示一个weight对应一个number  
    public void addWeightNumber(int weight,int num){      
        weights.add(weight);  
        List<Integer> list = new ArrayList<Integer>();  
        list.add(num);  
        numbers.add(list);  
    }  
    //此函数表示一个weight对应多个number  
    public void addWeightNumRange(int weight,int numFrom,int numTo,int...numExcludes){  
        weights.add(weight);  
        List<Integer> list = new ArrayList<Integer>();  
        for(int i = numFrom;i <= numTo;i++){  
            boolean exclude = false;  
            for(int numExclude:numExcludes){  
                if(i == numExclude){  
                    exclude = true;  
                }  
            }  
            if(!exclude){  
                list.add(i);  
            }  
        }  
        numbers.add(list);  
    }  
    /* 
     * 举例： 
     * 如果数字8的weight为1，数字9的weight为2，则totalWeight=3，8的范围为(0,1),9的范围为[1,3)如果随机数为2，则在9的范围内 
     * */  
    //此函数为随机取一个整数（按照权重概率取）  
    public Integer getNextInt(){  
        int weightRandom = random.nextInt(getTotalWeight());  
        int weightCount = 0;  
        for(int i = 0;i < weights.size();i++){  
            int weight = weights.get(i);
            if(weightRandom >= weightCount && weightRandom < weightCount + weight){  
                List<Integer> numList = numbers.get(i);  
                if(numList.size() == 1){
                    return numList.get(0);  
                }  
                int numRandom = random.nextInt(numList.size());  
                return numList.get(numRandom);  
            }  
            weightCount += weight;  
        }  
          
        return null;  
    }  
  
}