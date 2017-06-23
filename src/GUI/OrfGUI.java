package GUI;

/**
 * Created by Heleen on 27-3-2017.
 */


import RF_ORF_FileReader.FastaLooper;
import RF_ORF_FileReader.OrfProperty;
import Database.Database;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.*;


/**
 * Created by Heleen on 22-3-2017.
 */
public class OrfGUI {

    /**
     * mainFrame: JFrame: window that is the base of the graphical application.
     * fileTextField: JTextField: textfield that displays the filepath when file is loaded.
     * fileChooser: JFileChooser: class for opening an input file and loading the contents.
     * browseButton, openButton, analyzeButton, blastButton, saveButton: buttons for opening file, analyzing the contents,
     * blasting the results and saving the results to the database
     */
    private JFrame mainFrame;
    private JTextField fileTextField, orfSizeField;
    private JFileChooser fileChooser;
    private JButton browseButton, openButton, analyzeButton, blastButton, saveButton;
    private JLabel pathLabel, selectseqLabel,selectdbLabel, selectORFlenLabel;
    private JLabel orfDrawingPanelLabel, selectORFLabel, saveLabel, resultsLabel;
    private JSlider orfSlider;
    private JComboBox sequenceBox, dbSeqBox;
    private JComboBox orfBox;
    private JScrollPane rfScrollpane, orfScrollPane;
    private JPanel rfDrawingPane, orfDrawingPane, resultsPanel;
    private String path, sequenceChoice, dbSeqChoice, orfChoice, dnaSeq;
    private String[] dnaSeqs, rfSeqs;
    private ArrayList<String> openedFiles;
    private ArrayList<String> headers;
    private ArrayList<OrfProperty> orfProps;
    private ArrayList<String[]> rfOrfs;
    private Dimension rfArea;
    private Dimension orfArea;
    private int orfSize, fileVal;
    private Integer orfChoiceIndx;
    private boolean pathPasted = false;
    private boolean newFile = false;
    private boolean fromDb = false;
    private File selectedFile;
    private FastaLooper fastaLooper;

    /**
     * constructor for ORFGui, prepareGUI is called for initialization of the components of the GUI
     */
    public OrfGUI() {
        prepareGui();
    }

    /**
     * main method: new GUI object is initialized, showEvents is called for initialization of Listeners
     * @param args
     */
    public static void main(String[] args) {
        OrfGUI newGui = new OrfGUI();
        newGui.showEvents();
    }

    /**
     * method for initalizing, setting the properties and placing the components in the JFrame
     */
    private void prepareGui() {

        //initialzing ArrayList for keeping track of opened files in the program
        openedFiles = new ArrayList<>();

        //initializing JFrame, and setting properties
        mainFrame = new JFrame("ORF finder");
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

        //get content pane for adding the components
        Container pane = mainFrame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        //gridbagconstraints Layout for setting absolute coordinates
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,10,10,20);

        //initializing components and setting the coordinates
        pathLabel = new JLabel("file path");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(pathLabel, c);

        fileTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 0;
        //c.weightx = 1.0;
        c.gridwidth = 3;
        c.ipadx = 450;
        pane.add(fileTextField, c);

        browseButton = new JButton("browse");
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 6;
        c.gridy = 0;
        //c.weightx = 0;
        c.gridwidth = 1;
        c.ipadx = 0;
        pane.add(browseButton, c);

        selectseqLabel = new JLabel("from file");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(selectseqLabel, c);

        sequenceBox = new JComboBox();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 150;
        c.ipady = 0;
        Dimension d = new Dimension(40,20);
        sequenceBox.setPreferredSize(d);
        sequenceBox.setMaximumSize(d);
        System.out.println("combobox preferred size"+sequenceBox.getPreferredSize());
        pane.add(sequenceBox, c);

        selectdbLabel = new JLabel("Or from database");
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(selectdbLabel, c);

        dbSeqBox = new JComboBox();
        updateDbSeqComboBox();
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 2;
        c.ipadx = 150;
        c.ipady = 0;
        dbSeqBox.setPreferredSize(new Dimension(40,20));
        dbSeqBox.setMaximumSize(new Dimension(40,20));
        pane.add(dbSeqBox, c);

        openButton = new JButton("open");
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 6;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        pane.add(openButton, c);

        selectORFlenLabel = new JLabel("ORF length");
        c.anchor= GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipadx = 0;
        pane.add(selectORFlenLabel, c);

        orfSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 150);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.ipadx = 100;
        pane.add(orfSlider, c);

        orfSizeField = new JTextField("150");
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipadx = 30;
        pane.add(orfSizeField, c);

        analyzeButton = new JButton("analyze");
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 6;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipadx = 0;
        pane.add(analyzeButton, c);

        //initializing scrollpanel and adding JFRame to it
        rfDrawingPane = new RFDrawingPane();
        rfDrawingPane.setBackground(Color.WHITE);
        rfDrawingPane.setAutoscrolls(true);
        rfScrollpane = new JScrollPane(rfDrawingPane);
        rfScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 1;
        c.gridy = 3;
        //c.weightx = 1.0;
        c.gridwidth = 6;
        c.ipadx = 730;
        c.ipady = 300;
        pane.add(rfScrollpane, c);

        orfDrawingPanelLabel = new JLabel("ORF sequence");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(orfDrawingPanelLabel, c);

        orfDrawingPane = new ORFDrawingPane();
        orfDrawingPane.setBackground(Color.WHITE);
        orfDrawingPane.setAutoscrolls(true);
        orfScrollPane = new JScrollPane(orfDrawingPane);
        orfScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 6;
        c.ipadx = 730;
        c.ipady = 60;
        pane.add(orfScrollPane, c);

        selectORFLabel = new JLabel("select ORF");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(selectORFLabel, c);

        orfBox = new JComboBox();
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.ipadx = 100;
        pane.add(orfBox, c);

        blastButton = new JButton("BLAST ORF");
        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 1;
        c.ipadx = 0;
        pane.add(blastButton, c);

        saveLabel = new JLabel("save results to database");
        c.gridx = 4;
        c.gridy = 5;
        pane.add(saveLabel, c);

        saveButton = new JButton("Save");
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 6;
        c.gridy = 5;
        pane.add(saveButton, c);

        resultsLabel = new JLabel("BLAST results");
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 6;
        pane.add(resultsLabel, c);

        resultsPanel = new JPanel();
        resultsPanel.setBackground(Color.WHITE);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridx = 1;
        c.gridy = 6;
        //c.weightx = 1.0;
        c.gridwidth = 6;
        c.ipadx = 730;
        c.ipady = 100;
        pane.add(resultsPanel, c);

        Dimension d2 = new Dimension(882,672);
        System.out.println("dimension: "+d2);
        pane.setPreferredSize(d2);
        pane.setMaximumSize(d2);
        //Dimension d = mainFrame.getPreferredSize();
        //mainFrame.setPreferredSize(d);
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);


    }

    /**
     * initialzing Listeners for the textFields, Buttons, ComboBoxes and JPanels
     */
    private void showEvents() {
        //initializing action listeners
        fileTextField.addActionListener(new FileTextFieldListener());
        orfSizeField.addActionListener(new orfSizeFieldListener());
        browseButton.addActionListener(new BrowseButtonListener());
        openButton.addActionListener(new OpenButtonListener());
        blastButton.addActionListener(new BlastButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        analyzeButton.addActionListener(new AnalyzeButtonListener());
        sequenceBox.addActionListener(new SequenceBoxListener());
        dbSeqBox.addActionListener(new DbSeqBoxListener());
        orfBox.addActionListener(new OrfBoxListener());
        orfSlider.addChangeListener(new orfSliderListener());
        orfDrawingPane.addMouseListener(new ORFdrawingPaneListener());
        rfDrawingPane.addMouseListener(new RFdrawingPaneListener());

    }

    /**
     * Listener for ORFdrawingPane, repaint JPanel after scrolling
     */
    public class ORFdrawingPaneListener implements MouseListener {
        public void mouseEntered(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e) {
            orfDrawingPane.repaint();
        }
    }

    /**
     * Listener for RFdrawingPane, repaint JPanel after scrolling
     */
    public class RFdrawingPaneListener implements MouseListener {
        public void mouseEntered(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e) {
            rfDrawingPane.repaint();
        }


    }

    /**
     * Class for drawing the selected ORF from orfBox in the JPanel orfDrawingPane
     */
    public class ORFDrawingPane extends JPanel {
        protected void PaintComponent(Graphics g) {
            super.paintComponent(g);

            int indx;
            if(orfChoiceIndx!= null) {
                indx = orfChoiceIndx;}
            else {
                indx = 0;}
            String orf = orfProps.get(indx).getOrfSeq();
            orfArea = new Dimension(0, 300);
            orfDrawingPane.removeAll();
            g.setColor(Color.black);

            Font font = new Font("Consolas", Font.BOLD, 20);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics();
            double seqHeight = metrics.getStringBounds(orf,g).getHeight();
            double seqWidth = metrics.getStringBounds(orf,g).getWidth();
            g.drawString(orf, 0, (int)(30+seqHeight));

            orfArea.width = (int)seqWidth;
            orfDrawingPane.setPreferredSize(orfArea);
            orfDrawingPane.revalidate();
        }
    }

    /**
     * Class for drawing the selected header from sequenceBox in the JPanel rfDrawingPane
     */
    public class RFDrawingPane extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if(dnaSeqs != null && rfSeqs != null) {

                String template = dnaSeqs[0];
                String complementary = dnaSeqs[1];
                String rfT0 = rfSeqs[0];
                String rfT1 = rfSeqs[1];
                String rfT2 = rfSeqs[2];
                String rfC0 = rfSeqs[3];
                String rfC1 = rfSeqs[4];
                String rfC2 = rfSeqs[5];

                String[] rft0 = rfOrfs.get(0);
                String[] rft1 = rfOrfs.get(1);
                String[] rft2 = rfOrfs.get(2);
                String[] rfc0 = rfOrfs.get(3);
                String[] rfc1 = rfOrfs.get(4);
                String[] rfc2 = rfOrfs.get(5);

                rfArea = new Dimension(0, 300);
                double height = 300;
                rfDrawingPane.removeAll();

                //setting the Font with a font with uniform spacing
                Font font = new Font("Consolas", Font.BOLD, 20);
                FontMetrics metrics = g.getFontMetrics(font);

                //get bounds from font
                double letterWidth = metrics.getStringBounds("A", g).getWidth();
                double seqWidth = metrics.getStringBounds(template, g).getWidth();
                double seqHeight = metrics.getStringBounds(template,g).getHeight();

                //set font and color for the text
                g.setFont(font);
                g.setColor(Color.black);

                //draw template and complementary dna strand in the middle of the panel
                g.drawString(template, 0, (int)((height/2)-seqHeight));
                g.drawString(complementary, 0, (int)((height/2)+seqHeight));

                //standard intervening space for the aminoacid letters
                int space = (int)(letterWidth*3);
                //current starting point for drawing, reading frame 0 starts at 1.5, reading frame 1 at 2.5 etc
                int cur = (int)(letterWidth*1.5);
                //if the original dna strand (before splitting on *) containes a *, draw a *
                if(rfT0.startsWith("*")){
                    g.drawString("*", cur, 100);
                    cur+= space;
                }
                //for string in dna split on * symbol (a String[]) draw the string, when the substring is a ORF, draw
                //the string orange, when it is not, draw the string black
                for(String sub: rft0) {
                    g.setColor(Color.black);
                    if(sub.length()>orfSize) {
                        g.setColor(Color.orange);}
                    for(char c: sub.toCharArray()) {
                        g.drawString(Character.toString(c), cur, 100);
                        cur+= space;
                    }
                    if(!sub.equals(rft0[rft0.length-1]) || rfT0.endsWith("*"))
                        g.drawString("*", cur, 100);
                        cur+= space;
                }
                cur = (int)(letterWidth*2.5);
                if(rfT1.startsWith("*")){
                    g.setColor(Color.black);
                    g.drawString("*", cur, 70);
                    cur+= space;
                }
                for(String sub: rft1) {
                    g.setColor(Color.black);
                    if(sub.length()>orfSize) {
                        g.setColor(Color.orange);}
                    for(char c: sub.toCharArray()) {
                        g.drawString(Character.toString(c), cur, 70);
                        cur+= space;
                    }
                    if(!sub.equals(rft1[rft1.length-1]) || rfT1.endsWith("*"))
                        g.drawString("*", cur, 70);
                        cur+=space;
                }
                cur = (int)(letterWidth*3.5);
                if(rfT2.startsWith("*")){
                    g.setColor(Color.black);
                    g.drawString("*", cur, 40);
                    cur+= space;
                }
                for(String sub: rft2) {
                    g.setColor(Color.black);
                    if(sub.length()>orfSize) {
                        g.setColor(Color.orange);}
                    for(char c: sub.toCharArray()) {
                        g.drawString(Character.toString(c), cur, 40);
                        cur+= space;
                    }
                    if(!sub.equals(rft2[rft2.length-1]) || rfT2.endsWith("*"))
                    g.drawString("*", cur, 40);
                    cur+= space;
                }
                cur = (int)(letterWidth*1.5);
                if(rfC0.startsWith("*")){
                    g.setColor(Color.black);
                    g.drawString("*", cur, 200);
                    cur+= space;
                }
                for(String sub: rfc0) {
                    g.setColor(Color.black);
                    if(sub.length()>orfSize) {
                        g.setColor(Color.orange);}
                    for(char c: sub.toCharArray()) {
                        g.drawString(Character.toString(c), cur, 200);
                        cur+= space;
                    }
                    if(!sub.equals(rfc0[rfc0.length-1]) || rfC0.endsWith("*"))
                    g.drawString("*", cur, 200);
                    cur+= space;
                }
                cur = (int)(letterWidth*2.5);
                if(rfC1.startsWith("*")){
                    g.setColor(Color.black);
                    g.drawString("*", cur, 230);
                    cur+= space;
                }
                for(String sub: rfc1) {
                    g.setColor(Color.black);
                    if(sub.length()>orfSize) {
                        g.setColor(Color.orange);}
                    for(char c: sub.toCharArray()) {
                        g.drawString(Character.toString(c), cur, 230);
                        cur+= space;
                    }
                    if(!sub.equals(rfc1[rfc1.length-1]) || rfC1.endsWith("*"))
                    g.drawString("*", cur, 230);
                    cur+= space;
                }
                cur = (int)(letterWidth*3.5);
                if(rfC2.startsWith("*")){
                    g.setColor(Color.black);
                    g.drawString("*", cur, 260);
                    cur+= space;
                }
                for(String sub: rfc2) {
                    g.setColor(Color.black);
                    if(sub.length()>orfSize) {
                        g.setColor(Color.orange);}
                    for(char c: sub.toCharArray()) {
                        g.drawString(Character.toString(c), cur, 260);
                        cur+= space;
                    }
                    if(!sub.equals(rfc2[rfc2.length-1]) || rfC2.endsWith("*"))
                    g.drawString("*", cur, 260);
                    cur+= space;
                }

                //set the width of the frame to the width of the longest sequence
                rfArea.width = (int)seqWidth;
                rfDrawingPane.setPreferredSize(rfArea);
                rfDrawingPane.revalidate();
            }
        }
    }

    /**
     * Method for updating the header comboBox with headers from the loaded file
     */
    private void updateSeqComboBox() {
        if(!newFile) {
            headers = fastaLooper.getHeaderList();
            sequenceBox.setPreferredSize(new Dimension(40, 20));
            sequenceBox.setMaximumSize(new Dimension(40, 20));
            sequenceBox.setPrototypeDisplayValue("XXXX");
            for (String item : headers) {
                sequenceBox.addItem(item);
            }
            newFile = false;
            mainFrame.pack();
        }
    }

    /**
     * Method for updating the comboBox with the headers currently in the database
     */
    private void updateDbSeqComboBox() {
        dbSeqBox.setPreferredSize(new Dimension(60,20));
        dbSeqBox.setMaximumSize(new Dimension(60,20));
        dbSeqBox.setPrototypeDisplayValue("XXXXXXXXX");
        Database db = new Database();
        db.fetchHeaders();
        ArrayList<String> headers = db.getFastaHeaders();

        for(String header: headers) {
            dbSeqBox.addItem(header);
        }
        mainFrame.pack();
    }

    /**
     * Method for updating the comboBox orfBox with orfs found by the app
     */
    private void updateOrfBox() {
        orfBox.setPreferredSize(new Dimension(60,20));
        orfBox.setMaximumSize(new Dimension(60,20));
        orfBox.setPrototypeDisplayValue("XXXXXXXXXX");

        for(OrfProperty prop: orfProps) {
            orfBox.addItem(prop.getOrfName());
        }
        mainFrame.pack();
    }

    /**
     * Method for updating the slider when value is set in orfTextField
     * @param orfSize: minimum ORF size set by user
     */
    private void updateOrfSlider(int orfSize){
        orfSlider.setValue(orfSize);
    }

    /**
     * Listener for the slider, getting the minimum ORF size value
     */
    private class orfSliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                orfSize = source.getValue();
                orfSizeField.setText(Integer.toString(orfSize));
            }
        }
    }

    /**
     * Listener for te TextField orfTextField, orfSlider is updated
     */
    private class orfSizeFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String val = orfSizeField.getText();
            orfSize = Integer.parseInt(val);
            updateOrfSlider(orfSize);
        }
    }

    /**
     * Listener for the TextField where a path may be typed of pasted
     */
    private class FileTextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            path = fileTextField.getText();
            fileTextField.isEnabled();
            selectedFile = new File(path);
            pathPasted = true;
        }
    }

    /**
     * Listener for the Button browseButton, a File object is obtained from te loaded file
     */
    private class BrowseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fileChooser = new JFileChooser();
            fileVal = fileChooser.showOpenDialog(mainFrame);
            selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
            fileTextField.setText(path);
            //enable openbutton
        }
    }

    /**
     * Listener for the Button openButton, a FastaLooper object is initialized and SeqComboBox is updated
     */
    private class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (fileVal == JFileChooser.APPROVE_OPTION || pathPasted) {
                if (openedFiles.isEmpty() || !path.equals(openedFiles.get(openedFiles.size() - 1))) {
                    fastaLooper = new FastaLooper(selectedFile);
                    updateSeqComboBox();
                    openedFiles.add(path);
                    newFile = true;
                    fromDb = false;
                    if (pathPasted) {
                        pathPasted = false;
                    }
                }
            }//code voor open sequentie uit database
        }
    }

    /**
     * Listener for the analyzeButton, dnasequence analysis is initialized
     */
    public class AnalyzeButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            //check if a orf size is given by the user, if not default is assigned
            if(orfSize == 0) {
                orfSize = 30;
            }
            //if the sequence is selected from the database, the dna sequence is passed on to the class
            //else the file is passed on to the class
            if(!fromDb) {
                int headerIndex = headers.indexOf(sequenceChoice);
                fastaLooper.analyze(sequenceChoice, headerIndex, orfSize, false);}
            else {
                Database db = new Database();
                db.getFromDatabase(dbSeqChoice);
                dnaSeq = db.getDna();
                fastaLooper = new FastaLooper(dnaSeq);
                int headerIndex = 0;
                fastaLooper.analyze(dbSeqChoice, headerIndex, orfSize, true);
            }
            //get String[] dnaseqs with template and complementary
            dnaSeqs = fastaLooper.getDNAseqs();
            rfSeqs = fastaLooper.getRfSeqs();
            orfProps = fastaLooper.getOrfProps();
            rfOrfs = fastaLooper.getRfOrfs();

            if(orfProps.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                        "No ORFs were found for this sequence.");
            }
            else{
                updateOrfBox();
            }

            rfDrawingPane.repaint();
        }
    }

    private class BlastButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!orfProps.isEmpty() && dnaSeqs.length!=0) {
                Database db = new Database();
                dnaSeq = dnaSeqs[0];
                db.insertIntoDatabase(sequenceChoice, dnaSeq, orfProps);
                updateDbSeqComboBox();
            }
        }
    }

    private class SequenceBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sequenceBox = (JComboBox) e.getSource();
            sequenceBox.setEnabled(true);
            sequenceChoice = (String) sequenceBox.getSelectedItem();
            int index = sequenceBox.getSelectedIndex();
            System.out.println("selected index"+ index);
        }
    }

    private class DbSeqBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dbSeqBox = (JComboBox) e.getSource();
            dbSeqBox.setEnabled(true);
            dbSeqChoice = (String) dbSeqBox.getSelectedItem();
            fromDb = true;

        }
    }

    private class OrfBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            orfBox = (JComboBox) e.getSource();
            orfBox.setEnabled(true);
            orfChoice = (String) orfBox.getSelectedItem();
            orfChoiceIndx = orfBox.getSelectedIndex();
            orfDrawingPane.repaint();
        }
    }

}

