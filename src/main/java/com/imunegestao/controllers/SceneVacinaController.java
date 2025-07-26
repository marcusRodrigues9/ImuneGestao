package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.utils.ValidacaoException;
import com.imunegestao.utils.ValidacoesCidadao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SceneVacinaController extends BaseController {
    // =================== COMPONENTES FXML ===================
    // --- Tabela ---

    @FXML private TableColumn<Vacina, LocalDate> coluna_data_validade;

    @FXML private TableColumn<Vacina, Integer> coluna_doses_disponiveis;

    @FXML private TableColumn<Vacina, Integer> coluna_doses_recomendadas;

    @FXML private TableColumn<Vacina, String> coluna_fabricante;

    @FXML private TableColumn<Vacina, Integer> coluna_id;

    @FXML private TableColumn<Vacina, String> coluna_nome;

    @FXML private TableView<Vacina> tabela_vacinas;

    // --- Campos Formulário ---
    @FXML private DatePicker campo_data_validade;
    @FXML private TextField campo_doses_disponiveis, campo_doses_recomendadas, campo_fabricante, campo_nome;
    // --- Navegação ---
    @FXML private MenuItem botao_menu_cadastrar_vacina, botao_menu_visualizar_vacina;
    @FXML private Button botao_sair;
    @FXML private TextField buscar_vacina;
    @FXML private AnchorPane formulario_vacina;
    @FXML private AnchorPane tela_vacina;



    private void configurarTabela() {
        coluna_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluna_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        coluna_data_validade.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
        coluna_doses_disponiveis.setCellValueFactory(new PropertyValueFactory<>("dosesDisponiveis"));
        coluna_doses_recomendadas.setCellValueFactory(new PropertyValueFactory<>("dosesRecomendadas"));

        tabela_vacinas.setItems(listaVacinas);
    }
    @FXML
    private void salvarVacina() {
        String nome = campo_nome.getText();
        String fabricante = campo_fabricante.getText();
        String dosesRecomendadas = campo_doses_recomendadas.getText();
        String dosesDisponiveis = campo_doses_disponiveis.getText();
        LocalDate dataValidade = campo_data_validade.getValue();
        String dataFormatada = dataValidade != null ? dataValidade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";

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



    // ==== Navegação entre Telas ====

    @FXML
    void alterar_tela_agendamento(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Agendamentos.fxml", "Agendamento");
    }

    @FXML
    void alterar_tela_cidadao(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Cidadao.fxml", "Cidadãos");
    }
    @FXML
    void mostrar_formulario_vacina(ActionEvent event) {
        mostrarTela(formulario_vacina, tela_vacina);
    }

    @FXML
    void mostrar_tabela_vacina(ActionEvent event) {
        mostrarTela(tela_vacina, formulario_vacina);
    }

    @FXML
    void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }

}
