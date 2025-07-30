package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.repository.RepositorioCidadao;
import com.imunegestao.utils.ValidacaoException;
import com.imunegestao.utils.ValidacoesCidadao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneCidadaoController extends BaseController {

    // =================== INSTÂNCIAS ===================
    private final RepositorioCidadao repositorioCidadao = RepositorioCidadao.getInstancia();
    private final ObservableList<Cidadao> listaCidadaos = FXCollections.observableArrayList();
    private Cidadao cidadaoEmEdicao = null;

    // =================== COMPONENTES FXML ===================
    // --- Tabela ---
    @FXML private TableView<Cidadao> tabela_cidadaos;
    @FXML private TableColumn<Cidadao, Integer> coluna_id_cidadao;
    @FXML private TableColumn<Cidadao, String> coluna_nome_cidadao;
    @FXML private TableColumn<Cidadao, String> coluna_cpf_cidadao;
    @FXML private TableColumn<Cidadao, Integer> coluna_idade_cidadao;
    @FXML private TableColumn<Cidadao, String> coluna_sexo_cidadao;
    @FXML private TableColumn<Cidadao, String> coluna_endereco_cidadao;
    @FXML private TableColumn<Cidadao, String> coluna_email_cidadao;
    @FXML private TableColumn<Cidadao, String> coluna_telefone_cidadao;
    @FXML private TableColumn<Cidadao, Void> coluna_acao_cidadao;

    // --- Campos Formulário ---
    @FXML private TextField campo_nome, campo_cpf, campo_endereco, campo_idade, campo_email, campo_telefone;
    @FXML private RadioButton masculino, feminino;
    @FXML private ToggleGroup sexo_cidadao;

    // --- Navegação ---
    @FXML private TextField buscar_cidadao;
    @FXML private AnchorPane formulario_cidadao, tela_cidadao;
    @FXML private MenuItem botao_menu_cadastrar_cidadao, botao_menu_visualizar_cidadao;

    // =================== INICIALIZAÇÃO ===================

    @FXML
    public void initialize() {
        carregarCidadaosIniciais();
        configurarTabela();
        configurarBuscaCidadao();
    }

    private void carregarCidadaosIniciais() {
        if (repositorioCidadao.listarCidadaos().isEmpty()) {
            Cidadao c1 = new Cidadao("Maria Silva", "12345678901", 30, "Feminino", "Rua das Flores, 123", "35997337238", "marcus0vv@gmail.com");
            Cidadao c2 = new Cidadao("João Souza", "98765432100", 45, "Masculino", "Av. Brasil, 456", "35997337238", "marcus2vv@gmail.com");
            repositorioCidadao.adicionarCidadao(c1);
            repositorioCidadao.adicionarCidadao(c2);
        }

        listaCidadaos.setAll(repositorioCidadao.listarCidadaos().values());
    }

    private void configurarTabela() {
        coluna_id_cidadao.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluna_nome_cidadao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_cpf_cidadao.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        coluna_idade_cidadao.setCellValueFactory(new PropertyValueFactory<>("idade"));
        coluna_sexo_cidadao.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        coluna_endereco_cidadao.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        coluna_email_cidadao.setCellValueFactory(new PropertyValueFactory<>("email"));
        coluna_telefone_cidadao.setCellValueFactory(new PropertyValueFactory<>("numeroTelefone"));

        adicionarColunaAcoes();
        tabela_cidadaos.setItems(listaCidadaos);
    }

    private void configurarBuscaCidadao() {
        buscar_cidadao.textProperty().addListener((obs, oldText, newText) -> buscarCidadao());
    }

    // =================== AÇÕES DOS BOTÕES ===================

    @FXML
    private void salvarCidadao() {
        String nome = campo_nome.getText();
        String cpf = campo_cpf.getText();
        String endereco = campo_endereco.getText();
        String idadeStr = campo_idade.getText();
        String email = campo_email.getText();
        String telefone = campo_telefone.getText();
        String sexo = masculino.isSelected() ? "Masculino" : feminino.isSelected() ? "Feminino" : null;

        try {
            ValidacoesCidadao.validar(nome, cpf, idadeStr, sexo, endereco, email, telefone);
            int idade = Integer.parseInt(idadeStr);

            if (cidadaoEmEdicao == null) {
                Cidadao novo = new Cidadao(nome, cpf, idade, sexo, endereco, email, telefone);
                repositorioCidadao.adicionarCidadao(novo);
                listaCidadaos.add(novo);
                mostrarAlertaInformacao("Cidadão cadastrado com sucesso!\n\n" + formatarDados(novo));
            } else {
                cidadaoEmEdicao.setNome(nome);
                cidadaoEmEdicao.setCpf(cpf);
                cidadaoEmEdicao.setIdade(idade);
                cidadaoEmEdicao.setSexo(sexo);
                cidadaoEmEdicao.setEndereco(endereco);
                cidadaoEmEdicao.setEmail(email);
                cidadaoEmEdicao.setNumeroTelefone(telefone);

                listaCidadaos.setAll(repositorioCidadao.listarCidadaos().values());
                tabela_cidadaos.refresh();
                mostrarAlertaInformacao("Cidadão atualizado com sucesso!");
                cidadaoEmEdicao = null;
            }

            limparCampos();
            ordenarTabelaPorId();

        } catch (ValidacaoException e) {
            mostrarAlertaErro(e.getMessage());
        }
    }

    private void excluirCidadao(Cidadao cidadao) {
        repositorioCidadao.listarCidadaos().remove(cidadao.getId());
        listaCidadaos.remove(cidadao);
        mostrarAlertaInformacao("Cidadão excluído com sucesso.");
    }

    // =================== SUPORTE À TABELA ===================

    private void adicionarColunaAcoes() {
        coluna_acao_cidadao.setCellFactory(param -> new TableCell<>() {
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
                excluir.setOnAction(e -> excluirCidadao(getTableView().getItems().get(getIndex())));

                visualizar.setOnAction(e -> {
                    Cidadao cidadaoDaLinha = getTableView().getItems().get(getIndex());
                    visualizarPerfilCidadao(cidadaoDaLinha, e);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(10, editar, excluir, visualizar));
            }
        });
    }

    private void preencherFormularioParaEdicao(Cidadao cidadao) {
        campo_nome.setText(cidadao.getNome());
        campo_cpf.setText(cidadao.getCpf());
        campo_endereco.setText(cidadao.getEndereco());
        campo_email.setText(cidadao.getEmail());
        campo_telefone.setText(cidadao.getNumeroTelefone());
        campo_idade.setText(String.valueOf(cidadao.getIdade()));
        sexo_cidadao.selectToggle(cidadao.getSexo().equalsIgnoreCase("Masculino") ? masculino : feminino);
        cidadaoEmEdicao = cidadao;
        mostrarTela(formulario_cidadao, tela_cidadao);
    }

    private void ordenarTabelaPorId() {
        tabela_cidadaos.getSortOrder().clear();
        coluna_id_cidadao.setSortType(TableColumn.SortType.ASCENDING);
        tabela_cidadaos.getSortOrder().add(coluna_id_cidadao);
        tabela_cidadaos.sort();
    }

    private String formatarDados(Cidadao c) {
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
    private void mostrar_tabela_cidadao(ActionEvent event) {
        iniciarTabela();
        mostrarTela(tela_cidadao, formulario_cidadao);
    }

    @FXML
    private void mostrar_formulario_cidadao(ActionEvent event) {
        cidadaoEmEdicao = null;
        limparCampos();
        mostrarTela(formulario_cidadao, tela_cidadao);
    }

    @FXML
    private void alterar_tela_vacina(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Vacinas.fxml", "Vacinas");
    }

    @FXML
    private void alterar_tela_agendamento(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Agendamentos.fxml", "Agendamento");
    }

    @FXML
    private void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }

    // NOVO MÉTODO: Ação para o MenuItem "Cartão de Vacina"
    @FXML
    private void abrirCartaoVacinaMenu(ActionEvent event) {
        Cidadao cidadaoSelecionado = tabela_cidadaos.getSelectionModel().getSelectedItem(); // Pega o cidadão da tabela


        // Se um cidadão foi selecionado, chame o método que abre a tela de perfil
        visualizarPerfilCidadao(cidadaoSelecionado, event);
    }

    // Método que abre a tela de perfil, que já te passei e você já deve ter
    private void visualizarPerfilCidadao(Cidadao cidadao, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/imunegestao/views/Scene_Visualizar_PerfilCidadao.fxml"));
            Parent root = loader.load();

            ScenePerfilCidadaoController perfilController = loader.getController();
            perfilController.setCidadao(cidadao); // Passa o cidadão para o novo controller

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Perfil do Cidadão: " + cidadao.getNome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao carregar perfil ,Não foi possível carregar a tela de perfil do cidadão.");
        }
    }

    // =================== FUNCIONALIDADES AUXILIARES ===================

    private void buscarCidadao() {
        String cpf = buscar_cidadao.getText().trim();
        if (cpf.isEmpty()) {
            listaCidadaos.setAll(repositorioCidadao.listarCidadaos().values());
        } else {
            listaCidadaos.setAll(
                    repositorioCidadao.listarCidadaos().values().stream()
                            .filter(c -> c.getCpf().equals(cpf))
                            .toList()
            );
        }
        tabela_cidadaos.refresh();
    }

    private void limparCampos() {
        campo_nome.clear();
        campo_cpf.clear();
        campo_endereco.clear();
        campo_idade.clear();
        campo_telefone.clear();
        campo_email.clear();
        sexo_cidadao.selectToggle(null);
    }

    private void iniciarTabela() {
        listaCidadaos.setAll(repositorioCidadao.listarCidadaos().values());
        tabela_cidadaos.refresh();
    }
}
