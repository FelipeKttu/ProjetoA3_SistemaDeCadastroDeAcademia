package sistemacadastroacademia.view;

import sistemacadastroacademia.controller.MembroController;
import sistemacadastroacademia.controller.TreinoController;
import sistemacadastroacademia.model.Membro;
import sistemacadastroacademia.model.Treino;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaGerenciamentoTreinos extends JFrame {

    // --- Componentes Visuais ---
    private JComboBox<Membro> comboMembros;
    private JTable tabelaTreinos;
    private DefaultTableModel modeloTabela;
    private JButton btnAdicionar; // <-- Removidos btnEditar e btnExcluir daqui

    // --- Controllers ---
    private MembroController membroController;
    private TreinoController treinoController;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaGerenciamentoTreinos() {
        super("Gerenciamento de Treinos");

        this.membroController = new MembroController();
        this.treinoController = new TreinoController();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        // --- Painel Superior: Seleção de Membro ---
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        painelSuperior.add(new JLabel("Selecione o Membro:"));
        comboMembros = new JComboBox<>();
        comboMembros.setPreferredSize(new Dimension(300, 25));
        painelSuperior.add(comboMembros);
        this.add(painelSuperior, BorderLayout.NORTH);

        // --- Centro: Tabela de Treinos ---
        String[] colunas = {"ID", "Tipo", "Descrição", "Duração (min)", "Data Início"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaTreinos = new JTable(modeloTabela);
        this.add(new JScrollPane(tabelaTreinos), BorderLayout.CENTER);

        // --- Painel Inferior: Botão de Ação ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        btnAdicionar = new JButton("Adicionar Treino");
        painelBotoes.add(btnAdicionar);
        // Os botões de editar e excluir foram removidos daqui
        this.add(painelBotoes, BorderLayout.SOUTH);

        // --- Carregar Dados Iniciais ---
        carregarMembrosNoComboBox();

        // --- Lógica dos Eventos ---
        comboMembros.addActionListener(e -> atualizarTabelaTreinos());
        btnAdicionar.addActionListener(e -> adicionarTreino());
        // As ações para os botões de editar e excluir foram removidas daqui
    }

    private void carregarMembrosNoComboBox() {
        List<Membro> membros = membroController.listarMembros();
        comboMembros.addItem(null);
        for (Membro membro : membros) {
            comboMembros.addItem(membro);
        }
    }

    private void atualizarTabelaTreinos() {
        modeloTabela.setRowCount(0);
        Membro membroSelecionado = (Membro) comboMembros.getSelectedItem();
        if (membroSelecionado == null) return;

        List<Treino> treinos = treinoController.listarTreinosPorMembro(membroSelecionado.getId());
        for (Treino treino : treinos) {
            modeloTabela.addRow(new Object[]{
                    treino.getId(),
                    treino.getTipo(),
                    treino.getDescricao(),
                    treino.getDuracaoMinutos(),
                    treino.getDataInicio().format(dateFormatter)
            });
        }
    }

    private void adicionarTreino() {
        Membro membroSelecionado = (Membro) comboMembros.getSelectedItem();
        if (membroSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um membro primeiro para adicionar um treino.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FormularioTreinoPopUp dialog = new FormularioTreinoPopUp(this, null);
        dialog.setVisible(true);

        if (dialog.foiSalvo()) {
            Treino novoTreino = dialog.getTreino();
            novoTreino.setIdMembro(membroSelecionado.getId());
            if (treinoController.adicionarTreino(novoTreino)) {
                JOptionPane.showMessageDialog(this, "Treino adicionado com sucesso!");
                atualizarTabelaTreinos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar treino.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}