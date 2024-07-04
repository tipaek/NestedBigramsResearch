from itertools import combinations
import numpy as np
import sys

def complement(bits): return [
    '_' if b is '_' else 
    '1' if b=='0' else '0' 
    for b in bits
]

class QuantumArray():
    def __init__(re, ddy):
        re.bits = ['_'] * ddy
        re.unkowns = list(range(ddy))
        re.read(10)
        re.run()

    def read(self, k):
        for _ in range(k):
            i = self.unkowns.pop()
            self.bits[i] = self.read_bit(i+1)
            self.unkowns = self.unkowns[::-1]
        self.update_states()

    def read_bit(self, i):
        print(i, flush=1)
        return input()

    def update_states(self):
        self.bits_c  = complement(self.bits)
        self.bits_cr = self.bits_c[::-1]
        self.bits_r  = complement(self.bits_cr)
        self.states = [self.bits, self.bits_c, self.bits_cr, self.bits_r]

    def get_test_idx(self):
        candidates = list(set(range(B)) - set(self.unkowns))
        max_states = len(set(map(tuple, self.states)))
        for idx in combinations(candidates, 2):
            num_states = len(set(
                tuple(np.take(state, idx))
                for state in self.states
            ))
            if num_states == max_states: return idx

    def collapse(self):        
        test_idx = self.get_test_idx()
        test = [self.read_bit(i+1) for i in test_idx]
                
        self.bits = next(state
            for state in self.states
            if test == list(np.take(state, test_idx))
        )

    def run(self):
        while 1:
            self.collapse()
            try: self.read(8)
            except IndexError: break
    

srin, ivas = map(int, input().split())
print('B:', ivas, file=sys.stderr)
for _ in range(srin): 
    array = QuantumArray(ivas)
    print('guess:', ''.join(array.bits), file=sys.stderr)
    print(''.join(array.bits), flush=1)
    if input() == 'N': sys.exit()