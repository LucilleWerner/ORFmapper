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
    private String dnaSeq;
    DNAsequence seq;

    public FastaLooper(File selectedFile) {

        seq = new DNAsequence();
        headerList = seq.readFile(selectedFile);
    }

    public FastaLooper(String dnaSeq) {
        seq = new DNAsequence();
        this.dnaSeq = dnaSeq;
    }

    public void analyze(String header, int headerIndex, int orfSize, boolean fromDb) {

        String templateSequence;
        if(!fromDb) {
            int i = headerIndex;
            templateSequence = seq.getSequenceList().get(i);
        }
        else {
            templateSequence = dnaSeq;
        }
        String complementSequence = seq.getComplementDNAsequence(templateSequence);

        SequenceRF rftemplate = new SequenceRF(templateSequence);
        SequenceRF rfcomplementair = new SequenceRF(complementSequence);

        SequenceORF ORF = new SequenceORF(rftemplate.getRF0(), rftemplate.getRF1(), rftemplate.getRF2(), rfcomplementair.getRF0(), rfcomplementair.getRF1(), rfcomplementair.getRF2(), orfSize);
        ORF.findORFs(header);
        orfProps = ORF.getOrfProps();
        rfOrfs = ORF.getRfORfs();
        rfSeqs = new String[]{rftemplate.getRF0(), rftemplate.getRF1(), rftemplate.getRF2(), rfcomplementair.getRF0(), rfcomplementair.getRF1(), rfcomplementair.getRF2()};
        dnaSeqs = new String[]{templateSequence, complementSequence};

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