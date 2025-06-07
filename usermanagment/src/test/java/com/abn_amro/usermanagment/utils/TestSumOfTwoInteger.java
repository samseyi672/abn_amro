package com.abn_amro.usermanagment.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestSumOfTwoInteger {
    @Test
    @DisplayName("Sum of two Integer")
    public void GivenTwoIntegerWhenNotNullThenSum(){
        SumOfInteger sumOfInteger  = new SumOfInteger();
        int result = sumOfInteger.addTwoInterger(1,2);
        Assertions.assertEquals(3,result);
    }
}











































