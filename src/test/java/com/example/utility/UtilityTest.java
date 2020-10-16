package com.example.utility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UtilityTest {

    @Test
    public void given_Valid_date_When_Checked_If_Valid_Then_Throw_No_Exception(){
        boolean valid = Utility.checkIfDateIsValid("2020-09-06");
        Assert.assertTrue(valid);
    }

    @Test
    public void given_Date_In_Invalid_Format_When_Checked_If_Valid_Then_Throw_No_Exception(){
        boolean valid = Utility.checkIfDateIsValid("16-04-2008");
        Assert.assertFalse(valid);
    }
}
