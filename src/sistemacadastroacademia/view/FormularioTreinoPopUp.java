package sistemacadastroacademia.view;

import sistemacadastroacademia.model.Treino;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormularioTreinoPopUp extends JDialog {

    private JTextField txtTipo, txtDescricao, txtDuracao, txtDataInicio;
    private JButton btnSalvar, btnCancelar;

    private Treino treino;
    private boolean salvo = false;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FormularioTreinoPopUp(Frame owner, Treino treino) {
        super(owner, true);
        this.treino = treino;

        setTitle(treino == null ? "Adicionar Novo Treino" : "Editar Treino");
        setSize(450, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Configuração e adição dos JLabels (coluna 0)
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1; // Peso menor para a coluna de labels
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridy = 0; painelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridy = 1; painelFormulario.add(new JLabel("Descrição:"), gbc);
        gbc.gridy = 2; painelFormulario.add(new JLabel("Duração (minutos):"), gbc);
        gbc.gridy = 3; painelFormulario.add(new JLabel("Data Início (dd/MM/yyyy):"), gbc);

        // Configuração e adição dos JTextFields (coluna 1)
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.9; // Peso maior para a coluna de campos de texto
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0; txtTipo = new JTextField(); painelFormulario.add(txtTipo, gbc);
        gbc.gridy = 1; txtDescricao = new JTextField(); painelFormulario.add(txtDescricao, gbc);
        gbc.gridy = 2; txtDuracao = new JTextField(); painelFormulario.add(txtDuracao, gbc);
        gbc.gridy = 3; txtDataInicio = new JTextField(); painelFormulario.add(txtDataInicio, gbc);

        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        if (treino != null) {
            preencherFormulario();
        }

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void preencherFormulario() {
        txtTipo.setText(treino.getTipo());
        txtDescricao.setText(treino.getDescricao());
        txtDuracao.setText(String.valueOf(treino.getDuracaoMinutos()));
        txtDataInicio.setText(treino.getDataInicio().format(dateFormatter));
    }

    private void salvar() {
        if (txtTipo.getText().trim().isEmpty() || txtDataInicio.getText().trim().isEmpty() || txtDuracao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tipo, Duração e Data de Início são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.treino == null) {
            this.treino = new Treino();
        }

        try {
            treino.setTipo(txtTipo.getText());
            treino.setDescricao(txtDescricao.getText());
            treino.setDuracaoMinutos(Integer.parseInt(txtDuracao.getText()));
            treino.setDataInicio(LocalDate.parse(txtDataInicio.getText(), dateFormatter));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de número ou data inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.salvo = true;
        dispose();
    }

    public boolean foiSalvo() {
        return salvo;
    }

    public Treino getTreino() {
        return treino;
    }
}