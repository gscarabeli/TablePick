/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.tablepick.controller;

import br.edu.fesa.tablepick.App;
import br.edu.fesa.tablepick.dao.MesaDAO;
import br.edu.fesa.tablepick.dao.ReservaDAO;
import br.edu.fesa.tablepick.model.Mesa;
import br.edu.fesa.tablepick.model.Reserva;
import br.edu.fesa.tablepick.util.AlertaUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author DAVI
 */
public class ConsultaReservaController {
    
    @FXML
    private TextField txtReserva;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnGraficos;

    @FXML
    private Button btnVoltarHub;

    @FXML
    private TableView<Reserva> tableView;

    @FXML
    private TableColumn<Reserva, Integer> colId;
    
    @FXML
    private TableColumn<Reserva, String> colStatus;

    @FXML
    private TableColumn<Reserva, LocalDateTime> colData;

    @FXML
    private TableColumn<Reserva, Integer> colMesa;

    @FXML
    private TableColumn<Reserva, Void> colAcoes;
    
    private ObservableList<Reserva> reservaList;
    
    private ReservaDAO reservaDAO;
    private MesaDAO mesaDAO;
    
    public ConsultaReservaController() {
        reservaDAO = new ReservaDAO();
        mesaDAO = new MesaDAO();
    }
    
    @FXML
    private void initialize() {
        btnPesquisar.setOnAction(event -> pesquisarReserva());
        btnGraficos.setOnAction(event -> abrirDashReserva());
        btnVoltarHub.setOnAction(event -> voltar());

        colId.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataReserva"));
        colMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        colData.setCellFactory(column -> {
            return new TableCell<Reserva, LocalDateTime>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(formatter.format(item));
                    }
                }
            };
        });
        
        adicionarBotoesAcao();

        listarReservas();
    }
    
    private void pesquisarReserva() {
        String idReserva = txtReserva.getText();
        
        if (idReserva.isEmpty()) {
            listarReservas();
            return;
        }
        
        try {
            Reserva reserva = reservaDAO.listarPorID(Integer.parseInt(idReserva));
            if (reserva != null) {
                ObservableList<Reserva> mesas = FXCollections.observableArrayList(reserva);
                tableView.setItems(mesas);
            } else {
                AlertaUtil.exibirAlerta("Reserva não encontrada.", Alert.AlertType.INFORMATION);
                listarReservas();
            }
        } catch (NumberFormatException e) {
            AlertaUtil.exibirAlerta("ID inválido. Por favor, insira um número inteiro.", Alert.AlertType.ERROR);
        }
    }
    
    private void listarReservas() {
        ObservableList<Reserva> reservas = FXCollections.observableArrayList(reservaDAO.listar());
        tableView.setItems(reservas);
    }
    
    private void adicionarBotoesAcao() {
        Callback<TableColumn<Reserva, Void>, TableCell<Reserva, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Reserva, Void> call(final TableColumn<Reserva, Void> param) {
                final TableCell<Reserva, Void> cell = new TableCell<>() {

                    private final Button btnEditar = new Button("📝");
                    private final Button btnFinalizar = new Button("⬇");
                    private final Button btnExcluir = new Button("❌");

                    {
                        btnEditar.setOnAction(event -> {
                            Reserva reserva = getTableView().getItems().get(getIndex());
                            editarReserva(reserva);
                        });
                        
                        btnFinalizar.setOnAction(event -> {
                            Reserva reserva = getTableView().getItems().get(getIndex());
                            if ("Ativa".equals(reserva.getStatus())) {
                                reserva.setStatus("Finalizada");
                                reservaDAO.alterarStatus(reserva);
                                Mesa mesa = new Mesa(reserva.getIdMesa(), true, 0, "tipo");
                                mesaDAO.alterarStatus(mesa);
                                tableView.refresh();
                            } else {
                                AlertaUtil.exibirAlerta("Somente reservas ativas podem ser finalizadas.", Alert.AlertType.WARNING);
                            }
                        });

                        btnExcluir.setOnAction(event -> {
                            Reserva reserva = getTableView().getItems().get(getIndex());
                            excluirReserva(reserva);
                        });
                    }

                    HBox pane = new HBox(btnEditar, btnFinalizar,btnExcluir);

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
    
    private void editarReserva(Reserva reserva) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("edit_book.fxml"));
            Parent root = fxmlLoader.load();
            
            EditarReservaController controller = fxmlLoader.getController();
            controller.setReserva(reserva);
            
            App.setRoot(root);
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir a tela de edição de reserva.", Alert.AlertType.ERROR);
        }
    }
    
    private void excluirReserva(Reserva reserva) {
        reservaDAO.remover(reserva);
        reservaList.remove(reserva);
    }
    
    private void abrirDashReserva(){
        try{
            App.setRoot("book_dashboard");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir o dashboard de reservas.", Alert.AlertType.ERROR);
        }
    }
    
    private void voltar(){
        try{
            App.setRoot("main_hub");
        } catch (IOException ex) {
            AlertaUtil.exibirAlerta("Erro ao abrir hub principal.", Alert.AlertType.ERROR);
        }
    }
}
