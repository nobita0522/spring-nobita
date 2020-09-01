package tech.chenx;

import java.util.Collection;
import java.util.Objects;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/8/31 17:56
 * @description this is description about this file...
 */
public class CollectionUtil {

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }
}
