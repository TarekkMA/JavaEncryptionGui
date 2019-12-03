package com.tarekkma;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application extends JFrame {

    private final List<EncryptionAlgorithm> algorithmList;
    private EncryptionAlgorithm selectedAlgorithm;

    Application(List<EncryptionAlgorithm> algorithmList) {
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


        addComp(mainPanel,
                comboboxLayout,
                0, 0,
                100, 0,
                2, 1,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        addComp(mainPanel,
                infoPanel,
                0, 1,
                0, 0,
                2, 1,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);
        addComp(mainPanel,
                keyPanel,
                0, 2,
                100, 0,
                2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        addComp(mainPanel,
                new JLabel("Decrypted Text:"),
                0, 3,
                0, 0,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE);
        addComp(mainPanel,
                new JScrollPane(decTxt),
                0, 4,
                100, 100,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);
        addComp(mainPanel,
                new JLabel("Encrypted Text:"),
                1, 3,
                0, 0,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE);
        addComp(mainPanel,
                new JScrollPane(encTxt),
                1, 4,
                100, 100,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.BOTH);

        addComp(mainPanel,
                encBtn,
                0, 5,
                100, 0,
                1, 1,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL);
        addComp(mainPanel,
                decBtn,
                1, 5,
                100, 0,
                1, 1,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        JLabel tl1 = new JLabel("Created By Tarek Mohamed Abdalla");
        tl1.setHorizontalAlignment(JLabel.CENTER);
        addComp(mainPanel,
                tl1,
                0, 6,
                100, 0,
                2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        JLabel tl2 = new JLabel("GitHub.com/TarekkMA");
        tl2.setHorizontalAlignment(JLabel.CENTER);
        addComp(mainPanel,
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
        addATextWatcher(keyTxt, s -> enc(encTxt, decTxt, keyTxt));
        addATextWatcher(decTxt, s -> enc(encTxt, decTxt, keyTxt));
        addATextWatcher(encTxt, s -> dec(encTxt, decTxt, keyTxt));
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
                decTxt.setText("=== ERROR ===\n" + e.toString() + "\n===\n");
            }
        }
    }

    private boolean checkKey(String key) {
        return !selectedAlgorithm.requireKey() || selectedAlgorithm.isValidKey(key);
    }

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int xWeight, int yWeight, int compWidth, int compHeight, int place, int stretch) {

        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = xPos;
        gridConstraints.gridy = yPos;
        gridConstraints.gridwidth = compWidth;
        gridConstraints.gridheight = compHeight;
        gridConstraints.weightx = xWeight;
        gridConstraints.weighty = yWeight;
        gridConstraints.insets = new Insets(5, 5, 5, 5);
        gridConstraints.anchor = place;
        gridConstraints.fill = stretch;

        thePanel.add(comp, gridConstraints);

    }

    public void addATextWatcher(JTextComponent jTextComponent, Consumer<String> onTextChanged) {
        jTextComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            private void changed() {
                if (jTextComponent.isFocusOwner())
                    onTextChanged.accept(jTextComponent.getText());
            }
        });
    }

    String toMultiLineHtml(String in) {
        return "<html>" + in.replaceAll("\n", "<br>") + "</html>";
    }
}
