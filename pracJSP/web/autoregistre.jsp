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
    if(request.getParameter("crear") != null){
        String nom = request.getParameter("nom");
        String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        
        DBManager u = new DBManager();
        u.connect();
        if(!nom.equals("") && !mail.equals("")  && !pass.equals("") ){
            if(u.registrarUsuari(nom, mail, pass)){
                out.print("S'ha creat l'usuari");
            }else{
                out.print("No s'ha creat l'usuari");
            }
        }else{
            
        }
        
    }
    
    if(request.getParameter("tornar") != null){
        response.sendRedirect("login.jsp");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Crear nou usuari</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <style>
             .autoregistre{
                width: 30%;
                margin:100px auto;
                background-color: aliceblue;
                text-align: center;
                border-radius: 15px;
                padding: 5px;
            }
            #inputs{
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <div class="autoregistre">
            <h2>Crea el teu compte</h2>
            <form method="post" action="autoregistre.jsp">

                <div class="input-group input-group-lg" id="inputs">
                        <span class="input-group-text" id="inputGroup-sizing-lg">Nom</span>
                        <input type="text" class="form-control" name="nom"  placeholder="Escriu el teu nom.." aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" >
                </div>

                <div class="input-group input-group-lg" id="inputs">
                        <span class="input-group-text" id="inputGroup-sizing-lg">Mail</span>
                        <input type="text" class="form-control" name="mail"  placeholder="Escriu el teu mail.." aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" >
                </div>

                <div class="input-group input-group-lg" id="inputs">
                        <span class="input-group-text" id="inputGroup-sizing-lg">Password</span>
                        <input type="password" class="form-control" name="pass"  placeholder="Escriu la teva contrasenya.." aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" >
                </div>

                <input class="btn btn-primary" type="submit"  name="crear" value="Crear">
                <input type="submit" name="tornar" value="Tornar">
            </form>
         </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    </body>
</html>
