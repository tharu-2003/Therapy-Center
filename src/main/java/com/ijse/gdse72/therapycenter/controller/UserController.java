package com.ijse.gdse72.therapycenter.controller;

import com.ijse.gdse72.therapycenter.bo.BOFactory;
import com.ijse.gdse72.therapycenter.bo.custom.UserBO;
import com.ijse.gdse72.therapycenter.dto.UserDTO;
import com.ijse.gdse72.therapycenter.dto.tm.UserTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbUserRole;

    @FXML
    private TableColumn<UserTM , String> colRole;

    @FXML
    private TableColumn<UserTM , String> colUserId;

    @FXML
    private TableColumn<UserTM , String> colUsername;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TableView<UserTM> tblUsers;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    private final UserBO USERBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void initialize() {


        try {
            visibleData();
            refrashPage();
            loadTableData();

            String nextuserID = USERBO.getNextuserId();

            txtUserId.setText(nextuserID);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cmbUserRole.getItems().addAll("ADMIN" , "RECEPTIONIST");

    }

    private void loadTableData() throws Exception {
        ArrayList<UserDTO> userDtos = USERBO.getAllUser();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDTO userDto : userDtos) {
            UserTM userTM = new UserTM(
                    userDto.getUserId(),
                    userDto.getUserName(),
                    userDto.getPassword(),
                    userDto.getRole()
            );
            userTMS.add(userTM);
        }
        tblUsers.setItems(userTMS);
    }

    private void refrashPage() {
        String nextuserID = null;
        try {
            nextuserID = USERBO.getNextuserId();
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtUserId.setText(nextuserID);
        txtUsername.setText("");
        cmbUserRole.setValue(null);
    }

    private void visibleData() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        UserTM selectedUser = tblUsers.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to delete this user?");
            confirmAlert.setContentText("Please enter your password to confirm.");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            VBox dialogPaneContent = new VBox();
            dialogPaneContent.getChildren().addAll(new Label("Password:"), passwordField);
            confirmAlert.getDialogPane().setContent(dialogPaneContent);

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String password = passwordField.getText();
                    String userId = txtUserId.getText();
                    try {
                        boolean confirm = USERBO.confirmation(userId,password);
                        if (confirm) {
                            boolean isDeleted = USERBO.deleteUser(selectedUser.getUserId());
                            if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "User Deleted Successfully!").show();
                                loadTableData();
                                refrashPage();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed to Delete User!").show();
                            }
                        }else{
                            new Alert(Alert.AlertType.WARNING, "Invalid password try again....!").show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong: " + e.getMessage()).show();
                    }
                }
            });
        }else {
            new Alert(Alert.AlertType.WARNING, "Please select a User to delete!").show();
        }
    }

    @FXML
    void refreshOnAction(ActionEvent event) {
        refrashPage();
    }

    @FXML
    void saveOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(MouseEvent event) {
        String userId = txtUserId.getText().trim();

        if (userId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a User ID").show();
            return;
        }

        try {
            UserDTO userDto = USERBO.searchUser(userId);
            if (userDto != null) {

                txtUsername.setText(userDto.getUserName());
                cmbUserRole.setValue(userDto.getRole());

                ObservableList<UserTM> userTMS = FXCollections.observableArrayList();
                userTMS.add(new UserTM(
                        userDto.getUserId(),
                        userDto.getUserName(),
                        userDto.getPassword(),
                        userDto.getRole()
                ));
                tblUsers.setItems(userTMS);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void tableClickOnAction(MouseEvent event) {
        UserTM selectedUser = tblUsers.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            txtUserId.setText(selectedUser.getUserId());
            txtUsername.setText(selectedUser.getUserName());
            cmbUserRole.setValue(selectedUser.getRole());
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userName = txtUsername.getText();
        String role = cmbUserRole.getValue();

        if (!userId.isEmpty() && !userName.isEmpty() && !role.isEmpty()) {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Updation");
            confirmAlert.setHeaderText("Are you sure you want to Update this user Details?");
            confirmAlert.setContentText("Please enter your password to confirm.");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            VBox dialogPaneContent = new VBox();
            dialogPaneContent.getChildren().addAll(new Label("Password:"), passwordField);
            confirmAlert.getDialogPane().setContent(dialogPaneContent);

            confirmAlert.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {
                    String userpassword = passwordField.getText();
                    String userIdUp = txtUserId.getText();

                    try {
                        boolean confirm = USERBO.confirmation(userIdUp,userpassword);
                        if (confirm) {
                            UserDTO userDto = new UserDTO(userId, userName, userpassword ,role);
                            boolean isUpdated = USERBO.updateUser(userDto);
                            if (isUpdated) {
                                new Alert(Alert.AlertType.INFORMATION, "User Update Successfully!").show();
                                loadTableData();
                                refrashPage();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed to Update User!").show();
                            }
                        }else{
                            new Alert(Alert.AlertType.ERROR, "Invalid Password Please Try Again....!").show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "load Table Exception" + e.getMessage()).show();
                    }

                }
            });
        }else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

}
