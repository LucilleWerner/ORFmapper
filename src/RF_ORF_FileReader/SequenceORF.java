package RF_ORF_FileReader;
import java.util.ArrayList;


public class SequenceORF {
    private String RFT0, RFT1, RFT2, RFC0, RFC1, RFC2;
    private int orfSize;
    private ArrayList<String> rfs = new ArrayList<>();
    public ArrayList<ArrayList<String>> all_orfs = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<String>>> all_all_orfs = new ArrayList<>();
    public ArrayList<String[]> rfOrfs = new ArrayList<>();
    public ArrayList<OrfProperty> orfProps;


    //Constructor
    SequenceORF(String RFT0, String RFT1, String RFT2, String RFC0, String RFC1, String RFC2, int orfSize){
        this.RFT0 = RFT0;
        this.RFT1 = RFT1;
        this.RFT2 = RFT2;
        this.RFC0 = RFC0;
        this.RFC1 = RFC1;
        this.RFC2 = RFC2;
        this.orfSize = orfSize;
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
        int start;
        int end;
        orfProps = new ArrayList<>();
        // Loopt over je readingframes
        for(int i =0; i<rfs.size(); i++){
            String seq = rfs.get(i);
            String[] cds = seq.split("\\*");
            rfOrfs.add(cds);
            ArrayList<Integer> stopCodons = new ArrayList<>();
            while(seq.contains("*")) {
                int index = seq.indexOf("*");
                stopCodons.add(index);
                seq = seq.substring(index + 1);
            }
            ArrayList<String> orfs = new ArrayList<>();
            // Loopt over sequentie voor ORF's
            for(int j=0; j<cds.length; j++){
                if(j==0) {
                    System.out.println("stopCodons size" + stopCodons.size());
                    System.out.println("cds length" + cds.length);
                }
                // Insert minimum ORF size here
                //four possible cds sequences: 1. no stop at end or beginning: stopCodons: cds list size == stopCodons size-1
                //2. stop at beginning: cds list size == stopCodons size-1. (disregard first stop)
                //3: stop at end: cds list size == stopCondons-1
                //4. stop at end and beginning: cds list size == stopCodons list size (disregard first stop)
                if (cds[j].length() > orfSize){
                    String orf = cds[j];
                    //if the last cds has no stopcodon at the end, the length of the RF is taken as the end index
                    if (j==cds.length-1 && stopCodons.size() < cds.length) {
                        end = seq.length()-1;
                        start = stopCodons.get(j-1)+1;
                    }
                    //if the first subsequence from cds is > orfSize, it gets start index 0 instead of the index of
                    //the previous stopcodon in stopCodons (indexOutOfBounds)
                    else if (j==0) {
                        end = stopCodons.get(j)-1;
                        start = 0;
                    }
                    //else the end index is the index of the stopcodon in Stopcodons, start index is the index of the
                    //previous stopcodon in stopCodons
                    else {
                        end = stopCodons.get(j)-1;
                        start = stopCodons.get(j-1)+1;
                    }

                    //add orfProperty objects with orf sequence,RF index, start index, end index to ArrayList orfProps
                    orfProps.add(new OrfProperty(orf, i, start, end));
                    System.out.println("orfprops size: "+orfProps.size());
                }
            }
        }
    }
    public ArrayList<ArrayList<String>> getCodingsequence(){
        return this.all_orfs;
    }

    public ArrayList getOrfProps() {
        return orfProps;
    }

    public ArrayList getRfORfs() {
        return rfOrfs;
    }
}


