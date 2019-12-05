package bnav.baidu.com.sublibrary;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private HashMap<Integer, Integer> mStateInfo = new HashMap<Integer, Integer>();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void streamTest() {
        mStateInfo.put(0, 0);
        mStateInfo.put(1, 1);
        mStateInfo.put(2, 2);
        mStateInfo.put(3, 3);
        mStateInfo.put(4, 4);
        int x = 2;
//        boolean isParent = mStateInfo.values().stream()
//                .filter(si -> si.intValue() == x)
//                .findAny()
//                .isPresent();
//
    }
}