import java.awt.geom.GeneralPath;
import java.util.Timer;
import java.util.TimerTask;
public class matrixMultiply {
	public static void main(String[] args) {
		
		
		/**
		 * 
		 * |||| 	UNCOMMENT THE FOLLOWING IF YOU RUN FROM THE TERMINAL |||
		 * 
		 */
		int type = Integer.valueOf(		args[0]);
		String matrix_dimention = 		args[1]; // not needed for now
		int base_case = Integer.valueOf(	args[2]); //
		String input_file_name  = 		args[3]; //
		String output_file_name = 		args[4]; 
		
		/*
		 * splitting the input file to two matrices: matrix_1, matrix_2
		 */
		Double [][][] arr = Matrix.convert_to_2_matrices(Matrix.readFile(input_file_name)); // hardcoded
		Matrix matrix_1 = new Matrix(arr[0]);
		Matrix matrix_2 = new Matrix(arr[1]);
//		matrix_1.printMatrix();
//		matrix_2.printMatrix();


		Matrix result= null;
		switch(type) {
			case 0 : result = matrix_1.IterativeMultiply(matrix_2); break;
			case 1 : result = matrix_1.StrassensMultiply(matrix_2, base_case); break;
			case 2 : result = matrix_1.IterativeMultiplyWithDelay(matrix_2); break;
		}
		
		result.writeMatrix(output_file_name);
		
//		
	

		
		}
		
	}
