package metrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Super-Tang
 */
public class Calculate {
    public static void main(String[] args) {
        long hit = 0;
        long miss = 0;
        long total = 0;
        long all = 0;
        BufferedReader reader1, reader2;
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        ArrayList<String> stringArrayList;
        String tempString1, tempString2;
        try {
            reader1 = new BufferedReader(new FileReader(new File("C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\segs.txt")));
            while ((tempString1 = reader1.readLine()) != null) {
                String[] tokens = tempString1.split(" ");
                total += tokens.length-1;
                ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList(tokens));
                arrayList.add(arrayList1);
            }
            int line = 0;
            reader2 = new BufferedReader(new FileReader(new File("C:\\Users\\Super-Tang\\Desktop\\new_LM.txt")));
            while ((tempString2 = reader2.readLine()) != null) {
                if(tempString2.isEmpty()){
                    break;
                }
                String[] tokens = tempString2.split(" ");
                all += tokens.length -1 ;
                stringArrayList = arrayList.get(line);
                int line_hit = 0;
                for (int i=1; i<tokens.length; i++) {
                    if (stringArrayList.contains(tokens[i])) {
                        line_hit++;
                    }
                }
                hit += line_hit;
                miss += (tokens.length - 1 - line_hit);
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hit: \t" + hit + " Miss: \t" + miss + "Total: \t" + total + " All: \t" + all);
    }
}
