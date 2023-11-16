<%-- 
    Document   : login
    Created on : 29 nov. 2021, 19:01:43
    Author     : abdel
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ page import="org.milaifontanals.DBManager" %>
<!DOCTYPE html>
<%
       
        if(request.getParameter("login") != null) {
            String mail = request.getParameter("mail");
            String pass = request.getParameter("pass");
            DBManager u = new DBManager();
            u.connect();
            if(u.validUser(mail,pass)) {
                int userID = u.getUserID(mail);
                session.setAttribute("user", mail);
                session.setAttribute("userId", userID);
                response.sendRedirect("usuario.jsp");
            }
        }
        
        if(request.getParameter("registrar") != null) {
            response.sendRedirect("autoregistre.jsp");
        }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="pracJSP/style.css" rel="stylesheet" type="text/css">
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
 
        <style>
            body{
                background-color: aliceblue;
            }
            .login{
                width: 30%;
                margin:100px auto;
                background-color: aliceblue;
                text-align: center;
            }
            .login-form{
                display: flex;
                flex-direction: column;
                padding: 20px;
            }
            #inputs{
                margin-bottom: 10px;
            }
            .registrar{
                background-color: transparent;
                color: royalblue;
                border: 1px solid transparent;
                margin-top: 15px;
            }
        </style>
    </head>
    <body>
        <div class="login">
            <h1>Login</h1>
            <form method="post" action="login.jsp" class="login-form">
                <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Mail</span>
                    <input type="text" class="form-control" name="mail" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
                <!--PAss -->
               <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Pass</span>
                    <input type="password" class="form-control" name="pass" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
               <input class="btn btn-primary" type="submit"  name="login" value="Login">
               <input type="submit" name="registrar" value="Crea nou compte" class="registrar"/>

            </form> 
        </div>
          <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    </body>
</html>
