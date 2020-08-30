package io.github.amazing010692.rough;

import java.util.Date;

import io.github.amazing010692.utilities.TestUtil;

public class TestTimeStamp {

	public static void main(String[] args) {
		
		Date d = new Date();
		String screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		System.out.println(screenshotName);
		System.out.println(d);
		
		System.out.println(TestUtil.screenshotName);

	}

}
