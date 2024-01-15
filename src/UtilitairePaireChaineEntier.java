import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return 0;

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


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        return 0;
    }

}
