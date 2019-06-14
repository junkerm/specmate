package com.specmate.objectif.internal.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDSLLexer extends Lexer {
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

    public InternalDSLLexer() {;} 
    public InternalDSLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalDSLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalDSL.g"; }

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:11:7: ( 'WENN' )
            // InternalDSL.g:11:9: 'WENN'
            {
            match("WENN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:12:7: ( 'DANN' )
            // InternalDSL.g:12:9: 'DANN'
            {
            match("DANN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:13:7: ( 'SONST' )
            // InternalDSL.g:13:9: 'SONST'
            {
            match("SONST"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:14:7: ( 'ENDE-WENN' )
            // InternalDSL.g:14:9: 'ENDE-WENN'
            {
            match("ENDE-WENN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:15:7: ( 'ODER' )
            // InternalDSL.g:15:9: 'ODER'
            {
            match("ODER"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:16:7: ( 'UND' )
            // InternalDSL.g:16:9: 'UND'
            {
            match("UND"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "RULE_CHARACTER_SQUENCE"
    public final void mRULE_CHARACTER_SQUENCE() throws RecognitionException {
        try {
            int _type = RULE_CHARACTER_SQUENCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:410:24: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '=' | '/' | '%' | '#' | '<' | '>' | '|' | '.' | '?' | ',' | '\"' | '(' | ')' )+ )
            // InternalDSL.g:410:26: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '=' | '/' | '%' | '#' | '<' | '>' | '|' | '.' | '?' | ',' | '\"' | '(' | ')' )+
            {
            // InternalDSL.g:410:26: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '=' | '/' | '%' | '#' | '<' | '>' | '|' | '.' | '?' | ',' | '\"' | '(' | ')' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\"' && LA1_0<='#')||LA1_0=='%'||(LA1_0>='(' && LA1_0<=')')||LA1_0==','||(LA1_0>='.' && LA1_0<='9')||(LA1_0>='<' && LA1_0<='?')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')||LA1_0=='|') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalDSL.g:
            	    {
            	    if ( (input.LA(1)>='\"' && input.LA(1)<='#')||input.LA(1)=='%'||(input.LA(1)>='(' && input.LA(1)<=')')||input.LA(1)==','||(input.LA(1)>='.' && input.LA(1)<='9')||(input.LA(1)>='<' && input.LA(1)<='?')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='|' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_CHARACTER_SQUENCE"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:412:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalDSL.g:412:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalDSL.g:412:11: ( '^' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='^') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalDSL.g:412:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalDSL.g:412:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalDSL.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:414:10: ( ( '0' .. '9' )+ )
            // InternalDSL.g:414:12: ( '0' .. '9' )+
            {
            // InternalDSL.g:414:12: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalDSL.g:414:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:416:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalDSL.g:416:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalDSL.g:416:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\"') ) {
                alt7=1;
            }
            else if ( (LA7_0=='\'') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalDSL.g:416:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalDSL.g:416:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop5:
                    do {
                        int alt5=3;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0=='\\') ) {
                            alt5=1;
                        }
                        else if ( ((LA5_0>='\u0000' && LA5_0<='!')||(LA5_0>='#' && LA5_0<='[')||(LA5_0>=']' && LA5_0<='\uFFFF')) ) {
                            alt5=2;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalDSL.g:416:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalDSL.g:416:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalDSL.g:416:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalDSL.g:416:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop6:
                    do {
                        int alt6=3;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0=='\\') ) {
                            alt6=1;
                        }
                        else if ( ((LA6_0>='\u0000' && LA6_0<='&')||(LA6_0>='(' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFF')) ) {
                            alt6=2;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // InternalDSL.g:416:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalDSL.g:416:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:418:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalDSL.g:418:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalDSL.g:418:24: ( options {greedy=false; } : . )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='*') ) {
                    int LA8_1 = input.LA(2);

                    if ( (LA8_1=='/') ) {
                        alt8=2;
                    }
                    else if ( ((LA8_1>='\u0000' && LA8_1<='.')||(LA8_1>='0' && LA8_1<='\uFFFF')) ) {
                        alt8=1;
                    }


                }
                else if ( ((LA8_0>='\u0000' && LA8_0<=')')||(LA8_0>='+' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalDSL.g:418:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:420:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalDSL.g:420:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalDSL.g:420:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\u0000' && LA9_0<='\t')||(LA9_0>='\u000B' && LA9_0<='\f')||(LA9_0>='\u000E' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalDSL.g:420:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            // InternalDSL.g:420:40: ( ( '\\r' )? '\\n' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='\n'||LA11_0=='\r') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalDSL.g:420:41: ( '\\r' )? '\\n'
                    {
                    // InternalDSL.g:420:41: ( '\\r' )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='\r') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // InternalDSL.g:420:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:422:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalDSL.g:422:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalDSL.g:422:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='\t' && LA12_0<='\n')||LA12_0=='\r'||LA12_0==' ') ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalDSL.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalDSL.g:424:16: ( . )
            // InternalDSL.g:424:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalDSL.g:1:8: ( T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | RULE_CHARACTER_SQUENCE | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt13=14;
        alt13 = dfa13.predict(input);
        switch (alt13) {
            case 1 :
                // InternalDSL.g:1:10: T__12
                {
                mT__12(); 

                }
                break;
            case 2 :
                // InternalDSL.g:1:16: T__13
                {
                mT__13(); 

                }
                break;
            case 3 :
                // InternalDSL.g:1:22: T__14
                {
                mT__14(); 

                }
                break;
            case 4 :
                // InternalDSL.g:1:28: T__15
                {
                mT__15(); 

                }
                break;
            case 5 :
                // InternalDSL.g:1:34: T__16
                {
                mT__16(); 

                }
                break;
            case 6 :
                // InternalDSL.g:1:40: T__17
                {
                mT__17(); 

                }
                break;
            case 7 :
                // InternalDSL.g:1:46: RULE_CHARACTER_SQUENCE
                {
                mRULE_CHARACTER_SQUENCE(); 

                }
                break;
            case 8 :
                // InternalDSL.g:1:69: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 9 :
                // InternalDSL.g:1:77: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 10 :
                // InternalDSL.g:1:86: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 11 :
                // InternalDSL.g:1:98: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 12 :
                // InternalDSL.g:1:114: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 13 :
                // InternalDSL.g:1:130: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 14 :
                // InternalDSL.g:1:138: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS =
        "\1\uffff\7\22\1\17\3\22\1\17\3\uffff\2\22\1\uffff\5\22\1\uffff\2\22\3\uffff\1\22\1\uffff\5\22\1\55\1\22\1\uffff\1\56\1\57\2\22\1\62\3\uffff\1\63\3\uffff";
    static final String DFA13_eofS =
        "\64\uffff";
    static final String DFA13_minS =
        "\1\0\7\60\1\101\1\60\1\0\1\52\1\0\3\uffff\2\60\1\uffff\5\60\1\uffff\1\60\1\0\3\uffff\1\0\1\uffff\5\60\1\42\1\0\1\uffff\2\42\1\60\1\55\1\42\3\uffff\1\42\3\uffff";
    static final String DFA13_maxS =
        "\1\uffff\10\172\1\71\1\uffff\1\57\1\uffff\3\uffff\2\172\1\uffff\5\172\1\uffff\1\71\1\uffff\3\uffff\1\uffff\1\uffff\5\172\1\174\1\uffff\1\uffff\2\174\2\172\1\174\3\uffff\1\174\3\uffff";
    static final String DFA13_acceptS =
        "\15\uffff\1\7\1\15\1\16\2\uffff\1\7\5\uffff\1\10\2\uffff\1\12\1\7\1\13\1\uffff\1\15\7\uffff\1\14\5\uffff\1\6\1\1\1\2\1\uffff\1\4\1\5\1\3";
    static final String DFA13_specialS =
        "\1\4\11\uffff\1\1\1\uffff\1\0\15\uffff\1\3\3\uffff\1\2\7\uffff\1\5\15\uffff}>";
    static final String[] DFA13_transitionS = {
            "\11\17\2\16\2\17\1\16\22\17\1\16\1\17\1\12\1\15\1\17\1\15\1\17\1\14\2\15\2\17\1\15\1\17\1\15\1\13\12\11\2\17\4\15\1\17\3\7\1\2\1\4\11\7\1\5\3\7\1\3\1\7\1\6\1\7\1\1\3\7\3\17\1\10\1\7\1\17\32\7\1\17\1\15\uff83\17",
            "\12\21\7\uffff\4\21\1\20\25\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\1\23\31\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\16\21\1\24\13\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\15\21\1\25\14\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\3\21\1\26\26\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\15\21\1\27\14\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\32\21\4\uffff\1\21\1\uffff\32\21",
            "\32\30\4\uffff\1\30\1\uffff\32\30",
            "\12\31",
            "\42\33\1\34\1\32\1\33\1\32\2\33\2\32\2\33\1\32\1\33\14\32\2\33\4\32\1\33\32\32\4\33\1\32\1\33\32\32\1\33\1\32\uff83\33",
            "\1\35\4\uffff\1\36",
            "\0\33",
            "",
            "",
            "",
            "\12\21\7\uffff\15\21\1\40\14\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\32\21\4\uffff\1\21\1\uffff\32\21",
            "",
            "\12\21\7\uffff\15\21\1\41\14\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\15\21\1\42\14\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\3\21\1\43\26\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\4\21\1\44\25\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\3\21\1\45\26\21\4\uffff\1\21\1\uffff\32\21",
            "",
            "\12\31",
            "\42\33\1\34\1\32\1\33\1\32\2\33\2\32\2\33\1\32\1\33\14\32\2\33\4\32\1\33\32\32\4\33\1\32\1\33\32\32\1\33\1\32\uff83\33",
            "",
            "",
            "",
            "\42\47\2\46\1\47\1\46\2\47\2\46\2\47\1\46\1\47\14\46\2\47\4\46\1\47\32\46\4\47\1\46\1\47\32\46\1\47\1\46\uff83\47",
            "",
            "\12\21\7\uffff\15\21\1\50\14\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\15\21\1\51\14\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\22\21\1\52\7\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\4\21\1\53\25\21\4\uffff\1\21\1\uffff\32\21",
            "\12\21\7\uffff\21\21\1\54\10\21\4\uffff\1\21\1\uffff\32\21",
            "\2\22\1\uffff\1\22\2\uffff\2\22\2\uffff\1\22\1\uffff\2\22\12\21\2\uffff\4\22\1\uffff\32\21\4\uffff\1\21\1\uffff\32\21\1\uffff\1\22",
            "\42\47\2\46\1\47\1\46\2\47\2\46\2\47\1\46\1\47\14\46\2\47\4\46\1\47\32\46\4\47\1\46\1\47\32\46\1\47\1\46\uff83\47",
            "",
            "\2\22\1\uffff\1\22\2\uffff\2\22\2\uffff\1\22\1\uffff\2\22\12\21\2\uffff\4\22\1\uffff\32\21\4\uffff\1\21\1\uffff\32\21\1\uffff\1\22",
            "\2\22\1\uffff\1\22\2\uffff\2\22\2\uffff\1\22\1\uffff\2\22\12\21\2\uffff\4\22\1\uffff\32\21\4\uffff\1\21\1\uffff\32\21\1\uffff\1\22",
            "\12\21\7\uffff\23\21\1\60\6\21\4\uffff\1\21\1\uffff\32\21",
            "\1\61\2\uffff\12\21\7\uffff\32\21\4\uffff\1\21\1\uffff\32\21",
            "\2\22\1\uffff\1\22\2\uffff\2\22\2\uffff\1\22\1\uffff\2\22\12\21\2\uffff\4\22\1\uffff\32\21\4\uffff\1\21\1\uffff\32\21\1\uffff\1\22",
            "",
            "",
            "",
            "\2\22\1\uffff\1\22\2\uffff\2\22\2\uffff\1\22\1\uffff\2\22\12\21\2\uffff\4\22\1\uffff\32\21\4\uffff\1\21\1\uffff\32\21\1\uffff\1\22",
            "",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | RULE_CHARACTER_SQUENCE | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA13_12 = input.LA(1);

                        s = -1;
                        if ( ((LA13_12>='\u0000' && LA13_12<='\uFFFF')) ) {s = 27;}

                        else s = 15;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA13_10 = input.LA(1);

                        s = -1;
                        if ( (LA13_10=='#'||LA13_10=='%'||(LA13_10>='(' && LA13_10<=')')||LA13_10==','||(LA13_10>='.' && LA13_10<='9')||(LA13_10>='<' && LA13_10<='?')||(LA13_10>='A' && LA13_10<='Z')||LA13_10=='_'||(LA13_10>='a' && LA13_10<='z')||LA13_10=='|') ) {s = 26;}

                        else if ( ((LA13_10>='\u0000' && LA13_10<='!')||LA13_10=='$'||(LA13_10>='&' && LA13_10<='\'')||(LA13_10>='*' && LA13_10<='+')||LA13_10=='-'||(LA13_10>=':' && LA13_10<=';')||LA13_10=='@'||(LA13_10>='[' && LA13_10<='^')||LA13_10=='`'||LA13_10=='{'||(LA13_10>='}' && LA13_10<='\uFFFF')) ) {s = 27;}

                        else if ( (LA13_10=='\"') ) {s = 28;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA13_30 = input.LA(1);

                        s = -1;
                        if ( ((LA13_30>='\"' && LA13_30<='#')||LA13_30=='%'||(LA13_30>='(' && LA13_30<=')')||LA13_30==','||(LA13_30>='.' && LA13_30<='9')||(LA13_30>='<' && LA13_30<='?')||(LA13_30>='A' && LA13_30<='Z')||LA13_30=='_'||(LA13_30>='a' && LA13_30<='z')||LA13_30=='|') ) {s = 38;}

                        else if ( ((LA13_30>='\u0000' && LA13_30<='!')||LA13_30=='$'||(LA13_30>='&' && LA13_30<='\'')||(LA13_30>='*' && LA13_30<='+')||LA13_30=='-'||(LA13_30>=':' && LA13_30<=';')||LA13_30=='@'||(LA13_30>='[' && LA13_30<='^')||LA13_30=='`'||LA13_30=='{'||(LA13_30>='}' && LA13_30<='\uFFFF')) ) {s = 39;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA13_26 = input.LA(1);

                        s = -1;
                        if ( (LA13_26=='\"') ) {s = 28;}

                        else if ( ((LA13_26>='\u0000' && LA13_26<='!')||LA13_26=='$'||(LA13_26>='&' && LA13_26<='\'')||(LA13_26>='*' && LA13_26<='+')||LA13_26=='-'||(LA13_26>=':' && LA13_26<=';')||LA13_26=='@'||(LA13_26>='[' && LA13_26<='^')||LA13_26=='`'||LA13_26=='{'||(LA13_26>='}' && LA13_26<='\uFFFF')) ) {s = 27;}

                        else if ( (LA13_26=='#'||LA13_26=='%'||(LA13_26>='(' && LA13_26<=')')||LA13_26==','||(LA13_26>='.' && LA13_26<='9')||(LA13_26>='<' && LA13_26<='?')||(LA13_26>='A' && LA13_26<='Z')||LA13_26=='_'||(LA13_26>='a' && LA13_26<='z')||LA13_26=='|') ) {s = 26;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA13_0 = input.LA(1);

                        s = -1;
                        if ( (LA13_0=='W') ) {s = 1;}

                        else if ( (LA13_0=='D') ) {s = 2;}

                        else if ( (LA13_0=='S') ) {s = 3;}

                        else if ( (LA13_0=='E') ) {s = 4;}

                        else if ( (LA13_0=='O') ) {s = 5;}

                        else if ( (LA13_0=='U') ) {s = 6;}

                        else if ( ((LA13_0>='A' && LA13_0<='C')||(LA13_0>='F' && LA13_0<='N')||(LA13_0>='P' && LA13_0<='R')||LA13_0=='T'||LA13_0=='V'||(LA13_0>='X' && LA13_0<='Z')||LA13_0=='_'||(LA13_0>='a' && LA13_0<='z')) ) {s = 7;}

                        else if ( (LA13_0=='^') ) {s = 8;}

                        else if ( ((LA13_0>='0' && LA13_0<='9')) ) {s = 9;}

                        else if ( (LA13_0=='\"') ) {s = 10;}

                        else if ( (LA13_0=='/') ) {s = 11;}

                        else if ( (LA13_0=='\'') ) {s = 12;}

                        else if ( (LA13_0=='#'||LA13_0=='%'||(LA13_0>='(' && LA13_0<=')')||LA13_0==','||LA13_0=='.'||(LA13_0>='<' && LA13_0<='?')||LA13_0=='|') ) {s = 13;}

                        else if ( ((LA13_0>='\t' && LA13_0<='\n')||LA13_0=='\r'||LA13_0==' ') ) {s = 14;}

                        else if ( ((LA13_0>='\u0000' && LA13_0<='\b')||(LA13_0>='\u000B' && LA13_0<='\f')||(LA13_0>='\u000E' && LA13_0<='\u001F')||LA13_0=='!'||LA13_0=='$'||LA13_0=='&'||(LA13_0>='*' && LA13_0<='+')||LA13_0=='-'||(LA13_0>=':' && LA13_0<=';')||LA13_0=='@'||(LA13_0>='[' && LA13_0<=']')||LA13_0=='`'||LA13_0=='{'||(LA13_0>='}' && LA13_0<='\uFFFF')) ) {s = 15;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA13_38 = input.LA(1);

                        s = -1;
                        if ( ((LA13_38>='\"' && LA13_38<='#')||LA13_38=='%'||(LA13_38>='(' && LA13_38<=')')||LA13_38==','||(LA13_38>='.' && LA13_38<='9')||(LA13_38>='<' && LA13_38<='?')||(LA13_38>='A' && LA13_38<='Z')||LA13_38=='_'||(LA13_38>='a' && LA13_38<='z')||LA13_38=='|') ) {s = 38;}

                        else if ( ((LA13_38>='\u0000' && LA13_38<='!')||LA13_38=='$'||(LA13_38>='&' && LA13_38<='\'')||(LA13_38>='*' && LA13_38<='+')||LA13_38=='-'||(LA13_38>=':' && LA13_38<=';')||LA13_38=='@'||(LA13_38>='[' && LA13_38<='^')||LA13_38=='`'||LA13_38=='{'||(LA13_38>='}' && LA13_38<='\uFFFF')) ) {s = 39;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}