class Solution(object):
    def longestCommonPrefix(self, strs):
        """
        :type strs: List[str]
        :rtype: str
        """
        prefix = ""
        
        if strs is not None and len(strs) > 0:
            prefix = strs[0]
            pLen = len(prefix)
            
            for i in range(1, len(strs)):
                strToCompare = strs[i]
                while(prefix != strToCompare[:pLen] and pLen > 0):
                    pLen -= 1
                    prefix = prefix[:pLen]
                    
                if pLen == 0:
                    break
            
        return prefix
        
        