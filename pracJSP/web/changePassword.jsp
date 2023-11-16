<%-- 
    Document   : changePassword
    Created on : 5 ene. 2022, 1:11:41
    Author     : abdel
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ page import="org.milaifontanals.DBManager" %>
<!DOCTYPE html>
<%
    String name = (String)session.getAttribute("user");
    out.print(name);
    if (name == null){
        response.sendRedirect("login.jsp");
    }
    
    if(request.getParameter("canviar") != null){
        String actual = request.getParameter("current");
        String nou = request.getParameter("new");
        String confirmat = request.getParameter("confirm");
        DBManager u = new DBManager();
        u.connect();
        if(u.validUser(name,actual)) {
            if(nou.equals(confirmat)){
                u.canviarContrasenya(name, confirmat);
                 out.print("<br>");
                out.print("S'ha canviat la contrasenya");
               // response.sendRedirect("usuario.jsp");
            }else{
                out.print("valors no identics");
            }
        }else{
            out.print("<br>");
            out.print("La contrasenya actual es incorrecta");
        }
    }
    
    if(request.getParameter("tornar") != null){
        response.sendRedirect("usuario.jsp");
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Change Password</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <style>
            body{
                background-color: aliceblue;
            }
            .canviarContrasenya{
                width: 30%;
                margin:100px auto;
                background-color: aliceblue;
                text-align: center;
            }
            .change-form{
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
        <div class="canviarContrasenya">
            <h1>Canviar contrasenya</h1>
            <form method="post" action="changePassword.jsp" class="change-form">
                <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Actual</span>
                    <input type="text" class="form-control" name="current" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
                <!--PAss -->
               <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">La nova</span>
                    <input type="password" class="form-control" name="new" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
               <div class="input-group input-group-lg" id="inputs">
                    <span class="input-group-text" id="inputGroup-sizing-lg">Confirmar</span>
                    <input type="password" class="form-control" name="confirm" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg">
               </div>
               <input class="btn btn-primary" type="submit"  name="canviar" value="Canviar">
               <input type="submit" name="tornar" value="Tornar" class="tornar"/>
            </form> 
        </div>
         <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    </body>
</html>
