package hello.servlet.web.frontController.v4;

import java.util.Map;

/**
 *  V3 컨트롤러는 서블릿 종속성을 제거하고 뷰 경로의 중복을 제거하는 등 잘 설계된 컨트롤러이다. 그런데 실제 컨트롤러 인터페이스를
 *  구현하는 개발자 입장에서 보면, 항상 ModelView 객체를 생성하고 반환해야 하는 부분이 번거롭다.
 *  좋은 프레임워크는 아키텍쳐도 중요하지만, 그와 더불어 실제 개발하는 개발자가 단순하고 편리하게 사용할 수 있어야 한다.
 *
 *  - 기존구조에서 모델을 파라미터로 넘기고, 뷰의 논리 이름을 반환한다.
 */
public interface ControllerV4 {

    /**
     *
     * @param paramMap
     * @param model
     * @return viewName
     *
     * 인터페이스에 ModelView가 없다. model 객체는 파라미터로 전달되기때문에 그냥 사용하면 되고,
     * 결과로 뷰의 이름만 반환해주면 된다.
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
