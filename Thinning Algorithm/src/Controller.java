
public class Controller {

	
	public static void main(String[] args) {
		
		//create objects image 
	    Image imageA=new Image("A.txt",36,45);
	    Image imageE=new Image("E.txt",34,29);
	    
	    //Bs
	    Image imageB1=new Image("B1.txt",20,15);
	    Image imageB2=new Image("B2.txt",20,15);
	    
	    //eights
	    Image imageEight1=new Image("eight1.txt",23,15);
	    Image imageEight2=new Image("eight2.txt",23,15);
	    
	    //fives
	    Image imageFive1=new Image("five1.txt",22,15);
	    Image imageFive2=new Image("five2.txt",22,15);
	    
	    //Gs
	    Image imageG1=new Image("G1.txt",20,15);
	    Image imageG2=new Image("G2.txt",20,15);
	    
	    //Ss
	    Image imageS1=new Image("S1.txt",20,15);
	    Image imageS2=new Image("S2.txt",20,15);
	    
	    //Sixs
	    Image imageSix1=new Image("Six1.txt",22,15);
	    Image imageSix2=new Image("Six2.txt",22,15);
	    
	    //twos
	    Image imageTwo1=new Image("two1.txt",22,15);
	    Image imageTwo2=new Image("two2.txt",22,15);
	    
	    //Zs
	    Image imageZ1=new Image("Z1.txt",20,15);
	    Image imageZ2=new Image("Z2.txt",20,15);
	    
	    //Get skeletons
	    imageB1.applyZSThinning();
	    imageB1.printImage();
	    
	    imageB2.applyZSThinning();
	    imageB2.printImage();
	    
	    imageEight1.applyZSThinning();
	    imageEight1.printImage();
	    
	    imageEight2.applyZSThinning();
	    imageEight2.printImage();
	    
	    imageG1.applyZSThinning();
	    imageG1.printImage();
	    
	    imageG2.applyZSThinning();
	    imageG2.printImage();
	    
	    imageSix1.applyZSThinning();
	    imageSix1.printImage();
	    
	    imageSix2.applyZSThinning();
	    imageSix2.printImage();
	    
	    imageTwo1.applyZSThinning();
	    imageTwo1.printImage();
	    
	    imageTwo2.applyZSThinning();
	    imageTwo2.printImage();
	    
	    imageZ1.applyZSThinning();
	    imageZ1.printImage();
	    
	    imageZ2.applyZSThinning();
	    imageZ2.printImage();
	    
	    
	    //get skeleton,topological features and print final images
	    imageA.applyZSThinning();//A
	    imageA.topologicalFeatures();
	    imageA.pixelDensity();
	    imageA.printImage();
	    
	    System.out.println();
	    imageE.applyZSThinning();//E
	    imageE.topologicalFeatures();
	    imageE.pixelDensity();
	    imageE.printImage();
	    
	    imageFive1.applyZSThinning();//Five1
	    imageFive1.topologicalFeatures();
	    imageFive1.pixelDensity();
	    imageFive1.printImage();
	    
	    imageFive2.applyZSThinning();//Five2
	    imageFive2.topologicalFeatures();
	    imageFive2.pixelDensity();
	    imageFive2.printImage();
	    
	    imageS1.applyZSThinning();//S1
	    imageS1.topologicalFeatures();
	    imageS1.pixelDensity();
	    imageS1.printImage();
	    
	    imageS2.applyZSThinning();//S2
	    imageS2.topologicalFeatures();
	    imageS2.pixelDensity();
	    imageS2.printImage();
	    
	}

}
