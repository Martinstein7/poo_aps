package view.controller;

import dao.ConsumoDAO;
import dao.UsuarioDAO;
import model.Consumo;
import model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserTabController {

    @FXML private Label userNameLabel;
    @FXML private Label statusLabel;
    @FXML private TableView<Consumo> historyTable;
    @FXML private TableColumn<Consumo, String> volumeColumn;
    @FXML private TableColumn<Consumo, String> dateColumn;
    @FXML private TableColumn<Consumo, Void> actionsColumn;

    private ObservableList<Consumo> tableList = FXCollections.observableArrayList();
    private Usuario usuarioAtivo;

    @FXML
    public void initialize() {
        volumeColumn.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.3f", cell.getValue().getM3Gastos())));
        dateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDataLeitura()));

        configurarColunaDeAcoes();

        historyTable.setRowFactory(tv -> {
            TableRow<Consumo> row = new TableRow<>();
            Tooltip tooltip = new Tooltip();
            tooltip.setShowDelay(Duration.millis(50));
            tooltip.setHideDelay(Duration.millis(50));
            tooltip.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null && usuarioAtivo != null) {
                    double litros = newItem.getM3Gastos() * 1000;
                    double mediaDiariaPessoa = litros / (usuarioAtivo.getNumResidentes() * 30);

                    String status = (mediaDiariaPessoa <= 110.0) ? "Ideal (Dentro da média da ONU)" : "Acima da média da ONU";
                    String textoResumo = "Relatório de " + newItem.getDataLeitura() + "\nConsumo Total: " + litros + " Litros\nPor Pessoa: " + String.format("%.1f", mediaDiariaPessoa) + " L/dia\nStatus: " + status;
                    tooltip.setText(textoResumo);
                    row.setTooltip(tooltip);
                } else {
                    row.setTooltip(null);
                }
            });
            return row;
        });

        historyTable.setItems(tableList);
    }

    public void inicializarComUsuarioReal(Usuario usuario) {
        this.usuarioAtivo = usuario;
        userNameLabel.setText(usuarioAtivo.getNome());
        atualizarTabelaBancoDados();
    }

    @FXML
    void handleAlterarUsuario(ActionEvent event) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<String> usuariosCadastrados = usuarioDAO.listarNomesUsuarios();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(usuarioAtivo.getNome(), usuariosCadastrados);
        dialog.setTitle("Trocar de Usuário");
        dialog.setHeaderText("Alternar contexto do sistema");
        dialog.setContentText("Selecione o perfil que deseja visualizar:");

        dialog.showAndWait().ifPresent(usuarioSelecionado -> {
            Usuario usuarioEncontrado = usuarioDAO.buscarPorNome(usuarioSelecionado);
            if (usuarioEncontrado != null) {
                inicializarComUsuarioReal(usuarioEncontrado);
            }
        });
    }

    @FXML
    void handleAdicionarUsuario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/Registration.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 600);
            Stage stage = (Stage) userNameLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível carregar a tela de registro.");
        }
    }

    @FXML
    void handleExcluirUsuario(ActionEvent event) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<String> usuariosCadastrados = usuarioDAO.listarNomesUsuarios();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(usuarioAtivo.getNome(), usuariosCadastrados);
        dialog.setTitle("Excluir Usuário");
        dialog.setHeaderText("Atenção: Esta ação apagará o perfil e todo o seu histórico hídrico.");
        dialog.setContentText("Selecione o usuário que deseja EXCLUIR:");

        dialog.showAndWait().ifPresent(usuarioParaExcluir -> {
            Usuario encontrado = usuarioDAO.buscarPorNome(usuarioParaExcluir);
            if (encontrado != null) {
                usuarioDAO.excluirUsuario(encontrado.getIdUsuario());

                if (encontrado.getIdUsuario() == usuarioAtivo.getIdUsuario()) {
                    mostrarAlerta("Usuário Excluído", "Você excluiu o seu próprio perfil ativo. O sistema fará o logoff.");
                    handleAdicionarUsuario(null);
                } else {
                    mostrarAlerta("Sucesso", "O usuário " + usuarioParaExcluir + " foi apagado do banco.");
                    atualizarTabelaBancoDados();
                }
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
                double litrosTotal = Double.parseDouble(m3.replace(",", ".")) * 1000;
                double perCapita = litrosTotal / (usuarioAtivo.getNumResidentes() * 30);

                calc.CalcAgua calculadora = new calc.CalcAgua();
                String resultadoOnu = calculadora.ConsumoONU(perCapita);

                mostrarAlerta("Resultado da Simulação", String.format("Gasto Total: %.1f Litros\nPor Pessoa: %.1f L/dia\n\nStatus ONU: %s", litrosTotal, perCapita, resultadoOnu));
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "Valor inválido.");
            }
        });
    }

    @FXML
    void handleAdicionarConsumo(ActionEvent event) {
        abrirDialogoEdicaoInsercao(null);
    }

    private void atualizarTabelaBancoDados() {
        tableList.clear();
        if (usuarioAtivo != null) {
            ConsumoDAO consumoDAO = new ConsumoDAO();
            List<Consumo> listaDoBanco = consumoDAO.listarPorUsuario(usuarioAtivo.getIdUsuario());
            tableList.addAll(listaDoBanco);
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
                    Consumo record = getTableView().getItems().get(getIndex());
                    abrirDialogoEdicaoInsercao(record);
                });

                deleteBtn.setOnAction(event -> {
                    Consumo record = getTableView().getItems().get(getIndex());
                    ConsumoDAO consumoDAO = new ConsumoDAO();
                    consumoDAO.deletarConsumo(record.getIdConsumo());
                    atualizarTabelaBancoDados();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void abrirDialogoEdicaoInsercao(Consumo consumoParaEditar) {
        boolean isEdit = (consumoParaEditar != null);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Editar Consumo" : "Novo Consumo");
        dialog.setHeaderText(isEdit ? "Alterando registro existente" : "Adicionando novo registro de leitura");

        ButtonType saveButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField m3Field = new TextField();
        m3Field.setPromptText("Ex: 12.5");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Selecione a data");

        if (isEdit) {
            m3Field.setText(String.valueOf(consumoParaEditar.getM3Gastos()));
            try {
                datePicker.setValue(LocalDate.parse(consumoParaEditar.getDataLeitura(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

                    ConsumoDAO consumoDAO = new ConsumoDAO();

                    if (isEdit) {
                        consumoParaEditar.setM3Gastos(novoVolume);
                        consumoParaEditar.setDataLeitura(novaData);
                        consumoDAO.atualizarConsumo(consumoParaEditar);
                    } else {
                        Consumo novo = new Consumo(0, novoVolume, novaData, usuarioAtivo.getIdUsuario());
                        consumoDAO.registrarConsumo(novo);
                    }
                    atualizarTabelaBancoDados();
                } catch (Exception e) {
                    mostrarAlerta("Erro", "Por favor, preencha os campos com valores válidos.");
                }
            }
        });
    }

    private void atualizarStatusMedia12Meses() {
        if (tableList.isEmpty()) {
            statusLabel.setText("Nenhum registro de consumo encontrado para este perfil.");
            statusLabel.setStyle("-fx-text-fill: #ffffff;");
            return;
        }

        double somaM3 = 0;
        for (Consumo c : tableList) {
            somaM3 += c.getM3Gastos();
        }

        double mediaM3Mensal = somaM3 / tableList.size();
        double mediaLitrosTotalCasa = mediaM3Mensal * 1000;
        double mediaLitrosPorPessoaDiaria = mediaLitrosTotalCasa / (usuarioAtivo.getNumResidentes() * 30);

        dao.EstadoDAO estadoDAO = new dao.EstadoDAO();
        model.Estado estadoReal = estadoDAO.buscarPorSigla(usuarioAtivo.getIdEstado());

        String textoValorConta = "";
        if (estadoReal != null) {
            calc.CalcAgua calculadora = new calc.CalcAgua();
            try {
                double valorEstimado = calculadora.Calculo(mediaM3Mensal, null, estadoReal);
                textoValorConta = String.format("\nValor estimado da conta (com esgoto de %s): R$ %.2f", estadoReal.getNome(), valorEstimado);
            } catch (Exception e) {
                textoValorConta = String.format("\nEstado: %s (Coef. Esgoto: %.2f)", estadoReal.getNome(), estadoReal.getCoef_Esg());
            }
        }

        calc.CalcAgua calculadora = new calc.CalcAgua();
        String mensagemOnu = calculadora.ConsumoONU(mediaLitrosPorPessoaDiaria);

        statusLabel.setText(String.format("Sua média residencial mensal é de %.1f Litros.\nMédia diária por pessoa: %.1f L/dia%s\n\n%s",
                mediaLitrosTotalCasa, mediaLitrosPorPessoaDiaria, textoValorConta, mensagemOnu));

        if (mediaLitrosPorPessoaDiaria < (110 * 0.3)) {
            statusLabel.setStyle("-fx-text-fill: #FF9800;");
        } else if (mediaLitrosPorPessoaDiaria <= 110) {
            statusLabel.setStyle("-fx-text-fill: #4CAF50;");
        } else {
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
}