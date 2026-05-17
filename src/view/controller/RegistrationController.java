package view.controller;

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
    // TODO substituir por um comando Select de estados
    public void initialize() {
        stateComboBox.getItems().addAll("SP", "RJ", "MG", "BA", "PR", "Outros");
    }

    @FXML
    private void handleConfirmAction(ActionEvent event) {
        String name = nameField.getText();
        String state = stateComboBox.getValue();
        String residentsStr = residentsField.getText();

        if (name.isEmpty() || state == null || residentsStr.isEmpty()) {
            feedbackLabel.setText("Por favor, preencha todos os campos.");
            return;
        }

        try {
            int residents = Integer.parseInt(residentsStr);
            salvarUsuarioNoBackend(name, state, residents);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("O número de residentes deve ser um valor inteiro.");
        }
    }

    // TODO Substituir os prints abaixo por chamadas reais de Usuário
    private void salvarUsuarioNoBackend(String name, String state, int residents) {
        System.out.println("Mock Backend: Salvando usuário -> Nome: " + name + ", Estado: " + state + ", Residentes: " + residents);

        feedbackLabel.setStyle("-fx-text-fill: #43A047;");
        feedbackLabel.setText("Cadastro realizado com sucesso! Redirecionando...");

        Platform.runLater(() -> {
            irParaAbaPrincipal(name, state, residents);
        });
    }

    private void irParaAbaPrincipal(String nome, String estado, int residentes) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poo_aps/UserTab.fxml"));
            Parent root = loader.load();

            UserTabController userTabController = loader.getController();
            userTabController.inicializarComUsuarioFicticio(nome, estado, residentes);

            Scene scene = new Scene(root, 900, 600);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar abrir a UserTab: Verifique o caminho do arquivo!");
        }
    }
}
