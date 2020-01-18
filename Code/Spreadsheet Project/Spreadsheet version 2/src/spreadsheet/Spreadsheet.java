/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spreadsheet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Spreadsheet {

      // Collection for Storing the Cell data
      LinkedHashMap<String, Float> vMap = new LinkedHashMap<>(); //value map
      LinkedHashMap<String, String> fMap = new LinkedHashMap<>(); //formula map
      // Record the list to avoid re-calculation
      ArrayList<String> completedList = new ArrayList<String>();

      // Record the visited list
      ArrayList<String> visitedList = new ArrayList<String>();

      String header = null;
      //beautify letters
      public static String com_start = "----------------------------------------------------------------";
      public static String com_sub = "      ";
      public static String com_error = "Your input is not correct!";
      public static String nosheet_error = "Please select sheet first.";
      public static String sheet_create_success = "Sheet created successfully!";
      public static String sheet_save_success = "Sheet saved successfully!";
      public static String sheet_load_success = "Sheet loaded successfully!";

      // members
      public static int nRow = 0, nCol = 0;
      public static String[] alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
      public static Spreadsheet spreadsheet = new Spreadsheet();
      public static String[] funcStrings = { "SUMA", "MIN","MAX", "PREMEDIO"};
      //input and output objects
      InputStream in;
      OutputStream out;
      PrintStream out_printer;
       BufferedReader in_reader;
      // do one work
      public static void  do_work() {
            spreadsheet.out_printer = new PrintStream(spreadsheet.out);
            spreadsheet.in_reader = new BufferedReader(new InputStreamReader(spreadsheet.in));
            try {
                  System.out.println("Available  Commands:  New sheet[1],     Load sheet[2],      Save sheet[3]");
                  System.out.println("                    Display sheet[A], Display cell[B], Modify cell formula[C]");
                  System.out.print("Command: ");
                  //temp strings
                  String buf;
                  ///
                  String com = spreadsheet.in_reader.readLine();
                  switch (com) {
                        //sheet commands
                        case "1": {
                              procNewSheet();
                              break;
                        }
                        case "2": {
                              procLoadSheet();
                              break;
                        }
                        case "3": {
                              procSaveSheet();
                              break;
                        }
                        // calc commands
                        case "C": {
                              procModifyCell();
                              break;
                        }
                        case "B": {
                              procDisplayCell();
                              break;
                        }
                        case "A": {
                              procDisplaySheet();
                              break;
                        }
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }
      
        
      }
      //sjplit string
       public static String string_strip(String s)
      {
            s = s.replaceAll("\\s+","");
            return s;
      }
      //test one
       public static int do_test(String input , String output)
      {
            spreadsheet.in = new ByteArrayInputStream(input.getBytes());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            spreadsheet.out = bos;
            
            do_work();
            String real_out = bos.toString();
            
            System.out.println("\n**************************");
            System.out.println("Test Case");
            System.out.println("Input: "  + input);
            System.out.print("Output: " + bos.toString());
            String abc = string_strip(real_out);
            
            if( string_strip(output).equals(  string_strip(real_out) ))
            {
                  System.out.println("Test Result: Right");
                  return 1;
            }
            else{
                  System.out.println("Test Result: Wrong");
                  return 0;
             }
            
      }
      public static void main(String[] args) {
        
          //title of program
          System.out.println("*****Spread Sheet Program*****");  
           //create test
           do_test("1", spreadsheet.sheet_create_success); 
           //display test
           do_test("A", "Sheet is empty!");
           //formula add test
           do_test("C\n A1=5 ", "Formula of A1 changed[ Prevous: 0]");
           do_test("C\n A2=A1+5 ", "Formula of A2 changed[ Prevous: 0]");
           do_test("C\n A3=A1*5 ", "Formula of A3 changed[ Prevous: 0]");
           do_test("C\n D4=MIN(A1;A2) ", "Formula of D4 changed[ Prevous: 0]");
           do_test("C\n B1=SUMA(A1:A3) ", "Formula of B1 changed[ Prevous: 0]");
           do_test("C\n B2=HAPPY ", "Formula of B2 changed[ Prevous: 0]");
           do_test("C\n A4=GOOD ", "Formula of B2 changed[ Prevous: 0]");
           //formula value test
            do_test("B\n A1", "A1=5.0");
            do_test("B\n A2", "A2=A1+5=5.0");
            do_test("B\n A3", "A3=A1*5=25.0");
            do_test("B\n A4", "A4=0.0");
            do_test("B\n B1", "B1=SUMA(A1:A3)=40.0");
            do_test("B\n B2", "B2=0.0");
            do_test("B\n D4", "D4=MIN(A1;A2)=5.0");
            do_test("B\n B2", "B2=HAPPY");
            do_test("B\n A4", "A4=GOOD");
            //save test  to 1.ss
           do_test("3\n11.ss", spreadsheet.sheet_save_success);  
           //load test from 1.ss
           do_test("2\n11.ss", spreadsheet.sheet_load_success);  
            
           
           System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
           System.out.println("Test finished! Now you try!");
            //init in and out
          spreadsheet.in = System.in;
          spreadsheet.out = System.out;
          
          while(true)
          {
                 do_work();
          }
          //
          

      }
      public static Boolean procSaveSheet() throws Exception {
            System.out.println(com_start);
            System.out.println("Save Sheet");
            System.out.print("File path to save: ");
            String fn = spreadsheet.in_reader.readLine();
            int i,j;
            try { 
                
                BufferedWriter  fw = new BufferedWriter(new FileWriter(fn)) ;
                fw.write(String.format("%d %d\n", nRow, nCol));
                
                for(i = 0; i<nRow; i++)
                {
                    for(j = 0; j<nCol; j++)
                    {
                        String key = "" + alphabets[j] + (i+1);
                        fw.write(String.format(Locale.ROOT,"%.2f  ", spreadsheet.vMap.get(key)));
                    }
                    fw.write("\n");
                }   
                fw.flush();
                fw.close();
                spreadsheet.out_printer.println(spreadsheet.sheet_save_success);
            } catch (IOException e) {
                  System.err.format("Write file error!: %s%n", e);
            }
            return true;
      }
      public static Boolean procLoadSheet() throws Exception {
            System.out.println(com_start);
            System.out.println("Load Sheet");
             
            System.out.print("File path to load: ");
            String fn = spreadsheet.in_reader.readLine();
            int i,j;
            try {
                String line;
                BufferedReader  fr = new BufferedReader(new FileReader(fn));
                line = fr.readLine();
                String[] sa = line.split("\\s+");
                nRow = Integer.parseInt(sa[0]);
                nCol = Integer.parseInt(sa[1]);
               for( i = 0; i<nRow; i++)
               {
                   line = fr.readLine();
                   sa = line.split("\\s+");
                   for(j = 0; j<nCol; j++)
                   {
                       String key = "" + alphabets[j] + (i+1);
                       String v = sa[j].replace(",",".");
                       spreadsheet.vMap.put(key, Float.parseFloat(v));
                   }
               }
               fr.close();
               spreadsheet.out_printer.println(spreadsheet.sheet_load_success);
            } catch (Exception e) {
                       System.err.format("File not exist!!!: %s%n", e);
            }
            return true;
      }
      
      public static Boolean procDisplaySheet() throws Exception{
            System.out.println(com_start);
            System.out.println("Display Sheet");
            if( nRow == 0 || nCol == 0)
            {
                  spreadsheet.out_printer.println("Sheet is empty!");
                  return false;
            }
            int i,j;
            for(i = 0; i<nRow + 1; i++)
            {
                for(j = 0; j<nCol+1; j++)
                 {
                        if(i == 0) //first row
                        {
                              if(j == 0)
                              {
                                   spreadsheet.out_printer.print(String.format("%-6s", "  No  "));
                              }
                              else{
                                  spreadsheet.out_printer.print(String.format("%-6s",  "|  " + alphabets[j-1] ));
                              }
                        }
                        else{
                            if(j == 0)
                            {
                                System.out.print(String.format("%-6s", "   " + i));
                            }
                            else{
                                String key = "" + alphabets[j-1] + i;
                                Float value = spreadsheet.vMap.get(key);
                                String  formula = spreadsheet.fMap.get(key);
                                if(formula != "" && formula.matches("^[A-Z]*[a-z]*$"))//letter
                                {
                                      spreadsheet.out_printer.print( String.format("%-6s",  "| " + formula));
                                }
                                else{
                                      spreadsheet.out_printer.print( String.format("%-6s",  "| " + String.format("%.2f", value)));
                                }
                                
                            }
                        }
                        
                 }
               spreadsheet.out_printer.println("");
                for(j = 0; j<nCol + 1; j++)
                    spreadsheet.out_printer.print( String.format("%-6s", "------") );
                spreadsheet.out_printer.println("");
            }
            return true;
      }
      public static Boolean procDisplayCell() throws Exception{
            System.out.println(com_start);
            System.out.println("Display Cell");
            if(nRow == -1) 
            {
                   System.out.println(com_sub + nosheet_error);
                   return false;
            }
           
             //temp strings
            String buf;
            System.out.print("Select cell: ");
            
            buf = spreadsheet.in_reader.readLine().replace(" ","");
            String formula = spreadsheet.fMap.get(buf);
            Float  value = spreadsheet.vMap.get(buf);
            
            if(formula.matches("^[A-Z]*[a-z]*$")) //letter
            {
                spreadsheet.out_printer.println(buf + "=" + formula);
            }
            else{
                  if(formula.matches("^[-]*[0-9.]*$") ) //number
                      formula = "";
                  else
                      formula = formula + " = ";

                  spreadsheet.out_printer.println(buf + " = "  + formula + value); 
            }

            return true;
      }
      public static Boolean procModifyCell() throws Exception{
            
            System.out.println(com_start);
            System.out.println("Modify cell Formula");
            if(nRow == -1) 
            {
                   System.out.println(com_sub + nosheet_error);
                   return false;
            }
           
             //temp strings
            String buf;
            ///
            while(true)
            {
                   System.out.print(com_sub + "Input formula [eg: A1=2*B2]: ");
                  buf = spreadsheet.in_reader.readLine().replace(" ","");
                  String[] bufArray= buf.split("=");
                  if(bufArray.length != 2)
                  {
                         spreadsheet.out_printer.println(com_sub + com_error);
                         continue;
                  }
                  //get row and col
                  Pattern p = Pattern.compile("[A-Z][0-9]{1,2}");
                  Matcher m = p.matcher(buf);
                  while(m.find()){
                       String s =  m.group();
                       int r = Integer.parseInt(s.substring(1));
                       int c =s.charAt(0) - 'A'+1;
                       spreadsheet.nCol = Math.max(spreadsheet.nCol, c);
                       spreadsheet.nRow = Math.max(spreadsheet.nRow, r);
                  }
                  //replace empty -> 0
                  spreadsheet.fillZero();
                   //
                  String orgForm = spreadsheet.fMap.put(bufArray[0], bufArray[1]);
                  spreadsheet.out_printer.println("Formula of "+bufArray[0]+ " changed[ Prevous: " + orgForm + "] ");
                  break;
            }
          //calculate table
          Boolean ret = spreadsheet.calc_all();
            return true;
      }
      public static Boolean procNewSheet() throws Exception {
            System.out.println(com_start);
            spreadsheet.out_printer.println(spreadsheet.sheet_create_success);
            //temp strings
            String buf;
            ///
            
            return true;
      }

      /**
       * ********
       * Method to Print the Final Result
	 *********
       */
      public Boolean calc_all()  throws Exception
      {
          visitedList.clear();
          completedList.clear();
          
          for (String value : fMap.keySet()) {
                visitedList.add(value);
                float res = calc(fMap.get(value));
                vMap.put(value, res);
                completedList.add(value);
            }
          return true;
      }
      public float basic_calc(float left, float right, char op)
      {
          switch(op)
          {
              case '+':
                  return left + right;
              case '-': 
                  return left-right;
              case '*':
                  return left * right;
              case '/': 
                  return left/right;
          }
          return 0;
      }
      public float calc(String exp)
      {
            try {
                  //formula error check
                  if (deadlockDetector()) {
                        System.out.println("Formula error!");
                        return 0.0f;
                  }
            } catch (Exception ex) {
                  Logger.getLogger(Spreadsheet.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
           int i,j;
           int bracket_count=0;
           int last_bracket_pos = -1;
           int pmcount = 0; //+- count
           int brcount = 0; // {} count
           
           int no_pm_br = 1; //no +=()
           int no_md =1; //no */
         
           try{
              for(i = 0; i<exp.length(); i++)
              {
                  if(exp.charAt(i) == '(') bracket_count++;
                  else if(exp.charAt(i)== ')') bracket_count--;
                  // separate by operator + and -
                  if(bracket_count == 0)
                  {
                        if(  exp.charAt(i) == '+' || exp.charAt(i) == '-')
                        {
                            no_pm_br = 0;
                            float left =  calc ( exp.substring(0, i) );
                            float right = calc( exp.substring(i+1));
                            return basic_calc( left , right, exp.charAt(i) );
                        }
                  }
              }
              if( brcount == 0 && pmcount==0) //no bracket no +-
              {
                  for( i = 0; i<exp.length(); i++)
                  {
                      if(exp.charAt(i) == '*' || exp.charAt(i )== '/')
                      {
                          no_md = 0;
                            float left =  calc ( exp.substring(0, i) );
                            float right = calc( exp.substring(i+1));
                            return basic_calc( left , right, exp.charAt(i) );
                      }
                  }
              }
             //parse functions
             for(int ii = 0; ii< funcStrings.length; ii++)
             {
                 String fn = funcStrings[ii];
                 int fl = fn.length();
                 String pat= "^" +  fn +"(.*)$";
                 if( exp.matches(pat))//sum function
                {
                    //get cell list
                    String buf = exp.substring( fl + 1, exp.length() - 1).replace(" ", "");
                    String[] bufArray = buf.split(";");
                    ArrayList<String> cellList = new ArrayList<String>();
                    for( i = 0; i<bufArray.length; i++)
                    {
                         if(bufArray[i].contains(":")) //cell block
                         {
                              String[] ca = bufArray[i].split(":");
                              char fc = ca[0].charAt(0);
                              int fr = Integer.parseInt(ca[0].substring(1));
                              char ec = ca[1].charAt(0);
                              int er = Integer.parseInt(ca[1].substring(1));
                              for(j = fr; j<=er; j++)
                              {
                                  for(char k = fc; k<=ec; k++)
                                  {
                                      cellList.add(String.format("%c%d", k, j));
                                  }
                              }
                         }else //one cell
                         {
                               cellList.add(bufArray[i]);
                         }
                    }
                    // calculate
                    float min_v = Float.MAX_VALUE ;
                    float max_v = Float.MIN_VALUE;
                    float sum_v=0.0f;
                    for(i = 0; i<cellList.size(); i++)
                    {
                        String cv = cellList.get(i);
                        float res = 0;
                         if ( ! isCompleted( cv ) )
                         {
                             visitedList.add(cv);
                             res = calc( fMap.get(cv));
                             vMap.put(cv, res);
                             completedList.add(cv);
                             
                         }
                         else
                             res = vMap.get(cv);
                         sum_v += res;
                         min_v = Math.min(min_v, res);
                         max_v = Math.max(max_v, res);
                    }
                    switch(fn)
                    {
                        case "SUMA":
                            return sum_v;
                        case "MIN":
                            return min_v;
                        case "MAX":
                            return max_v;
                        case "PROMEDIO":
                            return sum_v/cellList.size();
                    }
                }
             }
             
               //stop conditions
               
              if(no_md ==1  && no_pm_br==1)
              {
                  if( exp.matches("^[-]*[0-9.]*$")) //number
                  {
                      return Float.parseFloat(exp);
                  }
                  else if(exp.matches("^[A-Z][0-9]{1,2}$")) //cell
                  {
                      if(isCompleted(exp))
                          return vMap.get(exp);
                      else{
                          visitedList.add(exp);
                          float res = calc( fMap.get(exp) );
                          vMap.put(exp, res);
                          completedList.add(exp);
                          return res;
                      }
                      
                  }
                  else if(exp.matches("^[A-Z]*[a-z]*")) //letters
                  {
                        return 0.0f;
                  }
              }
           }
           catch (StackOverflowError e) {
                  e.printStackTrace();
            } catch (Exception e) {
                  e.printStackTrace();
            }
          return 0;
         
      }
      public void printResults() throws Exception {
            System.out.println(header);
            for (Object value : vMap.values()) {
                  System.out.println(String.format("%.5f", (Double.parseDouble((String) value))));
            }
            // System.out.println("**********The End**********");
      }

      /**
       * ********
       * Method to Print the Error Message for dead-lock situation
	 *********
       */
      public void printError() {

            System.out.println(
                    "Possibility of a dead-lock situation. Please check the input file for infinite reference looping. Exiting the sequence.");
      }

      /**
       * ********
       * Method to look for possible dead-lock situation (infinite reference
       * looping). Dead-lock condition to warn and exit the sequence
	 *********
       */
      private Boolean deadlockDetector() throws Exception {
          
            if (visitedList.size() > fMap.size() * 2) {
                  return true;
            }
            
            return false;
      }

         /**
       * ********
       * Method to check if calculation is already done
	 *********
       */
      private boolean isCompleted(String checkCompletion) throws Exception {
            if (completedList.contains(checkCompletion)) {
                  return true;
            }
            return false;
      }

      private void fillZero() {
           //assign initial vlaue zero
            for (int i = 1; i <= nRow; i++) {
                  for (int j = 0; j < nCol; j++) {
                        String key = "" + alphabets[j] + i;
                        if( !spreadsheet.fMap.containsKey( key))
                        {
                              spreadsheet.vMap.put(alphabets[j] + i, new Float(0));
                              spreadsheet.fMap.put(alphabets[j] + i, "0");
                        }
                   
                  }
            }
                  
      }
}
