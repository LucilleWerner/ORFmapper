package RF_ORF_FileReader;

/**
 * Created by Xirect on 20-03-17.
 *
 *
 * FileReader
 */

import com.sun.xml.internal.ws.api.message.HeaderList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DNAsequence {
    private static BufferedReader inFile;
    private String sequenceTemp="";
    private ArrayList<String> sequenceList = new ArrayList<>();
    private ArrayList<String> headerList = new ArrayList<>();

    public ArrayList readFile(File selectedFile) {
        String line;
        try {
            inFile = new BufferedReader(new FileReader(selectedFile));
            while ((line = inFile.readLine()) != null){
                if (line.startsWith(">")){

                    line = line.replace(" ","_");
                    headerList.add(line);
                    if(sequenceTemp!=""){
                        sequenceList.add(sequenceTemp);
                    }
                    sequenceTemp="";
                    //sequenceTemp+= line +" ";
                }
                else{
                    sequenceTemp+= line;
                }
            }
            sequenceList.add(sequenceTemp);
            inFile.close();


        } catch (FileNotFoundException ex){
            System.out.println("Bestand niet gevonden");
        } catch (IOException ioe){
            System.out.println("Fout met het lezen van de regel.");
        }
        return headerList;
    }

    public ArrayList<String> getSequenceList(){
        return sequenceList;
    }

    public ArrayList<String> getHeaderList() {
        return headerList;
    }

    public String getComplementDNAsequence(String sequence){
        return sequence.toLowerCase().replace("a","T").replace("t","A").replace("c","G").replace("g","C");

    }

}