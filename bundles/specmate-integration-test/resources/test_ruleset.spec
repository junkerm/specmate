import EN.DEP.STANFORD.*
import EN.POS.PTB.*

def subtrees Cause, Effect, Subject, Predicate

//  If the tool detects an error then the tool beeps.
def rule Condition {
	[Effect] - advcl -> [Cause]	- mark -> (IN:CASE!'if'|IN:CASE!'If')
}

def rule SubjPred {
	[Predicate] - nsubj -> [Subject]
}