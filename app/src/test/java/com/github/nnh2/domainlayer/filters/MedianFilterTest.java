package com.github.nnh2.domainlayer.filters;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Zahar on 27.03.16.
 */
public class MedianFilterTest {

	@Test
	public void testDoWork() throws Exception {
		MedianFilter filter = new MedianFilter(3);

		int[] pixels = new int[16];
		int[] pixelsNew = new int[16];
		filter.doWork(pixels, 4,4);

		Assert.assertTrue(true);
	}
}