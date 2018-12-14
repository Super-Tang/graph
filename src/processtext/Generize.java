package processtext;

import java.io.*;
import java.util.ArrayList;

public class Generize {
    public static void main(String[] args) {
        BufferedReader reader;
        ArrayList<String> arrayList = new ArrayList<>();
        OutputStreamWriter fileWriter = null;
        String filePath = "C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\seg_LM.txt";
        boolean seg = true;
        try
        {
            reader = new BufferedReader(new FileReader(new File(filePath)));
            String tempString;
            String []tokens;
            while ((tempString = reader.readLine()) != null) {
                if(seg){
                    tempString = "   " + tempString;
                    seg = false;
                }
               tempString = tempString.substring(3);
               tokens = tempString.split("   ");
               for(String s : tokens){
                   if(s.isEmpty()) {
                       continue;
                   }
                   arrayList.add(" "+ s+"/");
               }
               arrayList.add("\n");
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new OutputStreamWriter(new FileOutputStream("C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\prosseses_segLM1.txt"));
            int i = 0;
            for(String s : arrayList){
                fileWriter.write(s);
            }
            fileWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
