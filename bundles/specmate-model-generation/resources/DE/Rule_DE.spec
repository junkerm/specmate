import DE.DEP.CDG.*
import DE.POS.STTS.*

def subtrees Cause, Effect, Cause_SubA, Effect_SubA, TMP, TMP_SubA

// Wenn/Falls das Werkzeug einen Fehler erkennt, zeigt es ein Fenster an.
// Das Werkzeug zeigt ein Fenster an, wenn es einen Fehler erkennt.
// Weil das Werkzeug einen Fehler erkennt, zeigt es ein Fenster an.
// Ein Fenster wird vom Werkzeug angezeigt, wenn es einen Fehler erkennt.
def rule Condition1_1 {
	[Effect] - NEB -> [Cause] - KONJ -> KOUS:'(wenn)|(falls)|(da)'
	[Effect] - ADV -> ADV:'(so)|(dann)'	
}

def rule Condition1_2 {
	[Effect] - NEB -> [Cause] - KONJ -> KOUS:'(wenn)|(falls)|(weil)|(sofern)|(obwohl)|(obgleich)|(obschon)|(obzwar)|(wenngleich)|(wiewohl)'	
}

def rule Condition1_3 {
	[Effect] - OBJC -> [Cause] - KONJ -> KOUS:'(wenn)|(falls)|(weil)|(sofern)|(damit)|(obwohl)|(obgleich)|(obschon)|(obzwar)|(wenngleich)|(wiewohl)'	
}

// Das Werkzeug zeigt ein Fenster an, weil es einen Fehler erkannt hat.
// Das Werkzeug zeigt ein Fenster an, sofern es einen Fehler findet.
def rule Condition1_4 {
	[Effect] - OBJC -> [Cause] - KONJ -> KOUS:'(weil)|(sofern)|(obwohl)|(obgleich)|(obschon)|(obzwar)|(wenngleich)|(wiewohl)'	
}

def rule Condition1_5 {
	[Effect] - OBJC -> [Cause] - SUBJ -> [Cause_SubA] - ADV -> 'obzwar' 
}

def rule Condition1_6 {
	[Cause] - NEB -> [Effect] - KONJ -> KOUS:'damit'	
}

def rule Condition1_7 {
	[Cause] - SUBJC -> [Effect] - SUBJ -> [Effect_SubA] - PP -> 'damit'	
}

// Für den Fall dass der Benutzer die Schaltfläche drückt lädt Specmate das Modell.
def rule Condition19 {
	[Effect] - PP -> 'für' - PN -> 'Fall' - OBJC -> [Cause] - KONJ -> 'dass'
}

// Unter der Voraussetzung dass das Modell korrekt ist speichert das Tool das Modell.
def rule Condition22 {
	[Effect] - PP -> 'unter' - PN -> 'Voraussetzung' - OBJC -> [Cause] - KONJ -> 'dass':[TMP]
}

// Unter der Voraussetzung dass das Modell korrekt ist speichert das Tool das Modell.
def rule Condition23 {
	[Cause] - OBJA -> [Effect] - REL -> [Effect_SubA] - ADV -> 'weswegen'
}

// Ein Klick auf die Schaltfläche hat zur Folge dass ein Backend-Aufruf ausgeführt wird.
def rule Condition24 {
	'hat':[TMP] - NEB -> [Effect]
	'hat':[TMP] - SUBJ -> [Cause]
	'hat':[TMP] - PP -> 'zur' - PN -> 'Folge'
	[Effect] - KONJ -> 'dass':[TMP_SubA]
}

// Gesetzt den Fall dass die Validierung erfolgreich ist wird die Speicher-Schaltfläche aktiviert.
def rule Condition24 {
	[Effect] - SUBJ -> 'Fall':[TMP] - OBJC -> [Cause]  
	'Fall':[TMP] - ADV -> 'gesetzt'
	[Cause] - KONJ -> 'dass'
}

// Infolge einer falschen Eingabe wird ein Hinweis angezeigt.
def rule Condition25 {
	[Effect] - PP -> 'infolge' - PN -> [Cause]
}

// Ein Fehler tritt auf aufgrund dessen Specmate abstürzt.
def rule Condition26 {
	[Cause] - KON -> [Effect] - PP -> 'aufgrund'
}

// Specmate zeigt das Modell an nachdem der Nutzer den Link angeklickt hat.
def rule Condition27 {
	[Effect] - OBJC -> [Cause] - KONJ -> 'nachdem'
}

// Das Werkzeug zeigt ein Fenster an, unter der Bedingung, dass es einen Fehler findet.
// Für den Fall, das Werkzeug einen Fehler gefunden hat, zeigt es einen Fehler an.
def rule Condition2_1 {
	[Effect] - NEB -> [Cause] - PP -> (APPR:'unter'|APPR:'für') - PN -> NN:CASE!'(Bedingung)|(Fall)|(Voraussetzung)|(Annahme)' - DET -> (ART:'der'|ART:'den')
}

// Das Werkzeug erkennt einen Fehler, weswegen es ein Fenster anzeigt.
def rule Condition2_2 {
	[Effect] - NEB -> [Cause] - PP -> PWAV:'weswegen'	
}

// Für den Fall dass der Benutzer die Schaltfläche drückt lädt Specmate das Modell.
def rule Condition21 {
	[Effect] - PP -> 'unter' - PN -> 'Bedingung' - OBJC -> [Cause] - KONJ -> 'dass'
}

def rule Condition2_3 {
	[Effect] - NEB -> [Cause] 
	[Effect] - PP -> (APPR:'unter'|APPR:'für') - PN -> NN:CASE!'(Bedingung)|(Fall)|(Voraussetzung)|(Annahme)' - DET -> (ART:'der'|ART:'den')
}

// Wenn entweder das Haus brennt oder das Haus einstürzt, evakuiere das Büro.
def rule Condition2_4 {
	[Effect] - KONJ -> KOUS:'wenn' - KON -> [Cause] 
}

// Das Werkzeug erkennt einen Fehler, dies hat zur Folge, dass es ein Fenster anzeigt.
def rule Condition3_1 {
	[Cause] - KON -> VAFIN:'hat'- NEB -> [Effect]	
}
// Das Werkzeug erkennt einen Fehler, dies hat das Anzeigen eines Fensters zur Folge.
def rule Condition3_2 {
	[Cause] - KON -> VAFIN:'hat'- OBJA -> [Effect] - PP -> APPRART:'zur'	
}


// Das Werkzeug zeigt ein Fenster an, gesetzt den Fall das es einen Fehler gefunden hat.
def rule Condition4_1 {
	[Effect] - NEB -> [Cause] - AUX -> VVPP:'gesetz(t?)'
	[Cause] - OBJA -> NN:CASE!'Fall'
}
// Gesetzt den Fall das das Werkzeug einen Fehler gefunden hat, zeigt es einen Fehler an.
def rule Condition4_2 {
	[Effect] - OBJC -> [Cause] - ADV -> VVPP:'gesetz(t?)'
	[Cause] - SUBJ -> NN:CASE!'Fall'
}

// Das das Werkzeug ein Fenster anzeigt hat etwas damit zu tun, dass es einen Fehler gefunden hat.
def rule Condition5 {
	VAFIN:'hat':[TMP] - NEB -> [Cause]
	[TMP] - S -> [Effect]
}

// Erkennt das Werkzeug einen Fehler, so zeigt es ein Fenster an.
def rule Condition6_1 {
	[Effect] - NEB -> [Cause]
	[Effect] - ADV -> ADV:'(so)|(dann)'	
}

// Der Fehler tritt auf, so dass Specmate abstürzt.
def rule Condition6_2 {
	[Cause] - NEB -> [Effect]	
	[Effect] - KONJ -> 'dass' - ADV -> 'so'
}

// Da der Benutzer auf den Link klickt, öffnet Specmate das Modell.
def rule Condition6_3 {
	[Effect] - NEB -> [Cause]	
	[Cause] - KONJ -> 'da'
}

// Angenommen die Verbindung schlägt fehl wird eine lokale Version geladen.
def rule Condition6_4 {
	[Effect] - NEB -> [Cause] - ADV -> 'Angenommen':[TMP]
}

// Wird ein Fehler vom Werkzeug erkannt, wird ein Fenster von ihm angezeigt.
// Sollte ein Fehler vom Werkzeug erkannt werden, zeigt es ein Fenster an.
def rule Condition6_5 {
	[Effect] - NEB -> [Cause]	
}


// Aufgrund, das das Werkzeug einen Fehler erkennt, zeigt es ein Fenster an.
def rule Condition7 {
	[Cause] - SUBJ -> * - PP -> APPR:'aufgrund'
	[Cause] - S -> [Effect]
}

// Das Werkzeug erkennt einen Fehler, (wegen dem)/(infolge dessen) es ein Fenster anzeigt.
def rule Condition8 {
	[Cause] - OBJA -> * - REL -> [Effect] - PP -> APPR:'(wegen)|(infolge)'
}

// Unter der Bedingung/Voraussetzung/Annahme, dass das Werkzeug einen Fehler findet, zeigt es einen Fehler an.
def rule Condition9 {
	[Effect] - PP -> APPR:'unter' - PN -> NN:CASE!'(Bedingung)|(Annahme)|(Voraussetzung)' - REL -> [Cause]
}

// Infolge des vom Werkzeug erkannten Fehlers, zeigt es ein Fenster an.
def rule Condition10 {
	[Effect] - PP -> APPR:'infolge' - PN -> [Cause] - DET -> ART:'des' 	
}

// Das Werkzeug erkennt einen Fehler, aufgrund dessen es ein Fenster anzeigt.
def rule Condition11_1 {
	[Cause] - PP -> APPR:'(aufgrund)' 
	[Cause] - KON -> [Effect] 	
}
// Das Werkzeug zeigt einen Fehler, dank des Erkennens eines Fehlers durch das Werkzeug, an.
// Wegen des vom Werkzeug erkannten Fehlers, zeigt es ein Fenster an.
def rule Condition11_2 {
	[Effect] - PP -> APPR:'(aufgrund)|(dank)|(wegen)' - PN -> [Cause] 	
}

def rule Condition11_3 {
	[Effect] - OBJA -> [Effect_SubA] - PP -> '(aufgrund)|(dank)|(wegen)' - PN -> [Cause] 	
}

// Das Anzeigen eines Fensers durch das Werkzeug, ist zurückzuführen auf das Erkennen eines Fehlers durch das Werkzeug.
def rule Condition12_1 {
	VAFIN:'ist':[TMP] - AUX -> VVIZU:'zurückzuführen' - PP -> [Cause]
	[TMP] - SUBJ -> [Effect]
}
// Zurückzuführen auf das werkzeugliche Erkennen eines Fehlers, ist das Anzeigen eines Fensters durch das Werkzeug.
def rule Condition12_2 {
	[Effect] - PRED -> VVIZU:'zurückzuführen' - PP -> [Cause]
}

// Das Werkzeug erkennt einen Fehler, so dass/damit es ein Fenster anzeigt.
def rule Condition13_1 {
	[Cause] - KON -> [Effect] - KONJ -> KOUS:'(das(s?))|(damit)'
	[Effect] - ADV -> ADV:'so'
}

def rule Condition13_2 {
	[Cause] - KON -> [Effect] - KONJ -> KOUS:'(das(s?))|(damit)'
}

// Im Fall, das das Werkzeug einen Fehler erkennt, zeigt es ein Fenster an.
def rule Condition14_1 {
	[Effect] - PP -> (APPRART:'im'|APPR:'für') - PN -> NN:CASE!'Fall' - REL -> [Cause]
}

def rule Condition14_2 {
	[Effect] - PP -> APPRART:'im' - PN -> NN:CASE!'Fall' 
	[Effect] - KON -> [Cause]
}

// Für alle Computer mit deutscher Tastertur gibt es eine Dokumentation.
def rule Condition15 {
	[Effect] - PP -> APPR:'für' - PN -> [Cause] - DET -> PIAT:'alle'
}

// Der Benutzer klickt auf die Schaltfläche, aus diesem Grund öffnet Specmate das Modell.
def rule Condition16 {
	[Cause] - KON -> [Effect]
	[Effect] - PP -> 'aus' - PN -> 'Grund' - DET -> 'diesem'
}

// Specmate zeigt das Fehlerfenster als Ergebnis ungültiger Login-Daten an.
def rule Condition17 {
	[Effect] - KOM -> 'als' - CJ -> 'Ergebnis' - GMOD -> [Cause] 
}

// Specmate zeigt das Fehlerfenster als Ergebnis ungültiger Login-Daten an.
def rule Condition18 {
	[Effect] - PP -> 'wegen' - PN -> [Cause] 
}

// Das Problem hat etwas/viel mit der Verbindung zu tun.
def rule Condition20 {
	'hat':[TMP] - SUBJ -> [Effect] 
	[TMP] - AUX -> 'tun' - PP -> 'mit' - PN -> [Cause]
}
//	[TMP] - AUX -> 'tun' - ADV -> '(etwas|viel)'
//	[TMP] - AUX -> 'tun' - PART -> 'zu'






// TODO Das Werkzeug zeigt ein Fenster an, auch wenn das Werkzeug einen Fehler findet.
// TODO Auch wenn das Werkzeug einen Fehler findet, zeigt das Werkzeug einen Fehler an.
//def rule Condition16{
//	[Effect] - ADV -> ADV:'auch' - UNKOWN -> [Cause] - KONJ -> KOUS:'wenn'
//}


// Das Werkzeug erkennt einen Fehler. Aus diesem Grund zeigt es ein Fenster.
//def rule DanglingConditional1 {
//	[Effect] - PP -> APPR:'aus' - PN -> NN:CASE!'Grund' - DET -> PDAT:'diesem' 
//}

// Das Werkzeug erkennt einen Fehler. Dies hat zur Folge, dass es ein Fenster anzeigt.
//def rule DanglingConditional2_1 {
//	VAFIN:'hat' - NEB -> [Effect] 
//}

// Das Werkzeug erkennt einen Fehler. Dies hat das Anzeigen eines Fensters zur Folge.
//def rule DanglingConditional2_2 {
//	VAFIN:'hat' - OBJA-> [Effect] 
//}

def subtrees  PartA, PartB
def subtrees  PartA_SubA, PartA_SubB, PartB_SubA

def rule Conjunction_XOR_1 {
	KON:'entweder' - CJ -> [PartA] - KON -> KON: 'oder' - CJ -> [PartB]
}

def rule Conjunction_XOR_2 {
	[PartA] - KON -> KON:'entweder' 
	[PartA] - KON -> KON: 'oder' - CJ -> [PartB]
}

def rule Conjunction_OR {
	[PartA] - KON -> KON: 'oder' - CJ -> [PartB]
}

def rule Conjunction_AND_1 {
	[PartA] - KON -> KON: 'und' - CJ -> [PartB]
}

def rule Conjunction_AND_2 {
	[PartA] - AUX -> [PartA_SubA] - KON -> KON: 'und' - CJ -> [PartB]
}

def rule Conjunction_AND_3 {
	[PartA] - KON -> KON: 'sowohl' 
	[PartA] - KOM -> KOKOM:'als' - CJ -> [PartB] - ADV -> ADV:'auch'
}

def rule Conjunction_NOR {
	[PartA] - SUBJ -> [PartA_SubA] - KON -> KON:'weder' - CJ -> [PartA_SubB]
	[PartA] - REL  -> [PartB] - SUBJ -> [PartB_SubA] - ADV -> ADV:'noch'
}

def subtrees  Head, Head_SubA, Head_SubB

def rule Negation_1 {
	[Head] - AUX -> [Head_SubA] - ADV -> 'nicht' 
}

def rule Negation_2 {
	[Head] - OBJA -> [Head_SubA] - DET -> 'kein(en?)?':[Head_SubB] 
}

def rule Negation_3 {
	[Head] - ADV -> 'nicht' 
	[Head] - SUBJ -> [Head_SubB]
}

def subtrees Variable, Condition

def rule CondVar {
	[Condition] - SUBJ -> [Variable]
}

def rule CondVar_2 {
	[Variable] - REL ->[Condition]
}

def subtrees Verb, Verb_SubA, Object

def rule VerbObject_1 {
	[Verb] - OBJA -> [Object] - GMOD -> [Verb_SubA]
}

def rule VerbObject_2 {
	[Verb] - OBJA -> [Object]
}

def rule VerbObject_3 {
	[Verb] - AUX -> [Verb_SubA] - OBJA -> [Object]
}

def subtrees Preposition

def rule VerbPreposition {
	[Verb] - PP -> [Preposition]
}