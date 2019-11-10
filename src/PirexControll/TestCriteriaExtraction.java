package PirexControll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestCriteriaExtraction {
	
	@Test
	void testInvalidString() {
		try {
			CriteriaExtraction extracted = new CriteriaExtraction("");
			fail("passed invalid string test");
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "String must contain at least 1 char");
		}
	}
	
	@Test
	void testNullString() {
		try {
			CriteriaExtraction extracted = new CriteriaExtraction(null);
			fail(extracted.getCantHaveString());
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "String must contain at least 1 char");
		}
	}
	
	@Test
	void testRemoveWhiteSpace() {
		String str = "test  this   time    verify            single space";
		CriteriaExtraction extracted = new CriteriaExtraction("test");
		
		assertEquals(extracted.removeExtraSpace(str), "test this time verify single space");
	}
	
	@Test
	void testSimpleTokes() {
		String str = "some text test"; // to is not allowed as criteria
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.getAdjancyString(), str);
	}
	
	@Test
	void testAdjancyCarrot() {
		String str = "some^text^to^test";
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		
		assertEquals(extracted.getAdjancyString(), "some text to test");
	}
	
	@Test
	void testCantHave() {
		StringBuilder 	sb 	= new StringBuilder("a an and are but did do does for had has ");
		
		sb.append("is it its of or that the this to were which with");
		
		CriteriaExtraction extracted = new CriteriaExtraction("test");

		String test = extracted.getCantHaveString(); 
		
		assertEquals(test, sb.toString());
	}
	
	@Test
	void testRemoveOmitToken() {
		String str = "good string will emerge from list provided";
		StringBuilder 	sb 	= new StringBuilder("a  good an string and will are emerge but from ");
		
		sb.append("did list do provided does for had has is it its of or that the this to were which with ");

		CriteriaExtraction extracted = new CriteriaExtraction(sb.toString());
		System.out.println(str);
		System.out.println(extracted.getAdjancyString());
		
		assertEquals(extracted.getAdjancyString(), str);
	}
	
	@Test
	void testOmitToken() {
		String str = "good string will be ~differnet changed";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		
		assertEquals(extracted.getAdjancyString(), "good string will be changed");
	}
	
	@Test
	void testOmitFirst() {
		String str = "~differnet changed";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		
		assertEquals(extracted.getAdjancyString(), "changed");
	}
	
	@Test
	void testReturnOmitString() {

		String str = "good will provided";
		StringBuilder 	sb 	= new StringBuilder("~good string ~will emerge from list ~provided");
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(sb.toString());
		
		assertEquals(extracted.getOmitString(), str);
	}

	@Test
	void testRemovePeriod1() {

		String str = "...good will provided";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		assertEquals(extracted.getAdjancyString(), "good will provided");
	}
	
	
	@Test
	void testRemovePeriod2() {

		String str = "go...od will provided";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		
		assertEquals(extracted.getAdjancyString(), "good will provided");
	}
	@Test
	void testRemovePeriod3() {

		String str = "good... will provided";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		
		assertEquals(extracted.getAdjancyString(), "good will provided");
	}
	@Test
	void testRemovePeriod4() {

		String str = "good will ...provided...";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.getAdjancyString(), "good will provided");
	}
	@Test
	void testRemovePeriod5() {

		String str = "good will 123.456";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		assertEquals(extracted.getAdjancyString(), "good will 123.456");
	}
	@Test
	void testRemovePeriod6() {

		String str = "good will ..12";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.getAdjancyString(), "good will 12");
	}
	@Test
	void testRemovePeriod7() {

		String str = "good will 56...12";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.getAdjancyString(), "good will 5612");
	}
	@Test
	void testRemovePeriod8() {

		String str = "good will 12...";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.getAdjancyString(), "good will 12");
	}
	@Test
	void testRemoveSingle1() {

		String str = "'good will prevail";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		assertEquals(extracted.getAdjancyString(), "good will prevail");
	}
	@Test
	void testRemoveSingle2() {

		String str = "good will prevail''";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		assertEquals(extracted.getAdjancyString(), "good will prevail");
	}
	@Test
	void testRemoveSingle3() {

		String str = "good will prevail'";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.getAdjancyString(), "good will prevail");
	}
	@Test
	void testRemoveSingle4() {

		String str = "bad can't prevail";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		System.out.println(extracted.getAdjancyString());
		assertEquals(extracted.getAdjancyString(), "bad can't prevail");
	}

	@Test
	void testRemoveSingle5() {

		String str = "'good 'can't' prevail'";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.modToken(str), "good can't prevail");
		System.out.println(extracted.modToken(str));
	}

	@Test
	void testRemoveSingle6() {

		String str = "'good'";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.modToken(str), "good");
		System.out.println(extracted.modToken(str));
	}

	@Test
	void testRemoveSingle7() {

		String str = "\"good\"";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);

		assertEquals(extracted.modToken(str), "good");
		System.out.println("remove double quotes" + extracted.modToken(str));
	}
	@Test
	void testIsNumber() {
		String str = "good 'will' prevail";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		for(int i = 0; i < 256; i++) {
			char ch = (char) i;
			boolean isDig = i > 47 && i < 58;
			assertEquals(isDig, extracted.isNumber(ch));
		}
	}

	@Test
	void testIsLeter() {
		String str = "good 'will' prevail";
		
		
		CriteriaExtraction extracted = new CriteriaExtraction(str);
		for(int i = 1; i < 256; i++) {
			char ch = (char) i;
			boolean isLet = (i > 64 && i < 91) || (i > 96 && i < 123);
			boolean fromCode = extracted.isLetter(ch);

			assertEquals(isLet, fromCode);
		}
	}
	
}
