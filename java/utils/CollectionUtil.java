import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollectionUtil {
	private static final String JOIN_DELIMITER = "|";
	
	public static final boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
	public static final <TV> List<TV> subList(List<TV> list, int start, int end) {
		return list != null && !list.isEmpty() ? list.subList(start, Math.min(end, list.size())) : Collections.emptyList();
	}
	@SafeVarargs
	public static final <TV> List<TV> removeByCondition(List<TV> list, List<TV> refList, Function<TV, String>... functions) {
		return removeByCondition(list, refList, Arrays.asList(functions));
	}
	private static final <TV> List<TV> removeByCondition(List<TV> list, List<TV> refList, List<Function<TV, String>> functionList) {
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		if (refList == null || refList.isEmpty()) {
			return new ArrayList<>(list);
		}
		Function<TV, String> functions = item -> functionList.stream().map(function -> function.apply(item)).collect(Collectors.joining(JOIN_DELIMITER));
		Set<String> refKey = new HashSet<>((int) Math.ceil(refList.size() / 0.75));
		for (TV ref : refList) {
			refKey.add(functions.apply(ref));
		}
		List<TV> resultList = new ArrayList<>(Math.max(1, list.size() - refList.size()));
		for (TV item : list) {
			if (!refKey.contains(functions.apply(item))) {
				resultList.add(item);
			}
		}
		return resultList;
	}
	public static final <T, V> List<V> parse(List<T> list, Function<T, V> function) {
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		List<V> resultList = new ArrayList<>(list.size());
		for (T item : list) {
			resultList.add(parse(item, function));
		}
		return resultList;
	}
	public static final <T, V> V parse(T item, Function<T, V> function) {
		try {
			return function.apply(item);
		} catch (Exception e) {
			log.error("Exception in parsing [parse] data.", e);
		}
		return null;
	}
}