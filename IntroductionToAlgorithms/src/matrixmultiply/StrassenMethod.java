package matrixmultiply;

public class StrassenMethod {

	public Matrix multiply(Matrix A,Matrix B) {
		int n=A.getRows(),m=B.getColumns();
		Matrix C=new Matrix(n,m);
		if (n==1) {
			C.getArray()[0][0]=A.entry(0,0)*B.entry(0, 0);
		}
		else {
			Matrix  A11=A.getSubMatrix11(),A12=A.getSubMatrix12(),
					A21=A.getSubMatrix21(),A22=A.getSubMatrix22(),
					B11=B.getSubMatrix11(),B12=B.getSubMatrix12(),
					B21=B.getSubMatrix21(),B22=B.getSubMatrix22(),
					S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,
					P1,P2,P3,P4,P5,P6,P7,
					C11,C12,C21,C22;
			
			S1=B12.substract(B22);
			S2=A11.add(A12);
			S3=A21.add(A22);
			S4=B21.substract(B11);
			S5=A11.add(A22);
			S6=B11.add(B22);
			S7=A12.substract(A22);
			S8=B21.add(B22);
			S9=A11.substract(A21);
			S10=B11.add(B12);
			
			P1=multiply(A11, S1);
			P2=multiply(S2, B22);
			P3=multiply(S3, B11);
			P4=multiply(A22, S4);
			P5=multiply(S5, S6);
			P6=multiply(S7, S8);
			P7=multiply(S9, S10);
			
			C11=P5.add(P4).substract(P2).add(P6);
			C12=P1.add(P2);
			C21=P3.add(P4);
			C22=P5.add(P1).substract(P3).substract(P7);
			
			C=compose(C11, C12, C21, C22);
		}
		return C;
	}
	
	public Matrix compose(Matrix A11,Matrix A12,Matrix A21,Matrix A22) {
		//∫œ≤¢æÿ’Û
		int n=A11.getRows()+A21.getRows(),
			m=A11.getColumns()+A12.getColumns();
		int[][] A=new int[n][m];
		
		for (int i = 0; i < A11.getRows(); i++) {
			for (int j = 0; j < A11.getColumns(); j++) {
				A[i][j]=A11.entry(i, j);
			}
		}
		
		for (int i = 0; i < A12.getRows(); i++) {
			for (int j = 0; j < A12.getColumns(); j++) {
				A[i][j+A11.getColumns()]=A12.entry(i, j);
			}
		}
		
		for (int i = 0; i < A21.getRows(); i++) {
			for (int j = 0; j < A21.getColumns(); j++) {
				A[i+A11.getRows()][j]=A21.entry(i, j);
			}
		}
		
		for (int i = 0; i < A22.getRows(); i++) {
			for (int j = 0; j < A22.getColumns(); j++) {
				A[i+A11.getRows()][j+A11.getColumns()]=A22.entry(i, j);
			}
		}
		
		return new Matrix(A);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [][] a={{1,3},
					{7,5}};
		int [][] b={{6,8},
					{4,2}};
		Matrix A=new Matrix(a);
		Matrix B=new Matrix(b);
		A.output();B.output();
		StrassenMethod str=new StrassenMethod();
		Matrix C=str.multiply(A,B);
		C.output();
		
		Matrix P=new Matrix(4);
		Matrix Q=new Matrix(4);
		P.output();Q.output();
		Matrix R=str.multiply(P, Q);
		R.output();
	}

}
