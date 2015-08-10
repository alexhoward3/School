/*
 * Alex Howard
 * CS 2050-003
 * 
 * This program shifts the blue amount in the image to + 255 of its original value
 * divided by 2.
 */

package filters;
import imagelab.*;

public class Blueing implements ImageFilter {
	ImgProvider filteredImage; // The new image

	public void filter(ImgProvider ip) {
		short[][] blue = ip.getBlue(); // Gets the blue form the image
		int height = blue.length; // The height of the image
		int width = blue[0].length; // The width of the image

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				blue[row][col] = (short) ((blue[row][col] + 255) / 2); //Sets the new array
			} // Col
		} // Row

		filteredImage = new ImageProvider(); // Creates the new image
		// Sets the colors to their original values except for blue, which gets new array of values
		filteredImage.setColors(ip.getRed(), ip.getGreen(), blue, ip.getAlpha());
		filteredImage.showPix("Blued"); // New window title
	} // filter

	public ImgProvider getImgProvider() {
		return filteredImage; // Returns the image
	} // getImgProvider

	public String getMenuLabel() {
		return "Blueing"; // Menu message
	} // getMenuLabel
} // Blueing