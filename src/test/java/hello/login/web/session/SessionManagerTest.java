package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        //세션 생성
        //서버 -> 클라이언트
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member,response);


        //요청에 응답 쿠키 저장
        //웹 브라우저의 요청을 가정
        //클라이언트 -> 서버
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션 조회
        Object result = sessionManager.getSession(request);

        Assertions.assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);

        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();


    }
}