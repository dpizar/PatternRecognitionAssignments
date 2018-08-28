import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Image1 {
	private int [][] image;
	private int height;
	private int width;
	private double [] centroid;
	private char [][] mask;
	

	//constructor.
	public Image1(String imageName,int height,int width)
	{
		//initiate variables
		this.height=height;
		this.width=width;
		//Set the size of the Image
		image=new int [this.height][this.width];
		this.centroid=new double[2];
		this.mask=new char[3][3];
		//initiate mask to type1
		for(int i=0;i<3;++i)
		{
			for(int j=0;j<3;++j)
			{
				if(i==0)
					this.mask[i][j]='e';
				if(i==1 && j!=1)
					this.mask[i][j]='e';
				if(i==2)
					this.mask[i][j]='x';

			}
		}

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
	//getter
	public int getHeight()
	{
		return this.height;
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

	//Slant Correction
	public void slantCorrection()
	{
		double theta;
		//Calculate the Angle Theta
		theta=getTheta();
		//CorrectImageSlant
		getSlantCorrectedImage(theta);
	}



	private void getSlantCorrectedImage(double theta)
	{
		double Newy;

		int [][] Newimage=new int [this.height][this.width];

		//Correct Slant.
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				if(this.image[i][j]==1)
				{
					//calculate new y
					//Newx=  i-((j-this.centroid[1])*Math.tan(theta));
					Newy=j-((i-this.centroid[0])*Math.tan(theta));
					Newy=Math.round(Newy);
					//System.out.println("X coordenate:"+i+" Y coordanate:"+Newy);

					//set new location for black pixel
					Newimage[i][(int) Newy]=1;




				}
			}

		}
		this.image=Newimage;
		Newimage=null;

	}

	//Betas and returns a matrix with the four x coordinates of the Betas.
	private  double  getTheta()
	{
		int area=0;
		int xCoordinatesSum=0;
		int yCoodinatesSum=0;
		//loop through image.
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				//calculate area(Zeroth Moment),the x coordinates summation and the y coordinate summation.
				if(this.image[i][j]==1)
				{
					area=area+1;
					xCoordinatesSum=xCoordinatesSum+i;
					yCoodinatesSum=yCoodinatesSum+j;
				}
			}

		}

		//calculate Centroid center.
		this.centroid[0]=(xCoordinatesSum/area);
		this.centroid[1]=(yCoodinatesSum/area);

		System.out.println("X Centroid:"+this.centroid[0]);
		System.out.println("Y Centroid:"+this.centroid[1]);

		double Cmoment11=0;
		double Cmoment02=0;
		double Cmoment20=0;
		//calculate central moments 11 and 02.
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{

				if(this.image[i][j]==1)
				{
					//central moment 11
					Cmoment11=Cmoment11+((i-this.centroid[0])*(j-this.centroid[1]));
					//central moment 02
					Cmoment02=Cmoment02+Math.pow((j-this.centroid[1]), 2);
					//central moment 02
					Cmoment20=Cmoment20+Math.pow((i-this.centroid[0]), 2);
				}
			}

		}

		System.out.println("Moment 11:"+Cmoment11);
		System.out.println("Moment 02:"+Cmoment02);
		System.out.println("Moment 20:"+Cmoment20);
		//calculate Theta.
		double theta=(Math.atan((2*Cmoment11)/(Cmoment20-Cmoment02)))*0.5;


		System.out.println("Theta:"+theta);
		return theta;
	}

	//Print image.
	public void printImage()
	{
		System.out.println("Printing image:");
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				System.out.print(this.image[i][j]);
			}
			System.out.println();
		}
	}

	//apply each kind of mask(a,b,c,d) once to the image.
	public void applyMasks()
	{
		//Smoothing and Removing noise 1(mask type(a).
		smoothNoise();
		//Rotate mask 90 degrees clockwise
		rotateMask();
		//Smoothing and Removing noise 2(mask type(b)).
		smoothNoise();
		//Rotate mask 90 degrees clockwise
		rotateMask();
		//Smoothing and Removing noise 3(mask type(c)).
		smoothNoise();
		//Rotate mask 90 degrees clockwise
		rotateMask();
		//Smoothing and Removing noise 4(mask type(d)).
		smoothNoise();
		//Set the mask to type(a)
		rotateMask();
	}

	//Remove noise and smoothes image by using one of three masks.
	private void smoothNoise()
	{
		int value;
		//Start Smoothing the image and removing noise from the lower right corner, row by row.
		for(int i=this.height-2;i>0;--i)
		{
			for(int j=this.width-2;j>0;--j)
			{
				//using mask (a) or (b)
				if(mask[0][0]=='e')
				{
					//using mask (a)
					if((mask[0][2])=='e')
					{
						value=this.image[i-1][j-1];

						if(image[i-1][j]==value && image[i-1][j+1]==value && image[i][j+1]==value && image[i][j-1]==value)
							this.image[i][j]=value;

					}
					else//using mask (d)
					{
						value=this.image[i-1][j-1];

						if(image[i-1][j]==value && image[i][j-1]==value && image[i+1][j-1]==value && image[i+1][j]==value)
							this.image[i][j]=value;
					}
				}else //either mask (b) or (c)
				{
					if(mask[0][2]=='e')//mask type(b)
					{
						value=this.image[i-1][j];

						if(image[i-1][j+1]==value && image[i][j+1]==value && image[i+1][j+1]==value && image[i+1][j]==value)
							this.image[i][j]=value;
					}
					else//mask type(c)
					{
						value=this.image[i][j+1];

						if(image[i+1][j+1]==value && image[i+1][j]==value && image[i+1][j-1]==value && image[i][j-1]==value)
							this.image[i][j]=value;
					}
				}

			}
		}


	}

	//rotates square matrix clockWise(In this case rotates the mask 90 degrees)
	private void rotateMask()
	{
		// first mirror the matrix along the diagonal line.
		for (int i = 0; i < mask.length; ++i)
		{
			for (int j = i + 1; j < mask[0].length; ++j)
			{
					char tmp = this.mask[i][j];
					mask[i][j] = mask[j][i];
					mask[j][i] = tmp;
			}
		}

		// mirror the matrix horizontally. for Left Rotation
		// and mirror the matrix vertically for right rotation

		for (int i = 0; i < (mask.length) / 2; i++)
		{
			for (int j = 0; j <(mask.length); j++)
			{
				char tmp = mask[j][i];
				mask[j][i] = mask[j][(mask.length) - i - 1];
				mask[j][(mask.length) - i - 1] = tmp;
			}
		}

	}
	//removes contour
	public void contour()
	{
		List<Integer> rowCoordBlackPix = new ArrayList<Integer>();
		List<Integer> columnCoordBlackPix = new ArrayList<Integer>();
		int [] currentPosition=new int[2];
		char direction=' ';
		boolean a=false;
		//scan from bottom to top left to right and record the first black pixel.
		for(int j=0;j<this.width;++j)
		{
			if(a==true)
				break;
			for(int i=this.height-1;i>=0;--i)
			{
				if(this.image[i][j]==1)
				{
					rowCoordBlackPix.add(i);
					columnCoordBlackPix.add(j);
					//Set the current position to this pixel.
					currentPosition[0]=i;
					currentPosition[1]=j;
					//set new direction
					direction='N';
					a=true;
					break;
				}
			}

		}
		//Get new direction and new current position
		direction=turning(direction,'L',currentPosition);
		//An error has happened
		if(direction=='X')
		{
			System.exit(0);
		}
		//Keep going until the first black pixel found is reached again.(Completing a cycle and removing the contour)
		while(currentPosition[0]!=rowCoordBlackPix.get(0) || currentPosition[1]!=columnCoordBlackPix.get(0))
		{

			//if just explored pixel is black.
			try
			{
				if(this.image[currentPosition[0]][currentPosition[1]]==1)
				{
						//Save black pixel's coordinates to get contour.
						rowCoordBlackPix.add(currentPosition[0]);
						columnCoordBlackPix.add(currentPosition[1]);
						//Turn left, get new direction and new position
						direction=turning(direction,'L',currentPosition);

				}

				else//pixel is not white
				{
					//Turn right, get new direction and new position
					direction=turning(direction,'R',currentPosition);
				}
			}catch(ArrayIndexOutOfBoundsException e)
			{
				//pixel is not white

					//Turn right, get new direction and new position
					direction=turning(direction,'R',currentPosition);
			}

		}

		//Now with the contour update image.
		int [][] temporal=new int[this.height][this.width];

		for(int i=0;i<rowCoordBlackPix.size();++i)
		{
			temporal[rowCoordBlackPix.get(i)][columnCoordBlackPix.get(i)]=1;
		}

		this.image=temporal;
		temporal=null;

	}
	//determines new direction and position inside the matrix after a move.
	private char turning(char direction, char turn,int [] currentPosition)
	{
		//If direction is north
		if(direction=='N')
		{
			if(turn=='L')
			{
				direction='W';//new direction
				currentPosition[1]=currentPosition[1]-1;//New position
				return direction;
			}
			else//turn == to right
			{
				direction='E';//new direction
				currentPosition[1]=currentPosition[1]+1;
				return direction;
			}

		}
		else if(direction=='W')//If direction is West
		{
			if(turn=='L')
			{
				direction='S';//new direction
				currentPosition[0]=currentPosition[0]+1;//New position
				return direction;
			}else//turn == to right
			{
				direction='N';//new direction
				currentPosition[0]=currentPosition[0]-1;//New position
				return direction;
			}
		}
		else if(direction=='E')//If direction is East
		{
			if(turn=='L')
			{
				direction='N';//new direction
				currentPosition[0]=currentPosition[0]-1;//New position
				return direction;
			}else//turn == to right
			{
				direction='S';//new direction
				currentPosition[0]=currentPosition[0]+1;//New position
				return direction;
			}
		}
		else if(direction=='S')//If direction is South
		{
			if(turn=='L')
			{
				direction='E';//new direction
				currentPosition[1]=currentPosition[1]+1;//New position
				return direction;
			}else//turn == to right
			{
				direction='W';//new direction
				currentPosition[1]=currentPosition[1]-1;//New position
				return direction;
			}
		}
		//Error
		return 'X';
	}

	//Filling in gaps
	public void fillImage()
	{
		int sum;
		for(int i=this.height-2;i>0;--i)
		{
			for(int j=this.width-2;j>0;--j)
			{
				sum=this.image[i-1][j-1]+image[i-1][j]+image[i-1][j+1]+image[i][j+1]+image[i+1][j+1]+image[i+1][j]+image[i+1][j-1]+image[i][j-1];

				if(sum>=4)
					this.image[i][j]=1;

			}
		}
	}

	public String arrayRows()
	{
		String row = "";
		for(int i=0;i< this.height;++i)
		{
			for(int j=0;j<this.width;++j)
			{
				if(image[i][j]==1)
					row=row.concat("1");
				else
					row=row.concat("0");
			}
			row=row.concat("\n");
		}
		return row;
	}


	//Resize image
	public void ResizeImage(int factorToMultiply)
	{
		// aspect ratio:
		int [][] newImage = new int[image.length * factorToMultiply][image[0].length * factorToMultiply];
		//make normalised image
		for (int i = 0; i < image.length ; ++i)
		{
			for (int j = 0; j < image[0].length; ++j)
			{
				int xInit = i * factorToMultiply;
				int xEnd = xInit + factorToMultiply - 1;
				int yInit = j * factorToMultiply;
				int yEnd = yInit + factorToMultiply - 1;

				for (int z = xInit; z <= xEnd; z++)
				{
					for (int w = yInit; w <= yEnd; w++)
					{
						newImage[z][w] = image[i][j];
					}
				}
			}
		}
		//set new dimensions
		this.height=newImage.length;
		this.width=newImage[0].length;

		this.image=newImage;
		newImage=null;

	}

	//get factor to resize
	public int getFactor(int newSize)
	{
		int biggestSide = image.length >= image[0].length ? image.length : image[0].length;
		return newSize / biggestSide;
	}

}
