import org.junit.Before;
import org.junit.Test;
import ru.innopois.stc9.SearchResultKeeper;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SearchResultKeeperTest {
    private SearchResultKeeper searchKeeper;

    @Before
    public void before() {
        searchKeeper = new SearchResultKeeper();
    }

    @Test
    public void addTest() {
        String string = "";
        searchKeeper.add(string);
        assertTrue(searchKeeper.getResult().contains(string));
    }

    @Test
    public void addAllTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        searchKeeper.addAll(list);
        for (String s : list) {
            assertTrue(searchKeeper.getResult().contains(s));
        }

    }

    @Test
    public void clearTest() throws RuntimeException {
        searchKeeper.clear();
        assertTrue(searchKeeper.getResult().isEmpty());
    }
}
