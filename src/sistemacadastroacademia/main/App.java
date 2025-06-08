package sistemacadastroacademia.main;

import sistemacadastroacademia.controller.FuncionarioController;
import sistemacadastroacademia.model.Funcionario;
import sistemacadastroacademia.view.TelaLogin;
import sistemacadastroacademia.util.Database;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class App {

    public static void main(String[] args) {

        // Garante que o usuário 'admin' sempre exista com a senha correta ('admin').
        try {
            System.out.println("Verificando configuração inicial do administrador...");
            FuncionarioController controller = new FuncionarioController();

            Funcionario admin = controller.buscarFuncionarioPorLogin("admin");

            if (admin == null) {
                System.out.println("Usuário 'admin' não encontrado. Criando usuário padrão...");
                Funcionario novoAdmin = new Funcionario("Administrador do Sistema", "Admin", "admin", "admin");
                if (controller.adicionarFuncionario(novoAdmin)) {
                    System.out.println("Usuário 'admin' padrão criado com sucesso! Use 'admin'/'admin' para o primeiro login.");
                } else {
                    System.err.println("Falha crítica ao criar o usuário 'admin' padrão.");
                }
            } else {
                System.out.println("Usuário 'admin' já existe. Configuração OK.");
            }
        } catch (Exception e) {
            System.err.println("Erro durante a verificação do usuário admin: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fecha a conexão usada para esta verificação inicial.
            Database.closeConnection();
        }


        // configuração da aparência
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Define uma fonte padrão mais bonita para a aplicação.
            setUIFont(new FontUIResource("Segoe UI", Font.PLAIN, 16)); // Use "Arial" se "Segoe UI" não estiver disponível

            // Define cores globais para um tema mais limpo
            Color corDeFundo = new Color(238, 238, 240);
            UIManager.put("control", corDeFundo);
            UIManager.put("nimbusBase", new Color(80, 140, 200));
            UIManager.put("nimbusFocus", new Color(100, 160, 220));
            UIManager.put("nimbusLightBackground", new ColorUIResource(Color.WHITE));
            UIManager.put("Panel.background", new ColorUIResource(corDeFundo));
            UIManager.put("Window.background", new ColorUIResource(corDeFundo));
            UIManager.put("Frame.background", new ColorUIResource(corDeFundo));
            UIManager.put("MenuBar.background", new ColorUIResource(corDeFundo));
            UIManager.put("Menu.background", new ColorUIResource(corDeFundo));
            UIManager.put("MenuItem.background", new ColorUIResource(corDeFundo));
            UIManager.put("PopupMenu.background", new ColorUIResource(corDeFundo));
            UIManager.put("Menu.foreground", new ColorUIResource(Color.BLACK));
            UIManager.put("MenuItem.foreground", new ColorUIResource(Color.BLACK));

            // melhora o espaçamento nos JOptionPane
            UIManager.put("OptionPane.messageAreaBorder", BorderFactory.createEmptyBorder(20, 20, 20, 20));
            UIManager.put("OptionPane.background", new ColorUIResource(corDeFundo));

            // traduz os botões padrão do JOptionPane para Português
            UIManager.put("OptionPane.yesButtonText", "Sim");
            UIManager.put("OptionPane.noButtonText", "Não");
            UIManager.put("OptionPane.cancelButtonText", "Cancelar");
            UIManager.put("OptionPane.okButtonText", "OK");

        } catch (Exception e) {
            // se o tema Nimbus não estiver disponível ou ocorrer outro erro, a aplicação continuará com o padrão.
            System.err.println("Erro ao configurar a estilização da UI.");
            e.printStackTrace();
        }

        //inicia a aplicação
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }

    /**
     * Método auxiliar para definir a fonte padrão para todos os componentes Swing.
     * @param f A fonte a ser definida.
     */
    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}