module RegexEngine

data Regex = ...
data MachineState = ...

type Pattern = String

compile :: Pattern -> Regex
  tokenize :: Pattern -> [Token]
  parse :: [Token] -> RegexTree
  construct :: RegexTree -> Regex

test :: Regex -> String -> Bool
  step :: MachineState -> Char -> MachineState
  isFinalState :: MachineState -> Bool
