package helpfulMethods;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface FileReadable {

    default public void printHTMLFile(HttpServletResponse resp, String PATH) throws IOException {

        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(PATH));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String string = contentBuilder.toString();

        System.out.println(string);

        PrintWriter printWriter=resp.getWriter();
        printWriter.write(string);
    }
}
