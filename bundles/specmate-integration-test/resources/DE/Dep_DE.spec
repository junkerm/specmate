def tagset dependencies DE.DEP.NEGRA {
	/* Adpositional case marker - adposition od. kasusmarkierung */
	AC
	/* Adjective component - komponente eines komplexen adjektivs */
	ADC
	/* Measure argument of adjective - massangabe bei adjektiv */
	AMS
	/* Apposition - apposition */
	APP
	/* Adverbial phrase component */
	AVC
	/* Comparative complement - vergleichskomplement */
	CC
	/* Coordinating conjunction */
	CD
	/* Conjunct - konjunkt */
	CJ
	/* Comparative conjunction - komparationskonjunkt (als, wie) */
	CM
	/* Complementizer - complementizer */
	CP
	/* Dative - dativobjekt oder freier dativuby: gf\=complement, case\=dative */
	DA
	/* Discourse-level head */
	DH
	/* Discourse marker - diskurspartikel (ja, nein) */
	DM
	/* Junctor - junktor */
	JU
	/* Modifier of np to the right - postnominaler modifikator */
	MNR
	/* Modifier - modifikator */
	MO
	/* Negation - negation nicht */
	NG
	/* Noun kernel modifier */
	NK
	/* Numerical component - numeralien */
	NMC
	/* Object accusative - akkusativobjektuby: gf\=directobject, case\=accusative */
	OA
	/* Object accusative 2 - zweites akkusativobjektuby: gf\=objectcomplement */
	OA2
	/* Object clausal - klausales objektuby: gf\=directobject or complement, sc\=subordinateclause or declarativeclause */
	OC
	/* Object genitive - genitivobjektuby: gf\=complement, case\=genitive */
	OG
	/* Predicative - prädikativ */
	PD
	/* Pseudo-genitive */
	PG
	/* Placeholder - platzhalter */
	PH
	/* Morphological particle - morphologische partikel (am) */
	PM
	/* Proper noun component */
	PNC
	/* Relative clause - relativsatz */
	RC
	/* Repeated element */
	RE
	/* Reported speech */
	RS
	/* Subject - subjektuby: gf\=subject */
	SB
	/* Subject passivised - (logisches) subjekt im passivsatz */
	SBP
	/* Subject or predicative - subjekt oder prädikativ */
	SP
	/* Separable verb prefix - abgetrennter verbpräfix */
	SVP
	/* (idiosyncratic) unit component */
	UC
	/* Vocative - anrede */
	VO
}

def tagset dependencies DE.DEP.TIGER {
	/* Adpositional case marker - adposition od. kasusmarkierung */
	AC
	/* Adjective component - komponente eines komplexen adjektivs */
	ADC
	/* Attribute, genitive - genitivattribut */
	AG
	/* Measure argument of adjective - massangabe bei adjektiv */
	AMS
	/* Apposition - apposition */
	APP
	/* Comparative complement - vergleichskomplement */
	CC
	/* Coordinating conjunction */
	CD
	/* Conjunct - konjunkt */
	CJ
	/* Comparative conjunction - komparationskonjunkt (als, wie) */
	CM
	/* Complementizer - complementizer */
	CP
	/* Collocational verb construction - funktionsverbgefüge */
	CVC
	/* Dative - dativobjekt oder freier dativuby: gf\=complement, case\=dative */
	DA
	/* Discourse marker - diskurspartikel (ja, nein) */
	DM
	/* Expletive - expletives esuby: sc\=expletive */
	EP
	/* Head - kopf */
	HD
	/* Junctor - junktor */
	JU
	/* Modifier of np to the right - postnominaler modifikator */
	MNR
	/* Modifier - modifikator */
	MO
	/* Negation - negation nicht */
	NG
	/* Noun kernel - element der kern-np */
	NK
	/* Numerical component - numeralien */
	NMC
	/* Object accusative - akkusativobjektuby: gf\=directobject, case\=accusative */
	OA
	/* Object accusative 2 - zweites akkusativobjektuby: gf\=objectcomplement */
	OA2
	/* Object clausal - klausales objektuby: gf\=directobject or complement, sc\=subordinateclause or declarativeclause */
	OC
	/* Object genitive - genitivobjektuby: gf\=complement, case\=genitive */
	OG
	/* Predicative - prädikativ */
	PD
	/* Phrasaler genitive - anstelle eines genitivs verwendete ’von’-pp */
	PG
	/* Placeholder - platzhalter */
	PH
	/* Morphological particle - morphologische partikel (am) */
	PM
	/* Proper noun component */
	PNC
	/* Relative clause - relativsatz */
	RC
	/* Subject - subjektuby: gf\=subject */
	SB
	/* Subject passivised - (logisches) subjekt im passivsatz */
	SBP
	/* Subject or predicative - subjekt oder prädikativ */
	SP
	/* Separable verb prefix - abgetrennter verbpräfix */
	SVP
	/* (idiosyncratic) unit component - fremdsprachliche zitate */
	UC
	/* Vocative - anrede */
	VO
}

// http://edoc.sub.uni-hamburg.de/informatik/volltexte/2014/204/pdf/foth_eine_umfassende_.pdf
def tagset dependencies DE.DEP.CDG {
	/* Adverbiale Modifikation */
	ADV
	/* Apposition */
	APP
	/* Attribute */
	ATTR
	/* Hilfsverb */
	AUX
	/* Getrenntes Verb */
	AVZ
	/* Konjunktion */
	CJ
	/* Determiner */
	DET
	/* Dativunterordnung */
	ETH
	/* Expletives 'es' an finitim Verb */
	EXPL
	/* Genitivattribut */
	GMOD
	/* Akkusativ NP + Maßangabe */
	GRAD
	/* Vergleichswort 'als', 'wie' */
	KOM
	/* Beiordnung */
	KON
	/* Unterordnende Konjunktion */
	KONJ
	/* Nebensatz */
	NEB
	/* Überzähliges Logisches Subjekt */
	NP2
	/* Vollverb zu Akkusativobjekt */
	OBJA
	/* Verb zum zweiten Akkusativobjekt (lehren, kosten, nennen) */
	OBJA2
	/* Objektsatz */
	OBJC
	/* Dativobjekt */
	OBJD
	/* Genitivobjekt */
	OBJG
	/* Objektinfinitiv */
	OBJI
	/* Präpositionsobjekt */
	OBJP
	/* Parenthese */
	PAR
	/* Unterordnung von eingeschränkten Partikeln (von...aus, zu...glauben) */
	PART
	/* Präposition */
	PN
	/* Präpositionsphrase */
	PP
	/* Prädikative Ergänzung (typischerweise am Verb 'sein') */
	PRED
	/* Relativsatz */
	REL
	/* Root, Wurzel des Satzes */
	S
	/* Subjekt des finiten Verbs */
	SUBJ
	/* Subjektsatz */
	SUBJC
	/* Anrede */
	VOK
	/* Konjunktionslose Zeitangabe */
	ZEIT
}

