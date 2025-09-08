package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.agendamento.Agendamento;
import com.imunegestao.models.enums.StatusAgendamento;
import com.imunegestao.models.pessoas.Paciente;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioAgendamento;
import com.imunegestao.repository.RepositorioPaciente;
import com.imunegestao.repository.RepositorioVacina;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SceneAgendamentoController extends BaseController {

    // =================== INSTÂNCIAS ===================
    private final RepositorioAgendamento repositorioAgendamento = RepositorioAgendamento.getInstancia();
    private final RepositorioVacina repositorioVacina = RepositorioVacina.getInstancia();
    private final RepositorioPaciente repositorioPaciente = RepositorioPaciente.getInstancia();

    private final ObservableList<Agendamento> listaAgendamentosBase = FXCollections.observableArrayList();
    private final FilteredList<Agendamento> listaAgendamentosFiltrada = new FilteredList<>(listaAgendamentosBase, a -> true);
    private final SortedList<Agendamento> listaAgendamentos = new SortedList<>(listaAgendamentosFiltrada);

    // =================== COMPONENTES FXML ===================
    @FXML private TextField campo_nome, campo_cpf, campo_hora;
    @FXML private TextField campo_id_vacina, campo_nome_vacina, campo_doses;
    @FXML private DatePicker campo_data;
    @FXML private TableView<Agendamento> tabela_agendamento;
    @FXML private TableColumn<Agendamento, Integer> coluna_id, coluna_doses;
    @FXML private TableColumn<Agendamento, String> coluna_nome, coluna_cpf, coluna_hora, coluna_vacina;
    @FXML private TableColumn<Agendamento, LocalDate> coluna_data;
    @FXML private TableColumn<Agendamento, StatusAgendamento> coluna_status;
    @FXML private TableColumn<Agendamento, Void> coluna_acao;
    @FXML private MenuItem botao_menu_cadastrar_agendamento, botao_menu_visualizar_agendamento;
    @FXML private Button botao_sair, realizar_cadastro_agendamento;
    @FXML private TextField buscar_agendamento;
    @FXML private AnchorPane formulario_agendamento, tela_agendamento;
    @FXML private ToggleButton toggle_ocultar_cancelados;

    // =================== INICIALIZAÇÃO ===================
    @FXML
    public void initialize() {
        configurarTabela();
        configurarBusca();

        campo_cpf.textProperty().addListener((obs, oldText, newText) -> {
            Paciente c = repositorioPaciente.buscarPacientePorCpf(newText);
            campo_nome.setText(c != null ? c.getNome() : "");
        });

        campo_id_vacina.textProperty().addListener((obs, oldText, newText) -> buscarVacinaPorId());

        Platform.runLater(() -> {
            atualizarListaCompleta();
            debugListaAgendamentos();
        });
    }

    // =================== CONFIGURAÇÃO DA TABELA ===================
    private void configurarTabela() {
        coluna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluna_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        coluna_vacina.setCellValueFactory(new PropertyValueFactory<>("vacina"));
        coluna_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        coluna_hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        coluna_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        coluna_doses.setCellValueFactory(new PropertyValueFactory<>("doses"));

        adicionarColunaAcoes();
        configurarCoresLinhas();
        configurarOrdenacaoPorPrioridade();

        coluna_id.setVisible(false);
        listaAgendamentosBase.setAll(repositorioAgendamento.listarAgendamentos().values());
        tabela_agendamento.setItems(listaAgendamentos);
    }

    private void adicionarColunaAcoes() {
        coluna_acao.setCellFactory(param -> new TableCell<>() {
            private final ComboBox<StatusAgendamento> comboStatus = new ComboBox<>(FXCollections.observableArrayList(StatusAgendamento.values()));

            {
                comboStatus.setOnAction(e -> {
                    Agendamento ag = getTableView().getItems().get(getIndex());
                    StatusAgendamento novoStatus = comboStatus.getValue();

                    if (ag.getStatus() != novoStatus) {
                        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmacao.setTitle("Confirmar alteração");
                        confirmacao.setHeaderText("Alterar status do agendamento");
                        confirmacao.setContentText("Deseja alterar o status para " + novoStatus + "?");

                        if (confirmacao.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                            ag.setStatus(novoStatus);
                            atualizarListaCompleta();
                        } else {
                            comboStatus.setValue(ag.getStatus());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : comboStatus);
                if (!empty) {
                    comboStatus.setValue(getTableView().getItems().get(getIndex()).getStatus());
                }
            }
        });
    }

    private void configurarCoresLinhas() {
        tabela_agendamento.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Agendamento ag, boolean empty) {
                super.updateItem(ag, empty);
                getStyleClass().removeAll("row-realizado", "row-agendado", "row-confirmado", "row-cancelado");
                setStyle("");

                if (!empty && ag != null) {
                    switch (ag.getStatus()) {
                        case REALIZADO -> getStyleClass().add("row-realizado");
                        case AGENDADO -> getStyleClass().add("row-agendado");
                        case CONFIRMADO -> getStyleClass().add("row-confirmado");
                        case CANCELADO -> getStyleClass().add("row-cancelado");
                    }
                }
            }
        });
    }

    private void configurarOrdenacaoPorPrioridade() {
        listaAgendamentos.setComparator((a1, a2) -> {
            int p1 = obterPrioridadeStatus(a1.getStatus());
            int p2 = obterPrioridadeStatus(a2.getStatus());
            int cmp = Integer.compare(p1, p2);
            if (cmp != 0) return cmp;
            cmp = a1.getData().compareTo(a2.getData());
            return cmp != 0 ? cmp : a1.getHora().compareTo(a2.getHora());
        });
    }

    private int obterPrioridadeStatus(StatusAgendamento status) {
        return switch (status) {
            case AGENDADO -> 1;
            case CONFIRMADO -> 2;
            case REALIZADO -> 3;
            case CANCELADO -> 4;
            default -> 5;
        };
    }

    private void configurarBusca() {
        buscar_agendamento.textProperty().addListener((obs, oldText, newText) -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        String textoBusca = buscar_agendamento.getText().toLowerCase().trim();
        boolean ocultarCancelados = toggle_ocultar_cancelados.isSelected();

        listaAgendamentosFiltrada.setPredicate(ag -> {
            if (ocultarCancelados && ag.getStatus() == StatusAgendamento.CANCELADO) return false;
            if (textoBusca.isEmpty()) return true;
            return ag.getNome().toLowerCase().contains(textoBusca)
                    || ag.getCpf().contains(textoBusca)
                    || ag.getVacina().toLowerCase().contains(textoBusca)
                    || ag.getStatus().toString().toLowerCase().contains(textoBusca)
                    || ag.getData().toString().contains(textoBusca);
        });
    }

    private void atualizarListaCompleta() {
        listaAgendamentosBase.setAll(repositorioAgendamento.listarAgendamentos().values());
        Platform.runLater(() -> {
            aplicarFiltros();
            atualizarCoresTabela();
        });
    }

    private void atualizarCoresTabela() {
        Platform.runLater(() -> {
            tabela_agendamento.refresh();
            ObservableList<Agendamento> currentItems = tabela_agendamento.getItems();
            tabela_agendamento.setItems(null);
            tabela_agendamento.layout();
            tabela_agendamento.setItems(currentItems);
        });
    }

    // =================== AÇÕES DOS BOTÕES ===================
    @FXML private void salvarAgendamento() {
        String nome = campo_nome.getText();
        String cpf = campo_cpf.getText();
        String hora = campo_hora.getText();
        String id_vacina = campo_id_vacina.getText();
        String dosesStr = campo_doses.getText();
        LocalDate data = campo_data.getValue();

        int doses, idVacina;
        try {
            doses = Integer.parseInt(dosesStr);
            idVacina = Integer.parseInt(id_vacina);
        } catch (NumberFormatException e) {
            mostrarAlertaErro("ID da vacina ou número de doses inválido.");
            return;
        }

        if (nome.isEmpty() || cpf.isEmpty() || hora.isEmpty() || data == null) {
            mostrarAlertaErro("Todos os campos devem ser preenchidos.");
            return;
        }

        Paciente paciente = repositorioPaciente.buscarPacientePorCpf(cpf);
        if (paciente == null) {
            mostrarAlertaErro("Pacienteão com CPF " + cpf + " não encontrado.");
            return;
        }

        Vacina vacina = repositorioVacina.buscarVacinaPorId(idVacina);
        if (vacina == null) {
            mostrarAlertaErro("Vacina com ID " + idVacina + " não encontrada.");
            return;
        }

        if (vacina.getDosesDisponiveis() < doses) {
            mostrarAlertaErro("Doses insuficientes disponíveis para esta vacina.");
            return;
        }

        vacina.setDosesDisponiveis(vacina.getDosesDisponiveis() - doses);
        Agendamento novo = new Agendamento(paciente, data, vacina, doses, hora, StatusAgendamento.AGENDADO);
        repositorioAgendamento.adicionarAgendamento(novo);
        atualizarListaCompleta();
        limparCampos();
        mostrarAlertaInformacao("Agendamento cadastrado com sucesso!");
    }

    private void limparCampos() {
        campo_nome.clear();
        campo_cpf.clear();
        campo_hora.clear();
        campo_data.setValue(null);
        campo_nome_vacina.clear();
        campo_id_vacina.clear();
        campo_doses.clear();
    }

    @FXML private void mostrar_formulario_agendamento(ActionEvent event) {
        mostrarTela(formulario_agendamento, tela_agendamento);
    }

    @FXML private void mostrar_tabela_agendamento(ActionEvent event) {
        atualizarListaCompleta();
        mostrarTela(tela_agendamento, formulario_agendamento);
    }

    @FXML private void alterar_tela_paciente(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/scene-visualizar-paciente.fxml", "Pacienteãos");
    }

    @FXML private void alterar_tela_vacina(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/scene-visualizar-vacinas.fxml", "Vacinas");
    }

    @FXML public void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }

    @FXML private void buscarVacinaPorId() {
        String idDigitado = campo_id_vacina.getText().trim();

        if (idDigitado.isEmpty()) {
            campo_nome_vacina.clear();
            return;
        }

        try {
            int idVacina = Integer.parseInt(idDigitado);
            Vacina vacina = repositorioVacina.buscarVacinaPorId(idVacina);

            if (vacina != null) {
                campo_nome_vacina.setText(vacina.getNome());
            } else {
                campo_nome_vacina.clear();
                mostrarAlertaErro("Vacina com ID " + idVacina + " não encontrada.");
            }
        } catch (NumberFormatException e) {
            campo_nome_vacina.clear();
            mostrarAlertaErro("ID da vacina deve ser um número válido.");
        }
    }

    @FXML private void alternarFiltroCancelados() {
        Platform.runLater(() -> {
            aplicarFiltros();
            atualizarCoresTabela();
        });
    }

    public void debugListaAgendamentos() {
        System.out.println("=== DEBUG LISTA AGENDAMENTOS ===");
        System.out.println("Total no repositório: " + repositorioAgendamento.listarAgendamentos().size());
        System.out.println("Total na lista base: " + listaAgendamentosBase.size());
        System.out.println("Total na lista filtrada: " + listaAgendamentosFiltrada.size());
        System.out.println("Total na lista final (tabela): " + listaAgendamentos.size());

        listaAgendamentosBase.forEach(a ->
                System.out.println("  - " + a.getNome() + " (" + a.getStatus() + ")")
        );
        System.out.println("================================");
    }
}
