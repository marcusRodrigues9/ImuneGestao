package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.agendamento.Agendamento;
import com.imunegestao.models.enums.StatusAgendamento;
import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioAgendamento;
import com.imunegestao.repository.RepositorioCidadao;
import com.imunegestao.repository.RepositorioVacina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SceneAgendamentoController extends BaseController {

    // Repositórios e lista observável
    private final RepositorioAgendamento repositorioAgendamento = RepositorioAgendamento.getInstancia();
    private final RepositorioVacina repositorioVacina = RepositorioVacina.getInstancia();

    private final ObservableList<Agendamento> listaAgendamentos = FXCollections.observableArrayList();
    private final RepositorioCidadao repositorioCidadao = RepositorioCidadao.getInstancia();

    // Componentes FXML
    @FXML private TextField campo_nome, campo_cpf, campo_hora;
    @FXML private TextField campo_id_vacina;
    @FXML private TextField campo_nome_vacina;
    @FXML private TextField campo_doses;
    @FXML private DatePicker campo_data;
    @FXML private TableView<Agendamento> tabela_agendamento;
    @FXML private TableColumn<Agendamento, Integer> coluna_id;
    @FXML private TableColumn<Agendamento, Integer> coluna_doses;

    @FXML private TableColumn<Agendamento, String> coluna_nome, coluna_cpf, coluna_hora, coluna_vacina;
    @FXML private TableColumn<Agendamento, LocalDate> coluna_data;
    @FXML private TableColumn<Agendamento, StatusAgendamento> coluna_status;
    @FXML private TableColumn<Agendamento, Void> coluna_acao;

    @FXML private MenuItem botao_menu_cadastrar_agendamento;
    @FXML private MenuItem botao_menu_visualizar_agendamento;

    @FXML private Button botao_sair;
    @FXML private Button realizar_cadastro_agendamento;

    @FXML private TextField buscar_agendamento;

    @FXML private AnchorPane formulario_agendamento;
    @FXML private AnchorPane tela_agendamento;

    // Inicialização
    @FXML
    public void initialize() {
        configurarTabela();
        campo_cpf.textProperty().addListener((obs, oldText, newText) -> {
            Cidadao c = repositorioCidadao.buscarCidadaoPorCpf(newText);
            if (c != null) {
                campo_nome.setText(c.getNome());
            } else {
                campo_nome.clear();
            }
        });
        campo_id_vacina.textProperty().addListener((obs, oldText, newText) -> {
            buscarVacinaPorId();
        });
        System.out.println("Vacinas no repositório ao iniciar agendamento:");
        repositorioVacina.listarVacinas().values().forEach(v -> System.out.println(v.getNome() + " - doses: " + v.getDosesDisponiveis()));
    }

    // Configura a tabela e colunas
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

        listaAgendamentos.setAll(repositorioAgendamento.listarAgendamentos().values());
        tabela_agendamento.setItems(listaAgendamentos);
    }

    // Coluna para alterar status via ComboBox
    private void adicionarColunaAcoes() {
        coluna_acao.setCellFactory(param -> new TableCell<>() {
            private final ComboBox<StatusAgendamento> comboStatus = new ComboBox<>();

            {
                comboStatus.getItems().addAll(StatusAgendamento.values());
                comboStatus.setOnAction(e -> {
                    Agendamento ag = getTableView().getItems().get(getIndex());
                    ag.setStatus(comboStatus.getValue());
                    tabela_agendamento.refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Agendamento ag = getTableView().getItems().get(getIndex());
                    comboStatus.setValue(ag.getStatus());
                    setGraphic(comboStatus);
                }
            }
        });
    }

    // Salvar agendamento
    @FXML
    private void salvarAgendamento() {
        String nome = campo_nome.getText();
        String cpf = campo_cpf.getText();
        String hora = campo_hora.getText();
        String id_vacina = campo_id_vacina.getText();
        String dosesStr = campo_doses.getText();

        int doses;
        int idVacina;
        try {
            doses = Integer.parseInt(dosesStr);
            idVacina = Integer.parseInt(id_vacina);
        } catch (NumberFormatException e) {
            mostrarAlertaErro("ID da vacina ou número de doses inválido.");
            return;
        }

        LocalDate data = campo_data.getValue();

        if (nome.isEmpty() || cpf.isEmpty() || hora.isEmpty() || data == null) {
            mostrarAlertaErro("Todos os campos devem ser preenchidos.");
            return;
        }

        Cidadao cidadao = repositorioCidadao.buscarCidadaoPorCpf(cpf);
        if (cidadao == null) {
            mostrarAlertaErro("Cidadão com CPF " + cpf + " não encontrado.");
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

        // Reduz as doses disponíveis
        vacina.setDosesDisponiveis(vacina.getDosesDisponiveis() - doses);

        // Cria o agendamento
        Agendamento novo = new Agendamento(cidadao, data, vacina,doses, hora, StatusAgendamento.AGENDADO);
        repositorioAgendamento.adicionarAgendamento(novo);
        listaAgendamentos.add(novo);

        limparCampos();
        mostrarAlertaInformacao("Agendamento cadastrado com sucesso!");
    }


    // Limpar campos do formulário
    private void limparCampos() {
        campo_nome.clear();
        campo_cpf.clear();
        campo_hora.clear();
        campo_data.setValue(null);
        campo_nome_vacina.clear();
        campo_id_vacina.clear();
        campo_doses.clear();
    }

    // Alternar visibilidade das telas
    @FXML
    void mostrar_formulario_agendamento(ActionEvent event) {
        mostrarTela(formulario_agendamento, tela_agendamento);
    }

    @FXML
    void mostrar_tabela_agendamento(ActionEvent event) {
        mostrarTela(tela_agendamento, formulario_agendamento);
    }

    // Trocar para tela de cidadão
    @FXML
    void alterar_tela_cidadao(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Cidadao.fxml", "Cidadãos");
    }

    // Trocar para tela de vacina
    @FXML
    void alterar_tela_vacina(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Vacinas.fxml", "Vacinas");
    }

    // Botão sair para tela de login
    @FXML
    public void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }

    // Buscar nome do cidadão pelo CPF (separado, caso use no FXML)
    @FXML
    private void buscarNomePorCpf() {
        String cpfDigitado = campo_cpf.getText();
        Cidadao cidadao = repositorioCidadao.buscarCidadaoPorCpf(cpfDigitado);

        if (cidadao != null) {
            campo_nome.setText(cidadao.getNome());
        } else {
            campo_nome.clear();
        }
    }
    @FXML
    private void buscarVacinaPorId() {
        String idDigitado = campo_id_vacina.getText();

        int idVacina;
        try {
            idVacina = Integer.parseInt(idDigitado);
        } catch (NumberFormatException e) {
            mostrarAlertaErro("ID da vacina inválido.");
            return;
        }

        Vacina vacina = repositorioVacina.buscarVacinaPorId(idVacina);
        if (vacina != null) {
            campo_nome_vacina.setText(vacina.getNome());
        } else {
            campo_nome_vacina.clear();
            mostrarAlertaErro("Vacina com ID " + idVacina + " não encontrada.");
        }
    }

}
