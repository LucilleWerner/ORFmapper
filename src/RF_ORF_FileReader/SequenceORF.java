package RF_ORF_FileReader;
import java.util.ArrayList;

public class SequenceORF {
    private String RFT0, RFT1, RFT2, RFC0, RFC1, RFC2;
    private int ORFSize;
    private ArrayList<String> rfs = new ArrayList<>();
    public ArrayList<String> orfs = new ArrayList<>();

    //Constructor
    SequenceORF(String RFT0, String RFT1, String RFT2, String RFC0, String RFC1, String RFC2, int ORFSize){
        this.RFT0 = RFT0;
        this.RFT1 = RFT1;
        this.RFT2 = RFT2;
        this.RFC0 = RFC0;
        this.RFC1 = RFC1;
        this.RFC2 = RFC2;
        this.ORFSize = ORFSize;
        initializeArrayList();
    }

    private void initializeArrayList(){
        rfs.add(RFT0);
        rfs.add(RFT1);
        rfs.add(RFT2);
        rfs.add(RFC0);
        rfs.add(RFC1);
        rfs.add(RFC2);

    }

    public void findORFs(){
        for(int i =0; i<rfs.size(); i++){
            String seq = rfs.get(i);
            String[] cds = seq.split("\\*");
            for(int j=0; j<cds.length; j++){
                // Insert minimum ORF size here
                if (cds[j].length() > ORFSize){
                    orfs.add(cds[j]);

                }

            }


        }

    }

    public ArrayList<String> getCodingsequence(){
        return this.orfs;
    }
}
