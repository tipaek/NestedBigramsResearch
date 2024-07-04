const fs = require('fs');
const input = fs.readFileSync(0, 'utf8').trim().split('\n');

let currentLine = 0;
function readLine(){
    return input[currentLine++];
}

let T = readLine();
for(let i=1; i<=T; i++){
    let values = readLine().split("");
    console.log(`Case #${i}: ${solve(values)}`);
}

function concatanation(s,number,charcter){
    for(var i=0;i<number;i++){
        s = s.concat(charcter);
    }
    return s;
}

function solve(arr){
    let s = "";
    s = concatanation(s,arr[0],'(');
    s = s.concat(arr[0]);
    for(var i=1;i<arr.length;i++){
        if(arr[i-1] === arr[i])
            s = s.concat(arr[i]);
        else if(arr[i-1] < arr[i]){
            s = concatanation(s,arr[i]-arr[i-1],'(');
            s = s.concat(arr[i]);
        }
        else{
            s = concatanation(s, arr[i-1]-arr[i], ')');
            s = s.concat(arr[i]);
        }
    }
    s = concatanation(s, arr[i-1], ')');
    return s;
}