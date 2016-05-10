import static java.lang.Math.abs;

public class EdgeDetection{

    Image image;

    public EdgeDetection(String imgName){
	this.image = new Image(imgName);
    }

    public boolean[][] detectEdges(int edgeDetValue){
	int[][] data = this.getGrayScale();
	boolean[][] edge = new boolean[image.getWidth()][image.getHeight()];
	//start with one because we compare with previous
	    //this compares horizontally

	for (int x = 0; x < image.getWidth()-1; x++){
	    for (int y = 0; y < image.getHeight()-1; y++){
		if (abs(data[x][y] - data[x+1][y]) >= edgeDetValue ||
		    abs(data[x][y] - data[x][y+1]) >= edgeDetValue ||
		    abs(data[x][y] - data[x+1][y+1]) >= edgeDetValue){
	
		    edge[x][y] = true;
		}
	    }
	}

	return edge;

    }

    public int[][] getGrayScale(){
	int[][] value = new int[image.getWidth()][image.getHeight()];
	for (int row = 0; row < image.getWidth(); row++){
	    for (int col = 0; col < image.getHeight(); col++){
		//you can tweak this values for better results //maybe use doubles and exact multiplication. better result but slower.
		//value[row][col] = (image.getRed(row,col) >> 2) + (image.getGreen(row,col) >> 1) + (image.getBlue(row,col) >> 2);

		value[row][col] = (int) ( (image.getRed(row,col) * 0.3) + (image.getGreen(row,col) *0.5) + (image.getBlue(row,col) *0.11) /3);
	    }
	}
	return value;
    }

    public Image getImage(){
	return image;
    }


    public void saveImageWithEdge(String filename,String type,int edgeThresh){
	boolean edge[][] = this.detectEdges(edgeThresh);
	
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

    public static void main(String[] args){
	EdgeDetection img = new EdgeDetection("test.jpg");

	img.saveImageWithEdge("subEdge2","png",10);

	//boolean[][] edge = img.detectEdges(2);

	//for (int row = 0; row < img.getImage().getWidth(); row++){
	//  for (int col = 0; col < img.getImage().getHeight(); col++){
	//	System.out.println(edge[row][col]);
	//  }
	//}

    }


}
