#experiment logging
from comet_ml import Experiment
from comet_ml.integration.sklearn import log_model
from config import API_KEY

import http.client

from config import GPT_API_KEY

import os
import chardet
import sys

import json
import time

import pandas as pd

sys.path.insert(0, 'C:\\Users\\l-tipaek\Desktop\\Research\\NestedBigramsResearch')

from Code.FeatureExtraction import checkFiles

from sklearn.metrics import precision_score, recall_score, f1_score, roc_auc_score, accuracy_score

#zeroGPT (their timeouts are too frequent and too long, making it unusable)
conn = http.client.HTTPSConnection("zerogpt.p.rapidapi.com")
headers = {
    'content-type': "application/json",
    'ATBBSsz93c01QNyT9YmehEvtR0QNcTeB2D42E40': "Check if GPT",
    'X-RapidAPI-Key': GPT_API_KEY,
    'X-RapidAPI-Host': "zerogpt.p.rapidapi.com"
}

def test_api(file_path, groupLength, input_file_path):
    
    
    failed = [0]
    row_index = [1] # we start our index at 1 for column label intersection reasons, but this doesn't change anything
    column_names = ['Index', 'API', 'Score', 'Actual']
    
    df = pd.read_csv(input_file_path, names=column_names)
    df_obj = [df]
    
    def process_file(file_name, file_path):
        try:   
            
            with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
            with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                code_lines = code.split('\n')
                code_groups = [code_lines[i:i + groupLength] for i in range(0, len(code_lines), groupLength)]
                
        
                for i, code_group in enumerate(code_groups):
                        if row_index[0] in df_obj[0]['Index']:
                            #print('already contained')
                            row_index[0] += 1
                            continue
                        while True:
                            try:
                                
                                code_string = '\n'.join(code_group)
                                
                                json_payload = json.dumps({"input_text": code_string})
                                #print(json_payload)
                                
                                    
                                conn.request("POST", "/api/v1/detectText", json_payload, headers)
                                        
                                res = conn.getresponse()
                                data = res.read()
                    
                                #print(data.decode("utf-8"))
                                        
                                data_string = data.decode("utf-8")
                                if data_string == 'input text is required':
                                    print(code_string)
                                    break
                                    
                                #print(data_string)
                                data_dict = json.loads(data_string)
                                is_gpt_generated = data_dict['data']['is_gpt_generated']
                                #print(is_gpt_generated)
                                       
                                new_row = {'Index': row_index[0], 'API': 1 if is_gpt_generated > 50 else 0, 'Score': is_gpt_generated, 'Actual': 1 if 'Anomalous' in file_path else 0} 
                                temp = pd.DataFrame(new_row, index=[0])       
                                df_obj[0] = pd.concat([df_obj[0], temp], ignore_index=True)
                                
                                row_index[0] += 1
                                print(new_row)
                                
                                df_obj[0].to_csv(input_file_path, index=False, header=False)
                                break
                            except Exception:
                                print(f'failed, {data_string} waiting 30 min')
                                time.sleep(60*30)
                                continue
                            
                        
        except Exception as e:
            raise Exception(f'An error occurred while processing file {file_name}: {e}')
                            
                        
        
    def process_directory(dir_path, data, processed_dirs=set(), processed_files=set()):
       for root, dirs, files in os.walk(dir_path):
           for file_name in files:
               if file_name.endswith('.java'):
                   file_path = os.path.join(root, file_name)
                   if os.path.realpath(file_path) not in processed_files:
                       processed_files.add(os.path.realpath(file_path))
                       #print(file_name)
                       process_file(file_name, file_path)
           for dir_name in dirs: 
               dir_path = os.path.join(root, dir_name)
               if os.path.realpath(dir_path) not in processed_dirs:
                   #print(dir_path)
                   processed_dirs.add(os.path.realpath(dir_path))
                   process_directory(dir_path, data, processed_dirs, processed_files)
    
    process_directory(file_path, groupLength)
    
    
    return df


def counter(file_path, groupLength):
    total_words = [0]
    def process_file(file_name, file_path):
        with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
        with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                code_lines = code.split('\n')
                code_groups = [code_lines[i:i + groupLength] for i in range(0, len(code_lines), groupLength)]
                
                total_words[0] += code.count(' ')
    
    def process_directory(dir_path, data, processed_dirs=set(), processed_files=set()):
       for root, dirs, files in os.walk(dir_path):
           for file_name in files:
               if file_name.endswith('.java'):
                   file_path = os.path.join(root, file_name)
                   if os.path.realpath(file_path) not in processed_files:
                       processed_files.add(os.path.realpath(file_path))
                       #print(file_name)
                       process_file(file_name, file_path)
           for dir_name in dirs: 
               dir_path = os.path.join(root, dir_name)
               if os.path.realpath(dir_path) not in processed_dirs:
                   #print(dir_path)
                   processed_dirs.add(os.path.realpath(dir_path))
                   process_directory(dir_path, data, processed_dirs, processed_files)
    
    process_directory(file_path, groupLength)
    
    
    print(total_words[0])
    


files = r'C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\GPT - Rewrite'
temp = [10, 20, 30, 40, 50, 60, 70]
#temp = [10]
#files=r'C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\temp4'
#temp = [100]

experiment = Experiment(
  api_key=API_KEY,
  project_name="anomaly-detection-research",
  workspace="tipaek",
)

experiment.add_tags(["GPTR, GPTZero", "GPT Zero"])
#path = r'C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Testing\comparison'
paths = [r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Datasets\GPT - Rewrite\GPTZero\Comparison"]


for path in paths:
    for size in temp:
        curr_path = f'{path}(G{size}).csv'
        data = pd.read_csv(curr_path)
        y_true = data['Actual'].astype(int)
        y_pred = data['API'].astype(int)
        y_score = data['Score'].astype(float)
        
        precision = precision_score(y_true, y_pred)
        recall = recall_score(y_true, y_pred)
        f1 = f1_score(y_true, y_pred)
        roc_auc = roc_auc_score(y_true, y_score)
        accuracy = accuracy_score(y_true, y_pred)
        
        print(f'precision: {precision}, recall: {recall}, f1: {f1}, auc: {roc_auc}, accuracy: {accuracy}\n\n\n')
        
        experiment.log_metrics({
                    "group_size": size,
                    "roc_auc": roc_auc, #metrics
                    "f1": f1,
                    "accuracy": accuracy,
                    "precision": precision
                    })

experiment.end()
        


for val in temp:
    print(f'group size: {val}\n')

    #checkFiles(files)
    #counter(files, val)
    currPath = f'{path}(G{val}).csv'
    if not os.path.exists(currPath) or pd.read_csv(currPath).empty:
        column_names = ['Index', 'API', 'Score', 'Actual']
        df = pd.DataFrame(columns=column_names)
        df.to_csv(f'Comparison(G{val}).csv', index=False)
    df = test_api(files, val, currPath)

    
    
    
    

    



