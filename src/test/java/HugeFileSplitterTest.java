import org.junit.Before;
import org.junit.Test;
import ru.innopois.stc9.HugeFileSplitter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.Assert.*;

public class HugeFileSplitterTest {
    private HugeFileSplitter fileSplitter;

    @Before
    public void before() throws IOException {
        String source = "c:\\1.txt";
        RandomAccessFile file = new RandomAccessFile(source, "rw");
        file.setLength(3*1024*1024);
        fileSplitter = new HugeFileSplitter(source);
    }

    @Test
    public void splitTest() {
        fileSplitter.split();
        assertTrue(fileSplitter.getTempFiles().size()>0);
        for (File file : fileSplitter.getTempFiles()) {
            assertTrue(file.exists());
        }
    }

    @Test
    public void deleteFilesTest() {
        fileSplitter.split();
        fileSplitter.deleteTempFiles();
        for (File file : fileSplitter.getTempFiles()) {
            assertFalse(file.exists());
        }
    }

}
