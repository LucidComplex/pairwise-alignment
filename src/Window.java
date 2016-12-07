import fasta.Fasta;
import fasta.FastaFormatter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

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
                        fastaInput.setText("");
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
                String input = fastaInput.getText();
                String[] inputs = input.split("\n\n");
                System.out.println(Arrays.toString(inputs));
                if (inputs.length > 2) {
                    JOptionPane.showMessageDialog(mainPanel, "Too many inputs");
                    return;
                }
                if (nucleotideRadioButton.isSelected()) {
                    SimpleAligner aligner = new SimpleAligner(
                            (int) matchScore.getValue(), (int) mismatchScore.getValue(), (int) gapScore.getValue());
                    Result result = aligner.align(FastaFormatter.format(
                            Fasta.NUCLEOTIDE, inputs[0]), FastaFormatter.format(Fasta.NUCLEOTIDE, inputs[1]));
                    ResultWindow.show(buildOutputString(result));
                }
                if (proteinRadioButton.isSelected()) {
                    try {
                        ScoringMatrix mat = new ScoringMatrix((String) scoringScheme.getSelectedItem());
                        ProteinAligner aligner = new ProteinAligner(mat);
                        Result result = aligner.align(FastaFormatter.format(
                                Fasta.PROTEIN, inputs[0]), FastaFormatter.format(Fasta.PROTEIN, inputs[1]));
                        ResultWindow.show(buildOutputString(result));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private String buildOutputString(Result result) {
        StringBuilder builder = new StringBuilder();
        builder.append("Pairwise Sequence Alignment ver. 1.0 by Eli Tan\n");
        builder.append("Run date: " + new Date(System.currentTimeMillis())+ "\n\n");
        builder.append("Submitted sequences:\n");
        builder.append(result.fastaA.getId() + "\n");
        for (int i = 0; i < result.fastaA.length(); i++) {
            if (i % 10 == 0 && i != 0) {
                builder.append(" ");
            }
            builder.append(result.fastaA.getSequence().charAt(i));
        }
        builder.append("\n");
        builder.append(result.fastaB.getId() + "\n");
        for (int i = 0; i < result.fastaB.length(); i++) {
            if (i % 10 == 0 && i != 0) {
                builder.append(" ");
            }
            builder.append(result.fastaB.getSequence().charAt(i));
        }
        builder.append("\n\n");
        builder.append("Lengths:\n");
        builder.append(result.fastaA.getId().substring(1) + ": " + result.fastaA.length() + "\n");
        builder.append(result.fastaB.getId().substring(1) + ": " + result.fastaB.length() + "\n");
        builder.append("\n");
        builder.append("Frequencies: \n");
        builder.append(result.fastaA.getId().substring(1) + ": \n");
        for (Map.Entry<Character, Integer> e : result.fastaA.getFrequencies().entrySet()) {
            builder.append(e.getKey() + " = " + e.getValue() + "\n");
        }
        builder.append("\n");
        builder.append(result.fastaB.getId().substring(1) + ": \n");
        for (Map.Entry<Character, Integer> e : result.fastaB.getFrequencies().entrySet()) {
            builder.append(e.getKey() + " = " + e.getValue() + "\n");
        }
        builder.append("\n");

        builder.append("Optimal Alignment: \n");
        for (Alignment alignment : result.alignments) {
            builder.append(alignment.toString() + "\n\n");
        }
        builder.append("Score: " + result.score);
        return builder.toString();
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
