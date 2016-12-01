package agrendalath.helpers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Input {
    public static String get(Class source) {
        StringBuilder path = new StringBuilder("/");
        path = path.append(source.getSimpleName()).delete(1, path.length()-1).append(".txt");

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(Input.class.getResource(path.toString()).toURI()));
            return new String(encoded, Charset.defaultCharset()).trim();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
    }
}
