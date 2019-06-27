package com.morningglory.basic.sort;

import java.util.Random;

/**
 * @Author: qianniu
 * @Date: 2019-04-18 23:54
 * @Desc:
 */
public class SortTest {

    // 冒泡排序
    private static void bubbleSort(int[] arr) {
        int size = arr.length;
        // 外层循环，保证out之后的值 是按照从小到大顺序排列的。
        for (int out = size - 1; out > 1; out --) {
            // 内层循环，将最大的值移动到最后out位置，实现了大数冒泡
            for (int in = 0; in < out; in++) {
                if (arr[in] > arr[in + 1]) {
                    swap(arr, in, in + 1);
                }
            }
        }
    }

    // 选择排序
    private static void selectSort(int[] arr) {
        int size = arr.length;
        // 外层循环，保证out之前的都是有序的
        for(int out = 0; out < size; out ++) {
            int mixIndex = out;
            // 内层循环，找到out位置最小值
            for (int in = out + 1; in < size; in++) {
                if (arr[mixIndex] > arr[in]) {
                    mixIndex = in;
                }
            }
            if (mixIndex != out) {
                swap(arr, mixIndex, out);
            }
        }
    }

    // 插入排序
    private static void insertSort(int[] arr) {
        int size = arr.length;
        //  out之前的是有序的，所以第0位置是有序的，然后将后面的数据插入到这个有序数组对应位置
        for(int out = 1; out < size; out++) {
            // 记录下out位置的值，将它替换至正确的位置
            int temp = arr[out];
            int in = out;
            // arr[in - 1] <= temp 表示temp就应该插入in位置，因为temp比in-1位置的值大，又比in+1位置的值小
            while(in - 1 >= 0 && arr[in - 1] > temp) {
                arr[in] = arr[in - 1];
                in --;
            }
            if (in != out) arr[in] = temp;
        }
    }

    private static void shellSort(int[] arr) {
        int size = arr.length;
        int h = 1;
        while(h <= size / 3) {
            //  这里加1是保证h = (h - 1) / 3最后一个值是1，
            h = h * 3 + 1;
        }
        while (h > 0) {
            for(int out = h; out < size; out++) {
                int temp = arr[out];
                int in = out;
                // arr[in - h] <= temp 表示temp就应该插入in位置，因为temp比in-h位置的值大，又比in+h位置的值小
                while(in - h >= 0 && arr[in - h] > temp) {
                    arr[in] = arr[in - h];
                    in = in - h;
                }
                if (in != out) arr[in] = temp;
            }

            h = (h - 1) / 3;
        }
    }

    private static void headSort(int[] arr) {
        int size = arr.length;
        Head head = new Head(size);
        for (int i = 0; i < size; i++) {
            head.insert(arr[i]);
        }
        for (int i = 0; i < size; i++) {
            //  实现从大到小的排序
            arr[size - 1 - i] = head.remove();
        }
    }

    static class Head{
        private int[] store;
        private int size;

        public Head(int size) {
            this.store = new int[size];
        }

        public void insert(int value) {
            // 第一步将插入的值，直接放在最后一个位置。并将长度加一
            store[size++] = value;
            // 得到新插入值所在位置。
            int index = size - 1;
            while(index > 0) {
                // 它的父节点位置坐标
                int parentIndex = (index - 1) / 2;
                // 如果父节点的值小于子节点的值，你不满足堆的条件，那么就交换值
                if (store[index] > store[parentIndex]) {
                    swap(store, index, parentIndex);
                    index = parentIndex;
                } else {
                    // 否则表示这条路径上的值已经满足降序，跳出循环
                    break;
                }
            }
        }

        public int remove() {
            // 将根的值记录，最后返回
            int result = store[0];
            // 将最后位置的值放到根节点位置
            store[0] = store[--size];
            int index = 0;
            // 通过循环，保证父节点的值不能小于子节点。
            while(true) {
                int leftIndex = 2 * index + 1; // 左子节点
                int rightIndex = 2 * index + 2; // 右子节点
                // leftIndex >= size 表示这个子节点还没有值。
                if (leftIndex >= size) break;
                int maxIndex = leftIndex;
                if (store[leftIndex] < store[rightIndex]) maxIndex = rightIndex;
                if (store[index] < store[maxIndex]) {
                    swap(store, index, maxIndex);
                    index = maxIndex;
                } else {
                    break;
                }
            }
            return result;
        }
    }

    private static void fastSort(int[] arr) {
        fastSort(arr, 0, arr.length - 1);
    }

    private static void fastSort(int[] arr, int left, int right) {
        // 跳出递归的条件
        if (right - left <= 0) return;
        // 使用arr[right]作为关键值
        int mid = partition(arr, left, right, arr[right]);
        fastSort(arr, left, mid - 1);
        fastSort(arr, mid + 1, right);
    }

    private static int partition(int[] arr, int left, int right, int pivot) {
        // 记录下开始时的right位置，它代表了关键值，最后要将这个关键值移动到中间位置。
        // 开始时，right没有加一，因为right位置作为关键值，最后要移动到中间位置。
        int originRight = right;
        left = left - 1;
        while (left < right) {
            while(left < right && arr[++left] < pivot);
            while(left < right && arr[--right] > pivot);
            swap(arr, left, right);
        }
        // right位置的值，一定不小于pivot，和最开始right位置值进行交换。
        // 这样才能满足right位置的值不小于right之前位置的值，又不大于right之后位置的值
        swap(arr, right, originRight);
        return right;
    }

    // 优化的快速排序
    private static void optimizeFastSort(int[] arr) {
        optimizeFastSort(arr, 0, arr.length - 1);
    }

    private static void optimizeFastSort(int[] arr, int left, int right) {
        int size = right - left + 1;
        if (size <= 3) { //  等于3的数组，手动排序
            manualSort(arr, left, right);
        } else {
            // 得到中位的关键值
            int pivot = getPivot(arr, left, right);
            int mid = optimizePartition(arr, left, right, pivot);
            optimizeFastSort(arr, left, mid - 1);
            optimizeFastSort(arr, mid + 1, right);
        }
    }

    // 手动排序
    private static void manualSort(int[] arr, int left, int right) {
        int size = right - left + 1;
        if (size <= 1)
            return;
        if (size == 2){
            if (arr[left] > arr[right]) {
                swap(arr, left, right);
            }
            return;
        }
        // 以下是size==3情况
        int mid = right - 1;
        if (arr[left] > arr[mid]) {
            swap(arr, left, mid);
        }
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }
        if (arr[mid] > arr[right]) {
            swap(arr,mid, right);
        }

    }

    private static int getPivot(int[] arr, int left, int right) {
        // 找到left和right中间值mid
        int mid = (left + right) / 2;
        // 下面三个判断，保证left、mid、right这三个位置的值是有序排列的。
        if (arr[left] > arr[mid]) {
            swap(arr, left, mid);
        }

        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }

        if (arr[mid] > arr[right]) {
            swap(arr, mid, right);
        }
        // 将mid位置的值和right - 1位置交换。因为原来mid位置的值就是我们需要的关键值。
        swap(arr, mid, right - 1);
        return arr[right - 1];
    }

    private static int optimizePartition(int[] arr, int left, int right, int pivot) {
        // 这里注意一下，pivot的值是在right - 1位置的，并且right位置的值是比right - 1大的。
        right = right - 1;
        int originRight = right;
        left = left - 1;
        while (left < right) {
            while( arr[++left] < pivot);
            // 右边的扫描是从right - 2开始的。
            while( left < right && arr[--right] > pivot);
            swap(arr, left, right);
        }
        // right位置的值，一定不小于pivot，和最开始right位置值进行交换。
        // 这样才能满足right位置的值不小于right之前位置的值，又不大于right之后位置的值
        swap(arr, right, originRight);
        return right;
    }


    private static void swap(int[] arr, int one, int two) {
        int temp = arr[one];
        arr[one] = arr[two];
        arr[two] = temp;
    }

    private static int[] getArr(int n) {
        Random random = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(10000);
        }
        return arr;
    }

    private static boolean isSort(int[] arr) {
        int size = arr.length;
        for(int i = 0; i < size - 1; i++){
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static void printArr(int[] arr){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.length; i++){
            sb.append(arr[i]+",");
        }
        System.out.println("arr=="+sb.toString());
        System.out.println("isSort=="+isSort(arr));
    }

    private static int partition(int[] arr, int pivot) {
        // 将left开始设置为-1，因为下面判断使用arr[++left]，会先自增。
        // 如果使用arr[left++]，那么进行判断arr[++left] < pivot时的left值
        // 和进行交换swap(arr, left, right)就不是同一个值了。
        int left = -1;
        // 同left一样，比真实坐标大一的位置
        int right = arr.length;
        while (left < right) {
            while (left < right && arr[++left] < pivot);
            while (left < right && arr[--right] > pivot);
            swap(arr, left, right);
        }
        return right;
    }

    public static void main(String[] args){
        int[] arr = getArr(100);
        optimizeFastSort(arr);
        printArr(arr);

        arr = getArr(100);
        selectSort(arr);
        printArr(arr);

        arr = getArr(100);
        insertSort(arr);
        printArr(arr);

        arr = getArr(100);
        shellSort(arr);
        printArr(arr);

        arr = getArr(100);
        headSort(arr);
        printArr(arr);


        arr = getArr(100);
        fastSort(arr);
        printArr(arr);

//        arr = getArr(50);
//        partition(arr, 50);
//        printArr(arr);

        System.out.println();
        System.out.println();
        System.out.println();
        main3();
    }

    private static void main3() {
        int count = 10000*10;

        int[] arr = getArr(count);
        /*SpendTime spendTime = new SpendTime();
        shellSort(arr);
        System.out.println("isSort=="+isSort(arr)+"  size=="+arr.length);
        System.out.println("花费时间:"+spendTime.getSpendSeconds());

        arr =  getArr(count);
        spendTime = new SpendTime();
        fastSort(arr);
        System.out.println("isSort=="+isSort(arr)+"  size=="+arr.length);
        System.out.println("花费时间:"+spendTime.getSpendSeconds());

        arr =  getArr(count);
        spendTime = new SpendTime();
        optimizeFastSort(arr);
        System.out.println("isSort=="+isSort(arr)+"  size=="+arr.length);
        System.out.println("花费时间:"+spendTime.getSpendSeconds());


        arr =  getArr(count);
        spendTime = new SpendTime();
        headSort(arr);
        System.out.println("isSort=="+isSort(arr)+"  size=="+arr.length);
        System.out.println("花费时间:"+spendTime.getSpendSeconds());*/
    }
}
