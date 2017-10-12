package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fkruege on 10/2/17.
 */
public class SortTest {

    public static void main(String[] args){
        List<String>  artists = new ArrayList<>();
        artists.add("f");
        artists.add("c");
        artists.add(null);
        artists.add("a");

        Collections.sort(artists);

        for(String artist : artists){
            System.out.println(artist);
        }
    }

}
