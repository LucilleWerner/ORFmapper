package RF_ORF_FileReader;
/**
 * Created by Xirect on 21-03-17.
 */
public class FastaLooper {
    private String RFT0, RFT1, RFT2, RFC0, RFC1, RFC2;
    private int orfsize = 35;

    FastaLooper(){

        DNAsequence seq = new DNAsequence();
        seq.readFile("fasta.fa");

        //Get(0) is de hoeveelste in de lijst, later uitbreiden met keuze adhv combobox.
        for (int i =0; i<seq.getSequenceList().size(); i++){
            String templateSequence = seq.getSequenceList().get(i).split(" ")[1];
            String complementSequence = seq.getComplementDNAsequence(templateSequence);


            SequenceRF rftemplate = new SequenceRF(templateSequence);
            SequenceRF rfcomplementair = new SequenceRF(complementSequence);
            RFT0 = rftemplate.getRF0();
            RFT1 = rftemplate.getRF1();
            RFT2 = rftemplate.getRF2();

            RFC0 = rfcomplementair.getRF0();
            RFC1 = rfcomplementair.getRF1();
            RFC2 = rfcomplementair.getRF2();


            System.out.println("Template Sequence");
            System.out.println("Sequence \t" +templateSequence);
            System.out.println("RFT0 \t" + RFT0);
            System.out.println("RFT1 \t" +RFT1);
            System.out.println("RFT2 \t" +RFT2);
            System.out.println("\n");
            System.out.println("Complement Sequence");
            System.out.println("Sequence \t" +complementSequence);
            System.out.println("RFC0 \t" +RFC0);
            System.out.println("RFC1 \t" +RFC1);
            System.out.println("RFC2 \t" +RFC2);
            System.out.println("\n");

            SequenceORF ORF = new SequenceORF(RFT0, RFT1, RFT2, RFC0, RFC1, RFC2, this.orfsize);
            ORF.findORFs();
            System.out.println(ORF.getCodingsequence());
            System.out.println("-------------------------------------------------------------------------------------------------------------------");


        }

    }
}
