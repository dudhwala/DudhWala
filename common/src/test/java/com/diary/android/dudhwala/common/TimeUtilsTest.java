package com.diary.android.dudhwala.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;

@RunWith(MockitoJUnitRunner.class)
public class TimeUtilsTest {

    private static final long TEST_TIMESTAMP = 1579320168;
    private static final long TEST_INVALID_TIMESTAMP = -1;
    private final String TEST_DATE = "19/01/1970";


    @Mock
    SimpleDateFormat mSimpleDateFormat;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void convertTimestampToDateString() {
        //when(new SimpleDateFormat()).thenReturn(mSimpleDateFormat);
        //assertThat(TEST_DATE, is(TimeUtils.convertTimestampToDateString(TEST_TIMESTAMP)));
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