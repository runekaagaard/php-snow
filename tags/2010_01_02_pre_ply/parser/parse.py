"""
Parser for the Snow language.
Based on http://pyparsing.wikispaces.com/file/view/indentedGrammarExample.py.
"""

import sys
from pyparsing import *
from json import dumps

# Read file
f = open(sys.argv[1], "r")
data = f.read()

# Parse
indentStack = [1]

def checkPeerIndent(s,l,t):
    curCol = col(l,s)
    if curCol != indentStack[-1]:
        if (not indentStack) or curCol > indentStack[-1]:
            raise ParseFatalException(s,l,"illegal nesting")
        raise ParseException(s,l,"not a peer entry")

def checkSubIndent(s,l,t):
    curCol = col(l,s)
    if curCol > indentStack[-1]:
        indentStack.append( curCol )
    else:
        raise ParseException(s,l,"not a subentry")

def checkUnindent(s,l,t):
    if l >= len(s): return
    curCol = col(l,s)
    if not(curCol < indentStack[-1] and curCol <= indentStack[-2]):
        raise ParseException(s,l,"not an unindent")

def doUnindent():
    indentStack.pop()
    
INDENT = lineEnd.suppress() + empty + empty.copy().setParseAction(checkSubIndent)
UNDENT = FollowedBy(empty).setParseAction(checkUnindent)
UNDENT.setParseAction(doUnindent)

stmt = Forward()
suite = Group( OneOrMore( empty + stmt.setParseAction( checkPeerIndent ) )  )

identifier = Word(alphas, alphanums)

funcDeclShort = ("fn" + identifier)
funcDeclFull = (funcDeclShort + Group( "(" + Optional( delimitedList(identifier) ) + ")" ))
funcDef = Group( (funcDeclFull | funcDeclShort) + INDENT + suite + UNDENT )

rvalue = Forward()
funcCall = Group(identifier + "(" + Optional(delimitedList(rvalue)) + ")")
rvalue << (funcCall | identifier | Word(nums))
assignment = Group(identifier + "=" + rvalue)
stmt << ( funcDef | assignment | identifier )

# Output json
parseTree = suite.parseString(data)
print dumps( parseTree.asList()[0])
