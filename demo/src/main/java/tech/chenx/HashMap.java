package tech.chenx;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author nobita chen
 * @description
 * @email nobitachenxiong@didiglobal.com
 * @date 2021/5/13 21:58
 */
public class HashMap {

    public static void main(String[] args) {

    }

    @Data
    @AllArgsConstructor
    public static class KeyValue{
        private Integer key;

        private Integer value;
    }

    public int foo(List<LinkedList<KeyValue>> hashMap,Integer key,Integer value) {
        if (hashMap.get(key.hashCode()) == null) {
            LinkedList<KeyValue> keyValues = Lists.newLinkedList();
            keyValues.add(new KeyValue(key,value));
            hashMap.add(key.hashCode(),keyValues);
            return 0;
        } else {
            LinkedList<KeyValue> keyValues = hashMap.get(key.hashCode());
            for (KeyValue keyValue : keyValues) {
                if (keyValue.getKey().equals(key)) {
                    return 1;
                }
            }
            return 2;
        }
    }
}
