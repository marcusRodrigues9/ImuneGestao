package com.imunegestao.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.imunegestao.models.pessoas.Paciente;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioPaciente;
import com.imunegestao.repository.RepositorioVacina;
import com.imunegestao.utils.ValidacaoException;
import com.imunegestao.utils.ValidacoesPaciente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ScenePacienteController extends BaseController {

    // =================== INSTÂNCIAS ===================
    private final RepositorioPaciente repositorioPaciente = RepositorioPaciente.getInstancia();
    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private Paciente pacienteEmEdicao = null;

    // =================== COMPONENTES FXML ===================
    // --- Tabela ---
    @FXML private TableView<Paciente> tabela_pacientes;
    @FXML private TableColumn<Paciente, Integer> coluna_id_paciente;
    @FXML private TableColumn<Paciente, String> coluna_nome_paciente;
    @FXML private TableColumn<Paciente, String> coluna_cpf_paciente;
    @FXML private TableColumn<Paciente, Integer> coluna_idade_paciente;
    @FXML private TableColumn<Paciente, String> coluna_sexo_paciente;
    @FXML private TableColumn<Paciente, String> coluna_endereco_paciente;
    @FXML private TableColumn<Paciente, String> coluna_email_paciente;
    @FXML private TableColumn<Paciente, String> coluna_telefone_paciente;
    @FXML private TableColumn<Paciente, Void> coluna_acao_paciente;

    // --- Campos Formulário ---
    @FXML private TextField campo_nome, campo_cpf, campo_endereco, campo_idade, campo_email, campo_telefone;
    @FXML private RadioButton masculino, feminino;
    @FXML private ToggleGroup sexo_paciente;

    // --- Navegação ---
    @FXML private TextField buscar_paciente;
    @FXML private AnchorPane formulario_paciente, tela_paciente;
    @FXML private MenuItem botao_menu_cadastrar_paciente, botao_menu_visualizar_paciente;

    // =================== INICIALIZAÇÃO ===================

    @FXML
    public void initialize() {
        carregarPacientesIniciais();
        configurarTabela();
        configurarBuscaPaciente();
    }

    private void carregarPacientesIniciais() {

        //  Pegando as vacinas que foram criadas na classe Main
        RepositorioVacina repositorioVacina = RepositorioVacina.getInstancia();
        List<Vacina> vacinasDisponiveis = new ArrayList<>(repositorioVacina.listarVacinas().values());
        Vacina vacinaGripe = vacinasDisponiveis.get(0);
        Vacina vacinaCovid = vacinasDisponiveis.get(1);

        if (repositorioPaciente.listarPacientes().isEmpty()) {
            System.out.println("Pacientes Vazio");
        }


        listaPacientes.setAll(repositorioPaciente.listarPacientes().values());
    }

    private void configurarTabela() {
        coluna_id_paciente.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluna_nome_paciente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_cpf_paciente.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        coluna_idade_paciente.setCellValueFactory(new PropertyValueFactory<>("idade"));
        coluna_sexo_paciente.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        coluna_endereco_paciente.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        coluna_email_paciente.setCellValueFactory(new PropertyValueFactory<>("email"));
        coluna_telefone_paciente.setCellValueFactory(new PropertyValueFactory<>("numeroTelefone"));

        adicionarColunaAcoes();
        tabela_pacientes.setItems(listaPacientes);
    }

    private void configurarBuscaPaciente() {
        buscar_paciente.textProperty().addListener((obs, oldText, newText) -> buscarPaciente());
    }

    // =================== AÇÕES DOS BOTÕES ===================

    @FXML
    private void salvarPaciente() {
        String nome = campo_nome.getText();
        String cpf = campo_cpf.getText();
        String endereco = campo_endereco.getText();
        String idadeStr = campo_idade.getText();
        String email = campo_email.getText();
        String telefone = campo_telefone.getText();
        String sexo = masculino.isSelected() ? "Masculino" : feminino.isSelected() ? "Feminino" : null;

        try {
            ValidacoesPaciente.validar(nome, cpf, idadeStr, sexo, endereco, email, telefone);
            int idade = Integer.parseInt(idadeStr);

            if (pacienteEmEdicao == null) {
                Paciente novo = new Paciente(nome, cpf, idade, sexo, endereco, email, telefone);
                repositorioPaciente.adicionarPaciente(novo);
                listaPacientes.add(novo);
                mostrarAlertaInformacao("Pacienteão cadastrado com sucesso!\n\n" + formatarDados(novo));
            } else {
                pacienteEmEdicao.setNome(nome);
                pacienteEmEdicao.setCpf(cpf);
                pacienteEmEdicao.setIdade(idade);
                pacienteEmEdicao.setSexo(sexo);
                pacienteEmEdicao.setEndereco(endereco);
                pacienteEmEdicao.setEmail(email);
                pacienteEmEdicao.setNumeroTelefone(telefone);

                listaPacientes.setAll(repositorioPaciente.listarPacientes().values());
                tabela_pacientes.refresh();
                mostrarAlertaInformacao("Pacienteão atualizado com sucesso!");

                pacienteEmEdicao = null;
            }

            limparCampos();
            ordenarTabelaPorId();

        } catch (ValidacaoException e) {
            mostrarAlertaErro(e.getMessage());
        }
    }

    private void excluirPaciente(Paciente paciente) {
        repositorioPaciente.excluirPaciente(paciente.getId());
        mostrarAlertaInformacao("Pacienteão excluído com sucesso.");
    }

    // =================== SUPORTE À TABELA ===================

    private void adicionarColunaAcoes() {
        coluna_acao_paciente.setCellFactory(param -> new TableCell<>() {
            private final Button editar = new Button("Editar");
            private final Button excluir = new Button("Excluir");
            private final Button visualizar = new Button("Visualizar Cartão");

            {
                String estiloPadrao = "-fx-background-color: #E4E1E2; -fx-text-fill: black; -fx-cursor: hand;";

                editar.setStyle(estiloPadrao);
                excluir.setStyle(estiloPadrao);
                visualizar.setStyle(estiloPadrao);

                // Efeito hover: Editar (azul)
                editar.setOnMouseEntered(e -> editar.setStyle("-fx-background-color: #4da6ff; -fx-text-fill: white; -fx-cursor: hand;"));
                editar.setOnMouseExited(e -> editar.setStyle(estiloPadrao));

                // Efeito hover: Excluir (vermelho)
                excluir.setOnMouseEntered(e -> excluir.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-cursor: hand;"));
                excluir.setOnMouseExited(e -> excluir.setStyle(estiloPadrao));

                // Efeito hover: Visualizar (rosa do projeto)
                visualizar.setOnMouseEntered(e -> visualizar.setStyle("-fx-background-color: #e89baf; -fx-text-fill: white; -fx-cursor: hand;"));
                visualizar.setOnMouseExited(e -> visualizar.setStyle(estiloPadrao));

                editar.setOnAction(e -> preencherFormularioParaEdicao(getTableView().getItems().get(getIndex())));
                excluir.setOnAction(e -> excluirPaciente(getTableView().getItems().get(getIndex())));

                visualizar.setOnAction(e -> {
                    Paciente pacienteDaLinha = getTableView().getItems().get(getIndex());
                    visualizarPerfilPaciente(pacienteDaLinha, e);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(10, editar, excluir, visualizar));
            }
        });
    }

    private void preencherFormularioParaEdicao(Paciente paciente) {
        campo_nome.setText(paciente.getNome());
        campo_cpf.setText(paciente.getCpf());
        campo_endereco.setText(paciente.getEndereco());
        campo_email.setText(paciente.getEmail());
        campo_telefone.setText(paciente.getNumeroTelefone());
        campo_idade.setText(String.valueOf(paciente.getIdade()));
        sexo_paciente.selectToggle(paciente.getSexo().equalsIgnoreCase("Masculino") ? masculino : feminino);
        pacienteEmEdicao = paciente;
        mostrarTela(formulario_paciente, tela_paciente);
    }

    private void ordenarTabelaPorId() {
        tabela_pacientes.getSortOrder().clear();
        coluna_id_paciente.setSortType(TableColumn.SortType.ASCENDING);
        tabela_pacientes.getSortOrder().add(coluna_id_paciente);
        tabela_pacientes.sort();
    }

    private String formatarDados(Paciente c) {
        return "ID: " + c.getId() +
                "\nNome: " + c.getNome() +
                "\nCPF: " + c.getCpf() +
                "\nIdade: " + c.getIdade() +
                "\nSexo: " + c.getSexo() +
                "\nEndereço: " + c.getEndereco() +
                "\nE-mail: " + c.getEmail() +
                "\nTelefone: " + c.getNumeroTelefone();
    }

    // =================== NAVEGAÇÃO ENTRE TELAS ===================

    @FXML
    private void mostrar_tabela_paciente(ActionEvent event) {
        iniciarTabela();
        mostrarTela(tela_paciente, formulario_paciente);
    }

    @FXML
    private void mostrar_formulario_paciente(ActionEvent event) {
        pacienteEmEdicao = null;
        limparCampos();
        mostrarTela(formulario_paciente, tela_paciente);
    }

    @FXML
    private void alterar_tela_vacina(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/scene-visualizar-vacinas.fxml", "Vacinas");
    }

    @FXML
    private void alterar_tela_agendamento(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/scene-visualizar-agendamentos.fxml", "Agendamento");
    }

    @FXML
    public void sair(ActionEvent event) {
        super.sair(event);
    }


    @FXML
    private void abrirCartaoVacinaMenu(ActionEvent event) {
        Paciente pacienteSelecionado = tabela_pacientes.getSelectionModel().getSelectedItem(); // Pega o pacientr da tabela

        visualizarPerfilPaciente(pacienteSelecionado, event);
    }

    private void visualizarPerfilPaciente(Paciente paciente, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/imunegestao/views/scene-visualizar-perfil-paciente.fxml"));
            Parent root = loader.load();

            ScenePerfilPacienteController perfilController = loader.getController();
            perfilController.setPaciente(paciente); // Passa o cidadão para o novo controller

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/imunegestao/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Perfil do Pacienteão: " + paciente.getNome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao carregar perfil ,Não foi possível carregar a tela de perfil do cidadão.");
        }
    }

    // =================== FUNCIONALIDADES AUXILIARES ===================

    private void buscarPaciente() {
        String textoBusca = buscar_paciente.getText().trim().toLowerCase();

        if (textoBusca.isEmpty()) {
            listaPacientes.setAll(repositorioPaciente.listarPacientes().values());
        } else {
            listaPacientes.setAll(
                    repositorioPaciente.listarPacientes().values().stream()
                            .filter(c -> c.getCpf().toLowerCase().contains(textoBusca))
                            .toList()
            );
        }

        tabela_pacientes.refresh();
    }

    private void limparCampos() {
        campo_nome.clear();
        campo_cpf.clear();
        campo_endereco.clear();
        campo_idade.clear();
        campo_telefone.clear();
        campo_email.clear();
        sexo_paciente.selectToggle(null);
    }

    private void iniciarTabela() {
        listaPacientes.setAll(repositorioPaciente.listarPacientes().values());
        tabela_pacientes.refresh();
    }
}
