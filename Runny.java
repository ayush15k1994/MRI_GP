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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

import org.math.plot.*;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Runny {

	public int Niters = 10;
	public int imgeCounter = 0;
	public double TotaliterNeurons[][] = new double[300][Niters];// total
																	// numbers
																	// of fired
																	// neurons
																	// for
																	// trained
																	// images
	public int classifier[] = new int[300];// contains 1 for malignant , -1 for
											// benign

	// static final File dir = new File("G:\\FCI\\4rth year\\2nd term\\GP\\1");
	// static final File dir1 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\1_2");
	// static final File dir2 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\6_1");
	// static final File dir3 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\2_1");
	// static final File dir4 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\5_2");
	// static final File dir5 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\2_2");
	// static final File dir6 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\5_1");
	// static final File dir7 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\3_1");
	// static final File dir8 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\4_2");
	// static final File dir9 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\3_2");
	// static final File dir10 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\4_1");
	//
	// // tested
	// static final File dir11 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\1_1");
	// static final File dir12 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\6_2");
	// // tasted train
	// static final File dir13 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\t_m");
	// static final File dir14 = new
	// File("G:\\FCI\\4rth year\\2nd term\\GP\\train data\\t_b");

	double[] y = new double[Niters];

	/**
	 * array of supported extensions (use a List if you prefer)
	 */
	final String[] EXTENSIONS = new String[] { "gif", "png", "bmp" // and other
																	// formats
																	// you need
	};

	/**
	 * filter to identify images based on their extensions
	 */
	final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	/**
	 * Plot the graph of fired neurons
	 */
	public void Plot() {
		double[] x = new double[Niters];
		double[] y = { 23860.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1208.0,
				1601.0, 2727.0, 2828.0, 3173.0, 2613.0, 2630.0, 2305.0, 2216.0,
				1945.0, 1730.0, 1990.0, 1500.0, 1955.0, 1737.0, 2318.0, 1598.0,
				2495.0, 1865.0, 2372.0, 2174.0, 2568.0, 1933.0, 2416.0, 2160.0,
				2004.0, 2466.0, 1857.0, 1659.0, 2587.0, 1926.0, 1696.0, 2179.0,
				2312.0, 1886.0, 2467.0, 1567.0, 2194.0, 2436.0, 2056.0, 1786.0,
				2478.0, 2058.0, 2331.0, 1920.0, 2376.0, 1624.0, 2104.0, 2243.0,
				2751.0, 1920.0, 1769.0, 1622.0, 2832.0, 2002.0, 2118.0, 1707.0,
				2419.0, 2208.0, 2346.0, 1998.0, 1872.0, 1632.0, 2432.0, 2013.0,
				2724.0, 2150.0, 1936.0, 2015.0, 1714.0, 2129.0, 2413.0, 1944.0,
				2378.0, 2149.0, 2052.0, 1666.0, 2770.0, 2089.0, 1933.0, 1566.0,
				2173.0, 1904.0, 2527.0, 2677.0, 2221.0, 1766.0, 1798.0, 1758.0,
				2517.0, 1879.0, 2269.0 };
		for (int i = 1; i <= Niters; i++) {
			x[i - 1] = i;
		}
		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();

		// add a line plot to the PlotPanel
		plot.addLinePlot("my plot", x, y);

		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("a plot panel");
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

	/**
	 * test one image and apply PCNN on it
	 */
	public void testOne(File f, int classify) {
		// System.out.println(f.getName()+" ?");
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
			Rimage stim = new Rimage(img.getHeight(), img.getWidth());
			stim.Load(f.getAbsolutePath());
			Rimage croped = stim.subsampleAvg(2, 2);
			pcnn2d Net = new pcnn2d(croped.vert, croped.horz);
			Net.vf = 0;
			croped.divDouble(256);
			double avg = 0.0;
			int c = 1;
			for (int i = 0, j = 0; i < 50; i++) {
				double neurons = Net.Iterate(croped);
				if (i >= 20) {
					if (((i - 20) % 3) <= 2) {
						avg += neurons;
						if (c == 3) {
							avg /= 3.0;
							TotaliterNeurons[imgeCounter][j] = avg;
							j++;
							avg = 0;
							c = 0;
						}
						c++;
					}
				}
			}
			classifier[imgeCounter] = classify;
			imgeCounter++;

		} catch (final IOException e) {
			System.out.println("there 's no images");
		}
	}

	/**
	 * read total neurons
	 * 
	 * @throws IOException
	 *             , if the file dos'nt exist
	 */
	public void readOutput() throws IOException {
		Path filePath = Paths
				.get("C:\\Users\\DreamOnline\\Desktop\\MRI\\output.txt");
		Scanner scanner = new Scanner(filePath);
		imgeCounter = scanner.nextInt();
		Niters = scanner.nextInt();
		TotaliterNeurons = new double[imgeCounter][Niters];
		classifier = new int[imgeCounter];
		for (int i = 0; i < imgeCounter; i++) {
			for (int j = 0; j < Niters; j++) {
				TotaliterNeurons[i][j] = scanner.nextDouble();
			}
		}
		for (int i = 0; i < imgeCounter; i++) {
			classifier[i] = scanner.nextInt();
		}
		scanner.close();
	}

	/**
	 * calculate the fired neurons for each image on that folder
	 * 
	 * @param dir1
	 *            , directory that contain trained data
	 * @param classify
	 *            , the classification of the images on that folder
	 * @throw IOException when there 's no images found
	 */
	public void AvgDirNerons(File dir1, int classify) throws IOException {
		if (dir1.isDirectory()) { // make sure it's a directory
			for (final File f : dir1.listFiles(IMAGE_FILTER)) {
				testOne(f, classify);
			}
		}
	}

	/**
	 * write total neurons to the output file
	 * 
	 * @throws IOException
	 *             , if the file dos'nt exist
	 */
	public  void writeOutput() throws IOException {
		FileWriter f = new FileWriter(
				"C:\\Users\\DreamOnline\\Desktop\\MRI\\output.txt");
		BufferedWriter myFile = new BufferedWriter(f);
		myFile.write(imgeCounter + " " + Niters + " ");

		for (int i = 0; i < imgeCounter; i++) {
			for (int j = 0; j < Niters; j++) {
				myFile.write(TotaliterNeurons[i][j] + " ");
			}
			myFile.newLine();
		}
		for (int i = 0; i < imgeCounter; i++) {
			myFile.write(classifier[i] + " ");
		}
		myFile.close();
	}

	public int conctrol (String path) throws IOException{
		
		readOutput();
		// Plot();
		// writeOutput();

		RegularRBF RBF = new RegularRBF();
		double jamma = 0.0000001;
		int k = 14;

		RBF.initializeWithBiasedData(TotaliterNeurons, classifier);
		// RBF.learn(jamma, k);
		RBF.readWeight();
	//	String path = "G:\\FCI\\4rth year\\2nd term\\GP\\train data\\t_m\\5798_36.bmp";
		File f = new File(path);
		imgeCounter = 0;
		TotaliterNeurons = new double[1][Niters];
		testOne(f, 1);
		double t[] = new double[Niters];
		for (int i = 0; i < Niters; i++) {
			t[i] = TotaliterNeurons[0][i];
		}
		return (int) RBF.classify(t, jamma);
		
	}
}
