import view.Interface;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();//visual modo escuro
            configurarEstiloGlobal();//estilizando botoes do pane estilo win10+
            //abre a interface
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    Interface tela = new Interface();
                    tela.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Erro ao abrir a interface: " + e.getMessage());
                }
            });

        } catch (Exception ex) {
            System.err.println("Falha ao iniciar o tema visual: " + ex.getMessage());
        }
    }

    //definindo que os paineis de erro estejam da mesma cor do programa
    private static void configurarEstiloGlobal() {
        //definindo cores com base no tema do projeto :)
        Color azulD = new Color(0, 120, 215); // Azul moderno
        Color fundoEscuro = new Color(15, 15, 25);   // Preto azulado

        UIManager.put("Button.arc", 999);            // Botões ovais
        UIManager.put("Component.arc", 15);          // Campos de texto e combos
        UIManager.put("TextComponent.arc", 15);
        UIManager.put("CheckBox.arc", 5);

        // Estilizando Pane
        UIManager.put("OptionPane.background", fundoEscuro);
        UIManager.put("Panel.background", fundoEscuro);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        // cor dos botes
        UIManager.put("Button.background", azulD);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.hoverBackground", azulD.brighter());


    }
}