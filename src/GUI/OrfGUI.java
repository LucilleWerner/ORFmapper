package GUI;

/**
 * Created by Heleen on 27-3-2017.
 */
/**
 * Created by Heleen on 23-3-2017.
 */

import RF_ORF_FileReader.FastaLooper;
import RF_ORF_FileReader.OrfProperty;

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

    private JFrame mainFrame;
    private JTextField fileTextField;
    private JTextField orfSizeField;
    private JFileChooser fileChooser;
    private JButton browseButton;
    private JButton openButton;
    private JButton analyzeButton;
    private JButton blastButton;
    private JButton saveButton;
    private JLabel pathLabel;
    private JLabel selectseqLabel;
    private JLabel selectdbLabel;
    private JLabel selectORFlenLabel;
    private JLabel orfPanelLabel;
    private JLabel selectORFLabel;
    private JLabel saveLabel;
    private JLabel resultsLabel;
    private JSlider orfSlider;
    private JComboBox sequenceBox;
    private JComboBox dbSeqBox;
    private JComboBox orfBox;
    private JScrollPane rfscrollpane;
    private JScrollBar rfScrollbar;
    private JPanel rfdrawingPane;
    private JPanel orfPanel;
    private JPanel resultsPanel;
    private String path;
    private String sequenceChoice;
    private String dbSeqChoice;
    private String orfChoice;
    private String[] dnaSeqs;
    private String[] rfSeqs;
    private ArrayList<String> headers;
    private ArrayList<OrfProperty> orfProps;
    private ArrayList<String[]> rfOrfs;
    private Dimension rfArea;
    private int orfSize;
    private int fileVal;
    private File selectedFile;
    private FastaLooper fastaLooper;


    public OrfGUI() {
        prepareGui();
    }

    public static void main(String[] args) {
        OrfGUI newGui = new OrfGUI();
        newGui.showEvents();

    }


    private void prepareGui() {

        mainFrame = new JFrame("ORF finder");
        //mainFrame.setSize(700,700);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

        Container pane = mainFrame.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);


        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,10,10,20);

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
        //c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(selectdbLabel, c);

        dbSeqBox = new JComboBox();
        //c.anchor = GridBagConstraints.CENTER;
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

        //rfdrawingPane = new JPanel(new BorderLayout());
        //rfdrawingPane.setBackground(Color.WHITE);
        rfdrawingPane = new RFDrawingPane();
        rfdrawingPane.setBackground(Color.WHITE);
        rfdrawingPane.setAutoscrolls(true);
        rfscrollpane = new JScrollPane(rfdrawingPane);
        rfscrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 1;
        c.gridy = 3;
        //c.weightx = 1.0;
        c.gridwidth = 6;
        c.ipadx = 730;
        c.ipady = 300;
        pane.add(rfscrollpane, c);

        orfPanelLabel = new JLabel("ORF sequence");
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 4;
        //c.weightx = 0;
        c.gridwidth = 1;
        c.ipadx = 0;
        c.ipady = 0;
        pane.add(orfPanelLabel, c);

        orfPanel = new JPanel();
        c.anchor = GridBagConstraints.LINE_END;
        orfPanel.setBackground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 4;
        //c.weightx = 1.0;
        c.gridwidth = 6;
        c.ipadx = 730;
        c.ipady = 60;
        pane.add(orfPanel, c);

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
        rfdrawingPane.addMouseListener(new RFdrawingPaneListener());


    }

    public class RFdrawingPaneListener implements MouseListener {
        public void mouseEntered(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e) {
            rfdrawingPane.repaint();
        }


    }

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
                rfdrawingPane.removeAll();

                Font font = new Font("Consolas", Font.BOLD, 20);
                FontMetrics metrics = g.getFontMetrics(font);

                double letterWidth = metrics.getStringBounds("A", g).getWidth();
                double seqWidth = metrics.getStringBounds(template, g).getWidth();
                double seqHeight = metrics.getStringBounds(template,g).getHeight();

                g.setFont(font);

                g.setColor(Color.black);
                g.drawString(template, 0, (int)((height/2)-seqHeight));
                g.drawString(complementary, 0, (int)((height/2)+seqHeight));

                int space = (int)(letterWidth*3);
                int cur = (int)(letterWidth*1.5);
                if(rfT0.startsWith("*")){
                    g.drawString("*", cur, 100);
                    cur+= space;
                }
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

                rfArea.width = (int)seqWidth;
                rfdrawingPane.setPreferredSize(rfArea);
                rfdrawingPane.revalidate();
            }
        }
    }


    private void updateSeqComboBox() {
        headers = fastaLooper.getHeaderList();
        sequenceBox.setPreferredSize(new Dimension(40,20));
        sequenceBox.setMaximumSize(new Dimension(40,20));
        sequenceBox.setPrototypeDisplayValue("XXXX");
        for(String item: headers) {
            sequenceBox.addItem(item);
        }
        //mainFrame.setPreferredSize(mainFrame.getPreferredSize());
        //sequenceBox.setPreferredSize(new Dimension(50,20));
        //sequenceBox.setModel(new DefaultComboBoxModel(headers.toArray()));
        //sequenceBox.setMaximumSize(new Dimension(50,20));
        //mainFrame.setResizable(false);

        mainFrame.pack();
    }

    private void updateOrfBox() {
        orfBox.setPreferredSize(new Dimension(60,20));
        orfBox.setMaximumSize(new Dimension(60,20));
        orfBox.setPrototypeDisplayValue("XXXXXXXXXX");
        ArrayList<String> orfNames = new ArrayList<>();
        String rF;
        for(OrfProperty prop: orfProps) {
            int rf = prop.RF;
            switch (rf) {
                case 0: rF = "Rft0";
                    break;
                case 1: rF = "Rft1";
                    break;
                case 2: rF = "Rft2";
                    break;
                case 3: rF = "Rfc0";
                    break;
                case 4: rF = "Rfc1";
                    break;
                case 5: rF = "Rfc2";
                    break;
                default: rF = "Rf";
            }
            //String name = sequenceChoice.substring(1).split(" ")[0]+"_"+rF;
            String name = sequenceChoice.split("_")[0].substring(1)+"_"+rF;
            System.out.println(name);
            int i = 1;
            while (orfNames.contains(name)) {
                name += "_"+Integer.toString(i);
                i++;}
            orfNames.add(name);
            orfBox.addItem(name);

        mainFrame.pack();
        }
    }

    private void updateOrfSlider(int orfSize){
        orfSlider.setValue(orfSize);
    }

    private class orfSliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                orfSize = source.getValue();
                orfSizeField.setText(Integer.toString(orfSize));
            }
        }
    }

    private class orfSizeFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String val = orfSizeField.getText();
            orfSize = Integer.parseInt(val);
            updateOrfSlider(orfSize);
        }
    }

    private class FileTextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String path = fileTextField.getText();
            fileTextField.isEnabled();
            selectedFile = new File(path);
            //enable openbutton
        }
    }
    private class BrowseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fileChooser = new JFileChooser();
            fileVal = fileChooser.showOpenDialog(mainFrame);
            selectedFile = fileChooser.getSelectedFile();

            fileTextField.setText(selectedFile.getAbsolutePath());
            //enable openbutton
        }
    }

    private class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (fileVal == JFileChooser.APPROVE_OPTION || fileTextField.isEnabled()) {
                fastaLooper = new FastaLooper(selectedFile);
                updateSeqComboBox();
            }
            //code voor open sequentie uit database
        }
    }

    public class AnalyzeButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(orfSize == 0) {
                orfSize = 30;
            }

            System.out.println("Orf size: "+ orfSize);
            int headerIndex = headers.indexOf(sequenceChoice);
            System.out.println("headerindex is " +headerIndex);
            fastaLooper.analyze(headerIndex, orfSize);
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

            rfdrawingPane.repaint();
        }
    }

    private class BlastButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //dostuff
        }
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //dostuff
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

        }
    }

    private class OrfBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            orfBox = (JComboBox) e.getSource();
            orfBox.setEnabled(true);
            orfChoice = (String) orfBox.getSelectedItem();
        }
    }

}

