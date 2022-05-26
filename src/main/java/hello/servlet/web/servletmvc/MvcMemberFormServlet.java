package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
        /**
         *  - /WEB-INF : 이 경로안에 JSP가 있으 외부에서 직접 JSP를 호출할 수 없다.(컨트롤러를 통해서만 JSP가 호출되게 하기 위해)
         *  - dispatcher.forward() : 다른 서블릿이나 JSP로 이동하는 기능. 서버 내부에서 다시 호출이 발생한다.
         *  - redirect vs forward
         *      redirect는 실제 클라이언트(웹 브라우저)로 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청을 보낸다. 따라서
         *      클라이언트가 인지할 수 있고, URL도 실제로 변경된다. 반면에 forward는 서버 내부에서 일어나는 호출이기 때문에
         *      클라이언트가 전혀 인지하지 못한다.
         */

    }
}
