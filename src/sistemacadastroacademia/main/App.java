package sistemacadastroacademia.main;

import sistemacadastroacademia.view.TelaPrincipal;
import sistemacadastroacademia.view.TelaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {

        // Estas linhas definem globalmente o texto para os botões padrão do Swing.
        // Fazemos isso antes de criar qualquer componente gráfico.
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.okButtonText", "OK");

        // Tenta definir a aparência da aplicação para se parecer com o sistema operacional.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Não foi possível definir o Look and Feel do sistema.");
        }

        // Garante que a interface gráfica seja criada na thread correta do Swing.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Cria e torna a tela de login visível.
                new TelaPrincipal().setVisible(true);
            }
        });
    }
}