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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<String> usuarios = usuarioDAO.listarNomesUsuarios();

        if (!usuarios.isEmpty()) {
            Usuario usuarioAtivo = usuarioDAO.buscarPorNome(usuarios.get(0));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poo_aps/UserTab.fxml"));
            root = loader.load();

            UserTabController userTabController = loader.getController();
            userTabController.inicializarComUsuarioReal(usuarioAtivo);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poo_aps/Registration.fxml"));
            root = loader.load();
        }

        Scene scene = new Scene(root, 900, 600);

        try {
            String cssPath = getClass().getResource("/poo_aps/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("Aviso: Arquivo style.css não encontrado.");
        }

        primaryStage.setTitle("POO_APS - Sistema de Monitoramento Hídrico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}