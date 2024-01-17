import jdk.jshell.execution.Util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Classification {


    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }

    /***
     * @post: écrit dans le fichier nomFichier l'id de la dépêche et sa catégorie calculée 
     * @post: puis 6 lignes montrant les moyennes de vérité par calcul sur chaque catégorie et la moyenne totale
     * @param depeches
     * @param categories
     * @param nomFichier
     */
    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        //TODO optimiser 
        try{
            ArrayList<PaireChaineEntier> verite = new ArrayList<>();
            for (Categorie cat:categories) {
                verite.add(new PaireChaineEntier(cat.getNom(), 0));
                if(cat.getLexique() == null)
                    cat.initLexique("./Lexique" + cat.getNom() + ".txt");
            }
            FileWriter file = new FileWriter(nomFichier);
            ArrayList<String> categoriesDepeches = new ArrayList<>();
            for(Depeche depeche:depeches){
                ArrayList<PaireChaineEntier> scores = UtilitairePaireChaineEntier.scoreParCat(depeche, categories);
                file.write(depeche.getId() + ":" + UtilitairePaireChaineEntier.chaineMax(scores) + "\n");
                categoriesDepeches.add(UtilitairePaireChaineEntier.chaineMax(scores));
            }
            // vérification de la vérité des scores

            
            for(int i = 0; i < categoriesDepeches.size(); i++) {
                for (int j = 0; j < categories.size(); j++) {
                    if (categoriesDepeches.get(i).equals(categories.get(j).getNom())) {
                        verite.get(j).setEntier(verite.get(j).getEntier() + 1);
                    }
                }
            }
            for(PaireChaineEntier paire:verite){
                file.write(paire.getChaine() + ":" + paire.getEntier() + "\n");
            }
            
            int moyenne = 0;
            for(PaireChaineEntier paire:verite) 
                moyenne += paire.getEntier();
            file.write("moyenne:" + moyenne / verite.size());
            
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /***
     * @pre: depeches, ArrayList de toutes les dépêches
     * @post: retourne un ArrayList de PaireChaineEntier contenant tous les mots de plus de 2 lettres
     * @param depeches
     * @param categorie
     */
    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        ArrayList<String> mots = new ArrayList<>();
        int i = 0;
        //vérifier pour chaque depeche si la catégorie correspond à la catégorie passée en paramètre
        for(Depeche depeche: depeches){
            if(depeche.getCategorie().equals(categorie)){
                for(String mot:depeche.getMots()){
                    if(mot.length() > 3)
                        mots.add(mot); //ajouter chaque mots de plus de 3 lettres
                }
            }
        }
        mots = UtilitairePaireChaineEntier.triFusion(mots); //trier pour simplifier la vérification
        while(i< mots.size()) {
            while (i + 1 < mots.size() && mots.get(i).equals(mots.get(i + 1))) { //tant que le mot suivant est le même
                i++; //passer a la suite
            }
            if (mots.get(i).length() > 3) //pour les mots de plus de 3 lettres
                resultat.add(new PaireChaineEntier(mots.get(i), 0)); //ajouter avec un score égal a 0
            i++;
        }
        return resultat;

    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
        HashMap<String, Integer> map = new HashMap<>();

        for (Depeche depeche : depeches) {
            for (String mot : depeche.getMots()) {
                map.put(mot, map.getOrDefault(mot, 0) + (depeche.getCategorie().equals(categorie) ? 1 : -1));
            }
        }

        for (PaireChaineEntier paire : dictionnaire) {
            paire.setEntier(map.getOrDefault(paire.getChaine(), 0));
        }
    }
    
    public static int poidsPourScore(int score) {
        if (score < 0)
            return 1;
        else if (score == 0)
            return 2;
        return 3;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        //TODO optimiser
        ArrayList<PaireChaineEntier> dictionnaire = initDico(depeches, categorie);
        calculScores(depeches, categorie, dictionnaire);
        try{
            FileWriter file = new FileWriter(nomFichier);
            for(PaireChaineEntier paire:dictionnaire){
                if(poidsPourScore(paire.getEntier()) > 0) {
                    file.write(paire.getChaine() + ":" + poidsPourScore(paire.getEntier()) + "\n");
                }
            }
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }
        /*****************************
         * Initialisation du lexique *
         *****************************/
        
        Categorie environnement = new Categorie("ENVIRONNEMENT-SCIENCES");
        environnement.initLexique("./LexiqueENVIRONNEMENT-SCIENCES.txt");
        Categorie politique = new Categorie("POLITIQUE");
        politique.initLexique("./LexiquePOLITIQUE.txt");
        Categorie economie = new Categorie("ECONOMIE");
        economie.initLexique("./LexiqueECONOMIE.txt");
        Categorie culture = new Categorie("CULTURE");
        culture.initLexique("./LexiqueCULTURE.txt");
        Categorie sport = new Categorie("SPORTS");
        sport.initLexique("./LexiqueSPORTS.txt");

        ArrayList<Categorie> categories = new ArrayList<>();
        categories.add(environnement);
        categories.add(politique);
        categories.add(economie);
        categories.add(culture);
        categories.add(sport);

        // liste de score en fonction de la catégorie
        ArrayList<PaireChaineEntier> scores = new ArrayList<>();
        for(int i = 0; i < categories.size(); i++){
            int score = categories.get(i).score(depeches.get(i));
            scores.add(new PaireChaineEntier(categories.get(i).getNom(), score));
        }
        for(PaireChaineEntier score : scores){
            System.out.println(score);
        }

        System.out.println("La catégorie ayant le score max est : " + UtilitairePaireChaineEntier.chaineMax(scores)); // prend le score max de scores

        System.out.println(initDico(depeches, "ECONOMIE"));
        ArrayList<PaireChaineEntier> dictionnaire = initDico(depeches, "ECONOMIE");

        calculScores(depeches, "ECONOMIE", dictionnaire);
        for(PaireChaineEntier paire : dictionnaire){
            System.out.println(paire);
        }


        classementDepeches(depeches, categories, "./resultat.txt");
        generationLexique(depeches, "ECONOMIE", "./LexiqueECONOMIE.txt");
        generationLexique(depeches, "CULTURE", "./LexiqueCULTURE.txt");
        generationLexique(depeches, "SPORTS", "./LexiqueSPORTS.txt");
        generationLexique(depeches, "POLITIQUE", "./LexiquePOLITIQUE.txt");
        generationLexique(depeches, "ENVIRONNEMENT-SCIENCES", "./LexiqueENVIRONNEMENT-SCIENCES.txt");


        
        //Unit tests sur entierPourChaine
        // Scanner lecteur = new Scanner(System.in);
        //        System.out.println("saississez un mot : ");
        //        String mot = lecteur.nextLine();
        //        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(categorie.getLexique(), mot));

        //affichage du score
//        System.out.println("Score : " + environnement.score(depeches.get(0)));

    }


}