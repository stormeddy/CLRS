package polynomialfft;

import java.util.ArrayList;
import java.util.List;

public class PolyDivide {
	//30.1-2
	class Polynomial{
		double coef;
		int index;
		
		public Polynomial(){
			
		}
		
		public Polynomial(double co,int in){
			coef=co;
			index=in;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return (coef>=0?"+"+coef:"-"+coef)+"x^"+index;
		}
	}
	
	public double divide_with_residual(List<Polynomial> a,List<Polynomial> q,double x0){
//		double r;
		int n=a.size();
		int k=a.get(n-1).index;
		double s=1;		
		if (n==1) {
			Polynomial p=new Polynomial();
			p.coef=a.get(0).coef;
			p.index=a.get(0).index-1;
			q.add(p);
			while(k>0){
				s*=x0;
				k--;
			}
			return s*a.get(0).coef;
		}else {
			while (k>=1) {
				Polynomial p=new Polynomial();
				p.coef=a.get(k).coef;
				p.index=a.get(k).index-1;
				a.get(k-1).coef=a.get(k-1).coef+a.get(k).coef*x0;
				q.add(p);
				k--;
			}
			return a.get(0).coef;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PolyDivide poly=new PolyDivide();
		List<Polynomial> a=new ArrayList<PolyDivide.Polynomial>();
		int[] index={0,1,2};
		double[] coef={2,6,3};
		for (int i = 0; i < coef.length; i++) {
			a.add(poly.new Polynomial(coef[i], index[i]));
		}
		for (Polynomial p : a) {
			System.out.print(p);
		}
		
		System.out.println();
		double x0=2.0;
		List<Polynomial> q=new ArrayList<PolyDivide.Polynomial>();
		System.out.println(poly.divide_with_residual(a, q, x0));
		for (Polynomial p : q) {
			System.out.print(p);
		}
	}

}
