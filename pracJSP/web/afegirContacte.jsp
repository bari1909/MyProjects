<%-- 
    Document   : afegirContacte
    Created on : 3 ene. 2022, 20:36:57
    Author     : abdel
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ page import="org.milaifontanals.DBManager" %>
<%@ page import="java.sql.*,java.util.*" %>
<!DOCTYPE html>
<%
    String userName = (String)session.getAttribute("user");
    DBManager db = new DBManager();
    db.connect();
    Integer userId = db.getUserID(userName);
    if(request.getParameter("afegir") != null){
        String nom = request.getParameter("nom");
        String cognoms = request.getParameter("cognoms");
        String adreca = request.getParameter("adreca");
        String email = request.getParameter("email");
        String telefon=request.getParameter("telefon");
        String[] categories = request.getParameterValues("categories");
 
        for(int i = 0; i<categories.length;i++){
            out.print(categories[i]);
            out.print("<br><br>");
        }
        
        DBManager u = new DBManager();
        u.connect();
        if(u.afegirContacte(nom, cognoms, adreca, email, telefon,categories[0],userId) && !nom.equals("") ){
                
                out.print("S'ha afegit el contacte!!");
                //response.sendRedirect("usuario.jsp");
        }else{
                out.print("No s'ha afegit el contacte!!");
        }
    }
    
    if(request.getParameter("tornar") != null){
        response.sendRedirect("usuario.jsp");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Afegir Contacte</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <style>
            body{
                background-color: aliceblue;
            }
            .afegirContacte{
                width: 30%;
                margin:100px auto;
                background-color: aliceblue;
                text-align: center;
            }
            .afegir-form{
                display: flex;
                flex-direction: column;
                padding: 20px;
            }
            #inputs{
                margin-bottom: 10px;
            }
            .tornar{
                background-color: transparent;
                color: royalblue;
                border: 1px solid transparent;
                margin-top: 15px;
            }
        </style>
    </head>
    <body>
<!--        <div class="afegirContacte">
            <h1>Afegir Contacte</h1>
            <form method="post" action="afegirContacte.jsp">
                Nom:<br>
                <input type="text" name="nom">
                <br>
                Cognoms:<br>
                <input type="text" name="cognoms">
                <br>
                Adreça:<br>
                <input type="text" name="adreca">
                <br>
                Correu:<br>
                <input type="email" name="email">
                <br><br>
                Telefon:<br>
                <input type="text" name="telefon">
                <br><br>
                <label>Multiple Selection </label><br>
                <select name="categories" size="3" multiple="multiple" tabindex="1">
                    <option value="-1">Selecciona categoria</option>
                    <%
//                        DBManager db = new DBManager();
//                        db.connect();
//                        String options = db.getCategories();
//                        out.print(options);

                    %>
                </select>
                <br><br>
                <input type="submit" name="afegir" value="afegir">
                <input type="submit" name="tornar" value="Tornar">
            </form>
        </div>-->
        <div class="afegirContacte">
            <h1>Afegir Contacte</h1>
            <form method="post" action="afegirContacte.jsp" class="afegir-form">
                <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Nom</span>
                    <input type="text" class="form-control" name="nom" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
                <!--PAss -->
               <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Cognoms</span>
                    <input type="text" class="form-control" name="cognoms" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
               <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Adreça</span>
                    <input type="text" class="form-control" name="adreca" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
               <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Mail</span>
                    <input type="text" class="form-control" name="email" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
               <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Telefon</span>
                    <input type="text" class="form-control" name="telefon" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
                <select name="categories" size="3" multiple="multiple" tabindex="1">
                    <option value="-1">Selecciona categoria</option>
                    <%
                        
                        String options = db.getCategories();
                        out.print(options);

                    %>
                </select>
                <br>
               <input class="btn btn-primary" type="submit"  name="afegir" value="Afegir">
               <input type="submit" name="tornar" value="Tornar" class="tornar"/>
            </form> 
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    </body>
</html>
