package hello.servlet.web.frontController.v3;

import hello.servlet.web.frontController.ModelView;

import java.util.Map;

/**
 * - 서블릿 종속 제거
 *  v2 컨트롤러 입장에서 HttpServletRequest, HttpServletResponse가 꼭 필요하지 않다.(받아서 넘기기만 하기떄문에)
 *  요청 파라미터정보는 자바의 Map으로 대신 넘기도록 하면 v2 구조에서는 컨트롤러가 서블릿 기술을 몰라도 동작할 수 있다.
 *  그리고 request 객체를 Model로 사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다.
 *
 * - 뷰 이름 중복 제거
 *  v2 컨트롤러에서 지정하는 뷰 이름에 중복이 있다.
 *  컨트롤러는 뷰의 논리 이름을 반환하고, 실제 물리 위치의 이름은 프론트 컨트롤러에서 처리하도록 하는 것이,
 *  향후 뷰의 폴더 위치가 함께 이동해도 프론트 컨트롤러만 수정하면 되는 등 편리하게 해준다.
 */
public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);
}
