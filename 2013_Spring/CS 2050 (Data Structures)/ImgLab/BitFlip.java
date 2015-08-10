/*
 * Alex Howard
 * CS 2050-003
 * 
 * This program uses a mask defined by a user to change the last few bits
 * of every point in a picture to downsample an image
 */

package filters;

import imagelab.*;
import javax.swing.JOptionPane;

public class BitFlip implements ImageFilter {
	ImgProvider filteredImage; // The new image

	public void filter(ImgProvider ip) {
		short[][] red = ip.getRed(); // Gets the red values from the old image
		short[][] green = ip.getGreen(); // Gets the green values from the old image
		short[][] blue = ip.getBlue(); // Gets the blue values from the old image
		int height = red.length; // The height of the image
		int width = red[0].length; // The width of the image
		int mask = getNumber(); // The mask the image is to be downsampled by

		// Traverses the image to mask each pixel by the specified amount
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				red[row][col] = (short) (red[row][col] & ~mask); // Masks red
				green[row][col] = (short) (green[row][col] & ~mask); // Masks green
				blue[row][col] = (short) (blue[row][col] & ~mask); // Mask blue
			} // Col
		} // Row

		filteredImage = new ImgProvider(); // Creates the new image
		filteredImage.setColors(red, green, blue, ip.getAlpha()); // Sets the colors for the new image
		filteredImage.showPix("Downsampled by " + mask + " bits"); // New window title

	} // filter

	public ImgProvider getImgProvider() {
		return filteredImage; // Returns the new image
	} // getImgProvider

	public String getMenuLabel() {
		return "Bit Flip"; // Returns the menu message
	} // getMenuLabel

	public int getNumber() // Gets a number from the user
	{
		String op = JOptionPane.showInputDialog(null,
				"Enter a downsample rate: ");
		int num = 0;
		try {
			num = Integer.parseInt(op); // Gets the number from the JOptionPane
		} catch (NumberFormatException ex) {
			num = 42;
		}
		return num;
	} // getNumber
} // BitFlip