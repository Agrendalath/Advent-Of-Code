package agrendalath.helpers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    public static String get(Class source) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(source.getSimpleName());
        String number = "";
        while (matcher.find())
            number = matcher.group();
        String path = '/' + number + ".txt";

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(Input.class.getResource(path).toURI()));
            return new String(encoded, Charset.defaultCharset()).trim();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
    }
}
