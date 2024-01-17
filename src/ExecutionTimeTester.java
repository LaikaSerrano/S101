import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExecutionTimeTester {
    public static void main(String[] args) {
        Classification classification = new Classification();
        ArrayList<Categorie> categories = new ArrayList<>();
        Categorie categorie1 = new Categorie("SPORTS");
        Categorie categorie2 = new Categorie("POLITIQUE");
        Categorie categorie3 = new Categorie("ECONOMIE");
        Categorie categorie4 = new Categorie("CULTURE");
        Categorie categorie5 = new Categorie("ENVIRONNEMENT-SCIENCES");
        categories.add(categorie1);
        categories.add(categorie2);
        categories.add(categorie3);
        categories.add(categorie4);
        categories.add(categorie5);
        lectureDepeches("./depeches.txt");
        classification.classementDepeches(lectureDepeches("./depeches.txt"), categories, "./resultat.txt");

        
        classification.initDico(lectureDepeches("./depeches.txt"), "ECONOMIE");

        long startTime = System.currentTimeMillis();
        classification.calculScores(lectureDepeches("./depeches.txt"), "ECONOMIE", classification.initDico(lectureDepeches("./depeches.txt"), "ECONOMIE"));
        long endTime = System.currentTimeMillis();
        
        classification.generationLexique(lectureDepeches("./depeches.txt"), "ECONOMIE", "./LexiqueECONOMIE.txt");
        categorie1.initLexique("./LexiqueENVIRONNEMENT-SCIENCES.txt");
        categorie1.score(lectureDepeches("./depeches.txt").get(0));
        UtilitairePaireChaineEntier.chaineMax(categorie1.getLexique());
        UtilitairePaireChaineEntier.moyenne(categorie1.getLexique());
        UtilitairePaireChaineEntier.entierPourChaine(categorie1.getLexique(), "pour");
        UtilitairePaireChaineEntier.indicePourChaine(categorie1.getLexique(), "pour");
        UtilitairePaireChaineEntier.entierMax(categorie1.getLexique());

        System.out.println("votre execution a été réalisée en : " + (endTime-startTime) + "ms");
    }

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
}
