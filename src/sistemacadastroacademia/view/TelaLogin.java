package sistemacadastroacademia.view;

import sistemacadastroacademia.controller.FuncionarioController;
import sistemacadastroacademia.model.Funcionario;
import org.mindrot.jbcrypt.BCrypt; // Importa a biblioteca para verificação de senha

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    // --- Componentes Visuais ---
    private JLabel lblLogin, lblSenha;
    private JTextField txtLogin;
    private JPasswordField txtSenha; // Campo específico para senhas
    private JButton btnEntrar;

    // --- Controller para a Lógica de Backend ---
    private FuncionarioController funcionarioController;

    public TelaLogin() {
        super("Login - Sistema de Academia");

        // Instancia o controller
        this.funcionarioController = new FuncionarioController();

        // Configurações da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar esta janela
        this.setSize(400, 250); // Tamanho um pouco maior para a nova fonte
        this.setLocationRelativeTo(null); // Centraliza na tela

        // Painel principal com GridBagLayout para um alinhamento preciso
        JPanel painelLogin = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes

        // Inicializa os componentes
        lblLogin = new JLabel("Login:");
        txtLogin = new JTextField(15);

        lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField(15);

        btnEntrar = new JButton("Entrar");

        // --- Adicionando componentes ao painel (Layout) ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelLogin.add(lblLogin, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painelLogin.add(txtLogin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelLogin.add(lblSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelLogin.add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // O botão ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        painelLogin.add(btnEntrar, gbc);

        // Adiciona o painel à janela (JFrame)
        this.add(painelLogin);

        // --- Lógica do Botão Entrar ---
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin(); // Chama o método que contém a lógica do login
            }
        });
    }

     // Método que executa a lógica de verificação do login.
    private void realizarLogin() {
        String loginDigitado = txtLogin.getText();
        // Para JPasswordField, o método correto de pegar a senha é getPassword(), que retorna um char[]
        String senhaDigitada = new String(txtSenha.getPassword());

        // Validação simples para campos vazios
        if (loginDigitado.trim().isEmpty() || senhaDigitada.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha os campos de login e senha.",
                    "Campos Vazios",
                    JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução do método
        }

        // 1. Busca o funcionário no banco de dados pelo login
        Funcionario funcionarioDoBanco = funcionarioController.buscarFuncionarioPorLogin(loginDigitado);

        // 2. Verifica se o funcionário foi encontrado E se a senha está correta
        // BCrypt.checkpw compara a senha digitada (em texto plano) com o hash guardado no banco
        if (funcionarioDoBanco != null && BCrypt.checkpw(senhaDigitada, funcionarioDoBanco.getSenha())) {
            // Login bem-sucedido
            JOptionPane.showMessageDialog(this,
                    "Login realizado com sucesso! Bem-vindo(a), " + funcionarioDoBanco.getNome() + "!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Fecha a tela de login
            this.dispose();

            // Abre a próxima tela (Tela Principal do sistema)
            new TelaPrincipal().setVisible(true);

        } else {
            // Login falhou (usuário não existe ou senha incorreta)
            JOptionPane.showMessageDialog(this,
                    "Login ou senha inválidos. Por favor, tente novamente.",
                    "Erro de Login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}