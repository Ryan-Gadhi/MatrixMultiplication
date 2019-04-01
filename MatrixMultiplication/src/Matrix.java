import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;

public class Matrix {
	private Double[][] matrix;
	private int rows_num ;
	private int cols_num ;// buggy?
	
	

	/**
	 * 
	 * @param file, file to be read to be converted to matrix
	 */
	public Matrix(String file) 
	{
		matrix = convert_to_matrix(readFile(file)); // hardcoded 
		this.rows_num = this.matrix.length;
		this.cols_num = this.matrix[0].length; // buggy?
	}
	
	public Matrix(Double arr [][]) 
	{
		this.matrix= arr;
		this.rows_num = arr.length;
		this.cols_num = arr[0].length;
		
	}
	public Matrix(Matrix matrix_2)  // intializaing a 0 matrix with same size as Matrix2
	{
		 this.rows_num = matrix_2.getRows_num();
		 this.cols_num = matrix_2.getCols_num(); // buggy?
		 
		 Double [][] temp= new Double[this.rows_num][this.cols_num];
		for(int i=0 ; i< rows_num;i++)
			for(int j=0; j< cols_num; j++)
				temp[i][j] = 0.0;
		
		this.matrix = temp;
	}

	
	public void addToElement(int i,int j,double value) 
		{
			this.matrix[i][j]+= value;
		}

	public void setElement(int i,int j,double value) 
	{
		this.matrix[i][j]= value;
	}
	
	public void addToElements (double value) 
	{
		
		
		for (int i=0; i <rows_num; i++)
			for (int j=0; j<cols_num ; j++)
				this.matrix[i][j]+=value;
	}
	
	/**
	 * 
	 * @param matrix_2
	 * @return
	 */
	public Matrix addMatrix(Matrix matrix_2) 
		{

			Matrix result = new Matrix (this);
			
//			int m2_rows= matrix_2.getRows_num();
//			int m2_cols= matrix_2.getCols_num();
			
			for (int i=0; i <rows_num; i++)
				for (int j=0; j<cols_num ; j++) 
				{
					double sum = this.matrix[i][j]+matrix_2.getE(i, j);	
					result.setElement(i, j, sum); 
				}
			return result;
					
		}
	
	public Matrix IterativeMultiply(Matrix m2) {
				
		return IterativeVersion.multiply(this,m2);
		
	}
	
	public Matrix StrassensMultiply(Matrix m2) {
		
		
		return StrassensMethod.multiply(this, m2);
	}
	
	
	/**
	 *  r and a and 3 > r,a,3 
	 * @param str
	 * @return
	 */
	public static Double[][] convert_to_matrix(String str){
		
		String [] rows = str.split("\n");		
		int rows_num = rows.length;
		int cols_num = rows[0].split(" ").length; // buggy?
		
		Double result [][] = new Double [rows_num][cols_num]; // 0 size ?

		for (int i=0; i<rows_num ; i++)
			{
				String [] cols = rows[i].split(" ");
				for(int j =0 ; j<cols_num; j++) 
					{
						result[i][j] = Double.valueOf(cols[j]); // iterating over ever column to build the matrix
					}
			}
		
		return result;
	}
	
	/**
	 *  just a way to print the matrix
	 */
	public void printMatrix() { // non generic: bad smell
		
		System.out.println();
		for (int i=0 ; i < this.rows_num ; i++) 
			{
				System.out.println(Arrays.toString(this.matrix[i]));
			}
	}
	
	
	public static Matrix generateMatrix(int x,int min, int max) 
	{
		String matrix_in_file = "";
		Double [][] newArr = new Double [x][x];
		
		for(int i=0; i<x ; i++) {
			for(int j=0 ; j<x ; j++) {
	
				double random_value = min + Math.random()* (max-min);
				
				random_value =(double)Math.round(random_value * 1000d) / 1000d;
				
				//matrix_in_file+= String.valueOf(random_value+"\n");
				newArr[i][j]=random_value;
			}
		}
		
		Matrix newMatrix = new Matrix(newArr);
		
		
		return newMatrix;
	}
	
	public static void writeMatrix(String file,Matrix m1) {

		String temp = "";
		Double [][] matrix1 = m1.getMatrix();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(file), "utf-8"))) {
			int size = m1.getCols_num();
			for (int i =0 ; i<size-1;i++)
			{
				temp ="";
				for (int j=0 ; j< size;j++)
					temp += matrix1[i][j] + " ";
				writer.write(temp.substring(0,temp.length()-1)+"\n");
			}
				for (int j=0 ; j< size;j++)
					temp += matrix1[size-1][j] + " "; // to solve the additional \n efficinelty
					writer.write(temp.substring(0,temp.length()-1));
				
	   //writer.write(matrix.getMatrix());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param file_name
	 * @return
	 */
	public static String readFile(String file_name) {
		BufferedReader br = null;
		String output = "";
		try 
		{	
			br = new BufferedReader(new FileReader(file_name));
			String line;
			while ((line=br.readLine()) != null)
				output+=line+"\n";	
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		finally {
			try 
			{
				br.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			
		}
		return output;	
	}
	
	public Double getE(int i,int j) {
		return this.matrix[i][j];
	}
	
	public Double[][] getMatrix() {
		return matrix;
	}


	public void setMatrix(Double[][] matrix) {
		this.matrix = matrix;
	}


	public int getRows_num() {
		return rows_num;
	}


	public void setRows_num(int rows_num) {
		this.rows_num = rows_num;
	}
	

	public int getCols_num() {
		return cols_num;
	}


	public void setCols_num(int cols_num) {
		this.cols_num = cols_num;
	}



}
