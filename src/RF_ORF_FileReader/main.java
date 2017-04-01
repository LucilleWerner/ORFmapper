package RF_ORF_FileReader;

import java.util.ArrayList;

/**
 * Created by Xirect on 20-03-17.
 */
public class main {
    public static void main(String[] args) {
        //new FastaLooper();

        ArrayList<OrfProperty> orfs = new ArrayList<>();
        orfs.add(new OrfProperty("ACACTGCATACTGCATGACGTGTG", 1, 5, 10));

        for(OrfProperty o: orfs) {
            System.out.println(o.orfSeq);
            System.out.println(o.RF);
            System.out.println(o.start);
            System.out.println(o.end);
        }




    }
}
