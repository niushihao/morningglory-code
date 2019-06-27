package com.morningglory.basic.collection;

import com.google.common.collect.Lists;
import com.morningglory.basic.pojo.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;

/**
 * @Author: nsh
 * @Date: 2018/5/28
 * @Description:
 */
public class ListTest {


    /**
     * 测试 toArray() 返回 Object 数组
     *     toArray(T[] t) 返回指定类型的数组
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(String.class instanceof Object);
        String a ="";
        System.out.println(a instanceof Object);
        System.out.println(String.class.isInstance(Object.class));
        System.out.println(String.class.isAssignableFrom(Object.class));


        //toArrayTest();

        //cloneTest();

        //clearTest();

        iteratorTest();

        //linkListAddTest();

        //subSet();

        //subList();

        //toMap1();

        //matchTest();

        //testReference();

        //sort();
        //User u = new User(1);
        //System.out.println(u.hashCode());
        //tt(u);
        //System.out.println(u.getName());

        //change();

        //removeTest();
    }

    private static void removeTest() {

        List<Integer> list1 = Lists.newArrayList(1,2,3,4);

        List<Integer> list2 = Lists.newArrayList(4,6,7,8);

        //list1.
        //交集
        list1.retainAll(list2);
        System.out.println("retainAll list1="+list1);
        System.out.println("retainAlllist2 ="+list2);

        list1 = Lists.newArrayList(1,2,3,4);
        list2 = Collections.EMPTY_LIST;
        //Lists.newArrayList(4,6,7,8);
        //差集
        list1.removeAll(list2);
        System.out.println("removeAll list1 ="+list1);
        System.out.println("removeAll list2 ="+list2);
    }

    // 这样不会改变list的值
    private static void change() {

        List<String> list = Lists.newArrayList("1","2","3","4");
        for(String str : list){
            if("2".equals(str)){
                str = "10";
            }
        }

        System.out.println("change list :" +list);
    }


    private static void testReference() {

        User user = new User(1);
        List<User> list1 = Lists.newArrayList();
        list1.add(user);

        List<User> list2 = Lists.newArrayList(list1);


        list1.forEach(u -> {
            u.setId(2);
            System.out.println(u.getId());
        });
        list2.forEach(u -> System.out.println(u.getId()));
    }

    private static void tt(User u) {
        u.setName("111");
        System.out.println(u.hashCode());
    }

    private static void sort() {
        List<User> list = Lists.newArrayList();
        for(int i=0;i<5;i++){
            User user = new User(i*i);
            list.add(user);
        }

        list.sort((x,y) -> y.getId().compareTo(x.getId()));

        List<User> subList = list.stream().filter(x -> x.getId() < 3).collect(Collectors.toList());
        subList.forEach(x -> {
            x.setName("test");
        });

        change(list);

        list.forEach(u -> System.out.println(u.getName()));

    }

    private static void change(List<User> list) {

        list.forEach(u -> {
            u.setName("x");
        });
    }

    private static void matchTest() {

        List<Long> list = Lists.newArrayList(1L,2L,3L,4L);

        boolean anyMatch = list.stream().anyMatch(i -> i.equals(1L));
        boolean allMatch = list.stream().allMatch(i -> i.equals(1L));
        boolean noneMatch = list.stream().noneMatch(i -> i.equals(1L));
        System.out.println("anyMatch ="+anyMatch+",allMatch ="+allMatch+",noneMatch ="+noneMatch);
    }

    private static void toMap1() {

        List<User> list = Lists.newArrayList();
        for(int i=0;i<5;i++){
            User user = new User(i*i);
            list.add(user);
        }
        list.add(new User(1));
        Map<Integer, User> collect = list.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        System.out.println(collect.size());
        System.out.println(collect);

    }

    private static void subSet() {
        int[] nums = new int[]{1, 2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        ArrayList<ArrayList<Integer>> res = subsets(nums);
        for(int i = 0; i < res.size(); i ++){
            System.out.println(res.get(i));
        }
    }

    private static ArrayList<ArrayList<Integer>> subsets(int[] nums) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> item = new ArrayList<Integer>();
        if(nums.length == 0 || nums == null)
            return res;
        Arrays.sort(nums); //排序
        dfs(nums, 0, item, res);  //递归调用
        //res.add(new ArrayList<Integer>());  //最后加上一个空集
        return res;
    }

    private static void dfs(int[] nums, int start, ArrayList<Integer> item, ArrayList<ArrayList<Integer>> res) {
        for(int i = start; i < nums.length; i ++){
            item.add(nums[i]);
            if(item.size() <3){
                continue;
            }
            //item是以整数为元素的动态数组，而res是以数组为元素的数组，在这一步，当item增加完元素后，item所有元素构成一个完整的子串，再由res纳入
            res.add(new ArrayList<Integer>(item));
            dfs(nums, i + 1, item, res);
            item.remove(item.size() - 1);
        }
    }

    private static void linkListAddTest() {
        List list = new LinkedList();
        list.add(null);
        list.add(null);
        System.out.println("list="+list);
        System.out.println(list.get(0));
    }

    /**
     * 如果将list.iterator()放在list.add前边，遍历时会报错
     * 如果遍过程中使用list.remove方法也会报错
     * 如果使用iterator.remove 不会报错，因为执行后会同步modCount的值
     */
    private static void iteratorTest() {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            Integer integer = iterator.next();

            if(integer == 1){
                iterator.remove();
            }
        }

    }

    /**
     * 测试clear方法
     */
    private static void clearTest() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        //list.clear();
        System.out.println("list :"+list);

        list.subList(1,2).clear();
        System.out.println("list :"+list);

    }

    /**
     * 测试克隆方法 浅克隆
     */
    private static void cloneTest() {

        ArrayList list = new ArrayList();

        User u1 = new User("");
        u1.setId(1);
        list.add(u1);

        User u2 = new User("");
        u1.setId(2);
        list.add(u2);

        Object clone = list.clone();

        //修改u2的值
        u2.setId(3);

        System.out.println("list ="+list);
        System.out.println("cloneList ="+clone);


    }

    /**
     * toArrayTest的两个方法
     */
    private static void toArrayTest() {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.println("list ="+list);
        Object[] objects = list.toArray();

        Integer[] integers = list.toArray(new Integer[list.size()]);
        System.out.println(integers);

        list.stream().forEach(c ->{
            if(c != 2){
                System.out.println("haha ");
            }else{
                throw new NullPointerException();
            }
        });



    }

    private static void subList(){
        List<Integer> list = Lists.newArrayList(1,2,3,4,5);

        List<List<Integer>> partition = Lists.partition(list, 100);
        System.out.println(partition.size());
    }




}