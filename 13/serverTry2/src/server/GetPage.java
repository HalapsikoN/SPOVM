package server;

import inputExceptionsMongo.NoHeaderEcxeption;
import inputExceptionsMongo.NoSuchElementInDBException;
import mongo.MongoWork;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/server/get")
public class GetPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/get.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String header=req.getParameter("header");

        MongoWork mongo=new MongoWork();

        try {
            String information=mongo.getByHeader(header);
            req.setAttribute("inf", information);
            req.getRequestDispatcher("/WEB-INF/getDone.jsp").forward(req,resp);
        } catch (NoHeaderEcxeption ex) {
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/getException.jsp").forward(req,resp);
        } catch (NoSuchElementInDBException ex) {
            req.setAttribute("head", header);
            req.getRequestDispatcher("/WEB-INF/getNoSuchElement.jsp").forward(req,resp);
        }
    }
}
