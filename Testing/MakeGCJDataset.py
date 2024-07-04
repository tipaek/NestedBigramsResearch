# -*- coding: utf-8 -*-
"""
Created on Thu Jun 27 19:12:02 2024

@author: l-tipaek
"""
import os
import csv
from openai import OpenAI
from transformers import GPT2TokenizerFast
import time

from config import OPENAI_API_KEY

# Set your OpenAI API key
client = OpenAI(api_key=OPENAI_API_KEY)

tokenizer = GPT2TokenizerFast.from_pretrained("gpt2")

def count_tokens_in_file(file_path):
    try:
        with open(file_path, 'r') as file:
            content = file.read()
        tokens = tokenizer.encode(content)
        return len(tokens)
    except Exception as e:
        print(f"Error reading {file_path}: {e}")
        return None

def count_tokens_in_files(input_directory, output_csv):
    data = []
    total = 0
    for root, _, files in os.walk(input_directory):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                token_count = count_tokens_in_file(file_path)
                if token_count is not None:
                    total += token_count
                    data.append([file, token_count, total])
    
    with open(output_csv, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['File Name', 'Token Count', 'Total Token Count'])
        writer.writerows(data)

def rewrite_java_file(file_path):
    try:
        with open(file_path, 'r') as file:
            java_code = file.read()
        
        messages = [
            {"role": "system", "content": "You are a helpful assistant."},
            {"role": "user", "content": f"This is Java code. Rewrite it entirely while maintaining functionality:\n```java\n{java_code}\n```"}
        ]
        
        response = client.chat.completions.create(
            model="gpt-4o",
            messages=messages,
            max_tokens=4096,  # Adjust based on your needs and cost constraints
            temperature=0.5
        )
        
        rewritten_code = response.choices[0].message.content.strip()
        
        if "```java" in rewritten_code and "```" in rewritten_code:
            start = rewritten_code.find("```java") + len("```java\n")
            end = rewritten_code.rfind("```")
            rewritten_code = rewritten_code[start:end].strip()
        return rewritten_code
    except Exception as e:
        print(f"Error processing {file_path}: {e}")
        return None
        

def rewrite_java_files(input_directory, output_directory, num_files):
    if not os.path.exists(output_directory):
        os.makedirs(output_directory)

    java_files = []
    processed_count = 0
    
    existing_files_count = sum([len(files) for r, d, files in os.walk(output_directory)])

    for root, _, files in os.walk(input_directory):
        for file in files:
            if file.endswith('.java'):
                input_file_path = os.path.join(root, file)
                relative_path = os.path.relpath(root, input_directory)
                output_file_path = os.path.join(output_directory, relative_path, file)

                # Check if the total number of files in the output directory exceeds the limit
                if existing_files_count + processed_count >= 20000:
                    print(f"Reached the limit of 14193 files in the output directory. Stopping.")
                    return

                # Check if the file already exists in the output directory
                if os.path.exists(output_file_path):
                    print(f"File {output_file_path} already processed. Skipping.")
                    continue
                
                java_files.append(input_file_path)
                if len(java_files) >= num_files:
                    break
        if len(java_files) >= num_files:
            break

    for file_path in java_files:
        rewritten_code = rewrite_java_file(file_path)
        
        if rewritten_code:
            relative_path = os.path.relpath(file_path, input_directory)
            output_file_path = os.path.join(output_directory, relative_path)
            output_file_directory = os.path.dirname(output_file_path)
            
            if not os.path.exists(output_file_directory):
                os.makedirs(output_file_directory)
            
            try:
                with open(output_file_path, 'w') as output_file:
                    output_file.write(rewritten_code)
                processed_count += 1
            except Exception as e:
                print(f"Error writing {output_file_path}: {e}")

        # Ensure to process exactly `num_files`
        if processed_count >= num_files:
            break

        time.sleep(5)


input_path = r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\GCJ\code"
output_path = r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\GCJ\Anomalous"
#count_tokens_in_files(input_path, 'output_token_counts.csv')
rewrite_java_files(input_path, output_path, 8000)
