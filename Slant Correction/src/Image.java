
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Image {
	private int [][] image;
	private int height;
	private int width;
	private int [] betas=new int[4];
	
	
	//constructor.
	public Image(String imageName,int height,int width)
	{
		this.height=height;
		this.width=width;
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
		//Set the size of the Image
		image=new int [this.height][this.width];
		//Create scanner object to read data form file.
		Scanner inPutFile=new Scanner(System.in);
		//open file with iterator in Top.
		inPutFile=new Scanner(new FileInputStream(imageName));
		//Load image into matrix image.
		for(int i=this.height-1;i>=0;--i)
		{
			for(int j=0;j<=this.width-1;++j)
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
		int [] xCoordiante=new int[2];
		//calculate betas, x coordinates and store them.
		xCoordiante=getBetas();
		for(int i=0;i<=3;++i)
		{
			System.out.println("Beta "+i+1+" :"+this.betas[i]);
		}

			//Calculate the Angle Theta
			//calculate height
			xCoordiante=getRealHeight(xCoordiante);
			double A=(xCoordiante[0]-xCoordiante[1])+1;
			System.out.println("Height="+A);

			double B=(this.betas[3]+this.betas[0]-this.betas[2]-this.betas[1])/2;
			System.out.println("B="+B);
			//double B=(this.betas[3]-betas[1])/2

			double theta=  Math.atan(B/A);
			System.out.println("Theta="+theta);

			correctSlant(theta);

	}

	//get height
	private int [] getRealHeight(int [] xCoordinates)
	{
		int xCoordinate;
		//Only if there are rows on top.
		if(xCoordinates[1]!=0)
		{
			xCoordinate=xCoordinates[1];
		//Start on the row where the highest pixel was found using the getBetas function and from there go up to see if there is a higher pixel.
			for(int i=xCoordinate;i>=0;--i)
			{
				for(int j=0;j<=this.width-1;++j)
				{
					//check if there are more clack pixels.If so save their x coordinate
					if(this.image[i][j]==1)
					{
						xCoordinates[1]=i;
					}
				}
			}
		}
		//Only if there are rows on botton.
		if(xCoordinates[0]!=this.height-1)
		{
			xCoordinate=xCoordinates[0];
			//Start on the row where the lowest pixel was found using the getBetas function and from there go down to see if there is a lower pixel.
			for(int i=xCoordinate;i<=height-1;++i)
			{
				for(int j=0;j<=this.width-1;++j)
				{
					//check if there are more clack pixels.If so save their x coordinate
					if(this.image[i][j]==1)
					{
						xCoordinates[0]=i;
					}
				}
			}
		}

		return xCoordinates;
	}

	private void correctSlant(double theta)
	{
		double Newx;

		int [][] Newimage=new int [this.height][this.width];

		//Correct Slant.
		for(int i=this.height-1;i>=0;--i)
		{
			for(int j=0;j<=this.width-1;++j)
			{
				Newx=  i-(j*Math.tan(theta));
				Newx=Math.round(Newx);
				try
				{
				Newimage[(int) Newx][j]=this.image[i][j];
				System.out.println("X coordenate:"+(int)Newx+" Y coordanate:"+j);
				}catch(ArrayIndexOutOfBoundsException e)
				{

				}
			}

		}
		this.image=Newimage;

	}

	//Betas and returns a matrix with the four x coordinates of the Betas.
	private  int []  getBetas()
	{
		//Array that will store the x coordinates of the Betas for later use.
		int [] xCoordiante=new int[4];

		int x=0;
		int y=0;
		//get Betas.
		for(int i=0;i<=3;++i)
		{
			if(i==0)
			{
				x=0;
				y=this.width-1;
				this.betas[i]=y;
			}else if(i==1)
			{
				x=height-1;
				y=0;
				this.betas[i]=y;
			}else if(i==2)
			{
				x=0;
				y=0;
				this.betas[i]=y;
			}else if(i==3)
			{
				x=height-1;
				y=this.width-1;
				this.betas[i]=y;
			}
			while(true)
			{
				System.out.println("X and Y ="+x+" "+y+" i="+i);
				//check if there's a one on the current position.
				if(this.image[x][y]==1)
				{
					//if true calculate Beta4 or Beta3.
					if(i==3 || i== 2)
					{
						betas[i]=y+x;
						System.out.println("coordiante for beta "+(i+1)+" ["+x+"]"+"["+y+"]");
						//Save coordinates
						xCoordiante[i]=x;
						break;
					}

					//if true calculate Beta1 Beta2.
					if(i==0 || i== 1)
					{
						betas[i]=y-x;
						System.out.println("coordiante for beta "+(i+1)+" ["+x+"]"+"["+y+"]");
						//Save coordinates
						xCoordiante[i]=x;
						break;
					}
				}

				//Advance diagonally to next position.(beta1)
				if(i==0)
				{
					//if position is out of bound go back to corresponding column.
					if(y==this.width-1)
					{
						y=this.betas[i];
						x=0;
						if(y!=0)
						{
							--y;
							this.betas[i]=y;
						}else
						{
							break;
						}

					}else
					{
						++x;
						++y;
					}


				}
				//Advance diagonally to next position.(beta2)
				if(i==1)
				{
					//if position is out of bound go back to corresponding column.
					if(y==0)
					{
						y=this.betas[i];
						x=this.height-1;
						if(y!=this.width-1)
						{
							++y;
							this.betas[i]=y;
						}else
						{
							break;
						}
					}else
					{
						--x;
						--y;
					}
				}
				//Advance diagonally to next position.(beta3)
				if(i==2)
				{

					//if position is out of bound go back to corresponding column.
					if(y==0)
					{
						y=this.betas[i];
						x=0;
						if(y!=this.width-1)
						{
							++y;
							this.betas[i]=y;
						}else
						{
							break;
						}
					}else
					{
						++x;
						--y;
					}
				}
				//Advance diagonally to next position.(beta4)
				if(i==3)
				{

					//if position is out of bound go back to corresponding column.
					if(y==this.width-1)
					{
						y=this.betas[i];
						x=height-1;
						if(y!=0)
						{
							--y;
							this.betas[i]=y;
						}else
						{
							break;
						}
					}else
					{
						--x;
						++y;
					}
				}

			}



		}
		//Check which one is the top  and bottom pixel to get the height of the black pixels in the image.
		if(xCoordiante[3]>xCoordiante[1])
			xCoordiante[1]=xCoordiante[3];
		if(xCoordiante[2]>xCoordiante[0])
			xCoordiante[0]=xCoordiante[2];

		return xCoordiante;

	}

	//Print image.
	public void printImage()
	{
		System.out.println("Printing image:");
		for(int i=this.height-1;i>=0;--i)
		{
			for(int j=0;j<=this.width-1;++j)
			{
				System.out.print(this.image[i][j]);
			}
			System.out.println();
		}
	}

}
