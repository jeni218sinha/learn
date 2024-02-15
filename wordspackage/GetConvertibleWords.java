package wordspackage;
import java.util.*;

class TrieNode {
    Character letter;
    Map<Character, TrieNode> children;

    public TrieNode(char letter) {
        this.letter = letter;
        this.children = new HashMap<>();
    }

    public TrieNode() {
        this.letter = null;
        this.children = new HashMap<>();
    }

    public Character getLetter() {
        return this.letter;
    }

    public void addChildNode(char childLetter) {
        if (this.children.containsKey(childLetter)) {
            return;
        }
        TrieNode childNode = new TrieNode(childLetter);
        children.put(childLetter, childNode);
    }

    public TrieNode getChildNode(char childLetter) {
        return this.children.get(childLetter);
    }

    public Map getChildren() {
        return this.children;
    }
}


public class GetConvertibleWords {

    public static List<String> getConvertibleWordsWithKEdits(List<String> dictionary, List<String> queries, int k) {
        if (dictionary == null || dictionary.isEmpty() || queries == null || queries.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> convertibleWords = new ArrayList<>();
        TrieNode dictionaryRoot = constructTrie(dictionary);
        
        for (String query: queries) {
            if (exploreDictionaryTrieWithKEdits(dictionaryRoot, query, k, 0, 0)) {
                convertibleWords.add(query);
            }
        }

        return convertibleWords;
    }

    public static boolean exploreDictionaryTrieWithKEdits(TrieNode root, String word, int k, int currLetterIndex, int edits) {
        Character rootLetter = root.getLetter();
        if (rootLetter != null) {
            if  (rootLetter != word.charAt(currLetterIndex)) {
                edits++;
            }
            currLetterIndex++;
        }

        List<TrieNode> chilNodes = new ArrayList<TrieNode>(root.getChildren().values());
        if (chilNodes == null || chilNodes.isEmpty()) {
            return edits <= k;
        }

        for (TrieNode child: chilNodes) {
            if (exploreDictionaryTrieWithKEdits(child, word, k, currLetterIndex, edits)) {
                return true;
            }
        }
        return false;
    }

    public static TrieNode constructTrie(List<String> words) {
        TrieNode root = new TrieNode();
        TrieNode head = root; // wood, joke, moat
        for (String word : words) {
            root = head;
            for (int index = 0; index < word.length(); index++) {
                char currLetter = word.charAt(index);
                root.addChildNode(currLetter);
                root = root.getChildNode(currLetter);
            }
        }
        return head;
    }


    public static void main(String [] args) {
        List<String> dictionary = Arrays.asList(new String[]{"wood", "joke", "moat"});
        List<String> queries = Arrays.asList(new String[]{"word", "note", "loke", "shau"});
        List<String> convertibleWords = getConvertibleWordsWithKEdits(dictionary, queries, 2);
        for (String word: convertibleWords) {
            System.out.println(word);
        }
    }
}
