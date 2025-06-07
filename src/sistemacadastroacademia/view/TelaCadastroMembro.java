package sistemacadastroacademia.view;

import sistemacadastroacademia.controller.MembroController;
import sistemacadastroacademia.model.Membro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TelaCadastroMembro extends JFrame {

    // --- Componentes Visuais ---
    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEndereco, txtDataCadastro;
    private JButton btnNovo, btnSalvar, btnExcluir;
    private JTable tabelaMembros;
    private DefaultTableModel modeloTabela;

    // --- Controller ---
    private MembroController membroController;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaCadastroMembro() {
        super("Gerenciamento de Membros");

        this.membroController = new MembroController();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Painel principal que usará BorderLayout
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel do Formulário (Norte) ---
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Configuração para JLabels (coluna 0 e 2)
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 0; painelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 0; gbc.gridy = 1; painelFormulario.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 0; gbc.gridy = 2; painelFormulario.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 2; gbc.gridy = 0; painelFormulario.add(new JLabel("Nome Completo:"), gbc);
        gbc.gridx = 2; gbc.gridy = 1; painelFormulario.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 2; gbc.gridy = 2; painelFormulario.add(new JLabel("Data Cadastro (dd/MM/yyyy):"), gbc);

        // Configuração para JTextFields (coluna 1 e 3)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.9;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 1; gbc.gridy = 0; txtId = new JTextField(5); txtId.setEditable(false); painelFormulario.add(txtId, gbc);
        gbc.gridx = 1; gbc.gridy = 1; txtCpf = new JTextField(15); painelFormulario.add(txtCpf, gbc);
        gbc.gridx = 1; gbc.gridy = 2; txtEndereco = new JTextField(20); painelFormulario.add(txtEndereco, gbc);
        gbc.gridx = 3; gbc.gridy = 0; txtNome = new JTextField(20); painelFormulario.add(txtNome, gbc);
        gbc.gridx = 3; gbc.gridy = 1; txtTelefone = new JTextField(15); painelFormulario.add(txtTelefone, gbc);
        gbc.gridx = 3; gbc.gridy = 2; txtDataCadastro = new JTextField(15); painelFormulario.add(txtDataCadastro, gbc);

        // Adiciona o formulário na parte superior do painel principal
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);

        // --- Painel de Botões (Sul) ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);

        // Adiciona os botões na parte inferior do painel principal
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // --- Tabela de Membros (Centro) ---
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Endereço", "Data Cadastro"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaMembros = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaMembros);

        // Adiciona a tabela na parte central do painel principal
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Adiciona o painel principal à janela
        this.add(painelPrincipal);

        // --- LÓGICA DOS EVENTOS ---
        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarMembro());
        btnExcluir.addActionListener(e -> excluirMembro());
        tabelaMembros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarDadosDoMembroSelecionado();
            }
        });

        // Carrega os dados iniciais na tabela
        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Membro> membros = membroController.listarMembros();
        for (Membro membro : membros) {
            modeloTabela.addRow(new Object[]{
                    membro.getId(),
                    membro.getNome(),
                    membro.getCpf(),
                    membro.getTelefone(),
                    membro.getEndereco(),
                    membro.getDataCadastro().format(dateFormatter)
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtDataCadastro.setText("");
        tabelaMembros.clearSelection();
    }

    private void carregarDadosDoMembroSelecionado() {
        int linhaSelecionada = tabelaMembros.getSelectedRow();
        if (linhaSelecionada != -1) {
            String id = modeloTabela.getValueAt(linhaSelecionada, 0).toString();
            String nome = modeloTabela.getValueAt(linhaSelecionada, 1).toString();
            String cpf = modeloTabela.getValueAt(linhaSelecionada, 2).toString();
            String telefone = modeloTabela.getValueAt(linhaSelecionada, 3).toString();
            String endereco = modeloTabela.getValueAt(linhaSelecionada, 4).toString();
            String dataCadastro = modeloTabela.getValueAt(linhaSelecionada, 5).toString();

            txtId.setText(id);
            txtNome.setText(nome);
            txtCpf.setText(cpf);
            txtTelefone.setText(telefone);
            txtEndereco.setText(endereco);
            txtDataCadastro.setText(dataCadastro);
        }
    }

    private void salvarMembro() {
        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Membro membro = new Membro();
        membro.setNome(txtNome.getText());
        membro.setCpf(txtCpf.getText());
        membro.setTelefone(txtTelefone.getText());
        membro.setEndereco(txtEndereco.getText());

        try {
            membro.setDataCadastro(LocalDate.parse(txtDataCadastro.getText(), dateFormatter));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (txtId.getText().trim().isEmpty()) {
            // Adicionar Novo Membro
            if (membroController.adicionarMembro(membro)) {
                JOptionPane.showMessageDialog(this, "Membro adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar membro. Verifique se o CPF já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Atualizar Membro Existente
            membro.setId(Integer.parseInt(txtId.getText()));
            if (membroController.atualizarMembro(membro)) {
                JOptionPane.showMessageDialog(this, "Membro atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar membro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        atualizarTabela();
    }

    private void excluirMembro() {
        int linhaSelecionada = tabelaMembros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um membro na tabela para excluir.", "Nenhum Membro Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o membro selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            int idParaExcluir = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            if (membroController.excluirMembro(idParaExcluir)) {
                JOptionPane.showMessageDialog(this, "Membro excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o membro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            atualizarTabela();
        }
    }
}