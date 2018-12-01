package test;

import java.util.Arrays;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.hzit.entity.Paper;
import com.hzit.util.HBUtil;

public class PaperTest {
	@Test
	public void getObj() {
		String str[] = "1,12,456,48,4,13,15,46".split(",");
		Arrays.sort(str);
		System.out.println(Arrays.asList(str));
	}
}
