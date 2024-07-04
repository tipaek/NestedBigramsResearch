package esab;
import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int t =0; t<T; t++){
            System.out.println(t%10);
        }
    }
}
from __future__ import print_function
import sys, subprocess, threading

class SubprocessThread(threading.Thread):
  def __init__(self,
               args,
               stdin_pipe=subprocess.PIPE,
               stdout_pipe=subprocess.PIPE,
               stderr_prefix=None):
    threading.Thread.__init__(self)
    self.stderr_prefix = stderr_prefix
    self.p = subprocess.Popen(
        args, stdin=stdin_pipe, stdout=stdout_pipe, stderr=subprocess.PIPE)

  def run(self):
    try:
      self.pipeToStdErr(self.p.stderr)
      self.return_code = self.p.wait()
      self.error_message = None
    except (SystemError, OSError):
      self.return_code = -1
      self.error_message = "The process crashed or produced too much output."

  # Reads bytes from the stream and writes them to sys.stderr prepending lines
  # with self.stderr_prefix.
  # We are not reading by lines to guard against the case when EOL is never
  # found in the stream.
  def pipeToStdErr(self, stream):
    new_line = True
    while True:
      chunk = stream.readline(1024)

      if not chunk:
        return

      chunk = chunk.decode("UTF-8")

      if new_line and self.stderr_prefix:
        chunk = self.stderr_prefix + chunk
        new_line = False

      sys.stderr.write(chunk)

      if chunk.endswith("\n"):
        new_line = True

      sys.stderr.flush()


assert sys.argv.count("--") == 1, (
    "There should be exactly one instance of '--' in the command line.")
sep_index = sys.argv.index("--")
judge_args = sys.argv[1:sep_index]
sol_args = sys.argv[sep_index + 1:]

t_sol = SubprocessThread(sol_args, stderr_prefix="  sol: ")
t_judge = SubprocessThread(
    judge_args,
    stdin_pipe=t_sol.p.stdout,
    stdout_pipe=t_sol.p.stdin,
    stderr_prefix="judge: ")
t_sol.start()
t_judge.start()
t_sol.join()
t_judge.join()

# Print an empty line to handle the case when stderr doesn't print EOL.
print()
print("Judge return code:", t_judge.return_code)
if t_judge.error_message:
  print("Judge error message:", t_judge.error_message)

print("Solution return code:", t_sol.return_code)
if t_judge.error_message:
  print("Solution error message:", t_sol.error_message)

from __future__ import print_function

import itertools
import random
import sys

# Use raw_input in Python2.
try:
  input = raw_input
except NameError:
  pass

MAX_QUERIES = 150
NUM_CASES = 100

_ERROR_MSG_EXTRA_NEW_LINES = 'Input has extra newline characters.'
_ERROR_MSG_INVALID_CHARACTER = 'Input contains character other than 0 and 1.'
_ERROR_MSG_INVALID_INPUT = 'Input is neither a number or a string with correct length.'
_ERROR_MSG_INPUT_OUT_OF_RANGE = 'Input position is out of range.'
_ERROR_MSG_READ_FAILURE = 'Read for input fails.'
_ERROR_MSG_WRONG_ANSWER_FORMAT_STR = 'Wrong answer: contestant input {}, but answer is {}.'
_ERROR_MSG_MAX_QUERIES_EXCEED = 'Contestant tries to query too many times.'

_CORRECT_MSG = 'Y'
_WRONG_ANSWER_MSG = 'N'


class IO(object):

  def ReadInput(self):
    return input()

  def PrintOutput(self, output):
    print(output)
    sys.stdout.flush()

  def SetCurrentCase(self, case):
    pass


def Reverse(s):
  return s[::-1]


def BitFlip(s):
  return ''.join(str(1 - int(c)) for c in s)


class JudgeSingleCase(object):

  def __init__(self, io, initial_arr):
    self.io = io
    self.io.SetCurrentCase(self)

    self.arr = initial_arr
    self.len = len(self.arr)

  def _ParseContestantInput(self, response):
    """Parses contestant's input.
    Parses contestant's input, which should be a number between 1 and self.len,
    or a string of length exactly self.len which contains only 0 and 1.
    Args:
      response: (str) one-line input given by the contestant.
    Returns:
      A int or str of the contestant's input.
      Also, an error string if input is invalid, otherwise None.
    """
    if ('\n' in response) or ('\r' in response):
      return None, _ERROR_MSG_EXTRA_NEW_LINES

    if len(response) == self.len:
      if any(c not in '01' for c in response):
        return None, _ERROR_MSG_INVALID_CHARACTER
      return response, None

    try:
      num = int(response)
      if not 1 <= num <= self.len:
        return None, _ERROR_MSG_INPUT_OUT_OF_RANGE
      return num, None
    except ValueError:
      return None, _ERROR_MSG_INVALID_INPUT

  def _ReadContestantInput(self):
    """Reads contestant's input.
    Reads contestant's input,  which should be a number between 1 and self.len,
    or a string of length exactly self.len which contains only 0 and 1.
    Returns:
      A int or str of the contestant's input.
      Also, an error string if input is invalid, otherwise None.
    """
    try:
      contestant_input = self.io.ReadInput()
    except Exception:
      return None, _ERROR_MSG_READ_FAILURE

    return self._ParseContestantInput(contestant_input)

  def Judge(self):
    """Judges one single case; should only be called once per test case.
    Returns:
      An error string if an I/O rule was violated or the answer was incorrect,
      otherwise None.
    """
    for i in range(MAX_QUERIES + 1):
      contestant_input, err = self._ReadContestantInput()
      if err is not None:
        return err

      if isinstance(contestant_input, str):
        if self.arr != contestant_input:
          return _ERROR_MSG_WRONG_ANSWER_FORMAT_STR.format(
              contestant_input[:2 * self.len], self.arr)
        self.io.PrintOutput(_CORRECT_MSG)
        return None

      if i == MAX_QUERIES:
        return _ERROR_MSG_MAX_QUERIES_EXCEED

      if i % 10 == 0:
        # Number of queries we've received ends with 1
        if random.randint(0, 1):
          self.arr = Reverse(self.arr)
        if random.randint(0, 1):
          self.arr = BitFlip(self.arr)
      self.io.PrintOutput(self.arr[contestant_input - 1])


def RandomBitString(b):
  return ''.join(str(random.randint(0, 1)) for _ in range(b))


def GenerateInputs(b):
  assert b in (10, 20, 100)

  cases = set()

  # Add your own cases here.
  # The one included here is just an example and is not necessarily part of
  # any real test set.
  cases.add('1' * b)

  while len(cases) < NUM_CASES:
    cases.add(RandomBitString(b))

  cases = list(cases)
  random.shuffle(cases)
  assert len(cases) == NUM_CASES
  assert all(len(case) == b for case in cases)
  assert all(all(c in '01' for c in case) for case in cases)
  return cases


def JudgeAllCases(test_number, io):
  """Sends input to contestant and judges contestant output.
  Returns:
    An error string, or None if the attempt was correct.
  """
  b = (10, 20, 100)[test_number]
  inputs = GenerateInputs(b)

  io.PrintOutput('{} {}'.format(NUM_CASES, b))
  for case_number in range(NUM_CASES):
    single_case = JudgeSingleCase(io, inputs[case_number])
    err = single_case.Judge()
    if err is not None:
      return 'Case #{} fails:\n{}'.format(case_number + 1, err)

  # Make sure nothing other than EOF is printed after all cases finish.
  try:
    response = io.ReadInput()
  except EOFError:
    return None
  except Exception:  # pylint: disable=broad-except
    return 'Exception raised while reading input after all cases finish.'
  return 'Additional input after all cases finish: {}'.format(response[:1000])


def main():
  try:
    test_number = int(sys.argv[1])
    assert test_number in (0, 1, 2)
    # Remember that the local testing tool is not guaranteed to implement
    # randomness in the same way as the actual judge.
    random.seed(123456 + test_number)
    io = IO()
    result = JudgeAllCases(test_number, io)
    if result is not None:
      print(result, file=sys.stderr)
      io.PrintOutput(_WRONG_ANSWER_MSG)
      sys.exit(1)
  except Exception as exception:
    # Hopefully this will never happen, but try to finish gracefully
    # and report a judge error in case of unexpected exception.
    io.PrintOutput(_WRONG_ANSWER_MSG)
    print('JUDGE_ERROR! Internal judge exception:', file=sys.stderr)
    print(str(exception)[:1000], file=sys.stderr)
    sys.exit(1)


if __name__ == '__main__':
  main()