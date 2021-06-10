<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="nav flex-column long-bg">
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="/dashboard"/>">
      <span>Pulpit</span>
      <i class="fas fa-angle-right"></i>
    </a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="/app/recipe/list"/>">
      <span>Przepisy</span>
      <i class="fas fa-angle-right"></i>
    </a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="/app/plan/list"/>">
      <span>Plany</span>
      <i class="fas fa-angle-right"></i>
    </a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="/app/user/edit"/>">
      <span>Edytuj dane</span>
      <i class="fas fa-angle-right"></i>
    </a>
  </li>
  <li class="nav-item">
    <a class="nav-link disabled" href="<c:url value="/app/password/edit"/>">
      <span>Zmień hasło</span>
      <i class="fas fa-angle-right"></i>
    </a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/super-admin-users.html">
      <span>Użytkownicy</span>
      <i class="fas fa-angle-right"></i>
    </a>
  </li>
</ul>