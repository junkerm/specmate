package com.specmate.cause_effect_patterns.internal.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;

import com.specmate.cause_effect_patterns.internal.services.SpecDSLGrammarAccess;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSpecDSLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'def'", "'tagset'", "'parts-of-speech'", "'{'", "'}'", "'='", "'dependencies'", "'subtrees'", "','", "'rule'", "'-'", "'->'", "'('", "'|'", "')'", "':'", "'*'", "'CASE!'", "'['", "']'", "'.'"
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

        public InternalSpecDSLParser(TokenStream input, SpecDSLGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected SpecDSLGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalSpecDSL.g:64:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalSpecDSL.g:64:46: (iv_ruleModel= ruleModel EOF )
            // InternalSpecDSL.g:65:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalSpecDSL.g:71:1: ruleModel returns [EObject current=null] : ( (lv_elements_0_0= ruleAbstractElement ) )* ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_elements_0_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:77:2: ( ( (lv_elements_0_0= ruleAbstractElement ) )* )
            // InternalSpecDSL.g:78:2: ( (lv_elements_0_0= ruleAbstractElement ) )*
            {
            // InternalSpecDSL.g:78:2: ( (lv_elements_0_0= ruleAbstractElement ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=11 && LA1_0<=12)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSpecDSL.g:79:3: (lv_elements_0_0= ruleAbstractElement )
            	    {
            	    // InternalSpecDSL.g:79:3: (lv_elements_0_0= ruleAbstractElement )
            	    // InternalSpecDSL.g:80:4: lv_elements_0_0= ruleAbstractElement
            	    {

            	    				newCompositeNode(grammarAccess.getModelAccess().getElementsAbstractElementParserRuleCall_0());
            	    			
            	    pushFollow(FOLLOW_3);
            	    lv_elements_0_0=ruleAbstractElement();

            	    state._fsp--;


            	    				if (current==null) {
            	    					current = createModelElementForParent(grammarAccess.getModelRule());
            	    				}
            	    				add(
            	    					current,
            	    					"elements",
            	    					lv_elements_0_0,
            	    					"com.specmate.cause_effect_patterns.SpecDSL.AbstractElement");
            	    				afterParserOrEnumRuleCall();
            	    			

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleAbstractElement"
    // InternalSpecDSL.g:100:1: entryRuleAbstractElement returns [EObject current=null] : iv_ruleAbstractElement= ruleAbstractElement EOF ;
    public final EObject entryRuleAbstractElement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractElement = null;


        try {
            // InternalSpecDSL.g:100:56: (iv_ruleAbstractElement= ruleAbstractElement EOF )
            // InternalSpecDSL.g:101:2: iv_ruleAbstractElement= ruleAbstractElement EOF
            {
             newCompositeNode(grammarAccess.getAbstractElementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAbstractElement=ruleAbstractElement();

            state._fsp--;

             current =iv_ruleAbstractElement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAbstractElement"


    // $ANTLR start "ruleAbstractElement"
    // InternalSpecDSL.g:107:1: ruleAbstractElement returns [EObject current=null] : (this_Import_0= ruleImport | this_PosDef_1= rulePosDef | this_DepDef_2= ruleDepDef | this_TreeDef_3= ruleTreeDef | this_Rule_4= ruleRule ) ;
    public final EObject ruleAbstractElement() throws RecognitionException {
        EObject current = null;

        EObject this_Import_0 = null;

        EObject this_PosDef_1 = null;

        EObject this_DepDef_2 = null;

        EObject this_TreeDef_3 = null;

        EObject this_Rule_4 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:113:2: ( (this_Import_0= ruleImport | this_PosDef_1= rulePosDef | this_DepDef_2= ruleDepDef | this_TreeDef_3= ruleTreeDef | this_Rule_4= ruleRule ) )
            // InternalSpecDSL.g:114:2: (this_Import_0= ruleImport | this_PosDef_1= rulePosDef | this_DepDef_2= ruleDepDef | this_TreeDef_3= ruleTreeDef | this_Rule_4= ruleRule )
            {
            // InternalSpecDSL.g:114:2: (this_Import_0= ruleImport | this_PosDef_1= rulePosDef | this_DepDef_2= ruleDepDef | this_TreeDef_3= ruleTreeDef | this_Rule_4= ruleRule )
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

                    if ( (LA2_3==18) ) {
                        alt2=3;
                    }
                    else if ( (LA2_3==14) ) {
                        alt2=2;
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
                    // InternalSpecDSL.g:115:3: this_Import_0= ruleImport
                    {

                    			newCompositeNode(grammarAccess.getAbstractElementAccess().getImportParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Import_0=ruleImport();

                    state._fsp--;


                    			current = this_Import_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:124:3: this_PosDef_1= rulePosDef
                    {

                    			newCompositeNode(grammarAccess.getAbstractElementAccess().getPosDefParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_PosDef_1=rulePosDef();

                    state._fsp--;


                    			current = this_PosDef_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalSpecDSL.g:133:3: this_DepDef_2= ruleDepDef
                    {

                    			newCompositeNode(grammarAccess.getAbstractElementAccess().getDepDefParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_DepDef_2=ruleDepDef();

                    state._fsp--;


                    			current = this_DepDef_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalSpecDSL.g:142:3: this_TreeDef_3= ruleTreeDef
                    {

                    			newCompositeNode(grammarAccess.getAbstractElementAccess().getTreeDefParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_TreeDef_3=ruleTreeDef();

                    state._fsp--;


                    			current = this_TreeDef_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalSpecDSL.g:151:3: this_Rule_4= ruleRule
                    {

                    			newCompositeNode(grammarAccess.getAbstractElementAccess().getRuleParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_Rule_4=ruleRule();

                    state._fsp--;


                    			current = this_Rule_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAbstractElement"


    // $ANTLR start "entryRuleImport"
    // InternalSpecDSL.g:163:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalSpecDSL.g:163:47: (iv_ruleImport= ruleImport EOF )
            // InternalSpecDSL.g:164:2: iv_ruleImport= ruleImport EOF
            {
             newCompositeNode(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleImport=ruleImport();

            state._fsp--;

             current =iv_ruleImport; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalSpecDSL.g:170:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_importedNamespace_1_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:176:2: ( (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) ) )
            // InternalSpecDSL.g:177:2: (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) )
            {
            // InternalSpecDSL.g:177:2: (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) )
            // InternalSpecDSL.g:178:3: otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) )
            {
            otherlv_0=(Token)match(input,11,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalSpecDSL.g:182:3: ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) )
            // InternalSpecDSL.g:183:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            {
            // InternalSpecDSL.g:183:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            // InternalSpecDSL.g:184:5: lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard
            {

            					newCompositeNode(grammarAccess.getImportAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_importedNamespace_1_0=ruleQualifiedNameWithWildcard();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getImportRule());
            					}
            					set(
            						current,
            						"importedNamespace",
            						lv_importedNamespace_1_0,
            						"com.specmate.cause_effect_patterns.SpecDSL.QualifiedNameWithWildcard");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRulePosDef"
    // InternalSpecDSL.g:205:1: entryRulePosDef returns [EObject current=null] : iv_rulePosDef= rulePosDef EOF ;
    public final EObject entryRulePosDef() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePosDef = null;


        try {
            // InternalSpecDSL.g:205:47: (iv_rulePosDef= rulePosDef EOF )
            // InternalSpecDSL.g:206:2: iv_rulePosDef= rulePosDef EOF
            {
             newCompositeNode(grammarAccess.getPosDefRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePosDef=rulePosDef();

            state._fsp--;

             current =iv_rulePosDef; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePosDef"


    // $ANTLR start "rulePosDef"
    // InternalSpecDSL.g:212:1: rulePosDef returns [EObject current=null] : (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'parts-of-speech' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= rulePOSTag ) )* otherlv_6= '}' ) ;
    public final EObject rulePosDef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_3_0 = null;

        EObject lv_tags_5_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:218:2: ( (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'parts-of-speech' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= rulePOSTag ) )* otherlv_6= '}' ) )
            // InternalSpecDSL.g:219:2: (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'parts-of-speech' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= rulePOSTag ) )* otherlv_6= '}' )
            {
            // InternalSpecDSL.g:219:2: (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'parts-of-speech' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= rulePOSTag ) )* otherlv_6= '}' )
            // InternalSpecDSL.g:220:3: otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'parts-of-speech' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= rulePOSTag ) )* otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getPosDefAccess().getDefKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getPosDefAccess().getTagsetKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_4); 

            			newLeafNode(otherlv_2, grammarAccess.getPosDefAccess().getPartsOfSpeechKeyword_2());
            		
            // InternalSpecDSL.g:232:3: ( (lv_name_3_0= ruleQualifiedName ) )
            // InternalSpecDSL.g:233:4: (lv_name_3_0= ruleQualifiedName )
            {
            // InternalSpecDSL.g:233:4: (lv_name_3_0= ruleQualifiedName )
            // InternalSpecDSL.g:234:5: lv_name_3_0= ruleQualifiedName
            {

            					newCompositeNode(grammarAccess.getPosDefAccess().getNameQualifiedNameParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_7);
            lv_name_3_0=ruleQualifiedName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPosDefRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_3_0,
            						"com.specmate.cause_effect_patterns.SpecDSL.QualifiedName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,15,FOLLOW_8); 

            			newLeafNode(otherlv_4, grammarAccess.getPosDefAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalSpecDSL.g:255:3: ( (lv_tags_5_0= rulePOSTag ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==RULE_ID) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSpecDSL.g:256:4: (lv_tags_5_0= rulePOSTag )
            	    {
            	    // InternalSpecDSL.g:256:4: (lv_tags_5_0= rulePOSTag )
            	    // InternalSpecDSL.g:257:5: lv_tags_5_0= rulePOSTag
            	    {

            	    					newCompositeNode(grammarAccess.getPosDefAccess().getTagsPOSTagParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_8);
            	    lv_tags_5_0=rulePOSTag();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getPosDefRule());
            	    					}
            	    					add(
            	    						current,
            	    						"tags",
            	    						lv_tags_5_0,
            	    						"com.specmate.cause_effect_patterns.SpecDSL.POSTag");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            otherlv_6=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getPosDefAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePosDef"


    // $ANTLR start "entryRulePOSTag"
    // InternalSpecDSL.g:282:1: entryRulePOSTag returns [EObject current=null] : iv_rulePOSTag= rulePOSTag EOF ;
    public final EObject entryRulePOSTag() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePOSTag = null;


        try {
            // InternalSpecDSL.g:282:47: (iv_rulePOSTag= rulePOSTag EOF )
            // InternalSpecDSL.g:283:2: iv_rulePOSTag= rulePOSTag EOF
            {
             newCompositeNode(grammarAccess.getPOSTagRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePOSTag=rulePOSTag();

            state._fsp--;

             current =iv_rulePOSTag; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePOSTag"


    // $ANTLR start "rulePOSTag"
    // InternalSpecDSL.g:289:1: rulePOSTag returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? ) ;
    public final EObject rulePOSTag() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_tagname_2_0=null;


        	enterRule();

        try {
            // InternalSpecDSL.g:295:2: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? ) )
            // InternalSpecDSL.g:296:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? )
            {
            // InternalSpecDSL.g:296:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? )
            // InternalSpecDSL.g:297:3: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )?
            {
            // InternalSpecDSL.g:297:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalSpecDSL.g:298:4: (lv_name_0_0= RULE_ID )
            {
            // InternalSpecDSL.g:298:4: (lv_name_0_0= RULE_ID )
            // InternalSpecDSL.g:299:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_9); 

            					newLeafNode(lv_name_0_0, grammarAccess.getPOSTagAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPOSTagRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalSpecDSL.g:315:3: (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==17) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalSpecDSL.g:316:4: otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) )
                    {
                    otherlv_1=(Token)match(input,17,FOLLOW_10); 

                    				newLeafNode(otherlv_1, grammarAccess.getPOSTagAccess().getEqualsSignKeyword_1_0());
                    			
                    // InternalSpecDSL.g:320:4: ( (lv_tagname_2_0= RULE_STRING ) )
                    // InternalSpecDSL.g:321:5: (lv_tagname_2_0= RULE_STRING )
                    {
                    // InternalSpecDSL.g:321:5: (lv_tagname_2_0= RULE_STRING )
                    // InternalSpecDSL.g:322:6: lv_tagname_2_0= RULE_STRING
                    {
                    lv_tagname_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_tagname_2_0, grammarAccess.getPOSTagAccess().getTagnameSTRINGTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getPOSTagRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"tagname",
                    							lv_tagname_2_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePOSTag"


    // $ANTLR start "entryRuleDepDef"
    // InternalSpecDSL.g:343:1: entryRuleDepDef returns [EObject current=null] : iv_ruleDepDef= ruleDepDef EOF ;
    public final EObject entryRuleDepDef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDepDef = null;


        try {
            // InternalSpecDSL.g:343:47: (iv_ruleDepDef= ruleDepDef EOF )
            // InternalSpecDSL.g:344:2: iv_ruleDepDef= ruleDepDef EOF
            {
             newCompositeNode(grammarAccess.getDepDefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDepDef=ruleDepDef();

            state._fsp--;

             current =iv_ruleDepDef; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDepDef"


    // $ANTLR start "ruleDepDef"
    // InternalSpecDSL.g:350:1: ruleDepDef returns [EObject current=null] : (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'dependencies' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= ruleDepTag ) )* otherlv_6= '}' ) ;
    public final EObject ruleDepDef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_3_0 = null;

        EObject lv_tags_5_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:356:2: ( (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'dependencies' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= ruleDepTag ) )* otherlv_6= '}' ) )
            // InternalSpecDSL.g:357:2: (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'dependencies' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= ruleDepTag ) )* otherlv_6= '}' )
            {
            // InternalSpecDSL.g:357:2: (otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'dependencies' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= ruleDepTag ) )* otherlv_6= '}' )
            // InternalSpecDSL.g:358:3: otherlv_0= 'def' otherlv_1= 'tagset' otherlv_2= 'dependencies' ( (lv_name_3_0= ruleQualifiedName ) ) otherlv_4= '{' ( (lv_tags_5_0= ruleDepTag ) )* otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getDepDefAccess().getDefKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getDepDefAccess().getTagsetKeyword_1());
            		
            otherlv_2=(Token)match(input,18,FOLLOW_4); 

            			newLeafNode(otherlv_2, grammarAccess.getDepDefAccess().getDependenciesKeyword_2());
            		
            // InternalSpecDSL.g:370:3: ( (lv_name_3_0= ruleQualifiedName ) )
            // InternalSpecDSL.g:371:4: (lv_name_3_0= ruleQualifiedName )
            {
            // InternalSpecDSL.g:371:4: (lv_name_3_0= ruleQualifiedName )
            // InternalSpecDSL.g:372:5: lv_name_3_0= ruleQualifiedName
            {

            					newCompositeNode(grammarAccess.getDepDefAccess().getNameQualifiedNameParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_7);
            lv_name_3_0=ruleQualifiedName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDepDefRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_3_0,
            						"com.specmate.cause_effect_patterns.SpecDSL.QualifiedName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,15,FOLLOW_8); 

            			newLeafNode(otherlv_4, grammarAccess.getDepDefAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalSpecDSL.g:393:3: ( (lv_tags_5_0= ruleDepTag ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==RULE_ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSpecDSL.g:394:4: (lv_tags_5_0= ruleDepTag )
            	    {
            	    // InternalSpecDSL.g:394:4: (lv_tags_5_0= ruleDepTag )
            	    // InternalSpecDSL.g:395:5: lv_tags_5_0= ruleDepTag
            	    {

            	    					newCompositeNode(grammarAccess.getDepDefAccess().getTagsDepTagParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_8);
            	    lv_tags_5_0=ruleDepTag();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDepDefRule());
            	    					}
            	    					add(
            	    						current,
            	    						"tags",
            	    						lv_tags_5_0,
            	    						"com.specmate.cause_effect_patterns.SpecDSL.DepTag");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            otherlv_6=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getDepDefAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDepDef"


    // $ANTLR start "entryRuleDepTag"
    // InternalSpecDSL.g:420:1: entryRuleDepTag returns [EObject current=null] : iv_ruleDepTag= ruleDepTag EOF ;
    public final EObject entryRuleDepTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDepTag = null;


        try {
            // InternalSpecDSL.g:420:47: (iv_ruleDepTag= ruleDepTag EOF )
            // InternalSpecDSL.g:421:2: iv_ruleDepTag= ruleDepTag EOF
            {
             newCompositeNode(grammarAccess.getDepTagRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDepTag=ruleDepTag();

            state._fsp--;

             current =iv_ruleDepTag; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDepTag"


    // $ANTLR start "ruleDepTag"
    // InternalSpecDSL.g:427:1: ruleDepTag returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? ) ;
    public final EObject ruleDepTag() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_tagname_2_0=null;


        	enterRule();

        try {
            // InternalSpecDSL.g:433:2: ( ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? ) )
            // InternalSpecDSL.g:434:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? )
            {
            // InternalSpecDSL.g:434:2: ( ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )? )
            // InternalSpecDSL.g:435:3: ( (lv_name_0_0= RULE_ID ) ) (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )?
            {
            // InternalSpecDSL.g:435:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalSpecDSL.g:436:4: (lv_name_0_0= RULE_ID )
            {
            // InternalSpecDSL.g:436:4: (lv_name_0_0= RULE_ID )
            // InternalSpecDSL.g:437:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_9); 

            					newLeafNode(lv_name_0_0, grammarAccess.getDepTagAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDepTagRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalSpecDSL.g:453:3: (otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==17) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSpecDSL.g:454:4: otherlv_1= '=' ( (lv_tagname_2_0= RULE_STRING ) )
                    {
                    otherlv_1=(Token)match(input,17,FOLLOW_10); 

                    				newLeafNode(otherlv_1, grammarAccess.getDepTagAccess().getEqualsSignKeyword_1_0());
                    			
                    // InternalSpecDSL.g:458:4: ( (lv_tagname_2_0= RULE_STRING ) )
                    // InternalSpecDSL.g:459:5: (lv_tagname_2_0= RULE_STRING )
                    {
                    // InternalSpecDSL.g:459:5: (lv_tagname_2_0= RULE_STRING )
                    // InternalSpecDSL.g:460:6: lv_tagname_2_0= RULE_STRING
                    {
                    lv_tagname_2_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_tagname_2_0, grammarAccess.getDepTagAccess().getTagnameSTRINGTerminalRuleCall_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDepTagRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"tagname",
                    							lv_tagname_2_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDepTag"


    // $ANTLR start "entryRuleTreeDef"
    // InternalSpecDSL.g:481:1: entryRuleTreeDef returns [EObject current=null] : iv_ruleTreeDef= ruleTreeDef EOF ;
    public final EObject entryRuleTreeDef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTreeDef = null;


        try {
            // InternalSpecDSL.g:481:48: (iv_ruleTreeDef= ruleTreeDef EOF )
            // InternalSpecDSL.g:482:2: iv_ruleTreeDef= ruleTreeDef EOF
            {
             newCompositeNode(grammarAccess.getTreeDefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTreeDef=ruleTreeDef();

            state._fsp--;

             current =iv_ruleTreeDef; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTreeDef"


    // $ANTLR start "ruleTreeDef"
    // InternalSpecDSL.g:488:1: ruleTreeDef returns [EObject current=null] : ( () otherlv_1= 'def' otherlv_2= 'subtrees' ( (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' ) | ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* ) ) ) ;
    public final EObject ruleTreeDef() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_trees_4_0 = null;

        EObject lv_trees_6_0 = null;

        EObject lv_trees_8_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:494:2: ( ( () otherlv_1= 'def' otherlv_2= 'subtrees' ( (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' ) | ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* ) ) ) )
            // InternalSpecDSL.g:495:2: ( () otherlv_1= 'def' otherlv_2= 'subtrees' ( (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' ) | ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* ) ) )
            {
            // InternalSpecDSL.g:495:2: ( () otherlv_1= 'def' otherlv_2= 'subtrees' ( (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' ) | ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* ) ) )
            // InternalSpecDSL.g:496:3: () otherlv_1= 'def' otherlv_2= 'subtrees' ( (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' ) | ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* ) )
            {
            // InternalSpecDSL.g:496:3: ()
            // InternalSpecDSL.g:497:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getTreeDefAccess().getTreeDefAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,12,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getTreeDefAccess().getDefKeyword_1());
            		
            otherlv_2=(Token)match(input,19,FOLLOW_13); 

            			newLeafNode(otherlv_2, grammarAccess.getTreeDefAccess().getSubtreesKeyword_2());
            		
            // InternalSpecDSL.g:511:3: ( (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' ) | ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==15) ) {
                alt9=1;
            }
            else if ( (LA9_0==RULE_ID) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalSpecDSL.g:512:4: (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' )
                    {
                    // InternalSpecDSL.g:512:4: (otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}' )
                    // InternalSpecDSL.g:513:5: otherlv_3= '{' ( (lv_trees_4_0= ruleTreeTag ) )* otherlv_5= '}'
                    {
                    otherlv_3=(Token)match(input,15,FOLLOW_14); 

                    					newLeafNode(otherlv_3, grammarAccess.getTreeDefAccess().getLeftCurlyBracketKeyword_3_0_0());
                    				
                    // InternalSpecDSL.g:517:5: ( (lv_trees_4_0= ruleTreeTag ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==RULE_ID) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalSpecDSL.g:518:6: (lv_trees_4_0= ruleTreeTag )
                    	    {
                    	    // InternalSpecDSL.g:518:6: (lv_trees_4_0= ruleTreeTag )
                    	    // InternalSpecDSL.g:519:7: lv_trees_4_0= ruleTreeTag
                    	    {

                    	    							newCompositeNode(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_0_1_0());
                    	    						
                    	    pushFollow(FOLLOW_14);
                    	    lv_trees_4_0=ruleTreeTag();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getTreeDefRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"trees",
                    	    								lv_trees_4_0,
                    	    								"com.specmate.cause_effect_patterns.SpecDSL.TreeTag");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,16,FOLLOW_2); 

                    					newLeafNode(otherlv_5, grammarAccess.getTreeDefAccess().getRightCurlyBracketKeyword_3_0_2());
                    				

                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:542:4: ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* )
                    {
                    // InternalSpecDSL.g:542:4: ( ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )* )
                    // InternalSpecDSL.g:543:5: ( (lv_trees_6_0= ruleTreeTag ) ) (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )*
                    {
                    // InternalSpecDSL.g:543:5: ( (lv_trees_6_0= ruleTreeTag ) )
                    // InternalSpecDSL.g:544:6: (lv_trees_6_0= ruleTreeTag )
                    {
                    // InternalSpecDSL.g:544:6: (lv_trees_6_0= ruleTreeTag )
                    // InternalSpecDSL.g:545:7: lv_trees_6_0= ruleTreeTag
                    {

                    							newCompositeNode(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_1_0_0());
                    						
                    pushFollow(FOLLOW_15);
                    lv_trees_6_0=ruleTreeTag();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getTreeDefRule());
                    							}
                    							add(
                    								current,
                    								"trees",
                    								lv_trees_6_0,
                    								"com.specmate.cause_effect_patterns.SpecDSL.TreeTag");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    // InternalSpecDSL.g:562:5: (otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==20) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // InternalSpecDSL.g:563:6: otherlv_7= ',' ( (lv_trees_8_0= ruleTreeTag ) )
                    	    {
                    	    otherlv_7=(Token)match(input,20,FOLLOW_13); 

                    	    						newLeafNode(otherlv_7, grammarAccess.getTreeDefAccess().getCommaKeyword_3_1_1_0());
                    	    					
                    	    // InternalSpecDSL.g:567:6: ( (lv_trees_8_0= ruleTreeTag ) )
                    	    // InternalSpecDSL.g:568:7: (lv_trees_8_0= ruleTreeTag )
                    	    {
                    	    // InternalSpecDSL.g:568:7: (lv_trees_8_0= ruleTreeTag )
                    	    // InternalSpecDSL.g:569:8: lv_trees_8_0= ruleTreeTag
                    	    {

                    	    								newCompositeNode(grammarAccess.getTreeDefAccess().getTreesTreeTagParserRuleCall_3_1_1_1_0());
                    	    							
                    	    pushFollow(FOLLOW_15);
                    	    lv_trees_8_0=ruleTreeTag();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getTreeDefRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"trees",
                    	    									lv_trees_8_0,
                    	    									"com.specmate.cause_effect_patterns.SpecDSL.TreeTag");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTreeDef"


    // $ANTLR start "entryRuleTreeTag"
    // InternalSpecDSL.g:593:1: entryRuleTreeTag returns [EObject current=null] : iv_ruleTreeTag= ruleTreeTag EOF ;
    public final EObject entryRuleTreeTag() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTreeTag = null;


        try {
            // InternalSpecDSL.g:593:48: (iv_ruleTreeTag= ruleTreeTag EOF )
            // InternalSpecDSL.g:594:2: iv_ruleTreeTag= ruleTreeTag EOF
            {
             newCompositeNode(grammarAccess.getTreeTagRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTreeTag=ruleTreeTag();

            state._fsp--;

             current =iv_ruleTreeTag; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTreeTag"


    // $ANTLR start "ruleTreeTag"
    // InternalSpecDSL.g:600:1: ruleTreeTag returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleTreeTag() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalSpecDSL.g:606:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalSpecDSL.g:607:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalSpecDSL.g:607:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalSpecDSL.g:608:3: (lv_name_0_0= RULE_ID )
            {
            // InternalSpecDSL.g:608:3: (lv_name_0_0= RULE_ID )
            // InternalSpecDSL.g:609:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getTreeTagAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getTreeTagRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"org.eclipse.xtext.common.Terminals.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTreeTag"


    // $ANTLR start "entryRuleRule"
    // InternalSpecDSL.g:628:1: entryRuleRule returns [EObject current=null] : iv_ruleRule= ruleRule EOF ;
    public final EObject entryRuleRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRule = null;


        try {
            // InternalSpecDSL.g:628:45: (iv_ruleRule= ruleRule EOF )
            // InternalSpecDSL.g:629:2: iv_ruleRule= ruleRule EOF
            {
             newCompositeNode(grammarAccess.getRuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRule=ruleRule();

            state._fsp--;

             current =iv_ruleRule; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRule"


    // $ANTLR start "ruleRule"
    // InternalSpecDSL.g:635:1: ruleRule returns [EObject current=null] : (otherlv_0= 'def' otherlv_1= 'rule' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '{' ( (lv_dependencies_4_0= ruleDependencyRule ) )+ otherlv_5= '}' ) ;
    public final EObject ruleRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_dependencies_4_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:641:2: ( (otherlv_0= 'def' otherlv_1= 'rule' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '{' ( (lv_dependencies_4_0= ruleDependencyRule ) )+ otherlv_5= '}' ) )
            // InternalSpecDSL.g:642:2: (otherlv_0= 'def' otherlv_1= 'rule' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '{' ( (lv_dependencies_4_0= ruleDependencyRule ) )+ otherlv_5= '}' )
            {
            // InternalSpecDSL.g:642:2: (otherlv_0= 'def' otherlv_1= 'rule' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '{' ( (lv_dependencies_4_0= ruleDependencyRule ) )+ otherlv_5= '}' )
            // InternalSpecDSL.g:643:3: otherlv_0= 'def' otherlv_1= 'rule' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '{' ( (lv_dependencies_4_0= ruleDependencyRule ) )+ otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getRuleAccess().getDefKeyword_0());
            		
            otherlv_1=(Token)match(input,21,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getRuleAccess().getRuleKeyword_1());
            		
            // InternalSpecDSL.g:651:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalSpecDSL.g:652:4: (lv_name_2_0= RULE_ID )
            {
            // InternalSpecDSL.g:652:4: (lv_name_2_0= RULE_ID )
            // InternalSpecDSL.g:653:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(lv_name_2_0, grammarAccess.getRuleAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRuleRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,15,FOLLOW_17); 

            			newLeafNode(otherlv_3, grammarAccess.getRuleAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalSpecDSL.g:673:3: ( (lv_dependencies_4_0= ruleDependencyRule ) )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>=RULE_ID && LA10_0<=RULE_STRING)||LA10_0==24||(LA10_0>=28 && LA10_0<=30)) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSpecDSL.g:674:4: (lv_dependencies_4_0= ruleDependencyRule )
            	    {
            	    // InternalSpecDSL.g:674:4: (lv_dependencies_4_0= ruleDependencyRule )
            	    // InternalSpecDSL.g:675:5: lv_dependencies_4_0= ruleDependencyRule
            	    {

            	    					newCompositeNode(grammarAccess.getRuleAccess().getDependenciesDependencyRuleParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_18);
            	    lv_dependencies_4_0=ruleDependencyRule();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRuleRule());
            	    					}
            	    					add(
            	    						current,
            	    						"dependencies",
            	    						lv_dependencies_4_0,
            	    						"com.specmate.cause_effect_patterns.SpecDSL.DependencyRule");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            otherlv_5=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getRuleAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRule"


    // $ANTLR start "entryRuleDependencyRule"
    // InternalSpecDSL.g:700:1: entryRuleDependencyRule returns [EObject current=null] : iv_ruleDependencyRule= ruleDependencyRule EOF ;
    public final EObject entryRuleDependencyRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDependencyRule = null;


        try {
            // InternalSpecDSL.g:700:55: (iv_ruleDependencyRule= ruleDependencyRule EOF )
            // InternalSpecDSL.g:701:2: iv_ruleDependencyRule= ruleDependencyRule EOF
            {
             newCompositeNode(grammarAccess.getDependencyRuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDependencyRule=ruleDependencyRule();

            state._fsp--;

             current =iv_ruleDependencyRule; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDependencyRule"


    // $ANTLR start "ruleDependencyRule"
    // InternalSpecDSL.g:707:1: ruleDependencyRule returns [EObject current=null] : ( ( ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) ) | ( ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) ) ) ) ;
    public final EObject ruleDependencyRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject lv_leftNode_0_0 = null;

        EObject lv_rightNode_4_1 = null;

        EObject lv_rightNode_4_2 = null;

        EObject lv_leftNode_5_0 = null;

        EObject lv_rightNode_9_1 = null;

        EObject lv_rightNode_9_2 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:713:2: ( ( ( ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) ) | ( ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) ) ) ) )
            // InternalSpecDSL.g:714:2: ( ( ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) ) | ( ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) ) ) )
            {
            // InternalSpecDSL.g:714:2: ( ( ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) ) | ( ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) ) ) )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // InternalSpecDSL.g:715:3: ( ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) )
                    {
                    // InternalSpecDSL.g:715:3: ( ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) )
                    // InternalSpecDSL.g:716:4: ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) )
                    {
                    // InternalSpecDSL.g:716:4: ( (lv_leftNode_0_0= ruleTreeNode ) )
                    // InternalSpecDSL.g:717:5: (lv_leftNode_0_0= ruleTreeNode )
                    {
                    // InternalSpecDSL.g:717:5: (lv_leftNode_0_0= ruleTreeNode )
                    // InternalSpecDSL.g:718:6: lv_leftNode_0_0= ruleTreeNode
                    {

                    						newCompositeNode(grammarAccess.getDependencyRuleAccess().getLeftNodeTreeNodeParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_19);
                    lv_leftNode_0_0=ruleTreeNode();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependencyRuleRule());
                    						}
                    						set(
                    							current,
                    							"leftNode",
                    							lv_leftNode_0_0,
                    							"com.specmate.cause_effect_patterns.SpecDSL.TreeNode");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,22,FOLLOW_4); 

                    				newLeafNode(otherlv_1, grammarAccess.getDependencyRuleAccess().getHyphenMinusKeyword_0_1());
                    			
                    // InternalSpecDSL.g:739:4: ( ( ruleQualifiedName ) )
                    // InternalSpecDSL.g:740:5: ( ruleQualifiedName )
                    {
                    // InternalSpecDSL.g:740:5: ( ruleQualifiedName )
                    // InternalSpecDSL.g:741:6: ruleQualifiedName
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDependencyRuleRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getDependencyRuleAccess().getDTagDepTagCrossReference_0_2_0());
                    					
                    pushFollow(FOLLOW_20);
                    ruleQualifiedName();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,23,FOLLOW_17); 

                    				newLeafNode(otherlv_3, grammarAccess.getDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_0_3());
                    			
                    // InternalSpecDSL.g:759:4: ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) )
                    // InternalSpecDSL.g:760:5: ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) )
                    {
                    // InternalSpecDSL.g:760:5: ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) )
                    // InternalSpecDSL.g:761:6: (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule )
                    {
                    // InternalSpecDSL.g:761:6: (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule )
                    int alt11=2;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // InternalSpecDSL.g:762:7: lv_rightNode_4_1= ruleNode
                            {

                            							newCompositeNode(grammarAccess.getDependencyRuleAccess().getRightNodeNodeParserRuleCall_0_4_0_0());
                            						
                            pushFollow(FOLLOW_2);
                            lv_rightNode_4_1=ruleNode();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getDependencyRuleRule());
                            							}
                            							set(
                            								current,
                            								"rightNode",
                            								lv_rightNode_4_1,
                            								"com.specmate.cause_effect_patterns.SpecDSL.Node");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 2 :
                            // InternalSpecDSL.g:778:7: lv_rightNode_4_2= ruleFreeDependencyRule
                            {

                            							newCompositeNode(grammarAccess.getDependencyRuleAccess().getRightNodeFreeDependencyRuleParserRuleCall_0_4_0_1());
                            						
                            pushFollow(FOLLOW_2);
                            lv_rightNode_4_2=ruleFreeDependencyRule();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getDependencyRuleRule());
                            							}
                            							set(
                            								current,
                            								"rightNode",
                            								lv_rightNode_4_2,
                            								"com.specmate.cause_effect_patterns.SpecDSL.FreeDependencyRule");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;

                    }


                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:798:3: ( ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) ) )
                    {
                    // InternalSpecDSL.g:798:3: ( ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) ) )
                    // InternalSpecDSL.g:799:4: ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) )
                    {
                    // InternalSpecDSL.g:799:4: ( (lv_leftNode_5_0= ruleNonTreeNode ) )
                    // InternalSpecDSL.g:800:5: (lv_leftNode_5_0= ruleNonTreeNode )
                    {
                    // InternalSpecDSL.g:800:5: (lv_leftNode_5_0= ruleNonTreeNode )
                    // InternalSpecDSL.g:801:6: lv_leftNode_5_0= ruleNonTreeNode
                    {

                    						newCompositeNode(grammarAccess.getDependencyRuleAccess().getLeftNodeNonTreeNodeParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_19);
                    lv_leftNode_5_0=ruleNonTreeNode();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependencyRuleRule());
                    						}
                    						set(
                    							current,
                    							"leftNode",
                    							lv_leftNode_5_0,
                    							"com.specmate.cause_effect_patterns.SpecDSL.NonTreeNode");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,22,FOLLOW_4); 

                    				newLeafNode(otherlv_6, grammarAccess.getDependencyRuleAccess().getHyphenMinusKeyword_1_1());
                    			
                    // InternalSpecDSL.g:822:4: ( ( ruleQualifiedName ) )
                    // InternalSpecDSL.g:823:5: ( ruleQualifiedName )
                    {
                    // InternalSpecDSL.g:823:5: ( ruleQualifiedName )
                    // InternalSpecDSL.g:824:6: ruleQualifiedName
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDependencyRuleRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getDependencyRuleAccess().getDTagDepTagCrossReference_1_2_0());
                    					
                    pushFollow(FOLLOW_20);
                    ruleQualifiedName();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_8=(Token)match(input,23,FOLLOW_17); 

                    				newLeafNode(otherlv_8, grammarAccess.getDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_1_3());
                    			
                    // InternalSpecDSL.g:842:4: ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) )
                    // InternalSpecDSL.g:843:5: ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) )
                    {
                    // InternalSpecDSL.g:843:5: ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) )
                    // InternalSpecDSL.g:844:6: (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule )
                    {
                    // InternalSpecDSL.g:844:6: (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule )
                    int alt12=2;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // InternalSpecDSL.g:845:7: lv_rightNode_9_1= ruleTreeNode
                            {

                            							newCompositeNode(grammarAccess.getDependencyRuleAccess().getRightNodeTreeNodeParserRuleCall_1_4_0_0());
                            						
                            pushFollow(FOLLOW_2);
                            lv_rightNode_9_1=ruleTreeNode();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getDependencyRuleRule());
                            							}
                            							set(
                            								current,
                            								"rightNode",
                            								lv_rightNode_9_1,
                            								"com.specmate.cause_effect_patterns.SpecDSL.TreeNode");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 2 :
                            // InternalSpecDSL.g:861:7: lv_rightNode_9_2= ruleDependencyRule
                            {

                            							newCompositeNode(grammarAccess.getDependencyRuleAccess().getRightNodeDependencyRuleParserRuleCall_1_4_0_1());
                            						
                            pushFollow(FOLLOW_2);
                            lv_rightNode_9_2=ruleDependencyRule();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getDependencyRuleRule());
                            							}
                            							set(
                            								current,
                            								"rightNode",
                            								lv_rightNode_9_2,
                            								"com.specmate.cause_effect_patterns.SpecDSL.DependencyRule");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;

                    }


                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDependencyRule"


    // $ANTLR start "entryRuleFreeDependencyRule"
    // InternalSpecDSL.g:884:1: entryRuleFreeDependencyRule returns [EObject current=null] : iv_ruleFreeDependencyRule= ruleFreeDependencyRule EOF ;
    public final EObject entryRuleFreeDependencyRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFreeDependencyRule = null;


        try {
            // InternalSpecDSL.g:884:59: (iv_ruleFreeDependencyRule= ruleFreeDependencyRule EOF )
            // InternalSpecDSL.g:885:2: iv_ruleFreeDependencyRule= ruleFreeDependencyRule EOF
            {
             newCompositeNode(grammarAccess.getFreeDependencyRuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFreeDependencyRule=ruleFreeDependencyRule();

            state._fsp--;

             current =iv_ruleFreeDependencyRule; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFreeDependencyRule"


    // $ANTLR start "ruleFreeDependencyRule"
    // InternalSpecDSL.g:891:1: ruleFreeDependencyRule returns [EObject current=null] : ( ( (lv_leftNode_0_0= ruleNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) ) ;
    public final EObject ruleFreeDependencyRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_leftNode_0_0 = null;

        EObject lv_rightNode_4_1 = null;

        EObject lv_rightNode_4_2 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:897:2: ( ( ( (lv_leftNode_0_0= ruleNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) ) )
            // InternalSpecDSL.g:898:2: ( ( (lv_leftNode_0_0= ruleNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) )
            {
            // InternalSpecDSL.g:898:2: ( ( (lv_leftNode_0_0= ruleNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) )
            // InternalSpecDSL.g:899:3: ( (lv_leftNode_0_0= ruleNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) )
            {
            // InternalSpecDSL.g:899:3: ( (lv_leftNode_0_0= ruleNode ) )
            // InternalSpecDSL.g:900:4: (lv_leftNode_0_0= ruleNode )
            {
            // InternalSpecDSL.g:900:4: (lv_leftNode_0_0= ruleNode )
            // InternalSpecDSL.g:901:5: lv_leftNode_0_0= ruleNode
            {

            					newCompositeNode(grammarAccess.getFreeDependencyRuleAccess().getLeftNodeNodeParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_19);
            lv_leftNode_0_0=ruleNode();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFreeDependencyRuleRule());
            					}
            					set(
            						current,
            						"leftNode",
            						lv_leftNode_0_0,
            						"com.specmate.cause_effect_patterns.SpecDSL.Node");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,22,FOLLOW_4); 

            			newLeafNode(otherlv_1, grammarAccess.getFreeDependencyRuleAccess().getHyphenMinusKeyword_1());
            		
            // InternalSpecDSL.g:922:3: ( ( ruleQualifiedName ) )
            // InternalSpecDSL.g:923:4: ( ruleQualifiedName )
            {
            // InternalSpecDSL.g:923:4: ( ruleQualifiedName )
            // InternalSpecDSL.g:924:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFreeDependencyRuleRule());
            					}
            				

            					newCompositeNode(grammarAccess.getFreeDependencyRuleAccess().getDTagDepTagCrossReference_2_0());
            				
            pushFollow(FOLLOW_20);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,23,FOLLOW_17); 

            			newLeafNode(otherlv_3, grammarAccess.getFreeDependencyRuleAccess().getHyphenMinusGreaterThanSignKeyword_3());
            		
            // InternalSpecDSL.g:942:3: ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) )
            // InternalSpecDSL.g:943:4: ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) )
            {
            // InternalSpecDSL.g:943:4: ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) )
            // InternalSpecDSL.g:944:5: (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule )
            {
            // InternalSpecDSL.g:944:5: (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule )
            int alt14=2;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // InternalSpecDSL.g:945:6: lv_rightNode_4_1= ruleNode
                    {

                    						newCompositeNode(grammarAccess.getFreeDependencyRuleAccess().getRightNodeNodeParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_rightNode_4_1=ruleNode();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFreeDependencyRuleRule());
                    						}
                    						set(
                    							current,
                    							"rightNode",
                    							lv_rightNode_4_1,
                    							"com.specmate.cause_effect_patterns.SpecDSL.Node");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:961:6: lv_rightNode_4_2= ruleFreeDependencyRule
                    {

                    						newCompositeNode(grammarAccess.getFreeDependencyRuleAccess().getRightNodeFreeDependencyRuleParserRuleCall_4_0_1());
                    					
                    pushFollow(FOLLOW_2);
                    lv_rightNode_4_2=ruleFreeDependencyRule();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFreeDependencyRuleRule());
                    						}
                    						set(
                    							current,
                    							"rightNode",
                    							lv_rightNode_4_2,
                    							"com.specmate.cause_effect_patterns.SpecDSL.FreeDependencyRule");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;

            }


            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFreeDependencyRule"


    // $ANTLR start "entryRuleNode"
    // InternalSpecDSL.g:983:1: entryRuleNode returns [EObject current=null] : iv_ruleNode= ruleNode EOF ;
    public final EObject entryRuleNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNode = null;


        try {
            // InternalSpecDSL.g:983:45: (iv_ruleNode= ruleNode EOF )
            // InternalSpecDSL.g:984:2: iv_ruleNode= ruleNode EOF
            {
             newCompositeNode(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNode=ruleNode();

            state._fsp--;

             current =iv_ruleNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // InternalSpecDSL.g:990:1: ruleNode returns [EObject current=null] : (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode | this_TreeNode_2= ruleTreeNode ) ;
    public final EObject ruleNode() throws RecognitionException {
        EObject current = null;

        EObject this_ExplicitNode_0 = null;

        EObject this_OptionNode_1 = null;

        EObject this_TreeNode_2 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:996:2: ( (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode | this_TreeNode_2= ruleTreeNode ) )
            // InternalSpecDSL.g:997:2: (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode | this_TreeNode_2= ruleTreeNode )
            {
            // InternalSpecDSL.g:997:2: (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode | this_TreeNode_2= ruleTreeNode )
            int alt15=3;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // InternalSpecDSL.g:998:3: this_ExplicitNode_0= ruleExplicitNode
                    {

                    			newCompositeNode(grammarAccess.getNodeAccess().getExplicitNodeParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExplicitNode_0=ruleExplicitNode();

                    state._fsp--;


                    			current = this_ExplicitNode_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:1007:3: this_OptionNode_1= ruleOptionNode
                    {

                    			newCompositeNode(grammarAccess.getNodeAccess().getOptionNodeParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_OptionNode_1=ruleOptionNode();

                    state._fsp--;


                    			current = this_OptionNode_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalSpecDSL.g:1016:3: this_TreeNode_2= ruleTreeNode
                    {

                    			newCompositeNode(grammarAccess.getNodeAccess().getTreeNodeParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_TreeNode_2=ruleTreeNode();

                    state._fsp--;


                    			current = this_TreeNode_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRuleNonTreeNode"
    // InternalSpecDSL.g:1028:1: entryRuleNonTreeNode returns [EObject current=null] : iv_ruleNonTreeNode= ruleNonTreeNode EOF ;
    public final EObject entryRuleNonTreeNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNonTreeNode = null;


        try {
            // InternalSpecDSL.g:1028:52: (iv_ruleNonTreeNode= ruleNonTreeNode EOF )
            // InternalSpecDSL.g:1029:2: iv_ruleNonTreeNode= ruleNonTreeNode EOF
            {
             newCompositeNode(grammarAccess.getNonTreeNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNonTreeNode=ruleNonTreeNode();

            state._fsp--;

             current =iv_ruleNonTreeNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNonTreeNode"


    // $ANTLR start "ruleNonTreeNode"
    // InternalSpecDSL.g:1035:1: ruleNonTreeNode returns [EObject current=null] : (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode ) ;
    public final EObject ruleNonTreeNode() throws RecognitionException {
        EObject current = null;

        EObject this_ExplicitNode_0 = null;

        EObject this_OptionNode_1 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:1041:2: ( (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode ) )
            // InternalSpecDSL.g:1042:2: (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode )
            {
            // InternalSpecDSL.g:1042:2: (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=RULE_ID && LA16_0<=RULE_STRING)||(LA16_0>=28 && LA16_0<=29)) ) {
                alt16=1;
            }
            else if ( (LA16_0==24) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalSpecDSL.g:1043:3: this_ExplicitNode_0= ruleExplicitNode
                    {

                    			newCompositeNode(grammarAccess.getNonTreeNodeAccess().getExplicitNodeParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExplicitNode_0=ruleExplicitNode();

                    state._fsp--;


                    			current = this_ExplicitNode_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:1052:3: this_OptionNode_1= ruleOptionNode
                    {

                    			newCompositeNode(grammarAccess.getNonTreeNodeAccess().getOptionNodeParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_OptionNode_1=ruleOptionNode();

                    state._fsp--;


                    			current = this_OptionNode_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNonTreeNode"


    // $ANTLR start "entryRuleOptionNode"
    // InternalSpecDSL.g:1064:1: entryRuleOptionNode returns [EObject current=null] : iv_ruleOptionNode= ruleOptionNode EOF ;
    public final EObject entryRuleOptionNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOptionNode = null;


        try {
            // InternalSpecDSL.g:1064:51: (iv_ruleOptionNode= ruleOptionNode EOF )
            // InternalSpecDSL.g:1065:2: iv_ruleOptionNode= ruleOptionNode EOF
            {
             newCompositeNode(grammarAccess.getOptionNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOptionNode=ruleOptionNode();

            state._fsp--;

             current =iv_ruleOptionNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOptionNode"


    // $ANTLR start "ruleOptionNode"
    // InternalSpecDSL.g:1071:1: ruleOptionNode returns [EObject current=null] : (otherlv_0= '(' ( (lv_leftNode_1_0= ruleExplicitNode ) ) (otherlv_2= '|' ( (lv_rightNodes_3_0= ruleExplicitNode ) ) )+ otherlv_4= ')' ) ;
    public final EObject ruleOptionNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_leftNode_1_0 = null;

        EObject lv_rightNodes_3_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:1077:2: ( (otherlv_0= '(' ( (lv_leftNode_1_0= ruleExplicitNode ) ) (otherlv_2= '|' ( (lv_rightNodes_3_0= ruleExplicitNode ) ) )+ otherlv_4= ')' ) )
            // InternalSpecDSL.g:1078:2: (otherlv_0= '(' ( (lv_leftNode_1_0= ruleExplicitNode ) ) (otherlv_2= '|' ( (lv_rightNodes_3_0= ruleExplicitNode ) ) )+ otherlv_4= ')' )
            {
            // InternalSpecDSL.g:1078:2: (otherlv_0= '(' ( (lv_leftNode_1_0= ruleExplicitNode ) ) (otherlv_2= '|' ( (lv_rightNodes_3_0= ruleExplicitNode ) ) )+ otherlv_4= ')' )
            // InternalSpecDSL.g:1079:3: otherlv_0= '(' ( (lv_leftNode_1_0= ruleExplicitNode ) ) (otherlv_2= '|' ( (lv_rightNodes_3_0= ruleExplicitNode ) ) )+ otherlv_4= ')'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_21); 

            			newLeafNode(otherlv_0, grammarAccess.getOptionNodeAccess().getLeftParenthesisKeyword_0());
            		
            // InternalSpecDSL.g:1083:3: ( (lv_leftNode_1_0= ruleExplicitNode ) )
            // InternalSpecDSL.g:1084:4: (lv_leftNode_1_0= ruleExplicitNode )
            {
            // InternalSpecDSL.g:1084:4: (lv_leftNode_1_0= ruleExplicitNode )
            // InternalSpecDSL.g:1085:5: lv_leftNode_1_0= ruleExplicitNode
            {

            					newCompositeNode(grammarAccess.getOptionNodeAccess().getLeftNodeExplicitNodeParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_22);
            lv_leftNode_1_0=ruleExplicitNode();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOptionNodeRule());
            					}
            					set(
            						current,
            						"leftNode",
            						lv_leftNode_1_0,
            						"com.specmate.cause_effect_patterns.SpecDSL.ExplicitNode");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSpecDSL.g:1102:3: (otherlv_2= '|' ( (lv_rightNodes_3_0= ruleExplicitNode ) ) )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==25) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSpecDSL.g:1103:4: otherlv_2= '|' ( (lv_rightNodes_3_0= ruleExplicitNode ) )
            	    {
            	    otherlv_2=(Token)match(input,25,FOLLOW_21); 

            	    				newLeafNode(otherlv_2, grammarAccess.getOptionNodeAccess().getVerticalLineKeyword_2_0());
            	    			
            	    // InternalSpecDSL.g:1107:4: ( (lv_rightNodes_3_0= ruleExplicitNode ) )
            	    // InternalSpecDSL.g:1108:5: (lv_rightNodes_3_0= ruleExplicitNode )
            	    {
            	    // InternalSpecDSL.g:1108:5: (lv_rightNodes_3_0= ruleExplicitNode )
            	    // InternalSpecDSL.g:1109:6: lv_rightNodes_3_0= ruleExplicitNode
            	    {

            	    						newCompositeNode(grammarAccess.getOptionNodeAccess().getRightNodesExplicitNodeParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_23);
            	    lv_rightNodes_3_0=ruleExplicitNode();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getOptionNodeRule());
            	    						}
            	    						add(
            	    							current,
            	    							"rightNodes",
            	    							lv_rightNodes_3_0,
            	    							"com.specmate.cause_effect_patterns.SpecDSL.ExplicitNode");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);

            otherlv_4=(Token)match(input,26,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getOptionNodeAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOptionNode"


    // $ANTLR start "entryRuleTreeNode"
    // InternalSpecDSL.g:1135:1: entryRuleTreeNode returns [EObject current=null] : iv_ruleTreeNode= ruleTreeNode EOF ;
    public final EObject entryRuleTreeNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTreeNode = null;


        try {
            // InternalSpecDSL.g:1135:49: (iv_ruleTreeNode= ruleTreeNode EOF )
            // InternalSpecDSL.g:1136:2: iv_ruleTreeNode= ruleTreeNode EOF
            {
             newCompositeNode(grammarAccess.getTreeNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTreeNode=ruleTreeNode();

            state._fsp--;

             current =iv_ruleTreeNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTreeNode"


    // $ANTLR start "ruleTreeNode"
    // InternalSpecDSL.g:1142:1: ruleTreeNode returns [EObject current=null] : ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) ) otherlv_4= ':' )? ( (lv_tree_5_0= ruleSubtree ) ) ) ;
    public final EObject ruleTreeNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_expr_2_0=null;
        Token lv_anyMatch_3_0=null;
        Token otherlv_4=null;
        EObject lv_tree_5_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:1148:2: ( ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) ) otherlv_4= ':' )? ( (lv_tree_5_0= ruleSubtree ) ) ) )
            // InternalSpecDSL.g:1149:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) ) otherlv_4= ':' )? ( (lv_tree_5_0= ruleSubtree ) ) )
            {
            // InternalSpecDSL.g:1149:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) ) otherlv_4= ':' )? ( (lv_tree_5_0= ruleSubtree ) ) )
            // InternalSpecDSL.g:1150:3: ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) ) otherlv_4= ':' )? ( (lv_tree_5_0= ruleSubtree ) )
            {
            // InternalSpecDSL.g:1150:3: ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_ID) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalSpecDSL.g:1151:4: ( ( ruleQualifiedName ) ) otherlv_1= ':'
                    {
                    // InternalSpecDSL.g:1151:4: ( ( ruleQualifiedName ) )
                    // InternalSpecDSL.g:1152:5: ( ruleQualifiedName )
                    {
                    // InternalSpecDSL.g:1152:5: ( ruleQualifiedName )
                    // InternalSpecDSL.g:1153:6: ruleQualifiedName
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTreeNodeRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getTreeNodeAccess().getPTagPOSTagCrossReference_0_0_0());
                    					
                    pushFollow(FOLLOW_24);
                    ruleQualifiedName();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,27,FOLLOW_25); 

                    				newLeafNode(otherlv_1, grammarAccess.getTreeNodeAccess().getColonKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalSpecDSL.g:1172:3: ( ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) ) otherlv_4= ':' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_STRING||LA20_0==28) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalSpecDSL.g:1173:4: ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) ) otherlv_4= ':'
                    {
                    // InternalSpecDSL.g:1173:4: ( ( (lv_expr_2_0= RULE_STRING ) ) | ( (lv_anyMatch_3_0= '*' ) ) )
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==RULE_STRING) ) {
                        alt19=1;
                    }
                    else if ( (LA19_0==28) ) {
                        alt19=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 0, input);

                        throw nvae;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalSpecDSL.g:1174:5: ( (lv_expr_2_0= RULE_STRING ) )
                            {
                            // InternalSpecDSL.g:1174:5: ( (lv_expr_2_0= RULE_STRING ) )
                            // InternalSpecDSL.g:1175:6: (lv_expr_2_0= RULE_STRING )
                            {
                            // InternalSpecDSL.g:1175:6: (lv_expr_2_0= RULE_STRING )
                            // InternalSpecDSL.g:1176:7: lv_expr_2_0= RULE_STRING
                            {
                            lv_expr_2_0=(Token)match(input,RULE_STRING,FOLLOW_24); 

                            							newLeafNode(lv_expr_2_0, grammarAccess.getTreeNodeAccess().getExprSTRINGTerminalRuleCall_1_0_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getTreeNodeRule());
                            							}
                            							setWithLastConsumed(
                            								current,
                            								"expr",
                            								lv_expr_2_0,
                            								"org.eclipse.xtext.common.Terminals.STRING");
                            						

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalSpecDSL.g:1193:5: ( (lv_anyMatch_3_0= '*' ) )
                            {
                            // InternalSpecDSL.g:1193:5: ( (lv_anyMatch_3_0= '*' ) )
                            // InternalSpecDSL.g:1194:6: (lv_anyMatch_3_0= '*' )
                            {
                            // InternalSpecDSL.g:1194:6: (lv_anyMatch_3_0= '*' )
                            // InternalSpecDSL.g:1195:7: lv_anyMatch_3_0= '*'
                            {
                            lv_anyMatch_3_0=(Token)match(input,28,FOLLOW_24); 

                            							newLeafNode(lv_anyMatch_3_0, grammarAccess.getTreeNodeAccess().getAnyMatchAsteriskKeyword_1_0_1_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getTreeNodeRule());
                            							}
                            							setWithLastConsumed(current, "anyMatch", true, "*");
                            						

                            }


                            }


                            }
                            break;

                    }

                    otherlv_4=(Token)match(input,27,FOLLOW_25); 

                    				newLeafNode(otherlv_4, grammarAccess.getTreeNodeAccess().getColonKeyword_1_1());
                    			

                    }
                    break;

            }

            // InternalSpecDSL.g:1213:3: ( (lv_tree_5_0= ruleSubtree ) )
            // InternalSpecDSL.g:1214:4: (lv_tree_5_0= ruleSubtree )
            {
            // InternalSpecDSL.g:1214:4: (lv_tree_5_0= ruleSubtree )
            // InternalSpecDSL.g:1215:5: lv_tree_5_0= ruleSubtree
            {

            					newCompositeNode(grammarAccess.getTreeNodeAccess().getTreeSubtreeParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_tree_5_0=ruleSubtree();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTreeNodeRule());
            					}
            					set(
            						current,
            						"tree",
            						lv_tree_5_0,
            						"com.specmate.cause_effect_patterns.SpecDSL.Subtree");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTreeNode"


    // $ANTLR start "entryRuleExplicitNode"
    // InternalSpecDSL.g:1236:1: entryRuleExplicitNode returns [EObject current=null] : iv_ruleExplicitNode= ruleExplicitNode EOF ;
    public final EObject entryRuleExplicitNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExplicitNode = null;


        try {
            // InternalSpecDSL.g:1236:53: (iv_ruleExplicitNode= ruleExplicitNode EOF )
            // InternalSpecDSL.g:1237:2: iv_ruleExplicitNode= ruleExplicitNode EOF
            {
             newCompositeNode(grammarAccess.getExplicitNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExplicitNode=ruleExplicitNode();

            state._fsp--;

             current =iv_ruleExplicitNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExplicitNode"


    // $ANTLR start "ruleExplicitNode"
    // InternalSpecDSL.g:1243:1: ruleExplicitNode returns [EObject current=null] : ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) ) | ( (lv_anyMatch_4_0= '*' ) ) ) ) ;
    public final EObject ruleExplicitNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_caseSensitive_2_0=null;
        Token lv_expr_3_0=null;
        Token lv_anyMatch_4_0=null;


        	enterRule();

        try {
            // InternalSpecDSL.g:1249:2: ( ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) ) | ( (lv_anyMatch_4_0= '*' ) ) ) ) )
            // InternalSpecDSL.g:1250:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) ) | ( (lv_anyMatch_4_0= '*' ) ) ) )
            {
            // InternalSpecDSL.g:1250:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) ) | ( (lv_anyMatch_4_0= '*' ) ) ) )
            // InternalSpecDSL.g:1251:3: ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )? ( ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) ) | ( (lv_anyMatch_4_0= '*' ) ) )
            {
            // InternalSpecDSL.g:1251:3: ( ( ( ruleQualifiedName ) ) otherlv_1= ':' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_ID) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSpecDSL.g:1252:4: ( ( ruleQualifiedName ) ) otherlv_1= ':'
                    {
                    // InternalSpecDSL.g:1252:4: ( ( ruleQualifiedName ) )
                    // InternalSpecDSL.g:1253:5: ( ruleQualifiedName )
                    {
                    // InternalSpecDSL.g:1253:5: ( ruleQualifiedName )
                    // InternalSpecDSL.g:1254:6: ruleQualifiedName
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getExplicitNodeRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getExplicitNodeAccess().getPTagPOSTagCrossReference_0_0_0());
                    					
                    pushFollow(FOLLOW_24);
                    ruleQualifiedName();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,27,FOLLOW_26); 

                    				newLeafNode(otherlv_1, grammarAccess.getExplicitNodeAccess().getColonKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalSpecDSL.g:1273:3: ( ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) ) | ( (lv_anyMatch_4_0= '*' ) ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_STRING||LA23_0==29) ) {
                alt23=1;
            }
            else if ( (LA23_0==28) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // InternalSpecDSL.g:1274:4: ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) )
                    {
                    // InternalSpecDSL.g:1274:4: ( ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) ) )
                    // InternalSpecDSL.g:1275:5: ( (lv_caseSensitive_2_0= 'CASE!' ) )? ( (lv_expr_3_0= RULE_STRING ) )
                    {
                    // InternalSpecDSL.g:1275:5: ( (lv_caseSensitive_2_0= 'CASE!' ) )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==29) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalSpecDSL.g:1276:6: (lv_caseSensitive_2_0= 'CASE!' )
                            {
                            // InternalSpecDSL.g:1276:6: (lv_caseSensitive_2_0= 'CASE!' )
                            // InternalSpecDSL.g:1277:7: lv_caseSensitive_2_0= 'CASE!'
                            {
                            lv_caseSensitive_2_0=(Token)match(input,29,FOLLOW_10); 

                            							newLeafNode(lv_caseSensitive_2_0, grammarAccess.getExplicitNodeAccess().getCaseSensitiveCASEKeyword_1_0_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getExplicitNodeRule());
                            							}
                            							setWithLastConsumed(current, "caseSensitive", true, "CASE!");
                            						

                            }


                            }
                            break;

                    }

                    // InternalSpecDSL.g:1289:5: ( (lv_expr_3_0= RULE_STRING ) )
                    // InternalSpecDSL.g:1290:6: (lv_expr_3_0= RULE_STRING )
                    {
                    // InternalSpecDSL.g:1290:6: (lv_expr_3_0= RULE_STRING )
                    // InternalSpecDSL.g:1291:7: lv_expr_3_0= RULE_STRING
                    {
                    lv_expr_3_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    							newLeafNode(lv_expr_3_0, grammarAccess.getExplicitNodeAccess().getExprSTRINGTerminalRuleCall_1_0_1_0());
                    						

                    							if (current==null) {
                    								current = createModelElement(grammarAccess.getExplicitNodeRule());
                    							}
                    							setWithLastConsumed(
                    								current,
                    								"expr",
                    								lv_expr_3_0,
                    								"org.eclipse.xtext.common.Terminals.STRING");
                    						

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSpecDSL.g:1309:4: ( (lv_anyMatch_4_0= '*' ) )
                    {
                    // InternalSpecDSL.g:1309:4: ( (lv_anyMatch_4_0= '*' ) )
                    // InternalSpecDSL.g:1310:5: (lv_anyMatch_4_0= '*' )
                    {
                    // InternalSpecDSL.g:1310:5: (lv_anyMatch_4_0= '*' )
                    // InternalSpecDSL.g:1311:6: lv_anyMatch_4_0= '*'
                    {
                    lv_anyMatch_4_0=(Token)match(input,28,FOLLOW_2); 

                    						newLeafNode(lv_anyMatch_4_0, grammarAccess.getExplicitNodeAccess().getAnyMatchAsteriskKeyword_1_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getExplicitNodeRule());
                    						}
                    						setWithLastConsumed(current, "anyMatch", true, "*");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExplicitNode"


    // $ANTLR start "entryRuleSubtree"
    // InternalSpecDSL.g:1328:1: entryRuleSubtree returns [EObject current=null] : iv_ruleSubtree= ruleSubtree EOF ;
    public final EObject entryRuleSubtree() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSubtree = null;


        try {
            // InternalSpecDSL.g:1328:48: (iv_ruleSubtree= ruleSubtree EOF )
            // InternalSpecDSL.g:1329:2: iv_ruleSubtree= ruleSubtree EOF
            {
             newCompositeNode(grammarAccess.getSubtreeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSubtree=ruleSubtree();

            state._fsp--;

             current =iv_ruleSubtree; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSubtree"


    // $ANTLR start "ruleSubtree"
    // InternalSpecDSL.g:1335:1: ruleSubtree returns [EObject current=null] : (otherlv_0= '[' ( (otherlv_1= RULE_ID ) ) otherlv_2= ']' ) ;
    public final EObject ruleSubtree() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSpecDSL.g:1341:2: ( (otherlv_0= '[' ( (otherlv_1= RULE_ID ) ) otherlv_2= ']' ) )
            // InternalSpecDSL.g:1342:2: (otherlv_0= '[' ( (otherlv_1= RULE_ID ) ) otherlv_2= ']' )
            {
            // InternalSpecDSL.g:1342:2: (otherlv_0= '[' ( (otherlv_1= RULE_ID ) ) otherlv_2= ']' )
            // InternalSpecDSL.g:1343:3: otherlv_0= '[' ( (otherlv_1= RULE_ID ) ) otherlv_2= ']'
            {
            otherlv_0=(Token)match(input,30,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getSubtreeAccess().getLeftSquareBracketKeyword_0());
            		
            // InternalSpecDSL.g:1347:3: ( (otherlv_1= RULE_ID ) )
            // InternalSpecDSL.g:1348:4: (otherlv_1= RULE_ID )
            {
            // InternalSpecDSL.g:1348:4: (otherlv_1= RULE_ID )
            // InternalSpecDSL.g:1349:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSubtreeRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_27); 

            					newLeafNode(otherlv_1, grammarAccess.getSubtreeAccess().getNameTreeTagCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,31,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getSubtreeAccess().getRightSquareBracketKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSubtree"


    // $ANTLR start "entryRuleQualifiedName"
    // InternalSpecDSL.g:1368:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalSpecDSL.g:1368:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalSpecDSL.g:1369:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;

             current =iv_ruleQualifiedName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalSpecDSL.g:1375:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalSpecDSL.g:1381:2: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // InternalSpecDSL.g:1382:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // InternalSpecDSL.g:1382:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // InternalSpecDSL.g:1383:3: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_28); 

            			current.merge(this_ID_0);
            		

            			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
            		
            // InternalSpecDSL.g:1390:3: (kw= '.' this_ID_2= RULE_ID )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==32) ) {
                    int LA24_2 = input.LA(2);

                    if ( (LA24_2==RULE_ID) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
            	case 1 :
            	    // InternalSpecDSL.g:1391:4: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,32,FOLLOW_4); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0());
            	    			
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_28); 

            	    				current.merge(this_ID_2);
            	    			

            	    				newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleQualifiedNameWithWildcard"
    // InternalSpecDSL.g:1408:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // InternalSpecDSL.g:1408:65: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // InternalSpecDSL.g:1409:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard();

            state._fsp--;

             current =iv_ruleQualifiedNameWithWildcard.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedNameWithWildcard"


    // $ANTLR start "ruleQualifiedNameWithWildcard"
    // InternalSpecDSL.g:1415:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= '.' kw= '*' )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalSpecDSL.g:1421:2: ( (this_QualifiedName_0= ruleQualifiedName (kw= '.' kw= '*' )? ) )
            // InternalSpecDSL.g:1422:2: (this_QualifiedName_0= ruleQualifiedName (kw= '.' kw= '*' )? )
            {
            // InternalSpecDSL.g:1422:2: (this_QualifiedName_0= ruleQualifiedName (kw= '.' kw= '*' )? )
            // InternalSpecDSL.g:1423:3: this_QualifiedName_0= ruleQualifiedName (kw= '.' kw= '*' )?
            {

            			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_28);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;


            			current.merge(this_QualifiedName_0);
            		

            			afterParserOrEnumRuleCall();
            		
            // InternalSpecDSL.g:1433:3: (kw= '.' kw= '*' )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==32) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalSpecDSL.g:1434:4: kw= '.' kw= '*'
                    {
                    kw=(Token)match(input,32,FOLLOW_29); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopKeyword_1_0());
                    			
                    kw=(Token)match(input,28,FOLLOW_2); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getAsteriskKeyword_1_1());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedNameWithWildcard"

    // Delegated rules


    protected DFA13 dfa13 = new DFA13(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA12 dfa12 = new DFA12(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA15 dfa15 = new DFA15(this);
    static final String dfa_1s = "\11\uffff";
    static final String dfa_2s = "\1\4\1\33\2\26\2\uffff\1\4\1\5\1\33";
    static final String dfa_3s = "\1\36\1\40\2\33\2\uffff\1\4\1\36\1\40";
    static final String dfa_4s = "\4\uffff\1\1\1\2\3\uffff";
    static final String dfa_5s = "\11\uffff}>";
    static final String[] dfa_6s = {
            "\1\1\1\2\22\uffff\1\5\3\uffff\1\3\1\5\1\4",
            "\1\7\4\uffff\1\6",
            "\1\5\4\uffff\1\4",
            "\1\5\4\uffff\1\4",
            "",
            "",
            "\1\10",
            "\1\2\26\uffff\1\3\1\5\1\4",
            "\1\7\4\uffff\1\6"
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "714:2: ( ( ( (lv_leftNode_0_0= ruleTreeNode ) ) otherlv_1= '-' ( ( ruleQualifiedName ) ) otherlv_3= '->' ( ( (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule ) ) ) ) | ( ( (lv_leftNode_5_0= ruleNonTreeNode ) ) otherlv_6= '-' ( ( ruleQualifiedName ) ) otherlv_8= '->' ( ( (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule ) ) ) ) )";
        }
    }
    static final String dfa_7s = "\40\uffff";
    static final String dfa_8s = "\3\uffff\2\13\4\uffff\1\13\14\uffff\1\13\7\uffff\1\13\1\uffff";
    static final String dfa_9s = "\1\4\1\33\1\5\5\4\1\5\1\4\2\uffff\1\36\1\33\1\5\2\31\1\37\1\33\1\4\1\5\2\4\2\33\1\5\2\31\1\4\1\5\1\4\1\33";
    static final String dfa_10s = "\1\36\1\40\1\5\2\36\1\35\2\4\2\36\2\uffff\1\36\1\40\1\5\2\31\1\37\1\40\1\4\2\35\1\36\2\40\1\5\2\32\1\4\1\35\1\36\1\40";
    static final String dfa_11s = "\12\uffff\1\2\1\1\24\uffff";
    static final String dfa_12s = "\40\uffff}>";
    static final String[] dfa_13s = {
            "\1\1\1\3\22\uffff\1\5\3\uffff\1\4\1\2\1\6",
            "\1\10\4\uffff\1\7",
            "\1\11",
            "\2\13\12\uffff\1\13\5\uffff\1\12\1\uffff\1\13\2\uffff\1\14\3\13",
            "\2\13\12\uffff\1\13\5\uffff\1\12\1\uffff\1\13\2\uffff\1\14\3\13",
            "\1\15\1\17\26\uffff\1\20\1\16",
            "\1\21",
            "\1\22",
            "\1\3\26\uffff\1\4\1\2\1\6",
            "\2\13\12\uffff\1\13\5\uffff\1\12\1\uffff\1\13\3\uffff\3\13",
            "",
            "",
            "\1\6",
            "\1\24\4\uffff\1\23",
            "\1\17",
            "\1\25",
            "\1\25",
            "\1\26",
            "\1\10\4\uffff\1\7",
            "\1\27",
            "\1\17\26\uffff\1\20\1\16",
            "\1\30\1\32\26\uffff\1\33\1\31",
            "\2\13\12\uffff\1\13\5\uffff\1\12\1\uffff\1\13\3\uffff\3\13",
            "\1\24\4\uffff\1\23",
            "\1\35\4\uffff\1\34",
            "\1\32",
            "\1\25\1\36",
            "\1\25\1\36",
            "\1\37",
            "\1\32\26\uffff\1\33\1\31",
            "\2\13\12\uffff\1\13\5\uffff\1\12\1\uffff\1\13\3\uffff\3\13",
            "\1\35\4\uffff\1\34"
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "761:6: (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule )";
        }
    }
    static final String dfa_14s = "\15\uffff";
    static final String dfa_15s = "\13\uffff\1\14\1\uffff";
    static final String dfa_16s = "\1\4\1\33\2\26\1\4\1\uffff\1\4\1\5\1\36\1\37\1\33\1\4\1\uffff";
    static final String dfa_17s = "\1\36\1\40\2\33\1\4\1\uffff\1\4\2\36\1\37\1\40\1\36\1\uffff";
    static final String dfa_18s = "\5\uffff\1\2\6\uffff\1\1";
    static final String dfa_19s = "\15\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\1\2\22\uffff\1\5\3\uffff\1\3\1\5\1\4",
            "\1\7\4\uffff\1\6",
            "\1\5\4\uffff\1\10",
            "\1\5\4\uffff\1\10",
            "\1\11",
            "",
            "\1\12",
            "\1\2\26\uffff\1\3\1\5\1\4",
            "\1\4",
            "\1\13",
            "\1\7\4\uffff\1\6",
            "\2\14\12\uffff\1\14\5\uffff\1\5\1\uffff\1\14\3\uffff\3\14",
            ""
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "844:6: (lv_rightNode_9_1= ruleTreeNode | lv_rightNode_9_2= ruleDependencyRule )";
        }
    }
    static final String dfa_21s = "\3\uffff\2\12\4\uffff\1\12\14\uffff\1\12\7\uffff\1\12\1\uffff";
    static final String dfa_22s = "\12\uffff\1\1\1\2\24\uffff";
    static final String[] dfa_23s = {
            "\1\1\1\3\22\uffff\1\5\3\uffff\1\4\1\2\1\6",
            "\1\10\4\uffff\1\7",
            "\1\11",
            "\2\12\12\uffff\1\12\5\uffff\1\13\1\uffff\1\12\2\uffff\1\14\3\12",
            "\2\12\12\uffff\1\12\5\uffff\1\13\1\uffff\1\12\2\uffff\1\14\3\12",
            "\1\15\1\17\26\uffff\1\20\1\16",
            "\1\21",
            "\1\22",
            "\1\3\26\uffff\1\4\1\2\1\6",
            "\2\12\12\uffff\1\12\5\uffff\1\13\1\uffff\1\12\3\uffff\3\12",
            "",
            "",
            "\1\6",
            "\1\24\4\uffff\1\23",
            "\1\17",
            "\1\25",
            "\1\25",
            "\1\26",
            "\1\10\4\uffff\1\7",
            "\1\27",
            "\1\17\26\uffff\1\20\1\16",
            "\1\30\1\32\26\uffff\1\33\1\31",
            "\2\12\12\uffff\1\12\5\uffff\1\13\1\uffff\1\12\3\uffff\3\12",
            "\1\24\4\uffff\1\23",
            "\1\35\4\uffff\1\34",
            "\1\32",
            "\1\25\1\36",
            "\1\25\1\36",
            "\1\37",
            "\1\32\26\uffff\1\33\1\31",
            "\2\12\12\uffff\1\12\5\uffff\1\13\1\uffff\1\12\3\uffff\3\12",
            "\1\35\4\uffff\1\34"
    };
    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[][] dfa_23 = unpackEncodedStringArray(dfa_23s);

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_7;
            this.eof = dfa_21;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_22;
            this.special = dfa_12;
            this.transition = dfa_23;
        }
        public String getDescription() {
            return "944:5: (lv_rightNode_4_1= ruleNode | lv_rightNode_4_2= ruleFreeDependencyRule )";
        }
    }
    static final String dfa_24s = "\12\uffff";
    static final String dfa_25s = "\3\uffff\2\2\5\uffff";
    static final String dfa_26s = "\1\4\1\33\1\uffff\2\4\2\uffff\1\4\1\5\1\33";
    static final String dfa_27s = "\1\36\1\40\1\uffff\2\36\2\uffff\1\4\1\36\1\40";
    static final String dfa_28s = "\2\uffff\1\1\2\uffff\1\2\1\3\3\uffff";
    static final String dfa_29s = "\12\uffff}>";
    static final String[] dfa_30s = {
            "\1\1\1\3\22\uffff\1\5\3\uffff\1\4\1\2\1\6",
            "\1\10\4\uffff\1\7",
            "",
            "\2\2\12\uffff\1\2\5\uffff\1\2\1\uffff\1\2\2\uffff\1\6\3\2",
            "\2\2\12\uffff\1\2\5\uffff\1\2\1\uffff\1\2\2\uffff\1\6\3\2",
            "",
            "",
            "\1\11",
            "\1\3\26\uffff\1\4\1\2\1\6",
            "\1\10\4\uffff\1\7"
    };

    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final char[] dfa_26 = DFA.unpackEncodedStringToUnsignedChars(dfa_26s);
    static final char[] dfa_27 = DFA.unpackEncodedStringToUnsignedChars(dfa_27s);
    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[][] dfa_30 = unpackEncodedStringArray(dfa_30s);

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = dfa_24;
            this.eof = dfa_25;
            this.min = dfa_26;
            this.max = dfa_27;
            this.accept = dfa_28;
            this.special = dfa_29;
            this.transition = dfa_30;
        }
        public String getDescription() {
            return "997:2: (this_ExplicitNode_0= ruleExplicitNode | this_OptionNode_1= ruleOptionNode | this_TreeNode_2= ruleTreeNode )";
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
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000008010L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000018010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000071000030L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000071010030L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000030000030L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000006000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000050000030L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000030000020L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000010000000L});

}