package org.example.repository;

import org.example.models.Human;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HumanRepositoryTest {

    @Autowired HumanRepository underTest;

    @Test
    void itShouldFindHumanByName() {
        //given
        String name = "Jack";
        Human human = new Human(name, 3);
        underTest.save(human);

        //when
        List<Human> result  = underTest.findHumansByName(name);

        //then
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .allSatisfy(foundHuman -> assertThat(foundHuman.getName()).isEqualTo(name));
    }

    @Test
    void itShouldFindHumanByNameDoesNotExists() {
        //given
        String name = "Jack";

        //when
        List<Human> result  = underTest.findHumansByName(name);

        //then
        assertThat(result).isEmpty();
    }
}