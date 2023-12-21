import javalang
print(hasattr(javalang.tree, 'ForStatement'))

# specify the path to the Java file
file_path = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data Output\Playing2.java'

# read the contents of the file
with open(file_path, 'r') as f:
    code = f.read()

# parse the code using javalang
try:
    tree = javalang.parse.parse(code)
    print('File parsed successfully')
except javalang.parser.JavaSyntaxError as e:
    print(f'Error parsing file: {e}')

#errors: constants, enemy, game, helpmethods, keyboardinputs, level, mouseinputs, state, urmbutton, playing2