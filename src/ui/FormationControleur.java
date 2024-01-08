package ui;

import java.net.URL;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * Le contr�leur associ� � la fen�tre d�finie dans formation.fxml.
 *
 * @author Eric Cariou
 */
public class FormationControleur {
  private GestionFormation ges;

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

  }

  @FXML
  void actionBoutonAffectationManuelleGroupes(ActionEvent event) {

  }

  @FXML
  void actionBoutonAfficherEtudiantsGroupeTD(ActionEvent event) {

  }

  @FXML
  void actionBoutonAfficherEtudiantsGroupeTP(ActionEvent event) {

  }

  @FXML
  void actionBoutonAfficherEtudiantsUEOptionnelle(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ObservableList<String> observableEtudiants = FXCollections.observableArrayList(ges
          .listeEtudiantsOption(ges.getGestionEtudiant().getListeUE().stream()
              .filter(ue -> ue.getNomUE().equals(listeUEOptionnelles.getSelectionModel().getSelectedItem()))
              .findFirst()
              .orElse(null))
          .stream().map(etudiant -> Integer.toString(etudiant.getNumero())).collect(Collectors.toSet()));
      listeEtudiants.setItems(observableEtudiants);
      labelListeEtudiants
          .setText("Les étudiants inscrits à " + listeUEOptionnelles.getSelectionModel().getSelectedItem());
    }
  }

  @FXML
  void actionBoutonAfficherTousEtudiants(ActionEvent event) {
    ObservableList<String> observableEtudiants = FXCollections
        .observableArrayList(ges.getGestionEtudiant().getListeEtudiants().stream()
            .map(etudiant -> Integer.toString(etudiant.getNumero()))
            .collect(Collectors.toList()));
    listeEtudiants.setItems(observableEtudiants);
    labelListeEtudiants.setText("Tous les étudiants de la formation");
  }

  @FXML
  void actionBoutonCreerFormation(ActionEvent event) {
    ges.creerFormation(entreeNomFormation.getText(), entreeNomResponsableFormation.getText(),
        entreeEmailResponsableFormation.getText());
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
  }

  @FXML
  void actionBoutonNombreChoixOptions(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ges.definirNombreOptions(Integer.parseInt(entreeNombreChoixOptions.getText()));
    }
  }

  @FXML
  void actionBoutonSetTailleGroupeTD(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ges.setTailleGroupeDirige(Integer.parseInt(entreeTailleGroupeTD.getText()));
    }
  }

  @FXML
  void actionBoutonSetTailleGroupeTP(ActionEvent event) {
    if (ges.getNomFormation() != null) {
      ges.setTailleGroupePratique(Integer.parseInt(entreeTailleGroupeTP.getText()));
    }
  }

  @FXML
  void actionMenuApropos(ActionEvent event) {

  }

  @FXML
  void actionMenuCharger(ActionEvent event) {

  }

  @FXML
  void actionMenuQuitter(ActionEvent event) {

  }

  @FXML
  void actionMenuSauvegarder(ActionEvent event) {

  }

  @FXML
  void actionSelectionEtudiant(MouseEvent event) {
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
        System.err.println("Erreur : La capacité d'accueil doit être un entier.");
      }
      entreeNomResponsableUE.setText("");
      entreeCapaciteAccueil.setText("");
      entreeNomUE.setText("");
    }
  }

  @FXML
  void initialize() {

  }
}
