/*
 * Alex Howard
 * CS 2050
 * 
 * This program calculates a varying of density in pixels inside of an image
 */

package filters;
import imagelab.*;

public class DensityMap implements ImageFilter {
	ImgProvider filteredImage; // The new image to be made

	public void filter(ImgProvider ip) {
		short[][] red = ip.getRed(); // Gets the red map from the original image
		short[][] green = ip.getGreen(); // Gets the green map from the original image
		short[][] blue = ip.getBlue(); // Gets the blue map from the original image
		int height = red.length; // The height of the image
		int width = red[0].length; // The width of the image

		/* Looks to see if the pixel's color at the position of the row and col
		 * is greater than 127 or less than 63. If it is, the color value is
		 * multiplied by 2. This shows a varying amount of pixel density.
		 */
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (red[row][col] > 127 || red[row][col] < 63) // Above 127 or below 63
				{
					red[row][col] = (short) ((red[row][col] * 2)); // Changes the red values
				}
				if (green[row][col] > 127 || green[row][col] < 63) // Above 127 or below 63
				{
					green[row][col] = (short) ((green[row][col] * 2)); // Changes the green values
				}
				if (blue[row][col] > 127 || blue[row][col] < 63) // Above 127 or below 63
				{
					blue[row][col] = (short) ((blue[row][col] * 2)); // Changes the blue values
				}
			} // Col
		} // Row

		filteredImage = new ImgProvider();
		filteredImage.setColors(red, green, blue, ip.getAlpha());
		filteredImage.showPix("Density");
	} // filter

	public ImgProvider getImgProvider() {
		return filteredImage;
	} // getImgProvider

	public String getMenuLabel() {
		return "Density Map";
	} // getMenuLabel
}