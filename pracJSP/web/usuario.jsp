<%-- 
    Document   : usuario
    Created on : 29 nov. 2021, 19:04:45
    Author     : abdel
--%>


<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ page import="org.milaifontanals.DBManager" %>
<%@ page import="org.milaifontanals.ExportarXML" %>
<%@ page import="java.sql.*,java.util.*" %>
<%@page import="java.io.*"%>
<%@page import="com.lowagie.text.*"%>
<%@page import="com.lowagie.text.pdf.*"%>
<!DOCTYPE html>
<%
    String userName = (String)session.getAttribute("user");
    if (userName == null){
        response.sendRedirect("login.jsp");
    }
    
    if(request.getParameter("canviarPswd")!=null){
        session.setAttribute("user", userName);
        response.sendRedirect("changePassword.jsp");
    }
    
    if(request.getParameter("afegir")!=null){
        response.sendRedirect("afegirContacte.jsp");
    }
    
    String taulaResult = "";
    DBManager dbm = new DBManager();
    dbm.connect();
    Integer userId = dbm.getUserID(userName);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>User Page</title>
        <style>
        body{
            background:rgb(201, 175, 152 );
        }
        table {
          font-family: arial, sans-serif;
          border-collapse: collapse;
          width: 100%;
        }

        td, th {
          border: 1px solid #dddddd;
          text-align: left;
          padding: 8px;
        }

        tr:nth-child(even) {
          background-color: #dddddd;
        }
        form{
            display: flex;
            background:rgb(40, 55, 71 );
        }
        
        .buscar-filtrar{
            width: 80%;
            height: 85vh;
            background-color: transparent;
            display: flex;
            flex-direction: column;
            border-radius: 10px; 
            margin:10px;
        }
        
        .nav-bar{
            width: 98.5%;
            height: 80px;
            background-color:rgb(118, 196, 227);
            margin:10px;
            display: flex;
            justify-content: space-around;
            align-items: center;
            border-radius: 10px;
        }
        
        .multi-select{
            height: 50px;
            display: flex;
            
        }
        
        .menu{
            width:20%;
            height: 85vh;
            background-color: rgb(118, 196, 227);
            display: flex;
            flex-direction: column;
            padding: 5px;
            border-radius: 10px;
            margin: 10px;
        }
        
        .mostrar{
            background-color: rgb(118, 196, 227);
            padding: 5px;
            margin:10px;
            border-radius: 10px;
        }
        #btn{
            margin:8px;
        }
        
        #btn-logout{
            margin:8px;
            background-color: coral;
        }
        .menu-btn{
            border-radius: 20px;
            padding: 5px;
            background-color: rgb(72, 38, 115);
            color: aliceblue;
            font-family: arial;
        }
        .logout{
            background-color: coral;
        }
        </style>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    </head>
    <body>
        <h2>Hola <%out.print(userName); %></h2>
        <form method="post" action="usuario.jsp">
            
            <div class="menu">
                <input type="submit" class="btn btn-secondary" id="btn" value="Afegir contacte" name="afegir" />
                <input type="submit" class="btn btn-secondary" id="btn" value="Canviar contrasenya" name="canviarPswd" />
                <input type="submit" class="btn btn-secondary" id="btn" name="exportar" value="Exportar en PDF"/>
                <input type="submit" class="btn btn-secondary" id="btn" name="exportarxml" value="Exportar en XML"/>
                <input type="submit" class="btn btn-secondary" id="btn-logout" name="logout" value="Log out"/>
            </div>
           
            <div class="buscar-filtrar">
                <div class="nav-bar">
                    <div>
                        <div class="hstack gap-3">
                            <input class="form-control me-auto" type="text" placeholder="Buscar contacte.." aria-label="Add your item here..." name="buscarTxt">
                            <input type="submit" class="btn btn-secondary" value="Buscar" name="buscar"/>
                        </div>
                    </div>
                    <div class="multi-select">
                        <select name="categories" class="form-select" multiple aria-label="multiple select example">
                            <option value="-1" selected>Sense filtre</option>
                            <%  
                                String options = dbm.getCategories();
                                out.print(options);
                            %>
                        </select>
                        <input type="submit" value="Filtrar" class="btn btn-secondary" name="filtrar" />
                    </div>
                </div>
                        <div class="mostrar" id="resultat">
                     <!--MOSTRAR CONTACTOS-->
                    <%
                        taulaResult= dbm.mostrarTodo(userId);
                        out.print(taulaResult);
                    
                    %>
                     <!-- buscar--filtrar -->
                <%
                 String buscarTxt = request.getParameter("buscarTxt");

                 if(request.getParameter("buscar")!=null){
                        try {
                            %>
                            <script>
                                let tabla = document.getElementById("resultat");
                                tabla.removeChild(tabla.children[0]);
                            </script>
                            <%
                             taulaResult = dbm.buscarContacte(buscarTxt);
                             out.print(taulaResult);
                        } catch (Exception e) {
                            out.print(e.getMessage());
                        }
                   }

                   if(request.getParameter("filtrar")!=null){
                   %>
                   <script>
                        let tabla = document.getElementById("resultat");
                        tabla.removeChild(tabla.children[0]);
                    </script>
                   <%
                     String[] arrCat = request.getParameterValues("categories");
                     if(arrCat[0].equals("-1") && arrCat.length == 1){
                         taulaResult = dbm.mostrarTodo(userId);
                         out.print(taulaResult);
                     }else{
                         //GUARDAR COOKIES DE LES CATEGORIES
                        for(int i=0; i<arrCat.length;i++){
                            if(!arrCat[i].equals("-1")){
                                Cookie galleta = new Cookie("cat"+(i+1),arrCat[i]);
                                galleta.setMaxAge(-1);
                                response.addCookie(galleta);
                            }  
                        }

                        //generar string multi-categoria
                        String aux ="";
                        String[] cats = new String[arrCat.length];
                        for(int i=0; i<arrCat.length;i++){
                            if(!arrCat[i].equals("-1")){
                               aux += "ca.idCat = ?";
                               cats[i] = arrCat[i];
                               if(i!= arrCat.length - 1 ){
                                   aux += " OR ";
                               }
                            }
                        }
                        String filterQuery = "SELECT nom,cognoms,correu,adreca,telefon,ca.nomCat,c.user FROM contacte c "
                                + "JOIN categories ca ON c.categoria = ca.idCat "
                                + "WHERE  " + aux;

                        taulaResult = dbm.filtrar(filterQuery, cats,userId);
                        out.print(taulaResult);
                     }

                   }
             %>
                </div>
            </div>
            <!--Exportar en un PDF-->
           <%              
            if(request.getParameter("exportar")!=null){
                try{
                   dbm.crearPDF(userId);
                   out.print("<h1>S'ha creat l'arxiu</h1>");
                }catch(Exception e){
                    out.print(e.getMessage());
                }  
            }
            //Exportar en XML
            if(request.getParameter("exportarxml")!=null){
                try{
                    dbm.exportarEnXML();
                    out.print("<h2>S'ha creat l'arxiu XML</h2>");
                }catch(Exception e){
                    out.println(e.getMessage()); 
                    out.print("<h2> No S'ha creat l'arxiu XML</h2>");
                }
            }
            
        //LOG OUT
         if(request.getParameter("logout")!=null){
            session.removeAttribute("user");
            session.removeAttribute("userId");
            response.sendRedirect("login.jsp");
         }
        %>
          
        
        </form>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    </body>
</html>
