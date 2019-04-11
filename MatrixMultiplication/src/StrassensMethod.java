
public class StrassensMethod extends Thread{
	double x =0;
 	double y =0;
 	boolean secondCalc =false;
 	int c =0;
 	int matrix_size=0;
 	int baseCase=1;
 	int finished = 0;
 
	Matrix m1 ;
	Matrix m2 ;

	Matrix m11 ;
	Matrix m22 ;

 public static void main(String[] args) {

//	 
//		Matrix m1 = Matrix.generateMatrix(1024, -100 , 100);
//		Matrix m2 = Matrix.generateMatrix(1024, -100 , 100);
//		
//		long start = System.currentTimeMillis();
//		//m1.IterativeMultiply(m2);
//		long end = System.currentTimeMillis();
//		System.out.println("iterative : "+(end-start)/1000.0 +" seconds");
//
//		for(int i=22 ; i<1050 ; i+=20) {	
//		 start = System.currentTimeMillis();
//		 m1.StrassensMultiply(m2, i);
//		 end = System.currentTimeMillis();
//		//System.out.println("Strassen : "+(end-start)/1000.0 +" seconds , base = "+i);
//		int time = (int)(end-start)/1000;
//		 setXY(time,i);

//	}
	 new GraphicsPlot();
}
 
 	public void calc(int c,int matrix_size) {
 			
// 			Matrix m1 = Matrix.generateMatrix(matrix_size+c, -100 , 100);
// 			Matrix m2 = Matrix.generateMatrix(matrix_size+c, -100 , 100);
 			if(c==1)
 			{
 				m1 = m11;
 				m2 = m22;
 			}
 			long start = System.currentTimeMillis();
 			//m1.IterativeMultiply(m2);
 			long end = System.currentTimeMillis();
 			//System.out.println("iterative : "+(end-start)/1000.0 +" seconds");

 			for(int i=baseCase ; i<=matrix_size ; i*=2 ,baseCase*=2) {	
 			 start = System.currentTimeMillis();
 			 //m1.StrassensMultiply(m2, i);
 			 if(baseCase==1)
 			 StrassensMultiply(m1, m2, baseCase);
 			 else
 			m1.StrassensMultiply(m2, baseCase);
 			 end = System.currentTimeMillis();
 			//System.out.println("Strassen : "+(end-start)/1000.0 +" seconds , base = "+i);
 			double time = (end-start)/1000.0;
 			 setXY(time,i);
 			 //System.out.println(c+ " : C");
 			}
				finished++;

 		 }
 	

 	
 	public void run() {
 		 m1 = Matrix.generateMatrix(matrix_size, -100 , 100);
 		 m2 = Matrix.generateMatrix(matrix_size, -100 , 100);

 		 m11 = Matrix.generateMatrix(matrix_size+c, -100 , 100);
 		 m22 = Matrix.generateMatrix(matrix_size+c, -100 , 100);
	 calc(c,matrix_size);
 	}

 	
 	public double [] getXY() {
 		double arr[] = {this.x,this.y};
 		return arr;
 	}
 	
 	public void setXY(double x, double y) {
 		this.x = x;
 		this.y =y;
 	}
		
	
	/**
	 * 
	 * Mulitplies two matrices using the strassens method, in case the
	 * matrices' sizes are not a power of two, it appends zeros then multiplies
	 * @param m2 , the matrix to be multiplied to this matrix
	 * @param base , the base for strassens algorithm, if 1 is passed it 
	 * muitiples the two matrices using the classical method
	 * @return the result of the mulitiplication
	 *
	 */
	public static Matrix multiply(Matrix m1, Matrix m2, int base) {
		Matrix result=null;
		if(isPowerOf2(m2.getCols_num())) {
			 result = StrassensMultiply(m1,m2,base); 
		} else {
			
		Matrix [] fixed_and_marker = fix_matrix(m1);
		Matrix m1_fixed = fixed_and_marker[0]; // the fixed matrix
		Matrix m2_fixed = fix_matrix(m2)[0]; // the second fixed matrix
		Matrix marker = fixed_and_marker[1]; // the marker matrix
	
		Matrix fixed_result = StrassensMultiply(m1_fixed,m2_fixed,base); // the result also contains extra zeros
		
		 result = getOriginal(fixed_result, marker); // removing the extra zeros
		}
		return result;
	}
	/*
	 * Mulitplies two matrices using the strassens method, in case the
	 * matrices' sizes are not a power of two, it does not appends zeros then multiplies
	 * @param m2 , the matrix to be multiplied to this matrix
	 * @param base , the base for strassens algorithm, if 1 is passed it 
	 * muitiples the two matrices using the classical method
	 * @return the result of the mulitiplication
	 */
	public static Matrix StrassensMultiply(Matrix m1, Matrix m2, int base) {
		
		
		// base case
		if (m1.getCols_num() <= base) {
			return m1.IterativeMultiply(m2);
		}

		Matrix A11 = getQuarter(m1, 1);
		Matrix A12 = getQuarter(m1, 2);
		Matrix A21 = getQuarter(m1, 3);
		Matrix A22 = getQuarter(m1, 4);
		
		Matrix B11 = getQuarter(m2, 1);
		Matrix B12 = getQuarter(m2, 2);
		Matrix B21 = getQuarter(m2, 3);
		Matrix B22 = getQuarter(m2, 4);
		
		Matrix D1 = ((A11.addMatrix(A22)).StrassensMultiply(B11.addMatrix(B22),base));
		Matrix D2 = ((A21.addMatrix(A22)).StrassensMultiply(B11,base));
		Matrix D3 = (A11).StrassensMultiply(B12.subMatrix(B22),base);
		Matrix D4 = (A22).StrassensMultiply(B21.subMatrix(B11),base);
		Matrix D5 = ((A11.addMatrix(A12)).StrassensMultiply(B22,base));
		Matrix D6 = ((A21.subMatrix(A11)).StrassensMultiply(B11.addMatrix(B12),base));
		Matrix D7 = ((A12.subMatrix(A22)).StrassensMultiply(B21.addMatrix(B22),base));
		
		
		
		Matrix C11 = (((D1.addMatrix(D4)).subMatrix(D5)).addMatrix(D7));
		Matrix C12 = D3.addMatrix(D5);
		Matrix C21 = D2.addMatrix(D4);
		Matrix C22 = (((D1.addMatrix(D3)).subMatrix(D2)).addMatrix(D6));

		Matrix result = new Matrix(m1.getCols_num()); // all zeros, 2 x 2 size
		
		/*
		 *  for testing
		 */
//		System.out.print("\nA11");
//		A11.printMatrix(); 
//		System.out.print("\nD1");
//		D1.printMatrix();
//		System.out.print("\nC11");
//		C11.printMatrix();
//		System.out.print("\nResult");
//		result.printMatrix();
//		
		
		setQuarter(result, C11, 1);
		setQuarter(result, C12, 2);
		setQuarter(result, C21, 3);
		setQuarter(result, C22, 4);

		
		
		return result;
	}
	
	
	public static Matrix getOriginal(Matrix fixed, Matrix marker) {
		
		Double markerFirstRow [] = marker.getMatrix()[0]; // returns the first row of marker

		// find how many 1's in that row
		int numOfOnes = 0; 
		
		for(int i=0 ; i<markerFirstRow.length; i++) {
			if (markerFirstRow[i] == 1)
				numOfOnes++;
		}
		
		Matrix original = new Matrix(numOfOnes); // = all zeros, the dimetnion will be numOfOnes x numOfOnes
		
		//transfering the items from fixed to result (which has the correct size)
		int size = original.getCols_num();
		for(int i = 0; i < size; i++) {
			for (int j =0; j< size; j++) {
				original.setElement(i, j, fixed.getE(i, j));
			}
		}
		
		return original;
	}
	
	public static Matrix[] fix_matrix(Matrix m) {
					//		if(isPowerOf2(m.getCols_num()))
					//				return m;
		/*
		 *  if we have 33 as dim, the next pow of 2 dim is 
		 *  		64 which is 2^6; minPowOf2 reutrns the 6
		 */
		
		int old_dimention = m.getCols_num();
		int minPowOf2 = (int)Math.ceil(Math.log10(old_dimention)/Math.log10(2)); 
		int new_dimention = (int) Math.pow(2, minPowOf2);
		int delta = new_dimention - old_dimention; // number of rows on cols that need to be added to the matrix
		
		Matrix marker = new Matrix(new_dimention); // all zeros, to know which elements have been changed
		Matrix result = new Matrix(new_dimention); // all zeros, will be overidden
		
		/*
		 *  marker: marks the elements of the original matrix
		 * 	resutl: is the m matrix with the appended zeros
		 * 
		 */
		for(int i=0 ; i < old_dimention; i++) {
			for(int j=0; j< old_dimention; j++) {
				marker.setElement(i, j, 1);
				result.setElement(i, j,m.getE(i, j));
			}
		}
		
		Matrix array [] = {result, marker}; 
		
		return array;
	}
	
	
	public static boolean isPowerOf2(int dimention) {
		double power = Math.log10(dimention)/Math.log10(2); // 2^? = dimention > if decimal value only then power of two otherwize not

		boolean isPowerOf2 = ((int)(power)*1.0-power==0);
		if(isPowerOf2)
			return true;		
		
		return false;
	}
	
	
	
	
	
	/*
	 *  reutrn a quarter of the passed matrix
	 */
	public static Matrix getQuarter(Matrix m, int num) {
		
		int i = 0 ; // index to start reading the matrix
		int j = 0 ;
		int end_i =0 ;
		int end_j= 0 ;
		
		Double [][] matrix = m.getMatrix();
		int qDim = m.getCols_num()/2; // we have N x N , we take N/2 x N/2
		Double [][] quarterMatrix = new Double[qDim][qDim]; // quarter dimeniton
		int flag_j =0;

		if(qDim == 1) {
			Double [][] d = new Double[1][1];
			
			switch (num) {
			  case 1: d[0][0] = matrix[0][0]; break;
			  case 2: d[0][0] = matrix[0][1]; break;
			  case 3: d[0][0] = matrix[1][0]; break;
			  case 4: d[0][0] = matrix[1][1]; break;
			  
			}
			return new Matrix(d);
		}
//			
			
			
		/*
		 * 		|Case1		Case2|
		 * 		|Case3		Case4|
		 * 
		 */
		switch (num) // finding the starting i and j
		{
			case 1 : i = 0 ; end_i = qDim; 		j =0 ; end_j = qDim; 		flag_j =j; break;
			case 2 : i = 0 ; end_i = qDim; 		j = qDim; end_j= qDim*2;		flag_j =j; break; 
			case 3 : i = qDim;  end_i= qDim*2;	j= 0 ; end_j = qDim; 		flag_j =j; break;
			case 4 : i = qDim;end_i=qDim*2 ;		j = qDim;  end_j= qDim*2;	flag_j =j; break;
		}
		
		
		for (int ii=0 ; i < end_i ; i++,ii++) 
		{	
			int jj = 0;
			for (j=flag_j ; j < end_j ; j++, jj++) 
			{
				quarterMatrix[ii][jj]=matrix[i][j]; 

			}
		}
		
		Matrix result = new Matrix(quarterMatrix);
		
		return result;
	}
	
	
	/*
	 *  reutrn a quarter of the passed matrix
	 */
	public static Matrix setQuarter(Matrix m, Matrix q,int num) {
		
		int i = 0 ; // index to start reading the matrix
		int j = 0 ;
		int end_i =0 ;
		int end_j= 0 ;
		
		Double [][] matrix = m.getMatrix();
//		int qDim = m.getCols_num()/2; // we have N x N , we take N/2 x N/2
		int qDim = q.getCols_num();
		int flag_j =0;
		
		// new for set
		Double [][] quarterMatrix = q.getMatrix();
		
		/*
		 *  special case if quarter size = 1
		 * 
		 */
		if(qDim == 1) {
			switch (num) {
			  case 1: matrix[0][0] = q.getE(0, 0); break;
			  case 2: matrix[0][1] = q.getE(0, 0); break;
			  case 3: matrix[1][0] = q.getE(0, 0); break;
			  case 4: matrix[1][1] = q.getE(0, 0); break;
			}
			
			Matrix result = new Matrix(matrix);
			return result;
		}

		
		/*
		 * 		|Case1		Case2|
		 * 		|Case3		Case4|
		 * 
		 */
		switch (num) // finding the starting i and j
		{
			case 1 : i = 0 ; end_i = qDim; 		j =0 ; end_j = qDim; 		flag_j =j; break;
			case 2 : i = 0 ; end_i = qDim; 		j = qDim; end_j= qDim*2;		flag_j =j; break; 
			case 3 : i = qDim;  end_i= qDim*2;	j= 0 ; end_j = qDim; 		flag_j =j; break;
			case 4 : i = qDim;end_i=qDim*2 ;		j = qDim;  end_j= qDim*2;	flag_j =j; break;
		}
		
		
		for (int ii=0 ; i < end_i ; i++,ii++) 
		{	
			int jj = 0;
			for (j=flag_j ; j < end_j ; j++, jj++) 
			{	
//				m.printMatrix();
//				q.printMatrix();
				matrix[i][j]=
						quarterMatrix[ii][jj]; 

			}
		}
				
		Matrix result = new Matrix(matrix); // update the matrix in the Matrix object
		
		return result; // return the matrix object
	}

}