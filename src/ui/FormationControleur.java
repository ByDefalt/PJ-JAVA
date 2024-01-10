package ui;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import formation.Etudiant;
import formation.GestionFormation;
import formation.InformationPersonnelle;
import formation.UniteEnseignement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Le contr�leur associ� � la fen�tre d�finie dans formation.fxml.
 *
 * @author Eric Cariou
 */
public class FormationControleur {
  private GestionFormation ges;
  private Stage fenetreEtudiants;
  private Stage FenetreFormation;

  public void setFenetreFormation(Stage FenetreFormation) {
    this.FenetreFormation = FenetreFormation;
  }

  public void setFenetreEtudiants(Stage fenetreEtudiants) {
    this.fenetreEtudiants = fenetreEtudiants;
  }

  public void setGes(GestionFormation ges) {
    this.ges = ges;
  }

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private CheckBox checkInscriptionFinalisee;

  @FXML
  private TextField entreeAdresseEtudiant;

  @FXML
  private TextField entreeAgeEtudiant;

  @FXML
  private TextField entreeCapaciteAccueil;

  @FXML
  private TextField entreeEmailResponsableFormation;

  @FXML
  private TextField entreeGroupeTDEtudiant;

  @FXML
  private TextField entreeGroupeTPEtudiant;

  @FXML
  private TextField entreeNomEtudiant;

  @FXML
  private TextField entreeNomFormation;

  @FXML
  private TextField entreeNomResponsableFormation;

  @FXML
  private TextField entreeNomResponsableUE;

  @FXML
  private TextField entreeNomUE;

  @FXML
  private TextField entreeNombreChoixOptions;

  @FXML
  private TextField entreePrenomEtudiant;

  @FXML
  private TextField entreeTailleGroupeTD;

  @FXML
  private TextField entreeTailleGroupeTP;

  @FXML
  private Label labelListeEtudiants;

  @FXML
  private Label labelNbGroupesTD;

  @FXML
  private Label labelNbGroupesTP;

  @FXML
  private ListView<String> listeEtudiants;

  @FXML
  private ListView<String> listeUEObligatoires;

  @FXML
  private ListView<String> listeUEOptionnelles;

  @FXML
  private ToggleGroup obligation;

  @FXML
  private RadioButton radioBoutonObligatoire;

  @FXML
  private RadioButton radioBoutonOptionnelle;

  @FXML
  void actionBoutonAffectationAutomatique(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ges.attribuerAutomatiquementGroupes();
      labelNbGroupesTD.setText(Integer.toString(ges.nombreGroupesTravauxDiriges()));
      labelNbGroupesTP.setText(Integer.toString(ges.nombreGroupesTravauxPratiques()));
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonAffectationManuelleGroupes(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      Etudiant etu = ges.getGestionEtudiant().getListeEtudiants().stream()
          .filter(etudiant -> Integer.toString(etudiant.getNumero())
              .equals(listeEtudiants.getSelectionModel().getSelectedItem()))
          .findFirst()
          .orElse(null);
      ges.changerGroupe(
          etu,
          Integer.parseInt(entreeGroupeTDEtudiant.getText()),
          Integer.parseInt(entreeGroupeTPEtudiant.getText()));
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonAfficherEtudiantsGroupeTD(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ObservableList<String> observableEtudiants = FXCollections.observableArrayList(
          Optional.ofNullable(ges.listeEtudiantsGroupeDirige(Integer.parseInt(entreeGroupeTDEtudiant.getText())))
              .map(liste -> liste.stream()
                  .map(etudiant -> Integer.toString(etudiant.getNumero()))
                  .collect(Collectors.toList()))
              .orElse(Collections.emptyList()));
      listeEtudiants.setItems(observableEtudiants);
      labelListeEtudiants.setText("Les étudiants du groupe de TD " + entreeGroupeTDEtudiant.getText());
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonAfficherEtudiantsGroupeTP(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      if (entreeGroupeTPEtudiant.getText() != null && !entreeGroupeTPEtudiant.getText().isEmpty()) {
        try {
          int a = Integer.parseInt(entreeGroupeTPEtudiant.getText());
          if (a > 0 && a <= ges.nombreGroupesTravauxPratiques()) {
            ObservableList<String> observableEtudiants = FXCollections.observableArrayList(
                Optional
                    .ofNullable(ges.listeEtudiantsGroupePratique(a))
                    .map(liste -> liste.stream()
                        .map(etudiant -> Integer.toString(etudiant.getNumero()))
                        .collect(Collectors.toList()))
                    .orElse(Collections.emptyList()));
            listeEtudiants.setItems(observableEtudiants);
            labelListeEtudiants.setText("Les étudiants du groupe de TP " + entreeGroupeTPEtudiant.getText());
          }else{
            this.afficherPopup("Le groupe n'existe pas, les groupe existant son compris entre 1 et "+ ges.nombreGroupesTravauxPratiques(), AlertType.ERROR);
          }
        } catch (NumberFormatException e) {
          this.afficherPopup("Le champ doit être un entier", AlertType.ERROR);
        }
      } else {
        this.afficherPopup("Le champ est vide", AlertType.ERROR);
      }
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonAfficherEtudiantsUEOptionnelle(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ObservableList<String> observableEtudiants = FXCollections.observableArrayList(ges
          .listeEtudiantsOption(ges.getGestionEtudiant().getListeUE().stream()
              .filter(ue -> ue.getNomUE().equals(listeUEOptionnelles.getSelectionModel().getSelectedItem()))
              .findFirst()
              .orElse(null))
          .stream()
          .map(etudiant -> Integer.toString(etudiant.getNumero()))
          .collect(Collectors.toSet()));
      listeEtudiants.setItems(observableEtudiants);
      labelListeEtudiants
          .setText("Les étudiants inscrits à " + listeUEOptionnelles.getSelectionModel().getSelectedItem());
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonAfficherTousEtudiants(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ObservableList<String> observableEtudiants = FXCollections
          .observableArrayList(ges.getGestionEtudiant().getListeEtudiants().stream()
              .map(etudiant -> Integer.toString(etudiant.getNumero()))
              .collect(Collectors.toList()));
      listeEtudiants.setItems(observableEtudiants);
      labelListeEtudiants.setText("Tous les étudiants de la formation");
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonCreerFormation(ActionEvent event) {
    ges.creerFormation(entreeNomFormation.getText(), entreeNomResponsableFormation.getText(),
        entreeEmailResponsableFormation.getText());
    if (ges.getNomFormation() != null) {
      labelNbGroupesTD.setText("...");
      labelNbGroupesTP.setText("...");
      entreeTailleGroupeTD.setText("");
      entreeTailleGroupeTP.setText("");
      entreeNombreChoixOptions.setText("");
      entreeNomResponsableUE.setText("");
      entreeCapaciteAccueil.setText("");
      entreeNomUE.setText("");
      listeUEObligatoires.getItems().clear();
      listeEtudiants.getItems().clear();
      listeUEOptionnelles.getItems().clear();
      entreeNomEtudiant.setText("");
      entreePrenomEtudiant.setText("");
      entreeAdresseEtudiant.setText("");
      entreeAgeEtudiant.setText("");
      entreeGroupeTDEtudiant.setText("");
      entreeGroupeTPEtudiant.setText("");
      checkInscriptionFinalisee.setSelected(false);
    } else {
      if (entreeNomResponsableFormation.getText() == null || entreeEmailResponsableFormation.getText().isEmpty()) {
        this.afficherPopup("Les champs nécessaire ne sont pas tous remplis !", AlertType.ERROR);
      } else {
        this.afficherPopup("Le champ Email est incorecte !", AlertType.ERROR);
      }
    }
  }

  @FXML
  void actionBoutonNombreChoixOptions(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      if (entreeNombreChoixOptions.getText() != null && !entreeNombreChoixOptions.getText().isEmpty()) {
        try {
          ges.definirNombreOptions(Integer.parseInt(entreeNombreChoixOptions.getText()));
          ges.getGestionEtudiant().getListeEtudiants().forEach(etudiant -> ges.setNbOptionEtudiant(etudiant));
        } catch (NumberFormatException e) {
          this.afficherPopup("Le champ est incorecte !", AlertType.ERROR);
        }
      } else {
        this.afficherPopup("Le champ est vide !", AlertType.ERROR);
      }
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonSetTailleGroupeTD(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      if (entreeTailleGroupeTD.getText() != null && !entreeTailleGroupeTD.getText().isEmpty()) {
        try {
          ges.setTailleGroupeDirige(Integer.parseInt(entreeTailleGroupeTD.getText()));
        } catch (NumberFormatException e) {
          this.afficherPopup("Le champ est incorecte !", AlertType.ERROR);
        }
      } else {
        this.afficherPopup("Le champ est vide !", AlertType.ERROR);
      }
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionBoutonSetTailleGroupeTP(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      if (entreeTailleGroupeTP.getText() != null && !entreeTailleGroupeTP.getText().isEmpty()) {
        try {
          ges.setTailleGroupePratique(Integer.parseInt(entreeTailleGroupeTP.getText()));
        } catch (NumberFormatException e) {
          this.afficherPopup("Le champ est incorecte !", AlertType.ERROR);
        }
      } else {
        this.afficherPopup("Le champ est vide !", AlertType.ERROR);
      }
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  @FXML
  void actionMenuApropos(ActionEvent event) {
    Stage nouvelleFenetre = new Stage();
    nouvelleFenetre.setTitle("Nouvelle Fenêtre");

    // Ajouter un Label pour le texte centré en haut
    Label texteLabel = new Label("Auteurs : ROUSVAL ROMAIN et LE BRAS ERWAN");

    // Ajouter un bouton pour fermer la fenêtre centré en bas
    Button fermerButton = new Button("Fermer la Fenêtre");
    fermerButton.setOnAction(Event -> nouvelleFenetre.close());

    // Utiliser un VBox pour disposer les éléments verticalement
    VBox layout = new VBox(10); // 10 pixels d'espace vertical entre les éléments
    layout.getChildren().addAll(texteLabel, fermerButton);

    // Centrer les éléments dans le VBox
    layout.setAlignment(javafx.geometry.Pos.CENTER);

    Scene scene = new Scene(layout, 300, 150);
    nouvelleFenetre.setScene(scene);

    nouvelleFenetre.show();
  }

  @FXML
  void actionMenuCharger(ActionEvent event) {
    try {
      ges.chargerDonnees("save");
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(ges.getGestionEtudiant().getListeEtudiants());
  }

  @FXML
  void actionMenuQuitter(ActionEvent event) {
    fenetreEtudiants.close();
    FenetreFormation.close();
  }

  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
    try {
      ges.sauvegarderDonnees("save");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void actionSelectionEtudiant(MouseEvent event) {
    if (ges.getNomFormation() != null) {
      Etudiant etu = ges.getGestionEtudiant().getListeEtudiants().stream()
          .filter(etudiant -> Integer.toString(etudiant.getNumero())
              .equals(listeEtudiants.getSelectionModel().getSelectedItem()))
          .findFirst()
          .orElse(null);
      if (etu != null) {
        InformationPersonnelle info = etu.getInformationPersonnelle();
        entreeNomEtudiant.setText(info.getNom());
        entreePrenomEtudiant.setText(info.getPrenom());
        entreeAdresseEtudiant.setText(info.getAdresse());
        entreeAgeEtudiant.setText(Integer.toString(info.getAge()));
        if (etu.getNumeroTd() != -1) {
          entreeGroupeTDEtudiant.setText(Integer.toString(etu.getNumeroTd()));
        }
        if (etu.getNumeroTp() != -1) {
          entreeGroupeTPEtudiant.setText(Integer.toString(etu.getNumeroTp()));
        }
        if (etu.getNumeroTd() != -1 && etu.getNumeroTp() != -1
            && etu.getListeUEsuivies().stream().filter(UniteEnseignement::getOptionnel).count() == ges.getNBoption()) {
          checkInscriptionFinalisee.setSelected(true);
        }
      }
    }
  }

  @FXML
  void actionSelectionUEObligatoire(MouseEvent event) {
    if (ges.getNomFormation() != null) {
      UniteEnseignement ue2 = ges.getGestionEtudiant().getListeUE().stream()
          .filter(ue -> ue.getNomUE().equals(listeUEObligatoires.getSelectionModel().getSelectedItem()))
          .findFirst()
          .orElse(null);
      if (ue2 != null) {
        entreeNomUE.setText(ue2.getNomUE());
        entreeNomResponsableUE.setText(ue2.getNomEnseignant());
        entreeCapaciteAccueil.setText(Integer.toString(ue2.getNbPlacesMax()));
      }
    }
  }

  @FXML
  void actionSelectionUEOptionnelle(MouseEvent event) {
    if (ges.getNomFormation() != null) {
      UniteEnseignement ue2 = ges.getGestionEtudiant().getListeUE().stream()
          .filter(ue -> ue.getNomUE().equals(listeUEOptionnelles.getSelectionModel().getSelectedItem()))
          .findFirst()
          .orElse(null);
      if (ue2 != null) {
        entreeNomUE.setText(ue2.getNomUE());
        entreeNomResponsableUE.setText(ue2.getNomEnseignant());
        entreeCapaciteAccueil.setText(Integer.toString(ue2.getNbPlacesMax()));
      }
    }
  }

  @FXML
  void actionBoutonCreerNouvelleUE(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      if (entreeNomUE.getText() != null && !entreeNomUE.getText().isEmpty() && entreeNomResponsableUE.getText() != null
          && !entreeNomResponsableUE.getText().isEmpty()) {
        UniteEnseignement ue = new UniteEnseignement(entreeNomUE.getText(), entreeNomResponsableUE.getText());
        try {
          if (radioBoutonObligatoire.isSelected()) {
            if (ges.ajouterEnseignementObligatoire(ue)) {
              listeUEObligatoires.getItems().add(ue.getNomUE());
            }
          } else if (radioBoutonOptionnelle.isSelected()) {
            int capaciteAccueil = Integer.parseInt(entreeCapaciteAccueil.getText());
            if (ges.ajouterEnseignementOptionnel(ue, capaciteAccueil)) {
              listeUEOptionnelles.getItems().add(ue.getNomUE());
            }
          }
        } catch (NumberFormatException e) {
          this.afficherPopup("La capacité d'accueil doit être un entier", AlertType.ERROR);
        }
        entreeNomResponsableUE.setText("");
        entreeCapaciteAccueil.setText("");
        entreeNomUE.setText("");
      } else {
        this.afficherPopup("Les champs nécessaire ne sont pas tous remplis !", AlertType.ERROR);
      }
    } else {
      this.afficherPopup("Aucune Formation", AlertType.ERROR);
    }
  }

  private void afficherPopup(String message, AlertType type) {
    Alert alert = new Alert(type);
    if (type == AlertType.ERROR) {
      alert.setTitle("Erreur");
    } else {
      alert.setTitle("Information");
    }
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.setResizable(true);
    alert.showAndWait();
  }

  // this.afficherPopup("Aucune Formation", AlertType.ERROR);
  @FXML
  void initialize() {

  }
}
