package Design;

import CKDT.SHA256;
import Hash.MD5;
import Hash.RIPEMD256;
import Hash.SHA_1;
import MHDX.*;
import MHBDX.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;


public class UI extends JFrame {

    JMenuBar menuBar;
    JMenu menuEncoding;
    JMenuItem itemMHDX, itemMHBDX, itemCKDT, itemHash;
    //JRadioButton radioButtonMHDX, radioButtonMHBDX, radioButtonCKDT, radioButtonHash;
    ButtonGroup group;
    JPanel panel, panelInput, panelTextvaFile, panelSelectModeAndkeySize, panelEnterKey, panelOutput, panelClose;
    JLabel labelSelectMode, labelKeySize, labelEnterSize, labelPublicKey, labelPrivateKey, labelPlaintextEncrypt, labelPlaintextDecrypt, labelEnerPubOrPriEncryct, labelEnerPubOrPriDecryct, labelCipherTypeEncrypt, labelCipherTypeDecrypt, labelKeyTypeEn, labelKeyTypeDe;
    JTextField fieldInput, fieldEnterkey, fieldPublicKey, fieldPrivateKey, fieldPlaintextEncrypt, fieldPlaintextDecrypt, fieldEnerPubOrPriEncryct, fieldEnerPubOrPriDecryct;
    JButton buttonText, buttonFile, buttonEncrypt, buttonDecrypt, butttonFileUpload, buttonClose, buttonGenerateKey;
    JTextArea areaOutput;
    JComboBox comboBoxSelectModeMHDX, comboBoxSelectModeMHBDXEncrypt, comboBoxSelectModeMHBDXDecrypt, comboBoxSelectModeHash, comboBoxkeySize;
    String[] selectModeMHDXString = {"Hill", "Vigenere", "AES", "DES", "Twofish"};
    String[] selectModeMHBDXString = {"RSA", "RSA/ECB/PKCS1Padding"};
    //String[] selectModeMHBDXStringDecrypt = {"RSA"};

    String[] selectModeHashString = {"RIPEMD256", "MD5","SHA-1","SHA-256"};
    String[] selectkeySizeString = {"56", "128", "192"};
    String[] selectkeySizeRSA = {"512", "1024", "2048"};
    String[] keyType = {"PublicKey, PrivateKey"};
    boolean isTextMHDX = true;
    boolean isTextHash = true;
    File fileMHDX = null;
    File fileHash = null;
    File fileCKDT = null;
    public UI() {
        panel = new JPanel();
        this.setTitle("Encryption Algorithms");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        create();

        this.setSize(500, 600);

        //this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void create() {

        menuBar = new JMenuBar();
        menuEncoding = new JMenu("Encoding");
        itemMHDX = new JMenuItem("MHDX");
        itemMHBDX = new JMenuItem("MHBDX");
        itemCKDT = new JMenuItem("CKDT");
        itemHash = new JMenuItem("Hash");

        menuEncoding.add(itemMHDX);
        menuEncoding.add(itemMHBDX);
        menuEncoding.add(itemCKDT);
        menuEncoding.add(itemHash);
        menuBar.add(menuEncoding);
        this.setJMenuBar(menuBar);

        itemMHDX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                createMHDX();
                revalidate();
                repaint();
            }
        });
        itemMHBDX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                createMHBDX();
                revalidate();
                repaint();
            }
        });
        itemHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                createHash();
                revalidate();
                repaint();
            }
        });
        itemCKDT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                createCKDT();
                revalidate();
                repaint();
            }
        });

    }

    // Tao giao dien ma hoa doi xung
    public void createMHDX() {

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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
        panelSelectModeAndkeySize.setLayout(new GridLayout(3, 1));
        labelSelectMode = new JLabel("Select Mode: ", JLabel.LEFT);
        JPanel panelCard = new JPanel();
        comboBoxSelectModeMHDX = new JComboBox(selectModeMHDXString);
        comboBoxSelectModeMHBDXEncrypt = new JComboBox(selectModeMHBDXString);

        comboBoxSelectModeHash = new JComboBox(selectModeHashString);
        panelCard.add(comboBoxSelectModeMHDX);
        panelCard.add(comboBoxSelectModeMHBDXEncrypt);
//        panelCard.add(comboBoxSelectModeCKDT);
        panelCard.add(comboBoxSelectModeHash);
//        labelKeySize = new JLabel("Enter Size in Bits: ", JLabel.LEFT);
//        comboBoxkeySize = new JComboBox(selectkeySizeString);

        panelSelectModeAndkeySize.add(labelSelectMode);
        panelSelectModeAndkeySize.add(panelCard);
//        panelSelectModeAndkeySize.add(labelKeySize);
//        panelSelectModeAndkeySize.add(comboBoxkeySize);

        // Enter key
        panelEnterKey = new JPanel();
        panelEnterKey.setLayout(new GridLayout(3, 1));
        labelEnterSize = new JLabel("Enter Secret Key: ", JLabel.LEFT);
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(1, 2));
        fieldEnterkey = new JTextField(30);
        buttonGenerateKey = new JButton("GenerateKey");
        panel4.add(fieldEnterkey);
        panel4.add(buttonGenerateKey);
        panelEnterKey.add(labelEnterSize);
        panelEnterKey.add(panel4);


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

        areaOutput = new JTextArea(10, 68);
        //areaOutput.setEditable(false);
        panelOutput.add(areaOutput);
        //areaOutput.setPreferredSize(new Dimension(30, 30));
        JScrollPane scroll = new JScrollPane(areaOutput);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelOutput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Output"));
        panelOutput.add(scroll);
        panelClose = new JPanel();
        panelClose.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonClose = new JButton("Close");
        panelClose.add(buttonClose);


        // panel.add(panelMH);
        panel.add(panelInput);
        panel.add(panelSelectModeAndkeySize);
        panel.add(panelEnterKey);
        panel.add(panelOutput);
        panel.add(panelClose);

        comboBoxSelectModeMHBDXEncrypt.setVisible(false);
        comboBoxSelectModeHash.setVisible(false);


        // setAction Button Text
        buttonText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTextMHDX = true;
                fieldInput.setVisible(true);
                panelfileUpload.setVisible(false);
                labelFilePath.setText(""); // Xóa đường dẫn khi chuyển về TextField
            }
        });
        // setAction ButtonFile
        buttonFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isTextMHDX = false;
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
                    fileMHDX = file;
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

        DES des = new DES();
        AES aes = new AES();
        Twofish twofish = new Twofish();
        Hill hill = new Hill();
        Vigenere vigenere = new Vigenere();
        buttonGenerateKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if(((String)comboBoxSelectModeMHDX.getSelectedItem()).equals("DES")) {

                        SecretKey key = des.createKey();
                        // Xuất khóa thành Chuỗi
                        String exportedKey = des.exportKey();
                        //Nhập lại chìa khóa
                        des.importKey(exportedKey);

                        // Chuyển key đã nhập thành String và hiển thị trên giao diện
                        String keyToString = bytesToChar(des.createKey().getEncoded());
                        fieldEnterkey.setText(keyToString);
                    } else if (((String)comboBoxSelectModeMHDX.getSelectedItem()).equals("AES")) {
                        SecretKey key = aes.createKey();
                        // Xuất khóa thành Chuỗi
                        String exportedKey = aes.exportKey();
                        //Nhập lại chìa khóa
                        aes.importKey(exportedKey);

                        // Chuyển key đã nhập thành String và hiển thị trên giao diện
                        String keyToString = bytesToChar(aes.createKey().getEncoded());
                        fieldEnterkey.setText(keyToString);
                    }else if (((String)comboBoxSelectModeMHDX.getSelectedItem()).equals("Twofish")) {
                        SecretKey key = twofish.createKey();
                        // Xuất khóa thành Chuỗi
                        String exportedKey = twofish.exportKey();
                        //Nhập lại chìa khóa
                        twofish.importKey(exportedKey);

                        // Chuyển key đã nhập thành String và hiển thị trên giao diện
                        String keyToString = bytesToChar(twofish.createKey().getEncoded());
                        fieldEnterkey.setText(keyToString);
                    }
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = fieldEnterkey.getText();
                try {
                    if(((String) comboBoxSelectModeMHDX.getSelectedItem()).equals("DES")){
                        // Kiểm tra xem khóa có sẵn hay không trước khi tiếp tục
                        if (des.key == null)
                            throw new RuntimeException("Key not found. Please generate a key first.");

                        if (isTextMHDX) {
                            // Encrypt text
                            des.importKey(key);
                            String plainText = fieldInput.getText();
                            byte[] encryptText = des.encrypt(plainText, (String) comboBoxSelectModeMHDX.getSelectedItem());
                            // Hiển thị văn bản được mã hóa
                            areaOutput.setText(Base64.getEncoder().encodeToString(encryptText));
                        }
                        else  {
                            des.importKey(key);
                            des.encryptFile(fileMHDX.getAbsolutePath(), fileMHDX.getAbsolutePath()+"_encrypt");
                            areaOutput.setText("Encrypted file "+ fileMHDX.getAbsolutePath()+"_encrypt");

                        }
                    } else if (((String)comboBoxSelectModeMHDX.getSelectedItem()).equals("AES")) {
                        // Kiểm tra xem khóa có sẵn hay không trước khi tiếp tục
                        if (aes.key == null)
                            throw new RuntimeException("Key not found. Please generate a key first.");

                        if (isTextMHDX) {
                            // Encrypt text
                            aes.importKey(key);
                            String plainText = fieldInput.getText();
                            byte[] encryptText = aes.encrypt(plainText,(String) comboBoxSelectModeMHDX.getSelectedItem());
                            // Hiển thị văn bản được mã hóa
                            areaOutput.setText(Base64.getEncoder().encodeToString(encryptText));
                        }
                        else  {
                            aes.importKey(key);
                            aes.encryptFile(fileMHDX.getAbsolutePath(), fileMHDX.getAbsolutePath()+"_encrypt");
                            areaOutput.setText("Encrypted file "+ fileMHDX.getAbsolutePath()+"_encrypt");

                        }
                    }else if (((String)comboBoxSelectModeMHDX.getSelectedItem()).equals("Twofish")) {
                        // Kiểm tra xem khóa có sẵn hay không trước khi tiếp tục
                        if (twofish.key == null)
                            throw new RuntimeException("Key not found. Please generate a key first.");

                        if (isTextMHDX) {
                            // Encrypt text
                            twofish.importKey(key);
                            String plainText = fieldInput.getText();
                            byte[] encryptText = twofish.encrypt(plainText, (String) comboBoxSelectModeMHDX.getSelectedItem());
                            // Hiển thị văn bản được mã hóa
                            areaOutput.setText(Base64.getEncoder().encodeToString(encryptText));
                        } else {
                            twofish.importKey(key);
                            twofish.encryptFile(fileMHDX.getAbsolutePath(), fileMHDX.getAbsolutePath() + "_encrypt");
                            areaOutput.setText("Encrypted file " + fileMHDX.getAbsolutePath() + "_encrypt");

                        }
                    }

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        buttonDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = fieldEnterkey.getText();
                try {
                    if(((String) comboBoxSelectModeMHDX.getSelectedItem()).equals("DES")){
                        // Kiểm tra xem khóa có sẵn hay không trước khi tiếp tục
                        if (des.key == null)
                            throw new RuntimeException("Key not found. Please generate a key first.");

                        if (isTextMHDX) {
                            // Encrypt text
                            des.importKey(key);
                            String cipherText = fieldInput.getText();
                            byte[] decryptText = des.decrypt(Base64.getDecoder().decode(cipherText), (String) comboBoxSelectModeMHDX.getSelectedItem()).getBytes();
                            // Hiển thị văn bản được mã hóa
                            areaOutput.setText(new String(decryptText, "UTF-8"));
                        }
                        else  {
                            des.importKey(key);
                            des.decryptFile(fileMHDX.getAbsolutePath(), fileMHDX.getAbsolutePath()+"_decrypt");
                            areaOutput.setText("Decrypted file "+ fileMHDX.getAbsolutePath()+"_decrypt");

                        }
                    }
                    else if(((String) comboBoxSelectModeMHDX.getSelectedItem()).equals("AES")){
                        // Kiểm tra xem khóa có sẵn hay không trước khi tiếp tục
                        if (aes.key == null)
                            throw new RuntimeException("Key not found. Please generate a key first.");

                        if (isTextMHDX) {
                            // Encrypt text
                            aes.importKey(key);
                            String cipherText = fieldInput.getText();
                            byte[] decryptText = aes.decrypt(Base64.getDecoder().decode(cipherText), (String) comboBoxSelectModeMHDX.getSelectedItem()).getBytes();
                            // Hiển thị văn bản được mã hóa
                            areaOutput.setText(new String(decryptText, "UTF-8"));
                        }
                        else  {
                            aes.importKey(key);
                            aes.decryptFile(fileMHDX.getAbsolutePath(), fileMHDX.getAbsolutePath()+"_decrypt");
                            areaOutput.setText("Decrypted file "+ fileMHDX.getAbsolutePath()+"_decrypt");

                        }
                    } else if(((String) comboBoxSelectModeMHDX.getSelectedItem()).equals("Twofish")) {
                        // Kiểm tra xem khóa có sẵn hay không trước khi tiếp tục
                        if (twofish.key == null)
                            throw new RuntimeException("Key not found. Please generate a key first.");

                        if (isTextMHDX) {
                            // Encrypt text
                            twofish.importKey(key);
                            String cipherText = fieldInput.getText();
                            byte[] decryptText = twofish.decrypt(Base64.getDecoder().decode(cipherText), (String) comboBoxSelectModeMHDX.getSelectedItem()).getBytes();
                            // Hiển thị văn bản được mã hóa
                            areaOutput.setText(new String(decryptText, "UTF-8"));
                        } else {
                            twofish.importKey(key);
                            twofish.decryptFile(fileMHDX.getAbsolutePath(), fileMHDX.getAbsolutePath() + "_decrypt");
                            areaOutput.setText("Decrypted file " + fileMHDX.getAbsolutePath() + "_decrypt");
                        }
                    }
                        } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        this.getContentPane().add(panel);
    }

    JPanel panel1, panel2, panel3;
    JComboBox comboBoxKeyTypeEn, comboBoxKeyTypeDe;
    String[] keyTypeString = {"PublicKey", "PrivateKey"};
    JTextArea areaEn, areaDe;

    // Tao giao dien ma hoa bat doi xung
    public void createMHBDX() {

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // input
        panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(2, 2));
        labelPublicKey = new JLabel("Public Key");
        labelPrivateKey = new JLabel("Private Key");
        fieldPublicKey = new JTextField();
        fieldPrivateKey = new JTextField();
        panelInput.add(labelPublicKey);
        panelInput.add(labelPrivateKey);
        panelInput.add(fieldPublicKey);
        panelInput.add(fieldPrivateKey);
        panelInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Input"));

        //panel1
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonGenerateKey = new JButton("Generate Key");
        labelKeySize = new JLabel("Key Size:");
        comboBoxkeySize = new JComboBox(selectkeySizeRSA);
        panel1.add(buttonGenerateKey);
        panel1.add(labelKeySize);
        panel1.add(comboBoxkeySize);

        // panel2
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(6, 2));
        labelPlaintextEncrypt = new JLabel("Plaintext Encrypt:");
        labelPlaintextDecrypt = new JLabel("Plaintext Decrypt:");
        fieldPlaintextEncrypt = new JTextField();
        fieldPlaintextDecrypt = new JTextField();
        labelEnerPubOrPriEncryct = new JLabel("Enter PublicKey/PrivateKey:");
        labelEnerPubOrPriDecryct = new JLabel("Enter PublicKey/PrivateKey:");
        fieldEnerPubOrPriEncryct = new JTextField();
        fieldEnerPubOrPriDecryct = new JTextField();
        labelCipherTypeEncrypt = new JLabel("Cipher Type:");
        labelCipherTypeDecrypt = new JLabel("Cipher Type: ");
        comboBoxSelectModeMHBDXEncrypt = new JComboBox(selectModeMHBDXString);
        comboBoxSelectModeMHBDXDecrypt = new JComboBox(selectModeMHBDXString);
        panel2.add(labelPlaintextEncrypt);
        panel2.add(labelPlaintextDecrypt);
        panel2.add(fieldPlaintextEncrypt);
        panel2.add(fieldPlaintextDecrypt);
        panel2.add(labelEnerPubOrPriEncryct);
        panel2.add(labelEnerPubOrPriDecryct);
        panel2.add(fieldEnerPubOrPriEncryct);
        panel2.add(fieldEnerPubOrPriDecryct);
        panel2.add(labelCipherTypeEncrypt);
        panel2.add(labelCipherTypeDecrypt);
        panel2.add(comboBoxSelectModeMHBDXEncrypt);
        panel2.add(comboBoxSelectModeMHBDXDecrypt);

        // panel3
        panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1, 4));
        labelKeyTypeEn = new JLabel("Key Type:");
        comboBoxKeyTypeEn = new JComboBox(keyTypeString);
        labelKeyTypeDe = new JLabel("Key Type:");
        comboBoxKeyTypeDe = new JComboBox(keyTypeString);
        panel3.add(labelKeyTypeEn);
        panel3.add(comboBoxKeyTypeEn);
        panel3.add(labelKeyTypeDe);
        panel3.add(comboBoxKeyTypeDe);

        // panelOutput
        panelOutput = new JPanel();
        panelOutput.setLayout(new GridLayout(2, 2));
        buttonEncrypt = new JButton("Encrypt");
        buttonDecrypt = new JButton("Decrypt");
        areaEn = new JTextArea();
        areaDe = new JTextArea();
        panelOutput.add(buttonEncrypt);
        panelOutput.add(buttonDecrypt);
        panelOutput.add(areaEn);
        panelOutput.add(areaDe);
        panelOutput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Output"));

        panelClose = new JPanel();
        panelClose.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonClose = new JButton("Close");
        panelClose.add(buttonClose);

        panel.add(panelInput);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panelOutput);
        panel.add(panelClose);
        this.getContentPane().add(panel);

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //new doi tuong RSA
        RSA rsa = new RSA();


        //su kien tao key
        buttonGenerateKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = Integer.parseInt((String) comboBoxkeySize.getSelectedItem());
                try {
                    rsa.genKey(size);
                    PrivateKey privateKey = rsa.privateKey;
                    PublicKey publicKey = rsa.publicKey;
                    //do du lieu len lai giao dien
                    String privateKeyToString = bytesToChar(privateKey.getEncoded());
                    fieldPrivateKey.setText(privateKeyToString);
                    String publicKeyToString = bytesToChar(publicKey.getEncoded());
                    fieldPublicKey.setText(publicKeyToString);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //su kien encrypt
        buttonEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plainText = fieldPlaintextEncrypt.getText();
                String key = fieldEnerPubOrPriEncryct.getText();
                try {
                    rsa.convertKey(key, (String) comboBoxKeyTypeEn.getSelectedItem());
                    String encryptText = rsa.encrypt(plainText, (String) comboBoxKeyTypeEn.getSelectedItem(), (String) comboBoxSelectModeMHBDXEncrypt.getSelectedItem());
                    areaEn.setText(encryptText);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                         IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //su kien encrypt
        buttonDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cipherText = fieldPlaintextDecrypt.getText();
                String key = fieldEnerPubOrPriDecryct.getText();
                try {
                    rsa.convertKey(key, (String) comboBoxKeyTypeDe.getSelectedItem());
                    String decryptText = rsa.decrypt(cipherText, (String) comboBoxKeyTypeDe.getSelectedItem(), (String) comboBoxSelectModeMHBDXDecrypt.getSelectedItem());
                    areaDe.setText(decryptText);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                         IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Tao giao dien Hash
    public void createHash() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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

        // Select Mode
        panelSelectModeAndkeySize = new JPanel();
        panelSelectModeAndkeySize.setLayout(new FlowLayout(FlowLayout.CENTER));
        labelSelectMode = new JLabel("Select Mode: ", JLabel.LEFT);
        comboBoxSelectModeHash = new JComboBox(selectModeHashString);
        panelSelectModeAndkeySize.add(labelSelectMode);
        panelSelectModeAndkeySize.add(comboBoxSelectModeHash);

        // Encrypt
        JPanel panelbutton = new JPanel();
        panelbutton.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonEncrypt = new JButton("Crypt");
        panelbutton.add(buttonEncrypt);

        // output
        panelOutput = new JPanel();
        panelOutput.setLayout(new BoxLayout(panelOutput, BoxLayout.Y_AXIS));

        areaOutput = new JTextArea(10, 68);
        areaOutput.setEditable(false);
        panelOutput.add(areaOutput);
        //areaOutput.setPreferredSize(new Dimension(30, 30));
        JScrollPane scroll = new JScrollPane(areaOutput);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelOutput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Output"));
        panelOutput.add(scroll);

        panelClose = new JPanel();
        panelClose.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonClose = new JButton("Close");
        panelClose.add(buttonClose);


        panel.add(panelInput);
        panel.add(panelSelectModeAndkeySize);
        panel.add(panelbutton);
        panel.add(panelOutput);
        panel.add(panelClose);

        this.getContentPane().add(panel);
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // setAction Button Text
        buttonText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTextHash = true;
                fieldInput.setVisible(true);
                panelfileUpload.setVisible(false);
                labelFilePath.setText(""); // Xóa đường dẫn khi chuyển về TextField
            }
        });
        // setAction ButtonFile
        buttonFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isTextHash = false;
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
                    fileHash = file;
                    labelFilePath.setText("File to upload: " + file.getAbsolutePath());
                    // Thực hiện upload file lên server tại đây.
                }
            }
        });
        MD5 md5 = new MD5();
        RIPEMD256 ripemd256 = new RIPEMD256();
        SHA_1 sha_1 = new SHA_1();
        SHA256 sha256 = new SHA256();
        buttonEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(((String)comboBoxSelectModeHash.getSelectedItem()).equals("MD5")) {
                        if (isTextHash) {
                            // Encrypt text
                            String plainText = fieldInput.getText();
                            String cryptedText = md5.check(plainText, (String) comboBoxSelectModeHash.getSelectedItem());
                            // Display the encrypted text
                            areaOutput.setText(cryptedText);
                        } else {
                            String hashText = md5.checkFile(fileHash.getAbsolutePath());
                            areaOutput.setText(hashText);

                        }
                    } if(((String)comboBoxSelectModeHash.getSelectedItem()).equals("RIPEMD256")) {
                        if (isTextHash) {
                            // Encrypt text
                            String plainText = fieldInput.getText();
                            String cryptedText = ripemd256.check(plainText, (String) comboBoxSelectModeHash.getSelectedItem());
                            // Display the encrypted text
                            areaOutput.setText(cryptedText);
                        } else {
                            String hashText = ripemd256.checkFile(fileHash.getAbsolutePath());
                            areaOutput.setText(hashText);

                        }
                    }if(((String)comboBoxSelectModeHash.getSelectedItem()).equals("SHA-1")) {
                        if (isTextHash) {
                            // Encrypt text
                            String plainText = fieldInput.getText();
                            String cryptedText = sha_1.check(plainText, (String) comboBoxSelectModeHash.getSelectedItem());
                            // Display the encrypted text
                            areaOutput.setText(cryptedText);
                        } else {
                            String hashText = sha_1.checkFile(fileHash.getAbsolutePath());
                            areaOutput.setText(hashText);
                        }
                    }
                    if(((String)comboBoxSelectModeHash.getSelectedItem()).equals("SHA-256")) {
                        if (isTextHash) {
                            // Encrypt text
                            String plainText = fieldInput.getText();
                            String cryptedText = sha256.check(plainText, (String) comboBoxSelectModeHash.getSelectedItem());
                            // Display the encrypted text
                            areaOutput.setText(cryptedText);
                        } else {
                            String hashText = sha256.checkFile(fileHash.getAbsolutePath());
                            areaOutput.setText(hashText);
                        }
                    }

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    JPanel panelEnterHash;
    JLabel labelEnterHash;
    JTextField fieldEnterHash;
    JButton buttonVerify;

    public void createCKDT(){
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panelInput = new JPanel();
        JLabel labelFilePath = new JLabel(); // Thêm một JLabel để hiển thị đường dẫn
        labelFilePath.setPreferredSize(new Dimension(200,40));
        JPanel panelfileUpload = new JPanel();
        panelfileUpload.setLayout(new FlowLayout(FlowLayout.CENTER));
        butttonFileUpload = new JButton("Click to Upload File");
        panelfileUpload.add(butttonFileUpload);
        panelInput.add(butttonFileUpload);
        panelInput.add(labelFilePath);
        panelInput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE,2),"Input"));

        panelEnterHash = new JPanel();
        panelEnterHash.setLayout(new BoxLayout(panelEnterHash, BoxLayout.Y_AXIS));
        labelEnterHash = new JLabel("Enter Hash: ");
        fieldEnterHash = new JTextField();
        buttonVerify = new JButton("Verify");
        panelEnterHash.add(labelEnterHash);
        panelEnterHash.add(fieldEnterHash);
        panelEnterHash.add(buttonVerify);

        // output
        panelOutput = new JPanel();
        panelOutput.setLayout(new BoxLayout(panelOutput, BoxLayout.Y_AXIS));

        areaOutput = new JTextArea(10, 68);
        areaOutput.setEditable(false);
        panelOutput.add(areaOutput);
        //areaOutput.setPreferredSize(new Dimension(30, 30));
        JScrollPane scroll = new JScrollPane(areaOutput);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelOutput.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Output"));
        panelOutput.add(scroll);

        panelClose = new JPanel();
        panelClose.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonClose = new JButton("Close");
        panelClose.add(buttonClose);

        panel.add(panelInput);
        panel.add(panelEnterHash);
        panel.add(panelOutput);
        panel.add(panelClose);
        this.getContentPane().add(panel);
        panelfileUpload.setVisible(true);
            butttonFileUpload.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                    int returnVal = fileChooser.showOpenDialog(panel);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        fileCKDT = file;
                        labelFilePath.setText(file.getName());
                        // Thực hiện upload file lên server tại đây.
                    }
            }
        });
        SHA256 sha256 = new SHA256();
            buttonVerify.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String CKDTText = sha256.checkFile(fileCKDT.getAbsolutePath());
                        if(CKDTText.equalsIgnoreCase(fieldEnterHash.getText())){
                            areaOutput.setText("Trùng khớp");
                        }else {
                            areaOutput.setText("Không trùng khớp");
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
    }

    private String bytesToChar(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void main(String[] args) {
        new UI();
    }

}
