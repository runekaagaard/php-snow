from ply import lex
import re
from error import raise_indentation_error
from standard import INDENTATION_TRIGGERS

def _new_token(type, lineno):
    tok = lex.LexToken()
    tok.type = type
    tok.value = None
    tok.lineno = lineno
    tok.lexpos = -1
    return tok

# Synthesize a DEDENT tag.
def DEDENT(lineno):
    return _new_token("DEDENT", lineno)

# Synthesize an INDENT tag.
def INDENT(lineno):
    return _new_token("INDENT", lineno)

# Error token.
def t_error(t):
    raise_syntax_error("invalid syntax", t)

##### Keep track of indentation state

# I implemented INDENT / DEDENT generation as a post-processing filter

# The original lex token stream contains WS and NEWLINE characters.
# WS will only occur before any other tokens on a line.

# I have three filters.  One tags tokens by adding two attributes.
# "must_indent" is True if the token must be indented from the
# previous code.  The other is "at_line_start" which is True for WS
# and the first non-WS/non-NEWLINE on a line.  It flags the check so
# see if the new line has changed indication level.

# Python's syntax has three INDENT states
#  0) no colon hence no need to indent
#  1) "if 1: go()" - simple statements have a COLON but no need for an indent
#  2) "if 1:\n  go()" - complex statements have a COLON NEWLINE and must indent
NO_INDENT = 0
MAY_INDENT = 1
MUST_INDENT = 2

# only care about whitespace at the start of a line
def annotate_indentation_state(lexer, token_stream):
    lexer.at_line_start = at_line_start = True
    indent = NO_INDENT
    indent_expected = False
    prev_was_newline = False
    for token in token_stream:
        token.at_line_start = at_line_start
        # If token if one of those who triggers an indentation we expect an
        # indentation after next newline (omitting whitespace though).
        if token.type in INDENTATION_TRIGGERS:
            indent_expected = True
            at_line_start = False
            # If we are already expecting indentation and the last token was a
            # newline this token should also indent.
            token.must_indent = (prev_was_newline and
                                 indent in (MAY_INDENT, MUST_INDENT))
            indent = MAY_INDENT
            prev_was_newline = False
        
        # A colon cancels expected indentation.
        elif token.type == 'COLON':
            indent_expected = False
            token.must_indent = False
            at_line_start = False
            prev_was_newline = False
        
        # New line can trigger a need for indentation if it is expected.
        elif token.type == "NEWLINE":
            prev_was_newline = True
            at_line_start = True
            if indent == MAY_INDENT:
                indent = MUST_INDENT
            token.must_indent = False

        # Whitespace does not change indent_expected.
        elif token.type == "WS":
            assert token.at_line_start == True
            at_line_start = True
            token.must_indent = False
        
        # Normal token.
        else:
            if indent == MUST_INDENT:
                token.must_indent = True
                indent_expected = False
            else:
                token.must_indent = False
            at_line_start = False
            # Dont reset indent if we are waiting for an indent.
            if not indent_expected:
                indent = NO_INDENT
            prev_was_newline = False

        yield token
        lexer.at_line_start = at_line_start

# Track the indentation level and emit the right INDENT / DEDENT events.
def synthesize_indentation_tokens(lexer, token_stream):
    # A stack of indentation levels; will never pop item 0
    levels = [0]
    token = None
    depth = 0
    prev_was_ws = False
    for token in token_stream:           
        token.lexer = lexer     
        # WS only occurs at the start of the line
        # There may be WS followed by NEWLINE so
        # only track the depth here.  Don't indent/dedent
        # until there's something real.
        if token.type == "WS":
            assert depth == 0
            depth = len(token.value)
            prev_was_ws = True
            # WS tokens are never passed to the parser
            continue

        if token.type == "NEWLINE":
            depth = 0
            if prev_was_ws or token.at_line_start:
                # ignore blank lines
                continue
            # pass the other cases on through
            yield token
            continue

        # then it must be a real token (not WS, not NEWLINE)
        # which can affect the indentation level

        prev_was_ws = False
        if token.must_indent:
            # The current depth must be larger than the previous level
            if not (depth > levels[-1]):
                raise_indentation_error("expected an indented block", token)

            levels.append(depth)
            yield INDENT(token.lineno)

        elif token.at_line_start:
            # Must be on the same level or one of the previous levels
            if depth == levels[-1]:
                # At the same level
                pass
            elif depth > levels[-1]:
                # indentation increase but not in new block
                raise_indentation_error("unexpected indent", token)
            else:
                # Back up; but only if it matches a previous level
                try:
                    i = levels.index(depth)
                except ValueError:
                    # I report the error position at the start of the
                    # token.  Python reports it at the end.  I prefer mine.
                    raise_indentation_error("unindent does not match any outer " 
                                            "indentation level", token)
                for _ in range(i+1, len(levels)):
                    yield DEDENT(token.lineno)
                    levels.pop()

        yield token
    # Must dedent any remaining levels
    if len(levels) > 1:
        assert token is not None
        for _ in range(1, len(levels)):
            yield DEDENT(token.lineno)
    

def add_endmarker(token_stream):
    tok = None
    for tok in token_stream:
        yield tok
    if tok is not None:
        lineno = tok.lineno
    else:
        lineno = 1
    yield _new_token("ENDMARKER", lineno)
_add_endmarker = add_endmarker

def make_token_stream(lexer, add_endmarker = True):
    token_stream = iter(lexer.token, None)
    token_stream = annotate_indentation_state(lexer, token_stream)
    token_stream = synthesize_indentation_tokens(lexer, token_stream)
    if add_endmarker:
        token_stream = _add_endmarker(token_stream)
    return token_stream

_newline_pattern = re.compile(r"\n")
def get_line_offsets(text):
    offsets = [0]
    for m in _newline_pattern.finditer(text):
        offsets.append(m.end())
    # This is only really needed if the input does not end with a newline
    offsets.append(len(text))
    return offsets

