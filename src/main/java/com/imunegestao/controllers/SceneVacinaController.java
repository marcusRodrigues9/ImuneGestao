package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioVacina;
import com.imunegestao.utils.ValidacaoException;
import com.imunegestao.utils.ValidacoesVacina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SceneVacinaController extends BaseController {

    // =================== INSTÂNCIAS ===================
    private final RepositorioVacina repositorioVacina = RepositorioVacina.getInstancia();
    private final ObservableList<Vacina> listaVacinas = FXCollections.observableArrayList();

    // =================== COMPONENTES FXML ===================
    // --- Tabela ---
    @FXML private TableView<Vacina> tabela_vacinas;
    @FXML private TableColumn<Vacina, Integer> coluna_id;
    @FXML private TableColumn<Vacina, String> coluna_nome;
    @FXML private TableColumn<Vacina, String> coluna_fabricante;
    @FXML private TableColumn<Vacina, LocalDate> coluna_data_validade;
    @FXML private TableColumn<Vacina, Integer> coluna_doses_disponiveis;
    @FXML private TableColumn<Vacina, Integer> coluna_doses_recomendadas;
    @FXML private TableColumn<Vacina, Void> coluna_acao_vacina;

    // --- Campos Formulário ---
    @FXML private TextField campo_nome, campo_fabricante, campo_doses_disponiveis, campo_doses_recomendadas;
    @FXML private DatePicker campo_data_validade;

    // --- Navegação ---
    @FXML private AnchorPane formulario_vacina, tela_vacina;
    @FXML private TextField buscar_vacina;
    @FXML private MenuItem botao_menu_cadastrar_vacina, botao_menu_visualizar_vacina;
    @FXML private Button botao_sair;

    // =================== INICIALIZAÇÃO ===================

    @FXML
    public void initialize() {
        configurarTabela();
        carregarVacinas();

    }
    private void carregarVacinas() {
        listaVacinas.setAll(repositorioVacina.listarVacinas().values());
    }


    private void configurarTabela() {
        coluna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluna_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        coluna_data_validade.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
        coluna_doses_disponiveis.setCellValueFactory(new PropertyValueFactory<>("dosesDisponiveis"));
        coluna_doses_recomendadas.setCellValueFactory(new PropertyValueFactory<>("dosesRecomendadas"));
        adicionarColunaAcoes(); // adiciona a coluna de exclusão
        tabela_vacinas.setItems(listaVacinas);
    }

    // =================== AÇÕES DOS BOTÕES ===================

    @FXML
    private void salvarVacina() {
        String nome = campo_nome.getText();
        String fabricante = campo_fabricante.getText();
        String dosesRecomendadasStr = campo_doses_recomendadas.getText();
        String dosesDisponiveisStr = campo_doses_disponiveis.getText();
        LocalDate dataValidade = campo_data_validade.getValue();

        try {
            ValidacoesVacina.validar(nome, fabricante, dosesRecomendadasStr, dosesDisponiveisStr, dataValidade);

            int dosesRecomendadas = Integer.parseInt(dosesRecomendadasStr);
            int dosesDisponiveis = Integer.parseInt(dosesDisponiveisStr);

            Vacina nova = new Vacina(nome, fabricante, dosesDisponiveis, dosesRecomendadas, dataValidade);
            repositorioVacina.adicionarVacina(nova);
            listaVacinas.add(nova);
            mostrarAlertaInformacao("Vacina cadastrada com sucesso!\n\n" + formatarDados(nova));
            limparCampos();

        } catch (ValidacaoException e) {
            mostrarAlertaErro(e.getMessage());
        }

    }

    private String formatarDados(Vacina v) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = v.getDataValidade() != null ? v.getDataValidade().format(formatter) : "Não informada";

        return "ID: " + v.getId() +
                "\nNome: " + v.getNome() +
                "\nFabricante: " + v.getFabricante() +
                "\nDoses Recomendadas: " + v.getDosesRecomendadas() +
                "\nDoses Disponíveis: " + v.getDosesDisponiveis() +
                "\nData de Validade: " + dataFormatada;
    }
    private void excluirVacina(Vacina vacina) {
        repositorioVacina.listarVacinas().remove(vacina.getId());
        listaVacinas.remove(vacina);
        mostrarAlertaInformacao("Vacina excluída com sucesso.");
    }
    private void adicionarColunaAcoes() {
        coluna_acao_vacina.setCellFactory(param -> new TableCell<>() {
            private final Button excluir = new Button("Excluir");

            {
                String estiloPadrao = "-fx-background-color: #E4E1E2; -fx-text-fill: black; -fx-cursor: hand;";
                excluir.setStyle(estiloPadrao);

                // Efeito hover: vermelho
                excluir.setOnMouseEntered(e -> excluir.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-cursor: hand;"));
                excluir.setOnMouseExited(e -> excluir.setStyle(estiloPadrao));

                excluir.setOnAction(e -> excluirVacina(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(10, excluir));
            }
        });
    }


    // =================== NAVEGAÇÃO ENTRE TELAS ===================

    @FXML
    private void mostrar_formulario_vacina(ActionEvent event) {
        mostrarTela(formulario_vacina, tela_vacina);
    }

    @FXML
    private void mostrar_tabela_vacina(ActionEvent event) {
        iniciarTabela();
        carregarVacinas();
        mostrarTela(tela_vacina, formulario_vacina);
    }

    @FXML
    private void alterar_tela_agendamento(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Agendamentos.fxml", "Agendamento");
    }

    @FXML
    private void alterar_tela_paciente(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Paciente.fxml", "Pacientes");
    }

    @FXML
    public void sair(ActionEvent event) {
        super.sair(event);
    }

    // =================== FUNCIONALIDADES AUXILIARES ===================

    private void limparCampos() {
        campo_nome.clear();
        campo_fabricante.clear();
        campo_doses_disponiveis.clear();
        campo_doses_recomendadas.clear();
        campo_data_validade.setValue(null);
    }

    private void iniciarTabela() {
        listaVacinas.setAll(repositorioVacina.listarVacinas().values());
        tabela_vacinas.refresh();
    }

}
