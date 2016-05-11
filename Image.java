/**
 * Image
 * This class acts as a wrapper for BufferedImage with built in methods for 
 * easier image manipulation 
 *
 * @author George Tsianakas
 * @versian 0.1
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Image{

    private BufferedImage image;

    /**
     * Loads an image for processing supports many types inluding jpg, png, pmg, gif
     * @param String imgName the name of the image file
     */

    public Image(String imgName){
	try{
	    this.image = ImageIO.read(new File(imgName));
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
    }

    /**
     * Returns a 2d integer array containing the red value for every pixel
     * @return int[][] red pixel values in x,y
     */

    public int[][] getRedArray(){
	int[][] values = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		values[row][col] = this.getRed(row,col);
	    }
	}
	return values;
    }

    /**
     * Returns a 2d integer array containing the Green value for every pixel
     * @return int[][] green pixel values in x,y
     */
    
    public int[][] getGreenArray(){
	int[][] values = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		values[row][col] = this.getGreen(row,col);
	    }
	}
	return values;
    }
    
    /**
     * Returns a 2d integer array containing the Blue value for every pixel
     * @return int[][] blue pixel values in x,y
     */
    
    public int[][] getBlueArray(){
	int[][] values = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		values[row][col] = this.getBlue(row,col);
	    }
	}
	return values;
    }
    
    /**
     * Returns the height of the image in pixels
     * @return int height
     */

    public int getHeight(){
	return image.getHeight();
    }

    /**
     * Returns the width of the image in pixels
     * @return int width
     */

    public int getWidth(){
	return image.getWidth();
    }
    
    /**
     * Wraps the BufferedImage flush()
     * can be used because BufferedImages may leak
     */

    public void closeImage(){
	image.flush();
    }

    /**
     * Returns the red value of the x,y pixel
     * @param int x x coordinate
     * @param int y y coordinate
     * @return int red pixel value
     */

    public int getRed(int x, int y){
	return (image.getRGB(x,y) >> 16) & 0xff;
    }

    /**
     * Returns the green value of the x,y pixel
     * @param int x x coordinate
     * @param int y y coordinate
     * @return int green pixel value
     */
    
    public int getGreen(int x, int y){
	return (image.getRGB(x,y) >> 8) & 0xff;
    }

    /**
     * Returns the blue value of the x,y pixel
     * @param int x x coordinate
     * @param int y y coordinate
     * @return int blue pixel value
     */

    public int getBlue(int x, int y){
	return image.getRGB(x,y) & 0xff;
    }

    /**
     * Returns the alpha value of the x,y pixel
     * @param int x x coordinate
     * @param int y y coordinate
     * @return int alpha pixel value
     */

    public int getAlpha(int x, int y){
	return (image.getRGB(x,y) >> 24) & 0xff;
    }
    
    /**
     * Sets the pixel at x,y to rgba value
     * @param int x x coordinate
     * @param int y y coordiate
     * @param int r red value 0-255 
     * @param int g green value 0-255
     * @param int b blue value 0-255
     * @param int a alpha value 0-255
     */

    public void setColor(int x, int y, int r, int g, int b, int a){ 
	int value = (( a << 24) & 0xFF000000) | 
	    ((r << 16) & 0x00FF0000) |
	    ((g << 8) & 0x0000FF00) | 
	    (b & 0x000000FF);
	image.setRGB(x,y,value);
    }

    /**
     * Sets the pixel at x,y to rgb value, Alpha value set to 255 (non-transparent)
     * @param int x x coordinate
     * @param int y y coordiate
     * @param int r red value 0-255 
     * @param int g green value 0-255
     * @param int b blue value 0-255
     */

    public void setColor(int x, int y, int r, int g, int b){
	int value = 0xFF000000 | 
	    ((r << 16) & 0x00FF0000) |
	    ((g << 8) & 0x0000FF00) | 
	    (b & 0x000000FF);
	image.setRGB(x,y,value);
    }

    /**
     * Saves new image with any changes that were made into a new file 
     * @param String name the name of the new file the .extension will be added automatically
     * @param String type the type of the image supports many types including jpg, png, pmg, gif
     */

    public void saveImage(String name, String type){
	try{
	    ImageIO.write(image,type,new File(name +"." +type));
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
    }

    /**
     * Returns a new subImage of the original image with any modification that were made in the original
     * @param int x the x coord beginning of the image
     * @param int y the y coord beginning of the image
     * @param int w the width of the new sub image
     * @param int h the height of the new sub image
     * @param String name the name of the new file
     * @param String type the type of the image supports many types including jpg, png, pmg, gif
     * @return Image the new subImage
     */

    public Image getSubimage(int x, int y, int w, int h, String name,String type){
	try{
	    ImageIO.write(image.getSubimage(x,y,w,h),type,new File(name +"." +type));
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
	return new Image(name);
    }
    
    /**
     * Returns the BufferedImage if you want to do something that might not be supported the Image class
     * @return BufferedImage the images BufferedImage
     */
    
    public BufferedImage getBufferedImage(){ 
	return image;
    }

    
    //testing
    public static void main(String[] args){
	/*
	Image img = new Image("test.jpg");
	
	//	img.setColor(10,10,0,255,255,255);
	for (int row = 0; row < img.getWidth(); row++){
	    for (int col = 0; col < img.getHeight(); col++){
		//	System.out.println(img.getRed(row,col)+", "+img.getBlue(row,col)+", "+img.getGreen(row,col));

		//	System.out.println(blue[row][col]);
	    }
	}

	img.getSubimage(10,10,100,100,"subimagetest","png");
	img.saveImage("save1","png");

	img.closeImage();
	*/
    }

}
