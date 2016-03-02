package com.tripsters.sample.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.text.TextUtils;

public class PinyinUtils{
    
	private static PinyinUtils sInstance;
	
	private static List<String> mPinyin = new ArrayList<String>();
	
	private static volatile boolean isLoad = false;
	private static ReentrantLock mLoadLock = new ReentrantLock(true);
	
	private static final char SPECIAL_HANZI = '\u3007';
	private static final String SPECIAL_HANZI_PINYIN = "LING";
		
	private static final char FIRST_CHINA = '\u4E00';
	private static final char LAST_CHINA = '\u9FA5';
	private static final int DISTINGUISH_LEN = 10;//distinguish() 中比较字符串的长度
	
    public static class MatchedResult {
        public int start = -1;
        public int end = -1;
    }
    
    private PinyinUtils() {
    }
    
    public static synchronized PinyinUtils getInstance(Context ctx) {
    	if (sInstance == null) {
    		sInstance = new PinyinUtils();
    	}
    	loadData(ctx);
    	return sInstance;
    }
    
    private static void loadData(Context ctx) {
    	try {
    		if (!mLoadLock.tryLock()) {
    			return;
    		}
    		if (isLoad) {
        		return;
        	}
    		InputStream is = ctx.getAssets().open("unicode_pinyin_map.txt");
    		BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		for (String line = br.readLine(); line != null; line = br.readLine()) {
    			String[] arr = line.split(" ");
    			if (arr != null && arr.length == 2) {
    				mPinyin.add(arr[1].toUpperCase());
    			}
    		}
    		isLoad = true;
    		
    	} catch(IOException e) {
    		isLoad = false;
    	} catch(Exception e) {
    		isLoad = false;
    	} finally {
    		mLoadLock.unlock();
    	}
    }
    
    private String getPinyin(char ch) {
    	if (!isLoad) {
    		return "";
    	}
    	
    	String pinyin = "";
    	if (ch == SPECIAL_HANZI) {
    		return SPECIAL_HANZI_PINYIN;
    	}
    	
    	if (ch < FIRST_CHINA || ch > LAST_CHINA) {
    		return String.valueOf(ch);
    		//return null;
    	}
    	
    	int pos = ch - FIRST_CHINA;
    	pinyin = mPinyin.get(pos);
    	
    	if (pinyin == null) {
    		pinyin = "";
    	}
    	
		return pinyin;
    }
    
    public String getPinyin(String s) {
    	if (TextUtils.isEmpty(s)) {
    		return "";
    	}
    	if (!isLoad) {
    		return "";
    	}
    	StringBuilder sb = new StringBuilder();
    	int len = s.length();
    	for(int i=0; i<len; i++) {
    		char c = s.charAt(i);
    		sb.append(getPinyin(c));
    	}
    	return sb.toString();
    } 
    
    public String getHeadPinyin(String s){
    	
    	if (TextUtils.isEmpty(s)) {
    		return "";
    	}
    	if (!isLoad) {
    		return "";
    	}
    	StringBuilder sb = new StringBuilder();
    	int len = s.length();
    	for(int i=0; i<len; i++) {
    		char c = s.charAt(i);
    		char head = getPinyin(c).toCharArray()[0];
    		sb.append(head);
    	}
    	return sb.toString();
    }
    
    public MatchedResult getMatchedResult(String src, String input) {
    	MatchedResult result = new MatchedResult();
    	result.start = -1;
    	result.end = -1;
    	if (!isLoad) {
    		return result;
    	}
    	
    	if (TextUtils.isEmpty(src) || TextUtils.isEmpty(input)) {
    		return result;
    	}
    	
        src = src.toUpperCase();
        input = input.toUpperCase();
        
        int n = Math.min(src.length(), input.length());
        if(n>DISTINGUISH_LEN){
        	src = src.substring(0,DISTINGUISH_LEN);
        	input = input.substring(0,DISTINGUISH_LEN);
        }
        
        int index = src.indexOf(input);
        if(index >= 0){
            result.start = index;
            result.end = index + input.length() -1;
        }
        
        char[] search = new char[input.length()];
        for (int i=0; i<input.length(); i++) {
        	search[i] = input.charAt(i);
        }
        
        char[] org = new char[src.length()];
        String[] fullPinyin = new String[src.length()]; 
        int srcLen = src.length();
        
        for(int i=0; i<srcLen; i++) {
        	
        	char ch = src.charAt(i);
        	org[i] = ch;
        	
            String pinyinCache = getPinyin(ch);

            if (!TextUtils.isEmpty(pinyinCache)) {
            	fullPinyin[i] = pinyinCache;
            }
            else {
            	fullPinyin[i] = ch + "";
            }
        }
        
        char firstSearch = search[0];
        for (int i=0; i<fullPinyin.length; i++) {
        	char ch1 = fullPinyin[i].charAt(0);
        	char ch2 = org[i];
        	int pos = -1;
        	if (ch1 == firstSearch || ch2 == firstSearch) {
        		pos = distinguish (search, 0, 
        				subCharRangeArray(org, i, org.length - 1), 
        				subStringRangeArray(fullPinyin, i, fullPinyin.length - 1), 
        				0, 0);
        		if (pos != -1) {
    				result.start = i;
    				result.end = i + pos;
//    				Log.e("pinyin", "src = " + src);
//    				Log.e("pinyin", "input = " + input);
//        			Log.e("pinyin", "result.start = " + result.start);
//        			Log.e("pinyin", "result.end = " + result.end);
        			return result;
        		}
        	}
        }
        return result;
    }
    
    public int distinguish(char[] search, int searchIndex, char src[], String pinyin[], int wordIndex, int wordStart) {
        if (searchIndex == 0) {  
            if (search[0] == src[0] || search[0] == pinyin[0].charAt(0)) {  
            	if (search.length != 1) {
            		return distinguish(search, 1, src, pinyin, 0, 1);
            	}
            	else {
            		return 0;
            	}
            }
        }
        if (pinyin[wordIndex].length() > wordStart && searchIndex < search.length
                && (search[searchIndex] == src[wordIndex] 
                || search[searchIndex] == pinyin[wordIndex].charAt(wordStart))) {
        	if (searchIndex == search.length - 1) {
        		if (distinguish(search, src, pinyin, wordIndex)) {
        			return wordIndex;
        		}
        		else {
        			return -1;
        		}
        	}
        	else {
        		return distinguish(search, searchIndex + 1, src, pinyin, wordIndex, wordStart + 1);
        	}
        }
        else if (pinyin.length > wordIndex + 1 && searchIndex < search.length  
                && (search[searchIndex] == src[wordIndex + 1] 
                || search[searchIndex] == pinyin[wordIndex + 1].charAt(0))) {  
        	if (searchIndex == search.length - 1) {
        		if (distinguish(search, src, pinyin, wordIndex)) {
        			return wordIndex + 1;
        		}
        		else {
        			return -1;
        		}
        	}
        	else {
        		return distinguish(search, searchIndex + 1, src, pinyin, wordIndex + 1, 1);
        	}
        }
        else if (pinyin.length > wordIndex + 1) {  
            for (int i = 1; i < searchIndex; i++) {
                if (distinguish(search, searchIndex - i, src, pinyin, wordIndex + 1, 0) != -1) { 
                	return wordIndex + 1;
                }
            }
        }
        return -1;  
    }
    
    private boolean distinguish(char[] search, char src[], String pinyin[], int wordIndex) {
        String searchString = new String(search);  
        int lastIndex = 0;  
        int i = 0;  
        for (i = 0; i < wordIndex; i++) {  
        	lastIndex = searchString.indexOf(pinyin[i].charAt(0), lastIndex);
            if (lastIndex == -1) {
            	lastIndex = searchString.indexOf(src[i], lastIndex); 
            }
            if (lastIndex == -1) {
            	return false;  
            }
            lastIndex++;  
        }  
        return true;  
    }  
    
    private char[] subCharRangeArray(char[] org, int start, int end) {
    	int len = end - start + 1;
    	char[] ret = new char[len];
    	for (int i=start, j=0; i<=end; i++, j++) {
    		ret[j] = org[i]; 
    	}
    	return ret;
    }
    
    private String[] subStringRangeArray(String[] org, int start, int end) {
    	int len = end - start + 1;
    	String[] ret = new String[len];
    	for (int i=start, j=0; i<=end; i++, j++) {
    		ret[j] = org[i]; 
    	}
    	return ret;
    }
}