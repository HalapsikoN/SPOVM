package server;

import inputExceptionsMongo.NoHeaderEcxeption;
import inputExceptionsMongo.NoSuchElementInDBException;
import mongo.MongoWorkNote;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/server/delete")
public class DeletePage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/delete.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String header=req.getParameter("header");

        MongoWorkNote mongo=new MongoWorkNote();

        try {
            mongo.deleteByHeader(header);
            req.getRequestDispatcher("/WEB-INF/deleteDone.jsp").forward(req,resp);
        } catch (NoHeaderEcxeption ex) {
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/deleteException.jsp").forward(req,resp);
        } catch (NoSuchElementInDBException ex) {
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/deleteNoSuchElement.jsp").forward(req,resp);
        }
    }
}
