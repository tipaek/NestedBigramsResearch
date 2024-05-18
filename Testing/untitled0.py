# -*- coding: utf-8 -*-
"""
Created on Mon Apr 15 15:11:22 2024

@author: l-tipaek
"""

import http.client
from config import GPT_API_KEY
import json

conn = http.client.HTTPSConnection("zerogpt.p.rapidapi.com")

payload = "{\r\n    \"input_text\": \"C++ is a high-level, general-purpose programming language that was developed by Bjarne Stroustrup in 1983 as an extension of the C programming language. It is an object-oriented language that allows developers to write efficient and portable code that can run on a wide range of platforms, from embedded systems to supercomputers. In this article, we will discuss some of the key features and benefits of C++, as well as its various applications and use cases.\"\r\n}"

headers = {
    'content-type': "application/json",
    'ATBBSsz93c01QNyT9YmehEvtR0QNcTeB2D42E40': "Check Detection",
    'X-RapidAPI-Key': GPT_API_KEY,
    'X-RapidAPI-Host': "zerogpt.p.rapidapi.com"
}

conn.request("POST", "/api/v1/detectText", payload, headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))

data_string = data.decode("utf-8")
data_dict = json.loads(data_string)
is_gpt_generated = data_dict['data']['is_gpt_generated']
print(is_gpt_generated)