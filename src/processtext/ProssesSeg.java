package processtext;

import java.io.*;
import java.util.ArrayList;

/**
 *    This class is used to generize the format of the file 199801_seg.txt and tansfer it to
 *    (word/ ) to calculate the evaluating indicator
 */
public class ProssesSeg {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\199801_seg.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String temgString;
        ArrayList<String> arrayList = new ArrayList<>();
        String[] tokens, split;
        while ((temgString = bufferedReader.readLine()) != null){
            if(temgString.length()  > 2) {
                tokens = temgString.split("  ");
                for(int i=1; i<tokens.length; i++){
                    split = tokens[i].split("/");
                    if(split[0].startsWith("[")){
                        split[0] = split[0].substring(1,split[0].length());
                    }
                    arrayList.add(" " + split[0] +"/");
                }
                arrayList.add("\n");
            }
        }
        FileWriter fileWriter = new FileWriter("C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\segs.txt");
        for(String s : arrayList){
            fileWriter.write(s);
        }
        fileWriter.close();
    }
}
