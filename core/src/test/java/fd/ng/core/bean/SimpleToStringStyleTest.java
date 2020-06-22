package fd.ng.core.bean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class SimpleToStringStyleTest {
	private final Integer base = Integer.valueOf(5);

	@Before
	public void setUp() throws Exception {
		ToStringBuilder.setDefaultStyle(ToStringStyle.SIMPLE_STYLE);
	}

	@After
	public void tearDown() throws Exception {
		ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
	}

	//----------------------------------------------------------------

	@Test
	public void testBlank() {
		assertEquals("", new ToStringBuilder(base).toString());
	}

	@Test
	public void testAppendSuper() {
		assertEquals("", new ToStringBuilder(base).appendSuper("").toString());
		assertEquals("<null>", new ToStringBuilder(base).appendSuper("<null>").toString());

		assertEquals("'hello'", new ToStringBuilder(base).appendSuper("").append("a", "hello").toString());
		assertEquals("<null>,'hello'", new ToStringBuilder(base).appendSuper("<null>").append("a", "hello").toString());
		assertEquals("'hello'", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
	}

	@Test
	public void testObject() {
		final Integer i3 = Integer.valueOf(3);
		final Integer i4 = Integer.valueOf(4);
		assertEquals("<null>", new ToStringBuilder(base).append((Object) null).toString());
		assertEquals("3", new ToStringBuilder(base).append(i3).toString());
		assertEquals("<null>", new ToStringBuilder(base).append("a", (Object) null).toString());
		assertEquals("3", new ToStringBuilder(base).append("a", i3).toString());
		assertEquals("3,4", new ToStringBuilder(base).append("a", i3).append("b", i4).toString());
		assertEquals("<Integer>", new ToStringBuilder(base).append("a", i3, false).toString());
		assertEquals("<size=0>", new ToStringBuilder(base).append("a", new ArrayList<>(), false).toString());
		assertEquals("[]", new ToStringBuilder(base).append("a", new ArrayList<>(), true).toString());
		assertEquals("<size=0>", new ToStringBuilder(base).append("a", new HashMap<>(), false).toString());
		assertEquals("{}", new ToStringBuilder(base).append("a", new HashMap<>(), true).toString());
		assertEquals("<size=0>", new ToStringBuilder(base).append("a", (Object) new String[0], false).toString());
		assertEquals("{}", new ToStringBuilder(base).append("a", (Object) new String[0], true).toString());
	}

	@Test
	public void testPerson() {
		final ToStringStyleTest.Person p = new ToStringStyleTest.Person();
		p.name = "Jane Q. Public";
		p.age = 47;
		p.smoker = false;
		assertEquals("'Jane Q. Public',47,false", new ToStringBuilder(p).append("name", p.name).append("age", p.age).append("smoker", p.smoker).toString());
	}

	@Test
	public void testLong() {
		assertEquals("3", new ToStringBuilder(base).append(3L).toString());
		assertEquals("3", new ToStringBuilder(base).append("a", 3L).toString());
		assertEquals("3,4", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString());
	}

	@Test
	public void testObjectArray() {
		Object[] array = new Object[] {null, base, new int[] {3, 6}};
		assertEquals("{<null>,5,{3,6}}", new ToStringBuilder(base).append(array).toString());
		assertEquals("{<null>,5,{3,6}}", new ToStringBuilder(base).append((Object) array).toString());
		array = null;
		assertEquals("<null>", new ToStringBuilder(base).append(array).toString());
		assertEquals("<null>", new ToStringBuilder(base).append((Object) array).toString());
	}

	@Test
	public void testLongArray() {
		long[] array = new long[] {1, 2, -3, 4};
		assertEquals("{1,2,-3,4}", new ToStringBuilder(base).append(array).toString());
		assertEquals("{1,2,-3,4}", new ToStringBuilder(base).append((Object) array).toString());
		array = null;
		assertEquals("<null>", new ToStringBuilder(base).append(array).toString());
		assertEquals("<null>", new ToStringBuilder(base).append((Object) array).toString());
	}

	@Test
	public void testLongArrayArray() {
		long[][] array = new long[][] {{1, 2}, null, {5}};
		assertEquals("{{1,2},<null>,{5}}", new ToStringBuilder(base).append(array).toString());
		assertEquals("{{1,2},<null>,{5}}", new ToStringBuilder(base).append((Object) array).toString());
		array = null;
		assertEquals("<null>", new ToStringBuilder(base).append(array).toString());
		assertEquals("<null>", new ToStringBuilder(base).append((Object) array).toString());
	}
}
