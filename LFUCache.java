import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

class LFUCache {
    private static class Node {
        private int value;
        private int freq;

        Node(int value, int freq) {
            this.value = value;
            this.freq = freq;
        }

        void setValue(int value) {
        	this.value = value;
        }

        void access() {
        	freq += 1;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> keyToNode;
    private final Map<Integer, LinkedHashSet<Integer>> countToLRUKeySet;

    private int lowestFrequency;

    public LFUCache(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be >= 0");
        }

        this.capacity = capacity;
        this.countToLRUKeySet = new HashMap<>();
        this.keyToNode = new HashMap<>((int) Math.ceil(capacity * 3 / 4));
        this.lowestFrequency = 1;
    }

    public int get(int key) {
        Node node = keyToNode.get(key);
        if (node == null) {
            return -1;
        }

        access(key, node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        } else if (keyToNode.containsKey(key)) {
            Node node = keyToNode.get(key);
            access(key, node);
            node.setValue(value);
            return;
        } else if (keyToNode.size() == capacity) {
            evictLeastFrequentlyLeastRecentlyUsedKey();
        }

        lowestFrequency = 1;
        countToLRUKeySet.computeIfAbsent(lowestFrequency, LFUCache::createLRU).add(key);
        keyToNode.put(key, new Node(value, lowestFrequency));
    }

    private void access(int key, Node node) {
    	LinkedHashSet<Integer> set = countToLRUKeySet.get(node.freq);
        set.remove(key);
        if (set.isEmpty()) {
            countToLRUKeySet.remove(node.freq);
            if (lowestFrequency == node.freq) {
                lowestFrequency = node.freq + 1;
            }
        }

        node.access();
        countToLRUKeySet.computeIfAbsent(node.freq, LFUCache::createLRU).add(key);
    }

    private void evictLeastFrequentlyLeastRecentlyUsedKey() {
        LinkedHashSet<Integer> set = countToLRUKeySet.get(lowestFrequency);
        Iterator<Integer> toRemove = set.iterator();
        keyToNode.remove(toRemove.next());
        toRemove.remove();
        if (set.isEmpty()) {
            countToLRUKeySet.remove(lowestFrequency);
        }
    }

    private static LinkedHashSet<Integer> createLRU(int i) {
    	return new LinkedHashSet<>();
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
