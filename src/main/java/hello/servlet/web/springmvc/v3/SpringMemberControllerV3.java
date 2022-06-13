package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RequestParam 사용
 * - 스프링은 HTTP 요청 파라미터를 @RequestParam으로 받을 수 있다.
 *  GET 쿼리 파라미터, POST Form 방식을 모두 지원한다.
 *
 * @RequestMapping -> @GetMapping, @PostMapping
 * @RequestMapping은 URL만 매칭하는 것이 아니라, HTTP Method도 함께 구분할 수 있다.
 * value 속성에 매칭할 URL을, method 속성에 HTTP Method를 각각 담아주면 된다.
 * 하지만 더 편리하게는 @GetMapping, @PostMapping 애노테이션을 사용할 수 있다.
 */
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

//    @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
    }

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,
            @RequestParam("age") int age,
            Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "members";
    }
}
