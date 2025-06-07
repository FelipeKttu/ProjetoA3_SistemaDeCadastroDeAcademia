package sistemacadastroacademia.view;

import sistemacadastroacademia.controller.MembroController;
import sistemacadastroacademia.controller.PagamentoController;
import sistemacadastroacademia.model.Membro;
import sistemacadastroacademia.model.Pagamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaRegistroPagamentos extends JFrame {

    // --- Componentes Visuais ---
    private JComboBox<Membro> comboMembros;
    private JTable tabelaPagamentos;
    private DefaultTableModel modeloTabela;
    private JButton btnRegistrarPagamento;

    // --- Controllers ---
    private MembroController membroController;
    private PagamentoController pagamentoController;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaRegistroPagamentos() {
        super("Gerenciamento de Pagamentos");

        this.membroController = new MembroController();
        this.pagamentoController = new PagamentoController();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        // --- Painel Superior ---
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        painelSuperior.add(new JLabel("Selecione o Membro:"));
        comboMembros = new JComboBox<>();
        comboMembros.setPreferredSize(new Dimension(300, 25));
        painelSuperior.add(comboMembros);
        this.add(painelSuperior, BorderLayout.NORTH);

        // --- Tabela de Pagamentos ---
        String[] colunas = {"ID", "Valor (R$)", "Data Pagamento", "Status", "Método", "Vencimento"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaPagamentos = new JTable(modeloTabela);
        this.add(new JScrollPane(tabelaPagamentos), BorderLayout.CENTER);

        // --- Painel Inferior: Botão de Ação ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        btnRegistrarPagamento = new JButton("Registrar Novo Pagamento");
        painelBotoes.add(btnRegistrarPagamento);
        this.add(painelBotoes, BorderLayout.SOUTH);

        carregarMembrosNoComboBox();

        comboMembros.addActionListener(e -> atualizarTabelaPagamentos());
        btnRegistrarPagamento.addActionListener(e -> registrarNovoPagamento());

        if (comboMembros.getItemCount() > 0) {
            comboMembros.setSelectedIndex(0);
        }
    }

    private void carregarMembrosNoComboBox() {
        List<Membro> membros = membroController.listarMembros();
        comboMembros.addItem(null);
        for (Membro membro : membros) {
            comboMembros.addItem(membro);
        }
    }

    private void atualizarTabelaPagamentos() {
        modeloTabela.setRowCount(0);
        Membro membroSelecionado = (Membro) comboMembros.getSelectedItem();
        if (membroSelecionado == null) return;

        List<Pagamento> pagamentos = pagamentoController.listarPagamentosPorMembro(membroSelecionado.getId());
        for (Pagamento p : pagamentos) {
            modeloTabela.addRow(new Object[]{
                    p.getId(),
                    p.getValor(),
                    p.getDataPagamento().format(dateFormatter),
                    p.getStatus(),
                    p.getMetodoPagamento(),
                    p.getDataVencimento() != null ? p.getDataVencimento().format(dateFormatter) : ""
            });
        }
    }

    private void registrarNovoPagamento() {
        Membro membroSelecionado = (Membro) comboMembros.getSelectedItem();
        if (membroSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um membro para registrar um pagamento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FormularioPagamentoPopUp dialog = new FormularioPagamentoPopUp(this);
        dialog.setVisible(true);

        if (dialog.foiSalvo()) {
            Pagamento novoPagamento = dialog.getPagamento();
            novoPagamento.setIdMembro(membroSelecionado.getId());
            if (pagamentoController.registrarPagamento(novoPagamento)) {
                JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso!");
                atualizarTabelaPagamentos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao registrar o pagamento.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}