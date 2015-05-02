## States ##
OK how to lex Snow? First lets consider the states.

  * snow: Default state.
  * Single singlequotes: `'inside'`
  * Single doublequotes: `"inside"`
  * Triple singlequotes: `'''inside'''`
  * Triple doublequotes: `"""inside"""`
  * Inline html: `%>inside<%`
  * Parenthesis: `(inside)`
  * Square brackets: `[inside]`

## Indentation / Whitespace ##
Before lexing all lines ending with a single `\` should have the next line
appended to it.

The following keywords forces an indentation on the following line. The use
thereafter of `:` in the same line cancels that indentation.

  * if
  * else
  * elif
  * for
  * switch
  * case
  * default
  * fn
  * class
  * con / pub / pro / pri / sta
  * while

Some keywords are more special and _can_ trigger an indentation.

  * ??
  * ?!

Whitespace works differently when inside `()` or `[]`. ..

## Parsing " and """ strings ##
The current Python lexer operates with both STRING and STRING\_CONTINUE tokens.
It then runs a postprocessing filter that joins these tokens.

Given that we also need to process {var/$constant/func\_tion()/etc.} in Snow
and also handle Snow-in-strings-in-snow recursion I have a feeling that there is
a conceptually simpler way of doing it, only using the t\_XXX functions and
storing stuff in t.lexer.XXX variables. At least it is good to try and explain
whats going on.

The flow for " could be like (repeat or do magic for """):

```
INITIAL
    t_STRING_BEGIN: '"', pushes state IN_DOUBLEQUOTED_STRING. Pushes an empty
        string to the stack lex.tokens.quoted_string_stack[n+1].

IN_DOUBLEQUOTED_STRING (exclusive)
    t_IN_DOUBLEQUOTED_STRING_STRING: Everything not '"' or '{'. Does not return
        a token but adds its value to lex.tokens.quoted_string_stack[n].
    
    t_IN_DOUBLEQUOTED_STRING_STRING_END: '"', pops state and we are back in 
        INITIAL. Returns a STRING token with the value of 
        lex.tokens.quoted_string_stack[n] and pops it. If nothing is in the
        stack a STRING token with the value '' is returned.
        
    t_IN_DOUBLEQUOTED_STRING_SNOW_BEGIN: "{", pushes state 
        SNOW_IN_DOUBLEQUOTED_STRING. Returns the same as 
        t_IN_DOUBLEQUOTED_STRING_STRING_END.

SNOW_IN_DOUBLEQUOTED_STRING (inclusive)
    SNOW_IN_DOUBLEQUOTED_STRING_SNOW_END: "}", pops state and we are back in
        IN_DOUBLEQUOTED_STRING.
```