import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.fiskkit.Fetcher;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceDetectorME;

public class ParseSentences {
    public static final String ENCODING = "utf-8";

    /**
     * Create the sentence detector and fetch the url.
     * This is then written to a file with writeToFile();
     *
     * @param url       the website url we will fetch our article from
     * @param filename  the name of the file
     */
    public void doParse(String url, String filename) {
        List<String> paragraphs = new ArrayList<String>();
        paragraphs = Fetcher.pullAndExtract(url);

        InputStream modelIn = null;
        try {
            // Find the pre-trained model file in our classpath
            modelIn = ParseSentences.class.getClassLoader().getResourceAsStream("en-sent.bin");
            SentenceModel model = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

            writeToFile(paragraphs, sentenceDetector, filename);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Parses the paragraphs into sentences and writes them to a file.
     * This method overwrites whichever file it is writing to.
     *
     * @param paragraphs        the List of paragraph strings, obtained with
     * @param sentenceDetector  the detector we use to parse the paragraphs
     * @param filename          where the strings will be written to
     */
    public void writeToFile(List<String> paragraphs, SentenceDetectorME sentenceDetector, String filename) {
        // Use BufferedWriter instead of Writer for newLine()
        BufferedWriter writer = null;
        String sentences[] = null;
        String line = null;
        // Index that keeps count throughout all of the paragraphs, not just one
        int lineIndex = 0;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), ENCODING));

            for (String p : paragraphs) {
                sentences = sentenceDetector.sentDetect(p);

                for (String s : sentences) {
                    lineIndex++;
                    line = String.format("[%d] --> \"%s\"", lineIndex, s);
                    // System.out.println(line);
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] argv) {
        ParseSentences parser = new ParseSentences();
        parser.doParse("http://www.weeklystandard.com/blogs/intel-chief-blasts-obama_802242.html", "intel.txt");
        parser.doParse("http://www.thedailybeast.com/articles/2014/08/21/swat-lobby-takes-a-shot-at-congress.html", "swat.txt");
        parser.doParse("http://www.thedailybeast.com/articles/2014/08/12/russia-s-suspicious-humanitarian-aid-for-ukraine-separatists.html", "russia.txt");
    }
}
