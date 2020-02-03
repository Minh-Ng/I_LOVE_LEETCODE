static int speedup = []() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    return 0;
}();

class LRUCache {
public:
    LRUCache(int capacity) : capacity(capacity) {
        keyToNode.reserve(capacity * 3 / 4);
    }
    
    int get(int key) {
        /* std::unordered_map<int, std::list<pair<int, int>>::iterator>::iterator */
        auto pair = keyToNode.find(key);
        if (pair == keyToNode.end()) {
            return -1;    
        }
        
        lruKeys.splice(lruKeys.begin(), lruKeys, pair->second);
        return pair->second->second;
    }
    
    void put(int key, int value) {
        auto pair = keyToNode.find(key);
        if (pair != keyToNode.end()) {
            pair->second->second = value;
            lruKeys.splice(lruKeys.begin(), lruKeys, pair->second);
            return;
        }
        
        lruKeys.emplace_front(key, value);
        keyToNode[key] = lruKeys.begin();
        if (lruKeys.size() > capacity) {
            keyToNode.erase(lruKeys.back().first);
            lruKeys.pop_back();
        }
    }
private:
    std::list<std::pair<int, int>> lruKeys; /* pair node of (key, value) */
    std::unordered_map<int, decltype(lruKeys)::iterator> keyToNode;
    int capacity;
};

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
