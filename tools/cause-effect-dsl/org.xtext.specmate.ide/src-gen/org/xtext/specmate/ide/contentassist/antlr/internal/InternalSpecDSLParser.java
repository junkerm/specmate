package org.xtext.specmate.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.xtext.specmate.services.SpecDSLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSpecDSLParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'def'", "'tagset'", "'parts-of-speech'", "'{'", "'}'", "'='", "'dependencies'", "'subtrees'", "','", "'rule'", "'-'", "'->'", "'('", "')'", "'|'", "':'", "'['", "']'", "'.'", "'*'", "'CASE!'"
    };
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalSpecDSLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSpecDSLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSpecDSLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSpecDSL.g"; }


    	private SpecDSLGrammarAccess grammarAccess;

    	public void setGrammarAccess(SpecDSLGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleModel"
    // InternalSpecDSL.g:53:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // InternalSpecDSL.g:54:1: ( ruleModel EOF )
            // InternalSpecDSL.g:55:1: ruleModel EOF
            {
             before(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            ruleModel();

            state._fsp--;

             after(grammarAccess.getModelRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalSpecDSL.g:62:1: ruleModel : ( ( rule__Model__ElementsAssignment )* ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:66:2: ( ( ( rule__Model__ElementsAssignment )* ) )
            // InternalSpecDSL.g:67:2: ( ( rule__Model__ElementsAssignment )* )
            {
            // InternalSpecDSL.g:67:2: ( ( rule__Model__ElementsAssignment )* )
            // InternalSpecDSL.g:68:3: ( rule__Model__ElementsAssignment )*
            {
             before(grammarAccess.getModelAccess().getElementsAssignment()); 
            // InternalSpecDSL.g:69:3: ( rule__Model__ElementsAssignment )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=11 && LA1_0<=12)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSpecDSL.g:69:4: rule__Model__ElementsAssignment
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__Model__ElementsAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getModelAccess().getElementsAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleAbstractElement"
    // InternalSpecDSL.g:78:1: entryRuleAbstractElement : ruleAbstractElement EOF ;
    public final void entryRuleAbstractElement() throws RecognitionException {
        try {
            // InternalSpecDSL.g:79:1: ( ruleAbstractElement EOF )
            // InternalSpecDSL.g:80:1: ruleAbstractElement EOF
            {
             before(grammarAccess.getAbstractElementRule()); 
            pushFollow(FOLLOW_1);
            ruleAbstractElement();

            state._fsp--;

             after(grammarAccess.getAbstractElementRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAbstractElement"


    // $ANTLR start "ruleAbstractElement"
    // InternalSpecDSL.g:87:1: ruleAbstractElement : ( ( rule__AbstractElement__Alternatives ) ) ;
    public final void ruleAbstractElement() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:91:2: ( ( ( rule__AbstractElement__Alternatives ) ) )
            // InternalSpecDSL.g:92:2: ( ( rule__AbstractElement__Alternatives ) )
            {
            // InternalSpecDSL.g:92:2: ( ( rule__AbstractElement__Alternatives ) )
            // InternalSpecDSL.g:93:3: ( rule__AbstractElement__Alternatives )
            {
             before(grammarAccess.getAbstractElementAccess().getAlternatives()); 
            // InternalSpecDSL.g:94:3: ( rule__AbstractElement__Alternatives )
            // InternalSpecDSL.g:94:4: rule__AbstractElement__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__AbstractElement__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getAbstractElementAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAbstractElement"


    // $ANTLR start "entryRuleImport"
    // InternalSpecDSL.g:103:1: entryRuleImport : ruleImport EOF ;
    public final void entryRuleImport() throws RecognitionException {
        try {
            // InternalSpecDSL.g:104:1: ( ruleImport EOF )
            // InternalSpecDSL.g:105:1: ruleImport EOF
            {
             before(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            ruleImport();

            state._fsp--;

             after(grammarAccess.getImportRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalSpecDSL.g:112:1: ruleImport : ( ( rule__Import__Group__0 ) ) ;
    public final void ruleImport() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:116:2: ( ( ( rule__Import__Group__0 ) ) )
            // InternalSpecDSL.g:117:2: ( ( rule__Import__Group__0 ) )
            {
            // InternalSpecDSL.g:117:2: ( ( rule__Import__Group__0 ) )
            // InternalSpecDSL.g:118:3: ( rule__Import__Group__0 )
            {
             before(grammarAccess.getImportAccess().getGroup()); 
            // InternalSpecDSL.g:119:3: ( rule__Import__Group__0 )
            // InternalSpecDSL.g:119:4: rule__Import__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRulePosDef"
    // InternalSpecDSL.g:128:1: entryRulePosDef : rulePosDef EOF ;
    public final void entryRulePosDef() throws RecognitionException {
        try {
            // InternalSpecDSL.g:129:1: ( rulePosDef EOF )
            // InternalSpecDSL.g:130:1: rulePosDef EOF
            {
             before(grammarAccess.getPosDefRule()); 
            pushFollow(FOLLOW_1);
            rulePosDef();

            state._fsp--;

             after(grammarAccess.getPosDefRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePosDef"


    // $ANTLR start "rulePosDef"
    // InternalSpecDSL.g:137:1: rulePosDef : ( ( rule__PosDef__Group__0 ) ) ;
    public final void rulePosDef() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:141:2: ( ( ( rule__PosDef__Group__0 ) ) )
            // InternalSpecDSL.g:142:2: ( ( rule__PosDef__Group__0 ) )
            {
            // InternalSpecDSL.g:142:2: ( ( rule__PosDef__Group__0 ) )
            // InternalSpecDSL.g:143:3: ( rule__PosDef__Group__0 )
            {
             before(grammarAccess.getPosDefAccess().getGroup()); 
            // InternalSpecDSL.g:144:3: ( rule__PosDef__Group__0 )
            // InternalSpecDSL.g:144:4: rule__PosDef__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PosDef__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPosDefAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePosDef"


    // $ANTLR start "entryRulePOSTag"
    // InternalSpecDSL.g:153:1: entryRulePOSTag : rulePOSTag EOF ;
    public final void entryRulePOSTag() throws RecognitionException {
        try {
            // InternalSpecDSL.g:154:1: ( rulePOSTag EOF )
            // InternalSpecDSL.g:155:1: rulePOSTag EOF
            {
             before(grammarAccess.getPOSTagRule()); 
            pushFollow(FOLLOW_1);
            rulePOSTag();

            state._fsp--;

             after(grammarAccess.getPOSTagRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePOSTag"


    // $ANTLR start "rulePOSTag"
    // InternalSpecDSL.g:162:1: rulePOSTag : ( ( rule__POSTag__Group__0 ) ) ;
    public final void rulePOSTag() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:166:2: ( ( ( rule__POSTag__Group__0 ) ) )
            // InternalSpecDSL.g:167:2: ( ( rule__POSTag__Group__0 ) )
            {
            // InternalSpecDSL.g:167:2: ( ( rule__POSTag__Group__0 ) )
            // InternalSpecDSL.g:168:3: ( rule__POSTag__Group__0 )
            {
             before(grammarAccess.getPOSTagAccess().getGroup()); 
            // InternalSpecDSL.g:169:3: ( rule__POSTag__Group__0 )
            // InternalSpecDSL.g:169:4: rule__POSTag__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__POSTag__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPOSTagAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePOSTag"


    // $ANTLR start "entryRuleDepDef"
    // InternalSpecDSL.g:178:1: entryRuleDepDef : ruleDepDef EOF ;
    public final void entryRuleDepDef() throws RecognitionException {
        try {
            // InternalSpecDSL.g:179:1: ( ruleDepDef EOF )
            // InternalSpecDSL.g:180:1: ruleDepDef EOF
            {
             before(grammarAccess.getDepDefRule()); 
            pushFollow(FOLLOW_1);
            ruleDepDef();

            state._fsp--;

             after(grammarAccess.getDepDefRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDepDef"


    // $ANTLR start "ruleDepDef"
    // InternalSpecDSL.g:187:1: ruleDepDef : ( ( rule__DepDef__Group__0 ) ) ;
    public final void ruleDepDef() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:191:2: ( ( ( rule__DepDef__Group__0 ) ) )
            // InternalSpecDSL.g:192:2: ( ( rule__DepDef__Group__0 ) )
            {
            // InternalSpecDSL.g:192:2: ( ( rule__DepDef__Group__0 ) )
            // InternalSpecDSL.g:193:3: ( rule__DepDef__Group__0 )
            {
             before(grammarAccess.getDepDefAccess().getGroup()); 
            // InternalSpecDSL.g:194:3: ( rule__DepDef__Group__0 )
            // InternalSpecDSL.g:194:4: rule__DepDef__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DepDef__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDepDefAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDepDef"


    // $ANTLR start "entryRuleDepTag"
    // InternalSpecDSL.g:203:1: entryRuleDepTag : ruleDepTag EOF ;
    public final void entryRuleDepTag() throws RecognitionException {
        try {
            // InternalSpecDSL.g:204:1: ( ruleDepTag EOF )
            // InternalSpecDSL.g:205:1: ruleDepTag EOF
            {
             before(grammarAccess.getDepTagRule()); 
            pushFollow(FOLLOW_1);
            ruleDepTag();

            state._fsp--;

             after(grammarAccess.getDepTagRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDepTag"


    // $ANTLR start "ruleDepTag"
    // InternalSpecDSL.g:212:1: ruleDepTag : ( ( rule__DepTag__Group__0 ) ) ;
    public final void ruleDepTag() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:216:2: ( ( ( rule__DepTag__Group__0 ) ) )
            // InternalSpecDSL.g:217:2: ( ( rule__DepTag__Group__0 ) )
            {
            // InternalSpecDSL.g:217:2: ( ( rule__DepTag__Group__0 ) )
            // InternalSpecDSL.g:218:3: ( rule__DepTag__Group__0 )
            {
             before(grammarAccess.getDepTagAccess().getGroup()); 
            // InternalSpecDSL.g:219:3: ( rule__DepTag__Group__0 )
            // InternalSpecDSL.g:219:4: rule__DepTag__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DepTag__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDepTagAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDepTag"


    // $ANTLR start "entryRuleTreeDef"
    // InternalSpecDSL.g:228:1: entryRuleTreeDef : ruleTreeDef EOF ;
    public final void entryRuleTreeDef() throws RecognitionException {
        try {
            // InternalSpecDSL.g:229:1: ( ruleTreeDef EOF )
            // InternalSpecDSL.g:230:1: ruleTreeDef EOF
            {
             before(grammarAccess.getTreeDefRule()); 
            pushFollow(FOLLOW_1);
            ruleTreeDef();

            state._fsp--;

             after(grammarAccess.getTreeDefRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTreeDef"


    // $ANTLR start "ruleTreeDef"
    // InternalSpecDSL.g:237:1: ruleTreeDef : ( ( rule__TreeDef__Group__0 ) ) ;
    public final void ruleTreeDef() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:241:2: ( ( ( rule__TreeDef__Group__0 ) ) )
            // InternalSpecDSL.g:242:2: ( ( rule__TreeDef__Group__0 ) )
            {
            // InternalSpecDSL.g:242:2: ( ( rule__TreeDef__Group__0 ) )
            // InternalSpecDSL.g:243:3: ( rule__TreeDef__Group__0 )
            {
             before(grammarAccess.getTreeDefAccess().getGroup()); 
            // InternalSpecDSL.g:244:3: ( rule__TreeDef__Group__0 )
            // InternalSpecDSL.g:244:4: rule__TreeDef__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTreeDefAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTreeDef"


    // $ANTLR start "entryRuleTreeTag"
    // InternalSpecDSL.g:253:1: entryRuleTreeTag : ruleTreeTag EOF ;
    public final void entryRuleTreeTag() throws RecognitionException {
        try {
            // InternalSpecDSL.g:254:1: ( ruleTreeTag EOF )
            // InternalSpecDSL.g:255:1: ruleTreeTag EOF
            {
             before(grammarAccess.getTreeTagRule()); 
            pushFollow(FOLLOW_1);
            ruleTreeTag();

            state._fsp--;

             after(grammarAccess.getTreeTagRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTreeTag"


    // $ANTLR start "ruleTreeTag"
    // InternalSpecDSL.g:262:1: ruleTreeTag : ( ( rule__TreeTag__NameAssignment ) ) ;
    public final void ruleTreeTag() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:266:2: ( ( ( rule__TreeTag__NameAssignment ) ) )
            // InternalSpecDSL.g:267:2: ( ( rule__TreeTag__NameAssignment ) )
            {
            // InternalSpecDSL.g:267:2: ( ( rule__TreeTag__NameAssignment ) )
            // InternalSpecDSL.g:268:3: ( rule__TreeTag__NameAssignment )
            {
             before(grammarAccess.getTreeTagAccess().getNameAssignment()); 
            // InternalSpecDSL.g:269:3: ( rule__TreeTag__NameAssignment )
            // InternalSpecDSL.g:269:4: rule__TreeTag__NameAssignment
            {
            pushFollow(FOLLOW_2);
            rule__TreeTag__NameAssignment();

            state._fsp--;


            }

             after(grammarAccess.getTreeTagAccess().getNameAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTreeTag"


    // $ANTLR start "entryRuleRule"
    // InternalSpecDSL.g:278:1: entryRuleRule : ruleRule EOF ;
    public final void entryRuleRule() throws RecognitionException {
        try {
            // InternalSpecDSL.g:279:1: ( ruleRule EOF )
            // InternalSpecDSL.g:280:1: ruleRule EOF
            {
             before(grammarAccess.getRuleRule()); 
            pushFollow(FOLLOW_1);
            ruleRule();

            state._fsp--;

             after(grammarAccess.getRuleRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRule"


    // $ANTLR start "ruleRule"
    // InternalSpecDSL.g:287:1: ruleRule : ( ( rule__Rule__Group__0 ) ) ;
    public final void ruleRule() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:291:2: ( ( ( rule__Rule__Group__0 ) ) )
            // InternalSpecDSL.g:292:2: ( ( rule__Rule__Group__0 ) )
            {
            // InternalSpecDSL.g:292:2: ( ( rule__Rule__Group__0 ) )
            // InternalSpecDSL.g:293:3: ( rule__Rule__Group__0 )
            {
             before(grammarAccess.getRuleAccess().getGroup()); 
            // InternalSpecDSL.g:294:3: ( rule__Rule__Group__0 )
            // InternalSpecDSL.g:294:4: rule__Rule__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Rule__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRuleAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRule"


    // $ANTLR start "entryRuleDependencyRule"
    // InternalSpecDSL.g:303:1: entryRuleDependencyRule : ruleDependencyRule EOF ;
    public final void entryRuleDependencyRule() throws RecognitionException {
        try {
            // InternalSpecDSL.g:304:1: ( ruleDependencyRule EOF )
            // InternalSpecDSL.g:305:1: ruleDependencyRule EOF
            {
             before(grammarAccess.getDependencyRuleRule()); 
            pushFollow(FOLLOW_1);
            ruleDependencyRule();

            state._fsp--;

             after(grammarAccess.getDependencyRuleRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDependencyRule"


    // $ANTLR start "ruleDependencyRule"
    // InternalSpecDSL.g:312:1: ruleDependencyRule : ( ( rule__DependencyRule__Alternatives ) ) ;
    public final void ruleDependencyRule() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:316:2: ( ( ( rule__DependencyRule__Alternatives ) ) )
            // InternalSpecDSL.g:317:2: ( ( rule__DependencyRule__Alternatives ) )
            {
            // InternalSpecDSL.g:317:2: ( ( rule__DependencyRule__Alternatives ) )
            // InternalSpecDSL.g:318:3: ( rule__DependencyRule__Alternatives )
            {
             before(grammarAccess.getDependencyRuleAccess().getAlternatives()); 
            // InternalSpecDSL.g:319:3: ( rule__DependencyRule__Alternatives )
            // InternalSpecDSL.g:319:4: rule__DependencyRule__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDependencyRule"


    // $ANTLR start "entryRuleFreeDependencyRule"
    // InternalSpecDSL.g:328:1: entryRuleFreeDependencyRule : ruleFreeDependencyRule EOF ;
    public final void entryRuleFreeDependencyRule() throws RecognitionException {
        try {
            // InternalSpecDSL.g:329:1: ( ruleFreeDependencyRule EOF )
            // InternalSpecDSL.g:330:1: ruleFreeDependencyRule EOF
            {
             before(grammarAccess.getFreeDependencyRuleRule()); 
            pushFollow(FOLLOW_1);
            ruleFreeDependencyRule();

            state._fsp--;

             after(grammarAccess.getFreeDependencyRuleRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFreeDependencyRule"


    // $ANTLR start "ruleFreeDependencyRule"
    // InternalSpecDSL.g:337:1: ruleFreeDependencyRule : ( ( rule__FreeDependencyRule__Group__0 ) ) ;
    public final void ruleFreeDependencyRule() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:341:2: ( ( ( rule__FreeDependencyRule__Group__0 ) ) )
            // InternalSpecDSL.g:342:2: ( ( rule__FreeDependencyRule__Group__0 ) )
            {
            // InternalSpecDSL.g:342:2: ( ( rule__FreeDependencyRule__Group__0 ) )
            // InternalSpecDSL.g:343:3: ( rule__FreeDependencyRule__Group__0 )
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getGroup()); 
            // InternalSpecDSL.g:344:3: ( rule__FreeDependencyRule__Group__0 )
            // InternalSpecDSL.g:344:4: rule__FreeDependencyRule__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getFreeDependencyRuleAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFreeDependencyRule"


    // $ANTLR start "entryRuleNode"
    // InternalSpecDSL.g:353:1: entryRuleNode : ruleNode EOF ;
    public final void entryRuleNode() throws RecognitionException {
        try {
            // InternalSpecDSL.g:354:1: ( ruleNode EOF )
            // InternalSpecDSL.g:355:1: ruleNode EOF
            {
             before(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_1);
            ruleNode();

            state._fsp--;

             after(grammarAccess.getNodeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // InternalSpecDSL.g:362:1: ruleNode : ( ( rule__Node__Alternatives ) ) ;
    public final void ruleNode() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:366:2: ( ( ( rule__Node__Alternatives ) ) )
            // InternalSpecDSL.g:367:2: ( ( rule__Node__Alternatives ) )
            {
            // InternalSpecDSL.g:367:2: ( ( rule__Node__Alternatives ) )
            // InternalSpecDSL.g:368:3: ( rule__Node__Alternatives )
            {
             before(grammarAccess.getNodeAccess().getAlternatives()); 
            // InternalSpecDSL.g:369:3: ( rule__Node__Alternatives )
            // InternalSpecDSL.g:369:4: rule__Node__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Node__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRuleNonTreeNode"
    // InternalSpecDSL.g:378:1: entryRuleNonTreeNode : ruleNonTreeNode EOF ;
    public final void entryRuleNonTreeNode() throws RecognitionException {
        try {
            // InternalSpecDSL.g:379:1: ( ruleNonTreeNode EOF )
            // InternalSpecDSL.g:380:1: ruleNonTreeNode EOF
            {
             before(grammarAccess.getNonTreeNodeRule()); 
            pushFollow(FOLLOW_1);
            ruleNonTreeNode();

            state._fsp--;

             after(grammarAccess.getNonTreeNodeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNonTreeNode"


    // $ANTLR start "ruleNonTreeNode"
    // InternalSpecDSL.g:387:1: ruleNonTreeNode : ( ( rule__NonTreeNode__Alternatives ) ) ;
    public final void ruleNonTreeNode() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:391:2: ( ( ( rule__NonTreeNode__Alternatives ) ) )
            // InternalSpecDSL.g:392:2: ( ( rule__NonTreeNode__Alternatives ) )
            {
            // InternalSpecDSL.g:392:2: ( ( rule__NonTreeNode__Alternatives ) )
            // InternalSpecDSL.g:393:3: ( rule__NonTreeNode__Alternatives )
            {
             before(grammarAccess.getNonTreeNodeAccess().getAlternatives()); 
            // InternalSpecDSL.g:394:3: ( rule__NonTreeNode__Alternatives )
            // InternalSpecDSL.g:394:4: rule__NonTreeNode__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__NonTreeNode__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getNonTreeNodeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNonTreeNode"


    // $ANTLR start "entryRuleOptionNode"
    // InternalSpecDSL.g:403:1: entryRuleOptionNode : ruleOptionNode EOF ;
    public final void entryRuleOptionNode() throws RecognitionException {
        try {
            // InternalSpecDSL.g:404:1: ( ruleOptionNode EOF )
            // InternalSpecDSL.g:405:1: ruleOptionNode EOF
            {
             before(grammarAccess.getOptionNodeRule()); 
            pushFollow(FOLLOW_1);
            ruleOptionNode();

            state._fsp--;

             after(grammarAccess.getOptionNodeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOptionNode"


    // $ANTLR start "ruleOptionNode"
    // InternalSpecDSL.g:412:1: ruleOptionNode : ( ( rule__OptionNode__Group__0 ) ) ;
    public final void ruleOptionNode() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:416:2: ( ( ( rule__OptionNode__Group__0 ) ) )
            // InternalSpecDSL.g:417:2: ( ( rule__OptionNode__Group__0 ) )
            {
            // InternalSpecDSL.g:417:2: ( ( rule__OptionNode__Group__0 ) )
            // InternalSpecDSL.g:418:3: ( rule__OptionNode__Group__0 )
            {
             before(grammarAccess.getOptionNodeAccess().getGroup()); 
            // InternalSpecDSL.g:419:3: ( rule__OptionNode__Group__0 )
            // InternalSpecDSL.g:419:4: rule__OptionNode__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__OptionNode__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOptionNodeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOptionNode"


    // $ANTLR start "entryRuleTreeNode"
    // InternalSpecDSL.g:428:1: entryRuleTreeNode : ruleTreeNode EOF ;
    public final void entryRuleTreeNode() throws RecognitionException {
        try {
            // InternalSpecDSL.g:429:1: ( ruleTreeNode EOF )
            // InternalSpecDSL.g:430:1: ruleTreeNode EOF
            {
             before(grammarAccess.getTreeNodeRule()); 
            pushFollow(FOLLOW_1);
            ruleTreeNode();

            state._fsp--;

             after(grammarAccess.getTreeNodeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTreeNode"


    // $ANTLR start "ruleTreeNode"
    // InternalSpecDSL.g:437:1: ruleTreeNode : ( ( rule__TreeNode__Group__0 ) ) ;
    public final void ruleTreeNode() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:441:2: ( ( ( rule__TreeNode__Group__0 ) ) )
            // InternalSpecDSL.g:442:2: ( ( rule__TreeNode__Group__0 ) )
            {
            // InternalSpecDSL.g:442:2: ( ( rule__TreeNode__Group__0 ) )
            // InternalSpecDSL.g:443:3: ( rule__TreeNode__Group__0 )
            {
             before(grammarAccess.getTreeNodeAccess().getGroup()); 
            // InternalSpecDSL.g:444:3: ( rule__TreeNode__Group__0 )
            // InternalSpecDSL.g:444:4: rule__TreeNode__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__TreeNode__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTreeNodeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTreeNode"


    // $ANTLR start "entryRuleExplicitNode"
    // InternalSpecDSL.g:453:1: entryRuleExplicitNode : ruleExplicitNode EOF ;
    public final void entryRuleExplicitNode() throws RecognitionException {
        try {
            // InternalSpecDSL.g:454:1: ( ruleExplicitNode EOF )
            // InternalSpecDSL.g:455:1: ruleExplicitNode EOF
            {
             before(grammarAccess.getExplicitNodeRule()); 
            pushFollow(FOLLOW_1);
            ruleExplicitNode();

            state._fsp--;

             after(grammarAccess.getExplicitNodeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleExplicitNode"


    // $ANTLR start "ruleExplicitNode"
    // InternalSpecDSL.g:462:1: ruleExplicitNode : ( ( rule__ExplicitNode__Group__0 ) ) ;
    public final void ruleExplicitNode() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:466:2: ( ( ( rule__ExplicitNode__Group__0 ) ) )
            // InternalSpecDSL.g:467:2: ( ( rule__ExplicitNode__Group__0 ) )
            {
            // InternalSpecDSL.g:467:2: ( ( rule__ExplicitNode__Group__0 ) )
            // InternalSpecDSL.g:468:3: ( rule__ExplicitNode__Group__0 )
            {
             before(grammarAccess.getExplicitNodeAccess().getGroup()); 
            // InternalSpecDSL.g:469:3: ( rule__ExplicitNode__Group__0 )
            // InternalSpecDSL.g:469:4: rule__ExplicitNode__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getExplicitNodeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExplicitNode"


    // $ANTLR start "entryRuleSubtree"
    // InternalSpecDSL.g:478:1: entryRuleSubtree : ruleSubtree EOF ;
    public final void entryRuleSubtree() throws RecognitionException {
        try {
            // InternalSpecDSL.g:479:1: ( ruleSubtree EOF )
            // InternalSpecDSL.g:480:1: ruleSubtree EOF
            {
             before(grammarAccess.getSubtreeRule()); 
            pushFollow(FOLLOW_1);
            ruleSubtree();

            state._fsp--;

             after(grammarAccess.getSubtreeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSubtree"


    // $ANTLR start "ruleSubtree"
    // InternalSpecDSL.g:487:1: ruleSubtree : ( ( rule__Subtree__Group__0 ) ) ;
    public final void ruleSubtree() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:491:2: ( ( ( rule__Subtree__Group__0 ) ) )
            // InternalSpecDSL.g:492:2: ( ( rule__Subtree__Group__0 ) )
            {
            // InternalSpecDSL.g:492:2: ( ( rule__Subtree__Group__0 ) )
            // InternalSpecDSL.g:493:3: ( rule__Subtree__Group__0 )
            {
             before(grammarAccess.getSubtreeAccess().getGroup()); 
            // InternalSpecDSL.g:494:3: ( rule__Subtree__Group__0 )
            // InternalSpecDSL.g:494:4: rule__Subtree__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Subtree__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSubtreeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSubtree"


    // $ANTLR start "entryRuleQualifiedName"
    // InternalSpecDSL.g:503:1: entryRuleQualifiedName : ruleQualifiedName EOF ;
    public final void entryRuleQualifiedName() throws RecognitionException {
        try {
            // InternalSpecDSL.g:504:1: ( ruleQualifiedName EOF )
            // InternalSpecDSL.g:505:1: ruleQualifiedName EOF
            {
             before(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_1);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getQualifiedNameRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalSpecDSL.g:512:1: ruleQualifiedName : ( ( rule__QualifiedName__Group__0 ) ) ;
    public final void ruleQualifiedName() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:516:2: ( ( ( rule__QualifiedName__Group__0 ) ) )
            // InternalSpecDSL.g:517:2: ( ( rule__QualifiedName__Group__0 ) )
            {
            // InternalSpecDSL.g:517:2: ( ( rule__QualifiedName__Group__0 ) )
            // InternalSpecDSL.g:518:3: ( rule__QualifiedName__Group__0 )
            {
             before(grammarAccess.getQualifiedNameAccess().getGroup()); 
            // InternalSpecDSL.g:519:3: ( rule__QualifiedName__Group__0 )
            // InternalSpecDSL.g:519:4: rule__QualifiedName__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__QualifiedName__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getQualifiedNameAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleQualifiedNameWithWildcard"
    // InternalSpecDSL.g:528:1: entryRuleQualifiedNameWithWildcard : ruleQualifiedNameWithWildcard EOF ;
    public final void entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        try {
            // InternalSpecDSL.g:529:1: ( ruleQualifiedNameWithWildcard EOF )
            // InternalSpecDSL.g:530:1: ruleQualifiedNameWithWildcard EOF
            {
             before(grammarAccess.getQualifiedNameWithWildcardRule()); 
            pushFollow(FOLLOW_1);
            ruleQualifiedNameWithWildcard();

            state._fsp--;

             after(grammarAccess.getQualifiedNameWithWildcardRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleQualifiedNameWithWildcard"


    // $ANTLR start "ruleQualifiedNameWithWildcard"
    // InternalSpecDSL.g:537:1: ruleQualifiedNameWithWildcard : ( ( rule__QualifiedNameWithWildcard__Group__0 ) ) ;
    public final void ruleQualifiedNameWithWildcard() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:541:2: ( ( ( rule__QualifiedNameWithWildcard__Group__0 ) ) )
            // InternalSpecDSL.g:542:2: ( ( rule__QualifiedNameWithWildcard__Group__0 ) )
            {
            // InternalSpecDSL.g:542:2: ( ( rule__QualifiedNameWithWildcard__Group__0 ) )
            // InternalSpecDSL.g:543:3: ( rule__QualifiedNameWithWildcard__Group__0 )
            {
             before(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup()); 
            // InternalSpecDSL.g:544:3: ( rule__QualifiedNameWithWildcard__Group__0 )
            // InternalSpecDSL.g:544:4: rule__QualifiedNameWithWildcard__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__QualifiedNameWithWildcard__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQualifiedNameWithWildcard"


    // $ANTLR start "rule__AbstractElement__Alternatives"
    // InternalSpecDSL.g:552:1: rule__AbstractElement__Alternatives : ( ( ruleImport ) | ( rulePosDef ) | ( ruleDepDef ) | ( ruleTreeDef ) | ( ruleRule ) );
    public final void rule__AbstractElement__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:556:1: ( ( ruleImport ) | ( rulePosDef ) | ( ruleDepDef ) | ( ruleTreeDef ) | ( ruleRule ) )
            int alt2=5;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==11) ) {
                alt2=1;
            }
            else if ( (LA2_0==12) ) {
                switch ( input.LA(2) ) {
                case 13:
                    {
                    int LA2_3 = input.LA(3);

                    if ( (LA2_3==14) ) {
                        alt2=2;
                    }
                    else if ( (LA2_3==18) ) {
                        alt2=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case 19:
                    {
                    alt2=4;
                    }
                    break;
                case 21:
                    {
                    alt2=5;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 2, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalSpecDSL.g:557:2: ( ruleImport )
                    {
                    // InternalSpecDSL.g:557:2: ( ruleImport )
                    // InternalSpecDSL.g:558:3: ruleImport
                    {
                     before(grammarAccess.getAbstractElementAccess().getImportParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleImport();

                    state._fsp--;

                     after(grammarAccess.getAbstractElementAccess().getImportParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:563:2: ( rulePosDef )
                    {
                    // InternalSpecDSL.g:563:2: ( rulePosDef )
                    // InternalSpecDSL.g:564:3: rulePosDef
                    {
                     before(grammarAccess.getAbstractElementAccess().getPosDefParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    rulePosDef();

                    state._fsp--;

                     after(grammarAccess.getAbstractElementAccess().getPosDefParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSpecDSL.g:569:2: ( ruleDepDef )
                    {
                    // InternalSpecDSL.g:569:2: ( ruleDepDef )
                    // InternalSpecDSL.g:570:3: ruleDepDef
                    {
                     before(grammarAccess.getAbstractElementAccess().getDepDefParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleDepDef();

                    state._fsp--;

                     after(grammarAccess.getAbstractElementAccess().getDepDefParserRuleCall_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSpecDSL.g:575:2: ( ruleTreeDef )
                    {
                    // InternalSpecDSL.g:575:2: ( ruleTreeDef )
                    // InternalSpecDSL.g:576:3: ruleTreeDef
                    {
                     before(grammarAccess.getAbstractElementAccess().getTreeDefParserRuleCall_3()); 
                    pushFollow(FOLLOW_2);
                    ruleTreeDef();

                    state._fsp--;

                     after(grammarAccess.getAbstractElementAccess().getTreeDefParserRuleCall_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalSpecDSL.g:581:2: ( ruleRule )
                    {
                    // InternalSpecDSL.g:581:2: ( ruleRule )
                    // InternalSpecDSL.g:582:3: ruleRule
                    {
                     before(grammarAccess.getAbstractElementAccess().getRuleParserRuleCall_4()); 
                    pushFollow(FOLLOW_2);
                    ruleRule();

                    state._fsp--;

                     after(grammarAccess.getAbstractElementAccess().getRuleParserRuleCall_4()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AbstractElement__Alternatives"


    // $ANTLR start "rule__TreeDef__Alternatives_3"
    // InternalSpecDSL.g:591:1: rule__TreeDef__Alternatives_3 : ( ( ( rule__TreeDef__Group_3_0__0 ) ) | ( ( rule__TreeDef__Group_3_1__0 ) ) );
    public final void rule__TreeDef__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:595:1: ( ( ( rule__TreeDef__Group_3_0__0 ) ) | ( ( rule__TreeDef__Group_3_1__0 ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==15) ) {
                alt3=1;
            }
            else if ( (LA3_0==RULE_ID) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalSpecDSL.g:596:2: ( ( rule__TreeDef__Group_3_0__0 ) )
                    {
                    // InternalSpecDSL.g:596:2: ( ( rule__TreeDef__Group_3_0__0 ) )
                    // InternalSpecDSL.g:597:3: ( rule__TreeDef__Group_3_0__0 )
                    {
                     before(grammarAccess.getTreeDefAccess().getGroup_3_0()); 
                    // InternalSpecDSL.g:598:3: ( rule__TreeDef__Group_3_0__0 )
                    // InternalSpecDSL.g:598:4: rule__TreeDef__Group_3_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TreeDef__Group_3_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getTreeDefAccess().getGroup_3_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:602:2: ( ( rule__TreeDef__Group_3_1__0 ) )
                    {
                    // InternalSpecDSL.g:602:2: ( ( rule__TreeDef__Group_3_1__0 ) )
                    // InternalSpecDSL.g:603:3: ( rule__TreeDef__Group_3_1__0 )
                    {
                     before(grammarAccess.getTreeDefAccess().getGroup_3_1()); 
                    // InternalSpecDSL.g:604:3: ( rule__TreeDef__Group_3_1__0 )
                    // InternalSpecDSL.g:604:4: rule__TreeDef__Group_3_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TreeDef__Group_3_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getTreeDefAccess().getGroup_3_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Alternatives_3"


    // $ANTLR start "rule__DependencyRule__Alternatives"
    // InternalSpecDSL.g:612:1: rule__DependencyRule__Alternatives : ( ( ( rule__DependencyRule__Group_0__0 ) ) | ( ( rule__DependencyRule__Group_1__0 ) ) );
    public final void rule__DependencyRule__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:616:1: ( ( ( rule__DependencyRule__Group_0__0 ) ) | ( ( rule__DependencyRule__Group_1__0 ) ) )
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // InternalSpecDSL.g:617:2: ( ( rule__DependencyRule__Group_0__0 ) )
                    {
                    // InternalSpecDSL.g:617:2: ( ( rule__DependencyRule__Group_0__0 ) )
                    // InternalSpecDSL.g:618:3: ( rule__DependencyRule__Group_0__0 )
                    {
                     before(grammarAccess.getDependencyRuleAccess().getGroup_0()); 
                    // InternalSpecDSL.g:619:3: ( rule__DependencyRule__Group_0__0 )
                    // InternalSpecDSL.g:619:4: rule__DependencyRule__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DependencyRule__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getDependencyRuleAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:623:2: ( ( rule__DependencyRule__Group_1__0 ) )
                    {
                    // InternalSpecDSL.g:623:2: ( ( rule__DependencyRule__Group_1__0 ) )
                    // InternalSpecDSL.g:624:3: ( rule__DependencyRule__Group_1__0 )
                    {
                     before(grammarAccess.getDependencyRuleAccess().getGroup_1()); 
                    // InternalSpecDSL.g:625:3: ( rule__DependencyRule__Group_1__0 )
                    // InternalSpecDSL.g:625:4: rule__DependencyRule__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DependencyRule__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getDependencyRuleAccess().getGroup_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Alternatives"


    // $ANTLR start "rule__DependencyRule__RightNodeAlternatives_0_4_0"
    // InternalSpecDSL.g:633:1: rule__DependencyRule__RightNodeAlternatives_0_4_0 : ( ( ruleNode ) | ( ruleFreeDependencyRule ) );
    public final void rule__DependencyRule__RightNodeAlternatives_0_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:637:1: ( ( ruleNode ) | ( ruleFreeDependencyRule ) )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSpecDSL.g:638:2: ( ruleNode )
                    {
                    // InternalSpecDSL.g:638:2: ( ruleNode )
                    // InternalSpecDSL.g:639:3: ruleNode
                    {
                     before(grammarAccess.getDependencyRuleAccess().getRightNodeNodeParserRuleCall_0_4_0_0()); 
                    pushFollow(FOLLOW_2);
                    ruleNode();

                    state._fsp--;

                     after(grammarAccess.getDependencyRuleAccess().getRightNodeNodeParserRuleCall_0_4_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:644:2: ( ruleFreeDependencyRule )
                    {
                    // InternalSpecDSL.g:644:2: ( ruleFreeDependencyRule )
                    // InternalSpecDSL.g:645:3: ruleFreeDependencyRule
                    {
                     before(grammarAccess.getDependencyRuleAccess().getRightNodeFreeDependencyRuleParserRuleCall_0_4_0_1()); 
                    pushFollow(FOLLOW_2);
                    ruleFreeDependencyRule();

                    state._fsp--;

                     after(grammarAccess.getDependencyRuleAccess().getRightNodeFreeDependencyRuleParserRuleCall_0_4_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__RightNodeAlternatives_0_4_0"


    // $ANTLR start "rule__DependencyRule__RightNodeAlternatives_1_4_0"
    // InternalSpecDSL.g:654:1: rule__DependencyRule__RightNodeAlternatives_1_4_0 : ( ( ruleTreeNode ) | ( ruleDependencyRule ) );
    public final void rule__DependencyRule__RightNodeAlternatives_1_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:658:1: ( ( ruleTreeNode ) | ( ruleDependencyRule ) )
            int alt6=2;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // InternalSpecDSL.g:659:2: ( ruleTreeNode )
                    {
                    // InternalSpecDSL.g:659:2: ( ruleTreeNode )
                    // InternalSpecDSL.g:660:3: ruleTreeNode
                    {
                     before(grammarAccess.getDependencyRuleAccess().getRightNodeTreeNodeParserRuleCall_1_4_0_0()); 
                    pushFollow(FOLLOW_2);
                    ruleTreeNode();

                    state._fsp--;

                     after(grammarAccess.getDependencyRuleAccess().getRightNodeTreeNodeParserRuleCall_1_4_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:665:2: ( ruleDependencyRule )
                    {
                    // InternalSpecDSL.g:665:2: ( ruleDependencyRule )
                    // InternalSpecDSL.g:666:3: ruleDependencyRule
                    {
                     before(grammarAccess.getDependencyRuleAccess().getRightNodeDependencyRuleParserRuleCall_1_4_0_1()); 
                    pushFollow(FOLLOW_2);
                    ruleDependencyRule();

                    state._fsp--;

                     after(grammarAccess.getDependencyRuleAccess().getRightNodeDependencyRuleParserRuleCall_1_4_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__RightNodeAlternatives_1_4_0"


    // $ANTLR start "rule__FreeDependencyRule__RightNodeAlternatives_4_0"
    // InternalSpecDSL.g:675:1: rule__FreeDependencyRule__RightNodeAlternatives_4_0 : ( ( ruleNode ) | ( ruleFreeDependencyRule ) );
    public final void rule__FreeDependencyRule__RightNodeAlternatives_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:679:1: ( ( ruleNode ) | ( ruleFreeDependencyRule ) )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // InternalSpecDSL.g:680:2: ( ruleNode )
                    {
                    // InternalSpecDSL.g:680:2: ( ruleNode )
                    // InternalSpecDSL.g:681:3: ruleNode
                    {
                     before(grammarAccess.getFreeDependencyRuleAccess().getRightNodeNodeParserRuleCall_4_0_0()); 
                    pushFollow(FOLLOW_2);
                    ruleNode();

                    state._fsp--;

                     after(grammarAccess.getFreeDependencyRuleAccess().getRightNodeNodeParserRuleCall_4_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:686:2: ( ruleFreeDependencyRule )
                    {
                    // InternalSpecDSL.g:686:2: ( ruleFreeDependencyRule )
                    // InternalSpecDSL.g:687:3: ruleFreeDependencyRule
                    {
                     before(grammarAccess.getFreeDependencyRuleAccess().getRightNodeFreeDependencyRuleParserRuleCall_4_0_1()); 
                    pushFollow(FOLLOW_2);
                    ruleFreeDependencyRule();

                    state._fsp--;

                     after(grammarAccess.getFreeDependencyRuleAccess().getRightNodeFreeDependencyRuleParserRuleCall_4_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__RightNodeAlternatives_4_0"


    // $ANTLR start "rule__Node__Alternatives"
    // InternalSpecDSL.g:696:1: rule__Node__Alternatives : ( ( ruleExplicitNode ) | ( ruleOptionNode ) | ( ruleTreeNode ) );
    public final void rule__Node__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:700:1: ( ( ruleExplicitNode ) | ( ruleOptionNode ) | ( ruleTreeNode ) )
            int alt8=3;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // InternalSpecDSL.g:701:2: ( ruleExplicitNode )
                    {
                    // InternalSpecDSL.g:701:2: ( ruleExplicitNode )
                    // InternalSpecDSL.g:702:3: ruleExplicitNode
                    {
                     before(grammarAccess.getNodeAccess().getExplicitNodeParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleExplicitNode();

                    state._fsp--;

                     after(grammarAccess.getNodeAccess().getExplicitNodeParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:707:2: ( ruleOptionNode )
                    {
                    // InternalSpecDSL.g:707:2: ( ruleOptionNode )
                    // InternalSpecDSL.g:708:3: ruleOptionNode
                    {
                     before(grammarAccess.getNodeAccess().getOptionNodeParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleOptionNode();

                    state._fsp--;

                     after(grammarAccess.getNodeAccess().getOptionNodeParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSpecDSL.g:713:2: ( ruleTreeNode )
                    {
                    // InternalSpecDSL.g:713:2: ( ruleTreeNode )
                    // InternalSpecDSL.g:714:3: ruleTreeNode
                    {
                     before(grammarAccess.getNodeAccess().getTreeNodeParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleTreeNode();

                    state._fsp--;

                     after(grammarAccess.getNodeAccess().getTreeNodeParserRuleCall_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Alternatives"


    // $ANTLR start "rule__NonTreeNode__Alternatives"
    // InternalSpecDSL.g:723:1: rule__NonTreeNode__Alternatives : ( ( ruleExplicitNode ) | ( ruleOptionNode ) );
    public final void rule__NonTreeNode__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:727:1: ( ( ruleExplicitNode ) | ( ruleOptionNode ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=RULE_ID && LA9_0<=RULE_STRING)||(LA9_0>=31 && LA9_0<=32)) ) {
                alt9=1;
            }
            else if ( (LA9_0==24) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalSpecDSL.g:728:2: ( ruleExplicitNode )
                    {
                    // InternalSpecDSL.g:728:2: ( ruleExplicitNode )
                    // InternalSpecDSL.g:729:3: ruleExplicitNode
                    {
                     before(grammarAccess.getNonTreeNodeAccess().getExplicitNodeParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleExplicitNode();

                    state._fsp--;

                     after(grammarAccess.getNonTreeNodeAccess().getExplicitNodeParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:734:2: ( ruleOptionNode )
                    {
                    // InternalSpecDSL.g:734:2: ( ruleOptionNode )
                    // InternalSpecDSL.g:735:3: ruleOptionNode
                    {
                     before(grammarAccess.getNonTreeNodeAccess().getOptionNodeParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleOptionNode();

                    state._fsp--;

                     after(grammarAccess.getNonTreeNodeAccess().getOptionNodeParserRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NonTreeNode__Alternatives"


    // $ANTLR start "rule__TreeNode__Alternatives_1_0"
    // InternalSpecDSL.g:744:1: rule__TreeNode__Alternatives_1_0 : ( ( ( rule__TreeNode__ExprAssignment_1_0_0 ) ) | ( ( rule__TreeNode__AnyMatchAssignment_1_0_1 ) ) );
    public final void rule__TreeNode__Alternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:748:1: ( ( ( rule__TreeNode__ExprAssignment_1_0_0 ) ) | ( ( rule__TreeNode__AnyMatchAssignment_1_0_1 ) ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_STRING) ) {
                alt10=1;
            }
            else if ( (LA10_0==31) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalSpecDSL.g:749:2: ( ( rule__TreeNode__ExprAssignment_1_0_0 ) )
                    {
                    // InternalSpecDSL.g:749:2: ( ( rule__TreeNode__ExprAssignment_1_0_0 ) )
                    // InternalSpecDSL.g:750:3: ( rule__TreeNode__ExprAssignment_1_0_0 )
                    {
                     before(grammarAccess.getTreeNodeAccess().getExprAssignment_1_0_0()); 
                    // InternalSpecDSL.g:751:3: ( rule__TreeNode__ExprAssignment_1_0_0 )
                    // InternalSpecDSL.g:751:4: rule__TreeNode__ExprAssignment_1_0_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TreeNode__ExprAssignment_1_0_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getTreeNodeAccess().getExprAssignment_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:755:2: ( ( rule__TreeNode__AnyMatchAssignment_1_0_1 ) )
                    {
                    // InternalSpecDSL.g:755:2: ( ( rule__TreeNode__AnyMatchAssignment_1_0_1 ) )
                    // InternalSpecDSL.g:756:3: ( rule__TreeNode__AnyMatchAssignment_1_0_1 )
                    {
                     before(grammarAccess.getTreeNodeAccess().getAnyMatchAssignment_1_0_1()); 
                    // InternalSpecDSL.g:757:3: ( rule__TreeNode__AnyMatchAssignment_1_0_1 )
                    // InternalSpecDSL.g:757:4: rule__TreeNode__AnyMatchAssignment_1_0_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__TreeNode__AnyMatchAssignment_1_0_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getTreeNodeAccess().getAnyMatchAssignment_1_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Alternatives_1_0"


    // $ANTLR start "rule__ExplicitNode__Alternatives_1"
    // InternalSpecDSL.g:765:1: rule__ExplicitNode__Alternatives_1 : ( ( ( rule__ExplicitNode__Group_1_0__0 ) ) | ( ( rule__ExplicitNode__AnyMatchAssignment_1_1 ) ) );
    public final void rule__ExplicitNode__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:769:1: ( ( ( rule__ExplicitNode__Group_1_0__0 ) ) | ( ( rule__ExplicitNode__AnyMatchAssignment_1_1 ) ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_STRING||LA11_0==32) ) {
                alt11=1;
            }
            else if ( (LA11_0==31) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalSpecDSL.g:770:2: ( ( rule__ExplicitNode__Group_1_0__0 ) )
                    {
                    // InternalSpecDSL.g:770:2: ( ( rule__ExplicitNode__Group_1_0__0 ) )
                    // InternalSpecDSL.g:771:3: ( rule__ExplicitNode__Group_1_0__0 )
                    {
                     before(grammarAccess.getExplicitNodeAccess().getGroup_1_0()); 
                    // InternalSpecDSL.g:772:3: ( rule__ExplicitNode__Group_1_0__0 )
                    // InternalSpecDSL.g:772:4: rule__ExplicitNode__Group_1_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExplicitNode__Group_1_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getExplicitNodeAccess().getGroup_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:776:2: ( ( rule__ExplicitNode__AnyMatchAssignment_1_1 ) )
                    {
                    // InternalSpecDSL.g:776:2: ( ( rule__ExplicitNode__AnyMatchAssignment_1_1 ) )
                    // InternalSpecDSL.g:777:3: ( rule__ExplicitNode__AnyMatchAssignment_1_1 )
                    {
                     before(grammarAccess.getExplicitNodeAccess().getAnyMatchAssignment_1_1()); 
                    // InternalSpecDSL.g:778:3: ( rule__ExplicitNode__AnyMatchAssignment_1_1 )
                    // InternalSpecDSL.g:778:4: rule__ExplicitNode__AnyMatchAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExplicitNode__AnyMatchAssignment_1_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getExplicitNodeAccess().getAnyMatchAssignment_1_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Alternatives_1"


    // $ANTLR start "rule__Import__Group__0"
    // InternalSpecDSL.g:786:1: rule__Import__Group__0 : rule__Import__Group__0__Impl rule__Import__Group__1 ;
    public final void rule__Import__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:790:1: ( rule__Import__Group__0__Impl rule__Import__Group__1 )
            // InternalSpecDSL.g:791:2: rule__Import__Group__0__Impl rule__Import__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__Import__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Import__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0"


    // $ANTLR start "rule__Import__Group__0__Impl"
    // InternalSpecDSL.g:798:1: rule__Import__Group__0__Impl : ( 'import' ) ;
    public final void rule__Import__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:802:1: ( ( 'import' ) )
            // InternalSpecDSL.g:803:1: ( 'import' )
            {
            // InternalSpecDSL.g:803:1: ( 'import' )
            // InternalSpecDSL.g:804:2: 'import'
            {
             before(grammarAccess.getImportAccess().getImportKeyword_0()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getImportAccess().getImportKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0__Impl"


    // $ANTLR start "rule__Import__Group__1"
    // InternalSpecDSL.g:813:1: rule__Import__Group__1 : rule__Import__Group__1__Impl ;
    public final void rule__Import__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:817:1: ( rule__Import__Group__1__Impl )
            // InternalSpecDSL.g:818:2: rule__Import__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1"


    // $ANTLR start "rule__Import__Group__1__Impl"
    // InternalSpecDSL.g:824:1: rule__Import__Group__1__Impl : ( ( rule__Import__ImportedNamespaceAssignment_1 ) ) ;
    public final void rule__Import__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:828:1: ( ( ( rule__Import__ImportedNamespaceAssignment_1 ) ) )
            // InternalSpecDSL.g:829:1: ( ( rule__Import__ImportedNamespaceAssignment_1 ) )
            {
            // InternalSpecDSL.g:829:1: ( ( rule__Import__ImportedNamespaceAssignment_1 ) )
            // InternalSpecDSL.g:830:2: ( rule__Import__ImportedNamespaceAssignment_1 )
            {
             before(grammarAccess.getImportAccess().getImportedNamespaceAssignment_1()); 
            // InternalSpecDSL.g:831:2: ( rule__Import__ImportedNamespaceAssignment_1 )
            // InternalSpecDSL.g:831:3: rule__Import__ImportedNamespaceAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Import__ImportedNamespaceAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getImportedNamespaceAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1__Impl"


    // $ANTLR start "rule__PosDef__Group__0"
    // InternalSpecDSL.g:840:1: rule__PosDef__Group__0 : rule__PosDef__Group__0__Impl rule__PosDef__Group__1 ;
    public final void rule__PosDef__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:844:1: ( rule__PosDef__Group__0__Impl rule__PosDef__Group__1 )
            // InternalSpecDSL.g:845:2: rule__PosDef__Group__0__Impl rule__PosDef__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__PosDef__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PosDef__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__0"


    // $ANTLR start "rule__PosDef__Group__0__Impl"
    // InternalSpecDSL.g:852:1: rule__PosDef__Group__0__Impl : ( 'def' ) ;
    public final void rule__PosDef__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:856:1: ( ( 'def' ) )
            // InternalSpecDSL.g:857:1: ( 'def' )
            {
            // InternalSpecDSL.g:857:1: ( 'def' )
            // InternalSpecDSL.g:858:2: 'def'
            {
             before(grammarAccess.getPosDefAccess().getDefKeyword_0()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getPosDefAccess().getDefKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__0__Impl"


    // $ANTLR start "rule__PosDef__Group__1"
    // InternalSpecDSL.g:867:1: rule__PosDef__Group__1 : rule__PosDef__Group__1__Impl rule__PosDef__Group__2 ;
    public final void rule__PosDef__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:871:1: ( rule__PosDef__Group__1__Impl rule__PosDef__Group__2 )
            // InternalSpecDSL.g:872:2: rule__PosDef__Group__1__Impl rule__PosDef__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__PosDef__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PosDef__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__1"


    // $ANTLR start "rule__PosDef__Group__1__Impl"
    // InternalSpecDSL.g:879:1: rule__PosDef__Group__1__Impl : ( 'tagset' ) ;
    public final void rule__PosDef__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:883:1: ( ( 'tagset' ) )
            // InternalSpecDSL.g:884:1: ( 'tagset' )
            {
            // InternalSpecDSL.g:884:1: ( 'tagset' )
            // InternalSpecDSL.g:885:2: 'tagset'
            {
             before(grammarAccess.getPosDefAccess().getTagsetKeyword_1()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getPosDefAccess().getTagsetKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__1__Impl"


    // $ANTLR start "rule__PosDef__Group__2"
    // InternalSpecDSL.g:894:1: rule__PosDef__Group__2 : rule__PosDef__Group__2__Impl rule__PosDef__Group__3 ;
    public final void rule__PosDef__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:898:1: ( rule__PosDef__Group__2__Impl rule__PosDef__Group__3 )
            // InternalSpecDSL.g:899:2: rule__PosDef__Group__2__Impl rule__PosDef__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__PosDef__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PosDef__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__2"


    // $ANTLR start "rule__PosDef__Group__2__Impl"
    // InternalSpecDSL.g:906:1: rule__PosDef__Group__2__Impl : ( 'parts-of-speech' ) ;
    public final void rule__PosDef__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:910:1: ( ( 'parts-of-speech' ) )
            // InternalSpecDSL.g:911:1: ( 'parts-of-speech' )
            {
            // InternalSpecDSL.g:911:1: ( 'parts-of-speech' )
            // InternalSpecDSL.g:912:2: 'parts-of-speech'
            {
             before(grammarAccess.getPosDefAccess().getPartsOfSpeechKeyword_2()); 
            match(input,14,FOLLOW_2); 
             after(grammarAccess.getPosDefAccess().getPartsOfSpeechKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__2__Impl"


    // $ANTLR start "rule__PosDef__Group__3"
    // InternalSpecDSL.g:921:1: rule__PosDef__Group__3 : rule__PosDef__Group__3__Impl rule__PosDef__Group__4 ;
    public final void rule__PosDef__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:925:1: ( rule__PosDef__Group__3__Impl rule__PosDef__Group__4 )
            // InternalSpecDSL.g:926:2: rule__PosDef__Group__3__Impl rule__PosDef__Group__4
            {
            pushFollow(FOLLOW_7);
            rule__PosDef__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PosDef__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__3"


    // $ANTLR start "rule__PosDef__Group__3__Impl"
    // InternalSpecDSL.g:933:1: rule__PosDef__Group__3__Impl : ( ( rule__PosDef__NameAssignment_3 ) ) ;
    public final void rule__PosDef__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:937:1: ( ( ( rule__PosDef__NameAssignment_3 ) ) )
            // InternalSpecDSL.g:938:1: ( ( rule__PosDef__NameAssignment_3 ) )
            {
            // InternalSpecDSL.g:938:1: ( ( rule__PosDef__NameAssignment_3 ) )
            // InternalSpecDSL.g:939:2: ( rule__PosDef__NameAssignment_3 )
            {
             before(grammarAccess.getPosDefAccess().getNameAssignment_3()); 
            // InternalSpecDSL.g:940:2: ( rule__PosDef__NameAssignment_3 )
            // InternalSpecDSL.g:940:3: rule__PosDef__NameAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__PosDef__NameAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getPosDefAccess().getNameAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__3__Impl"


    // $ANTLR start "rule__PosDef__Group__4"
    // InternalSpecDSL.g:948:1: rule__PosDef__Group__4 : rule__PosDef__Group__4__Impl rule__PosDef__Group__5 ;
    public final void rule__PosDef__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:952:1: ( rule__PosDef__Group__4__Impl rule__PosDef__Group__5 )
            // InternalSpecDSL.g:953:2: rule__PosDef__Group__4__Impl rule__PosDef__Group__5
            {
            pushFollow(FOLLOW_8);
            rule__PosDef__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PosDef__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__4"


    // $ANTLR start "rule__PosDef__Group__4__Impl"
    // InternalSpecDSL.g:960:1: rule__PosDef__Group__4__Impl : ( '{' ) ;
    public final void rule__PosDef__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:964:1: ( ( '{' ) )
            // InternalSpecDSL.g:965:1: ( '{' )
            {
            // InternalSpecDSL.g:965:1: ( '{' )
            // InternalSpecDSL.g:966:2: '{'
            {
             before(grammarAccess.getPosDefAccess().getLeftCurlyBracketKeyword_4()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getPosDefAccess().getLeftCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__4__Impl"


    // $ANTLR start "rule__PosDef__Group__5"
    // InternalSpecDSL.g:975:1: rule__PosDef__Group__5 : rule__PosDef__Group__5__Impl rule__PosDef__Group__6 ;
    public final void rule__PosDef__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:979:1: ( rule__PosDef__Group__5__Impl rule__PosDef__Group__6 )
            // InternalSpecDSL.g:980:2: rule__PosDef__Group__5__Impl rule__PosDef__Group__6
            {
            pushFollow(FOLLOW_8);
            rule__PosDef__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PosDef__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__5"


    // $ANTLR start "rule__PosDef__Group__5__Impl"
    // InternalSpecDSL.g:987:1: rule__PosDef__Group__5__Impl : ( ( rule__PosDef__TagsAssignment_5 )* ) ;
    public final void rule__PosDef__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:991:1: ( ( ( rule__PosDef__TagsAssignment_5 )* ) )
            // InternalSpecDSL.g:992:1: ( ( rule__PosDef__TagsAssignment_5 )* )
            {
            // InternalSpecDSL.g:992:1: ( ( rule__PosDef__TagsAssignment_5 )* )
            // InternalSpecDSL.g:993:2: ( rule__PosDef__TagsAssignment_5 )*
            {
             before(grammarAccess.getPosDefAccess().getTagsAssignment_5()); 
            // InternalSpecDSL.g:994:2: ( rule__PosDef__TagsAssignment_5 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_ID) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSpecDSL.g:994:3: rule__PosDef__TagsAssignment_5
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__PosDef__TagsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getPosDefAccess().getTagsAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__5__Impl"


    // $ANTLR start "rule__PosDef__Group__6"
    // InternalSpecDSL.g:1002:1: rule__PosDef__Group__6 : rule__PosDef__Group__6__Impl ;
    public final void rule__PosDef__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1006:1: ( rule__PosDef__Group__6__Impl )
            // InternalSpecDSL.g:1007:2: rule__PosDef__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PosDef__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__6"


    // $ANTLR start "rule__PosDef__Group__6__Impl"
    // InternalSpecDSL.g:1013:1: rule__PosDef__Group__6__Impl : ( '}' ) ;
    public final void rule__PosDef__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1017:1: ( ( '}' ) )
            // InternalSpecDSL.g:1018:1: ( '}' )
            {
            // InternalSpecDSL.g:1018:1: ( '}' )
            // InternalSpecDSL.g:1019:2: '}'
            {
             before(grammarAccess.getPosDefAccess().getRightCurlyBracketKeyword_6()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getPosDefAccess().getRightCurlyBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__Group__6__Impl"


    // $ANTLR start "rule__POSTag__Group__0"
    // InternalSpecDSL.g:1029:1: rule__POSTag__Group__0 : rule__POSTag__Group__0__Impl rule__POSTag__Group__1 ;
    public final void rule__POSTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1033:1: ( rule__POSTag__Group__0__Impl rule__POSTag__Group__1 )
            // InternalSpecDSL.g:1034:2: rule__POSTag__Group__0__Impl rule__POSTag__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__POSTag__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__POSTag__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group__0"


    // $ANTLR start "rule__POSTag__Group__0__Impl"
    // InternalSpecDSL.g:1041:1: rule__POSTag__Group__0__Impl : ( ( rule__POSTag__NameAssignment_0 ) ) ;
    public final void rule__POSTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1045:1: ( ( ( rule__POSTag__NameAssignment_0 ) ) )
            // InternalSpecDSL.g:1046:1: ( ( rule__POSTag__NameAssignment_0 ) )
            {
            // InternalSpecDSL.g:1046:1: ( ( rule__POSTag__NameAssignment_0 ) )
            // InternalSpecDSL.g:1047:2: ( rule__POSTag__NameAssignment_0 )
            {
             before(grammarAccess.getPOSTagAccess().getNameAssignment_0()); 
            // InternalSpecDSL.g:1048:2: ( rule__POSTag__NameAssignment_0 )
            // InternalSpecDSL.g:1048:3: rule__POSTag__NameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__POSTag__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getPOSTagAccess().getNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group__0__Impl"


    // $ANTLR start "rule__POSTag__Group__1"
    // InternalSpecDSL.g:1056:1: rule__POSTag__Group__1 : rule__POSTag__Group__1__Impl ;
    public final void rule__POSTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1060:1: ( rule__POSTag__Group__1__Impl )
            // InternalSpecDSL.g:1061:2: rule__POSTag__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__POSTag__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group__1"


    // $ANTLR start "rule__POSTag__Group__1__Impl"
    // InternalSpecDSL.g:1067:1: rule__POSTag__Group__1__Impl : ( ( rule__POSTag__Group_1__0 )? ) ;
    public final void rule__POSTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1071:1: ( ( ( rule__POSTag__Group_1__0 )? ) )
            // InternalSpecDSL.g:1072:1: ( ( rule__POSTag__Group_1__0 )? )
            {
            // InternalSpecDSL.g:1072:1: ( ( rule__POSTag__Group_1__0 )? )
            // InternalSpecDSL.g:1073:2: ( rule__POSTag__Group_1__0 )?
            {
             before(grammarAccess.getPOSTagAccess().getGroup_1()); 
            // InternalSpecDSL.g:1074:2: ( rule__POSTag__Group_1__0 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==17) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalSpecDSL.g:1074:3: rule__POSTag__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__POSTag__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPOSTagAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group__1__Impl"


    // $ANTLR start "rule__POSTag__Group_1__0"
    // InternalSpecDSL.g:1083:1: rule__POSTag__Group_1__0 : rule__POSTag__Group_1__0__Impl rule__POSTag__Group_1__1 ;
    public final void rule__POSTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1087:1: ( rule__POSTag__Group_1__0__Impl rule__POSTag__Group_1__1 )
            // InternalSpecDSL.g:1088:2: rule__POSTag__Group_1__0__Impl rule__POSTag__Group_1__1
            {
            pushFollow(FOLLOW_11);
            rule__POSTag__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__POSTag__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group_1__0"


    // $ANTLR start "rule__POSTag__Group_1__0__Impl"
    // InternalSpecDSL.g:1095:1: rule__POSTag__Group_1__0__Impl : ( '=' ) ;
    public final void rule__POSTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1099:1: ( ( '=' ) )
            // InternalSpecDSL.g:1100:1: ( '=' )
            {
            // InternalSpecDSL.g:1100:1: ( '=' )
            // InternalSpecDSL.g:1101:2: '='
            {
             before(grammarAccess.getPOSTagAccess().getEqualsSignKeyword_1_0()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getPOSTagAccess().getEqualsSignKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group_1__0__Impl"


    // $ANTLR start "rule__POSTag__Group_1__1"
    // InternalSpecDSL.g:1110:1: rule__POSTag__Group_1__1 : rule__POSTag__Group_1__1__Impl ;
    public final void rule__POSTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1114:1: ( rule__POSTag__Group_1__1__Impl )
            // InternalSpecDSL.g:1115:2: rule__POSTag__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__POSTag__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group_1__1"


    // $ANTLR start "rule__POSTag__Group_1__1__Impl"
    // InternalSpecDSL.g:1121:1: rule__POSTag__Group_1__1__Impl : ( ( rule__POSTag__TagnameAssignment_1_1 ) ) ;
    public final void rule__POSTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1125:1: ( ( ( rule__POSTag__TagnameAssignment_1_1 ) ) )
            // InternalSpecDSL.g:1126:1: ( ( rule__POSTag__TagnameAssignment_1_1 ) )
            {
            // InternalSpecDSL.g:1126:1: ( ( rule__POSTag__TagnameAssignment_1_1 ) )
            // InternalSpecDSL.g:1127:2: ( rule__POSTag__TagnameAssignment_1_1 )
            {
             before(grammarAccess.getPOSTagAccess().getTagnameAssignment_1_1()); 
            // InternalSpecDSL.g:1128:2: ( rule__POSTag__TagnameAssignment_1_1 )
            // InternalSpecDSL.g:1128:3: rule__POSTag__TagnameAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__POSTag__TagnameAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getPOSTagAccess().getTagnameAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__Group_1__1__Impl"


    // $ANTLR start "rule__DepDef__Group__0"
    // InternalSpecDSL.g:1137:1: rule__DepDef__Group__0 : rule__DepDef__Group__0__Impl rule__DepDef__Group__1 ;
    public final void rule__DepDef__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1141:1: ( rule__DepDef__Group__0__Impl rule__DepDef__Group__1 )
            // InternalSpecDSL.g:1142:2: rule__DepDef__Group__0__Impl rule__DepDef__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__DepDef__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepDef__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__0"


    // $ANTLR start "rule__DepDef__Group__0__Impl"
    // InternalSpecDSL.g:1149:1: rule__DepDef__Group__0__Impl : ( 'def' ) ;
    public final void rule__DepDef__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1153:1: ( ( 'def' ) )
            // InternalSpecDSL.g:1154:1: ( 'def' )
            {
            // InternalSpecDSL.g:1154:1: ( 'def' )
            // InternalSpecDSL.g:1155:2: 'def'
            {
             before(grammarAccess.getDepDefAccess().getDefKeyword_0()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getDepDefAccess().getDefKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__0__Impl"


    // $ANTLR start "rule__DepDef__Group__1"
    // InternalSpecDSL.g:1164:1: rule__DepDef__Group__1 : rule__DepDef__Group__1__Impl rule__DepDef__Group__2 ;
    public final void rule__DepDef__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1168:1: ( rule__DepDef__Group__1__Impl rule__DepDef__Group__2 )
            // InternalSpecDSL.g:1169:2: rule__DepDef__Group__1__Impl rule__DepDef__Group__2
            {
            pushFollow(FOLLOW_12);
            rule__DepDef__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepDef__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__1"


    // $ANTLR start "rule__DepDef__Group__1__Impl"
    // InternalSpecDSL.g:1176:1: rule__DepDef__Group__1__Impl : ( 'tagset' ) ;
    public final void rule__DepDef__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1180:1: ( ( 'tagset' ) )
            // InternalSpecDSL.g:1181:1: ( 'tagset' )
            {
            // InternalSpecDSL.g:1181:1: ( 'tagset' )
            // InternalSpecDSL.g:1182:2: 'tagset'
            {
             before(grammarAccess.getDepDefAccess().getTagsetKeyword_1()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getDepDefAccess().getTagsetKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__1__Impl"


    // $ANTLR start "rule__DepDef__Group__2"
    // InternalSpecDSL.g:1191:1: rule__DepDef__Group__2 : rule__DepDef__Group__2__Impl rule__DepDef__Group__3 ;
    public final void rule__DepDef__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1195:1: ( rule__DepDef__Group__2__Impl rule__DepDef__Group__3 )
            // InternalSpecDSL.g:1196:2: rule__DepDef__Group__2__Impl rule__DepDef__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__DepDef__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepDef__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__2"


    // $ANTLR start "rule__DepDef__Group__2__Impl"
    // InternalSpecDSL.g:1203:1: rule__DepDef__Group__2__Impl : ( 'dependencies' ) ;
    public final void rule__DepDef__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1207:1: ( ( 'dependencies' ) )
            // InternalSpecDSL.g:1208:1: ( 'dependencies' )
            {
            // InternalSpecDSL.g:1208:1: ( 'dependencies' )
            // InternalSpecDSL.g:1209:2: 'dependencies'
            {
             before(grammarAccess.getDepDefAccess().getDependenciesKeyword_2()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getDepDefAccess().getDependenciesKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__2__Impl"


    // $ANTLR start "rule__DepDef__Group__3"
    // InternalSpecDSL.g:1218:1: rule__DepDef__Group__3 : rule__DepDef__Group__3__Impl rule__DepDef__Group__4 ;
    public final void rule__DepDef__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1222:1: ( rule__DepDef__Group__3__Impl rule__DepDef__Group__4 )
            // InternalSpecDSL.g:1223:2: rule__DepDef__Group__3__Impl rule__DepDef__Group__4
            {
            pushFollow(FOLLOW_7);
            rule__DepDef__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepDef__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__3"


    // $ANTLR start "rule__DepDef__Group__3__Impl"
    // InternalSpecDSL.g:1230:1: rule__DepDef__Group__3__Impl : ( ( rule__DepDef__NameAssignment_3 ) ) ;
    public final void rule__DepDef__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1234:1: ( ( ( rule__DepDef__NameAssignment_3 ) ) )
            // InternalSpecDSL.g:1235:1: ( ( rule__DepDef__NameAssignment_3 ) )
            {
            // InternalSpecDSL.g:1235:1: ( ( rule__DepDef__NameAssignment_3 ) )
            // InternalSpecDSL.g:1236:2: ( rule__DepDef__NameAssignment_3 )
            {
             before(grammarAccess.getDepDefAccess().getNameAssignment_3()); 
            // InternalSpecDSL.g:1237:2: ( rule__DepDef__NameAssignment_3 )
            // InternalSpecDSL.g:1237:3: rule__DepDef__NameAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__DepDef__NameAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getDepDefAccess().getNameAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__3__Impl"


    // $ANTLR start "rule__DepDef__Group__4"
    // InternalSpecDSL.g:1245:1: rule__DepDef__Group__4 : rule__DepDef__Group__4__Impl rule__DepDef__Group__5 ;
    public final void rule__DepDef__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1249:1: ( rule__DepDef__Group__4__Impl rule__DepDef__Group__5 )
            // InternalSpecDSL.g:1250:2: rule__DepDef__Group__4__Impl rule__DepDef__Group__5
            {
            pushFollow(FOLLOW_8);
            rule__DepDef__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepDef__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__4"


    // $ANTLR start "rule__DepDef__Group__4__Impl"
    // InternalSpecDSL.g:1257:1: rule__DepDef__Group__4__Impl : ( '{' ) ;
    public final void rule__DepDef__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1261:1: ( ( '{' ) )
            // InternalSpecDSL.g:1262:1: ( '{' )
            {
            // InternalSpecDSL.g:1262:1: ( '{' )
            // InternalSpecDSL.g:1263:2: '{'
            {
             before(grammarAccess.getDepDefAccess().getLeftCurlyBracketKeyword_4()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getDepDefAccess().getLeftCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__4__Impl"


    // $ANTLR start "rule__DepDef__Group__5"
    // InternalSpecDSL.g:1272:1: rule__DepDef__Group__5 : rule__DepDef__Group__5__Impl rule__DepDef__Group__6 ;
    public final void rule__DepDef__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1276:1: ( rule__DepDef__Group__5__Impl rule__DepDef__Group__6 )
            // InternalSpecDSL.g:1277:2: rule__DepDef__Group__5__Impl rule__DepDef__Group__6
            {
            pushFollow(FOLLOW_8);
            rule__DepDef__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepDef__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__5"


    // $ANTLR start "rule__DepDef__Group__5__Impl"
    // InternalSpecDSL.g:1284:1: rule__DepDef__Group__5__Impl : ( ( rule__DepDef__TagsAssignment_5 )* ) ;
    public final void rule__DepDef__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1288:1: ( ( ( rule__DepDef__TagsAssignment_5 )* ) )
            // InternalSpecDSL.g:1289:1: ( ( rule__DepDef__TagsAssignment_5 )* )
            {
            // InternalSpecDSL.g:1289:1: ( ( rule__DepDef__TagsAssignment_5 )* )
            // InternalSpecDSL.g:1290:2: ( rule__DepDef__TagsAssignment_5 )*
            {
             before(grammarAccess.getDepDefAccess().getTagsAssignment_5()); 
            // InternalSpecDSL.g:1291:2: ( rule__DepDef__TagsAssignment_5 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==RULE_ID) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalSpecDSL.g:1291:3: rule__DepDef__TagsAssignment_5
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__DepDef__TagsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

             after(grammarAccess.getDepDefAccess().getTagsAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__5__Impl"


    // $ANTLR start "rule__DepDef__Group__6"
    // InternalSpecDSL.g:1299:1: rule__DepDef__Group__6 : rule__DepDef__Group__6__Impl ;
    public final void rule__DepDef__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1303:1: ( rule__DepDef__Group__6__Impl )
            // InternalSpecDSL.g:1304:2: rule__DepDef__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DepDef__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__6"


    // $ANTLR start "rule__DepDef__Group__6__Impl"
    // InternalSpecDSL.g:1310:1: rule__DepDef__Group__6__Impl : ( '}' ) ;
    public final void rule__DepDef__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1314:1: ( ( '}' ) )
            // InternalSpecDSL.g:1315:1: ( '}' )
            {
            // InternalSpecDSL.g:1315:1: ( '}' )
            // InternalSpecDSL.g:1316:2: '}'
            {
             before(grammarAccess.getDepDefAccess().getRightCurlyBracketKeyword_6()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getDepDefAccess().getRightCurlyBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__Group__6__Impl"


    // $ANTLR start "rule__DepTag__Group__0"
    // InternalSpecDSL.g:1326:1: rule__DepTag__Group__0 : rule__DepTag__Group__0__Impl rule__DepTag__Group__1 ;
    public final void rule__DepTag__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1330:1: ( rule__DepTag__Group__0__Impl rule__DepTag__Group__1 )
            // InternalSpecDSL.g:1331:2: rule__DepTag__Group__0__Impl rule__DepTag__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__DepTag__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepTag__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group__0"


    // $ANTLR start "rule__DepTag__Group__0__Impl"
    // InternalSpecDSL.g:1338:1: rule__DepTag__Group__0__Impl : ( ( rule__DepTag__NameAssignment_0 ) ) ;
    public final void rule__DepTag__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1342:1: ( ( ( rule__DepTag__NameAssignment_0 ) ) )
            // InternalSpecDSL.g:1343:1: ( ( rule__DepTag__NameAssignment_0 ) )
            {
            // InternalSpecDSL.g:1343:1: ( ( rule__DepTag__NameAssignment_0 ) )
            // InternalSpecDSL.g:1344:2: ( rule__DepTag__NameAssignment_0 )
            {
             before(grammarAccess.getDepTagAccess().getNameAssignment_0()); 
            // InternalSpecDSL.g:1345:2: ( rule__DepTag__NameAssignment_0 )
            // InternalSpecDSL.g:1345:3: rule__DepTag__NameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__DepTag__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getDepTagAccess().getNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group__0__Impl"


    // $ANTLR start "rule__DepTag__Group__1"
    // InternalSpecDSL.g:1353:1: rule__DepTag__Group__1 : rule__DepTag__Group__1__Impl ;
    public final void rule__DepTag__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1357:1: ( rule__DepTag__Group__1__Impl )
            // InternalSpecDSL.g:1358:2: rule__DepTag__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DepTag__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group__1"


    // $ANTLR start "rule__DepTag__Group__1__Impl"
    // InternalSpecDSL.g:1364:1: rule__DepTag__Group__1__Impl : ( ( rule__DepTag__Group_1__0 )? ) ;
    public final void rule__DepTag__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1368:1: ( ( ( rule__DepTag__Group_1__0 )? ) )
            // InternalSpecDSL.g:1369:1: ( ( rule__DepTag__Group_1__0 )? )
            {
            // InternalSpecDSL.g:1369:1: ( ( rule__DepTag__Group_1__0 )? )
            // InternalSpecDSL.g:1370:2: ( rule__DepTag__Group_1__0 )?
            {
             before(grammarAccess.getDepTagAccess().getGroup_1()); 
            // InternalSpecDSL.g:1371:2: ( rule__DepTag__Group_1__0 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==17) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalSpecDSL.g:1371:3: rule__DepTag__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DepTag__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDepTagAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group__1__Impl"


    // $ANTLR start "rule__DepTag__Group_1__0"
    // InternalSpecDSL.g:1380:1: rule__DepTag__Group_1__0 : rule__DepTag__Group_1__0__Impl rule__DepTag__Group_1__1 ;
    public final void rule__DepTag__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1384:1: ( rule__DepTag__Group_1__0__Impl rule__DepTag__Group_1__1 )
            // InternalSpecDSL.g:1385:2: rule__DepTag__Group_1__0__Impl rule__DepTag__Group_1__1
            {
            pushFollow(FOLLOW_11);
            rule__DepTag__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DepTag__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group_1__0"


    // $ANTLR start "rule__DepTag__Group_1__0__Impl"
    // InternalSpecDSL.g:1392:1: rule__DepTag__Group_1__0__Impl : ( '=' ) ;
    public final void rule__DepTag__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1396:1: ( ( '=' ) )
            // InternalSpecDSL.g:1397:1: ( '=' )
            {
            // InternalSpecDSL.g:1397:1: ( '=' )
            // InternalSpecDSL.g:1398:2: '='
            {
             before(grammarAccess.getDepTagAccess().getEqualsSignKeyword_1_0()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getDepTagAccess().getEqualsSignKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group_1__0__Impl"


    // $ANTLR start "rule__DepTag__Group_1__1"
    // InternalSpecDSL.g:1407:1: rule__DepTag__Group_1__1 : rule__DepTag__Group_1__1__Impl ;
    public final void rule__DepTag__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1411:1: ( rule__DepTag__Group_1__1__Impl )
            // InternalSpecDSL.g:1412:2: rule__DepTag__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DepTag__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group_1__1"


    // $ANTLR start "rule__DepTag__Group_1__1__Impl"
    // InternalSpecDSL.g:1418:1: rule__DepTag__Group_1__1__Impl : ( ( rule__DepTag__TagnameAssignment_1_1 ) ) ;
    public final void rule__DepTag__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1422:1: ( ( ( rule__DepTag__TagnameAssignment_1_1 ) ) )
            // InternalSpecDSL.g:1423:1: ( ( rule__DepTag__TagnameAssignment_1_1 ) )
            {
            // InternalSpecDSL.g:1423:1: ( ( rule__DepTag__TagnameAssignment_1_1 ) )
            // InternalSpecDSL.g:1424:2: ( rule__DepTag__TagnameAssignment_1_1 )
            {
             before(grammarAccess.getDepTagAccess().getTagnameAssignment_1_1()); 
            // InternalSpecDSL.g:1425:2: ( rule__DepTag__TagnameAssignment_1_1 )
            // InternalSpecDSL.g:1425:3: rule__DepTag__TagnameAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__DepTag__TagnameAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getDepTagAccess().getTagnameAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__Group_1__1__Impl"


    // $ANTLR start "rule__TreeDef__Group__0"
    // InternalSpecDSL.g:1434:1: rule__TreeDef__Group__0 : rule__TreeDef__Group__0__Impl rule__TreeDef__Group__1 ;
    public final void rule__TreeDef__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1438:1: ( rule__TreeDef__Group__0__Impl rule__TreeDef__Group__1 )
            // InternalSpecDSL.g:1439:2: rule__TreeDef__Group__0__Impl rule__TreeDef__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__TreeDef__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeDef__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__0"


    // $ANTLR start "rule__TreeDef__Group__0__Impl"
    // InternalSpecDSL.g:1446:1: rule__TreeDef__Group__0__Impl : ( () ) ;
    public final void rule__TreeDef__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1450:1: ( ( () ) )
            // InternalSpecDSL.g:1451:1: ( () )
            {
            // InternalSpecDSL.g:1451:1: ( () )
            // InternalSpecDSL.g:1452:2: ()
            {
             before(grammarAccess.getTreeDefAccess().getTreeDefAction_0()); 
            // InternalSpecDSL.g:1453:2: ()
            // InternalSpecDSL.g:1453:3: 
            {
            }

             after(grammarAccess.getTreeDefAccess().getTreeDefAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__0__Impl"


    // $ANTLR start "rule__TreeDef__Group__1"
    // InternalSpecDSL.g:1461:1: rule__TreeDef__Group__1 : rule__TreeDef__Group__1__Impl rule__TreeDef__Group__2 ;
    public final void rule__TreeDef__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1465:1: ( rule__TreeDef__Group__1__Impl rule__TreeDef__Group__2 )
            // InternalSpecDSL.g:1466:2: rule__TreeDef__Group__1__Impl rule__TreeDef__Group__2
            {
            pushFollow(FOLLOW_14);
            rule__TreeDef__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeDef__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__1"


    // $ANTLR start "rule__TreeDef__Group__1__Impl"
    // InternalSpecDSL.g:1473:1: rule__TreeDef__Group__1__Impl : ( 'def' ) ;
    public final void rule__TreeDef__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1477:1: ( ( 'def' ) )
            // InternalSpecDSL.g:1478:1: ( 'def' )
            {
            // InternalSpecDSL.g:1478:1: ( 'def' )
            // InternalSpecDSL.g:1479:2: 'def'
            {
             before(grammarAccess.getTreeDefAccess().getDefKeyword_1()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getTreeDefAccess().getDefKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__1__Impl"


    // $ANTLR start "rule__TreeDef__Group__2"
    // InternalSpecDSL.g:1488:1: rule__TreeDef__Group__2 : rule__TreeDef__Group__2__Impl rule__TreeDef__Group__3 ;
    public final void rule__TreeDef__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1492:1: ( rule__TreeDef__Group__2__Impl rule__TreeDef__Group__3 )
            // InternalSpecDSL.g:1493:2: rule__TreeDef__Group__2__Impl rule__TreeDef__Group__3
            {
            pushFollow(FOLLOW_15);
            rule__TreeDef__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeDef__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__2"


    // $ANTLR start "rule__TreeDef__Group__2__Impl"
    // InternalSpecDSL.g:1500:1: rule__TreeDef__Group__2__Impl : ( 'subtrees' ) ;
    public final void rule__TreeDef__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1504:1: ( ( 'subtrees' ) )
            // InternalSpecDSL.g:1505:1: ( 'subtrees' )
            {
            // InternalSpecDSL.g:1505:1: ( 'subtrees' )
            // InternalSpecDSL.g:1506:2: 'subtrees'
            {
             before(grammarAccess.getTreeDefAccess().getSubtreesKeyword_2()); 
            match(input,19,FOLLOW_2); 
             after(grammarAccess.getTreeDefAccess().getSubtreesKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__2__Impl"


    // $ANTLR start "rule__TreeDef__Group__3"
    // InternalSpecDSL.g:1515:1: rule__TreeDef__Group__3 : rule__TreeDef__Group__3__Impl ;
    public final void rule__TreeDef__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1519:1: ( rule__TreeDef__Group__3__Impl )
            // InternalSpecDSL.g:1520:2: rule__TreeDef__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__3"


    // $ANTLR start "rule__TreeDef__Group__3__Impl"
    // InternalSpecDSL.g:1526:1: rule__TreeDef__Group__3__Impl : ( ( rule__TreeDef__Alternatives_3 ) ) ;
    public final void rule__TreeDef__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1530:1: ( ( ( rule__TreeDef__Alternatives_3 ) ) )
            // InternalSpecDSL.g:1531:1: ( ( rule__TreeDef__Alternatives_3 ) )
            {
            // InternalSpecDSL.g:1531:1: ( ( rule__TreeDef__Alternatives_3 ) )
            // InternalSpecDSL.g:1532:2: ( rule__TreeDef__Alternatives_3 )
            {
             before(grammarAccess.getTreeDefAccess().getAlternatives_3()); 
            // InternalSpecDSL.g:1533:2: ( rule__TreeDef__Alternatives_3 )
            // InternalSpecDSL.g:1533:3: rule__TreeDef__Alternatives_3
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__Alternatives_3();

            state._fsp--;


            }

             after(grammarAccess.getTreeDefAccess().getAlternatives_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group__3__Impl"


    // $ANTLR start "rule__TreeDef__Group_3_0__0"
    // InternalSpecDSL.g:1542:1: rule__TreeDef__Group_3_0__0 : rule__TreeDef__Group_3_0__0__Impl rule__TreeDef__Group_3_0__1 ;
    public final void rule__TreeDef__Group_3_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1546:1: ( rule__TreeDef__Group_3_0__0__Impl rule__TreeDef__Group_3_0__1 )
            // InternalSpecDSL.g:1547:2: rule__TreeDef__Group_3_0__0__Impl rule__TreeDef__Group_3_0__1
            {
            pushFollow(FOLLOW_16);
            rule__TreeDef__Group_3_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeDef__Group_3_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_0__0"


    // $ANTLR start "rule__TreeDef__Group_3_0__0__Impl"
    // InternalSpecDSL.g:1554:1: rule__TreeDef__Group_3_0__0__Impl : ( '{' ) ;
    public final void rule__TreeDef__Group_3_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1558:1: ( ( '{' ) )
            // InternalSpecDSL.g:1559:1: ( '{' )
            {
            // InternalSpecDSL.g:1559:1: ( '{' )
            // InternalSpecDSL.g:1560:2: '{'
            {
             before(grammarAccess.getTreeDefAccess().getLeftCurlyBracketKeyword_3_0_0()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getTreeDefAccess().getLeftCurlyBracketKeyword_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_0__0__Impl"


    // $ANTLR start "rule__TreeDef__Group_3_0__1"
    // InternalSpecDSL.g:1569:1: rule__TreeDef__Group_3_0__1 : rule__TreeDef__Group_3_0__1__Impl rule__TreeDef__Group_3_0__2 ;
    public final void rule__TreeDef__Group_3_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1573:1: ( rule__TreeDef__Group_3_0__1__Impl rule__TreeDef__Group_3_0__2 )
            // InternalSpecDSL.g:1574:2: rule__TreeDef__Group_3_0__1__Impl rule__TreeDef__Group_3_0__2
            {
            pushFollow(FOLLOW_16);
            rule__TreeDef__Group_3_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeDef__Group_3_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_0__1"


    // $ANTLR start "rule__TreeDef__Group_3_0__1__Impl"
    // InternalSpecDSL.g:1581:1: rule__TreeDef__Group_3_0__1__Impl : ( ( rule__TreeDef__TreesAssignment_3_0_1 )* ) ;
    public final void rule__TreeDef__Group_3_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1585:1: ( ( ( rule__TreeDef__TreesAssignment_3_0_1 )* ) )
            // InternalSpecDSL.g:1586:1: ( ( rule__TreeDef__TreesAssignment_3_0_1 )* )
            {
            // InternalSpecDSL.g:1586:1: ( ( rule__TreeDef__TreesAssignment_3_0_1 )* )
            // InternalSpecDSL.g:1587:2: ( rule__TreeDef__TreesAssignment_3_0_1 )*
            {
             before(grammarAccess.getTreeDefAccess().getTreesAssignment_3_0_1()); 
            // InternalSpecDSL.g:1588:2: ( rule__TreeDef__TreesAssignment_3_0_1 )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalSpecDSL.g:1588:3: rule__TreeDef__TreesAssignment_3_0_1
            	    {
            	    pushFollow(FOLLOW_17);
            	    rule__TreeDef__TreesAssignment_3_0_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

             after(grammarAccess.getTreeDefAccess().getTreesAssignment_3_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_0__1__Impl"


    // $ANTLR start "rule__TreeDef__Group_3_0__2"
    // InternalSpecDSL.g:1596:1: rule__TreeDef__Group_3_0__2 : rule__TreeDef__Group_3_0__2__Impl ;
    public final void rule__TreeDef__Group_3_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1600:1: ( rule__TreeDef__Group_3_0__2__Impl )
            // InternalSpecDSL.g:1601:2: rule__TreeDef__Group_3_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__Group_3_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_0__2"


    // $ANTLR start "rule__TreeDef__Group_3_0__2__Impl"
    // InternalSpecDSL.g:1607:1: rule__TreeDef__Group_3_0__2__Impl : ( '}' ) ;
    public final void rule__TreeDef__Group_3_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1611:1: ( ( '}' ) )
            // InternalSpecDSL.g:1612:1: ( '}' )
            {
            // InternalSpecDSL.g:1612:1: ( '}' )
            // InternalSpecDSL.g:1613:2: '}'
            {
             before(grammarAccess.getTreeDefAccess().getRightCurlyBracketKeyword_3_0_2()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getTreeDefAccess().getRightCurlyBracketKeyword_3_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_0__2__Impl"


    // $ANTLR start "rule__TreeDef__Group_3_1__0"
    // InternalSpecDSL.g:1623:1: rule__TreeDef__Group_3_1__0 : rule__TreeDef__Group_3_1__0__Impl rule__TreeDef__Group_3_1__1 ;
    public final void rule__TreeDef__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1627:1: ( rule__TreeDef__Group_3_1__0__Impl rule__TreeDef__Group_3_1__1 )
            // InternalSpecDSL.g:1628:2: rule__TreeDef__Group_3_1__0__Impl rule__TreeDef__Group_3_1__1
            {
            pushFollow(FOLLOW_18);
            rule__TreeDef__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeDef__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1__0"


    // $ANTLR start "rule__TreeDef__Group_3_1__0__Impl"
    // InternalSpecDSL.g:1635:1: rule__TreeDef__Group_3_1__0__Impl : ( ( rule__TreeDef__TreesAssignment_3_1_0 ) ) ;
    public final void rule__TreeDef__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1639:1: ( ( ( rule__TreeDef__TreesAssignment_3_1_0 ) ) )
            // InternalSpecDSL.g:1640:1: ( ( rule__TreeDef__TreesAssignment_3_1_0 ) )
            {
            // InternalSpecDSL.g:1640:1: ( ( rule__TreeDef__TreesAssignment_3_1_0 ) )
            // InternalSpecDSL.g:1641:2: ( rule__TreeDef__TreesAssignment_3_1_0 )
            {
             before(grammarAccess.getTreeDefAccess().getTreesAssignment_3_1_0()); 
            // InternalSpecDSL.g:1642:2: ( rule__TreeDef__TreesAssignment_3_1_0 )
            // InternalSpecDSL.g:1642:3: rule__TreeDef__TreesAssignment_3_1_0
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__TreesAssignment_3_1_0();

            state._fsp--;


            }

             after(grammarAccess.getTreeDefAccess().getTreesAssignment_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1__0__Impl"


    // $ANTLR start "rule__TreeDef__Group_3_1__1"
    // InternalSpecDSL.g:1650:1: rule__TreeDef__Group_3_1__1 : rule__TreeDef__Group_3_1__1__Impl ;
    public final void rule__TreeDef__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1654:1: ( rule__TreeDef__Group_3_1__1__Impl )
            // InternalSpecDSL.g:1655:2: rule__TreeDef__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1__1"


    // $ANTLR start "rule__TreeDef__Group_3_1__1__Impl"
    // InternalSpecDSL.g:1661:1: rule__TreeDef__Group_3_1__1__Impl : ( ( rule__TreeDef__Group_3_1_1__0 )* ) ;
    public final void rule__TreeDef__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1665:1: ( ( ( rule__TreeDef__Group_3_1_1__0 )* ) )
            // InternalSpecDSL.g:1666:1: ( ( rule__TreeDef__Group_3_1_1__0 )* )
            {
            // InternalSpecDSL.g:1666:1: ( ( rule__TreeDef__Group_3_1_1__0 )* )
            // InternalSpecDSL.g:1667:2: ( rule__TreeDef__Group_3_1_1__0 )*
            {
             before(grammarAccess.getTreeDefAccess().getGroup_3_1_1()); 
            // InternalSpecDSL.g:1668:2: ( rule__TreeDef__Group_3_1_1__0 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==20) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSpecDSL.g:1668:3: rule__TreeDef__Group_3_1_1__0
            	    {
            	    pushFollow(FOLLOW_19);
            	    rule__TreeDef__Group_3_1_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getTreeDefAccess().getGroup_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1__1__Impl"


    // $ANTLR start "rule__TreeDef__Group_3_1_1__0"
    // InternalSpecDSL.g:1677:1: rule__TreeDef__Group_3_1_1__0 : rule__TreeDef__Group_3_1_1__0__Impl rule__TreeDef__Group_3_1_1__1 ;
    public final void rule__TreeDef__Group_3_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1681:1: ( rule__TreeDef__Group_3_1_1__0__Impl rule__TreeDef__Group_3_1_1__1 )
            // InternalSpecDSL.g:1682:2: rule__TreeDef__Group_3_1_1__0__Impl rule__TreeDef__Group_3_1_1__1
            {
            pushFollow(FOLLOW_15);
            rule__TreeDef__Group_3_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeDef__Group_3_1_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1_1__0"


    // $ANTLR start "rule__TreeDef__Group_3_1_1__0__Impl"
    // InternalSpecDSL.g:1689:1: rule__TreeDef__Group_3_1_1__0__Impl : ( ',' ) ;
    public final void rule__TreeDef__Group_3_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1693:1: ( ( ',' ) )
            // InternalSpecDSL.g:1694:1: ( ',' )
            {
            // InternalSpecDSL.g:1694:1: ( ',' )
            // InternalSpecDSL.g:1695:2: ','
            {
             before(grammarAccess.getTreeDefAccess().getCommaKeyword_3_1_1_0()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getTreeDefAccess().getCommaKeyword_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1_1__0__Impl"


    // $ANTLR start "rule__TreeDef__Group_3_1_1__1"
    // InternalSpecDSL.g:1704:1: rule__TreeDef__Group_3_1_1__1 : rule__TreeDef__Group_3_1_1__1__Impl ;
    public final void rule__TreeDef__Group_3_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1708:1: ( rule__TreeDef__Group_3_1_1__1__Impl )
            // InternalSpecDSL.g:1709:2: rule__TreeDef__Group_3_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__Group_3_1_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1_1__1"


    // $ANTLR start "rule__TreeDef__Group_3_1_1__1__Impl"
    // InternalSpecDSL.g:1715:1: rule__TreeDef__Group_3_1_1__1__Impl : ( ( rule__TreeDef__TreesAssignment_3_1_1_1 ) ) ;
    public final void rule__TreeDef__Group_3_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1719:1: ( ( ( rule__TreeDef__TreesAssignment_3_1_1_1 ) ) )
            // InternalSpecDSL.g:1720:1: ( ( rule__TreeDef__TreesAssignment_3_1_1_1 ) )
            {
            // InternalSpecDSL.g:1720:1: ( ( rule__TreeDef__TreesAssignment_3_1_1_1 ) )
            // InternalSpecDSL.g:1721:2: ( rule__TreeDef__TreesAssignment_3_1_1_1 )
            {
             before(grammarAccess.getTreeDefAccess().getTreesAssignment_3_1_1_1()); 
            // InternalSpecDSL.g:1722:2: ( rule__TreeDef__TreesAssignment_3_1_1_1 )
            // InternalSpecDSL.g:1722:3: rule__TreeDef__TreesAssignment_3_1_1_1
            {
            pushFollow(FOLLOW_2);
            rule__TreeDef__TreesAssignment_3_1_1_1();

            state._fsp--;


            }

             after(grammarAccess.getTreeDefAccess().getTreesAssignment_3_1_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__Group_3_1_1__1__Impl"


    // $ANTLR start "rule__Rule__Group__0"
    // InternalSpecDSL.g:1731:1: rule__Rule__Group__0 : rule__Rule__Group__0__Impl rule__Rule__Group__1 ;
    public final void rule__Rule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1735:1: ( rule__Rule__Group__0__Impl rule__Rule__Group__1 )
            // InternalSpecDSL.g:1736:2: rule__Rule__Group__0__Impl rule__Rule__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__Rule__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Rule__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__0"


    // $ANTLR start "rule__Rule__Group__0__Impl"
    // InternalSpecDSL.g:1743:1: rule__Rule__Group__0__Impl : ( 'def' ) ;
    public final void rule__Rule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1747:1: ( ( 'def' ) )
            // InternalSpecDSL.g:1748:1: ( 'def' )
            {
            // InternalSpecDSL.g:1748:1: ( 'def' )
            // InternalSpecDSL.g:1749:2: 'def'
            {
             before(grammarAccess.getRuleAccess().getDefKeyword_0()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getRuleAccess().getDefKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__0__Impl"


    // $ANTLR start "rule__Rule__Group__1"
    // InternalSpecDSL.g:1758:1: rule__Rule__Group__1 : rule__Rule__Group__1__Impl rule__Rule__Group__2 ;
    public final void rule__Rule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1762:1: ( rule__Rule__Group__1__Impl rule__Rule__Group__2 )
            // InternalSpecDSL.g:1763:2: rule__Rule__Group__1__Impl rule__Rule__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__Rule__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Rule__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__1"


    // $ANTLR start "rule__Rule__Group__1__Impl"
    // InternalSpecDSL.g:1770:1: rule__Rule__Group__1__Impl : ( 'rule' ) ;
    public final void rule__Rule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1774:1: ( ( 'rule' ) )
            // InternalSpecDSL.g:1775:1: ( 'rule' )
            {
            // InternalSpecDSL.g:1775:1: ( 'rule' )
            // InternalSpecDSL.g:1776:2: 'rule'
            {
             before(grammarAccess.getRuleAccess().getRuleKeyword_1()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getRuleAccess().getRuleKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__1__Impl"


    // $ANTLR start "rule__Rule__Group__2"
    // InternalSpecDSL.g:1785:1: rule__Rule__Group__2 : rule__Rule__Group__2__Impl rule__Rule__Group__3 ;
    public final void rule__Rule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1789:1: ( rule__Rule__Group__2__Impl rule__Rule__Group__3 )
            // InternalSpecDSL.g:1790:2: rule__Rule__Group__2__Impl rule__Rule__Group__3
            {
            pushFollow(FOLLOW_7);
            rule__Rule__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Rule__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__2"


    // $ANTLR start "rule__Rule__Group__2__Impl"
    // InternalSpecDSL.g:1797:1: rule__Rule__Group__2__Impl : ( ( rule__Rule__NameAssignment_2 ) ) ;
    public final void rule__Rule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1801:1: ( ( ( rule__Rule__NameAssignment_2 ) ) )
            // InternalSpecDSL.g:1802:1: ( ( rule__Rule__NameAssignment_2 ) )
            {
            // InternalSpecDSL.g:1802:1: ( ( rule__Rule__NameAssignment_2 ) )
            // InternalSpecDSL.g:1803:2: ( rule__Rule__NameAssignment_2 )
            {
             before(grammarAccess.getRuleAccess().getNameAssignment_2()); 
            // InternalSpecDSL.g:1804:2: ( rule__Rule__NameAssignment_2 )
            // InternalSpecDSL.g:1804:3: rule__Rule__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Rule__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getRuleAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__2__Impl"


    // $ANTLR start "rule__Rule__Group__3"
    // InternalSpecDSL.g:1812:1: rule__Rule__Group__3 : rule__Rule__Group__3__Impl rule__Rule__Group__4 ;
    public final void rule__Rule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1816:1: ( rule__Rule__Group__3__Impl rule__Rule__Group__4 )
            // InternalSpecDSL.g:1817:2: rule__Rule__Group__3__Impl rule__Rule__Group__4
            {
            pushFollow(FOLLOW_21);
            rule__Rule__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Rule__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__3"


    // $ANTLR start "rule__Rule__Group__3__Impl"
    // InternalSpecDSL.g:1824:1: rule__Rule__Group__3__Impl : ( '{' ) ;
    public final void rule__Rule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1828:1: ( ( '{' ) )
            // InternalSpecDSL.g:1829:1: ( '{' )
            {
            // InternalSpecDSL.g:1829:1: ( '{' )
            // InternalSpecDSL.g:1830:2: '{'
            {
             before(grammarAccess.getRuleAccess().getLeftCurlyBracketKeyword_3()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getRuleAccess().getLeftCurlyBracketKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__3__Impl"


    // $ANTLR start "rule__Rule__Group__4"
    // InternalSpecDSL.g:1839:1: rule__Rule__Group__4 : rule__Rule__Group__4__Impl rule__Rule__Group__5 ;
    public final void rule__Rule__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1843:1: ( rule__Rule__Group__4__Impl rule__Rule__Group__5 )
            // InternalSpecDSL.g:1844:2: rule__Rule__Group__4__Impl rule__Rule__Group__5
            {
            pushFollow(FOLLOW_22);
            rule__Rule__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Rule__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__4"


    // $ANTLR start "rule__Rule__Group__4__Impl"
    // InternalSpecDSL.g:1851:1: rule__Rule__Group__4__Impl : ( ( ( rule__Rule__DependenciesAssignment_4 ) ) ( ( rule__Rule__DependenciesAssignment_4 )* ) ) ;
    public final void rule__Rule__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1855:1: ( ( ( ( rule__Rule__DependenciesAssignment_4 ) ) ( ( rule__Rule__DependenciesAssignment_4 )* ) ) )
            // InternalSpecDSL.g:1856:1: ( ( ( rule__Rule__DependenciesAssignment_4 ) ) ( ( rule__Rule__DependenciesAssignment_4 )* ) )
            {
            // InternalSpecDSL.g:1856:1: ( ( ( rule__Rule__DependenciesAssignment_4 ) ) ( ( rule__Rule__DependenciesAssignment_4 )* ) )
            // InternalSpecDSL.g:1857:2: ( ( rule__Rule__DependenciesAssignment_4 ) ) ( ( rule__Rule__DependenciesAssignment_4 )* )
            {
            // InternalSpecDSL.g:1857:2: ( ( rule__Rule__DependenciesAssignment_4 ) )
            // InternalSpecDSL.g:1858:3: ( rule__Rule__DependenciesAssignment_4 )
            {
             before(grammarAccess.getRuleAccess().getDependenciesAssignment_4()); 
            // InternalSpecDSL.g:1859:3: ( rule__Rule__DependenciesAssignment_4 )
            // InternalSpecDSL.g:1859:4: rule__Rule__DependenciesAssignment_4
            {
            pushFollow(FOLLOW_23);
            rule__Rule__DependenciesAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getRuleAccess().getDependenciesAssignment_4()); 

            }

            // InternalSpecDSL.g:1862:2: ( ( rule__Rule__DependenciesAssignment_4 )* )
            // InternalSpecDSL.g:1863:3: ( rule__Rule__DependenciesAssignment_4 )*
            {
             before(grammarAccess.getRuleAccess().getDependenciesAssignment_4()); 
            // InternalSpecDSL.g:1864:3: ( rule__Rule__DependenciesAssignment_4 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=RULE_ID && LA18_0<=RULE_STRING)||LA18_0==24||LA18_0==28||(LA18_0>=31 && LA18_0<=32)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalSpecDSL.g:1864:4: rule__Rule__DependenciesAssignment_4
            	    {
            	    pushFollow(FOLLOW_23);
            	    rule__Rule__DependenciesAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

             after(grammarAccess.getRuleAccess().getDependenciesAssignment_4()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__4__Impl"


    // $ANTLR start "rule__Rule__Group__5"
    // InternalSpecDSL.g:1873:1: rule__Rule__Group__5 : rule__Rule__Group__5__Impl ;
    public final void rule__Rule__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1877:1: ( rule__Rule__Group__5__Impl )
            // InternalSpecDSL.g:1878:2: rule__Rule__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Rule__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__5"


    // $ANTLR start "rule__Rule__Group__5__Impl"
    // InternalSpecDSL.g:1884:1: rule__Rule__Group__5__Impl : ( '}' ) ;
    public final void rule__Rule__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1888:1: ( ( '}' ) )
            // InternalSpecDSL.g:1889:1: ( '}' )
            {
            // InternalSpecDSL.g:1889:1: ( '}' )
            // InternalSpecDSL.g:1890:2: '}'
            {
             before(grammarAccess.getRuleAccess().getRightCurlyBracketKeyword_5()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getRuleAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__Group__5__Impl"


    // $ANTLR start "rule__DependencyRule__Group_0__0"
    // InternalSpecDSL.g:1900:1: rule__DependencyRule__Group_0__0 : rule__DependencyRule__Group_0__0__Impl rule__DependencyRule__Group_0__1 ;
    public final void rule__DependencyRule__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1904:1: ( rule__DependencyRule__Group_0__0__Impl rule__DependencyRule__Group_0__1 )
            // InternalSpecDSL.g:1905:2: rule__DependencyRule__Group_0__0__Impl rule__DependencyRule__Group_0__1
            {
            pushFollow(FOLLOW_24);
            rule__DependencyRule__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__0"


    // $ANTLR start "rule__DependencyRule__Group_0__0__Impl"
    // InternalSpecDSL.g:1912:1: rule__DependencyRule__Group_0__0__Impl : ( ( rule__DependencyRule__LeftNodeAssignment_0_0 ) ) ;
    public final void rule__DependencyRule__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1916:1: ( ( ( rule__DependencyRule__LeftNodeAssignment_0_0 ) ) )
            // InternalSpecDSL.g:1917:1: ( ( rule__DependencyRule__LeftNodeAssignment_0_0 ) )
            {
            // InternalSpecDSL.g:1917:1: ( ( rule__DependencyRule__LeftNodeAssignment_0_0 ) )
            // InternalSpecDSL.g:1918:2: ( rule__DependencyRule__LeftNodeAssignment_0_0 )
            {
             before(grammarAccess.getDependencyRuleAccess().getLeftNodeAssignment_0_0()); 
            // InternalSpecDSL.g:1919:2: ( rule__DependencyRule__LeftNodeAssignment_0_0 )
            // InternalSpecDSL.g:1919:3: rule__DependencyRule__LeftNodeAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__LeftNodeAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getLeftNodeAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__0__Impl"


    // $ANTLR start "rule__DependencyRule__Group_0__1"
    // InternalSpecDSL.g:1927:1: rule__DependencyRule__Group_0__1 : rule__DependencyRule__Group_0__1__Impl rule__DependencyRule__Group_0__2 ;
    public final void rule__DependencyRule__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1931:1: ( rule__DependencyRule__Group_0__1__Impl rule__DependencyRule__Group_0__2 )
            // InternalSpecDSL.g:1932:2: rule__DependencyRule__Group_0__1__Impl rule__DependencyRule__Group_0__2
            {
            pushFollow(FOLLOW_4);
            rule__DependencyRule__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__1"


    // $ANTLR start "rule__DependencyRule__Group_0__1__Impl"
    // InternalSpecDSL.g:1939:1: rule__DependencyRule__Group_0__1__Impl : ( '-' ) ;
    public final void rule__DependencyRule__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1943:1: ( ( '-' ) )
            // InternalSpecDSL.g:1944:1: ( '-' )
            {
            // InternalSpecDSL.g:1944:1: ( '-' )
            // InternalSpecDSL.g:1945:2: '-'
            {
             before(grammarAccess.getDependencyRuleAccess().getHyphenMinusKeyword_0_1()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getDependencyRuleAccess().getHyphenMinusKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__1__Impl"


    // $ANTLR start "rule__DependencyRule__Group_0__2"
    // InternalSpecDSL.g:1954:1: rule__DependencyRule__Group_0__2 : rule__DependencyRule__Group_0__2__Impl rule__DependencyRule__Group_0__3 ;
    public final void rule__DependencyRule__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1958:1: ( rule__DependencyRule__Group_0__2__Impl rule__DependencyRule__Group_0__3 )
            // InternalSpecDSL.g:1959:2: rule__DependencyRule__Group_0__2__Impl rule__DependencyRule__Group_0__3
            {
            pushFollow(FOLLOW_25);
            rule__DependencyRule__Group_0__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_0__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__2"


    // $ANTLR start "rule__DependencyRule__Group_0__2__Impl"
    // InternalSpecDSL.g:1966:1: rule__DependencyRule__Group_0__2__Impl : ( ( rule__DependencyRule__DTagAssignment_0_2 ) ) ;
    public final void rule__DependencyRule__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1970:1: ( ( ( rule__DependencyRule__DTagAssignment_0_2 ) ) )
            // InternalSpecDSL.g:1971:1: ( ( rule__DependencyRule__DTagAssignment_0_2 ) )
            {
            // InternalSpecDSL.g:1971:1: ( ( rule__DependencyRule__DTagAssignment_0_2 ) )
            // InternalSpecDSL.g:1972:2: ( rule__DependencyRule__DTagAssignment_0_2 )
            {
             before(grammarAccess.getDependencyRuleAccess().getDTagAssignment_0_2()); 
            // InternalSpecDSL.g:1973:2: ( rule__DependencyRule__DTagAssignment_0_2 )
            // InternalSpecDSL.g:1973:3: rule__DependencyRule__DTagAssignment_0_2
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__DTagAssignment_0_2();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getDTagAssignment_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__2__Impl"


    // $ANTLR start "rule__DependencyRule__Group_0__3"
    // InternalSpecDSL.g:1981:1: rule__DependencyRule__Group_0__3 : rule__DependencyRule__Group_0__3__Impl rule__DependencyRule__Group_0__4 ;
    public final void rule__DependencyRule__Group_0__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1985:1: ( rule__DependencyRule__Group_0__3__Impl rule__DependencyRule__Group_0__4 )
            // InternalSpecDSL.g:1986:2: rule__DependencyRule__Group_0__3__Impl rule__DependencyRule__Group_0__4
            {
            pushFollow(FOLLOW_21);
            rule__DependencyRule__Group_0__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_0__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__3"


    // $ANTLR start "rule__DependencyRule__Group_0__3__Impl"
    // InternalSpecDSL.g:1993:1: rule__DependencyRule__Group_0__3__Impl : ( '->' ) ;
    public final void rule__DependencyRule__Group_0__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:1997:1: ( ( '->' ) )
            // InternalSpecDSL.g:1998:1: ( '->' )
            {
            // InternalSpecDSL.g:1998:1: ( '->' )
            // InternalSpecDSL.g:1999:2: '->'
            {
             before(grammarAccess.getDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_0_3()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_0_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__3__Impl"


    // $ANTLR start "rule__DependencyRule__Group_0__4"
    // InternalSpecDSL.g:2008:1: rule__DependencyRule__Group_0__4 : rule__DependencyRule__Group_0__4__Impl ;
    public final void rule__DependencyRule__Group_0__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2012:1: ( rule__DependencyRule__Group_0__4__Impl )
            // InternalSpecDSL.g:2013:2: rule__DependencyRule__Group_0__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_0__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__4"


    // $ANTLR start "rule__DependencyRule__Group_0__4__Impl"
    // InternalSpecDSL.g:2019:1: rule__DependencyRule__Group_0__4__Impl : ( ( rule__DependencyRule__RightNodeAssignment_0_4 ) ) ;
    public final void rule__DependencyRule__Group_0__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2023:1: ( ( ( rule__DependencyRule__RightNodeAssignment_0_4 ) ) )
            // InternalSpecDSL.g:2024:1: ( ( rule__DependencyRule__RightNodeAssignment_0_4 ) )
            {
            // InternalSpecDSL.g:2024:1: ( ( rule__DependencyRule__RightNodeAssignment_0_4 ) )
            // InternalSpecDSL.g:2025:2: ( rule__DependencyRule__RightNodeAssignment_0_4 )
            {
             before(grammarAccess.getDependencyRuleAccess().getRightNodeAssignment_0_4()); 
            // InternalSpecDSL.g:2026:2: ( rule__DependencyRule__RightNodeAssignment_0_4 )
            // InternalSpecDSL.g:2026:3: rule__DependencyRule__RightNodeAssignment_0_4
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__RightNodeAssignment_0_4();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getRightNodeAssignment_0_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_0__4__Impl"


    // $ANTLR start "rule__DependencyRule__Group_1__0"
    // InternalSpecDSL.g:2035:1: rule__DependencyRule__Group_1__0 : rule__DependencyRule__Group_1__0__Impl rule__DependencyRule__Group_1__1 ;
    public final void rule__DependencyRule__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2039:1: ( rule__DependencyRule__Group_1__0__Impl rule__DependencyRule__Group_1__1 )
            // InternalSpecDSL.g:2040:2: rule__DependencyRule__Group_1__0__Impl rule__DependencyRule__Group_1__1
            {
            pushFollow(FOLLOW_24);
            rule__DependencyRule__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__0"


    // $ANTLR start "rule__DependencyRule__Group_1__0__Impl"
    // InternalSpecDSL.g:2047:1: rule__DependencyRule__Group_1__0__Impl : ( ( rule__DependencyRule__LeftNodeAssignment_1_0 ) ) ;
    public final void rule__DependencyRule__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2051:1: ( ( ( rule__DependencyRule__LeftNodeAssignment_1_0 ) ) )
            // InternalSpecDSL.g:2052:1: ( ( rule__DependencyRule__LeftNodeAssignment_1_0 ) )
            {
            // InternalSpecDSL.g:2052:1: ( ( rule__DependencyRule__LeftNodeAssignment_1_0 ) )
            // InternalSpecDSL.g:2053:2: ( rule__DependencyRule__LeftNodeAssignment_1_0 )
            {
             before(grammarAccess.getDependencyRuleAccess().getLeftNodeAssignment_1_0()); 
            // InternalSpecDSL.g:2054:2: ( rule__DependencyRule__LeftNodeAssignment_1_0 )
            // InternalSpecDSL.g:2054:3: rule__DependencyRule__LeftNodeAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__LeftNodeAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getLeftNodeAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__0__Impl"


    // $ANTLR start "rule__DependencyRule__Group_1__1"
    // InternalSpecDSL.g:2062:1: rule__DependencyRule__Group_1__1 : rule__DependencyRule__Group_1__1__Impl rule__DependencyRule__Group_1__2 ;
    public final void rule__DependencyRule__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2066:1: ( rule__DependencyRule__Group_1__1__Impl rule__DependencyRule__Group_1__2 )
            // InternalSpecDSL.g:2067:2: rule__DependencyRule__Group_1__1__Impl rule__DependencyRule__Group_1__2
            {
            pushFollow(FOLLOW_4);
            rule__DependencyRule__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__1"


    // $ANTLR start "rule__DependencyRule__Group_1__1__Impl"
    // InternalSpecDSL.g:2074:1: rule__DependencyRule__Group_1__1__Impl : ( '-' ) ;
    public final void rule__DependencyRule__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2078:1: ( ( '-' ) )
            // InternalSpecDSL.g:2079:1: ( '-' )
            {
            // InternalSpecDSL.g:2079:1: ( '-' )
            // InternalSpecDSL.g:2080:2: '-'
            {
             before(grammarAccess.getDependencyRuleAccess().getHyphenMinusKeyword_1_1()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getDependencyRuleAccess().getHyphenMinusKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__1__Impl"


    // $ANTLR start "rule__DependencyRule__Group_1__2"
    // InternalSpecDSL.g:2089:1: rule__DependencyRule__Group_1__2 : rule__DependencyRule__Group_1__2__Impl rule__DependencyRule__Group_1__3 ;
    public final void rule__DependencyRule__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2093:1: ( rule__DependencyRule__Group_1__2__Impl rule__DependencyRule__Group_1__3 )
            // InternalSpecDSL.g:2094:2: rule__DependencyRule__Group_1__2__Impl rule__DependencyRule__Group_1__3
            {
            pushFollow(FOLLOW_25);
            rule__DependencyRule__Group_1__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_1__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__2"


    // $ANTLR start "rule__DependencyRule__Group_1__2__Impl"
    // InternalSpecDSL.g:2101:1: rule__DependencyRule__Group_1__2__Impl : ( ( rule__DependencyRule__DTagAssignment_1_2 ) ) ;
    public final void rule__DependencyRule__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2105:1: ( ( ( rule__DependencyRule__DTagAssignment_1_2 ) ) )
            // InternalSpecDSL.g:2106:1: ( ( rule__DependencyRule__DTagAssignment_1_2 ) )
            {
            // InternalSpecDSL.g:2106:1: ( ( rule__DependencyRule__DTagAssignment_1_2 ) )
            // InternalSpecDSL.g:2107:2: ( rule__DependencyRule__DTagAssignment_1_2 )
            {
             before(grammarAccess.getDependencyRuleAccess().getDTagAssignment_1_2()); 
            // InternalSpecDSL.g:2108:2: ( rule__DependencyRule__DTagAssignment_1_2 )
            // InternalSpecDSL.g:2108:3: rule__DependencyRule__DTagAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__DTagAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getDTagAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__2__Impl"


    // $ANTLR start "rule__DependencyRule__Group_1__3"
    // InternalSpecDSL.g:2116:1: rule__DependencyRule__Group_1__3 : rule__DependencyRule__Group_1__3__Impl rule__DependencyRule__Group_1__4 ;
    public final void rule__DependencyRule__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2120:1: ( rule__DependencyRule__Group_1__3__Impl rule__DependencyRule__Group_1__4 )
            // InternalSpecDSL.g:2121:2: rule__DependencyRule__Group_1__3__Impl rule__DependencyRule__Group_1__4
            {
            pushFollow(FOLLOW_21);
            rule__DependencyRule__Group_1__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_1__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__3"


    // $ANTLR start "rule__DependencyRule__Group_1__3__Impl"
    // InternalSpecDSL.g:2128:1: rule__DependencyRule__Group_1__3__Impl : ( '->' ) ;
    public final void rule__DependencyRule__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2132:1: ( ( '->' ) )
            // InternalSpecDSL.g:2133:1: ( '->' )
            {
            // InternalSpecDSL.g:2133:1: ( '->' )
            // InternalSpecDSL.g:2134:2: '->'
            {
             before(grammarAccess.getDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_1_3()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_1_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__3__Impl"


    // $ANTLR start "rule__DependencyRule__Group_1__4"
    // InternalSpecDSL.g:2143:1: rule__DependencyRule__Group_1__4 : rule__DependencyRule__Group_1__4__Impl ;
    public final void rule__DependencyRule__Group_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2147:1: ( rule__DependencyRule__Group_1__4__Impl )
            // InternalSpecDSL.g:2148:2: rule__DependencyRule__Group_1__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__Group_1__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__4"


    // $ANTLR start "rule__DependencyRule__Group_1__4__Impl"
    // InternalSpecDSL.g:2154:1: rule__DependencyRule__Group_1__4__Impl : ( ( rule__DependencyRule__RightNodeAssignment_1_4 ) ) ;
    public final void rule__DependencyRule__Group_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2158:1: ( ( ( rule__DependencyRule__RightNodeAssignment_1_4 ) ) )
            // InternalSpecDSL.g:2159:1: ( ( rule__DependencyRule__RightNodeAssignment_1_4 ) )
            {
            // InternalSpecDSL.g:2159:1: ( ( rule__DependencyRule__RightNodeAssignment_1_4 ) )
            // InternalSpecDSL.g:2160:2: ( rule__DependencyRule__RightNodeAssignment_1_4 )
            {
             before(grammarAccess.getDependencyRuleAccess().getRightNodeAssignment_1_4()); 
            // InternalSpecDSL.g:2161:2: ( rule__DependencyRule__RightNodeAssignment_1_4 )
            // InternalSpecDSL.g:2161:3: rule__DependencyRule__RightNodeAssignment_1_4
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__RightNodeAssignment_1_4();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getRightNodeAssignment_1_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__Group_1__4__Impl"


    // $ANTLR start "rule__FreeDependencyRule__Group__0"
    // InternalSpecDSL.g:2170:1: rule__FreeDependencyRule__Group__0 : rule__FreeDependencyRule__Group__0__Impl rule__FreeDependencyRule__Group__1 ;
    public final void rule__FreeDependencyRule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2174:1: ( rule__FreeDependencyRule__Group__0__Impl rule__FreeDependencyRule__Group__1 )
            // InternalSpecDSL.g:2175:2: rule__FreeDependencyRule__Group__0__Impl rule__FreeDependencyRule__Group__1
            {
            pushFollow(FOLLOW_24);
            rule__FreeDependencyRule__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__0"


    // $ANTLR start "rule__FreeDependencyRule__Group__0__Impl"
    // InternalSpecDSL.g:2182:1: rule__FreeDependencyRule__Group__0__Impl : ( ( rule__FreeDependencyRule__LeftNodeAssignment_0 ) ) ;
    public final void rule__FreeDependencyRule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2186:1: ( ( ( rule__FreeDependencyRule__LeftNodeAssignment_0 ) ) )
            // InternalSpecDSL.g:2187:1: ( ( rule__FreeDependencyRule__LeftNodeAssignment_0 ) )
            {
            // InternalSpecDSL.g:2187:1: ( ( rule__FreeDependencyRule__LeftNodeAssignment_0 ) )
            // InternalSpecDSL.g:2188:2: ( rule__FreeDependencyRule__LeftNodeAssignment_0 )
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getLeftNodeAssignment_0()); 
            // InternalSpecDSL.g:2189:2: ( rule__FreeDependencyRule__LeftNodeAssignment_0 )
            // InternalSpecDSL.g:2189:3: rule__FreeDependencyRule__LeftNodeAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__LeftNodeAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getFreeDependencyRuleAccess().getLeftNodeAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__0__Impl"


    // $ANTLR start "rule__FreeDependencyRule__Group__1"
    // InternalSpecDSL.g:2197:1: rule__FreeDependencyRule__Group__1 : rule__FreeDependencyRule__Group__1__Impl rule__FreeDependencyRule__Group__2 ;
    public final void rule__FreeDependencyRule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2201:1: ( rule__FreeDependencyRule__Group__1__Impl rule__FreeDependencyRule__Group__2 )
            // InternalSpecDSL.g:2202:2: rule__FreeDependencyRule__Group__1__Impl rule__FreeDependencyRule__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__FreeDependencyRule__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__1"


    // $ANTLR start "rule__FreeDependencyRule__Group__1__Impl"
    // InternalSpecDSL.g:2209:1: rule__FreeDependencyRule__Group__1__Impl : ( '-' ) ;
    public final void rule__FreeDependencyRule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2213:1: ( ( '-' ) )
            // InternalSpecDSL.g:2214:1: ( '-' )
            {
            // InternalSpecDSL.g:2214:1: ( '-' )
            // InternalSpecDSL.g:2215:2: '-'
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getHyphenMinusKeyword_1()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getFreeDependencyRuleAccess().getHyphenMinusKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__1__Impl"


    // $ANTLR start "rule__FreeDependencyRule__Group__2"
    // InternalSpecDSL.g:2224:1: rule__FreeDependencyRule__Group__2 : rule__FreeDependencyRule__Group__2__Impl rule__FreeDependencyRule__Group__3 ;
    public final void rule__FreeDependencyRule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2228:1: ( rule__FreeDependencyRule__Group__2__Impl rule__FreeDependencyRule__Group__3 )
            // InternalSpecDSL.g:2229:2: rule__FreeDependencyRule__Group__2__Impl rule__FreeDependencyRule__Group__3
            {
            pushFollow(FOLLOW_25);
            rule__FreeDependencyRule__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__2"


    // $ANTLR start "rule__FreeDependencyRule__Group__2__Impl"
    // InternalSpecDSL.g:2236:1: rule__FreeDependencyRule__Group__2__Impl : ( ( rule__FreeDependencyRule__DTagAssignment_2 ) ) ;
    public final void rule__FreeDependencyRule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2240:1: ( ( ( rule__FreeDependencyRule__DTagAssignment_2 ) ) )
            // InternalSpecDSL.g:2241:1: ( ( rule__FreeDependencyRule__DTagAssignment_2 ) )
            {
            // InternalSpecDSL.g:2241:1: ( ( rule__FreeDependencyRule__DTagAssignment_2 ) )
            // InternalSpecDSL.g:2242:2: ( rule__FreeDependencyRule__DTagAssignment_2 )
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getDTagAssignment_2()); 
            // InternalSpecDSL.g:2243:2: ( rule__FreeDependencyRule__DTagAssignment_2 )
            // InternalSpecDSL.g:2243:3: rule__FreeDependencyRule__DTagAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__DTagAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getFreeDependencyRuleAccess().getDTagAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__2__Impl"


    // $ANTLR start "rule__FreeDependencyRule__Group__3"
    // InternalSpecDSL.g:2251:1: rule__FreeDependencyRule__Group__3 : rule__FreeDependencyRule__Group__3__Impl rule__FreeDependencyRule__Group__4 ;
    public final void rule__FreeDependencyRule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2255:1: ( rule__FreeDependencyRule__Group__3__Impl rule__FreeDependencyRule__Group__4 )
            // InternalSpecDSL.g:2256:2: rule__FreeDependencyRule__Group__3__Impl rule__FreeDependencyRule__Group__4
            {
            pushFollow(FOLLOW_21);
            rule__FreeDependencyRule__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__3"


    // $ANTLR start "rule__FreeDependencyRule__Group__3__Impl"
    // InternalSpecDSL.g:2263:1: rule__FreeDependencyRule__Group__3__Impl : ( '->' ) ;
    public final void rule__FreeDependencyRule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2267:1: ( ( '->' ) )
            // InternalSpecDSL.g:2268:1: ( '->' )
            {
            // InternalSpecDSL.g:2268:1: ( '->' )
            // InternalSpecDSL.g:2269:2: '->'
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_3()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getFreeDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__3__Impl"


    // $ANTLR start "rule__FreeDependencyRule__Group__4"
    // InternalSpecDSL.g:2278:1: rule__FreeDependencyRule__Group__4 : rule__FreeDependencyRule__Group__4__Impl ;
    public final void rule__FreeDependencyRule__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2282:1: ( rule__FreeDependencyRule__Group__4__Impl )
            // InternalSpecDSL.g:2283:2: rule__FreeDependencyRule__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__4"


    // $ANTLR start "rule__FreeDependencyRule__Group__4__Impl"
    // InternalSpecDSL.g:2289:1: rule__FreeDependencyRule__Group__4__Impl : ( ( rule__FreeDependencyRule__RightNodeAssignment_4 ) ) ;
    public final void rule__FreeDependencyRule__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2293:1: ( ( ( rule__FreeDependencyRule__RightNodeAssignment_4 ) ) )
            // InternalSpecDSL.g:2294:1: ( ( rule__FreeDependencyRule__RightNodeAssignment_4 ) )
            {
            // InternalSpecDSL.g:2294:1: ( ( rule__FreeDependencyRule__RightNodeAssignment_4 ) )
            // InternalSpecDSL.g:2295:2: ( rule__FreeDependencyRule__RightNodeAssignment_4 )
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getRightNodeAssignment_4()); 
            // InternalSpecDSL.g:2296:2: ( rule__FreeDependencyRule__RightNodeAssignment_4 )
            // InternalSpecDSL.g:2296:3: rule__FreeDependencyRule__RightNodeAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__RightNodeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getFreeDependencyRuleAccess().getRightNodeAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__Group__4__Impl"


    // $ANTLR start "rule__OptionNode__Group__0"
    // InternalSpecDSL.g:2305:1: rule__OptionNode__Group__0 : rule__OptionNode__Group__0__Impl rule__OptionNode__Group__1 ;
    public final void rule__OptionNode__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2309:1: ( rule__OptionNode__Group__0__Impl rule__OptionNode__Group__1 )
            // InternalSpecDSL.g:2310:2: rule__OptionNode__Group__0__Impl rule__OptionNode__Group__1
            {
            pushFollow(FOLLOW_26);
            rule__OptionNode__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OptionNode__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__0"


    // $ANTLR start "rule__OptionNode__Group__0__Impl"
    // InternalSpecDSL.g:2317:1: rule__OptionNode__Group__0__Impl : ( '(' ) ;
    public final void rule__OptionNode__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2321:1: ( ( '(' ) )
            // InternalSpecDSL.g:2322:1: ( '(' )
            {
            // InternalSpecDSL.g:2322:1: ( '(' )
            // InternalSpecDSL.g:2323:2: '('
            {
             before(grammarAccess.getOptionNodeAccess().getLeftParenthesisKeyword_0()); 
            match(input,24,FOLLOW_2); 
             after(grammarAccess.getOptionNodeAccess().getLeftParenthesisKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__0__Impl"


    // $ANTLR start "rule__OptionNode__Group__1"
    // InternalSpecDSL.g:2332:1: rule__OptionNode__Group__1 : rule__OptionNode__Group__1__Impl rule__OptionNode__Group__2 ;
    public final void rule__OptionNode__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2336:1: ( rule__OptionNode__Group__1__Impl rule__OptionNode__Group__2 )
            // InternalSpecDSL.g:2337:2: rule__OptionNode__Group__1__Impl rule__OptionNode__Group__2
            {
            pushFollow(FOLLOW_27);
            rule__OptionNode__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OptionNode__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__1"


    // $ANTLR start "rule__OptionNode__Group__1__Impl"
    // InternalSpecDSL.g:2344:1: rule__OptionNode__Group__1__Impl : ( ( rule__OptionNode__LeftNodeAssignment_1 ) ) ;
    public final void rule__OptionNode__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2348:1: ( ( ( rule__OptionNode__LeftNodeAssignment_1 ) ) )
            // InternalSpecDSL.g:2349:1: ( ( rule__OptionNode__LeftNodeAssignment_1 ) )
            {
            // InternalSpecDSL.g:2349:1: ( ( rule__OptionNode__LeftNodeAssignment_1 ) )
            // InternalSpecDSL.g:2350:2: ( rule__OptionNode__LeftNodeAssignment_1 )
            {
             before(grammarAccess.getOptionNodeAccess().getLeftNodeAssignment_1()); 
            // InternalSpecDSL.g:2351:2: ( rule__OptionNode__LeftNodeAssignment_1 )
            // InternalSpecDSL.g:2351:3: rule__OptionNode__LeftNodeAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__OptionNode__LeftNodeAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getOptionNodeAccess().getLeftNodeAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__1__Impl"


    // $ANTLR start "rule__OptionNode__Group__2"
    // InternalSpecDSL.g:2359:1: rule__OptionNode__Group__2 : rule__OptionNode__Group__2__Impl rule__OptionNode__Group__3 ;
    public final void rule__OptionNode__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2363:1: ( rule__OptionNode__Group__2__Impl rule__OptionNode__Group__3 )
            // InternalSpecDSL.g:2364:2: rule__OptionNode__Group__2__Impl rule__OptionNode__Group__3
            {
            pushFollow(FOLLOW_28);
            rule__OptionNode__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OptionNode__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__2"


    // $ANTLR start "rule__OptionNode__Group__2__Impl"
    // InternalSpecDSL.g:2371:1: rule__OptionNode__Group__2__Impl : ( ( ( rule__OptionNode__Group_2__0 ) ) ( ( rule__OptionNode__Group_2__0 )* ) ) ;
    public final void rule__OptionNode__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2375:1: ( ( ( ( rule__OptionNode__Group_2__0 ) ) ( ( rule__OptionNode__Group_2__0 )* ) ) )
            // InternalSpecDSL.g:2376:1: ( ( ( rule__OptionNode__Group_2__0 ) ) ( ( rule__OptionNode__Group_2__0 )* ) )
            {
            // InternalSpecDSL.g:2376:1: ( ( ( rule__OptionNode__Group_2__0 ) ) ( ( rule__OptionNode__Group_2__0 )* ) )
            // InternalSpecDSL.g:2377:2: ( ( rule__OptionNode__Group_2__0 ) ) ( ( rule__OptionNode__Group_2__0 )* )
            {
            // InternalSpecDSL.g:2377:2: ( ( rule__OptionNode__Group_2__0 ) )
            // InternalSpecDSL.g:2378:3: ( rule__OptionNode__Group_2__0 )
            {
             before(grammarAccess.getOptionNodeAccess().getGroup_2()); 
            // InternalSpecDSL.g:2379:3: ( rule__OptionNode__Group_2__0 )
            // InternalSpecDSL.g:2379:4: rule__OptionNode__Group_2__0
            {
            pushFollow(FOLLOW_29);
            rule__OptionNode__Group_2__0();

            state._fsp--;


            }

             after(grammarAccess.getOptionNodeAccess().getGroup_2()); 

            }

            // InternalSpecDSL.g:2382:2: ( ( rule__OptionNode__Group_2__0 )* )
            // InternalSpecDSL.g:2383:3: ( rule__OptionNode__Group_2__0 )*
            {
             before(grammarAccess.getOptionNodeAccess().getGroup_2()); 
            // InternalSpecDSL.g:2384:3: ( rule__OptionNode__Group_2__0 )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==26) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSpecDSL.g:2384:4: rule__OptionNode__Group_2__0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__OptionNode__Group_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

             after(grammarAccess.getOptionNodeAccess().getGroup_2()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__2__Impl"


    // $ANTLR start "rule__OptionNode__Group__3"
    // InternalSpecDSL.g:2393:1: rule__OptionNode__Group__3 : rule__OptionNode__Group__3__Impl ;
    public final void rule__OptionNode__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2397:1: ( rule__OptionNode__Group__3__Impl )
            // InternalSpecDSL.g:2398:2: rule__OptionNode__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OptionNode__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__3"


    // $ANTLR start "rule__OptionNode__Group__3__Impl"
    // InternalSpecDSL.g:2404:1: rule__OptionNode__Group__3__Impl : ( ')' ) ;
    public final void rule__OptionNode__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2408:1: ( ( ')' ) )
            // InternalSpecDSL.g:2409:1: ( ')' )
            {
            // InternalSpecDSL.g:2409:1: ( ')' )
            // InternalSpecDSL.g:2410:2: ')'
            {
             before(grammarAccess.getOptionNodeAccess().getRightParenthesisKeyword_3()); 
            match(input,25,FOLLOW_2); 
             after(grammarAccess.getOptionNodeAccess().getRightParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group__3__Impl"


    // $ANTLR start "rule__OptionNode__Group_2__0"
    // InternalSpecDSL.g:2420:1: rule__OptionNode__Group_2__0 : rule__OptionNode__Group_2__0__Impl rule__OptionNode__Group_2__1 ;
    public final void rule__OptionNode__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2424:1: ( rule__OptionNode__Group_2__0__Impl rule__OptionNode__Group_2__1 )
            // InternalSpecDSL.g:2425:2: rule__OptionNode__Group_2__0__Impl rule__OptionNode__Group_2__1
            {
            pushFollow(FOLLOW_26);
            rule__OptionNode__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OptionNode__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group_2__0"


    // $ANTLR start "rule__OptionNode__Group_2__0__Impl"
    // InternalSpecDSL.g:2432:1: rule__OptionNode__Group_2__0__Impl : ( '|' ) ;
    public final void rule__OptionNode__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2436:1: ( ( '|' ) )
            // InternalSpecDSL.g:2437:1: ( '|' )
            {
            // InternalSpecDSL.g:2437:1: ( '|' )
            // InternalSpecDSL.g:2438:2: '|'
            {
             before(grammarAccess.getOptionNodeAccess().getVerticalLineKeyword_2_0()); 
            match(input,26,FOLLOW_2); 
             after(grammarAccess.getOptionNodeAccess().getVerticalLineKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group_2__0__Impl"


    // $ANTLR start "rule__OptionNode__Group_2__1"
    // InternalSpecDSL.g:2447:1: rule__OptionNode__Group_2__1 : rule__OptionNode__Group_2__1__Impl ;
    public final void rule__OptionNode__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2451:1: ( rule__OptionNode__Group_2__1__Impl )
            // InternalSpecDSL.g:2452:2: rule__OptionNode__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OptionNode__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group_2__1"


    // $ANTLR start "rule__OptionNode__Group_2__1__Impl"
    // InternalSpecDSL.g:2458:1: rule__OptionNode__Group_2__1__Impl : ( ( rule__OptionNode__RightNodesAssignment_2_1 ) ) ;
    public final void rule__OptionNode__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2462:1: ( ( ( rule__OptionNode__RightNodesAssignment_2_1 ) ) )
            // InternalSpecDSL.g:2463:1: ( ( rule__OptionNode__RightNodesAssignment_2_1 ) )
            {
            // InternalSpecDSL.g:2463:1: ( ( rule__OptionNode__RightNodesAssignment_2_1 ) )
            // InternalSpecDSL.g:2464:2: ( rule__OptionNode__RightNodesAssignment_2_1 )
            {
             before(grammarAccess.getOptionNodeAccess().getRightNodesAssignment_2_1()); 
            // InternalSpecDSL.g:2465:2: ( rule__OptionNode__RightNodesAssignment_2_1 )
            // InternalSpecDSL.g:2465:3: rule__OptionNode__RightNodesAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__OptionNode__RightNodesAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getOptionNodeAccess().getRightNodesAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__Group_2__1__Impl"


    // $ANTLR start "rule__TreeNode__Group__0"
    // InternalSpecDSL.g:2474:1: rule__TreeNode__Group__0 : rule__TreeNode__Group__0__Impl rule__TreeNode__Group__1 ;
    public final void rule__TreeNode__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2478:1: ( rule__TreeNode__Group__0__Impl rule__TreeNode__Group__1 )
            // InternalSpecDSL.g:2479:2: rule__TreeNode__Group__0__Impl rule__TreeNode__Group__1
            {
            pushFollow(FOLLOW_30);
            rule__TreeNode__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeNode__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group__0"


    // $ANTLR start "rule__TreeNode__Group__0__Impl"
    // InternalSpecDSL.g:2486:1: rule__TreeNode__Group__0__Impl : ( ( rule__TreeNode__Group_0__0 )? ) ;
    public final void rule__TreeNode__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2490:1: ( ( ( rule__TreeNode__Group_0__0 )? ) )
            // InternalSpecDSL.g:2491:1: ( ( rule__TreeNode__Group_0__0 )? )
            {
            // InternalSpecDSL.g:2491:1: ( ( rule__TreeNode__Group_0__0 )? )
            // InternalSpecDSL.g:2492:2: ( rule__TreeNode__Group_0__0 )?
            {
             before(grammarAccess.getTreeNodeAccess().getGroup_0()); 
            // InternalSpecDSL.g:2493:2: ( rule__TreeNode__Group_0__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_ID) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSpecDSL.g:2493:3: rule__TreeNode__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TreeNode__Group_0__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTreeNodeAccess().getGroup_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group__0__Impl"


    // $ANTLR start "rule__TreeNode__Group__1"
    // InternalSpecDSL.g:2501:1: rule__TreeNode__Group__1 : rule__TreeNode__Group__1__Impl rule__TreeNode__Group__2 ;
    public final void rule__TreeNode__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2505:1: ( rule__TreeNode__Group__1__Impl rule__TreeNode__Group__2 )
            // InternalSpecDSL.g:2506:2: rule__TreeNode__Group__1__Impl rule__TreeNode__Group__2
            {
            pushFollow(FOLLOW_30);
            rule__TreeNode__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeNode__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group__1"


    // $ANTLR start "rule__TreeNode__Group__1__Impl"
    // InternalSpecDSL.g:2513:1: rule__TreeNode__Group__1__Impl : ( ( rule__TreeNode__Group_1__0 )? ) ;
    public final void rule__TreeNode__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2517:1: ( ( ( rule__TreeNode__Group_1__0 )? ) )
            // InternalSpecDSL.g:2518:1: ( ( rule__TreeNode__Group_1__0 )? )
            {
            // InternalSpecDSL.g:2518:1: ( ( rule__TreeNode__Group_1__0 )? )
            // InternalSpecDSL.g:2519:2: ( rule__TreeNode__Group_1__0 )?
            {
             before(grammarAccess.getTreeNodeAccess().getGroup_1()); 
            // InternalSpecDSL.g:2520:2: ( rule__TreeNode__Group_1__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_STRING||LA21_0==31) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSpecDSL.g:2520:3: rule__TreeNode__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__TreeNode__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getTreeNodeAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group__1__Impl"


    // $ANTLR start "rule__TreeNode__Group__2"
    // InternalSpecDSL.g:2528:1: rule__TreeNode__Group__2 : rule__TreeNode__Group__2__Impl ;
    public final void rule__TreeNode__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2532:1: ( rule__TreeNode__Group__2__Impl )
            // InternalSpecDSL.g:2533:2: rule__TreeNode__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TreeNode__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group__2"


    // $ANTLR start "rule__TreeNode__Group__2__Impl"
    // InternalSpecDSL.g:2539:1: rule__TreeNode__Group__2__Impl : ( ( rule__TreeNode__TreeAssignment_2 ) ) ;
    public final void rule__TreeNode__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2543:1: ( ( ( rule__TreeNode__TreeAssignment_2 ) ) )
            // InternalSpecDSL.g:2544:1: ( ( rule__TreeNode__TreeAssignment_2 ) )
            {
            // InternalSpecDSL.g:2544:1: ( ( rule__TreeNode__TreeAssignment_2 ) )
            // InternalSpecDSL.g:2545:2: ( rule__TreeNode__TreeAssignment_2 )
            {
             before(grammarAccess.getTreeNodeAccess().getTreeAssignment_2()); 
            // InternalSpecDSL.g:2546:2: ( rule__TreeNode__TreeAssignment_2 )
            // InternalSpecDSL.g:2546:3: rule__TreeNode__TreeAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__TreeNode__TreeAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getTreeNodeAccess().getTreeAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group__2__Impl"


    // $ANTLR start "rule__TreeNode__Group_0__0"
    // InternalSpecDSL.g:2555:1: rule__TreeNode__Group_0__0 : rule__TreeNode__Group_0__0__Impl rule__TreeNode__Group_0__1 ;
    public final void rule__TreeNode__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2559:1: ( rule__TreeNode__Group_0__0__Impl rule__TreeNode__Group_0__1 )
            // InternalSpecDSL.g:2560:2: rule__TreeNode__Group_0__0__Impl rule__TreeNode__Group_0__1
            {
            pushFollow(FOLLOW_31);
            rule__TreeNode__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeNode__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_0__0"


    // $ANTLR start "rule__TreeNode__Group_0__0__Impl"
    // InternalSpecDSL.g:2567:1: rule__TreeNode__Group_0__0__Impl : ( ( rule__TreeNode__PTagAssignment_0_0 ) ) ;
    public final void rule__TreeNode__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2571:1: ( ( ( rule__TreeNode__PTagAssignment_0_0 ) ) )
            // InternalSpecDSL.g:2572:1: ( ( rule__TreeNode__PTagAssignment_0_0 ) )
            {
            // InternalSpecDSL.g:2572:1: ( ( rule__TreeNode__PTagAssignment_0_0 ) )
            // InternalSpecDSL.g:2573:2: ( rule__TreeNode__PTagAssignment_0_0 )
            {
             before(grammarAccess.getTreeNodeAccess().getPTagAssignment_0_0()); 
            // InternalSpecDSL.g:2574:2: ( rule__TreeNode__PTagAssignment_0_0 )
            // InternalSpecDSL.g:2574:3: rule__TreeNode__PTagAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__TreeNode__PTagAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getTreeNodeAccess().getPTagAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_0__0__Impl"


    // $ANTLR start "rule__TreeNode__Group_0__1"
    // InternalSpecDSL.g:2582:1: rule__TreeNode__Group_0__1 : rule__TreeNode__Group_0__1__Impl ;
    public final void rule__TreeNode__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2586:1: ( rule__TreeNode__Group_0__1__Impl )
            // InternalSpecDSL.g:2587:2: rule__TreeNode__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TreeNode__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_0__1"


    // $ANTLR start "rule__TreeNode__Group_0__1__Impl"
    // InternalSpecDSL.g:2593:1: rule__TreeNode__Group_0__1__Impl : ( ':' ) ;
    public final void rule__TreeNode__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2597:1: ( ( ':' ) )
            // InternalSpecDSL.g:2598:1: ( ':' )
            {
            // InternalSpecDSL.g:2598:1: ( ':' )
            // InternalSpecDSL.g:2599:2: ':'
            {
             before(grammarAccess.getTreeNodeAccess().getColonKeyword_0_1()); 
            match(input,27,FOLLOW_2); 
             after(grammarAccess.getTreeNodeAccess().getColonKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_0__1__Impl"


    // $ANTLR start "rule__TreeNode__Group_1__0"
    // InternalSpecDSL.g:2609:1: rule__TreeNode__Group_1__0 : rule__TreeNode__Group_1__0__Impl rule__TreeNode__Group_1__1 ;
    public final void rule__TreeNode__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2613:1: ( rule__TreeNode__Group_1__0__Impl rule__TreeNode__Group_1__1 )
            // InternalSpecDSL.g:2614:2: rule__TreeNode__Group_1__0__Impl rule__TreeNode__Group_1__1
            {
            pushFollow(FOLLOW_31);
            rule__TreeNode__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__TreeNode__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_1__0"


    // $ANTLR start "rule__TreeNode__Group_1__0__Impl"
    // InternalSpecDSL.g:2621:1: rule__TreeNode__Group_1__0__Impl : ( ( rule__TreeNode__Alternatives_1_0 ) ) ;
    public final void rule__TreeNode__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2625:1: ( ( ( rule__TreeNode__Alternatives_1_0 ) ) )
            // InternalSpecDSL.g:2626:1: ( ( rule__TreeNode__Alternatives_1_0 ) )
            {
            // InternalSpecDSL.g:2626:1: ( ( rule__TreeNode__Alternatives_1_0 ) )
            // InternalSpecDSL.g:2627:2: ( rule__TreeNode__Alternatives_1_0 )
            {
             before(grammarAccess.getTreeNodeAccess().getAlternatives_1_0()); 
            // InternalSpecDSL.g:2628:2: ( rule__TreeNode__Alternatives_1_0 )
            // InternalSpecDSL.g:2628:3: rule__TreeNode__Alternatives_1_0
            {
            pushFollow(FOLLOW_2);
            rule__TreeNode__Alternatives_1_0();

            state._fsp--;


            }

             after(grammarAccess.getTreeNodeAccess().getAlternatives_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_1__0__Impl"


    // $ANTLR start "rule__TreeNode__Group_1__1"
    // InternalSpecDSL.g:2636:1: rule__TreeNode__Group_1__1 : rule__TreeNode__Group_1__1__Impl ;
    public final void rule__TreeNode__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2640:1: ( rule__TreeNode__Group_1__1__Impl )
            // InternalSpecDSL.g:2641:2: rule__TreeNode__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__TreeNode__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_1__1"


    // $ANTLR start "rule__TreeNode__Group_1__1__Impl"
    // InternalSpecDSL.g:2647:1: rule__TreeNode__Group_1__1__Impl : ( ':' ) ;
    public final void rule__TreeNode__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2651:1: ( ( ':' ) )
            // InternalSpecDSL.g:2652:1: ( ':' )
            {
            // InternalSpecDSL.g:2652:1: ( ':' )
            // InternalSpecDSL.g:2653:2: ':'
            {
             before(grammarAccess.getTreeNodeAccess().getColonKeyword_1_1()); 
            match(input,27,FOLLOW_2); 
             after(grammarAccess.getTreeNodeAccess().getColonKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__Group_1__1__Impl"


    // $ANTLR start "rule__ExplicitNode__Group__0"
    // InternalSpecDSL.g:2663:1: rule__ExplicitNode__Group__0 : rule__ExplicitNode__Group__0__Impl rule__ExplicitNode__Group__1 ;
    public final void rule__ExplicitNode__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2667:1: ( rule__ExplicitNode__Group__0__Impl rule__ExplicitNode__Group__1 )
            // InternalSpecDSL.g:2668:2: rule__ExplicitNode__Group__0__Impl rule__ExplicitNode__Group__1
            {
            pushFollow(FOLLOW_26);
            rule__ExplicitNode__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group__0"


    // $ANTLR start "rule__ExplicitNode__Group__0__Impl"
    // InternalSpecDSL.g:2675:1: rule__ExplicitNode__Group__0__Impl : ( ( rule__ExplicitNode__Group_0__0 )? ) ;
    public final void rule__ExplicitNode__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2679:1: ( ( ( rule__ExplicitNode__Group_0__0 )? ) )
            // InternalSpecDSL.g:2680:1: ( ( rule__ExplicitNode__Group_0__0 )? )
            {
            // InternalSpecDSL.g:2680:1: ( ( rule__ExplicitNode__Group_0__0 )? )
            // InternalSpecDSL.g:2681:2: ( rule__ExplicitNode__Group_0__0 )?
            {
             before(grammarAccess.getExplicitNodeAccess().getGroup_0()); 
            // InternalSpecDSL.g:2682:2: ( rule__ExplicitNode__Group_0__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==RULE_ID) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSpecDSL.g:2682:3: rule__ExplicitNode__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExplicitNode__Group_0__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getExplicitNodeAccess().getGroup_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group__0__Impl"


    // $ANTLR start "rule__ExplicitNode__Group__1"
    // InternalSpecDSL.g:2690:1: rule__ExplicitNode__Group__1 : rule__ExplicitNode__Group__1__Impl ;
    public final void rule__ExplicitNode__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2694:1: ( rule__ExplicitNode__Group__1__Impl )
            // InternalSpecDSL.g:2695:2: rule__ExplicitNode__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group__1"


    // $ANTLR start "rule__ExplicitNode__Group__1__Impl"
    // InternalSpecDSL.g:2701:1: rule__ExplicitNode__Group__1__Impl : ( ( rule__ExplicitNode__Alternatives_1 ) ) ;
    public final void rule__ExplicitNode__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2705:1: ( ( ( rule__ExplicitNode__Alternatives_1 ) ) )
            // InternalSpecDSL.g:2706:1: ( ( rule__ExplicitNode__Alternatives_1 ) )
            {
            // InternalSpecDSL.g:2706:1: ( ( rule__ExplicitNode__Alternatives_1 ) )
            // InternalSpecDSL.g:2707:2: ( rule__ExplicitNode__Alternatives_1 )
            {
             before(grammarAccess.getExplicitNodeAccess().getAlternatives_1()); 
            // InternalSpecDSL.g:2708:2: ( rule__ExplicitNode__Alternatives_1 )
            // InternalSpecDSL.g:2708:3: rule__ExplicitNode__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Alternatives_1();

            state._fsp--;


            }

             after(grammarAccess.getExplicitNodeAccess().getAlternatives_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group__1__Impl"


    // $ANTLR start "rule__ExplicitNode__Group_0__0"
    // InternalSpecDSL.g:2717:1: rule__ExplicitNode__Group_0__0 : rule__ExplicitNode__Group_0__0__Impl rule__ExplicitNode__Group_0__1 ;
    public final void rule__ExplicitNode__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2721:1: ( rule__ExplicitNode__Group_0__0__Impl rule__ExplicitNode__Group_0__1 )
            // InternalSpecDSL.g:2722:2: rule__ExplicitNode__Group_0__0__Impl rule__ExplicitNode__Group_0__1
            {
            pushFollow(FOLLOW_31);
            rule__ExplicitNode__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_0__0"


    // $ANTLR start "rule__ExplicitNode__Group_0__0__Impl"
    // InternalSpecDSL.g:2729:1: rule__ExplicitNode__Group_0__0__Impl : ( ( rule__ExplicitNode__PTagAssignment_0_0 ) ) ;
    public final void rule__ExplicitNode__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2733:1: ( ( ( rule__ExplicitNode__PTagAssignment_0_0 ) ) )
            // InternalSpecDSL.g:2734:1: ( ( rule__ExplicitNode__PTagAssignment_0_0 ) )
            {
            // InternalSpecDSL.g:2734:1: ( ( rule__ExplicitNode__PTagAssignment_0_0 ) )
            // InternalSpecDSL.g:2735:2: ( rule__ExplicitNode__PTagAssignment_0_0 )
            {
             before(grammarAccess.getExplicitNodeAccess().getPTagAssignment_0_0()); 
            // InternalSpecDSL.g:2736:2: ( rule__ExplicitNode__PTagAssignment_0_0 )
            // InternalSpecDSL.g:2736:3: rule__ExplicitNode__PTagAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__ExplicitNode__PTagAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getExplicitNodeAccess().getPTagAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_0__0__Impl"


    // $ANTLR start "rule__ExplicitNode__Group_0__1"
    // InternalSpecDSL.g:2744:1: rule__ExplicitNode__Group_0__1 : rule__ExplicitNode__Group_0__1__Impl ;
    public final void rule__ExplicitNode__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2748:1: ( rule__ExplicitNode__Group_0__1__Impl )
            // InternalSpecDSL.g:2749:2: rule__ExplicitNode__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_0__1"


    // $ANTLR start "rule__ExplicitNode__Group_0__1__Impl"
    // InternalSpecDSL.g:2755:1: rule__ExplicitNode__Group_0__1__Impl : ( ':' ) ;
    public final void rule__ExplicitNode__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2759:1: ( ( ':' ) )
            // InternalSpecDSL.g:2760:1: ( ':' )
            {
            // InternalSpecDSL.g:2760:1: ( ':' )
            // InternalSpecDSL.g:2761:2: ':'
            {
             before(grammarAccess.getExplicitNodeAccess().getColonKeyword_0_1()); 
            match(input,27,FOLLOW_2); 
             after(grammarAccess.getExplicitNodeAccess().getColonKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_0__1__Impl"


    // $ANTLR start "rule__ExplicitNode__Group_1_0__0"
    // InternalSpecDSL.g:2771:1: rule__ExplicitNode__Group_1_0__0 : rule__ExplicitNode__Group_1_0__0__Impl rule__ExplicitNode__Group_1_0__1 ;
    public final void rule__ExplicitNode__Group_1_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2775:1: ( rule__ExplicitNode__Group_1_0__0__Impl rule__ExplicitNode__Group_1_0__1 )
            // InternalSpecDSL.g:2776:2: rule__ExplicitNode__Group_1_0__0__Impl rule__ExplicitNode__Group_1_0__1
            {
            pushFollow(FOLLOW_32);
            rule__ExplicitNode__Group_1_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Group_1_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_1_0__0"


    // $ANTLR start "rule__ExplicitNode__Group_1_0__0__Impl"
    // InternalSpecDSL.g:2783:1: rule__ExplicitNode__Group_1_0__0__Impl : ( ( rule__ExplicitNode__CaseSensitiveAssignment_1_0_0 )? ) ;
    public final void rule__ExplicitNode__Group_1_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2787:1: ( ( ( rule__ExplicitNode__CaseSensitiveAssignment_1_0_0 )? ) )
            // InternalSpecDSL.g:2788:1: ( ( rule__ExplicitNode__CaseSensitiveAssignment_1_0_0 )? )
            {
            // InternalSpecDSL.g:2788:1: ( ( rule__ExplicitNode__CaseSensitiveAssignment_1_0_0 )? )
            // InternalSpecDSL.g:2789:2: ( rule__ExplicitNode__CaseSensitiveAssignment_1_0_0 )?
            {
             before(grammarAccess.getExplicitNodeAccess().getCaseSensitiveAssignment_1_0_0()); 
            // InternalSpecDSL.g:2790:2: ( rule__ExplicitNode__CaseSensitiveAssignment_1_0_0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==32) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSpecDSL.g:2790:3: rule__ExplicitNode__CaseSensitiveAssignment_1_0_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ExplicitNode__CaseSensitiveAssignment_1_0_0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getExplicitNodeAccess().getCaseSensitiveAssignment_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_1_0__0__Impl"


    // $ANTLR start "rule__ExplicitNode__Group_1_0__1"
    // InternalSpecDSL.g:2798:1: rule__ExplicitNode__Group_1_0__1 : rule__ExplicitNode__Group_1_0__1__Impl ;
    public final void rule__ExplicitNode__Group_1_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2802:1: ( rule__ExplicitNode__Group_1_0__1__Impl )
            // InternalSpecDSL.g:2803:2: rule__ExplicitNode__Group_1_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ExplicitNode__Group_1_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_1_0__1"


    // $ANTLR start "rule__ExplicitNode__Group_1_0__1__Impl"
    // InternalSpecDSL.g:2809:1: rule__ExplicitNode__Group_1_0__1__Impl : ( ( rule__ExplicitNode__ExprAssignment_1_0_1 ) ) ;
    public final void rule__ExplicitNode__Group_1_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2813:1: ( ( ( rule__ExplicitNode__ExprAssignment_1_0_1 ) ) )
            // InternalSpecDSL.g:2814:1: ( ( rule__ExplicitNode__ExprAssignment_1_0_1 ) )
            {
            // InternalSpecDSL.g:2814:1: ( ( rule__ExplicitNode__ExprAssignment_1_0_1 ) )
            // InternalSpecDSL.g:2815:2: ( rule__ExplicitNode__ExprAssignment_1_0_1 )
            {
             before(grammarAccess.getExplicitNodeAccess().getExprAssignment_1_0_1()); 
            // InternalSpecDSL.g:2816:2: ( rule__ExplicitNode__ExprAssignment_1_0_1 )
            // InternalSpecDSL.g:2816:3: rule__ExplicitNode__ExprAssignment_1_0_1
            {
            pushFollow(FOLLOW_2);
            rule__ExplicitNode__ExprAssignment_1_0_1();

            state._fsp--;


            }

             after(grammarAccess.getExplicitNodeAccess().getExprAssignment_1_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__Group_1_0__1__Impl"


    // $ANTLR start "rule__Subtree__Group__0"
    // InternalSpecDSL.g:2825:1: rule__Subtree__Group__0 : rule__Subtree__Group__0__Impl rule__Subtree__Group__1 ;
    public final void rule__Subtree__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2829:1: ( rule__Subtree__Group__0__Impl rule__Subtree__Group__1 )
            // InternalSpecDSL.g:2830:2: rule__Subtree__Group__0__Impl rule__Subtree__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__Subtree__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subtree__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subtree__Group__0"


    // $ANTLR start "rule__Subtree__Group__0__Impl"
    // InternalSpecDSL.g:2837:1: rule__Subtree__Group__0__Impl : ( '[' ) ;
    public final void rule__Subtree__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2841:1: ( ( '[' ) )
            // InternalSpecDSL.g:2842:1: ( '[' )
            {
            // InternalSpecDSL.g:2842:1: ( '[' )
            // InternalSpecDSL.g:2843:2: '['
            {
             before(grammarAccess.getSubtreeAccess().getLeftSquareBracketKeyword_0()); 
            match(input,28,FOLLOW_2); 
             after(grammarAccess.getSubtreeAccess().getLeftSquareBracketKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subtree__Group__0__Impl"


    // $ANTLR start "rule__Subtree__Group__1"
    // InternalSpecDSL.g:2852:1: rule__Subtree__Group__1 : rule__Subtree__Group__1__Impl rule__Subtree__Group__2 ;
    public final void rule__Subtree__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2856:1: ( rule__Subtree__Group__1__Impl rule__Subtree__Group__2 )
            // InternalSpecDSL.g:2857:2: rule__Subtree__Group__1__Impl rule__Subtree__Group__2
            {
            pushFollow(FOLLOW_33);
            rule__Subtree__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subtree__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subtree__Group__1"


    // $ANTLR start "rule__Subtree__Group__1__Impl"
    // InternalSpecDSL.g:2864:1: rule__Subtree__Group__1__Impl : ( ( rule__Subtree__NameAssignment_1 ) ) ;
    public final void rule__Subtree__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2868:1: ( ( ( rule__Subtree__NameAssignment_1 ) ) )
            // InternalSpecDSL.g:2869:1: ( ( rule__Subtree__NameAssignment_1 ) )
            {
            // InternalSpecDSL.g:2869:1: ( ( rule__Subtree__NameAssignment_1 ) )
            // InternalSpecDSL.g:2870:2: ( rule__Subtree__NameAssignment_1 )
            {
             before(grammarAccess.getSubtreeAccess().getNameAssignment_1()); 
            // InternalSpecDSL.g:2871:2: ( rule__Subtree__NameAssignment_1 )
            // InternalSpecDSL.g:2871:3: rule__Subtree__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Subtree__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getSubtreeAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subtree__Group__1__Impl"


    // $ANTLR start "rule__Subtree__Group__2"
    // InternalSpecDSL.g:2879:1: rule__Subtree__Group__2 : rule__Subtree__Group__2__Impl ;
    public final void rule__Subtree__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2883:1: ( rule__Subtree__Group__2__Impl )
            // InternalSpecDSL.g:2884:2: rule__Subtree__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Subtree__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subtree__Group__2"


    // $ANTLR start "rule__Subtree__Group__2__Impl"
    // InternalSpecDSL.g:2890:1: rule__Subtree__Group__2__Impl : ( ']' ) ;
    public final void rule__Subtree__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2894:1: ( ( ']' ) )
            // InternalSpecDSL.g:2895:1: ( ']' )
            {
            // InternalSpecDSL.g:2895:1: ( ']' )
            // InternalSpecDSL.g:2896:2: ']'
            {
             before(grammarAccess.getSubtreeAccess().getRightSquareBracketKeyword_2()); 
            match(input,29,FOLLOW_2); 
             after(grammarAccess.getSubtreeAccess().getRightSquareBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subtree__Group__2__Impl"


    // $ANTLR start "rule__QualifiedName__Group__0"
    // InternalSpecDSL.g:2906:1: rule__QualifiedName__Group__0 : rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 ;
    public final void rule__QualifiedName__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2910:1: ( rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 )
            // InternalSpecDSL.g:2911:2: rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1
            {
            pushFollow(FOLLOW_34);
            rule__QualifiedName__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QualifiedName__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group__0"


    // $ANTLR start "rule__QualifiedName__Group__0__Impl"
    // InternalSpecDSL.g:2918:1: rule__QualifiedName__Group__0__Impl : ( RULE_ID ) ;
    public final void rule__QualifiedName__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2922:1: ( ( RULE_ID ) )
            // InternalSpecDSL.g:2923:1: ( RULE_ID )
            {
            // InternalSpecDSL.g:2923:1: ( RULE_ID )
            // InternalSpecDSL.g:2924:2: RULE_ID
            {
             before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group__0__Impl"


    // $ANTLR start "rule__QualifiedName__Group__1"
    // InternalSpecDSL.g:2933:1: rule__QualifiedName__Group__1 : rule__QualifiedName__Group__1__Impl ;
    public final void rule__QualifiedName__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2937:1: ( rule__QualifiedName__Group__1__Impl )
            // InternalSpecDSL.g:2938:2: rule__QualifiedName__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifiedName__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group__1"


    // $ANTLR start "rule__QualifiedName__Group__1__Impl"
    // InternalSpecDSL.g:2944:1: rule__QualifiedName__Group__1__Impl : ( ( rule__QualifiedName__Group_1__0 )* ) ;
    public final void rule__QualifiedName__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2948:1: ( ( ( rule__QualifiedName__Group_1__0 )* ) )
            // InternalSpecDSL.g:2949:1: ( ( rule__QualifiedName__Group_1__0 )* )
            {
            // InternalSpecDSL.g:2949:1: ( ( rule__QualifiedName__Group_1__0 )* )
            // InternalSpecDSL.g:2950:2: ( rule__QualifiedName__Group_1__0 )*
            {
             before(grammarAccess.getQualifiedNameAccess().getGroup_1()); 
            // InternalSpecDSL.g:2951:2: ( rule__QualifiedName__Group_1__0 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==30) ) {
                    int LA24_2 = input.LA(2);

                    if ( (LA24_2==RULE_ID) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
            	case 1 :
            	    // InternalSpecDSL.g:2951:3: rule__QualifiedName__Group_1__0
            	    {
            	    pushFollow(FOLLOW_35);
            	    rule__QualifiedName__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

             after(grammarAccess.getQualifiedNameAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group__1__Impl"


    // $ANTLR start "rule__QualifiedName__Group_1__0"
    // InternalSpecDSL.g:2960:1: rule__QualifiedName__Group_1__0 : rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 ;
    public final void rule__QualifiedName__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2964:1: ( rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 )
            // InternalSpecDSL.g:2965:2: rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1
            {
            pushFollow(FOLLOW_4);
            rule__QualifiedName__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QualifiedName__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group_1__0"


    // $ANTLR start "rule__QualifiedName__Group_1__0__Impl"
    // InternalSpecDSL.g:2972:1: rule__QualifiedName__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifiedName__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2976:1: ( ( '.' ) )
            // InternalSpecDSL.g:2977:1: ( '.' )
            {
            // InternalSpecDSL.g:2977:1: ( '.' )
            // InternalSpecDSL.g:2978:2: '.'
            {
             before(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
            match(input,30,FOLLOW_2); 
             after(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group_1__0__Impl"


    // $ANTLR start "rule__QualifiedName__Group_1__1"
    // InternalSpecDSL.g:2987:1: rule__QualifiedName__Group_1__1 : rule__QualifiedName__Group_1__1__Impl ;
    public final void rule__QualifiedName__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:2991:1: ( rule__QualifiedName__Group_1__1__Impl )
            // InternalSpecDSL.g:2992:2: rule__QualifiedName__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifiedName__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group_1__1"


    // $ANTLR start "rule__QualifiedName__Group_1__1__Impl"
    // InternalSpecDSL.g:2998:1: rule__QualifiedName__Group_1__1__Impl : ( RULE_ID ) ;
    public final void rule__QualifiedName__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3002:1: ( ( RULE_ID ) )
            // InternalSpecDSL.g:3003:1: ( RULE_ID )
            {
            // InternalSpecDSL.g:3003:1: ( RULE_ID )
            // InternalSpecDSL.g:3004:2: RULE_ID
            {
             before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedName__Group_1__1__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group__0"
    // InternalSpecDSL.g:3014:1: rule__QualifiedNameWithWildcard__Group__0 : rule__QualifiedNameWithWildcard__Group__0__Impl rule__QualifiedNameWithWildcard__Group__1 ;
    public final void rule__QualifiedNameWithWildcard__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3018:1: ( rule__QualifiedNameWithWildcard__Group__0__Impl rule__QualifiedNameWithWildcard__Group__1 )
            // InternalSpecDSL.g:3019:2: rule__QualifiedNameWithWildcard__Group__0__Impl rule__QualifiedNameWithWildcard__Group__1
            {
            pushFollow(FOLLOW_34);
            rule__QualifiedNameWithWildcard__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QualifiedNameWithWildcard__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group__0"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group__0__Impl"
    // InternalSpecDSL.g:3026:1: rule__QualifiedNameWithWildcard__Group__0__Impl : ( ruleQualifiedName ) ;
    public final void rule__QualifiedNameWithWildcard__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3030:1: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3031:1: ( ruleQualifiedName )
            {
            // InternalSpecDSL.g:3031:1: ( ruleQualifiedName )
            // InternalSpecDSL.g:3032:2: ruleQualifiedName
            {
             before(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group__0__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group__1"
    // InternalSpecDSL.g:3041:1: rule__QualifiedNameWithWildcard__Group__1 : rule__QualifiedNameWithWildcard__Group__1__Impl ;
    public final void rule__QualifiedNameWithWildcard__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3045:1: ( rule__QualifiedNameWithWildcard__Group__1__Impl )
            // InternalSpecDSL.g:3046:2: rule__QualifiedNameWithWildcard__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifiedNameWithWildcard__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group__1"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group__1__Impl"
    // InternalSpecDSL.g:3052:1: rule__QualifiedNameWithWildcard__Group__1__Impl : ( ( rule__QualifiedNameWithWildcard__Group_1__0 )? ) ;
    public final void rule__QualifiedNameWithWildcard__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3056:1: ( ( ( rule__QualifiedNameWithWildcard__Group_1__0 )? ) )
            // InternalSpecDSL.g:3057:1: ( ( rule__QualifiedNameWithWildcard__Group_1__0 )? )
            {
            // InternalSpecDSL.g:3057:1: ( ( rule__QualifiedNameWithWildcard__Group_1__0 )? )
            // InternalSpecDSL.g:3058:2: ( rule__QualifiedNameWithWildcard__Group_1__0 )?
            {
             before(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup_1()); 
            // InternalSpecDSL.g:3059:2: ( rule__QualifiedNameWithWildcard__Group_1__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==30) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSpecDSL.g:3059:3: rule__QualifiedNameWithWildcard__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__QualifiedNameWithWildcard__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group__1__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group_1__0"
    // InternalSpecDSL.g:3068:1: rule__QualifiedNameWithWildcard__Group_1__0 : rule__QualifiedNameWithWildcard__Group_1__0__Impl rule__QualifiedNameWithWildcard__Group_1__1 ;
    public final void rule__QualifiedNameWithWildcard__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3072:1: ( rule__QualifiedNameWithWildcard__Group_1__0__Impl rule__QualifiedNameWithWildcard__Group_1__1 )
            // InternalSpecDSL.g:3073:2: rule__QualifiedNameWithWildcard__Group_1__0__Impl rule__QualifiedNameWithWildcard__Group_1__1
            {
            pushFollow(FOLLOW_36);
            rule__QualifiedNameWithWildcard__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QualifiedNameWithWildcard__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group_1__0"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group_1__0__Impl"
    // InternalSpecDSL.g:3080:1: rule__QualifiedNameWithWildcard__Group_1__0__Impl : ( '.' ) ;
    public final void rule__QualifiedNameWithWildcard__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3084:1: ( ( '.' ) )
            // InternalSpecDSL.g:3085:1: ( '.' )
            {
            // InternalSpecDSL.g:3085:1: ( '.' )
            // InternalSpecDSL.g:3086:2: '.'
            {
             before(grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopKeyword_1_0()); 
            match(input,30,FOLLOW_2); 
             after(grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group_1__0__Impl"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group_1__1"
    // InternalSpecDSL.g:3095:1: rule__QualifiedNameWithWildcard__Group_1__1 : rule__QualifiedNameWithWildcard__Group_1__1__Impl ;
    public final void rule__QualifiedNameWithWildcard__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3099:1: ( rule__QualifiedNameWithWildcard__Group_1__1__Impl )
            // InternalSpecDSL.g:3100:2: rule__QualifiedNameWithWildcard__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QualifiedNameWithWildcard__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group_1__1"


    // $ANTLR start "rule__QualifiedNameWithWildcard__Group_1__1__Impl"
    // InternalSpecDSL.g:3106:1: rule__QualifiedNameWithWildcard__Group_1__1__Impl : ( '*' ) ;
    public final void rule__QualifiedNameWithWildcard__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3110:1: ( ( '*' ) )
            // InternalSpecDSL.g:3111:1: ( '*' )
            {
            // InternalSpecDSL.g:3111:1: ( '*' )
            // InternalSpecDSL.g:3112:2: '*'
            {
             before(grammarAccess.getQualifiedNameWithWildcardAccess().getAsteriskKeyword_1_1()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getQualifiedNameWithWildcardAccess().getAsteriskKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QualifiedNameWithWildcard__Group_1__1__Impl"


    // $ANTLR start "rule__Model__ElementsAssignment"
    // InternalSpecDSL.g:3122:1: rule__Model__ElementsAssignment : ( ruleAbstractElement ) ;
    public final void rule__Model__ElementsAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3126:1: ( ( ruleAbstractElement ) )
            // InternalSpecDSL.g:3127:2: ( ruleAbstractElement )
            {
            // InternalSpecDSL.g:3127:2: ( ruleAbstractElement )
            // InternalSpecDSL.g:3128:3: ruleAbstractElement
            {
             before(grammarAccess.getModelAccess().getElementsAbstractElementParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleAbstractElement();

            state._fsp--;

             after(grammarAccess.getModelAccess().getElementsAbstractElementParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__ElementsAssignment"


    // $ANTLR start "rule__Import__ImportedNamespaceAssignment_1"
    // InternalSpecDSL.g:3137:1: rule__Import__ImportedNamespaceAssignment_1 : ( ruleQualifiedNameWithWildcard ) ;
    public final void rule__Import__ImportedNamespaceAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3141:1: ( ( ruleQualifiedNameWithWildcard ) )
            // InternalSpecDSL.g:3142:2: ( ruleQualifiedNameWithWildcard )
            {
            // InternalSpecDSL.g:3142:2: ( ruleQualifiedNameWithWildcard )
            // InternalSpecDSL.g:3143:3: ruleQualifiedNameWithWildcard
            {
             before(grammarAccess.getImportAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedNameWithWildcard();

            state._fsp--;

             after(grammarAccess.getImportAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__ImportedNamespaceAssignment_1"


    // $ANTLR start "rule__PosDef__NameAssignment_3"
    // InternalSpecDSL.g:3152:1: rule__PosDef__NameAssignment_3 : ( ruleQualifiedName ) ;
    public final void rule__PosDef__NameAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3156:1: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3157:2: ( ruleQualifiedName )
            {
            // InternalSpecDSL.g:3157:2: ( ruleQualifiedName )
            // InternalSpecDSL.g:3158:3: ruleQualifiedName
            {
             before(grammarAccess.getPosDefAccess().getNameQualifiedNameParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getPosDefAccess().getNameQualifiedNameParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__NameAssignment_3"


    // $ANTLR start "rule__PosDef__TagsAssignment_5"
    // InternalSpecDSL.g:3167:1: rule__PosDef__TagsAssignment_5 : ( rulePOSTag ) ;
    public final void rule__PosDef__TagsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3171:1: ( ( rulePOSTag ) )
            // InternalSpecDSL.g:3172:2: ( rulePOSTag )
            {
            // InternalSpecDSL.g:3172:2: ( rulePOSTag )
            // InternalSpecDSL.g:3173:3: rulePOSTag
            {
             before(grammarAccess.getPosDefAccess().getTagsPOSTagParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            rulePOSTag();

            state._fsp--;

             after(grammarAccess.getPosDefAccess().getTagsPOSTagParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PosDef__TagsAssignment_5"


    // $ANTLR start "rule__POSTag__NameAssignment_0"
    // InternalSpecDSL.g:3182:1: rule__POSTag__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__POSTag__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3186:1: ( ( RULE_ID ) )
            // InternalSpecDSL.g:3187:2: ( RULE_ID )
            {
            // InternalSpecDSL.g:3187:2: ( RULE_ID )
            // InternalSpecDSL.g:3188:3: RULE_ID
            {
             before(grammarAccess.getPOSTagAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getPOSTagAccess().getNameIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__NameAssignment_0"


    // $ANTLR start "rule__POSTag__TagnameAssignment_1_1"
    // InternalSpecDSL.g:3197:1: rule__POSTag__TagnameAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__POSTag__TagnameAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3201:1: ( ( RULE_STRING ) )
            // InternalSpecDSL.g:3202:2: ( RULE_STRING )
            {
            // InternalSpecDSL.g:3202:2: ( RULE_STRING )
            // InternalSpecDSL.g:3203:3: RULE_STRING
            {
             before(grammarAccess.getPOSTagAccess().getTagnameSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getPOSTagAccess().getTagnameSTRINGTerminalRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__POSTag__TagnameAssignment_1_1"


    // $ANTLR start "rule__DepDef__NameAssignment_3"
    // InternalSpecDSL.g:3212:1: rule__DepDef__NameAssignment_3 : ( ruleQualifiedName ) ;
    public final void rule__DepDef__NameAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3216:1: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3217:2: ( ruleQualifiedName )
            {
            // InternalSpecDSL.g:3217:2: ( ruleQualifiedName )
            // InternalSpecDSL.g:3218:3: ruleQualifiedName
            {
             before(grammarAccess.getDepDefAccess().getNameQualifiedNameParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getDepDefAccess().getNameQualifiedNameParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__NameAssignment_3"


    // $ANTLR start "rule__DepDef__TagsAssignment_5"
    // InternalSpecDSL.g:3227:1: rule__DepDef__TagsAssignment_5 : ( ruleDepTag ) ;
    public final void rule__DepDef__TagsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3231:1: ( ( ruleDepTag ) )
            // InternalSpecDSL.g:3232:2: ( ruleDepTag )
            {
            // InternalSpecDSL.g:3232:2: ( ruleDepTag )
            // InternalSpecDSL.g:3233:3: ruleDepTag
            {
             before(grammarAccess.getDepDefAccess().getTagsDepTagParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleDepTag();

            state._fsp--;

             after(grammarAccess.getDepDefAccess().getTagsDepTagParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepDef__TagsAssignment_5"


    // $ANTLR start "rule__DepTag__NameAssignment_0"
    // InternalSpecDSL.g:3242:1: rule__DepTag__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__DepTag__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3246:1: ( ( RULE_ID ) )
            // InternalSpecDSL.g:3247:2: ( RULE_ID )
            {
            // InternalSpecDSL.g:3247:2: ( RULE_ID )
            // InternalSpecDSL.g:3248:3: RULE_ID
            {
             before(grammarAccess.getDepTagAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getDepTagAccess().getNameIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__NameAssignment_0"


    // $ANTLR start "rule__DepTag__TagnameAssignment_1_1"
    // InternalSpecDSL.g:3257:1: rule__DepTag__TagnameAssignment_1_1 : ( RULE_STRING ) ;
    public final void rule__DepTag__TagnameAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3261:1: ( ( RULE_STRING ) )
            // InternalSpecDSL.g:3262:2: ( RULE_STRING )
            {
            // InternalSpecDSL.g:3262:2: ( RULE_STRING )
            // InternalSpecDSL.g:3263:3: RULE_STRING
            {
             before(grammarAccess.getDepTagAccess().getTagnameSTRINGTerminalRuleCall_1_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getDepTagAccess().getTagnameSTRINGTerminalRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DepTag__TagnameAssignment_1_1"


    // $ANTLR start "rule__TreeDef__TreesAssignment_3_0_1"
    // InternalSpecDSL.g:3272:1: rule__TreeDef__TreesAssignment_3_0_1 : ( ruleTreeTag ) ;
    public final void rule__TreeDef__TreesAssignment_3_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3276:1: ( ( ruleTreeTag ) )
            // InternalSpecDSL.g:3277:2: ( ruleTreeTag )
            {
            // InternalSpecDSL.g:3277:2: ( ruleTreeTag )
            // InternalSpecDSL.g:3278:3: ruleTreeTag
            {
             before(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleTreeTag();

            state._fsp--;

             after(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__TreesAssignment_3_0_1"


    // $ANTLR start "rule__TreeDef__TreesAssignment_3_1_0"
    // InternalSpecDSL.g:3287:1: rule__TreeDef__TreesAssignment_3_1_0 : ( ruleTreeTag ) ;
    public final void rule__TreeDef__TreesAssignment_3_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3291:1: ( ( ruleTreeTag ) )
            // InternalSpecDSL.g:3292:2: ( ruleTreeTag )
            {
            // InternalSpecDSL.g:3292:2: ( ruleTreeTag )
            // InternalSpecDSL.g:3293:3: ruleTreeTag
            {
             before(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleTreeTag();

            state._fsp--;

             after(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__TreesAssignment_3_1_0"


    // $ANTLR start "rule__TreeDef__TreesAssignment_3_1_1_1"
    // InternalSpecDSL.g:3302:1: rule__TreeDef__TreesAssignment_3_1_1_1 : ( ruleTreeTag ) ;
    public final void rule__TreeDef__TreesAssignment_3_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3306:1: ( ( ruleTreeTag ) )
            // InternalSpecDSL.g:3307:2: ( ruleTreeTag )
            {
            // InternalSpecDSL.g:3307:2: ( ruleTreeTag )
            // InternalSpecDSL.g:3308:3: ruleTreeTag
            {
             before(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_1_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleTreeTag();

            state._fsp--;

             after(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeDef__TreesAssignment_3_1_1_1"


    // $ANTLR start "rule__TreeTag__NameAssignment"
    // InternalSpecDSL.g:3317:1: rule__TreeTag__NameAssignment : ( RULE_ID ) ;
    public final void rule__TreeTag__NameAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3321:1: ( ( RULE_ID ) )
            // InternalSpecDSL.g:3322:2: ( RULE_ID )
            {
            // InternalSpecDSL.g:3322:2: ( RULE_ID )
            // InternalSpecDSL.g:3323:3: RULE_ID
            {
             before(grammarAccess.getTreeTagAccess().getNameIDTerminalRuleCall_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getTreeTagAccess().getNameIDTerminalRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeTag__NameAssignment"


    // $ANTLR start "rule__Rule__NameAssignment_2"
    // InternalSpecDSL.g:3332:1: rule__Rule__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Rule__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3336:1: ( ( RULE_ID ) )
            // InternalSpecDSL.g:3337:2: ( RULE_ID )
            {
            // InternalSpecDSL.g:3337:2: ( RULE_ID )
            // InternalSpecDSL.g:3338:3: RULE_ID
            {
             before(grammarAccess.getRuleAccess().getNameIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getRuleAccess().getNameIDTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__NameAssignment_2"


    // $ANTLR start "rule__Rule__DependenciesAssignment_4"
    // InternalSpecDSL.g:3347:1: rule__Rule__DependenciesAssignment_4 : ( ruleDependencyRule ) ;
    public final void rule__Rule__DependenciesAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3351:1: ( ( ruleDependencyRule ) )
            // InternalSpecDSL.g:3352:2: ( ruleDependencyRule )
            {
            // InternalSpecDSL.g:3352:2: ( ruleDependencyRule )
            // InternalSpecDSL.g:3353:3: ruleDependencyRule
            {
             before(grammarAccess.getRuleAccess().getDependenciesDependencyRuleParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleDependencyRule();

            state._fsp--;

             after(grammarAccess.getRuleAccess().getDependenciesDependencyRuleParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Rule__DependenciesAssignment_4"


    // $ANTLR start "rule__DependencyRule__LeftNodeAssignment_0_0"
    // InternalSpecDSL.g:3362:1: rule__DependencyRule__LeftNodeAssignment_0_0 : ( ruleTreeNode ) ;
    public final void rule__DependencyRule__LeftNodeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3366:1: ( ( ruleTreeNode ) )
            // InternalSpecDSL.g:3367:2: ( ruleTreeNode )
            {
            // InternalSpecDSL.g:3367:2: ( ruleTreeNode )
            // InternalSpecDSL.g:3368:3: ruleTreeNode
            {
             before(grammarAccess.getDependencyRuleAccess().getLeftNodeTreeNodeParserRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleTreeNode();

            state._fsp--;

             after(grammarAccess.getDependencyRuleAccess().getLeftNodeTreeNodeParserRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__LeftNodeAssignment_0_0"


    // $ANTLR start "rule__DependencyRule__DTagAssignment_0_2"
    // InternalSpecDSL.g:3377:1: rule__DependencyRule__DTagAssignment_0_2 : ( ( ruleQualifiedName ) ) ;
    public final void rule__DependencyRule__DTagAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3381:1: ( ( ( ruleQualifiedName ) ) )
            // InternalSpecDSL.g:3382:2: ( ( ruleQualifiedName ) )
            {
            // InternalSpecDSL.g:3382:2: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3383:3: ( ruleQualifiedName )
            {
             before(grammarAccess.getDependencyRuleAccess().getDTagDepTagCrossReference_0_2_0()); 
            // InternalSpecDSL.g:3384:3: ( ruleQualifiedName )
            // InternalSpecDSL.g:3385:4: ruleQualifiedName
            {
             before(grammarAccess.getDependencyRuleAccess().getDTagDepTagQualifiedNameParserRuleCall_0_2_0_1()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getDependencyRuleAccess().getDTagDepTagQualifiedNameParserRuleCall_0_2_0_1()); 

            }

             after(grammarAccess.getDependencyRuleAccess().getDTagDepTagCrossReference_0_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__DTagAssignment_0_2"


    // $ANTLR start "rule__DependencyRule__RightNodeAssignment_0_4"
    // InternalSpecDSL.g:3396:1: rule__DependencyRule__RightNodeAssignment_0_4 : ( ( rule__DependencyRule__RightNodeAlternatives_0_4_0 ) ) ;
    public final void rule__DependencyRule__RightNodeAssignment_0_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3400:1: ( ( ( rule__DependencyRule__RightNodeAlternatives_0_4_0 ) ) )
            // InternalSpecDSL.g:3401:2: ( ( rule__DependencyRule__RightNodeAlternatives_0_4_0 ) )
            {
            // InternalSpecDSL.g:3401:2: ( ( rule__DependencyRule__RightNodeAlternatives_0_4_0 ) )
            // InternalSpecDSL.g:3402:3: ( rule__DependencyRule__RightNodeAlternatives_0_4_0 )
            {
             before(grammarAccess.getDependencyRuleAccess().getRightNodeAlternatives_0_4_0()); 
            // InternalSpecDSL.g:3403:3: ( rule__DependencyRule__RightNodeAlternatives_0_4_0 )
            // InternalSpecDSL.g:3403:4: rule__DependencyRule__RightNodeAlternatives_0_4_0
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__RightNodeAlternatives_0_4_0();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getRightNodeAlternatives_0_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__RightNodeAssignment_0_4"


    // $ANTLR start "rule__DependencyRule__LeftNodeAssignment_1_0"
    // InternalSpecDSL.g:3411:1: rule__DependencyRule__LeftNodeAssignment_1_0 : ( ruleNonTreeNode ) ;
    public final void rule__DependencyRule__LeftNodeAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3415:1: ( ( ruleNonTreeNode ) )
            // InternalSpecDSL.g:3416:2: ( ruleNonTreeNode )
            {
            // InternalSpecDSL.g:3416:2: ( ruleNonTreeNode )
            // InternalSpecDSL.g:3417:3: ruleNonTreeNode
            {
             before(grammarAccess.getDependencyRuleAccess().getLeftNodeNonTreeNodeParserRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleNonTreeNode();

            state._fsp--;

             after(grammarAccess.getDependencyRuleAccess().getLeftNodeNonTreeNodeParserRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__LeftNodeAssignment_1_0"


    // $ANTLR start "rule__DependencyRule__DTagAssignment_1_2"
    // InternalSpecDSL.g:3426:1: rule__DependencyRule__DTagAssignment_1_2 : ( ( ruleQualifiedName ) ) ;
    public final void rule__DependencyRule__DTagAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3430:1: ( ( ( ruleQualifiedName ) ) )
            // InternalSpecDSL.g:3431:2: ( ( ruleQualifiedName ) )
            {
            // InternalSpecDSL.g:3431:2: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3432:3: ( ruleQualifiedName )
            {
             before(grammarAccess.getDependencyRuleAccess().getDTagDepTagCrossReference_1_2_0()); 
            // InternalSpecDSL.g:3433:3: ( ruleQualifiedName )
            // InternalSpecDSL.g:3434:4: ruleQualifiedName
            {
             before(grammarAccess.getDependencyRuleAccess().getDTagDepTagQualifiedNameParserRuleCall_1_2_0_1()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getDependencyRuleAccess().getDTagDepTagQualifiedNameParserRuleCall_1_2_0_1()); 

            }

             after(grammarAccess.getDependencyRuleAccess().getDTagDepTagCrossReference_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__DTagAssignment_1_2"


    // $ANTLR start "rule__DependencyRule__RightNodeAssignment_1_4"
    // InternalSpecDSL.g:3445:1: rule__DependencyRule__RightNodeAssignment_1_4 : ( ( rule__DependencyRule__RightNodeAlternatives_1_4_0 ) ) ;
    public final void rule__DependencyRule__RightNodeAssignment_1_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3449:1: ( ( ( rule__DependencyRule__RightNodeAlternatives_1_4_0 ) ) )
            // InternalSpecDSL.g:3450:2: ( ( rule__DependencyRule__RightNodeAlternatives_1_4_0 ) )
            {
            // InternalSpecDSL.g:3450:2: ( ( rule__DependencyRule__RightNodeAlternatives_1_4_0 ) )
            // InternalSpecDSL.g:3451:3: ( rule__DependencyRule__RightNodeAlternatives_1_4_0 )
            {
             before(grammarAccess.getDependencyRuleAccess().getRightNodeAlternatives_1_4_0()); 
            // InternalSpecDSL.g:3452:3: ( rule__DependencyRule__RightNodeAlternatives_1_4_0 )
            // InternalSpecDSL.g:3452:4: rule__DependencyRule__RightNodeAlternatives_1_4_0
            {
            pushFollow(FOLLOW_2);
            rule__DependencyRule__RightNodeAlternatives_1_4_0();

            state._fsp--;


            }

             after(grammarAccess.getDependencyRuleAccess().getRightNodeAlternatives_1_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DependencyRule__RightNodeAssignment_1_4"


    // $ANTLR start "rule__FreeDependencyRule__LeftNodeAssignment_0"
    // InternalSpecDSL.g:3460:1: rule__FreeDependencyRule__LeftNodeAssignment_0 : ( ruleNode ) ;
    public final void rule__FreeDependencyRule__LeftNodeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3464:1: ( ( ruleNode ) )
            // InternalSpecDSL.g:3465:2: ( ruleNode )
            {
            // InternalSpecDSL.g:3465:2: ( ruleNode )
            // InternalSpecDSL.g:3466:3: ruleNode
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getLeftNodeNodeParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleNode();

            state._fsp--;

             after(grammarAccess.getFreeDependencyRuleAccess().getLeftNodeNodeParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__LeftNodeAssignment_0"


    // $ANTLR start "rule__FreeDependencyRule__DTagAssignment_2"
    // InternalSpecDSL.g:3475:1: rule__FreeDependencyRule__DTagAssignment_2 : ( ( ruleQualifiedName ) ) ;
    public final void rule__FreeDependencyRule__DTagAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3479:1: ( ( ( ruleQualifiedName ) ) )
            // InternalSpecDSL.g:3480:2: ( ( ruleQualifiedName ) )
            {
            // InternalSpecDSL.g:3480:2: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3481:3: ( ruleQualifiedName )
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getDTagDepTagCrossReference_2_0()); 
            // InternalSpecDSL.g:3482:3: ( ruleQualifiedName )
            // InternalSpecDSL.g:3483:4: ruleQualifiedName
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getDTagDepTagQualifiedNameParserRuleCall_2_0_1()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getFreeDependencyRuleAccess().getDTagDepTagQualifiedNameParserRuleCall_2_0_1()); 

            }

             after(grammarAccess.getFreeDependencyRuleAccess().getDTagDepTagCrossReference_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__DTagAssignment_2"


    // $ANTLR start "rule__FreeDependencyRule__RightNodeAssignment_4"
    // InternalSpecDSL.g:3494:1: rule__FreeDependencyRule__RightNodeAssignment_4 : ( ( rule__FreeDependencyRule__RightNodeAlternatives_4_0 ) ) ;
    public final void rule__FreeDependencyRule__RightNodeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3498:1: ( ( ( rule__FreeDependencyRule__RightNodeAlternatives_4_0 ) ) )
            // InternalSpecDSL.g:3499:2: ( ( rule__FreeDependencyRule__RightNodeAlternatives_4_0 ) )
            {
            // InternalSpecDSL.g:3499:2: ( ( rule__FreeDependencyRule__RightNodeAlternatives_4_0 ) )
            // InternalSpecDSL.g:3500:3: ( rule__FreeDependencyRule__RightNodeAlternatives_4_0 )
            {
             before(grammarAccess.getFreeDependencyRuleAccess().getRightNodeAlternatives_4_0()); 
            // InternalSpecDSL.g:3501:3: ( rule__FreeDependencyRule__RightNodeAlternatives_4_0 )
            // InternalSpecDSL.g:3501:4: rule__FreeDependencyRule__RightNodeAlternatives_4_0
            {
            pushFollow(FOLLOW_2);
            rule__FreeDependencyRule__RightNodeAlternatives_4_0();

            state._fsp--;


            }

             after(grammarAccess.getFreeDependencyRuleAccess().getRightNodeAlternatives_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FreeDependencyRule__RightNodeAssignment_4"


    // $ANTLR start "rule__OptionNode__LeftNodeAssignment_1"
    // InternalSpecDSL.g:3509:1: rule__OptionNode__LeftNodeAssignment_1 : ( ruleExplicitNode ) ;
    public final void rule__OptionNode__LeftNodeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3513:1: ( ( ruleExplicitNode ) )
            // InternalSpecDSL.g:3514:2: ( ruleExplicitNode )
            {
            // InternalSpecDSL.g:3514:2: ( ruleExplicitNode )
            // InternalSpecDSL.g:3515:3: ruleExplicitNode
            {
             before(grammarAccess.getOptionNodeAccess().getLeftNodeExplicitNodeParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExplicitNode();

            state._fsp--;

             after(grammarAccess.getOptionNodeAccess().getLeftNodeExplicitNodeParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__LeftNodeAssignment_1"


    // $ANTLR start "rule__OptionNode__RightNodesAssignment_2_1"
    // InternalSpecDSL.g:3524:1: rule__OptionNode__RightNodesAssignment_2_1 : ( ruleExplicitNode ) ;
    public final void rule__OptionNode__RightNodesAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3528:1: ( ( ruleExplicitNode ) )
            // InternalSpecDSL.g:3529:2: ( ruleExplicitNode )
            {
            // InternalSpecDSL.g:3529:2: ( ruleExplicitNode )
            // InternalSpecDSL.g:3530:3: ruleExplicitNode
            {
             before(grammarAccess.getOptionNodeAccess().getRightNodesExplicitNodeParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExplicitNode();

            state._fsp--;

             after(grammarAccess.getOptionNodeAccess().getRightNodesExplicitNodeParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OptionNode__RightNodesAssignment_2_1"


    // $ANTLR start "rule__TreeNode__PTagAssignment_0_0"
    // InternalSpecDSL.g:3539:1: rule__TreeNode__PTagAssignment_0_0 : ( ( ruleQualifiedName ) ) ;
    public final void rule__TreeNode__PTagAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3543:1: ( ( ( ruleQualifiedName ) ) )
            // InternalSpecDSL.g:3544:2: ( ( ruleQualifiedName ) )
            {
            // InternalSpecDSL.g:3544:2: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3545:3: ( ruleQualifiedName )
            {
             before(grammarAccess.getTreeNodeAccess().getPTagPOSTagCrossReference_0_0_0()); 
            // InternalSpecDSL.g:3546:3: ( ruleQualifiedName )
            // InternalSpecDSL.g:3547:4: ruleQualifiedName
            {
             before(grammarAccess.getTreeNodeAccess().getPTagPOSTagQualifiedNameParserRuleCall_0_0_0_1()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getTreeNodeAccess().getPTagPOSTagQualifiedNameParserRuleCall_0_0_0_1()); 

            }

             after(grammarAccess.getTreeNodeAccess().getPTagPOSTagCrossReference_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__PTagAssignment_0_0"


    // $ANTLR start "rule__TreeNode__ExprAssignment_1_0_0"
    // InternalSpecDSL.g:3558:1: rule__TreeNode__ExprAssignment_1_0_0 : ( RULE_STRING ) ;
    public final void rule__TreeNode__ExprAssignment_1_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3562:1: ( ( RULE_STRING ) )
            // InternalSpecDSL.g:3563:2: ( RULE_STRING )
            {
            // InternalSpecDSL.g:3563:2: ( RULE_STRING )
            // InternalSpecDSL.g:3564:3: RULE_STRING
            {
             before(grammarAccess.getTreeNodeAccess().getExprSTRINGTerminalRuleCall_1_0_0_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getTreeNodeAccess().getExprSTRINGTerminalRuleCall_1_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__ExprAssignment_1_0_0"


    // $ANTLR start "rule__TreeNode__AnyMatchAssignment_1_0_1"
    // InternalSpecDSL.g:3573:1: rule__TreeNode__AnyMatchAssignment_1_0_1 : ( ( '*' ) ) ;
    public final void rule__TreeNode__AnyMatchAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3577:1: ( ( ( '*' ) ) )
            // InternalSpecDSL.g:3578:2: ( ( '*' ) )
            {
            // InternalSpecDSL.g:3578:2: ( ( '*' ) )
            // InternalSpecDSL.g:3579:3: ( '*' )
            {
             before(grammarAccess.getTreeNodeAccess().getAnyMatchAsteriskKeyword_1_0_1_0()); 
            // InternalSpecDSL.g:3580:3: ( '*' )
            // InternalSpecDSL.g:3581:4: '*'
            {
             before(grammarAccess.getTreeNodeAccess().getAnyMatchAsteriskKeyword_1_0_1_0()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getTreeNodeAccess().getAnyMatchAsteriskKeyword_1_0_1_0()); 

            }

             after(grammarAccess.getTreeNodeAccess().getAnyMatchAsteriskKeyword_1_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__AnyMatchAssignment_1_0_1"


    // $ANTLR start "rule__TreeNode__TreeAssignment_2"
    // InternalSpecDSL.g:3592:1: rule__TreeNode__TreeAssignment_2 : ( ruleSubtree ) ;
    public final void rule__TreeNode__TreeAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3596:1: ( ( ruleSubtree ) )
            // InternalSpecDSL.g:3597:2: ( ruleSubtree )
            {
            // InternalSpecDSL.g:3597:2: ( ruleSubtree )
            // InternalSpecDSL.g:3598:3: ruleSubtree
            {
             before(grammarAccess.getTreeNodeAccess().getTreeSubtreeParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleSubtree();

            state._fsp--;

             after(grammarAccess.getTreeNodeAccess().getTreeSubtreeParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__TreeNode__TreeAssignment_2"


    // $ANTLR start "rule__ExplicitNode__PTagAssignment_0_0"
    // InternalSpecDSL.g:3607:1: rule__ExplicitNode__PTagAssignment_0_0 : ( ( ruleQualifiedName ) ) ;
    public final void rule__ExplicitNode__PTagAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3611:1: ( ( ( ruleQualifiedName ) ) )
            // InternalSpecDSL.g:3612:2: ( ( ruleQualifiedName ) )
            {
            // InternalSpecDSL.g:3612:2: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:3613:3: ( ruleQualifiedName )
            {
             before(grammarAccess.getExplicitNodeAccess().getPTagPOSTagCrossReference_0_0_0()); 
            // InternalSpecDSL.g:3614:3: ( ruleQualifiedName )
            // InternalSpecDSL.g:3615:4: ruleQualifiedName
            {
             before(grammarAccess.getExplicitNodeAccess().getPTagPOSTagQualifiedNameParserRuleCall_0_0_0_1()); 
            pushFollow(FOLLOW_2);
            ruleQualifiedName();

            state._fsp--;

             after(grammarAccess.getExplicitNodeAccess().getPTagPOSTagQualifiedNameParserRuleCall_0_0_0_1()); 

            }

             after(grammarAccess.getExplicitNodeAccess().getPTagPOSTagCrossReference_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__PTagAssignment_0_0"


    // $ANTLR start "rule__ExplicitNode__CaseSensitiveAssignment_1_0_0"
    // InternalSpecDSL.g:3626:1: rule__ExplicitNode__CaseSensitiveAssignment_1_0_0 : ( ( 'CASE!' ) ) ;
    public final void rule__ExplicitNode__CaseSensitiveAssignment_1_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3630:1: ( ( ( 'CASE!' ) ) )
            // InternalSpecDSL.g:3631:2: ( ( 'CASE!' ) )
            {
            // InternalSpecDSL.g:3631:2: ( ( 'CASE!' ) )
            // InternalSpecDSL.g:3632:3: ( 'CASE!' )
            {
             before(grammarAccess.getExplicitNodeAccess().getCaseSensitiveCASEKeyword_1_0_0_0()); 
            // InternalSpecDSL.g:3633:3: ( 'CASE!' )
            // InternalSpecDSL.g:3634:4: 'CASE!'
            {
             before(grammarAccess.getExplicitNodeAccess().getCaseSensitiveCASEKeyword_1_0_0_0()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getExplicitNodeAccess().getCaseSensitiveCASEKeyword_1_0_0_0()); 

            }

             after(grammarAccess.getExplicitNodeAccess().getCaseSensitiveCASEKeyword_1_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__CaseSensitiveAssignment_1_0_0"


    // $ANTLR start "rule__ExplicitNode__ExprAssignment_1_0_1"
    // InternalSpecDSL.g:3645:1: rule__ExplicitNode__ExprAssignment_1_0_1 : ( RULE_STRING ) ;
    public final void rule__ExplicitNode__ExprAssignment_1_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3649:1: ( ( RULE_STRING ) )
            // InternalSpecDSL.g:3650:2: ( RULE_STRING )
            {
            // InternalSpecDSL.g:3650:2: ( RULE_STRING )
            // InternalSpecDSL.g:3651:3: RULE_STRING
            {
             before(grammarAccess.getExplicitNodeAccess().getExprSTRINGTerminalRuleCall_1_0_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getExplicitNodeAccess().getExprSTRINGTerminalRuleCall_1_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__ExprAssignment_1_0_1"


    // $ANTLR start "rule__ExplicitNode__AnyMatchAssignment_1_1"
    // InternalSpecDSL.g:3660:1: rule__ExplicitNode__AnyMatchAssignment_1_1 : ( ( '*' ) ) ;
    public final void rule__ExplicitNode__AnyMatchAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3664:1: ( ( ( '*' ) ) )
            // InternalSpecDSL.g:3665:2: ( ( '*' ) )
            {
            // InternalSpecDSL.g:3665:2: ( ( '*' ) )
            // InternalSpecDSL.g:3666:3: ( '*' )
            {
             before(grammarAccess.getExplicitNodeAccess().getAnyMatchAsteriskKeyword_1_1_0()); 
            // InternalSpecDSL.g:3667:3: ( '*' )
            // InternalSpecDSL.g:3668:4: '*'
            {
             before(grammarAccess.getExplicitNodeAccess().getAnyMatchAsteriskKeyword_1_1_0()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getExplicitNodeAccess().getAnyMatchAsteriskKeyword_1_1_0()); 

            }

             after(grammarAccess.getExplicitNodeAccess().getAnyMatchAsteriskKeyword_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ExplicitNode__AnyMatchAssignment_1_1"


    // $ANTLR start "rule__Subtree__NameAssignment_1"
    // InternalSpecDSL.g:3679:1: rule__Subtree__NameAssignment_1 : ( ( RULE_ID ) ) ;
    public final void rule__Subtree__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSpecDSL.g:3683:1: ( ( ( RULE_ID ) ) )
            // InternalSpecDSL.g:3684:2: ( ( RULE_ID ) )
            {
            // InternalSpecDSL.g:3684:2: ( ( RULE_ID ) )
            // InternalSpecDSL.g:3685:3: ( RULE_ID )
            {
             before(grammarAccess.getSubtreeAccess().getNameTreeTagCrossReference_1_0()); 
            // InternalSpecDSL.g:3686:3: ( RULE_ID )
            // InternalSpecDSL.g:3687:4: RULE_ID
            {
             before(grammarAccess.getSubtreeAccess().getNameTreeTagIDTerminalRuleCall_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getSubtreeAccess().getNameTreeTagIDTerminalRuleCall_1_0_1()); 

            }

             after(grammarAccess.getSubtreeAccess().getNameTreeTagCrossReference_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subtree__NameAssignment_1"

    // Delegated rules


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA8 dfa8 = new DFA8(this);
    static final String dfa_1s = "\11\uffff";
    static final String dfa_2s = "\1\4\1\33\2\26\2\uffff\1\4\1\5\1\33";
    static final String dfa_3s = "\1\40\1\36\2\33\2\uffff\1\4\1\40\1\36";
    static final String dfa_4s = "\4\uffff\1\1\1\2\3\uffff";
    static final String dfa_5s = "\11\uffff}>";
    static final String[] dfa_6s = {
            "\1\1\1\2\22\uffff\1\5\3\uffff\1\4\2\uffff\1\3\1\5",
            "\1\7\2\uffff\1\6",
            "\1\5\4\uffff\1\4",
            "\1\5\4\uffff\1\4",
            "",
            "",
            "\1\10",
            "\1\2\26\uffff\1\4\2\uffff\1\3\1\5",
            "\1\7\2\uffff\1\6"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "612:1: rule__DependencyRule__Alternatives : ( ( ( rule__DependencyRule__Group_0__0 ) ) | ( ( rule__DependencyRule__Group_1__0 ) ) );";
        }
    }
    static final String dfa_7s = "\50\uffff";
    static final String dfa_8s = "\3\uffff\2\13\4\uffff\1\13\14\uffff\1\13\10\uffff\1\13\10\uffff";
    static final String dfa_9s = "\1\4\1\33\1\5\5\4\1\5\1\4\1\34\2\uffff\1\33\1\5\2\32\1\35\1\33\1\4\1\5\2\4\2\33\1\5\2\31\1\4\1\5\2\4\2\33\1\5\2\31\1\4\1\5\1\33";
    static final String dfa_10s = "\1\40\1\36\1\5\3\40\2\4\2\40\1\34\2\uffff\1\36\1\5\2\32\1\35\1\36\1\4\3\40\2\36\1\5\2\32\1\4\3\40\2\36\1\5\2\32\1\4\1\40\1\36";
    static final String dfa_11s = "\13\uffff\1\1\1\2\33\uffff";
    static final String dfa_12s = "\50\uffff}>";
    static final String[] dfa_13s = {
            "\1\1\1\3\22\uffff\1\5\3\uffff\1\6\2\uffff\1\4\1\2",
            "\1\10\2\uffff\1\7",
            "\1\11",
            "\2\13\12\uffff\1\13\5\uffff\1\14\1\uffff\1\13\2\uffff\1\12\1\13\2\uffff\2\13",
            "\2\13\12\uffff\1\13\5\uffff\1\14\1\uffff\1\13\2\uffff\1\12\1\13\2\uffff\2\13",
            "\1\15\1\17\31\uffff\1\20\1\16",
            "\1\21",
            "\1\22",
            "\1\3\26\uffff\1\6\2\uffff\1\4\1\2",
            "\2\13\12\uffff\1\13\5\uffff\1\14\1\uffff\1\13\3\uffff\1\13\2\uffff\2\13",
            "\1\6",
            "",
            "",
            "\1\24\2\uffff\1\23",
            "\1\17",
            "\1\25",
            "\1\25",
            "\1\26",
            "\1\10\2\uffff\1\7",
            "\1\27",
            "\1\17\31\uffff\1\20\1\16",
            "\1\30\1\32\31\uffff\1\33\1\31",
            "\2\13\12\uffff\1\13\5\uffff\1\14\1\uffff\1\13\3\uffff\1\13\2\uffff\2\13",
            "\1\24\2\uffff\1\23",
            "\1\35\2\uffff\1\34",
            "\1\32",
            "\1\37\1\36",
            "\1\37\1\36",
            "\1\40",
            "\1\32\31\uffff\1\33\1\31",
            "\1\41\1\43\31\uffff\1\44\1\42",
            "\2\13\12\uffff\1\13\5\uffff\1\14\1\uffff\1\13\3\uffff\1\13\2\uffff\2\13",
            "\1\35\2\uffff\1\34",
            "\1\46\2\uffff\1\45",
            "\1\43",
            "\1\37\1\36",
            "\1\37\1\36",
            "\1\47",
            "\1\43\31\uffff\1\44\1\42",
            "\1\46\2\uffff\1\45"
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "633:1: rule__DependencyRule__RightNodeAlternatives_0_4_0 : ( ( ruleNode ) | ( ruleFreeDependencyRule ) );";
        }
    }
    static final String dfa_14s = "\15\uffff";
    static final String dfa_15s = "\13\uffff\1\14\1\uffff";
    static final String dfa_16s = "\1\4\1\33\2\26\1\4\1\uffff\1\4\1\5\1\34\1\35\1\33\1\4\1\uffff";
    static final String dfa_17s = "\1\40\1\36\2\33\1\4\1\uffff\1\4\1\40\1\34\1\35\1\36\1\40\1\uffff";
    static final String dfa_18s = "\5\uffff\1\2\6\uffff\1\1";
    static final String dfa_19s = "\15\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\1\2\22\uffff\1\5\3\uffff\1\4\2\uffff\1\3\1\5",
            "\1\7\2\uffff\1\6",
            "\1\5\4\uffff\1\10",
            "\1\5\4\uffff\1\10",
            "\1\11",
            "",
            "\1\12",
            "\1\2\26\uffff\1\4\2\uffff\1\3\1\5",
            "\1\4",
            "\1\13",
            "\1\7\2\uffff\1\6",
            "\2\14\12\uffff\1\14\5\uffff\1\5\1\uffff\1\14\3\uffff\1\14\2\uffff\2\14",
            ""
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "654:1: rule__DependencyRule__RightNodeAlternatives_1_4_0 : ( ( ruleTreeNode ) | ( ruleDependencyRule ) );";
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "675:1: rule__FreeDependencyRule__RightNodeAlternatives_4_0 : ( ( ruleNode ) | ( ruleFreeDependencyRule ) );";
        }
    }
    static final String dfa_21s = "\12\uffff";
    static final String dfa_22s = "\3\uffff\2\2\5\uffff";
    static final String dfa_23s = "\1\4\1\33\1\uffff\2\4\2\uffff\1\4\1\5\1\33";
    static final String dfa_24s = "\1\40\1\36\1\uffff\2\40\2\uffff\1\4\1\40\1\36";
    static final String dfa_25s = "\2\uffff\1\1\2\uffff\1\2\1\3\3\uffff";
    static final String dfa_26s = "\12\uffff}>";
    static final String[] dfa_27s = {
            "\1\1\1\3\22\uffff\1\5\3\uffff\1\6\2\uffff\1\4\1\2",
            "\1\10\2\uffff\1\7",
            "",
            "\2\2\12\uffff\1\2\5\uffff\1\2\1\uffff\1\2\2\uffff\1\6\1\2\2\uffff\2\2",
            "\2\2\12\uffff\1\2\5\uffff\1\2\1\uffff\1\2\2\uffff\1\6\1\2\2\uffff\2\2",
            "",
            "",
            "\1\11",
            "\1\3\26\uffff\1\6\2\uffff\1\4\1\2",
            "\1\10\2\uffff\1\7"
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final char[] dfa_24 = DFA.unpackEncodedStringToUnsignedChars(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final short[][] dfa_27 = unpackEncodedStringArray(dfa_27s);

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = dfa_21;
            this.eof = dfa_22;
            this.min = dfa_23;
            this.max = dfa_24;
            this.accept = dfa_25;
            this.special = dfa_26;
            this.transition = dfa_27;
        }
        public String getDescription() {
            return "696:1: rule__Node__Alternatives : ( ( ruleExplicitNode ) | ( ruleOptionNode ) | ( ruleTreeNode ) );";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000018010L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000008012L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000191000030L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000191000032L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000180000030L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000090000030L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000100000020L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000080000000L});

}