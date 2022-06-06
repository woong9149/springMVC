package hello.servlet.web.frontController.v5;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v3.ControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontController.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontController.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontController.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  어댑터 패턴
 *  지금까지의 프론트 컨트롤러는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있다.
 *  어댑터 패턴을 사용해서 프론트 컨트롤러가 ControllerV3, ControllerV4 등 다양한 방식의 컨트롤러를 처리할 수 있도록 변경한다.
 *
 *  정리
 *  v1: 프론트 컨트롤러 도입
 *  - 기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입
 *
 *  v2: view 분류
 *  - 단순 반복 되는 뷰 로직 분리
 *
 *  v3: Model 추가
 *  - 서블릿 종속성 제거
 *  - 뷰 이름 중복 제거
 *
 *  v4: 단순하고 실용적인 컨트롤러
 *  - v3와 거의 비슷
 *  - 구현 입장에서 modelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스 제공
 *
 *  v5: 유연한 컨트롤러
 *  - 어댑터 도입
 *  - 어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계
 *
 *  SpringMVC 구조
 *  - 동작 순서 -
 *  1. 핸들러 조회: 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.
 *  2. 핸들러 어댑터 조회: 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.
 *  3. 핸들러 어댑터 실행: 핸들러 어댑터를 실행한다.
 *  4. 핸들러 실행: 핸들러 어댑터가 실제 핸들러를 실행한다.
 *  5. ModelAndView 반환: 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서 반환한다.
 *  6. ViewResolver 호출: viewResolver를 찾고 실행한다.
 *      - JSP의 경우: InternalResourceViewResolver가 자동 등록되고, 사용된다.
 *  7. view 반환: viewResolver는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 하는 뷰 객체를 반환한다.
 *      - JSP의 경우: InternalResourceView(JstlView) 를 반환하는데, 내부에 forward() 로직이 있다.
 *  8. 뷰 렌더링: 뷰를 통해서 뷰를 렌더링 한다.
 *
 */
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappintMap();
        initHandlerAdapters();
    }

    private void initHandlerMappintMap() {
        // V3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV5.service");

        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        /**
         *  어댑터의 handle(request, response, handler) 메서드를 통해 실제 어댑터가 호출된다.
         *  어댑터는 핸들러를 호출하고 그 결과를 어댑터에 맞추어 반환한다.
         *  ControllerV3HandlerAdapter 의 경우 어댑터의 모양과 컨트롤러의 모양이 유사해서 변환 로직이 단순하다.
         */
        ModelView mv = adapter.handle(request, response, handler);


        String viewName = mv.getViewName();// 논리이름 new-form
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    /**
     *  handlerMapptinMap 에서 URL에 맵핑된 핸들러(컨트롤러) 객체를 찾아서 반환한다.
     *
     * @param request
     * @return
     */
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        Object handler = handlerMappingMap.get(requestURI);

        return handler;
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) { // handler를 처리할 수 있는 어댑터를 adapter.supports(handler)를 통해서 찾는다.
                return adapter;
            }
        }

        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
