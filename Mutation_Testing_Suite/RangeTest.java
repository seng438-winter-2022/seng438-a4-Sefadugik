package org.jfree.data.test;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
	private Range exampleRange;
    private Range UpperBoundZero;
    private Range UpperBoundPositive;
    private Range UpperBoundNegative;
    private Range UpperBoundChar;
    private Range UpperBoundMax;
    private Range UpperBoundOverMax;
    private Range LowerBoundZero;
    private Range LowerBoundPositive;
    private Range LowerBoundNegative;
    private Range LowerBoundChar;
    private Range LowerBoundMin;
    private Range LowerBoundUnderMin;
    private Range twoNegativeBounds; //For testNegativeBounds() method
    private Range positiveUpperBoundNegativeLowerBound; //for testPositiveUpperBoundNegativeLowerBound() method
    private Range bothBoundsPositive; //for testbothBoundsPositive() method
    private Range bothBoundsHaveDecimals;//for testbothBoundsHaveDecimals() method
    private Range bothBoundsZero; // for testBothBoundsZero() method
    private static Range range;
    private Range Range1;
	private Range Range2;
	private Range combinedRange;
    
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }

    
    @Before
    public void setUp() throws Exception { 
    	exampleRange = new Range(-1, 1);
    	UpperBoundZero = new Range(-1, 0);
        UpperBoundPositive = new Range(1, 20.1);
        UpperBoundNegative = new Range(-20, -1);
        UpperBoundChar = new Range(0, 'a');
        UpperBoundMax = new Range(1, Double.MAX_VALUE);
        UpperBoundOverMax = new Range (0, Double.MAX_VALUE + 1000000000000000000000000000000000000000000.0);
        LowerBoundZero = new Range(0, 1);
    	LowerBoundPositive = new Range(20.1, 40.5);
    	LowerBoundNegative = new Range(-1, 100);
    	LowerBoundChar = new Range('b', 200);
    	LowerBoundMin = new Range(Double.MIN_VALUE, 1000000000);
        LowerBoundUnderMin = new Range (4.6E-324, 1000000000);
        twoNegativeBounds = new Range(-9,-5); 
    	positiveUpperBoundNegativeLowerBound = new Range(-5,8); 
    	bothBoundsPositive = new Range(10,20);
    	bothBoundsHaveDecimals = new Range(10.6,20.3);
    	bothBoundsZero = new Range(0,0);
    	range = new Range (-5, 15);
    	
        // Reversing range does not work since the function requires lower < upper
        // RangeReversed = new Range (10, 1);
    }
    
    @Test 
    public void testExpandInclude() {
    	Range1 = null;
    	Range1 = Range.expandToInclude(Range1, 0);
    	assertEquals(0, Range1.getLowerBound(), 0);
    	assertEquals(0, Range1.getUpperBound(), 0);
    	
    	Range2 = new Range(-5,0);
    	
    	Range2 = Range.expandToInclude(Range2, -3);
    	assertEquals(-5, Range2.getLowerBound(), 0);
    	assertEquals(0, Range2.getUpperBound(), 0);
    	
    	Range2 = Range.expandToInclude(Range2, -5);
    	Range2 = Range.expandToInclude(Range2, 0);
    	assertEquals(-5, Range2.getLowerBound(), 0);
    	assertEquals(0, Range2.getUpperBound(), 0);
    	
    	Range2 = Range.expandToInclude(Range2, -10);
    	Range2 = Range.expandToInclude(Range2, 20);
    	assertEquals(-10, Range2.getLowerBound(), 0);
    	assertEquals(20, Range2.getUpperBound(), 0);
    	
    }
    
    @Test
    public void testCombineNaNNullMutation() {
    	Range1 = null;
    	Range2 = new Range(-5,0);
    	combinedRange = Range.combineIgnoringNaN(Range1, Range2);
    	assertEquals(-5, combinedRange.getLowerBound(), 0);
    	assertEquals(0, combinedRange.getUpperBound(), 0);
    	
    	Range1 = new Range(5,15);
    	Range2 = null;
    	combinedRange = Range.combineIgnoringNaN(Range1, Range2);
    	assertEquals(5, combinedRange.getLowerBound(), 0);
    	assertEquals(15, combinedRange.getUpperBound(), 0);
    }
    
    @Test
    public void testCombineNaNMutation() {
    	Range1 = new Range(5,15);
    	Range2 = new Range(-5,0);
    	combinedRange = Range.combineIgnoringNaN(Range1, Range2);
    	assertEquals(-5, combinedRange.getLowerBound(), 0);
    	assertEquals(15, combinedRange.getUpperBound(), 0);
    	
    	combinedRange = Range.combineIgnoringNaN(Range2, Range1);
    	assertEquals(-5, combinedRange.getLowerBound(), 0);
    	assertEquals(15, combinedRange.getUpperBound(), 0);
    	
    	Range1 = new Range(-10, Double.NaN);
    	combinedRange = Range.combineIgnoringNaN(Range1, Range2);
    	assertEquals(-10, combinedRange.getLowerBound(), 0);
    	assertEquals(0, combinedRange.getUpperBound(), 0);
    	
    	Range1 = new Range(5,15);
    	Range2 = new Range(Double.NaN, 20);
    	combinedRange = Range.combineIgnoringNaN(Range1, Range2);
    	assertEquals(5, combinedRange.getLowerBound(), 0);
    	assertEquals(20, combinedRange.getUpperBound(), 0);
    	
    	Range1 = new Range(Double.NaN, Double.NaN);
    	Range2 = new Range(-5,0);
    	combinedRange = Range.combineIgnoringNaN(Range1, Range2);
    	assertEquals(-5, combinedRange.getLowerBound(), 0);
    	assertEquals(0, combinedRange.getUpperBound(), 0);
    	
    	Range1 = new Range(Double.NaN, Double.NaN);
    	Range2 = new Range(Double.NaN, Double.NaN);
    	combinedRange = Range.combineIgnoringNaN(Range1, Range2);
    	assertNull(combinedRange);
    }
    
    @Test
    public void testCombineNullMutation() {
    	Range1 = null;
    	Range2 = new Range(-5,0);
    	combinedRange = Range.combine(Range1, Range2);
    	assertEquals(-5, combinedRange.getLowerBound(), 0);
    	assertEquals(0, combinedRange.getUpperBound(), 0);
    	
    	Range1 = new Range(5,15);
    	Range2 = null;
    	combinedRange = Range.combine(Range1, Range2);
    	assertEquals(5, combinedRange.getLowerBound(), 0);
    	assertEquals(15, combinedRange.getUpperBound(), 0);
    }
    
    @Test
    public void testCombineMutation() {
    	Range1 = new Range(5,15);
    	Range2 = new Range(-5,0);
    	combinedRange = Range.combine(Range1, Range2);
    	assertEquals(-5, combinedRange.getLowerBound(), 0);
    	assertEquals(15, combinedRange.getUpperBound(), 0);
    	
    	combinedRange = Range.combine(Range2, Range1);
    	assertEquals(-5, combinedRange.getLowerBound(), 0);
    	assertEquals(15, combinedRange.getUpperBound(), 0);
    }
    
    @Test
    public void testIntersectsBoundary() {
        Range1 = new Range(5, 15);
        boolean result = Range1.intersects(5,15);
        assertTrue(result);
        assertFalse(Range1.intersects(0, 4));
        assertFalse(Range1.intersects(16, 20));
        assertTrue(Range1.intersects(5, 15));
        assertTrue(Range1.intersects(5, 6));
        assertTrue(Range1.intersects(14, 15));
        assertTrue(Range1.intersects(4, 6));
        assertTrue(Range1.intersects(14, 16));
        assertTrue(Range1.intersects(-5, 20));
        assertFalse(Range1.intersects(4, 5));
        assertFalse(Range1.intersects(15, 16));
        assertFalse(Range1.intersects(16, 18));
        assertFalse(Range1.intersects(5, 5));
        assertFalse(Range1.intersects(15, 15));

        assertFalse(Range1.intersects(4,0));
        assertFalse(Range1.intersects(20,16));
        assertFalse(Range1.intersects(15,5));
        assertTrue(Range1.intersects(5,6));
        assertFalse(Range1.intersects(15,14));
        assertFalse(Range1.intersects(6,4));
        assertFalse(Range1.intersects(16,14));
        assertFalse(Range1.intersects(20,-5));
        assertFalse(Range1.intersects(5,4));
        assertFalse(Range1.intersects(16,15));
        assertFalse(Range1.intersects(18,16));
        assertFalse(Range1.intersects(5, 5));
        assertFalse(Range1.intersects(15, 15));
        assertFalse(Range1.intersects(10, 9));
        
        assertEquals(5 ,Range1.getLowerBound(),0);
        assertEquals(15 ,Range1.getUpperBound(),0);
        
        Range2 = new Range(0,4);
        boolean result2 = Range1.intersects(Range1);
        boolean result3 = Range1.intersects(Range2);
        assertTrue(result2);
        assertFalse(result3);
        assertFalse(Range1.intersects(Range2));
    }
    
    @Test 
    public void testContrainsMutation() {
        Range1 = new Range(-5, 15);
        Range2 = new Range(-15, -5);
        double result = Range1.constrain(0);
        assertEquals(0, result, 0);
        double result2 = Range1.constrain(-5);
        assertEquals(-5, result2, 0);
        double result3 = Range1.constrain(15);
        assertEquals(15, result3, 0);
        double result4 = Range1.constrain(-4);
        assertEquals(-4, result4, 0);
        double result5 = Range1.constrain(-6);
        assertEquals(-5, result5, 0);
        double result6 = Range1.constrain(16);
        assertEquals(15, result6, 0);
        double result7 = Range1.constrain(14);
        assertEquals(14, result7, 0);
        assertEquals(0, Range1.constrain(0), 0);
        assertEquals(-5, Range1.constrain(-5), 0);
        assertEquals(15,Range1.constrain(15),0);
        assertEquals(-5,Range1.constrain(-6),0);
        assertEquals(14,Range1.constrain(14),0);
        assertEquals(-4,Range1.constrain(-4),0);
        assertEquals(15,Range1.constrain(16),0);
       
        assertEquals(-10,Range2.constrain(-10),0);
        assertEquals(-5, Range2.constrain(0), 0);
        assertEquals(-5, Range2.constrain(-5), 0);
        assertEquals(-5,Range2.constrain(15),0);
        assertEquals(-6,Range2.constrain(-6),0);
        assertEquals(-5,Range2.constrain(14),0);
        assertEquals(-5,Range2.constrain(-4),0);
        assertEquals(-5,Range2.constrain(16),0);
        
        assertEquals(-5, Range1.getLowerBound(),0);
        assertEquals(15, Range1.getUpperBound(),0);
    }
    
    @Test 
    public void testContainsMutation() {
        Range1 = new Range(-5, 15);
        Range2 = new Range(-15, -5);
        boolean result = Range1.contains(0);
        assertTrue(result);
        boolean result2 = Range1.contains(20);
        assertFalse(result2);
        assertTrue(Range1.contains(0));
        assertTrue(Range1.contains(-5));
        assertTrue(Range1.contains(15));
        assertFalse(Range1.contains(-6));
        assertTrue(Range1.contains(14));
        assertTrue(Range1.contains(-4));
        assertFalse(Range1.contains(16));
       
        assertTrue(Range2.contains(-10));
        assertEquals(-5, Range1.getLowerBound(),0);
        assertEquals(15, Range1.getUpperBound(),0);
    }
    
    // after running mutation (line 90)
    @Test (expected = IllegalArgumentException.class)
    public void tetRangeComparison() {
        Range1 = new Range(15, 5);
        assertNull(Range1);
    }
    
    // after running mutation (line 157)
    @Test
    public void testIntersectsComparison() {
        Range1 = new Range(5, 15);
        assertFalse(Range1.intersects(0, 4));
        assertTrue(Range1.intersects(5, 15));
        assertEquals(5 ,Range1.getLowerBound(),0);
        assertEquals(15 ,Range1.getUpperBound(),0);
    }
    
    // after running mutation (line 157)
    @Test
    public void testIntersectsComparison2() {
        Range1 = new Range(5, 15);
        assertTrue(Range1.intersects(5, 10));
    }
    
    // after running mutation (line 329)
    @Test (expected = IllegalArgumentException.class)
    public void testExpandIllegal() {
        Range1 = null;
        Range.expand(Range1, 0, 20);
        assertNull(Range1);
    }
    
    /*
    @Test 
    public void testRangeGlobalChange() {
    	assertEquals(-5, range.getLowerBound(),0);
    	assertEquals(15, range.getUpperBound(),0);
    	
    	range.contains(0);
    	range.intersects(0, 0);
    	range.intersects(exampleRange);
    	range.constrain(0);
    	Range.combine(range, exampleRange);
    	Range.combineIgnoringNaN(range, exampleRange);
    	Range.expandToInclude(range, 0);
    	Range.expand(range, 0, 0);
    	Range.shift(range, 0);
    	Range.shift(range, 0, true);
    	
    	assertEquals(-5, range.getLowerBound(),0);
    	assertEquals(15, range.getUpperBound(),0);
    	
    }
    */
    
    @Test
    public void testEqualsNegative() {
        Range1 = new Range(-5, 15);
        Range2 = new Range(-5, 15);
        boolean isEqual = Range1.equals(Range2);
        assertTrue(isEqual);
    }
    @Test
    public void testEqualsLower() {
        Range1 = new Range(-4, 15);
        Range2 = new Range(-5, 15);
        boolean isEqual = Range1.equals(Range2);
        assertFalse(isEqual);
    }
    @Test
    public void testEqualsUpper() {
        Range1 = new Range(-5, 14);
        Range2 = new Range(-5, 15);
        boolean isEqual = Range1.equals(Range2);
        assertFalse(isEqual);
    }
    @Test
    public void testIsNaNRangeMoreBranch() {
        Range1 = new Range(-5, 15);
        boolean value = Range1.isNaNRange();
        assertFalse(value);
    }
    @Test
    public void testShiftWithNoZeroCrossing() {
        Range1 = new Range(-5, 14);
        Range value = Range1.shift(Range1, -2.0, true);
        double out = value.getLowerBound();
        assertEquals(-7, out, .000000001d);
    }
     @Test
        public void testHasCodeMoreBranch() {
            Range1 = new Range(-5, 15);
            int value = Range1.hashCode();
            System.out.println(value);
            assertEquals(41025536, value, .000000001d);
      }
     @Test
     public void testConstrainDoesNotContain2() {
    	 Range1 = new Range(-5,15);
    	 double a = Range1.constrain(-6);
    	 assertEquals(-5,a,.000000001d);
     }
     @Test
     public void testConstrainDoesNotContain3() {
    	 Range1 = new Range(-5,15);
    	 double a = Range1.constrain(16);
    	 assertEquals(15,a,.000000001d);
     }
     @Test
     public void expandToIncludeNull() {
    	 Range1 = new Range(2,4);
    	 Range1 = Range.expandToInclude(Range2, 4);
    	 assertEquals(4,Range1.getUpperBound(),.000000001d);
     }
     @Test
     public void expandToIncludeLess() {
    	 Range1 = new Range(2,4);
    	 Range1 = Range.expandToInclude(Range1, 1);
    	 assertEquals(1,Range1.getLowerBound(),.000000001d);
     }
     @Test
     public void expandToIncludeElse() {
    	 Range1 = new Range(4,4);
    	 Range1 = Range.expandToInclude(Range1, 4);
    	 assertEquals(4,Range1.getLowerBound(),.000000001d);
     }
     
    @Test
	public void testCombine() {
    	//This test is testing partition of (positive, positive)
    	
    	Range1 = new Range(2,8);
    	Range2 = new Range(10,12);
    	Range combinedRange = Range.combine(Range1, Range2);
        assertEquals("The lower bound should be 2",
        2, combinedRange.getLowerBound(), .000000001d);
        assertEquals("The upper bound should be 12",
                12, combinedRange.getUpperBound(), .000000001d);
    }
	@Test
	public void testCombineRange1Null() {
		Range2 = new Range(10,12);
		Range combinedRange = Range.combine(Range1, Range2);
	}
	@Test
	public void testCombineRange2Null() {
		Range1 = new Range(10,12);
		Range combinedRange = Range.combine(Range1, Range2);
	}
	@Test
	public void testConstructorLowerRange() {
		try {
			Range1 = new Range(4,2);
		}
		catch(Exception exc) {
			assertNotNull(exc);
		}
	}
	@Test
	public void testCombineIgnoringNAN() {
		Range1 = new Range(5,8);
    	Range2 = new Range(10,12);
    	Range combinedRange = Range.combineIgnoringNaN(Range1, Range2);
        assertEquals("The lower bound should be 5",
        5, combinedRange.getLowerBound(), .000000001d);
        assertEquals("The upper bound should be 12",
                12, combinedRange.getUpperBound(), .000000001d);
	}
	@Test
	public void testCombineIgnoringNANRange1Null() {
    	Range2 = new Range(10,12);
    	Range combinedRange = Range.combineIgnoringNaN(Range1, Range2);
        assertEquals("The lower bound should be 10",
        10, combinedRange.getLowerBound(), .000000001d);
        assertEquals("The upper bound should be 12",
                12, combinedRange.getUpperBound(), .000000001d);
	}
	@Test
	public void testCombineIgnoringNANRange2Null() {
    	Range1 = new Range(10,12);
    	Range combinedRange = Range.combineIgnoringNaN(Range1, Range2);
        assertEquals("The lower bound should be 10",
        10, combinedRange.getLowerBound(), .000000001d);
        assertEquals("The upper bound should be 12",
                12, combinedRange.getUpperBound(), .000000001d);
	}
	@Test public void testCombineIgnoringNANNull() {
		Range1 = new Range(Double.NaN,Double.NaN);
		Range2 = new Range(Double.NaN,Double.NaN);
		Range combinedRange = Range.combineIgnoringNaN(Range1, Range2);
		assertEquals(combinedRange,null);
		
	}
	@Test public void testCombineIgnoringNANRange1NullRange2NAN() {
		Range2 = new Range(Double.NaN,Double.NaN);
		Range combinedRange = Range.combineIgnoringNaN(Range1, Range2);
		assertEquals(combinedRange,null);
	}
	@Test public void testCombineIgnoringNANRange2NullRange1NAN() {
		Range1 = new Range(Double.NaN,Double.NaN);
		Range combinedRange = Range.combineIgnoringNaN(Range1, Range2);
		assertEquals(combinedRange,null);
	}
	@Test
	public void testExpand() {
		Range1 = new Range(2,3);
		Range expandedRange = Range.expand(Range1, 4, 12);
		assertEquals("The lower bound should be -2", -2, expandedRange.getLowerBound(), .000000001d);
		assertEquals("The upper bound should be 15", 15, expandedRange.getUpperBound(), .000000001d);
	}
	@Test
	public void testExpandToInclude() {
		Range1 = new Range(2,7);
		Range expandToIncludeRange = Range.expandToInclude(Range1, 8);
		assertEquals("upper bound should be 8", 8, expandToIncludeRange.getUpperBound(), 000000001d);
	}
	@Test
	public void testScale() {
		Range1 = new Range(2,4);
		Range1 = Range.scale(Range1, 2);
		assertEquals("Upper bound should be 8", 8, Range1.getUpperBound(), 000000001d);
		assertEquals("The lower bound should be 4", 4, Range1.getLowerBound(), .000000001d);
	}
	@Test
	public void testShift() {
		Range1 = new Range(2,4);
		Range1 = Range.shift(Range1, 2);
		assertEquals("Upper bound should be 6", 6, Range1.getUpperBound(), 000000001d);
		assertEquals("Lower bound should be 4", 4, Range1.getLowerBound(), 000000001d);
	}
	@Test
	public void testIntersects() {
		Range1 = new Range(2,4);
		Range2 = new Range(2,4);
		assertEquals(Range1.intersects(2,4), true);
	}
	@Test
	public void testIntersectsWithRange() {
		Range1 = new Range(2,4);
		Range2 = new Range(2,4);
		assertEquals(Range1.intersects(Range2), true);
	}
	@Test 
	public void testDoesNotIntersect() {
		Range1 = new Range(2,4);
		assertEquals(Range1.intersects(5,7), false);
	}
	@Test
	public void testLowerBoundBranch() {
		Range1 = new Range(2,4);
		assertEquals(Range1.intersects(3,4), true);
	}
	@Test
	public void testToString() {
		Range1 = new Range(2,4);
		String a = Range1.toString();
		String result = "Range[2.0,4.0]";
		System.out.println(result);
		assertEquals(result, a);
	}
	@Test
    public void testConstrain() {
        Range1 = new Range(-5, 15);
        double value = Range1.constrain(6);
        assertEquals(6, value, .000000001d);
    }
	@Test
    public void testEquals() {
        Range1 = new Range(-5, 15);
        Range2 = new Range(-5, 15);
        boolean isEqual = Range1.equals(Range2);
        assertTrue(isEqual);
    }
	@Test
    public void testIsNaNRange() {
        Range1 = new Range(-5, 15);
        boolean value = Range1.isNaNRange();
        assertFalse(value);
    }
	@Test
    public void testHasCode() {
        Range1 = new Range(-5, 15);
        int value = Range1.hashCode();
        System.out.println(value);
        assertEquals(41025536, value, .000000001d);
	}
	 
    @Test
    public void testContainsPositiveNumber () {
    	// This test is testing partition of positive numbers within the range
        boolean res = range.contains(5.0);
        assertTrue (res);
    }

    @Test
    public void testContainsNegativeNumber () {
    	// This test is testing partition of negative numbers within the range
    	boolean res = range.contains(-1);
    	assertTrue (res);
    }
    
    @Test
    public void testContainsPositiveValueOutsideTheRange () {
    	// This test is testing partition of positive numbers out of range
    	boolean res = range.contains(100);
    	assertFalse (res);
    }
    
    @Test
    public void testContainsNegativeValueOutsideTheRange () {
    	// This test is testing partition of negative numbers out of range
        boolean res = range.contains(-100);
        assertFalse (res);
    }
    
    @Test
    public void testContainsValueUpperBound () {
    	// This test is testing partition of numbers at the upper bound
    	boolean res = range.contains(15);
    	assertTrue (res);
    }
    
    @Test
    public void testContainsValueLowerBound () {
    	// This test is testing partition of numbers at the lower bound
        boolean res = range.contains(-5);
        assertTrue (res);
    }
    
    @Test
    public void testCentralValuesPositive() {
    	//This test is testing partition of (positive, positive)
    	
    	exampleRange = new Range(2,8);
    	System.out.println( exampleRange.getCentralValue());
        assertEquals("The central value of 2 and 8 should be 5",
        5, exampleRange.getCentralValue(), .000000001d);
    }
    @Test
    public void testCentralValuesNegative() {
    	//This test is testing partition of (negative, negative)
    	exampleRange = new Range(-8,-2);
    	System.out.println( exampleRange.getCentralValue());
        assertEquals("The central value of -2 and -8 should be -5",
        -5, exampleRange.getCentralValue(), .000000001d);
    }
    @Test
    public void testCentralValuesZeros() {
    	//This test is testing partition of (0, 0)
    	exampleRange = new Range(0,0);
    	System.out.println( exampleRange.getCentralValue());
        assertEquals("The central value of 0 and 0 should be 0",
        0, exampleRange.getCentralValue(), .000000001d);
    }
    @Test
    public void testCentralValuesEvens() {
    	//This test is testing partition of (even, even)
    	exampleRange = new Range(-6,-4);
    	System.out.println( exampleRange.getCentralValue());
        assertEquals("The central value of -4 and -6 should be -5",
        -5, exampleRange.getCentralValue(), 0);
    }
    @Test
    public void testCentralValuesSpreadPositive() {
    	//This test is testing partition of (positive even , positive odd)
    	exampleRange = new Range(2,7);
    	System.out.println( exampleRange.getCentralValue());
        assertEquals("The central value of 2 and 7 should be 4.5",
        4.5, exampleRange.getCentralValue(), .000000001d);
    }
    @Test
    public void testCentralValuesSpreadNegative() {
    	//This test is testing partition of (negative odd, negative even)
    	exampleRange = new Range(-7,-2);
    	System.out.println( exampleRange.getCentralValue());
        assertEquals("The central value of -2 and -7 should be -4.5",
        -4.5, exampleRange.getCentralValue(), .000000001d);
    }
    @Test
    public void testCentralValuesSpread() {
    	//This test is testing partition of (negative even, positive odd)
    	exampleRange = new Range(-2,7);
    	System.out.println( exampleRange.getCentralValue());
        assertEquals("The central value of -2 and 7 should be 2.5",
        2.5, exampleRange.getCentralValue(), .000000001d);
    }
    
    /*
     * This test is for method getLength() in class Range
     * This test tests the partition in which the Range object has both bounds as negative numbers.
     */
    @Test
    public void testNegativeBounds() {
    	assertEquals("The length should be 4", 4, twoNegativeBounds.getLength(), 0);
    }
    /*
     * This test is for method getLength() in class Range
     * This test tests the partition in which the Range object has a positive upper bound and a negative lower bound
     */
    @Test
    public void testPositiveUpperBoundNegativeLowerBound() {
    	assertEquals("The length should be 13", 13, positiveUpperBoundNegativeLowerBound.getLength(), 0);
    }
    /*
     * This test is for method getLength() in class Range
     * This test tests the partition in which the Range object has both bounds as positive numbers.
     */
    @Test
    public void testbothBoundsPositive() {
    	assertEquals("The length should be 10", 10, bothBoundsPositive.getLength(), 0);
    }
    /*
     * This test is for method getLength() in class Range
     * This test tests the partition in which the Range object has both bounds with decimal numbers.
     */
    @Test
    public void testbothBoundsHaveDecimals() {
    	assertEquals("The length should be 9.7", 9.7, bothBoundsHaveDecimals.getLength(), 0.1d);
    }
    /* 
     * This test is for method getLength() in class Range
     * This test tests the partition in which the Range object has both bounds as zero.
     */
    @Test
    public void testBothBoundsZero() {
    	assertEquals("The length should be 0", 0, bothBoundsZero.getLength(), 0);
    }
    
    @Test
    public void testLowerBoundZero() {
        assertEquals("The lower bound value of the range should be 0", 0, LowerBoundZero.getLowerBound(), 0);
    }
    
    /* 
     * This test covers the positive values for the lower bound with an arbitrary upper bound.
     */
    @Test
    public void testLowerBoundPositive() {
        assertEquals("The lower bound value of the range should be 20", 20.1, LowerBoundPositive.getLowerBound(), 0);
    }
    
    /* 
     * This test covers the negative values for the lower bound with an arbitrary upper bound.
     */
    @Test
    public void testLowerBoundNegative() {
        assertEquals("The lower bound value of the range should be -1", -1, LowerBoundNegative.getLowerBound(), 0);
    }
    
    /* 
     * This test covers char values for the lower bound with an arbitrary upper bound.
     */
    @Test
    public void testLowerBoundChar() {
        assertEquals("This test should fail because chars are not numbers, but passes because of char to int conversion", 'b', LowerBoundChar.getLowerBound(), 0);
    }
    
    /* 
     * This test covers the minimum value for the lower bound with an arbitrary upper bound.
     * It tests if the values around the boundary work properly using two unequal numbers and should fail in this case.
     */
    @Test
    public void testLowerBoundMin() {
        assertEquals("The lower bound value of the range should be the min value for a double", 5.0E-324, LowerBoundMin.getLowerBound(), 0);
    }
    
    /* 
     * This test covers below the minimum value for the lower bound with an arbitrary upper bound.
     * It tests for values below the minimum value (negative infinity) and compares them to numbers above the minimum value.
     * The test is designed to fail since negative infinity does not equal a number above the minimum value of a double.
     */
    @Test
    public void testLowerBoundUnderMin() {
    	assertEquals("The lower bound value of the range should be beyond the max value of a double"
    			, 4.8E-324, LowerBoundUnderMin.getLowerBound(), 0);
    }
    
    /* 
     * This test covers the upper bound value of 0 with an arbitrary lower bound.
     */
    @Test
    public void testUpperBoundZero() {
        assertEquals("The upper bound value of the range should be 0", 0, UpperBoundZero.getUpperBound(), 0);
    }
    
    /* 
     * This test covers the positive values for the upper bound with an arbitrary lower bound.
     */
    @Test
    public void testUpperBoundPositive() {
        assertEquals("The upper bound value of the range should be 20", 20.1, UpperBoundPositive.getUpperBound(), 0);
    }
    
    /* 
     * This test covers the negative values for the upper bound with an arbitrary lower bound.
     */
    @Test
    public void testUpperBoundNegative() {
        assertEquals("The upper bound value of the range should be -1", -1, UpperBoundNegative.getUpperBound(), 0);
    }
    
    /* 
     * This test covers char values for the upper bound with an arbitrary lower bound.
     */
    @Test
    public void testUpperBoundChar() {
        assertEquals("This test should fail because chars are not numbers, but passes because of char to int conversion", 'a', UpperBoundChar.getUpperBound(), 0);
    }
    
    /* 
     * This test covers the maximum value for the upper bound with an arbitrary lower bound.
     * It tests if the values around the boundary work properly using two unequal numbers and should fail in this case.
     */
    @Test
    public void testUpperBoundMax() {
        assertEquals("The upper bound value of the range should not be the max value for a double", Double.MAX_VALUE - 1000000000000000000000.0, UpperBoundMax.getUpperBound(), 0);
    }
    
    /* 
     * This test covers beyond the maximum value for the upper bound with an arbitrary lower bound.
     * It tests for values above the maximum value (infinity) and compares them to numbers below the maximum value.
     * The test is designed to fail since infinity does not equal a number below the max value of a double.
     */
    @Test
    public void testUpperBoundOverMax() {
    	assertEquals("The upper bound value of the range should be beyond the max value of a double"
    			, Double.MAX_VALUE - 10, UpperBoundOverMax.getUpperBound(), 0);
    }
    
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}