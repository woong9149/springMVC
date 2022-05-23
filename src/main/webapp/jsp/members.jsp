<%--
  Created by IntelliJ IDEA.
  User: gerrard
  Date: 2022/05/23
  Time: 9:07 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="java.util.List" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
    <%
        for (Member member : members) {
            out.write(" <tr>"
                    + "<td>" + member.getId() + "</td>"
                    + "<td>" + member.getUsername() + "</td>"
                    + "<td>" + member.getAge() + "</td>"
                    + "</tr>");
        }
    %>
    </tbody>
</table>
</body>
</html>
