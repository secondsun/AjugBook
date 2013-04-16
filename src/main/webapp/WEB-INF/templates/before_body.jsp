<%-- 
    Document   : index
    Created on : Apr 12, 2013, 1:09:06 PM
    Author     : Summers
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">

        <title>AG Book</title>
        <style>
            hr {
                -moz-border-bottom-colors: none;
                -moz-border-image: none;
                -moz-border-left-colors: none;
                -moz-border-right-colors: none;
                -moz-border-top-colors: none;
                border-color: #EEEEEE -moz-use-text-color #FFFFFF;
                border-style: solid none;
                border-width: 1px 0;
                margin: 18px 0;
            }
            .tight {
                margin: 0 !important;
            }


            .header_corner{
                background-color: rgb(241,92,34);
            }
            .header_content {
                background-color: rgb(226,111,30);
            }

            .left_column {
                background-color: rgb(246,142,30);
                overflow:auto;
                height: 100%;
            }
            .container-fluid{
                margin: 0 auto;
                height: 100%;
                padding: 0px 0;

                -moz-box-sizing: border-box;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
            }

            .columns{
                height: 100%;
            }

            html, body {
                height: 100%;
            }

            a .header_corner h1 {
                color: #000;
            }

        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row-fluid header_content">
                <a href="/AjugBook/">
                    <div class="tight span2 header_corner">
                        <h1>Ajug Book</h1>
                    </div>
                </a>
                <div class="tight span10">
                    <c:if test="${identity.user != null}">
                        <span class="lead">Welcome <c:out value="${identity.user.firstName}"/>!</span>
                        <span class="nav pull-right">

                            <a href="account" >
                                Account
                            </a>
                            <a href="post" >
                                Make A Post
                            </a>

                            <a href="logout">Logout</a>

                        </span>
                    </c:if>
                    <c:if test="${identity.user == null}">
                        <form class="navbar-form pull-right" action="login" method="POST">
                            <input class="span4" name="aeroGearUser.username" type="text" placeholder="Username">
                            <input class="span4" name="aeroGearUser.password" type="password" placeholder="Password">
                            <button style="margin-top: 5px;" type="submit" class="btn">Sign in</button>
                        </form>
                    </c:if>

                </div>
            </div>

            <div class="row-fluid columns">
                <div class="span2 left_column ">
                    <c:if test="${identity.user != null}">
                        <a href="photos"> View Your Photos</a>
                    </c:if>
                </div>
                <div class="span10 main_body">