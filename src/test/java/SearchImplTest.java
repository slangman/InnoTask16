import org.apache.log4j.Logger;
import org.junit.*;
import ru.innopois.stc9.SearchImpl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class SearchImplTest {
    private static Logger logger = Logger.getLogger(SearchImplTest.class);

    private String testDir = "c:\\testset\\verysimple";
    private SearchImpl searchImpl;
    private String[] sources;
    private String[] words;
    private String resultFile;

    @BeforeClass
    public static void beforeClass() {
        logger.info("@BeforeClass");
    }

    @Before
    public void before() {
        logger.info("@Before");
        searchImpl = new SearchImpl();
        File[] files = new File(testDir).listFiles();
        sources = new String[files.length];
        for (int i = 0; i < sources.length; i++) {
            logger.info("Test file added: " + files[i].getPath());
            sources[i] = files[i].getPath();
        }
        words = new String[] {"starter", "ffdf", "wfrrf", "cdcd","dc"};
        resultFile = "c:\\result_file.txt";
    }

    @Test
    public void nullInputTest() {
        sources = null;
        words = null;
        resultFile = null;
        searchImpl.getOccurencies(sources, words, resultFile);
    }

    @Test
    public void invalidInputTest() {
        sources = new String[] {"idhnvidncv", "edjeioqcw", "1028910"};
        searchImpl.getOccurencies(sources, words, resultFile);
    }

    @Test
    public void inputContainsNullsTest() {
        String[] sourcesWithNull = new String[2];
        sourcesWithNull[0] = sources[0];
        sourcesWithNull[1] = null;
        String[] wordsWithNull = new String[2];
        wordsWithNull[0] = words[0];
        wordsWithNull[1] = null;
        searchImpl.getOccurencies(sourcesWithNull, wordsWithNull, resultFile);
    }

    @Test
    /**
     * tests input which contains not-existing sources.
     */
    public void fakeInputTest() {
        String[] fakeSources = new String[] {"g:\\1.txt", "ftp://fakeftp.com/fake.txt", "http://fakesite.fakedomain/fake.txt"};
        searchImpl.getOccurencies(fakeSources, words, resultFile);
    }

    @Test
    /**
     * tests output resource in location that can not be created.
     */
    public void fakeOutputTest() {
        String fakeResultFile = "k:\\resultFile.txt";
        searchImpl.getOccurencies(sources, words, fakeResultFile);
    }

    @Test
    public void getOccurenciesResultFileExistsTest() {
        logger.info("@Test getOccurencies method");
        searchImpl.getOccurencies(sources, words, resultFile);
        File result = new File(resultFile);
        assertTrue(result.exists());
    }


    @Test
    public void isHugeTest() throws IOException {
        logger.info("@Test isHuge method");
        boolean res;
        String source = "c:\\1.txt";
        RandomAccessFile file = new RandomAccessFile(source, "rw");
        file.setLength(3*1024*1024);
        res = searchImpl.isHugeFile(source);
        assertTrue(res);
    }

    @After
    public void after() {
        if (resultFile!=null && resultFile.length()>0) {
            File result = new File(resultFile);
            if (result.isFile()) {
                try {
                    Files.delete(result.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
