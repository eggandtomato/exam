package test;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
public class PaperTest {
	@Test
	public void getObj() {
		String str[] = "1,12,456,48,4,13,15,46".split(",");
		Arrays.sort(str);
		System.out.println(Arrays.asList(str));
	}
}
