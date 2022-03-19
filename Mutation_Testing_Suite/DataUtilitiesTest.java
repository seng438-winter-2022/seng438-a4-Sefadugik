package org.jfree.data.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jfree.data.KeyedValues;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

public class DataUtilitiesTest {

	@Test (expected = NullPointerException.class)
	 public void testColumnTotalMutation() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getRowCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(7.5));
		            one(values).getValue(0, 1);
		            will(returnValue(2.5));
		        }
		    });
		    int[] validCols = null;
		    double result = DataUtilities.calculateColumnTotal(values, 0, validCols);
		    assertEquals(7.5, result, 0);
	}
	@Test (expected = IllegalArgumentException.class)
	 public void testColumnTotalValidNull() {
		    final Values2D values = null;
		    int[] validCols = {0};
		    double result = DataUtilities.calculateColumnTotal(values, 0, validCols);
		    assertNull(values);
	}
	
	@Test (expected = IllegalArgumentException.class)
	 public void testColumnRowValidNull() {
		    final Values2D values = null;
		    int[] valid = {0};
		    double result = DataUtilities.calculateRowTotal(values, 0, valid);
		    assertNull(values);
	}
	
	@Test (expected = IllegalArgumentException.class)
	 public void testColumnRowNull() {
		    final Values2D values = null;
		    double result = DataUtilities.calculateRowTotal(values, 0);
		    assertNull(values);
	}
	
	@Test
    public void testEqualMutation() throws Exception {
        // setup
        double[][] array1 = {{1,2},{2,3}};
        double[][] array2 = {{1,2},{2,3}};
        double[][] array3 = {{4,5,6},{7,8,9}};
        double[][] array4 = {{1,3},{3,3}};
        double[][] array5 = {{1,2},{2,3},{3,4}};
        double[][] array6 = {{-1,-2},{-2,-3}};
        double[][] array7 = {{1,2,3,4,4,5,6,7},{2,3,4,5,6,7,8}};
        double[][] array8 = {{2,3},{4,5}};
        double[][] array9 = {{0,2,3,4,4,5,6,7},{2,3,4,5,6,7,8}};
        double[][] array10 = {{1,3,3,4,4,5,6,7},{2,3,4,5,6,7,8}};
        double[][] array11 = {{1,2,3},{1,2,3,},{1,2,3},{1,2,3}};
        double[][] array12 = {{1},{2}};
        double[][] array13 = {{4},{1}};
        double[][] arrayNull1 = null;
        
        // verify
        assertEquals(1, array1[0][0], 0);
        assertTrue("The arrays should be equal, return true", DataUtilities.equal(array1, array2));
        assertFalse("The arrays should not same length, return false", DataUtilities.equal(array1, array3));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array1, array4));
        assertFalse("Array b is null, return false", DataUtilities.equal(array1, arrayNull1));
        assertFalse("Array a is null, return false", DataUtilities.equal(arrayNull1, array1));
        assertFalse("The arrays should not same length, return false", DataUtilities.equal(array1, array5));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array1, array6));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array1, array7));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array7, array1));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array1, array8));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array7, array9));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array7, array10));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array1, array11));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array11, array1));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array12, array13));

        assertEquals(1, array1[0][0], 0);
        
        // tear-down: NONE in this test method
    }
	
	// mutation tests
	@Test (expected = AssertionError.class)
    public void testCloneEquals() throws Exception {
        // setup
        double[][] array = new double[5][5];
        array[0][0] = 1;
        array[0][1] = 1;
        array[0][2] = 1;
        array[1][4] = 1;
        // exercise  
        double[][] result = DataUtilities.clone(array);
        // verify
        assertNull(result);
        // tear-down: NONE in this test method
	}
	@Test (expected = IllegalArgumentException.class)
    public void testCloneNull() throws Exception {
        // setup
        double[][] array = null;
        // exercise   
        double[][] result = DataUtilities.clone(array);
        // verify
        assertNull(result);
        // tear-down: NONE in this test method
    }
	/*
	 * This test tests a row with valid data (normal numbers), valid columns (they exist) and valid rows, which is the partition (0, limit -1)
	 * We expect this to pass.
	 */
	
	
	/*
	 * This test covers a valid row and data, but the column does not exist, so we expect 0 as nothing as added.
	 * It tests the partition Column index beyond limits
	 * We expect this test to pass.
	 */
	
	/*
	 * This test covers a valid data and column, but an invalid row, as we are inputting a negative row number, we expect this test to
	 * still pass because we tried catching an exception, but it failed, so we assume this is a bug.
	 * We are covering the partition Row index out of bounds.
	 */
		
	public void testRowDataIsZeroValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(0));
	            one(values).getValue(1, 0);
	            will(returnValue(0));
	        }
	    });
	    // exercise	
	    int[] valid = {5};
	    double result = DataUtilities.calculateRowTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be 0", 0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with only positive data values. 
	 */
	@Test
	public void testRowPositiveDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getColumnCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	        }
	    });
	    // exercise	
	    
	    int[] valid = {0};
	    double result = DataUtilities.calculateRowTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be 10", 7.5, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with only negative data values. 
	 */
	@Test
	public void testRowNegativeDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getColumnCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(-7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    int[] valid = {0,1};
	    double result = DataUtilities.calculateRowTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be -10", -10.0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with positive, negative and data values of 0. 
	 */
	@Test
	public void testRowMixedDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getColumnCount();
	            will(returnValue(4));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(3, 0);
	            will(returnValue(0));
	        }
	    });
	    // exercise	
	    int[] valid = {0};
	    double result = DataUtilities.calculateRowTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be 7.5", 7.5, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column index value at the boundary.
	 */
	@Test
	public void testRowBoundaryValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getColumnCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	            one(values).getValue(1, 2);
	            will(returnValue(10));
	        }
	    });
	    // exercise	
	    int[] valid = {0,1,2};
	    double result = DataUtilities.calculateRowTotal(values, 1, valid);
	    // verify
	    assertEquals("The sum of the columns should be 42.9", 42.9, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column index value outside the boundary.
	 * It is designed to fail since there are no columns outside the boundary.
	 */

	
	/*
	 * This test covers columns with values of null data. It should ignore the null value 
	 * since Values2D uses Numbers objects instead of primitive data types, and thus should pass. 
	 */
	@Test
	public void testRowTotalNullDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getColumnCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(null));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    int[] valid = {3};
	    double result = DataUtilities.calculateRowTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should give an error", 0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers negative column index values. 
	 */
	@Test
	public void testNegativeRowIndexValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getColumnCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	            
	            one(values).getValue(0, -1);
	            will(returnValue(20));
	            one(values).getValue(1, -1);
	            will(returnValue(30.4));
	            one(values).getValue(2, -1);
	            will(returnValue(-2.5));
	            one(values).getValue(1, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(-1, 0);
	            will(returnValue(-10));
	            one(values).getValue(-1, 1);
	            will(returnValue(-7.5));
	            one(values).getValue(-1, 2);
	            will(returnValue(-20));
	            
	        }
	    });
	    // exercise
	    int[] valid = {0,1,2};
	    double result = DataUtilities.calculateRowTotal(values, -1, valid);
	    // verify
	    assertEquals("The sum of the columns should be 47.9", -37.5, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test tests a row with valid data (normal numbers), valid columns (they exist) and valid rows, which is the partition (0, limit -1)
	 * We expect this to pass.
	 */
	@Test
	 public void testRowTotalColumnValid() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(7.5));
		            one(values).getValue(0, 1);
		            will(returnValue(2.5));
		        }
		    });
		    int[] validCols = {0};
		    double result = DataUtilities.calculateRowTotal(values, 0, validCols);
		    assertEquals(7.5, result, 0);
	}
	/*
	 * This test tests a valid row and a valid column data, but the data is negative instead of positive this time.
	 * It tests the partition data(-limit+1, limit -1)
	 * We expect this to pass
	 */
	@Test
	public void testRowTotalColumnValidWithNegatives() {
		Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {
	        {
	            one(values).getColumnCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(-7.5));
	            one(values).getValue(0, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    int[] validCols = {0};
	    double result = DataUtilities.calculateRowTotal(values, 0, validCols);
	    assertEquals(-7.5, result, 0);
	}
	/*
	 * This test covers a valid row and data, but the column does not exist, so we expect 0 as nothing as added.
	 * It tests the partition Column index beyond limits
	 * We expect this test to pass.
	 */
	@Test
	public void testRowTotalInvalidColumn() {
		Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {
	        {
	            one(values).getColumnCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(-7.5));
	            one(values).getValue(0, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    int[] validCols = {4};
	    double result = DataUtilities.calculateRowTotal(values, 0, validCols);
	    assertEquals(0, result, 0);
	}
	/*
	 * This test covers a valid data and column, but an invalid row, as we are inputting a negative row number, we expect this test to
	 * still pass because we tried catching an exception, but it failed, so we assume this is a bug.
	 * We are covering the partition Row index out of bounds.
	 */
		
	public void testColumnDataIsZeroValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(0));
	            one(values).getValue(1, 0);
	            will(returnValue(0));
	        }
	    });
	    // exercise	
	    int[] valid = {5};
	    double result = DataUtilities.calculateColumnTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be 0", 0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with only positive data values. 
	 */
	@Test
	public void testColumnPositiveDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	        }
	    });
	    // exercise	
	    
	    int[] valid = {0};
	    double result = DataUtilities.calculateColumnTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be 10", 7.5, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with only negative data values. 
	 */
	@Test
	public void testColumnNegativeDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(-7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    int[] valid = {0,1};
	    double result = DataUtilities.calculateColumnTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be -10", -10.0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with positive, negative and data values of 0. 
	 */
	@Test
	public void testColumnMixedDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(4));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(3, 0);
	            will(returnValue(0));
	        }
	    });
	    // exercise	
	    int[] valid = {0};
	    double result = DataUtilities.calculateColumnTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should be 7.5", 7.5, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column index value at the boundary.
	 */
	@Test
	public void testColumnBoundaryValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    int[] valid = {0,1,2};
	    double result = DataUtilities.calculateColumnTotal(values, 1, valid);
	    // verify
	    assertEquals("The sum of the columns should be 47.9", 47.9, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column index value outside the boundary.
	 * It is designed to fail since there are no columns outside the boundary.
	 */

	
	/*
	 * This test covers columns with values of null data. It should ignore the null value 
	 * since Values2D uses Numbers objects instead of primitive data types, and thus should pass. 
	 */
	@Test
	public void testColumnTotalNullDataValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(null));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    int[] valid = {3};
	    double result = DataUtilities.calculateColumnTotal(values, 0, valid);
	    // verify
	    assertEquals("The sum of the columns should give an error", 0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers negative column index values. 
	 */
	@Test
	public void testNegativeColumnIndexValid() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	            
	            one(values).getValue(0, -1);
	            will(returnValue(20));
	            one(values).getValue(1, -1);
	            will(returnValue(30.4));
	            one(values).getValue(2, -1);
	            will(returnValue(-2.5));
	            
	        }
	    });
	    // exercise
	    int[] valid = {0,1,2};
	    double result = DataUtilities.calculateColumnTotal(values, -1, valid);
	    // verify
	    assertEquals("The sum of the columns should be 47.9", 47.9, result, 0);
	    // tear-down: NONE in this test method
	}
	/*
	 * This test covers a column with data values of 0.
	 */
	@Test
	public void testColumnDataIsZero() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(0));
	            one(values).getValue(1, 0);
	            will(returnValue(0));
	        }
	    });
	    // exercise	
	    double result = DataUtilities.calculateColumnTotal(values, 0);
	    // verify
	    assertEquals("The sum of the columns should be 0", 0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with only positive data values. 
	 */
	@Test
	public void testColumnPositiveData() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	        }
	    });
	    // exercise	
	    double result = DataUtilities.calculateColumnTotal(values, 0);
	    // verify
	    assertEquals("The sum of the columns should be 10", 10.0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with only negative data values. 
	 */
	@Test
	public void testColumnNegativeData() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(2));
	            one(values).getValue(0, 0);
	            will(returnValue(-7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    double result = DataUtilities.calculateColumnTotal(values, 0);
	    // verify
	    assertEquals("The sum of the columns should be -10", -10.0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column with positive, negative and data values of 0. 
	 */
	@Test
	public void testColumnMixedData() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(4));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(3, 0);
	            will(returnValue(0));
	        }
	    });
	    // exercise	
	    double result = DataUtilities.calculateColumnTotal(values, 0);
	    // verify
	    assertEquals("The sum of the columns should be 7.5", 7.5, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column index value at the boundary.
	 */
	@Test
	public void testColumnBoundary() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    double result = DataUtilities.calculateColumnTotal(values, 1);
	    // verify
	    assertEquals("The sum of the columns should be 47.9", 47.9, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers a column index value outside the boundary.
	 * It is designed to fail since there are no columns outside the boundary.
	 */

	
	/*
	 * This test covers columns with values of null data. It should ignore the null value 
	 * since Values2D uses Numbers objects instead of primitive data types, and thus should pass. 
	 */
	@Test
	public void testColumnTotalNullData() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(null));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	        }
	    });
	    // exercise	
	    double result = DataUtilities.calculateColumnTotal(values, 0);
	    // verify
	    assertEquals("The sum of the columns should give an error", 0, result, 0);
	    // tear-down: NONE in this test method
	}
	
	/*
	 * This test covers negative column index values. 
	 */
	@Test
	public void testNegativeColumnIndex() throws Exception {
	    // setup
	    Mockery mockingContext = new Mockery();
	    final Values2D values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {{
	            one(values).getRowCount();
	            will(returnValue(3));
	            one(values).getValue(0, 0);
	            will(returnValue(7.5));
	            one(values).getValue(1, 0);
	            will(returnValue(2.5));
	            one(values).getValue(2, 0);
	            
	            will(returnValue(-2.5));
	            one(values).getValue(0, 1);
	            will(returnValue(20));
	            one(values).getValue(1, 1);
	            will(returnValue(30.4));
	            one(values).getValue(2, 1);
	            will(returnValue(-2.5));
	            
	            one(values).getValue(0, -1);
	            will(returnValue(20));
	            one(values).getValue(1, -1);
	            will(returnValue(30.4));
	            one(values).getValue(2, -1);
	            will(returnValue(-2.5));
	            
	        }
	    });
	    // exercise	
	    double result = DataUtilities.calculateColumnTotal(values, -1);
	    // verify
	    assertEquals("The sum of the columns should be 47.9", 47.9, result, 0);
	    // tear-down: NONE in this test method
	}
	
	@Test (expected = IllegalArgumentException.class)
    public void testCumulativeNull() throws Exception {
        // setup
        KeyedValues values = null;
        // exercise    
        KeyedValues result = DataUtilities.getCumulativePercentages(values);
        // verify
        assertNull(values);
        // tear-down: NONE in this test method
    }
	
	/*
	 * Here we test a valid ordered list
	 * Meaning all the numbers are valid inputs in ascending order. This tests the partition data > 0 ,KeyValues[0] < KeyValues[1] < KeyValues[2] < …
	 * We expect this test to pass
	 */
	@Test
    public void testValidOrderedList() throws Exception {
        Mockery mockingContext = new Mockery();
        final KeyedValues values = mockingContext.mock(KeyedValues.class);
        mockingContext.checking(new Expectations() {{
            atMost(100).of(values).getItemCount();
            will(returnValue(3));
            
            atMost(100).of(values).getValue(0);
            will(returnValue(10));
            one(values).getKey(0);
            will(returnValue(0));
            
            atMost(100).of(values).getValue(1);
            will(returnValue(20));
            one(values).getKey(1);
            will(returnValue(1));
            
            atMost(10).of(values).getValue(2);
            will(returnValue(20));
            one(values).getKey(2);
            will(returnValue(2));
            }
        });  
        KeyedValues result = DataUtilities.getCumulativePercentages(values);
        assertEquals(0.2, result.getValue(0));
        assertEquals(0.6, result.getValue(1));
        assertEquals(1.0, result.getValue(2));
    }
	/*
	 * This test is testing a valid unordered list, same as the previous test but the data
	 * is not in ascending order.
	 * We expect this test to pass.
	 */
	@Test
	public void testValidUnOrderedList() throws Exception {
        Mockery mockingContext = new Mockery();
        final KeyedValues values = mockingContext.mock(KeyedValues.class);
        mockingContext.checking(new Expectations() {{
            atMost(100).of(values).getItemCount();
            will(returnValue(3));
            
            atMost(100).of(values).getValue(0);
            will(returnValue(70));
            one(values).getKey(0);
            will(returnValue(0));
                       
            atMost(10).of(values).getValue(2);
            will(returnValue(20));
            one(values).getKey(2);
            will(returnValue(2));
            
            atMost(100).of(values).getValue(1);
            will(returnValue(10));
            one(values).getKey(1);
            will(returnValue(1));
            }
        });  
        KeyedValues result = DataUtilities.getCumulativePercentages(values);
        assertEquals(0.7, result.getValue(0));
        assertEquals(0.8, result.getValue(1));
        assertEquals(1.0, result.getValue(2));
	}
	/*
	 * This test is testing an Invalid List in which we put in chars instead of data,
	 * so we throw an exception to detect the error, if this test passes, there is no bug.
	 * partition covered: data = char
	 */
	@Test
	public void testInvalidList() throws Exception {
        Mockery mockingContext = new Mockery();
        final KeyedValues values = mockingContext.mock(KeyedValues.class);
        mockingContext.checking(new Expectations() {{
            atMost(100).of(values).getItemCount();
            will(returnValue(3));
            
            atMost(100).of(values).getValue(0);
            will(returnValue('a'));
            one(values).getKey(0);
            will(returnValue(0));
                                   
            atMost(100).of(values).getValue(1);
            will(returnValue('z'));
            one(values).getKey(1);
            will(returnValue(1));
            
            atMost(10).of(values).getValue(2);
            will(returnValue('h'));
            one(values).getKey(2);
            will(returnValue(2));
            }
        });  
        try {
        	 KeyedValues result = DataUtilities.getCumulativePercentages(values);
        	 fail("Should not be able to get percentages from characters");
        }
        catch(Exception exc){
        	assertNotNull(exc);
        }
	}
	/*
	 * This test is for the partition data < 0, we expect a result of 0.7 if the
	 * numbers are correctly calculated, but instead we got 1.16, this should be a bug, an acceptable answer
	 * would have been a null as well, as negatives should not be supported in percentages but since we got 
	 * a real answer that did not match what we expected, this is a bug.
	 */
	@Test
	public void testValidListWithNegativeNumbers() throws Exception {
        Mockery mockingContext = new Mockery();
        final KeyedValues values = mockingContext.mock(KeyedValues.class);
        mockingContext.checking(new Expectations() {{
            atMost(100).of(values).getItemCount();
            will(returnValue(3));
            
            atMost(100).of(values).getValue(0);
            will(returnValue(50));
            one(values).getKey(0);
            will(returnValue(0));
                       
            atMost(10).of(values).getValue(2);
            will(returnValue(-20));
            one(values).getKey(2);
            will(returnValue(2));
            
            atMost(100).of(values).getValue(1);
            will(returnValue(10));
            one(values).getKey(1);
            will(returnValue(1));
            }
        });  
        KeyedValues result = DataUtilities.getCumulativePercentages(values);
        assertEquals(1.25, result.getValue(0));
        assertEquals(1.5, result.getValue(1));
        assertEquals(1.0, result.getValue(2));
	}
	/*
	 * This test is testing the partition data = null
	 * We expect this test to pass since the method states to not put in null values
	 * but an exception is never thrown so this is a bug
	 */
	@Test
	public void testNullArgumentList() throws Exception {
        Mockery mockingContext = new Mockery();
        final KeyedValues values = mockingContext.mock(KeyedValues.class);
        mockingContext.checking(new Expectations() {{
            atMost(100).of(values).getItemCount();
            will(returnValue(3));
            
            atMost(100).of(values).getValue(0);
            will(returnValue(40));
            one(values).getKey(0);
            will(returnValue(0));
                       
            atMost(10).of(values).getValue(1);
            will(returnValue(10));
            one(values).getKey(1);
            will(returnValue(1));
            
            atMost(100).of(values).getValue(2);
            will(returnValue(null));
            one(values).getKey(2);
            will(returnValue(2));
            }
        });  
        	KeyedValues result = DataUtilities.getCumulativePercentages(values);
        	assertEquals(0.8, result.getValue(0));
        	assertEquals(1.0,result.getValue(1));
        	assertEquals(1.0, result.getValue(2));
	}
	/*
	 * This test is testing the partition key > max
	 * We expect this test to pass
	 */
	@Test
	public void testKeyOutsideBounds() throws Exception {
        Mockery mockingContext = new Mockery();
        final KeyedValues values = mockingContext.mock(KeyedValues.class);
        mockingContext.checking(new Expectations() {{
            atMost(100).of(values).getItemCount();
            will(returnValue(3));
            
            atMost(100).of(values).getValue(0);
            will(returnValue(30));
            one(values).getKey(0);
            will(returnValue(0));
                       
            atMost(10).of(values).getValue(1);
            will(returnValue(10));
            one(values).getKey(1);
            will(returnValue(1));
            
            atMost(100).of(values).getValue(2);
            will(returnValue(10));
            one(values).getKey(2);
            will(returnValue(2));
            }
        });  
        	KeyedValues result = DataUtilities.getCumulativePercentages(values);
        	assertEquals(0.6, result.getValue(0));
        	assertEquals(0.8,result.getValue(1));
        	try {
        		result.getValue(5);
        		fail("This index is out of bounds, should not work");
        	}
        	catch (Exception exc) {
        		assertNotNull(exc);
        	}
	}
	@Test
	public void testCumulativePercentageMutationItemCountZero() throws Exception {
        Mockery mockingContext = new Mockery();
        final KeyedValues values = mockingContext.mock(KeyedValues.class);
        mockingContext.checking(new Expectations() {{
            atMost(100).of(values).getItemCount();
            will(returnValue(0));
            
            atMost(100).of(values).getValue(0);
            will(returnValue(30));
            one(values).getKey(0);
            will(returnValue(0));
                       
            atMost(10).of(values).getValue(1);
            will(returnValue(10));
            one(values).getKey(1);
            will(returnValue(1));
            
            atMost(100).of(values).getValue(2);
            will(returnValue(10));
            one(values).getKey(2);
            will(returnValue(2));
            }
        });  
        KeyedValues result = DataUtilities.getCumulativePercentages(values);
        try {
    		result.getValue(0);
    		fail("This index is out of bounds, should not work");
    	}
    	catch (Exception exc) {
    		assertNotNull(exc);
    	}
	}
	
	@Test
    public void testColumnValid() {
		//This test is testing partition of (0, limit -1)
		
		
        // setup
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(3));
                one(values).getValue(0, 0);
                will(returnValue(7.5));
                one(values).getValue(1, 0);
                will(returnValue(2.5));
                one(values).getValue(2, 0);
                will(returnValue(5.5));
            }
        });
        int[] validRows = new int[]{1,2};
        double result = DataUtilities.calculateColumnTotal(values, 0,validRows);
        // verify
        assertEquals(result, 8.0, .000000001d);
        // tear-down: NONE in this test method
    }
	@Test
    public void testColumnValidNegative() {
		//This test is testing partition of (-limit+1, limit -1)	
		
        // setup
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(4));
                one(values).getValue(0, 0);
                will(returnValue(-7.5));
                one(values).getValue(1, 0);
                will(returnValue(-2.5));
                one(values).getValue(2, 0);
                will(returnValue(5.5));
                one(values).getValue(3, 0);
                will(returnValue(7));
            }
        });
        int[] validRows = new int[]{0,1,3};
        double result = DataUtilities.calculateColumnTotal(values, 0,validRows);
        
        // verify
        assertEquals("Result should be -3 but is "+result,result, -3.0, .000000001d);
        // tear-down: NONE in this test method
    }
	@Test 
	public void testColumnGreaterOrEqualTo() {
		
        // setup
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(4));
                one(values).getValue(0, 0);
                will(returnValue(-7.5));
                one(values).getValue(1, 0);
                will(returnValue(-2.5));
                one(values).getValue(2, 0);
                will(returnValue(5.5));
                one(values).getValue(3, 0);
                will(returnValue(7));
            }
        });
        int[] validRows = new int[] {};
        double result = DataUtilities.calculateColumnTotal(values, 0,validRows);
        
        // verify
        assertEquals(result, 0, .000000001d);
        // tear-down: NONE in this test method
    }
	@Test
public void testColumnEmpty() {
        // setup
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(0));
            }
        });
        int[] validRows = new int[] {};
        double result = DataUtilities.calculateColumnTotal(values, 0,validRows);
        
        // verify
        assertEquals(result, 0, .000000001d);
        // tear-down: NONE in this test method
    }
	@Test
	public void testColumnEmptyNoValidRowsMutation() {
	        // setup
	        Mockery mockingContext = new Mockery();
	        final Values2D values = mockingContext.mock(Values2D.class);
	        mockingContext.checking(new Expectations() {
	            {
	                one(values).getRowCount();
	                will(returnValue(0));
	            }
	        });
	        double result = DataUtilities.calculateColumnTotal(values, 0);
	        // verify
	        assertEquals(result, 0, .000000001d);
	        // tear-down: NONE in this test method
	    }
	@Test 
	public void testColumnGreaterOrEqualToNoValidRowsMutation() {
		
        // setup
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(4));
                one(values).getValue(0, 0);
                will(returnValue(-7.5));
                one(values).getValue(1, 0);
                will(returnValue(-2.5));
                one(values).getValue(2, 0);
                will(returnValue(5.5));
                one(values).getValue(3, 0);
                will(returnValue(7));
            }
        });
        int[] validRows = new int[] {};
        double result = DataUtilities.calculateColumnTotal(values, 0,validRows);
        
        // verify
        assertEquals(result, 0, .000000001d);
        // tear-down: NONE in this test method
    }
	@Test 
	public void testColumnRowCountZeroMutation() {
		
        // setup
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(0));
                one(values).getValue(0, 0);
                will(returnValue(-7.5));
                one(values).getValue(1, 0);
                will(returnValue(-2.5));
                one(values).getValue(2, 0);
                will(returnValue(5.5));
                one(values).getValue(3, 0);
                will(returnValue(7));
            }
        });
        int[] validRows = new int[] {};
        double result = DataUtilities.calculateColumnTotal(values, 0,validRows);
        
        // verify
        assertEquals(result, 0, .000000001d);
        // tear-down: NONE in this test method
    }
	@Test 
	public void testColumnRowCountZeroNoValidRowsMutation() {
		
        // setup
        Mockery mockingContext = new Mockery();
        final Values2D values = mockingContext.mock(Values2D.class);
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(0));
                one(values).getValue(0, 0);
                will(returnValue(-7.5));
                one(values).getValue(1, 0);
                will(returnValue(-2.5));
                one(values).getValue(2, 0);
                will(returnValue(5.5));
                one(values).getValue(3, 0);
                will(returnValue(7));
            }
        });
        double result = DataUtilities.calculateColumnTotal(values, 0);
        
        // verify
        assertEquals(result, 0, .000000001d);
        // tear-down: NONE in this test method
    }
	@Test
	 public void testAllNegativeRow() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(-7.5));
		            one(values).getValue(0, 1);
		            will(returnValue(-2.5));
		        }
		    });
		    double result = DataUtilities.calculateRowTotal(values, 0);
		    assertEquals(-10.0, result, 0);
	 }
	 /*
	  * This test case is for the method calculateRowTotal() in class DataUtilities
	  * It tests the partition where the row has an overall negative value
	  */
	 @Test
	 public void testOverallNegativeRow() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(-7.5));
		            one(values).getValue(0, 1);
		            will(returnValue(4));
		        }
		    });
		    double result = DataUtilities.calculateRowTotal(values, 0);
		    assertEquals(-3.5, result, 0);
	 }
	 /*
	  * This test case is for the method calculateRowTotal in class DataUtilities
	  * It tests the partition where the row has an overall positive value
	  */
	 @Test
	 public void testMostlyPositiveRow() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(7.5));
		            one(values).getValue(0, 1);
		            will(returnValue(-2.5));
		        }
		    });
		    double result = DataUtilities.calculateRowTotal(values, 0);
		    assertEquals(5.0, result, 0);
	 }
	 /*
	  * This test case is for the method calculateRowTotal in class DataUtilities
	  * It tests the partition where the row is a negative version of a row that already exists
	  * We expect this test to pass, since we throw an exception, but it failed, so we assume this is a bug.
	  */
	 @Test
	 public void testNegativeIndexRow() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(7.5));
		            one(values).getValue(0, 1);
		            will(returnValue(-2.5));
		        }
		    });
		    double result = DataUtilities.calculateRowTotal(values, -0);
		    assertEquals(5.0, result, 0);
	 }
	 /*
	  * This test case is for the method calculateRowTotal in class DataUtilities
	  * It tests the partition where the first index of the row is a null value
	  */
	 @Test
	 public void testFirstIndexNull() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(null));
		            one(values).getValue(0, 1);
		            will(returnValue(2.5));
		        }
		    });
		    double result = DataUtilities.calculateRowTotal(values, 0);
		    assertEquals(2.5, result, 0);
	 }
	 /*
	  * This test case is for the method calculateRowTotal in class DataUtilities
	  * It tests the partition where the last index of the row is a null value
	  */
	 @Test
	 public void testLastIndexNull() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(50.45));
		            one(values).getValue(0, 1);
		            will(returnValue(null));
		        }
		    });
		    double result = DataUtilities.calculateRowTotal(values, 0);
		    assertEquals(50.45, result, 0);
	 }
	 @Test
	 public void testRowCountColumnCountZeroMutation() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(0));
		            one(values).getValue(0, 0);
		            will(returnValue(50.45));
		            one(values).getValue(0, 1);
		            will(returnValue(10.0));
		        }
		    });
		    double result = DataUtilities.calculateRowTotal(values, 0);
		    assertEquals(0, result, 0);
	 }
	 @Test
	 public void testRowCountWithValidColsZeroMutation() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(2));
		            one(values).getValue(0, 0);
		            will(returnValue(50.45));
		            one(values).getValue(0, 1);
		            will(returnValue(10.0));
		        }
		    });
		    int validCols[] = {};
		    double result = DataUtilities.calculateRowTotal(values, 0, validCols);
		    assertEquals(0, result, 0);
	 }
	 @Test
	 public void testRowCountWithValidColsZeroRowCountZeroMutation() {
		    Mockery mockingContext = new Mockery();
		    final Values2D values = mockingContext.mock(Values2D.class);
		    mockingContext.checking(new Expectations() {
		        {
		            one(values).getColumnCount();
		            will(returnValue(0));
		            one(values).getValue(0, 0);
		            will(returnValue(50.45));
		            one(values).getValue(0, 1);
		            will(returnValue(10.0));
		        }
		    });
		    int validCols[] = {};
		    double result = DataUtilities.calculateRowTotal(values, 0, validCols);
		    assertEquals(0, result, 0);
	 }
	@Test
    public void testClone() throws Exception {
        // setup
        double[][] array = {{1,2,3,4},{2,3,4,5}};
        // exercise    
        double[][] result = DataUtilities.clone(array);
        // verify
        assertArrayEquals("The arrays should be equal", array, result);
        // tear-down: NONE in this test method
    }
    @Test
    public void testCloneEqualityCheckTrueMutation() throws Exception {
        // setup
        double[][] array = {{}};
        // exercise    
        double[][] result = DataUtilities.clone(array);
        // verify
        assertArrayEquals("The arrays should be equal", array, result);
        // tear-down: NONE in this test method
    }
    @Test
    public void testCloneSourceNull() throws Exception {
        // setup
        double[][] array = new double[5][5];
        Arrays.fill(array, null);
        // exercise    
        double[][] result = DataUtilities.clone(array);
        // verify
        assertArrayEquals("The arrays should be equal", array, result);
        // tear-down: NONE in this test method
    }
    @Test
    public void testCloneFalse() throws Exception {
        // setup
        double[][] array = {{0,0}};
        // exercise    
        double[][] result = DataUtilities.clone(array);
        // verify
        assertArrayEquals("The arrays should be equal", array, result);
        // tear-down: NONE in this test method
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testCreateArrayNull() throws Exception {
        // setup
        double[] array = null;
        // exercise    
        Number[] result = DataUtilities.createNumberArray(array);
        // verify
        assertNull(array);
        // tear-down: NONE in this test method
    }
    
    @Test
    public void testCreateArray() throws Exception {
        // setup
        double[] array = {1,2,3,4};
        // exercise    
        Number[] result = DataUtilities.createNumberArray(array);
        // verify
        assertEquals("The numbers should be equal", array[0], result[0]);
        assertEquals("The numbers should be equal", array[1], result[1]);
        assertEquals("The numbers should be equal", array[2], result[2]);
        assertEquals("The numbers should be equal", array[3], result[3]);
        // tear-down: NONE in this test method
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testCreateArray2DNull() throws Exception {
        // setup
        double[][] array = null;
        // exercise    
        Number[][] result = DataUtilities.createNumberArray2D(array);
        // verify
        assertNull(array);
        // tear-down: NONE in this test method
    }
    
    @Test
    public void testCreateArray2D() throws Exception {
        // setup
        double[][] array = {{1,2},{2,3}};
        // exercise    
        Number[][] result = DataUtilities.createNumberArray2D(array);
        // verify
        assertEquals("The numbers should be equal", array[0][0], result[0][0]);
        assertEquals("The numbers should be equal", array[1][0], result[1][0]);
        assertEquals("The numbers should be equal", array[0][1], result[0][1]);
        assertEquals("The numbers should be equal", array[1][1], result[1][1]);
        // tear-down: NONE in this test method
    }
    
    
    @Test
    public void testEqual() throws Exception {
        // setup
        double[][] array1 = {{1,2},{2,3}};
        double[][] array2 = {{1,2},{2,3}};
        double[][] array3 = {{4,5,6},{7,8,9}};
        double[][] array4 = {{1,3},{3,3}};
        double[][] array5 = {{1,2},{2,3},{3,4}};
        double[][] arrayNull1 = null;
        
        // verify
        assertTrue("The arrays should be equal, return true", DataUtilities.equal(array1, array2));
        assertFalse("The arrays should not same length, return false", DataUtilities.equal(array1, array3));
        assertFalse("The arrays should not equal, return false", DataUtilities.equal(array1, array4));
        assertFalse("Array b is null, return false", DataUtilities.equal(array1, arrayNull1));
        assertFalse("Array a is null, return false", DataUtilities.equal(arrayNull1, array1));
        assertFalse("The arrays should not same length, return false", DataUtilities.equal(array1, array5));
        
        // tear-down: NONE in this test method
        }
    @Test
    public void testEqualFalseEqualityCheckMutation() throws Exception {
    	double[][] array1 = null;
    	double[][] array2 = null;
    	double[][] arrayA = {{1,3},{3,3}};
    	double[][] arrayB = {{2,5,4}};
    	double[][] arrayC = new double[5][5];
    	Arrays.fill(arrayC, null);
    	assertTrue("The arrays should be equal, return true", DataUtilities.equal(array1, array2));
        assertFalse("The arrays should not same length, return false", DataUtilities.equal(arrayA, arrayB));
        assertFalse("The arrays are not equal, return false", DataUtilities.equal(arrayA, arrayC));
    }
    @Test 
    public void testEqualMutationLessThanEqualTo() throws Exception {
    	double[][] array1 = null;
    	double [][] array2 = null;
    	double[][] arrayA = {{1,3},{3,3}};
    	double [][] arrayB = {{2,5,4}};
    	assertTrue("The arrays should be equal, return true", DataUtilities.equal(array2, array1));
    	assertTrue("The arrays should be equal, return true", DataUtilities.equal(array1, array2));
        assertFalse("The arrays are not equal, return false", DataUtilities.equal(arrayB, arrayA));
        assertFalse("The arrays are not equal, return false", DataUtilities.equal(arrayA, arrayB));
    }
    @Test 
    public void testEqualMutationLong() throws Exception {
    	double[][] arrayA = {{1,3,5,2,7,9,4,2},{3,3,9,7,2,5,2,6}};
    	double[][] arrayB = {{2,5,4,1,1,1,1,1},{1,5,7,9,6,3,1,3}};
    	double[][] arrayC = new double[10][10];
        assertFalse("The arrays are not equal, return false", DataUtilities.equal(arrayB, arrayA));
        assertFalse("The arrays are not equal, return false", DataUtilities.equal(arrayA, arrayB));
        assertFalse("The arrays are not equal, return false", DataUtilities.equal(arrayA, arrayC));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testCalculateColumnNull() throws Exception {
    	final Values2D array = null;
    	double result1 = DataUtilities.calculateColumnTotal(array, 0);
    	int thing[] = {1,2};
    	double result2 = DataUtilities.calculateColumnTotal(array, 0, thing);
    	assertEquals(0, result1, 0);
    	assertEquals(0, result2, 0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testCalculateRowNull() throws Exception {
    	final Values2D array = null;
    	int thing[] = {1,2};
    	double result1 = DataUtilities.calculateColumnTotal(array, 0);
    	double result2 = DataUtilities.calculateColumnTotal(array, 0, thing);
    	assertEquals(0, result1, 0);
    	assertEquals(0, result2, 0);
    }
    
}
