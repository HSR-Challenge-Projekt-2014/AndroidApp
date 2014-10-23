package ch.hsr.challp.museum;

import android.test.ActivityUnitTestCase;

import junit.framework.Assert;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class ApplicationTest extends ActivityUnitTestCase {

    public ApplicationTest() {
        super(HomeActivity.class);
    }

    public void testFoo() {
        Assert.assertEquals(1, 1);
    }

}