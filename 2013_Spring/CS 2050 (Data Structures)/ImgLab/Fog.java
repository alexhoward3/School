/*
 * Alex Howard
 * CS 2050-003
 * 
 * This program creates a monochrome image out of an original image
 * that has a slight grain amount so it looks like there is fog in the image.
 */
package filters;
import imagelab.*;

public class Fog implements ImageFilter {
	ImgProvider filteredImage;

	public void filter(ImgProvider ip) {
		
		short[][] im = ip.getBWImage(); //Gets a black and white image from the original
		int height = im.length; //Image height
		int width = im[0].length; //Image width

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				// Top left corner
				if (row == 0 && col == 0) {
					im[row][col] = (short) (((im[row][col]
							+ im[row + 1][col + 1] + im[row][col + 1] + im[row + 1][col]) + ((short) (Math
							.random() * 256))) / 5);
				}
				// Bottom left corner
				else if (row == height - 1 && col == 0) {
					im[row][col] = (short) (((im[row][col]
							+ im[row - 1][col] + im[row][col + 1] + im[row - 1][col + 1]) + ((short) (Math
							.random() * 256))) / 5);
				}
				// Bottom right corner
				else if (row == height - 1 && col == width - 1) {
					im[row][col] = (short) (((im[row][col]
							+ im[row - 1][col] + im[row][col - 1] + im[row - 1][col - 1]) + ((short) (Math
							.random() * 256))) / 5);
				}
				// Top right corner
				else if (row == 0 && col == width - 1) {
					im[row][col] = (short) (((im[row][col]
							+ im[row][col - 1] + im[row + 1][col] + im[row + 1][col - 1]) + ((short) (Math
							.random() * 256))) / 5);
				}
				// Top row
				else if (row == 0 && col != 0 && col != width - 1) {
					im[row][col] = (short) (((im[row][col]
							+ im[row][col - 1] + im[row + 1][col - 1]
							+ im[row + 1][col] + im[row + 1][col + 1] + im[row][col + 1]) + ((short) (Math
							.random() * 256))) / 7);
				}
				// Bottom row
				else if (row == height - 1 && col != 0 && col != width - 1) {
					im[row][col] = (short) (((im[row][col]
							+ im[row][col - 1] + im[row - 1][col - 1]
							+ im[row - 1][col] + im[row - 1][col + 1] + im[row][col + 1]) + ((short) (Math
							.random() * 256))) / 7);
				}
				// Leftmost column
				else if (col == 0 && row != 0 && row != height - 1) {
					im[row][col] = (short) (((im[row][col]
							+ im[row - 1][col] + im[row - 1][col + 1]
							+ im[row][col + 1] + im[row + 1][col + 1] + im[row + 1][col]) + ((short) (Math
							.random() * 256))) / 7);
				}
				// Rightmost column
				else if (col == width - 1 && row != 0 && row != height - 1) // column
				{
					im[row][col] = (short) (((im[row][col]
							+ im[row - 1][col] + im[row - 1][col - 1]
							+ im[row][col - 1] + im[row + 1][col - 1] + im[row - 1][col]) + ((short) (Math
							.random() * 256))) / 7);
				}
				// Middle
				else {
					im[row][col] = (short) (((im[row][col]
							+ im[row + 1][col + 1] + im[row + 1][col]
							+ im[row + 1][col - 1] + im[row][col + 1]
							+ im[row][col - 1] + im[row - 1][col - 1]
							+ im[row - 1][col] + im[row - 1][col + 1]) + ((short) (Math
							.random() * 256))) / 10);
				}
			}// Col
		}// Row

		filteredImage = new ImgProvider();
		filteredImage.setBWImage(im);
		filteredImage.showPix("Monochromed");
	}// filter

	public ImgProvider getImgProvider() {
		return filteredImage;
	}// getImgProvider

	public String getMenuLabel() {
		return "Monochrome";
	}// getMenuLabel

}// Monochrome