package RF_ORF_FileReader;

/**
 * Created by Xirect on 20-03-17.
 *
 *
 * FileReader
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DNAsequence {
    private static BufferedReader inFile;
    private String sequence="";
    private ArrayList<String> sequenceList = new ArrayList<>();

    public void readFile(String filename) {
        String line;
        try {
            inFile = new BufferedReader(new FileReader(filename));
            while ((line = inFile.readLine()) != null){
                if (line.startsWith(">")){
                    if(sequence!=""){
                        sequenceList.add(sequence);
                    }
                    sequence="";
                    sequence+= line +" ";
                }
                else{
                    sequence+= line;
                }
            }
            sequenceList.add(sequence);
            inFile.close();

        } catch (FileNotFoundException ex){
            System.out.println("Bestand niet gevonden");
        } catch (IOException ioe){
            System.out.println("Fout met het lezen van de regel.");
        }
    }

    public ArrayList<String> getSequenceList(){
        return sequenceList;
    }

    private void setSequenceList(ArrayList<String> sequenceList){
        this.sequenceList = sequenceList;
    }

    public String getComplementDNAsequence(String sequence){
        return sequence.toLowerCase().replace("a","T").replace("t","A").replace("c","G").replace("g","C");

    }



}
