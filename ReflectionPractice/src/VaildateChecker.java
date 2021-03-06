import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class VaildateChecker {
	final private Class request;
	private Map<String, Method> map;

	public VaildateChecker(Class request) {
		this.request = request;
	}

	public boolean checkParam(Object request, String... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		createMap();

		for (String name : args) {
			Method method = map.get(name);

			if (method == null || method.invoke(request) == null) {
				return false;
			}
		}

		return true;
	}

	private void createMap() {
		if (map == null) {
			synchronized (this) {
				if (map != null) {
					this.map = new HashMap<>();

					for (Method method : request.getMethods()) {
						String methodName = method.getName();

						if (methodName.startsWith("get")) {
							String key = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
							map.put(key, method);
						}
					}

					/* 위의 로직을 스트림으로 대체 가능 */
					map = Stream.of(request.getMethods()).filter(each -> each.getName().startsWith("get"))
							.collect(toMap(each -> {
								return each.getName().substring(0, 1).toLowerCase() + each.getName().substring(1);
							}, each -> each));

				}
			}
		}
	}

}