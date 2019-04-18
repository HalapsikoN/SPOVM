package server;

import inputExceptionsMongo.NoHeaderEcxeption;
import inputExceptionsMongo.NoInformationException;
import inputExceptionsMongo.NoSuchElementInDBException;
import mongo.MongoWork;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/server/change")
public class ChangePage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/change.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String header=req.getParameter("header");
        String inf=req.getParameter("inf");





        MongoWork mongo=new MongoWork();

        try {
            mongo.updateByHeader(header, inf);
            req.getRequestDispatcher("/WEB-INF/changeDone.jsp").forward(req,resp);
        } catch (NoHeaderEcxeption | NoInformationException ex) {
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/changeException.jsp").forward(req,resp);
        } catch (NoSuchElementInDBException ex) {
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/changeNoSuchElement.jsp").forward(req,resp);
        }
    }
}
