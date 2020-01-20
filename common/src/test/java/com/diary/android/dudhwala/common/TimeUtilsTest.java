package com.diary.android.dudhwala.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.SimpleDateFormat;

import static junit.framework.TestCase.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({
        SimpleDateFormat.class
})
@RunWith(PowerMockRunner.class)
public class TimeUtilsTest {

    private static final long TEST_TIMESTAMP = 1579320168;
    private static final long TEST_INVALID_TIMESTAMP = -1;
    private final String TEST_DATE = "19/01/1970";

    SimpleDateFormat mSimpleDateFormat;

    @Before
    public void setUp() throws Exception {
        //mockStatic(SimpleDateFormat.class);
        mSimpleDateFormat = mock(SimpleDateFormat.class);
        mSimpleDateFormat.applyPattern("dd/MM/yyyy");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void convertTimestampToDateString() throws Exception {
        whenNew(SimpleDateFormat.class).withArguments("dd/MM/yyyy").thenReturn(mSimpleDateFormat);
        //when(mSimpleDateFormat.format(TEST_TIMESTAMP)).thenReturn("Hello");
        assertEquals(TEST_DATE, TimeUtils.convertTimestampToDateString(TEST_TIMESTAMP));
    }

    @Test
    public void convertStringToTimestamp() {
    }

    @Test
    public void getMonthStartTimeStamp() {
    }

    @Test
    public void getMonthEndTimeStamp() {
    }
}