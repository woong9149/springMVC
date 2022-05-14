package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    /**
     * @WebServlet : 서블릿 애노테이션
     * name : 서블릿 이름
     * urlPatterns : URL 맵핑
     *
     * HttpServletRequest 역할
     *  - HTTP 요청 메시지를 편리하게 조회할 수 있다.
     *  - 부가 기능
     *      - 임시 저장소 기능
     *          - 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능
     *              - 저장: request.setAttribute(name, value)
     *              - 조회: request.getAttribute(name)
     *      - 세션 관리 기능
     *          - request.getSession(create: true)
     *  - HttpServletRequest, HttpServletResponse를 사용할 때 가장 중요한 점은 이 객체들이 HTTP 요청 메세지와 응답 메세지를
     *    편리하게 사용하도록 도와주는 객체라는 점이다. 따라서 이기능에 대해서 깊이 있는 이해를 하려면, HTTP 스펙이 제공하는 요청, 응답 메세지 자체를
     *    이해해야 한다.
     *
     * HTTP 요청 데이터
     * - GET - 쿼리 파라미터
     *  - /url?username=hello&age=20
     *  - 메세지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
     *  - 예) 검색, 필터, 페이징 등에서 많이 사용하는 방식
     * - POST - HTML Form
     *  - content-type: application/x-www-form-urlencoded
     *  - 메세지 바디에 쿼리 파라미터 형식으로 전달 (username=hello&age=20)
     *  - 예) 회원가입, 상품주문, HTML Form 사용
     * - HTTP message body에 데이터를 직접 담아서 요청
     *  - HTTP API에서 주로 사용, JSON, XML, TEXT
     *  - 데이터 형식은 주로 JSON 사용
     *  - POST, PUT, PATCH
     *
     *  참고
     *  - content-type은 HTTP 메세지 바디의 데이터 형식을 지정한다.
     *  - GET URL 쿼리 파라미터 형식으로 클라이언트에서 서버로 데이터를 전달할 때는 HTTP 메세지 바디를 사용하지 않기 때문에 content-type이 없다
     *  - POST HTML Form 형식으로 데이터를 전달하면 HTTP 메세지 바디에 해당 데이터를 포함해서 보내기 때문에
     *      바디에 포함된 데이터가 어떤 형식인지 content-type을 꼭 지정해야 한다.
     *      이렇게 폼으로 데이터를 전송하는 형식을 application/x-www-form-urlencoded 라 한다.
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);

    }
}
