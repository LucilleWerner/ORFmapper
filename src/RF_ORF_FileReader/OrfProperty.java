package RF_ORF_FileReader;

/**
 * Created by Heleen on 30-3-2017.
 */
public class OrfProperty {

    private String orfName;
    private String orfSeq;
    private String rf;
    private Integer rfIndx;
    private Integer start;
    private Integer end;

    OrfProperty(String orfName, String orfSeq, String rf, Integer rfIndx, Integer start, Integer end) {

        this.orfName = orfName;
        this.orfSeq = orfSeq;
        this.rf = rf;
        this.rfIndx = rfIndx;
        this.start = start;
        this.end = end;
    }

    public String getRf() {
        return rf;
    }

    public Integer getRfIndx() {
        return rfIndx;
    }

    public String getOrfName() {
        return orfName;
    }

    public String getOrfSeq() {
        return orfSeq;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
}
