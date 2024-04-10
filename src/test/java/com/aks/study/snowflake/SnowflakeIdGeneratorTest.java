package com.aks.study.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class SnowflakeIdGeneratorTest {

    @Test
    public void 고유Id가_생성되어야한다() {

        // given
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1L);

        // then
        long id = generator.nextId();

        Assertions.assertThat(id).isNotNull();
    }

    @Test
    public void 먼저생성한ID보다_늦게생성한ID가_더큰값이어야한다() {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1L);

        long firstId = generator.nextId();
        long secondId = generator.nextId();

        Assertions.assertThat(firstId).isLessThan(secondId);
    }
}