package processtext;

import java.io.*;
import java.util.HashMap;

/**
 * @author Super-Tang
 *
 *   This class is used to extract the transfer probability P(w_i | W_i-1)
 *   The format of output text is: word_i/word_i-1/P(w_i | w_i-1)
 */
public class Bigram {

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        String[] split;
        HashMap<String, Integer> map = new HashMap<>();
        OutputStreamWriter fileWriter = null;
        String filePath = "C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\new 2.txt";
        HashMap<String,Integer>map1 = new HashMap<>();
        map1.put("start", 0);
        int freq;
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.isEmpty()) {
                    continue;
                }
                map1.put("start",map1.get("start") + 1);
                String[] tokens = tempString.split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    split = tokens[i].split("/");
                    if (split[0].startsWith("[")) {
                        split[0] = split[0].substring(1, split[0].length());
                    }
                    tokens[i] = split[0];
                }
                for (int i = 0; i < tokens.length; i++) {
                    if(map1.containsKey(tokens[i])) {
                        freq = map1.get(tokens[i]);
                        map1.put(tokens[i], freq + 1);
                    } else {
                        map1.put(tokens[i], 1);
                    }
                }
                for (int i = 0; i <= tokens.length; i++) {
                    if (i == 0) {
                        if (map.containsKey(tokens[i] + "/start")) {
                            freq = map.get(tokens[i] + "/start");
                            map.put(tokens[i] + "/start", freq + 1);
                        } else {
                            map.put(tokens[i] + "/start", 1);
                        }
                    } else if (i == tokens.length) {
                        if (map.containsKey("end/" + tokens[i-1])) {
                            freq = map.get("end/" + tokens[i-1]);
                            map.put("end/" + tokens[i-1], freq + 1);
                        } else {
                            map.put("end/" + tokens[i-1], 1);
                        }
                    } else {
                       // System.out.println(tokens[i=1]);
                        if (map.containsKey(tokens[i] +"/" + tokens[i-1])) {
                            freq = map.get(tokens[i] +"/" + tokens[i-1]);
                            map.put(tokens[i] +"/" + tokens[i-1], freq + 1);
                        } else {
                            map.put(tokens[i] +"/" + tokens[i-1], 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new OutputStreamWriter(new FileOutputStream("C:\\Users\\Super-Tang\\Desktop\\corpus_Guideline\\freq4.txt"));
            int i = 0;
            for (String anArrayList : map.keySet()) {
                i++;
                split = anArrayList.split("/");
                fileWriter.write(anArrayList +"/" + map.get(anArrayList) + "/" + map1.get(split[1]) +"\t");
                if(i == 10){
                    fileWriter.write("\n");
                    i = 0;
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
}
