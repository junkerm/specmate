import EN.DEP.STANFORD.*
import EN.POS.PTB.*

def subtrees Limit, Conditional

def rule LimitedCondition_1 {
	[Limit] - nsubjpass -> [Conditional] - prep -> IN:'until'
}

def rule LimitedCondition_2 {
	[Limit] - csubjpass -> [Conditional] - prep -> IN:'until'
}


def subtrees Cause, Effect, TMP

//  If the tool detects an error then the tool beeps.
def rule Condition1_1 {
	[Effect] - advcl -> [Cause]	- mark -> IN:'if'
	[Effect] - advmod -> RB: 'then'
}

def rule Condition1_2 {
	[Effect] - dep -> [Cause] - mark -> IN:'if'
	[Effect] - advmod -> RB: 'then'
}

def rule Condition1_3 {
	[Effect] - dobj -> [Cause] - mark -> IN:'if'
	[Effect] - advmod -> RB: 'then'
}

//  If/Because/Although the tool detects an error, the tool beeps.
//  The tool beeps if/because/although the tool detects an error.
def rule Condition1_4 {
	[Effect] - advcl -> [Cause] - mark -> IN:'(if)|(because)|(although)'
}

def rule Condition1_5 {
	[Effect] - dep -> [Cause] - mark -> IN:'(if)|(because)|(although)'
}

// When the tool detects an error then the tool beeps.
def rule Condition2_1 {
	[Cause] - ccomp -> [Effect]
	[Cause] - advmod -> WRB:'when'
	[Cause] - advmod -> RB: 'then'
}

def rule Condition2_2 {
	[Cause] - dobj -> [Effect]
	[Cause] - advmod -> WRB:'when'
	[Cause] - advmod -> RB: 'then'
}

def rule Condition2_3 {
	[Cause] - advcl -> [Effect]
	[Cause] - advmod -> RB: 'then'
	[Cause] - advmod -> WRB:'when'
}

// When the tool detects an error, the tool beeps.
def rule Condition2_4 {
	[Effect] - dep -> [Cause] - advmod -> WRB:'when'
}
// The tool beeps when the tool detects an error.
def rule Condition2_5 {
	[Effect] - advcl -> [Cause] - advmod -> WRB:'when'
}

def rule Condition2_6 {
	[Cause] - advmod -> WRB: 'when'
	[Cause] - parataxis -> [Effect]
}

// The tool detects an error for this reason the tool beeps .
def rule Condition3_1 {
	[Cause] - advcl -> [Effect] - mark -> IN:'for'
	[Effect] - nsubj -> NN:'reason' - det -> DT:'this'
}

// The tool detects an error as a result the tool beeps.
def rule Condition4_1 {
	[Cause] - advcl -> [Effect] - mark -> IN:'as'
	[Effect] - nsubj -> NN:'result' - det -> DT:'a'
}

// Specmate shows the error window as a result of invalid login data.
def rule Condition4_2 {
	[Cause] - dobj -> [TMP] - prep -> IN:'as' - pobj -> NN:'result' - prep -> IN:'of' - pobj -> [Effect]
}

// Specmate shows the error window as a result of invalid login data.
def rule Condition4_2 {
	[Cause] - prep -> IN:'as' - pobj -> NN:'result' - prep -> IN:'of' - pobj -> [Effect]
}

// The tool beeps due to the tool detecting an error.
def rule Condition5_1 {
	[Cause] - amod -> JJ:'due' - prep -> TO:'to' - pobj -> [Effect]
}
// Due to the tool detecting an error, the tool beeps.
def rule Condition5_2 {
	JJ:'due':[TMP] - prep -> TO:'to' - pobj -> [Cause]
	[TMP] - dep -> [Effect]
}

// The tool beeps owning to the tool detecting an error.
def rule Condition6_1 {
	VBG:'owning':[TMP] - prep -> TO:'to' - pobj -> [Cause]
	[TMP] - nsubj -> [Effect]
}

// Owning to the tool detecting an error, the tool beeps.
def rule Condition6_2 {
	[Effect] - dep -> VBG:'owning':[TMP] - prep -> TO:'to' - pobj -> [Cause]
}

// The tool beeps provided/supposing that the tool detected an error.
def rule Condition7_1 {
	[Effect] - partmod -> (VBN:'provided'|VBG:'supposing') -ccomp -> [Cause] - complm -> IN:'that' 
}
// Supposing that the tool detected an error, the tool beeps .
def rule Condition7_2 {
	[Effect] -dep-> VBG:'supposing' -ccomp-> [Cause] -complm-> IN:'that'
}

// Provided that the tool detected an error, the tool beeps.
// Provided that the tool crashed, the tool beeps.
def rule Condition7_3 {
	VBN:'provided'  - ccomp -> [Cause] - dobj -> [Effect]
	[Cause] - complm -> IN:'that'
}

// The tool beeping has something/(a lot) to do with it detecting an error.
def rule Condition8_1 {
	VBZ:*:[TMP] - nsubj -> [Effect]
	[TMP] - dobj -> NN:"(something)|lot" - infmod -> VB:"do" - prep -> IN:'with' - pobj -> [Cause]
}

// The tool detects an error so that it can report it.
def rule Condition9_1 {
	[Cause] - dep -> [Effect] - dep -> IN:'that'
	[Effect] - advmod -> RB:'so'
}
// The tool detects an error so to report it.
def rule Condition9_2 {
	[Cause] - advmod -> RB:'so'
	[Cause] - xcomp -> [Effect] - aux -> TO:'to'
}


// The tool detects an error in order that it can report it. 
def rule Condition10_1 {
	[Cause] - prep -> IN:'in' - pobj -> NN:'order' - ccomp ->  [Effect] - complm -> IN:'that'
}
// In order that the tool can report an error, the tool detects errors. 
def rule Condition10_2 {
	[Cause] - prep -> IN:'in' - pobj -> NN:'order' 
	[Cause] - advcl ->  [Effect] - complm -> IN:'that'
}
// The tool detects an error in order to report it.
def rule Condition10_3 {
	[Cause] - advcl -> [Effect]
	[Effect] - mark -> IN:'in'
	[Effect] - dep -> NN:'order'
	[Effect] - aux -> TO:'to'
}

// The tool beeps even though the tool detected an error .
def rule Condition11_1 {
	[Cause] - nsubj -> [Effect]
	[Cause] - advmod -> RB:'even'
	[Cause] - dep -> RB:'though'
}
// Even though the tool detected an error, the tool beeps.
// Even though the tool crashed, the tool beeps.
def rule Condition11_2 {
	[Effect] - advcl -> [Cause] - advmod -> RB:'even'
	[Cause] - dep -> IN:'though'
}

// The tool beeps in case that the tool detected an error.
def rule Condition12_1 {
	[Effect] - ccomp -> [Cause] - complm -> IN:'that'
	[Effect] - prep -> IN:'in' - pobj -> NN:'case'
}

// In case that the tool detected an error, the tool beeps.
// In case that the tool crashes, the tool beeps.
def rule Condition12_2 {
	IN:'in':[TMP] - pobj -> NN:'case'
	[TMP] - dep -> IN:'that' - pobj -> [Cause] - appos -> [Effect]
}

// The tool beeps on the condition that the tool detected an error .
def rule Condition13_1 {
	[Effect] - ccomp -> [Cause] - complm -> IN:'that'
	[Effect] - prep -> IN:'on' - pobj -> NN:'condition'
}

// On the condition that the tool detected an error, the tool beeps.
// On the condition that the tool crashes, the tool beeps.
def rule Condition13_2 {
	IN:'on' - pobj -> NN:'condition' - prep -> IN:'that' - pobj -> [Cause] - appos -> [Effect]
}
// TODO "Any child in this tree may have a subtree X"

// The tool detects an error. For this reason the tool beeps.
//def rule DanglingConditional1 {
//	IN:'for' - pobj -> [Effect] - nsubj -> NN:'reason'
//}

// The tool detects an error. As a result the tool beeps.
//def rule DanglingConditional2 {
//	[Effect] - mark -> IN:'as'
//	[Effect] - nsubj -> NN:'result'
//}

def subtrees  PartA, PartB, Head, Head_tmp
def subtrees  PartA_SubA, PartB_SubA

def rule Conjunction_NOR_1 {
	[PartA] - preconj -> CC:'neither'
	[PartA] - cc -> CC: 'nor'
	[PartA] - conj -> [PartB]
}

def rule Conjunction_NOR_2 {
	[PartA] - preconj -> CC:'neither'
	[PartA] - cc -> CC: 'nor'
	[PartA] - conj -> [PartB]
}

def rule Conjunction_XOR {
	[PartA] - advmod -> RB:'either'
	[PartA] - cc -> CC: 'or'
	[PartA] - conj -> [PartB]
}

def rule Conjunction_OR {
	[PartA] - cc -> CC:'or'
	[PartA] - conj -> [PartB]
}

def rule Conjunction_OR_2 {
	[PartA] - ccomp -> [PartB]  - nsubj -> [PartA_SubA] - conj -> [PartB_SubA]
	 [PartA_SubA] - cc -> CC:'or'
}

def rule Conjunction_AND_1 {
	[PartA] - cc -> CC:'and'
	[PartA] - conj -> [PartB]
	[PartA] - preconj -> DT:'both'
}

def rule Conjunction_AND_2 {
	[PartA] - cc -> CC:'and'
	[PartA] - conj -> [PartB]
}

def rule Conjunction_AND_3 {
	[PartA] - ccomp -> [PartB]  - nsubj -> [PartA_SubA] - conj -> [PartB_SubA]
	[PartA_SubA] - cc -> CC:'and'
}

def rule Conjunction_AND_4 {
	[PartA] - dobj -> [PartA_SubA]  - conj -> [PartB]
	[PartA_SubA] - cc -> CC:'and'
}


def rule Negation {
	[Head] - neg -> RB:*
}

def rule Negation_2 {
	[Head] -dobj-> [Head_tmp] - neg -> DT:*
}

def rule Negation_3 {
	[Head] - dobj-> [Head_tmp] - det -> DT:'no'
}

def subtrees Variable, Condition, Variable_Sub

def rule CondVar_1 {
	[Condition] - nsubj -> [Variable]
}

def rule CondVar_2 {
	[Condition] - nsubjpass -> [Variable]
}

//def rule CondVar_3 {
//	[Condition] - nn -> [Variable]
//	[Condition] - det -> [Variable_Sub]
//}

//def rule CondVar_4 {
//	[Condition] - nn -> [Variable]
//}

def subtrees Verb, Object

def rule VerbObject {
	[Verb] - dobj -> [Object]
}