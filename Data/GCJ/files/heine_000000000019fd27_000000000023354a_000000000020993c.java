const $readline = require('readline');
const rl = $readline.createInterface({
    input: process.stdin,
    output: process.stderr
});
const transpose = a => a[0].map((_, c) => a.map(r => r[c]));

let caseNumMax = 0;
let caseNum = 1;
let count = 0;
let param1 = 0;
let param2 = [];

function processing(param) {
    let matrix = [];
    let trans = [];
    let i = 0;
    let trace = 0;
    let rowNum = 0;
    let colNum = 0;
    let len = 1;
    param.forEach((value, index) => {
        matrix[index] = value.split(' ').map(x => parseInt(x));
    })
    len = matrix[0].length;
    for(i = 0; i < len; i++) {
        trace += matrix[i][i];
        let set = new Set(matrix[i]);
        rowNum += (set.size === len ? 0 : 1);
    }
    trans = transpose(matrix);
    for(i = 0; i < len; i++) {
        let set = new Set(trans[i]);
        colNum += (set.size === len ? 0 : 1);
    }
    return `${trace} ${rowNum} ${colNum}`;
}

rl.on('line', function (input) {
    if (caseNumMax === 0) {
        caseNumMax = parseInt(input, 10);
    } else if (count === 0) {
        param1 = input|0;
        count++;
    } else {
        param2.push(input);
        if (count < param1) {
            count++;
        } else {
            const answer = processing(param2);
            console.log('Case #' + caseNum + ': ' + answer);
            count = 0;
            param2 = [];
            caseNum++;
            if (caseNum > caseNumMax) {
                rl.close();
                process.exit();
            }
        }
    }
});