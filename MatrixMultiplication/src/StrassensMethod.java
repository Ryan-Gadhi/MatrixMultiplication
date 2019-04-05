
public class StrassensMethod {

	
	
	public static void main(String[] args) {
		// do testing here
		
//		Matrix m1 = new Matrix("Matrix1.txt");
//		m1.printMatrix();
//		
//		Matrix q1 = getQuarter(m1, 1);
//		Matrix q2 = getQuarter(m1, 2);
//		Matrix q3 = getQuarter(m1, 3);
//		Matrix q4 = getQuarter(m1, 4);
//
//		q1.printMatrix();
//		q2.printMatrix();
//		q3.printMatrix();
//		q4.printMatrix();
//		
		
//			System.out.println(Math.ceil(Math.log10(64)/Math.log10(2)));
//			System.out.println(Math.pow(2, 7));
		
		// latest commenetd -> l-38
//		Matrix original = new Matrix("matrix2.txt");
//		original.printMatrix();
//		
//		Matrix arr[] = fix_matrix(original);
//		Matrix fixedMatrix = arr[0];
//		Matrix marker = arr[1];
//		
//		fixedMatrix.printMatrix();
//		marker.printMatrix();
//		
//		Matrix originalCheck = getOriginal(fixedMatrix, marker);
//		originalCheck.printMatrix();
		
		
		
		// testing set quarter
//		Matrix origin = new Matrix("matrix1.txt");
//		Matrix quarter = new Matrix("matrix2.txt");
//		
//		origin.printMatrix();
//		quarter.printMatrix();
//		
//		setQuarter(origin, quarter, 1);
//		origin.printMatrix();
		
		Matrix m1 = new Matrix("matrix1.txt");
		Matrix m2 = new Matrix("matrix2.txt");
		m1.printMatrix();
		m2.printMatrix();
		
		Matrix result = StrassensMultiply(m1,m2,1);
		result.printMatrix();
		
	}
	
	public static Matrix multiply(Matrix m1, Matrix m2, int base) {
		Matrix [] fixed_and_marker = fix_matrix(m1);
		Matrix m1_fixed = fixed_and_marker[0]; // the fixed matrix
		Matrix m2_fixed = fix_matrix(m2)[0]; // the fixed matrix
		Matrix marker = fixed_and_marker[1]; // the marker matrix
	
		Matrix fixed_result = StrassensMultiply(m1_fixed,m2_fixed,base); // the result also contains extra zeros
		
		Matrix result = getOriginal(fixed_result, marker); // removing the extra zeros
		
		return result;
	}
	/*
	 *  we have to fix the matrices before calling mulitply
	 * 	after we finish we need to get the original matrices back
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

		Matrix result = new Matrix(4); // all zeros, 2 x 2 size
		

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
		for(int i = 0; i < original.getCols_num(); i++) {
			for (int j =0; j< original.getCols_num(); j++) {
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
			  case 2: matrix[1][0] = q.getE(0, 0); break;
			  case 3: matrix[0][1] = q.getE(0, 0); break;
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
				matrix[i][j]=quarterMatrix[ii][jj]; 

			}
		}
				
		Matrix result = new Matrix(matrix); // update the matrix in the Matrix object
		
		return result; // return the matrix object
	}

}