import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Image{

    private BufferedImage image;

    public Image(String imgName){
	try{
	    this.image = ImageIO.read(new File(imgName));
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
    }

    public int[][] getRedArray(){
	int[][] values = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		values[row][col] = this.getRed(row,col);
	    }
	}
	return values;
    }
    
    public int[][] getGreenArray(){
	int[][] values = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		values[row][col] = this.getGreen(row,col);
	    }
	}
	return values;
    }
    
    public int[][] getBlueArray(){
	int[][] values = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		values[row][col] = this.getBlue(row,col);
	    }
	}
	return values;
    }
    
    public int getHeight(){
	return image.getHeight();
    }

    public int getWidth(){
	return image.getWidth();
    }

    public void closeImage(){ //Buffered image may leak
	image.flush();
    }

    public int getRed(int x, int y){
	return (image.getRGB(x,y) >> 16) & 0xff;
    }
    
    public int getGreen(int x, int y){
	return (image.getRGB(x,y) >> 8) & 0xff;
    }

    public int getBlue(int x, int y){
	return image.getRGB(x,y) & 0xff;
    }

    public int getAlpha(int x, int y){
	return (image.getRGB(x,y) >> 24) & 0xff;
    }

    public void setColor(int x, int y, int r, int g, int b, int a){ // converts r g b to rgb int
	int value = (( a << 24) & 0xFF000000) | 
	    ((r << 16) & 0x00FF0000) |
	    ((g << 8) & 0x0000FF00) | 
	    (b & 0x000000FF);
	image.setRGB(x,y,value);
    }

    public void setColor(int x, int y, int r, int g, int b){ // converts r g b to rgb int assumes 100% alpha
	int value = 0xFF000000 | 
	    ((r << 16) & 0x00FF0000) |
	    ((g << 8) & 0x0000FF00) | 
	    (b & 0x000000FF);
	image.setRGB(x,y,value);
    }

    public void saveImage(String name, String type){
	try{
	    ImageIO.write(image,type,new File(name +"." +type));
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
    }

    public Image getSubimage(int x, int y, int w, int h, String name,String type){
	try{
	    ImageIO.write(image.getSubimage(x,y,w,h),type,new File(name +"." +type));
	}catch(IOException ioe){
	    ioe.printStackTrace();
	}
	return new Image(name);
    }

    
    public BufferedImage getBufferedImage(){ // if you want to act upon the buffered image directly
	return image;
    }

    
    //testing
    public static void main(String[] args){

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
    }

}
