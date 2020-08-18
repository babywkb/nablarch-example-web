<%@page import="com.nablarch.example.app.web.common.code.ProjectType"%>
<%@page import="com.nablarch.example.app.web.common.code.ProjectSortKey"%>
<%@page import="com.nablarch.example.app.web.common.code.SortOrder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="n" uri="http://tis.co.jp/nablarch" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/listSearchResult" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%-- javascript --%>
        <n:script type="text/javascript" src="/javascripts/lib/jquery-1.11.2.min.js"></n:script>
        <n:script type="text/javascript" src="/javascripts/projectList.js"></n:script>
        <n:script type="text/javascript" src="/javascripts/clientList.js"></n:script>
        <link rel="stylesheet" href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
        <link rel="stylesheet" href="//fonts.googleapis.com/icon?family=Material+Icons">
        <title>メトリクス</title>
    </head>
    <body>
        <n:include path="/WEB-INF/view/common/noscript.jsp" />
        <div class="container-fluid mainContents">
            <n:include path="/WEB-INF/view/common/menu.jsp" />
            <n:include path="/WEB-INF/view/common/header.jsp" />
            <div class="row">
                <n:include path="/WEB-INF/view/common/sidemenu.jsp" />
                <div class="pages col-xs-10">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <pre><n:write name="metrics" /></pre>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <n:include path="/WEB-INF/view/common/noscript.jsp" />
        <n:script type="text/javascript">
            $(function() {
                saveListUrl();
            });
        </n:script>
    </body>
</html>