/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import br.edu.fesa.tablepick.dao.MesaDAO;
import br.edu.fesa.tablepick.model.Mesa;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author DAVI
 */
public class ConsultaMesaController {
    
    @FXML
    private TextField txtMesa;
    
    @FXML
    private Button btnPesquisar;
    
    @FXML
    private Button btnGraficos;
    
    @FXML
    private Button btnVoltarHub;
    
    @FXML
    private TableView<Mesa> tableView;
    
    @FXML
    private TableColumn<Mesa, Integer> colId;
    
    @FXML
    private TableColumn<Mesa, String> colLivre;
    
    @FXML
    private TableColumn<Mesa, Integer> colQtdLugares;
    
    @FXML
    private TableColumn<Mesa, String> colTipoMesa;
    
    @FXML
    private TableColumn<Mesa, Void> colAcoes;

    private ObservableList<Mesa> mesaList;
    
    private MesaDAO mesaDAO;
    
    public ConsultaMesaController() {
        mesaDAO = new MesaDAO();
    }
    
     @FXML
    public void initialize() {
        ValidarSomenteNumero(txtMesa);
        
        btnPesquisar.setOnAction(event -> pesquisarMesa());
        btnGraficos.setOnAction(event -> abrirDashMesa());
        btnVoltarHub.setOnAction(event -> voltarHub());

        colId.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colLivre.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getLivre() ? "Sim" : "Não"));
        colQtdLugares.setCellValueFactory(new PropertyValueFactory<>("qtdLugares"));
        colTipoMesa.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        adicionarBotoesAcao();

        listarMesas();
    }
    
    private void pesquisarMesa() {
        String idMesa = txtMesa.getText();
        
        if (idMesa.isEmpty()) {
            listarMesas();
            return;
        }
        
        try {
            Mesa mesa = mesaDAO.listarPorID(Integer.parseInt(idMesa));
            if (mesa != null) {
                ObservableList<Mesa> mesas = FXCollections.observableArrayList(mesa);
                tableView.setItems(mesas);
            } else {
                AlertaUtil.exibirAlerta("Mesa não encontrada.", Alert.AlertType.INFORMATION);
                listarMesas();
            }
        } catch (NumberFormatException e) {
            AlertaUtil.exibirAlerta("ID inválido. Por favor, insira um número inteiro.", Alert.AlertType.ERROR);
        }
    }
    
    private void listarMesas() {
        ObservableList<Mesa> mesas = FXCollections.observableArrayList(mesaDAO.listar());
        tableView.setItems(mesas);
    }
    
    private void adicionarBotoesAcao() {
        Callback<TableColumn<Mesa, Void>, TableCell<Mesa, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Mesa, Void> call(final TableColumn<Mesa, Void> param) {
                final TableCell<Mesa, Void> cell = new TableCell<>() {
                    
                    private final Button btnEditar = new Button("📝");
                    private final Button btnExcluir = new Button("❌");

                    {
                        btnEditar.setOnAction(event -> {
                            Mesa mesa = getTableView().getItems().get(getIndex());
                            editarMesa(mesa);
                        });

                        btnExcluir.setOnAction(event -> {
                            Mesa mesa = getTableView().getItems().get(getIndex());
                            excluirMesa(mesa);
                        });
                    }

                    HBox pane = new HBox(btnEditar, btnExcluir);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        };

        colAcoes.setCellFactory(cellFactory);
    }
    
    private void editarMesa(Mesa mesa) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("edit_table.fxml"));
            Parent root = fxmlLoader.load();
            
            EditarMesaController controller = fxmlLoader.getController();
            controller.setMesa(mesa);
            
            App.setRoot(root);
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de edição de mesa.", Alert.AlertType.ERROR);
        }
    }

    private void excluirMesa(Mesa mesa) {
        mesaDAO.remover(mesa);
        mesaList.remove(mesa);
    }
    
    private void abrirDashMesa(){
        try{
            App.setRoot("table_dashboard");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir o dashboard de mesas.", Alert.AlertType.ERROR);
        }
    }
    
    private void voltarHub(){
        try{
            App.setRoot("main_hub");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir hub principal.", Alert.AlertType.ERROR);
        }
    }
    
    private void ValidarSomenteNumero(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
    }
}
