package com.example.demo;

import com.example.demo.domain.User;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListAndSet {
    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        List<User> userList2 = new ArrayList<>();
        HashSet<User> userSet = new HashSet<>();
        HashSet<User> userSet2 = new HashSet<>();

        for (long i = 0; i < 1000000; i++) {
            userList.add(new User(i, i + "first", i + "last"));
        }
        for (long i = 0; i < 500000; i++) {
            userList2.add(new User(i, i + "first", i + "last"));
        }
        for (long i = 0; i < 1000000; i++) {
            userSet.add(new User(i, i + "first", i + "last"));
        }
        for (long i = 0; i < 500000; i++) {
            userSet2.add(new User(i, i + "first", i + "last"));
        }
        // new User(); 重写hashCode 方法 。定位User的类型
        // todo 基本上list<->list 和 set <->set , 不会太误解
        // todo list<->collection  看collection 的contains
        // todo  set<->collection 看 set 和collection 的size大小， 来确定是用set 的equals, 或者collection 的equals
        // todo 相应的用list的contains 就比set的慢好多。
        // list-list  补集 0  直接 equals，若为true, 则删除所有原元素，遍历方式是顺序遍历
//        userList.removeAll(userList2);
//        System.out.println(userList);
        // set-set 0 若id相等则相等， 先hash
//        userSet.removeAll(userSet2);
//        System.out.println(userSet);
        //  list-set ?  先hash 后equals  剩余5
//        userList.removeAll(userSet2);
//        System.out.println(userList);
//      根据hashCode set删除元素  set-list ?   先hash equals  0 -> 大集合-小集合   取大集合的类型参数，若为list则需要先equals，然后是hashcode
//        userSet.removeAll(userList2);
//        System.out.println(userSet);
        // 小集合-大集合  根据大集合的contains方法
        userSet2.removeAll(userList2);
        System.out.println(userSet);
//        userSet.remo
        // 综上  只有list-list 是先equals ，后hash 数组方式，会导致  大集合-小集合 顺序判断大集合是否相等，


    }
}
