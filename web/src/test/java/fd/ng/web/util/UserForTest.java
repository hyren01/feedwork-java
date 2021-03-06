package fd.ng.web.util;

import fd.ng.db.entity.TableEntity;
import fd.ng.db.entity.anno.Column;
import fd.ng.db.entity.anno.Table;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Table(tableName = DboTest.testTableName)
public class UserForTest extends TableEntity {
	private static final long serialVersionUID = 2660711228606794306L;
	private String name;
	private int age;
	private String password;
	private String create_time;
	@Column("class")
	private String uclass; // 测试对字段注解用

	private transient static final Set<String> __PrimaryKeys;
	static {
		Set<String> tmpPrimaryKeys = new HashSet<>();
		tmpPrimaryKeys.add("name");
		__PrimaryKeys = Collections.unmodifiableSet(tmpPrimaryKeys);
	}

	public UserForTest() {

	}

	public String getUclass() {
		return uclass;
	}

	public void setUclass(String uclass) {
		this.uclass = uclass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", UserForTest.class.getSimpleName() + "[", "]")
				.add("name='" + name + "'")
				.add("age=" + age)
				.add("password='" + password + "'")
				.add("create_time='" + create_time + "'")
				.toString();
	}
}
