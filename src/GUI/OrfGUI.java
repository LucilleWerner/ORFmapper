package GUI;

/**
 * Created by Heleen on 27-3-2017.
 */
/**
 * Created by Heleen on 23-3-2017.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/**
 * Created by Heleen on 22-3-2017.
 */
public class OrfGUI {

    private JFrame mainFrame;
    private JTextField fileTextField;
    private JFileChooser fileChooser;
    private JButton browseButton;
    private JButton openButton;
    private JButton blastButton;
    private JButton saveButton;
    private JLabel pathLabel;
    private JLabel selectseqLabel;
    private JLabel selectdbLabel;
    private JLabel orfPanelLabel;
    private JLabel selectORFLabel;
    private JLabel saveLabel;
    private JLabel resultsLabel;
    private JComboBox sequenceBox;
    private JComboBox dbSeqBox;
    private JComboBox orfBox;
    private JPanel rfPanel;
    private JPanel orfPanel;
    private JPanel resultsPanel;
    private String path;
    private String sequenceChoice;
    private String dbSeqChoice;
    private String orfChoice;
    private int fileVal;

    public OrfGUI() {
        prepareGui();
    }

    public static void main(String[] args) {
        OrfGUI newGui = new OrfGUI();
        newGui.prepareGui();
        newGui.showEvents();
    }


    private void prepareGui() {

        //initializing JFrame mainFrame, setting title, size, layout type and method for terminating program on closing
        mainFrame = new JFrame("ORFfinder");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

        //initializing JTextField fileTextField and setting dimensions
        fileTextField = new JTextField();
        fileTextField.setPreferredSize(new Dimension(420, 40));

        //initializing JComboBox sequenceBox, dbSeqBox and orfBox with dimensions
        sequenceBox = new JComboBox();
        sequenceBox.setPreferredSize(new Dimension(200, 20));
        dbSeqBox = new JComboBox();
        dbSeqBox.setPreferredSize(new Dimension(200, 20));
        orfBox = new JComboBox();
        orfBox.setPreferredSize(new Dimension(200,20));

        //initializing JPanel InfoPanel with background color, layout type and dimensions
        rfPanel = new JPanel();
        rfPanel.setBackground(Color.DARK_GRAY);
        rfPanel.setLayout(new FlowLayout());
        rfPanel.setPreferredSize(new Dimension(460, 280));

        //initializing JPanels orfPanel and resultsPanel with color and dimensions
        orfPanel = new JPanel();
        orfPanel.setBackground(Color.DARK_GRAY);
        orfPanel.setPreferredSize(new Dimension(460, 100));
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridBagLayout());
        resultsPanel.setPreferredSize(new Dimension(460, 100));

        //initializing JButtons browseButton, openButton, blastButton, saveButton and setting dimensions
        browseButton = new JButton("browse");
        browseButton.setPreferredSize(new Dimension(100,30));
        openButton = new JButton("open");
        openButton.setPreferredSize(new Dimension(100,30));
        blastButton = new JButton("BLAST ORF");
        blastButton.setPreferredSize(new Dimension(100,30));
        saveButton = new JButton("save");
        saveButton.setPreferredSize(new Dimension(100,30));


        //initializing JLabels pathLabel, selectdbLabel, seqPanelLabel, selectORFLabel,
        // saveLabel, resultsLabel and setting locations
        pathLabel = new JLabel("file path", JLabel.LEFT);
        pathLabel.setLocation(20,20);
        selectseqLabel = new JLabel("select sequence", JLabel.LEFT);
        selectseqLabel.setLocation(20,40);
        selectdbLabel = new JLabel("from database", JLabel.LEFT);
        selectdbLabel.setLocation(20,60);
        orfPanelLabel = new JLabel("ORF sequence", JLabel.LEFT);
        orfPanelLabel.setLocation(20, 420);
        selectORFLabel = new JLabel("select ORF", JLabel.LEFT);
        selectORFLabel.setLocation(20, 460);
        saveLabel = new JLabel("save to database", JLabel.RIGHT);
        saveLabel.setLocation(500, 460);
        resultsLabel = new JLabel("BLAST results", JLabel.LEFT);
        resultsLabel.setLocation(20, 500 );


        //add all components to mainFrame
        mainFrame.add(pathLabel);
        mainFrame.add(fileTextField);
        mainFrame.add(browseButton);
        mainFrame.add(sequenceBox);
        mainFrame.add(openButton);
        mainFrame.add(selectdbLabel);
        mainFrame.add(dbSeqBox);
        mainFrame.add(rfPanel);
        mainFrame.add(orfPanelLabel);
        mainFrame.add(orfPanel);
        mainFrame.add(selectORFLabel);
        mainFrame.add(orfBox);
        mainFrame.add(blastButton);
        mainFrame.add(saveLabel);
        mainFrame.add(saveButton);
        mainFrame.add(resultsLabel);
        mainFrame.add(resultsPanel);
        mainFrame.setVisible(true);

    }

    private void showEvents() {
        //initializing action listeners
        fileTextField.addActionListener(new FileTextFieldListener());
        browseButton.addActionListener(new BrowseButtonListener());
        openButton.addActionListener(new OpenButtonListener());
        browseButton.addActionListener(new BrowseButtonListener());
        blastButton.addActionListener(new BlastButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        sequenceBox.addActionListener(new SequenceBoxListener());
        dbSeqBox.addActionListener(new DbSeqBoxListener());
        orfBox.addActionListener(new OrfBoxListener());
        //mainFrame.setVisible(true);

    }

    private class FileTextFieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String path = fileTextField.getText();
            File selectedFile = new File(path);
        }
    }
    private class BrowseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            File selectedFile;
            fileChooser = new JFileChooser();
            fileVal = fileChooser.showOpenDialog(mainFrame);
            selectedFile = fileChooser.getSelectedFile();
            fileTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    private class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //dostuff
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

