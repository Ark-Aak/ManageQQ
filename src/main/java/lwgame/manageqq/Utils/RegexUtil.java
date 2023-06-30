package lwgame.manageqq.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static Matcher match(String line, String pattern){
        Pattern r = Pattern.compile(pattern);
        return r.matcher(line);
    }
}
