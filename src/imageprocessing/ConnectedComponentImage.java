package imageprocessing;


import java.awt.Color;
import java.util.Random;
import edu.princeton.cs.introcs.Picture;


/*************************************************************************
 * Compilation: javac ConnectedComponentImage.java
 * 
 * The <tt>ConnectedComponentImage</tt> class
 * <p>
 * You do the rest....
 * 
 * @author 
 * @param <Picture>
 *************************************************************************/
public class ConnectedComponentImage<picture> {

	Picture picture;
	private int count;   // number of components
	private int[] id;
	private static final double Threshold = 0;
	private static final int width = 0;
	private static final int height = 0;


	/**
	 * Initialise fields
	 * 
	 * @param fileLocation
	 */

	public ConnectedComponentImage(String filelocation){
		picture = new Picture("C:/Users/Jamie/Pictures/Saved Pictures/bacteria.bmp");
		int N = width*height;
		id = new int [N];
		countComponents();
		weightedQuickUnionUF(N);
		identifyComponentImage();
		colourComponentImage();
		binaryComponentImage();
	}
	/**
	 * Returns the number of components identified in the image.
	 * 
	 * @return the number of components (between 1 and N)
	 */
	public int countComponents() {
		return 0;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void weightedQuickUnionUF(int N) {

		id = new int [N];
		for (int i = 0; i < N; i++)
			id[i] = i;
		count = N;
	}
	public int count() {
		return 0;
	}

	/**
	 * Returns the component identifier for the component containing site p.
	 */
	public int find(int p) {
		//validate(p); Original Quick Union
		//return id[p]; Original Quick Union
		if (p != id[p])
			id[p] = find (id[p]); //Compression attempt
		return id[p];
	}

	// validate that p is a valid index
	private void validate(int p) {
		int N = id.length;
		if (p < 0 || p >= N) {
			throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));
		}
	}

	/**
	 * Returns true if the the two sites are in the same component.
	 */
	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}

	/**
	 * Merges the component containing site p with the 
	 * the component containing site q.
	 */
	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; q < id.hashCode(); i++)
			if (id[i] == pid) id[i] = qid;
		count --;
	}

	/**
	 * Returns the original image with each object bounded by a red box.
	 * 
	 * @return a picture object with all components surrounded by a red box
	 */
	public Picture identifyComponentImage() {

		Picture pic = picture ("C:/Users/Jamie/Pictures/Saved Pictures/bacteria.bmp");

		int maxX = 0;
		int minX = pic.width();
		int maxY = 0;
		int minY = pic.height();

		for (int x = 0; x < pic.width(); x++) {
			for (int y = 0; y < pic.height(); y++) {
				if (!pic.get(x, y).equals(Color.WHITE) ) {

					if (x < minX)
						minX = x;
					if (x > maxX)
						maxX = x;
					if (y < minY)
						minY = y;
					if (y > maxY)
						maxY = y;

				}
			}

		}

		if (minX > maxX || minY > maxY) {
			System.out.println("Image contains all white pixels");
		} else {
			for (int x = minX; x <= maxX; x++) {
				pic.set(x, minY, Color.RED);
				pic.set(x, maxY, Color.RED);
			}

			for (int y = minY; y <= maxY; y++) {
				pic.set(minX, y, Color.RED);
				pic.set(maxX, y, Color.RED);
			}
		}

		pic.show();
		{
			return pic;
		}
	}

	private static Picture picture(String string) {
		return null;
	}

	/**
	 * Returns a picture with each object updated to a random colour.
	 * 
	 * @return a picture object with all components coloured.
	 */
	public Picture colourComponentImage() {

		Picture picture = new Picture("C:/Users/Jamie/Pictures/Saved Pictures/bacteria.bmp");
		int width = picture.width();
		int height = picture.height();

		// convert to black and white
		for (int x = 0; x < width; x++) 
		{
			for (int y = 0; y < height; y++) 
			{
				Color color = picture.get(x, y);
				if (Luminance.lum(color) < Threshold)
				{
					picture.set(x, y, Color.BLACK);
				}
				else
				{
					picture.set(x, y, Color.WHITE);
				}
			}
		}
		return colourComponentImage();
	}


	/**
	 * Returns a binarised version of the original image
	 * 
	 * @return a picture object with all components surrounded by a red box
	 */
	public Picture binaryComponentImage(){
		Picture pic = new Picture("C:/Users/Jamie/Pictures/Saved Pictures/bacteria.bmp");
		int width = picture.width();
		int height = picture.height();
		double Threshold = 128.0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < pic.height(); j++) {
				Color color = pic.get(i, j);
				if (Luminance.lum(color) <= Threshold){
					picture.set(i, j, Color.WHITE);	
				}					
				else{
					picture.set(i, j, Color.BLACK);				}
			}
		}
		return identifyComponentImage();
	}
}

