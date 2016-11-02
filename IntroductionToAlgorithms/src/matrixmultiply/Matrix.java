package matrixmultiply;

import java.util.Random;

public class Matrix {
	private int[][] matrix;
	private int n,m;
	
	public Matrix(int n){
		matrix=new int[n][n];
		this.n=n;this.m=n;
		Random random=new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j]=random.nextInt()%100;
			}
		}
	}
	
	public Matrix(int n,int m){
		matrix=new int[n][m];
		this.n=n;this.m=m;
		Random random=new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j]=random.nextInt()%100;
			}
		}
	}
	
	public Matrix(int[][] A) {
		n=A.length;m=A[0].length;
		matrix=new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j]=A[i][j];
			}
		}
	}
	
	public int[][] getArray() {
		return matrix;
	}
	
	public Matrix getSubMatrix11() {
		//n要求为2的幂
		if ((n&(n-1))==0 && n==m) {
			int [][] matrix11=new int[n/2][n/2];
			for (int i = 0; i < n/2; i++) {
				for (int j = 0; j < n/2; j++) {
					matrix11[i][j]=matrix[i][j];
				}
			}
			return new Matrix(matrix11);
		}else {
			return null;
		}		
	}
	
	public Matrix getSubMatrix12() {
		if ((n&(n-1))==0 && n==m) {
			int [][] matrix12=new int[n/2][n/2];
			for (int i = 0; i < n/2; i++) {
				for (int j = 0; j < n/2; j++) {
					matrix12[i][j]=matrix[i][j+n/2];
				}
			}
			return new Matrix(matrix12);
		}else {
			return null;
		}		
	}
	
	public Matrix getSubMatrix21() {
		if ((n&(n-1))==0 && n==m) {
			int [][] matrix21=new int[n/2][n/2];
			for (int i = 0; i < n/2; i++) {
				for (int j = 0; j < n/2; j++) {
					matrix21[i][j]=matrix[i+n/2][j];
				}
			}
			return new Matrix(matrix21);
		}else {
			return null;
		}		
	}
	
	public Matrix getSubMatrix22() {
		if ((n&(n-1))==0 && n==m) {
			int [][] matrix22=new int[n/2][n/2];
			for (int i = 0; i < n/2; i++) {
				for (int j = 0; j < n/2; j++) {
					matrix22[i][j]=matrix[i+n/2][j+n/2];
				}
			}
			return new Matrix(matrix22);
		}else {
			return null;
		}		
	}
	
    public void output() {  
        //输出矩阵
    	for (int i = 0; i < matrix.length; i++) {  
            for (int j = 0; j < matrix[i].length; j++) {  
                System.out.print(matrix[i][j] + "\t");  
            }  
            System.out.println();
        }  
    	System.out.println();
    }
    
    public Matrix add(Matrix b) {  
    	  
        int[][] matrixOfB = b.getArray();  
        int n = matrixOfB.length;  
        int m = matrixOfB[0].length; 
        int[][] matrixSum=new int[n][m];
  
        if (n != matrix.length || m != matrix[0].length) {  
            System.out.println("矩阵的长度不一致，不能相加");  
        } else {  
            for (int i = 0; i < n; i++) {  
                for (int j = 0; j < m; j++) {  
                    matrixSum[i][j] = matrix[i][j]+matrixOfB[i][j];  
                }  
            }  
  
        }
		return new Matrix(matrixSum);  
    }
    
    public Matrix substract(Matrix b) {  
    	
    	int[][] matrixOfB = b.getArray();  
    	int n = matrixOfB.length;  
    	int m = matrixOfB[0].length; 
    	int[][] matrixDif=new int[n][m];
    	
    	if (n != matrix.length || m != matrix[0].length) {  
    		System.out.println("矩阵的长度不一致，不能相减");  
    	} else {  
    		for (int i = 0; i < n; i++) {  
    			for (int j = 0; j < m; j++) {  
    				matrixDif[i][j] = matrix[i][j]-matrixOfB[i][j];  
    			}  
    		}  
    		
    	}
    	return new Matrix(matrixDif);  
    }  
	
	public int entry(int i,int j) {
		return matrix[i][j];
	}
	
	public int getRows() {
		return n;
	}
	
	public int getColumns() {
		return m;
	}
}
