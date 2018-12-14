package processtext;

import java.io.*;
import java.util.ArrayList;

public class Process {
    public static void main(String[] args) {
        BufferedReader reader;
        ArrayList<String>arrayList = new ArrayList<>();
        OutputStreamWriter fileWriter = null;
        String filePath = "C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\processed_seg.txt";
        try
        {
            reader = new BufferedReader(new FileReader(new File(filePath)));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.length() <= 1) {
                    continue;
                }
                else if(tempString.substring(0,1).equals(" ")){
                    tempString = tempString.substring(1,tempString.length());
                    arrayList.add(tempString + "\n");
                }else{
                    arrayList.add(tempString+"\n");
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new OutputStreamWriter(new FileOutputStream("C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\prosseses.txt"));
            int i = 0;
            for(String s : arrayList){
                fileWriter.write(s);
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
