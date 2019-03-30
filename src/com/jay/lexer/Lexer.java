package com.jay.lexer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jay.exception.ParseException;

/**
 * 词法分析器
 * 
 * @author jay
 *
 */
public class Lexer {
    public static String REGEX_PATTERN = "\\s*(?<total>TOTAL)?"//
            .replace(TokenPattern.TOTAL.id, TokenPattern.TOTAL.expression)//
            .replace(TokenPattern.COMMENT.id, TokenPattern.COMMENT.expression)//
            .replace(TokenPattern.STRING.id, TokenPattern.STRING.expression)//
            .replace(TokenPattern.NUMBER.id, TokenPattern.NUMBER.expression);

    private Pattern pattern = Pattern.compile(REGEX_PATTERN);
    
    public static void main(String[] args) {
        Matcher matcher = Pattern.compile(REGEX_PATTERN).matcher("1");
        if(matcher.find()) {
            System.out.println(matcher.groupCount());
            System.out.println(matcher.group(1));
        }
    }

    private List<Token> queue = new LinkedList<Token>();

    private boolean hasMore; // Mark if there are still characters
    
    private LineNumberReader reader; // Tracking character input stream for tracking line numbers

    public Lexer(Reader r) { // Pass in a Reader object
        hasMore = true;
        reader = new LineNumberReader(r);
    }

    /**
     * 获取一个单词
     * 
     * @return
     * @throws ParseException
     */
    public Token read() throws ParseException {
        if (fillQueue(0)) {
            return queue.remove(0);
        }
        return Token.EOF;
    }

    /**
     * 预读一个单词
     * 
     * @param i
     * @return
     * @throws ParseException
     */
    public Token peek(int i) throws ParseException {
        if (fillQueue(i)) {
            return queue.get(i);
        } else {
            return Token.EOF;
        }
    }

    private boolean fillQueue(int i) throws ParseException {
        while (i >= queue.size()) {
            if (hasMore) {
                readLine();
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 读取一行
     * 
     * @return 是否存在下一行
     * @throws ParseException
     */
    protected void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        int lineNo = reader.getLineNumber(); // Get the current line number
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false); // About this code you can view the API
        int pos = 0;
        int endPos = line.length();
        
        while (pos < endPos) {
            matcher.region(pos, endPos); // Sets the limits of this matcher's region
            if (matcher.lookingAt()) { // Attempts to match the input sequence, starting at the beginning of the
                                       // region, against the pattern
                addToken(lineNo, matcher);
                pos = matcher.end(); // Returns the offset after the last matching character
            } else {
                throw new ParseException("bad token at line " + lineNo);
            }
        }
        queue.add(new IdToken(lineNo, Token.EOL));
    }

    protected void addToken(int lineNo, Matcher matcher) {
        String m = matcher.group(TokenPattern.TOTAL.name); // Returns the input subsequence captured by the given 1
                                                            // during
                                                            // the previous
        // match operation
        if (m != null) {// if not a space
            if (matcher.group(TokenPattern.COMMENT.name) == null) { // if not a comment
                Token token;
                if (matcher.group(TokenPattern.STRING.name) != null) {// is StrToken
                    token = new StrToken(lineNo, toStringLiteral(m));
                } else if (matcher.group(TokenPattern.NUMBER.name) != null) {// is NumToken
                    if(m.contains(".")) {
                        token = new NumToken(lineNo, Double.valueOf(m));
                    } else {
                        token = new NumToken(lineNo, Long.valueOf(m));
                    }
                } else {// is IdToken
                    token = new IdToken(lineNo, m);
                }
                queue.add(token);
            }
        }
    }

    protected String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) { // Determine if it is an escape character '\'
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\') {// \" | \\
                    c = s.charAt(++i);
                } else if (c2 == 'n') { // \n
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}