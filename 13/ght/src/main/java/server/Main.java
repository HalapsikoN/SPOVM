package server;

import helpfulMethods.FileReadable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/server")
public class Main extends HttpServlet implements FileReadable {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("D:\\SPOVM\\13\\httpPages\\mainPage.html"));
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
        printWriter.write(string);*/
        printHTMLFile(resp, "D:\\SPOVM\\13\\httpPages\\mainPage.html");
    }
}
