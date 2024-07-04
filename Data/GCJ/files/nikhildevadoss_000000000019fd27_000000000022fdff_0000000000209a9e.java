  t, b = readline_int_list()      // reads 100 into t and 10 into b.
  // The judge starts with the predetermined array for this test case:
  // 0001101111. (Note: the actual Test Set 1 will not necessarily
  // use this array.)
  printline 1 to stdout   // we ask about position 1.
  flush stdout
  // Since this is our 1st query, and 1 is 1 mod 10, the judge secretly and
  // randomly chooses one of the four possible quantum fluctuation effects, as
  // described above. It happens to choose complementation + reversal, so now
  // the stored value is 0000100111.
  r = readline_chr()      // reads 0.
  printline 6 to stdout   // we ask about position 6.
  flush stdout
  // Since this is our 2nd query, and 2 is 2 mod 10, the judge does not choose
  // a quantum fluctuation effect.
  r = readline_chr()      // reads 0.
  ...
  // We have omitted the third through tenth queries in this example.
  ...
  printline 1 to stdout   // we decide to ask about position 1 again.
  flush stdout
  // Since this is our 11th query, and 11 is 1 mod 10, the judge secretly and
  // randomly chooses a quantum fluctuation effect, and happens to get
  // reversal, so now the stored value is 1110010000.
  r = readline_chr()      // reads 1.
  printline 1110110000 to stdout   // we try to answer. why?!?!
  flush stdout
  ok = readline_chr()     // reads N -- we have made a mistake!
  exit                    // exits to avoid an ambiguous TLE error