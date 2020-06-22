package fd.ng.core.conf;

import fd.ng.core.yaml.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AppinfoConf {
	public static final String AppBasePackage;
	public static final boolean HasDatabase;
	public static final boolean LoggedExceptionRaw;   // 是否把 RawlayerRuntimeException 异常堆栈自动写入日志
	public static final boolean LoggedExceptionFrame; // 是否把 FrameworkRuntimeException 异常堆栈自动写入日志
	public static final String ProjectId; // 每个需要部署的项目的唯一编号
	public static final Map<String, String> PropertiesMap; // 自定义的任意配置参数
	static {
		YamlMap rootConfig = YamlFactory.load(ConfFileLoader.getConfFile("appinfo")).asMap();

		AppBasePackage = rootConfig.getString("basePackage", "fdapp");
		HasDatabase = rootConfig.getBool("hasDatabase", true);
		LoggedExceptionRaw = rootConfig.getBool("loggedException.Raw", true);
		LoggedExceptionFrame = rootConfig.getBool("loggedException.Frame", true);
		ProjectId = rootConfig.getString("projectId","");

		Map<String, String> tmpPropMap = new HashMap<>();
		YamlMapAnywhere propMap = (YamlMapAnywhere)rootConfig.getMap("properties");
		if(propMap!=null) {
			for (YamlNode node : propMap.keys()) {
				String key = node.toString().trim();
				tmpPropMap.put(node.toString(), propMap.getString(key));
			}
		}
		PropertiesMap = Collections.unmodifiableMap(tmpPropMap);
	}
}
