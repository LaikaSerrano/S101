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

    /***
     * @pre: 
     * @post: retourne la chaine associée à l'entier entier
     * @param listePaires
     */
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

    /***
     * @pre: liste non triée
     * @post: retourne la liste triée
     * @param liste
     */
    public static ArrayList<String> triFusion(ArrayList<String> liste){
        if(liste.size() <= 1)
            return liste;
        ArrayList<String> liste1 = new ArrayList<>();
        ArrayList<String> liste2 = new ArrayList<>();
        for(int i = 0; i < liste.size(); i++){
            if(i < liste.size()/2)
                liste1.add(liste.get(i));
            else
                liste2.add(liste.get(i));
        }
        liste1 = triFusion(liste1);
        liste2 = triFusion(liste2);
        return fusion(liste1, liste2);
    }

    /***
     * @pre: liste1 et liste2 triées
     * @post: renvoie la fusion des deux listes
     * @param liste1
     * @param liste2
     */
    private static ArrayList<String> fusion(ArrayList<String> liste1, ArrayList<String> liste2){
        ArrayList<String> resultat = new ArrayList<>();
        int i = 0;
        int j = 0;
        while(i < liste1.size() && j < liste2.size()){
            if(liste1.get(i).compareTo(liste2.get(j)) < 0){
                resultat.add(liste1.get(i));
                i++;
            }
            else{
                resultat.add(liste2.get(j));
                j++;
            }
        }
        while(i < liste1.size()){
            resultat.add(liste1.get(i));
            i++;
        }
        while(j < liste2.size()){
            resultat.add(liste2.get(j));
            j++;
        }
        return resultat;
    }

    /**
     * @post: renvoie l'entier maximum de la liste de PaireChaineEntier
     * @param scores
     * @return
     */
    public static int entierMax(ArrayList<PaireChaineEntier> scores) {
        int max = 0;
        for(PaireChaineEntier score : scores){
            if(score.getEntier() > max){
                max = score.getEntier();
            }
        }
        return max;
    }

    /***
     * @post: renvoie un arrayList de tout les scores par catégories de la depeche
     * @param d
     * @param categories
     * @return
     */
    public static ArrayList<PaireChaineEntier> scoreParCat(Depeche d, ArrayList<Categorie> categories) {
        ArrayList<String> mots = d.getMots(); //tout les mots de la dépêche
        int score =1 ;
        ArrayList<PaireChaineEntier> scores = new ArrayList<>();
        for(Categorie cat:categories){
            System.out.println();
            score = 0;
            for(String mot:mots){
                for(PaireChaineEntier paire : cat.getLexique()){
                    if(mot.equals(paire.getChaine())){ //si le mot est contenu dans le lexique
                        score += paire.getEntier(); //ajouter le poids du mot au score
                    }
                }
            }
            scores.add(new PaireChaineEntier(cat.getNom(), score));
        }
        System.out.println(scores);
        return scores;
    }
}
