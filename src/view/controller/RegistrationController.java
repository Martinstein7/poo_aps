package view.controller;

import dao.UsuarioDAO;
import model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> stateComboBox;
    @FXML private TextField residentsField;
    @FXML private Button confirmButton;
    @FXML private Label feedbackLabel;

    @FXML
    public void initialize() {
        stateComboBox.getItems().addAll("SP", "RJ", "MG", "BA", "PR", "AC", "AL", "AP", "AM", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "PA", "PB", "PE", "PI", "RN", "RS", "RO", "RR", "SC", "SE", "TO");
    }

    @FXML
    private void handleConfirmAction(ActionEvent event) {
        String name = nameField.getText().trim();
        String state = stateComboBox.getValue();
        String residentsStr = residentsField.getText().trim();

        if (name.isEmpty() || state == null || residentsStr.isEmpty()) {
            feedbackLabel.setStyle("-fx-text-fill: #e53935;");
            feedbackLabel.setText("Por favor, preencha todos os campos.");
            return;
        }

        try {
            int residents = Integer.parseInt(residentsStr);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioExistente = usuarioDAO.buscarPorNome(name);

            if (usuarioExistente != null) {
                feedbackLabel.setStyle("-fx-text-fill: #64B5F6;");
                feedbackLabel.setText("Usuário encontrado! Entrando...");
                Platform.runLater(() -> irParaAbaPrincipal(usuarioExistente));
            } else {
                Usuario novoUsuario = new Usuario(0, name, residents, state);
                usuarioDAO.registrarUsuario(novoUsuario);

                Usuario usuarioCadastrado = usuarioDAO.buscarPorNome(name);

                feedbackLabel.setStyle("-fx-text-fill: #43A047;");
                feedbackLabel.setText("Cadastro realizado com sucesso! Redirecionando...");
                Platform.runLater(() -> irParaAbaPrincipal(usuarioCadastrado));
            }

        } catch (NumberFormatException e) {
            feedbackLabel.setStyle("-fx-text-fill: #e53935;");
            feedbackLabel.setText("O número de residentes deve ser um valor inteiro.");
        }
    }

    private void irParaAbaPrincipal(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poo_aps/UserTab.fxml"));
            Parent root = loader.load();

            UserTabController userTabController = loader.getController();
            userTabController.inicializarComUsuarioReal(usuario);

            Scene scene = new Scene(root, 900, 600);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar abrir a UserTab: Verifique o caminho do arquivo!");
        }
    }
}