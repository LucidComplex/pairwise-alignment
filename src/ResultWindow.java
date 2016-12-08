import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ResultWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane resultsPane;
    private JButton saveButton;
    String result;

    public ResultWindow(String result) {
        this.result = result;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getName().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return "Text File";
                    }
                });
                int retVal = fileChooser.showSaveDialog(contentPane);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File fastaFile = fileChooser.getSelectedFile();
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fastaFile));
                        bufferedWriter.write(result);
                        bufferedWriter.close();
                    } catch (java.io.IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void show(String result) {
        ResultWindow dialog = new ResultWindow(result);
        dialog.resultsPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        dialog.resultsPane.setText(result);
        dialog.pack();
        dialog.setVisible(true);
    }
}
