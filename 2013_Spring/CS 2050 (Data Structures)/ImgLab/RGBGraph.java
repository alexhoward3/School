/*
 * Alex Howard
 * CS 2050-003
 * 
 * This filter takes all the pixel information for a specific column for an
 * image and graphs that information on a new image with a height of 256 and
 * a width the size of the original image.
 */

package filters;
import imagelab.*;

public class RGBGraph implements ImageFilter {
	
	ImgProvider filteredImage; // The new image

	public void filter(ImgProvider ip) {
		
		short[][] red = ip.getRed(); // 2D array that holds the red values
		short[][] green = ip.getGreen(); // 2D array that holds the green values
		short[][] blue = ip.getBlue(); // 2D array that holds the blue values
		int height = red.length; // The height of the image
		int width = red[0].length; // The width of the image

		short[][] avgRed = new short[256][width]; // Holds the average values of the red pixels
		short[][] avgGreen = new short[256][width]; // Holds the average values of the green pixels
		short[][] avgBlue = new short[256][width]; // Holds the average values of the blue pixels
		short[][] alpha = new short[256][width]; // Holds a "dummy" alpha so the image appears

		for (int col = 0; col < width; col++) {
			short avgr = 0; // Variable for the average red in a column
			short avgg = 0; // Variable for the average green in a column
			short avgb = 0; // Variable for the average blue in a column

			for (int row = 0; row < height; row++) {
				avgr += red[row][col]; // Adds all the red values in the column
				avgg += green[row][col]; // Adds all the green values in the column
				avgb += blue[row][col]; // Adds all the blue values in the column
			} // Row

			avgr = (short) (Math.abs(avgr / height)); // Computes the average red in the column
			avgg = (short) (Math.abs(avgg / height)); // Computes the average green in the column
			avgb = (short) (Math.abs(avgb / height)); // Computes the average blue in the column

			avgRed[255 - avgr][col] = 255; // Sets a red pixel at the average point in the graph
			avgGreen[255 - avgg][col] = 255; // Sets a green pixel at the average point in the graph
			avgBlue[255 - avgb][col] = 255; // Sets a blue pixel at the average point in the graph
		} // Col

		for (int row = 0; row < 256; row++) {
			for (int col = 0; col < width; col++) {
				alpha[row][col] = 255; // Populates a non-transparent alpha map
			} // Col
		} // Row

		filteredImage = new ImgProvider(); // Creates the image object
		filteredImage.setColors(avgRed, avgGreen, avgBlue, alpha); // Sets the colors in the image
		filteredImage.showPix("RGB Graph"); // Window title for the new image
	} // filter

	public ImgProvider getImgProvider() {
		return filteredImage; // Returns the image
	} // getImgProvider

	public String getMenuLabel() {
		return "RGB Graph"; // Sets the label name for the GUI's menu
	} // getMenuLabel
} // RGBGraph