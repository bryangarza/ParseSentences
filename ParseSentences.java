import java.util.List;
import java.util.ArrayList;

import com.fiskkit.Fetcher;

public class ParseSentences {
    public static void main(String[] argv) {
        List<String> sentences = new ArrayList<String>();
        sentences = Fetcher.pullAndExtract("http://www.weeklystandard.com/blogs/intel-chief-blasts-obama_802242.html");

    for (String s : sentences)
        System.out.println(s);
    }
}
