class Solution(object):
    def kEmptySlots(self, flowers, k):
        N = len(flowers)
        days = [0] * N
        
        for day, position in enumerate(flowers, 1):
            days[position - 1] = day
		
        soonestDay = float('inf')
        left = 0
        right = k + 1
        while right < N:
            for i in xrange(left + 1, right): # check window
                if days[i] < days[left] or days[i] < days[right]:
                    left = i
                    right = left + k + 1
                    break
            else: # found a solution
                soonestDay = min(soonestDay, max(days[left], days[right]))
                left = right + 1
                right = left + k
        
        return soonestDay if soonestDay != float('inf') else -1
        