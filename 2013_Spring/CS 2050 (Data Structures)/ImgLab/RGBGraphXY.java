/*
 * Alex Howard
 * CS 2050-003
 */

package filters;
import imagelab.*;

public class RGBGraphXY implements ImageFilter
{
	ImgProvider filteredImage;

	public void filter(ImgProvider ip)
	{
		short[][] red = ip.getRed();
		short[][] green = ip.getGreen();
		short[][] blue = ip.getBlue();
		int height = red.length;
		int width = red[0].length;

		short[][] avgRedY = new short[256][width];
		short[][] avgGreenY = new short[256][width];
		short[][] avgBlueY = new short[256][width];

		short[][] avgRedX = new short[height][256];
		short[][] avgGreenX = new short[height][256];
		short[][] avgBlueX = new short[height][256];

		short[][] avgRed = new short[height][width];
		short[][] avgGreen = new short[height][width];
		short[][] avgBlue = new short[height][width];
		short[][] alpha = new short[height][width];

		//Populate Y values
		for(int col = 0; col < width; col++)
		{
			short avgr = 0;
			short avgg = 0;
			short avgb = 0;

			for(int row = 0; row < height; row++)
			{
				avgr += red[row][col];
				avgg += green[row][col];
				avgb += blue[row][col];
			}

			avgr = (short)(Math.abs(avgr/height));
			avgg = (short)(Math.abs(avgg/height));
			avgb = (short)(Math.abs(avgb/height));

			avgRedY[255-avgr][col] = 255;
			avgGreenY[255-avgg][col] = 255;
			avgBlueY[255-avgb][col] = 255;
		}

		//Populate X values
		for(int row = 0; row < height; row++)
		{
			short avgr = 0;
			short avgg = 0;
			short avgb = 0;

			for(int col = 0; col < width; col++)
			{
				avgr += red[row][col];
				avgg += green[row][col];
				avgb += blue[row][col];
			}

			avgr = (short)(Math.abs(avgr/width));
			avgg = (short)(Math.abs(avgg/width));
			avgb = (short)(Math.abs(avgb/width));

			avgRedX[row][255-avgr] = 255;
			avgGreenX[row][255-avgg] = 255;
			avgBlueX[row][255-avgb] = 255;
		}

		//Populate Avg Map X
		boolean exit;
		do
		{
			exit = true;
			try
			{
				for(int row = 0; row < height; row++)
				{
					for(int col = 0; col < width; col++)
					{
						avgRed[row][col] = avgRedX[row][col];
						avgGreen[row][col] = avgGreenX[row][col];
						avgBlue[row][col] = avgBlueX[row][col];

						avgRed[row][col] = avgRedY[row][col];
						avgGreen[row][col] = avgGreenY[row][col];
						avgBlue[row][col] = avgBlueY[row][col];
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				exit = false;
			}
		}while(!exit);

		//Popluate Alpha
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				alpha[row][col] = 255;
			}
		}

		filteredImage = new ImgProvider();
		filteredImage.setColors(avgRed,avgGreen,avgBlue,alpha);
		filteredImage.showPix("RGB Graph XY");
	}

	public ImgProvider getImgProvider()
	{
		return filteredImage;
	}

	public String getMenuLabel()
	{
		return "RGB Graph XY";
	}
}