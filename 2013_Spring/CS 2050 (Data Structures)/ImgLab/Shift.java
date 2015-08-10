/*
 * Alex Howard
 * CS 2050-003
 * 
 * This program shifts an image to the right and wraps the pixels that fall off
 * back around to the left side.
 */
package filters;
import imagelab.*;
import javax.swing.JOptionPane;

public class Shift implements ImageFilter {
	
	ImgProvider filteredImage; //The new image

	public void filter(ImgProvider ip) {
		
		short[][] red = ip.getRed(); //Gets the red from the original image
		short[][] green = ip.getGreen(); //Gets the green from the original image
		short[][] blue = ip.getBlue(); //Gets the blue from the original image
		short[][] alpha = ip.getAlpha(); //Gets the alpha from the original image
		int height = red.length; //The height of the image
		int width = red[0].length; //The width of the image
		int shift = getShiftAmount(width); //The shift amount defined by the user

		short[][] newRed = new short[height][width]; //Creates a new array for the red
		short[][] newGreen = new short[height][width]; //Creates a new array for the green
		short[][] newBlue = new short[height][width]; //Creates a new array for the blue
		short[][] newAlpha = new short[height][width]; //Creates a new array for the alpha
		
		//Traverses the image and shifts the values on each side of the shift amount
		for (int row = 0; row < height; row++) {
			for (int col = width - shift, x = 0; col < width; col++, x++) {
				newRed[row][x] = red[row][col]; //Value swap red
				newGreen[row][x] = green[row][col]; //Value swap green
				newBlue[row][x] = blue[row][col]; //Value swap blue
				newAlpha[row][x] = alpha[row][col]; //Value swap alpha
			} //Col

			for (int col = 0, x = shift; col < width - shift; col++, x++) {
				newRed[row][x] = red[row][col]; //Value swap red
				newGreen[row][x] = green[row][col]; //Value swap green
				newBlue[row][x] = blue[row][col]; //Value swap blue
				newAlpha[row][x] = alpha[row][col]; //Value swap alpha
			} //Col
		} //Row

		filteredImage = new ImgProvider(); //Creates the new image
		filteredImage.setColors(newRed, newGreen, newBlue, newAlpha); //Sets the new image's color
		filteredImage.showPix("Shifted " + shift + " spaces"); //New window title
	} //filter

	public ImgProvider getImgProvider() {
		return filteredImage; //Returns the new image
	} //getImgProvider

	public String getMenuLabel() {
		return "Shift"; //Menu message
	} //getMenuLabel

	public int getShiftAmount(int width) {
		String op = JOptionPane.showInputDialog(null,"Enter a starting point (10-100): ");
		int num = 0;
		try {
			num = Integer.parseInt(op); //Gets number from JOptionPane
			//If num is too big or small it gets set to the width divided by 2
			if (num <= 0 || num > width) num = (width / 2);
		} catch (NumberFormatException ex) {
			num = (width / 2);
		}
		return num;
	} //getNumber
} //Shift