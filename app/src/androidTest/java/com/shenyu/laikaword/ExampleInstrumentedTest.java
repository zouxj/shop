package com.shenyu.laikaword;

import android.content.Context;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();

//        assertEquals("com.shenyu.laikaword", appContext.getPackageName());
    }

    @Test
    public void name() throws Exception {
        Log.i("tag", "$$$$$$$$$$$$");
        assertEquals("result:", 123, 100 + 33);
    }

    @Test
    private void test2() {
        Log.i("tag", "$$$$$$$$$$$$");
        assertEquals("result:", 123, 100 + 33);
    }

}
