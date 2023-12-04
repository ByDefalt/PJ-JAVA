package formation;

import java.util.Objects;

// A DEFINIR
public class UniteEnseignement implements InterUniteEnseignement {

    private final String nomUE;
    private String nomEnseignant;
    private int nbPlaces = 0;
    private int nbPlacesMax = 0;

    public UniteEnseignement(String nomUE, String nomEnseignant, int nbPlaces) {
        this.nomUE = nomUE;
        this.nomEnseignant = nomEnseignant;
        this.nbPlacesMax = nbPlaces;
    }

    public UniteEnseignement(String nomUE, String nomEnseignant) {
        this.nomUE = nomUE;
        this.nomEnseignant = nomEnseignant;
    }

    @Override
    public String getNomUE() {
        return nomUE;
    }

    @Override
    public int getNbPlacesMax() {
		return nbPlacesMax;
	}

	public void setNbPlacesMax(int nbPlacesMax) {
		this.nbPlacesMax = nbPlacesMax;
	}

	@Override
    public String getNomEnseignant() {
        return nomEnseignant;
    }

    @Override
    public void setNomEnseignant(String nomEnseignant) {
        this.nomEnseignant = nomEnseignant;
    }

    @Override
    public int getNbPlaces() {
        return nbPlaces;
    }

    @Override
    public void setNbPlaces() {
        this.nbPlaces += 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nomUE, this.nomEnseignant, this.nbPlaces);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniteEnseignement other = (UniteEnseignement) obj;
        if (nomUE == null) {
            if (other.nomUE != null)
                return false;
        } else if (!nomUE.equals(other.nomUE))
            return false;
        return true;
    }

}
