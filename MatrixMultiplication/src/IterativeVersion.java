
/**
 * A class for handling matrix multiplication the classical way
 * @author Ryan
 *
 */
public class IterativeVersion {
	
	public static void main(String[] args) {
	}
	
	/**
	 * multiplies the two matrices the classical way, but if the matrices 
	 * are not a power of two, it converts it to a power of by appending zeros
	 * then multipes 
	 * @param m2 , the second matrix to be multiplied
	 * @param m1 , the first matrix to be multiplied
	 * @return the result of m1*m2
	 * 
	 */
	public static Matrix mutiplyWithDelay(Matrix m1, Matrix m2) {
		Matrix result=null;
		if(StrassensMethod.isPowerOf2(m2.getCols_num())) {
			 result =  multiply(m1, m2);
		} else {
			
		Matrix [] fixed_and_marker = StrassensMethod.fix_matrix(m1);
		Matrix m1_fixed = fixed_and_marker[0]; // the fixed matrix
		Matrix m2_fixed = StrassensMethod.fix_matrix(m2)[0]; // the second fixed matrix
		Matrix marker = fixed_and_marker[1]; // the marker matrix
	
		Matrix fixed_result =  multiply(m1,m2);
		
		 result = StrassensMethod.getOriginal(fixed_result, marker); // removing the extra zeros
		}
		return result;
	}
	
	/**
	 * Multiplies two matrices the classical way, the order is this_Matrix * passed_Matrix
	 * @param m2 , the second matrix to be multiplied
	 * @param m1 , the first matrix to be multiplied
	 * @return the result of m1*m2
	 */
	public static Matrix multiply(Matrix m1, Matrix m2) {
		Matrix result = new Matrix(m1); // creates a new matrix with the same dimensions but with 0s
		int n = m1.getCols_num();

		for (int i =0 ;i < n;i++) // this refers to the first matrix, and m2 is the second matrix 
			for (int j=0; j< n; j++) {
				for (int k=0; k<n ;k++)
					{
						double multiplication = m1.getE(i,k)*m2.getE(k,j) ; // 
						result.setElement(i,j, result.getE(i,j)+multiplication);
					}
			}
		return result;	
	}

}
