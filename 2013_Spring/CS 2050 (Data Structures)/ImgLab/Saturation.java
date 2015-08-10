/*
 * Alex Howard
 * CS 2050
 * 
 * This program manipulates an image's saturation
 */

package filters;
import imagelab.*;
import javax.swing.JOptionPane;

public class Saturation implements ImageFilter {
	ImgProvider filteredImage; // The new image to be made

	public void filter(ImgProvider ip) {
		short[][] red = ip.getRed(); // Gets the red map from the original image
		short[][] green = ip.getGreen(); // Gets the green map from the original image
		short[][] blue = ip.getBlue(); // Gets the blue map from the original image
		int height = red.length; // The height of the image
		int width = red[0].length; // The width of the image
		int threshold = getNumber(); // The user created saturation "intensity" threshold (higher values = less intense)

		/*
		 * These loops traverse the image. If the pixel at the point of the row
		 * and column is higher than 127 + the threshold number, the saturation
		 * amount is 0. If the point is above the threshold number + 127, the
		 * saturation amount is 255. Otherwise the amount is set to the medium
		 * saturation.
		 */
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				// Red
				if (red[row][col] < (127 - threshold)) {
					red[row][col] = 0;
				} else if (red[row][col] > (127 + threshold)) {
					red[row][col] = 255;
				} else {
					red[row][col] = 127;
				}

				// Green
				if (green[row][col] < (127 - threshold)) {
					green[row][col] = 0;
				} else if (green[row][col] > (127 + threshold)) {
					green[row][col] = 255;
				} else {
					green[row][col] = 127;
				}

				// Blue
				if (blue[row][col] < (127 - threshold)) {
					blue[row][col] = 0;
				} else if (blue[row][col] > (127 + threshold)) {
					blue[row][col] = 255;
				} else {
					blue[row][col] = 127;
				}
			} // Col
		} // Row

		filteredImage = new ImgProvider(); //Creates the new image
		filteredImage.setColors(red, green, blue, ip.getAlpha()); //Sets the new image's colors
		filteredImage.showPix("Firey (" + threshold + ")"); //New window title
	} // filter

	public ImgProvider getImgProvider() {
		return filteredImage; // Returns the new image
	} // getImgProvider

	public String getMenuLabel() {
		return "Saturation"; // Returns the menu label
	} // getMenuLabel

	 // Gets a threshold amount from the user
	public int getNumber() {
		String op = JOptionPane.showInputDialog(null,
				"Enter threshold (0-63)\nHiger values = less saturation");
		int num = 0;
		try {
			num = Integer.parseInt(op); // Get the number from the JOptionPane
			if (num < 0 || num > 63)
				num = 31; // Too high or low defaults to 31
		} catch (NumberFormatException ex) {
			num = 31; // If the user inputs something other than a number it
						// defaults to 31
		}
		return num;
	}// getNumber
} // Saturation