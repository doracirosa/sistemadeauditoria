package sistema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import auditoria.Audit;
import auditorias.AuditManager;

public class AuditGUI {
    private JFrame frame;
    private JTextField poNumberField;
    private JTextField articleNumberField;
    private JTextField orderQuantityField;
    private JTextField sampleQuantityField;
    private JTextField dateField; // Campo de data corrigido
    private JRadioButton approvedRadioButton;
    private JRadioButton rejectedRadioButton;
    private JPanel defectPanel;
    private List<JComboBox<String>> defectComboBoxes;
    private List<JTextField> quantityFields;
    private AuditManager auditManager;
    private SimpleDateFormat dateFormat;

    public AuditGUI() {
        auditManager = new AuditManager();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        defectComboBoxes = new ArrayList<>();
        quantityFields = new ArrayList<>();

        frame = new JFrame("Sistema de cadastramento de Auditoria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expande o componente para preencher a célula

        // Data
        JLabel dateLabel = new JLabel("Data:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(dateLabel, gbc);

        dateField = new JTextField(15); // Corrigido para ser um campo de instância
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(dateField, gbc);

        // PO Number
        JLabel poNumberLabel = new JLabel("OP:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(poNumberLabel, gbc);

        poNumberField = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(poNumberField, gbc);

        // Article Number
        JLabel articleNumberLabel = new JLabel("Artigo:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(articleNumberLabel, gbc);

        articleNumberField = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(articleNumberField, gbc);

        // Order Quantity
        JLabel orderQuantityLabel = new JLabel("Quantidade:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(orderQuantityLabel, gbc);

        orderQuantityField = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(orderQuantityField, gbc);

        // Sample Quantity
        JLabel sampleQuantityLabel = new JLabel("Amostragem:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(sampleQuantityLabel, gbc);

        sampleQuantityField = new JTextField(15);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(sampleQuantityField, gbc);

        // Campo para Status
        JLabel statusLabel = new JLabel("Status:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(statusLabel, gbc);

        approvedRadioButton = new JRadioButton("Aprovado");
        rejectedRadioButton = new JRadioButton("Reprovado");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(approvedRadioButton);
        statusGroup.add(rejectedRadioButton);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.add(approvedRadioButton);
        statusPanel.add(rejectedRadioButton);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(statusPanel, gbc);

        // Defeitos e Quantidades
        JLabel defectsLabel = new JLabel("Defeitos:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(defectsLabel, gbc);

        defectPanel = new JPanel();
        defectPanel.setLayout(new BoxLayout(defectPanel, BoxLayout.Y_AXIS));
        addDefectRow(); // Adiciona uma linha inicial

        JScrollPane scrollPane = new JScrollPane(defectPanel);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(scrollPane, gbc);

        JButton addDefectButton = new JButton("Adicionar Defeito");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(addDefectButton, gbc);

        addDefectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDefectRow();
            }
        });

        // Painel para botões
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centraliza os botões horizontalmente
        JButton addButton = new JButton("Adicionar Auditoria");
        buttonsPanel.add(addButton);

        JButton exportButton = new JButton("Exportar CSV");
        buttonsPanel.add(exportButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; // O painel não se expande
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o painel
        frame.add(buttonsPanel, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Coleta e processa os dados inseridos pelo usuário
                    Date date = dateFormat.parse(dateField.getText());
                    String poNumber = poNumberField.getText();
                    String articleNumber = articleNumberField.getText();
                    int orderQuantity = Integer.parseInt(orderQuantityField.getText());
                    int sampleQuantity = Integer.parseInt(sampleQuantityField.getText());

                    // Determina o status
                    String status = "";
                    if (approvedRadioButton.isSelected()) {
                        status = "Aprovado";
                    } else if (rejectedRadioButton.isSelected()) {
                        status = "Reprovado";
                    } else {
                        status = "Não definido";
                    }

                    // Obtém os defeitos e suas quantidades
                    List<String> defects = new ArrayList<>();
                    List<Integer> quantities = new ArrayList<>();
                    for (int i = 0; i < defectComboBoxes.size(); i++) {
                        String defect = (String) defectComboBoxes.get(i).getSelectedItem();
                        String quantityText = quantityFields.get(i).getText();
                        if (!defect.equals("Selecione um defeito") && !quantityText.trim().isEmpty()) {
                            defects.add(defect);
                            quantities.add(Integer.parseInt(quantityText));
                        }
                    }

                    // Cria um objeto Audit e o adiciona ao AuditManager
                    Audit audit = new Audit(date, poNumber, articleNumber, orderQuantity, sampleQuantity, status, defects, quantities);
                    auditManager.addAudit(audit);

                    // Mostra uma mensagem de sucesso
                    JOptionPane.showMessageDialog(frame, "Auditoria adicionada com sucesso!");

                    // Limpa os campos
                    clearFields();

                } catch (ParseException ex) {
                    // Exceção para formato de data inválido
                    JOptionPane.showMessageDialog(frame, "Formato de data inválido. Use dd/MM/yyyy.");
                } catch (NumberFormatException ex) {
                    // Exceção para formato de número inválido
                    JOptionPane.showMessageDialog(frame, "Formato de número inválido.");
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    auditManager.exportToCSV("audits.csv");
                    JOptionPane.showMessageDialog(frame, "Auditoria exportada com sucesso!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro na exportação.");
                }
            }
        });

        frame.setVisible(true);
    }

    private void addDefectRow() {
        // Cria o JComboBox para selecionar um defeito
        JComboBox<String> defectComboBox = new JComboBox<>(new String[]{
            "Selecione um defeito", 
            "Ponto falhado/pulado", 
            "Costura cortada", 
            "Costura escapada",
            "Manga escapada",
            "Beirola",
            "Desencontro bainha",
            "Desencontro cava",
            "Pesponto friso",
            "Pesponto decote",
            "Prega manga",
            "Prega ombro",
            "Prega peitilho",
            "Falta etiqueta",
            "Trocar etiqueta",
            "Etiqueta escapada",
            "Peitilho torto",
            "Costura interna do peitilho",
            "Costura torta",
            "Costura lateral",
            "Gola",
            "Pala",
            "Abertura lateral",
            "Costura franzida/ondulada",
            "Regulagem do ponto: frouxo",
            "Regulagem do ponto: Apertado",
            "Fio nao cortado/ fios soltos",
            "Lavacao",
            "Contaminacao",
            "Outros defeitos",
            "2 qualidade",
            
        });

        // Cria o JTextField para a quantidade
        JTextField quantityField = new JTextField(10);

        // Cria um JPanel para a linha do defeito com layout de FlowLayout
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.add(defectComboBox);
        rowPanel.add(new JLabel("Quantidade:"));
        rowPanel.add(quantityField);

        // Adiciona o JComboBox e JTextField às listas de componentes
        defectComboBoxes.add(defectComboBox);
        quantityFields.add(quantityField);

        // Adiciona o rowPanel ao painel de defeitos
        defectPanel.add(rowPanel);

        // Revalida e repinta o painel para refletir a nova linha adicionada
        defectPanel.revalidate();
        defectPanel.repaint();
    }

    private void clearFields() {
        dateField.setText("");
        poNumberField.setText("");
        articleNumberField.setText("");
        orderQuantityField.setText("");
        sampleQuantityField.setText("");
        approvedRadioButton.setSelected(false);
        rejectedRadioButton.setSelected(false);

        // Limpa todas as linhas de defeito
        defectPanel.removeAll();
        defectComboBoxes.clear();
        quantityFields.clear();
        addDefectRow(); // Adiciona uma linha inicial após limpar
        defectPanel.revalidate();
        defectPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuditGUI();
            }
        });
    }
}
