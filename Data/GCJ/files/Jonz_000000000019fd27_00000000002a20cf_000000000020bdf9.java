doihavetime <- function(person, start, end){
    for l in 1:length(person){
        if (person[l][1] < start & person[l][2] > start) & (person[l][1] < end & person[l][2] > end){
            return(FALSE)
        }
    }
    return(TRUE)
}

num1 <- strtoi(readline())
for (i in 1:num1){
    output <- ""
    num2 <- strtoi(readline())
    persc <- c()
    persj <- c()
    for (j in 1:num2){
        row <- readline()
        start <- strsplit(row,  " ")
        end <- strsplit(row, " ")
        if doihavetime(persc, start, end){
            output <- output + "C"
            persc.append(c(start, end))
        } else if doihavetime(persj, start, end){
            output <- output + "J"
            persj.append(c(start, end))
        } else {
            output <- "IMPOSSIBLE"
            break
        }
               
        
    }
    print("CASE #" + toString (i+1) + ": " + output)
    
}
