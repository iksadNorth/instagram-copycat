package me.iksadnorth.insta;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test", "common", "jwt", "s3"})
@SpringBootTest
class InstaApplicationTests {

	@Test
	void contextLoads() {
	}

}
