import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Image {
	private int [][] image;
	private int height;
	private int width;
	private int endPoint;
	private int branchPoint;
	private int crossPoint;
	
	//constructor.	
	public Image(String imageName,int height,int width)
	{
		//initiate variables
		this.height=height;
		this.width=width;
		this.endPoint=0;
		this.branchPoint=0;
		this.crossPoint=0;
		//Set the size of the Image
		image=new int [this.height][this.width];
		
		try
		{
			loadImage(imageName);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error-Trouble opening Files.");
			System.out.println("Program will terminate.");
			System.exit(0);	
		}
		catch(IOException e)
		{
			System.out.println("Error-Trouble reading from the file.");
			System.out.println("Program will terminate.");
			System.exit(0);
		}
	}
	
	//Load image into matrix.
	private void loadImage(String imageName)throws IOException,FileNotFoundException
	{
		//Create scanner object to read data form file.
		Scanner inPutFile=new Scanner(System.in);
		//open file with iterator in Top.
		inPutFile=new Scanner(new FileInputStream(imageName));
		//Load image into matrix image starting from the bottom.
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				image[i][j]=inPutFile.nextInt();
			}
			inPutFile.nextLine();
		}
		inPutFile.close();
	}
	
	//applies the Zhang-Suen algorithm as many times as necessary.
	public void applyZSThinning()
	{
		//Keep thinning until only the skeleton is left.
		while(!this.ZSThinning())
		{	}
		
	}
	
	//Zhang-Suen thinning algorithm
	private boolean ZSThinning()
	{
		List<Integer> flaggedXCoordinate = new ArrayList<Integer>();
		List<Integer> flaggedYCoordinate = new ArrayList<Integer>();
		int [] Ps=new int[2];
		int [] neighbourValues=new int[8];
		//Step 1 in Zhang-Suen Thinning.
		//Select all flagged pixels if the conditions are satisfied.
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				//Calculate A(P) and B(P)for current pixel.
				Ps=this.transitionsZeroToOne(i, j,neighbourValues);
				
				//conditions(+only record those pixels that are equal to 1 or black pixels)
				if(this.image[i][j]==1 && Ps[1]>=2 && Ps[1]<=6 && Ps[0]==1 && ((neighbourValues[0]*neighbourValues[2]*neighbourValues[4])==0)&& ((neighbourValues[2]*neighbourValues[4]*neighbourValues[6])==0))
				{
					//record flagged pixels
					flaggedXCoordinate.add(i);
					flaggedYCoordinate.add(j);
				}
			}
		}
		//Delete all flagged pixels
		for(int i=0;i< flaggedXCoordinate.size();++i)
		{
			this.image[flaggedXCoordinate.get(i)][flaggedYCoordinate.get(i)]=0;
		}
		//Delete content on ArrayList holding the coordinates.
		flaggedXCoordinate.clear();
		flaggedYCoordinate.clear();
		
		//Step 2 in Zhang-Suen Thinning.
		//Select all flagged pixels if the conditions are satisfied.
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				//Calculate A(P) and B(P)for current pixel.
				Ps=this.transitionsZeroToOne(i, j,neighbourValues);
				
				//conditions(+only record those pixels that are equal to 1 or black pixels)
				if(this.image[i][j]==1&&Ps[1]>=2 && Ps[1]<=6 && Ps[0]==1 && ((neighbourValues[0]*neighbourValues[2]*neighbourValues[6])==0)&& ((neighbourValues[0]*neighbourValues[4]*neighbourValues[6])==0))
				{
					//record flagged pixels
					flaggedXCoordinate.add(i);
					flaggedYCoordinate.add(j);
				}
			}
		}
		
		//Delete all flagged pixels
		for(int i=0;i< flaggedXCoordinate.size();++i)
		{
			this.image[flaggedXCoordinate.get(i)][flaggedYCoordinate.get(i)]=0;
		}
		
		return flaggedXCoordinate.isEmpty();
		
		
	}
	
	
	private int [] transitionsZeroToOne(int x, int y,int [] auxArray)
	{
		int value=0;
		int [] transitions=new int[2];
		int P1=0;
		int P2=0;
		//check surrounding neighbours are out of bound and store the values of the neighbours in the new array.Also caculate P2(Number of black pixel neighbours)
		try{auxArray[0]=this.image[x-1][y];}catch(ArrayIndexOutOfBoundsException e){auxArray[0]=0;}if(auxArray[0]==1)++P2;
		try{auxArray[1]=this.image[x-1][y+1];}catch(ArrayIndexOutOfBoundsException e){auxArray[1]=0;}if(auxArray[1]==1)++P2;
		try{auxArray[2]=this.image[x][y+1];}catch(ArrayIndexOutOfBoundsException e){auxArray[2]=0;}if(auxArray[2]==1)++P2;
		try{auxArray[3]=this.image[x+1][y+1];}catch(ArrayIndexOutOfBoundsException e){auxArray[3]=0;}if(auxArray[3]==1)++P2;
		try{auxArray[4]=this.image[x+1][y];}catch(ArrayIndexOutOfBoundsException e){auxArray[4]=0;}if(auxArray[4]==1)++P2;
		try{auxArray[5]=this.image[x+1][y-1];}catch(ArrayIndexOutOfBoundsException e){auxArray[5]=0;}if(auxArray[5]==1)++P2;
		try{auxArray[6]=this.image[x][y-1];}catch(ArrayIndexOutOfBoundsException e){auxArray[6]=0;}if(auxArray[6]==1)++P2;
		try{auxArray[7]=this.image[x-1][y-1];}catch(ArrayIndexOutOfBoundsException e){auxArray[7]=0;}if(auxArray[7]==1)++P2;
		
		
		//check number of transitions from zero to one.in order(p2,p3,p4,p5,p6,p7,p8,p9,p2)
		int modValue;
		for(int i=0;i<auxArray.length;++i)
		{
			value=auxArray[i];
			modValue=(i+1)%8;//circular array to compare last value of auxArray with the first one.
			if(value==0 && auxArray[modValue]==1)
			{
					++P1;
			}	
		}
		//set values of P1 and P2 in the array to be returned
		transitions[0]=P1;
		transitions[1]=P2;
		return transitions;
	}
	//Extract end points,branch points and cross points
	public void topologicalFeatures()
	{
		int type=0;
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				//if pixel is one then it could be an end point,cross point or branch point.So check.
				if(this.image[i][j]==1)
				{
					type=endPointBranchCross(i,j);
					
					if(type==1)//pixel is an end point
					{
						System.out.println("Pixel at position: X="+i+" Y="+j+" Is an end point.");
						this.endPoint++;
					}else if(type==2)//pixel is an branch point
					{
						System.out.println("Pixel at position: X="+i+" Y="+j+" Is a branch point.");
						this.branchPoint++;
					}else if(type==3)//pixel is an cross point
					{
						System.out.println("Pixel at position: X="+i+" Y="+j+" Is a Cross point.");
						this.crossPoint++;
					}
				}
			
			}
		}
	}
	
	//checks if a pixel is an end point.Returns 0 if pixel is neither.Returns one(1) if pixel is end point,two(2) if it's branch point and three(3) if it's cross point.
	private int  endPointBranchCross(int x,int y)
	{
		int counter=0;
		int counter2=0;
		//start at left top pixel in the imaginary 3x3 neighbour matrix.
		int x2=x-1;
		int y2=y-1;
		for(int i=x2;i< x2+3;++i)
		{
			for(int j=y2;j<y2+3;++j)
			{
				//examine only the neighbours of the pixel being checked.
				if(i!=x || j!=y)
				{
					
					try
					{
						
						if(this.image[i][j]==1)	//if any of the neighbours is one.
							++counter;
							
					}catch(ArrayIndexOutOfBoundsException e){}
					
					//if checking the top,bottom,left or right neighbour.
					if(((x-1)==(i)&&(y)==(j)) || ((x)==(i)&&(y+1)==(j)) || ((x+1)==(i)&&(y)==(j)) || ((x)==(i)&&(y-1)==(j)))
					{
						try
						{
							
							if(this.image[i][j]==1)//if the top,bottom,left or right neighbour is one.
								++counter2;
						}catch(ArrayIndexOutOfBoundsException e){}
					}
				}
			
			}
		}

		//pixel is an end point
		if(counter==1)return 1;
		
		if(counter2==3)return 2;//pixel is a branch point
		else if(counter2==4) return 3;//pixel is a cross point
		else return 0;//pixel is neither of the three types;
	}
	
	//prints number of pixels in the image.
	public void pixelDensity()
	{
		int counter=0;
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				if(this.image[i][j]==1)
				{
					++counter;
				}
			}
		}
		System.out.println("Global Pixel Density:"+counter);
	}
	
	//Print image.
	public void printImage()
	{
		System.out.println("Printing image:");
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				System.out.print(this.image[i][j]+" ");
			}
			System.out.println();
		}
		
		if(this.endPoint>0)//only if the topological features have been extracted
		{
		System.out.println("End Points:"+this.endPoint);
		System.out.println("End Points:"+this.branchPoint);
		System.out.println("End Points:"+this.crossPoint);
		}
	}
	

}
