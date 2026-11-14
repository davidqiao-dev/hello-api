package com.qiao.hello;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class HelloApiApplicationTests {

    @Test
    void contextLoads() {
    }

    /*in a scratch main or a test class, make a List<String> of 5 department names,
     then use a Set to remove duplicates, then iterate*/
    @Test
    void test() {
		List<String> departments = List.of(
				"Engineering",
				"Sales",
				"Engineering",
				"Marketing",
				"Sales"
		);

		Set<String> unique = new HashSet<>(departments);

		unique.forEach(System.out::println);
    }
}
