import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Runny {

	public final static  int Niters = 50;
	public final static int dim = 128;
	public static int imgeCounter = 0;
	public final static Vector<Double> averDirCancer = new Vector<Double>();
	public final static Vector<Double> averDirNot = new Vector<Double>();
	public static Vector<Double> test = new Vector<Double>();
	public static double TotaliterNeurons[][] = new double[300][Niters];//3dd 2l swar * interations
	public static int classifier[] = new int [300];//3dd 2l swar contains -1 or 1
	static final File dir1 = new File("C:\\Users\\admin\\Desktop\\gp\\1");										
	static final File dir2 = new File("C:\\Users\\admin\\Desktop\\gp\\2");
	static final File dir3 = new File("C:\\Users\\admin\\Desktop\\gp\\3");
	static final File dir4 = new File("C:\\Users\\admin\\Desktop\\gp\\4");
	static final File dir5 = new File("C:\\Users\\admin\\Desktop\\gp\\5");
	static final File dir6 = new File("C:\\Users\\admin\\Desktop\\gp\\6");

	// array of supported extensions (us\e a List if you prefer)
	static final String[] EXTENSIONS = new String[] { "gif", "png", "bmp" // and
																			// other
																			// formats
																			// you
																			// need
	};
	// filter to identify images based on their extensions
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	public static void AvgDirNerons(File dir1, int classify) {
		if (dir1.isDirectory()) { // make sure it's a directory
			for (final File f : dir1.listFiles(IMAGE_FILTER)) {
				BufferedImage img = null;
				try {
					img = ImageIO.read(f);
					Rimage stim = new Rimage(img.getHeight(), img.getWidth());
					pcnn2d Net = new pcnn2d(img.getHeight(), img.getWidth());
					Net.vf = 0 ;
					stim.Load(f.getAbsolutePath());
				//	double sum = 0.0;int c = 1;

					for (int i = 0 , j = 0 ; i < Niters ; i++) {
						double neurons = Net.Iterate(stim, i);
						System.out.println(neurons);
						TotaliterNeurons[imgeCounter][i] = neurons;
						/*if(i >= 20){
							if(((i-20) % 3) <= 2) {
				                sum += neurons;
				                if(c == 3){
									sum /=3.0;
									TotaliterNeurons[imgeCounter][j] = sum;
									j++;
									sum = 0 ; c = 0 ;
								}
								c++;
							}
						}*/
					}					
					imgeCounter ++;
					classifier[imgeCounter]=classify;

				} catch (final IOException e) {}
				break;
			}
		}
	}

	public static void MseCancerDetector() throws IOException {

		double avg1 = 0, avg2 = 0;
		for (int i = 0; i < Niters; i++) {
			avg1 += Math.pow(test.get(i) - averDirCancer.get(i), 2);
			avg2 += Math.pow(test.get(i) - averDirNot.get(i), 2);
		}
		avg1 /= Niters;
		avg2 /= Niters;
		System.out.println("malignant = " + avg1 + " benign = " + avg2);
		System.out.println(Math.abs(avg1-avg2));
	}


    public static void CrossValidation() throws IOException {

		for (int i = 0; i < 4; i++) {
			int[] t = new int[] {0,1,2,3};

			t[i] = -1 ;

			t[i] = i;
		}

	}


	/*public static void nearstNeighbour() {
		Vector<Double> result = new Vector<Double>();
		double avg = 0.0, mini = Double.MAX_VALUE;
		int indx = -1;
		for (int it = 0; it < 6; it++) {
			result = patients.get(it);

			System.out.println("itt " + it);
			for (int j = 0; j < 6; j++) {
				if (j != it) {
					mini = 999999999;
					avg=0.0;
					for (int i = 0; i < Niters; i++) {
						avg += Math.pow(patients.get(j).get(i) - result.get(i), 2);
					}
					avg /= Niters;
					System.out.println("j =" + j + " avg = " + avg + " mini = " + mini);
					if (mini >= avg) {
						mini = avg;
						indx = j + 1;
					}
				}
			}

			System.out.println("nearst to " + indx);
		}
	}
    */

	public static void readOutput() throws IOException {
		FileReader f = new FileReader("C:\\Users\\bata\\workspace\\MRI\\averDirCancer.txt");
		FileReader f2 = new FileReader("C:\\Users\\bata\\workspace\\MRI\\averDirNot.txt");
		BufferedReader myFile = new BufferedReader(f);
		String s;
		while((s = myFile.readLine())!=null){
			averDirCancer.add(Double.parseDouble(s));
		}
		BufferedReader myFile2 = new BufferedReader(f2);
		while((s = myFile2.readLine())!=null){
			averDirNot.add(Double.parseDouble(s));
		}
		myFile.close(); myFile2.close();

	}

	public static void writeToFile(Vector<Double> aver, String output){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(output+".txt", true)))){
			for(int i = 0; i < aver.size(); i ++){
				out.println(aver.get(i).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		AvgDirNerons(dir1, 1);
		System.out.println("done 1 ");
	/*	AvgDirNerons(dir2, 1);
		System.out.println("done 2 ");
		AvgDirNerons(dir3, -1);
		System.out.println("done 3 ");
		AvgDirNerons(dir4, -1);
		System.out.println("done 4 ");
		AvgDirNerons(dir5, -1);
		System.out.println("done 5 ");*/
	
		RegularRBF RBF = new RegularRBF();
        double jamma = 0.1;
		int k = imgeCounter/10 ;


		 RBF.initialize(TotaliterNeurons, classifier);
		 RBF.learn(jamma, k);
		 TotaliterNeurons = new double[300][Niters];
		 classifier = new int [300];
		 AvgDirNerons(dir6, -1);
		 System.out.println("done 6 ");
		 imgeCounter = 0;
		 double res = RBF.getErrorOut(TotaliterNeurons, classifier, jamma);
		 System.out.println("error result " + res);
		 System.out.println("K " + k);

	}

}
