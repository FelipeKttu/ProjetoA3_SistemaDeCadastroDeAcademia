package sistemacadastroacademia.view;

import sistemacadastroacademia.util.Database;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaPrincipal extends JFrame {

    //--- Componentes ---
    private JMenuBar menuBar;
    private JMenu menuGerenciamento, menuConsultas, menuSistema;
    private JMenuItem menuItemMembros, menuItemTreinos, menuItemPagamentos;
    private JMenuItem menuItemHistorico;
    private JMenuItem menuItemSair;

    public TelaPrincipal() {
        super("Sistema de Academia - Menu Principal");

        //--- Configuração da Janela ---
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        //--- Construção do Menu ---
        menuBar = new JMenuBar();

        // Menu "Gerenciamento"
        menuGerenciamento = new JMenu("Gerenciamento");
        menuItemMembros = new JMenuItem("Gerenciar Membros");
        menuItemTreinos = new JMenuItem("Gerenciar Treinos");
        menuItemPagamentos = new JMenuItem("Registrar Pagamento");
        menuGerenciamento.add(menuItemMembros);
        menuGerenciamento.add(menuItemTreinos);
        menuGerenciamento.add(menuItemPagamentos);
        menuBar.add(menuGerenciamento);

        // Menu "Consultas"
        menuConsultas = new JMenu("Consultas");
        menuItemHistorico = new JMenuItem("Histórico de Atividades");
        menuConsultas.add(menuItemHistorico);
        menuBar.add(menuConsultas);

        // Menu "Sistema"
        menuSistema = new JMenu("Sistema");
        menuItemSair = new JMenuItem("Sair");
        menuSistema.add(menuItemSair);
        menuBar.add(menuSistema);

        this.setJMenuBar(menuBar);

        //--- Eventos
        menuItemMembros.addActionListener(e -> new TelaCadastroMembro().setVisible(true));
        menuItemTreinos.addActionListener(e -> new TelaGerenciamentoTreinos().setVisible(true));
        menuItemPagamentos.addActionListener(e -> new TelaRegistroPagamentos().setVisible(true));
        menuItemHistorico.addActionListener(e -> new TelaHistoricoAtividades().setVisible(true));
        menuItemSair.addActionListener(e -> fecharAplicacao());

        // Ação para o botão 'X' da janela
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fecharAplicacao();
            }
        });
    }

    // Pede confirmação ao usuário, fecha a conexão com o banco e encerra a aplicação
    private void fecharAplicacao() {
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja sair do sistema?",
                "Confirmar Saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            System.out.println("Fechando a conexão com o banco de dados...");
            Database.closeConnection();
            System.exit(0);
        }
    }
}