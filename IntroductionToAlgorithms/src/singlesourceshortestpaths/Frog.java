package singlesourceshortestpaths;

import java.util.Scanner;

public class Frog {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()){
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			int P = scanner.nextInt();
			int [][] mi = new int [n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					mi[i][j] = scanner.nextInt();
				}
			}
			boolean [][] visited = new boolean [n][m];
			visited[0][0] = true;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if(mi[i][j] ==0)
						visited[i][j] = true;
				}
			}
			String path = "";
			Frog solution = new Frog();
			solution.migong(n, m, P, mi, 0, 0, path, visited);
		}
	}
	boolean flag = false;
	public void migong(int n,int m,int P,int [][] mi,int i,int j,String path,boolean [][] visited){
		path = path+"["+i+","+j+"],";
		if(i==0 && j==m-1){
			System.out.println(path.substring(0, path.length()-1));
			flag = true;
//			for (int ii = 0; ii < n; ii++) {
//				for (int jj = 0; jj < m; jj++) {
//					visited[i][j] = true;
//				}
//			}
			return;
		}
		if(j<m-1 && !visited[i][j+1]&& !flag){
			visited[i][j+1] = true;
			migong(n, m, P-1, mi, i, j+1, path, visited);
		}		
		if(i>0 && !visited[i-1][j] && !flag){
			visited[i-1][j] = true;
			migong(n, m, P-3, mi, i-1, j, path, visited);
		}
		if(i<n-1 && !visited[i+1][j]&& !flag){
			visited[i+1][j] = true;
			migong(n, m, P, mi, i+1, j, path, visited);
		}
		if(j>0 && !visited[i][j-1]&& !flag){
			visited[i][j-1] = true;
			migong(n, m, P-1, mi, i, j-1, path, visited);
		}		
		if(!flag){
			boolean a = false;
			my:for (int ii = 0; ii < n; ii++) {
				for (int jj = 0; jj < m; jj++) {
					if(visited[ii][jj] ==false){
						a=true;
						break my;
					}
						
				}
			}
			if(!a && !flag){
				System.out.println("Can not escape!");
				flag = true;
			}
		}
	}
}
