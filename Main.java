import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // initialized fields
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        boolean flag = false;
        String strInput = "";
        int intInput = 0;
        IterableMultiKeyRBT<Song> songDatabase = new IterableMultiKeyRBT<>();
        Backend backend = new Backend(songDatabase);

        // command loop
        while (option != 4) {
            System.out.println("\nWelcome to song searcher, choose an option:\n" +
                    "1: load playlist data\n" +
                    "2: show average dancebility of playlist\n" +
                    "3: list songs >= a minimum danceability\n" +
                    "4: exit loop\n"
            );
            option = scanner.nextInt();

            // clear the input buffer
            scanner.nextLine();

            if (option == 4) {
                System.out.println("end cmdloop");
                continue;
            }
            else if (option == 1){
                System.out.print("enter playlist to load: ");
                strInput = scanner.nextLine();
                System.out.println("successfully loaded playlist: " + strInput);
                backend.readFromFile(strInput);
                flag = true; // guarantees a playlist is loaded
            }
            else if (option == 2 && flag) {
                System.out.println("Average dancebility of this playlist is: " + backend.findAvgDanceability());
            }
            else if (option == 3 && flag) {
                System.out.print("enter minDanceability allowed: ");
                intInput = scanner.nextInt();
                Song[] minSongs = backend.minDanceability(intInput);
                for (Song song: minSongs) {
                    System.out.println(song.getTitle() + " by " + song.getArtist());
                }
            }
            else {
                System.out.println("please provide valid input, thanks");
            }
        }
        scanner.close();
    }
}

