package ru.innopois.stc9;

/**
 * Runs search through huge input file.
 *
 * @author Daniil Ivantsov
 * @version 1.0
 */


import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HugeFileProcessor {
    final static Logger logger = Logger.getLogger("file");

    public String file;
    private SearchResultKeeper resultKeeper;
    HugeFileSplitter hugeFileSplitter;
    String[] words;
    ArrayList<Thread> threadList;
    public final int TEMP_FILE_LENGTH = 512*1024;

    public HugeFileProcessor(SearchResultKeeper resultKeeper, String file, String[] words) {
        this.resultKeeper = resultKeeper;
        this.file = file;
        this.words = words;
        hugeFileSplitter = new HugeFileSplitter(file);
        hugeFileSplitter.split();
        threadList = new ArrayList<>();
    }

    /*public void process1() {
        Executor executor = Executors.newFixedThreadPool(32);
        byte[] buffer = new byte[TEMP_FILE_LENGTH];
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            int bytes = 0;
            while ((bytes = inputStream.read(buffer)) > 0) {

            }
        }
    }*/

    /**
     * Splits huge file using HugeFileSplitter class and creates a thread for each temp file
     */
    public void process() {
        Executor executor = Executors.newFixedThreadPool(64);
        for (File file : hugeFileSplitter.getTempFiles()) {
            String filePath = file.getAbsolutePath();
            Runnable fw = new SourceWalker(resultKeeper, filePath, words);
            executor.execute(fw);
        }
        ((ExecutorService) executor).shutdown();
        while (!((ExecutorService) executor).isTerminated()) {
        }
        logger.info("Finished all threads.");
        hugeFileSplitter.deleteTempFiles();
    }

}
