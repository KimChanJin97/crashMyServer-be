package cjkimhello97.toy.crashMyServer.kafka.service.testdata;

import cjkimhello97.toy.crashMyServer.click.domain.Click;
import cjkimhello97.toy.crashMyServer.member.domain.Member;

public class KafkaClickListenerFixtureObject {

    private static final Long MEMBER_ID = 1L;
    private static final String NICKNAME = "aaa";
    private static final String PASSWORD = "aaa";
    private static final Long CLICK_ID = 1L;
    private static final Double COUNT = 0D;
    private static final Member MEMBER = member();

    public static Member member() {
        return Member.builder()
                .memberId(MEMBER_ID)
                .nickname(NICKNAME)
                .password(PASSWORD)
                .build();
    }

    public static Click click() {
        return Click.builder()
                .member(MEMBER)
                .clickId(CLICK_ID)
                .count(COUNT)
                .build();
    }
}
