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

/**
 * This class facilitates matrix mathimatical operations
 * it also allows for reading from and writing to files 
 * @author Ryan Gadhi and Abdullah Al Mezayen
 *
 */
/**
 * @author projectx
 *
 */
public class Matrix {
	private Double[][] matrix;
	private int rows_num ;
	private int cols_num ;// buggy?
	
	

	/**
	 * makes a new Matrix object and by assinging the file 
	 * test to the matrix variable  
	 * @param file , file name to be read
	 */
	public Matrix(String file) 
	{
		matrix = convert_to_matrix(readFile(file)); // hardcoded 
		this.rows_num = this.matrix.length;
		this.cols_num = this.matrix[0].length; // buggy?
	}
	
	
	/**
	 * allows for creating a new object by providing 
	 * a 2D array that will be assined to the matrix variable
	 * 
	 * @param arr , the array the will be assigned for the matrix variable
	 * 
	 */
	public Matrix(Double arr [][]) 
	{
		this.matrix= arr;
		this.rows_num = arr.length;
		this.cols_num = arr[0].length;
		
	}
	
	/**
	 * makes a new Matrix with zero entries
	 * @param size , the size of the wanted Matrix
	 */
	public Matrix(int size) {
		 this.rows_num = size;
		 this.cols_num = size;
		 Double [][] temp= new Double[size][size];

		for(int i=0 ; i< size;i++)
			for(int j=0; j< size; j++)
				temp[i][j] = 0.0;
		
		this.matrix = temp;
		
	}
	/**
	 * intializaing a 0 matrix with same size as the passed Matrix
	 * 
	 * @param matrix_2 , the Matrix that the size of the new Matrix
	 *  will be taken from
	 */
	public Matrix(Matrix matrix_2) 
	{
		 this.rows_num = matrix_2.getRows_num();
		 this.cols_num = matrix_2.getCols_num(); // buggy?
		 
		 Double [][] temp= new Double[this.rows_num][this.cols_num];
		for(int i=0 ; i< rows_num;i++)
			for(int j=0; j< cols_num; j++)
				temp[i][j] = 0.0;
		
		this.matrix = temp;
	}
	
	/**
	 * compares this Matrix to the passed Matrix element wize
	 * @param m2 , the Matrix that is wanted to be compared to
	 * @return boolean , that tells whether they are equal or not
	 */
	public boolean equals(Matrix m2) {
		
		for ( int i =0 ; i< m2.getCols_num() ; i++) {
			for(int j =0 ; j< m2.getCols_num(); j++) {
				if(Math.round(this.getE(i, j))!=Math.round(m2.getE(i, j))) { // should not be rounded ! change it later
					return false;
				}
			}
		}
		
		return true;
	}

	
	/**
	 * adds a value to the passed element
	 * @param i , the raw number of the element  
	 * @param j , the column number of the element
	 * @param value , the value that is wanted to be added to the element
	 */
	public void addToElement(int i,int j,double value) 
		{
			this.matrix[i][j]+= value;
		}

	/**
	 * sets a value to the passed element
	 * @param i , the raw number of the element  
	 * @param j , the column number of the element
	 * @param value, the value that is wanted to be set to the element
	 */
	public void setElement(int i,int j,double value) 
	{
		this.matrix[i][j]= value;
	}
	
	/**
	 * adds a certain value to all elements in the Matrix
	 * @param value , the value that will be added to all
	 */
	public void addToElements (double value) 
	{
		
		
		for (int i=0; i <rows_num; i++)
			for (int j=0; j<cols_num ; j++)
				this.matrix[i][j]+=value;
	}
	
	
	/**
	 * Adds another matrix to this Matrix element wize
	 * @param matrix_2 , the Matrix that is wanted to be added to this
	 * @return the result of the addition
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
	
	/**
	 * substracts another matrix from this Matrix element wize
	 * @param matrix_2 , the Matrix that is wanted to be subtracted from this
	 * @return the result of the subtraction
	 */
	public Matrix subMatrix(Matrix matrix_2) 
	{

		Matrix result = new Matrix (this);
		
//		int m2_rows= matrix_2.getRows_num();
//		int m2_cols= matrix_2.getCols_num();
		
		for (int i=0; i <rows_num; i++)
			for (int j=0; j<cols_num ; j++) 
			{
				double sum = this.matrix[i][j]-matrix_2.getE(i, j);	
				result.setElement(i, j, sum); 
			}
		return result;
				
	}

	
	/**
	 * Multiplies two matrices the classical way, the order is this_Matrix * passed_Matrix
	 * @param m2 , the matrix that will be multiplied by this
	 * 
	 * @return the result of the the multiplication
	 */
	public Matrix IterativeMultiply(Matrix m2) {
				
		return IterativeVersion.multiply(this,m2);
		
	}
	
	/**
	 * multiplies the two matrices the classical way, but if the matrices 
	 * are not a power of two, it converts it to a power of by appending zeros
	 * then multipes 
	 * @param m2 , the matrix that will be multiplied to this matrix
	 * @return the result of the mulitplication as a Matrix
	 */
	public Matrix IterativeMultiplyWithDelay(Matrix m2) {
		
		return IterativeVersion.mutiplyWithDelay(this, m2);
		
	}
	
	/**
	 * Mulitplies two matrices using the strassens method, in case the
	 * matrices' sizes are not a power of two, it appends zeros than multiplies
	 * @param m2 , the matrix to be multiplied to this matrix
	 * @param base , the base for strassens algorithm, if 1 is passed it 
	 * muitiples the two matrices using the classical method
	 * @return the result of the mulitiplication
	 */
	public Matrix StrassensMultiply(Matrix m2, int base) {
		
		
		return StrassensMethod.multiply(this, m2, base);
	}
	
	
	/**
	 *  takes a String and converts it to a 2D array, by splitting lines then spaces
	 * @param str , the string to be converted
	 * @return the converted 2D array
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
	 * Reads a file and convert its text to two matrices, divding the number
	 * of rows by 2 and asuming that the number of rows = 2 times the number of colums 
	 * @param str , the name of the file
	 * @return and array that contains the first matrix at index 0 and the 
	 * second matrix at index 1
	 */
	public static Double[][][] convert_to_2_matrices(String str){ 
		
		String [] rows = str.split("\n");		
		int rows_num = rows.length;
		int cols_num = rows[0].split(" ").length; // buggy?
		
		Double result1 [][] = new Double [rows_num/2][cols_num]; // 0 size ?
		Double result2 [][] = new Double [rows_num/2][cols_num]; // 0 size ?

		for (int i=0; i<rows_num/2 ; i++)
			{
				String [] cols = rows[i].split(" ");
				for(int j =0 ; j<cols_num; j++) 
					{
					result1[i][j] = Double.valueOf(cols[j]); // iterating over ever column to build the matrix
					}
			}
		
		for (int i=rows_num/2 , ii=0 ; i<rows_num ; i++,ii++)
		{
			String [] cols = rows[i].split(" ");
			for(int j =0,jj=0 ; j<cols_num; j++,jj++) 
				{
					result2[ii][jj] = Double.valueOf(cols[j]); // iterating over ever column to build the matrix
				}
		}
		
		Double bag [][][] = {result2,result1}; 
		return bag;
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
	
	
	/**
	 * Generates a new matrix with random entries the are integers
	 * @param x , the number of colums and the number of rows (sqaure matrix)
	 * @param min , the minmum value inside each entry 
	 * @param max , the maximum value inside each entry
	 * @return a randomizad matrix of size X rows and X columns
	 */
	public static Matrix generateMatrix(int x,int min, int max) 
	{
		String matrix_in_file = "";
		Double [][] newArr = new Double [x][x];
		
		for(int i=0; i<x ; i++) {
			for(int j=0 ; j<x ; j++) {
	
				double random_value = min + Math.random()* (max-min);
				
				random_value =((double)Math.round(random_value * 1000d) / 1000d);
				
				//matrix_in_file+= String.valueOf(random_value+"\n");
				newArr[i][j]=random_value;
			}
		}
		
		Matrix newMatrix = new Matrix(newArr);
		
		
		return newMatrix;
	}
	
	/**
	 * writes the matrix to a file
	 * @param file , the name of the file to be written to
	 */
	public void writeMatrix(String file) {
		
		Matrix m1=this;
		
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
				temp="";
				for (int j=0 ; j< size;j++)
					temp += matrix1[size-1][j] + " "; // to solve the additional \n efficinelty
					writer.write(temp.substring(0,temp.length()-1));
				
	   //writer.write(matrix.getMatrix());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * reads a file
	 * @param file_name , file to be read
	 * @return the file test as a string
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
	
	/** 
	 * returns the matrix element
	 * @param i , element row number 
	 * @param j , elemetn column number
	 * @return that specified element in Double type
	 */
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
