import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by tan on 12/5/16.
 */
public class Window {
    private JButton uploadFASTAButton;
    private JTextArea fastaInput;
    private JButton clearButton;
    private JRadioButton nucleotideRadioButton;
    private JRadioButton proteinRadioButton;
    private JComboBox comboBox1;
    private JButton alignButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
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
                fileChooser.showOpenDialog(uploadFASTAButton);
            }
        });
    }
}
