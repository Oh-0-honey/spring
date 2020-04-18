package com.spring.test.web.dto;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;


public class HelloResponseDtoTest {
    @Test
    public void 롬복기능테스트(){
        //given
        String name="test";
        int amo=1234;

        HelloResponseDto dto=new HelloResponseDto(name,amo);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amo);
    }
}
