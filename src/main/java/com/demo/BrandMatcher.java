package com.demo;

import java.util.*;

public class BrandMatcher {

    // Trie 树节点类
    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    // Trie 树类
    private static class Trie {
        private TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        // 插入品牌词
        void insert(String word) {
            TrieNode curr = root;
            for (char c : word.toCharArray()) {
                if (c == '[' || c == ']' || c == '{' || c == '}' || c == '(' || c == ')') {
                    continue; // 忽略特殊符号
                }
                curr.children.putIfAbsent(c, new TrieNode());
                curr = curr.children.get(c);
            }
            curr.isEndOfWord = true;
        }

        // 匹配待匹配内容，返回匹配到的品牌词列表
        List<String> match(String text) {
            List<String> brands = new ArrayList<>();
            TrieNode curr = root;
            StringBuilder sb = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (c == '[' || c == ']' || c == '{' || c == '}' || c == '(' || c == ')') {
                    continue; // 忽略特殊符号
                }
                if (curr.children.containsKey(c)) {
                    sb.append(c);
                    curr = curr.children.get(c);
                    if (curr.isEndOfWord) {
                        brands.add(sb.toString());
                    }
                } else {
                    curr = root;
                    sb.setLength(0);
                }
            }
            return brands;
        }
    }

    public static void main(String[] args) {
        // 建立品牌词表的 Trie 树
        Trie brandTrie = new Trie();
        brandTrie.insert("肯德基麦辣鸡腿堡");
        brandTrie.insert("买一送一");
        brandTrie.insert("汉堡王狠霸王牛堡");
        brandTrie.insert("美味无限");
        brandTrie.insert("麦 1 当 1 劳 1112331 香浓咖啡");
        brandTrie.insert("无限续杯");

        // 匹配待匹配内容
        String text = "[汉堡]王狠霸王牛堡，买一送一";
        List<String> matchedBrands = brandTrie.match(text);
        System.out.println("匹配到的品牌词有：" + matchedBrands);
    }
}
