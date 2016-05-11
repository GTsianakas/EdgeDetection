/**
 * EdgeDetection
 * This class is used for simple edge detection in coloured images
 * you can tweak the final output in multiple ways and 
 * add and improve functionality easily
 *
 * @author George Tsianakas
 * @versian 0.1
 */

import static java.lang.Math.abs;

public class EdgeDetection{

    private Image image;
    
    /**
     * Returns an Image object of the imgName image file
     */

    public EdgeDetection(String imgName){
	this.image = new Image(imgName);
    }

    /**
     * Returns a boolean 2d array representing the edges that were detected 
     * @param int edgeDetValue the threshold (pixel value diference) between an edge
     * @return boolean[][] edge array were true indicates edge
     */

    public boolean[][] detectEdges(int edgeDetValue){
	int[][] data = this.smoothFilter();
	boolean[][] edge = new boolean[image.getWidth()][image.getHeight()];

	//detect edges based on edgeDetValue
	for (int x = 0; x < image.getWidth()-1; x++){
	    for (int y = 0; y < image.getHeight()-1; y++){
		if (abs(data[x][y] - data[x+1][y]) >= edgeDetValue ||
		    abs(data[x][y] - data[x][y+1]) >= edgeDetValue ||
		    abs(data[x][y] - data[x+1][y+1]) >= edgeDetValue ||
		    abs(data[x][y] - data[x+1][y+1]) >= edgeDetValue){
		    		    		    
		    edge[x][y] = true;
		}
	    }
	}

	//if its sourounded by edges its not an edge attempt to reduce noise
	for (int x = 1; x < image.getWidth()-1; x++){
	    for (int y = 1; y < image.getHeight()-1; y++){
		if (edge[x][y]){
		    if (edge[x-1][y-1] &&
			edge[x-1][y] &&
			edge[x][y-1] &&
			edge[x+1][y+1] &&
			edge[x+1][y] &&
			edge[x][y+1] ){
			
			edge[x][y] = false;
		    }
		}
	    }
	}
	
	return edge;
    }
    
    /**
     * returns a 2d int array representing the image in grayscale(tweak for better results as necessary
     * @return int[][] the grayscale values x,y of the image 0-255
     */

    public int[][] getGrayScale(){
	int[][] value = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		//you can tweak this values for better results 
		//maybe use doubles and exact multiplication. better result but slower.
		//value[row][col] = (image.getRed(row,col) >> 2) + (image.getGreen(row,col) >> 1) + (image.getBlue(row,col) >> 2);
		value[row][col] = (int) ( ((image.getRed(row,col) * 0.3) + (image.getGreen(row,col) *0.5) + (image.getBlue(row,col) *0.11)) /3);
	    }
	}
	return value;
    }

    /**
     * Returns the Image object of the image if you want more control over it
     * @return Image image
     */

    public Image getImage(){
	return image;
    }

    /**
     * Returns the 2d image grayScale values representing x,y coords with a simple smoothing algorithm that helps
     * with images with smooth transitions and removes a some noice.
     * @return int[][] grayscale values of image with a minor smoothing filter
     */

    public int[][] smoothFilter(){
	
	int[][] smooth = this.getGrayScale();
	
	//averages a pixel with the average of its neighbours
	for (int x = 1; x < image.getWidth()-1; x++){
	    for (int y = 1; y < image.getHeight()-1; y++){
		int A = (int) (smooth[x-1][y-1]+smooth[x-1][y]+smooth[x][y-1]+smooth[x+1][y+1]+
			       smooth[x+1][y]+smooth[x][y-1]+smooth[x-1][y+1]+smooth[x+1][y-1]) / 8;
		smooth[x][y] = (int) (A + smooth[x][y])/2;
	    }
	}
	
	return smooth;
    }
    
    /**
     * Creates the new image file containing the edge detection result.
     * @param String filename the new image files name extensio added automatically
     * @param String type the type of the image includes jpg,png,pmg,jpg
     * @param edgeThresh the value diference to identify edges
     */

    public void saveImageWithEdge(String filename,String type,int edgeThresh){
	boolean edge[][] = this.detectEdges(edgeThresh);
	
	//creates the edge image using the boolean values of edge
	for (int x = 0; x < image.getWidth(); x++){
	    for (int y = 0; y < image.getHeight(); y++){
		if (edge[x][y]){
		    image.setColor(x,y,255,255,255);
		} else{
		    image.setColor(x,y,0,0,0);
		}
	    }
	}
	image.saveImage(filename,type);
    }

    /**
     * Prints the Histogram of the image on console can be used to determine if the grayscale is balanced
     */

    public void printHistogram(){
	
	//Image img = image.createImage(256,2000,fileName,type);
	int counter[] = new int[256];
	int[][] gray = this.getGrayScale();

	//get frequency data
	for (int x = 0; x < image.getWidth(); x++){
	    for (int y = 0; y < image.getHeight(); y++){
		counter[gray[x][y]]++;
	    }
	}
	
	//display
	for (int i = 0; i < counter.length; i++){
	    System.out.println(i +": " + counter[i]);
	}

    }

    //testing
    public static void main(String[] args){
	/*
	EdgeDetection img = new EdgeDetection("test.jpg");
	img.printHistogram();
	img.saveImageWithEdge("testsmooth10B","png",10);
	*/
    }


}
