import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.io.IOException;


public class Controller {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("Text Area Examples");
	    JPanel upperPanel = new JPanel();
	    JPanel lowerPanel = new JPanel();
	  
	    f.getContentPane().add(upperPanel, "North");
	    f.getContentPane().add(lowerPanel, "South");

	    
	    
	    
	    //create text area
	    JTextArea array=new JTextArea(20,15);
	    JTextArea array2=new JTextArea(20,15);
	    JTextArea array3=new JTextArea(20,15);
	    
	    //create text Area for results
	    JTextArea result1=new JTextArea(20,15);
	    JTextArea result2=new JTextArea(20,15);
	    JTextArea result3=new JTextArea(20,15);
	    
	    
	  //add JtextArea to Panel
		upperPanel.add(array);
		upperPanel.add(array2);
		upperPanel.add(array3);
		
		//lowerPanel.add;
		lowerPanel.add(result1);
		lowerPanel.add(result2);
		lowerPanel.add(result3);
		
	    
	    //create object image and display it
	    Image1 imageOne=new Image1("C1.txt",22,17);
	    Image1 imageOne2=new Image1("C2.txt",15,26);
	    Image1 imageOne3=new Image1("C3.txt",11,18);
		
	    imageOne.printImage();
		imageOne2.printImage();
		imageOne3.printImage();
	    
		//append string to JTextArea
		array.append(imageOne.arrayRows());
		array2.append(imageOne2.arrayRows());
		array3.append(imageOne3.arrayRows());
		
		//normalize images
		int newSize = 30; 
	    int factor1 = imageOne.getFactor(newSize);
	    int factor2 = imageOne2.getFactor(newSize);
	    int factor3 = imageOne3.getFactor(newSize);
	    imageOne.ResizeImage( factor1);
	    imageOne2.ResizeImage(factor2);
	    imageOne3.ResizeImage(factor3);
	    
	    
			
		//Apply first coat of masks
		imageOne.applyMasks();
		imageOne2.applyMasks();
		imageOne3.applyMasks();
		//Apply second coat of masks
		imageOne.applyMasks();
		imageOne2.applyMasks();
		imageOne3.applyMasks();
		//Apply third coat of masks
		imageOne.applyMasks();
		imageOne2.applyMasks();
		imageOne3.applyMasks();
			
		//Correct slant 
		imageOne.slantCorrection();
		imageOne2.slantCorrection();
		imageOne3.slantCorrection();
		
		imageOne.printImage();
		imageOne2.printImage();
		imageOne3.printImage();
		
		//append results to lower JTextArea
		result1.append(imageOne.arrayRows());
		result2.append(imageOne2.arrayRows());
		result3.append(imageOne3.arrayRows());
		
		//filling
		imageOne.fillImage();
		imageOne2.fillImage();
		imageOne3.fillImage();
		
		//Get contour
		imageOne.contour();
		imageOne2.contour();
		imageOne3.contour();
		
		//contours will be printed in console.
		imageOne.printImage();
		imageOne2.printImage();
		imageOne3.printImage();
		
	    f.pack();
	    f.setVisible(true);
		
		
	}

}
