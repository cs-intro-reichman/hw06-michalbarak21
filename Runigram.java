// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;
import java.util.Arrays;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);
		System.out.println();

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		imageOut = flippedVertically(tinypic);
		System.out.println();
		///print(imageOut);
		///print(grayScaled(tinypic));
		print(scaled(tinypic, 3, 5));
		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt()); 
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		/// each cell of the 2d array represents a color
		/// so we will go over each of these cells, and send the color object in that cell to the print (color) function
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		/// to flip the picture horizontally we will create a new image
		/// therefore we will begin by creating a new 2d color array, named flipped
		Color[][] flipped = new Color[image.length][image[0].length];
		/// in the new image, we will put each color obejct from the original image in it's reversed location
		for (int i =0; i < flipped.length; i++) {
			for (int j = 0; j < flipped[0].length; j++) {
				flipped[i][j] = image[i][(flipped[0].length-1)-j];
			}
		}
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		/// to flip the picture horizontally we will create a new image
		/// therefore we will begin by creating a new 2d color array, named flipped
		Color[][] flipped = new Color[image.length][image[0].length];
		/// in the new image, we will put each color obejct from the original image in it's reversed row location
		for (int i =0; i < flipped.length; i++) {
			for (int j = 0; j < flipped[0].length; j++) {
				flipped[i][j] = image[(flipped.length-1)-i][j];
			}
		}
		return flipped;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		/// first we will calculate the luminance value of our desired color using the formula
		/// next we will create a new color in which the 3 rgb values are the luminance value we calculated before
		Integer gsInteger =  (int) ((0.299 * pixel.getRed()) + (0.587 * pixel.getGreen() ) + ( 0.114 * pixel.getBlue())) ; 
		Color gsColor = new Color(gsInteger ,gsInteger ,gsInteger );
		return gsColor;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		/// we will create a new image based on the original
		/// our new image will use luminance replace the cell color in the original image with it's gray-scaled match
		Color[][] graColors = new Color[image.length][image[0].length];
		for (int i =0; i < graColors.length; i++) {
			for (int j = 0; j < graColors[0].length; j++) {
				graColors[i][j] = luminance(image[i][j]);
			}
		} 
		return graColors;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		//// first we will calculate our scaling parameters
		Double numRows = (double) image.length;
		Double numCols = (double) image[0].length;  
 
		Double hScale = numCols / height; 
		Double wScale = numRows / width;

		//// next we will create a new image with our new given dimensions
		Color[][] scaleImage = new Color[height][width];
		System.out.println();
		for (int i =0; i < scaleImage.length; i++) {
			for (int j = 0; j < scaleImage[0].length; j++) {
				Integer newHeight = (int)(i*hScale); 
				Integer newWidth = (int)(j*wScale);
				scaleImage[i][j] = image[newHeight][newWidth];
			}
		} 
		return scaleImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		//// we will need to create and return a new color, therefore we want to calculate it's rgb values
		//// we will do that for each color separately, and then create a new color with our calculated values
		int red = (int) (c1.getRed()  * alpha + (c2.getRed() * (1-alpha)));
		int green = (int) (c1.getRed()  * alpha + (c2.getRed() * (1-alpha)));
		int blue = (int) (c1.getRed()  * alpha + (c2.getRed() * (1-alpha)));
		Color blended = new Color(red, green, blue); 
		//// Replace the following statement with your code
		return blended;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		//// we will need to return a new image that is a blend of both images. Therefore we will create a new Color[][] object of the same dimensions
		Color[][] blended = new Color[image1.length][image1[0].length];
		//// now we will populate each cell of the new image with the blended cell of the 2 images
		for (int i =0; i < blended.length; i++) {
			for (int j = 0; j < blended[0].length; j++) {
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		//// Replace the following statement with your code
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		//// Replace this comment with your code
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

