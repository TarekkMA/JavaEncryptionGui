package com.tarekkma.ui;

import com.tarekkma.EncryptionAlgorithm;
import com.tarekkma.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import static com.tarekkma.Utils.*;
import java.util.List;

public class AppView extends JFrame {

    private final List<EncryptionAlgorithm> algorithmList;
    private EncryptionAlgorithm selectedAlgorithm;

    public AppView(List<EncryptionAlgorithm> algorithmList) {
        this.algorithmList = algorithmList;
        selectedAlgorithm = algorithmList.get(0);
        setUpUI();
    }

    private String[] getAlgorithmNameArr() {
        return algorithmList.stream().map(EncryptionAlgorithm::name).toArray(String[]::new);
    }


    private void setUpUI() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Encryption Algorithms | Tarek Mohamed Abdalla | Section 2");

        JPanel mainPanel = new JPanel();
        //mainPanel.setBackground(Color.yellow);
        mainPanel.setLayout(new GridBagLayout());

        JTextArea encTxt = new JTextArea();
        JTextArea decTxt = new JTextArea();
        JTextField keyTxt = new JTextField();
        JButton encBtn = new JButton("Encrypt ->");
        JButton decBtn = new JButton("<- Decrypt");
        JComboBox<String> algorithmJComboBox = new JComboBox<>(getAlgorithmNameArr());

        JPanel comboboxLayout = new JPanel(new BorderLayout());
        comboboxLayout.setBackground(mainPanel.getBackground());
        comboboxLayout.setBorder(BorderFactory.createTitledBorder("Selected Algorithm:"));
        //comboboxLayout.add(new JLabel("Selected Algorithm:"));
        comboboxLayout.add(algorithmJComboBox, BorderLayout.CENTER);


        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setSize(1000,100);
        infoPanel.setBackground(mainPanel.getBackground());
        Border border = BorderFactory.createTitledBorder("Description:");
        infoPanel.setBorder(border);
        JTextArea desConLabel = new JTextArea(selectedAlgorithm.description());
        desConLabel.setLineWrap(true);
        desConLabel.setEditable(false);
        desConLabel.setBackground(mainPanel.getBackground());
        JScrollPane scrollPane = new JScrollPane(desConLabel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        infoPanel.add(scrollPane);


        CardLayout keyCardLayout = new CardLayout();
        JPanel keyPanel = new JPanel(keyCardLayout);
        keyPanel.setBackground(mainPanel.getBackground());
        keyPanel.setBorder(BorderFactory.createTitledBorder("Key:"));
        Box keyLayout = Box.createHorizontalBox();
        keyLayout.add(keyTxt);
        JButton genKeyBtn = new JButton("Random Key");
        keyLayout.add(genKeyBtn);


        String KEY = "KEY";
        String NO_KEY = "NO_KEY";

        keyPanel.add(keyLayout, KEY);
        JLabel noKeyLabel = new JLabel("This Algorithm Doesn't Need A Key");
        noKeyLabel.setHorizontalAlignment(JLabel.CENTER);
        keyPanel.add(noKeyLabel, NO_KEY);

        if (selectedAlgorithm.requireKey()) {
            keyCardLayout.show(keyPanel, KEY);
        } else {
            keyCardLayout.show(keyPanel, NO_KEY);
        }


        encTxt.setLineWrap(true);
        decTxt.setLineWrap(true);


        addComponentToGBL(mainPanel,
                comboboxLayout,
                0, 0,
                100, 0,
                2, 1,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        addComponentToGBL(mainPanel,
                infoPanel,
                0, 1,
                0, 0,
                2, 1,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);
        addComponentToGBL(mainPanel,
                keyPanel,
                0, 2,
                100, 0,
                2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        addComponentToGBL(mainPanel,
                new JLabel("Decrypted Text:"),
                0, 3,
                0, 0,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE);
        addComponentToGBL(mainPanel,
                new JScrollPane(decTxt),
                0, 4,
                100, 100,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);
        addComponentToGBL(mainPanel,
                new JLabel("Encrypted Text:"),
                1, 3,
                0, 0,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE);
        addComponentToGBL(mainPanel,
                new JScrollPane(encTxt),
                1, 4,
                100, 100,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);

        addComponentToGBL(mainPanel,
                encBtn,
                0, 5,
                100, 0,
                1, 1,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL);
        addComponentToGBL(mainPanel,
                decBtn,
                1, 5,
                100, 0,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        JLabel tl1 = new JLabel("Created By Tarek Mohamed Abdalla");
        tl1.setHorizontalAlignment(JLabel.CENTER);
        addComponentToGBL(mainPanel,
                tl1,
                0, 6,
                100, 0,
                2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        JLabel tl2 = new JLabel("GitHub.com/TarekkMA");
        tl2.setHorizontalAlignment(JLabel.CENTER);
        addComponentToGBL(mainPanel,
                tl2,
                0, 7,
                100, 0,
                2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);


        algorithmJComboBox.addItemListener(event -> {
            int selectedIndex = algorithmJComboBox.getSelectedIndex();
            selectedAlgorithm = algorithmList.get(selectedIndex);
            enc(encTxt, decTxt, keyTxt);


            desConLabel.setText(selectedAlgorithm.description());

            if (selectedAlgorithm.requireKey()) {
                keyCardLayout.show(keyPanel, KEY);
            } else {
                keyCardLayout.show(keyPanel, NO_KEY);
            }
        });

        genKeyBtn.addActionListener(e -> {
            keyTxt.setText(selectedAlgorithm.generateKey());
            enc(encTxt, decTxt, keyTxt);
        });
        Utils.addATextWatcher(keyTxt, s -> enc(encTxt, decTxt, keyTxt));
        Utils.addATextWatcher(decTxt, s -> enc(encTxt, decTxt, keyTxt));
        Utils.addATextWatcher(encTxt, s -> dec(encTxt, decTxt, keyTxt));
        encBtn.addActionListener(e -> enc(encTxt, decTxt, keyTxt));
        decBtn.addActionListener(e -> dec(encTxt, decTxt, keyTxt));

        add(mainPanel);
        setSize(1000, 1000);
        setVisible(true);
    }


    private void enc(JTextComponent encTxt, JTextComponent decTxt, JTextComponent keyTxt) {
        String plain = decTxt.getText();
        String key = keyTxt.getText();
        if (!checkKey(key)) {
            encTxt.setText("=== ERROR ===\nInvalid Key\n===\n");
        } else {
            try {
                encTxt.setText(selectedAlgorithm.encrypt(plain, key));
            } catch (Exception e) {
                e.printStackTrace();
                encTxt.setText("=== ERROR ===\n" + e.toString() + "\n===\n");
            }
        }
    }

    private void dec(JTextComponent encTxt, JTextComponent decTxt, JTextComponent keyTxt) {
        String plain = encTxt.getText();
        String key = keyTxt.getText();
        if (!checkKey(key)) {
            decTxt.setText("=== ERROR ===\nInvalid Key\n===\n");
        } else {
            try {
                decTxt.setText(selectedAlgorithm.decrypt(plain, key));
            } catch (Exception e) {
                e.printStackTrace();
                decTxt.setText("=== ERROR ===\n" + e.toString() + "\n===\n");
            }
        }
    }

    private boolean checkKey(String key) {
        return !selectedAlgorithm.requireKey() || selectedAlgorithm.isValidKey(key);
    }

}
