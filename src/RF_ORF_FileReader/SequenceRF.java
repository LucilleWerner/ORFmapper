package RF_ORF_FileReader;
public class SequenceRF {
    private String RF0,RF1,RF2, sequence0, sequence1, sequence2;
    //Constructor
    SequenceRF(String sequence){
        this.sequence0 = sequence;
        this.sequence1 = sequence.substring(1);
        this.sequence2 = sequence.substring(2);
        GenerateAmino gen = new GenerateAmino();
        this.RF0 = makeCodon(sequence0, gen);
        this.RF1 = makeCodon(sequence1, gen);
        this.RF2 = makeCodon(sequence2, gen);
    }

    private String makeCodon(String sequence, GenerateAmino gen){
        String amino ="";
        String aminoseq = "";
        String codon ="";
        for (int i=0; i<sequence.length()-2; i+=3){

            codon = sequence.substring(i,i+3);
            amino = gen.getAminoAcidSequence(codon);
            aminoseq += amino;

        }
        return aminoseq;
    }

    public String getRF0() {
        return RF0;
    }

    public String getRF1() {
        return RF1;
    }

    public String getRF2() {
        return RF2;
    }
}