def tagset parts-of-speech DE.POS.STTS {
	/* $(	sonstige satzzeichen; satzintern	- [,]() */
	DPAR = "$("
	/* $,	komma	, */
	DCOM = "$,"
	/* $.	satzbeendende interpunktion	. ? ! ; : */
	DDOT = "$."
	/* Attributives adjektiv	[das] große [haus] */
	ADJA
	/* Adverbiales oder prädikatives adjektiv	[er fährt] schnell, [er ist] schnell */
	ADJD
	/* Adverb	schon, bald, doch */
	ADV
	/* Postposition	[ihm] zufolge, [der sache] wegen */
	APPO
	/* Präposition; zirkumposition links	in [der stadt], ohne [mich] */
	APPR
	/* Präposition mit artikel	im [haus], zur [sache] */
	APPRART
	/* Zirkumposition rechts	[von jetzt] an */
	APZR
	/* Bestimmter oder unbestimmter artikel	der, die, das, ein, eine */
	ART
	/* Kardinalzahl	zwei [männer], [im jahre] 1994 */
	CARD
	/* Fremdsprachliches material	[er hat das mit ``] a big fish ['' übersetzt] */
	FM
	/* Interjektion	mhm, ach, tja */
	ITJ
	/* Vergleichskonjunktion	als, wie */
	KOKOM
	/* Nebenordnende konjunktion	und, oder, aber */
	KON
	/* Unterordnende konjunktion mit ``zu'' und infinitiv	um [zu leben], anstatt [zu fragen] */
	KOUI
	/* Unterordnende konjunktion mit satz	weil, daß, damit, wenn, ob */
	KOUS
	/* Eigennamen	hans, hamburg, hsv */
	NE
	/* Normales nomen	tisch, herr, [das] reisen */
	NN
	/* Pronominaladverb	dafür, dabei, deswegen, trotzdem */
	PAV
	/* Attribuierendes demonstrativpronomen	jener [mensch] */
	PDAT
	/* Substituierendes demonstrativpronomen	dieser, jener */
	PDS
	/* Attribuierendes indefinitpronomen ohne determiner	kein [mensch], irgendein [glas] */
	PIAT
	/* Attribuierendes indefinitpronomen mit determiner	[ein] wenig [wasser], [die] beiden [brüder] */
	PIDAT
	/* Substituierendes indefinitpronomen	keiner, viele, man, niemand */
	PIS
	/* Irreflexives personalpronomen	ich, er, ihm, mich, dir */
	PPER
	/* Attribuierendes possessivpronomen	mein [buch], deine [mutter] */
	PPOSAT
	/* Substituierendes possessivpronomen	meins, deiner */
	PPOSS
	/* Attribuierendes relativpronomen	[der mann ,] dessen [hund] */
	PRELAT
	/* Substituierendes relativpronomen	[der hund ,] der */
	PRELS
	/* Reflexives personalpronomen	sich, einander, dich, mir */
	PRF
	/* Pronominaladverb	dafür, dabei, deswegen, trotzdem (tiger) */
	PROAV
	/* Partikel bei adjektiv oder adverb	am [schönsten], zu [schnell] */
	PTKA
	/* Antwortpartikel	ja, nein, danke, bitte */
	PTKANT
	/* Negationspartikel	nicht */
	PTKNEG
	/* Abgetrennter verbzusatz	[er kommt] an, [er fährt] rad */
	PTKVZ
	/* ``zu'' vor infinitiv	zu [gehen] */
	PTKZU
	/* Attribuierendes interrogativpronomen	welche [farbe], wessen [hut] */
	PWAT
	/* Adverbiales interrogativ- oder relativpronomen	warum, wo, wann, worüber, wobei */
	PWAV
	/* Substituierendes interrogativpronomen	wer, was */
	PWS
	/* Kompositions-erstglied	an- [und abreise] */
	TRUNC
	/* Finites verb, aux	[du] bist, [wir] werden */
	VAFIN
	/* Imperativ, aux	sei [ruhig !] */
	VAIMP
	/* Infinitiv, aux	werden, sein */
	VAINF
	/* Partizip perfekt, aux	gewesen */
	VAPP
	/* Finites verb, modal	dürfen */
	VMFIN
	/* Infinitiv, modal	wollen */
	VMINF
	/* Partizip perfekt, modal	gekonnt, [er hat gehen] können */
	VMPP
	/* Finites verb, voll	[du] gehst, [wir] kommen [an] */
	VVFIN
	/* Imperativ, voll	komm [!] */
	VVIMP
	/* Infinitiv, voll	gehen, ankommen */
	VVINF
	/* Infinitiv mit ``zu'', voll	anzukommen, loszulassen */
	VVIZU
	/* Partizip perfekt, voll	gegangen, angekommen */
	VVPP
	/* Nichtwort, sonderzeichen enthaltend	3:7, h2o, d2xw3 */
	XY
}

def tagset parts-of-speech DE.POS.TIGER_RFTAGGER {
	/* No description */
	ADJA
	/* No description */
	ADJD
	/* No description */
	ADV
	/* No description */
	APPO
	/* No description */
	APPR
	/* No description */
	APPRART
	/* No description */
	APZR
	/* No description */
	ART
	/* No description */
	CARD
	/* No description */
	CONJ
	/* No description */
	N
	/* No description */
	PRO
	/* No description */
	PROADV
	/* No description */
	SENT
	/* No description */
	SYM
	/* No description */
	VFIN
	/* No description */
	VIMP
	/* No description */
	VINF
	/* No description */
	VPP
}

