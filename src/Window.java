import fasta.Fasta;
import fasta.FastaFormatter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

/**
 * Created by tan on 12/5/16.
 */
public class Window {
    private JButton uploadFASTAButton;
    private JTextArea fastaInput;
    private JButton clearButton;
    private JRadioButton nucleotideRadioButton;
    private JRadioButton proteinRadioButton;
    private JComboBox scoringScheme;
    private JButton alignButton;
    private JPanel mainPanel;
    private JButton quitButton;
    private JSpinner matchScore;
    private JSpinner mismatchScore;
    private JSpinner gapScore;
    private JLabel scoringSchemeLabel;
    private JButton resetButton;
    private ButtonGroup inputType;

    public Window() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fastaInput.setText("");
            }
        });
        uploadFASTAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getName().endsWith(".fasta");
                    }

                    @Override
                    public String getDescription() {
                        return "FASTA File";
                    }
                });
                int retVal = fileChooser.showOpenDialog(mainPanel);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File fastaFile = fileChooser.getSelectedFile();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(fastaFile));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            fastaInput.append(line + "\n");
                        }
                    } catch (java.io.IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        nucleotideRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoringScheme.setEnabled(false);
                scoringSchemeLabel.setEnabled(false);
            }
        });

        proteinRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoringSchemeLabel.setEnabled(true);
                scoringScheme.setEnabled(true);
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoringScheme.setSelectedIndex(0);
                mismatchScore.setValue(0);
                gapScore.setValue(-1);
                matchScore.setValue(1);
                inputType.clearSelection();
            }
        });
        alignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nucleotideRadioButton.isSelected()) {
                    SimpleAligner aligner = new SimpleAligner(
                            (int) matchScore.getValue(), (int) mismatchScore.getValue(), (int) gapScore.getValue());
                    String input = fastaInput.getText();
                    String[] inputs = input.split("\n\n");
                    System.out.println(Arrays.toString(inputs));
                    if (inputs.length > 2) {
                        JOptionPane.showMessageDialog(mainPanel, "Too many inputs");
                    } else {
                        int score = aligner.align(FastaFormatter.format(
                                Fasta.NUCLEOTIDE, inputs[0]), FastaFormatter.format(Fasta.NUCLEOTIDE, inputs[1]));
                        System.out.println(score);
                    }
                }
                if (proteinRadioButton.isSelected()) {

                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pairwise Sequence Alignment by Eli Tan");
        frame.setContentPane(new Window().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        matchScore = new JSpinner();
        mismatchScore = new JSpinner();
        gapScore = new JSpinner();
        matchScore.setValue(1);
        mismatchScore.setValue(0);
        gapScore.setValue(-1);

        String[] matrices = {"BLOSUM62", "PAM120"};
        scoringScheme = new JComboBox(matrices);
    }
}
