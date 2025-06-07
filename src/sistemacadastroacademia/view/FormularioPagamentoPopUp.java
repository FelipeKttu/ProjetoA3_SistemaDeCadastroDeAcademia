package sistemacadastroacademia.view;

import sistemacadastroacademia.model.Pagamento;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormularioPagamentoPopUp extends JDialog {

    private JTextField txtValor, txtDataPagamento, txtMetodo, txtDataVencimento;
    private JComboBox<String> comboStatus;
    private JButton btnSalvar, btnCancelar;

    private Pagamento pagamento;
    private boolean salvo = false;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FormularioPagamentoPopUp(Frame owner) {
        super(owner, "Registrar Novo Pagamento", true);
        setSize(450, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Configuração e adição dos JLabels (coluna 0) ---
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridy = 0; painelFormulario.add(new JLabel("Valor (R$):"), gbc);
        gbc.gridy = 1; painelFormulario.add(new JLabel("Data Pagamento:"), gbc);
        gbc.gridy = 2; painelFormulario.add(new JLabel("Status:"), gbc);
        gbc.gridy = 3; painelFormulario.add(new JLabel("Método Pag.:"), gbc);
        gbc.gridy = 4; painelFormulario.add(new JLabel("Data Vencimento:"), gbc);


        // --- Configuração e adição dos Componentes de Entrada (coluna 1) ---
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.9;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0; txtValor = new JTextField(); painelFormulario.add(txtValor, gbc);
        gbc.gridy = 1; txtDataPagamento = new JTextField(); txtDataPagamento.setText(LocalDate.now().format(dateFormatter)); painelFormulario.add(txtDataPagamento, gbc);
        gbc.gridy = 2; comboStatus = new JComboBox<>(new String[]{"Pago", "Pendente", "Atrasado", "Cancelado"}); painelFormulario.add(comboStatus, gbc);
        gbc.gridy = 3; txtMetodo = new JTextField(); painelFormulario.add(txtMetodo, gbc);
        gbc.gridy = 4; txtDataVencimento = new JTextField(); painelFormulario.add(txtDataVencimento, gbc);


        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvar() {
        if (txtValor.getText().trim().isEmpty() || txtDataPagamento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Valor e Data de Pagamento são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.pagamento = new Pagamento();
        try {
            pagamento.setValor(new BigDecimal(txtValor.getText().replace(",", ".")));
            pagamento.setDataPagamento(LocalDate.parse(txtDataPagamento.getText(), dateFormatter));
            pagamento.setStatus((String) comboStatus.getSelectedItem());
            pagamento.setMetodoPagamento(txtMetodo.getText());

            if (!txtDataVencimento.getText().trim().isEmpty()) {
                pagamento.setDataVencimento(LocalDate.parse(txtDataVencimento.getText(), dateFormatter));
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de número ou data inválido. Use dd/MM/yyyy para datas.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.salvo = true;
        dispose();
    }

    public boolean foiSalvo() {
        return salvo;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }
}