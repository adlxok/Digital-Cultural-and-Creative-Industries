package org.example.digitalculturalportal.utils;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.text.normalizer.Trie;

/**
 * 敏感词过滤器（前缀树）
 * @author jisamin
 * @since 2024/7/16
 */
//@Component
//public class SensitiveFilter {
//    private static final Logger logger= (Logger) LoggerFactory.getLogger(SensitiveFilter.class);
//    //将敏感词替换成***
//    private static final String REPLACEMENT="***";
//    //根节点
//    private  TrieNode rootNode =new TrieNode();
//
//    /**
//     * 初始化前缀树
//     */
//    @PostConstruct//该注解标记的方法会在对象的构造函数执行完成后立即执行完成初始化工作
//    public void init(){
//        try(InputStream is=this.getClass().getClassLoader().getResourceAsStream("sensitive-word.txt");
//            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
//            ){
//            String keyword;
//            while ((keyword=reader.readLine())!=null){
//                this.addKeyword(keyword);
//            }
//        }catch (IOException e){
//            logger.error("加载敏感词文件失败"+e.getMessage());
//        }
//    }
//
//    /**
//     * 将一个敏感词加入前缀树中
//     * @param keyword
//     */
//    public void addKeyword(String keyword){
//        TrieNode tempNode =rootNode;
//        for (int i = 0; i < keyword.length(); i++) {
//            char c=keyword.charAt(i);
//            TrieNode subNode =tempNode.getSubNode(c);//判断是否存在相同子节点
//            if(subNode==null){
//                subNode=new TrieNode();
//                tempNode.addSubNode(c,subNode);//添加子节点
//            }
//            tempNode=subNode;//指向子节点，进入下一层循环
//            if(i==keyword.length()-1){
//                tempNode.setKeywordEnd(true);
//            }
//        }
//    }
//
//    /**
//     * 过滤敏感词
//     * @param text 待过滤的文本
//     * @return 过滤后的文本
//     */
//    public String filter(String text){
//        if(text.isEmpty()||text==null){
//            return null;
//        }
//        TrieNode tempNode =rootNode;
//        int begin=0;
//        int end=0;
//
//        StringBuilder sb=new StringBuilder();
//         while (end<text.length()){
//             char c=text.charAt(end);
//             if(isSymbol(c)){//跳过符号
//                 if(tempNode==rootNode){
//                     sb.append(c);
//                     begin++;
//                 }
//                 end++;
//                 continue;
//             }
//            //检查节点
//            tempNode=tempNode.getSubNode(c);
//             if(tempNode==null){
//                 sb.append(text.charAt(begin));//以指针begin开头的字符串不是敏感词
//                 begin++;//下一位判断
//                 end=begin;
//                 tempNode=rootNode;//指针1重新指向根节点
//             }
//            else if(tempNode.isKeywordEnd()) {
//                sb.append(REPLACEMENT);//发现敏感词
//                 end++;//下一位判断
//                 begin=end;
//                tempNode=rootNode;
//             }
//            else{
//                end++;//检查下一个字符
//             }
//         }
//         //将最后一批字符计入结果
//         sb.append(text.substring(begin));
//         return sb.toString();
//    }
//
//    /**
//     * 判断某个字符是否是符号
//     * @param c
//     * @return
//     */
//    private boolean isSymbol(Character c){
//        // 0x2E80~0x9FFF 是东亚文字范围
//        return !Character.isLetterOrDigit(c)&&(c < 0x2E80 || c > 0x9FFF);//Character.isLetterOrDigit(c)不是字母也不是数字返回false
//}
//
//    /**
//     * 定义前缀树
//     */
//    private class TrieNode{
//        //关键结束标识（叶子结点）
//        private boolean isKeywordEnd =false;
//        //子节点
//        private Map<Character,TrieNode> subNodes= new HashMap<>();
//
//        public boolean isKeywordEnd() {
//            return isKeywordEnd;
//        }
//
//        public void setKeywordEnd(boolean keywordEnd) {
//            isKeywordEnd = keywordEnd;
//        }
//
//        //添加子节点
//        public void addSubNode(Character c,TrieNode node){
//            subNodes.put(c,node);
//        }
//        //获取子节点
//        public TrieNode getSubNode(Character c){
//            return subNodes.get(c);
//        }
//    }
//}

