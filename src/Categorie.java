import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private ArrayList<PaireChaineEntier> lexique; //le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }


    public String getNom() {
        return nom;
    }


    public  ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }


    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public static void initLexique(String nomFichier) {
        ArrayList<PaireChaineEntier> lexique = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                ligne = ligne.toLowerCase();
                String[] mots = ligne.split(":");
                String mot = mots[0];
                int poids = Integer.parseInt(mots[1]);
                PaireChaineEntier paire = new PaireChaineEntier(mot, poids);
                lexique.add(paire);
                String lignes = ligne.substring(0);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(PaireChaineEntier paire : lexique){
            System.out.println(paire.getChaine() + " " + paire.getEntier());
        }
    }


    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        ArrayList<String> mots = d.getMots();
        String categorie = d.getCategorie();
        String nomFichiers = "./Lexique"+ categorie + ".txt";
        initLexique(nomFichiers);
        int score = 0;
        for(String mot:mots){
            for(PaireChaineEntier paire : lexique){
                if(mot.equals(paire.getChaine())){
                    score += paire.getEntier();
                }
            }
        }

        return score;
    }


}
