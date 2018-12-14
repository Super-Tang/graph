package graph;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Super-Tang
 */
public class WordGraph {
    private final graph.Graph<String> graph = Graph.empty();
    private final ArrayList<String>arrayList = new ArrayList<>();
    private final long wordNum = 1000000;
    private final double lambda = 0.1;
    private final double defaultValue;
    private double []matrix;
    private double []array;
    private int[]path;
    private String before, temp, tempString;

    private WordGraph(String file){
        if(graph.isEmpty()){
            String []split2;
            String[] tokens;
            BufferedReader reader;
            double weight;
            try {
                reader = new BufferedReader(new FileReader(new File(file)));
                while ((tempString = reader.readLine()) != null) {
                    tokens = tempString.split("\t");
                    for (String token : tokens) {
                        split2 = token.split("/");
                        weight =  Math.log((Double.parseDouble(split2[2]) + lambda) / (Double.parseDouble(split2[3]) + wordNum * lambda));
                        graph.set(split2[1], split2[0], Integer.parseInt(split2[3]), weight);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        defaultValue = Math.log(lambda /(lambda * wordNum + graph.getNum("start")));
    }

    private void segmentation(String line, boolean f){
        int n = line.length();
        matrix = new double[n+1];
        path = new int[n+1];
        String []split;
        if(graph.targets("start").containsKey(line.substring(0,1))){
            matrix[0] = graph.targets("start").get(line.substring(0,1));
            path[0] = -1;
        }
        else {
            matrix[0] = defaultValue;
            path[0] = -1;
        }
        for(int i=1; i<=n; i++){
            tempString = calculate(line, i);
            split = tempString.split("/");
            path[i] = Integer.parseInt(split[0]);
            matrix[i] = Double.parseDouble(split[1]);
        }
        if(f) {
            arrayList.add("\n");
        }
        int i = n;
        if(line.length() == 1){
            arrayList.add(line);
            return;
        }
        while(path[path[i]] != -1){
            arrayList.add(line.substring(path[i], i));
            i = path[i];
        }
        arrayList.add(line.substring(0,i));
//        for(String s : arrayList){
//            System.out.println(s);
//        }
    }

    private String calculate(String line, int num){
        array = new double[num];
        if(num <= 8) {
            temp = line.substring(0, num);
            array[0] = graph.targets("start").getOrDefault(temp, (1 + (double)num/2) * defaultValue);
        }
        double tempMax = -100000000;
        int tempIndex = 0;
        for(int i = 1; i<num; i++){
            temp = line.substring(i,num);
            if(i > 26){
                for (int j = i - 26; j < i; j++) {
                    before = line.substring(j, i);
                    if (graph.targets(before).get(temp) != null && graph.targets(before).get(temp) > tempMax) {
                        tempIndex = j;
                        tempMax = graph.targets(before).get(temp);
                    }
                }
            }else {
                for (int j = 0; j < i; j++) {
                    before = line.substring(j, i);
                    if (graph.targets(before).get(temp) != null && graph.targets(before).get(temp) > tempMax) {
                        tempIndex = j;
                        tempMax = graph.targets(before).get(temp);
                    }
                }
            }
            if(tempIndex > 0){
                before = line.substring(tempIndex,i);
                array[i] = matrix[i] + tempMax;
            } else {
                before = line.substring(path[i],i);
                if(graph.targets(before).get(temp) != null){
                    array[i] = matrix[path[i]] + graph.targets(before).get(temp);
                }else{
                    array[i] = matrix[i];
                    for(int k = i; k < num; k++) {
                        array[i] += Math.log(lambda / (graph.getNum(before) + lambda * wordNum ));
                    }
                }
            }
            tempIndex = 0;
            tempMax = -100000000;
        }
        tempMax = -1000000;
        tempIndex = 0;
        for(int i=0; i<num; i++){
            if(array[i] > tempMax && array[i] < 0){
                tempMax = array[i];
                tempIndex = i;
            }
        }
        if( tempIndex == 0 && num == line.length()){
            for(int i = num-1; i>0; i--){
                if(path[i] != 0){
                    tempIndex = i;
                    break;
                }
            }
        }
        return tempIndex +"/" + tempMax;
    }

    private ArrayList<String> eliminate(String line){
        int start = 0;
        ArrayList<String>ret = new ArrayList<>();
        for(int i=0; i<line.length(); i++){
            if(line.substring(i,i+1).matches("[‘’“”∶、。《》『』！（）＊：；？]")){
                ret.add(line.substring(start,i));
                ret.add(line.substring(i,i+1));
                start = i+1;
            }
        }
        if(start < line.length()){
            ret.add(line.substring(start,line.length()));
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        WordGraph wordGraph = new WordGraph("C:\\Users\\super-Tang\\Desktop\\corpus_Guideline\\freq4.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("C:\\Users\\super-Tang\\Desktop\\corpus_Guideline\\199801_sent.txt")));
        String tempString, s;
        ArrayList<String>processed;
        int line = 0, n;
        boolean flag;
        while ((tempString = bufferedReader.readLine())!=null) {
            line++;
            flag = false;
            System.out.println(line);
            if (tempString.length() < 1) {
                continue;
            }
            processed = wordGraph.eliminate(tempString);
            n = processed.size();
            for (int i = 0; i < n; i++) {
                s = processed.get(i);
                if (i == n - 1) {
                    flag = true;
                }
                if (s.length() != 0) {
                    wordGraph.segmentation(s, flag);
                    wordGraph.write();
                }
            }
        }
    }

    private void write(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("C:\\Users\\super-Tang\\Desktop\\corpus_Guideline\\seg_LM.txt",true);
            int i = arrayList.size()-1;
            while (i >= 0){
                if(arrayList.get(i).length() == 0){
                    i--;
                    continue;
                }
                else {
                    fileWriter.write(arrayList.get(i) + "   ");
                }
                i--;
            }
            arrayList.clear();
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
