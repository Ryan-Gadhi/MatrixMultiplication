import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Random;

public class TestCases {
	Matrix[] matrix_arr1;
	Matrix[] matrix_arr2;
	boolean[] is_power_of2;
	int size_arr [];
	
	Matrix[] strassensResults;
	double [] strassensTimes;
	int [] bases_arr;
	int random_size=0;

	Matrix[] iterativeResults;
	double [] iterativeTimes;
	/* YOU CAN CHANGE THE FOLLOWING 3 OPTIONS*/
	int number_unique_of_tests=30; // unique = diffrent matrix sizes
	int same_size_tests=20; // same size but differnt base
	int max_size =128;	// maximum allowed size
	static String file_name = "tests.txt"; // the output file
	/* END OF OPTIONS*/
	
	
	/*tatal num of test case ill equal : number_of_tests * same_size_tests 
	*/
	int total_tests = number_unique_of_tests*same_size_tests;
	
	
	public static void main(String[] args) {
		TestCases t1 = new TestCases();
		t1.calculate();
		t1.writeStats(file_name);
		System.out.println("Writing Done");

		
	}
	
	public void calculate() {
	
	
	matrix_arr1 = new Matrix [number_unique_of_tests];
	matrix_arr2 = new Matrix [number_unique_of_tests];
	is_power_of2 = new boolean [number_unique_of_tests];
	size_arr = new int[number_unique_of_tests];
	
	


	for(int i=0; i<number_unique_of_tests ; i++)
	{
		int swicher = (int)(Math.random()+0.5);
		
		switch(swicher){
		case 0:
			int log_calc = (int)(Math.log10(max_size)/Math.log10(2));
			Random r = new Random();
			int power = r.nextInt(log_calc+1);
			random_size= (int) Math.pow(2,(int) (power)); 
			//System.out.println(random_base + " pow " + size_at_i );
			break;
		
		case 1: random_size = (int)(Math.random()*max_size);
				break;
	}
	
	//random_size = (int)(Math.random()*max_size);
	random_size++; // avoiding the zero
	matrix_arr1 [i] = Matrix.generateMatrix(random_size, -100, 100);
	matrix_arr2 [i] = Matrix.generateMatrix(random_size, -100, 100);	
	is_power_of2[i] = StrassensMethod.isPowerOf2(random_size);
	size_arr[i] = random_size;
	}
	
	System.out.println("Randomized Matrices Generated");
	
	strassensResults = new Matrix [total_tests];
	strassensTimes = new double [total_tests]; // this depends on the base
	bases_arr = new int[total_tests];
	
	long start =0, end=0;
	double time=0;
	
	int index =0;
//	for(int i=0; i<number_unique_of_tests ; i++)
//	{
//		int size_at_i = matrix_arr1[i].getCols_num();
//		
//		for(int j=0 ; j<same_size_tests;j++) 
//		{
//			int random_base = (int)(Math.random()*(size_at_i)); // this will give an int between 1->matrix_size
//			random_base+=1; // avoiding the zero
//			bases_arr[index] = random_base;
//			index++;
//		}
//	
//	}
//	Arrays.sort(bases_arr);
//	

	index=0;
	for(int i=0; i<number_unique_of_tests ; i++)
	{
		int size_at_i = matrix_arr1[i].getCols_num();
		
		for(int j=0 ; j<same_size_tests;j++) 
		{
			int random_base = 0;
			int swicher = (int)(Math.random()+0.5);
			
			switch(swicher){
				case 0:
					int log_calc = (int)(Math.log10(size_at_i)/Math.log10(2));
					Random r = new Random();
					int power = r.nextInt(log_calc+1);
					random_base= (int) Math.pow(2,(int) (power)); 
					//System.out.println(random_base + " pow " + size_at_i );
					break;
				
				case 1:
						random_base = (int)(Math.random()*(size_at_i)); // this will give an int between 1->matrix_size
						random_base+=1; // avoiding the zero
						//System.out.println(random_base + " --");

						break;
			}
			
			
			bases_arr[index] = random_base;

			
			start = System.currentTimeMillis();
			strassensResults[index] = matrix_arr1[i].StrassensMultiply(matrix_arr2[i], bases_arr[index]);
			end= System.currentTimeMillis();
			time = (end-start)/1000.0;
	
			strassensTimes[index] = time;
			
			index++;
		}
	}
	
	
	System.out.println("Strassens Multiplication Done");

	
	iterativeResults = new Matrix [number_unique_of_tests];
	iterativeTimes = new double [number_unique_of_tests];
	start =0; end=0;
	time=0;
	for(int i=0; i<number_unique_of_tests ; i++)
	{		
		start = System.currentTimeMillis();
		iterativeResults[i] = matrix_arr1[i].IterativeMultiply(matrix_arr2[i]);
		end= System.currentTimeMillis();
		time = (end-start)/1000.0;
		
		iterativeTimes[i] = time;
	}
	
	System.out.println("Itertive Multiplication Done");
	
	}
	
	public void writeStats(String name) {


		String outLine = "Size   base  Martix_power_of_2   Iterative_time  Strassens_time\n";
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(name), "utf-8"))) {

				writer.write(outLine+"\n"); // writes the outline
				int index=0;
				for(int i=0 ; i<number_unique_of_tests; i++) {

					for(int j =0; j<same_size_tests;j++) {
						
					writer.write(String.valueOf(size_arr[i]));
					writer.write("\t\t");
					
					writer.write(String.valueOf(bases_arr[index]));
					writer.write("\t\t");
					
					String temp = String.valueOf(is_power_of2[i]);
					if(is_power_of2[i])
						temp+=" ";
					writer.write(temp);
					writer.write("\t\t\t");

					
					
					double iterative_rounded_time = Math.round(1000*iterativeTimes[i])/1000.0;
					temp = String.valueOf(iterative_rounded_time) + "00000";
					writer.write(temp.substring(0,5));
					writer.write("\t\t");
					
					double strassens_rounded_time = Math.round(1000*strassensTimes[index])/1000.0;
					temp = String.valueOf(strassens_rounded_time)+"00000";
					writer.write(temp.substring(0,5));
					writer.write("\t\t\t");
					
			
					
					writer.write("\n");
					index++;
					}
				}

			

	   //writer.write(matrix.getMatrix());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Size   base  power_of_2  Strassens_time    Iterative_time

	}

