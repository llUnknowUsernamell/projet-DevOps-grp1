<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Erreur Lors du Chargement</title>
</head>
<body>
<h1>Une erreur est survenue lors du chargement des données utilisateur.</h1>
<p>Veuillez réessayer de vous reconnecter plus tard.</p>

<s:form name="myFormRetour" action="retourAccueil" method="POST">
    <s:submit name="Retour" value="Retour à l'accueil" />
</s:form>
</body>
</html>
