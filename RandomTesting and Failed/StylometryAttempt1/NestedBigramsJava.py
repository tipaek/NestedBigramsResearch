import os

import collections

import javalang
from javalang.parse import parse

#return list of bigrams of one node by comparing it to every other accessible node. if not method declaration,
#go one level lower and keep going
def code_bigram(node):
    result = []
    if isinstance(node, javalang.tree.CompilationUnit):
        block = node.body
        for child in block:
            if child is not None:
                bigram_collector = code_bigram(child)
                result.extend(bigram_collector)
    else:
        for child in node.children:
            if child is not None:
                bigram = [str(node), str(child)]
                result.append(bigram)
                bigram_collector = code_bigram(child)
                if bigram_collector:
                    result.extend(bigram_collector)
    return result

#returns tuple: unique bigrams and their frequencies
def bigram_freq(bigrams):
    bigram_ListofTuple = []
    for bigram in bigrams:
        bigram_ListofTuple.append(tuple(bigram))
    bigram_freq_holder = collections.Counter(bigram_ListofTuple)
    bigram_SetofTuple = set(bigram_ListofTuple)
    return (bigram_SetofTuple, bigram_freq_holder)

#two dictionaries, one for user names to unique bigrams, other for user names to dictionary of frequency of each unique bigram
def dataset_bigrams(mydir):
    all_users_bigramset = {}
    all_users_bigramfreq = {}
    count = 0
    for root, dir, file in os.walk(mydir, topdown=True):
        if count > 0:
            user = os.path.basename(root)
            for code in file:
                try:
                    tree = javalang.parse.parse(open(os.path.join(root, code)).read())
                except:
                    print('code not compatible with javalang module')
                    continue
                code_bigrams, code_bigrams_freq = bigram_freq(code_bigram(tree))
                if user in all_users_bigramset:
                    all_users_bigramset[user] = set.union(all_users_bigramset[user], code_bigrams) #combo + maintain distinct
                else:
                    all_users_bigramset[user] = code_bigrams
                if user in all_users_bigramfreq:
                    for key in code_bigrams_freq:
                        if key in all_users_bigramfreq[user]:
                            all_users_bigramfreq[user][key] += code_bigrams_freq[key]
                        else:
                            all_users_bigramfreq[user][key] = code_bigrams_freq[key]
                else:
                    all_users_bigramfreq[user] = code_bigrams_freq
        count = 1
    return (all_users_bigramset, all_users_bigramfreq)

#input: dictionary of bigram sets + integer threshold, returns tuple (list o/unique bigrams, list o/frequent bigrams > i,
#dictionary w/frequencies of each bigram)
def bigrams_feature_vector(bigramset, i):
    union = set()
    frequent = {}
    frequent_union = []
    for key in bigramset:
        union = set.union(bigramset[key], union)
    all_union = list(union)
    for key in bigramset:
        for bigram in bigramset[key]:
            try:
                frequent[bigram] += 1
            except:
                frequent[bigram] = 1
    for bigr in frequent.keys():
        if frequent[bigr] > i:
            frequent_union.append(bigr)
    return (all_union, frequent_union, frequent)

if __name__ == '__main__':
    mydir = os.path.join(os.path.dirname(__file__), 'SourceCode_byYear_ordered', '2014', '6')
    freq_thr = 1
    bigramset, bigramfreq = dataset_bigrams(mydir)
    all_bigrams, frequent_bigrams, frequent = bigrams_feature_vector(bigramset, freq_thr)
    diff = [i for i in all_bigrams if i not in frequent_bigrams]

    print(len(all_bigrams), len(frequent_bigrams))
    print(len(diff))

files = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data'
print(dataset_bigrams(files))