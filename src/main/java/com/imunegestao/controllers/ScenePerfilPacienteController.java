package com.imunegestao.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.imunegestao.models.RegistroVacina;
import com.imunegestao.models.pessoas.Paciente;

import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioPaciente;
import com.imunegestao.repository.RepositorioVacina;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    @FXML
    private TextField campo_id;
    @FXML
    private TextField campo_nome;
    @FXML
    private TextField campo_fabricante;
    @FXML
    private TextField campo_funcionario;

    @FXML private VBox formulario_administrar_vacina;

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

        campo_id.textProperty().addListener((obs, oldText, newText) -> preencherPorId());

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
    public  void administrarVacinaPaciente(ActionEvent event){
        TextField campo_id = (TextField) formulario_administrar_vacina.lookup("#campo_id");
        String idStr = campo_id.getText();

        if (idStr == null || idStr.isEmpty()) {
            mostrarAlertaErro("Informe o ID da vacina.");
            return;
        }
        int idVacina = Integer.parseInt(idStr);

        Vacina vacina = RepositorioVacina.getInstancia().buscarVacinaPorId(idVacina);
        if (vacina == null) {
            mostrarAlertaErro("Vacina com ID " + idVacina + " não encontrada.");
            return;
        }

        RegistroVacina registroVacina = new RegistroVacina(LocalDate.now(),vacina); // Pega a data Atual

        // Administra a Vacina na lista de Vacinas Tomadas
        if (pacienteAtual != null) {
            RepositorioPaciente.getInstancia().registrarVacinaParaPaciente(pacienteAtual, registroVacina);
            RepositorioVacina.getInstancia().gastarDoseVacina(1,vacina);
        }else{
            mostrarAlertaErro("Não foi Possivel Administrar Vacina");
        }
        listaDeVacinasTomadas.add(registroVacina);
        alternarVisibilidadeTela(formulario_administrar_vacina);
    }
    public void mostrarFormularioAdministrarVacina(ActionEvent event){
        alternarVisibilidadeTela(formulario_administrar_vacina);
        limparFormularioVacina();
    }

    public void limparFormularioVacina() {
        if (campo_id != null) campo_id.clear();
        if (campo_nome != null) campo_nome.clear();
        if (campo_fabricante != null) campo_fabricante.clear();
        if (campo_funcionario != null) campo_funcionario.clear();
    }
    public void preencherPorId(){ //Preenche Automaticamente os outros campos ao digitar o ID da Vacina
        String idDigitado = campo_id.getText().trim();
        if (idDigitado.isEmpty()) {
            limparFormularioVacina();
            return;
        }

        try {
            int idVacina = Integer.parseInt(idDigitado);
            Vacina vacina = RepositorioVacina.getInstancia().buscarVacinaPorId(idVacina);

            if (vacina != null) {
                campo_nome.setText(vacina.getNome());
                campo_fabricante.setText(vacina.getFabricante());
            } else {
                campo_nome.clear();
                mostrarAlertaErro("Vacina com ID " + idVacina + " não encontrada.");
            }
        } catch (NumberFormatException e) {
            campo_nome.clear();
            campo_fabricante.clear();
            mostrarAlertaErro("ID da vacina deve ser um número válido.");
        }
    }

    @FXML
    private void voltarParaPacientes(ActionEvent event) {

        try {
            trocarCena(event, "/com/imunegestao/views/scene-visualizar-paciente.fxml", "Pacienteãos");
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
