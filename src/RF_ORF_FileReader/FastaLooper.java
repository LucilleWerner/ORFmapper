package RF_ORF_FileReader;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Xirect on 21-03-17.
 */
public class FastaLooper {
    private String RFT0, RFT1, RFT2, RFC0, RFC1, RFC2;
    private ArrayList headerList;
    private ArrayList orfProps;
    private ArrayList rfOrfs;
    private String[] dnaSeqs;
    private String[] rfSeqs;
    private int orfSizeDf = 100;
    DNAsequence seq;

    public FastaLooper(File selectedFile) {

        seq = new DNAsequence();
        headerList = seq.readFile(selectedFile);
        System.out.println(seq.getHeaderList());
        System.out.println(seq.getSequenceList());

    }

    public void analyze(int headerIndex, int orfSize) {

        //Get(0) is de hoeveelste in de lijst, later uitbreiden met keuze adhv combobox.
        int i = headerIndex;

        System.out.println("sequentielijst:");
        for(Object o: seq.getSequenceList()) {
            System.out.println((String)o);
        }
        //String templateSequence = seq.getSequenceList().get(i).split(" ")[1];
        String templateSequence = seq.getSequenceList().get(i);
        String complementSequence = seq.getComplementDNAsequence(templateSequence);

        SequenceRF rftemplate = new SequenceRF(templateSequence);
        SequenceRF rfcomplementair = new SequenceRF(complementSequence);

        SequenceORF ORF = new SequenceORF(rftemplate.getRF0(), rftemplate.getRF1(), rftemplate.getRF2(), rfcomplementair.getRF0(), rfcomplementair.getRF1(), rfcomplementair.getRF2(), orfSize);
        ORF.findORFs();
        orfProps = ORF.getOrfProps();
        rfOrfs = ORF.getRfORfs();
        //System.out.println(ORF.getCodingsequence());
        rfSeqs = new String[]{rftemplate.getRF0(), rftemplate.getRF1(), rftemplate.getRF2(), rfcomplementair.getRF0(), rfcomplementair.getRF1(), rfcomplementair.getRF2()};
        dnaSeqs = new String[] {templateSequence, complementSequence};

    }

    public ArrayList<String> getHeaderList() {
        return headerList;
    }

    public String[] getDNAseqs() {
        return dnaSeqs;
    }

    public String[] getRfSeqs() {
        return rfSeqs;
    }

    public ArrayList getOrfProps() {
        return orfProps;
    }

    public ArrayList getRfOrfs() {
        return rfOrfs;
    }
}