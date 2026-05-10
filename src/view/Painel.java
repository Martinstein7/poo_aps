package view;

import javax.swing.*;

public class Painel {

    public void msgE (String txt) {
        JOptionPane.showMessageDialog(null, txt, "Erro de validação", JOptionPane.ERROR_MESSAGE);
    }//Mensagem de erro para caso o usuario digite algum componente errado

    public void msgR (String txt) {
        JOptionPane.showMessageDialog(null, txt, "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
}
