import java.util.Timer;
import java.util.TimerTask;

public class Main {
	public static void main(String[] args) {
		
		//.. this is a demo on how to use the Matrix class
		Matrix m1 = new Matrix("Matrix1.txt");
		m1.printMatrix();
		System.out.println();
		
		Matrix m2 = new Matrix("Matrix2.txt");
		m2.printMatrix();
		
		Matrix r = m1.IterativeMultiply(m2);
		r.printMatrix();
		
		Matrix t = Matrix.generateMatrix( 4000, 0, 4000); //29369.74 seconds for iterative version

		System.out.println("t1 generated");
		Matrix t2 = Matrix.generateMatrix( 4000, 0, 4000); //  time taken = arround 1 hour
		System.out.println("t2 generated+\ncurrently multiplyign");

		//Matrix.writeMatrix("testing.txt",t);
		
		
		/**
		 *  the follwing copares the two alogrithms
		 */
		long start = System.currentTimeMillis(); // gives wronge answer > change the way 
		// .. call interative matrix mulitplicaiton
		Matrix result = t.IterativeMultiply(t2);
		
		//Matrix r3 = m1.addMatrix(m3);
		//r3.printMatrix();
		System.out.println();
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		System.out.println(timeElapsed/100.0 + " seconds for iterative version");
		
		start = System.currentTimeMillis();
		// .. call interative matrix mulitplicaiton
		finish = System.currentTimeMillis();
		timeElapsed = finish - start;
		System.out.println(timeElapsed/100.0 + " seconds for srtassens version");
		
		
		}
		
	}
