package RF_ORF_FileReader;

/**
 * Created by Heleen on 30-3-2017.
 */
public class OrfProperty {

    public String orfSeq;
    public Integer RF;
    public Integer start;
    public Integer end;

    public OrfProperty(String orfSeq, Integer RF, Integer start, Integer end) {

        this.orfSeq = orfSeq;
        this.RF = RF;
        this.start = start;
        this.end = end;
    }
}
