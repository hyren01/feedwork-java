package fd.ng.core.bean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;


public class StandardToStringStyleTest {
	private final Integer base = Integer.valueOf(5);
	private final String baseStr = "Integer";

	private static final StandardToStringStyle STYLE = new StandardToStringStyle();

	static {
		STYLE.setUseShortClassName(true);
		STYLE.setUseIdentityHashCode(false);
		STYLE.setArrayStart("[");
		STYLE.setArraySeparator(", ");
		STYLE.setArrayEnd("]");
		STYLE.setNullText("%NULL%");
		STYLE.setSizeStartText("%SIZE=");
		STYLE.setSizeEndText("%");
		STYLE.setSummaryObjectStartText("%");
		STYLE.setSummaryObjectEndText("%");
	}

	@Before
	public void setUp() throws Exception {
		ToStringBuilder.setDefaultStyle(STYLE);
	}

	@After
	public void tearDown() throws Exception {
		ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
	}

	//----------------------------------------------------------------

	@Test
	public void testBlank() {
		assertEquals(baseStr + "[]", new ToStringBuilder(base).toString());
	}

	@Test
	public void testAppendSuper() {
		assertEquals(baseStr + "[]", new ToStringBuilder(base).appendSuper("Integer@8888[]").toString());
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).appendSuper("Integer@8888[%NULL%]").toString());

		assertEquals(baseStr + "[a='hello']", new ToStringBuilder(base).appendSuper("Integer@8888[]").append("a", "hello").toString());
		assertEquals(baseStr + "[%NULL%,a='hello']", new ToStringBuilder(base).appendSuper("Integer@8888[%NULL%]").append("a", "hello").toString());
		assertEquals(baseStr + "[a='hello']", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
	}

	@Test
	public void testObject() {
		final Integer i3 = Integer.valueOf(3);
		final Integer i4 = Integer.valueOf(4);
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append((Object) null).toString());
		assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(i3).toString());
		assertEquals(baseStr + "[a=%NULL%]", new ToStringBuilder(base).append("a", (Object) null).toString());
		assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", i3).toString());
		assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString());
		assertEquals(baseStr + "[a=%Integer%]", new ToStringBuilder(base).append("a", i3, false).toString());
		assertEquals(baseStr + "[a=%SIZE=0%]", new ToStringBuilder(base).append("a", new ArrayList<>(), false).toString());
		assertEquals(baseStr + "[a=[]]", new ToStringBuilder(base).append("a", new ArrayList<>(), true).toString());
		assertEquals(baseStr + "[a=%SIZE=0%]", new ToStringBuilder(base).append("a", new HashMap<>(), false).toString());
		assertEquals(baseStr + "[a={}]", new ToStringBuilder(base).append("a", new HashMap<>(), true).toString());
		assertEquals(baseStr + "[a=%SIZE=0%]", new ToStringBuilder(base).append("a", (Object) new String[0], false).toString());
		assertEquals(baseStr + "[a=[]]", new ToStringBuilder(base).append("a", (Object) new String[0], true).toString());
	}

	@Test
	public void testPerson() {
		final ToStringStyleTest.Person p = new ToStringStyleTest.Person();
		p.name = "Suzy Queue";
		p.age = 19;
		p.smoker = false;
		final String pBaseStr = "ToStringStyleTest.Person";
		assertEquals(pBaseStr + "[name='Suzy Queue',age=19,smoker=false]",
				new ToStringBuilder(p).append("name", p.name).append("age", p.age).append("smoker", p.smoker).toString());
	}

	@Test
	public void testLong() {
		assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3L).toString());
		assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3L).toString());
		assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString());
	}

	@Test
	public void testObjectArray() {
		Object[] array = new Object[] {null, base, new int[] {3, 6}};
		assertEquals(baseStr + "[[%NULL%, 5, [3, 6]]]", new ToStringBuilder(base).append(array).toString());
		assertEquals(baseStr + "[[%NULL%, 5, [3, 6]]]", new ToStringBuilder(base).append((Object) array).toString());
		array = null;
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array).toString());
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append((Object) array).toString());
	}

	@Test
	public void testLongArray() {
		long[] array = new long[] {1, 2, -3, 4};
		assertEquals(baseStr + "[[1, 2, -3, 4]]", new ToStringBuilder(base).append(array).toString());
		assertEquals(baseStr + "[[1, 2, -3, 4]]", new ToStringBuilder(base).append((Object) array).toString());
		array = null;
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array).toString());
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append((Object) array).toString());
	}

	@Test
	public void testLongArrayArray() {
		long[][] array = new long[][] {{1, 2}, null, {5}};
		assertEquals(baseStr + "[[[1, 2], %NULL%, [5]]]", new ToStringBuilder(base).append(array).toString());
		assertEquals(baseStr + "[[[1, 2], %NULL%, [5]]]", new ToStringBuilder(base).append((Object) array).toString());
		array = null;
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array).toString());
		assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append((Object) array).toString());
	}
}
