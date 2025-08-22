package com.morningglory.leetcode;

public class TrieTest {

    static int[][] tree = new int[10000][26];
    static int cnt = 0;
    static boolean[] end = new boolean[10000];

    public static void main(String[] args) {
        insert("abc");
        insert("bcd");
        insert("cde");
        boolean b = find("ab");
        System.out.println(b);
    }

    public static void insert(String word) {
        int p = 0;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int c = chars[i] - 'a';
            if (tree[p][c] == 0) {
                tree[p][c] = ++cnt;
            }
            p = tree[p][c];
        }
        end[p] = true;
    }

    public static boolean find(String word) {
        int p = 0;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int c = chars[i] - 'a';
            if (tree[p][c] == 0) {
                return false;
            }
            p = tree[p][c];
        }
        return end[p];
    }
}
