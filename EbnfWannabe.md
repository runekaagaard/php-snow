# Functions #

## Standard syntax ##
### Definition ###
```
[ <identifier> "=" ] <function-identifier> "(" [ <parameter-list> ] ")"
<parameter-list> ::= ( <identifier> | <constant> ) ( [ " " ( <identifier> | <constant> ) ] )*
```

## Receiver syntax ##

### Definition ###
```
[ <identifier> "=" ] ( <identifier> | <constant> ) "->" <function-identifier> [ "(" [ <parameter-list> ] ")" ]
```

# For Loop #

## Definition ##
```
for_stmt ::=  "for" variable [variable] "in" expression_list 
for_stmt ::=  "for" variable "=" expression_list "to"|"downto" expression_list [step expression_list]
```

# Apply operator #
## Definition ##
```
<apply-operator> ::= "*"
```