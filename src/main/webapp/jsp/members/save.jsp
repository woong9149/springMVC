<%--
  Created by IntelliJ IDEA.
  User: gerrard
  Date: 2022/05/23
  Time: 9:02 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%
    //request, response는 그냥 사용 가능

    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%member.getId()%></li>
    <li>username=<%member.getUsername()%></li>
    <li>age=<%member.getAge()%></li>
</ul>
</body>
</html>
