package sistemacadastroacademia.view;

import sistemacadastroacademia.controller.HistoricoAtividadeController;
import sistemacadastroacademia.controller.MembroController;
import sistemacadastroacademia.model.HistoricoAtividade;
import sistemacadastroacademia.model.Membro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaHistoricoAtividades extends JFrame {

    // --- Componentes Visuais ---
    private JComboBox<Membro> comboMembros;
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;

    // --- Controllers ---
    private MembroController membroController;
    private HistoricoAtividadeController historicoController;

    // Formatter para exibir data e hora de forma legível
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm:ss");

    public TelaHistoricoAtividades() {
        super("Consulta de Histórico de Atividades");

        // Instancia os controllers
        this.membroController = new MembroController();
        this.historicoController = new HistoricoAtividadeController();

        // Configurações da Janela
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        // --- Painel Superior: Seleção de Membro ---
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        painelSuperior.add(new JLabel("Selecione o Membro para ver o Histórico:"));
        comboMembros = new JComboBox<>();
        comboMembros.setPreferredSize(new Dimension(300, 25));
        painelSuperior.add(comboMembros);
        this.add(painelSuperior, BorderLayout.NORTH);

        // --- Centro: Tabela de Histórico ---
        String[] colunas = {"ID", "Data e Hora", "Atividade", "Duração (min)", "Observações", "ID Funcionário Resp."};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        tabelaHistorico = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaHistorico);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        // --- Carregar Dados Iniciais ---
        carregarMembrosNoComboBox();

        // --- Lógica dos Eventos ---
        comboMembros.addActionListener(e -> atualizarTabelaHistorico());

        // Atualiza a tabela uma primeira vez para o primeiro membro da lista (se houver)
        if (comboMembros.getItemCount() > 0) {
            comboMembros.setSelectedIndex(0);
        }
    }

    private void carregarMembrosNoComboBox() {
        try {
            List<Membro> membros = membroController.listarMembros();
            comboMembros.addItem(null); // Item para "Nenhum selecionado"
            for (Membro membro : membros) {
                comboMembros.addItem(membro);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar lista de membros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabelaHistorico() {
        modeloTabela.setRowCount(0);
        Membro membroSelecionado = (Membro) comboMembros.getSelectedItem();
        if (membroSelecionado == null) return;

        try {
            List<HistoricoAtividade> historico = historicoController.listarAtividadesPorMembro(membroSelecionado.getId());
            for (HistoricoAtividade ha : historico) {
                String dataHoraFormatada = "";
                if (ha.getDataHoraAtividade() != null) {
                    dataHoraFormatada = ha.getDataHoraAtividade().format(dateTimeFormatter);
                }

                modeloTabela.addRow(new Object[]{
                        ha.getId(),
                        dataHoraFormatada,
                        ha.getAtividade(),
                        ha.getTempoExecucaoMinutos(),
                        ha.getObservacoes(),
                        ha.getIdFuncionarioResponsavel()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico do membro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}