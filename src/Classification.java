import jdk.jshell.execution.Util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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


    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        try{
            FileWriter file = new FileWriter(nomFichier);
            for(Depeche depeche : depeches){
                ArrayList<PaireChaineEntier> scores = new ArrayList<>();
                for(Categorie categorie : categories){
                    int score = categorie.score(depeche);
                    PaireChaineEntier paire = new PaireChaineEntier(categorie.getNom(), score);
                    scores.add(paire);
                }
                String categorie = UtilitairePaireChaineEntier.chaineMax(scores);
                file.write(depeche.getId() + "\n");
                file.write(categorie + "\n");
            }
            //les 5 lignes suivantes correspondent au pourcentage de réponses correctes par rapport à la réalité (depeche.getCategorie())
            file.write("Environnement : 0.0\n");
            file.write("Politique : 0.0\n");
            file.write("Economie : 0.0\n");
            file.write("Culture : 0.0\n");
            file.write("Sport : 0.0\n");
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        ArrayList<String> mots = new ArrayList<>();
        int i = 0;
        for(Depeche depeche: depeches){
            if(depeche.getCategorie().equals(categorie)){
                for(String mot:depeche.getMots()){
                    if(mot.length()>2){
                        mots.add(mot);
                    }
                }
            }
        }
        mots = UtilitairePaireChaineEntier.triFusion(mots);
        while(i< mots.size()){
            while(i+1 < mots.size() && mots.get(i).equals(mots.get(i+1))){
                i++;
            }
            if(mots.get(i).length() > 3) {
                resultat.add(new PaireChaineEntier(mots.get(i), 0));
            }
            i++;
        }




        return resultat;

    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
    }

    public static int poidsPourScore(int score) {
        return 0;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

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
        
        Categorie environnement = new Categorie("Environnement");
        environnement.initLexique("./LexiqueENVIRONNEMENT-SCIENCES.txt");
        Categorie politique = new Categorie("Politique");
        politique.initLexique("./LexiquePOLITIQUE.txt");
        Categorie economie = new Categorie("Economie");
        economie.initLexique("./LexiqueECONOMIE.txt");
        Categorie culture = new Categorie("Culture");
        culture.initLexique("./LexiqueCULTURE.txt");
        Categorie sport = new Categorie("Sport");
        sport.initLexique("./LexiqueSPORT.txt");

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


        
        //Unit tests sur entierPourChaine
        Scanner lecteur = new Scanner(System.in);
        //        System.out.println("saississez un mot : ");
        //        String mot = lecteur.nextLine();
        //        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(categorie.getLexique(), mot));

        //affichage du score
        System.out.println("Score : " + environnement.score(depeches.get(0)));

    }


}