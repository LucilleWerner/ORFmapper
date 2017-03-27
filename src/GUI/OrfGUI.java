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
    private JPanel sidePane;
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
        //newGui.prepareGui();
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
        c.gridx = 0;
        c.gridy = 0;
        pane.add(pathLabel, c);

        fileTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 0;
        //c.weightx = 1.0;
        c.gridwidth = 4;
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

        selectseqLabel = new JLabel("select sequence");
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(selectseqLabel, c);

        sequenceBox = new JComboBox();
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        c.ipadx = 200;
        pane.add(sequenceBox, c);

        openButton = new JButton("open");
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 6;
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipadx = 0;
        pane.add(openButton, c);

        selectdbLabel = new JLabel("from database");
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(selectdbLabel, c);

        dbSeqBox = new JComboBox();
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 4;
        c.ipadx = 200;
        pane.add(dbSeqBox, c);

        rfPanel = new JPanel();
        c.anchor = GridBagConstraints.LINE_END;
        rfPanel.setBackground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 3;
        //c.weightx = 1.0;
        c.gridwidth = 6;
        c.ipadx = 600;
        c.ipady = 300;
        pane.add(rfPanel, c);

        orfPanelLabel = new JLabel("ORF sequence");
        c.anchor = GridBagConstraints.CENTER;
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
        c.ipadx = 600;
        c.ipady = 60;
        pane.add(orfPanel, c);

        selectORFLabel = new JLabel("select ORF");
        c.anchor = GridBagConstraints.CENTER;
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
        c.gridx = 1;
        c.gridy = 6;
        //c.weightx = 1.0;
        c.gridwidth = 6;
        c.ipadx = 600;
        c.ipady = 100;
        pane.add(resultsPanel, c);

        mainFrame.pack();
        mainFrame.setVisible(true);


      /*  //initializing JFrame mainFrame, setting title, size, layout type and method for terminating program on closing
        mainFrame = new JFrame("ORFfinder");
        mainFrame.setSize(700, 650);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

        //initializing JTextField fileTextField and setting dimensions
        fileTextField = new JTextField();
        fileTextField.setPreferredSize(new Dimension(420,30));

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
        resultsPanel.setBackground(Color.DARK_GRAY);
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
        pathLabel.setLocation(0,20);
        selectseqLabel = new JLabel("select sequence", JLabel.LEFT);
        selectseqLabel.setLocation(0,40);
        selectdbLabel = new JLabel("from database", JLabel.LEFT);
        selectdbLabel.setLocation(0,60);
        orfPanelLabel = new JLabel("ORF sequence", JLabel.LEFT);
        orfPanelLabel.setLocation(0, 420);
        selectORFLabel = new JLabel("select ORF", JLabel.LEFT);
        selectORFLabel.setLocation(0, 460);
        saveLabel = new JLabel("save to database", JLabel.RIGHT);
        saveLabel.setLocation(500, 460);
        resultsLabel = new JLabel("BLAST results", JLabel.LEFT);
        resultsLabel.setLocation(0, 500 );


        *//*sidePane = new JPanel();
        sidePane.setLayout(new GridBagLayout());
        sidePane.setPreferredSize(new Dimension(60,580));
        sidePane.setLocation(0,0);
        GridBagConstraints constraints = new GridBagConstraints();
        //constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        sidePane.add(pathLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 50;
        sidePane.add(selectseqLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 100;
        sidePane.add(selectdbLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 400;
        sidePane.add(orfPanelLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 450;
        sidePane.add(selectORFLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 500;
        sidePane.add(resultsLabel, constraints);*//*


        //add all components to mainFrame
        mainFrame.add(pathLabel);
        //mainFrame.add(sidePane);
        mainFrame.add(fileTextField);
        mainFrame.add(browseButton);
        mainFrame.add(sequenceBox);
        mainFrame.add(selectdbLabel);
        mainFrame.add(dbSeqBox);
        mainFrame.add(openButton);
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
        mainFrame.setVisible(true);*/

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

