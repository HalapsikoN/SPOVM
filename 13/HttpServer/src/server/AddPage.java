package server;

import helpfulMethods.FileReadable;
import inputExceptionsMongo.AlreadyHasHeaderException;
import inputExceptionsMongo.NoHeaderEcxeption;
import inputExceptionsMongo.NoInformationException;
import inputExceptionsMongo.ParameterException;
import mongo.MongoWork;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@WebServlet("/server/add")
public class AddPage extends HttpServlet implements FileReadable {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("D:\\SPOVM\\13\\HttpPages\\add.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String string = contentBuilder.toString();

        PrintWriter printWriter=resp.getWriter();
        printWriter.write(string);*/
        //printHTMLFile(resp, "D:\\SPOVM\\13\\HttpPages\\add.html");
        /*String message="sddsad";
        req.setAttribute("mes", message);*/
        req.getRequestDispatcher("/WEB-INF/add.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> enumerationParametersName=req.getParameterNames();
        List<String> listParametersName=new ArrayList<String>();
        while(enumerationParametersName.hasMoreElements()){
            listParametersName.add(enumerationParametersName.nextElement());
        }
        Map<String, String> parametersMap=new HashMap<String,String>();
        for(String parameter:listParametersName){
            parametersMap.put(parameter,req.getParameter(parameter));
            System.out.println(parameter+"="+req.getParameter(parameter));
        }

        MongoWork mongo=new MongoWork();
        /*try {
            mongo.add(listParametersName, parametersMap);
            req.getRequestDispatcher("/WEB-INF/addDone.jsp").forward(req,resp);
        }catch (ParameterException | NoHeaderEcxeption | NoInformationException | AlreadyHasHeaderException ex){
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/addException.jsp").forward(req,resp);
        }*/
        printHTMLFile(resp, "D:\\SPOVM\\13\\HttpPages\\addDone.html");
    }
}
