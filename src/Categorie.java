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

    private static int Atoi(String nbr){
        String[] list = nbr.split("");
        int result = 0;
        int i = 0;
        while(i < list.length) {
            if (list[i].compareTo(" ") != 0){
                result = Integer.parseInt(list[i]);
            }
            i++;
        }
        return result;
    }

    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {
        lexique = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String ligne = scanner.nextLine();
                ligne = ligne.toLowerCase();
                String[] list = ligne.split(":");
                String chaine = list[0];
                int entier = Atoi(list[1]);
                PaireChaineEntier paire = new PaireChaineEntier(chaine, entier);
                lexique.add(paire);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
