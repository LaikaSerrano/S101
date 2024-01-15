import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    /***
     * @pre: 
     * @post: retourne l'indice de la chaine chaine dans la liste listePaires
     * @param listePaires
     * @param chaine
     */
    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        for (PaireChaineEntier chaineEntier : listePaires) {
            if (chaineEntier.getChaine().equals(chaine)) {
                return listePaires.indexOf(chaineEntier);
            }
        }
        return -1;
    }

    /***
     * @pre: 
     * @post: retourne l'entier associé à la chaine chaine
     * @param listePaires
     * @param chaine
     */
    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int entier = 0;
         for(PaireChaineEntier chaineEntier : listePaires){
             if(chaineEntier.getChaine().equals(chaine)){
                 entier = chaineEntier.getEntier();
             }
         }
         return entier;
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        int max = 0;
        String chaine = "";
        for(PaireChaineEntier chaineEntier : listePaires){
            if(chaineEntier.getEntier() > max){ // si l'entier trouvé est supérieur au max
                max = chaineEntier.getEntier(); // changer le max relatif
                chaine = chaineEntier.getChaine(); // changer la chaine courante
            }
        }
        return chaine;
    }

    /**
     * @post: renvoie la moyenne des entiers de la liste listePaires
     * @param listePaires
     * @return float moyenne
     */
    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        int sum = 0;
        for (PaireChaineEntier chaineEntier : listePaires) {
            sum += chaineEntier.getEntier();
        }
        return (float) sum / listePaires.size();
    }

}
