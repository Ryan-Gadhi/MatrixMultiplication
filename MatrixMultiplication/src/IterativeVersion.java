
public class IterativeVersion {
	
	public static void main(String[] args) {
		// do testing here
	}
	
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
