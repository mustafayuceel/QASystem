/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sc.application;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import my.sc.db.MongoDbOperations;

import my.sc.models.Event;
import my.sc.models.Result;
import my.sc.models.Target;

/**
 *
 * @author myuceel
 */
public class ApplicationController extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8"); 
        request.setCharacterEncoding("UTF-8");
        

        PrintWriter out = response.getWriter();

        String userPath = request.getServletPath();
        String dbName = "scdb";
        String collectionName = "people";


        String jsonResponse = "wrong-url";

        try {
            Gson gson = new Gson();
            MongoDbOperations mongoDbOperations = new MongoDbOperations();


            if (userPath.equals("/ApplicationController/getAllDbNames")) {

                List<String> sonuc = mongoDbOperations.getAllDbNames();
                jsonResponse = gson.toJson(sonuc);

            } else if (userPath.equals("/ApplicationController/getAllCollectionNames")) {

                Set<String> sonuc = mongoDbOperations.getAllCollectionNames(dbName);
                jsonResponse = gson.toJson(sonuc);

            } else if (userPath.equals("/ApplicationController/addEvent")) {

                String author_id = request.getParameter("author_id");
                String m_type = request.getParameter("m_type");
                String m_id = request.getParameter("m_id");
                
                String event_name = request.getParameter("event_name");
                String event_id = request.getParameter("event_id");
                String actor_id = request.getParameter("actor_id");
                String detail = request.getParameter("detail");
                String message_text = request.getParameter("message_text");
                message_text=message_text==null?"empty":message_text;
                detail=detail==null?"":detail;
                event_id=event_id==null?"0":event_id;
                

                Target target = new Target(author_id,m_type,m_id);
                
                Result sonuc = mongoDbOperations.addEvent(dbName, collectionName, target, new Event(event_id,event_name, actor_id,detail,Calendar.getInstance().getTimeInMillis()),message_text);

                jsonResponse = gson.toJson(sonuc);

            } else if (userPath.equals("/ApplicationController/test")) {

                String sonuc = "test";
                jsonResponse = gson.toJson(sonuc);

            }



            out.print(jsonResponse);

        } catch (Exception ex) {
            out.print(ex);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
