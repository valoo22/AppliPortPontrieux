package metier;

public class Emplacement
{
	private int refEmplacement;
	private boolean dispo;
    private Type unType;

    public Emplacement(int refEmplacement, boolean dispo, Type unType)
        {
            this.refEmplacement = refEmplacement;
            this.dispo = dispo;
            this.unType = unType;
        }

    public Emplacement()
        {

        }

    public int getRefEmplacement() {
        return refEmplacement;
    }

    public void setRefEmplacement(int refEmplacement) {
        this.refEmplacement = refEmplacement;
    }

    public boolean getDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public Type getUnType() {
        return unType;
    }

    public void setUnType(Type unType) {
        this.unType = unType;
    }
}
