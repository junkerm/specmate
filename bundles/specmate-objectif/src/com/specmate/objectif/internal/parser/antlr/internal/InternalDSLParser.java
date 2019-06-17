package com.specmate.objectif.internal.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.specmate.objectif.internal.services.DSLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDSLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_CHARACTER_SQUENCE", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'WENN'", "'DANN'", "'SONST'", "'ENDE-WENN'", "'ODER'", "'UND'"
    };
    public static final int RULE_ID=5;
    public static final int RULE_WS=10;
    public static final int RULE_STRING=7;
    public static final int RULE_ANY_OTHER=11;
    public static final int RULE_SL_COMMENT=9;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int RULE_INT=6;
    public static final int RULE_ML_COMMENT=8;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int RULE_CHARACTER_SQUENCE=4;

    // delegates
    // delegators


        public InternalDSLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalDSLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalDSLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalDSL.g"; }



     	private DSLGrammarAccess grammarAccess;

        public InternalDSLParser(TokenStream input, DSLGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected DSLGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalDSL.g:64:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalDSL.g:64:46: (iv_ruleModel= ruleModel EOF )
            // InternalDSL.g:65:2: iv_ruleModel= ruleModel EOF
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
    // InternalDSL.g:71:1: ruleModel returns [EObject current=null] : ( (lv_rules_0_0= ruleBusinessRule ) )* ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_rules_0_0 = null;



        	enterRule();

        try {
            // InternalDSL.g:77:2: ( ( (lv_rules_0_0= ruleBusinessRule ) )* )
            // InternalDSL.g:78:2: ( (lv_rules_0_0= ruleBusinessRule ) )*
            {
            // InternalDSL.g:78:2: ( (lv_rules_0_0= ruleBusinessRule ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalDSL.g:79:3: (lv_rules_0_0= ruleBusinessRule )
            	    {
            	    // InternalDSL.g:79:3: (lv_rules_0_0= ruleBusinessRule )
            	    // InternalDSL.g:80:4: lv_rules_0_0= ruleBusinessRule
            	    {

            	    				newCompositeNode(grammarAccess.getModelAccess().getRulesBusinessRuleParserRuleCall_0());
            	    			
            	    pushFollow(FOLLOW_3);
            	    lv_rules_0_0=ruleBusinessRule();

            	    state._fsp--;


            	    				if (current==null) {
            	    					current = createModelElementForParent(grammarAccess.getModelRule());
            	    				}
            	    				add(
            	    					current,
            	    					"rules",
            	    					lv_rules_0_0,
            	    					"com.specmate.objectif.DSL.BusinessRule");
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


    // $ANTLR start "entryRuleBusinessRule"
    // InternalDSL.g:100:1: entryRuleBusinessRule returns [EObject current=null] : iv_ruleBusinessRule= ruleBusinessRule EOF ;
    public final EObject entryRuleBusinessRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBusinessRule = null;


        try {
            // InternalDSL.g:100:53: (iv_ruleBusinessRule= ruleBusinessRule EOF )
            // InternalDSL.g:101:2: iv_ruleBusinessRule= ruleBusinessRule EOF
            {
             newCompositeNode(grammarAccess.getBusinessRuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBusinessRule=ruleBusinessRule();

            state._fsp--;

             current =iv_ruleBusinessRule; 
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
    // $ANTLR end "entryRuleBusinessRule"


    // $ANTLR start "ruleBusinessRule"
    // InternalDSL.g:107:1: ruleBusinessRule returns [EObject current=null] : (otherlv_0= 'WENN' ( (lv_cause_1_0= ruleOR_Node ) ) otherlv_2= 'DANN' ( ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) ) ) (otherlv_4= 'SONST' ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) ) )? otherlv_6= 'ENDE-WENN' ) ;
    public final EObject ruleBusinessRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_cause_1_0 = null;

        EObject lv_effect_3_1 = null;

        EObject lv_effect_3_2 = null;

        EObject lv_alternative_5_1 = null;

        EObject lv_alternative_5_2 = null;



        	enterRule();

        try {
            // InternalDSL.g:113:2: ( (otherlv_0= 'WENN' ( (lv_cause_1_0= ruleOR_Node ) ) otherlv_2= 'DANN' ( ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) ) ) (otherlv_4= 'SONST' ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) ) )? otherlv_6= 'ENDE-WENN' ) )
            // InternalDSL.g:114:2: (otherlv_0= 'WENN' ( (lv_cause_1_0= ruleOR_Node ) ) otherlv_2= 'DANN' ( ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) ) ) (otherlv_4= 'SONST' ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) ) )? otherlv_6= 'ENDE-WENN' )
            {
            // InternalDSL.g:114:2: (otherlv_0= 'WENN' ( (lv_cause_1_0= ruleOR_Node ) ) otherlv_2= 'DANN' ( ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) ) ) (otherlv_4= 'SONST' ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) ) )? otherlv_6= 'ENDE-WENN' )
            // InternalDSL.g:115:3: otherlv_0= 'WENN' ( (lv_cause_1_0= ruleOR_Node ) ) otherlv_2= 'DANN' ( ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) ) ) (otherlv_4= 'SONST' ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) ) )? otherlv_6= 'ENDE-WENN'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getBusinessRuleAccess().getWENNKeyword_0());
            		
            // InternalDSL.g:119:3: ( (lv_cause_1_0= ruleOR_Node ) )
            // InternalDSL.g:120:4: (lv_cause_1_0= ruleOR_Node )
            {
            // InternalDSL.g:120:4: (lv_cause_1_0= ruleOR_Node )
            // InternalDSL.g:121:5: lv_cause_1_0= ruleOR_Node
            {

            					newCompositeNode(grammarAccess.getBusinessRuleAccess().getCauseOR_NodeParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_5);
            lv_cause_1_0=ruleOR_Node();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getBusinessRuleRule());
            					}
            					set(
            						current,
            						"cause",
            						lv_cause_1_0,
            						"com.specmate.objectif.DSL.OR_Node");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getBusinessRuleAccess().getDANNKeyword_2());
            		
            // InternalDSL.g:142:3: ( ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) ) )
            // InternalDSL.g:143:4: ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) )
            {
            // InternalDSL.g:143:4: ( (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node ) )
            // InternalDSL.g:144:5: (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node )
            {
            // InternalDSL.g:144:5: (lv_effect_3_1= ruleBusinessRule | lv_effect_3_2= ruleOR_Node )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==12) ) {
                alt2=1;
            }
            else if ( (LA2_0==RULE_CHARACTER_SQUENCE) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalDSL.g:145:6: lv_effect_3_1= ruleBusinessRule
                    {

                    						newCompositeNode(grammarAccess.getBusinessRuleAccess().getEffectBusinessRuleParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_7);
                    lv_effect_3_1=ruleBusinessRule();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getBusinessRuleRule());
                    						}
                    						set(
                    							current,
                    							"effect",
                    							lv_effect_3_1,
                    							"com.specmate.objectif.DSL.BusinessRule");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;
                case 2 :
                    // InternalDSL.g:161:6: lv_effect_3_2= ruleOR_Node
                    {

                    						newCompositeNode(grammarAccess.getBusinessRuleAccess().getEffectOR_NodeParserRuleCall_3_0_1());
                    					
                    pushFollow(FOLLOW_7);
                    lv_effect_3_2=ruleOR_Node();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getBusinessRuleRule());
                    						}
                    						set(
                    							current,
                    							"effect",
                    							lv_effect_3_2,
                    							"com.specmate.objectif.DSL.OR_Node");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;

            }


            }


            }

            // InternalDSL.g:179:3: (otherlv_4= 'SONST' ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==14) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalDSL.g:180:4: otherlv_4= 'SONST' ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) )
                    {
                    otherlv_4=(Token)match(input,14,FOLLOW_8); 

                    				newLeafNode(otherlv_4, grammarAccess.getBusinessRuleAccess().getSONSTKeyword_4_0());
                    			
                    // InternalDSL.g:184:4: ( ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) ) )
                    // InternalDSL.g:185:5: ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) )
                    {
                    // InternalDSL.g:185:5: ( (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node ) )
                    // InternalDSL.g:186:6: (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node )
                    {
                    // InternalDSL.g:186:6: (lv_alternative_5_1= ruleBusinessRule | lv_alternative_5_2= ruleOR_Node )
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==12) ) {
                        alt3=1;
                    }
                    else if ( (LA3_0==RULE_CHARACTER_SQUENCE) ) {
                        alt3=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 0, input);

                        throw nvae;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalDSL.g:187:7: lv_alternative_5_1= ruleBusinessRule
                            {

                            							newCompositeNode(grammarAccess.getBusinessRuleAccess().getAlternativeBusinessRuleParserRuleCall_4_1_0_0());
                            						
                            pushFollow(FOLLOW_9);
                            lv_alternative_5_1=ruleBusinessRule();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getBusinessRuleRule());
                            							}
                            							set(
                            								current,
                            								"alternative",
                            								lv_alternative_5_1,
                            								"com.specmate.objectif.DSL.BusinessRule");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 2 :
                            // InternalDSL.g:203:7: lv_alternative_5_2= ruleOR_Node
                            {

                            							newCompositeNode(grammarAccess.getBusinessRuleAccess().getAlternativeOR_NodeParserRuleCall_4_1_0_1());
                            						
                            pushFollow(FOLLOW_9);
                            lv_alternative_5_2=ruleOR_Node();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getBusinessRuleRule());
                            							}
                            							set(
                            								current,
                            								"alternative",
                            								lv_alternative_5_2,
                            								"com.specmate.objectif.DSL.OR_Node");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getBusinessRuleAccess().getENDEWENNKeyword_5());
            		

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
    // $ANTLR end "ruleBusinessRule"


    // $ANTLR start "entryRuleOR_Node"
    // InternalDSL.g:230:1: entryRuleOR_Node returns [EObject current=null] : iv_ruleOR_Node= ruleOR_Node EOF ;
    public final EObject entryRuleOR_Node() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOR_Node = null;


        try {
            // InternalDSL.g:230:48: (iv_ruleOR_Node= ruleOR_Node EOF )
            // InternalDSL.g:231:2: iv_ruleOR_Node= ruleOR_Node EOF
            {
             newCompositeNode(grammarAccess.getOR_NodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOR_Node=ruleOR_Node();

            state._fsp--;

             current =iv_ruleOR_Node; 
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
    // $ANTLR end "entryRuleOR_Node"


    // $ANTLR start "ruleOR_Node"
    // InternalDSL.g:237:1: ruleOR_Node returns [EObject current=null] : ( ( (lv_node_0_0= ruleAND_Node ) ) ( ( (lv_or_1_0= 'ODER' ) ) ( (lv_subNode_2_0= ruleOR_Node ) ) )? ) ;
    public final EObject ruleOR_Node() throws RecognitionException {
        EObject current = null;

        Token lv_or_1_0=null;
        EObject lv_node_0_0 = null;

        EObject lv_subNode_2_0 = null;



        	enterRule();

        try {
            // InternalDSL.g:243:2: ( ( ( (lv_node_0_0= ruleAND_Node ) ) ( ( (lv_or_1_0= 'ODER' ) ) ( (lv_subNode_2_0= ruleOR_Node ) ) )? ) )
            // InternalDSL.g:244:2: ( ( (lv_node_0_0= ruleAND_Node ) ) ( ( (lv_or_1_0= 'ODER' ) ) ( (lv_subNode_2_0= ruleOR_Node ) ) )? )
            {
            // InternalDSL.g:244:2: ( ( (lv_node_0_0= ruleAND_Node ) ) ( ( (lv_or_1_0= 'ODER' ) ) ( (lv_subNode_2_0= ruleOR_Node ) ) )? )
            // InternalDSL.g:245:3: ( (lv_node_0_0= ruleAND_Node ) ) ( ( (lv_or_1_0= 'ODER' ) ) ( (lv_subNode_2_0= ruleOR_Node ) ) )?
            {
            // InternalDSL.g:245:3: ( (lv_node_0_0= ruleAND_Node ) )
            // InternalDSL.g:246:4: (lv_node_0_0= ruleAND_Node )
            {
            // InternalDSL.g:246:4: (lv_node_0_0= ruleAND_Node )
            // InternalDSL.g:247:5: lv_node_0_0= ruleAND_Node
            {

            					newCompositeNode(grammarAccess.getOR_NodeAccess().getNodeAND_NodeParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_10);
            lv_node_0_0=ruleAND_Node();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOR_NodeRule());
            					}
            					set(
            						current,
            						"node",
            						lv_node_0_0,
            						"com.specmate.objectif.DSL.AND_Node");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalDSL.g:264:3: ( ( (lv_or_1_0= 'ODER' ) ) ( (lv_subNode_2_0= ruleOR_Node ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==16) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalDSL.g:265:4: ( (lv_or_1_0= 'ODER' ) ) ( (lv_subNode_2_0= ruleOR_Node ) )
                    {
                    // InternalDSL.g:265:4: ( (lv_or_1_0= 'ODER' ) )
                    // InternalDSL.g:266:5: (lv_or_1_0= 'ODER' )
                    {
                    // InternalDSL.g:266:5: (lv_or_1_0= 'ODER' )
                    // InternalDSL.g:267:6: lv_or_1_0= 'ODER'
                    {
                    lv_or_1_0=(Token)match(input,16,FOLLOW_4); 

                    						newLeafNode(lv_or_1_0, grammarAccess.getOR_NodeAccess().getOrODERKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOR_NodeRule());
                    						}
                    						setWithLastConsumed(current, "or", true, "ODER");
                    					

                    }


                    }

                    // InternalDSL.g:279:4: ( (lv_subNode_2_0= ruleOR_Node ) )
                    // InternalDSL.g:280:5: (lv_subNode_2_0= ruleOR_Node )
                    {
                    // InternalDSL.g:280:5: (lv_subNode_2_0= ruleOR_Node )
                    // InternalDSL.g:281:6: lv_subNode_2_0= ruleOR_Node
                    {

                    						newCompositeNode(grammarAccess.getOR_NodeAccess().getSubNodeOR_NodeParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_subNode_2_0=ruleOR_Node();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOR_NodeRule());
                    						}
                    						set(
                    							current,
                    							"subNode",
                    							lv_subNode_2_0,
                    							"com.specmate.objectif.DSL.OR_Node");
                    						afterParserOrEnumRuleCall();
                    					

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
    // $ANTLR end "ruleOR_Node"


    // $ANTLR start "entryRuleAND_Node"
    // InternalDSL.g:303:1: entryRuleAND_Node returns [EObject current=null] : iv_ruleAND_Node= ruleAND_Node EOF ;
    public final EObject entryRuleAND_Node() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAND_Node = null;


        try {
            // InternalDSL.g:303:49: (iv_ruleAND_Node= ruleAND_Node EOF )
            // InternalDSL.g:304:2: iv_ruleAND_Node= ruleAND_Node EOF
            {
             newCompositeNode(grammarAccess.getAND_NodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAND_Node=ruleAND_Node();

            state._fsp--;

             current =iv_ruleAND_Node; 
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
    // $ANTLR end "entryRuleAND_Node"


    // $ANTLR start "ruleAND_Node"
    // InternalDSL.g:310:1: ruleAND_Node returns [EObject current=null] : ( ( (lv_node_0_0= ruleLiteral ) ) ( ( (lv_and_1_0= 'UND' ) ) ( (lv_subNode_2_0= ruleAND_Node ) ) )? ) ;
    public final EObject ruleAND_Node() throws RecognitionException {
        EObject current = null;

        Token lv_and_1_0=null;
        EObject lv_node_0_0 = null;

        EObject lv_subNode_2_0 = null;



        	enterRule();

        try {
            // InternalDSL.g:316:2: ( ( ( (lv_node_0_0= ruleLiteral ) ) ( ( (lv_and_1_0= 'UND' ) ) ( (lv_subNode_2_0= ruleAND_Node ) ) )? ) )
            // InternalDSL.g:317:2: ( ( (lv_node_0_0= ruleLiteral ) ) ( ( (lv_and_1_0= 'UND' ) ) ( (lv_subNode_2_0= ruleAND_Node ) ) )? )
            {
            // InternalDSL.g:317:2: ( ( (lv_node_0_0= ruleLiteral ) ) ( ( (lv_and_1_0= 'UND' ) ) ( (lv_subNode_2_0= ruleAND_Node ) ) )? )
            // InternalDSL.g:318:3: ( (lv_node_0_0= ruleLiteral ) ) ( ( (lv_and_1_0= 'UND' ) ) ( (lv_subNode_2_0= ruleAND_Node ) ) )?
            {
            // InternalDSL.g:318:3: ( (lv_node_0_0= ruleLiteral ) )
            // InternalDSL.g:319:4: (lv_node_0_0= ruleLiteral )
            {
            // InternalDSL.g:319:4: (lv_node_0_0= ruleLiteral )
            // InternalDSL.g:320:5: lv_node_0_0= ruleLiteral
            {

            					newCompositeNode(grammarAccess.getAND_NodeAccess().getNodeLiteralParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_11);
            lv_node_0_0=ruleLiteral();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAND_NodeRule());
            					}
            					set(
            						current,
            						"node",
            						lv_node_0_0,
            						"com.specmate.objectif.DSL.Literal");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalDSL.g:337:3: ( ( (lv_and_1_0= 'UND' ) ) ( (lv_subNode_2_0= ruleAND_Node ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==17) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalDSL.g:338:4: ( (lv_and_1_0= 'UND' ) ) ( (lv_subNode_2_0= ruleAND_Node ) )
                    {
                    // InternalDSL.g:338:4: ( (lv_and_1_0= 'UND' ) )
                    // InternalDSL.g:339:5: (lv_and_1_0= 'UND' )
                    {
                    // InternalDSL.g:339:5: (lv_and_1_0= 'UND' )
                    // InternalDSL.g:340:6: lv_and_1_0= 'UND'
                    {
                    lv_and_1_0=(Token)match(input,17,FOLLOW_4); 

                    						newLeafNode(lv_and_1_0, grammarAccess.getAND_NodeAccess().getAndUNDKeyword_1_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAND_NodeRule());
                    						}
                    						setWithLastConsumed(current, "and", true, "UND");
                    					

                    }


                    }

                    // InternalDSL.g:352:4: ( (lv_subNode_2_0= ruleAND_Node ) )
                    // InternalDSL.g:353:5: (lv_subNode_2_0= ruleAND_Node )
                    {
                    // InternalDSL.g:353:5: (lv_subNode_2_0= ruleAND_Node )
                    // InternalDSL.g:354:6: lv_subNode_2_0= ruleAND_Node
                    {

                    						newCompositeNode(grammarAccess.getAND_NodeAccess().getSubNodeAND_NodeParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_subNode_2_0=ruleAND_Node();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAND_NodeRule());
                    						}
                    						set(
                    							current,
                    							"subNode",
                    							lv_subNode_2_0,
                    							"com.specmate.objectif.DSL.AND_Node");
                    						afterParserOrEnumRuleCall();
                    					

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
    // $ANTLR end "ruleAND_Node"


    // $ANTLR start "entryRuleLiteral"
    // InternalDSL.g:376:1: entryRuleLiteral returns [EObject current=null] : iv_ruleLiteral= ruleLiteral EOF ;
    public final EObject entryRuleLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLiteral = null;


        try {
            // InternalDSL.g:376:48: (iv_ruleLiteral= ruleLiteral EOF )
            // InternalDSL.g:377:2: iv_ruleLiteral= ruleLiteral EOF
            {
             newCompositeNode(grammarAccess.getLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLiteral=ruleLiteral();

            state._fsp--;

             current =iv_ruleLiteral; 
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
    // $ANTLR end "entryRuleLiteral"


    // $ANTLR start "ruleLiteral"
    // InternalDSL.g:383:1: ruleLiteral returns [EObject current=null] : ( (lv_content_0_0= RULE_CHARACTER_SQUENCE ) )+ ;
    public final EObject ruleLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_content_0_0=null;


        	enterRule();

        try {
            // InternalDSL.g:389:2: ( ( (lv_content_0_0= RULE_CHARACTER_SQUENCE ) )+ )
            // InternalDSL.g:390:2: ( (lv_content_0_0= RULE_CHARACTER_SQUENCE ) )+
            {
            // InternalDSL.g:390:2: ( (lv_content_0_0= RULE_CHARACTER_SQUENCE ) )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_CHARACTER_SQUENCE) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalDSL.g:391:3: (lv_content_0_0= RULE_CHARACTER_SQUENCE )
            	    {
            	    // InternalDSL.g:391:3: (lv_content_0_0= RULE_CHARACTER_SQUENCE )
            	    // InternalDSL.g:392:4: lv_content_0_0= RULE_CHARACTER_SQUENCE
            	    {
            	    lv_content_0_0=(Token)match(input,RULE_CHARACTER_SQUENCE,FOLLOW_12); 

            	    				newLeafNode(lv_content_0_0, grammarAccess.getLiteralAccess().getContentCHARACTER_SQUENCETerminalRuleCall_0());
            	    			

            	    				if (current==null) {
            	    					current = createModelElement(grammarAccess.getLiteralRule());
            	    				}
            	    				addWithLastConsumed(
            	    					current,
            	    					"content",
            	    					lv_content_0_0,
            	    					"com.specmate.objectif.DSL.CHARACTER_SQUENCE");
            	    			

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
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
    // $ANTLR end "ruleLiteral"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000000000000D010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000009010L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000012L});

}