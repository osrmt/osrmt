package com.osframework.framework.utility;
import java.awt.*;
import javax.swing.*;
import java.awt.print.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Properties;

/** A simple utility class that lets you very simply print
 *  an arbitrary component. Just pass the component to the
 *  PrintUtilities.printComponent. The component you want to
 *  print doesn't need a print method and doesn't have to
 *  implement any interface or do anything special at all.
 *  <P>
 *  If you are going to be printing many times, it is marginally more 
 *  efficient to first do the following:
 *  <PRE>
 *    PrintUtilities printHelper = new PrintUtilities(theComponent);
 *  </PRE>
 *  then later do printHelper.print(). But this is a very tiny
 *  difference, so in most cases just do the simpler
 *  PrintUtilities.printComponent(componentToBePrinted).
 *
 *  7/99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 *  May be freely used or adapted.
 */
public class PrintUtility implements Printable {

	  private Component componentToBePrinted;

	  public static void printComponent(Component c) {
	    new PrintUtility(c).print();
	  }
	  
	  public PrintUtility(Component componentToBePrinted) {
	    this.componentToBePrinted = componentToBePrinted;
	  }
	  
	  public void print() {
	    PrinterJob printJob = PrinterJob.getPrinterJob();
	    printJob.setPrintable(this);
	    if (printJob.printDialog())
	      try {
	        printJob.print();
	      } catch(PrinterException pe) {
	        System.out.println("Error printing: " + pe);
	      }
	  }

	  public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
	    if (pageIndex > 0) {
	      return(NO_SUCH_PAGE);
	    } else {
	      Graphics2D g2d = (Graphics2D)g;
	      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	      disableDoubleBuffering(componentToBePrinted);
	      componentToBePrinted.paint(g2d);
	      enableDoubleBuffering(componentToBePrinted);
	      return(PAGE_EXISTS);
	    }
	  }

	  /** The speed and quality of printing suffers dramatically if
	   *  any of the containers have double buffering turned on.
	   *  So this turns if off globally.
	   *  @see enableDoubleBuffering
	   */
	  public static void disableDoubleBuffering(Component c) {
	    RepaintManager currentManager = RepaintManager.currentManager(c);
	    currentManager.setDoubleBufferingEnabled(false);
	  }

	  /** Re-enables double buffering globally. */
	  
	  public static void enableDoubleBuffering(Component c) {
	    RepaintManager currentManager = RepaintManager.currentManager(c);
	    currentManager.setDoubleBufferingEnabled(true);
	  }
	  
	  public static void printString(String s) {
	     PrintJob pjob = new JFrame().getToolkit().getPrintJob(new JFrame(), "Cool Stuff", new Properties());
	      if (pjob != null) {
	        Graphics pg = pjob.getGraphics();
	        if (pg != null) {
	          printLongString (pjob, pg, s, 72);
	          pg.dispose();
	        }
	        pjob.end();
	      }
	  }
	      
      // Print string to graphics via printjob
      // Does not deal with word wrap or tabs
	  //TODO needs work!
      private static void printLongString (PrintJob pjob, Graphics pg, String s, int margin) {
          
        int pageNum = 1;
        int linesForThisPage = 0;
        int linesForThisJob = 0;
        // Note: String is immutable so won't change while printing.
        if (!(pg instanceof PrintGraphics)) {
          throw new IllegalArgumentException ("Graphics context not PrintGraphics");
        }
        StringReader sr = new StringReader (s);
        LineNumberReader lnr = new LineNumberReader (sr);
        String nextLine;
        int pageHeight = pjob.getPageDimension().height - margin;
        Font helv = new Font("Helvetica", Font.PLAIN, 10);
        //have to set the font to get any output
        pg.setFont (helv);
        FontMetrics fm = pg.getFontMetrics(helv);
        int fontHeight = fm.getHeight();
        int fontDescent = fm.getDescent();
        int curHeight = margin;
        try {
          do {
            nextLine = lnr.readLine();
            if (nextLine != null) {         
              if ((curHeight + fontHeight) > pageHeight) {
                // New Page
                System.out.println ("" + linesForThisPage + " lines printed for page " + pageNum);
                if (linesForThisPage == 0) {
                   System.out.println ("Font is too big for pages of this size; aborting...");
                   break;
                }
                pageNum++;
                linesForThisPage = 0;
                pg.dispose();
                pg = pjob.getGraphics();
                if (pg != null) {
                  pg.setFont (helv);
                }
                curHeight = 0;
              }
              curHeight += fontHeight;
              if (pg != null) {
                pg.drawString (nextLine, margin, curHeight - fontDescent);
                linesForThisPage++;

                linesForThisJob++;
              } else {
                System.out.println ("pg null");
              }
            }
          } while (nextLine != null);
        } catch (EOFException eof) {
          // Fine, ignore
        } catch (Throwable t) { // Anything else
          t.printStackTrace();
        }
        System.out.println ("" + linesForThisPage + " lines printed for page " + pageNum);
        System.out.println ("pages printed: " + pageNum);
        System.out.println ("total lines printed: " + linesForThisJob);
      }

}
