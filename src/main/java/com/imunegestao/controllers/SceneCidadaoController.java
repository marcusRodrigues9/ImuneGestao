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

    private RepositorioCidadao repositorioCidadao = RepositorioCidadao.getInstancia();
    private ObservableList<Cidadao> listaCidadaos = FXCollections.observableArrayList();
    //==============================================
    @FXML
    private TableView<Cidadao>tabela_cidadaos;
    @FXML
    private TableColumn<Cidadao, String> coluna_cpf_cidadao;
    @FXML
    private TableColumn<Cidadao, String> coluna_endereco_cidadao;
    @FXML
    private TableColumn<Cidadao, Integer> coluna_id_cidadao;
    @FXML
    private TableColumn<Cidadao, Integer> coluna_idade_cidadao;
    @FXML
    private TableColumn<Cidadao, String> coluna_nome_cidadao;
    @FXML
    private TableColumn<Cidadao, String> coluna_sexo_cidadao;
    @FXML
    private TableColumn<Cidadao, Void> coluna_acao_cidadao;

    //==============================================
    @FXML
    private TextField campo_cpf;
    @FXML
    private TextField campo_endereco;
    @FXML
    private TextField campo_idade;
    @FXML
    private TextField campo_nome;
    @FXML
    private RadioButton masculino;
    @FXML
    private RadioButton feminino;

    @FXML
    private ToggleGroup sexo_cidadao;
    private Cidadao cidadaoEmEdicao = null;
    //==============================================
    @FXML
    private Button botao_sair;
    @FXML
    private MenuItem botao_menu_cadastrar_cidadao;
    @FXML
    private MenuItem botao_menu_visualizar_cidadao;
    @FXML
    private TextField buscar_cidadao;
    @FXML
    private AnchorPane formulario_cidadao;
    @FXML
    private AnchorPane tela_cidadao;
    //===============================================


    @FXML
    public void initialize() {
        iniciarTabela();
        // Adiciona cidadãos iniciais (somente na primeira execução)
        if (repositorioCidadao.listarCidadaos().isEmpty()) {
            Cidadao c1 = new Cidadao("Maria Silva", "12345678901", 30, "Feminino", "Rua das Flores, 123");
            Cidadao c2 = new Cidadao("João Souza", "98765432100", 45, "Masculino", "Av. Brasil, 456");
            repositorioCidadao.adicionarCidadao(c1);
            repositorioCidadao.adicionarCidadao(c2);
        }
        listaCidadaos.clear(); // limpa lista atual
        listaCidadaos.addAll(repositorioCidadao.listarCidadaos().values()); // recarrega os dados do singleton
        buscar_cidadao.textProperty().addListener((obs, oldText, newText) -> buscarCidadao()); //buscar cidadao na tabela
    }

    public void iniciarTabela() {
        System.out.println("Inicializando tabela. Lista de cidadãos: " + listaCidadaos.size());
        coluna_id_cidadao.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluna_nome_cidadao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_cpf_cidadao.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        coluna_idade_cidadao.setCellValueFactory(new PropertyValueFactory<>("idade"));
        coluna_sexo_cidadao.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        coluna_endereco_cidadao.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        adicionarColunaAcoes(); // metodo que adiciona uma nova coluna de ações com funcionalidade de excluir/editar/ visualizar cartao de vacina
        tabela_cidadaos.setItems(listaCidadaos);
    }

    //metodo para salvar o cidadao na tabela
    public void salvarCidadao() {
        String nome = campo_nome.getText();
        String cpf = campo_cpf.getText();
        String endereco = campo_endereco.getText();
        String idadeStr = campo_idade.getText();
        String sexo = null;

        if (masculino.isSelected()) {
            sexo = "Masculino";
        } else if (feminino.isSelected()) {
            sexo = "Feminino";
        }

        try {
            ValidacoesCidadao.validar(nome, cpf, idadeStr, sexo, endereco);
            int idade = Integer.parseInt(idadeStr);

            if (cidadaoEmEdicao == null) {
                // Cadastro novo
                Cidadao novo = new Cidadao(nome, cpf, idade, sexo, endereco);
                repositorioCidadao.adicionarCidadao(novo);
                listaCidadaos.add(novo);

                mostrarAlertaInformacao("Cidadão cadastrado com sucesso!\n\n"
                        + "ID: " + novo.getId() + "\n"
                        + "Nome: " + novo.getNome() + "\n"
                        + "CPF: " + novo.getCpf() + "\n"
                        + "Idade: " + novo.getIdade() + "\n"
                        + "Sexo: " + novo.getSexo() + "\n"
                        + "Endereço: " + novo.getEndereco());
            } else {
                // Edição
                cidadaoEmEdicao.setNome(nome);
                cidadaoEmEdicao.setCpf(cpf);
                cidadaoEmEdicao.setIdade(idade);
                cidadaoEmEdicao.setSexo(sexo);
                cidadaoEmEdicao.setEndereco(endereco);

                listaCidadaos.setAll(repositorioCidadao.listarCidadaos().values());
                tabela_cidadaos.refresh();
                mostrarAlertaInformacao("Cidadão atualizado com sucesso!");
                cidadaoEmEdicao = null; // limpa modo edição
            }

            limparCampos();
            tabela_cidadaos.getSortOrder().clear();
            tabela_cidadaos.getSortOrder().add(coluna_id_cidadao);
            coluna_id_cidadao.setSortType(TableColumn.SortType.ASCENDING);
            tabela_cidadaos.sort();
        } catch (ValidacaoException e) {
            mostrarAlertaErro(e.getMessage());
        }
    }

    //metodo que adiciona a coluna de ações para excluir, editar, visualizar o cartao de vacina de cada cidadao de forma exclusiva
    private void adicionarColunaAcoes() {
        coluna_acao_cidadao.setCellFactory(param -> new TableCell<>() {
            private final Button botaoEditar = new Button("Editar");
            private final Button botaoExcluir = new Button("Excluir");
            private final Button botaoVisualizarCartao = new Button("Visualizar Cartão");

            {
                botaoEditar.setStyle("-fx-background-color: #E4E1E2; -fx-text-fill: black;");
                botaoExcluir.setStyle("-fx-background-color: #E4E1E2; -fx-text-fill: black;");
                botaoVisualizarCartao.setStyle("-fx-background-color: #E4E1E2; -fx-text-fill: black;");
                botaoEditar.setOnAction(event -> {
                    Cidadao cidadao = getTableView().getItems().get(getIndex());
                    preencherFormularioParaEdicao(cidadao);
                });

                botaoExcluir.setOnAction(event -> {
                    Cidadao cidadao = getTableView().getItems().get(getIndex());
                    excluirCidadao(cidadao);
                });
            }

            // acrescenta os botões para cada linha da tabela
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10, botaoEditar, botaoExcluir, botaoVisualizarCartao);
                    setGraphic(container);
                }
            }
        });
    }

    // metodo para editar os dados do cidadao
    private void preencherFormularioParaEdicao(Cidadao cidadao) {
        campo_nome.setText(cidadao.getNome());
        campo_cpf.setText(cidadao.getCpf());
        campo_endereco.setText(cidadao.getEndereco());
        campo_idade.setText(String.valueOf(cidadao.getIdade()));
        if (cidadao.getSexo().equalsIgnoreCase("Masculino")) {
            sexo_cidadao.selectToggle(masculino);
        } else {
            sexo_cidadao.selectToggle(feminino);
        }

        cidadaoEmEdicao = cidadao;
        mostrar_formulario_cidadao(null); // troca para a tela de formulário, se necessário
    }
    private void excluirCidadao(Cidadao cidadao) {
        repositorioCidadao.listarCidadaos().remove(cidadao.getId());
        listaCidadaos.remove(cidadao);
        mostrarAlertaInformacao("Cidadão excluído com sucesso.");
    }
    //===================================================
    @FXML
    void mostrar_tabela_cidadao(ActionEvent event) {
        iniciarTabela();
        mostrarTela(tela_cidadao, formulario_cidadao);
    }
    @FXML
    void mostrar_formulario_cidadao(ActionEvent event) {
        mostrarTela(formulario_cidadao, tela_cidadao);
    }
    @FXML
    void alterar_tela_vacina(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Vacinas.fxml", "Vacinas");
    }

    @FXML
    void alterar_tela_agendamento(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Agendamentos.fxml", "Agendamento");
    }
    //===================================================
    @FXML
    public void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
    //===================================================
    @FXML
    private void mostrarAlertaInformacao(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informações do Cidadão");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
    private void mostrarAlertaErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de Validação");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    //metodo para buscar cidadao pelo CPF, é necessario digitar o cpf completo
    @FXML
    private void buscarCidadao() {
        String cpfBusca = buscar_cidadao.getText().trim();

        if (cpfBusca.isEmpty()) {
            // Volta a mostrar todos
            listaCidadaos.setAll(repositorioCidadao.listarCidadaos().values());
        } else {
            listaCidadaos.setAll(repositorioCidadao.listarCidadaos().values().stream().filter(c -> c.getCpf().equals(cpfBusca)).toList());
        }
        tabela_cidadaos.refresh();
    }

    //metodo para limpar os campos após preencher o formulario
    private void limparCampos() {
        campo_nome.clear();
        campo_cpf.clear();
        campo_endereco.clear();
        campo_idade.clear();
        sexo_cidadao.selectToggle(null); // desmarca ambos
    }

}
