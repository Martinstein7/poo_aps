package view.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserTabController {

    @FXML private Label userNameLabel;
    @FXML private Label statusLabel;
    @FXML private TableView<ConsumptionRecord> historyTable;
    @FXML private TableColumn<ConsumptionRecord, String> volumeColumn;
    @FXML private TableColumn<ConsumptionRecord, String> dateColumn;
    @FXML private TableColumn<ConsumptionRecord, Void> actionsColumn;

    // TODO Quando o banco SQLite estiver conectado, delete a MASTER_LIST.
    // A tableList será populada com "dao.buscarConsumosPorUsuario(nomeUsuarioLogado)"
    private ObservableList<ConsumptionRecord> MASTER_LIST = FXCollections.observableArrayList();
    private ObservableList<ConsumptionRecord> tableList = FXCollections.observableArrayList();

    private String nomeUsuarioLogado = "Desconhecido";
    private int numeroResidentes = 1;

    @FXML
    public void initialize() {
        volumeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.3f", cell.getValue().getVolumeM3())));
        dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDate()));

        configurarColunaDeAcoes();

        historyTable.setRowFactory(tv -> {
            TableRow<ConsumptionRecord> row = new TableRow<>();
            Tooltip tooltip = new Tooltip();
            tooltip.setShowDelay(Duration.millis(50));
            tooltip.setHideDelay(Duration.millis(50));
            tooltip.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    double litros = newItem.getVolumeM3() * 1000;
                    String status = (litros <= (110.0 * numeroResidentes)) ? "Ideal (Abaixo da média da ONU)" : "Acima da média da ONU";
                    String textoResumo = "Relatório de " + newItem.getDate() + "\nConsumo: " + litros + " Litros\nStatus: " + status;
                    tooltip.setText(textoResumo);
                    row.setTooltip(tooltip);
                } else {
                    row.setTooltip(null);
                }
            });
            return row;
        });

        // Dados inventados como se fosse o banco de dados
        MASTER_LIST.add(new ConsumptionRecord("1", "16/05/2026", 0.045, "Anderson"));
        MASTER_LIST.add(new ConsumptionRecord("2", "15/05/2026", 0.060, "Anderson"));
        MASTER_LIST.add(new ConsumptionRecord("3", "14/05/2026", 0.080, "Willian"));
        historyTable.setItems(tableList);
    }

    public void inicializarComUsuarioFicticio(String nome, String estado, int residentes) {
        this.nomeUsuarioLogado = nome;
        this.numeroResidentes = residentes;
        userNameLabel.setText(nomeUsuarioLogado);
        filtrarTabelaPorUsuarioAtivo();
    }

    @FXML
    void handleAlterarUsuario(ActionEvent event) {
        // TODO Substituir lista estática por "SELECT nome FROM usuarios"
        List<String> usuariosCadastrados = Arrays.asList("Anderson", "Willian", "Lucas", "Maria");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(nomeUsuarioLogado, usuariosCadastrados);
        dialog.setTitle("Trocar de Usuário");
        dialog.setHeaderText("Alternar contexto do sistema");
        dialog.setContentText("Selecione o perfil que deseja visualizar:");

        dialog.showAndWait().ifPresent(usuarioSelecionado -> {
            // TODO Substituir por busca real de residentes daquele usuário no banco
            int residentesSimulados = usuarioSelecionado.equals("Anderson") ? 3 : 2;
            inicializarComUsuarioFicticio(usuarioSelecionado, "SP", residentesSimulados);
        });
    }

    @FXML
    void handleAdicionarUsuario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/poo_aps/Registration.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);
            Stage stage = (Stage) userNameLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível carregar a tela de registro.");
        }
    }

    @FXML
    void handleExcluirUsuario(ActionEvent event) {
        // TODO Substituir lista por "SELECT nome FROM usuarios"
        List<String> usuariosCadastrados = Arrays.asList("Anderson", "Willian", "Lucas", "Maria");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(nomeUsuarioLogado, usuariosCadastrados);
        dialog.setTitle("Excluir Usuário");
        dialog.setHeaderText("Atenção: Esta ação apagará o perfil e todo o seu histórico hídrico.");
        dialog.setContentText("Selecione o usuário que deseja EXCLUIR:");

        dialog.showAndWait().ifPresent(usuarioParaExcluir -> {
            // TODO Executar DELETE no banco de dados aqui (tabela usuario e consumos)
            MASTER_LIST.removeIf(record -> record.getUserAdded().equals(usuarioParaExcluir));

            if (usuarioParaExcluir.equals(nomeUsuarioLogado)) {
                mostrarAlerta("Usuário Excluído", "Você excluiu o seu próprio perfil ativo. O sistema fará o logoff.");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/poo_aps/Registration.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) userNameLabel.getScene().getWindow();
                    stage.setScene(new Scene(root, 900, 600));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mostrarAlerta("Sucesso", "O usuário " + usuarioParaExcluir + " foi apagado do banco.");
                filtrarTabelaPorUsuarioAtivo();
            }
        });
    }

    @FXML
    void handleCalculoM3(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Calculadora m³ (Simulação)");
        dialog.setHeaderText("Insira o valor em m³ para calcular o status:");
        dialog.setContentText("m³:");

        dialog.showAndWait().ifPresent(m3 -> {
            try {
                double litros = Double.parseDouble(m3.replace(",", ".")) * 1000;
                mostrarAlerta("Resultado do Cálculo", litros + " Litros. Status: Simulação concluída (não salvo no BD).");
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "Valor inválido.");
            }
        });
    }

    @FXML
    void handleAdicionarConsumo(ActionEvent event) {
        abrirDialogoEdicaoInsercao(null);
    }

    private void filtrarTabelaPorUsuarioAtivo() {
        tableList.clear();
        for (ConsumptionRecord record : MASTER_LIST) {
            if (record.getUserAdded().equals(nomeUsuarioLogado)) {
                tableList.add(record);
            }
        }
        atualizarStatusMedia12Meses();
    }

    private void configurarColunaDeAcoes() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("E");
            private final Button deleteBtn = new Button("D");
            private final HBox pane = new HBox(10, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #FFB300; -fx-cursor: hand;");
                deleteBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-cursor: hand;");

                editBtn.setOnAction(event -> {
                    ConsumptionRecord record = getTableView().getItems().get(getIndex());
                    abrirDialogoEdicaoInsercao(record);
                });

                deleteBtn.setOnAction(event -> {
                    ConsumptionRecord record = getTableView().getItems().get(getIndex());
                    // TODO Executar DELETE FROM consumo WHERE id = record.getId()
                    MASTER_LIST.remove(record);
                    filtrarTabelaPorUsuarioAtivo();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void abrirDialogoEdicaoInsercao(ConsumptionRecord recordToEdit) {
        boolean isEdit = (recordToEdit != null);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Editar Consumo" : "Novo Consumo");
        dialog.setHeaderText(isEdit ? "Alterando registro existente" : "Adicionando novo registro de leitura");

        ButtonType saveButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField m3Field = new TextField();
        m3Field.setPromptText("Ex: 0.125");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Selecione a data");

        if (isEdit) {
            m3Field.setText(String.valueOf(recordToEdit.getVolumeM3()));
            try {
                datePicker.setValue(LocalDate.parse(recordToEdit.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (Exception e) {
                datePicker.setValue(LocalDate.now());
            }
        } else {
            datePicker.setValue(LocalDate.now());
        }

        grid.add(new Label("m³ gasto:"), 0, 0);
        grid.add(m3Field, 1, 0);
        grid.add(new Label("Data da Leitura:"), 0, 1);
        grid.add(datePicker, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(result -> {
            if (result == saveButtonType) {
                try {
                    double novoVolume = Double.parseDouble(m3Field.getText().replace(",", "."));
                    String novaData = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    if (isEdit) {
                        // TODO Executar UPDATE consumo SET volume = ?, data = ? WHERE id = ?
                        recordToEdit.setVolumeM3(novoVolume);
                        recordToEdit.setDate(novaData);
                        historyTable.refresh();
                        atualizarStatusMedia12Meses();
                    } else {
                        // TODO Executar INSERT INTO consumo (usuario_id, volume, data) VALUES (...)
                        MASTER_LIST.add(0, new ConsumptionRecord("NOVO_ID", novaData, novoVolume, nomeUsuarioLogado));
                        filtrarTabelaPorUsuarioAtivo();
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    mostrarAlerta("Erro", "Por favor, preencha a data e insira um número válido para m³.");
                }
            }
        });
    }

    private void atualizarStatusMedia12Meses() {
        double soma = 0;
        for (ConsumptionRecord r : tableList) soma += r.getVolumeM3();

        double mediaM3 = tableList.isEmpty() ? 0 : soma / tableList.size();
        double mediaLitros = mediaM3 * 1000;
        double metaCasa = 110.0 * numeroResidentes;

        if (mediaLitros <= metaCasa) {
            statusLabel.setText(String.format("Status: Seu consumo médio de %.1f L/dia está IDEAL e dentro da meta que a ONU propôs (%.1f L/dia para %d pessoa(s)).", mediaLitros, metaCasa, numeroResidentes));
            statusLabel.setStyle("-fx-text-fill: #4CAF50;");
        } else {
            statusLabel.setText(String.format("Status: Seu consumo médio de %.1f L/dia está ACIMA DA MÉDIA que a ONU propôs como ideal (%.1f L/dia).", mediaLitros, metaCasa));
            statusLabel.setStyle("-fx-text-fill: #F44336;");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static class ConsumptionRecord {
        private String id;
        private String date;
        private double volumeM3;
        private String userAdded; // Mantido para o Mock identificar de quem é

        public ConsumptionRecord(String id, String date, double volumeM3, String userAdded) {
            this.id = id; this.date = date; this.volumeM3 = volumeM3; this.userAdded = userAdded;
        }

        public String getId() { return id; }
        public String getDate() { return date; }
        public double getVolumeM3() { return volumeM3; }
        public String getUserAdded() { return userAdded; }

        public void setVolumeM3(double volumeM3) { this.volumeM3 = volumeM3; }
        public void setDate(String date) { this.date = date; }
    }
}