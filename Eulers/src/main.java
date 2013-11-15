import java.math.*;

public class main {
	public static void main(String[] args){
		double a =1, b=2, alpha=-1;
		int N=20;
		double h = 0.05;
		double t = a;
		double w = alpha;
		double z = -(1/t);
		double e = Math.abs(w-z);
		System.out.println("T\tW\tsoln\terror");
		System.out.printf("%6.3f\t%6.3f", t, w);
		System.out.printf("\t%6.3f\t%6.3f\n", z, e);
		for(int i=1; i<=N; i++){
			w=w+h*((1/(t*t))-(w/t)-(w*w));
			t=a+i*h;
			z = -(1/t);
			e = Math.abs(w-z);
			System.out.printf("%6.3f\t%6.3f", t, w);
			System.out.printf("\t%6.3f\t%6.3f\n", z, e);
		}
	}
}
