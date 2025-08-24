package com.imunegestao.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.imunegestao.models.RegistroVacina;
import com.imunegestao.models.pessoas.Paciente;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ScenePerfilPacienteController extends BaseController {
    // --- Variável de Instância ---
    private Paciente pacienteAtual; // Variável para armazenar o paciente que está sendo visualizado

    // --- Componentes FXML ---
    @FXML private Label labelNome;
    @FXML private Label labelCpf;
    @FXML private Label labelEndereco;
    @FXML private Label labelEmail;
    @FXML private Label labelTelefone;
    @FXML private Label labelSexo;
    @FXML private Label labelIdade;


    @FXML private TableView<RegistroVacina> tabela_historico;

    @FXML private TableColumn<RegistroVacina, LocalDate> coluna_data;
    @FXML private TableColumn<RegistroVacina, String> coluna_vacina;
    @FXML private TableColumn<RegistroVacina, String> coluna_fabricante;
    //@FXML private TableColumn<RegistroVacina, String> coluna_funcionario;

    private ObservableList<RegistroVacina> listaDeVacinasTomadas;

    @FXML
    public void initialize() {
        // Inicializa a lista e a tabela
        listaDeVacinasTomadas = FXCollections.observableArrayList();
        tabela_historico.setItems(listaDeVacinasTomadas);

        // Configura as colunas
        coluna_data.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDataAplicacao()));
        coluna_vacina.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVacina().getNome()));
        coluna_fabricante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVacina().getFabricante()));
        //coluna_funcionario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeFuncionario()));

    }


    public void setPaciente(Paciente paciente) {
        this.pacienteAtual = paciente; // Armazena o paciente recebido
        preencherDadospaciente();    // Preenche os Labels com os dados
        carregarVacinasDoPaciente();
    }
    private void carregarVacinasDoPaciente() {
        if (pacienteAtual != null) {
            listaDeVacinasTomadas.clear();
            listaDeVacinasTomadas.addAll(pacienteAtual.getVacinasTomadas());
        }
    }


    private void preencherDadospaciente() {
        if (pacienteAtual != null) {
            labelNome.setText(pacienteAtual.getNome());
            labelCpf.setText(pacienteAtual.getCpf());
            labelEndereco.setText(pacienteAtual.getEndereco());
            labelEmail.setText(pacienteAtual.getEmail());
            labelTelefone.setText(pacienteAtual.getNumeroTelefone());
            labelSexo.setText(pacienteAtual.getSexo());
            labelIdade.setText(String.valueOf(pacienteAtual.getIdade())); // Converte int para String
        } else {
            // Se por algum motivo o paciente for nulo.
            labelNome.setText("N/A");
            labelCpf.setText("N/A");
            labelEndereco.setText("N/A");
            labelEmail.setText("N/A");
            labelTelefone.setText("N/A");
            labelSexo.setText("N/A");
            labelIdade.setText("N/A");
        }
    }


    @FXML
    private void voltarParaPacientes(ActionEvent event) {

        try {
            trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Paciente.fxml", "Pacienteãos");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao voltar para a tela de Pacienteãos.");
        }
    }

    @FXML
    public void sair(ActionEvent event){
        super.sair(event);
    }
}
