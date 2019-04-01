
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
		
		
		Matrix original = new Matrix("matrix2.txt");
		original.printMatrix();
		
		Matrix arr[] = fix_matrix(original);
		Matrix fixedMatrix = arr[0];
		Matrix marker = arr[1];
		
		fixedMatrix.printMatrix();
		marker.printMatrix();
		
		Matrix originalCheck = getOriginal(fixedMatrix, marker);
		originalCheck.printMatrix();
	}
	
	public static Matrix multiply(Matrix m1, Matrix m2) {
		Matrix [] fixed_and_marker = fix_matrix(m1);
		Matrix m1_fixed = fixed_and_marker[0]; // the fixed matrix
		Matrix m2_fixed = fix_matrix(m2)[0]; // the fixed matrix
		Matrix marker = fixed_and_marker[1]; // the marker matrix
	
		Matrix fixed_result = StrassensMultiply(m1_fixed,m2_fixed); // the result also contains extra zeros
		
		Matrix result = getOriginal(fixed_result, marker); // removing the extra zeros
		
		return result;
	}
	/*
	 *  we have to fix the matrices before calling mulitply
	 * 	after we finish we need to get the original matrices back
	 */
	public static Matrix StrassensMultiply(Matrix m1, Matrix m2) {
		
		
		
		
		return null;
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

}