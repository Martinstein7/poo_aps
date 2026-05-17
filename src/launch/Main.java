package launch;

import dao.UsuarioDAO;
import model.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controller.UserTabController;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;

        dao.UsuarioDAO usuarioDAO = new dao.UsuarioDAO();
        java.util.List<String> usuarios = usuarioDAO.listarNomesUsuarios();

        // Aponta exatamente para a pasta onde os FXMLs estão na sua árvore de arquivos
        java.net.URL userTabUrl = getClass().getResource("/view/fxml/UserTab.fxml");
        java.net.URL registrationUrl = getClass().getResource("/view/fxml/Registration.fxml");

        if (userTabUrl == null || registrationUrl == null) {
            System.out.println("ERRO CRÍTICO: Arquivos FXML não encontrados em /view/fxml/");
            System.out.println("Caminho UserTab: " + userTabUrl);
            System.out.println("Caminho Registration: " + registrationUrl);
            throw new java.io.FileNotFoundException("Verifique o posicionamento dos arquivos FXML.");
        }

        if (!usuarios.isEmpty()) {
            model.Usuario usuarioAtivo = usuarioDAO.buscarPorNome(usuarios.get(0));

            FXMLLoader loader = new FXMLLoader(userTabUrl);
            root = loader.load();

            view.controller.UserTabController userTabController = loader.getController();
            userTabController.inicializarComUsuarioReal(usuarioAtivo);
        } else {
            FXMLLoader loader = new FXMLLoader(registrationUrl);
            root = loader.load();
        }

        Scene scene = new Scene(root, 900, 600);

        try {
            // Aponta exatamente para onde o seu arquivo CSS está
            java.net.URL cssUrl = getClass().getResource("/view/fxml/style.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.out.println("Aviso: Arquivo style.css não pôde ser carregado.");
        }

        primaryStage.setTitle("POO_APS - Sistema de Monitoramento Hídrico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}