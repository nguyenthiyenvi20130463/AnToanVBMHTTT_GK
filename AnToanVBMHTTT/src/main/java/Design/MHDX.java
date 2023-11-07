package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

public class MHDX extends JFrame {
    JRadioButton radioButtonMHDX, radioButtonMHBDX, radioButtonCKDT, radioButtonHash;
    ButtonGroup group;
    JPanel panel, panelMH, panelInput, panelTextvaFile, panelSelectModeAndkeySize,panelEnterKey, panelOutput, panelClose ;
    JLabel  labelSelectMode, labelKeySize, labelEnterSize;
    JTextField fieldInput, fieldEnterkey;
    JButton  buttonText, buttonFile, buttonEncrypt, buttonDecrypt, butttonFileUpload, buttonClose;
    JTextArea areaOutput;
    JComboBox comboBoxSelectModeMHDX,comboBoxSelectModeMHBDX,comboBoxSelectModeCKDT, comboBoxSelectModeHash, comboBoxkeySize;
    String[] selectModeMHDXString ={"Hill","Affine","AES","Camellia","Twofish"};
    String[] selectModeMHBDXString = {"ECDSA"};
    String[] selectModeCKDTString ={"SHA-256"};
    String[] selectModeHashString ={"RIPEMD256","MD5"};
    String[] selectkeySizeString = {"128","192"};

    public MHDX(){
        this.setTitle("Eymmetric encryption");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Encrypt();


        this.setSize(600,600);

        //this.pack();
        this.setVisible(true);
    }
    public void Encrypt() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // RadionButton MHDX và MHBDX
        panelMH = new JPanel();
        panelMH.setLayout(new FlowLayout(FlowLayout.CENTER));
        radioButtonMHDX = new JRadioButton("MHDX");
        radioButtonMHBDX = new JRadioButton("MHBDX");
        radioButtonCKDT = new JRadioButton("CKDT");
        radioButtonHash = new JRadioButton("Hash");

        group = new ButtonGroup();
        group.add(radioButtonMHDX);
        group.add(radioButtonMHBDX);
        group.add(radioButtonCKDT);
        group.add(radioButtonHash);
        panelMH.add(radioButtonMHDX);
        panelMH.add(radioButtonMHBDX);
        panelMH.add(radioButtonCKDT);
        panelMH.add(radioButtonHash);

        // Input
        panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
//        labelInput = new JLabel("Input:");
        panelTextvaFile = new JPanel();
        panelTextvaFile.setLayout(new GridLayout(1, 2));
        buttonText = new JButton("Text");
        //buttonText.setPreferredSize(new Dimension(10,20));
        buttonFile = new JButton("File");
        //buttonFile.setPreferredSize(new Dimension(10,20));
        panelTextvaFile.add(buttonText);
        panelTextvaFile.add(buttonFile);
        fieldInput = new JTextField(50);
        JLabel labelFilePath = new JLabel(); // Thêm một JLabel để hiển thị đường dẫn
        JPanel panelfileUpload = new JPanel();
        butttonFileUpload = new JButton("Click to Upload File");
        panelfileUpload.add(butttonFileUpload);

        panelInput.add(panelTextvaFile);
        panelInput.add(fieldInput);
        panelInput.add(labelFilePath);
        panelInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Input"));

        // SelectMode and KeySize
        panelSelectModeAndkeySize = new JPanel();
        panelSelectModeAndkeySize.setLayout(new GridLayout(4, 1));
        labelSelectMode = new JLabel("Select Mode: ", JLabel.LEFT);
        JPanel panelCard = new JPanel();
        comboBoxSelectModeMHDX = new JComboBox(selectModeMHDXString);
        comboBoxSelectModeMHBDX = new JComboBox(selectModeMHBDXString);
        comboBoxSelectModeCKDT = new JComboBox(selectModeCKDTString);
        comboBoxSelectModeHash = new JComboBox(selectModeHashString);
        panelCard.add(comboBoxSelectModeMHDX);
        panelCard.add(comboBoxSelectModeMHBDX);
        panelCard.add(comboBoxSelectModeCKDT);
        panelCard.add(comboBoxSelectModeHash);
        labelKeySize = new JLabel("Enter Size in Bits: ", JLabel.LEFT);
        comboBoxkeySize = new JComboBox(selectkeySizeString);

        panelSelectModeAndkeySize.add(labelSelectMode);
        panelSelectModeAndkeySize.add(panelCard);
        panelSelectModeAndkeySize.add(labelKeySize);
        panelSelectModeAndkeySize.add(comboBoxkeySize);

        // Enter key
        panelEnterKey = new JPanel();
        panelEnterKey.setLayout(new GridLayout(3,1));
        labelEnterSize = new JLabel("Enter Secret Key: ", JLabel.LEFT);
        fieldEnterkey = new JTextField(30);
        panelEnterKey.add(labelEnterSize);
        panelEnterKey.add(fieldEnterkey);

        JPanel panelbutton = new JPanel();
        panelbutton.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonEncrypt = new JButton("Encrypt");
        buttonDecrypt = new JButton("Decrypt");
        panelbutton.add(buttonEncrypt);
        panelbutton.add(buttonDecrypt);
        panelEnterKey.add(panelbutton);

        // Encrypt and Decrypt Output
        panelOutput = new JPanel();
        panelOutput.setLayout(new BoxLayout(panelOutput, BoxLayout.Y_AXIS));

        areaOutput = new JTextArea();
        areaOutput.setEditable(false);
        areaOutput.setPreferredSize(new Dimension(200, 30));
        JScrollPane scrollPane = new JScrollPane(areaOutput);
        panelOutput.add(scrollPane, BorderLayout.WEST);

        panelOutput.add(areaOutput);
        panelOutput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Output"));

        panelClose = new JPanel();
        panelClose.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonClose = new JButton("Close");
        panelClose.add(buttonClose);


        panel.add(panelMH);
        panel.add(panelInput);
        panel.add(panelSelectModeAndkeySize);
        panel.add(panelEnterKey);
        panel.add(panelOutput);
        panel.add(panelClose);

        comboBoxSelectModeMHBDX.setVisible(false);
        comboBoxSelectModeCKDT.setVisible(false);
        comboBoxSelectModeHash.setVisible(false);

        // setAction Radiobuton MHDX
        radioButtonMHDX.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    comboBoxSelectModeMHDX.setVisible(true);
                    comboBoxSelectModeMHBDX.setVisible(false);
                    comboBoxSelectModeCKDT.setVisible(false);
                    comboBoxSelectModeHash.setVisible(false);
                }
            }
        });
        // setAction Radiobuton MHBDX
        radioButtonMHBDX.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    comboBoxSelectModeMHDX.setVisible(false);
                    comboBoxSelectModeMHBDX.setVisible(true);
                    comboBoxSelectModeCKDT.setVisible(false);
                    comboBoxSelectModeHash.setVisible(false);
                }
            }
        });

        // setAction RadioButton CKDT
        radioButtonCKDT.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    comboBoxSelectModeMHDX.setVisible(false);
                    comboBoxSelectModeMHBDX.setVisible(false);
                    comboBoxSelectModeCKDT.setVisible(true);
                    comboBoxSelectModeHash.setVisible(false);
                }
            }
        });
        // setAction RadioButton Hash
        radioButtonHash.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    comboBoxSelectModeMHDX.setVisible(false);
                    comboBoxSelectModeMHBDX.setVisible(false);
                    comboBoxSelectModeCKDT.setVisible(false);
                    comboBoxSelectModeHash.setVisible(true);
                }
            }
        });
        // setAction Button Text
        buttonText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldInput.setVisible(true);
                panelfileUpload.setVisible(false);
                labelFilePath.setText(""); // Xóa đường dẫn khi chuyển về TextField
            }
        });
        // setAction ButtonFile
       buttonFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fieldInput.setVisible(false);
                panelfileUpload.setVisible(true);
            }
        });

        panelInput.add(panelfileUpload);
        panelfileUpload.setVisible(false);

        butttonFileUpload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(panel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    labelFilePath.setText("File to upload: " + file.getAbsolutePath());
                    // Thực hiện upload file lên server tại đây.
                }
            }
        });
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.getContentPane().add(panel);
    }
    public static void main(String[] args) {
        new MHDX();
    }

}
