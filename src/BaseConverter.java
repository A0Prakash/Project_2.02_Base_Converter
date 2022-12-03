import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author 26prakash
 * @version 12.2.2022
 * Extra is using JFileChooser as a UI to select file to convert(lines 56-80, and line 95
 */
public class BaseConverter extends JPanel {
    private final String DIGITS = "0123456789ABCDEF";
    /**
     * Convert a String num in fromBase to base-10 int.
     * @param num the original number
     * @param fromBase the original from base
     * @return a base-10 int of num base fromBase
     */
    public int strToInt(String num, String fromBase)    {
        int value = 0, decimalBase, exp = 0;

        for(int i = num.length() - 1; i >= 0; i--) {
            value += DIGITS.indexOf(num.charAt(i)) * Math.pow(Integer.parseInt(fromBase), exp);
            exp++;
        }

        return value;
    }

    /**
     * intToStr method
     * Converts an integer to a String
     * The integer is in base 10, and the output will be in base toBase
     * @param num
     * @param toBase
     * @return
     */
    public String intToStr(int num, int toBase) {
        String toNum = new String();
        toNum = "";
        while(num > 0) {
            int ctr = num % toBase;
            toNum = "" + DIGITS.charAt(ctr) + toNum;
            num /= toBase;

        }



        return toNum.equals("") ? "0" :toNum;
    }

    /**UI creator method
     *Filters for .dat files only
     * must select a file correctly
     * @return a String with the filename that user selects
     */
    public String UIcreator() {

        String file = "";
        JFileChooser chooser = new JFileChooser();
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("dat");
        //chooser.setFileFilter(filter);
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("dat");
        //chooser.setFileFilter(filter);
        final JFrame frame = new JFrame("");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("DAT", "dat", "Delevered at Terminal");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getName();
        }
        else{
            System.out.println("ERROR: NO FILE SELECTED");
            return "";
        }
    }


    /**
     * inputConvertPrintWrite method
     * It takes the input of a dat file, converts the bases, and writes the output to the file converted.dat.
     *
     */


    public void inputConvertPrintWrite()    {
        Scanner in = null;
        PrintWriter out = null;

        try {
            in = new Scanner(new File("datafiles/" + UIcreator()));
            out = new PrintWriter(new File("datafiles/converted.dat"));
            String[] line;
            while(in.hasNext()) {
                line = in.nextLine().split("\t");
                //System.out.println(line[0]); //String num
                //System.out.println(line[1]); //String fromBase
                //System.out.println(line[2]); //String toBase
                //Write orig num -String
                //tab
                //orig base -String
                //tab
                //convert numb
                //tab
                //write the toBase - String
                if(Integer.parseInt(line[1]) < 2 || Integer.parseInt(line[1]) > 16 ){
                    System.out.println("invalid output base " + line[1]);
                }

                else if(Integer.parseInt(line[2]) < 2 || Integer.parseInt(line[2]) > 16 ){
                    System.out.println("invalid output base " + line[1]);
                }
                else{
                    String line3 = intToStr(strToInt(line[0], line[1]), Integer.parseInt(line[2]));
                    out.println(line[0] + "\t"+ line[1] + "\t" + line3 + "\t" + line[2] + "\t");
                    System.out.println(line[0] + " base " + line[1] + " = " + line3 + " base " + line[2]);
                }
            }

            if(out != null)
                out.close();
            if(in != null)
                in.close();
        }
        catch(Exception e) {
            System.out.println("Error occured: " + e.toString());
        }
    }

    /**
     * Main method of the BaseConverter class.
     * @param args command line arguments, if needed
     */
    public static void main(String[] args) {
       BaseConverter bc = new BaseConverter();
       bc.inputConvertPrintWrite();
    }
}
